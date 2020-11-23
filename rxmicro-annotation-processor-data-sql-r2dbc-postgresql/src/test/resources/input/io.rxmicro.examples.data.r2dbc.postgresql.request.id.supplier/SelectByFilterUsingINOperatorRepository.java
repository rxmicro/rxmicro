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

package io.rxmicro.examples.data.r2dbc.postgresql.request.id.supplier;

import io.rxmicro.data.sql.operation.Select;
import io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLRepository;
import io.rxmicro.examples.data.r2dbc.postgresql.request.id.supplier.model.Account;
import io.rxmicro.examples.data.r2dbc.postgresql.request.id.supplier.model.Role;
import io.rxmicro.logger.RequestIdSupplier;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

// tag::content[]
@PostgreSQLRepository
public interface SelectByFilterUsingINOperatorRepository {

    @Select("SELECT * FROM ${table} WHERE role IN ('CEO'::role, 'Systems_Architect'::role)")
    CompletableFuture<List<Account>> findByRole(RequestIdSupplier requestIdSupplier);

    @Select("SELECT * FROM ${table} WHERE email NOT IN (SELECT email FROM blocked_accounts)")
    CompletableFuture<List<Account>> findNotBlockedAccount(RequestIdSupplier requestIdSupplier);

    @Select("SELECT * FROM ${table} WHERE email in ?")
    CompletableFuture<Optional<Account>> findByEmail(RequestIdSupplier requestIdSupplier, String email);

    @Select("SELECT * FROM ${table} WHERE email in ?")
    CompletableFuture<List<Account>> findByEmail(RequestIdSupplier requestIdSupplier, List<String> emails);

    @Select("SELECT * FROM ${table} WHERE role in (?)")
    CompletableFuture<List<Account>> findByRole(RequestIdSupplier requestIdSupplier, Role role);

    @Select("SELECT * FROM ${table} WHERE role in (?)")
    CompletableFuture<List<Account>> findByRole(RequestIdSupplier requestIdSupplier, List<Role> roles);

    @Select("SELECT * FROM ${table} WHERE balance in (?)")
    CompletableFuture<List<Account>> findByBalance(RequestIdSupplier requestIdSupplier, BigDecimal balance);

    @Select("SELECT * FROM ${table} WHERE balance in ?")
    CompletableFuture<List<Account>> findByBalance(RequestIdSupplier requestIdSupplier, List<BigDecimal> balances);
}
// end::content[]
