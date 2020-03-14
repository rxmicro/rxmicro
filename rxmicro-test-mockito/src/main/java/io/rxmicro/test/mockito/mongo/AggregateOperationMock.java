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

import io.rxmicro.test.mockito.mongo.internal.AbstractAggregateOperationMock;
import io.rxmicro.test.mockito.mongo.internal.util.Validators;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static io.rxmicro.test.mockito.mongo.internal.util.Validators.validateBson;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public final class AggregateOperationMock extends AbstractAggregateOperationMock {

    public AggregateOperationMock(final List<Document> pipeline,
                                  final Document hint,
                                  final boolean allowDiskUse) {
        super(pipeline, hint, allowDiskUse);
    }

    /**
     * @author nedis
     * @link http://rxmicro.io
     * @since 0.1
     */
    @SuppressWarnings("UnusedReturnValue")
    public static final class Builder {

        private final List<Document> pipeline = new ArrayList<>();

        private boolean anyPipeline;

        private Document hint;

        private boolean allowDiskUse;

        public Builder setAnyPipeline() {
            this.anyPipeline = true;
            this.pipeline.clear();
            return this;
        }

        public Builder addPipeline(final Document pipeline) {
            this.pipeline.add(Validators.validateBson(pipeline, "pipeline"));
            this.anyPipeline = false;
            return this;
        }

        public Builder addPipeline(final String pipeline) {
            this.pipeline.add(validateBson(pipeline, "pipeline"));
            this.anyPipeline = false;
            return this;
        }

        public Builder setHint(final Document hint) {
            this.hint = Validators.validateBson(hint, "hint");
            return this;
        }

        public Builder setHint(final String hint) {
            this.hint = validateBson(hint, "hint");
            return this;
        }

        public Builder setAllowDiskUse(final boolean allowDiskUse) {
            this.allowDiskUse = allowDiskUse;
            return this;
        }

        public AggregateOperationMock build() {
            if (!anyPipeline && pipeline.isEmpty()) {
                throw new IllegalStateException("'setAnyPipeline' or 'addPipeline' must be called!");
            }
            return new AggregateOperationMock(pipeline, hint, allowDiskUse);
        }
    }
}
