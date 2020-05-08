/*
 * Copyright (c) 2020 https://rxmicro.io
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

package io.rxmicro.examples.cdi.all.rxmicro.components.jee;

import com.mongodb.reactivestreams.client.MongoClient;
import io.r2dbc.pool.ConnectionPool;
import io.r2dbc.spi.ConnectionFactory;
import io.rxmicro.cdi.Inject;
import io.rxmicro.cdi.Named;
import io.rxmicro.cdi.PostConstruct;
import io.rxmicro.data.mongo.MongoConfig;
import io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLConfig;
import io.rxmicro.examples.cdi.all.rxmicro.components.CustomPostgreSQLNamedConfig;
import io.rxmicro.examples.cdi.all.rxmicro.components.component.MongoRepository;
import io.rxmicro.examples.cdi.all.rxmicro.components.component.PostgreSQLRepository;
import io.rxmicro.examples.cdi.all.rxmicro.components.component.RestClient;
import io.rxmicro.http.client.HttpClientConfig;
import io.rxmicro.rest.method.GET;
import io.rxmicro.rest.server.HttpServerConfig;
import io.rxmicro.rest.server.RestServerConfig;
import io.rxmicro.rest.server.netty.NettyRestServerConfig;

@SuppressWarnings("EmptyMethod")
public final class InternalTypesJEEStyleFieldInjectionRestController {

    @Inject
    MongoRepository mongoRepository;

    @Inject
    MongoConfig mongoConfig;

    @Inject
    @Named("custom-mongo")
    MongoConfig customMongoConfig;

    @Inject
    MongoClient mongoClient;

    @Inject
    @Named("custom-mongo")
    MongoClient customMongoClient;

    @Inject
    PostgreSQLRepository postgreSQLRepository;

    @Inject
    PostgreSQLConfig postgreSQLConfig;

    @Inject
    @CustomPostgreSQLNamedConfig
    PostgreSQLConfig customPostgreSQLConfig;

    @Inject
    ConnectionFactory connectionFactory;

    @Inject
    ConnectionPool connectionPool;

    @Inject
    @CustomPostgreSQLNamedConfig
    ConnectionFactory customConnectionFactory;

    @Inject
    @CustomPostgreSQLNamedConfig
    ConnectionPool customConnectionPool;

    @Inject
    RestClient restClient;

    @Inject
    HttpClientConfig httpClientConfig;

    @Inject
    @Named("custom-http-client")
    HttpClientConfig customHttpClientConfig;

    @Inject
    HttpServerConfig httpServerConfig;

    @Inject
    RestServerConfig restServerConfig;

    @Inject
    NettyRestServerConfig nettyRestServerConfig;

    @PostConstruct
    void postConstruct() {

    }

    @GET("/jee/fields")
    void test() {

    }

}
