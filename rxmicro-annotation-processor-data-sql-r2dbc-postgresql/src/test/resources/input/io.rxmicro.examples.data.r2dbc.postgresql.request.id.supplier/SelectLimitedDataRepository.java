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

import io.rxmicro.data.Pageable;
import io.rxmicro.data.sql.operation.Select;
import io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLRepository;
import io.rxmicro.examples.data.r2dbc.postgresql.request.id.supplier.model.Account;
import io.rxmicro.logger.RequestIdSupplier;

import java.util.List;
import java.util.concurrent.CompletableFuture;

// tag::content[]
@PostgreSQLRepository
public interface SelectLimitedDataRepository {

    @Select("SELECT * FROM ${table} ORDER BY id LIMIT 2")
    CompletableFuture<List<Account>> findFirst2Accounts(RequestIdSupplier requestIdSupplier);

    @Select("SELECT * FROM ${table} ORDER BY id LIMIT ?")
    CompletableFuture<List<Account>> findAccounts(RequestIdSupplier requestIdSupplier, int limit);

    @Select("SELECT * FROM ${table} ORDER BY id LIMIT ? OFFSET ?")
    CompletableFuture<List<Account>> findAccounts(RequestIdSupplier requestIdSupplier, int limit, int offset);

    @Select("SELECT * FROM ${table} ORDER BY id LIMIT ? OFFSET ?")
    CompletableFuture<List<Account>> findAccounts(RequestIdSupplier requestIdSupplier, Pageable pageable);
}
// end::content[]
