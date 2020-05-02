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

package io.rxmicro.logger.internal.jul;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static io.rxmicro.files.PropertiesResources.loadProperties;
import static io.rxmicro.logger.Constants.LOGGER_VARIABLE_PREFIX;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
final class ConfigCustomizer {

    private static final int LOGGER_VARIABLE_PREFIX_INDEX = LOGGER_VARIABLE_PREFIX.length();

    private static final String JAVA_LOGGING_TEST_PROPERTIES = "jul.test.properties";

    private static final String JAVA_LOGGING_PROPERTIES = "jul.properties";

    Optional<String> customizeConfig(final Map<String, String> config) {
        Optional<Map<String, String>> propertiesOptional = getFromSystemPropertiesConfiguration();
        if (propertiesOptional.isPresent()) {
            customize(config, propertiesOptional.get());
            return Optional.of("Java System Properties");
        }
        propertiesOptional = getFromSystemVariablesConfiguration();
        if (propertiesOptional.isPresent()) {
            customize(config, propertiesOptional.get());
            return Optional.of("System Environment Variables");
        }
        for (final String property : List.of(JAVA_LOGGING_TEST_PROPERTIES, JAVA_LOGGING_PROPERTIES)) {
            propertiesOptional = loadProperties(property);
            if (propertiesOptional.isPresent()) {
                customize(config, propertiesOptional.get());
                return Optional.of("classpath:/" + property);
            }
        }
        return Optional.empty();
    }

    private void customize(final Map<String, String> dest,
                           final Map<String, String> src) {
        for (final Map.Entry<String, String> entry : src.entrySet()) {
            dest.put(entry.getKey().trim(), LevelMappings.fixLevelValue(entry.getValue().trim()));
        }
    }

    private Optional<Map<String, String>> getFromSystemVariablesConfiguration() {
        final Map<String, String> properties = new LinkedHashMap<>();
        for (final Map.Entry<String, String> entry : System.getenv().entrySet()) {
            if (entry.getKey().startsWith(LOGGER_VARIABLE_PREFIX)) {
                properties.put(entry.getKey().substring(LOGGER_VARIABLE_PREFIX_INDEX), entry.getValue());
            }
        }
        return properties.isEmpty() ? Optional.empty() : Optional.of(properties);
    }

    private Optional<Map<String, String>> getFromSystemPropertiesConfiguration() {
        final Map<String, String> properties = new LinkedHashMap<>();
        for (final Map.Entry<Object, Object> entry : System.getProperties().entrySet()) {
            if (((String) entry.getKey()).startsWith(LOGGER_VARIABLE_PREFIX)) {
                properties.put(((String) entry.getKey()).substring(LOGGER_VARIABLE_PREFIX_INDEX), (String) entry.getValue());
            }
        }
        return properties.isEmpty() ? Optional.empty() : Optional.of(properties);
    }
}
