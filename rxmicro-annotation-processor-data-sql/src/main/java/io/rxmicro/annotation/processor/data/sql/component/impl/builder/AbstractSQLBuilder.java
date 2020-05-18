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

package io.rxmicro.annotation.processor.data.sql.component.impl.builder;

import com.google.inject.Inject;
import io.rxmicro.annotation.processor.common.component.impl.AbstractProcessorComponent;
import io.rxmicro.annotation.processor.common.model.ClassHeader;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.annotation.processor.data.model.Variable;
import io.rxmicro.annotation.processor.data.sql.component.PlatformPlaceHolderGeneratorFactory;
import io.rxmicro.annotation.processor.data.sql.component.impl.builder.select.SelectSQLOperatorReader;
import io.rxmicro.annotation.processor.data.sql.model.PlatformPlaceHolderGenerator;
import io.rxmicro.annotation.processor.data.sql.model.VariableValuesMap;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;

import static io.rxmicro.common.util.Formats.FORMAT_PLACEHOLDER_TOKEN;
import static java.util.stream.Collectors.toSet;

/**
 * @author nedis
 * @since 0.1
 */
public abstract class AbstractSQLBuilder extends AbstractProcessorComponent {

    @Inject
    private Set<SelectSQLOperatorReader> selectSQLOperatorReaders;

    @Inject
    private PlatformPlaceHolderGeneratorFactory platformPlaceHolderGeneratorFactory;

    protected void validateSupportedVars(final Class<? extends Annotation> annotationClass,
                                         final ExecutableElement method,
                                         final Iterable<String> vars,
                                         final Set<String> supportedSelectVariableNames,
                                         final Set<String> extVariables,
                                         final String... additionalVariables) {
        final Set<String> set =
                Stream.of(
                        supportedSelectVariableNames.stream(),
                        extVariables.stream(),
                        Arrays.stream(additionalVariables)
                )
                        .flatMap(Function.identity())
                        .collect(toSet());
        for (final String var : vars) {
            if (!set.contains(var)) {
                throw new InterruptProcessingException(
                        method,
                        "Unsupported @? variables: '?'. Supported variables are: ?",
                        annotationClass.getSimpleName(),
                        var,
                        supportedSelectVariableNames
                );
            }
        }
    }

    protected final Set<String> extractVariables(final ExecutableElement method,
                                                 final List<String> sqlTokens,
                                                 final List<String> extraVarNames) {
        return sqlTokens.stream()
                .filter(token -> extraVarNames.contains(token) || token.startsWith("${"))
                .peek(token -> {
                    if (token.startsWith("${") && !token.endsWith("}")) {
                        throw new InterruptProcessingException(method, "Missing '}' for variable: ?", token.substring(2));
                    }
                }).collect(toSet());
    }

    protected final void setVariableValues(final ExecutableElement method,
                                           final List<String> sqlTokens,
                                           final Set<String> vars,
                                           final VariableValuesMap variableValuesMap) {
        if (!vars.isEmpty()) {
            final Set<String> removableVars = new HashSet<>(vars);
            int index = 0;
            while (index < sqlTokens.size()) {
                final String token = sqlTokens.get(index);
                if ("*".equals(token) && isAsteriskShouldBeIgnored(index, sqlTokens)) {
                    index++;
                    continue;
                }
                if (vars.contains(token)) {
                    removableVars.remove(token);
                    if (variableValuesMap.isStringValue(token)) {
                        sqlTokens.set(index, variableValuesMap.getString(token));
                    } else if (variableValuesMap.isSqlVariableValue(token)) {
                        sqlTokens.remove(index);
                        final List<String> tokens = variableValuesMap.getSqlVariableValue(token).getSQLTokens();
                        sqlTokens.addAll(index, tokens);
                        index += tokens.size();
                    } else {
                        throw new InterruptProcessingException(
                                method,
                                "Variable '?' couldn't be resolved. Remove this variable or add missing config param.",
                                token
                        );
                    }
                }
                index++;
            }
            // Asterisk is not a variable
            removableVars.remove("*");
            if (!removableVars.isEmpty()) {
                throw new InterruptProcessingException(
                        method,
                        "Variable(s) couldn't be resolved: '?'. Remote it or configure missing components!",
                        removableVars
                );
            }
        }
    }

    protected abstract boolean isAsteriskShouldBeIgnored(int index,
                                                         List<String> sqlTokens);

    protected final void validatePlaceholderCount(final ExecutableElement method,
                                                  final List<String> sqlTokens,
                                                  final List<?> methodParams) {
        final long placeholderCount = sqlTokens.stream().filter(FORMAT_PLACEHOLDER_TOKEN::equals).count();
        final long methodParamSize = methodParams.size();
        if (placeholderCount < methodParamSize) {
            throw new InterruptProcessingException(
                    method,
                    "Missing param placeholder(s): Expected ?, but actual is ?",
                    methodParamSize, placeholderCount
            );
        } else if (placeholderCount > methodParamSize) {
            throw new InterruptProcessingException(
                    method,
                    "Redundant param placeholder(s): Expected ?, but actual is ?",
                    methodParamSize, placeholderCount
            );
        }
    }

    protected final void replaceAllPlaceholders(final List<String> sqlTokens) {
        final PlatformPlaceHolderGenerator platformPlaceHolderGenerator =
                platformPlaceHolderGeneratorFactory.create();
        for (int i = 0; i < sqlTokens.size(); i++) {
            if (FORMAT_PLACEHOLDER_TOKEN.equals(sqlTokens.get(i))) {
                sqlTokens.set(i, platformPlaceHolderGenerator.getNextPlaceHolder());
            }
        }
    }

    protected final void splitParams(final Element owner,
                                     final ClassHeader.Builder classHeaderBuilder,
                                     final List<String> sqlTokens,
                                     final List<Variable> methodParams,
                                     final List<String> formatParams,
                                     final List<String> bindParams) {
        final PlatformPlaceHolderGenerator platformPlaceHolderGenerator =
                platformPlaceHolderGeneratorFactory.create();
        final ListIterator<String> iterator = sqlTokens.listIterator();
        while (iterator.hasNext()) {
            final String token = iterator.next();
            for (final SelectSQLOperatorReader reader : selectSQLOperatorReaders) {
                if (reader.canRead(token)) {
                    reader.read(classHeaderBuilder, iterator, methodParams, formatParams);
                    break;
                }
            }
            if (FORMAT_PLACEHOLDER_TOKEN.equals(token)) {
                if (methodParams.isEmpty()) {
                    throw new InterruptProcessingException(owner, "Redundant '?' placeholder! Remove it!");
                }
                iterator.set(platformPlaceHolderGenerator.getNextPlaceHolder());
                bindParams.add(methodParams.remove(0).getGetter());
            }
        }
    }
}
