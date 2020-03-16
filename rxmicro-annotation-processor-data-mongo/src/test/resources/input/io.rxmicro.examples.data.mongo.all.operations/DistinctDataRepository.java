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
import io.rxmicro.data.mongo.MongoRepository;
import io.rxmicro.data.mongo.operation.Distinct;
import io.rxmicro.examples.data.mongo.all.operations.model.Role;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@MongoRepository(collection = "account")
public interface DistinctDataRepository {

    @Distinct(field = "email", query = "{_id: ?}")
    Mono<String> getEmailById1(long id);

    @Distinct(field = "email", query = "{role: ?}")
    Mono<String> getEmailByRole1(Role role);

    @Distinct(field = "role")
    Flux<Role> getAllUsedRoles1();

    @Distinct(field = "balance")
    Mono<List<BigDecimal>> getAllUsedBalances1();

    // -----------------------------------------------------------------------------------------------------------------

    @Distinct(field = "email", query = "{_id: ?}")
    Single<String> getEmailById2(long id);

    @Distinct(field = "email", query = "{role: ?}")
    Maybe<String> getEmailByRole2(Role role);

    @Distinct(field = "role")
    Flowable<Role> getAllUsedRoles2();

    @Distinct(field = "balance")
    Single<List<BigDecimal>> getAllUsedBalances2();

    // -----------------------------------------------------------------------------------------------------------------

    @Distinct(field = "email", query = "{_id: ?}")
    CompletableFuture<String> getEmailById3(long id);

    @Distinct(field = "email", query = "{role: ?}")
    CompletableFuture<Optional<String>> getEmailByRole3(Role role);

    @Distinct(field = "role")
    CompletableFuture<List<Role>> getAllUsedRoles3();

    @Distinct(field = "balance")
    CompletableFuture<List<BigDecimal>> getAllUsedBalances3();

    // -----------------------------------------------------------------------------------------------------------------

    @Distinct(field = "email", query = "{_id: ?}")
    CompletionStage<String> getEmailById4(long id);

    @Distinct(field = "email", query = "{role: ?}")
    CompletionStage<Optional<String>> getEmailByRole4(Role role);

    @Distinct(field = "role")
    CompletionStage<List<Role>> getAllUsedRoles4();

    @Distinct(field = "balance")
    CompletionStage<List<BigDecimal>> getAllUsedBalances4();
}
