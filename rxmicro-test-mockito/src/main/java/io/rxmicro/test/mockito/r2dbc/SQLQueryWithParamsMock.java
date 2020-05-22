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

import io.rxmicro.common.InvalidStateException;
import io.rxmicro.common.meta.BuilderMethod;
import io.rxmicro.test.mockito.r2dbc.internal.AbstractSQLParamsMock;

import java.util.List;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.common.util.Requires.require;

/**
 * The SQL query with parameters mock using <a href="https://site.mockito.org/">Mockito</a> testing framework.
 *
 * @author nedis
 * @see io.rxmicro.data.sql.operation.Select
 * @see io.rxmicro.data.sql.operation.Insert
 * @see io.rxmicro.data.sql.operation.Update
 * @see io.rxmicro.data.sql.operation.Delete
 * @see io.rxmicro.data.sql.model.reactor.Transaction
 * @see io.rxmicro.data.sql.model.rxjava3.Transaction
 * @see io.rxmicro.data.sql.model.completablefuture.Transaction
 * @see io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLRepository
 * @since 0.1
 */
public final class SQLQueryWithParamsMock extends AbstractSQLParamsMock {

    private SQLQueryWithParamsMock(final boolean transactional,
                                   final String sql,
                                   final List<Object> bindParams) {
        super(transactional, sql, bindParams);
    }

    /**
     * The builder for building an SQL query with parameters mock.
     *
     * @author nedis
     * @since 0.1
     */
    @SuppressWarnings("UnusedReturnValue")
    public static final class Builder {

        private boolean transactional;

        private boolean anySql;

        private String sql;

        private List<Object> bindParams = List.of();

        /**
         * Marks the SQL query with parameters mock as transactional.
         *
         * @param transactional the transactional flag
         * @return the reference to this {@link Builder} instance
         */
        @BuilderMethod
        public Builder setTransactional(final boolean transactional) {
            this.transactional = transactional;
            return this;
        }

        /**
         * Configures the SQL query with parameters mock that it will match to any SQL query.
         *
         * @return the reference to this {@link Builder} instance
         */
        @BuilderMethod
        public Builder setAnySql() {
            this.anySql = true;
            this.sql = null;
            this.bindParams = List.of();
            return this;
        }

        /**
         * Sets the SQL query for the the SQL query with parameters mock that it will match to an SQL query with the specified SQL query.
         *
         * @param sql the specified SQL query
         * @return the reference to this {@link Builder} instance
         * @throws NullPointerException if the specified SQL query is {@code null}
         */
        @BuilderMethod
        public Builder setSql(final String sql) {
            this.sql = require(sql);
            this.anySql = false;
            return this;
        }

        /**
         * Sets the SQL bind parameters for the the SQL query with parameters mock that it will match to an SQL query with
         * the specified SQL bind parameters.
         *
         * @param bindParams the specified SQL bind parameters
         * @return the reference to this {@link Builder} instance
         * @throws IllegalArgumentException if any bind parameter is {@code null}
         */
        @BuilderMethod
        public Builder setBindParams(final Object... bindParams) {
            for (final Object bindParam : bindParams) {
                if (bindParam == null) {
                    throw new IllegalArgumentException(
                            format("`null` is not allowed bind parameter. Use new ?(<TYPE>) instead.",
                                    Null.class.getName())
                    );
                }
            }
            this.bindParams = List.of(bindParams);
            return this;
        }

        /**
         * Builds the immutable SQL query with parameters mock instance using the configured {@link Builder} settings.
         *
         * @return the immutable SQL query with parameters mock instance using the configured {@link Builder} settings
         * @throws InvalidStateException if the current {@link Builder} contains invalid settings
         */
        public SQLQueryWithParamsMock build() {
            if (!anySql && sql == null) {
                throw new InvalidStateException("'setAnySql' or 'setSql' must be invoked!");
            }
            return new SQLQueryWithParamsMock(transactional, sql, bindParams);
        }
    }
}
