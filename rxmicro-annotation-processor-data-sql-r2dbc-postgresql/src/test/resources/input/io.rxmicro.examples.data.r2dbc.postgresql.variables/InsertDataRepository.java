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
import io.rxmicro.data.sql.operation.Insert;
import io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLRepository;
import io.rxmicro.examples.data.r2dbc.postgresql.variables.model.Entity;

import java.util.concurrent.CompletableFuture;

// tag::content[]
@PostgreSQLRepository
@VariableValues({
        "${table}", InsertDataRepository.GLOBAL_TABLE
})
public interface InsertDataRepository {

    String GLOBAL_TABLE = "global_table";

    String ENTITY_TABLE = "entity_table";

    String LOCAL_TABLE = "local_table";

    @Insert
    CompletableFuture<Void> insertIntoEntityTable1(Entity entity);

    @Insert("INSERT INTO ${table}(${inserted-columns}) VALUES(${values}) RETURNING *")
    CompletableFuture<Entity> insertIntoEntityTable2(Entity entity);

    @Insert("INSERT INTO ${table} VALUES(?) RETURNING *")
    CompletableFuture<Entity> insertIntoEntityTable3(String value);

    @Insert(value = "INSERT INTO ${table} VALUES(?) RETURNING *", entityClass = Entity.class)
    CompletableFuture<EntityFieldMap> insertIntoEntityTable4(String value);

    @Insert("INSERT INTO ${table} VALUES(?) RETURNING value")
    CompletableFuture<EntityFieldMap> insertIntoGlobalTable1(String value);

    @Insert("INSERT INTO ${table} VALUES(?)")
    CompletableFuture<Void> insertIntoGlobalTable2(String value);

    @Insert("INSERT INTO ${table} VALUES(?) RETURNING value")
    @VariableValues({
            "${table}", InsertDataRepository.LOCAL_TABLE
    })
    CompletableFuture<EntityFieldMap> insertIntoLocalTable1(String value);

    @Insert("INSERT INTO ${table} VALUES(?)")
    @VariableValues({
            "${table}", InsertDataRepository.LOCAL_TABLE
    })
    CompletableFuture<Void> insertIntoLocalTable2(String value);
}
// end::content[]
