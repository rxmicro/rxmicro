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
import io.rxmicro.data.sql.operation.Update;
import io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLRepository;
import io.rxmicro.examples.data.r2dbc.postgresql.variables.model.Entity;

import java.util.concurrent.CompletableFuture;

// tag::content[]
@PostgreSQLRepository
@VariableValues({
        "${table}", UpdateDataRepository.GLOBAL_TABLE
})
public interface UpdateDataRepository {

    String GLOBAL_TABLE = "global_table";

    String ENTITY_TABLE = "entity_table";

    String LOCAL_TABLE = "local_table";

    @Update("UPDATE ${table} SET ${updated-columns}")
    CompletableFuture<Void> updateEntityTable1(Entity entity);

    @Update("UPDATE ${table} SET ${updated-columns} RETURNING *")
    CompletableFuture<Entity> updateEntityTable2(Entity entity);

    @Update("UPDATE ${table} SET value=? RETURNING *")
    CompletableFuture<Entity> updateEntityTable3(String value);

    @Update(value = "UPDATE ${table} SET value=? RETURNING *", entityClass = Entity.class)
    CompletableFuture<EntityFieldMap> updateEntityTable4(String value);

    @Update("UPDATE ${table} SET value=? RETURNING value")
    CompletableFuture<EntityFieldMap> updateGlobalTable1(String value);

    @Update("UPDATE ${table} SET value=? ")
    CompletableFuture<Void> updateGlobalTable2(String value);

    @Update("UPDATE ${table} SET value=? RETURNING value")
    @VariableValues({
            "${table}", UpdateDataRepository.LOCAL_TABLE
    })
    CompletableFuture<EntityFieldMap> updateLocalTable1(String value);

    @Update("UPDATE ${table} SET value=? ")
    @VariableValues({
            "${table}", UpdateDataRepository.LOCAL_TABLE
    })
    CompletableFuture<Void> updateLocalTable2(String value);
}
// end::content[]
