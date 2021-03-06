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

package io.rxmicro.rest.server.internal.component.impl;

import io.rxmicro.rest.server.RestServerConfig;
import io.rxmicro.rest.server.detail.component.HttpResponseBuilder;
import io.rxmicro.rest.server.internal.component.ComponentResolver;
import io.rxmicro.rest.server.internal.component.RequestMappingKeyBuilder;
import io.rxmicro.rest.server.local.component.DefaultHttpErrorResponseBodyBuilder;
import io.rxmicro.rest.server.local.component.HttpErrorResponseBodyBuilder;
import io.rxmicro.rest.server.local.component.ServerFactory;

import java.util.ServiceLoader;

import static io.rxmicro.common.RxMicroModule.RX_MICRO_REST_SERVER_NETTY_MODULE;
import static io.rxmicro.config.Configs.getConfig;
import static io.rxmicro.runtime.local.Implementations.getOptionalImplementation;
import static io.rxmicro.runtime.local.Implementations.getRequiredRuntimeImplementation;

/**
 * @author nedis
 * @since 0.1
 */
public final class ComponentResolverImpl implements ComponentResolver {

    private final HttpResponseBuilder httpResponseBuilder =
            getRequiredRuntimeImplementation(HttpResponseBuilder.class, ServiceLoader::load, RX_MICRO_REST_SERVER_NETTY_MODULE);

    private final ServerFactory serverFactory =
            getRequiredRuntimeImplementation(ServerFactory.class, ServiceLoader::load, RX_MICRO_REST_SERVER_NETTY_MODULE);

    private final HttpErrorResponseBodyBuilder httpErrorResponseBodyBuilder =
            getOptionalImplementation(HttpErrorResponseBodyBuilder.class, ServiceLoader::load)
                    .orElseGet(DefaultHttpErrorResponseBodyBuilder::new);

    private final RestServerConfig restServerConfig = getConfig(RestServerConfig.class);

    private final RequestMappingKeyBuilder requestMappingKeyBuilder = new RequestMappingKeyBuilderImpl();

    @Override
    public HttpResponseBuilder getHttpResponseBuilder() {
        return httpResponseBuilder;
    }

    @Override
    public HttpErrorResponseBodyBuilder getHttpErrorResponseBodyBuilder() {
        return httpErrorResponseBodyBuilder;
    }

    @Override
    public ServerFactory getServerFactory() {
        return serverFactory;
    }

    @Override
    public RestServerConfig getRestServerConfig() {
        return restServerConfig;
    }

    @Override
    public RequestMappingKeyBuilder getRequestMappingKeyBuilder() {
        return requestMappingKeyBuilder;
    }
}
