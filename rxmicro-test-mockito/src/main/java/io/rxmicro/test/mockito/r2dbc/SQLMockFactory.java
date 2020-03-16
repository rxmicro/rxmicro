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

package io.rxmicro.test.mockito.r2dbc;

import io.r2dbc.pool.ConnectionPool;
import io.rxmicro.test.mockito.r2dbc.internal.FailedInvocationSQLMockFactory;
import io.rxmicro.test.mockito.r2dbc.internal.SuccessInvocationSQLMockFactory;

import java.util.List;

import static io.rxmicro.test.mockito.r2dbc.ErrorDuringSQLInvocationType.RETURN_RESULT_SET_FAILED;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class SQLMockFactory {

    private static final SuccessInvocationSQLMockFactory SUCCESS_INVOCATION_SQL_MOCK_FACTORY =
            new SuccessInvocationSQLMockFactory();

    private static final FailedInvocationSQLMockFactory FAILED_INVOCATION_SQL_MOCK_FACTORY =
            new FailedInvocationSQLMockFactory();

    public static void prepareSQLOperationMocks(final ConnectionPool connectionPool,
                                                final SQLParamsMock sqlParamsMock,
                                                final List<List<Object>> resultRows) {
        SUCCESS_INVOCATION_SQL_MOCK_FACTORY.prepare(connectionPool, sqlParamsMock, resultRows);
    }

    @SafeVarargs
    public static void prepareSQLOperationMocks(final ConnectionPool connectionPool,
                                                final SQLParamsMock sqlParamsMock,
                                                final List<Object>... resultRows) {
        SUCCESS_INVOCATION_SQL_MOCK_FACTORY.prepare(connectionPool, sqlParamsMock, asList(resultRows));
    }

    public static void prepareSQLOperationMocks(final ConnectionPool connectionPool,
                                                final SQLParamsMock sqlParamsMock,
                                                final Object... resultRow) {
        SUCCESS_INVOCATION_SQL_MOCK_FACTORY.prepare(connectionPool, sqlParamsMock, singletonList(asList(resultRow)));
    }

    public static void prepareSQLOperationMocks(final ConnectionPool connectionPool,
                                                final SQLParamsMock sqlParamsMock,
                                                final int rowsUpdated) {
        SUCCESS_INVOCATION_SQL_MOCK_FACTORY.prepare(connectionPool, sqlParamsMock, rowsUpdated);
    }

    public static void prepareSQLOperationMocks(final ConnectionPool connectionPool,
                                                final SQLParamsMock sqlParamsMock,
                                                final Throwable throwable) {
        prepareSQLOperationMocks(connectionPool, sqlParamsMock, throwable, RETURN_RESULT_SET_FAILED);
    }

    public static void prepareSQLOperationMocks(final ConnectionPool connectionPool,
                                                final SQLParamsMock sqlParamsMock,
                                                final Throwable throwable,
                                                final ErrorDuringSQLInvocationType errorDuringSQLInvocationTYpe) {
        FAILED_INVOCATION_SQL_MOCK_FACTORY.prepare(connectionPool, sqlParamsMock, throwable, errorDuringSQLInvocationTYpe);
    }

    private SQLMockFactory() {
    }
}
