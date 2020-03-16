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

import io.rxmicro.data.mongo.MongoRepository;
import io.rxmicro.data.mongo.operation.Distinct;
import io.rxmicro.data.mongo.operation.Find;
import io.rxmicro.examples.data.mongo.update.model.AccountEntity;
import io.rxmicro.examples.data.mongo.update.model.Role;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;

@MongoRepository(collection = DataRepository.COLLECTION_NAME)
public interface VerificationRepository {

    @Find(query = "{_id: ?}")
    Mono<AccountEntity> findAccountById(long id);

    @Distinct(field = "balance", query = "{_id: ?}")
    Mono<BigDecimal> findBalanceById(long id);

    @Distinct(field = "balance", query = "{role: ?}")
    Mono<List<BigDecimal>> findBalanceByRole(Role role);

    @Distinct(field = "balance")
    Mono<List<BigDecimal>> findAllBalance();
}
