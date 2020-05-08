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
 * Helper class with useful static methods that help to configure a SQL mocks.
 * <p>
 * This class must be used for testing purposes
 *
 * @author nedis
 * @since 0.1
 * @see io.rxmicro.data.sql.operation.Select
 * @see io.rxmicro.data.sql.operation.Insert
 * @see io.rxmicro.data.sql.operation.Update
 * @see io.rxmicro.data.sql.operation.Delete
 * @see io.rxmicro.data.sql.model.reactor.Transaction
 * @see io.rxmicro.data.sql.model.rxjava3.Transaction
 * @see io.rxmicro.data.sql.model.completablefuture.Transaction
 * @see io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLRepository
 */
public final class SQLMockFactory {

    private static final SuccessInvocationSQLMockFactory SUCCESS_INVOCATION_SQL_MOCK_FACTORY =
            new SuccessInvocationSQLMockFactory();

    private static final FailedInvocationSQLMockFactory FAILED_INVOCATION_SQL_MOCK_FACTORY =
            new FailedInvocationSQLMockFactory();

    /**
     * Configures the specified {@link ConnectionPool} mock.
     * The specified result rows will be returned
     * if the RxMicro framework will execute the specified {@link SQLQueryWithParamsMock}.
     * <p>
     * (<i>This method requires that {@link ConnectionPool} will be a mock!</i>)
     *
     * @param connectionPool the configuring connection pool mock
     * @param sqlQueryWithParamsMock the specified SQL query with parameters mock
     * @param resultRows the specified result rows that must be returned
     * @throws io.rxmicro.test.local.InvalidTestConfigException if the specified parameters are invalid
     */
    public static void prepareSQLOperationMocks(final ConnectionPool connectionPool,
                                                final SQLQueryWithParamsMock sqlQueryWithParamsMock,
                                                final List<List<Object>> resultRows) {
        SUCCESS_INVOCATION_SQL_MOCK_FACTORY.prepare(connectionPool, sqlQueryWithParamsMock, resultRows);
    }

    /**
     * Configures the specified {@link ConnectionPool} mock.
     * The specified result rows will be returned
     * if the RxMicro framework will execute the specified {@link SQLQueryWithParamsMock}.
     * <p>
     * (<i>This method requires that {@link ConnectionPool} will be a mock!</i>)
     *
     * @param connectionPool the configuring connection pool mock
     * @param sqlQueryWithParamsMock the specified SQL query with parameters mock
     * @param resultRows the specified result rows that must be returned
     * @throws io.rxmicro.test.local.InvalidTestConfigException if the specified parameters are invalid
     */
    @SafeVarargs
    public static void prepareSQLOperationMocks(final ConnectionPool connectionPool,
                                                final SQLQueryWithParamsMock sqlQueryWithParamsMock,
                                                final List<Object>... resultRows) {
        SUCCESS_INVOCATION_SQL_MOCK_FACTORY.prepare(connectionPool, sqlQueryWithParamsMock, asList(resultRows));
    }

    /**
     * Configures the specified {@link ConnectionPool} mock.
     * The specified single row will be returned
     * if the RxMicro framework will execute the specified {@link SQLQueryWithParamsMock}.
     * <p>
     * (<i>This method requires that {@link ConnectionPool} will be a mock!</i>)
     *
     * @param connectionPool the configuring connection pool mock
     * @param sqlQueryWithParamsMock the specified SQL query with parameters mock
     * @param resultRow the specified single row that must be returned
     * @throws io.rxmicro.test.local.InvalidTestConfigException if the specified parameters are invalid
     */
    public static void prepareSQLOperationMocks(final ConnectionPool connectionPool,
                                                final SQLQueryWithParamsMock sqlQueryWithParamsMock,
                                                final Object... resultRow) {
        SUCCESS_INVOCATION_SQL_MOCK_FACTORY.prepare(connectionPool, sqlQueryWithParamsMock, singletonList(asList(resultRow)));
    }

    /**
     * Configures the specified {@link ConnectionPool} mock.
     * The specified updated row count will be returned
     * if the RxMicro framework will execute the specified {@link SQLQueryWithParamsMock}.
     * <p>
     * (<i>This method requires that {@link ConnectionPool} will be a mock!</i>)
     *
     * @param connectionPool the configuring connection pool mock
     * @param sqlQueryWithParamsMock the specified SQL query with parameters mock
     * @param rowsUpdated the specified updated row count that must be returned
     * @throws io.rxmicro.test.local.InvalidTestConfigException if the specified parameters are invalid
     */
    public static void prepareSQLOperationMocks(final ConnectionPool connectionPool,
                                                final SQLQueryWithParamsMock sqlQueryWithParamsMock,
                                                final int rowsUpdated) {
        SUCCESS_INVOCATION_SQL_MOCK_FACTORY.prepare(connectionPool, sqlQueryWithParamsMock, rowsUpdated);
    }

    /**
     * Configures the specified {@link ConnectionPool} mock.
     * The error signal with the specified {@link Throwable} during returning the result set for SQL query will be returned
     * if the RxMicro framework will execute the specified {@link SQLQueryWithParamsMock}.
     * <p>
     * (<i>This method requires that {@link ConnectionPool} will be a mock!</i>)
     *
     * @param connectionPool the configuring connection pool mock
     * @param sqlQueryWithParamsMock the specified SQL query with parameters mock
     * @param throwable the specified throwable
     * @throws io.rxmicro.test.local.InvalidTestConfigException if the specified parameters are invalid
     */
    public static void prepareSQLOperationMocks(final ConnectionPool connectionPool,
                                                final SQLQueryWithParamsMock sqlQueryWithParamsMock,
                                                final Throwable throwable) {
        prepareSQLOperationMocks(connectionPool, sqlQueryWithParamsMock, throwable, RETURN_RESULT_SET_FAILED);
    }

    /**
     * Configures the specified {@link ConnectionPool} mock.
     * The error signal with the specified {@link Throwable} during the specified {@link ErrorDuringSQLInvocationType} will be returned
     * if the RxMicro framework will execute the specified {@link SQLQueryWithParamsMock}.
     * <p>
     * (<i>This method requires that {@link ConnectionPool} will be a mock!</i>)
     *
     * @param connectionPool the configuring connection pool mock
     * @param sqlQueryWithParamsMock the specified SQL query with parameters mock
     * @param throwable the specified throwable
     * @param errorDuringSQLInvocationType stage for emulation an error with the specified throwable
     * @throws io.rxmicro.test.local.InvalidTestConfigException if the specified parameters are invalid
     */
    public static void prepareSQLOperationMocks(final ConnectionPool connectionPool,
                                                final SQLQueryWithParamsMock sqlQueryWithParamsMock,
                                                final Throwable throwable,
                                                final ErrorDuringSQLInvocationType errorDuringSQLInvocationType) {
        FAILED_INVOCATION_SQL_MOCK_FACTORY.prepare(connectionPool, sqlQueryWithParamsMock, throwable, errorDuringSQLInvocationType);
    }

    private SQLMockFactory() {
    }
}
