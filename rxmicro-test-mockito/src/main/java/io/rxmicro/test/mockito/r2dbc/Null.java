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

package io.rxmicro.test.mockito.r2dbc;

import static io.rxmicro.common.util.Requires.require;

/**
 * Represents a {@code null} value in the column of database table
 *
 * @author nedis
 * @since 0.1
 */
public final class Null {

    private final Class<?> type;

    /**
     * Creates a new instance of {@link Null} class with the specified class of the database table column
     *
     * @param type the specified class of the database table column
     */
    public Null(final Class<?> type) {
        this.type = require(type);
    }

    /**
     * Returns the class of the database table column
     *
     * @return the class of the database table column
     */
    public Class<?> getType() {
        return type;
    }
}
