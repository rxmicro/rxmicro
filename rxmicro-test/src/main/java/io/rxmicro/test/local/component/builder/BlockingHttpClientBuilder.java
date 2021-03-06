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

import io.rxmicro.rest.client.detail.HttpClientContentConverter;
import io.rxmicro.test.BlockingHttpClient;
import io.rxmicro.test.internal.http.JdkBlockingHttpClient;
import io.rxmicro.test.local.BlockingHttpClientConfig;
import io.rxmicro.test.local.InvalidTestConfigException;

import java.util.ServiceLoader;
import java.util.function.Function;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.runtime.local.Implementations.getOptionalImplementation;

/**
 * @author nedis
 * @since 0.8
 */
public final class BlockingHttpClientBuilder {

    public BlockingHttpClient build(final BlockingHttpClientConfig blockingHttpClientConfig) {
        final JdkBlockingHttpClient.Builder builder = new JdkBlockingHttpClient.Builder()
                .setBlockingHttpClientConfig(blockingHttpClientConfig)
                .setContentConverter(
                        getOptionalImplementation(HttpClientContentConverter.class, ServiceLoader::load)
                                .orElseGet(DefaultHttpClientContentConverter::new)
                );
        final String versionValue = blockingHttpClientConfig.getVersionValue();
        if (!versionValue.isEmpty()) {
            validateVersionValue(versionValue);
            builder.setVersion(
                    blockingHttpClientConfig.getVersionStrategy(),
                    versionValue
            );
        }
        final String baseUrlPath = blockingHttpClientConfig.getBaseUrlPath();
        if (!baseUrlPath.isEmpty()) {
            builder.setBaseUrl(
                    baseUrlPath,
                    blockingHttpClientConfig.getBaseUrlPosition()
            );
        }
        return builder.build();
    }

    private void validateVersionValue(final String versionValue) {
        if (!versionValue.equals(versionValue.trim())) {
            throw new InvalidTestConfigException("Invalid version value: '?'! Expected version value without spaces!", versionValue);
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
