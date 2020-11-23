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

package io.rxmicro.test.dbunit.internal.component;

import io.rxmicro.config.ConfigException;
import io.rxmicro.test.dbunit.DatabaseType;
import io.rxmicro.test.dbunit.TestDatabaseConfig;

/**
 * @author nedis
 * @since 0.7
 */
public final class JdbcUrlParser {

    private static final String JDBC_FORMAT = "jdbc:${subProtocol}://${host}[:${port}]/${databaseName}";

    public TestDatabaseConfig parse(final DatabaseType databaseType,
                                    final String jdbcUrl) {
        final int index = jdbcUrl.indexOf("://");
        if (index == -1) {
            throw new ConfigException("Invalid jdbc url format: ?. Expected the following: '?'", jdbcUrl, JDBC_FORMAT);
        }
        final StringBuilder builder = new StringBuilder();
        boolean portFound = false;
        final TestDatabaseConfig config = new TestDatabaseConfig()
                .setType(databaseType);
        for (int i = index + 3; i < jdbcUrl.length(); i++) {
            final char ch = jdbcUrl.charAt(i);
            if (ch == ':') {
                setHost(jdbcUrl, config, builder);
                portFound = true;
            } else if (ch == '/') {
                setPortOrHost(jdbcUrl, config, builder, portFound);
                builder.delete(0, builder.length());
            } else {
                builder.append(ch);
            }
        }
        setDatabase(jdbcUrl, config, builder);
        return config;
    }

    private void setHost(final String jdbcUrl,
                         final TestDatabaseConfig config,
                         final StringBuilder builder) {
        if (builder.length() == 0) {
            throw new ConfigException("Missing host for jdbc url: ?. Expected the following: '?'", jdbcUrl, JDBC_FORMAT);
        }
        config.setHost(builder.toString());
        builder.delete(0, builder.length());
    }

    private void setPortOrHost(final String jdbcUrl,
                               final TestDatabaseConfig config,
                               final StringBuilder builder,
                               final boolean portFound) {
        if (builder.length() == 0) {
            if (portFound) {
                throw new ConfigException("Missing port for jdbc url: ?. Expected the following: '?'", jdbcUrl, JDBC_FORMAT);
            } else {
                throw new ConfigException("Missing host for jdbc url: ?. Expected the following: '?'", jdbcUrl, JDBC_FORMAT);
            }
        }
        if (portFound) {
            config.setPort(Integer.parseInt(builder.toString()));
        } else {
            config.setHost(builder.toString());
        }
    }

    private void setDatabase(final String jdbcUrl,
                             final TestDatabaseConfig config,
                             final StringBuilder builder) {
        if (builder.length() == 0) {
            throw new ConfigException("Missing database name for jdbc url: ?. Expected the following: '?'", jdbcUrl, JDBC_FORMAT);
        }
        config.setDatabase(builder.toString());
    }
}
