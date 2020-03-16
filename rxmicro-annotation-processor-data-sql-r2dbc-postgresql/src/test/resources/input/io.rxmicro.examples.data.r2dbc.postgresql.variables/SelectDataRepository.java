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
import io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLRepository;
import io.rxmicro.examples.data.r2dbc.postgresql.variables.model.Entity;

import java.util.List;
import java.util.concurrent.CompletableFuture;

// tag::content[]
@PostgreSQLRepository
@VariableValues({
        "${table}", SelectDataRepository.GLOBAL_TABLE
})
public interface SelectDataRepository {

    String GLOBAL_TABLE = "global_table";

    String ENTITY_TABLE = "entity_table";

    String LOCAL_TABLE = "local_table";

    @Select("SELECT * FROM ${table}")
    CompletableFuture<List<Entity>> findFromEntityTable1();

    @Select(value = "SELECT * FROM ${table}", entityClass = Entity.class)
    CompletableFuture<List<EntityFieldMap>> findFromEntityTable2();

    @Select("SELECT * FROM ${table}")
    CompletableFuture<List<EntityFieldMap>> findFromGlobalTable();

    @Select("SELECT * FROM ${table}")
    @VariableValues({
            "${table}", SelectDataRepository.LOCAL_TABLE
    })
    CompletableFuture<List<EntityFieldMap>> findFromLocalTable();
}
// end::content[]
