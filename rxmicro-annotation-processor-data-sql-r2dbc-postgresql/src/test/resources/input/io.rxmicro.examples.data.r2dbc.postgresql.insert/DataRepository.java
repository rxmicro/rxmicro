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

package io.rxmicro.examples.data.r2dbc.postgresql.insert;

import io.rxmicro.data.sql.VariableValues;
import io.rxmicro.data.sql.model.EntityFieldMap;
import io.rxmicro.data.sql.operation.Insert;
import io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLRepository;
import io.rxmicro.examples.data.r2dbc.postgresql.insert.model.Account;
import io.rxmicro.examples.data.r2dbc.postgresql.insert.model.AccountResult;
import io.rxmicro.examples.data.r2dbc.postgresql.insert.model.Role;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CompletableFuture;

// tag::content[]
@PostgreSQLRepository
public interface DataRepository {

    @Insert
    CompletableFuture<Boolean> insert1(Account account);

    @Insert
    CompletableFuture<Account> insert2(Account account);

    @Insert("INSERT INTO ${table} VALUES(nextval('account_seq'),?,?,?,?,?)")
    // <1>
    @VariableValues({
            "${table}", "account"
    })
    CompletableFuture<Integer> insert3(
            String email, String firstName, String lastName, BigDecimal balance, Role role
    );

    @Insert("INSERT INTO ${table} VALUES(nextval('account_seq'),?,?,?,?,?) RETURNING *")
    CompletableFuture<Account> insert4(
            String email, String firstName, String lastName, BigDecimal balance, Role role
    );

    @Insert(
            value = "INSERT INTO ${table} VALUES(nextval('account_seq'),?,?,?,?,?)",
            entityClass = Account.class
    )
    CompletableFuture<Integer> insert5(
            String email, String firstName, String lastName, BigDecimal balance, Role role
    );

    @Insert(
            value = "INSERT INTO ${table} VALUES(nextval('account_seq'),?,?,?,?,?) RETURNING *",
            entityClass = Account.class
    )
    CompletableFuture<EntityFieldMap> insert6(
            String email, String firstName, String lastName, BigDecimal balance, Role role
    );

    @Insert("INSERT INTO ${table}(${inserted-columns}) VALUES(${values}) " +
            "RETURNING ${returning-columns}")
    CompletableFuture<AccountResult> insert7(Account account);

    @Insert("INSERT INTO ${table}(${inserted-columns}) VALUES(${values}) " +
            "ON CONFLICT (${id-columns}) DO UPDATE SET ${on-conflict-update-inserted-columns}" +
            "RETURNING ${returning-columns}")
    CompletableFuture<AccountResult> insert8(Account account);

    @Insert("INSERT INTO ${table}(${inserted-columns}) VALUES(${values}) " +
            "ON CONFLICT (${id-columns}) DO UPDATE SET ${on-conflict-update-inserted-columns}")
    CompletableFuture<Void> insert9(Account account);

    @Insert("INSERT INTO ${table}(${inserted-columns}) VALUES(${values}) " +
            "ON CONFLICT (${id-columns}) DO NOTHING")
    CompletableFuture<Void> insert10(Account account);

    @Insert("INSERT INTO ${table} SELECT * FROM dump RETURNING *")
    CompletableFuture<List<Account>> insertMany1();

    @Insert("INSERT INTO account SELECT * FROM dump")
    CompletableFuture<Integer> insertMany2();
}
// end::content[]
