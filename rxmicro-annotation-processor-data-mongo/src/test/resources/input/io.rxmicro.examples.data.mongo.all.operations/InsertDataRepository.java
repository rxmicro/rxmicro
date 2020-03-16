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

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.rxmicro.data.mongo.MongoRepository;
import io.rxmicro.data.mongo.operation.Insert;
import io.rxmicro.examples.data.mongo.all.operations.model.Account;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

@MongoRepository(collection = "collection")
public interface InsertDataRepository {

    @Insert
    Mono<Account> insert1(Account account);

    @Insert
    Mono<Void> insert2(Account account);

    @Insert
    Single<Account> insert3(Account account);

    @Insert
    Completable insert4(Account account);

    @Insert
    CompletableFuture<Account> insert5(Account account);

    @Insert
    CompletableFuture<Void> insert6(Account account);
}
