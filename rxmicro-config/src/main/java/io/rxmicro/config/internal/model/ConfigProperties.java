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

package io.rxmicro.config.internal.model;

import io.rxmicro.config.ConfigException;
import io.rxmicro.config.ConfigSource;
import io.rxmicro.logger.Logger;
import io.rxmicro.logger.LoggerFactory;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.common.util.Strings.capitalize;
import static io.rxmicro.config.Config.RX_MICRO_CONFIG_DIRECTORY_NAME;
import static io.rxmicro.config.Config.RX_MICRO_CONFIG_FILE_NAME;
import static io.rxmicro.config.ConfigSource.DEFAULT_CONFIG_VALUES;
import static io.rxmicro.config.ConfigSource.ENVIRONMENT_VARIABLES;
import static io.rxmicro.config.ConfigSource.JAVA_SYSTEM_PROPERTIES;
import static io.rxmicro.config.ConfigSource.RXMICRO_CLASS_PATH_RESOURCE;
import static io.rxmicro.config.ConfigSource.RXMICRO_FILE_AT_THE_CURRENT_DIR;
import static io.rxmicro.config.ConfigSource.RXMICRO_FILE_AT_THE_HOME_DIR;
import static io.rxmicro.config.ConfigSource.RXMICRO_FILE_AT_THE_RXMICRO_CONFIG_DIR;
import static io.rxmicro.config.ConfigSource.SEPARATE_CLASS_PATH_RESOURCE;
import static io.rxmicro.config.ConfigSource.SEPARATE_FILE_AT_THE_CURRENT_DIR;
import static io.rxmicro.config.ConfigSource.SEPARATE_FILE_AT_THE_HOME_DIR;
import static io.rxmicro.config.ConfigSource.SEPARATE_FILE_AT_THE_RXMICRO_CONFIG_DIR;
import static io.rxmicro.config.internal.ExternalSourceProviderFactory.getCurrentDir;
import static io.rxmicro.config.internal.ExternalSourceProviderFactory.getEnvironmentVariables;
import static io.rxmicro.config.internal.model.AbstractDefaultConfigValueBuilder.getCurrentDefaultConfigValueStorage;
import static io.rxmicro.config.internal.model.PropertyNames.USER_HOME_PROPERTY;
import static io.rxmicro.files.PropertiesResources.loadProperties;
import static java.util.Map.entry;
import static java.util.stream.Collectors.toList;

/**
 * @author nedis
 * @since 0.1
 */
public final class ConfigProperties {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigProperties.class);

    private static final Map<String, String> SYSTEM_ENV = getEnvironmentVariables();

    private static final Properties SYSTEM_PROPERTIES = System.getProperties();

    private static final String USER_HOME = SYSTEM_PROPERTIES.getProperty(USER_HOME_PROPERTY);

    private static final String RX_MICRO_CONFIG_DIRECTORY = USER_HOME + "/" + RX_MICRO_CONFIG_DIRECTORY_NAME;

    private static final String CURRENT_DIR = getCurrentDir();

    private static final Map<String, Optional<Map<String, String>>> RESOURCE_CACHE = new WeakHashMap<>();

    private final String namespace;

    private final Collection<ConfigProperty> properties;

    public ConfigProperties(final String namespace,
                            final Collection<ConfigProperty> properties) {
        this.namespace = namespace;
        this.properties = properties;
    }

    public void discoverProperties(final Set<ConfigSource> configSources,
                                   final Map<String, String> commandLineArgs) {
        final DebugMessageBuilder debugMessageBuilder = new DebugMessageBuilder(namespace, configSources, commandLineArgs);
        discoverProperties(configSources, commandLineArgs, debugMessageBuilder);
        LOGGER.debug(debugMessageBuilder.toString());
    }

    private void discoverProperties(final Set<ConfigSource> configSources,
                                    final Map<String, String> commandLineArgs,
                                    final DebugMessageBuilder debugMessageBuilder) {
        for (final ConfigSource configSource : configSources) {
            if (configSource == DEFAULT_CONFIG_VALUES) {
                loadDefaultConfigValues(debugMessageBuilder);
            } else if (configSource == SEPARATE_CLASS_PATH_RESOURCE) {
                loadFromClassPathResource(namespace, false, debugMessageBuilder);
            } else if (configSource == RXMICRO_CLASS_PATH_RESOURCE) {
                loadFromClassPathResource(RX_MICRO_CONFIG_FILE_NAME, true, debugMessageBuilder);
            } else if (configSource == ENVIRONMENT_VARIABLES) {
                loadFromEnvironmentVariables(debugMessageBuilder);
            } else if (configSource == RXMICRO_FILE_AT_THE_HOME_DIR) {
                loadFromPropertiesFileIfExists(USER_HOME, RX_MICRO_CONFIG_FILE_NAME, true, debugMessageBuilder);
            } else if (configSource == RXMICRO_FILE_AT_THE_RXMICRO_CONFIG_DIR) {
                loadFromPropertiesFileIfExists(RX_MICRO_CONFIG_DIRECTORY, RX_MICRO_CONFIG_FILE_NAME, true, debugMessageBuilder);
            } else if (configSource == RXMICRO_FILE_AT_THE_CURRENT_DIR) {
                loadFromPropertiesFileIfExists(CURRENT_DIR, RX_MICRO_CONFIG_FILE_NAME, true, debugMessageBuilder);
            } else if (configSource == SEPARATE_FILE_AT_THE_HOME_DIR) {
                loadFromPropertiesFileIfExists(USER_HOME, namespace, false, debugMessageBuilder);
            } else if (configSource == SEPARATE_FILE_AT_THE_RXMICRO_CONFIG_DIR) {
                loadFromPropertiesFileIfExists(RX_MICRO_CONFIG_DIRECTORY, namespace, false, debugMessageBuilder);
            } else if (configSource == SEPARATE_FILE_AT_THE_CURRENT_DIR) {
                loadFromPropertiesFileIfExists(CURRENT_DIR, namespace, false, debugMessageBuilder);
            } else if (configSource == JAVA_SYSTEM_PROPERTIES) {
                loadFromJavaSystemProperties(debugMessageBuilder);
            } else {
                throw new ConfigException("Unsupported load order: " + configSource);
            }
        }
        if (!commandLineArgs.isEmpty()) {
            loadFromCommandLineArguments(commandLineArgs, debugMessageBuilder);
        }
    }

    private void loadDefaultConfigValues(final DebugMessageBuilder debugMessageBuilder) {
        final Set<Map.Entry<String, String>> resolvedEntries = new LinkedHashSet<>();
        final DefaultConfigValueStorage storage = getCurrentDefaultConfigValueStorage();
        final String messageTemplate = "Discovered properties from default config storage: ?";
        if (storage.hasDefaultStringValuesStorage()) {
            properties.forEach(p -> p.resolve(storage.getDefaultStringValuesStorage(), true).ifPresent(resolvedEntries::add));
            debugMessageBuilder.append(messageTemplate, storage.getDefaultStringValuesStorage());
        }
        if (storage.hasDefaultSupplierValuesStorage()) {
            properties.forEach(p -> p.resolve(storage.getDefaultSupplierValuesStorage(), true)
                    .ifPresent(e -> resolvedEntries.add(entry(e.getKey(), e.getValue().toString()))));
            debugMessageBuilder.append(messageTemplate, storage.getDefaultSupplierValuesStorage());
        }
        if (!resolvedEntries.isEmpty()) {
            debugMessageBuilder.addResolvedEntries(resolvedEntries);
            debugMessageBuilder.append(messageTemplate, resolvedEntries);
        }
    }

    public void setProperties() {
        properties.forEach(ConfigProperty::setProperty);
    }

    private void loadFromEnvironmentVariables(final DebugMessageBuilder debugMessageBuilder) {
        loadFromMap(SYSTEM_ENV, "environment variables", debugMessageBuilder);
    }

    private void loadFromMap(final Map<String, String> map,
                             final String sourceName,
                             final DebugMessageBuilder debugMessageBuilder) {
        final Set<Map.Entry<String, String>> resolvedEntries = new LinkedHashSet<>();
        properties.forEach(p -> p.resolve(map, true).ifPresent(resolvedEntries::add));
        if (!resolvedEntries.isEmpty()) {
            debugMessageBuilder.addResolvedEntries(resolvedEntries);
            debugMessageBuilder.append("Discovered properties from ?: ?", sourceName, resolvedEntries);
        }
    }

    private void loadFromClassPathResource(final String name,
                                           final boolean useFullName,
                                           final DebugMessageBuilder debugMessageBuilder) {
        final String fullClassPathFileName = name + ".properties";
        final Supplier<Optional<Map<String, String>>> propertiesSupplier;
        if (useFullName) {
            propertiesSupplier = () -> RESOURCE_CACHE.computeIfAbsent(name, n -> loadProperties(fullClassPathFileName));
        } else {
            propertiesSupplier = () -> loadProperties(fullClassPathFileName);
        }
        loadResource(propertiesSupplier, "classpath resource", fullClassPathFileName, useFullName, debugMessageBuilder);
    }

    private void loadFromPropertiesFileIfExists(final String path,
                                                final String fileName,
                                                final boolean useFullName,
                                                final DebugMessageBuilder debugMessageBuilder) {
        final Path fullFilePath = Paths.get(format("?/?.properties", path, fileName)).toAbsolutePath();
        final String fullFilePathName = fullFilePath.toString();
        final Supplier<Optional<Map<String, String>>> propertiesSupplier;
        if (useFullName) {
            propertiesSupplier = () -> RESOURCE_CACHE.computeIfAbsent(fullFilePathName, n -> loadProperties(fullFilePath));
        } else {
            propertiesSupplier = () -> loadProperties(fullFilePath);
        }
        loadResource(propertiesSupplier, "config file", fullFilePathName, useFullName, debugMessageBuilder);
    }

    private void loadResource(final Supplier<Optional<Map<String, String>>> propertiesSupplier,
                              final String resourceType,
                              final String resourceName,
                              final boolean useFullName,
                              final DebugMessageBuilder debugMessageBuilder) {
        final Optional<Map<String, String>> resourceOptional = propertiesSupplier.get();
        if (resourceOptional.isPresent()) {
            final Set<Map.Entry<String, String>> resolvedEntries = new LinkedHashSet<>();
            properties.forEach(p -> p.resolve(resourceOptional.get(), useFullName).ifPresent(resolvedEntries::add));
            if (!resolvedEntries.isEmpty()) {
                debugMessageBuilder.addResolvedEntries(resolvedEntries);
                debugMessageBuilder.append("Discovered properties from '?' ?: ?", resourceName, resourceType, resolvedEntries);
            }
        } else {
            debugMessageBuilder.append("? not found: ?", capitalize(resourceType), resourceName);
        }
    }

    private void loadFromJavaSystemProperties(final DebugMessageBuilder debugMessageBuilder) {
        final Set<Map.Entry<String, String>> resolvedEntries = new LinkedHashSet<>();
        properties.forEach(p -> p.resolve(SYSTEM_PROPERTIES, true).ifPresent(resolvedEntries::add));
        if (!resolvedEntries.isEmpty()) {
            debugMessageBuilder.addResolvedEntries(resolvedEntries);
            debugMessageBuilder.append("Discovered properties from Java system properties: ?", resolvedEntries);
        }
    }

    private void loadFromCommandLineArguments(final Map<String, String> commandLineArgs,
                                              final DebugMessageBuilder debugMessageBuilder) {
        loadFromMap(commandLineArgs, "command line arguments", debugMessageBuilder);
    }

    /**
     * @author nedis
     * @since 0.3
     */
    private static final class DebugMessageBuilder {

        private static final String SHIFT = "  ";

        private static final Object INSTANCE = null;

        private final boolean debugEnabled;

        private final String namespace;

        private final List<String> messages;

        private final Map<Map.Entry<String, String>, Object> resolvedEntries;

        private final Set<ConfigSource> configSources;

        private final Map<String, String> commandLineArgs;

        private int count;

        private DebugMessageBuilder(final String namespace,
                                    final Set<ConfigSource> configSources,
                                    final Map<String, String> commandLineArgs) {
            this.configSources = configSources;
            this.commandLineArgs = commandLineArgs;
            this.debugEnabled = LOGGER.isDebugEnabled();
            this.namespace = namespace;
            this.messages = debugEnabled ? new ArrayList<>() : List.of();
            this.resolvedEntries = debugEnabled ? new LinkedHashMap<>() : Map.of();
        }

        private void append(final String message,
                            final Object arg1) {
            if (debugEnabled) {
                messages.add(SHIFT + SHIFT + format(message, arg1));
            }
        }

        private void append(final String message,
                            final Object arg1,
                            final Object arg2) {
            if (debugEnabled) {
                messages.add(SHIFT + SHIFT + format(message, arg1, arg2));
            }
        }

        private void append(final String message,
                            final Object arg1,
                            final Object arg2,
                            final Object arg3) {
            if (debugEnabled) {
                messages.add(SHIFT + SHIFT + format(message, arg1, arg2, arg3));
            }
        }

        private void addResolvedEntries(final Set<Map.Entry<String, String>> resolvedEntries) {
            if (debugEnabled) {
                count += resolvedEntries.size();
                resolvedEntries.forEach(e -> this.resolvedEntries.put(e, INSTANCE));
            }
        }

        @Override
        public String toString() {
            if (debugEnabled) {
                messages.add(0, format("Discovering properties for '?' namespace:", namespace));
                messages.add(1, format("?Config source: ?", SHIFT, new ConfigSourceProvider(configSources, commandLineArgs)));
                if (count == 0) {
                    messages.add(format("?No properties found for '?' namespace. Using default config instance!", SHIFT, namespace));
                } else {
                    messages.add(format("?Property(ies) discovered for '?' namespace:", SHIFT, namespace));
                    messages.add(format("??Count: ?, Entries: ?", SHIFT, SHIFT, count, resolvedEntries.keySet()));
                }
                return String.join(System.lineSeparator(), messages);
            } else {
                return "";
            }
        }
    }

    /**
     * @author nedis
     * @since 0.3
     */
    private static final class ConfigSourceProvider {

        private final Set<ConfigSource> configSources;

        private final Map<String, String> commandLineArgs;

        private ConfigSourceProvider(final Set<ConfigSource> configSources,
                                     final Map<String, String> commandLineArgs) {
            this.configSources = configSources;
            this.commandLineArgs = commandLineArgs;
        }

        @Override
        public String toString() {
            if (commandLineArgs.isEmpty()) {
                return commandLineArgs.toString();
            } else {
                return Stream.concat(
                        configSources.stream().map(Objects::toString),
                        Stream.of("COMMAND_LINE_ARGUMENTS")
                ).collect(toList()).toString();
            }
        }
    }
}
