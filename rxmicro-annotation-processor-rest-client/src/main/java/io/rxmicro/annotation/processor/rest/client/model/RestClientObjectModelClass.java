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

package io.rxmicro.annotation.processor.rest.client.model;

import io.rxmicro.annotation.processor.common.model.type.ModelClass;
import io.rxmicro.annotation.processor.common.util.UsedByFreemarker;
import io.rxmicro.annotation.processor.rest.model.RestModelField;
import io.rxmicro.annotation.processor.rest.model.RestObjectModelClass;
import io.rxmicro.rest.client.detail.ModelReader;
import io.rxmicro.rest.client.detail.PathBuilder;
import io.rxmicro.rest.client.detail.RequestModelExtractor;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import java.util.Map;

import static io.rxmicro.annotation.processor.common.util.GeneratedClassNames.getModelTransformerSimpleClassName;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class RestClientObjectModelClass extends RestObjectModelClass {

    public RestClientObjectModelClass(final TypeMirror modelTypeMirror,
                                      final TypeElement modelTypeElement,
                                      final Map<RestModelField, ModelClass> fields) {
        super(modelTypeMirror, modelTypeElement, fields);
    }

    public RestClientObjectModelClass cloneWithHeadersOnly() {
        return new RestClientObjectModelClass(getModelTypeMirror(), getModelTypeElement(), headers);
    }

    public RestClientObjectModelClass cloneWithPathVariablesOnly() {
        return new RestClientObjectModelClass(getModelTypeMirror(), getModelTypeElement(), pathVariables);
    }

    @UsedByFreemarker({
            "$$RestClientPathBuilderTemplate.javaftl",
    })
    public String getPathBuilderImplSimpleClassName() {
        return getModelTransformerSimpleClassName(getModelTypeElement(), PathBuilder.class);
    }

    @UsedByFreemarker(
            "$$RestJsonModelWriterTemplate.javaftl"
    )
    public String getRequestModelExtractorImplSimpleClassName() {
        return getModelTransformerSimpleClassName(getModelTypeElement(), RequestModelExtractor.class);
    }

    @UsedByFreemarker(
            "$$RestJsonModelReaderTemplate.javaftl"
    )
    public String getModelReaderImplSimpleClassName() {
        return getModelTransformerSimpleClassName(getModelTypeElement(), ModelReader.class);
    }
}
