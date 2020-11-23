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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static io.rxmicro.config.Config.getDefaultNameSpace;
import static io.rxmicro.config.ConfigSource.DEFAULT_CONFIG_VALUES;
import static io.rxmicro.config.ConfigSource.ENVIRONMENT_VARIABLES;
import static io.rxmicro.config.ConfigSource.JAVA_SYSTEM_PROPERTIES;
import static io.rxmicro.config.ConfigSource.RXMICRO_CLASS_PATH_RESOURCE;
import static io.rxmicro.config.ConfigSource.SEPARATE_CLASS_PATH_RESOURCE;
import static io.rxmicro.config.ConfigSource.SEPARATE_FILE_AT_THE_RXMICRO_CONFIG_DIR;
import static io.rxmicro.config.internal.waitfor.component.WaitForUtils.withoutWaitForArguments;
import static java.util.Arrays.asList;
import static java.util.Map.entry;
import static java.util.stream.Collectors.toMap;

/**
 * Allows working with all supported configs.
 *
 * <p>
 * To get an instance of the requested config it is necessary to use
 * {@link Configs#getConfig(Class)} or {@link Configs#getConfig(String, Class)} methods.
 *
 * @author nedis
 * @see Config
 * @see ConfigSource
 * @since 0.1
 */
public final class Configs {

    private static final Set<ConfigSource> DEFAULT_CONFIG_LOAD_SOURCE_ORDER = new LinkedHashSet<>(asList(
            DEFAULT_CONFIG_VALUES,
            RXMICRO_CLASS_PATH_RESOURCE,
            SEPARATE_CLASS_PATH_RESOURCE,
            ENVIRONMENT_VARIABLES,
            JAVA_SYSTEM_PROPERTIES
    ));

    private static Configs instance;

    private final EnvironmentConfigLoader loader;

    private final Map<String, Config> storage;

    private final Map<String, String> commandLineArgs;

    /**
     * Returns the requested config instance that corresponds to the provided {@code namespace} and {@code configClass}.
     *
     * @param namespace the requested namespace
     * @param configClass the requested config class
     * @param <T> config type
     * @return the config instance
     * @throws ConfigException if Configs are not built
     */
    @SuppressWarnings("unchecked")
    public static <T extends Config> T getConfig(final String namespace,
                                                 final Class<T> configClass) {
        if (instance == null) {
            throw new ConfigException(
                    "Configs are not built. Use Configs.Builder to build configuration");
        }
        return (T) instance.storage.computeIfAbsent(namespace, n ->
                instance.loader.getEnvironmentConfig(namespace, configClass, instance.commandLineArgs));
    }

    /**
     * Returns the requested config instance that corresponds to the provided {@code configClass} and the default namespace.
     *
     * <p>
     * The RxMicro framework uses the {@link Config#getDefaultNameSpace(Class)} method to define the default namespace.
     *
     * @param configClass the requested config class
     * @param <T> the config type
     * @return the config instance
     */
    public static <T extends Config> T getConfig(final Class<T> configClass) {
        return getConfig(getDefaultNameSpace(configClass), configClass);
    }

    private Map<String, String> commandLineArgsToMap(final List<String> commandLineArgs) {
        return commandLineArgs.isEmpty() ?
                Map.of() :
                commandLineArgs.stream().map(cmd -> {
                    final String[] data = cmd.split("=");
                    if (data.length != 2) {
                        throw new ConfigException("Invalid command line arguments. " +
                                "Expected: 'name_space.propertyName=propertyValue', but actual is '?'", cmd);
                    } else {
                        return entry(data[0], data[1]);
                    }
                }).collect(toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private Configs(final Map<String, Config> storage,
                    final Set<ConfigSource> configSources,
                    final List<String> commandLineArgs) {
        this.loader = new EnvironmentConfigLoader(configSources);
        this.storage = new ConcurrentHashMap<>(storage);
        this.commandLineArgs = commandLineArgsToMap(commandLineArgs);
    }

    /**
     * Allows configuring the {@link Configs} manager before usage.
     *
     * @author nedis
     * @since 0.1
     */
    @SuppressWarnings("UnusedReturnValue")
    public static final class Builder {

        private final List<String> commandLineArgs = new ArrayList<>();

        private final Map<String, Config> storage;

        private final Set<ConfigSource> configSources = new LinkedHashSet<>(DEFAULT_CONFIG_LOAD_SOURCE_ORDER);

        /**
         * Creates default {@link Builder} instance.
         */
        public Builder() {
            storage = new HashMap<>();
        }

        /**
         * Allows adding the configuration using java classes with custom namespace.
         *
         * @param namespace the custom namespace
         * @param config the created by developer config instance
         * @return the reference to this {@link Builder} instance
         */
        public Builder withConfig(final String namespace,
                                  final Config config) {
            final Config oldConfig = storage.put(namespace, config);
            if (oldConfig != null) {
                throw new ConfigException(
                        "'?' namespace is already configured. Old class is '?', new class is '?'",
                        namespace, oldConfig.getClass().getName(), config.getClass().getName());
            }
            return this;
        }

        /**
         * Allows adding configurations using java classes with default namespace.
         *
         * <p>
         * The RxMicro framework uses {@link Config#getDefaultNameSpace(Class)} method to define a default namespace.
         *
         * @param configs the var args of config instances
         * @return the reference to this {@link Builder} instance
         */
        public Builder withConfigs(final Config... configs) {
            for (final Config config : configs) {
                withConfig(config.getNameSpace(), config);
            }
            return this;
        }

        /**
         * Allows adding the configuration using java classes with custom namespace.
         *
         * @param configs the map that contains entries with custom namespaces and config instances.
         * @return the reference to this {@link Builder} instance
         */
        public Builder withConfigs(final Map<String, Config> configs) {
            configs.forEach(this::withConfig);
            return this;
        }

        /**
         * Allows changing the order of the configuration reading.
         *
         * @param sources the custom order of the configuration reading
         * @return the reference to this {@link Builder} instance
         */
        public Builder withOrderedConfigSources(final ConfigSource... sources) {
            configSources.clear();
            configSources.addAll(asList(sources));
            return this;
        }

        /**
         * Disables all configuration sources.
         *
         * <p>
         * <i>This method can be useful for tests.</i>
         *
         * @return the reference to this {@link Builder} instance
         */
        public Builder withoutAnyConfigSources() {
            configSources.clear();
            return this;
        }

        /**
         * Enables all supported config sources according to natural order.
         *
         * <p>
         * Natural order is defined by {@link ConfigSource} enum.
         *
         * @return the reference to this {@link Builder} instance
         */
        public Builder withAllConfigSources() {
            withOrderedConfigSources(ConfigSource.values());
            return this;
        }

        /**
         * Enables config sources according to recommended order for docker or kubernetes environments.
         *
         * <p>
         * Recommended order for docker or kubernetes environments:
         * <ol>
         *     <li>Hardcoded config using annotations.</li>
         *     <li>Config from env variables.</li>
         *     <li>Config from file: {@code ~/.rxmicro/${name_space}.properties}</li>
         * </ol>
         *
         * @return the reference to this {@link Builder} instance
         */
        public Builder withContainerConfigSources() {
            withOrderedConfigSources(
                    DEFAULT_CONFIG_VALUES,
                    ENVIRONMENT_VARIABLES,
                    SEPARATE_FILE_AT_THE_RXMICRO_CONFIG_DIR
            );
            return this;
        }

        /**
         * Enables the config reading from command line arguments.
         *
         * <p>
         * This type of configuration has the highest priority and overrides all other types.
         *
         * <p>
         * (Except of configuration using Java classes.)
         *
         * @param args command line arguments
         * @return the reference to this {@link Builder} instance
         */
        public Builder withCommandLineArguments(final String... args) {
            if (args.length > 0) {
                commandLineArgs.addAll(withoutWaitForArguments(args));
            }
            return this;
        }

        /**
         * Creates a new immutable instance of the {@link Configs} manager.
         *
         * <p>
         * Each subsequent invocation of this method overrides all configuration manager settings.
         *
         * <p>
         * <strong>(In any microservice project there is only one configuration manager object!)</strong>
         *
         * <p>
         * It means that if the developer creates several {@link Builder} instances,
         * it will be the last invocation of the build method that matters, the others will be ignored.
         */
        public void build() {
            instance = new Configs(storage, configSources, commandLineArgs);
        }

        /**
         * Creates a new immutable instance of the {@link Configs} manager only if it not be created before.
         */
        public void buildIfNotConfigured() {
            if (instance == null) {
                build();
            }
        }
    }
}
