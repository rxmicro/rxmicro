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

import io.r2dbc.spi.Connection;
import io.r2dbc.spi.Result;
import io.r2dbc.spi.Statement;
import io.rxmicro.logger.Logger;
import reactor.core.publisher.Mono;

import java.util.Arrays;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class Statements {

    private final Logger logger;

    public Statements(final Logger logger) {
        this.logger = logger;
    }

    public Mono<? extends Result> executeStatement(final Connection connection,
                                                   final String sql) {
        if (logger.isTraceEnabled()) {
            logCreateStatement(connection);
        }
        final Statement statement = connection.createStatement(sql);
        final Mono<? extends Result> resultMono = Mono.from(statement.execute());
        if (logger.isTraceEnabled()) {
            return resultMono.doOnSuccess(r ->
                    logger.trace("Statement{sql=?} executed", sql)
            );
        } else {
            return resultMono;
        }
    }

    public Mono<? extends Result> executeStatement(final Connection connection,
                                                   final String sql,
                                                   final Object param) {
        if (logger.isTraceEnabled()) {
            logCreateStatement(connection);
        }
        final Statement statement = connection.createStatement(sql)
                .bind(0, param);
        final Mono<? extends Result> resultMono = Mono.from(statement.execute());
        if (logger.isTraceEnabled()) {
            return resultMono.doOnSuccess(r ->
                    logger.trace("Statement{sql=?, params=[?]} executed", sql, param)
            );
        } else {
            return resultMono;
        }
    }

    public Mono<? extends Result> executeStatement(final Connection connection,
                                                   final String sql,
                                                   final Object param1,
                                                   final Object param2) {
        if (logger.isTraceEnabled()) {
            logCreateStatement(connection);
        }
        final Statement statement = connection.createStatement(sql)
                .bind(0, param1)
                .bind(1, param2);
        final Mono<? extends Result> resultMono = Mono.from(statement.execute());
        if (logger.isTraceEnabled()) {
            return resultMono.doOnSuccess(r ->
                    logger.trace("Statement{sql=?, params=[?, ?]} executed", sql, param1, param2)
            );
        } else {
            return resultMono;
        }
    }

    public Mono<? extends Result> executeStatement(final Connection connection,
                                                   final String sql,
                                                   final Object param1,
                                                   final Object param2,
                                                   final Object param3) {
        if (logger.isTraceEnabled()) {
            logCreateStatement(connection);
        }
        final Statement statement = connection.createStatement(sql)
                .bind(0, param1)
                .bind(1, param2)
                .bind(2, param3);
        final Mono<? extends Result> resultMono = Mono.from(statement.execute());
        if (logger.isTraceEnabled()) {
            return resultMono.doOnSuccess(r ->
                    logger.trace("Statement{sql=?, params=[?, ?, ?]} executed", sql, param1, param2, param3)
            );
        } else {
            return resultMono;
        }
    }

    public Mono<? extends Result> executeStatement(final Connection connection,
                                                   final String sql,
                                                   final Object param1,
                                                   final Object param2,
                                                   final Object param3,
                                                   final Object param4) {
        if (logger.isTraceEnabled()) {
            logCreateStatement(connection);
        }
        final Statement statement = connection.createStatement(sql)
                .bind(0, param1)
                .bind(1, param2)
                .bind(2, param3)
                .bind(3, param4);
        final Mono<? extends Result> resultMono = Mono.from(statement.execute());
        if (logger.isTraceEnabled()) {
            return resultMono.doOnSuccess(r ->
                    logger.trace("Statement{sql=?, params=[?, ?, ?, ?]} executed", sql, param1, param2, param3, param4)
            );
        } else {
            return resultMono;
        }
    }

    public Mono<? extends Result> executeStatement(final Connection connection,
                                                   final String sql,
                                                   final Object param1,
                                                   final Object param2,
                                                   final Object param3,
                                                   final Object param4,
                                                   final Object param5) {
        if (logger.isTraceEnabled()) {
            logCreateStatement(connection);
        }
        final Statement statement = connection.createStatement(sql)
                .bind(0, param1)
                .bind(1, param2)
                .bind(2, param3)
                .bind(3, param4)
                .bind(4, param5);
        final Mono<? extends Result> resultMono = Mono.from(statement.execute());
        if (logger.isTraceEnabled()) {
            return resultMono.doOnSuccess(r ->
                    logger.trace("Statement{sql=?, params=[?, ?, ?, ?, ?]} executed", sql, param1, param2, param3, param4, param5)
            );
        } else {
            return resultMono;
        }
    }

    public Mono<? extends Result> executeStatement(final Connection connection,
                                                   final String sql,
                                                   final Object... params) {
        if (logger.isTraceEnabled()) {
            logCreateStatement(connection);
        }
        final Statement statement = connection.createStatement(sql);
        int index = 0;
        for (final Object param : params) {
            statement.bind(index++, param);
        }
        final Mono<? extends Result> resultMono = Mono.from(statement.execute());
        if (logger.isTraceEnabled()) {
            return resultMono.doOnSuccess(r ->
                    logger.trace("Statement{sql=?, params=?} executed", sql, Arrays.toString(params))
            );
        } else {
            return resultMono;
        }
    }

    private void logCreateStatement(final Connection connection) {
        logger.trace("Connection{type=?, id=?} received from pool",
                connection.getClass().getSimpleName(),
                System.identityHashCode(connection)
        );
    }
}
