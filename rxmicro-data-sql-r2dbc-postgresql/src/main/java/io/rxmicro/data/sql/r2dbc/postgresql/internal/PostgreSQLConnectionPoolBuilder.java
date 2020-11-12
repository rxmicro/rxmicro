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

package io.rxmicro.data.sql.r2dbc.postgresql.internal;

import io.r2dbc.pool.ConnectionPool;
import io.r2dbc.pool.ConnectionPoolConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import io.r2dbc.postgresql.extension.CodecRegistrar;
import io.r2dbc.spi.Connection;
import io.r2dbc.spi.ConnectionFactory;
import io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLConfig;
import io.rxmicro.logger.Logger;
import io.rxmicro.logger.LoggerFactory;
import io.rxmicro.runtime.AutoRelease;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static io.rxmicro.common.util.Requires.require;
import static io.rxmicro.config.Configs.getConfig;
import static io.rxmicro.runtime.local.InstanceContainer.registerAutoRelease;

/**
 * Read more:
 * https://github.com/r2dbc/r2dbc-postgresql
 * https://github.com/r2dbc/r2dbc-pool
 * https://www.postgresql.org/docs/current/runtime-config-client.html
 *
 * @author nedis
 * @since 0.1
 */
public final class PostgreSQLConnectionPoolBuilder {

    private static final PostgreSQLConnectionPoolBuilder INSTANCE = new PostgreSQLConnectionPoolBuilder();

    private final List<CodecRegistrar> codecRegistrars = new ArrayList<>();

    private Function<Connection, Connection> connectionDecorator;

    private PostgreSQLConnectionPool postgreSQLConnectionPool;

    public static PostgreSQLConnectionPoolBuilder getInstance() {
        return INSTANCE;
    }

    private PostgreSQLConnectionPoolBuilder() {
    }

    public void addCodecRegistrar(final CodecRegistrar codecRegistrar) {
        validateState();
        this.codecRegistrars.add(require(codecRegistrar));
    }

    public void setConnectionDecorator(final Function<Connection, Connection> connectionDecorator) {
        validateState();
        this.connectionDecorator = require(connectionDecorator);
    }

    public ConnectionPool build(final String namespace) {
        validateState();
        final PostgreSQLConfig postgreSQLConfig = getConfig(namespace, PostgreSQLConfig.class);
        final ConnectionFactory connectionFactory = createConnectionFactory(postgreSQLConfig);
        return createConnectionPool(postgreSQLConfig, connectionFactory);
    }

    private void validateState() {
        if (postgreSQLConnectionPool != null) {
            throw new IllegalStateException("Connection pool already built! " +
                    "Any customizations must be done before building of the connection pool!");
        }
    }

    private ConnectionFactory createConnectionFactory(final PostgreSQLConfig postgreSQLConfig) {
        final PostgresqlConnectionConfiguration.Builder builder = PostgresqlConnectionConfiguration.builder()
                .host(postgreSQLConfig.getHost())
                .port(postgreSQLConfig.getPort())
                .username(postgreSQLConfig.getUser())
                .password(postgreSQLConfig.getPassword())
                .database(postgreSQLConfig.getDatabase())
                .connectTimeout(postgreSQLConfig.getConnectTimeout());
        codecRegistrars.forEach(builder::codecRegistrar);
        postgreSQLConfig.getOptions().ifPresent(builder::options);
        return new PostgresqlConnectionFactory(builder.build());
    }

    private ConnectionPool createConnectionPool(final PostgreSQLConfig postgreSQLConfig,
                                                final ConnectionFactory connectionFactory) {
        final ConnectionPoolConfiguration.Builder builder = ConnectionPoolConfiguration.builder(connectionFactory)
                .name("rxmicro-postgresql-connection-pool")
                .acquireRetry(postgreSQLConfig.getAcquireRetry())
                .initialSize(postgreSQLConfig.getInitialSize())
                .maxSize(postgreSQLConfig.getMaxSize())
                .validationQuery(postgreSQLConfig.getValidationQuery())
                .maxAcquireTime(postgreSQLConfig.getMaxAcquireTime())
                .maxCreateConnectionTime(postgreSQLConfig.getMaxCreateConnectionTime())
                .maxIdleTime(postgreSQLConfig.getMaxIdleTime())
                .maxLifeTime(postgreSQLConfig.getMaxLifeTime());
        final ConnectionPool connectionPool = buildConnectionPool(builder);
        postgreSQLConnectionPool = new PostgreSQLConnectionPool(this, postgreSQLConfig, connectionPool);
        return connectionPool;
    }

    private ConnectionPool buildConnectionPool(final ConnectionPoolConfiguration.Builder builder) {
        return Optional.ofNullable(connectionDecorator)
                .map(decorator -> (ConnectionPool) new ConnectionPool(builder.build()) {

                    @Override
                    @SuppressWarnings("NullableProblems")
                    public Mono<Connection> create() {
                        return super.create().map(decorator);
                    }
                })
                .orElseGet(() -> new ConnectionPool(builder.build()));
    }

    /**
     * @author nedis
     * @since 0.1
     */
    private static final class PostgreSQLConnectionPool implements AutoRelease {

        private static final Logger LOGGER = LoggerFactory.getLogger(PostgreSQLConnectionPool.class);

        private final PostgreSQLConnectionPoolBuilder builder;

        private final PostgreSQLConfig postgreSQLConfig;

        private final ConnectionPool connectionPool;

        private PostgreSQLConnectionPool(final PostgreSQLConnectionPoolBuilder builder,
                                         final PostgreSQLConfig postgreSQLConfig,
                                         final ConnectionPool connectionPool) {
            this.builder = builder;
            this.postgreSQLConfig = require(postgreSQLConfig);
            this.connectionPool = require(connectionPool);
            LOGGER.info("Pool created: connectionString='?', poolSize:{init=?, max=?}",
                    postgreSQLConfig.getConnectionString(), postgreSQLConfig.getInitialSize(), postgreSQLConfig.getMaxSize()
            );
            registerAutoRelease(this);
        }

        @Override
        public void release() {
            connectionPool.dispose();
            LOGGER.info("Pool disposed: connectionString='?'", postgreSQLConfig.getConnectionString());
            builder.postgreSQLConnectionPool = null;
        }
    }
}
