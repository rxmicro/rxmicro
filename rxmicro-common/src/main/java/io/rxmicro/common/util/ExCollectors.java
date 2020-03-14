/*
 * Copyright (c) 2020 http://rxmicro.io
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

package io.rxmicro.common.util;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collector;

import static io.rxmicro.common.util.ExCollections.unmodifiableOrderedMap;
import static io.rxmicro.common.util.Formats.format;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toMap;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public final class ExCollectors {

    public static <T, K, U> Collector<T, ?, Map<K, U>> toOrderedMap(final Function<? super T, ? extends K> keyMapper,
                                                                    final Function<? super T, ? extends U> valueMapper) {
        return toMap(keyMapper, valueMapper, throwingMerger(), LinkedHashMap::new);
    }

    @SuppressWarnings("unchecked")
    public static <T, K, U> Collector<T, ?, Map<K, U>> toUnmodifiableOrderedMap(final Function<? super T, ? extends K> keyMapper,
                                                                                final Function<? super T, ? extends U> valueMapper) {
        return collectingAndThen(
                toOrderedMap(keyMapper, valueMapper),
                map -> (Map<K, U>) unmodifiableOrderedMap(map)
        );
    }

    public static <T, K, U> Collector<T, ?, Map<K, U>> toTreeMap(final Function<? super T, ? extends K> keyMapper,
                                                                 final Function<? super T, ? extends U> valueMapper) {
        return toMap(keyMapper, valueMapper, throwingMerger(), TreeMap::new);
    }

    public static <T> Collector<T, ?, Set<T>> toOrderedSet() {
        return toCollection(LinkedHashSet::new);
    }

    public static <T> Collector<T, ?, Set<T>> toTreeSet() {
        return toCollection(TreeSet::new);
    }

    public static <T> Collector<T, ?, Set<T>> toTreeSet(final Comparator<T> comparator) {
        return toCollection(() -> new TreeSet<>(comparator));
    }

    public static <T> Collector<T, ?, Set<T>> toUnmodifiableTreeSet() {
        return collectingAndThen(
                toCollection(TreeSet::new),
                ExCollections::unmodifiableOrderedSet
        );
    }

    public static <T> Collector<T, ?, Set<T>> toUnmodifiableOrderedSet() {
        return collectingAndThen(
                toCollection(LinkedHashSet::new),
                ExCollections::unmodifiableOrderedSet
        );
    }

    private static <T> BinaryOperator<T> throwingMerger() {
        return (u, v) -> {
            throw new IllegalArgumentException(format("Duplicate key ?", u));
        };
    }

    private ExCollectors() {
    }
}
