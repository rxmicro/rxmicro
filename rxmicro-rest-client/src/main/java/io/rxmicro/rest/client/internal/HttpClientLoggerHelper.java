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

import io.rxmicro.logger.Logger;
import io.rxmicro.logger.LoggerFactory;
import io.rxmicro.rest.client.RestClientConfig;
import io.rxmicro.rest.client.detail.HttpClient;

import static java.lang.System.lineSeparator;

/**
 * @author nedis
 * @since 0.1
 */
public final class HttpClientLoggerHelper {

    public static void logClientConfig(final Class<?> restClientInterface,
                                       final HttpClient httpClient,
                                       final RestClientConfig config) {
        final Logger logger = LoggerFactory.getLogger(restClientInterface);
        if (logger.isDebugEnabled()) {
            logger.debug("Http client created: ?\tConfig=? ?\tImpl class=?",
                    lineSeparator(),
                    config,
                    lineSeparator(),
                    httpClient.getClass().getName());
        } else {
            logger.info("Http client with endpoint '?' created", config::getConnectionString);
        }
    }

    private HttpClientLoggerHelper() {
    }
}
