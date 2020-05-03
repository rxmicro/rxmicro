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

package io.rxmicro.annotation.processor.rest.client.component.impl.method;

import io.rxmicro.annotation.processor.common.model.ClassHeader;
import io.rxmicro.annotation.processor.common.model.EnvironmentContext;
import io.rxmicro.annotation.processor.common.model.method.MethodBody;
import io.rxmicro.annotation.processor.common.model.virtual.VirtualTypeElement;
import io.rxmicro.annotation.processor.rest.client.component.impl.BaseRestClientMethodBodyBuilder;
import io.rxmicro.annotation.processor.rest.client.model.RestClientClassStructureStorage;
import io.rxmicro.annotation.processor.rest.client.model.RestClientMethodBody;
import io.rxmicro.annotation.processor.rest.client.model.RestClientMethodSignature;
import io.rxmicro.annotation.processor.rest.model.RestObjectModelClass;
import io.rxmicro.annotation.processor.rest.model.RestRequestModel;
import io.rxmicro.annotation.processor.rest.model.RestResponseModel;
import io.rxmicro.annotation.processor.rest.model.StaticHeaders;
import io.rxmicro.annotation.processor.rest.model.StaticQueryParameters;
import io.rxmicro.rest.client.detail.RequestModelExtractor;

import javax.lang.model.element.TypeElement;
import java.util.Map;

import static io.rxmicro.annotation.processor.common.util.GeneratedClassNames.getModelTransformerInstanceName;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.4
 */
public abstract class AbstractParametrizedBaseRestClientMethodBodyBuilder
        extends BaseRestClientMethodBodyBuilder {

    protected abstract String getTemplateName();

    @Override
    public final MethodBody build(final EnvironmentContext environmentContext,
                                  final RestClientClassStructureStorage storage,
                                  final ClassHeader.Builder classHeaderBuilder,
                                  final StaticHeaders staticHeaders,
                                  final StaticQueryParameters staticQueryParameters,
                                  final RestClientMethodSignature methodSignature) {
        validate(methodSignature, staticQueryParameters);
        customizeClassHeaderBuilder(classHeaderBuilder);
        final RestRequestModel requestModel = methodSignature.getRequestModel();
        final RestResponseModel responseModel = methodSignature.getResponseModel();
        final TypeElement parameterType = requestModel.getRequiredRequestType();
        final RestObjectModelClass modelClass = storage.getModelClass(parameterType.asType().toString()).orElseThrow();

        final Map<String, Object> templateArguments = createTemplateArguments(staticHeaders, staticQueryParameters, methodSignature);
        templateArguments.put("HAS_MODEL_HEADERS", modelClass.isHeadersPresent());
        customizeTemplateArguments(templateArguments, modelClass, parameterType);
        templateArguments.put("VALIDATE_REQUEST_MODE", getRequestValidationMode(methodSignature, environmentContext));
        templateArguments.put("MODEL", requestModel.getRequiredVariableName());
        templateArguments.put("EXTRACTOR", getModelTransformerInstanceName(parameterType, RequestModelExtractor.class));

        addValidators(environmentContext, storage, classHeaderBuilder, parameterType, responseModel, templateArguments);
        addPathBuilder(methodSignature, templateArguments);
        final boolean isRequestClassVirtual = requestModel.getRequiredRequestType() instanceof VirtualTypeElement;
        templateArguments.put("IS_REQUEST_CLASS_VIRTUAL", isRequestClassVirtual);
        if (isRequestClassVirtual) {
            templateArguments.put("REQUEST_VIRTUAL_CLASS", requestModel.getRequiredRequestType().getSimpleName().toString());
            templateArguments.put("VIRTUAL_FIELDS", ((VirtualTypeElement) requestModel.getRequiredRequestType()).getVirtualFieldElements());
        }
        return new RestClientMethodBody(methodBodyGenerator.generate(getTemplateName(), templateArguments));
    }

    protected abstract void validate(final RestClientMethodSignature methodSignature,
                                     final StaticQueryParameters staticQueryParameters);

    protected abstract void customizeClassHeaderBuilder(ClassHeader.Builder classHeaderBuilder);

    protected abstract void customizeTemplateArguments(Map<String, Object> templateArguments,
                                                       RestObjectModelClass modelClass,
                                                       TypeElement parameterType);
}
