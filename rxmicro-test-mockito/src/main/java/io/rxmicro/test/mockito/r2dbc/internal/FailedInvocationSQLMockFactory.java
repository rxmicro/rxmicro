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

package io.rxmicro.test.mockito.r2dbc.internal;

import io.r2dbc.pool.ConnectionPool;
import io.rxmicro.test.mockito.InvalidPreparedMockException;
import io.rxmicro.test.mockito.r2dbc.ErrorDuringSQLInvocationType;
import io.rxmicro.test.mockito.r2dbc.SQLParamsMock;

import java.util.function.BiFunction;

import static io.rxmicro.test.mockito.internal.CommonMatchers.any;
import static io.rxmicro.test.mockito.r2dbc.ErrorDuringSQLInvocationType.RETURN_RESULT_SET_FAILED;
import static io.rxmicro.test.mockito.r2dbc.ErrorDuringSQLInvocationType.RETURN_ROWS_UPDATED_FAILED;
import static io.rxmicro.test.mockito.r2dbc.internal.AnyValues.ANY_MAP_RESULT_FUNCTION;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class FailedInvocationSQLMockFactory extends AbstractInvocationSQLMockFactory {

    @SuppressWarnings("unchecked")
    public void prepare(final ConnectionPool connectionPool,
                        final SQLParamsMock sqlParamsMock,
                        final Throwable throwable,
                        final ErrorDuringSQLInvocationType errorDuringSQLInvocationType) {
        validate(connectionPool);
        newConnectionMock(connectionPool, sqlParamsMock, throwable, errorDuringSQLInvocationType)
                .flatMap(connection -> newStatementMock(connection, sqlParamsMock, throwable, errorDuringSQLInvocationType))
                .flatMap(statement -> newResultMock(statement, throwable, errorDuringSQLInvocationType))
                .ifPresent(result -> {
                    if (errorDuringSQLInvocationType == RETURN_RESULT_SET_FAILED) {
                        when(result.map(any(BiFunction.class, ANY_MAP_RESULT_FUNCTION))).thenThrow(throwable);
                        lenient().when(result.map(any(BiFunction.class, ANY_MAP_RESULT_FUNCTION))).thenAnswer(invocation -> {
                            throw new InvalidPreparedMockException(
                                    "Expected '?' but actual is '?'", RETURN_RESULT_SET_FAILED, RETURN_ROWS_UPDATED_FAILED
                            );
                        });
                    } else if (errorDuringSQLInvocationType == RETURN_ROWS_UPDATED_FAILED) {
                        when(result.getRowsUpdated()).thenThrow(throwable);
                        lenient().when(result.map(any(BiFunction.class, ANY_MAP_RESULT_FUNCTION))).thenAnswer(invocation -> {
                            throw new InvalidPreparedMockException(
                                    "Expected '?' but actual is '?'", RETURN_ROWS_UPDATED_FAILED, RETURN_RESULT_SET_FAILED
                            );
                        });
                    } else {
                        throw new IllegalArgumentException("Unsupported failedType: " + errorDuringSQLInvocationType);
                    }
                });
    }

}
