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
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @author nedis
 * @since 0.1
 */
public final class Converters {

    private static final Map<Class<?>, Function<String, Object>> CONVERTERS;

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
        CONVERTERS = Map.copyOf(map);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static Object convert(final Class<?> type,
                                 final String value) {
        if (value == null) {
            return null;
        } else if (type.isEnum()) {
            return Enum.valueOf((Class<? extends Enum>) type, value);
        } else {
            final Function<String, Object> converter = CONVERTERS.get(type);
            if (converter != null) {
                return converter.apply(value);
            } else {
                throw new ConfigException(
                        "Can't convert '?' to '?'", String.class.getName(), type.getName()
                );
            }
        }
    }

    private Converters() {
    }
}
