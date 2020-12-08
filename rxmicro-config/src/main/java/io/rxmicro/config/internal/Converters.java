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

import io.rxmicro.common.util.Reflections;
import io.rxmicro.config.ConfigException;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
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
import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Function;

import static io.rxmicro.common.util.ExCollectors.toUnmodifiableOrderedMap;
import static io.rxmicro.common.util.Strings.startsWith;
import static io.rxmicro.config.Config.KEY_VALUE_ENTRY_DELIMITER;
import static io.rxmicro.config.Config.VALUES_DELIMITER;
import static java.util.Collections.unmodifiableSortedSet;
import static java.util.Map.entry;

/**
 * @author nedis
 * @since 0.1
 */
public final class Converters {

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
        map.put(List.class, s -> List.of(s.split(String.valueOf(VALUES_DELIMITER))));
        map.put(Set.class, s -> Set.of(s.split(String.valueOf(VALUES_DELIMITER))));
        map.put(SortedSet.class, s -> unmodifiableSortedSet(new TreeSet<>(Arrays.asList(s.split(String.valueOf(VALUES_DELIMITER))))));
        map.put(Map.class, s -> Arrays.stream(s.split(String.valueOf(VALUES_DELIMITER)))
                .map(pair -> pair.split(String.valueOf(KEY_VALUE_ENTRY_DELIMITER)))
                .map(d -> entry(d[0], d[1]))
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
                return convertToCustomType(type, value).orElseThrow(() -> {
                    throw new ConfigException(
                            "Can't convert '?' to '?'", String.class.getName(), type.getName()
                    );
                });
            }
        }
    }

    private static Optional<Object> convertToCustomType(final Class<?> type,
                                                        final String value) {
        if (startsWith(value, '@')) {
            final int delimiter = value.indexOf(':');
            if (delimiter != -1) {
                final String fullClassName = value.substring(1, delimiter);
                try {
                    final Class<?> fullClass = Class.forName(value.substring(1, delimiter));
                    final String constName = value.substring(delimiter + 1);
                    if (fullClass.isEnum()) {
                        return useEnumConstant(type, fullClass, constName);
                    } else {
                        return usePublicStaticFinalConstant(type, fullClass, constName);
                    }
                } catch (final ClassNotFoundException ignore) {
                    throw new ConfigException(
                            "Can't convert '?' to '?', because '?' class not defined!",
                            String.class.getName(), type.getName(), fullClassName
                    );
                }
            }
        }
        return Optional.empty();
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static Optional<Object> useEnumConstant(final Class<?> type,
                                                    final Class<?> fullClass,
                                                    final String constName) {
        try {
            return Optional.of(Enum.valueOf((Class<? extends Enum>) fullClass, constName));
        } catch (final IllegalArgumentException ignore) {
            throw new ConfigException(
                    "Can't convert '?' to '?', because '?' ? does not contain '?' enum constant!",
                    String.class.getName(), type.getName(), fullClass.getName(), getKind(fullClass), constName
            );
        }
    }

    private static Optional<Object> usePublicStaticFinalConstant(final Class<?> type,
                                                                 final Class<?> fullClass,
                                                                 final String constName) {
        try {
            final Field declaredField = fullClass.getDeclaredField(constName);
            if (!Modifier.isStatic(declaredField.getModifiers())) {
                throw new ConfigException(
                        "Can't convert '?' to '?', because '?' field declared at '?' ? not static!",
                        String.class.getName(), type.getName(), constName, fullClass.getName(), getKind(fullClass)
                );
            }
            if (!Modifier.isPublic(declaredField.getModifiers())) {
                throw new ConfigException(
                        "Can't convert '?' to '?', because '?' field declared at '?' ? not public!",
                        String.class.getName(), type.getName(), constName, fullClass.getName(), getKind(fullClass)
                );
            }
            if (!Modifier.isFinal(declaredField.getModifiers())) {
                throw new ConfigException(
                        "Can't convert '?' to '?', because '?' field declared at '?' ? not final!",
                        String.class.getName(), type.getName(), constName, fullClass.getName(), getKind(fullClass)
                );
            }
            return Optional.of(Reflections.getFieldValue((Object) null, declaredField));
        } catch (final NoSuchFieldException ignore) {
            throw new ConfigException(
                    "Can't convert '?' to '?', because '?' ? does not contain '?' public static final field!",
                    String.class.getName(), type.getName(), fullClass.getName(), getKind(fullClass), constName
            );
        }
    }

    private static String getKind(final Class<?> fullClass) {
        if (fullClass.isEnum()) {
            return "enum";
        } else if (fullClass.isInterface()) {
            return "interface";
        } else if (fullClass.isAnnotation()) {
            return "annotation";
        } else {
            return "class";
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
        return value;
    }

    private Converters() {
    }
}
