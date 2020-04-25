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

/**
 * @author nedis
 * @link https://rxmicro.io
 */
@SuppressWarnings({"FieldCanBeLocal", "EmptyMethod"})
public final class InternalTypesJEEStyleConstructorInjectionRestController {

    private final MongoRepository mongoRepository;

    private final MongoConfig mongoConfig;

    private final MongoConfig customMongoConfig;

    private final MongoClient mongoClient;

    private final MongoClient customMongoClient;

    private final PostgreSQLRepository postgreSQLRepository;

    private final PostgreSQLConfig postgreSQLConfig;

    private final PostgreSQLConfig customPostgreSQLConfig;

    private final ConnectionFactory connectionFactory;

    private final ConnectionPool connectionPool;

    private final ConnectionFactory customConnectionFactory;

    private final ConnectionPool customConnectionPool;

    private final RestClient restClient;

    private final HttpClientConfig httpClientConfig;

    private final HttpClientConfig customHttpClientConfig;

    private final HttpServerConfig httpServerConfig;

    private final RestServerConfig restServerConfig;

    private final NettyRestServerConfig nettyRestServerConfig;

    @Inject
    public InternalTypesJEEStyleConstructorInjectionRestController(final MongoRepository mongoRepository,
                                                                   final MongoConfig mongoConfig,
                                                                   @Named("custom-mongo") final MongoConfig customMongoConfig,
                                                                   final MongoClient mongoClient,
                                                                   @Named("custom-mongo") final MongoClient customMongoClient,
                                                                   final PostgreSQLRepository postgreSQLRepository,
                                                                   final PostgreSQLConfig postgreSQLConfig,
                                                                   @CustomPostgreSQLNamedConfig final PostgreSQLConfig customPostgreSQLConfig,
                                                                   final ConnectionFactory connectionFactory,
                                                                   final ConnectionPool connectionPool,
                                                                   @CustomPostgreSQLNamedConfig final ConnectionFactory customConnectionFactory,
                                                                   @CustomPostgreSQLNamedConfig final ConnectionPool customConnectionPool,
                                                                   final RestClient restClient,
                                                                   final HttpClientConfig httpClientConfig,
                                                                   @Named("custom-http-client") final HttpClientConfig customHttpClientConfig,
                                                                   final HttpServerConfig httpServerConfig,
                                                                   final RestServerConfig restServerConfig,
                                                                   final NettyRestServerConfig nettyRestServerConfig) {
        this.mongoRepository = mongoRepository;
        this.mongoConfig = mongoConfig;
        this.customMongoConfig = customMongoConfig;
        this.mongoClient = mongoClient;
        this.customMongoClient = customMongoClient;
        this.postgreSQLRepository = postgreSQLRepository;
        this.postgreSQLConfig = postgreSQLConfig;
        this.customPostgreSQLConfig = customPostgreSQLConfig;
        this.connectionFactory = connectionFactory;
        this.connectionPool = connectionPool;
        this.customConnectionFactory = customConnectionFactory;
        this.customConnectionPool = customConnectionPool;
        this.restClient = restClient;
        this.httpClientConfig = httpClientConfig;
        this.customHttpClientConfig = customHttpClientConfig;
        this.httpServerConfig = httpServerConfig;
        this.restServerConfig = restServerConfig;
        this.nettyRestServerConfig = nettyRestServerConfig;
    }

    @PostConstruct
    void postConstruct() {

    }

    @GET("/jee/constructor")
    void test() {

    }

}
