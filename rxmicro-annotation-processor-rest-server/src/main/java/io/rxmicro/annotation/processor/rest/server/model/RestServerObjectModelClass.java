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

package io.rxmicro.annotation.processor.rest.server.model;

import io.rxmicro.annotation.processor.common.model.type.ModelClass;
import io.rxmicro.annotation.processor.common.util.UsedByFreemarker;
import io.rxmicro.annotation.processor.rest.model.RestModelField;
import io.rxmicro.annotation.processor.rest.model.RestObjectModelClass;
import io.rxmicro.rest.server.detail.component.ModelReader;
import io.rxmicro.rest.server.detail.component.ModelWriter;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import java.util.Map;

import static io.rxmicro.annotation.processor.common.util.GeneratedClassNames.getModelTransformerSimpleClassName;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class RestServerObjectModelClass extends RestObjectModelClass {

    public RestServerObjectModelClass(final TypeMirror modelTypeMirror,
                                      final TypeElement modelTypeElement,
                                      final Map<RestModelField, ModelClass> fields) {
        super(modelTypeMirror, modelTypeElement, fields);
    }

    @UsedByFreemarker(
            "$$RestJsonModelReaderTemplate.javaftl"
    )
    public String getModelReaderImplSimpleClassName() {
        return getModelTransformerSimpleClassName(getModelTypeElement(), ModelReader.class);
    }

    @UsedByFreemarker(
            "$$RestJsonModelWriterTemplate.javaftl"
    )
    public String getModelWriterImplSimpleClassName() {
        return getModelTransformerSimpleClassName(getModelTypeElement(), ModelWriter.class);
    }
}
