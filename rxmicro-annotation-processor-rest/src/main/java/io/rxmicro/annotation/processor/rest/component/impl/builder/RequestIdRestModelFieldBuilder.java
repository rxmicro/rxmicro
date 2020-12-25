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

package io.rxmicro.annotation.processor.rest.component.impl.builder;

import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.common.component.impl.BaseProcessorComponent;
import io.rxmicro.annotation.processor.common.model.AnnotatedModelElement;
import io.rxmicro.annotation.processor.common.model.ModelFieldType;
import io.rxmicro.annotation.processor.rest.component.RestModelFieldBuilder;
import io.rxmicro.annotation.processor.rest.model.RestModelField;
import io.rxmicro.rest.RequestId;
import io.rxmicro.rest.model.HttpModelType;

import java.util.Set;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

import static io.rxmicro.http.HttpStandardHeaderNames.REQUEST_ID;

/**
 * @author nedis
 * @since 0.5
 */
@Singleton
public final class RequestIdRestModelFieldBuilder extends BaseProcessorComponent implements RestModelFieldBuilder<RequestId> {

    @Override
    public RestModelField build(final ModelFieldType modelFieldType,
                                final TypeElement typeElement,
                                final AnnotatedModelElement annotated,
                                final RequestId annotation,
                                final Set<String> modelNames,
                                final int nestedLevel) {
        final VariableElement field = annotated.getField();
        if (nestedLevel > 1) {
            error(
                    annotated.getElementAnnotatedBy(RequestId.class).orElse(field),
                    "Annotation @? not allowed here. RequestId can be defined in root class only",
                    RequestId.class.getSimpleName()
            );
        }
        final TypeMirror fieldType = field.asType();
        if (!String.class.getName().equals(fieldType.toString())) {
            error(
                    annotated.getElementAnnotatedBy(RequestId.class).orElse(field),
                    "Invalid request id type. Allowed type is: String"
            );
        }
        final String modelName = REQUEST_ID;
        if (!modelNames.add(modelName)) {
            error(
                    annotated.getElementAnnotatedBy(RequestId.class).orElse(field),
                    "Detected duplicate of HTTP header name: ?",
                    modelName
            );
        }
        return new RestModelField(annotated, HttpModelType.HEADER, modelName);
    }
}
