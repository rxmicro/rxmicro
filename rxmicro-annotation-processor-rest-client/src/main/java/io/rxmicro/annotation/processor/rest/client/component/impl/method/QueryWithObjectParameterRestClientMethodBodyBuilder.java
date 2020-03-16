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

package io.rxmicro.annotation.processor.rest.client.component.impl.method;

import com.google.inject.Singleton;
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
import io.rxmicro.annotation.processor.rest.model.StaticHeaders;
import io.rxmicro.annotation.processor.rest.model.StaticQueryParameters;
import io.rxmicro.rest.client.detail.HeaderBuilder;
import io.rxmicro.rest.client.detail.QueryBuilder;
import io.rxmicro.rest.client.detail.RequestModelExtractor;

import javax.lang.model.element.TypeElement;
import java.util.Map;

import static io.rxmicro.annotation.processor.common.util.GeneratedClassNames.getModelTransformerInstanceName;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
@Singleton
public final class QueryWithObjectParameterRestClientMethodBodyBuilder
        extends BaseRestClientMethodBodyBuilder {

    @Override
    public boolean isSupported(final RestClientMethodSignature methodSignature) {
        if (methodSignature.getHttpMethodMapping().isHttpBody()) {
            return false;
        }
        return !methodSignature.getRequestModel().requestModelNotExists();
    }

    @Override
    public MethodBody build(final EnvironmentContext environmentContext,
                            final RestClientClassStructureStorage storage,
                            final ClassHeader.Builder classHeaderBuilder,
                            final StaticHeaders staticHeaders,
                            final StaticQueryParameters staticQueryParameters,
                            final RestClientMethodSignature methodSignature) {
        final RestRequestModel requestModel = methodSignature.getRequestModel();
        final TypeElement parameterType = requestModel.getRequiredRequestType();
        final RestObjectModelClass modelClass = storage.getModelClass(parameterType.asType().toString()).orElseThrow();
        classHeaderBuilder
                .addImports(
                        QueryBuilder.class,
                        HeaderBuilder.class
                );
        final Map<String, Object> templateArguments = createTemplateArguments(staticHeaders, staticQueryParameters, methodSignature);
        templateArguments.put("VALIDATE_REQUEST_MODE", getRequestValidationMode(methodSignature, environmentContext));
        templateArguments.put("HAS_MODEL_HEADERS", modelClass.isHeadersPresent());
        templateArguments.put("HAS_MODEL_QUERY_PARAMS", modelClass.isParamsPresent());
        templateArguments.put("MODEL", requestModel.getRequiredVariableName());
        templateArguments.put("EXTRACTOR", getModelTransformerInstanceName(parameterType, RequestModelExtractor.class));
        addValidators(environmentContext, storage, classHeaderBuilder, parameterType, methodSignature.getResponseModel(), templateArguments);
        addPathBuilder(methodSignature, templateArguments);
        final boolean isRequestClassVirtual = requestModel.getRequiredRequestType() instanceof VirtualTypeElement;
        templateArguments.put("IS_REQUEST_CLASS_VIRTUAL", isRequestClassVirtual);
        if (isRequestClassVirtual) {
            templateArguments.put("REQUEST_VIRTUAL_CLASS", requestModel.getRequiredRequestType().getSimpleName().toString());
            templateArguments.put("VIRTUAL_FIELDS", ((VirtualTypeElement) requestModel.getRequiredRequestType()).getVirtualFieldElements());
        }
        return new RestClientMethodBody(
                methodBodyGenerator.generate(
                        "rest/client/method/$$RestClientQueryWithObjectParameterMethodBodyTemplate.javaftl",
                        templateArguments)
        );
    }
}
