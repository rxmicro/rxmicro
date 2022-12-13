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

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.rxmicro.data.RepeatParameter;
import io.rxmicro.data.sql.ExpectedUpdatedRowsCount;
import io.rxmicro.data.sql.VariableValues;
import io.rxmicro.data.sql.model.reactor.Transaction;
import io.rxmicro.data.sql.operation.Delete;
import io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLRepository;
import io.rxmicro.examples.data.r2dbc.postgresql.expected.updated.rows.count.model.Account;
import io.rxmicro.logger.RequestIdSupplier;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@PostgreSQLRepository
@VariableValues({
        "${table}", "account"
})
public interface DeleteDataRepository {

    @ExpectedUpdatedRowsCount(1)
    @Delete(entityClass = Account.class)
    CompletableFuture<Void> delete01(Long id);

    @ExpectedUpdatedRowsCount(10)
    @Delete
    CompletableFuture<Long> delete02();

    @ExpectedUpdatedRowsCount(1)
    @Delete(entityClass = Account.class)
    CompletableFuture<Boolean> delete03(Long id);

    @ExpectedUpdatedRowsCount(1)
    @Delete("DELETE FROM ${table} WHERE ${by-id-filter} RETURNING *")
    CompletableFuture<Account> delete04(Long id);

    @ExpectedUpdatedRowsCount(0)
    @Delete("DELETE FROM ${table} WHERE ${by-id-filter} RETURNING *")
    CompletableFuture<Account> delete05(Long id);

    @ExpectedUpdatedRowsCount(1)
    @Delete(entityClass = Account.class)
    CompletionStage<Void> delete06(Long id);

    @ExpectedUpdatedRowsCount(10)
    @Delete("DELETE FROM ${table} WHERE first_name=? OR first_name=? OR first_name=?")
    CompletionStage<Long> delete07(String firstName1, String firstName2, String firstName3);

    @ExpectedUpdatedRowsCount(1)
    @Delete(entityClass = Account.class)
    CompletionStage<Boolean> delete08(Long id);

    @ExpectedUpdatedRowsCount(1)
    @Delete("DELETE FROM ${table} WHERE ${by-id-filter} RETURNING *")
    CompletionStage<Account> delete09(Long id);

    @ExpectedUpdatedRowsCount(0)
    @Delete("DELETE FROM ${table} WHERE ${by-id-filter} RETURNING *")
    CompletionStage<Account> delete10(Long id);

    // tag::content[]
    @ExpectedUpdatedRowsCount(1)
    @Delete(entityClass = Account.class)
    Mono<Void> delete11(Long id);

    @ExpectedUpdatedRowsCount(10)
    @Delete("DELETE FROM ${table} WHERE first_name ILIKE ? OR last_name ILIKE ?")
    Mono<Long> delete12(Transaction transaction, @RepeatParameter(2) String name);

    @ExpectedUpdatedRowsCount(1)
    @Delete(entityClass = Account.class)
    Mono<Boolean> delete13(Long id);

    @ExpectedUpdatedRowsCount(1)
    @Delete("DELETE FROM ${table} WHERE ${by-id-filter} RETURNING *")
    Mono<Account> delete14(Long id);

    @ExpectedUpdatedRowsCount(0)
    @Delete("DELETE FROM ${table} WHERE ${by-id-filter} RETURNING *")
    Mono<Account> delete15(Long id);
    // end::content[]

    @ExpectedUpdatedRowsCount(1)
    @Delete(entityClass = Account.class)
    Completable delete16(RequestIdSupplier requestIdSupplier, Long id);

    @ExpectedUpdatedRowsCount(1)
    @Delete("DELETE FROM ${table} WHERE email NOT IN (SELECT email FROM blocked_accounts)")
    Single<Long> delete17();

    @ExpectedUpdatedRowsCount(1)
    @Delete(entityClass = Account.class)
    Single<Boolean> delete18(Long id);

    @ExpectedUpdatedRowsCount(1)
    @Delete("DELETE FROM ${table} WHERE ${by-id-filter} RETURNING *")
    Single<Account> delete19(Long id);

    @ExpectedUpdatedRowsCount(0)
    @Delete("DELETE FROM ${table} WHERE ${by-id-filter} RETURNING *")
    Single<Account> delete20(Long id);
}
