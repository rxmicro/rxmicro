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

package io.rxmicro.examples.data.r2dbc.postgresql.select.projection;

import io.rxmicro.data.sql.operation.Select;
import io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLRepository;
import io.rxmicro.examples.data.r2dbc.postgresql.select.projection.model.Account;

import java.util.concurrent.CompletableFuture;

// tag::content[]
@PostgreSQLRepository
public interface SelectProjectionDataRepository {

    @Select("SELECT * FROM ${table} " +
            "WHERE email='richard.hendricks@piedpiper.com'")
    CompletableFuture<Account> findAllColumns();

    @Select("SELECT id, email, first_name, last_name, balance FROM ${table} " +
            "WHERE email='richard.hendricks@piedpiper.com'")
    CompletableFuture<Account> findAllColumnsExceptRole1();

    @Select("SELECT id, email, last_name, first_name, balance FROM ${table} " +
            "WHERE email='richard.hendricks@piedpiper.com'")
    CompletableFuture<Account> findAllColumnsExceptRole2();

    @Select("SELECT 1 as id, " +
            "'richard.hendricks@piedpiper.com' as email, " +
            "'Hendricks' as last_name, " +
            "'Richard' as first_name, " +
            "70000.00 as balance")
    CompletableFuture<Account> findAllColumnsExceptRole3();

    @Select("SELECT first_name, last_name FROM ${table} " +
            "WHERE email='richard.hendricks@piedpiper.com'")
    CompletableFuture<Account> findFirstAndLastName();

    @Select("SELECT id, " +
            "'***@***' as email, " +
            "upper(last_name) as last_name, " +
            "first_name, " +
            "(20000 + 50000.00) as balance " +
            "FROM ${table} " +
            "WHERE email='richard.hendricks@piedpiper.com'")
    CompletableFuture<Account> findModifiedColumns();
}
// end::content[]
