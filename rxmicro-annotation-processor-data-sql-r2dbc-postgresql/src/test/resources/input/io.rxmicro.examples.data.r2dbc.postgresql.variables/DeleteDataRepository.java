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
import io.rxmicro.data.sql.operation.Delete;
import io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLRepository;
import io.rxmicro.examples.data.r2dbc.postgresql.variables.model.Entity;

import java.util.concurrent.CompletableFuture;

// tag::content[]
@PostgreSQLRepository
@VariableValues({
        "${table}", DeleteDataRepository.GLOBAL_TABLE
})
public interface DeleteDataRepository {

    String GLOBAL_TABLE = "global_table";

    String ENTITY_TABLE = "entity_table";

    String LOCAL_TABLE = "local_table";

    @Delete("DELETE FROM ${table}")
    CompletableFuture<Void> deleteEntityTable1(Entity entity);

    @Delete("DELETE FROM ${table} RETURNING *")
    CompletableFuture<Entity> deleteEntityTable2(Entity entity);

    @Delete("DELETE FROM ${table} WHERE value=? RETURNING *")
    CompletableFuture<Entity> deleteEntityTable3(String value);

    @Delete(value = "DELETE FROM ${table} WHERE value=? RETURNING *", entityClass = Entity.class)
    CompletableFuture<EntityFieldMap> deleteEntityTable4(String value);

    @Delete("DELETE FROM ${table} WHERE value=? RETURNING value")
    CompletableFuture<EntityFieldMap> deleteGlobalTable1(String value);

    @Delete("DELETE FROM ${table} WHERE value=?")
    CompletableFuture<Void> deleteGlobalTable2(String value);

    @Delete("DELETE FROM ${table} WHERE value=? RETURNING value")
    @VariableValues({
            "${table}", DeleteDataRepository.LOCAL_TABLE
    })
    CompletableFuture<EntityFieldMap> deleteLocalTable1(String value);

    @Delete("DELETE FROM ${table} WHERE value=?")
    @VariableValues({
            "${table}", DeleteDataRepository.LOCAL_TABLE
    })
    CompletableFuture<Void> deleteLocalTable2(String value);
}
// end::content[]
