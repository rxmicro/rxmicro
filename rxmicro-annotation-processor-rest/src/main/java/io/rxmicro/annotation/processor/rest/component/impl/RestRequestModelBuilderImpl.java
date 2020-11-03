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

package io.rxmicro.annotation.processor.rest.component.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.common.model.ModelFieldBuilderOptions;
import io.rxmicro.annotation.processor.common.model.definition.SupportedTypesProvider;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.annotation.processor.common.model.virtual.VirtualTypeElement;
import io.rxmicro.annotation.processor.rest.component.RestRequestModelBuilder;
import io.rxmicro.annotation.processor.rest.model.RestRequestModel;

import java.util.List;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.ModuleElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

import static io.rxmicro.annotation.processor.common.util.ModelTypeElements.asValidatedModelTypeElement;

/**
 * @author nedis
 * @since 0.1
 */
@Singleton
public final class RestRequestModelBuilderImpl implements RestRequestModelBuilder {

    private static final String VIRTUAL_REQUEST_SIMPLE_CLASS_NAME_TEMPLATE = "${owner}Request${index}";

    @Inject
    private SupportedTypesProvider supportedTypesProvider;

    @Override
    public RestRequestModel build(final ModuleElement restControllerModule,
                                  final ExecutableElement method,
                                  final boolean isRequestObjectBuiltByFramework) {
        final List<? extends VariableElement> parameters = method.getParameters();
        if (parameters.isEmpty()) {
            return RestRequestModel.VOID;
        } else {
            final VariableElement parameter = parameters.get(0);
            final TypeMirror type = parameter.asType();
            if (parameters.size() == 1 &&
                    !supportedTypesProvider.getNotEntityMethodParameters().contains(type) &&
                    !type.getKind().isPrimitive()) {
                final ModelFieldBuilderOptions options = new ModelFieldBuilderOptions()
                        .setRequireDefConstructor(isRequestObjectBuiltByFramework);
                return new RestRequestModel(
                        asValidatedModelTypeElement(restControllerModule, method, type, "Invalid business method parameter", options),
                        parameter.getSimpleName().toString()
                );
            } else {
                if (isRequestObjectBuiltByFramework) {
                    for (final VariableElement variableElement : parameters) {
                        if (variableElement.asType().getKind().isPrimitive()) {
                            throw new InterruptProcessingException(
                                    method,
                                    "Primitive parameter type is not allowed. Use Java boxed type instead of '?' type!",
                                    variableElement.asType());
                        }
                    }
                }
                return new RestRequestModel(
                        new VirtualTypeElement(VIRTUAL_REQUEST_SIMPLE_CLASS_NAME_TEMPLATE, method),
                        "virtualRequest"
                );
            }
        }
    }
}
