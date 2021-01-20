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

package io.rxmicro.logger.internal.jul.config;

import io.rxmicro.logger.LoggerConfigSource;
import io.rxmicro.logger.internal.jul.config.provider.ClasspathLoggerConfigProvider;
import io.rxmicro.logger.internal.jul.config.provider.DefaultLoggerConfigProvider;
import io.rxmicro.logger.internal.jul.config.provider.FileLoggerConfigProvider;
import io.rxmicro.logger.internal.jul.config.provider.JavaSystemPropertiesLoggerConfigProvider;
import io.rxmicro.logger.internal.jul.config.provider.SystemVariablesLoggerConfigProvider;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static io.rxmicro.logger.LoggerConfigSource.CLASS_PATH_RESOURCE;
import static io.rxmicro.logger.LoggerConfigSource.DEFAULT;
import static io.rxmicro.logger.LoggerConfigSource.ENVIRONMENT_VARIABLES;
import static io.rxmicro.logger.LoggerConfigSource.FILE_AT_THE_CURRENT_DIR;
import static io.rxmicro.logger.LoggerConfigSource.FILE_AT_THE_HOME_DIR;
import static io.rxmicro.logger.LoggerConfigSource.FILE_AT_THE_RXMICRO_CONFIG_DIR;
import static io.rxmicro.logger.LoggerConfigSource.JAVA_SYSTEM_PROPERTIES;
import static io.rxmicro.logger.LoggerConfigSource.TEST_CLASS_PATH_RESOURCE;
import static io.rxmicro.resource.Paths.createPath;
import static java.util.Map.entry;
import static java.util.stream.Collectors.toList;

/**
 * @author nedis
 * @since 0.7
 */
public final class LoggerConfigBuilder {

    private final List<LoggerConfigProvider> customLoggerConfigProviders;

    public LoggerConfigBuilder(final List<LoggerConfigSource> loggerConfigSources) {
        final Map<LoggerConfigSource, LoggerConfigProvider> loggerConfigProviderMap = getLoggerConfigProviderMap();
        customLoggerConfigProviders = loggerConfigSources.stream().map(loggerConfigProviderMap::get).collect(toList());
    }

    private Map<LoggerConfigSource, LoggerConfigProvider> getLoggerConfigProviderMap() {
        return Map.ofEntries(
                entry(DEFAULT, new DefaultLoggerConfigProvider()),
                entry(CLASS_PATH_RESOURCE, new ClasspathLoggerConfigProvider("jul.properties")),
                entry(TEST_CLASS_PATH_RESOURCE, new ClasspathLoggerConfigProvider("jul.test.properties")),
                entry(FILE_AT_THE_HOME_DIR, new FileLoggerConfigProvider(createPath("~/jul.properties"))),
                entry(FILE_AT_THE_CURRENT_DIR, new FileLoggerConfigProvider(createPath("./jul.properties"))),
                entry(FILE_AT_THE_RXMICRO_CONFIG_DIR, new FileLoggerConfigProvider(createPath("~/.rxmicro/jul.properties"))),
                entry(ENVIRONMENT_VARIABLES, new SystemVariablesLoggerConfigProvider()),
                entry(JAVA_SYSTEM_PROPERTIES, new JavaSystemPropertiesLoggerConfigProvider())
        );
    }

    public Map<String, String> build() {
        final List<Map<String, String>> configs = new ArrayList<>(customLoggerConfigProviders.size());
        for (final LoggerConfigProvider customLoggerConfigProvider : customLoggerConfigProviders) {
            final Map<String, String> customConfiguration = customLoggerConfigProvider.getConfiguration();
            if (!customConfiguration.isEmpty()) {
                configs.add(customConfiguration);
            }
        }
        if (configs.size() == 1) {
            return configs.get(0);
        } else {
            return getMergedConfiguration(configs);
        }
    }

    private Map<String, String> getMergedConfiguration(final List<Map<String, String>> customConfigs) {
        final Map<String, String> result = new LinkedHashMap<>();
        for (final Map<String, String> customConfig : customConfigs) {
            result.putAll(customConfig);
        }
        return result;
    }
}
