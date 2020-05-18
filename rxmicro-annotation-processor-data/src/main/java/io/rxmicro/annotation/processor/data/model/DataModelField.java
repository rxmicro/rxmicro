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

package io.rxmicro.annotation.processor.data.model;

import io.rxmicro.annotation.processor.common.model.AnnotatedModelElement;
import io.rxmicro.annotation.processor.common.model.ModelField;
import io.rxmicro.annotation.processor.common.util.Names;
import io.rxmicro.annotation.processor.common.util.UsedByFreemarker;

import static io.rxmicro.annotation.processor.common.util.Names.getGenericType;

/**
 * @author nedis
 * @since 0.1
 */
public abstract class DataModelField extends ModelField {

    public DataModelField(final AnnotatedModelElement annotatedModelElement,
                          final String modelName) {
        super(annotatedModelElement, modelName);
    }

    @UsedByFreemarker
    public String getColumnItemType() {
        final String type = getGenericType(getFieldClass());
        return getColumnType(type);
    }

    @UsedByFreemarker
    public String getColumnType() {
        final String type = getFieldClass().toString();
        return getColumnType(type);
    }

    private String getColumnType(final String type) {
        return Names.getSimpleName(type);
    }
}
