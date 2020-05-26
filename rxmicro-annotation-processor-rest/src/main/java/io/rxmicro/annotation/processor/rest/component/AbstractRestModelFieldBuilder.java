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

package io.rxmicro.annotation.processor.rest.component;

import com.google.inject.Inject;
import io.rxmicro.annotation.processor.common.component.impl.AbstractModelFieldBuilder;
import io.rxmicro.annotation.processor.common.model.AnnotatedModelElement;
import io.rxmicro.annotation.processor.common.model.ModelFieldType;
import io.rxmicro.annotation.processor.common.model.definition.SupportedTypesProvider;
import io.rxmicro.annotation.processor.common.model.error.InternalErrorException;
import io.rxmicro.annotation.processor.common.model.type.PrimitiveModelClass;
import io.rxmicro.annotation.processor.rest.model.RestModelField;
import io.rxmicro.annotation.processor.rest.model.RestObjectModelClass;
import io.rxmicro.annotation.processor.rest.model.RestPrimitiveModelClass;
import io.rxmicro.rest.Header;
import io.rxmicro.rest.Parameter;
import io.rxmicro.rest.PathVariable;
import io.rxmicro.rest.RemoteAddress;
import io.rxmicro.rest.RequestBody;
import io.rxmicro.rest.RequestId;
import io.rxmicro.rest.RequestMethod;
import io.rxmicro.rest.RequestUrlPath;
import io.rxmicro.rest.ResponseBody;
import io.rxmicro.rest.ResponseStatusCode;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

import static io.rxmicro.annotation.processor.common.util.Errors.IMPOSSIBLE_ERROR_ANNOTATION_NOT_FOUND_SUPPLIER;
import static io.rxmicro.annotation.processor.common.util.ProcessingEnvironmentHelper.getElements;

/**
 * @author nedis
 * @since 0.1
 */
public abstract class AbstractRestModelFieldBuilder extends AbstractModelFieldBuilder<RestModelField, RestObjectModelClass> {

    @Inject
    private SupportedTypesProvider supportedTypesProvider;

    @Inject
    private RestModelFieldBuilder<Header> headerRestModelFieldBuilder;

    @Inject
    private RestModelFieldBuilder<PathVariable> pathVariableRestModelFieldBuilder;

    @Inject
    private RestModelFieldBuilder<RequestId> requestIdRestModelFieldBuilder;

    @Inject
    private RestModelFieldBuilder<Parameter> parameterRestModelFieldBuilder;

    @Override
    protected final SupportedTypesProvider getSupportedTypesProvider() {
        return supportedTypesProvider;
    }

    @Override
    protected final RestModelField build(final ModelFieldType modelFieldType,
                                         final VariableElement field,
                                         final TypeElement typeElement,
                                         final ModelNames modelNames,
                                         final Set<String> fieldNames,
                                         final int nestedLevel) {
        final String fieldName = field.getSimpleName().toString();
        if (!fieldNames.add(fieldName)) {
            error(field, "Detected duplicate of class field name: ?", fieldName);
        }
        final AnnotatedModelElement annotated = build(typeElement, field);
        validateAnnotated(modelFieldType, annotated);
        return buildInternal(modelFieldType, annotated).orElseGet(() ->
                buildCustomParameter(modelFieldType, typeElement, modelNames, nestedLevel, annotated)
        );
    }

    @Override
    protected final PrimitiveModelClass createPrimitiveModelClass(final TypeMirror type) {
        return new RestPrimitiveModelClass(type);
    }

    protected abstract Optional<RestModelField> buildInternal(ModelFieldType modelFieldType,
                                                              AnnotatedModelElement annotated);

    private RestModelField buildCustomParameter(final ModelFieldType modelFieldType,
                                                final TypeElement typeElement,
                                                final ModelNames modelNames,
                                                final int nestedLevel,
                                                final AnnotatedModelElement annotated) {
        final PathVariable pathVariable = annotated.getAnnotation(PathVariable.class);
        if (pathVariable != null) {
            final RestModelField restModelField = pathVariableRestModelFieldBuilder.build(
                    modelFieldType, typeElement, annotated, pathVariable, modelNames.modelNames("path"), nestedLevel
            );
            return validateAndReturn(restModelField, typeElement);
        }
        final Header header = annotated.getAnnotation(Header.class);
        if (header != null) {
            final RestModelField restModelField = headerRestModelFieldBuilder.build(
                    modelFieldType, typeElement, annotated, header, modelNames.modelNames("headers"), nestedLevel
            );
            return validateAndReturn(restModelField, typeElement);
        }
        final RequestId requestId = annotated.getAnnotation(RequestId.class);
        if (requestId != null) {
            final RestModelField restModelField = requestIdRestModelFieldBuilder.build(
                    modelFieldType, typeElement, annotated, requestId, modelNames.modelNames("headers"), nestedLevel
            );
            return validateAndReturn(restModelField, typeElement);
        }
        final Parameter parameter = annotated.getAnnotation(Parameter.class);
        final RestModelField restModelField = parameterRestModelFieldBuilder.build(
                modelFieldType, typeElement, annotated, parameter, modelNames.modelNames("params"), nestedLevel
        );
        return validateAndReturn(restModelField, typeElement);
    }

    protected final void validateNoAnnotations(final AnnotatedModelElement annotated) {
        if (!annotated.getAllAnnotationMirrors(getElements()).isEmpty()) {
            error(annotated.getField(),
                    "Annotations are not allowed for this element");
        }
    }

    protected abstract Set<Class<? extends Annotation>> supportedRequestAnnotations();

    protected abstract Set<Class<? extends Annotation>> supportedResponseAnnotations();

    private void validateAnnotated(final ModelFieldType modelFieldType,
                                   final AnnotatedModelElement annotated) {
        final List<Class<? extends Annotation>> annotationClasses = List.of(
                Header.class,
                Parameter.class,
                PathVariable.class,
                RequestMethod.class,
                RequestUrlPath.class,
                RequestId.class,
                RemoteAddress.class,
                RequestBody.class,
                ResponseBody.class,
                ResponseStatusCode.class
        );
        validateOnlyOneAnnotationPerElement(annotated, annotationClasses);
        validateSupportedAnnotations(annotated, modelFieldType, annotationClasses);
    }

    private void validateOnlyOneAnnotationPerElement(final AnnotatedModelElement annotated,
                                                     final List<Class<? extends Annotation>> annotationClasses) {
        if (annotationClasses.stream().map(annotated::getAnnotation).filter(Objects::nonNull).count() > 1) {
            final Class<? extends Annotation> annotationClass = annotationClasses.stream()
                    .filter(a -> annotated.getAnnotation(a) != null)
                    .findFirst()
                    .orElseThrow(IMPOSSIBLE_ERROR_ANNOTATION_NOT_FOUND_SUPPLIER);
            error(
                    annotated.getElementAnnotatedBy(annotationClass).orElseThrow(IMPOSSIBLE_ERROR_ANNOTATION_NOT_FOUND_SUPPLIER),
                    "Use only one annotation per element from the following list: ?",
                    annotationClasses
            );
        }
    }

    private void validateSupportedAnnotations(final AnnotatedModelElement annotated,
                                              final ModelFieldType modelFieldType,
                                              final List<Class<? extends Annotation>> annotationClasses) {
        final Set<Class<? extends Annotation>> supportedAnnotationClasses = getSupportedAnnotations(modelFieldType);
        for (final Class<? extends Annotation> annotationClass : annotationClasses) {
            final Annotation annotation = annotated.getAnnotation(annotationClass);
            if (annotation != null && !supportedAnnotationClasses.contains(annotation.annotationType())) {
                error(
                        annotated.getElementAnnotatedBy(annotationClass).orElseThrow(IMPOSSIBLE_ERROR_ANNOTATION_NOT_FOUND_SUPPLIER),
                        "REST annotation '@?' is not allowed here. " +
                                "All supported REST annotations are: ?. " +
                                "Remove the unsupported annotation!",
                        annotation.annotationType().getName(),
                        supportedAnnotationClasses
                );
            }
        }
    }

    private Set<Class<? extends Annotation>> getSupportedAnnotations(final ModelFieldType modelFieldType) {
        if (modelFieldType.isRequest()) {
            return supportedRequestAnnotations();
        } else if (modelFieldType.isResponse()) {
            return supportedResponseAnnotations();
        } else {
            throw new InternalErrorException("Unsupported model type: ?", modelFieldType);
        }
    }
}
