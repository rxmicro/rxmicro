/*
 * Copyright (c) 2020. http://rxmicro.io
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

package io.rxmicro.data.sql.r2dbc.internal;

import io.r2dbc.spi.Connection;
import io.rxmicro.data.sql.r2dbc.internal.transaction.CompletableFutureTransaction;
import io.rxmicro.data.sql.r2dbc.internal.transaction.ReactorTransaction;
import io.rxmicro.data.sql.r2dbc.internal.transaction.RxJava3Transaction;
import io.rxmicro.logger.Logger;
import reactor.core.publisher.Mono;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public final class Connections {

    private final Logger LOGGER;

    public Connections(final Logger LOGGER) {
        this.LOGGER = LOGGER;
    }

    public Mono<Connection> extractConnectionFrom(final io.rxmicro.data.sql.model.reactor.Transaction transaction) {
        return Mono.just(((AbstractTransaction) transaction).getConnection());
    }

    public Mono<Connection> extractConnectionFrom(final io.rxmicro.data.sql.model.rxjava3.Transaction transaction) {
        return Mono.just(((AbstractTransaction) transaction).getConnection());
    }

    public Mono<Connection> extractConnectionFrom(final io.rxmicro.data.sql.model.completablefuture.Transaction transaction) {
        return Mono.just(((AbstractTransaction) transaction).getConnection());
    }

    public Mono<io.rxmicro.data.sql.model.reactor.Transaction> beginReactorTransaction(final Connection connection) {
        return Mono.from(connection.beginTransaction())
                .thenReturn(new ReactorTransaction(connection));
    }

    public Mono<io.rxmicro.data.sql.model.rxjava3.Transaction> beginRxJava3Transaction(final Connection connection) {
        return Mono.from(connection.beginTransaction())
                .thenReturn(new RxJava3Transaction(connection));
    }

    public Mono<io.rxmicro.data.sql.model.completablefuture.Transaction> beginCompletableFutureTransaction(final Connection connection) {
        return Mono.from(connection.beginTransaction())
                .thenReturn(new CompletableFutureTransaction(connection));
    }

    public Mono<Void> close(final Connection connection) {
        final Mono<Void> closeMono = Mono.from(connection.close());
        if (LOGGER.isTraceEnabled()) {
            return closeMono.doFinally(s ->
                    LOGGER.trace("Connection{type=?, id=?} closed: signal: '?'",
                            connection.getClass().getSimpleName(),
                            System.identityHashCode(connection),
                            s
                    ));
        } else {
            return closeMono;
        }
    }
}
