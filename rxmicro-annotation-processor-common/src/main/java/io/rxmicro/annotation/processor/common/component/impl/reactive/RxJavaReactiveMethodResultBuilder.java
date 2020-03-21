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
import io.reactivex.rxjava3.core.Completable;
import io.rxmicro.annotation.processor.common.component.impl.ReactiveMethodResultBuilder;
import io.rxmicro.annotation.processor.common.model.definition.SupportedTypesProvider;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.annotation.processor.common.model.method.MethodResult;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import java.util.Optional;

import static io.rxmicro.annotation.processor.common.model.method.MethodResult.createRxJavaResult;
import static io.rxmicro.annotation.processor.common.model.method.MethodResult.createWithVoidResult;
import static io.rxmicro.annotation.processor.common.util.AnnotationProcessorEnvironment.types;
import static io.rxmicro.annotation.processor.common.util.Names.getSimpleName;
import static io.rxmicro.annotation.processor.common.util.Reactives.isCompletable;
import static io.rxmicro.annotation.processor.common.util.Reactives.isFlowable;
import static io.rxmicro.annotation.processor.common.util.Reactives.isMaybe;
import static io.rxmicro.annotation.processor.common.util.Reactives.isRxJavaType;
import static io.rxmicro.annotation.processor.common.util.Reactives.isSingle;
import static io.rxmicro.annotation.processor.common.util.validators.TypeValidators.validateGenericType;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
@Singleton
public final class RxJavaReactiveMethodResultBuilder implements ReactiveMethodResultBuilder {

    @Override
    public boolean isSupport(final ExecutableElement method) {
        return isRxJavaType(method.getReturnType());
    }

    @Override
    public MethodResult build(final ExecutableElement method,
                              final SupportedTypesProvider supportedTypesProvider) {
        final TypeMirror returnType = method.getReturnType();
        if (isCompletable(returnType)) {
            return createWithVoidResult(returnType);
        } else {
            validateGenericType(method, returnType, "Invalid return type");
            final TypeMirror reactiveType = types().erasure(returnType);
            final TypeMirror genericType = ((DeclaredType) returnType).getTypeArguments().get(0);
            validateNotVoid(method, genericType);
            validateNotOptional(method, reactiveType, genericType);

            final TypeMirror resultType;
            final boolean isGenericList = supportedTypesProvider.collectionContainers().contains(types().erasure(genericType));
            validateGenericListIfSingleOnly(method, reactiveType, genericType, isGenericList);
            if (isGenericList) {
                validateGenericType(method, genericType, "Invalid return type");
                resultType = ((DeclaredType) genericType).getTypeArguments().get(0);
                final boolean primitive = supportedTypesProvider.resultReturnPrimitiveTypes().contains(resultType);
                return MethodResult.createRxJavaResult(reactiveType, false, resultType, primitive);
            } else {
                resultType = genericType;
                final boolean primitive = supportedTypesProvider.resultReturnPrimitiveTypes().contains(resultType);
                return createRxJavaResult(reactiveType, resultType, primitive);
            }
        }
    }


    private void validateNotVoid(final ExecutableElement method,
                                 final TypeMirror genericType) {
        if (genericType.toString().equals(Void.class.getName())) {
            throw new InterruptProcessingException(
                    method,
                    "Return type '?' is invalid. Use '?' instead!",
                    getSimpleName(method.getReturnType()),
                    Completable.class.getSimpleName()
            );
        }
    }

    private void validateNotOptional(final ExecutableElement method,
                                     final TypeMirror reactiveType,
                                     final TypeMirror genericType) {
        if (types().erasure(genericType).toString().equals(Optional.class.getName())) {
            if (isSingle(reactiveType)) {
                throw new InterruptProcessingException(
                        method,
                        "Single type does not support optional logic. Use Maybe instead! " +
                                "Replace Single<Optional<TYPE>> by Maybe<TYPE>!"
                );
            }
            if (isMaybe(reactiveType)) {
                throw new InterruptProcessingException(
                        method,
                        "Maybe type already supports optional logic. " +
                                "Replace Maybe<Optional<TYPE>> by Maybe<TYPE>!"
                );
            }
            if (isFlowable(reactiveType)) {
                throw new InterruptProcessingException(
                        method,
                        "Flowable type already supports optional logic. " +
                                "Replace Flowable<Optional<TYPE>> by Flowable<TYPE>!"
                );
            }
        }
    }

    private void validateGenericListIfSingleOnly(final ExecutableElement method,
                                                 final TypeMirror reactiveType,
                                                 final TypeMirror genericType,
                                                 final boolean genericList) {
        if (genericList && !isSingle(reactiveType)) {
            final String type = getSimpleName(types().erasure(genericType).toString());
            throw new InterruptProcessingException(
                    method,
                    "Only Single could be parametrized by ?<TYPE>. Replace ?<?<TYPE>> by Single<?<TYPE>>",
                    type, getSimpleName(types().erasure(reactiveType)), type, type
            );
        }
    }
}
