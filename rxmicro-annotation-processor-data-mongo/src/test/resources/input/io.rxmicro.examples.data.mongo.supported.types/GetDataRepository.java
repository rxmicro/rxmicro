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

package io.rxmicro.examples.data.mongo.supported.types;

import io.rxmicro.data.Pageable;
import io.rxmicro.data.SortOrder;
import io.rxmicro.data.mongo.IndexUsage;
import io.rxmicro.data.mongo.MongoRepository;
import io.rxmicro.data.mongo.ProjectionType;
import io.rxmicro.data.mongo.operation.Aggregate;
import io.rxmicro.data.mongo.operation.CountDocuments;
import io.rxmicro.data.mongo.operation.Distinct;
import io.rxmicro.data.mongo.operation.EstimatedDocumentCount;
import io.rxmicro.data.mongo.operation.Find;
import io.rxmicro.examples.data.mongo.supported.types.model.Status;
import io.rxmicro.examples.data.mongo.supported.types.model.SupportedTypesEntity;
import org.bson.types.ObjectId;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@MongoRepository(collection = SupportedTypesEntity.COLLECTION_NAME)
public interface GetDataRepository {

    @Aggregate(
            pipeline = {
                    "{ $match: { " +
                            "_id: ObjectId('507f1f77bcf86cd799439011'), " +
                            "status: 'created', " +
                            "aBoolean : true, " +
                            "aByte : NumberInt(127), " +
                            "aShort : NumberInt(500), " +
                            "aInteger : NumberInt(1000), " +
                            "aLong : NumberLong(999999999999), " +
                            "bigDecimal : NumberDecimal('3.14'), " +
                            "character : 'y', " +
                            "string : 'string', " +
                            "instant : ISODate('2020-02-02T02:20:00.000Z'), " +
                            "uuid : BinData(03, 'Ej5FZ+ibEtOkVlVmQkQAAA==')" +
                            "} }",
                    "{ $sort: { status: 1, _id: -1 } }",
                    "{ $limit: 10 }",
                    "{ $skip: 0 }"
            },
            hint = "{ _id: 1 }"
    )
    Mono<SupportedTypesEntity> aggregate1();

    @Aggregate(
            pipeline = {
                    "{ $match: { " +
                            "_id: ?, " +
                            "status: ?, " +
                            "aBoolean : ?, " +
                            "aByte : ?, " +
                            "aShort : ?, " +
                            "aInteger : ?, " +
                            "aLong : ?, " +
                            "bigDecimal : ?, " +
                            "character : ?, " +
                            "string : ?, " +
                            "instant : ?, " +
                            "uuid : ?" +
                            "} }",
                    "{ $sort: { status: ?, _id: ? } }",
                    "{ $limit: ? }",
                    "{ $skip: ? }"
            },
            hint = "{ _id: ? }"
    )
    Mono<SupportedTypesEntity> aggregate2(ObjectId objectId,
                                          Status status,
                                          boolean aBoolean,
                                          byte aByte,
                                          short aShort,
                                          int aInteger,
                                          long aLong,
                                          BigDecimal bigDecimal,
                                          Character character,
                                          String string,
                                          Instant instant,
                                          UUID uuid,
                                          SortOrder statusSortOrder,
                                          SortOrder idSortOrder,
                                          int limit,
                                          int skip,
                                          IndexUsage indexUsage);

    @Aggregate(
            pipeline = {
                    "{ $match: { " +
                            "_id: ObjectId('507f1f77bcf86cd799439011'), " +
                            "status: 'created', " +
                            "aBoolean : true, " +
                            "aByte : NumberInt(127), " +
                            "aShort : NumberInt(500), " +
                            "aInteger : NumberInt(1000), " +
                            "aLong : NumberLong(999999999999), " +
                            "bigDecimal : NumberDecimal('3.14'), " +
                            "character : 'y', " +
                            "string : 'string', " +
                            "instant : ISODate('2020-02-02T02:20:00.000Z'), " +
                            "uuid : BinData(03, 'Ej5FZ+ibEtOkVlVmQkQAAA==')" +
                            "} }",
                    "{ $sort: { status: 1, _id: -1 } }",
                    "{ $limit: ? }",
                    "{ $skip: ? }"
            }
    )
    Mono<SupportedTypesEntity> aggregate3(Pageable pageable);

    // -----------------------------------------------------------------------------------------------------------------

    @Find(
            query = "{ " +
                    "_id: ObjectId('507f1f77bcf86cd799439011'), " +
                    "status: 'created', " +
                    "aBoolean : true, " +
                    "aByte : NumberInt(127), " +
                    "aShort : NumberInt(500), " +
                    "aInteger : NumberInt(1000), " +
                    "aLong : NumberLong(999999999999), " +
                    "bigDecimal : NumberDecimal('3.14'), " +
                    "character : 'y', " +
                    "string : 'string', " +
                    "instant : ISODate('2020-02-02T02:20:00.000Z'), " +
                    "uuid : BinData(03, 'Ej5FZ+ibEtOkVlVmQkQAAA==')" +
                    "}",
            sort = "{ status: 1, _id: -1 }",
            hint = "{ _id: 1 }",
            limit = 10,
            skip = 0
    )
    Mono<SupportedTypesEntity> find1();

    @Find(
            query = "{ " +
                    "_id: ?, " +
                    "status: ?, " +
                    "aBoolean : ?, " +
                    "aByte : ?, " +
                    "aShort : ?, " +
                    "aInteger : ?, " +
                    "aLong : ?, " +
                    "bigDecimal : ?, " +
                    "character : ?, " +
                    "string : ?, " +
                    "instant : ?, " +
                    "uuid : ?" +
                    "}",
            hint = "{ _id: ? }",
            sort = "{ status: ?, _id: ? }"
    )
    Mono<SupportedTypesEntity> find2(ObjectId objectId,
                                     Status status,
                                     boolean aBoolean,
                                     byte aByte,
                                     short aShort,
                                     int aInteger,
                                     long aLong,
                                     BigDecimal bigDecimal,
                                     Character character,
                                     String string,
                                     Instant instant,
                                     UUID uuid,
                                     IndexUsage indexUsage,
                                     SortOrder statusSortOrder,
                                     SortOrder idSortOrder,
                                     int limit,
                                     int skip);

    @Find(
            query = "{ " +
                    "_id: ObjectId('507f1f77bcf86cd799439011'), " +
                    "status: 'created', " +
                    "aBoolean : true, " +
                    "aByte : NumberInt(127), " +
                    "aShort : NumberInt(500), " +
                    "aInteger : NumberInt(1000), " +
                    "aLong : NumberLong(999999999999), " +
                    "bigDecimal : NumberDecimal('3.14'), " +
                    "character : 'y', " +
                    "string : 'string', " +
                    "instant : ISODate('2020-02-02T02:20:00.000Z'), " +
                    "uuid : BinData(03, 'Ej5FZ+ibEtOkVlVmQkQAAA==')" +
                    "}",
            sort = "{ status: 1, _id: -1 }"
    )
    Mono<SupportedTypesEntity> find3(int skip, int limit);

    @Find(
            query = "{ " +
                    "_id: ObjectId('507f1f77bcf86cd799439011'), " +
                    "status: 'created', " +
                    "aBoolean : true, " +
                    "aByte : NumberInt(127), " +
                    "aShort : NumberInt(500), " +
                    "aInteger : NumberInt(1000), " +
                    "aLong : NumberLong(999999999999), " +
                    "bigDecimal : NumberDecimal('3.14'), " +
                    "character : 'y', " +
                    "string : 'string', " +
                    "instant : ISODate('2020-02-02T02:20:00.000Z'), " +
                    "uuid : BinData(03, 'Ej5FZ+ibEtOkVlVmQkQAAA==')" +
                    "}",
            sort = "{ status: 1, _id: -1 }"
    )
    Mono<SupportedTypesEntity> find4(Pageable pageable);

    @Find(
            query = "{ " +
                    "_id: ?, " +
                    "status: ?, " +
                    "aBoolean : ?, " +
                    "aByte : ?, " +
                    "aShort : ?, " +
                    "aInteger : ?, " +
                    "aLong : ?, " +
                    "bigDecimal : ?, " +
                    "character : ?, " +
                    "string : ?, " +
                    "instant : ?, " +
                    "uuid : ?" +
                    "}",
            projection = "{ bigDecimal: ? }",
            hint = "{ _id: ? }",
            sort = "{ status: ?, _id: ? }"
    )
    Mono<SupportedTypesEntity> find5(ObjectId objectId,
                                     Status status,
                                     boolean aBoolean,
                                     byte aByte,
                                     short aShort,
                                     int aInteger,
                                     long aLong,
                                     BigDecimal bigDecimal,
                                     Character character,
                                     String string,
                                     Instant instant,
                                     UUID uuid,
                                     ProjectionType bigDecimalProjectionType,
                                     IndexUsage indexUsage,
                                     SortOrder statusSortOrder,
                                     SortOrder idSortOrder,
                                     int limit,
                                     int skip);

    // -----------------------------------------------------------------------------------------------------------------

    @CountDocuments
    Mono<Long> countDocuments1();

    @CountDocuments(
            query = "{ " +
                    "_id: ObjectId('507f1f77bcf86cd799439011'), " +
                    "status: 'created', " +
                    "aBoolean : true, " +
                    "aByte : NumberInt(127), " +
                    "aShort : NumberInt(500), " +
                    "aInteger : NumberInt(1000), " +
                    "aLong : NumberLong(999999999999), " +
                    "bigDecimal : NumberDecimal('3.14'), " +
                    "character : 'y', " +
                    "string : 'string', " +
                    "instant : ISODate('2020-02-02T02:20:00.000Z'), " +
                    "uuid : BinData(03, 'Ej5FZ+ibEtOkVlVmQkQAAA==')" +
                    "}",
            hint = "{ _id: 1 }",
            limit = 10,
            skip = 0
    )
    Mono<Long> countDocuments2();

    @CountDocuments(
            hint = "{ _id: 1 }"
    )
    Mono<Long> countDocuments3(int skip, int limit);

    @CountDocuments(
            query = "{ " +
                    "_id: ObjectId('507f1f77bcf86cd799439011'), " +
                    "status: 'created', " +
                    "aBoolean : true, " +
                    "aByte : NumberInt(127), " +
                    "aShort : NumberInt(500), " +
                    "aInteger : NumberInt(1000), " +
                    "aLong : NumberLong(999999999999), " +
                    "bigDecimal : NumberDecimal('3.14'), " +
                    "character : 'y', " +
                    "string : 'string', " +
                    "instant : ISODate('2020-02-02T02:20:00.000Z'), " +
                    "uuid : BinData(03, 'Ej5FZ+ibEtOkVlVmQkQAAA==')" +
                    "}",
            hint = "{ _id: 1 }"
    )
    Mono<Long> countDocuments4(Pageable pageable);

    @CountDocuments(
            query = "{ " +
                    "_id: ?, " +
                    "status: ?, " +
                    "aBoolean : ?, " +
                    "aByte : ?, " +
                    "aShort : ?, " +
                    "aInteger : ?, " +
                    "aLong : ?, " +
                    "bigDecimal : ?, " +
                    "character : ?, " +
                    "string : ?, " +
                    "instant : ?, " +
                    "uuid : ?" +
                    "}",
            hint = "{ _id: ? }"
    )
    Mono<Long> countDocuments5(ObjectId objectId,
                               Status status,
                               boolean aBoolean,
                               byte aByte,
                               short aShort,
                               int aInteger,
                               long aLong,
                               BigDecimal bigDecimal,
                               Character character,
                               String string,
                               Instant instant,
                               UUID uuid,
                               IndexUsage indexUsage,
                               int limit,
                               int skip);

    // -----------------------------------------------------------------------------------------------------------------

    @EstimatedDocumentCount
    Mono<Long> estimatedDocumentCount();

    // -----------------------------------------------------------------------------------------------------------------

    @Distinct(
            field = "bigDecimal"
    )
    Mono<BigDecimal> distinct1();

    @Distinct(
            field = "bigDecimal",
            query = "{ " +
                    "_id: ObjectId('507f1f77bcf86cd799439011'), " +
                    "status: 'created', " +
                    "aBoolean : true, " +
                    "aByte : NumberInt(127), " +
                    "aShort : NumberInt(500), " +
                    "aInteger : NumberInt(1000), " +
                    "aLong : NumberLong(999999999999), " +
                    "bigDecimal : NumberDecimal('3.14'), " +
                    "character : 'y', " +
                    "string : 'string', " +
                    "instant : ISODate('2020-02-02T02:20:00.000Z'), " +
                    "uuid : BinData(03, 'Ej5FZ+ibEtOkVlVmQkQAAA==')" +
                    "}"
    )
    Mono<BigDecimal> distinct2();

    @Distinct(
            field = "bigDecimal",
            query = "{ " +
                    "_id: ?, " +
                    "status: ?, " +
                    "aBoolean : ?, " +
                    "aByte : ?, " +
                    "aShort : ?, " +
                    "aInteger : ?, " +
                    "aLong : ?, " +
                    "bigDecimal : ?, " +
                    "character : ?, " +
                    "string : ?, " +
                    "instant : ?, " +
                    "uuid : ?" +
                    "}"
    )
    Mono<BigDecimal> distinct3(ObjectId objectId,
                               Status status,
                               boolean aBoolean,
                               byte aByte,
                               short aShort,
                               int aInteger,
                               long aLong,
                               BigDecimal bigDecimal,
                               Character character,
                               String string,
                               Instant instant,
                               UUID uuid);
}
