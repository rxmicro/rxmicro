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

package io.rxmicro.examples.data.r2dbc.postgresql.select.returnreactivetypes;

import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import io.rxmicro.data.sql.operation.Select;
import io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLRepository;
import io.rxmicro.examples.data.r2dbc.postgresql.select.returnreactivetypes.model.Account;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

// tag::content[]
@PostgreSQLRepository
public interface SelectSingleDataRepository {

    @Select("SELECT * FROM ${table} WHERE email = ?")
    Mono<Account> findByEmail1(String email);

    @Select("SELECT * FROM ${table} WHERE email = ?")
    CompletableFuture<Account> findByEmail2(String email);

    @Select("SELECT * FROM ${table} WHERE email = ?")
    CompletionStage<Account> findByEmail3(String email);

    @Select("SELECT * FROM ${table} WHERE email = ?")
    CompletableFuture<Optional<Account>> findByEmail4(String email);

    @Select("SELECT * FROM ${table} WHERE email = ?")
    CompletionStage<Optional<Account>> findByEmail5(String email);

    @Select("SELECT * FROM ${table} WHERE email = ?")
    Single<Account> findByEmail6(String email);

    @Select("SELECT * FROM ${table} WHERE email = ?")
    Maybe<Account> findByEmail7(String email);
}
// end::content[]
