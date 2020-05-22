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

package io.rxmicro.rest.client.detail;

import io.rxmicro.config.Configs;
import io.rxmicro.http.client.HttpClient;
import io.rxmicro.http.client.HttpClientConfig;
import io.rxmicro.rest.client.internal.RestClientBuilder;

import java.util.function.BiFunction;

import static io.rxmicro.config.Configs.getConfig;
import static io.rxmicro.rest.client.internal.HttpClientLoggerHelper.logClientConfig;
import static io.rxmicro.runtime.local.InstanceContainer.registerAutoRelease;

/**
 * Used by generated code that created by the {@code RxMicro Annotation Processor}.
 *
 * @author nedis
 * @hidden
 * @since 0.1
 */
public final class RestClientImplFactory {

    private static final RestClientBuilder BUILDER = new RestClientBuilder();

    static {
        new Configs.Builder().buildIfNotConfigured();
    }

    public static <T, C extends HttpClientConfig> T createRestClient(final String namespace,
                                                                     final Class<C> configClass,
                                                                     final Class<?> restClientInterface,
                                                                     final BiFunction<HttpClient, C, T> creator) {
        final C config = getConfig(namespace, configClass);
        final HttpClient httpClient = BUILDER.createHttpClient(restClientInterface, config);
        logClientConfig(restClientInterface, httpClient, config);
        registerAutoRelease(httpClient);
        return creator.apply(httpClient, config);
    }

    private RestClientImplFactory() {
    }
}
