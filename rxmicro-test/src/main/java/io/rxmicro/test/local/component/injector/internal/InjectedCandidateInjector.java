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

package io.rxmicro.test.local.component.injector.internal;

import io.rxmicro.common.ImpossibleException;
import io.rxmicro.test.internal.AlternativeEntryPoint;
import io.rxmicro.test.local.InvalidTestConfigException;
import io.rxmicro.test.local.model.internal.InjectedCandidate;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static io.rxmicro.common.util.Reflections.setFieldValue;
import static io.rxmicro.common.util.Requires.require;
import static java.util.stream.Collectors.toList;

/**
 * @author nedis
 * @since 0.1
 */
public final class InjectedCandidateInjector {

    private final Map<Class<?>, List<AlternativeEntryPoint>> alternativeEntryPointsMap;

    public InjectedCandidateInjector(final Map<Class<?>, List<AlternativeEntryPoint>> alternativeEntryPointsMap) {
        this.alternativeEntryPointsMap = require(alternativeEntryPointsMap);
    }

    public void inject(final Map<Class<?>, List<InjectedCandidate>> candidatesMap) {
        final Set<AlternativeEntryPoint> injectedAlternatives = new HashSet<>();
        for (final Map.Entry<Class<?>, List<InjectedCandidate>> entry : candidatesMap.entrySet()) {
            final List<InjectedCandidate> injectedCandidates = entry.getValue();
            final List<AlternativeEntryPoint> alternatives = require(alternativeEntryPointsMap.get(entry.getKey()));
            if (injectedCandidates.size() == 1 && alternatives.size() == 1) {
                injectSingleAlternativeToInjectionCandidate(injectedAlternatives, injectedCandidates, alternatives);
            } else if (injectedCandidates.size() == 1 && alternatives.size() > 1) {
                throwConfigError(injectedCandidates, alternatives);
            } else if (!alternatives.isEmpty() && injectedCandidates.size() > 1) {
                injectMultipleAlternativesToInjectionCandidates(injectedAlternatives, injectedCandidates, alternatives);
            } else {
                throw new ImpossibleException("Injected candidate internal logic error");
            }
        }
        validateRedundantAlternatives(injectedAlternatives);
    }

    private void injectSingleAlternativeToInjectionCandidate(final Set<AlternativeEntryPoint> injectedAlternatives,
                                                             final List<InjectedCandidate> injectedCandidates,
                                                             final List<AlternativeEntryPoint> alternatives) {
        final InjectedCandidate injectedCandidate = injectedCandidates.get(0);
        final AlternativeEntryPoint alternative = alternatives.get(0);
        setFieldValue(injectedCandidate.getInstance(), injectedCandidate.getField(), alternative.getValue());
        injectedAlternatives.add(alternative);
    }

    private void throwConfigError(final List<InjectedCandidate> injectedCandidates,
                                  final List<AlternativeEntryPoint> alternatives) {
        final InjectedCandidate injectedCandidate = injectedCandidates.get(0);
        throw new InvalidTestConfigException(
                "Detected ? alternatives for one injection point: ?. " +
                        "Only one alternative is allowed from the following items: ?." +
                        "Remove redundant alternatives!",
                alternatives.size(),
                injectedCandidate.getHumanReadableErrorName(),
                alternatives.stream().map(AlternativeEntryPoint::getHumanReadableErrorName).collect(toList()));
    }

    private void injectMultipleAlternativesToInjectionCandidates(final Set<AlternativeEntryPoint> injectedAlternatives,
                                                                 final List<InjectedCandidate> injectedCandidates,
                                                                 final List<AlternativeEntryPoint> alternatives) {
        boolean injected = false;
        for (final AlternativeEntryPoint alternative : alternatives) {
            for (final InjectedCandidate injectedCandidate : injectedCandidates) {
                if (injectedCandidate.getField().getName().equals(alternative.getInjectionName())) {
                    setFieldValue(injectedCandidate.getInstance(), injectedCandidate.getField(), alternative.getValue());
                    injectedAlternatives.add(alternative);
                    injected = true;
                }
            }
        }
        // Injected unspecified alternative to all injection points
        if (alternatives.size() == 1 && !injected) {
            for (final InjectedCandidate injectedCandidate : injectedCandidates) {
                final AlternativeEntryPoint alternative = alternatives.get(0);
                setFieldValue(injectedCandidate.getInstance(), injectedCandidate.getField(), alternative.getValue());
                injectedAlternatives.add(alternative);
            }
        }
    }

    private void validateRedundantAlternatives(final Set<AlternativeEntryPoint> injectedAlternatives) {
        alternativeEntryPointsMap.values().stream()
                .flatMap(Collection::stream)
                .filter(a -> !injectedAlternatives.contains(a))
                .findFirst()
                .ifPresent(a -> {
                    throw new InvalidTestConfigException(
                            "'?' alternative is redundant. " +
                                    "Remove redundant alternative!",
                            a.getHumanReadableErrorName());
                });
    }
}
