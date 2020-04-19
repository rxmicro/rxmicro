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

package io.rxmicro.config.internal.model;

import io.rxmicro.config.ConfigException;
import io.rxmicro.config.ConfigSource;
import io.rxmicro.logger.Logger;
import io.rxmicro.logger.LoggerFactory;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.function.Supplier;

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
import static io.rxmicro.config.internal.model.DefaultConfigValueStorage.DEFAULT_STRING_VALUES_STORAGE;
import static io.rxmicro.config.internal.model.DefaultConfigValueStorage.DEFAULT_SUPPLIER_VALUES_STORAGE;
import static io.rxmicro.files.PropertiesResources.loadProperties;
import static java.util.Map.entry;
import static java.util.stream.Collectors.toMap;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class ConfigProperties {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigProperties.class);

    private static final Map<String, String> SYSTEM_ENV = System.getenv();

    private static final Properties SYSTEM_PROPERTIES = System.getProperties();

    private static final String USER_HOME = SYSTEM_PROPERTIES.getProperty("user.home") + "/";

    private static final Map<String, Optional<Map<String, String>>> RESOURCE_CACHE = new HashMap<>();

    private final String nameSpace;

    private final Collection<ConfigProperty> properties;

    public ConfigProperties(final String nameSpace,
                            final Collection<ConfigProperty> properties) {
        this.nameSpace = nameSpace;
        this.properties = properties;
    }

    public void discoverProperties(final Set<ConfigSource> configSources,
                                   final List<String> commandLineArgs) {
        LOGGER.debug("Discovering properties for '?' namespace from sources: ?", nameSpace, configSources);
        for (final ConfigSource configSource : configSources) {
            if (configSource == DEFAULT_CONFIG_VALUES) {
                loadDefaultConfigValues();
            } else if (configSource == SEPARATE_CLASS_PATH_RESOURCE) {
                loadFromClassPathResource(nameSpace, false);
            } else if (configSource == RXMICRO_CLASS_PATH_RESOURCE) {
                loadFromClassPathResource(RX_MICRO_CONFIG_FILE_NAME, true);
            } else if (configSource == ENVIRONMENT_VARIABLES) {
                loadFromEnvironmentVariables();
            } else if (configSource == RXMICRO_FILE_AT_THE_HOME_DIR) {
                loadFromPropertiesFileIfExists(USER_HOME, RX_MICRO_CONFIG_FILE_NAME, true);
            } else if (configSource == RXMICRO_FILE_AT_THE_RXMICRO_CONFIG_DIR) {
                loadFromPropertiesFileIfExists(USER_HOME + RX_MICRO_CONFIG_DIRECTORY_NAME, RX_MICRO_CONFIG_FILE_NAME, true);
            } else if (configSource == RXMICRO_FILE_AT_THE_CURRENT_DIR) {
                loadFromPropertiesFileIfExists("", RX_MICRO_CONFIG_FILE_NAME, true);
            } else if (configSource == SEPARATE_FILE_AT_THE_HOME_DIR) {
                loadFromPropertiesFileIfExists(USER_HOME, nameSpace, false);
            } else if (configSource == SEPARATE_FILE_AT_THE_RXMICRO_CONFIG_DIR) {
                loadFromPropertiesFileIfExists(USER_HOME + RX_MICRO_CONFIG_DIRECTORY_NAME, nameSpace, false);
            } else if (configSource == SEPARATE_FILE_AT_THE_CURRENT_DIR) {
                loadFromPropertiesFileIfExists("", nameSpace, false);
            } else if (configSource == JAVA_SYSTEM_PROPERTIES) {
                loadFromJavaSystemProperties();
            } else {
                throw new ConfigException("Unsupported load order: " + configSource);
            }
        }
        if (!commandLineArgs.isEmpty()) {
            loadFromCommandLineArguments(commandLineArgs);
        }
        LOGGER.debug("All properties discovered for '?' namespace", nameSpace);
    }

    private void loadDefaultConfigValues() {
        if (!DEFAULT_STRING_VALUES_STORAGE.isEmpty()) {
            properties.forEach(p -> p.resolve(DEFAULT_STRING_VALUES_STORAGE, true));
            LOGGER.debug("Discovered properties from default config value storage: ?", DEFAULT_STRING_VALUES_STORAGE);
        }
        if (!DEFAULT_SUPPLIER_VALUES_STORAGE.isEmpty()) {
            properties.forEach(p -> p.resolve(DEFAULT_SUPPLIER_VALUES_STORAGE, true));
            LOGGER.debug("Discovered properties from default config value storage: ?", DEFAULT_SUPPLIER_VALUES_STORAGE);
        }
    }

    public void setProperties() {
        properties.forEach(ConfigProperty::setProperty);
    }

    private void loadFromEnvironmentVariables() {
        loadFromMap(SYSTEM_ENV, "environment variables");
    }

    private void loadFromMap(final Map<String, String> map,
                             final String sourceName) {
        final Set<Map.Entry<String, String>> resolvedEntries = new LinkedHashSet<>();
        properties.forEach(p -> p.resolve(map, true).ifPresent(resolvedEntries::add));
        if (!resolvedEntries.isEmpty()) {
            LOGGER.debug("Discovered properties from ?: ?", sourceName, resolvedEntries);
        }
    }

    private void loadFromClassPathResource(final String name,
                                           final boolean useFullName) {
        final String fullClassPathFileName = name + ".properties";
        loadResource(
                useFullName ?
                        () -> RESOURCE_CACHE.computeIfAbsent(name, n -> loadProperties(fullClassPathFileName)) :
                        () -> loadProperties(fullClassPathFileName),
                "classpath resource",
                fullClassPathFileName,
                useFullName
        );
    }

    private void loadFromPropertiesFileIfExists(final String path,
                                                final String fileName,
                                                final boolean useFullName) {
        final Path fullFilePath = Paths.get(format("??.properties", path, fileName)).toAbsolutePath();
        final String fullFilePathName = fullFilePath.toString();
        loadResource(
                useFullName ?
                        () -> RESOURCE_CACHE.computeIfAbsent(fullFilePathName, n -> loadProperties(fullFilePath)) :
                        () -> loadProperties(fullFilePath),
                "config file",
                fullFilePathName,
                useFullName
        );
    }

    private void loadResource(final Supplier<Optional<Map<String, String>>> propertiesSupplier,
                              final String resourceType,
                              final String resourceName,
                              final boolean useFullName) {
        final Optional<Map<String, String>> resourceOptional = propertiesSupplier.get();
        if (resourceOptional.isPresent()) {
            final Set<Map.Entry<String, String>> resolvedEntries = new LinkedHashSet<>();
            properties.forEach(p -> p.resolve(resourceOptional.get(), useFullName).ifPresent(resolvedEntries::add));
            if (!resolvedEntries.isEmpty()) {
                LOGGER.debug("Discovered properties from '?' ?: ?", resourceName, resourceType, resolvedEntries);
            }
        } else {
            LOGGER.debug("? not found: ?", capitalize(resourceType), resourceName);
        }
    }

    private void loadFromJavaSystemProperties() {
        final Set<Map.Entry<String, String>> resolvedEntries = new LinkedHashSet<>();
        properties.forEach(p -> p.resolve(SYSTEM_PROPERTIES, true).ifPresent(resolvedEntries::add));
        if (!resolvedEntries.isEmpty()) {
            LOGGER.debug("Discovered properties from Java system properties: ?", resolvedEntries);
        }
    }

    private void loadFromCommandLineArguments(final List<String> commandLineArgs) {
        final Map<String, String> sourceMap = commandLineArgs.stream().map(cmd -> {
            final String[] data = cmd.split("=");
            if (data.length != 2) {
                throw new ConfigException("Invalid command line arguments. " +
                        "Expected: 'name_space.propertyName=propertyValue', but actual is '?'", cmd);
            }
            return entry(data[0], data[1]);
        }).collect(toMap(Map.Entry::getKey, Map.Entry::getValue));
        loadFromMap(sourceMap, "command line arguments");
    }
}
