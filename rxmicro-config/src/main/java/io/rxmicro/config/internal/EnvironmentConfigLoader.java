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

package io.rxmicro.config.internal;

import io.rxmicro.config.Config;
import io.rxmicro.config.ConfigSource;
import io.rxmicro.config.internal.component.ConfigPropertiesBuilder;
import io.rxmicro.config.internal.model.ConfigProperties;

import java.util.Map;
import java.util.Set;

import static io.rxmicro.runtime.local.Instances.instantiate;

/**
 * @author nedis
 * @since 0.1
 */
public final class EnvironmentConfigLoader {

    private final ConfigPropertiesBuilder configPropertiesBuilder = new ConfigPropertiesBuilder();

    private final Set<ConfigSource> configSources;

    public EnvironmentConfigLoader(final Set<ConfigSource> configSources) {
        this.configSources = configSources;
    }

    public Config getEnvironmentConfig(final String namespace,
                                       final Class<? extends Config> configClass,
                                       final Map<String, String> commandLineArgs) {
        final Config config = instantiate(configClass);
        if (!configSources.isEmpty()) {
            resolveEnvironmentVariables(namespace, config, commandLineArgs);
        }
        return config;
    }

    private void resolveEnvironmentVariables(final String namespace,
                                             final Config config,
                                             final Map<String, String> commandLineArgs) {
        final ConfigProperties configProperties = configPropertiesBuilder.build(namespace, config);
        configProperties.discoverProperties(configSources, commandLineArgs);
        configProperties.setProperties();
    }
}
