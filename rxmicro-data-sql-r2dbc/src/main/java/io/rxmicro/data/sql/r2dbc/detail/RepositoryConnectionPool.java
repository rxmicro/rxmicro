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

package io.rxmicro.data.sql.r2dbc.detail;

import io.r2dbc.pool.ConnectionPool;
import io.r2dbc.spi.ConnectionFactoryMetadata;
import io.rxmicro.data.sql.r2dbc.internal.RepositoryConnectionImpl;
import io.rxmicro.logger.RequestIdSupplier;
import reactor.core.publisher.Mono;

/**
 * @author nedis
 * @since 0.7
 */
public final class RepositoryConnectionPool implements RepositoryConnectionFactory {

    private final Class<?> repositoryInterface;

    private final ConnectionPool pool;

    public RepositoryConnectionPool(final Class<?> repositoryInterface,
                                    final ConnectionPool pool) {
        this.repositoryInterface = repositoryInterface;
        this.pool = pool;
    }


    @Override
    public Mono<RepositoryConnection> create() {
        return pool.create().map(c -> new RepositoryConnectionImpl(repositoryInterface, c));
    }

    @Override
    public Mono<RepositoryConnection> create(final RequestIdSupplier requestIdSupplier) {
        return pool.create().map(c -> new RepositoryConnectionImpl(repositoryInterface, c, requestIdSupplier.getRequestId()));
    }

    @Override
    public ConnectionFactoryMetadata getMetadata() {
        return pool.getMetadata();
    }
}
