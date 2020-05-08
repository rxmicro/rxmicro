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
import io.rxmicro.test.mockito.mongo.internal.AbstractFindOperationMock;
import org.bson.Document;

import static io.rxmicro.test.mockito.mongo.internal.util.Validators.validateBson;

/**
 * The Mongo {@link io.rxmicro.data.mongo.operation.Find} operation mock using
 * <a href="https://site.mockito.org/">Mockito</a> testing framework
 *
 * @author nedis
 * @since 0.1
 * @see io.rxmicro.data.mongo.operation.Find
 * @see io.rxmicro.data.mongo.MongoRepository
 */
public final class FindOperationMock extends AbstractFindOperationMock {

    private FindOperationMock(final boolean anyQuery,
                              final Document query,
                              final Document projection,
                              final Document hint,
                              final Document sort,
                              final int limit,
                              final int skip) {
        super(anyQuery, query, projection, hint, sort, limit, skip);
    }

    /**
     * The builder for building a Mongo {@link io.rxmicro.data.mongo.operation.Find} operation mock
     *
     * @author nedis
     * @since 0.1
     */
    @SuppressWarnings("UnusedReturnValue")
    public static final class Builder {

        private boolean anyQuery;

        private Document query;

        private Document projection;

        private Document hint;

        private Document sort;

        private int limit = -1;

        private int skip = -1;

        /**
         * Configures the Mongo {@link io.rxmicro.data.mongo.operation.Find} operation mock that it will match to
         * a Mongo {@link io.rxmicro.data.mongo.operation.Find} operation with any query
         *
         * @return the reference to this {@link Builder} instance
         */
        @BuilderMethod
        public Builder setAnyQuery() {
            this.anyQuery = true;
            this.query = null;
            return this;
        }

        /**
         * Sets the query for the Mongo {@link io.rxmicro.data.mongo.operation.Find} operation mock that it will match to
         * a Mongo {@link io.rxmicro.data.mongo.operation.Find} operation with the specified query
         *
         * @param query the specified query
         * @return the reference to this {@link Builder} instance
         * @throws NullPointerException if the specified query is {@code null}
         */
        @BuilderMethod
        public Builder setQuery(final Document query) {
            this.query = validateBson(query, "query");
            this.anyQuery = false;
            return this;
        }

        /**
         * Sets the query for the Mongo {@link io.rxmicro.data.mongo.operation.Find} operation mock that it will match to
         * a Mongo {@link io.rxmicro.data.mongo.operation.Find} operation with the specified query
         *
         * @param query the specified query
         * @return the reference to this {@link Builder} instance
         * @throws NullPointerException if the specified query is {@code null}
         * @throws IllegalArgumentException if the specified query is blank
         * @throws org.bson.json.JsonParseException if the specified query has invalid JSON structure
         */
        @BuilderMethod
        public Builder setQuery(final String query) {
            this.query = validateBson(query, "query");
            this.anyQuery = false;
            return this;
        }

        /**
         * Sets the projection for the Mongo {@link io.rxmicro.data.mongo.operation.Find} operation mock that it will match to
         * a Mongo {@link io.rxmicro.data.mongo.operation.Find} operation with the specified projection
         *
         * @param projection the specified projection
         * @return the reference to this {@link Builder} instance
         * @throws NullPointerException if the specified query is {@code null}
         */
        @BuilderMethod
        public Builder setProjection(final Document projection) {
            this.projection = validateBson(projection, "projection");
            return this;
        }

        /**
         * Sets the projection for the Mongo {@link io.rxmicro.data.mongo.operation.Find} operation mock that it will match to
         * a Mongo {@link io.rxmicro.data.mongo.operation.Find} operation with the specified projection
         *
         * @param projection the specified projection
         * @return the reference to this {@link Builder} instance
         * @throws NullPointerException if the specified query is {@code null}
         * @throws IllegalArgumentException if the specified query is blank
         * @throws org.bson.json.JsonParseException if the specified query has invalid JSON structure
         */
        @BuilderMethod
        public Builder setProjection(final String projection) {
            this.projection = validateBson(projection, "projection");
            return this;
        }

        /**
         * Sets the hint for the Mongo {@link io.rxmicro.data.mongo.operation.Find} operation mock that it will match to
         * a Mongo {@link io.rxmicro.data.mongo.operation.Find} operation with the specified hint value
         *
         * @param hint the specified hint value
         * @return the reference to this {@link Builder} instance
         * @throws NullPointerException if the specified hint value is {@code null}
         */
        @BuilderMethod
        public Builder setHint(final Document hint) {
            this.hint = validateBson(hint, "hint");
            return this;
        }

        /**
         * Sets the hint for the Mongo {@link io.rxmicro.data.mongo.operation.Find} operation mock that it will match to
         * a Mongo {@link io.rxmicro.data.mongo.operation.Find} operation with the specified hint value
         *
         * @param hint the specified hint value
         * @return the reference to this {@link Builder} instance
         * @throws NullPointerException if the specified hint value is {@code null}
         * @throws IllegalArgumentException if the specified hint is blank
         * @throws org.bson.json.JsonParseException if the specified hint value has invalid JSON structure
         */
        @BuilderMethod
        public Builder setHint(final String hint) {
            this.hint = validateBson(hint, "hint");
            return this;
        }

        /**
         * Sets the sort expression for the Mongo {@link io.rxmicro.data.mongo.operation.Find} operation mock that it will match to
         * a Mongo {@link io.rxmicro.data.mongo.operation.Find} operation with the specified sort expression
         *
         * @param sort the specified sort expression
         * @return the reference to this {@link Builder} instance
         * @throws NullPointerException if the specified sort expression is {@code null}
         */
        @BuilderMethod
        public Builder setSort(final Document sort) {
            this.sort = validateBson(sort, "sort");
            return this;
        }

        /**
         * Sets the sort expression for the Mongo {@link io.rxmicro.data.mongo.operation.Find} operation mock that it will match to
         * a Mongo {@link io.rxmicro.data.mongo.operation.Find} operation with the specified sort expression
         *
         * @param sort the specified sort expression
         * @return the reference to this {@link Builder} instance
         * @throws NullPointerException if the specified sort expression is {@code null}
         * @throws IllegalArgumentException if the specified sort expression is blank
         * @throws org.bson.json.JsonParseException if the specified sort expression has invalid JSON structure
         */
        @BuilderMethod
        public Builder setSort(final String sort) {
            this.sort = validateBson(sort, "sort");
            return this;
        }

        /**
         * Sets the limit for the Mongo {@link io.rxmicro.data.mongo.operation.Find} operation mock that it will match to
         * a Mongo {@link io.rxmicro.data.mongo.operation.Find} operation with the specified limit value
         *
         * @param limit the specified limit value
         * @return the reference to this {@link Builder} instance
         * @throws IllegalArgumentException if the specified limit value is invalid
         */
        @BuilderMethod
        public Builder setLimit(final int limit) {
            if (limit < 0) {
                throw new IllegalArgumentException("'limit' must be >= 0");
            }
            this.limit = limit;
            return this;
        }

        /**
         * Sets the skip for the Mongo {@link io.rxmicro.data.mongo.operation.Find} operation mock that it will match to
         * a Mongo {@link io.rxmicro.data.mongo.operation.Find} operation with the specified skip value
         *
         * @param skip the specified limit value
         * @return the reference to this {@link Builder} instance
         * @throws IllegalArgumentException if the specified skip value is invalid
         */
        @BuilderMethod
        public Builder setSkip(final int skip) {
            if (skip < 0) {
                throw new IllegalArgumentException("'skip' must be >= 0");
            }
            this.skip = skip;
            return this;
        }

        /**
         * Builds the immutable Mongo {@link io.rxmicro.data.mongo.operation.Find} operation mock
         * instance using the configured {@link Builder} settings
         *
         * @return the immutable Mongo {@link io.rxmicro.data.mongo.operation.Find} operation mock instance
         */
        public FindOperationMock build() {
            return new FindOperationMock(anyQuery, query, projection, hint, sort, limit, skip);
        }
    }
}
