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

package io.rxmicro.test.local.component.injector;

import io.rxmicro.rest.client.RestClientFactory;
import io.rxmicro.runtime.detail.ByTypeInstanceQualifier;
import io.rxmicro.runtime.local.AbstractFactory;
import io.rxmicro.test.local.AlternativeEntryPoint;

import java.util.List;

import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @since 0.1
 */
public final class RestClientInjector extends AbstractFactory {

    private final List<AlternativeEntryPoint> restClientComponents;

    RestClientInjector(final List<AlternativeEntryPoint> restClientComponents) {
        this.restClientComponents = require(restClientComponents);
    }

    @SuppressWarnings("unchecked")
    public void injectIfFound(final List<Object> testInstances) {
        if (!restClientComponents.isEmpty()) {
            registerFactory(RestClientFactory.REST_CLIENT_FACTORY_IMPL_CLASS_NAME, this);
            for (final AlternativeEntryPoint alternativeEntryPoint : restClientComponents) {
                alternativeEntryPoint.readValue(testInstances);
                register(
                        alternativeEntryPoint.getValue(),
                        new ByTypeInstanceQualifier<>((Class<Object>) alternativeEntryPoint.getFieldType())
                );
            }
        }
    }
}
