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

import io.rxmicro.runtime.detail.ByTypeInstanceQualifier;
import io.rxmicro.runtime.local.provider.EagerInstanceProvider;
import io.rxmicro.test.local.AlternativeEntryPoint;

import java.util.List;

import static io.rxmicro.common.util.Requires.require;
import static io.rxmicro.runtime.local.InstanceContainer.registerSingleton;

/**
 * @author nedis
 * @since 0.1
 */
public final class RuntimeContextComponentInjector {

    private final List<AlternativeEntryPoint> runtimeContextRegistrationComponents;

    RuntimeContextComponentInjector(final List<AlternativeEntryPoint> runtimeContextRegistrationComponents) {
        this.runtimeContextRegistrationComponents = require(runtimeContextRegistrationComponents);
    }

    @SuppressWarnings("unchecked")
    public void injectIfFound(final List<Object> testInstances) {
        for (final AlternativeEntryPoint alternativeEntryPoint : runtimeContextRegistrationComponents) {
            alternativeEntryPoint.readValue(testInstances);
            registerSingleton(
                    new EagerInstanceProvider<>(alternativeEntryPoint.getValue()),
                    new ByTypeInstanceQualifier<>((Class<Object>) alternativeEntryPoint.getFieldType())
            );
        }
    }
}
