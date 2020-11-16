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
 * @author nedis
 * @since 0.7
 */
public enum InitDatabaseStrategy {

    CLEAN_INSERT(DatabaseOperation.CLEAN_INSERT),

    TRUNCATE_INSERT(new CompositeOperation(DatabaseOperation.TRUNCATE_TABLE, DatabaseOperation.INSERT)),

    INSERT(DatabaseOperation.INSERT),

    REFRESH(DatabaseOperation.REFRESH),

    UPDATE(DatabaseOperation.UPDATE);

    private final DatabaseOperation operation;

    InitDatabaseStrategy(DatabaseOperation operation) {
        this.operation = operation;
    }

    public DatabaseOperation getOperation() {
        return operation;
    }
}
