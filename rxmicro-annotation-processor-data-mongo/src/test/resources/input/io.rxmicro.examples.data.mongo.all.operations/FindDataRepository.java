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

package io.rxmicro.examples.data.mongo.all.operations;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import io.rxmicro.data.Pageable;
import io.rxmicro.data.RepeatParameter;
import io.rxmicro.data.SortOrder;
import io.rxmicro.data.mongo.MongoRepository;
import io.rxmicro.data.mongo.ProjectionType;
import io.rxmicro.data.mongo.operation.Find;
import io.rxmicro.examples.data.mongo.all.operations.model.Account;
import io.rxmicro.examples.data.mongo.all.operations.model.Role;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@MongoRepository(collection = "collection")
public interface FindDataRepository {

    @Find(query = "{_id: ?}")
    Mono<Account> findById1(long id);

    @Find(query = "{role: ?}")
    Mono<Account> findByRole1(Role role);

    @Find(sort = "{balance: -1}", limit = 20, skip = 5)
    Flux<Account> findAll1();

    @Find(sort = "{balance: ?}")
    Flux<Account> findAll1(SortOrder sortOrder);

    @Find(sort = "{balance: ?}")
    Mono<List<Account>> findAll1(SortOrder sortOrder, Pageable pageable);

    @Find(query = "{role: ?}", sort = "{balance: ?}")
    Flux<Account> findFiltered1(Role role, SortOrder sortOrder, Pageable pageable);

    @Find(query = "{$or: [{firstName: ?}, {lastName: ?}]}", sort = "{balance: ?}")
    Mono<List<Account>> findFiltered1(@RepeatParameter(2) String name, SortOrder sortOrder, Pageable pageable);

    @Find(projection = "{firstName: 1, lastName: 1}")
    Flux<Account> findProjection1(Pageable pageable);

    @Find(projection = "{firstName: ?, lastName: ?}", sort = "{balance: ?}")
    Mono<List<Account>> findProjection1(ProjectionType firstName, ProjectionType lastName, SortOrder sortOrder, Pageable pageable);

    // -----------------------------------------------------------------------------------------------------------------

    @Find(query = "{_id: ?}")
    Single<Account> findById2(long id);

    @Find(query = "{role: ?}")
    Maybe<Account> findByRole2(Role role);

    @Find(sort = "{balance: -1}", limit = 20, skip = 5)
    Flowable<Account> findAll2();

    @Find(sort = "{balance: ?}")
    Flowable<Account> findAll2(SortOrder sortOrder);

    @Find(sort = "{balance: ?}")
    Single<List<Account>> findAll2(SortOrder sortOrder, Pageable pageable);

    @Find(query = "{role: ?}", sort = "{balance: ?}")
    Flowable<Account> findFiltered2(Role role, SortOrder sortOrder, Pageable pageable);

    @Find(query = "{$or: [{firstName: ?}, {lastName: ?}]}", sort = "{balance: ?}")
    Single<List<Account>> findFiltered2(@RepeatParameter(2) String name, SortOrder sortOrder, Pageable pageable);

    @Find(projection = "{firstName: 1, lastName: 1}")
    Flowable<Account> findProjection2(Pageable pageable);

    @Find(projection = "{firstName: ?, lastName: ?}", sort = "{balance: ?}")
    Single<List<Account>> findProjection2(ProjectionType firstName, ProjectionType lastName, SortOrder sortOrder, Pageable pageable);

    // -----------------------------------------------------------------------------------------------------------------

    @Find(query = "{_id: ?}")
    CompletableFuture<Account> findById3(long id);

    @Find(query = "{role: ?}")
    CompletableFuture<Optional<Account>> findByRole3(Role role);

    @Find(sort = "{balance: -1}", limit = 20, skip = 5)
    CompletableFuture<List<Account>> findAll3();

    @Find(sort = "{balance: ?}")
    CompletableFuture<List<Account>> findAll3(SortOrder sortOrder);

    @Find(sort = "{balance: ?}")
    CompletableFuture<List<Account>> findAll3(SortOrder sortOrder, Pageable pageable);

    @Find(query = "{role: ?}", sort = "{balance: ?}")
    CompletableFuture<List<Account>> findFiltered3(Role role, SortOrder sortOrder, Pageable pageable);

    @Find(query = "{$or: [{firstName: ?}, {lastName: ?}]}", sort = "{balance: ?}")
    CompletableFuture<List<Account>> findFiltered3(@RepeatParameter(2) String name, SortOrder sortOrder, Pageable pageable);

    @Find(projection = "{firstName: 1, lastName: 1}")
    CompletableFuture<List<Account>> findProjection3(Pageable pageable);

    @Find(projection = "{firstName: ?, lastName: ?}", sort = "{balance: ?}")
    CompletableFuture<List<Account>> findProjection3(ProjectionType firstName, ProjectionType lastName, SortOrder sortOrder, Pageable pageable);

    // -----------------------------------------------------------------------------------------------------------------

    @Find(query = "{_id: ?}")
    CompletionStage<Account> findById4(long id);

    @Find(query = "{role: ?}")
    CompletionStage<Optional<Account>> findByRole4(Role role);

    @Find(sort = "{balance: -1}", limit = 20, skip = 5, hint = "{balance: 1}")
    CompletionStage<List<Account>> findAll4();

    @Find(sort = "{balance: ?}")
    CompletionStage<List<Account>> findAll4(SortOrder sortOrder);

    @Find(sort = "{balance: ?}")
    CompletionStage<List<Account>> findAll4(SortOrder sortOrder, Pageable pageable);

    @Find(query = "{role: ?}", sort = "{balance: ?}")
    CompletionStage<List<Account>> findFiltered4(Role role, SortOrder sortOrder, Pageable pageable);

    @Find(query = "{$or: [{firstName: ?}, {lastName: ?}]}", sort = "{balance: ?}")
    CompletionStage<List<Account>> findFiltered4(@RepeatParameter(2) String name, SortOrder sortOrder, Pageable pageable);

    @Find(projection = "{firstName: 1, lastName: 1}", hint = "{balance: 1}")
    CompletionStage<List<Account>> findProjection4(Pageable pageable);

    @Find(projection = "{firstName: ?, lastName: ?}", sort = "{balance: ?}", hint = "{balance: 1}")
    CompletionStage<List<Account>> findProjection4(ProjectionType firstName, ProjectionType lastName, SortOrder sortOrder, Pageable pageable);
}
