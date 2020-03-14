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

package io.rxmicro.test.mockito.r2dbc.internal;

import io.r2dbc.pool.ConnectionPool;
import io.r2dbc.spi.Connection;
import io.r2dbc.spi.Result;
import io.r2dbc.spi.Statement;
import io.rxmicro.test.local.InvalidTestConfigException;
import io.rxmicro.test.mockito.InvalidPreparedMockException;
import io.rxmicro.test.mockito.r2dbc.ErrorDuringSQLInvocationType;
import io.rxmicro.test.mockito.r2dbc.Null;
import io.rxmicro.test.mockito.r2dbc.SQLParamsMock;
import org.mockito.stubbing.Answer;
import org.mockito.stubbing.OngoingStubbing;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static io.rxmicro.test.mockito.internal.CommonMatchers.any;
import static io.rxmicro.test.mockito.internal.CommonMatchers.anyInt;
import static io.rxmicro.test.mockito.internal.CommonMatchers.anyString;
import static io.rxmicro.test.mockito.r2dbc.ErrorDuringSQLInvocationType.BIND_STATEMENT_ARGUMENTS_FAILED;
import static io.rxmicro.test.mockito.r2dbc.ErrorDuringSQLInvocationType.CLOSE_CONNECTION_FAILED;
import static io.rxmicro.test.mockito.r2dbc.ErrorDuringSQLInvocationType.CREATE_CONNECTION_FAILED;
import static io.rxmicro.test.mockito.r2dbc.ErrorDuringSQLInvocationType.CREATE_STATEMENT_FAILED;
import static io.rxmicro.test.mockito.r2dbc.ErrorDuringSQLInvocationType.EXECUTE_STATEMENT_FAILED;
import static io.rxmicro.test.mockito.r2dbc.internal.AnyValues.ANY_BIND_VALUE;
import static io.rxmicro.test.mockito.r2dbc.internal.AnyValues.ANY_SQL_STATEMENT;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.internal.util.MockUtil.isMock;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
abstract class AbstractInvocationSQLMockFactory {

    final void validate(final ConnectionPool connectionPool) {
        if (!isMock(connectionPool)) {
            throw new InvalidTestConfigException("'httpClientFactory' must be Mockito mock!");
        }
    }

    final Optional<Connection> newConnectionMock(final ConnectionPool connectionPool,
                                                 final SQLParamsMock sqlParamsMock,
                                                 final Throwable throwable,
                                                 final ErrorDuringSQLInvocationType errorDuringSQLInvocationType) {
        final Connection connection = mock(Connection.class);
        if (!sqlParamsMock.isTransactional()) {
            if (errorDuringSQLInvocationType == CLOSE_CONNECTION_FAILED) {
                when(connection.close()).thenThrow(throwable);
            } else {
                when(connection.close()).thenReturn(Mono.empty());
            }
        } else if (errorDuringSQLInvocationType == CLOSE_CONNECTION_FAILED) {
            throw new InvalidPreparedMockException(
                    "Transactional connections do not close by repository method, " +
                            "thus '?' failed type can't be configured correctly", CLOSE_CONNECTION_FAILED);
        }
        if (errorDuringSQLInvocationType == CREATE_CONNECTION_FAILED) {
            when(connectionPool.create()).thenThrow(throwable);
            return Optional.empty();
        } else {
            when(connectionPool.create()).thenReturn(Mono.just(connection));
            return Optional.of(connection);
        }
    }

    final Optional<Statement> newStatementMock(final Connection connection,
                                               final SQLParamsMock sqlParamsMock,
                                               final Throwable throwable,
                                               final ErrorDuringSQLInvocationType errorDuringSQLInvocationType) {
        final Statement statement = mock(Statement.class);
        final Optional<String> sql = sqlParamsMock.getSql();
        final OngoingStubbing<Statement> ongoingCreateStubbing;
        if (sql.isPresent()) {
            ongoingCreateStubbing = when(connection.createStatement(sql.get()));
        } else {
            ongoingCreateStubbing = when(connection.createStatement(anyString(ANY_SQL_STATEMENT)));
        }
        if (errorDuringSQLInvocationType == CREATE_STATEMENT_FAILED) {
            ongoingCreateStubbing.thenThrow(throwable);
            return Optional.empty();
        } else {
            ongoingCreateStubbing.thenReturn(statement);
        }

        return bindStatementParams(statement, sqlParamsMock, throwable, errorDuringSQLInvocationType);
    }

    private Optional<Statement> bindStatementParams(final Statement statement,
                                                    final SQLParamsMock sqlParamsMock,
                                                    final Throwable throwable,
                                                    final ErrorDuringSQLInvocationType errorDuringSQLInvocationType) {
        if (sqlParamsMock.getBindParams().isEmpty()) {
            if (errorDuringSQLInvocationType == BIND_STATEMENT_ARGUMENTS_FAILED) {
                lenient().when(statement.bind(anyInt(), any(Object.class, ANY_BIND_VALUE))).thenThrow(throwable);
                return Optional.empty();
            } else {
                lenient().when(statement.bind(anyInt(), any(Object.class, ANY_BIND_VALUE))).thenReturn(statement);
            }
        } else {
            for (int i = 0; i < sqlParamsMock.getBindParams().size(); i++) {
                final Object param = sqlParamsMock.getBindParams().get(i);
                final OngoingStubbing<Statement> ongoingBindStubbing;
                if (param instanceof Null) {
                    ongoingBindStubbing = when(statement.bindNull(i, ((Null) param).getType()));
                } else {
                    ongoingBindStubbing = when(statement.bind(i, param));
                }
                if (errorDuringSQLInvocationType == BIND_STATEMENT_ARGUMENTS_FAILED) {
                    ongoingBindStubbing.thenThrow(throwable);
                    return Optional.empty();
                } else {
                    ongoingBindStubbing.thenReturn(statement);
                }
            }
        }
        return Optional.ofNullable(statement);
    }

    final Optional<Result> newResultMock(final Statement statement,
                                         final Throwable throwable,
                                         final ErrorDuringSQLInvocationType errorDuringSQLInvocationType) {
        final Result result = mock(Result.class);
        if (errorDuringSQLInvocationType == EXECUTE_STATEMENT_FAILED) {
            when(statement.execute()).thenThrow(throwable);
            return Optional.empty();
        } else {
            when(statement.execute()).thenAnswer((Answer<Publisher<? extends Result>>) invocation -> Mono.just(result));
            return Optional.of(result);
        }
    }
}
