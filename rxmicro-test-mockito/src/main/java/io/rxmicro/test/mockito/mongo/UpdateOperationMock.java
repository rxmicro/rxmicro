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
import io.rxmicro.test.mockito.mongo.internal.AbstractUpdateOperationMock;
import org.bson.Document;

import static io.rxmicro.test.mockito.mongo.internal.util.Validators.validateBson;

/**
 * The Mongo {@link io.rxmicro.data.mongo.operation.Update} operation mock using
 * <a href="https://site.mockito.org/">Mockito</a> testing framework.
 *
 * @author nedis
 * @see io.rxmicro.data.mongo.operation.Update
 * @see io.rxmicro.data.mongo.MongoRepository
 * @since 0.1
 */
public final class UpdateOperationMock extends AbstractUpdateOperationMock {

    private UpdateOperationMock(final boolean anyUpdate,
                                final Document update,
                                final boolean anyFilter,
                                final Document filter,
                                final boolean upsert) {
        super(anyUpdate, update, anyFilter, filter, upsert);
    }

    /**
     * The builder for building a Mongo {@link io.rxmicro.data.mongo.operation.Update} operation mock.
     *
     * @author nedis
     * @since 0.1
     */
    @SuppressWarnings("UnusedReturnValue")
    public static final class Builder {

        private boolean anyUpdate;

        private Document update;

        private boolean anyFilter;

        private Document filter;

        private boolean upsert;

        /**
         * Configures the Mongo {@link io.rxmicro.data.mongo.operation.Update} operation mock that it will match to
         * a Mongo {@link io.rxmicro.data.mongo.operation.Update} operation with any update expression.
         *
         * @return the reference to this {@link Builder} instance
         */
        @BuilderMethod
        public Builder setAnyUpdate() {
            this.anyUpdate = true;
            this.update = null;
            return this;
        }

        /**
         * Sets the update document for the Mongo {@link io.rxmicro.data.mongo.operation.Find} operation mock that it will match to
         * a Mongo {@link io.rxmicro.data.mongo.operation.Find} operation with the specified update document.
         *
         * @param update the specified update document
         * @return the reference to this {@link Builder} instance
         * @throws NullPointerException if the specified query is {@code null}
         */
        @BuilderMethod
        public Builder setUpdate(final Document update) {
            this.update = validateBson(update, "update");
            this.anyUpdate = false;
            return this;
        }

        /**
         * Sets the update expression for the Mongo {@link io.rxmicro.data.mongo.operation.Find} operation mock that it will match to
         * a Mongo {@link io.rxmicro.data.mongo.operation.Find} operation with the specified update expression.
         *
         * @param update the specified update document
         * @return the reference to this {@link Builder} instance
         * @throws NullPointerException if the specified query is {@code null}
         * @throws IllegalArgumentException if the specified update expression is blank
         * @throws org.bson.json.JsonParseException if the specified update expression has invalid JSON structure
         */
        @BuilderMethod
        public Builder setUpdate(final String update) {
            this.update = validateBson(update, "update");
            this.anyUpdate = false;
            return this;
        }

        /**
         * Configures the Mongo {@link io.rxmicro.data.mongo.operation.Update} operation mock that it will match to
         * a Mongo {@link io.rxmicro.data.mongo.operation.Update} operation with any filter.
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
         * Sets the query for the Mongo {@link io.rxmicro.data.mongo.operation.Update} operation mock that it will match to
         * a Mongo {@link io.rxmicro.data.mongo.operation.Update} operation with the specified filter.
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
         * Sets the query for the Mongo {@link io.rxmicro.data.mongo.operation.Update} operation mock that it will match to
         * a Mongo {@link io.rxmicro.data.mongo.operation.Update} operation with the specified filter.
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
         * Sets the {@code upsert} option for the Mongo {@link io.rxmicro.data.mongo.operation.Update} operation mock
         * that it will match to a Mongo {@link io.rxmicro.data.mongo.operation.Update} operation with
         * the specified {@code upsert} option.
         *
         * @param upsert the specified option
         * @return the reference to this {@link Builder} instance
         */
        @BuilderMethod
        public Builder setUpsert(final boolean upsert) {
            this.upsert = upsert;
            return this;
        }

        /**
         * Builds the immutable Mongo {@link io.rxmicro.data.mongo.operation.Update} operation mock
         * instance using the configured {@link Builder} settings.
         *
         * @return the immutable Mongo {@link io.rxmicro.data.mongo.operation.Update} operation mock instance
         * @throws InvalidStateException if the current {@link Builder} contains invalid settings
         */
        public UpdateOperationMock build() {
            if (!anyUpdate && update == null) {
                throw new InvalidStateException("'setUpdate' or 'setAnyUpdate' must be invoked!");
            }
            if (!anyFilter && filter == null) {
                throw new InvalidStateException("'setFilter' or 'setAnyFilter' must be invoked!");
            }
            return new UpdateOperationMock(anyUpdate, update, anyFilter, filter, upsert);
        }
    }
}
