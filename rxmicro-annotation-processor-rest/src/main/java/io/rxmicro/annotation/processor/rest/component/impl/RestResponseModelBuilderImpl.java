/*
 * Copyright 2019 http://rxmicro.io
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
import io.rxmicro.annotation.processor.common.component.MethodResultBuilder;
import io.rxmicro.annotation.processor.common.model.definition.SupportedTypesProvider;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.annotation.processor.common.model.method.MethodResult;
import io.rxmicro.annotation.processor.rest.component.RestResponseModelBuilder;
import io.rxmicro.annotation.processor.rest.model.RestResponseModel;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.ModuleElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

import static io.rxmicro.annotation.processor.common.util.TypeValidators.validateAndGetModelType;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
@Singleton
public final class RestResponseModelBuilderImpl implements RestResponseModelBuilder {

    @Inject
    private MethodResultBuilder methodResultBuilder;

    @Inject
    private SupportedTypesProvider supportedTypesProvider;

    @Override
    public RestResponseModel build(final ModuleElement restControllerModule,
                                   final ExecutableElement method,
                                   final boolean requireDefConstructor) {
        final TypeMirror returnType = method.getReturnType();
        if (returnType.getKind() == TypeKind.VOID) {
            return RestResponseModel.VOID;
        } else {
            final MethodResult methodResult = methodResultBuilder.build(method, supportedTypesProvider);
            if (methodResult.isPrimitive()) {
                throw new InterruptProcessingException(method, "Response couldn't be a primitive! Use void or model class instead!");
            } else if (methodResult.isVoid()) {
                return new RestResponseModel(methodResult);
            } else {
                validateAndGetModelType(
                        restControllerModule, method, methodResult.getResultType(),
                        "Invalid business method result",
                        requireDefConstructor
                );
                return new RestResponseModel(methodResult);
            }
        }
    }
}
