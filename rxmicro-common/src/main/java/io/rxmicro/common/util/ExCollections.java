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

import static io.rxmicro.common.util.Formats.format;
import static java.util.Map.entry;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

/**
 * Extended {@link Collections} utils
 *
 * @author nedis
 * @since 0.1
 * @see Collections
 * @see Map
 * @see Set
 * @see List
 */
public final class ExCollections {

    /**
     * Empty string array
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
     * Returns {@code true} if the tested object is the unmodifiable {@link List}
     *
     * @param object the tested object
     * @return {@code true} if the tested object is the unmodifiable {@link List}
     */
    public static boolean isUnmodifiableList(final Object object) {
        return object != null &&
                UNMODIFIABLE_LIST_CLASSES.contains(object.getClass());
    }

    /**
     * Returns the short-lived unmodifiable {@link List} of the specified collection.
     *
     * @param collection the specified collection
     * @param <E> the class of the objects in the collection
     * @return the short-lived unmodifiable {@link List} of the specified collection
     * @see List#of()
     * @see Collections#unmodifiableList(List)
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
     * Returns {@code true} if the tested object is the unmodifiable {@link Map}
     *
     * @param object the tested object
     * @return {@code true} if the tested object is the unmodifiable {@link Map}
     */
    public static boolean isUnmodifiableMap(final Object object) {
        return object != null &&
                UNMODIFIABLE_MAP_CLASSES.contains(object.getClass());
    }

    /**
     * Returns the short-lived unmodifiable {@link Map} of the specified map.
     *
     * @param map the specified map
     * @param <K> the class of the map keys
     * @param <V> the class of the map values
     * @return the short-lived unmodifiable {@link Map} of the specified map.
     * @see Map#of()
     * @see Collections#unmodifiableMap(Map)
     */
    public static <K, V> Map<K, V> unmodifiableMap(final Map<K, V> map) {
        return unmodifiableOrderedMap(map);
    }

    /**
     * Returns the short-lived unmodifiable ordered {@link Map} of the specified map.
     *
     * @param map the specified map
     * @param <K> the class of the map keys
     * @param <V> the class of the map values
     * @return the short-lived unmodifiable ordered {@link Map} of the specified map.
     * @see Map#of()
     * @see Collections#unmodifiableMap(Map)
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
     * Returns the short-lived unmodifiable ordered {@link Set} of the specified collection.
     *
     * @param collection the specified collection.
     * @param <E> the class of the objects in the collection
     * @return the short-lived unmodifiable ordered {@link Set} of the specified collection.
     * @see Set#of()
     * @see Collections#unmodifiableSet(Set)
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
     * Returns the short-lived unmodifiable ordered {@link Set} of the specified elements.
     *
     * @param elements the specified elements
     * @param <E> the class of the objects in the collection
     * @return the short-lived unmodifiable ordered {@link Set} of the specified elements.
     * @see Set#of()
     * @see Collections#unmodifiableSet(Set)
     */
    @SafeVarargs
    public static <E> Set<E> unmodifiableOrderedSet(final E... elements) {
        return unmodifiableOrderedSet(Arrays.asList(elements));
    }

    /**
     * Joins the specified array of the {@link Set}s into one modifiable {@link Set}
     *
     * @param items the specified array of the {@link Set}s
     * @param <E> the class of the objects in the each {@link Set}
     * @return the joined modifiable {@link Set}
     */
    @SafeVarargs
    public static <E> Set<E> join(final Set<E>... items) {
        return Arrays.stream(items).flatMap(Collection::stream).collect(toSet());
    }

    /**
     * Joins the specified array of the {@link List}s into one modifiable {@link List}
     *
     * @param items the specified array of the {@link List}s
     * @param <E> the class of the objects in the each {@link List}
     * @return the joined modifiable {@link List}
     */
    @SafeVarargs
    public static <E> List<E> join(final List<E>... items) {
        return Arrays.stream(items).flatMap(Collection::stream).collect(toList());
    }

    /**
     * Returns the short-lived unmodifiable ordered {@link Map} containing a single mapping.
     *
     * @param <K> the {@code Map}'s key type
     * @param <V> the {@code Map}'s value type
     * @param k1 the mapping's key
     * @param v1 the mapping's value
     * @return the short-lived unmodifiable ordered {@link Map} containing the specified mapping
     * @throws NullPointerException if the key or the value is {@code null}
     * @see LinkedHashMap
     * @see Map#of(Object, Object)
     */
    public static <K, V> Map<K, V> orderedMap(final K k1, final V v1) {
        return orderedMapFromEntries(
                entry(k1, v1)
        );
    }

    /**
     * Returns the short-lived unmodifiable ordered {@link Map} containing two mappings.
     *
     * @param <K> the {@code Map}'s key type
     * @param <V> the {@code Map}'s value type
     * @param k1 the first mapping's key
     * @param v1 the first mapping's value
     * @param k2 the second mapping's key
     * @param v2 the second mapping's value
     * @return the short-lived unmodifiable ordered {@link Map} containing the specified mappings
     * @throws IllegalArgumentException if the keys are duplicates
     * @throws NullPointerException if any key or value is {@code null}
     * @see LinkedHashMap
     * @see Map#of(Object, Object, Object, Object)
     */
    public static <K, V> Map<K, V> orderedMap(final K k1, final V v1,
                                              final K k2, final V v2) {
        return orderedMapFromEntries(
                entry(k1, v1),
                entry(k2, v2)
        );
    }

    /**
     * Returns the short-lived unmodifiable ordered {@link Map} containing three mappings.
     *
     * @param <K> the {@code Map}'s key type
     * @param <V> the {@code Map}'s value type
     * @param k1 the first mapping's key
     * @param v1 the first mapping's value
     * @param k2 the second mapping's key
     * @param v2 the second mapping's value
     * @param k3 the third mapping's key
     * @param v3 the third mapping's value
     * @return the short-lived unmodifiable ordered {@link Map} containing the specified mappings
     * @throws IllegalArgumentException if there are any duplicate keys
     * @throws NullPointerException if any key or value is {@code null}
     * @see LinkedHashMap
     * @see Map#of(Object, Object, Object, Object, Object, Object)
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
     * Returns the short-lived unmodifiable ordered {@link Map} containing four mappings.
     *
     * @param <K> the {@code Map}'s key type
     * @param <V> the {@code Map}'s value type
     * @param k1 the first mapping's key
     * @param v1 the first mapping's value
     * @param k2 the second mapping's key
     * @param v2 the second mapping's value
     * @param k3 the third mapping's key
     * @param v3 the third mapping's value
     * @param k4 the fourth mapping's key
     * @param v4 the fourth mapping's value
     * @return the short-lived unmodifiable ordered {@link Map} containing the specified mappings
     * @throws IllegalArgumentException if there are any duplicate keys
     * @throws NullPointerException if any key or value is {@code null}
     * @see LinkedHashMap
     * @see Map#of(Object, Object, Object, Object, Object, Object, Object, Object)
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
     * Returns the short-lived unmodifiable ordered {@link Map} containing five mappings.
     *
     * @param <K> the {@code Map}'s key type
     * @param <V> the {@code Map}'s value type
     * @param k1 the first mapping's key
     * @param v1 the first mapping's value
     * @param k2 the second mapping's key
     * @param v2 the second mapping's value
     * @param k3 the third mapping's key
     * @param v3 the third mapping's value
     * @param k4 the fourth mapping's key
     * @param v4 the fourth mapping's value
     * @param k5 the fifth mapping's key
     * @param v5 the fifth mapping's value
     * @return the short-lived unmodifiable ordered {@link Map} containing the specified mappings
     * @throws IllegalArgumentException if there are any duplicate keys
     * @throws NullPointerException if any key or value is {@code null}
     * @see LinkedHashMap
     * @see Map#of(Object, Object, Object, Object, Object, Object, Object, Object, Object, Object)
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
     * Returns the short-lived unmodifiable ordered {@link Map} containing six mappings.
     *
     * @param <K> the {@code Map}'s key type
     * @param <V> the {@code Map}'s value type
     * @param k1 the first mapping's key
     * @param v1 the first mapping's value
     * @param k2 the second mapping's key
     * @param v2 the second mapping's value
     * @param k3 the third mapping's key
     * @param v3 the third mapping's value
     * @param k4 the fourth mapping's key
     * @param v4 the fourth mapping's value
     * @param k5 the fifth mapping's key
     * @param v5 the fifth mapping's value
     * @param k6 the sixth mapping's key
     * @param v6 the sixth mapping's value
     * @return the short-lived unmodifiable ordered {@link Map} containing the specified mappings
     * @throws IllegalArgumentException if there are any duplicate keys
     * @throws NullPointerException if any key or value is {@code null}
     * @see LinkedHashMap
     * @see Map#of(Object, Object, Object, Object, Object, Object, Object, Object, Object, Object)
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
     * Returns the short-lived unmodifiable ordered {@link Map} containing seven mappings.
     *
     * @param <K> the {@code Map}'s key type
     * @param <V> the {@code Map}'s value type
     * @param k1 the first mapping's key
     * @param v1 the first mapping's value
     * @param k2 the second mapping's key
     * @param v2 the second mapping's value
     * @param k3 the third mapping's key
     * @param v3 the third mapping's value
     * @param k4 the fourth mapping's key
     * @param v4 the fourth mapping's value
     * @param k5 the fifth mapping's key
     * @param v5 the fifth mapping's value
     * @param k6 the sixth mapping's key
     * @param v6 the sixth mapping's value
     * @param k7 the seventh mapping's key
     * @param v7 the seventh mapping's value
     * @return the short-lived unmodifiable ordered {@link Map} containing the specified mappings
     * @throws IllegalArgumentException if there are any duplicate keys
     * @throws NullPointerException if any key or value is {@code null}
     * @see LinkedHashMap
     * @see Map#of(Object, Object, Object, Object, Object, Object, Object, Object, Object, Object)
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
     * Returns the short-lived unmodifiable ordered {@link Map} containing eight mappings.
     *
     * @param <K> the {@code Map}'s key type
     * @param <V> the {@code Map}'s value type
     * @param k1 the first mapping's key
     * @param v1 the first mapping's value
     * @param k2 the second mapping's key
     * @param v2 the second mapping's value
     * @param k3 the third mapping's key
     * @param v3 the third mapping's value
     * @param k4 the fourth mapping's key
     * @param v4 the fourth mapping's value
     * @param k5 the fifth mapping's key
     * @param v5 the fifth mapping's value
     * @param k6 the sixth mapping's key
     * @param v6 the sixth mapping's value
     * @param k7 the seventh mapping's key
     * @param v7 the seventh mapping's value
     * @param k8 the eighth mapping's key
     * @param v8 the eighth mapping's value
     * @return the short-lived unmodifiable ordered {@link Map} containing the specified mappings
     * @throws IllegalArgumentException if there are any duplicate keys
     * @throws NullPointerException if any key or value is {@code null}
     * @see LinkedHashMap
     * @see Map#of(Object, Object, Object, Object, Object, Object, Object, Object, Object, Object)
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
     * Returns the short-lived unmodifiable ordered {@link Map} containing nine mappings.
     *
     * @param <K> the {@code Map}'s key type
     * @param <V> the {@code Map}'s value type
     * @param k1 the first mapping's key
     * @param v1 the first mapping's value
     * @param k2 the second mapping's key
     * @param v2 the second mapping's value
     * @param k3 the third mapping's key
     * @param v3 the third mapping's value
     * @param k4 the fourth mapping's key
     * @param v4 the fourth mapping's value
     * @param k5 the fifth mapping's key
     * @param v5 the fifth mapping's value
     * @param k6 the sixth mapping's key
     * @param v6 the sixth mapping's value
     * @param k7 the seventh mapping's key
     * @param v7 the seventh mapping's value
     * @param k8 the eighth mapping's key
     * @param v8 the eighth mapping's value
     * @param k9 the ninth mapping's key
     * @param v9 the ninth mapping's value
     * @return the short-lived unmodifiable ordered {@link Map} containing the specified mappings
     * @throws IllegalArgumentException if there are any duplicate keys
     * @throws NullPointerException if any key or value is {@code null}
     * @see LinkedHashMap
     * @see Map#of(Object, Object, Object, Object, Object, Object, Object, Object, Object, Object)
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
     * Returns the short-lived unmodifiable ordered {@link Map} containing ten mappings.
     *
     * @param <K> the {@code Map}'s key type
     * @param <V> the {@code Map}'s value type
     * @param k1 the first mapping's key
     * @param v1 the first mapping's value
     * @param k2 the second mapping's key
     * @param v2 the second mapping's value
     * @param k3 the third mapping's key
     * @param v3 the third mapping's value
     * @param k4 the fourth mapping's key
     * @param v4 the fourth mapping's value
     * @param k5 the fifth mapping's key
     * @param v5 the fifth mapping's value
     * @param k6 the sixth mapping's key
     * @param v6 the sixth mapping's value
     * @param k7 the seventh mapping's key
     * @param v7 the seventh mapping's value
     * @param k8 the eighth mapping's key
     * @param v8 the eighth mapping's value
     * @param k9 the ninth mapping's key
     * @param v9 the ninth mapping's value
     * @param k10 the tenth mapping's key
     * @param v10 the tenth mapping's value
     * @return the short-lived unmodifiable ordered {@link Map} containing the specified mappings
     * @throws IllegalArgumentException if there are any duplicate keys
     * @throws NullPointerException if any key or value is {@code null}
     * @see LinkedHashMap
     * @see Map#of(Object, Object, Object, Object, Object, Object, Object, Object, Object, Object)
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
     * Returns the short-lived unmodifiable ordered {@link Map} containing keys and values extracted from the given entries.
     *
     * @param <K> the {@code Map}'s key type
     * @param <V> the {@code Map}'s value type
     * @param entries {@code Map.Entry}s containing the keys and values from which the map is populated
     * @return the short-lived unmodifiable ordered {@link Map} containing the specified mappings
     * @throws IllegalArgumentException if there are any duplicate keys
     * @throws NullPointerException if any entry, key, or value is {@code null}, or if
     *         the {@code entries} array is {@code null}
     * @see LinkedHashMap
     * @see Map#ofEntries(Map.Entry[])
     */
    @SafeVarargs
    @SuppressWarnings("varargs")
    public static <K, V> Map<K, V> orderedMapFromEntries(final Map.Entry<? extends K, ? extends V>... entries) {
        final Map<K, V> map = new LinkedHashMap<>();
        for (final Map.Entry<? extends K, ? extends V> entry : entries) {
            final V oldValue = map.put(entry.getKey(), entry.getValue());
            if (oldValue != null) {
                throw new IllegalArgumentException(
                        format("Duplicate detected: key=?, value1=?, value2=?", entry.getKey(), entry.getValue(), oldValue)
                );
            }
        }
        return unmodifiableMap(map);
    }

    private ExCollections() {
    }
}
