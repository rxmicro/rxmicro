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

import io.rxmicro.test.mockito.mongo.internal.AbstractInsertOperationMock;
import org.bson.Document;

import static io.rxmicro.test.mockito.mongo.internal.util.Validators.validateBson;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public final class InsertOperationMock extends AbstractInsertOperationMock {

    private InsertOperationMock(final Document document) {
        super(document);
    }

    /**
     * @author nedis
     * @link http://rxmicro.io
     * @since 0.1
     */
    @SuppressWarnings("UnusedReturnValue")
    public static final class Builder {

        private boolean anyDocument;

        private Document document;

        public Builder setAnyDocument() {
            this.anyDocument = true;
            this.document = null;
            return this;
        }

        public Builder setDocument(final Document document) {
            this.document = validateBson(document, "document");
            this.anyDocument = false;
            return this;
        }

        public InsertOperationMock build() {
            if (!anyDocument && document == null) {
                throw new IllegalStateException("'setDocument' or 'setAnyDocument' must be invoked!");
            }
            return new InsertOperationMock(document);
        }
    }
}
