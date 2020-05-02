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
import io.rxmicro.test.mockito.mongo.internal.AbstractCountDocumentsOperationMock;
import org.bson.Document;

import static io.rxmicro.test.mockito.mongo.internal.util.Validators.validateBson;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class CountDocumentsOperationMock extends AbstractCountDocumentsOperationMock {

    private CountDocumentsOperationMock(final boolean anyQuery,
                                        final Document query,
                                        final Document hint,
                                        final int limit,
                                        final int skip) {
        super(anyQuery, query, hint, limit, skip);
    }

    /**
     * @author nedis
     * @link https://rxmicro.io
     * @since 0.1
     */
    @SuppressWarnings("UnusedReturnValue")
    public static final class Builder {

        private boolean anyQuery;

        private Document query;

        private Document hint;

        private int limit = -1;

        private int skip = -1;

        @BuilderMethod
        public Builder setAnyQuery() {
            this.anyQuery = true;
            this.query = null;
            return this;
        }

        @BuilderMethod
        public Builder setQuery(final Document query) {
            this.query = validateBson(query, "query");
            this.anyQuery = false;
            return this;
        }

        @BuilderMethod
        public Builder setQuery(final String query) {
            this.query = validateBson(query, "query");
            this.anyQuery = false;
            return this;
        }

        @BuilderMethod
        public Builder setHint(final Document hint) {
            this.hint = validateBson(hint, "hint");
            return this;
        }

        @BuilderMethod
        public Builder setHint(final String hint) {
            this.hint = validateBson(hint, "hint");
            return this;
        }

        @BuilderMethod
        public Builder setLimit(final int limit) {
            if (limit < 0) {
                throw new IllegalArgumentException("'limit' must be >= 0");
            }
            this.limit = limit;
            return this;
        }

        @BuilderMethod
        public Builder setSkip(final int skip) {
            if (skip < 0) {
                throw new IllegalArgumentException("'skip' must be >= 0");
            }
            this.skip = skip;
            return this;
        }

        public CountDocumentsOperationMock build() {
            return new CountDocumentsOperationMock(anyQuery, query, hint, limit, skip);
        }
    }
}
