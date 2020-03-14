/*
 * Copyright (c) 2020. http://rxmicro.io
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

import io.rxmicro.test.mockito.mongo.internal.AbstractFindOperationMock;
import org.bson.Document;

import static io.rxmicro.test.mockito.mongo.internal.util.Validators.validateBson;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
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
     * @author nedis
     * @link http://rxmicro.io
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

        public Builder setAnyQuery() {
            this.anyQuery = true;
            this.query = null;
            return this;
        }

        public Builder setQuery(final Document query) {
            this.query = validateBson(query, "query");
            this.anyQuery = false;
            return this;
        }

        public Builder setQuery(final String query) {
            this.query = validateBson(query, "query");
            this.anyQuery = false;
            return this;
        }

        public Builder setProjection(final Document projection) {
            this.projection = validateBson(projection, "projection");
            return this;
        }

        public Builder setProjection(final String projection) {
            this.projection = validateBson(projection, "projection");
            return this;
        }

        public Builder setHint(final Document hint) {
            this.hint = validateBson(hint, "hint");
            return this;
        }

        public Builder setHint(final String hint) {
            this.hint = validateBson(hint, "hint");
            return this;
        }

        public Builder setSort(final Document sort) {
            this.sort = validateBson(sort, "sort");
            return this;
        }

        public Builder setSort(final String sort) {
            this.sort = validateBson(sort, "sort");
            return this;
        }

        public Builder setLimit(final int limit) {
            if (limit < 0) {
                throw new IllegalArgumentException("'limit' must be >= 0");
            }
            this.limit = limit;
            return this;
        }

        public Builder setSkip(final int skip) {
            if (skip < 0) {
                throw new IllegalArgumentException("'skip' must be >= 0");
            }
            this.skip = skip;
            return this;
        }

        public FindOperationMock build() {
            return new FindOperationMock(anyQuery, query, projection, hint, sort, limit, skip);
        }
    }
}
