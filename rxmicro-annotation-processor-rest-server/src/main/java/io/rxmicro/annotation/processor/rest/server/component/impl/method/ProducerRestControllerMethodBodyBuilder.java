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

package io.rxmicro.annotation.processor.rest.server.component.impl.method;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.common.component.MethodBodyGenerator;
import io.rxmicro.annotation.processor.common.model.ClassHeader;
import io.rxmicro.annotation.processor.common.model.method.MethodBody;
import io.rxmicro.annotation.processor.common.model.method.MethodName;
import io.rxmicro.annotation.processor.rest.model.RestRequestModel;
import io.rxmicro.annotation.processor.rest.model.RestResponseModel;
import io.rxmicro.annotation.processor.rest.model.StaticHeaders;
import io.rxmicro.annotation.processor.rest.server.component.RestControllerMethodBodyBuilder;
import io.rxmicro.annotation.processor.rest.server.model.RestControllerClassStructureStorage;
import io.rxmicro.annotation.processor.rest.server.model.RestControllerMethodBody;
import io.rxmicro.annotation.processor.rest.server.model.RestControllerMethodSignature;
import io.rxmicro.annotation.processor.rest.server.model.RestServerModuleGeneratorConfig;

import java.util.Map;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
@Singleton
public final class ProducerRestControllerMethodBodyBuilder implements RestControllerMethodBodyBuilder {

    @Inject
    private MethodBodyGenerator methodBodyGenerator;

    @Override
    public boolean isSupport(final RestControllerMethodSignature methodSignature) {
        return methodSignature.getRequestModel().requestModelNotExists() &&
                methodSignature.getResponseModel().getResultType().isPresent();
    }

    @Override
    public MethodBody build(final RestServerModuleGeneratorConfig restServerModuleGeneratorConfig,
                            final ClassHeader.Builder classHeaderBuilder,
                            final MethodName methodName,
                            final int successStatusCode,
                            final StaticHeaders staticHeaders,
                            final RestRequestModel requestModel,
                            final RestResponseModel responseModel,
                            final RestControllerClassStructureStorage restControllerClassStructureStorage) {
        return new RestControllerMethodBody(
                methodBodyGenerator.generate(
                        "rest/server/method/$$RestControllerProducerMethodBodyTemplate.javaftl",
                        Map.of(
                                "METHOD_NAME", methodName,
                                "STATUS_CODE", successStatusCode,
                                "RETURN", responseModel,
                                "STATIC_HEADERS", staticHeaders.getEntries()
                        ))
        );
    }
}
