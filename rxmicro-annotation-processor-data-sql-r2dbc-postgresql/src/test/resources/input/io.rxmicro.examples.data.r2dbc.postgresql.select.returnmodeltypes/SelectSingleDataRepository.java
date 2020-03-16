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

package io.rxmicro.examples.data.r2dbc.postgresql.select.returnmodeltypes;

import io.rxmicro.data.sql.VariableValues;
import io.rxmicro.data.sql.model.EntityFieldList;
import io.rxmicro.data.sql.model.EntityFieldMap;
import io.rxmicro.data.sql.operation.Select;
import io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLRepository;
import io.rxmicro.examples.data.r2dbc.postgresql.select.returnmodeltypes.model.Account;
import io.rxmicro.examples.data.r2dbc.postgresql.select.returnmodeltypes.model.Role;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;

// tag::content[]
@PostgreSQLRepository
@VariableValues({
        "${table}", "account"
})
public interface SelectSingleDataRepository {

    @Select("SELECT * FROM ${table} " +
            "WHERE email='richard.hendricks@piedpiper.com'")
    CompletableFuture<Account> findSingleAccount();

    @Select("SELECT first_name, last_name FROM ${table} " +
            "WHERE email='richard.hendricks@piedpiper.com'")
    CompletableFuture<EntityFieldMap> findSingleEntityFieldMap();

    @Select("SELECT first_name, last_name FROM ${table} " +
            "WHERE email='richard.hendricks@piedpiper.com'")
    CompletableFuture<EntityFieldList> findSingleEntityFieldList();

    @Select("SELECT email FROM ${table} " +
            "WHERE email='richard.hendricks@piedpiper.com'")
    CompletableFuture<String> findSingleEmail();

    @Select("SELECT role::text FROM ${table} " +
            "WHERE email='richard.hendricks@piedpiper.com'")
    CompletableFuture<Role> findSingleRole();

    @Select("SELECT balance FROM ${table} " +
            "WHERE email='richard.hendricks@piedpiper.com'")
    CompletableFuture<BigDecimal> findSingleBalance();
}
// end::content[]
