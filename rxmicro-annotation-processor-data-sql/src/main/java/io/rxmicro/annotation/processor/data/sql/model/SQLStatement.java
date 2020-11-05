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

package io.rxmicro.annotation.processor.data.sql.model;

import io.rxmicro.annotation.processor.common.util.UsedByFreemarker;
import io.rxmicro.common.meta.BuilderMethod;

import java.util.List;

import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @since 0.1
 */
public final class SQLStatement {

    private final String originalSql;

    private final List<String> resultColumns;

    private final boolean defaultColumnOrder;

    private final String sqlExpression;

    private final List<BindParameter> bindParams;

    private SQLStatement(final String originalSql,
                         final List<String> resultColumns,
                         final boolean defaultColumnOrder,
                         final String sqlExpression,
                         final List<BindParameter> bindParams) {
        this.originalSql = originalSql;
        this.resultColumns = require(resultColumns);
        this.defaultColumnOrder = defaultColumnOrder;
        this.sqlExpression = require(sqlExpression);
        this.bindParams = require(bindParams);
    }

    public boolean isDefaultColumnOrder() {
        return defaultColumnOrder;
    }

    public List<String> getResultColumns() {
        return resultColumns;
    }

    @UsedByFreemarker({
            "$$PostgreSQLRepositorySelectMethodBodyTemplate.javaftl",
            "$$PostgreSQLRepositoryInsertMethodBodyTemplate.javaftl"
    })
    public String getOriginalSql() {
        return originalSql;
    }

    @UsedByFreemarker({
            "$$PostgreSQLRepositorySelectMethodBodyTemplate.javaftl",
            "$$PostgreSQLRepositoryInsertMethodBodyTemplate.javaftl"
    })
    public String getSqlExpression() {
        return sqlExpression;
    }

    @UsedByFreemarker({
            "$$PostgreSQLRepositorySelectMethodBodyTemplate.javaftl",
            "$$PostgreSQLRepositoryInsertMethodBodyTemplate.javaftl"
    })
    public List<BindParameter> getBindParams() {
        return bindParams;
    }

    /**
     * @author nedis
     * @since 0.1
     */
    @SuppressWarnings("UnusedReturnValue")
    public static final class Builder {

        private String originalSql;

        private List<String> resultColumns = List.of();

        private boolean defaultColumnOrder = true;

        private String sqlExpression;

        private List<BindParameter> bindParams = List.of();

        @BuilderMethod
        public Builder setOriginalSql(final String originalSql) {
            this.originalSql = require(originalSql);
            return this;
        }

        @BuilderMethod
        public Builder setResultColumns(final List<String> resultColumns) {
            this.resultColumns = require(resultColumns);
            return this;
        }

        @BuilderMethod
        public Builder setDefaultColumnOrder(final boolean defaultColumnOrder) {
            this.defaultColumnOrder = defaultColumnOrder;
            return this;
        }

        @BuilderMethod
        public Builder setSqlExpression(final String sqlExpression) {
            this.sqlExpression = require(sqlExpression);
            return this;
        }

        @BuilderMethod
        public Builder setBindParams(final List<BindParameter> bindParams) {
            this.bindParams = require(bindParams);
            return this;
        }

        public SQLStatement build() {
            return new SQLStatement(originalSql, resultColumns, defaultColumnOrder, sqlExpression, bindParams);
        }
    }
}
