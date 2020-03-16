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

package io.rxmicro.test.local.component.injector;

import io.rxmicro.test.local.AlternativeEntryPoint;
import io.rxmicro.test.local.InvalidTestConfigException;
import io.rxmicro.test.local.component.builder.internal.InjectedCandidateBuilder;
import io.rxmicro.test.local.component.injector.internal.InjectedCandidateInjector;
import io.rxmicro.test.local.model.internal.InjectedCandidate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class UserCreatedComponentInjector {

    private final List<AlternativeEntryPoint> userCreatedComponents;

    private final InjectedCandidateBuilder injectedCandidateBuilder;

    private final InjectedCandidateInjector injectedCandidateInjector;

    UserCreatedComponentInjector(final List<AlternativeEntryPoint> userCreatedComponents) {
        this.userCreatedComponents = userCreatedComponents;
        final Map<Class<?>, List<AlternativeEntryPoint>> alternativeEntryPointsMap = new HashMap<>();
        for (final AlternativeEntryPoint alternative : userCreatedComponents) {
            alternativeEntryPointsMap.computeIfAbsent(alternative.getFieldType(), v -> new ArrayList<>()).add(alternative);
        }
        this.injectedCandidateBuilder = new InjectedCandidateBuilder(alternativeEntryPointsMap.keySet());
        this.injectedCandidateInjector = new InjectedCandidateInjector(alternativeEntryPointsMap);
    }

    public void injectIfFound(final List<Object> testInstances,
                              final List<Object> destinationInstances) {
        if (userCreatedComponents.isEmpty()) {
            return;
        }
        userCreatedComponents.forEach(a -> a.readValue(testInstances));
        for (final Object destinationInstance : destinationInstances) {
            final Map<Class<?>, List<InjectedCandidate>> candidatesMap =
                    injectedCandidateBuilder.build(destinationInstance);
            validate(candidatesMap);
            injectedCandidateInjector.inject(candidatesMap);
        }
    }

    private void validate(final Map<Class<?>, List<InjectedCandidate>> candidatesMap) {
        for (final Map.Entry<Class<?>, List<InjectedCandidate>> entry : candidatesMap.entrySet()) {
            if (entry.getValue().isEmpty()) {
                throw new InvalidTestConfigException(
                        "Redundant alternative of the user component: '?'. " +
                                "Remove redundant alternative!",
                        userCreatedComponents.stream()
                                .filter(a -> entry.getKey() == a.getFieldType())
                                .findFirst()
                                .orElseThrow()
                                .getHumanReadableErrorName()
                );
            }
        }
    }
}
