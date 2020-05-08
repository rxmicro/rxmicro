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
import io.rxmicro.test.mockito.mongo.internal.AbstractInsertOperationMock;
import org.bson.Document;

import static io.rxmicro.test.mockito.mongo.internal.util.Validators.validateBson;

/**
 * The Mongo {@link io.rxmicro.data.mongo.operation.Insert} operation mock using
 * <a href="https://site.mockito.org/">Mockito</a> testing framework
 *
 * @author nedis
 * @since 0.1
 * @see io.rxmicro.data.mongo.operation.Insert
 * @see io.rxmicro.data.mongo.MongoRepository
 */
public final class InsertOperationMock extends AbstractInsertOperationMock {

    private InsertOperationMock(final Document document) {
        super(document);
    }

    /**
     * The builder for building a Mongo {@link io.rxmicro.data.mongo.operation.Insert} operation mock
     *
     * @author nedis
     * @since 0.1
     */
    @SuppressWarnings("UnusedReturnValue")
    public static final class Builder {

        private boolean anyDocument;

        private Document document;

        /**
         * Configures the Mongo {@link io.rxmicro.data.mongo.operation.Insert} operation mock that it will match to
         * a Mongo {@link io.rxmicro.data.mongo.operation.Insert} operation with any inserting document
         *
         * @return the reference to this {@link Builder} instance
         */
        @BuilderMethod
        public Builder setAnyDocument() {
            this.anyDocument = true;
            this.document = null;
            return this;
        }

        /**
         * Sets the document for the Mongo {@link io.rxmicro.data.mongo.operation.Insert} operation mock that it will match to
         * a Mongo {@link io.rxmicro.data.mongo.operation.Insert} operation with the specified inserting document
         *
         * @param document the specified document
         * @return the reference to this {@link Builder} instance
         * @throws NullPointerException if the specified query is {@code null}
         */
        @BuilderMethod
        public Builder setDocument(final Document document) {
            this.document = validateBson(document, "document");
            this.anyDocument = false;
            return this;
        }

        /**
         * Builds the immutable Mongo {@link io.rxmicro.data.mongo.operation.Insert} operation mock
         * instance using the configured {@link Builder} settings
         *
         * @return the immutable Mongo {@link io.rxmicro.data.mongo.operation.Insert} operation mock instance
         * @throws InvalidStateException if the current {@link Builder} contains invalid settings
         */
        public InsertOperationMock build() {
            if (!anyDocument && document == null) {
                throw new InvalidStateException("'setDocument' or 'setAnyDocument' must be invoked!");
            }
            return new InsertOperationMock(document);
        }
    }
}
