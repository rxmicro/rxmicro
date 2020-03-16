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

package io.rxmicro.test.mockito.r2dbc.internal;

import java.util.function.BiFunction;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class AnyValues {

    public static final String ANY_SQL_STATEMENT = "<ANY-SQL-STATEMENT>";

    public static final String ANY_BIND_VALUE = "<ANY-BIND-VALUE>";

    public static final BiFunction<?, ?, ?> ANY_MAP_RESULT_FUNCTION = new BiFunctionImpl<>("<ANY-MAP-RESULT-FUNCTION>");

    private AnyValues() {
    }

    /**
     * @author nedis
     * @link https://rxmicro.io
     * @since 0.1
     */
    private static final class BiFunctionImpl<T, U, R> implements BiFunction<T, U, R> {

        private final String string;

        public BiFunctionImpl(final String string) {
            this.string = string;
        }

        @Override
        public R apply(final T t, final U u) {
            return null;
        }

        @Override
        public String toString() {
            return string;
        }
    }
}
