/*
 * Copyright (c) 2020. http://rxmicro.io
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

package io.rxmicro.cdi.internal;

import io.rxmicro.common.RxMicroException;
import io.rxmicro.common.util.Formats;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static io.rxmicro.common.util.Formats.format;
import static java.lang.String.join;
import static java.util.Map.entry;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public final class CircularDependenciesResolver {

    private static final int MAX_DEPENDENCY_DEPTH = 50;

    private final Deque<Map.Entry<Object, String>> deque = new ArrayDeque<>();

    public void push(final Object instance,
                     final String injectionPoint) {
        deque.push(entry(instance, injectionPoint));
        if (deque.size() >= MAX_DEPENDENCY_DEPTH) {
            throw new CircularDependenciesDetectedException(join(";", getCircularDependencies()));
        }
    }

    private List<String> getCircularDependencies() {
        final Map<String, AtomicInteger> countMap = new HashMap<>();
        for (final Map.Entry<Object, String> entry : deque) {
            final AtomicInteger count = countMap.computeIfAbsent(
                    format("\n{Bean: '?', injection point: '?'", entry.getKey().getClass().getName(), entry.getValue()),
                    k -> new AtomicInteger(0)
            );
            count.incrementAndGet();
        }
        return countMap.entrySet().stream()
                .sorted((o1, o2) -> Integer.compare(o2.getValue().get(), o1.getValue().get()))
                .map(e -> format("?, circular dependencies marker: ?}", e.getKey(), e.getValue().get()))
                .collect(Collectors.toList());
    }

    public void pop() {
        deque.pop();
    }

    /**
     * @author nedis
     * @link http://rxmicro.io
     * @since 0.1
     */
    private static final class CircularDependenciesDetectedException extends RxMicroException {

        /**
         * This constructor uses {@link Formats#format(String, Object...) Formats.format} to format error message
         */
        private CircularDependenciesDetectedException(final Object... args) {
            super(
                    "The following beans contain the circular dependencies: \n?.\n\n" +
                            "Remove these circular dependencies!",
                    null,
                    false,
                    false,
                    args
            );
        }
    }
}
