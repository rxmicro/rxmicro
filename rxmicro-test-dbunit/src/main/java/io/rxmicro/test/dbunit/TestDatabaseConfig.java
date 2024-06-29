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

import io.rxmicro.common.CommonConstants;
import io.rxmicro.common.meta.BuilderMethod;
import io.rxmicro.config.Configs;
import io.rxmicro.config.SingletonConfigClass;
import io.rxmicro.test.local.model.BaseTestConfig;
import io.rxmicro.validation.constraint.HostName;
import io.rxmicro.validation.constraint.Nullable;
import io.rxmicro.validation.constraint.Port;

import static io.rxmicro.test.dbunit.DatabaseType.POSTGRES;

/**
 * Allows configuring the connection options that used by the DBUnit to work with test database.
 *
 * @author nedis
 * @since 0.7
 */
@SingletonConfigClass
public final class TestDatabaseConfig extends BaseTestConfig implements Cloneable {

    private static final ThreadLocal<TestDatabaseConfig> CURRENT_TEST_DATABASE_CONFIG = new ThreadLocal<>();

    private DatabaseType type = POSTGRES;

    @Nullable
    private String jdbcDriver;

    @HostName
    private String host = CommonConstants.LOCALHOST;

    @Nullable
    @Port
    private Integer port;

    private String database;

    @Nullable
    private String schema;

    private String user;

    private CharSequence password;

    public TestDatabaseConfig() {
        super(TestDatabaseConfig.getDefaultNameSpace(TestDatabaseConfig.class));
    }

    /**
     * Returns the current instance of the {@link TestDatabaseConfig} class that used by the DBUnit to work with test database.
     *
     * <p>
     * This method uses the {@link ThreadLocal} variable to store the reference to the current instance of the {@link TestDatabaseConfig}.
     * It means that developer can use this method correctly for parallel tests.
     *
     * @return the current instance of the {@link TestDatabaseConfig} class that used by the DBUnit to work with test database.
     */
    public static TestDatabaseConfig getCurrentTestDatabaseConfig() {
        TestDatabaseConfig testDatabaseConfig = CURRENT_TEST_DATABASE_CONFIG.get();
        if (testDatabaseConfig == null) {
            testDatabaseConfig = Configs.getConfig(TestDatabaseConfig.class).clone();
            CURRENT_TEST_DATABASE_CONFIG.set(testDatabaseConfig);
        }
        return testDatabaseConfig;
    }

    /**
     * Remove the current instance of the {@link TestDatabaseConfig} from the {@link ThreadLocal} variable.
     */
    public static void releaseCurrentTestDatabaseConfig() {
        CURRENT_TEST_DATABASE_CONFIG.remove();
    }

    /**
     * Returns the JDBC driver full class name.
     *
     * @return the JDBC driver full class name.
     * @see java.sql.Driver
     */
    public String getJdbcDriver() {
        return jdbcDriver != null ? jdbcDriver : getType().getDefaultJdbcDriver();
    }

    /**
     * Sets the custom JDBC driver full class name.
     *
     * @param jdbcDriver the custom JDBC driver full class name.
     * @return the reference to this  {@link TestDatabaseConfig} instance
     * @see java.sql.Driver
     */
    @BuilderMethod
    public TestDatabaseConfig setJdbcDriver(final String jdbcDriver) {
        this.jdbcDriver = ensureValid(jdbcDriver);
        return this;
    }

    /**
     * Returns the {@link DatabaseType} that defines the standard JDBC settings for each supported database.
     *
     * @return the {@link DatabaseType} that defines the standard JDBC settings for each supported database.
     */
    public DatabaseType getType() {
        return type;
    }


    /**
     * Sets the {@link DatabaseType} that defines the standard JDBC settings for each supported database.
     *
     * @param type the {@link DatabaseType} that defines the standard JDBC settings for each supported database.
     * @return the reference to this  {@link TestDatabaseConfig} instance
     */
    @BuilderMethod
    public TestDatabaseConfig setType(final DatabaseType type) {
        this.type = ensureValid(type);
        return this;
    }

    /**
     * Returns the JDBC connection url.
     *
     * @return the JDBC connection url.
     * @see java.sql.DriverManager
     */
    public String getJdbcUrl() {
        if (port == null) {
            return getType().getJdbcUrl(getHost(), getDatabase());
        } else {
            return getType().getJdbcUrl(getHost(), port, getDatabase());
        }
    }

    /**
     * Sets the JDBC connection url.
     *
     * <p>
     * Alias for {@link #setUrl(String)} method.
     *
     * @param jdbcUrl the JDBC connection url.
     * @return the reference to this  {@link TestDatabaseConfig} instance
     */
    @BuilderMethod
    public TestDatabaseConfig setJdbcUrl(final String jdbcUrl) {
        final TestDatabaseConfig config = DatabaseType.parseJdbcUrl(jdbcUrl);
        this.type = ensureValidProperty("type", config.type);
        this.host = ensureValidProperty("host", config.host);
        this.port = ensureValidProperty("port", config.port);
        this.database = ensureValidProperty("database", config.database);
        return this;
    }

    /**
     * Sets the JDBC connection url.
     *
     * <p>
     * Alias for {@link #setJdbcUrl(String)} method.
     *
     * @param jdbcUrl the JDBC connection url.
     * @return the reference to this {@link TestDatabaseConfig} instance
     */
    @BuilderMethod
    public TestDatabaseConfig setUrl(final String jdbcUrl) {
        return setJdbcUrl(jdbcUrl);
    }

    /**
     * Returns the database host name or IP address.
     *
     * @return the database host name or IP address.
     */
    public String getHost() {
        return host;
    }

    /**
     * Sets the database host name or IP address.
     *
     * @param host the database host name or IP address.
     * @return the reference to this  {@link TestDatabaseConfig} instance
     */
    @BuilderMethod
    public TestDatabaseConfig setHost(final String host) {
        this.host = ensureValid(host);
        return this;
    }

    /**
     * Sets the custom database port.
     *
     * @param port the custom database port or {@code null} if default value should be applied.
     * @return the reference to this  {@link TestDatabaseConfig} instance
     */
    @BuilderMethod
    public TestDatabaseConfig setPort(final Integer port) {
        this.port = ensureValid(port);
        return this;
    }

    /**
     * Returns the database name.
     *
     * @return the database name.
     */
    public String getDatabase() {
        return database;
    }

    /**
     * Sets the database name.
     *
     * @param database the database name.
     * @return the reference to this  {@link TestDatabaseConfig} instance
     */
    @BuilderMethod
    public TestDatabaseConfig setDatabase(final String database) {
        this.database = ensureValid(database);
        return this;
    }

    /**
     * Returns {@code true} if database schema is configured.
     *
     * @return {@code true} if database schema is configured.
     */
    public boolean isSchemaPresent() {
        return schema != null;
    }

    /**
     * Returns the configured database schema.
     *
     * @return the configured database schema.
     */
    public String getSchema() {
        return schema;
    }

    /**
     * Sets the database schema.
     *
     * @param schema the database schema.
     * @return the reference to this  {@link TestDatabaseConfig} instance
     */
    @BuilderMethod
    public TestDatabaseConfig setSchema(final String schema) {
        this.schema = ensureValid(schema);
        return this;
    }

    /**
     * Returns the database user.
     *
     * @return the database user.
     */
    public String getUser() {
        return user;
    }

    /**
     * Sets the database user.
     *
     * @param user the database user.
     * @return the reference to this  {@link TestDatabaseConfig} instance
     */
    @BuilderMethod
    public TestDatabaseConfig setUser(final String user) {
        this.user = ensureValid(user);
        return this;
    }

    /**
     * Returns the user password.
     *
     * @return the user password.
     */
    public CharSequence getPassword() {
        return password;
    }

    /**
     * Sets the user password.
     *
     * @param password the user password.
     * @return the reference to this  {@link TestDatabaseConfig} instance
     */
    @BuilderMethod
    public TestDatabaseConfig setPassword(final CharSequence password) {
        this.password = ensureValid(password);
        return this;
    }

    @Override
    public TestDatabaseConfig clone() {
        try {
            return (TestDatabaseConfig) super.clone();
        } catch (final CloneNotSupportedException ex) {
            // this shouldn't happen, since we are Cloneable
            throw new InternalError(ex);
        }
    }

    @Override
    public String toString() {
        return "TestDatabaseConfig{" +
                "type=" + type +
                ", jdbcDriver='" + jdbcDriver + '\'' +
                ", host='" + host + '\'' +
                ", port=" + port +
                ", database='" + database + '\'' +
                ", schema='" + schema + '\'' +
                ", user='" + user + '\'' +
                ", password=" + password +
                '}';
    }
}
