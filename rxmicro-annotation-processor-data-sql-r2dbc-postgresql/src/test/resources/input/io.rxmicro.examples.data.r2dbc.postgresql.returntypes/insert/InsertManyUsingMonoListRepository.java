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

package io.rxmicro.examples.data.r2dbc.postgresql.returntypes.insert;

import io.rxmicro.data.sql.VariableValues;
import io.rxmicro.data.sql.model.EntityFieldList;
import io.rxmicro.data.sql.model.EntityFieldMap;
import io.rxmicro.data.sql.operation.Insert;
import io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLRepository;
import io.rxmicro.examples.data.r2dbc.postgresql.returntypes.model.Account;
import reactor.core.publisher.Mono;

import java.util.List;

@PostgreSQLRepository
@VariableValues({
        "${table}", "account"
})
public interface InsertManyUsingMonoListRepository {

    @Insert("INSERT INTO ${table} SELECT * FROM dump RETURNING *")
    Mono<List<Account>> insertAll01();

    @Insert("INSERT INTO ${table} SELECT * FROM dump RETURNING first_name, last_name")
    Mono<List<EntityFieldMap>> insertAll02();

    @Insert("INSERT INTO ${table} SELECT * FROM dump RETURNING first_name, last_name")
    Mono<List<EntityFieldList>> insertAll03();
}
