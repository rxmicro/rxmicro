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

package io.rxmicro.annotation.processor.documentation.asciidoctor.component.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.common.component.ModelFieldBuilder;
import io.rxmicro.annotation.processor.common.model.EnvironmentContext;
import io.rxmicro.annotation.processor.common.model.ModelFieldBuilderOptions;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.annotation.processor.documentation.asciidoctor.component.ModelExceptionErrorResponseBuilder;
import io.rxmicro.annotation.processor.documentation.asciidoctor.model.Response;
import io.rxmicro.annotation.processor.documentation.component.DescriptionReader;
import io.rxmicro.annotation.processor.documentation.component.HttpResponseExampleBuilder;
import io.rxmicro.annotation.processor.documentation.model.ProjectMetaData;
import io.rxmicro.annotation.processor.documentation.model.ReadMoreModel;
import io.rxmicro.annotation.processor.documentation.model.StandardHttpErrorStorage;
import io.rxmicro.annotation.processor.rest.model.RestModelField;
import io.rxmicro.annotation.processor.rest.model.RestObjectModelClass;
import io.rxmicro.documentation.ModelExceptionErrorResponse;
import io.rxmicro.documentation.ResourceDefinition;
import io.rxmicro.rest.Header;
import io.rxmicro.rest.Parameter;
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

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.ModuleElement;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeKind;

import static io.rxmicro.annotation.processor.common.model.ModelFieldType.REST_SERVER_RESPONSE;
import static io.rxmicro.annotation.processor.common.util.Annotations.getRequiredAnnotationClassParameter;
import static io.rxmicro.annotation.processor.common.util.Elements.allFields;
import static io.rxmicro.annotation.processor.common.util.Elements.allModelFields;
import static io.rxmicro.common.local.DeniedPackages.isDeniedPackage;
import static io.rxmicro.documentation.asciidoctor.AsciidoctorDocumentationConstants.STATUS_CODE_STATIC_FIELD_NAME;

/**
 * @author nedis
 * @since 0.7
 */
@Singleton
public final class ModelExceptionErrorResponseBuilderImpl implements ModelExceptionErrorResponseBuilder {

    private static final Set<Class<? extends Annotation>> UNSUPPORTED_ANNOTATION_CLASSES = Set.of(
            ResponseStatusCode.class,
            ResponseBody.class,
            RequestUrlPath.class,
            RequestMethod.class,
            RequestId.class,
            RequestBody.class,
            RepeatQueryParameter.class,
            RepeatHeader.class,
            RemoteAddress.class,
            PathVariable.class
    );

    @Inject
    private DescriptionReader descriptionReader;

    @Inject
    private StandardHttpErrorStorage standardHttpErrorStorage;

    @Inject
    private ModelFieldBuilder<RestModelField, RestObjectModelClass> modelFieldBuilder;

    @Inject
    private ModelExceptionErrorResponseStandardBuilder modelExceptionErrorResponseStandardBuilder;

    @Inject
    private ModelExceptionErrorResponseCustomBuilder modelExceptionErrorResponseCustomBuilder;

    @Inject
    private HttpResponseExampleBuilder httpResponseExampleBuilder;

    @Override
    public Response buildResponse(final EnvironmentContext environmentContext,
                                  final Element owner,
                                  final ProjectMetaData projectMetaData,
                                  final ResourceDefinition resourceDefinition,
                                  final ModelExceptionErrorResponse modelExceptionErrorResponse,
                                  final List<ReadMoreModel> showErrorCauseReadMoreLinks) {
        final TypeElement exceptionTypeElement = getRequiredAnnotationClassParameter(modelExceptionErrorResponse::value);
        final int status = extractStatusCode(owner, exceptionTypeElement);
        final Response.Builder responseBuilder = new Response.Builder()
                .setCode(status);
        descriptionReader.readDescription(exceptionTypeElement, projectMetaData.getProjectDirectory())
                .ifPresentOrElse(
                        responseBuilder::setDescription,
                        () -> standardHttpErrorStorage.get(status).ifPresent(e -> responseBuilder.setDescription(e.getDescription()))
                );
        if (isRxMicroExceptionClass(exceptionTypeElement)) {
            modelExceptionErrorResponseStandardBuilder.setResponseBodyAndExamples(
                    owner, resourceDefinition, showErrorCauseReadMoreLinks, exceptionTypeElement, status, responseBuilder
            );
        } else {
            final RestObjectModelClass modelClass = getModelClass(environmentContext, exceptionTypeElement);
            if (resourceDefinition.withExamples()) {
                responseBuilder.setExample(httpResponseExampleBuilder.build(resourceDefinition, status, modelClass));
            }
            modelExceptionErrorResponseCustomBuilder.setResponseHeaders(
                    environmentContext, resourceDefinition, projectMetaData, exceptionTypeElement, modelClass, responseBuilder
            );
            if (isCustomParamsPresent(owner, exceptionTypeElement)) {
                modelExceptionErrorResponseCustomBuilder.setResponseBody(
                        environmentContext, resourceDefinition, projectMetaData, exceptionTypeElement, modelClass, responseBuilder
                );
            } else {
                modelExceptionErrorResponseStandardBuilder.setResponseBodyAndExamples(
                        owner, resourceDefinition, showErrorCauseReadMoreLinks, exceptionTypeElement, status, responseBuilder
                );
            }
        }
        return responseBuilder.build();
    }

    private int extractStatusCode(final Element owner,
                                  final TypeElement exceptionTypeElement) {
        final Predicate<VariableElement> variableElementPredicate = el ->
                el.getModifiers().containsAll(Set.of(Modifier.STATIC, Modifier.FINAL)) &&
                        STATUS_CODE_STATIC_FIELD_NAME.equals(el.getSimpleName().toString()) &&
                        el.asType().getKind() == TypeKind.INT;

        return (int) allFields(exceptionTypeElement, variableElementPredicate).stream()
                .findFirst()
                .orElseThrow(() -> {
                    throw new InterruptProcessingException(owner,
                            "Required static final int field: '?.STATUS_CODE' not defined",
                            exceptionTypeElement.asType().toString());
                }).getConstantValue();
    }

    private boolean isRxMicroExceptionClass(final TypeElement exceptionTypeElement) {
        return isDeniedPackage(((PackageElement) exceptionTypeElement.getEnclosingElement()).getQualifiedName().toString());
    }

    private RestObjectModelClass getModelClass(final EnvironmentContext environmentContext,
                                               final TypeElement exceptionTypeElement) {
        final ModuleElement currentModule = environmentContext.getCurrentModule();
        final ModelFieldBuilderOptions options = new ModelFieldBuilderOptions()
                .setWithFieldsFromParentClasses(false)
                .setAccessViaReflectionMustBeDetected(false);
        return modelFieldBuilder.build(REST_SERVER_RESPONSE, currentModule, Set.of(exceptionTypeElement), options)
                .get(exceptionTypeElement);
    }

    private boolean isCustomParamsPresent(final Element owner,
                                          final TypeElement exceptionTypeElement) {
        boolean paramsPresent = false;
        for (final VariableElement field : allModelFields(exceptionTypeElement, false)) {
            if (field.getAnnotation(Parameter.class) != null) {
                if (field.getAnnotation(Header.class) != null) {
                    throw new InterruptProcessingException(
                            owner,
                            "A '?.?' can't annotated by '@?' and '@?'! Remove one of these annotations!",
                            exceptionTypeElement.getQualifiedName(), field.getSimpleName(),
                            Parameter.class.getSimpleName(), Header.class.getSimpleName()
                    );
                }
                paramsPresent = true;
            } else {
                for (final Class<? extends Annotation> annotationClass : UNSUPPORTED_ANNOTATION_CLASSES) {
                    if (field.getAnnotation(annotationClass) != null) {
                        throw new InterruptProcessingException(
                                owner,
                                "'@?' annotation can be used for field: '?.?'! Remove unsupported annotation!",
                                annotationClass.getName(), exceptionTypeElement.getQualifiedName(), field.getSimpleName()
                        );
                    }
                }
                if (field.getAnnotation(Header.class) == null) {
                    paramsPresent = true;
                }
            }
        }
        return paramsPresent;
    }
}
