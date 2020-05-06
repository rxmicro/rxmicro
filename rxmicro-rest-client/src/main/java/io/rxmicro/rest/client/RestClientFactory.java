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

package io.rxmicro.rest.client;

import io.rxmicro.runtime.detail.ByTypeInstanceQualifier;
import io.rxmicro.runtime.local.AbstractFactory;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.common.util.GeneratedClassRules.GENERATED_CLASS_NAME_PREFIX;

/**
 * Utility class that must be used to get an instance of the REST client
 *
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public abstract class RestClientFactory extends AbstractFactory {

    /**
     * Default name of the REST client factory implementation class
     */
    public static final String REST_CLIENT_FACTORY_IMPL_CLASS_NAME =
            format("??Impl", GENERATED_CLASS_NAME_PREFIX, RestClientFactory.class.getSimpleName());

    /**
     * Returns an instance of the REST client
     *
     * @param restClientInterface the REST client interface
     * @return the instance of the REST client
     */
    public static <T> T getRestClient(final Class<T> restClientInterface) {
        return get(REST_CLIENT_FACTORY_IMPL_CLASS_NAME).getImpl(new ByTypeInstanceQualifier<>(restClientInterface))
                .orElseThrow(implNotFoundError(restClientInterface));
    }
}
