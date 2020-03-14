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

import io.rxmicro.test.mockito.mongo.internal.AbstractUpdateOperationMock;
import io.rxmicro.test.mockito.mongo.internal.util.Validators;
import org.bson.Document;

import static io.rxmicro.test.mockito.mongo.internal.util.Validators.validateBson;

/**
 * @author nedis
 * @link http://rxmicro.io
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
     * @author nedis
     * @link http://rxmicro.io
     * @since 0.1
     */
    @SuppressWarnings("UnusedReturnValue")
    public static final class Builder {

        private boolean anyUpdate;

        private Document update;

        private boolean anyFilter;

        private Document filter;

        private boolean upsert;

        public Builder setAnyUpdate() {
            this.anyUpdate = true;
            this.update = null;
            return this;
        }

        public Builder setUpdate(final Document update) {
            this.update = validateBson(update, "update");
            this.anyUpdate = false;
            return this;
        }

        public Builder setUpdate(final String update) {
            this.update = validateBson(update, "update");
            this.anyUpdate = false;
            return this;
        }

        public Builder setAnyFilter() {
            this.anyFilter = true;
            this.filter = null;
            return this;
        }

        public Builder setFilter(final Document filter) {
            this.filter = Validators.validateBson(filter, "filter");
            this.anyFilter = false;
            return this;
        }

        public Builder setFilter(final String filter) {
            this.filter = validateBson(filter, "filter");
            this.anyFilter = false;
            return this;
        }

        public Builder setUpsert(final boolean upsert) {
            this.upsert = upsert;
            return this;
        }

        public UpdateOperationMock build() {
            if (!anyUpdate && update == null) {
                throw new IllegalStateException("'setUpdate' or 'setAnyUpdate' must be invoked!");
            }
            if (!anyFilter && filter == null) {
                throw new IllegalStateException("'setFilter' or 'setAnyFilter' must be invoked!");
            }
            return new UpdateOperationMock(anyUpdate, update, anyFilter, filter, upsert);
        }
    }
}
