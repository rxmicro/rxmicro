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

import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @since 0.7
 */
public final class DatabaseConnectionHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseConnectionHelper.class);

    private final static ThreadLocal<DatabaseConnection> DATABASE_CONNECTION_THREAD_LOCAL = new ThreadLocal<>();

    public static DatabaseConnection getCurrentDatabaseConnection() {
        return require(
                DATABASE_CONNECTION_THREAD_LOCAL.get(),
                "Database connection is not configured for thread: '?'!",
                () -> Thread.currentThread().getName()
        );
    }

    public static void setCurrentDatabaseConnection(final DatabaseConnection connection) {
        DATABASE_CONNECTION_THREAD_LOCAL.set(
                require(
                        connection,
                        "Database connection is not configured for thread: '?'!",
                        () -> Thread.currentThread().getName()
                )
        );
    }

    public static boolean isCurrentDatabaseConnectionPresent() {
        return DATABASE_CONNECTION_THREAD_LOCAL.get() != null;
    }

    public static void releaseCurrentDatabaseConnection() {
        final DatabaseConnection connection = DATABASE_CONNECTION_THREAD_LOCAL.get();
        try {
            if (connection != null) {
                closeDatabaseConnection(connection);
            }
        } finally {
            DATABASE_CONNECTION_THREAD_LOCAL.remove();
        }
    }

    public static void closeDatabaseConnection(final DatabaseConnection connection){
        try {
            connection.close();
        } catch (final SQLException ex) {
            LOGGER.warn(ex, "Close connection failed: ?", ex.getMessage());
        }
    }
}
