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
import io.rxmicro.data.sql.operation.Insert;
import io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLRepository;
import io.rxmicro.examples.data.r2dbc.postgresql.expected.updated.rows.count.model.Account;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@PostgreSQLRepository
@VariableValues({
        "${table}", "account"
})
public interface InsertDataRepository {

    @ExpectedUpdatedRowsCount(1)
    @Insert
    CompletableFuture<Void> insert01(Account account);

    @ExpectedUpdatedRowsCount(10)
    @Insert("INSERT INTO ${table} SELECT * FROM dump")
    CompletableFuture<Integer> insert02();

    @ExpectedUpdatedRowsCount(1)
    @Insert("INSERT INTO ${table} VALUES(nextval('account_seq'),?,?)")
    CompletableFuture<Boolean> insert03(String firstName, String lastName);

    @ExpectedUpdatedRowsCount(1)
    @Insert("INSERT INTO ${table} VALUES(nextval('account_seq'),?,?) RETURNING *")
    CompletableFuture<Account> insert04(String firstName, String lastName);

    @ExpectedUpdatedRowsCount(0)
    @Insert("INSERT INTO ${table} VALUES(nextval('account_seq'),?,?) RETURNING *")
    CompletableFuture<Account> insert05(String firstName, String lastName);

    @ExpectedUpdatedRowsCount(1)
    @Insert
    CompletionStage<Void> insert06(Account account);

    @ExpectedUpdatedRowsCount(10)
    @Insert("INSERT INTO ${table} SELECT * FROM dump")
    CompletionStage<Integer> insert07();

    @ExpectedUpdatedRowsCount(1)
    @Insert("INSERT INTO ${table} VALUES(nextval('account_seq'),?,?)")
    CompletionStage<Boolean> insert08(String firstName, String lastName);

    @ExpectedUpdatedRowsCount(1)
    @Insert("INSERT INTO ${table} VALUES(nextval('account_seq'),?,?) RETURNING *")
    CompletionStage<Account> insert09(String firstName, String lastName);

    @ExpectedUpdatedRowsCount(0)
    @Insert("INSERT INTO ${table} VALUES(nextval('account_seq'),?,?) RETURNING *")
    CompletionStage<Account> insert10(String firstName, String lastName);

    @ExpectedUpdatedRowsCount(1)
    @Insert
    Mono<Void> insert11(Account account);

    // tag::content[]
    @ExpectedUpdatedRowsCount(10)
    @Insert("INSERT INTO ${table} SELECT * FROM dump")
    Mono<Integer> insert12();

    @ExpectedUpdatedRowsCount(1)
    @Insert("INSERT INTO ${table} VALUES(nextval('account_seq'),?,?)")
    Mono<Boolean> insert13(String firstName, String lastName);

    @ExpectedUpdatedRowsCount(1)
    @Insert("INSERT INTO ${table} VALUES(nextval('account_seq'),?,?) RETURNING *")
    Mono<Account> insert14(String firstName, String lastName);

    @ExpectedUpdatedRowsCount(0)
    @Insert("INSERT INTO ${table} VALUES(nextval('account_seq'),?,?) RETURNING *")
    Mono<Account> insert15(String firstName, String lastName);
    // end::content[]

    @ExpectedUpdatedRowsCount(1)
    @Insert
    Completable insert16(Account account);

    @ExpectedUpdatedRowsCount(10)
    @Insert("INSERT INTO ${table} SELECT * FROM dump")
    Single<Integer> insert17();

    @ExpectedUpdatedRowsCount(1)
    @Insert("INSERT INTO ${table} VALUES(nextval('account_seq'),?,?)")
    Single<Boolean> insert18(String firstName, String lastName);

    @ExpectedUpdatedRowsCount(1)
    @Insert("INSERT INTO ${table} VALUES(nextval('account_seq'),?,?) RETURNING *")
    Single<Account> insert19(String firstName, String lastName);

    @ExpectedUpdatedRowsCount(0)
    @Insert("INSERT INTO ${table} VALUES(nextval('account_seq'),?,?) RETURNING *")
    Single<Account> insert20(String firstName, String lastName);
}
