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

import io.rxmicro.test.BlockingHttpClient;
import io.rxmicro.test.local.BlockingHttpClientConfig;
import io.rxmicro.test.local.BlockingHttpClientImpl;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class BlockingHttpClientBuilder {

    private final HttpClientBuilder httpClientBuilder = new HttpClientBuilder();

    public BlockingHttpClient build(final Class<?> loggerClass,
                                    final BlockingHttpClientConfig config) {
        return new BlockingHttpClientImpl(
                httpClientBuilder.build(loggerClass, config),
                config.getVersionValue(),
                config.getVersionStrategy()
        );
    }
}
