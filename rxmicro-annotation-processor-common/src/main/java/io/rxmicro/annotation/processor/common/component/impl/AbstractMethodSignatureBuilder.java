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

package io.rxmicro.annotation.processor.common.component.impl;

import io.rxmicro.annotation.processor.common.model.SupportedAnnotations;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.ExecutableElement;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Set;

import static io.rxmicro.annotation.processor.common.util.Elements.methodSignatureEquals;
import static io.rxmicro.annotation.processor.common.util.MethodValidators.validateNotAbstractMethod;
import static io.rxmicro.annotation.processor.common.util.MethodValidators.validateNotDefaultMethod;
import static io.rxmicro.annotation.processor.common.util.MethodValidators.validateNotNativeMethod;
import static io.rxmicro.annotation.processor.common.util.MethodValidators.validateNotPrivateMethod;
import static io.rxmicro.annotation.processor.common.util.MethodValidators.validateNotStaticMethod;
import static io.rxmicro.annotation.processor.common.util.MethodValidators.validateNotSynchronizedMethod;
import static io.rxmicro.common.util.Formats.format;
import static java.util.stream.Collectors.joining;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public abstract class AbstractMethodSignatureBuilder {

    private SupportedAnnotations supportedAnnotations;

    protected abstract Set<Class<? extends Annotation>> getSupportedAnnotationsPerMethod();

    public final SupportedAnnotations getSupportedAnnotations() {
        if (supportedAnnotations == null) {
            supportedAnnotations = new SupportedAnnotations(getSupportedAnnotationsPerMethod());
        }
        return supportedAnnotations;
    }

    protected final boolean notContainIn(final ExecutableElement method,
                                         final List<ExecutableElement> methods) {
        if (methods.isEmpty()) {
            return true;
        } else {
            return methods.stream()
                    .noneMatch(otherMethod -> methodSignatureEquals(method, otherMethod));
        }
    }

    protected final void validateAnnotatedInterfaceMethodModifiers(final ExecutableElement method,
                                                                   final AnnotationMirror annotation) {
        final String errorTemplate = "Annotation '?' couldn't be applied to the ? method. Only ? methods are supported";
        validateNotPrivateMethod(
                method, errorTemplate, annotation,
                "private", "public"
        );
        validateNotStaticMethod(
                method, errorTemplate, annotation,
                "static", "not static"
        );
        validateNotDefaultMethod(
                method, errorTemplate, annotation,
                "default", "abstract"
        );
    }

    protected final void validateAnnotatedClassMethodModifiers(final ExecutableElement method,
                                                               final List<? extends AnnotationMirror> annotations) {
        final String errorTemplate = "Annotation(s) '?' couldn't be applied to the ? method. Only ? methods are supported";
        final String annotationList = annotations.stream().map(a -> format("@?", a.getAnnotationType())).collect(joining(", "));

        validateNotPrivateMethod(
                method, errorTemplate, annotationList,
                "private", "<default> or protected or public"
        );
        validateNotAbstractMethod(
                method, errorTemplate, annotationList,
                "abstract", "not abstract"
        );
        validateNotStaticMethod(
                method, errorTemplate, annotationList,
                "static", "not static"
        );
        validateNotSynchronizedMethod(
                method, errorTemplate, annotationList,
                "synchronized", "not synchronized"
        );
        validateNotNativeMethod(
                method, errorTemplate, annotationList,
                "native", "not native"
        );
    }
}
