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

package io.rxmicro.examples.cdi.all.rxmicro.components.spring;

import com.mongodb.reactivestreams.client.MongoClient;
import io.r2dbc.pool.ConnectionPool;
import io.r2dbc.spi.ConnectionFactory;
import io.rxmicro.cdi.Autowired;
import io.rxmicro.cdi.PostConstruct;
import io.rxmicro.cdi.Qualifier;
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
public final class InternalTypesSpringStyleMethodInjectionRestController {

    private MongoRepository mongoRepository;

    private MongoConfig mongoConfig;

    private MongoConfig customMongoConfig;

    private MongoClient mongoClient;

    private MongoClient customMongoClient;

    private PostgreSQLRepository postgreSQLRepository;

    private PostgreSQLConfig postgreSQLConfig;

    private PostgreSQLConfig customPostgreSQLConfig;

    private ConnectionFactory connectionFactory;

    private ConnectionPool connectionPool;

    private ConnectionFactory customConnectionFactory;

    private ConnectionPool customConnectionPool;

    private RestClient restClient;

    private HttpClientConfig httpClientConfig;

    private HttpClientConfig customHttpClientConfig;

    private HttpServerConfig httpServerConfig;

    private RestServerConfig restServerConfig;

    private NettyRestServerConfig nettyRestServerConfig;

    @Autowired
    public void setMongoRepository(final MongoRepository mongoRepository) {
        this.mongoRepository = mongoRepository;
    }

    @Autowired
    public void setMongoConfig(final MongoConfig mongoConfig) {
        this.mongoConfig = mongoConfig;
    }

    @Autowired
    @Qualifier("custom-mongo")
    public void setCustomMongoConfig(final MongoConfig customMongoConfig) {
        this.customMongoConfig = customMongoConfig;
    }

    @Autowired
    public void setMongoClient(final MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    @Autowired
    public void setCustomMongoClient(@Qualifier("custom-mongo") final MongoClient customMongoClient) {
        this.customMongoClient = customMongoClient;
    }

    @Autowired
    public void setPostgreSQLRepository(final PostgreSQLRepository postgreSQLRepository) {
        this.postgreSQLRepository = postgreSQLRepository;
    }

    @Autowired
    public void setPostgreSQLConfig(final PostgreSQLConfig postgreSQLConfig) {
        this.postgreSQLConfig = postgreSQLConfig;
    }

    @Autowired
    @CustomPostgreSQLNamedConfig
    public void setCustomPostgreSQLConfig(final PostgreSQLConfig customPostgreSQLConfig) {
        this.customPostgreSQLConfig = customPostgreSQLConfig;
    }

    @Autowired
    public void setConnectionFactory(final ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Autowired
    public void setConnectionPool(final ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Autowired
    public void setCustomConnectionFactory(@CustomPostgreSQLNamedConfig final ConnectionFactory customConnectionFactory) {
        this.customConnectionFactory = customConnectionFactory;
    }

    @Autowired
    @CustomPostgreSQLNamedConfig
    public void setCustomConnectionPool(final ConnectionPool customConnectionPool) {
        this.customConnectionPool = customConnectionPool;
    }

    @Autowired
    public void setRestClient(final RestClient restClient) {
        this.restClient = restClient;
    }

    @Autowired
    public void setHttpClientConfig(final HttpClientConfig httpClientConfig) {
        this.httpClientConfig = httpClientConfig;
    }

    @Autowired
    public void setCustomHttpClientConfig(@Qualifier("custom-http-client") final HttpClientConfig customHttpClientConfig) {
        this.customHttpClientConfig = customHttpClientConfig;
    }

    @Autowired
    public void setHttpServerConfig(final HttpServerConfig httpServerConfig) {
        this.httpServerConfig = httpServerConfig;
    }

    @Autowired
    public void setRestServerConfig(final RestServerConfig restServerConfig) {
        this.restServerConfig = restServerConfig;
    }

    @Autowired
    public void setNettyRestServerConfig(final NettyRestServerConfig nettyRestServerConfig) {
        this.nettyRestServerConfig = nettyRestServerConfig;
    }

    @PostConstruct
    void postConstruct() {

    }

    @GET("/spring/fields")
    void test() {

    }

}
