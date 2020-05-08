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

package io.rxmicro.test.mockito.mongo.internal.util;

import org.bson.BsonRegularExpression;
import org.bson.BsonWriter;
import org.bson.Document;
import org.bson.codecs.PatternCodec;
import org.bson.types.Decimal128;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

import static java.lang.reflect.Proxy.newProxyInstance;

/**
 * @author nedis
 * @since 0.1
 */
public final class TypeFixer {

    private static final PatternCodec PATTERN_CODEC = new PatternCodec();

    public static void fixDocumentTypes(final Document... documents) {
        for (final Document document : documents) {
            fixDocumentType(document);
        }
    }

    public static void fixDocumentType(final Document document) {
        for (final Map.Entry<String, Object> entry : document.entrySet()) {
            final Object value = entry.getValue();
            if (value instanceof Document) {
                fixDocumentType((Document) value);
            } else if (value instanceof BigDecimal) {
                entry.setValue(Decimal128.parse(((BigDecimal) value).toPlainString()));
            } else if (value instanceof Double || value instanceof Float) {
                entry.setValue(((Number) value).doubleValue());
            } else if (value instanceof Byte || value instanceof Short) {
                entry.setValue(((Number) value).intValue());
            } else if (value instanceof Character) {
                entry.setValue(String.valueOf(value));
            } else if (value instanceof Pattern) {
                entry.setValue(toBsonRegularExpression((Pattern) value));
            } else if (value instanceof LocalDate) {
                entry.setValue(toDate(((LocalDate) value).atStartOfDay(ZoneId.systemDefault()).toInstant()));
            } else if (value instanceof LocalDateTime) {
                entry.setValue(toDate(((LocalDateTime) value).atZone(ZoneId.systemDefault()).toInstant()));
            } else if (value instanceof LocalTime) {
                entry.setValue(toDate(((LocalTime) value).atOffset(ZoneOffset.UTC).atDate(LocalDate.now()).toInstant()));
            } else if (value instanceof Instant) {
                entry.setValue(value);
            } else if (value instanceof Enum) {
                entry.setValue(((Enum<?>) value).name());
            } else if (value instanceof UUID) {
                throw new UnsupportedOperationException("Use Binary data!");
            }
        }
    }

    private static Date toDate(final Instant instant) {
        return new Date(instant.toEpochMilli());
    }

    private static BsonRegularExpression toBsonRegularExpression(final Pattern value) {
        final List<BsonRegularExpression> list = new ArrayList<>(1);
        final BsonWriter writer = (BsonWriter) newProxyInstance(
                BsonWriter.class.getClassLoader(),
                new Class[]{BsonWriter.class}, (proxy, method, args) -> {
                    list.add((BsonRegularExpression) args[0]);
                    return null;
                });
        PATTERN_CODEC.encode(writer, value, null);
        return list.get(0);
    }

    private TypeFixer() {
    }
}
