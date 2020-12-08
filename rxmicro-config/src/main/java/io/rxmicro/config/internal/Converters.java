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
import io.rxmicro.config.internal.component.ToCustomTypeConverter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.MonthDay;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.Period;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Function;

import static io.rxmicro.common.util.ExCollectors.toUnmodifiableOrderedMap;
import static io.rxmicro.config.Config.KEY_VALUE_ENTRY_DELIMITER;
import static io.rxmicro.config.Config.VALUES_DELIMITER;
import static java.util.Collections.unmodifiableSortedSet;
import static java.util.Map.entry;

/**
 * @author nedis
 * @since 0.1
 */
public final class Converters {

    private static final ToCustomTypeConverter TO_CUSTOM_TYPE_CONVERTER = new ToCustomTypeConverter();

    private static final Map<Class<?>, Function<String, Object>> CONVERTERS_MAP;

    static {
        final Map<Class<?>, Function<String, Object>> map = new HashMap<>();
        // String
        map.put(String.class, s -> s);
        map.put(CharSequence.class, s -> s);
        map.put(Character.class, s -> s.charAt(0));
        map.put(Character.TYPE, s -> s.charAt(0));
        // Boolean
        map.put(Boolean.class, Boolean::parseBoolean);
        map.put(Boolean.TYPE, Boolean::parseBoolean);
        // Int Numbers
        map.put(Byte.class, Byte::parseByte);
        map.put(Byte.TYPE, Byte::parseByte);
        map.put(Short.class, Short::parseShort);
        map.put(Short.TYPE, Short::parseShort);
        map.put(Integer.class, Integer::parseInt);
        map.put(Integer.TYPE, Integer::parseInt);
        map.put(Long.class, Long::parseLong);
        map.put(Long.TYPE, Long::parseLong);
        map.put(BigInteger.class, BigInteger::new);
        // Float Numbers
        map.put(Float.class, Float::parseFloat);
        map.put(Float.TYPE, Float::parseFloat);
        map.put(Double.class, Double::parseDouble);
        map.put(Double.TYPE, Double::parseDouble);
        map.put(BigDecimal.class, BigDecimal::new);
        // Date and Times
        map.put(Instant.class, Instant::parse);
        map.put(LocalDate.class, LocalDate::parse);
        map.put(LocalDateTime.class, LocalDateTime::parse);
        map.put(LocalTime.class, LocalTime::parse);
        map.put(MonthDay.class, MonthDay::parse);
        map.put(OffsetDateTime.class, OffsetDateTime::parse);
        map.put(OffsetTime.class, OffsetTime::parse);
        map.put(Year.class, Year::parse);
        map.put(YearMonth.class, YearMonth::parse);
        map.put(ZonedDateTime.class, ZonedDateTime::parse);
        map.put(Duration.class, Duration::parse);
        map.put(ZoneOffset.class, ZoneOffset::of);
        map.put(ZoneId.class, ZoneId::of);
        map.put(Period.class, Period::parse);
        // Collections
        map.put(List.class, s -> List.of(getConvertedWithoutTypeDefinitionValues(s)));
        map.put(Set.class, s -> Set.of(getConvertedWithoutTypeDefinitionValues(s)));
        map.put(SortedSet.class, s -> unmodifiableSortedSet(new TreeSet<>(Arrays.asList(getConvertedWithoutTypeDefinitionValues(s)))));
        map.put(Map.class, s -> Arrays.stream(s.split(String.valueOf(VALUES_DELIMITER)))
                .map(pair -> pair.split(String.valueOf(KEY_VALUE_ENTRY_DELIMITER)))
                .map(d -> entry(
                        convertWithoutTypeDefinition(d[0], false, false),
                        convertWithoutTypeDefinition(d[1], false, false)
                ))
                .collect(toUnmodifiableOrderedMap(Map.Entry::getKey, Map.Entry::getValue)));
        // Init CONVERTERS_MAP
        CONVERTERS_MAP = Map.copyOf(map);
    }

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
                    try {
                        return Long.parseLong(value);
                    } catch (final NumberFormatException ignore) {
                        return new BigInteger(value);
                    }
                }
            } catch (final NumberFormatException ignore) {
                // return string value
            }
        }
        return TO_CUSTOM_TYPE_CONVERTER.convertToCustomType("undefined", value).orElse(value);
    }

    private static Object[] getConvertedWithoutTypeDefinitionValues(final String source) {
        final String[] sourceArray = source.split(String.valueOf(VALUES_DELIMITER));
        final Object[] destinationArray = new Object[sourceArray.length];
        for (int i = 0; i < sourceArray.length; i++) {
            destinationArray[i] = convertWithoutTypeDefinition(sourceArray[i], false, false);
        }
        return destinationArray;
    }

    private Converters() {
    }
}
