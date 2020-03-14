/*
 * Copyright (c) 2020. http://rxmicro.io
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

package io.rxmicro.annotation.processor.data.component.impl;

import com.google.inject.Inject;
import io.rxmicro.annotation.processor.common.component.MethodParametersBuilder;
import io.rxmicro.annotation.processor.common.component.MethodResultBuilder;
import io.rxmicro.annotation.processor.common.component.impl.AbstractMethodSignatureBuilder;
import io.rxmicro.annotation.processor.common.model.EnvironmentContext;
import io.rxmicro.annotation.processor.common.model.definition.SupportedTypesProvider;
import io.rxmicro.annotation.processor.data.component.DataRepositoryMethodSignatureBuilder;
import io.rxmicro.annotation.processor.data.model.DataRepositoryMethodSignature;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static io.rxmicro.annotation.processor.common.util.AnnotationValidators.validateNoRxMicroAnnotationsPerElement;
import static io.rxmicro.annotation.processor.common.util.AnnotationValidators.validateRedundantAnnotationsPerElement;
import static io.rxmicro.annotation.processor.common.util.Elements.allImplementableMethods;
import static io.rxmicro.annotation.processor.common.util.Elements.methodSignatureEquals;
import static java.util.stream.Collectors.toList;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public abstract class AbstractDataRepositoryMethodSignatureBuilder
        extends AbstractMethodSignatureBuilder
        implements DataRepositoryMethodSignatureBuilder {

    @Inject
    private SupportedTypesProvider supportedTypesProvider;

    @Inject
    private MethodResultBuilder methodResultBuilder;

    @Inject
    private MethodParametersBuilder methodParametersBuilder;

    @Override
    public final List<DataRepositoryMethodSignature> build(final EnvironmentContext environmentContext,
                                                           final TypeElement restClientInterface,
                                                           final Map.Entry<TypeElement, List<ExecutableElement>> overriddenMethodCandidates) {
        final List<ExecutableElement> methods = allImplementableMethods(restClientInterface);
        validateMethods(methods, overriddenMethodCandidates.getValue());
        return methods.stream()
                .filter(e -> notContainIn(e, overriddenMethodCandidates.getValue()))
                .map(method -> new DataRepositoryMethodSignature(
                        supportedTypesProvider,
                        method,
                        getOperationReturnVoidSet().stream().anyMatch(cl -> method.getAnnotation(cl) != null),
                        methodResultBuilder.build(method, supportedTypesProvider),
                        methodParametersBuilder.build(environmentContext, method, supportedTypesProvider)
                ))
                .collect(toList());
    }

    protected abstract Set<Class<? extends Annotation>> getOperationReturnVoidSet();

    private void validateMethods(final List<ExecutableElement> methods,
                                 final List<ExecutableElement> overriddenMethods) {
        for (final ExecutableElement method : methods) {
            final List<? extends AnnotationMirror> annotations = method.getAnnotationMirrors();
            validateRedundantAnnotationsPerElement(
                    method,
                    annotations,
                    getSupportedAnnotations(),
                    "Repository method"
            );
            annotations.stream()
                    .filter(annotation -> getSupportedAnnotations().isAnnotationSupported(annotation.getAnnotationType()))
                    .findFirst()
                    .ifPresent(annotation -> validateAnnotatedInterfaceMethodModifiers(method, annotation));
            if (overriddenMethods.stream().anyMatch(otherMethod -> methodSignatureEquals(method, otherMethod))) {
                validateNoRxMicroAnnotationsPerElement(method, "current method is already implemented");
            }
        }
    }
}
