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

package io.rxmicro.test.dbunit;

import io.rxmicro.config.ConfigException;
import io.rxmicro.test.dbunit.internal.component.JdbcUrlParser;
import io.rxmicro.test.dbunit.internal.db.postgres.RxMicroPostgresDatabaseConnection;
import org.dbunit.database.DatabaseConnection;

import java.util.Arrays;

import static io.rxmicro.common.util.Formats.format;
import static java.util.stream.Collectors.toList;

/**
 * @author nedis
 * @since 0.7
 */
public enum DatabaseType {

    POSTGRES(RxMicroPostgresDatabaseConnection.class, "jdbc:postgresql", "org.postgres.Driver", 5432);

    private final Class<? extends DatabaseConnection> databaseConnectionClass;

    private final String jdbcUrlPrefix;

    private final String defaultJdbcDriver;

    private final int defaultPort;

    DatabaseType(final Class<? extends DatabaseConnection> databaseConnectionClass,
                 final String jdbcUrlPrefix,
                 final String defaultJdbcDriver,
                 final int defaultPort) {
        this.databaseConnectionClass = databaseConnectionClass;
        this.jdbcUrlPrefix = jdbcUrlPrefix;
        this.defaultJdbcDriver = defaultJdbcDriver;
        this.defaultPort = defaultPort;
    }

    public Class<? extends DatabaseConnection> getDatabaseConnectionClass() {
        return databaseConnectionClass;
    }

    public String getDefaultJdbcDriver() {
        return defaultJdbcDriver;
    }

    public String getJdbcUrl(final String host,
                             final int port,
                             final String database) {
        return getJdbcUrl(host, (Object) port, database);
    }

    public String getJdbcUrl(final String host,
                             final String database) {
        return getJdbcUrl(host, defaultPort, database);
    }

    private String getJdbcUrl(final String host,
                              final Object port,
                              final String database) {
        return format("?://?:?/?", jdbcUrlPrefix, host, port, database);
    }

    public static TestDatabaseConfig parseJdbcUrl(final String jdbcUrl) {
        for (final DatabaseType databaseType : DatabaseType.values()) {
            if (jdbcUrl.startsWith(databaseType.jdbcUrlPrefix)) {
                return new JdbcUrlParser().parse(databaseType, jdbcUrl);
            }
        }
        throw new ConfigException(
                "Unsupported jdbc url: ?. Only following jdbc url supported: ?",
                jdbcUrl,
                Arrays.stream(DatabaseType.values())
                        .map(databaseType -> databaseType.getJdbcUrl("${host}", "${port}", "${databaseName}"))
                        .collect(toList())
        );
    }
}
