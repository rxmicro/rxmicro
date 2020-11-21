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

package io.rxmicro.test.dbunit;

import org.dbunit.operation.CompositeOperation;
import org.dbunit.operation.DatabaseOperation;

/**
 * Provides the supported init database strategies.
 *
 * @author nedis
 * @since 0.7
 */
public enum InitDatabaseStrategy {

    /**
     * This strategy means that DBUnit will delete all and then insert data in tables present in provided dataset.
     *
     * @see org.dbunit.operation.DeleteAllOperation
     * @see org.dbunit.operation.InsertOperation
     */
    CLEAN_INSERT(DatabaseOperation.CLEAN_INSERT),

    /**
     * This strategy means that DBUnit will truncate all and then insert data in tables present in provided dataset.
     *
     * @see org.dbunit.operation.TruncateTableOperation
     * @see org.dbunit.operation.InsertOperation
     */
    TRUNCATE_INSERT(new CompositeOperation(DatabaseOperation.TRUNCATE_TABLE, DatabaseOperation.INSERT)),

    /**
     * This strategy means that DBUnit will insert data in tables present in provided dataset.
     *
     * @see org.dbunit.operation.InsertOperation
     */
    INSERT(DatabaseOperation.INSERT),

    /**
     * This strategy means that DBUnit will refresh data in tables present in provided dataset.
     *
     * @see org.dbunit.operation.RefreshOperation
     */
    REFRESH(DatabaseOperation.REFRESH),

    /**
     * This strategy means that DBUnit will update data in tables present in provided dataset.
     *
     * @see org.dbunit.operation.UpdateOperation
     */
    UPDATE(DatabaseOperation.UPDATE);

    private final DatabaseOperation operation;

    InitDatabaseStrategy(DatabaseOperation operation) {
        this.operation = operation;
    }

    /**
     * Returns the {@link DatabaseOperation} for the current strategy.
     *
     * @return the {@link DatabaseOperation} for the current strategy.
     */
    public DatabaseOperation getOperation() {
        return operation;
    }
}
