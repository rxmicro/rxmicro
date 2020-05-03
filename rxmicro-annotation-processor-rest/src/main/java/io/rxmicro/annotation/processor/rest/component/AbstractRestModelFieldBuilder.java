/*
 * Copyright 2019 https://rxmicro.io
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
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.annotation.processor.common.model.type.PrimitiveModelClass;
import io.rxmicro.annotation.processor.rest.model.RestModelField;
import io.rxmicro.annotation.processor.rest.model.RestObjectModelClass;
import io.rxmicro.annotation.processor.rest.model.RestPrimitiveModelClass;
import io.rxmicro.rest.Header;
import io.rxmicro.rest.HeaderMappingStrategy;
import io.rxmicro.rest.Parameter;
import io.rxmicro.rest.ParameterMappingStrategy;
import io.rxmicro.rest.PathVariable;
import io.rxmicro.rest.RemoteAddress;
import io.rxmicro.rest.RepeatHeader;
import io.rxmicro.rest.RepeatQueryParameter;
import io.rxmicro.rest.RequestBody;
import io.rxmicro.rest.RequestId;
import io.rxmicro.rest.RequestMethod;
import io.rxmicro.rest.RequestUrlPath;
import io.rxmicro.rest.ResponseBody;
import io.rxmicro.rest.ResponseStatusCode;
import io.rxmicro.rest.model.HttpModelType;

import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

import static io.rxmicro.annotation.processor.common.model.ModelFieldType.REST_CLIENT_RESPONSE;
import static io.rxmicro.annotation.processor.common.model.ModelFieldType.REST_SERVER_REQUEST;
import static io.rxmicro.annotation.processor.common.util.ProcessingEnvironmentHelper.getElements;
import static io.rxmicro.annotation.processor.common.util.Errors.createInternalErrorSupplier;
import static io.rxmicro.annotation.processor.common.util.Names.getSimpleName;
import static io.rxmicro.http.HttpHeaders.REQUEST_ID;
import static io.rxmicro.http.local.HttpValidators.validateHeaderName;
import static io.rxmicro.http.local.HttpValidators.validateParameterName;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public abstract class AbstractRestModelFieldBuilder
        extends AbstractModelFieldBuilder<RestModelField, RestObjectModelClass> {

    private static final Supplier<? extends InternalErrorException> ERROR_SUPPLIER =
            createInternalErrorSupplier("Impossible ERROR: Annotation not found");

    @Inject
    private SupportedTypesProvider supportedTypesProvider;

    @Override
    protected final SupportedTypesProvider getSupportedTypesProvider() {
        return supportedTypesProvider;
    }

    @Override
    protected final PrimitiveModelClass createPrimitiveModelClass(final TypeMirror type) {
        return new RestPrimitiveModelClass(type);
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
            error(field, "Detected duplicate of class field name: " + fieldName);
        }
        final AnnotatedModelElement annotated = build(typeElement, field);
        validateAnnotated(modelFieldType, annotated);
        return buildInternal(modelFieldType, annotated).orElseGet(() ->
                buildCustomParameter(modelFieldType, field, typeElement, modelNames, nestedLevel, fieldName, annotated)
        );
    }

    protected abstract Optional<RestModelField> buildInternal(ModelFieldType modelFieldType,
                                                              AnnotatedModelElement annotated);

    private RestModelField buildCustomParameter(final ModelFieldType modelFieldType,
                                                final VariableElement field,
                                                final TypeElement typeElement,
                                                final ModelNames modelNames,
                                                final int nestedLevel,
                                                final String fieldName,
                                                final AnnotatedModelElement annotated) {
        final PathVariable pathVariable = annotated.getAnnotation(PathVariable.class);
        if (pathVariable != null) {
            return validate(buildPathVariable(
                    field, modelNames.modelNames("path"), nestedLevel, annotated, fieldName, pathVariable
            ), typeElement);
        }
        final Header header = annotated.getAnnotation(Header.class);
        if (header != null) {
            return validate(buildHeader(
                    modelFieldType, field, typeElement, modelNames.modelNames("headers"), nestedLevel, fieldName, annotated, header
            ), typeElement);
        }
        final RequestId requestId = annotated.getAnnotation(RequestId.class);
        if (requestId != null) {
            return validate(buildRequestIdHeader(
                    field, modelNames.modelNames("headers"), nestedLevel, annotated
            ), typeElement);
        }
        return validate(buildParameter(
                modelFieldType, field, typeElement, modelNames.modelNames("params"), fieldName, annotated
        ), typeElement);
    }

    protected final void validateNoAnnotations(final AnnotatedModelElement annotated) {
        if (!annotated.getAllAnnotationMirrors(getElements()).isEmpty()) {
            error(annotated.getElement(),
                    "Annotations are not allowed for this element");
        }
    }

    private RestModelField buildPathVariable(final VariableElement field,
                                             final Set<String> modelNames,
                                             final int nestedLevel,
                                             final AnnotatedModelElement annotated,
                                             final String fieldName,
                                             final PathVariable pathVariable) {
        if (nestedLevel > 1) {
            error(annotated.getElementAnnotatedBy(PathVariable.class).orElse(field),
                    "Annotation @? not allowed here. Path variable can be defined in root class only",
                    PathVariable.class.getSimpleName());
        }
        final String modelName = !pathVariable.value().isEmpty() ? pathVariable.value() : fieldName;
        if (!modelNames.add(modelName)) {
            error(annotated.getElementAnnotatedBy(PathVariable.class).orElse(field),
                    "Detected duplicate of path variable name: " + modelName);
        }
        if (!getSupportedTypesProvider().getPrimitives().contains(field.asType())) {
            error(annotated.getElementAnnotatedBy(PathVariable.class).orElse(field),
                    "Invalid path variable type. Allowed types are: ?",
                    getSupportedTypesProvider().getPrimitives());
        }
        return new RestModelField(annotated, HttpModelType.PATH, modelName);
    }

    @SuppressWarnings("Convert2MethodRef")
    private RestModelField buildHeader(final ModelFieldType modelFieldType,
                                       final VariableElement field,
                                       final TypeElement typeElement,
                                       final Set<String> modelNames,
                                       final int nestedLevel,
                                       final String fieldName,
                                       final AnnotatedModelElement annotated,
                                       final Header header) {
        if (nestedLevel > 1) {
            error(annotated.getElementAnnotatedBy(Header.class).orElse(field),
                    "Annotation @? not allowed here. Header can be defined in root class only",
                    Header.class.getSimpleName());
        }
        final TypeMirror fieldType = field.asType();
        if (!isModelPrimitive(fieldType) && !isModelPrimitiveList(fieldType)) {
            error(annotated.getElementAnnotatedBy(Header.class).orElse(field),
                    "Invalid header type. Allowed types are: ?",
                    getSupportedTypesProvider().getPrimitives());
        }
        final HeaderMappingStrategy strategy = typeElement.getAnnotation(HeaderMappingStrategy.class);
        final String modelName = getModelName(header.value(), strategy, fieldName, () -> strategy.value());
        if (!modelNames.add(modelName)) {
            error(annotated.getElementAnnotatedBy(Header.class).orElse(field),
                    "Detected duplicate of HTTP header name: ?. " +
                            "For multi value header use List<?> type and '@?' annotation",
                    modelName, getSimpleName(field.asType()), RepeatHeader.class
            );
        }
        try {
            validateHeaderName(modelName);
        } catch (final IllegalArgumentException e) {
            error(annotated.getElementAnnotatedBy(Header.class).orElse(field),
                    e.getMessage()
            );
        }
        final boolean repeat = annotated.isAnnotationPresent(RepeatHeader.class);
        if (repeat) {
            validateRepeatHeader(modelFieldType, annotated);
        }
        return new RestModelField(annotated, HttpModelType.HEADER, modelName, repeat);
    }

    private RestModelField buildRequestIdHeader(final VariableElement field,
                                                final Set<String> modelNames,
                                                final int nestedLevel,
                                                final AnnotatedModelElement annotated) {
        if (nestedLevel > 1) {
            error(annotated.getElementAnnotatedBy(RequestId.class).orElse(field),
                    "Annotation @? not allowed here. RequestId can be defined in root class only",
                    RequestId.class.getSimpleName());
        }
        final TypeMirror fieldType = field.asType();
        if (!String.class.getName().equals(fieldType.toString())) {
            error(annotated.getElementAnnotatedBy(RequestId.class).orElse(field),
                    "Invalid request id type. Allowed type is: String");
        }
        final String modelName = REQUEST_ID;
        if (!modelNames.add(modelName)) {
            error(annotated.getElementAnnotatedBy(RequestId.class).orElse(field),
                    "Detected duplicate of HTTP header name: " + modelName);
        }
        return new RestModelField(annotated, HttpModelType.HEADER, modelName);
    }

    @SuppressWarnings("Convert2MethodRef")
    private RestModelField buildParameter(final ModelFieldType modelFieldType,
                                          final VariableElement field,
                                          final TypeElement typeElement,
                                          final Set<String> modelNames,
                                          final String fieldName,
                                          final AnnotatedModelElement annotated) {
        final Parameter parameter = annotated.getAnnotation(Parameter.class);
        final ParameterMappingStrategy strategy = typeElement.getAnnotation(ParameterMappingStrategy.class);
        final String modelName = getModelName(parameter != null ? parameter.value() : "", strategy, fieldName, () -> strategy.value());
        if (!modelNames.add(modelName)) {
            error(annotated.getElementAnnotatedBy(Parameter.class).orElse(field),
                    "Detected duplicate of HTTP parameter name: " + modelName);
        }
        try {
            validateParameterName(modelName);
        } catch (final IllegalArgumentException e) {
            error(annotated.getElementAnnotatedBy(Parameter.class).orElse(field),
                    e.getMessage()
            );
        }
        final boolean repeat = annotated.isAnnotationPresent(RepeatQueryParameter.class);
        if (repeat) {
            validateRepeatQueryParameter(modelFieldType, annotated);
        }
        return new RestModelField(annotated, HttpModelType.PARAMETER, modelName, repeat);
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
                    .orElseThrow(ERROR_SUPPLIER);
            error(annotated.getElementAnnotatedBy(annotationClass).orElseThrow(ERROR_SUPPLIER),
                    "Use only one annotation per element from the following list: ?",
                    annotationClasses);
        }
    }

    private void validateSupportedAnnotations(final AnnotatedModelElement annotated,
                                              final ModelFieldType modelFieldType,
                                              final List<Class<? extends Annotation>> annotationClasses) {
        final Set<Class<? extends Annotation>> supportedAnnotationClasses = getSupportedAnnotations(modelFieldType);
        for (final Class<? extends Annotation> annotationClass : annotationClasses) {
            final Annotation annotation = annotated.getAnnotation(annotationClass);
            if (annotation != null && !supportedAnnotationClasses.contains(annotation.annotationType())) {
                error(annotated.getElementAnnotatedBy(annotationClass).orElseThrow(ERROR_SUPPLIER),
                        "REST annotation '@?' is not allowed here. " +
                                "All supported REST annotations are: ?. " +
                                "Remove the unsupported annotation!",
                        annotation.annotationType().getName(),
                        supportedAnnotationClasses
                );
            }
        }
    }

    private void validateRepeatQueryParameter(final ModelFieldType modelFieldType,
                                              final AnnotatedModelElement annotated) {
        if (modelFieldType == REST_CLIENT_RESPONSE) {
            throw new InterruptProcessingException(
                    annotated.getElementAnnotatedBy(RepeatQueryParameter.class).orElseThrow(ERROR_SUPPLIER),
                    "'@?' annotation can be applied for client HTTP request model only! Remove the redundant annotation!",
                    RepeatQueryParameter.class
            );
        }
        if (!getSupportedTypesProvider().getPrimitiveContainers().contains(annotated.getField().asType())) {
            throw new InterruptProcessingException(
                    annotated.getElementAnnotatedBy(RepeatQueryParameter.class).orElseThrow(ERROR_SUPPLIER),
                    "'@?' annotation can be applied for array type only! Remove the redundant annotation!",
                    RepeatQueryParameter.class
            );
        }
    }

    private void validateRepeatHeader(final ModelFieldType modelFieldType,
                                      final AnnotatedModelElement annotated) {
        if (modelFieldType == REST_SERVER_REQUEST) {
            throw new InterruptProcessingException(
                    annotated.getElementAnnotatedBy(RepeatHeader.class).orElseThrow(ERROR_SUPPLIER),
                    "'@?' annotation can be applied for server HTTP response model only! Remove the redundant annotation!",
                    RepeatHeader.class
            );
        }
        if (modelFieldType == REST_CLIENT_RESPONSE) {
            throw new InterruptProcessingException(
                    annotated.getElementAnnotatedBy(RepeatHeader.class).orElseThrow(ERROR_SUPPLIER),
                    "'@?' annotation can be applied for client HTTP request model only! Remove the redundant annotation!",
                    RepeatHeader.class
            );
        }
        if (!getSupportedTypesProvider().getPrimitiveContainers().contains(annotated.getField().asType())) {
            throw new InterruptProcessingException(
                    annotated.getElementAnnotatedBy(RepeatHeader.class).orElseThrow(ERROR_SUPPLIER),
                    "'@?' annotation can be applied for array type only! Remove the redundant annotation!",
                    RepeatHeader.class
            );
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
