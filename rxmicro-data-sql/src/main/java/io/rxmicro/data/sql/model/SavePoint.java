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

package io.rxmicro.data.sql.model;

import static io.rxmicro.common.util.Requires.require;

/**
 * Base savepoint class
 *
 * @author nedis
 * @since 0.1
 * @see TransactionType
 * @see io.rxmicro.data.sql.model.reactor.Transaction
 * @see io.rxmicro.data.sql.model.rxjava3.Transaction
 * @see io.rxmicro.data.sql.model.completablefuture.Transaction
 * @see IsolationLevel
 */
public final class SavePoint {

    private final String name;

    /**
     * Creates a new instance of savepoint with the given savepoint name
     *
     * @param name the save point name
     */
    public SavePoint(final String name) {
        this.name = require(name);
    }

    /**
     * Returns the savepoint name
     *
     * @return the savepoint name
     */
    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        final SavePoint savePoint = (SavePoint) other;
        return name.equals(savePoint.name);
    }

    @Override
    public String toString() {
        return name;
    }
}
