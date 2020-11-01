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

import io.rxmicro.logger.internal.jul.config.provider.ClasspathLoggerConfigProvider;
import io.rxmicro.logger.internal.jul.config.provider.DefaultLoggerConfigProvider;
import io.rxmicro.logger.internal.jul.config.provider.JavaSystemPropertiesLoggerConfigProvider;
import io.rxmicro.logger.internal.jul.config.provider.SystemVariablesLoggerConfigProvider;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author nedis
 * @since 0.7
 */
public final class LoggerConfigBuilder {

    private static final String JAVA_LOGGING_TEST_PROPERTIES = "jul.test.properties";

    private static final String JAVA_LOGGING_PROPERTIES = "jul.properties";

    // From lowest to highest priority
    private final LoggerConfigProvider[] customLoggerConfigProviders = {
            new DefaultLoggerConfigProvider(),
            new ClasspathLoggerConfigProvider(JAVA_LOGGING_PROPERTIES),
            new ClasspathLoggerConfigProvider(JAVA_LOGGING_TEST_PROPERTIES),
            new SystemVariablesLoggerConfigProvider(),
            new JavaSystemPropertiesLoggerConfigProvider()
    };

    public Map<String, String> build() {
        final List<Map<String, String>> configs = new ArrayList<>(customLoggerConfigProviders.length);
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
