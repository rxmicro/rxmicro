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

package io.rxmicro.annotation.processor.common.util.validators;

import io.rxmicro.annotation.processor.common.model.SupportedAnnotations;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.common.meta.SupportedTypes;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypesException;
import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class AnnotationValidators {

    public static void validateNoAnnotationPerElement(final Element element,
                                                      final Class<? extends Annotation> annotationType) {
        if (element.getAnnotationsByType(annotationType).length > 0 || element.getAnnotation(annotationType) != null) {
            throw new InterruptProcessingException(
                    element,
                    "Detected redundant annotation: '@?'. This annotation couldn't be used at the current context. Remove this annotation!",
                    annotationType.getName()
            );
        }
    }

    public static void validateOnlyOneAnnotationPerElement(final Element element,
                                                           final List<? extends AnnotationMirror> annotations,
                                                           final SupportedAnnotations supportedAnnotations) {
        if (annotations.stream()
                .filter(a -> supportedAnnotations.isAnnotationSupported(a.getAnnotationType()))
                .count() > 1) {
            throw new InterruptProcessingException(
                    element,
                    "Expected only one annotation per method. This annotation must be one from the following list: {?}",
                    String.join(", ", supportedAnnotations.getSupportedAnnotations()));
        }
    }

    public static void validateRedundantAnnotationsPerElement(final Element element,
                                                              final List<? extends AnnotationMirror> annotations,
                                                              final SupportedAnnotations supportedAnnotations,
                                                              final String ownerName) {
        for (final AnnotationMirror annotation : annotations) {
            if (!supportedAnnotations.isAnnotationSupported(annotation.getAnnotationType())) {
                throw new InterruptProcessingException(
                        element,
                        "Detected redundant annotation: '@?'. " +
                                "? can contain only the following annotations: {?}. " +
                                "Remove redundant annotation!",
                        annotation.getAnnotationType(),
                        ownerName,
                        String.join(", ", supportedAnnotations.getSupportedAnnotations()));
            }
        }
    }

    public static void validateNoRxMicroAnnotationsPerElement(final Element element,
                                                              final String cause) {
        for (final AnnotationMirror annotationMirror : element.getAnnotationMirrors()) {
            if (annotationMirror.getAnnotationType().toString().startsWith("io.rxmicro.")) {
                throw new InterruptProcessingException(
                        element,
                        "Redundant annotation: '@?' because ?. Remote this annotation!",
                        annotationMirror.getAnnotationType().toString(), cause
                );
            }
        }
    }

    public static void validateCustomAnnotation(final TypeElement annotationElement,
                                                final Set<ElementType> allowedElementType) {
        validateRetention(annotationElement);
        validateTarget(annotationElement, allowedElementType);
    }

    public static void validateSupportedTypes(final Element element,
                                              final Class<? extends Annotation> annotationType) {
        List<String> types;
        try {
            types = Arrays.stream(annotationType.getAnnotation(SupportedTypes.class).value())
                    .map(AnnotationValidators::getClassName)
                    .collect(Collectors.toList());
        } catch (final MirroredTypesException e) {
            types = e.getTypeMirrors().stream()
                    .map(Object::toString)
                    .collect(Collectors.toList());
        }
        if (!types.contains(element.asType().toString())) {
            throw new InterruptProcessingException(element,
                    "Field type '?' not supported. Use one of the following: ?",
                    element.asType(), types);
        }
    }

    private static String getClassName(final Class<?> cl) {
        if (cl.isArray()) {
            return cl.getComponentType() + "[]";
        } else {
            return cl.getName();
        }
    }

    public static void validateRetention(final TypeElement annotationElement) {
        final Retention retention = annotationElement.getAnnotation(Retention.class);
        if (retention == null) {
            throw new InterruptProcessingException(
                    annotationElement,
                    "Missing required @Retention annotation for '?' annotation type. Add it!",
                    annotationElement.asType()
            );
        }
        if (retention.value() != RetentionPolicy.SOURCE) {
            throw new InterruptProcessingException(
                    annotationElement,
                    "Invalid retention policy for '?' annotation type. " +
                            "Set 'RetentionPolicy.SOURCE' as retention policy for the custom qualifier annotation!",
                    annotationElement.asType()
            );
        }
    }

    private static void validateTarget(final TypeElement annotationElement,
                                       final Set<ElementType> allowedElementType) {
        final Target target = annotationElement.getAnnotation(Target.class);
        if (target == null) {
            throw new InterruptProcessingException(
                    annotationElement,
                    "Missing required @Target annotation for '?' annotation type. Add it!",
                    annotationElement.asType()
            );
        }
        for (final ElementType elementType : target.value()) {
            if(allowedElementType.isEmpty()){
                throw new InterruptProcessingException(
                        annotationElement,
                        "Unsupported target element: '?' for '?' annotation type. Current annotation must have empty targets, i.e. @?({})",
                        elementType,
                        annotationElement.asType(),
                        Target.class.getSimpleName()
                );
            }
            if (!allowedElementType.contains(elementType)) {
                throw new InterruptProcessingException(
                        annotationElement,
                        "Unsupported target element: '?' for '?' annotation type. The following elements supported only: ?",
                        elementType,
                        annotationElement.asType(),
                        allowedElementType
                );
            }
        }
    }

    private AnnotationValidators() {
    }
}
