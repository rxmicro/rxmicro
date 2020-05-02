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

import io.rxmicro.common.meta.BuilderMethod;
import io.rxmicro.test.mockito.r2dbc.internal.AbstractSQLParamsMock;

import java.util.List;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class SQLParamsMock extends AbstractSQLParamsMock {

    private SQLParamsMock(final boolean transactional,
                          final String sql,
                          final List<Object> bindParams) {
        super(transactional, sql, bindParams);
    }

    /**
     * @author nedis
     * @link https://rxmicro.io
     * @since 0.1
     */
    @SuppressWarnings("UnusedReturnValue")
    public static final class Builder {

        private boolean transactional;

        private boolean anySql;

        private String sql;

        private List<Object> bindParams = List.of();

        @BuilderMethod
        public Builder setTransactional(final boolean transactional) {
            this.transactional = transactional;
            return this;
        }

        @BuilderMethod
        public Builder setAnySql() {
            this.anySql = true;
            this.sql = null;
            this.bindParams = List.of();
            return this;
        }

        @BuilderMethod
        public Builder setSql(final String sql) {
            this.sql = require(sql);
            this.anySql = false;
            return this;
        }

        @BuilderMethod
        public Builder setBindParams(final Object... bindParams) {
            require(bindParams);
            try {
                this.bindParams = List.of(bindParams);
            } catch (final NullPointerException e) {
                throw new IllegalArgumentException(
                        format("`null` is not allowed bind parameter. Use new ?(<TYPE>) instead.",
                                Null.class.getName())
                );
            }
            return this;
        }

        public SQLParamsMock build() {
            if (!anySql && sql == null) {
                throw new IllegalStateException("'setAnySql' or 'setSql' must be invoked!");
            }
            return new SQLParamsMock(transactional, sql, bindParams);
        }
    }
}
