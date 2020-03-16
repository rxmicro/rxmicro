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

import com.mongodb.client.result.UpdateResult;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.rxmicro.data.mongo.MongoRepository;
import io.rxmicro.data.mongo.operation.Update;
import io.rxmicro.examples.data.mongo.all.operations.model.Account;
import io.rxmicro.examples.data.mongo.all.operations.model.AccountDocument;
import io.rxmicro.examples.data.mongo.all.operations.model.Role;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@MongoRepository(collection = UpdateDataRepository.COLLECTION_NAME)
public interface UpdateDataRepository {

    String COLLECTION_NAME = "account";

    @Update
    Mono<Void> updateEntity1(Account account);

    @Update(filter = "{_id: ?}")
    Mono<Boolean> updateDocument1(AccountDocument accountDocument, long id);

    @Update(update = "{$set: {balance: ?}}", filter = "{_id: ?}")
    Mono<Long> updateById1(BigDecimal balance, long id);

    @Update(update = "{$set: {balance: ?}}", filter = "{role: ?}")
    Mono<UpdateResult> updateByRole1(BigDecimal balance, Role role);

    // -----------------------------------------------------------------------------------------------------------------

    @Update
    Completable updateEntity2(Account account);

    @Update(filter = "{_id: ?}")
    Single<Boolean> updateDocument2(AccountDocument accountDocument, long id);

    @Update(update = "{$set: {balance: ?}}", filter = "{_id: ?}")
    Single<Long> updateById2(BigDecimal balance, long id);

    @Update(update = "{$set: {balance: ?}}", filter = "{role: ?}")
    Single<UpdateResult> updateByRole2(BigDecimal balance, Role role);

    // -----------------------------------------------------------------------------------------------------------------

    @Update
    CompletableFuture<Void> updateEntity3(Account account);

    @Update(filter = "{_id: ?}")
    CompletableFuture<Boolean> updateDocument3(AccountDocument accountDocument, long id);

    @Update(update = "{$set: {balance: ?}}", filter = "{_id: ?}")
    CompletableFuture<Long> updateById3(BigDecimal balance, long id);

    @Update(update = "{$set: {balance: ?}}", filter = "{role: ?}")
    CompletableFuture<UpdateResult> updateByRole3(BigDecimal balance, Role role);

    // -----------------------------------------------------------------------------------------------------------------

    @Update
    CompletionStage<Void> updateEntity4(Account account);

    @Update(filter = "{_id: ?}")
    CompletionStage<Boolean> updateDocument4(AccountDocument accountDocument, long id);

    @Update(update = "{$set: {balance: ?}}", filter = "{_id: ?}")
    CompletionStage<Long> updateById4(BigDecimal balance, long id);

    @Update(update = "{$set: {balance: ?}}", filter = "{role: ?}")
    CompletionStage<UpdateResult> updateByRole4(BigDecimal balance, Role role);
}
