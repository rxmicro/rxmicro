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

package io.rxmicro.examples.data.mongo.find;

import io.rxmicro.data.Pageable;
import io.rxmicro.data.RepeatParameter;
import io.rxmicro.data.SortOrder;
import io.rxmicro.data.mongo.MongoRepository;
import io.rxmicro.data.mongo.operation.Find;
import io.rxmicro.examples.data.mongo.find.model.Account;
import io.rxmicro.examples.data.mongo.find.model.Role;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@MongoRepository(collection = DataRepository.COLLECTION_NAME)
public interface DataRepository {

    String COLLECTION_NAME = "account";

    // tag::content[]
    @Find(query = "{_id: ?}")
    Mono<Account> findById(long id);

    @Find(query = "{_id: ?}", projection = "{firstName: 1, lastName: 1}")
    Mono<Account> findWithProjectionById(long id);

    @Find(query = "{role: ?}", sort = "{role: 1, balance: 1}")
    Flux<Account> findByRole(Role role, Pageable pageable);

    @Find(query = "{role: ?}", sort = "{role: ?, balance: ?}")
    Flux<Account> findByRole(Role role, @RepeatParameter(2) SortOrder sortOrder, Pageable pageable);
    // end::content[]
}
