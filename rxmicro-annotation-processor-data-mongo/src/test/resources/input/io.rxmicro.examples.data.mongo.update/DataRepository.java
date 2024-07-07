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

package io.rxmicro.examples.data.mongo.update;

import com.mongodb.client.result.UpdateResult;
import io.rxmicro.data.mongo.MongoRepository;
import io.rxmicro.data.mongo.operation.Update;
import io.rxmicro.examples.data.mongo.update.model.AccountDocument;
import io.rxmicro.examples.data.mongo.update.model.AccountEntity;
import io.rxmicro.examples.data.mongo.update.model.Role;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@MongoRepository(collection = DataRepository.COLLECTION_NAME)
public interface DataRepository {

    String COLLECTION_NAME = "account";

    // tag::content[]
    @Update
    Mono<Boolean> updateEntity(AccountEntity accountEntity);

    @Update(filter = "{_id: ?}")
    Mono<Void> updateDocument(AccountDocument accountDocument, long id);

    @Update(update = "{$set: {balance: ?}}", filter = "{_id: ?}")
    Mono<Long> updateById(BigDecimal balance, long id);

    @Update(update = "{$set: {balance: ?}}", filter = "{role: ?}")
    Mono<UpdateResult> updateByRole(BigDecimal balance, Role role);

    @Update(update = "{$set: {balance: ?}}")
    Mono<UpdateResult> updateAll(BigDecimal balance);
    // end::content[]
}
