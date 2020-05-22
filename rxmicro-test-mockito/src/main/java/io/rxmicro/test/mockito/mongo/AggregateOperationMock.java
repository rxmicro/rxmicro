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
import io.rxmicro.test.mockito.mongo.internal.AbstractAggregateOperationMock;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static io.rxmicro.test.mockito.mongo.internal.util.Validators.validateBson;

/**
 * The Mongo {@link io.rxmicro.data.mongo.operation.Aggregate} operation mock using
 * <a href="https://site.mockito.org/">Mockito</a> testing framework.
 *
 * @author nedis
 * @see io.rxmicro.data.mongo.operation.Aggregate
 * @see io.rxmicro.data.mongo.MongoRepository
 * @since 0.1
 */
public final class AggregateOperationMock extends AbstractAggregateOperationMock {

    private AggregateOperationMock(final List<Document> pipeline,
                                   final Document hint,
                                   final boolean allowDiskUse) {
        super(pipeline, hint, allowDiskUse);
    }

    /**
     * The builder for building a Mongo {@link io.rxmicro.data.mongo.operation.Aggregate} operation mock.
     *
     * @author nedis
     * @since 0.1
     */
    @SuppressWarnings("UnusedReturnValue")
    public static final class Builder {

        private final List<Document> pipeline = new ArrayList<>();

        private boolean anyPipeline;

        private Document hint;

        private boolean allowDiskUse;

        /**
         * Configures the Mongo {@link io.rxmicro.data.mongo.operation.Aggregate} operation mock that it will match to
         * a Mongo {@link io.rxmicro.data.mongo.operation.Aggregate} operation with any pipelines.
         *
         * @return the reference to this {@link Builder} instance
         */
        @BuilderMethod
        public Builder setAnyPipeline() {
            this.anyPipeline = true;
            this.pipeline.clear();
            return this;
        }

        /**
         * Adds the specified {@link Document} to the pipeline list.
         *
         * <p>
         * The Mongo {@link io.rxmicro.data.mongo.operation.Aggregate} operation mock that it will match to
         * a Mongo {@link io.rxmicro.data.mongo.operation.Aggregate} operation with all pipelines added using this method.
         *
         * @param pipeline the adding {@link Document} pipeline
         * @return the reference to this {@link Builder} instance
         * @throws NullPointerException if the adding {@link Document} pipeline is {@code null}
         */
        @BuilderMethod
        public Builder addPipeline(final Document pipeline) {
            this.pipeline.add(validateBson(pipeline, "pipeline"));
            this.anyPipeline = false;
            return this;
        }

        /**
         * Adds the specified pipeline to the pipeline list.
         *
         * <p>
         * The Mongo {@link io.rxmicro.data.mongo.operation.Aggregate} operation mock that it will match to
         * a Mongo {@link io.rxmicro.data.mongo.operation.Aggregate} operation with all pipelines added using this method.
         *
         * @param pipeline the adding pipeline
         * @return the reference to this {@link Builder} instance
         * @throws NullPointerException if the adding pipeline is {@code null}
         * @throws IllegalArgumentException if the adding pipeline is blank
         * @throws org.bson.json.JsonParseException if the adding pipeline has invalid JSON structure
         */
        @BuilderMethod
        public Builder addPipeline(final String pipeline) {
            this.pipeline.add(validateBson(pipeline, "pipeline"));
            this.anyPipeline = false;
            return this;
        }

        /**
         * Sets the hint for the Mongo {@link io.rxmicro.data.mongo.operation.Aggregate} operation mock that it will match to
         * a Mongo {@link io.rxmicro.data.mongo.operation.Aggregate} operation with the specified hint value.
         *
         * @param hint the specified hint value
         * @return the reference to this {@link Builder} instance
         * @throws NullPointerException if the specified hint value is {@code null}
         */
        @BuilderMethod
        public Builder setHint(final Document hint) {
            this.hint = validateBson(hint, "hint");
            return this;
        }

        /**
         * Sets the hint for the Mongo {@link io.rxmicro.data.mongo.operation.Aggregate} operation mock that it will match to
         * a Mongo {@link io.rxmicro.data.mongo.operation.Aggregate} operation with the specified hint value.
         *
         * @param hint the specified hint value
         * @return the reference to this {@link Builder} instance
         * @throws NullPointerException if the specified hint value is {@code null}
         * @throws IllegalArgumentException if the specified hint is blank
         * @throws org.bson.json.JsonParseException if the specified hint value has invalid JSON structure
         */
        @BuilderMethod
        public Builder setHint(final String hint) {
            this.hint = validateBson(hint, "hint");
            return this;
        }

        /**
         * Sets the {@code allowDiskUse} option for the Mongo {@link io.rxmicro.data.mongo.operation.Aggregate} operation mock
         * that it will match to a Mongo {@link io.rxmicro.data.mongo.operation.Aggregate} operation with
         * the specified {@code allowDiskUse} option.
         *
         * @param allowDiskUse the specified option
         * @return the reference to this {@link Builder} instance
         */
        @BuilderMethod
        public Builder setAllowDiskUse(final boolean allowDiskUse) {
            this.allowDiskUse = allowDiskUse;
            return this;
        }

        /**
         * Builds the immutable Mongo {@link io.rxmicro.data.mongo.operation.Aggregate} operation mock
         * instance using the configured {@link Builder} settings.
         *
         * @return the immutable Mongo {@link io.rxmicro.data.mongo.operation.Aggregate} operation mock instance
         * @throws InvalidStateException if the current {@link Builder} contains invalid settings
         */
        public AggregateOperationMock build() {
            if (!anyPipeline && pipeline.isEmpty()) {
                throw new InvalidStateException("'setAnyPipeline' or 'addPipeline' must be called!");
            }
            return new AggregateOperationMock(pipeline, hint, allowDiskUse);
        }
    }
}
