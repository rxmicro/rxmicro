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

package io.rxmicro.examples.data.r2dbc.postgresql.variables;

import io.rxmicro.data.sql.VariableValues;
import io.rxmicro.data.sql.model.EntityFieldMap;
import io.rxmicro.data.sql.operation.Select;

import java.util.concurrent.CompletableFuture;

public interface DataRepository {

    // tag::content[]
    // <1>
    String TABLE_NAME = "entity";

    // <2>
    @Select("SELECT id, value FROM " + TABLE_NAME + " WHERE id = ?")
    CompletableFuture<EntityFieldMap> findById1(long id);

    // <3>
    @Select("SELECT id, value FROM ${table} WHERE id = ?")
    @VariableValues({
            "${table}", TABLE_NAME
    })
    CompletableFuture<EntityFieldMap> findById2(long id);
    // end::content[]
}
