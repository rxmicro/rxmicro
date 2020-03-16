/*
 * Copyright 2019 https://rxmicro.io
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

package io.rxmicro.test.local.util;

import java.lang.reflect.AnnotatedElement;
import java.util.Arrays;
import java.util.Set;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class Mocks {

    private static final Set<String> MOCK_ANNOTATIONS = Set.of(
            "org.mockito.Mock"
            // Add supported mock annotations here
    );

    private static final String[] MOCK_NAME_FRAGMENTS = {
            "$MockitoMock$",
            // Add supported mock annotations here
    };

    public static boolean isMock(final AnnotatedElement annotatedElement) {
        return Arrays.stream(annotatedElement.getAnnotations())
                .map(a -> a.annotationType().getName())
                .anyMatch(MOCK_ANNOTATIONS::contains);
    }

    public static boolean isMock(final Object instance) {
        return Arrays.stream(MOCK_NAME_FRAGMENTS)
                .anyMatch(f -> instance.getClass().getName().contains(f));
    }

    private Mocks() {
    }
}
