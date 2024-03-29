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

package io.rxmicro.annotation.processor.common.component.impl.reactive;

import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.common.component.impl.ReactiveMethodResultBuilder;
import io.rxmicro.annotation.processor.common.model.definition.SupportedTypesProvider;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.annotation.processor.common.model.method.MethodResult;

import java.util.Optional;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;

import static io.rxmicro.annotation.processor.common.model.method.MethodResult.createCompletableFutureResult;
import static io.rxmicro.annotation.processor.common.util.Names.getSimpleName;
import static io.rxmicro.annotation.processor.common.util.ProcessingEnvironmentHelper.getTypes;
import static io.rxmicro.annotation.processor.common.util.Reactives.isFuture;
import static io.rxmicro.annotation.processor.common.util.validators.TypeValidators.validateGenericType;

/**
 * @author nedis
 * @since 0.1
 */
@Singleton
public final class JSECompletableFutureReactiveMethodResultBuilder implements ReactiveMethodResultBuilder {

    private static final String INVALID_RETURN_TYPE_PREFIX = "Invalid return type";

    @Override
    public boolean isSupport(final ExecutableElement method) {
        return isFuture(method.getReturnType());
    }

    @Override
    public MethodResult build(final ExecutableElement method,
                              final SupportedTypesProvider supportedTypesProvider) {
        final TypeMirror returnType = method.getReturnType();
        validateGenericType(method, returnType, INVALID_RETURN_TYPE_PREFIX);
        final TypeMirror reactiveType = getTypes().erasure(returnType);
        final TypeMirror genericType = ((DeclaredType) returnType).getTypeArguments().get(0);

        final TypeMirror resultType;
        final boolean oneItem;
        final boolean optional;
        final boolean isGenericList = supportedTypesProvider.getCollectionContainers().contains(getTypes().erasure(genericType));
        if (isGenericList) {
            validateGenericType(method, genericType, INVALID_RETURN_TYPE_PREFIX);
            resultType = ((DeclaredType) genericType).getTypeArguments().get(0);
            oneItem = false;
            optional = false;
        } else if (getTypes().erasure(genericType).toString().equals(Optional.class.getName())) {
            validateGenericType(method, genericType, INVALID_RETURN_TYPE_PREFIX);
            resultType = ((DeclaredType) genericType).getTypeArguments().get(0);
            validateNotVoid(method, resultType);
            oneItem = true;
            optional = true;
        } else {
            resultType = genericType;
            oneItem = true;
            optional = false;
        }
        final boolean primitive = supportedTypesProvider.getResultReturnPrimitiveTypes().contains(resultType);
        return createCompletableFutureResult(reactiveType, oneItem, optional, resultType, primitive);
    }

    private void validateNotVoid(final ExecutableElement method,
                                 final TypeMirror resultType) {
        if (Void.class.getName().equals(resultType.toString())) {
            throw new InterruptProcessingException(
                    method,
                    "Void does not support optional logic. " +
                            "Use ?<Void> instead of ?<Optional<Void>!",
                    getSimpleName(getTypes().erasure(method.getReturnType()))
            );
        }
    }
}
