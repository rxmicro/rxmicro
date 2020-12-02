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

package io.rxmicro.test.dbunit.local;

import io.rxmicro.logger.Logger;
import io.rxmicro.logger.LoggerFactory;
import org.dbunit.database.DatabaseConnection;

import java.sql.SQLException;
import java.util.function.Supplier;

import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @since 0.7
 */
public final class DatabaseConnectionHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseConnectionHelper.class);

    private static final ThreadLocal<DatabaseConnection> DATABASE_CONNECTION_THREAD_LOCAL = new ThreadLocal<>();

    private static final Supplier<Object> CURRENT_THREAD_NAME_SUPPLIER = () -> Thread.currentThread().getName();

    public static DatabaseConnection getCurrentDatabaseConnection() {
        final DatabaseConnection connection = getDatabaseConnectionFromThreadLocal();
        return require(connection, "Database connection is not configured for thread: '?'!", CURRENT_THREAD_NAME_SUPPLIER);
    }

    public static void setCurrentDatabaseConnection(final DatabaseConnection connection) {
        require(connection, "Database connection is not configured for thread: '?'!", CURRENT_THREAD_NAME_SUPPLIER);
        final DatabaseConnection prevDatabaseConnection = getDatabaseConnectionFromThreadLocal();
        if (prevDatabaseConnection != null) {
            closeDatabaseConnection(prevDatabaseConnection);
        }
        DATABASE_CONNECTION_THREAD_LOCAL.set(connection);
    }

    public static boolean isCurrentDatabaseConnectionPresent() {
        return getDatabaseConnectionFromThreadLocal() != null;
    }

    public static void releaseCurrentDatabaseConnection() {
        final DatabaseConnection databaseConnection = getDatabaseConnectionFromThreadLocal();
        try {
            if (databaseConnection != null) {
                closeDatabaseConnection(databaseConnection);
            }
        } finally {
            if (databaseConnection != null) {
                DATABASE_CONNECTION_THREAD_LOCAL.remove();
            }
        }
    }

    public static void closeDatabaseConnection(final DatabaseConnection connection) {
        try {
            connection.close();
        } catch (final SQLException ex) {
            LOGGER.warn(ex, "Close connection failed: ?", ex.getMessage());
        }
    }

    private static DatabaseConnection getDatabaseConnectionFromThreadLocal() {
        return DATABASE_CONNECTION_THREAD_LOCAL.get();
    }

    private DatabaseConnectionHelper() {
    }
}
