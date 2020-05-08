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
import io.rxmicro.annotation.processor.rest.client.component.impl.BaseRestClientMethodBodyBuilder;
import io.rxmicro.annotation.processor.rest.client.model.RestClientClassStructureStorage;
import io.rxmicro.annotation.processor.rest.client.model.RestClientMethodBody;
import io.rxmicro.annotation.processor.rest.client.model.RestClientMethodSignature;
import io.rxmicro.annotation.processor.rest.model.RestResponseModel;
import io.rxmicro.annotation.processor.rest.model.StaticHeaders;
import io.rxmicro.annotation.processor.rest.model.StaticQueryParameters;
import io.rxmicro.rest.client.detail.HeaderBuilder;
import io.rxmicro.rest.client.detail.QueryBuilder;
import io.rxmicro.validation.detail.ResponseValidators;

import java.util.Map;

/**
 * @author nedis
 * @since 0.1
 */
@Singleton
public final class WithoutParametersRestClientMethodBodyBuilder
        extends BaseRestClientMethodBodyBuilder {

    @Override
    public boolean isSupported(final RestClientMethodSignature methodSignature) {
        return methodSignature.getRequestModel().requestModelNotExists();
    }

    @Override
    public MethodBody build(final EnvironmentContext environmentContext,
                            final RestClientClassStructureStorage storage,
                            final ClassHeader.Builder classHeaderBuilder,
                            final StaticHeaders staticHeaders,
                            final StaticQueryParameters staticQueryParameters,
                            final RestClientMethodSignature methodSignature) {
        classHeaderBuilder
                .addImports(
                        QueryBuilder.class,
                        HeaderBuilder.class
                );
        final Map<String, Object> templateArguments = createTemplateArguments(staticHeaders, staticQueryParameters, methodSignature);
        templateArguments.put("HAS_MODEL_HEADERS", false);
        templateArguments.put("HAS_MODEL_QUERY_PARAMS", false);

        addValidators(classHeaderBuilder, storage, methodSignature.getResponseModel(), templateArguments);

        return new RestClientMethodBody(
                methodBodyGenerator.generate(
                        "rest/client/method/$$RestClientWithoutParametersMethodBodyTemplate.javaftl",
                        templateArguments)
        );
    }

    private void addValidators(final ClassHeader.Builder classHeaderBuilder,
                               final RestClientClassStructureStorage storage,
                               final RestResponseModel methodResult,
                               final Map<String, Object> templateArguments) {
        templateArguments.put("GENERATE_REQUEST_VALIDATORS", false);
        final boolean generateResponseValidators =
                !methodResult.isReactiveVoid() &&
                        methodResult.getResultType().isPresent() &&
                        storage.isResponseValidatorPresent(
                                methodResult.getResultType().orElseThrow().asType().toString());
        if (generateResponseValidators) {
            if (methodResult.isFuture()) {
                classHeaderBuilder.addStaticImport(ResponseValidators.class, "validateIfResponseExists");
            } else {
                classHeaderBuilder.addStaticImport(ResponseValidators.class, "validateResponse");
            }
        }
        templateArguments.put("GENERATE_RESPONSE_VALIDATORS", generateResponseValidators);
    }
}
