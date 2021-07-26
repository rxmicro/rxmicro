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

package io.rxmicro.config.internal;

import io.rxmicro.config.ConfigException;
import io.rxmicro.config.internal.component.ConverterConfigurator;
import io.rxmicro.config.internal.component.ToCustomTypeConverter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @author nedis
 * @since 0.1
 */
public final class Converters {

    private static final ToCustomTypeConverter TO_CUSTOM_TYPE_CONVERTER =
            new ToCustomTypeConverter();

    private static final Map<Class<?>, Function<String, Object>> CONVERTERS_MAP =
            new ConverterConfigurator().buildConverterMap();

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static Object convertToType(final Class<?> type,
                                       final String value) {
        if (value == null) {
            return null;
        } else if (type.isEnum()) {
            return Enum.valueOf((Class<? extends Enum>) type, value);
        } else {
            final Function<String, Object> converter = CONVERTERS_MAP.get(type);
            if (converter != null) {
                return converter.apply(value);
            } else {
                return TO_CUSTOM_TYPE_CONVERTER.convertToCustomType(type, value).orElseThrow(() -> {
                    throw new ConfigException(
                            "Can't convert '?' to '?'", String.class.getName(), type.getName()
                    );
                });
            }
        }
    }

    public static Object convertWithoutTypeDefinition(final String value,
                                                      final boolean supportsMap,
                                                      final boolean supportsList) {
        if ("true".equals(value) || "false".equals(value)) {
            return Boolean.parseBoolean(value);
        } else {
            boolean foundComma = false;
            boolean foundDot = false;
            boolean foundAssignment = false;
            boolean notANumber = false;
            int eIndex = -1;
            for (int i = 0; i < value.length(); i++) {
                final char ch = value.charAt(i);
                if (ch == '.') {
                    if (foundDot) {
                        notANumber = true;
                    }
                    foundDot = true;
                } else if (ch == '=') {
                    foundAssignment = true;
                    notANumber = true;
                } else if (ch == ',') {
                    foundComma = true;
                    notANumber = true;
                } else if ((ch == '+' || ch == '-') && i > 0 && eIndex != i - 1) {
                    notANumber = true;
                } else if (ch == 'e' || ch == 'E') {
                    if (eIndex != -1) {
                        notANumber = true;
                    }
                    eIndex = i;
                }
            }
            final boolean isList = supportsList && foundComma;
            final boolean isDecimalNumberCandidate = foundDot || eIndex != -1;
            final boolean isMap = supportsMap && foundAssignment;
            return convert(value, isList, isDecimalNumberCandidate, isMap, notANumber);
        }
    }

    private static Object convert(final String value,
                                  final boolean isList,
                                  final boolean isDecimalNumberCandidate,
                                  final boolean isMap,
                                  final boolean notANumber) {
        if (isMap) {
            return convertToType(Map.class, value);
        } else if (isList) {
            return convertToType(List.class, value);
        } else if (!notANumber) {
            try {
                if (isDecimalNumberCandidate) {
                    return new BigDecimal(value);
                } else {
                    return Long.parseLong(value);
                }
            } catch (final NumberFormatException ignored) {
                // do nothing
            }
        }
        return TO_CUSTOM_TYPE_CONVERTER.convertToCustomType("undefined", value).orElse(value);
    }

    private Converters() {
    }
}
