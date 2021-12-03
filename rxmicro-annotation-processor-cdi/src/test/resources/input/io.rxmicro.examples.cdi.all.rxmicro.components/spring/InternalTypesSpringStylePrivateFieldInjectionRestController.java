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
import io.rxmicro.rest.client.RestClientConfig;
import io.rxmicro.rest.method.GET;
import io.rxmicro.rest.server.HttpServerConfig;
import io.rxmicro.rest.server.RestServerConfig;
import io.rxmicro.rest.server.netty.NettyRestServerConfig;

@SuppressWarnings("EmptyMethod")
public final class InternalTypesSpringStylePrivateFieldInjectionRestController {

    @Autowired
    private MongoRepository mongoRepository;

    @Autowired
    private MongoConfig mongoConfig;

    @Autowired
    @Qualifier("custom-mongo")
    private MongoConfig customMongoConfig;

    @Autowired
    private MongoClient mongoClient;

    @Autowired
    @Qualifier("custom-mongo")
    private MongoClient customMongoClient;

    @Autowired
    private PostgreSQLRepository postgreSQLRepository;

    @Autowired
    private PostgreSQLConfig postgreSQLConfig;

    @Autowired
    @CustomPostgreSQLNamedConfig
    private PostgreSQLConfig customPostgreSQLConfig;

    @Autowired
    private ConnectionFactory connectionFactory;

    @Autowired
    private ConnectionPool connectionPool;

    @Autowired
    @CustomPostgreSQLNamedConfig
    private ConnectionFactory customConnectionFactory;

    @Autowired
    @CustomPostgreSQLNamedConfig
    private ConnectionPool customConnectionPool;

    @Autowired
    private RestClient restClient;

    @Autowired
    private RestClientConfig restClientConfig;

    @Autowired
    @Qualifier("custom-rest-client")
    private RestClientConfig customRestClientConfig;

    @Autowired
    private HttpServerConfig httpServerConfig;

    @Autowired
    private RestServerConfig restServerConfig;

    @Autowired
    private NettyRestServerConfig nettyRestServerConfig;

    @PostConstruct
    private void postConstruct() {

    }

    @GET("/spring/fields")
    void test() {

    }

}
