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

package io.rxmicro.test.mockito.mongo;

import io.rxmicro.common.meta.BuilderMethod;
import io.rxmicro.test.mockito.mongo.internal.AbstractDistinctOperationMock;
import org.bson.Document;

import static io.rxmicro.common.util.Requires.require;
import static io.rxmicro.test.mockito.mongo.internal.util.Validators.validateBson;
import static io.rxmicro.test.mockito.mongo.internal.util.Validators.validateString;

/**
 * The Mongo {@link io.rxmicro.data.mongo.operation.Distinct} operation mock using
 * <a href="https://site.mockito.org/">Mockito</a> testing framework.
 *
 * @author nedis
 * @param <T> the type of result for this {@link io.rxmicro.data.mongo.operation.Distinct} operation
 * @see io.rxmicro.data.mongo.operation.Distinct
 * @see io.rxmicro.data.mongo.MongoRepository
 * @since 0.1
 */
public final class DistinctOperationMock<T> extends AbstractDistinctOperationMock<T> {

    private DistinctOperationMock(final Class<T> resultClass,
                                  final String field,
                                  final Document query,
                                  final boolean anyQuery) {
        super(resultClass, field, query, anyQuery);
    }

    /**
     * The builder for building a Mongo {@link io.rxmicro.data.mongo.operation.Distinct} operation mock.
     *
     * @author nedis
     * @param <T> the type of result for this {@link io.rxmicro.data.mongo.operation.Distinct} operation
     * @since 0.1
     */
    @SuppressWarnings("UnusedReturnValue")
    public static final class Builder<T> {

        private Class<T> resultClass;

        private String field;

        private boolean anyQuery;

        private Document query;

        /**
         * Sets the result class for the Mongo {@link io.rxmicro.data.mongo.operation.Distinct} operation mock.
         *
         * @param resultClass the result class
         * @return the reference to this {@link Builder} instance
         * @throws NullPointerException if the specified result class is {@code null}
         */
        @BuilderMethod
        public Builder<T> setResultClass(final Class<T> resultClass) {
            this.resultClass = require(resultClass);
            return this;
        }

        /**
         * Sets the specified field for the Mongo {@link io.rxmicro.data.mongo.operation.Distinct} operation mock that it will match to
         * a Mongo {@link io.rxmicro.data.mongo.operation.Distinct} operation with the specified field.
         *
         * @param field the specified field
         * @return the reference to this {@link Builder} instance
         * @throws NullPointerException if the specified field is {@code null}
         * @throws IllegalArgumentException if the specified field is blank
         */
        @BuilderMethod
        public Builder<T> setField(final String field) {
            this.field = validateString(field, "field");
            return this;
        }

        /**
         * Configures the Mongo {@link io.rxmicro.data.mongo.operation.Distinct} operation mock that it will match to
         * a Mongo {@link io.rxmicro.data.mongo.operation.Distinct} operation with any query.
         *
         * @return the reference to this {@link Builder} instance
         */
        @BuilderMethod
        public Builder<T> setAnyQuery() {
            this.anyQuery = true;
            this.query = null;
            return this;
        }

        /**
         * Sets the query for the Mongo {@link io.rxmicro.data.mongo.operation.Distinct} operation mock that it will match to
         * a Mongo {@link io.rxmicro.data.mongo.operation.Distinct} operation with the specified query.
         *
         * @param query the specified query
         * @return the reference to this {@link Builder} instance
         * @throws NullPointerException if the specified query is {@code null}
         */
        @BuilderMethod
        public Builder<T> setQuery(final Document query) {
            this.query = validateBson(query, "query");
            this.anyQuery = false;
            return this;
        }

        /**
         * Sets the query for the Mongo {@link io.rxmicro.data.mongo.operation.Distinct} operation mock that it will match to
         * a Mongo {@link io.rxmicro.data.mongo.operation.Distinct} operation with the specified query.
         *
         * @param query the specified query
         * @return the reference to this {@link Builder} instance
         * @throws NullPointerException if the specified query is {@code null}
         * @throws IllegalArgumentException if the specified query is blank
         * @throws org.bson.json.JsonParseException if the specified query has invalid JSON structure
         */
        @BuilderMethod
        public Builder<T> setQuery(final String query) {
            this.query = validateBson(query, "query");
            this.anyQuery = false;
            return this;
        }

        /**
         * Builds the immutable Mongo {@link io.rxmicro.data.mongo.operation.Distinct} operation mock
         * instance using the configured {@link Builder} settings.
         *
         * @return the immutable Mongo {@link io.rxmicro.data.mongo.operation.Distinct} operation mock instance
         */
        public DistinctOperationMock<T> build() {
            return new DistinctOperationMock<>(resultClass, field, query, anyQuery);
        }
    }
}
