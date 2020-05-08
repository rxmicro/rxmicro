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

import io.rxmicro.common.InvalidStateException;
import io.rxmicro.common.meta.BuilderMethod;
import io.rxmicro.test.mockito.mongo.internal.AbstractDeleteOperationMock;
import org.bson.Document;

import static io.rxmicro.test.mockito.mongo.internal.util.Validators.validateBson;

/**
 * The Mongo {@link io.rxmicro.data.mongo.operation.Delete} operation mock using
 * <a href="https://site.mockito.org/">Mockito</a> testing framework
 *
 * @author nedis
 * @since 0.1
 * @see io.rxmicro.data.mongo.operation.Delete
 * @see io.rxmicro.data.mongo.MongoRepository
 */
public final class DeleteOperationMock extends AbstractDeleteOperationMock {

    private DeleteOperationMock(final Document filter) {
        super(filter);
    }

    /**
     * The builder for building a Mongo {@link io.rxmicro.data.mongo.operation.Delete} operation mock
     *
     * @author nedis
     * @since 0.1
     */
    @SuppressWarnings("UnusedReturnValue")
    public static final class Builder {

        private boolean anyFilter;

        private Document filter;

        /**
         * Configures the Mongo {@link io.rxmicro.data.mongo.operation.Delete} operation mock that it will match to
         * a Mongo {@link io.rxmicro.data.mongo.operation.Delete} operation with any filter
         *
         * @return the reference to this {@link Builder} instance
         */
        @BuilderMethod
        public Builder setAnyFilter() {
            this.anyFilter = true;
            this.filter = null;
            return this;
        }

        /**
         * Sets the query for the Mongo {@link io.rxmicro.data.mongo.operation.Delete} operation mock that it will match to
         * a Mongo {@link io.rxmicro.data.mongo.operation.Delete} operation with the specified filter
         *
         * @param filter the specified filter
         * @return the reference to this {@link Builder} instance
         * @throws NullPointerException if the specified filter is {@code null}
         */
        @BuilderMethod
        public Builder setFilter(final Document filter) {
            this.filter = validateBson(filter, "filter");
            this.anyFilter = false;
            return this;
        }

        /**
         * Sets the query for the Mongo {@link io.rxmicro.data.mongo.operation.Delete} operation mock that it will match to
         * a Mongo {@link io.rxmicro.data.mongo.operation.Delete} operation with the specified filter
         *
         * @param filter the specified filter
         * @return the reference to this {@link Builder} instance
         * @throws NullPointerException if the specified filter is {@code null}
         * @throws IllegalArgumentException if the specified filter is blank
         * @throws org.bson.json.JsonParseException if the specified filter has invalid JSON structure
         */
        @BuilderMethod
        public Builder setFilter(final String filter) {
            this.filter = validateBson(filter, "filter");
            this.anyFilter = false;
            return this;
        }

        /**
         * Builds the immutable Mongo {@link io.rxmicro.data.mongo.operation.Delete} operation mock
         * instance using the configured {@link Builder} settings
         *
         * @return the immutable Mongo {@link io.rxmicro.data.mongo.operation.Delete} operation mock instance
         * @throws InvalidStateException if the current {@link Builder} contains invalid settings
         */
        public DeleteOperationMock build() {
            if (!anyFilter && filter == null) {
                throw new InvalidStateException("'setFilter' or 'setAnyFilter' must be invoked!");
            }
            return new DeleteOperationMock(filter);
        }
    }
}
