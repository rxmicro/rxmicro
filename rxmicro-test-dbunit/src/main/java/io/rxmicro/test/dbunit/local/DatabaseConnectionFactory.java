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

import io.rxmicro.common.CheckedWrapperException;
import io.rxmicro.common.ImpossibleException;
import io.rxmicro.common.InvalidStateException;
import io.rxmicro.test.dbunit.TestDatabaseConfig;
import org.dbunit.database.DatabaseConnection;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static io.rxmicro.common.util.Formats.format;

/**
 * @author nedis
 * @since 0.7
 */
public final class DatabaseConnectionFactory {

    private static final ConnectionProvider CONNECTION_PROVIDER = config ->
            DriverManager.getConnection(config.getJdbcUrl(), config.getUser(), config.getPassword().toString());

    private static final Map<DatabaseConnection, String> CONNECTION_SETTINGS_CACHE = new HashMap<>();

    public static DatabaseConnection createNewDatabaseConnection(final TestDatabaseConfig config) {
        try {
            final Connection connection = CONNECTION_PROVIDER.getConnection(config);
            final DatabaseConnection databaseConnection;
            if (config.isSchemaPresent()) {
                final Constructor<? extends DatabaseConnection> constructor =
                        config.getType().getDatabaseConnectionClass().getDeclaredConstructor(Connection.class, String.class, Boolean.TYPE);
                databaseConnection = constructor.newInstance(connection, config.getSchema(), true);
            } else {
                final Constructor<? extends DatabaseConnection> constructor =
                        config.getType().getDatabaseConnectionClass().getDeclaredConstructor(Connection.class);
                databaseConnection = constructor.newInstance(connection);
            }
            CONNECTION_SETTINGS_CACHE.put(
                    databaseConnection,
                    format("??user=?&password=?", config.getJdbcUrl(), '?', config.getUser(), config.getPassword())
            );
            return databaseConnection;
        } catch (final SQLException ex) {
            throw new CheckedWrapperException(ex, "Can't retrieve jdbc connection using url: '?': ?", config.getJdbcUrl(), ex.getMessage());
        } catch (final InvocationTargetException ex) {
            throw new CheckedWrapperException(ex, "Exception during creation of database connection class: ?", ex.getMessage());
        } catch (final InstantiationException ex) {
            throw new ImpossibleException(ex, "DatabaseConnectionClass must be instantiable class!");
        } catch (final NoSuchMethodException ex) {
            throw new ImpossibleException(ex, "Required constructor must be present!");
        } catch (final IllegalAccessException ex) {
            throw new ImpossibleException(ex, "Required constructor must be accessible!");
        }
    }

    public static String getCashedConnectionSetting(final DatabaseConnection databaseConnection) {
        return Optional.ofNullable(CONNECTION_SETTINGS_CACHE.get(databaseConnection)).orElseThrow(() -> {
            throw new InvalidStateException("Connection setting was removed or not created for ? connection", databaseConnection);
        });
    }

    static void removeDatabaseConnectionFromSettingCache(final DatabaseConnection databaseConnection) {
        CONNECTION_SETTINGS_CACHE.remove(databaseConnection);
    }

    private DatabaseConnectionFactory() {
    }

    /**
     * @author nedis
     * @since 0.7
     */
    public interface ConnectionProvider {

        Connection getConnection(TestDatabaseConfig config) throws SQLException;
    }
}
