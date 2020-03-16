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

import com.mongodb.client.result.DeleteResult;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.rxmicro.data.RepeatParameter;
import io.rxmicro.data.mongo.MongoRepository;
import io.rxmicro.data.mongo.operation.Delete;
import io.rxmicro.examples.data.mongo.all.operations.model.Account;
import io.rxmicro.examples.data.mongo.all.operations.model.Role;
import org.bson.types.ObjectId;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@MongoRepository(collection = "collection")
public interface DeleteDataRepository {

    @Delete
    Mono<Void> delete1(ObjectId id);

    @Delete
    Mono<Boolean> delete2(Role id);

    @Delete
    Mono<Long> delete3(UUID id);

    @Delete
    Mono<DeleteResult> delete4(Byte id);

    // -----------------------------------------------------------------------------------------------------------------

    @Delete
    Completable delete5(Short id);

    @Delete
    Single<Boolean> delete6(Integer id);

    @Delete
    Single<Long> delete7(Long id);

    @Delete
    Single<DeleteResult> delete8(Float id);

    // -----------------------------------------------------------------------------------------------------------------

    @Delete
    CompletionStage<Void> delete9(Double id);

    @Delete
    CompletionStage<Boolean> delete10(BigDecimal id);

    @Delete
    CompletionStage<Long> delete11(Character id);

    @Delete
    CompletionStage<DeleteResult> delete12(String id);

    // -----------------------------------------------------------------------------------------------------------------

    @Delete
    CompletableFuture<Void> delete13(Instant id);

    @Delete
    CompletableFuture<Boolean> delete14(LocalDate id);

    @Delete
    CompletableFuture<Long> delete15(LocalDateTime id);

    @Delete
    CompletableFuture<DeleteResult> delete16(LocalTime id);

    // -----------------------------------------------------------------------------------------------------------------

    @Delete
    Mono<Boolean> delete17(Account account);

    // -----------------------------------------------------------------------------------------------------------------

    @Delete(filter = "{email: ?}")
    Mono<Boolean> delete18(String email);

    @Delete(filter = "{role: ?}")
    Mono<Long> delete19(Role role);

    @Delete(filter = "{$or: [ {balance: ?}, {balance: ?}, {balance: ?}, {balance: ?}]}")
    Mono<DeleteResult> delete20(@RepeatParameter(4) BigDecimal balance);
}
