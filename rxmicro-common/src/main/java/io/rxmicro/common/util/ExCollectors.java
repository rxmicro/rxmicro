/*
 * Copyright (c) 2020 https://rxmicro.io
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
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static io.rxmicro.common.util.ExCollections.unmodifiableOrderedMap;
import static io.rxmicro.common.util.Formats.format;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toMap;

/**
 * Extended {@link java.util.stream.Collectors} utils
 *
 * @author nedis
 * @since 0.1
 * @see java.util.stream.Collectors
 */
public final class ExCollectors {

    /**
     * Returns the {@link Collector} which collects elements into a modifiable {@link LinkedHashMap}
     *
     * @param <T> the type of the input elements
     * @param <K> the output type of the key mapping function
     * @param <U> the output type of the value mapping function
     * @param <M> the type of the resulting {@code Map}
     * @param keyMapper a mapping function to produce keys
     * @param valueMapper a mapping function to produce values
     * @return the {@link Collector} which collects elements into a modifiable {@link LinkedHashMap}
     * @see LinkedHashMap
     * @see java.util.stream.Collectors#toMap(Function, Function)
     */
    public static <T, K, U> Collector<T, ?, Map<K, U>> toOrderedMap(final Function<? super T, ? extends K> keyMapper,
                                                                    final Function<? super T, ? extends U> valueMapper) {
        return toMap(keyMapper, valueMapper, throwingMerger(), LinkedHashMap::new);
    }

    /**
     * Returns the {@link Collector} which collects elements into a unmodifiable ordered {@link Map}
     *
     * @param <T> the type of the input elements
     * @param <K> the output type of the key mapping function
     * @param <U> the output type of the value mapping function
     * @param <M> the type of the resulting {@code Map}
     * @param keyMapper a mapping function to produce keys
     * @param valueMapper a mapping function to produce values
     * @return the {@link Collector} which collects elements into a unmodifiable ordered {@link Map}
     * @see java.util.stream.Collectors#toUnmodifiableMap(Function, Function)
     */
    @SuppressWarnings("unchecked")
    public static <T, K, U> Collector<T, ?, Map<K, U>> toUnmodifiableOrderedMap(final Function<? super T, ? extends K> keyMapper,
                                                                                final Function<? super T, ? extends U> valueMapper) {
        return collectingAndThen(
                toOrderedMap(keyMapper, valueMapper),
                map -> (Map<K, U>) unmodifiableOrderedMap(map)
        );
    }

    /**
     * Returns the {@link Collector} which collects elements into a modifiable {@link TreeMap}
     *
     * @param <T> the type of the input elements
     * @param <K> the output type of the key mapping function
     * @param <U> the output type of the value mapping function
     * @param <M> the type of the resulting {@code Map}
     * @param keyMapper a mapping function to produce keys
     * @param valueMapper a mapping function to produce values
     * @return the {@link Collector} which collects elements into a modifiable {@link TreeMap}
     * @see TreeMap
     * @see java.util.stream.Collectors#toMap(Function, Function)
     */
    public static <T, K, U> Collector<T, ?, Map<K, U>> toTreeMap(final Function<? super T, ? extends K> keyMapper,
                                                                 final Function<? super T, ? extends U> valueMapper) {
        return toMap(keyMapper, valueMapper, throwingMerger(), TreeMap::new);
    }

    /**
     * Returns the {@link Collector} that accumulates the input elements into {@link LinkedHashSet}
     *
     * @param <T> the type of the input elements
     * @return the {@link Collector} that accumulates the input elements into {@link LinkedHashSet}
     * @see LinkedHashSet
     * @see java.util.stream.Collectors#toCollection(Supplier)
     * @see Collectors#toSet()
     */
    public static <T> Collector<T, ?, Set<T>> toOrderedSet() {
        return toCollection(LinkedHashSet::new);
    }

    /**
     * Returns the {@link Collector} that accumulates the input elements into {@link TreeSet}
     *
     * @param <T> the type of the input elements
     * @return the {@link Collector} that accumulates the input elements into {@link TreeSet}
     * @see TreeSet
     * @see java.util.stream.Collectors#toCollection(Supplier)
     * @see Collectors#toSet()
     */
    public static <T> Collector<T, ?, Set<T>> toTreeSet() {
        return toCollection(TreeSet::new);
    }

    /**
     * Returns the {@link Collector} that accumulates the input elements into {@link TreeSet} with the specified comparator
     *
     * @param comparator the specified comparator
     * @param <T> the type of the input elements
     * @return the {@link Collector} that accumulates the input elements into {@link TreeSet}
     * @see TreeSet
     * @see java.util.stream.Collectors#toCollection(Supplier)
     * @see Collectors#toSet()
     */
    public static <T> Collector<T, ?, Set<T>> toTreeSet(final Comparator<T> comparator) {
        return toCollection(() -> new TreeSet<>(comparator));
    }

    /**
     * Returns the {@link Collector} that accumulates the input elements into the unmodifiable {@link TreeSet}
     *
     * @param <T> the type of the input elements
     * @return the {@link Collector} that accumulates the input elements into the unmodifiable {@link TreeSet}
     * @see TreeSet
     * @see java.util.stream.Collectors#toCollection(Supplier)
     * @see Collectors#toUnmodifiableSet()
     */
    public static <T> Collector<T, ?, Set<T>> toUnmodifiableTreeSet() {
        return collectingAndThen(
                toCollection(TreeSet::new),
                ExCollections::unmodifiableOrderedSet
        );
    }

    /**
     * Returns the {@link Collector} that accumulates the input elements into the unmodifiable {@link LinkedHashSet}
     *
     * @param <T> the type of the input elements
     * @return the {@link Collector} that accumulates the input elements into the unmodifiable {@link LinkedHashSet}
     * @see LinkedHashSet
     * @see java.util.stream.Collectors#toCollection(Supplier)
     * @see Collectors#toUnmodifiableSet()
     */
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
