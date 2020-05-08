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

package io.rxmicro.test.local.component.builder;

import io.rxmicro.config.Secrets;
import io.rxmicro.http.client.HttpClient;
import io.rxmicro.http.client.HttpClientConfig;
import io.rxmicro.http.client.HttpClientContentConverter;
import io.rxmicro.http.client.HttpClientFactory;

import java.util.ServiceLoader;

import static io.rxmicro.runtime.local.Instances.getImplementation;

/**
 * @author nedis
 * @since 0.1
 */
public final class HttpClientBuilder {

    public HttpClient build(final Class<?> loggerClass,
                            final HttpClientConfig httpClientConfig) {
        final HttpClientFactory httpClientFactory =
                getImplementation(HttpClientFactory.class, true, ServiceLoader::load);
        final HttpClientContentConverter converter =
                getImplementation(HttpClientContentConverter.class, true, ServiceLoader::load);
        return httpClientFactory.create(loggerClass, httpClientConfig, DisabledSecretsImpl.INSTANCE, converter);
    }

    /**
     * @author nedis
     * @since 0.3
     */
    private static final class DisabledSecretsImpl implements Secrets {

        private static final DisabledSecretsImpl INSTANCE = new DisabledSecretsImpl();

        @Override
        public String hideIfSecret(final String value) {
            return value;
        }

        @Override
        public String hideAllSecretsIn(final String message) {
            return message;
        }
    }
}
