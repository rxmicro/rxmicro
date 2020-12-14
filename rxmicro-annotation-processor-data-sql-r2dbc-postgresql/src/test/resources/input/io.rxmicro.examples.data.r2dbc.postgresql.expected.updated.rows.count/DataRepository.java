/*
 * Copyright (c) 2020. http://rxmicro.io
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

package io.rxmicro.examples.data.r2dbc.postgresql.expected.updated.rows.count;

import io.rxmicro.data.sql.ExpectedUpdatedRowsCount;
import io.rxmicro.data.sql.VariableValues;
import io.rxmicro.data.sql.operation.Update;
import io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLRepository;
import io.rxmicro.examples.data.r2dbc.postgresql.expected.updated.rows.count.model.Account;
import reactor.core.publisher.Mono;

@PostgreSQLRepository
@VariableValues({
        "${table}", "account"
})
public interface DataRepository {

    @ExpectedUpdatedRowsCount(1)
    @Update("UPDATE ${table} SET first_name=?, last_name=? WHERE email=?")
    Mono<Integer> update01(String firstName, String lastName, String email);

    @ExpectedUpdatedRowsCount(1)
    @Update("UPDATE ${table} SET first_name='firstName', last_name='lastName' WHERE email='email'")
    Mono<Integer> update02();

    @ExpectedUpdatedRowsCount(1)
    @Update("UPDATE ${table} SET first_name=?, last_name=? WHERE email=? RETURNING *")
    Mono<Account> update03(String firstName, String lastName, String email);

    @ExpectedUpdatedRowsCount(0)
    @Update("UPDATE ${table} SET first_name='Richard', last_name='Hendricks' WHERE email='richard.hendricks@piedpiper.com' RETURNING *")
    Mono<Account> update04();
}

