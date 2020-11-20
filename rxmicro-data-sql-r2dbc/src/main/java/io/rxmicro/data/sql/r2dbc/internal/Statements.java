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

import io.r2dbc.spi.Result;
import io.r2dbc.spi.Statement;
import io.rxmicro.data.sql.r2dbc.detail.RepositoryConnection;
import io.rxmicro.logger.Logger;
import reactor.core.publisher.Mono;

import java.util.Arrays;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @since 0.1
 */
public final class Statements {

    private final Logger logger;

    public Statements(final Logger logger) {
        this.logger = logger;
    }

    public Mono<? extends Result> executeStatement(final RepositoryConnection connection,
                                                   final String sql) {
        logStatement(connection, sql);
        final Statement statement = connection.createStatement(sql);
        return execute(connection, statement, sql);
    }

    public Mono<? extends Result> executeStatement(final RepositoryConnection connection,
                                                   final String sql,
                                                   final Object... params) {
        logStatement(connection, sql, params);
        final Statement statement = connection.createStatement(sql);
        for (int i = 0; i < params.length; i++) {
            final Object param = require(
                    params[i],
                    "Bind parameter must be not null: sql='?', index=[?]! " +
                            "For null param use 'WHERE column IS NULL' instead of 'WHERE column=?', " +
                            "where '?' is placeholder for 'null' value!",
                    sql,
                    i + 1
            );
            statement.bind(i, param);
        }
        return execute(connection, statement, sql, params);
    }

    public Mono<? extends Result> executeStatement(final RepositoryConnection connection,
                                                   final String sql,
                                                   final Object[] params,
                                                   final Class<?>[] types) {
        logStatement(connection, sql, params);
        final Statement statement = connection.createStatement(sql);
        for (int i = 0; i < params.length; i++) {
            final Object param = params[i];
            if (param == null) {
                statement.bindNull(i, types[i]);
            } else {
                statement.bind(i, param);
            }
        }
        return execute(connection, statement, sql, params);
    }

    private void logStatement(final RepositoryConnection connection,
                              final String sql,
                              final Object... params) {
        if (logger.isTraceEnabled()) {
            logger.trace(
                    connection,
                    "Execute SQL '?'? using connection: class='?', id='?'...",
                    sql, getWithParamsString(params), connection.getConnectionClassName(), connection.getConnectionId()
            );
        } else if (logger.isDebugEnabled()) {
            logger.debug(connection, "Execute SQL '?' using connection with id='?'...", sql, connection.getConnectionId());
        }
    }

    private Mono<? extends Result> execute(final RepositoryConnection connection,
                                           final Statement statement,
                                           final String sql,
                                           final Object... params) {
        if (logger.isTraceEnabled()) {
            return Mono.from(statement.execute())
                    .doOnSuccess(r -> logger.trace(connection, "SQL '?'? executed successful", sql, getWithParamsString(params)));
        } else {
            return Mono.from(statement.execute());
        }
    }

    private String getWithParamsString(final Object[] params) {
        return params.length > 0 ? format(" with params: ?", Arrays.toString(params)) : "";
    }
}
