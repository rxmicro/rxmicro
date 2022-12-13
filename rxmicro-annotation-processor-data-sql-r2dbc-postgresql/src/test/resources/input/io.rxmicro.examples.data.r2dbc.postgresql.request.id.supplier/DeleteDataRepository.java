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

import io.rxmicro.data.sql.VariableValues;
import io.rxmicro.data.sql.model.EntityFieldMap;
import io.rxmicro.data.sql.operation.Delete;
import io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLRepository;
import io.rxmicro.examples.data.r2dbc.postgresql.request.id.supplier.model.Account;
import io.rxmicro.logger.RequestIdSupplier;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;

// tag::content[]
@PostgreSQLRepository
public interface DeleteDataRepository {

    @Delete
    CompletableFuture<Boolean> delete1(RequestIdSupplier requestIdSupplier, Account account);

    @Delete("DELETE FROM ${table} WHERE balance < ?")
    // <1>
    @VariableValues({
            "${table}", "account"
    })
    CompletableFuture<Long> delete2(RequestIdSupplier requestIdSupplier, BigDecimal minRequiredBalance);

    @Delete("DELETE FROM ${table} WHERE ${by-id-filter} RETURNING *")
    CompletableFuture<Account> delete3(RequestIdSupplier requestIdSupplier, Long id);

    @Delete(entityClass = Account.class)
    CompletableFuture<Long> delete4(RequestIdSupplier requestIdSupplier, Long id);

    @Delete(
            value = "DELETE FROM ${table} WHERE ${by-id-filter} RETURNING *",
            entityClass = Account.class
    )
    CompletableFuture<EntityFieldMap> delete5(RequestIdSupplier requestIdSupplier, Long id);
}
// end::content[]
