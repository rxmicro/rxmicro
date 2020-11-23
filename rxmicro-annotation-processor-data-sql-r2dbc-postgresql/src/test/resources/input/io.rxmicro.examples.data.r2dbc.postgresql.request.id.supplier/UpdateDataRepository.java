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
import io.rxmicro.data.sql.operation.Update;
import io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLRepository;
import io.rxmicro.examples.data.r2dbc.postgresql.request.id.supplier.model.Account;
import io.rxmicro.logger.RequestIdSupplier;

import java.util.concurrent.CompletableFuture;

// tag::content[]
@PostgreSQLRepository
public interface UpdateDataRepository {

    @Update
    CompletableFuture<Boolean> update1(RequestIdSupplier requestIdSupplier, Account account);

    @Update("UPDATE ${table} SET first_name=?, last_name=? WHERE id=?")
    // <1>
    @VariableValues({
            "${table}", "account"
    })
    CompletableFuture<Integer> update2(RequestIdSupplier requestIdSupplier, String firstName, String lastName, Long id);

    @Update("UPDATE ${table} SET first_name=?, last_name=? WHERE ${by-id-filter} RETURNING *")
    CompletableFuture<Account> update3(String firstName, String lastName, Long id);

    @Update(
            value = "UPDATE ${table} SET first_name=?, last_name=? " +
                    "WHERE id = ?",
            entityClass = Account.class
    )
    CompletableFuture<Integer> update4(RequestIdSupplier requestIdSupplier, String firstName, String lastName, Long id);

    @Update(
            value = "UPDATE ${table} SET first_name=?, last_name=? " +
                    "WHERE ${by-id-filter} RETURNING *",
            entityClass = Account.class
    )
    CompletableFuture<EntityFieldMap> update5(RequestIdSupplier requestIdSupplier, String firstName, String lastName, Long id);
}
// end::content[]
