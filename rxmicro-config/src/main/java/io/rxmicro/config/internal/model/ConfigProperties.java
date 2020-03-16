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
import io.rxmicro.config.ConfigLoadSource;
import io.rxmicro.logger.Logger;
import io.rxmicro.logger.LoggerFactory;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.function.Supplier;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.common.util.Strings.capitalize;
import static io.rxmicro.config.Config.RX_MICRO_CONFIG_FILE_NAME;
import static io.rxmicro.config.ConfigLoadSource.DEFAULT_CONFIG_VALUES;
import static io.rxmicro.config.ConfigLoadSource.ENVIRONMENT_VARIABLES;
import static io.rxmicro.config.ConfigLoadSource.JAVA_SYSTEM_PROPERTIES;
import static io.rxmicro.config.ConfigLoadSource.RXMICRO_CLASS_PATH_RESOURCE;
import static io.rxmicro.config.ConfigLoadSource.RXMICRO_FILE_AT_THE_CURRENT_DIR;
import static io.rxmicro.config.ConfigLoadSource.RXMICRO_FILE_AT_THE_HOME_DIR;
import static io.rxmicro.config.ConfigLoadSource.SEPARATE_CLASS_PATH_RESOURCE;
import static io.rxmicro.config.ConfigLoadSource.SEPARATE_FILE_AT_THE_CURRENT_DIR;
import static io.rxmicro.config.ConfigLoadSource.SEPARATE_FILE_AT_THE_HOME_DIR;
import static io.rxmicro.config.internal.model.DefaultConfigValueStorage.CONFIGS;
import static io.rxmicro.files.PropertiesResources.loadProperties;

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

    public void discoverProperties(final Set<ConfigLoadSource> configLoadSources) {
        LOGGER.debug("Discovering properties for '?' namespace from sources: ?", nameSpace, configLoadSources);
        for (final ConfigLoadSource configLoadSource : configLoadSources) {
            if (configLoadSource == DEFAULT_CONFIG_VALUES) {
                loadDefaultConfigValues();
            } else if (configLoadSource == SEPARATE_CLASS_PATH_RESOURCE) {
                loadFromClassPathResource(nameSpace, false);
            } else if (configLoadSource == RXMICRO_CLASS_PATH_RESOURCE) {
                loadFromClassPathResource(RX_MICRO_CONFIG_FILE_NAME, true);
            } else if (configLoadSource == ENVIRONMENT_VARIABLES) {
                loadFromEnvironmentVariables();
            } else if (configLoadSource == RXMICRO_FILE_AT_THE_HOME_DIR) {
                loadFromPropertiesFileIfExists(USER_HOME, RX_MICRO_CONFIG_FILE_NAME, true);
            } else if (configLoadSource == RXMICRO_FILE_AT_THE_CURRENT_DIR) {
                loadFromPropertiesFileIfExists("", RX_MICRO_CONFIG_FILE_NAME, true);
            } else if (configLoadSource == SEPARATE_FILE_AT_THE_HOME_DIR) {
                loadFromPropertiesFileIfExists(USER_HOME, nameSpace, false);
            } else if (configLoadSource == SEPARATE_FILE_AT_THE_CURRENT_DIR) {
                loadFromPropertiesFileIfExists("", nameSpace, false);
            } else if (configLoadSource == JAVA_SYSTEM_PROPERTIES) {
                loadFromJavaSystemProperties();
            } else {
                throw new ConfigException("Unsupported load order: " + configLoadSource);
            }
        }
        LOGGER.debug("All properties discovered for '?' namespace", nameSpace);
    }

    private void loadDefaultConfigValues() {
        properties.forEach(p -> p.resolve(CONFIGS, true));
        if (!CONFIGS.isEmpty()) {
            LOGGER.debug("Discovered properties from default config value storage: ?", CONFIGS);
        }
    }

    public void setProperties() {
        properties.forEach(ConfigProperty::setProperty);
    }

    private void loadFromEnvironmentVariables() {
        final Set<Map.Entry<String, String>> resolvedEntries = new LinkedHashSet<>();
        properties.forEach(p -> p.resolve(SYSTEM_ENV, true).ifPresent(resolvedEntries::add));
        if (!resolvedEntries.isEmpty()) {
            LOGGER.debug("Discovered properties from environment variables: ?", resolvedEntries);
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
}
