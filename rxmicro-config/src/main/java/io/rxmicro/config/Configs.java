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

package io.rxmicro.config;

import io.rxmicro.config.internal.EnvironmentConfigLoader;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static io.rxmicro.config.Config.getDefaultNameSpace;
import static io.rxmicro.config.ConfigLoadSource.DEFAULT_CONFIG_VALUES;
import static io.rxmicro.config.ConfigLoadSource.ENVIRONMENT_VARIABLES;
import static io.rxmicro.config.ConfigLoadSource.JAVA_SYSTEM_PROPERTIES;
import static io.rxmicro.config.ConfigLoadSource.RXMICRO_CLASS_PATH_RESOURCE;
import static io.rxmicro.config.ConfigLoadSource.SEPARATE_CLASS_PATH_RESOURCE;
import static java.util.Arrays.asList;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class Configs {

    private static final Set<ConfigLoadSource> DEFAULT_CONFIG_LOAD_SOURCE_ORDER = new LinkedHashSet<>(asList(
            DEFAULT_CONFIG_VALUES,
            RXMICRO_CLASS_PATH_RESOURCE,
            SEPARATE_CLASS_PATH_RESOURCE,
            ENVIRONMENT_VARIABLES,
            JAVA_SYSTEM_PROPERTIES
    ));

    private static Configs INSTANCE;

    private final EnvironmentConfigLoader loader;

    private final Map<String, Config> storage;

    @SuppressWarnings("unchecked")
    public static <T extends Config> T getConfig(final String nameSpace,
                                                 final Class<T> configClass) {
        if (INSTANCE == null) {
            throw new ConfigException(
                    "Configs are not built. Use Configs.Builder to build configuration");
        }
        return (T) INSTANCE.storage.computeIfAbsent(nameSpace, n ->
                INSTANCE.loader.getEnvironmentConfig(nameSpace, configClass));
    }

    public static <T extends Config> T getConfig(final Class<T> configClass) {
        return getConfig(getDefaultNameSpace(configClass), configClass);
    }

    public static Module getConfigModule() {
        return Configs.class.getModule();
    }

    private Configs(final Map<String, Config> storage,
                    final Set<ConfigLoadSource> configLoadSources) {
        this.loader = new EnvironmentConfigLoader(configLoadSources);
        this.storage = new ConcurrentHashMap<>(storage);
    }

    /**
     * @author nedis
     * @link https://rxmicro.io
     * @since 0.1
     */
    @SuppressWarnings("UnusedReturnValue")
    public static final class Builder {

        private final Map<String, Config> storage;

        private final Set<ConfigLoadSource> configLoadSources = new LinkedHashSet<>(DEFAULT_CONFIG_LOAD_SOURCE_ORDER);

        public Builder() {
            storage = new HashMap<>();
        }

        public Builder withConfig(final String nameSpace,
                                  final Config config) {
            final Config oldConfig = storage.put(nameSpace, config);
            if (oldConfig != null) {
                throw new ConfigException(
                        "'?' name space is already configured. Old class is '?', new class is '?'",
                        nameSpace, oldConfig.getClass().getName(), config.getClass().getName());
            }
            return this;
        }

        public Builder withConfigs(final Config... configs) {
            for (final Config config : configs) {
                withConfig(config.getNameSpace(), config);
            }
            return this;
        }

        public Builder withConfigs(final Map<String, Config> configs) {
            configs.forEach(this::withConfig);
            return this;
        }

        public Builder withOrderedConfigLoadSources(final ConfigLoadSource... sources) {
            configLoadSources.clear();
            configLoadSources.addAll(Arrays.asList(sources));
            return this;
        }

        public Builder withoutAnyConfigLoadSources() {
            configLoadSources.clear();
            return this;
        }

        public Builder withAllConfigLoadSources() {
            withOrderedConfigLoadSources(ConfigLoadSource.values());
            return this;
        }

        public Builder withDockerConfigLoadSources() {
            withOrderedConfigLoadSources(
                    DEFAULT_CONFIG_VALUES,
                    ENVIRONMENT_VARIABLES
            );
            return this;
        }

        public void build() {
            INSTANCE = new Configs(storage, configLoadSources);
        }

        public void buildIfNotConfigured() {
            if (INSTANCE == null) {
                build();
            }
        }
    }
}
