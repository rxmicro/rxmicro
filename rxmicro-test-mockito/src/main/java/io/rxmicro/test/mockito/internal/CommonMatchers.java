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

package io.rxmicro.test.mockito.internal;

import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.argThat;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public final class CommonMatchers {

    public static <T> T any(final Class<T> type,
                            final T toStringInstance) {
        ArgumentMatchers.any(type);
        return toStringInstance;
    }

    public static int anyInt() {
        ArgumentMatchers.anyInt();
        return 0;
    }

    public static String anyString(final String toStringInstance) {
        ArgumentMatchers.anyString();
        return toStringInstance;
    }

    public static <T> List<T> anyList(final String toStringInstance) {
        ArgumentMatchers.any(List.class);
        return new ArrayList<>(0) {
            @Override
            public String toString() {
                return toStringInstance;
            }
        };
    }

    public static <T> T equal(final T value) {
        ArgumentMatchers.eq(value);
        return value;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static <T> T equal(final ArgumentMatcher<T> argumentMatcher,
                              final T value) {
        argThat(argumentMatcher);
        return value;
    }

    private CommonMatchers() {
    }
}
