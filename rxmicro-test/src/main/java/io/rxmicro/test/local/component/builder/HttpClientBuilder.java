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

package io.rxmicro.test.local.component.builder;

import io.rxmicro.config.Secrets;
import io.rxmicro.http.client.HttpClient;
import io.rxmicro.http.client.HttpClientConfig;
import io.rxmicro.http.client.HttpClientContentConverter;
import io.rxmicro.http.client.HttpClientFactory;

import java.util.ServiceLoader;
import java.util.function.Function;

import static io.rxmicro.common.RxMicroModule.RX_MICRO_REST_CLIENT_JDK_MODULE;
import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.runtime.local.Implementations.getOptionalImplementation;
import static io.rxmicro.runtime.local.Implementations.getRequiredTestImplementation;

/**
 * @author nedis
 * @since 0.1
 */
public final class HttpClientBuilder {

    public HttpClient build(final Class<?> loggerClass,
                            final HttpClientConfig httpClientConfig) {
        final HttpClientFactory httpClientFactory =
                getRequiredTestImplementation(HttpClientFactory.class, ServiceLoader::load, RX_MICRO_REST_CLIENT_JDK_MODULE);
        final HttpClientContentConverter converter =
                getOptionalImplementation(HttpClientContentConverter.class, ServiceLoader::load)
                        .orElseGet(DefaultHttpClientContentConverter::new);
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

    /**
     * @author nedis
     * @since 0.8
     */
    private static final class DefaultHttpClientContentConverter implements HttpClientContentConverter {

        @Override
        public Function<Object, byte[]> getRequestContentConverter() {
            return o -> {
                if (o == null) {
                    return new byte[0];
                } else if (o instanceof byte[]) {
                    return (byte[]) o;
                } else {
                    throw new UnsupportedOperationException(format("Can't convert ? to byte array", o.getClass().getName()));
                }
            };
        }

        @Override
        public Function<byte[], Object> getResponseContentConverter() {
            return bytes -> bytes;
        }

        /**
         * RFC 2046 states in section 4.5.1:
         *
         * The "octet-stream" subtype is used to indicate that a body contains arbitrary binary data.
         */
        @Override
        public String getContentType() {
            return "application/octet-stream";
        }
    }
}
