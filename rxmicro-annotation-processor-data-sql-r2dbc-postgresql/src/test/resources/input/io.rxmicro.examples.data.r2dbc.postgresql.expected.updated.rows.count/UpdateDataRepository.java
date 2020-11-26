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
import io.rxmicro.data.sql.ExpectedUpdatedRowsCount;
import io.rxmicro.data.sql.VariableValues;
import io.rxmicro.data.sql.operation.Update;
import io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLRepository;
import io.rxmicro.examples.data.r2dbc.postgresql.expected.updated.rows.count.model.Account;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@PostgreSQLRepository
@VariableValues({
        "${table}", "account"
})
public interface UpdateDataRepository {

    @ExpectedUpdatedRowsCount(1)
    @Update
    CompletableFuture<Void> update01(Account account);

    @ExpectedUpdatedRowsCount(10)
    @Update("UPDATE ${table} SET first_name=?, last_name=? WHERE email=?")
    CompletableFuture<Integer> update02(String firstName, String lastName, String email);

    @ExpectedUpdatedRowsCount(1)
    @Update("UPDATE ${table} SET first_name=?, last_name=? WHERE id=?")
    CompletableFuture<Boolean> update03(String firstName, String lastName, Long id);

    @ExpectedUpdatedRowsCount(1)
    @Update("UPDATE ${table} SET first_name=?, last_name=? WHERE ${by-id-filter} RETURNING *")
    CompletableFuture<Account> update04(String firstName, String lastName, Long id);

    @ExpectedUpdatedRowsCount(0)
    @Update("UPDATE ${table} SET first_name=?, last_name=? WHERE ${by-id-filter} RETURNING *")
    CompletableFuture<Account> update05(String firstName, String lastName, Long id);

    @ExpectedUpdatedRowsCount(1)
    @Update
    CompletionStage<Void> update06(Account account);

    @ExpectedUpdatedRowsCount(10)
    @Update("UPDATE ${table} SET first_name=?, last_name=?")
    CompletionStage<Integer> update07(String firstName, String lastName);

    @ExpectedUpdatedRowsCount(1)
    @Update("UPDATE ${table} SET first_name=?, last_name=? WHERE id=?")
    CompletionStage<Boolean> update08(String firstName, String lastName, Long id);

    @ExpectedUpdatedRowsCount(1)
    @Update("UPDATE ${table} SET first_name=?, last_name=? WHERE ${by-id-filter} RETURNING *")
    CompletionStage<Account> update09(String firstName, String lastName, Long id);

    @ExpectedUpdatedRowsCount(0)
    @Update("UPDATE ${table} SET first_name=?, last_name=? WHERE ${by-id-filter} RETURNING *")
    CompletionStage<Account> update10(String firstName, String lastName, Long id);

    @ExpectedUpdatedRowsCount(1)
    @Update
    Mono<Void> update11(Account account);

    // tag::content[]
    // <1>
    @ExpectedUpdatedRowsCount(10)
    @Update("UPDATE ${table} SET first_name=?, last_name=? WHERE email=?")
    Mono<Integer> update12(String firstName, String lastName, String email);

    @ExpectedUpdatedRowsCount(1)
    @Update("UPDATE ${table} SET first_name=?, last_name=? WHERE id=?")
    Mono<Boolean> update13(String firstName, String lastName, Long id);

    @ExpectedUpdatedRowsCount(1)
    @Update("UPDATE ${table} SET first_name=?, last_name=? WHERE ${by-id-filter} RETURNING *")
    Mono<Account> update14(String firstName, String lastName, Long id);

    @ExpectedUpdatedRowsCount(0)
    @Update("UPDATE ${table} SET first_name=?, last_name=? WHERE ${by-id-filter} RETURNING *")
    Mono<Account> update15(String firstName, String lastName, Long id);
    // end::content[]

    @ExpectedUpdatedRowsCount(1)
    @Update
    Completable update16(Account account);

    @ExpectedUpdatedRowsCount(10)
    @Update("UPDATE ${table} SET first_name=?, last_name=?")
    Single<Integer> update17(String firstName, String lastName);

    @ExpectedUpdatedRowsCount(1)
    @Update("UPDATE ${table} SET first_name=?, last_name=? WHERE id=?")
    Single<Boolean> update18(String firstName, String lastName, Long id);

    @ExpectedUpdatedRowsCount(1)
    @Update("UPDATE ${table} SET first_name=?, last_name=? WHERE ${by-id-filter} RETURNING *")
    Single<Account> update19(String firstName, String lastName, Long id);

    @ExpectedUpdatedRowsCount(0)
    @Update("UPDATE ${table} SET first_name=?, last_name=? WHERE ${by-id-filter} RETURNING *")
    Single<Account> update20(String firstName, String lastName, Long id);
}
