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

package io.rxmicro.data.sql.r2dbc.postgresql.detail;

import io.r2dbc.pool.ConnectionPool;
import io.rxmicro.data.sql.r2dbc.postgresql.internal.PostgreSQLConnectionPoolBuilder;
import io.rxmicro.runtime.detail.ByTypeInstanceQualifier;
import io.rxmicro.runtime.local.provider.LazyInstanceProvider;

import java.util.function.Function;

import static io.rxmicro.runtime.local.InstanceContainer.getSingleton;

/**
 * Used by generated code that was created by RxMicro Annotation Processor
 *
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class PostgreSQLRepositoryFactory {

    private static final PostgreSQLConnectionPoolBuilder BUILDER = new PostgreSQLConnectionPoolBuilder();

    static {
        // https://github.com/r2dbc/r2dbc-postgresql#logging
        System.setProperty("reactor.logging.fallback", "JDK");
    }

    public static <T> T createPostgreSQLRepository(final String namespace,
                                                   final Function<ConnectionPool, T> creator) {
        final ConnectionPool connectionPool = getSingleton(
                new ByTypeInstanceQualifier<>(ConnectionPool.class),
                new LazyInstanceProvider<>(ConnectionPool.class, () -> BUILDER.createConnectionPool(namespace)));
        return creator.apply(connectionPool);
    }

    private PostgreSQLRepositoryFactory() {
    }
}
