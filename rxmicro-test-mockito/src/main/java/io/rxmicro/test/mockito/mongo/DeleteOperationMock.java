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
import io.rxmicro.test.mockito.mongo.internal.AbstractDeleteOperationMock;
import org.bson.Document;

import static io.rxmicro.test.mockito.mongo.internal.util.Validators.validateBson;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class DeleteOperationMock extends AbstractDeleteOperationMock {

    private DeleteOperationMock(final Document filter) {
        super(filter);
    }

    /**
     * @author nedis
     * @link https://rxmicro.io
     * @since 0.1
     */
    @SuppressWarnings("UnusedReturnValue")
    public static final class Builder {

        private boolean anyFilter;

        private Document filter;

        @BuilderMethod
        public Builder setAnyFilter() {
            this.anyFilter = true;
            this.filter = null;
            return this;
        }

        @BuilderMethod
        public Builder setFilter(final Document filter) {
            this.filter = validateBson(filter, "filter");
            this.anyFilter = false;
            return this;
        }

        @BuilderMethod
        public Builder setFilter(final String filter) {
            this.filter = validateBson(filter, "filter");
            this.anyFilter = false;
            return this;
        }

        public DeleteOperationMock build() {
            if (!anyFilter && filter == null) {
                throw new IllegalStateException("'setFilter' or 'setAnyFilter' must be invoked!");
            }
            return new DeleteOperationMock(filter);
        }
    }
}
