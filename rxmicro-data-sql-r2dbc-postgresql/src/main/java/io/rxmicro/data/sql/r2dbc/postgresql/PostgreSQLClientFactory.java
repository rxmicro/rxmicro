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

package io.rxmicro.data.sql.r2dbc.postgresql;

import io.r2dbc.pool.ConnectionPool;
import io.r2dbc.spi.ConnectionFactory;
import io.rxmicro.data.sql.r2dbc.postgresql.internal.PostgreSQLConnectionPoolBuilder;
import io.rxmicro.runtime.detail.ByTypeInstanceQualifier;
import io.rxmicro.runtime.local.provider.LazyInstanceProvider;

import static io.rxmicro.config.Config.getDefaultNameSpace;
import static io.rxmicro.runtime.local.InstanceContainer.getSingleton;

/**
 * Utility class that must be used to get an instance of the {@link ConnectionFactory} or {@link ConnectionPool}
 * integrated to the RxMicro framework.
 *
 * <p>
 * This utility class allows using additional features that {@link ConnectionFactory} or {@link ConnectionPool} is provided.
 *
 * @author nedis
 * @see PostgreSQLConfig
 * @see PostgreSQLRepository
 * @since 0.1
 */
public final class PostgreSQLClientFactory {

    private static final PostgreSQLConnectionPoolBuilder POSTGRE_SQL_CONNECTION_POOL_BUILDER =
            new PostgreSQLConnectionPoolBuilder();

    /**
     * Returns the instance of the {@link ConnectionFactory} that is bound to config with the default namespace.
     *
     * @return the instance of the {@link ConnectionFactory}
     */
    public static ConnectionFactory getPostgreSQLConnectionFactory() {
        return getPostgreSQLConnectionPool();
    }

    /**
     * Returns the instance of the {@link ConnectionFactory} that is bound to config with the requested namespace.
     *
     * @param namespace the requested namespace
     * @return the instance of the {@link ConnectionFactory}
     */
    public static ConnectionFactory getPostgreSQLConnectionFactory(final String namespace) {
        return getPostgreSQLConnectionPool(namespace);
    }

    /**
     * Returns the instance of the {@link ConnectionPool} that is bound to config with the default namespace.
     *
     * @return the instance of the {@link ConnectionPool}
     */
    public static ConnectionPool getPostgreSQLConnectionPool() {
        return getPostgreSQLConnectionPool(getDefaultNameSpace(PostgreSQLConfig.class));
    }

    /**
     * Returns the instance of the {@link ConnectionPool} that is bound to config with the requested namespace.
     *
     * @param namespace the requested namespace
     * @return the instance of the {@link ConnectionPool}
     */
    public static ConnectionPool getPostgreSQLConnectionPool(final String namespace) {
        return getSingleton(
                new ByTypeInstanceQualifier<>(ConnectionPool.class),
                new LazyInstanceProvider<>(ConnectionPool.class, () -> POSTGRE_SQL_CONNECTION_POOL_BUILDER.build(namespace))
        );
    }

    private PostgreSQLClientFactory() {
    }
}
