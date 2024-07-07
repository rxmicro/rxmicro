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

import io.reactivex.rxjava3.core.Single;
import io.rxmicro.data.Pageable;
import io.rxmicro.data.mongo.MongoRepository;
import io.rxmicro.data.mongo.operation.CountDocuments;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@MongoRepository(collection = "collection")
public interface CountDocumentsDataRepository {

    @CountDocuments
    Mono<Long> countDocumentsMono1();

    @CountDocuments(query = "{filter: ?}")
    Mono<Long> countDocumentsMono2(String value, Pageable pageable);

    @CountDocuments(query = "{filter: ?}", skip = 5, limit = 5)
    Mono<Long> countDocumentsMono3(String value);

    @CountDocuments(query = "{filter: ?}", hint = "{filter: 1}", skip = 5, limit = 5)
    Mono<Long> countDocumentsMono4(String value);

    // -----------------------------------------------------------------------------------------------------------------
    @CountDocuments
    Single<Long> countDocumentsSingle1();

    @CountDocuments(query = "{filter: ?}")
    Single<Long> countDocumentsSingle2(String value, Pageable pageable);

    @CountDocuments(query = "{filter: ?}", skip = 5, limit = 5)
    Single<Long> countDocumentsSingle3(String value);

    @CountDocuments(query = "{filter: ?}", hint = "{filter: 1}", skip = 5, limit = 5)
    Single<Long> countDocumentsSingle4(String value);

    // -----------------------------------------------------------------------------------------------------------------
    @CountDocuments
    CompletableFuture<Long> countDocumentsCompletableFuture1();

    @CountDocuments(query = "{filter: ?}")
    CompletableFuture<Long> countDocumentsCompletableFuture2(String value, Pageable pageable);

    @CountDocuments(query = "{filter: ?}", skip = 5, limit = 5)
    CompletableFuture<Long> countDocumentsCompletableFuture3(String value);

    @CountDocuments(query = "{filter: ?}", hint = "{filter: 1}", skip = 5, limit = 5)
    CompletableFuture<Long> countDocumentsCompletableFuture4(String value);

    // -----------------------------------------------------------------------------------------------------------------
    @CountDocuments
    CompletionStage<Long> countDocumentsCompletionStage1();

    @CountDocuments(query = "{filter: ?}")
    CompletionStage<Long> countDocumentsCompletionStage2(String value, Pageable pageable);

    @CountDocuments(query = "{filter: ?}", skip = 5, limit = 5)
    CompletionStage<Long> countDocumentsCompletionStage3(String value);

    @CountDocuments(query = "{filter: ?}", hint = "{filter: 1}", skip = 5, limit = 5)
    CompletionStage<Long> countDocumentsCompletionStage4(String value);
}
