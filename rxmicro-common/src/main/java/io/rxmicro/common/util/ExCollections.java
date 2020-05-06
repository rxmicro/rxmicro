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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.Map.entry;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

/**
 * TODO
 *
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class ExCollections {

    /**
     * TODO
     */
    public static final String[] EMPTY_STRING_ARRAY = new String[0];

    @SuppressWarnings("rawtypes")
    private static final Set<Class<? extends Map>> UNMODIFIABLE_MAP_CLASSES = Set.of(
            Collections.unmodifiableMap(Map.of()).getClass(),   // java.util.Collections.UnmodifiableMap
            Map.of().getClass(),                    // java.util.ImmutableCollections.MapN
            Map.of(1, 1).getClass()        // java.util.ImmutableCollections.Map1
    );

    @SuppressWarnings("rawtypes")
    private static final Set<Class<? extends Set>> UNMODIFIABLE_SET_CLASSES = Set.of(
            Collections.unmodifiableSet(Set.of()).getClass(),   // java.util.Collections.UnmodifiableSet
            Set.of().getClass(),                    // java.util.ImmutableCollections.SetN
            Set.of(1).getClass()                    // java.util.ImmutableCollections.Set12
    );

    @SuppressWarnings("rawtypes")
    private static final Set<Class<? extends List>> UNMODIFIABLE_LIST_CLASSES = Set.of(
            Collections.unmodifiableList(List.of()).getClass(),   // java.util.Collections.UnmodifiableList
            List.of().getClass(),                    // java.util.ImmutableCollections.ListN
            List.of(1).getClass()                    // java.util.ImmutableCollections.List12
    );

    /**
     * TODO
     *
     * @param object
     * @return
     */
    public static boolean isUnmodifiableList(final Object object) {
        return object != null &&
                UNMODIFIABLE_LIST_CLASSES.contains(object.getClass());
    }

    /**
     * TODO
     *
     * @param collection
     * @param <E>
     * @return
     */
    @SuppressWarnings("Java9CollectionFactory")
    public static <E> List<E> unmodifiableList(final Collection<E> collection) {
        if (collection.isEmpty()) {
            return List.of();
        } else if (UNMODIFIABLE_LIST_CLASSES.contains(collection.getClass())) {
            return (List<E>) collection;
        } else if (collection.size() == 1) {
            return List.of(collection.iterator().next());
        } else if (collection instanceof List) {
            return Collections.unmodifiableList((List<E>) collection);
        } else {
            return Collections.unmodifiableList(new ArrayList<>(collection));
        }
    }

    /**
     * TODO
     *
     * @param object
     * @return
     */
    public static boolean isUnmodifiableMap(final Object object) {
        return object != null &&
                UNMODIFIABLE_MAP_CLASSES.contains(object.getClass());
    }

    /**
     * TODO
     *
     * @param map
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> Map<K, V> unmodifiableMap(final Map<K, V> map) {
        return unmodifiableOrderedMap(map);
    }

    /**
     * TODO
     *
     * @param map
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> Map<K, V> unmodifiableOrderedMap(final Map<K, V> map) {
        if (map.isEmpty()) {
            return Map.of();
        } else if (UNMODIFIABLE_MAP_CLASSES.contains(map.getClass())) {
            return map;
        } else if (map.size() == 1) {
            final Map.Entry<K, V> entry = map.entrySet().iterator().next();
            return Map.of(entry.getKey(), entry.getValue());
        } else {
            return Collections.unmodifiableMap(map);
        }
    }

    /**
     * TODO
     *
     * @param collection
     * @param <E>
     * @return
     */
    public static <E> Set<E> unmodifiableOrderedSet(final Collection<E> collection) {
        if (collection.isEmpty()) {
            return Set.of();
        } else if (UNMODIFIABLE_SET_CLASSES.contains(collection.getClass())) {
            return (Set<E>) collection;
        } else if (collection.size() == 1) {
            return Set.of(collection.iterator().next());
        } else if (collection instanceof Set) {
            return Collections.unmodifiableSet((Set<E>) collection);
        } else {
            return Collections.unmodifiableSet(new LinkedHashSet<>(collection));
        }
    }

    /**
     * TODO
     *
     * @param elements
     * @param <E>
     * @return
     */
    @SafeVarargs
    public static <E> Set<E> unmodifiableOrderedSet(final E... elements) {
        return unmodifiableOrderedSet(Arrays.asList(elements));
    }

    /**
     * TODO
     *
     * @param items
     * @param <E>
     * @return
     */
    @SafeVarargs
    public static <E> Set<E> join(final Set<E>... items) {
        return Arrays.stream(items).flatMap(Collection::stream).collect(toSet());
    }

    /**
     * TODO
     *
     * @param items
     * @param <E>
     * @return
     */
    @SafeVarargs
    public static <E> List<E> join(final List<E>... items) {
        return Arrays.stream(items).flatMap(Collection::stream).collect(toList());
    }

    /**
     * TODO
     *
     * @param k1
     * @param v1
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> Map<K, V> orderedMap(final K k1, final V v1) {
        return orderedMapFromEntries(
                entry(k1, v1)
        );
    }

    /**
     * TODO
     *
     * @param k1
     * @param v1
     * @param k2
     * @param v2
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> Map<K, V> orderedMap(final K k1, final V v1,
                                              final K k2, final V v2) {
        return orderedMapFromEntries(
                entry(k1, v1),
                entry(k2, v2)
        );
    }

    /**
     * TODO
     *
     * @param k1
     * @param v1
     * @param k2
     * @param v2
     * @param k3
     * @param v3
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> Map<K, V> orderedMap(final K k1, final V v1,
                                              final K k2, final V v2,
                                              final K k3, final V v3) {
        return orderedMapFromEntries(
                entry(k1, v1),
                entry(k2, v2),
                entry(k3, v3)
        );
    }

    /**
     * TODO
     *
     * @param k1
     * @param v1
     * @param k2
     * @param v2
     * @param k3
     * @param v3
     * @param k4
     * @param v4
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> Map<K, V> orderedMap(final K k1, final V v1,
                                              final K k2, final V v2,
                                              final K k3, final V v3,
                                              final K k4, final V v4) {
        return orderedMapFromEntries(
                entry(k1, v1),
                entry(k2, v2),
                entry(k3, v3),
                entry(k4, v4)
        );
    }

    /**
     * TODO
     *
     * @param k1
     * @param v1
     * @param k2
     * @param v2
     * @param k3
     * @param v3
     * @param k4
     * @param v4
     * @param k5
     * @param v5
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> Map<K, V> orderedMap(final K k1, final V v1,
                                              final K k2, final V v2,
                                              final K k3, final V v3,
                                              final K k4, final V v4,
                                              final K k5, final V v5) {
        return orderedMapFromEntries(
                entry(k1, v1),
                entry(k2, v2),
                entry(k3, v3),
                entry(k4, v4),
                entry(k5, v5)
        );
    }

    /**
     * TODO
     *
     * @param k1
     * @param v1
     * @param k2
     * @param v2
     * @param k3
     * @param v3
     * @param k4
     * @param v4
     * @param k5
     * @param v5
     * @param k6
     * @param v6
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> Map<K, V> orderedMap(final K k1, final V v1,
                                              final K k2, final V v2,
                                              final K k3, final V v3,
                                              final K k4, final V v4,
                                              final K k5, final V v5,
                                              final K k6, final V v6) {
        return orderedMapFromEntries(
                entry(k1, v1),
                entry(k2, v2),
                entry(k3, v3),
                entry(k4, v4),
                entry(k5, v5),
                entry(k6, v6)
        );
    }

    /**
     * TODO
     *
     * @param k1
     * @param v1
     * @param k2
     * @param v2
     * @param k3
     * @param v3
     * @param k4
     * @param v4
     * @param k5
     * @param v5
     * @param k6
     * @param v6
     * @param k7
     * @param v7
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> Map<K, V> orderedMap(final K k1, final V v1,
                                              final K k2, final V v2,
                                              final K k3, final V v3,
                                              final K k4, final V v4,
                                              final K k5, final V v5,
                                              final K k6, final V v6,
                                              final K k7, final V v7) {
        return orderedMapFromEntries(
                entry(k1, v1),
                entry(k2, v2),
                entry(k3, v3),
                entry(k4, v4),
                entry(k5, v5),
                entry(k6, v6),
                entry(k7, v7)
        );
    }

    /**
     * TODO
     *
     * @param k1
     * @param v1
     * @param k2
     * @param v2
     * @param k3
     * @param v3
     * @param k4
     * @param v4
     * @param k5
     * @param v5
     * @param k6
     * @param v6
     * @param k7
     * @param v7
     * @param k8
     * @param v8
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> Map<K, V> orderedMap(final K k1, final V v1,
                                              final K k2, final V v2,
                                              final K k3, final V v3,
                                              final K k4, final V v4,
                                              final K k5, final V v5,
                                              final K k6, final V v6,
                                              final K k7, final V v7,
                                              final K k8, final V v8) {
        return orderedMapFromEntries(
                entry(k1, v1),
                entry(k2, v2),
                entry(k3, v3),
                entry(k4, v4),
                entry(k5, v5),
                entry(k6, v6),
                entry(k7, v7),
                entry(k8, v8)
        );
    }

    /**
     * TODO
     *
     * @param k1
     * @param v1
     * @param k2
     * @param v2
     * @param k3
     * @param v3
     * @param k4
     * @param v4
     * @param k5
     * @param v5
     * @param k6
     * @param v6
     * @param k7
     * @param v7
     * @param k8
     * @param v8
     * @param k9
     * @param v9
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> Map<K, V> orderedMap(final K k1, final V v1,
                                              final K k2, final V v2,
                                              final K k3, final V v3,
                                              final K k4, final V v4,
                                              final K k5, final V v5,
                                              final K k6, final V v6,
                                              final K k7, final V v7,
                                              final K k8, final V v8,
                                              final K k9, final V v9) {
        return orderedMapFromEntries(
                entry(k1, v1),
                entry(k2, v2),
                entry(k3, v3),
                entry(k4, v4),
                entry(k5, v5),
                entry(k6, v6),
                entry(k7, v7),
                entry(k8, v8),
                entry(k9, v9)
        );
    }

    /**
     * TODO
     *
     * @param k1
     * @param v1
     * @param k2
     * @param v2
     * @param k3
     * @param v3
     * @param k4
     * @param v4
     * @param k5
     * @param v5
     * @param k6
     * @param v6
     * @param k7
     * @param v7
     * @param k8
     * @param v8
     * @param k9
     * @param v9
     * @param k10
     * @param v10
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> Map<K, V> orderedMap(final K k1, final V v1,
                                              final K k2, final V v2,
                                              final K k3, final V v3,
                                              final K k4, final V v4,
                                              final K k5, final V v5,
                                              final K k6, final V v6,
                                              final K k7, final V v7,
                                              final K k8, final V v8,
                                              final K k9, final V v9,
                                              final K k10, final V v10) {
        return orderedMapFromEntries(
                entry(k1, v1),
                entry(k2, v2),
                entry(k3, v3),
                entry(k4, v4),
                entry(k5, v5),
                entry(k6, v6),
                entry(k7, v7),
                entry(k8, v8),
                entry(k9, v9),
                entry(k10, v10)
        );
    }

    /**
     * TODO
     *
     * @param entries
     * @param <K>
     * @param <V>
     * @return
     */
    @SafeVarargs
    @SuppressWarnings("varargs")
    public static <K, V> Map<K, V> orderedMapFromEntries(final Map.Entry<? extends K, ? extends V>... entries) {
        final Map<K, V> map = new LinkedHashMap<>();
        for (final Map.Entry<? extends K, ? extends V> entry : entries) {
            map.put(entry.getKey(), entry.getValue());
        }
        return unmodifiableMap(map);
    }

    private ExCollections() {
    }
}
