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

package io.rxmicro.annotation.processor.data.sql.model;

import io.rxmicro.common.InvalidStateException;

import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @since 0.1
 */
public abstract class VariableContext {

    private DbObjectName currentTableName;

    public final DbObjectName getCurrentTableName() {
        return require(currentTableName, "Current table is not set");
    }

    public void setCurrentTableName(final DbObjectName currentTableName) {
        if (this.currentTableName != null) {
            throw new InvalidStateException("currentTable  already set");
        }
        this.currentTableName = currentTableName;
    }

    public void releaseCurrentTable() {
        currentTableName = null;
    }

    /**
     * @param sequenceName - sequence name
     * @return get next sequence value expression
     * @throws UnsupportedOperationException if variable context does not support sequences
     */
    public abstract String getNextSequenceValue(String sequenceName);

    /**
     * @return the pseudo table name to read original values for modification
     * @throws UnsupportedOperationException if variable context does not support pseudo table name to read original values for modification
     */
    public abstract String getPseudoTableNameToReadOriginalValuesForModification();
}
