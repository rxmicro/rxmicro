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
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.annotation.processor.rest.client.model.RestClientMethodSignature;
import io.rxmicro.annotation.processor.rest.model.RestObjectModelClass;
import io.rxmicro.annotation.processor.rest.model.StaticQueryParameters;
import io.rxmicro.exchange.json.detail.ModelToJsonConverter;
import io.rxmicro.rest.client.detail.HeaderBuilder;

import javax.lang.model.element.TypeElement;
import java.util.Map;

import static io.rxmicro.annotation.processor.common.util.GeneratedClassNames.getModelTransformerInstanceName;
import static io.rxmicro.annotation.processor.common.util.Names.getSimpleName;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
@Singleton
public final class HttpBodyObjectParameterRestClientMethodBodyBuilder
        extends AbstractParametrizedBaseRestClientMethodBodyBuilder {

    @Override
    public boolean isSupported(final RestClientMethodSignature methodSignature) {
        if (!methodSignature.getHttpMethodMapping().isHttpBody()) {
            return false;
        }
        return !methodSignature.getRequestModel().requestModelNotExists();
    }

    @Override
    protected String getTemplateName() {
        return "rest/client/method/$$RestClientHttpBodyObjectParameterMethodBodyTemplate.javaftl";
    }

    @Override
    protected void validate(final RestClientMethodSignature methodSignature,
                            final StaticQueryParameters queryParams) {
        if (!queryParams.isEmpty()) {
            throw new InterruptProcessingException(methodSignature.getMethod(),
                    "Query parameter(s) not allowed for '?' HTTP method",
                    methodSignature.getHttpMethodMapping().getMethod()
            );
        }
    }

    @Override
    protected void customizeClassHeaderBuilder(final ClassHeader.Builder classHeaderBuilder) {
        classHeaderBuilder.addImports(HeaderBuilder.class);
    }

    @Override
    protected void customizeTemplateArguments(final Map<String, Object> templateArguments,
                                              final RestObjectModelClass modelClass,
                                              final TypeElement parameterType) {
        templateArguments.put("HAS_MODEL_QUERY_PARAMS", false);
        templateArguments.put("HAS_BODY", modelClass.isParamsPresent());

        final String instanceName = getModelTransformerInstanceName(getSimpleName(parameterType), ModelToJsonConverter.class);
        templateArguments.put("REQUEST_CONVERTER", instanceName);
    }
}
