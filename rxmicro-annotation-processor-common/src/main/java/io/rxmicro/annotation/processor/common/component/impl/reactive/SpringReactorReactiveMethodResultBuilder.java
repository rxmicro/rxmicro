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

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import java.util.Optional;

import static io.rxmicro.annotation.processor.common.model.method.MethodResult.createProjectReactorResult;
import static io.rxmicro.annotation.processor.common.util.AnnotationProcessorEnvironment.types;
import static io.rxmicro.annotation.processor.common.util.Names.getSimpleName;
import static io.rxmicro.annotation.processor.common.util.Reactives.isFlux;
import static io.rxmicro.annotation.processor.common.util.Reactives.isMono;
import static io.rxmicro.annotation.processor.common.util.Reactives.isSpringReactorType;
import static io.rxmicro.annotation.processor.common.util.TypeValidators.validateGenericType;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
@Singleton
public final class SpringReactorReactiveMethodResultBuilder implements ReactiveMethodResultBuilder {

    @Override
    public boolean isSupport(final ExecutableElement method) {
        return isSpringReactorType(method.getReturnType());
    }

    @Override
    public MethodResult build(final ExecutableElement method,
                              final SupportedTypesProvider supportedTypesProvider) {
        final TypeMirror returnType = method.getReturnType();
        validateGenericType(method, returnType, "Invalid return type");
        final TypeMirror reactiveType = types().erasure(returnType);
        final TypeMirror genericType = ((DeclaredType) returnType).getTypeArguments().get(0);
        validateNotOptional(method, reactiveType, genericType);

        final boolean isGenericList = supportedTypesProvider.collectionContainers().contains(types().erasure(genericType));
        validateNotGenericListIfFlux(method, reactiveType, genericType, isGenericList);
        if (isGenericList) {
            validateGenericType(method, genericType, "Invalid return type");
            final TypeMirror resultType = ((DeclaredType) genericType).getTypeArguments().get(0);
            final boolean primitive = supportedTypesProvider.resultReturnPrimitiveTypes().contains(resultType);
            return MethodResult.createProjectReactorResult(reactiveType, false, resultType, primitive);
        } else {
            final boolean primitive = supportedTypesProvider.resultReturnPrimitiveTypes().contains(genericType);
            return createProjectReactorResult(reactiveType, genericType, primitive);
        }

    }

    private void validateNotOptional(final ExecutableElement method,
                                     final TypeMirror reactiveType,
                                     final TypeMirror genericType) {
        if (types().erasure(genericType).toString().equals(Optional.class.getName())) {
            if (isMono(reactiveType)) {
                throw new InterruptProcessingException(method,
                        "Mono type already supports optional logic. Replace Mono<Optional<TYPE>> by Mono<TYPE>!");
            }
            if (isFlux(reactiveType)) {
                throw new InterruptProcessingException(method,
                        "Flux type already supports optional logic. Replace Flux<Optional<TYPE>> by Flux<TYPE>!");
            }
        }
    }

    private void validateNotGenericListIfFlux(final ExecutableElement method,
                                              final TypeMirror reactiveType,
                                              final TypeMirror genericType,
                                              final boolean genericList) {
        if (genericList && isFlux(reactiveType)) {
            throw new InterruptProcessingException(
                    method,
                    "Flux type already supports iterable logic. Replace Flux<?<TYPE>> by Flux<TYPE>!",
                    getSimpleName(types().erasure(genericType).toString())
            );
        }
    }
}
