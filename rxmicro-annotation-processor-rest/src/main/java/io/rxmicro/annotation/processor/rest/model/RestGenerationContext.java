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

package io.rxmicro.annotation.processor.rest.model;

import java.util.List;

import static io.rxmicro.common.util.ExCollections.unmodifiableList;

/**
 * @author nedis
 * @since 0.1
 */
public final class RestGenerationContext {

    private final List<MappedRestObjectModelClass> fromHttpDataModelClasses;

    private final List<MappedRestObjectModelClass> toHttpDataModelClasses;

    public RestGenerationContext(final List<MappedRestObjectModelClass> fromHttpDataModelClasses,
                                 final List<MappedRestObjectModelClass> toHttpDataModelClasses) {
        this.fromHttpDataModelClasses = unmodifiableList(fromHttpDataModelClasses);
        this.toHttpDataModelClasses = unmodifiableList(toHttpDataModelClasses);
    }

    public List<MappedRestObjectModelClass> getFromHttpDataModelClasses() {
        return fromHttpDataModelClasses;
    }

    public List<MappedRestObjectModelClass> getToHttpDataModelClasses() {
        return toHttpDataModelClasses;
    }
}
