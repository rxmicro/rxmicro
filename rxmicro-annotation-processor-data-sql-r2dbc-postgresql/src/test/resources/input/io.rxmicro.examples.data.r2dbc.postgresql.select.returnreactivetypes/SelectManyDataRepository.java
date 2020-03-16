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

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.rxmicro.data.sql.operation.Select;
import io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLRepository;
import io.rxmicro.examples.data.r2dbc.postgresql.select.returnreactivetypes.model.Account;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

// tag::content[]
@PostgreSQLRepository
public interface SelectManyDataRepository {

    @Select("SELECT * FROM ${table} ORDER BY id")
    Mono<List<Account>> findAll1();

    @Select("SELECT * FROM ${table} ORDER BY id")
    Flux<Account> findAll2();

    @Select("SELECT * FROM ${table} ORDER BY id")
    CompletableFuture<List<Account>> findAll3();

    @Select("SELECT * FROM ${table} ORDER BY id")
    CompletionStage<List<Account>> findAll4();

    @Select("SELECT * FROM ${table} ORDER BY id")
    Flowable<Account> findAll5();

    @Select("SELECT * FROM ${table} ORDER BY id")
    Single<List<Account>> findAll6();
}
// end::content[]
