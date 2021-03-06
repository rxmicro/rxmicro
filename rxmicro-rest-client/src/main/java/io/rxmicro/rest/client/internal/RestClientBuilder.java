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

package io.rxmicro.rest.client.internal;

import io.rxmicro.config.Secrets;
import io.rxmicro.rest.client.RestClientConfig;
import io.rxmicro.rest.client.detail.HttpClient;
import io.rxmicro.rest.client.detail.HttpClientContentConverter;
import io.rxmicro.rest.client.detail.HttpClientFactory;
import io.rxmicro.runtime.detail.ByTypeInstanceQualifier;
import io.rxmicro.runtime.local.provider.LazyInstanceProvider;

import java.util.ServiceLoader;

import static io.rxmicro.common.RxMicroModule.RX_MICRO_REST_CLIENT_EXCHANGE_JSON_MODULE;
import static io.rxmicro.common.RxMicroModule.RX_MICRO_REST_CLIENT_JDK_MODULE;
import static io.rxmicro.common.RxMicroModule.RX_MICRO_REST_CLIENT_NETTY_MODULE;
import static io.rxmicro.runtime.local.Implementations.getRequiredRuntimeImplementation;
import static io.rxmicro.runtime.local.InstanceContainer.getSingleton;

/**
 * @author nedis
 * @since 0.1
 */
public final class RestClientBuilder {

    public HttpClient createHttpClient(final Class<?> restClientInterface,
                                       final String namespace,
                                       final RestClientConfig restClientConfig) {
        return getHttpClientFactory()
                .create(
                        restClientInterface,
                        namespace,
                        restClientConfig,
                        Secrets.getDefaultInstance(),
                        getHttpClientBodyConverter()
                );
    }

    private HttpClientContentConverter getHttpClientBodyConverter() {
        return getSingleton(
                new ByTypeInstanceQualifier<>(HttpClientContentConverter.class),
                new LazyInstanceProvider<>(
                        HttpClientContentConverter.class,
                        () -> getRequiredRuntimeImplementation(
                                HttpClientContentConverter.class, ServiceLoader::load,
                                RX_MICRO_REST_CLIENT_EXCHANGE_JSON_MODULE
                        )
                )
        );
    }

    private HttpClientFactory getHttpClientFactory() {
        return getSingleton(
                new ByTypeInstanceQualifier<>(HttpClientFactory.class),
                new LazyInstanceProvider<>(
                        HttpClientFactory.class,
                        () -> getRequiredRuntimeImplementation(
                                HttpClientFactory.class, ServiceLoader::load,
                                RX_MICRO_REST_CLIENT_JDK_MODULE, RX_MICRO_REST_CLIENT_NETTY_MODULE
                        )
                )
        );
    }
}
