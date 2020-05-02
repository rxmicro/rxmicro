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
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class DistinctOperationMock<T> extends AbstractDistinctOperationMock<T> {

    public DistinctOperationMock(final Class<T> resultClass,
                                 final String field,
                                 final Document query,
                                 final boolean anyQuery) {
        super(resultClass, field, query, anyQuery);
    }

    /**
     * @author nedis
     * @link https://rxmicro.io
     * @since 0.1
     */
    @SuppressWarnings("UnusedReturnValue")
    public static final class Builder<T> {

        private Class<T> resultClass;

        private String field;

        private boolean anyQuery;

        private Document query;

        @BuilderMethod
        public Builder<T> setResultClass(final Class<T> resultClass) {
            this.resultClass = require(resultClass);
            return this;
        }

        @BuilderMethod
        public Builder<T> setField(final String field) {
            this.field = validateString(field, "field");
            return this;
        }

        @BuilderMethod
        public Builder<T> setAnyQuery() {
            this.anyQuery = true;
            this.query = null;
            return this;
        }

        @BuilderMethod
        public Builder<T> setQuery(final Document query) {
            this.query = validateBson(query, "query");
            this.anyQuery = false;
            return this;
        }

        @BuilderMethod
        public Builder<T> setQuery(final String query) {
            this.query = validateBson(query, "query");
            this.anyQuery = false;
            return this;
        }

        public DistinctOperationMock<T> build() {
            return new DistinctOperationMock<>(resultClass, field, query, anyQuery);
        }
    }
}
