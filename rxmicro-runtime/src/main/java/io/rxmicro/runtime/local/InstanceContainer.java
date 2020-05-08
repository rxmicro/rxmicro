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

package io.rxmicro.runtime.local;

import io.rxmicro.logger.Logger;
import io.rxmicro.logger.LoggerFactory;
import io.rxmicro.runtime.AutoRelease;
import io.rxmicro.runtime.detail.InstanceQualifier;
import io.rxmicro.runtime.local.provider.NotUniqueInstanceProvider;
import io.rxmicro.runtime.local.provider.ProxyInstanceProvider;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

import static io.rxmicro.common.Constants.RX_MICRO_FRAMEWORK_NAME;
import static io.rxmicro.common.util.ExCollectors.toTreeSet;
import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.common.util.Requires.require;
import static io.rxmicro.runtime.internal.RuntimeVersion.setRxMicroVersion;
import static java.lang.Runtime.getRuntime;

/**
 * @author nedis
 * @since 0.1
 */
public final class InstanceContainer {

    private static final Logger LOGGER = LoggerFactory.getLogger(InstanceContainer.class);

    private static final Map<InstanceQualifier<?>, InstanceProvider<?>> COMPONENT_MAP = new ConcurrentHashMap<>();

    private final static Set<AutoRelease> AUTO_RELEASES = new HashSet<>();

    static {
        setRxMicroVersion();
        getRuntime().addShutdownHook(
                new Thread(InstanceContainer::closeAll, format("?-CloseableShutdownHook", RX_MICRO_FRAMEWORK_NAME))
        );
    }

    public static void registerAutoRelease(final AutoRelease autoRelease) {
        AUTO_RELEASES.add(require(autoRelease, format("autoRelease couldn't be null")));
    }

    @SafeVarargs
    public static <T> InstanceProvider<T> registerSingleton(final InstanceProvider<T> componentInstanceProvider,
                                                            final InstanceQualifier<? super T>... instanceQualifiers) {

        return registerSingleton(
                componentInstanceProvider,
                (instanceProvider, instanceQualifier) -> {
                    COMPONENT_MAP.put(instanceQualifier, new NotUniqueInstanceProvider<>(instanceQualifier.getType()));
                    LOGGER.debug("Instance qualifier: '?' is not unique for '?' component",
                            instanceQualifier, instanceProvider.getType().getName());
                },
                instanceQualifiers
        );
    }

    @SafeVarargs
    public static <T> void overrideSingleton(final InstanceProvider<T> componentInstanceProvider,
                                             final InstanceQualifier<? super T>... instanceQualifiers) {

        registerSingleton(
                componentInstanceProvider,
                (instanceProvider, instanceQualifier) -> COMPONENT_MAP.put(instanceQualifier, instanceProvider),
                instanceQualifiers
        );
    }

    @SafeVarargs
    private static <T> InstanceProvider<T> registerSingleton(final InstanceProvider<T> componentInstanceProvider,
                                                             final BiConsumer<InstanceProvider<T>, InstanceQualifier<? super T>> conflictResolver,
                                                             final InstanceQualifier<? super T>... instanceQualifiers) {

        final InstanceProvider<T> instanceProvider;
        if (AutoRelease.class.isAssignableFrom(componentInstanceProvider.getType())) {
            instanceProvider = new ProxyInstanceProvider<>(
                    componentInstanceProvider,
                    instance -> registerAutoRelease((AutoRelease) instance)
            );
        } else {
            instanceProvider = componentInstanceProvider;
        }
        for (final InstanceQualifier<? super T> instanceQualifier : instanceQualifiers) {
            if (COMPONENT_MAP.containsKey(instanceQualifier)) {
                conflictResolver.accept(instanceProvider, instanceQualifier);
            } else {
                COMPONENT_MAP.put(instanceQualifier, instanceProvider);
            }
        }
        return instanceProvider;
    }

    @SuppressWarnings("unchecked")
    public static <T> Optional<T> getSingleton(final InstanceQualifier<? super T> instanceQualifier) {
        return (Optional<T>) Optional.ofNullable(COMPONENT_MAP.get(instanceQualifier)).map(InstanceProvider::getInstance);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getSingleton(final InstanceQualifier<? super T> instanceQualifier,
                                     final InstanceProvider<T> componentInstanceProvider) {
        InstanceProvider<?> instanceProvider = COMPONENT_MAP.get(instanceQualifier);
        if (instanceProvider == null) {
            instanceProvider = registerSingleton(componentInstanceProvider, instanceQualifier);
        }
        return (T) instanceProvider.getInstance();
    }

    @SuppressWarnings("unchecked")
    public static <T> Set<T> getSingletonsByType(final Class<T> interfaceType) {
        return COMPONENT_MAP.values().stream()
                .filter(p -> interfaceType.isAssignableFrom(p.getType()) &&
                        p.getClass() != NotUniqueInstanceProvider.class)
                .map(p -> (T) p.getInstance())
                .collect(toTreeSet(Comparator.comparing(o -> o.getClass().getName())));
    }

    public static void clearContainer() {
        COMPONENT_MAP.clear();
        closeAll();
    }

    private static void closeAll() {
        for (final AutoRelease autoRelease : AUTO_RELEASES) {
            try {
                autoRelease.release();
            } catch (final Throwable throwable) {
                LOGGER.error(throwable, "Error during close: ?", throwable.getMessage());
            }
        }
        AUTO_RELEASES.clear();
    }

    private InstanceContainer() {
    }
}
