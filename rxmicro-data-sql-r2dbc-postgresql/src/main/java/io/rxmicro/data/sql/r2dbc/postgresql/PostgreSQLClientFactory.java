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
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class PostgreSQLClientFactory {

    private static final PostgreSQLConnectionPoolBuilder BUILDER = new PostgreSQLConnectionPoolBuilder();

    public static ConnectionFactory getPostgreSQLConnectionFactory() {
        return getPostgreSQLConnectionPool();
    }

    public static ConnectionFactory getPostgreSQLConnectionFactory(final String nameSpace) {
        return getPostgreSQLConnectionPool(nameSpace);
    }

    public static ConnectionPool getPostgreSQLConnectionPool() {
        return getPostgreSQLConnectionPool(getDefaultNameSpace(PostgreSQLConfig.class));
    }

    public static ConnectionPool getPostgreSQLConnectionPool(final String nameSpace) {
        return getSingleton(
                new ByTypeInstanceQualifier<>(ConnectionPool.class),
                new LazyInstanceProvider<>(ConnectionPool.class, () -> BUILDER.createConnectionPool(nameSpace))
        );
    }

    private PostgreSQLClientFactory() {
    }
}
