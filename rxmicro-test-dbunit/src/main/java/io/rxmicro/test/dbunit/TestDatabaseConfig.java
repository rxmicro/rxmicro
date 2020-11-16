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

import io.rxmicro.common.meta.BuilderMethod;
import io.rxmicro.config.Config;
import io.rxmicro.config.Configs;

import static io.rxmicro.common.util.Requires.require;
import static io.rxmicro.config.Networks.validatePort;

/**
 * @author nedis
 * @since 0.7
 */
public final class TestDatabaseConfig extends Config {

    private static final int PORT_NOT_SPECIFIED = -1;

    private static final ThreadLocal<TestDatabaseConfig> CURRENT_TEST_DATABASE_CONFIG = new ThreadLocal<>();

    private DatabaseType type;

    private String jdbcDriver;

    private String host = "localhost";

    private int port = PORT_NOT_SPECIFIED;

    private String database;

    private String schema;

    private String user;

    private CharSequence password;

    public static TestDatabaseConfig getCurrentTestDatabaseConfig() {
        TestDatabaseConfig testDatabaseConfig = CURRENT_TEST_DATABASE_CONFIG.get();
        if (testDatabaseConfig == null) {
            new Configs.Builder().buildIfNotConfigured();
            testDatabaseConfig = Configs.getConfig(TestDatabaseConfig.class);
            CURRENT_TEST_DATABASE_CONFIG.set(testDatabaseConfig);
        }
        return testDatabaseConfig;
    }

    public static void releaseCurrentTestDatabaseConfig() {
        CURRENT_TEST_DATABASE_CONFIG.remove();
    }

    public String getJdbcDriver() {
        return jdbcDriver != null ? jdbcDriver : getType().getDefaultJdbcDriver();
    }

    @BuilderMethod
    public TestDatabaseConfig setJdbcDriver(final String jdbcDriver) {
        this.jdbcDriver = jdbcDriver;
        return this;
    }

    public DatabaseType getType() {
        return require(type, "Required 'type' property is null! Set database type!");
    }

    @BuilderMethod
    public TestDatabaseConfig setType(final DatabaseType type) {
        this.type = type;
        return this;
    }

    public String getJdbcUrl() {
        if (port == PORT_NOT_SPECIFIED) {
            return getType().getJdbcUrl(getHost(), getDatabase());
        } else {
            return getType().getJdbcUrl(getHost(), port, getDatabase());
        }
    }

    @BuilderMethod
    public TestDatabaseConfig setJdbcUrl(final String jdbcUrl){
        final TestDatabaseConfig config = DatabaseType.parseJdbcUrl(jdbcUrl);
        this.type = config.type;
        this.host = config.host;
        this.port = config.port;
        this.database = config.database;
        return this;
    }

    /**
     * Alias for {@link #setJdbcUrl(String)}.
     *
     * @param jdbcUrl the jdbc url
     * @return the reference to this {@link TestDatabaseConfig} instance
     */
    @BuilderMethod
    public TestDatabaseConfig setUrl(final String jdbcUrl){
        return setJdbcUrl(jdbcUrl);
    }

    public String getHost() {
        return require(host, "Required 'host' property is null! Provide a valid database host!");
    }

    @BuilderMethod
    public TestDatabaseConfig setHost(final String host) {
        this.host = require(host, "Required 'host' property can't be null! Provide a valid database host!");
        return this;
    }

    @BuilderMethod
    public TestDatabaseConfig setPort(final int port) {
        this.port = validatePort(port);
        return this;
    }

    public String getDatabase() {
        return require(database, "Required 'database' property is null! Provide a valid database name!");
    }

    @BuilderMethod
    public TestDatabaseConfig setDatabase(final String database) {
        this.database = require(database, "Required 'database' property can't be null! Provide a valid database name!");
        return this;
    }

    public boolean isSchemaPresent(){
        return schema != null;
    }

    public String getSchema() {
        return require(schema, "Optional 'schema' property is null! Provide a valid schema name!");
    }

    @BuilderMethod
    public TestDatabaseConfig setSchema(final String schema) {
        this.schema = require(schema, "Required 'schema' property can't be null! Provide a valid schema name!");
        return this;
    }

    public String getUser() {
        return require(user, "Required 'username' property is null! Provide a valid database username!");
    }

    @BuilderMethod
    public TestDatabaseConfig setUser(final String user) {
        this.user = require(user, "Required 'username' property can't be null! Provide a valid database username!");
        return this;
    }

    public CharSequence getPassword() {
        return require(password, "Required 'password' property is null! Provide a valid database password!");
    }

    @BuilderMethod
    public TestDatabaseConfig setPassword(final CharSequence password) {
        require(password, "Required 'password' property can't be null! Provide a valid database password!");
        this.password = password;
        return this;
    }
}
