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
import io.r2dbc.spi.Connection;
import io.r2dbc.spi.ConnectionFactory;
import io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLConfig;
import io.rxmicro.logger.Logger;
import io.rxmicro.logger.LoggerFactory;
import io.rxmicro.runtime.AutoRelease;
import reactor.core.publisher.Mono;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.common.util.Requires.require;
import static io.rxmicro.config.Configs.getConfig;
import static io.rxmicro.runtime.local.InstanceContainer.registerAutoRelease;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @link https://github.com/r2dbc/r2dbc-postgresql
 * @link https://github.com/r2dbc/r2dbc-pool
 * @link https://www.postgresql.org/docs/current/runtime-config-client.html
 * @since 0.1
 */
public final class PostgreSQLConnectionPoolBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(
            format("?.PostgreSQLPooledClient", PostgreSQLConnectionPoolBuilder.class.getPackageName())
    );

    public ConnectionPool createConnectionPool(final String nameSpace) {
        final PostgreSQLConfig postgreSQLConfig = getConfig(nameSpace, PostgreSQLConfig.class);
        final ConnectionFactory connectionFactory = createConnectionFactory(postgreSQLConfig);
        return createConnectionPool(postgreSQLConfig, connectionFactory);
    }

    private ConnectionFactory createConnectionFactory(final PostgreSQLConfig postgreSQLConfig) {
        final PostgresqlConnectionConfiguration.Builder builder = PostgresqlConnectionConfiguration.builder()
                .host(postgreSQLConfig.getHost())
                .port(postgreSQLConfig.getPort())
                .username(postgreSQLConfig.getUser())
                .password(postgreSQLConfig.getPassword())
                .database(postgreSQLConfig.getDatabase())
                .connectTimeout(postgreSQLConfig.getConnectTimeout());
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
        final ConnectionPool connectionPool = buildConnectionPool(postgreSQLConfig, builder);
        LOGGER.info("PostgreSQL pooled client created: connectionString='?', poolSize:{init=?, max=?}",
                postgreSQLConfig.getConnectionString(), postgreSQLConfig.getInitialSize(), postgreSQLConfig.getMaxSize()
        );
        registerAutoRelease(new R2DBCPostgreSQLConnectionPool(postgreSQLConfig, connectionPool));
        return connectionPool;
    }

    private ConnectionPool buildConnectionPool(final PostgreSQLConfig postgreSQLConfig,
                                               final ConnectionPoolConfiguration.Builder builder) {
        return postgreSQLConfig.getConnectionDecorator()
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
     * @link https://rxmicro.io
     * @since 0.1
     */
    private static final class R2DBCPostgreSQLConnectionPool implements AutoRelease {

        private final PostgreSQLConfig postgreSQLConfig;

        private final ConnectionPool connectionPool;

        private R2DBCPostgreSQLConnectionPool(final PostgreSQLConfig postgreSQLConfig,
                                              final ConnectionPool connectionPool) {
            this.postgreSQLConfig = require(postgreSQLConfig);
            this.connectionPool = require(connectionPool);
        }

        @Override
        public void release() {
            connectionPool.dispose();
            LOGGER.info("PostgreSQL pooled client closed: connectionString='?'", postgreSQLConfig.getConnectionString());
        }
    }
}
