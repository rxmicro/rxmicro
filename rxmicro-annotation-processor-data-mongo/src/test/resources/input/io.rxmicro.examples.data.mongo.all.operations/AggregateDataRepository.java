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
import io.rxmicro.data.mongo.operation.Aggregate;
import io.rxmicro.examples.data.mongo.all.operations.model.Report;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@MongoRepository(collection = "account")
public interface AggregateDataRepository {

    @Aggregate(pipeline = {
            "{ $group : { _id: '$role', total : { $sum: '$balance'}} }",
            "{ $sort: { total: -1, _id: -1} }"
    })
    Flux<Report> aggregate1();

    @Aggregate(pipeline = {
            "{ $group : { _id: '$role', total : { $sum: '$balance'}} }",
            "{ $sort: { total: ?, _id: ?} }"
    })
    Flux<Report> aggregate1(@RepeatParameter(2) SortOrder sortOrder);

    @Aggregate(pipeline = {
            "{ $group : { _id: '$role', total : { $sum: '$balance'}} }",
            "{ $sort: { total: ?, _id: ?} }"
    })
    Mono<List<Report>> aggregate1(SortOrder totalSortOrder, SortOrder idSortOrder);

    @Aggregate(pipeline = {
            "{ $group : { _id: '$role', total : { $sum: '?'}} }",
            "{ $sort: { total: ?, _id: ?} }"
    })
    Mono<List<Report>> aggregate1(String field, SortOrder totalSortOrder, SortOrder idSortOrder);

    @Aggregate(pipeline = {
            "{ $group : { _id: '$role', total : { $sum: '?'}} }",
            "{ $sort: { total: ?, _id: ?} }",
            "{ $limit : ? }"
    })
    Mono<Report> aggregate1(String field, SortOrder totalSortOrder, SortOrder idSortOrder, int limit);

    @Aggregate(
            allowDiskUse = true,
            hint = "{balance: 1}",
            pipeline = {
                    "{ $group : { _id: '$role', total : { $sum: '?'}} }",
                    "{ $sort: { total: ?, _id: ?} }",
                    "{ $limit : ? }",
                    "{ $skip : ? }"
            })
    Mono<Report> aggregate1(String field, SortOrder totalSortOrder, SortOrder idSortOrder, int limit, int skip);

    @Aggregate(pipeline = {
            "{ $group : { _id: '$role', total : { $sum: '?'}} }",
            "{ $sort: { total: ?, _id: ?} }",
            "{ $limit : ? }",
            "{ $skip : ? }"
    })
    Mono<Report> aggregate1(String field, SortOrder totalSortOrder, SortOrder idSortOrder, Pageable pageable);

    // -----------------------------------------------------------------------------------------------------------------

    @Aggregate(pipeline = {
            "{ $group : { _id: '$role', total : { $sum: '$balance'}} }",
            "{ $sort: { total: -1, _id: -1} }"
    })
    Flowable<Report> aggregate2();

    @Aggregate(pipeline = {
            "{ $group : { _id: '$role', total : { $sum: '$balance'}} }",
            "{ $sort: { total: ?, _id: ?} }"
    })
    Flowable<Report> aggregate2(@RepeatParameter(2) SortOrder sortOrder);

    @Aggregate(pipeline = {
            "{ $group : { _id: '$role', total : { $sum: '$balance'}} }",
            "{ $sort: { total: ?, _id: ?} }"
    })
    Single<List<Report>> aggregate2(SortOrder totalSortOrder, SortOrder idSortOrder);

    @Aggregate(pipeline = {
            "{ $group : { _id: '$role', total : { $sum: '?'}} }",
            "{ $sort: { total: ?, _id: ?} }"
    })
    Single<List<Report>> aggregate2(String field, SortOrder totalSortOrder, SortOrder idSortOrder);

    @Aggregate(pipeline = {
            "{ $group : { _id: '$role', total : { $sum: '?'}} }",
            "{ $sort: { total: ?, _id: ?} }",
            "{ $limit : ? }"
    })
    Single<Report> aggregate2(String field, SortOrder totalSortOrder, SortOrder idSortOrder, int limit);

    @Aggregate(
            allowDiskUse = true,
            hint = "{balance: 1}",
            pipeline = {
                    "{ $group : { _id: '$role', total : { $sum: '?'}} }",
                    "{ $sort: { total: ?, _id: ?} }",
                    "{ $limit : ? }",
                    "{ $skip : ? }"
            })
    Maybe<Report> aggregate2(String field, SortOrder totalSortOrder, SortOrder idSortOrder, int limit, int skip);

    @Aggregate(pipeline = {
            "{ $group : { _id: '$role', total : { $sum: '?'}} }",
            "{ $sort: { total: ?, _id: ?} }",
            "{ $limit : ? }",
            "{ $skip : ? }"
    })
    Maybe<Report> aggregate2(String field, SortOrder totalSortOrder, SortOrder idSortOrder, Pageable pageable);

    // -----------------------------------------------------------------------------------------------------------------

    @Aggregate(pipeline = {
            "{ $group : { _id: '$role', total : { $sum: '$balance'}} }",
            "{ $sort: { total: -1, _id: -1} }"
    })
    CompletableFuture<List<Report>> aggregate3();

    @Aggregate(pipeline = {
            "{ $group : { _id: '$role', total : { $sum: '$balance'}} }",
            "{ $sort: { total: ?, _id: ?} }"
    })
    CompletableFuture<List<Report>> aggregate3(@RepeatParameter(2) SortOrder sortOrder);

    @Aggregate(pipeline = {
            "{ $group : { _id: '$role', total : { $sum: '$balance'}} }",
            "{ $sort: { total: ?, _id: ?} }"
    })
    CompletableFuture<List<Report>> aggregate3(SortOrder totalSortOrder, SortOrder idSortOrder);

    @Aggregate(pipeline = {
            "{ $group : { _id: '$role', total : { $sum: '?'}} }",
            "{ $sort: { total: ?, _id: ?} }"
    })
    CompletableFuture<List<Report>> aggregate3(String field, SortOrder totalSortOrder, SortOrder idSortOrder);

    @Aggregate(pipeline = {
            "{ $group : { _id: '$role', total : { $sum: '?'}} }",
            "{ $sort: { total: ?, _id: ?} }",
            "{ $limit : ? }"
    })
    CompletableFuture<Report> aggregate3(String field, SortOrder totalSortOrder, SortOrder idSortOrder, int limit);

    @Aggregate(
            allowDiskUse = true,
            hint = "{balance: 1}",
            pipeline = {
                    "{ $group : { _id: '$role', total : { $sum: '?'}} }",
                    "{ $sort: { tal: ?, _id: ?} }",
                    "{ $limit : ? }",
                    "{ $skip : ? }"
            })
    CompletableFuture<Optional<Report>> aggregate3(String field, SortOrder totalSortOrder, SortOrder idSortOrder, int limit, int skip);

    @Aggregate(pipeline = {
            "{ $group : { _id: '$role', total : { $sum: '?'}} }",
            "{ $sort: { total: ?, _id: ?} }",
            "{ $limit : ? }",
            "{ $skip : ? }"
    })
    CompletableFuture<Optional<Report>> aggregate3(String field, SortOrder totalSortOrder, SortOrder idSortOrder, Pageable pageable);

    // -----------------------------------------------------------------------------------------------------------------

    @Aggregate(pipeline = {
            "{ $group : { _id: '$role', total : { $sum: '$balance'}} }",
            "{ $sort: { total: -1, _id: -1} }"
    })
    CompletionStage<List<Report>> aggregate4();

    @Aggregate(pipeline = {
            "{ $group : { _id: '$role', total : { $sum: '$balance'}} }",
            "{ $sort: { total: ?, _id: ?} }"
    })
    CompletionStage<List<Report>> aggregate4(@RepeatParameter(2) SortOrder sortOrder);

    @Aggregate(pipeline = {
            "{ $group : { _id: '$role', total : { $sum: '$balance'}} }",
            "{ $sort: { total: ?, _id: ?} }"
    })
    CompletionStage<List<Report>> aggregate4(SortOrder totalSortOrder, SortOrder idSortOrder);

    @Aggregate(pipeline = {
            "{ $group : { _id: '$role', total : { $sum: '?'}} }",
            "{ $sort: { total: ?, _id: ?} }"
    })
    CompletionStage<List<Report>> aggregate4(String field, SortOrder totalSortOrder, SortOrder idSortOrder);

    @Aggregate(pipeline = {
            "{ $group : { _id: '$role', total : { $sum: '?'}} }",
            "{ $sort: { total: ?, _id: ?} }",
            "{ $limit : ? }"
    })
    CompletionStage<Report> aggregate4(String field, SortOrder totalSortOrder, SortOrder idSortOrder, int limit);

    @Aggregate(
            allowDiskUse = true,
            hint = "{balance: 1}",
            pipeline = {
                    "{ $group : { _id: '$role', total : { $sum: '?'}} }",
                    "{ $sort: { total: ?, _id: ?} }",
                    "{ $limit : ? }",
                    "{ $skip : ? }"
            })
    CompletionStage<Optional<Report>> aggregate4(String field, SortOrder totalSortOrder, SortOrder idSortOrder, int limit, int skip);

    @Aggregate(pipeline = {
            "{ $group : { _id: '$role', total : { $sum: '?'}} }",
            "{ $sort: { total: ?, _id: ?} }",
            "{ $limit : ? }",
            "{ $skip : ? }"
    })
    CompletionStage<Optional<Report>> aggregate4(String field, SortOrder totalSortOrder, SortOrder idSortOrder, Pageable pageable);
}
