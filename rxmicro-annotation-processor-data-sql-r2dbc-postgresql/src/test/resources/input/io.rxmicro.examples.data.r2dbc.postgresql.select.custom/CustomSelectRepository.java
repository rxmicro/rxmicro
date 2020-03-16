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

package io.rxmicro.examples.data.r2dbc.postgresql.select.custom;

import io.rxmicro.data.sql.model.EntityFieldMap;
import io.rxmicro.data.sql.operation.CustomSelect;
import io.rxmicro.data.sql.operation.Select;
import io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLRepository;
import io.rxmicro.examples.data.r2dbc.postgresql.select.custom.model.Account;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

// tag::content[]
@PostgreSQLRepository
public interface CustomSelectRepository {

    @Select
    CompletableFuture<List<EntityFieldMap>> findAll(
            @CustomSelect String sql // <1>
    );

    @Select
    CompletableFuture<Optional<Account>> findAccount(
            @CustomSelect(supportUniversalPlaceholder = false) String sql, // <2>
            String firstName
    );

    @Select
    CompletableFuture<Optional<Account>> findFirstAndLastName(
            @CustomSelect(selectedColumns = {"first_name", "last_name"}) String sql, // <3>
            String firstName
    );

    @Select
    CompletableFuture<Optional<Account>> findLastAndFirstName(
            @CustomSelect(selectedColumns = {"last_name", "first_name"}) String sql,
            String firstName
    );
}
// end::content[]
