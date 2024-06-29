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

package io.rxmicro.data.sql.r2dbc.internal;

import io.r2dbc.spi.Batch;
import io.r2dbc.spi.Connection;
import io.r2dbc.spi.ConnectionMetadata;
import io.r2dbc.spi.IsolationLevel;
import io.r2dbc.spi.Statement;
import io.r2dbc.spi.TransactionDefinition;
import io.r2dbc.spi.ValidationDepth;
import io.rxmicro.data.sql.r2dbc.detail.RepositoryConnection;
import io.rxmicro.logger.Logger;
import io.rxmicro.logger.LoggerFactory;
import io.rxmicro.logger.RequestIdSupplier;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * @author nedis
 * @since 0.7
 */
@SuppressWarnings("NullableProblems")
public final class RepositoryConnectionImpl implements RepositoryConnection {

    private final Logger logger;

    private final Connection connection;

    private final String requestId;

    public RepositoryConnectionImpl(final Class<?> repositoryInterface,
                                    final Connection connection) {
        this.logger = LoggerFactory.getLogger(repositoryInterface);
        this.connection = connection;
        this.requestId = RequestIdSupplier.UNDEFINED_REQUEST_ID;
    }

    public RepositoryConnectionImpl(final Class<?> repositoryInterface,
                                    final Connection connection,
                                    final String requestId) {
        this.logger = LoggerFactory.getLogger(repositoryInterface);
        this.connection = connection;
        this.requestId = requestId;
    }

    @Override
    public Mono<Void> beginTransaction() {
        final Mono<Void> mono = Mono.from(connection.beginTransaction());
        if (logger.isTraceEnabled()) {
            return mono.doOnSuccess(s ->
                    logger.trace(
                            this,
                            "Transaction started using connection: class='?', id='?'.",
                            getConnectionClassName(),
                            getConnectionId()
                    )
            );
        }
        return mono;
    }

    @Override
    public Publisher<Void> beginTransaction(final TransactionDefinition definition) {
        final Mono<Void> mono = Mono.from(connection.beginTransaction(definition));
        if (logger.isTraceEnabled()) {
            return mono.doOnSuccess(s ->
                    logger.trace(
                            this,
                            "Transaction started using connection: class='?', id='?'.",
                            getConnectionClassName(),
                            getConnectionId()
                    )
            );
        }
        return mono;
    }

    @Override
    public Mono<Void> close() {
        final Mono<Void> mono = Mono.from(connection.close());
        if (logger.isTraceEnabled()) {
            return mono.doFinally(s ->
                    logger.trace(
                            this,
                            "Connection closed: class='?', id='?', signal='?'",
                            getConnectionClassName(),
                            getConnectionId(),
                            s
                    )
            );
        } else {
            return mono;
        }
    }

    @Override
    public Mono<Void> commitTransaction() {
        final Mono<Void> mono = Mono.from(connection.commitTransaction());
        if (logger.isTraceEnabled()) {
            return mono.doOnSuccess(s ->
                    logger.trace(
                            this,
                            "Transaction committed using connection: class='?', id='?'.",
                            getConnectionClassName(),
                            getConnectionId()
                    )
            );
        } else {
            return mono;
        }
    }

    @Override
    public Batch createBatch() {
        return connection.createBatch();
    }

    @Override
    public Mono<Void> createSavepoint(final String name) {
        final Mono<Void> mono = Mono.from(connection.createSavepoint(name));
        if (logger.isTraceEnabled()) {
            return mono.doOnSuccess(s ->
                    logger.trace(
                            this,
                            "Save point created with name='?' using connection: class='?', id='?'.",
                            name,
                            getConnectionClassName(),
                            getConnectionId()
                    )
            );
        } else {
            return mono;
        }
    }

    @Override
    public Statement createStatement(final String sql) {
        return connection.createStatement(sql);
    }

    @Override
    public boolean isAutoCommit() {
        return connection.isAutoCommit();
    }

    @Override
    public ConnectionMetadata getMetadata() {
        return connection.getMetadata();
    }

    @Override
    public IsolationLevel getTransactionIsolationLevel() {
        return connection.getTransactionIsolationLevel();
    }

    @Override
    public Mono<Void> releaseSavepoint(final String name) {
        final Mono<Void> mono = Mono.from(connection.releaseSavepoint(name));
        if (logger.isTraceEnabled()) {
            return mono.doOnSuccess(s ->
                    logger.trace(
                            this,
                            "Save point released with name='?' using connection: class='?', id='?'.",
                            name,
                            getConnectionClassName(),
                            getConnectionId()
                    )
            );
        } else {
            return mono;
        }
    }

    @Override
    public Mono<Void> rollbackTransaction() {
        final Mono<Void> mono = Mono.from(connection.rollbackTransaction());
        if (logger.isTraceEnabled()) {
            return mono.doOnSuccess(s ->
                    logger.trace(
                            this,
                            "Transaction rolled back using connection: class='?', id='?'.",
                            getConnectionClassName(),
                            getConnectionId()
                    )
            );
        } else {
            return mono;
        }
    }

    @Override
    public Mono<Void> rollbackTransactionToSavepoint(final String name) {
        final Mono<Void> mono = Mono.from(connection.rollbackTransactionToSavepoint(name));
        if (logger.isTraceEnabled()) {
            return mono.doOnSuccess(s ->
                    logger.trace(
                            this,
                            "Transaction rolled back to save point with name='?' using connection: class='?', id='?'.",
                            name,
                            getConnectionClassName(),
                            getConnectionId()
                    )
            );
        } else {
            return mono;
        }
    }

    @Override
    public Mono<Void> setAutoCommit(final boolean autoCommit) {
        final Mono<Void> mono = Mono.from(connection.setAutoCommit(autoCommit));
        if (logger.isTraceEnabled()) {
            return mono.doOnSuccess(s ->
                    logger.trace(
                            this,
                            "Connection autoCommit mode updated: autoCommit=?, class='?', id='?'.",
                            autoCommit,
                            getConnectionClassName(),
                            getConnectionId()
                    )
            );
        } else {
            return mono;
        }
    }

    @Override
    public Mono<Void> setTransactionIsolationLevel(final IsolationLevel isolationLevel) {
        final Mono<Void> mono = Mono.from(connection.setTransactionIsolationLevel(isolationLevel));
        if (logger.isTraceEnabled()) {
            return mono.doOnSuccess(s ->
                    logger.trace(
                            this,
                            "Transaction isolation level updated: isolationLevel='?', class='?', id='?'.",
                            isolationLevel,
                            getConnectionClassName(),
                            getConnectionId()
                    )
            );
        } else {
            return mono;
        }
    }

    @Override
    public Publisher<Void> setLockWaitTimeout(final Duration timeout) {
        final Mono<Void> mono = Mono.from(connection.setLockWaitTimeout(timeout));
        if (logger.isTraceEnabled()) {
            return mono.doOnSuccess(s ->
                    logger.trace(
                            this,
                            "Lock wait timeout updated: timeout='?', class='?', id='?'.",
                            timeout,
                            getConnectionClassName(),
                            getConnectionId()
                    )
            );
        } else {
            return mono;
        }
    }

    @Override
    public Publisher<Void> setStatementTimeout(final Duration timeout) {
        final Mono<Void> mono = Mono.from(connection.setStatementTimeout(timeout));
        if (logger.isTraceEnabled()) {
            return mono.doOnSuccess(s ->
                    logger.trace(
                            this,
                            "Statement timeout updated: timeout='?', class='?', id='?'.",
                            timeout,
                            getConnectionClassName(),
                            getConnectionId()
                    )
            );
        } else {
            return mono;
        }
    }

    @Override
    public Mono<Boolean> validate(final ValidationDepth depth) {
        return Mono.from(connection.validate(depth));
    }

    @Override
    public String getRequestId() {
        return requestId;
    }

    @Override
    public String getConnectionId() {
        return "c" + System.identityHashCode(connection);
    }

    @Override
    public String getConnectionClassName() {
        return connection.getClass().getSimpleName();
    }
}
