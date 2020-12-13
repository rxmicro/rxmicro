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

package io.rxmicro.rest.server;

import io.rxmicro.common.RxMicroException;
import io.rxmicro.rest.server.feature.RequestIdGenerator;

/**
 * Defines a request id generator provider.
 *
 * @author nedis
 * @see RestServerConfig
 * @since 0.1
 */
public interface RequestIdGeneratorProvider {

    /**
     * Returns the request id generator instance.
     *
     * @param restServerConfig the rest server config instance
     * @return the request id generator instance
     * @see RequestIdGenerator
     * @see RestServerConfig#getWaitingForRequestIdGeneratorInitTimeoutInMillis()
     * @throws CurrentRequestIdGeneratorCantBeUsedException if current request id generator can't be used
     */
    RequestIdGenerator getRequestIdGenerator(RestServerConfig restServerConfig);

    /**
     * Indicates that current request id generator can't be used, because the next request id generation method is blocked by
     * operation system for example, if the entropy source is {@code /dev/random} on various Unix-like operating systems.
     *
     * @author nedis
     * @since 0.7.3
     */
    class CurrentRequestIdGeneratorCantBeUsedException extends RxMicroException {

        /**
         * Creates a default instance with the specified request id generator.
         *
         * @param requestIdGenerator the specified request id generator.
         */
        public CurrentRequestIdGeneratorCantBeUsedException(final RequestIdGenerator requestIdGenerator) {
            super(
                    "The '?' request id generator can't be used, " +
                            "because the next request id generation method is blocked by operation system!",
                    requestIdGenerator.getClass().getName()
            );
        }
    }
}
