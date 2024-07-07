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

import io.rxmicro.data.mongo.MongoRepository;
import io.rxmicro.data.mongo.operation.Update;
import io.rxmicro.examples.data.mongo.supported.types.model.Status;
import io.rxmicro.examples.data.mongo.supported.types.model.SupportedTypesDocument;
import io.rxmicro.examples.data.mongo.supported.types.model.SupportedTypesEntity;
import org.bson.types.ObjectId;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@MongoRepository(collection = SupportedTypesEntity.COLLECTION_NAME)
public interface UpdateDataRepository {

    @Update
    Mono<Long> update1(SupportedTypesEntity supportedTypesEntity);

    @Update(
            filter = "{ _id: ? }"
    )
    Mono<Long> update2(SupportedTypesDocument supportedTypesDocument, ObjectId id);

    @Update(
            update = "{$set : {" +
                    "status: 'created', " +
                    "aBoolean : true, " +
                    "aByte : NumberInt(127), " +
                    "aShort : NumberInt(500), " +
                    "aInteger : NumberInt(1000), " +
                    "aLong : NumberLong(999999999999), " +
                    "bigDecimal : NumberDecimal('0.3'), " +
                    "character : 'y', " +
                    "string : 'string', " +
                    "instant : ISODate('2020-02-02T02:20:00.000Z'), " +
                    "uuid : BinData(03, 'Ej5FZ+ibEtOkVlVmQkQAAA==')" +
                    "}}",
            filter = "{ _id: ObjectId('507f1f77bcf86cd799439011') }"
    )
    Mono<Long> update3();

    @Update(
            update = "{$set : {" +
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
                    "}}",
            filter = "{ _id: ? }"
    )
    Mono<Long> update4(Status status,
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
                       ObjectId objectId);
}
