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

package io.rxmicro.config.internal.component;

import io.rxmicro.resource.Paths;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.file.Path;
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
import static io.rxmicro.config.internal.Converters.convertWithoutTypeDefinition;
import static java.util.Collections.unmodifiableSortedSet;
import static java.util.Map.entry;

/**
 * @author nedis
 * @since 0.10
 */
public final class ConverterConfigurator {

    public Map<Class<?>, Function<String, Object>> buildConverterMap() {
        final Map<Class<?>, Function<String, Object>> map = new HashMap<>();
        putStringsMapping(map);
        putBooleansMapping(map);
        putNumbersMapping(map);
        putDateAndTimeClassesMapping(map);
        putFileSystemClassesMapping(map);
        putCollectionsMapping(map);
        return Map.copyOf(map);
    }

    private void putStringsMapping(final Map<Class<?>, Function<String, Object>> map) {
        map.put(String.class, s -> s);
        map.put(CharSequence.class, s -> s);
        map.put(Character.class, s -> s.charAt(0));
        map.put(Character.TYPE, s -> s.charAt(0));
    }

    private void putBooleansMapping(final Map<Class<?>, Function<String, Object>> map) {
        map.put(Boolean.class, Boolean::parseBoolean);
        map.put(Boolean.TYPE, Boolean::parseBoolean);
    }

    private void putNumbersMapping(final Map<Class<?>, Function<String, Object>> map) {
        map.put(Byte.class, Byte::parseByte);
        map.put(Byte.TYPE, Byte::parseByte);
        map.put(Short.class, Short::parseShort);
        map.put(Short.TYPE, Short::parseShort);
        map.put(Integer.class, Integer::parseInt);
        map.put(Integer.TYPE, Integer::parseInt);
        map.put(Long.class, Long::parseLong);
        map.put(Long.TYPE, Long::parseLong);
        map.put(BigInteger.class, BigInteger::new);
        map.put(Float.class, Float::parseFloat);
        map.put(Float.TYPE, Float::parseFloat);
        map.put(Double.class, Double::parseDouble);
        map.put(Double.TYPE, Double::parseDouble);
        map.put(BigDecimal.class, BigDecimal::new);
    }

    private void putDateAndTimeClassesMapping(final Map<Class<?>, Function<String, Object>> map) {
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
    }

    private void putFileSystemClassesMapping(final Map<Class<?>, Function<String, Object>> map) {
        map.put(Path.class, Paths::createPath);
    }

    private void putCollectionsMapping(final Map<Class<?>, Function<String, Object>> map) {
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
    }

    private Object[] getConvertedWithoutTypeDefinitionValues(final String source) {
        final String[] sourceArray = source.split(String.valueOf(VALUES_DELIMITER));
        final Object[] destinationArray = new Object[sourceArray.length];
        for (int i = 0; i < sourceArray.length; i++) {
            destinationArray[i] = convertWithoutTypeDefinition(sourceArray[i], false, false);
        }
        return destinationArray;
    }
}
