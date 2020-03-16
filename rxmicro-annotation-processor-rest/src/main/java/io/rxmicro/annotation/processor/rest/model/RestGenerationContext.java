/*
 * Copyright 2019 https://rxmicro.io
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

package io.rxmicro.annotation.processor.rest.model;

import java.util.List;

import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class RestGenerationContext {

    private final List<MappedRestObjectModelClass> fromHttpDataModelClasses;

    private final List<MappedRestObjectModelClass> toHttpDataModelClasses;

    private RestGenerationContext(final List<MappedRestObjectModelClass> fromHttpDataModelClasses,
                                  final List<MappedRestObjectModelClass> toHttpDataModelClasses) {
        this.fromHttpDataModelClasses = require(fromHttpDataModelClasses);
        this.toHttpDataModelClasses = require(toHttpDataModelClasses);
    }

    public List<MappedRestObjectModelClass> getFromHttpDataModelClasses() {
        return fromHttpDataModelClasses;
    }

    public List<MappedRestObjectModelClass> getToHttpDataModelClasses() {
        return toHttpDataModelClasses;
    }

    /**
     * @author nedis
     * @link https://rxmicro.io
     * @since 0.1
     */
    @SuppressWarnings("UnusedReturnValue")
    public static final class Builder {

        private List<MappedRestObjectModelClass> fromHttpDataModelClasses;

        private List<MappedRestObjectModelClass> toHttpDataModelClasses;

        public void setFromHttpDataModelClasses(final List<MappedRestObjectModelClass> fromHttpDataModelClasses) {
            this.fromHttpDataModelClasses = require(fromHttpDataModelClasses);
        }

        public void setToHttpDataModelClasses(final List<MappedRestObjectModelClass> toHttpDataModelClasses) {
            this.toHttpDataModelClasses = require(toHttpDataModelClasses);
        }

        public RestGenerationContext build() {
            return new RestGenerationContext(
                    fromHttpDataModelClasses,
                    toHttpDataModelClasses
            );
        }
    }
}
