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

package io.rxmicro.examples.processor.all.components;

import io.rxmicro.cdi.Inject;
import io.rxmicro.examples.processor.all.components.component.MongoDataRepository;
import io.rxmicro.examples.processor.all.components.component.PostgreSQLDataRepository;
import io.rxmicro.examples.processor.all.components.component.RestClient;
import io.rxmicro.examples.processor.all.components.model.Account;
import io.rxmicro.rest.method.GET;

import java.util.concurrent.CompletableFuture;

public final class MicroService {

    @Inject
    RestClient restClient;

    @Inject
    PostgreSQLDataRepository postgreSQLDataRepository;

    @Inject
    MongoDataRepository mongoDataRepository;

    @GET("/rest-client")
    CompletableFuture<Account> fromRestClient() {
        return restClient.get();
    }

    @GET("/postgresql")
    CompletableFuture<Account> fromPostgreSQL() {
        return postgreSQLDataRepository.get();
    }

    @GET("/mongo")
    CompletableFuture<Account> fromMongo() {
        return mongoDataRepository.get();
    }
}
