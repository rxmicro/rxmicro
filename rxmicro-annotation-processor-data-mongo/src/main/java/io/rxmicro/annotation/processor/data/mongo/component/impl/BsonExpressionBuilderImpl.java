/*
 * Copyright (c) 2020. https://rxmicro.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.rxmicro.annotation.processor.data.mongo.component.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.mongodb.BasicDBObject;
import io.rxmicro.annotation.processor.common.component.TokenParser;
import io.rxmicro.annotation.processor.common.model.ClassHeader;
import io.rxmicro.annotation.processor.common.model.TokenParserResult;
import io.rxmicro.annotation.processor.common.model.TokenParserRule;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.annotation.processor.data.model.Variable;
import io.rxmicro.annotation.processor.data.mongo.component.BsonExpressionBuilder;
import io.rxmicro.annotation.processor.data.mongo.model.BsonExpression;
import io.rxmicro.annotation.processor.data.mongo.model.BsonTokenParserRule;
import org.bson.internal.UuidHelper;
import org.bson.json.JsonParseException;
import org.bson.types.Binary;
import org.bson.types.Decimal128;
import org.bson.types.ObjectId;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import javax.lang.model.element.ExecutableElement;

import static io.rxmicro.annotation.processor.common.util.Elements.isNotStandardEnum;
import static io.rxmicro.common.util.Formats.FORMAT_PLACEHOLDER_TOKEN;
import static io.rxmicro.common.util.Formats.format;
import static java.util.Map.entry;
import static java.util.stream.Collectors.joining;
import static org.bson.BsonBinarySubType.UUID_LEGACY;
import static org.bson.UuidRepresentation.JAVA_LEGACY;

/**
 * @author nedis
 * @since 0.1
 */
@Singleton
public final class BsonExpressionBuilderImpl implements BsonExpressionBuilder {

    private static final String PREFIX = "#$%@_~";

    private static final Set<String> PLACEHOLDERS = Set.of(
            FORMAT_PLACEHOLDER_TOKEN,
            format("'?'", FORMAT_PLACEHOLDER_TOKEN),
            format("\"?\"", FORMAT_PLACEHOLDER_TOKEN)
    );

    private static final Set<Class<?>> SUPPORTED_EXPRESSION_TYPES = Set.of(
            ObjectId.class,
            Boolean.class,
            Byte.class,
            Short.class,
            Integer.class,
            Long.class,
            Float.class,
            Double.class,
            BigDecimal.class,
            Character.class,
            String.class,
            Instant.class,
            UUID.class
    );

    @Inject
    private TokenParser tokenParser;

    @Inject
    @BsonTokenParserRule
    private TokenParserRule bsonTokenParserRule;

    @Override
    public BsonExpression build(final ExecutableElement repositoryMethod,
                                final ClassHeader.Builder classHeaderBuilder,
                                final String expressionTemplate,
                                final MethodParameterReader methodParameterReader) {
        final Map.Entry<String, Integer> entry = getTempValidExpression(expressionTemplate);
        final BasicDBObject basicDBObject;
        try {
            basicDBObject = BasicDBObject.parse(entry.getKey());
        } catch (final JsonParseException ex) {
            throw new InterruptProcessingException(
                    repositoryMethod,
                    "Expression '?' is invalid: ?!",
                    expressionTemplate,
                    ex.getMessage()
            );
        }
        final List<Variable> arguments = methodParameterReader.getVars(repositoryMethod, entry.getValue());
        final List<String> lines = getLines(repositoryMethod, classHeaderBuilder, arguments, basicDBObject);
        return new BsonExpression(expressionTemplate, lines);
    }

    private List<String> getLines(final ExecutableElement repositoryMethod,
                                  final ClassHeader.Builder classHeaderBuilder,
                                  final List<Variable> arguments,
                                  final BasicDBObject basicDBObject) {
        if (basicDBObject.size() == 1 && !(basicDBObject.values().iterator().next() instanceof BasicDBObject)) {
            final Map.Entry<String, Object> entry = basicDBObject.entrySet().iterator().next();
            return List.of(format(
                    "new Document(\"?\", ?);",
                    entry.getKey(),
                    arguments.isEmpty() ?
                            getSimpleValue(repositoryMethod, classHeaderBuilder, entry.getValue()) :
                            getArgumentValue(arguments.get(0))
            ));
        } else {
            final List<String> lines = new ArrayList<>();
            lines.add("new Document()");
            extractCodeLines(repositoryMethod, classHeaderBuilder, lines, basicDBObject, arguments.iterator(), 1);
            // Add ';' to the last line
            lines.set(lines.size() - 1, lines.get(lines.size() - 1) + ";");
            return lines;
        }
    }

    private Map.Entry<String, Integer> getTempValidExpression(final String template) {
        final TokenParserResult result = tokenParser.parse(template, bsonTokenParserRule, false);
        final AtomicInteger index = new AtomicInteger(0);
        final String exp = result.getTokens().stream()
                .map(token -> {
                    if (PLACEHOLDERS.contains(token)) {
                        index.getAndIncrement();
                        return "'" + PREFIX + "'";
                    } else {
                        return token;
                    }
                })
                .collect(joining(" "));
        return entry(exp, index.get());
    }

    private void extractCodeLines(final ExecutableElement repositoryMethod,
                                  final ClassHeader.Builder classHeaderBuilder,
                                  final List<String> lines,
                                  final BasicDBObject basicDBObject,
                                  final Iterator<Variable> varIterator,
                                  final int shift) {
        for (final Map.Entry<String, Object> entry : basicDBObject.entrySet()) {
            final Object value = entry.getValue();
            if (value instanceof BasicDBObject) {
                lines.add(format("?.append(\"?\", new Document()", spaces(shift), entry.getKey()));
                extractCodeLines(repositoryMethod, classHeaderBuilder, lines, (BasicDBObject) value, varIterator, shift + 1);
                lines.add(spaces(shift) + ")");
            } else if (value instanceof String) {
                final String string = (String) value;
                if (PREFIX.equals(string)) {
                    lines.add(format(
                            "?.append(\"?\", ?)",
                            spaces(shift), entry.getKey(), getArgumentValue(varIterator.next())
                    ));
                } else {
                    lines.add(format(
                            "?.append(\"?\", \"?\")",
                            spaces(shift), entry.getKey(), string
                    ));
                }
            } else {
                lines.add(format(
                        "?.append(\"?\", ?)",
                        spaces(shift), entry.getKey(), getSimpleValue(repositoryMethod, classHeaderBuilder, entry.getValue())
                ));
            }
        }
    }

    private String getSimpleValue(final ExecutableElement repositoryMethod,
                                  final ClassHeader.Builder classHeaderBuilder,
                                  final Object value) {
        final Optional<String> simpleValueByType = getSimpleValueByType(classHeaderBuilder, value);
        if (simpleValueByType.isPresent()) {
            return simpleValueByType.get();
        } else if (SUPPORTED_EXPRESSION_TYPES.contains(value.getClass())) {
            return value.toString();
        } else if (value instanceof Binary && ((Binary) value).getType() == UUID_LEGACY.getValue()) {
            classHeaderBuilder.addImports(UUID.class);
            final UUID uuid = UuidHelper.decodeBinaryToUuid(((Binary) value).getData(), UUID_LEGACY.getValue(), JAVA_LEGACY);
            return format("UUID.fromString(\"?\")", uuid.toString());
        } else {
            throw new InterruptProcessingException(
                    repositoryMethod,
                    "'?' type is not supported as expression type! Use one of the following only: '?'",
                    value.getClass(),
                    SUPPORTED_EXPRESSION_TYPES
            );
        }
    }

    private Optional<String> getSimpleValueByType(final ClassHeader.Builder classHeaderBuilder,
                                                  final Object value) {
        if (value instanceof Long) {
            return Optional.of(format("?L", value));
        } else if (value instanceof Decimal128) {
            classHeaderBuilder.addImports(BigDecimal.class);
            return Optional.of(format("new BigDecimal(\"?\")", value));
        } else if (value instanceof ObjectId) {
            classHeaderBuilder.addImports(ObjectId.class);
            return Optional.of(format("new ObjectId(\"?\")", value));
        } else if (value instanceof UUID) {
            classHeaderBuilder.addImports(UUID.class);
            return Optional.of(format("UUID.fromString(\"?\")", value));
        } else if (value instanceof Date) {
            classHeaderBuilder.addImports(Instant.class);
            return Optional.of(format("Instant.ofEpochMilli(?L)", ((Date) value).getTime()));
        } else {
            return Optional.empty();
        }
    }

    private String getArgumentValue(final Variable variable) {
        if (isNotStandardEnum(variable.getType())) {
            return format("?.mongo()", variable.getName());
        } else {
            return variable.getGetter();
        }
    }

    private String spaces(final int shift) {
        return "        ".repeat(shift);
    }
}
