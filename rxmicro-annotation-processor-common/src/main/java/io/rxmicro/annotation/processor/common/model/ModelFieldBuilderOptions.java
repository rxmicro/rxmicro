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

package io.rxmicro.annotation.processor.common.model;

/**
 * @author nedis
 * @since 0.7
 */
public final class ModelFieldBuilderOptions {

    public static final ModelFieldBuilderOptions DEFAULT_OPTIONS = new ModelFieldBuilderOptions();

    private boolean requireDefConstructor;

    private boolean withFieldsFromParentClasses = true;

    public ModelFieldBuilderOptions setRequireDefConstructor(final boolean requireDefConstructor) {
        this.requireDefConstructor = requireDefConstructor;
        return this;
    }

    public ModelFieldBuilderOptions setWithFieldsFromParentClasses(final boolean withFieldsFromParentClasses) {
        this.withFieldsFromParentClasses = withFieldsFromParentClasses;
        return this;
    }

    public boolean isRequireDefConstructor() {
        return requireDefConstructor;
    }

    public boolean isWithFieldsFromParentClasses() {
        return withFieldsFromParentClasses;
    }
}
