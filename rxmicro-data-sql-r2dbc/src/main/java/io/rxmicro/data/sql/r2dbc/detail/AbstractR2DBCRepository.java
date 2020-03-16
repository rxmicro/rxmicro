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

import io.r2dbc.spi.Connection;
import io.r2dbc.spi.Result;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import io.rxmicro.data.sql.detail.AbstractSQLRepository;
import io.rxmicro.data.sql.model.EntityFieldList;
import io.rxmicro.data.sql.model.EntityFieldMap;
import io.rxmicro.data.sql.r2dbc.internal.Connections;
import io.rxmicro.data.sql.r2dbc.internal.ResultConverters;
import io.rxmicro.data.sql.r2dbc.internal.Statements;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public abstract class AbstractR2DBCRepository extends AbstractSQLRepository {

    private final Connections connections;

    private final Statements statements;

    protected AbstractR2DBCRepository(final Class<?> repositoryClass) {
        super(repositoryClass);
        connections = new Connections(LOGGER);
        statements = new Statements(LOGGER);
    }

    protected final Mono<Connection> extractConnectionFrom(final io.rxmicro.data.sql.model.reactor.Transaction transaction) {
        return connections.extractConnectionFrom(transaction);
    }

    protected final Mono<Connection> extractConnectionFrom(final io.rxmicro.data.sql.model.rxjava3.Transaction transaction) {
        return connections.extractConnectionFrom(transaction);
    }

    protected final Mono<Connection> extractConnectionFrom(final io.rxmicro.data.sql.model.completablefuture.Transaction transaction) {
        return connections.extractConnectionFrom(transaction);
    }

    protected final Mono<io.rxmicro.data.sql.model.reactor.Transaction> beginReactorTransaction(final Connection connection) {
        return connections.beginReactorTransaction(connection);
    }

    protected final Mono<io.rxmicro.data.sql.model.rxjava3.Transaction> beginRxJava3Transaction(final Connection connection) {
        return connections.beginRxJava3Transaction(connection);
    }

    protected final Mono<io.rxmicro.data.sql.model.completablefuture.Transaction> beginCompletableFutureTransaction(final Connection connection) {
        return connections.beginCompletableFutureTransaction(connection);
    }

    protected final Mono<Void> close(final Connection connection) {
        return connections.close(connection);
    }

    protected final Mono<? extends Result> executeStatement(final Connection connection,
                                                            final String sql) {
        return statements.executeStatement(connection, sql);
    }

    protected final Mono<? extends Result> executeStatement(final Connection connection,
                                                            final String sql,
                                                            final Object param) {
        return statements.executeStatement(connection, sql, param);
    }

    protected final Mono<? extends Result> executeStatement(final Connection connection,
                                                            final String sql,
                                                            final Object param1,
                                                            final Object param2) {
        return statements.executeStatement(connection, sql, param1, param2);
    }

    protected final Mono<? extends Result> executeStatement(final Connection connection,
                                                            final String sql,
                                                            final Object param1,
                                                            final Object param2,
                                                            final Object param3) {
        return statements.executeStatement(connection, sql, param1, param2, param3);
    }

    protected final Mono<? extends Result> executeStatement(final Connection connection,
                                                            final String sql,
                                                            final Object param1,
                                                            final Object param2,
                                                            final Object param3,
                                                            final Object param4) {
        return statements.executeStatement(connection, sql, param1, param2, param3, param4);
    }

    protected final Mono<? extends Result> executeStatement(final Connection connection,
                                                            final String sql,
                                                            final Object param1,
                                                            final Object param2,
                                                            final Object param3,
                                                            final Object param4,
                                                            final Object param5) {
        return statements.executeStatement(connection, sql, param1, param2, param3, param4, param5);
    }

    protected final Mono<? extends Result> executeStatement(final Connection connection,
                                                            final String sql,
                                                            final Object... params) {
        return statements.executeStatement(connection, sql, params);
    }

    @SuppressWarnings("SameReturnValue")
    protected final BiFunction<Row, RowMetadata, EntityFieldMap> toEntityFieldMap() {
        return ResultConverters.TO_ENTITY_FIELD_MAP_BI_FUNCTION;
    }

    @SuppressWarnings("SameReturnValue")
    protected final BiFunction<Row, RowMetadata, EntityFieldList> toEntityFieldList() {
        return ResultConverters.TO_ENTITY_FIELD_LIST_BI_FUNCTION;
    }
}
