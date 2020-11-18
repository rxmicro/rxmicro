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

package io.rxmicro.logger.internal.jul.config.provider;

import io.rxmicro.logger.internal.jul.config.LoggerConfigProvider;

import java.util.LinkedHashMap;
import java.util.Map;

import static io.rxmicro.common.util.ExCollections.unmodifiableOrderedMap;
import static io.rxmicro.logger.LoggerConstants.LOGGER_VARIABLE_PREFIX;

/**
 * @author nedis
 * @since 0.7
 */
public final class JavaSystemPropertiesLoggerConfigProvider implements LoggerConfigProvider {

    private static final int LOGGER_VARIABLE_PREFIX_INDEX = LOGGER_VARIABLE_PREFIX.length();

    @Override
    public Map<String, String> getConfiguration() {
        final Map<String, String> properties = new LinkedHashMap<>();
        for (final Map.Entry<Object, Object> entry : System.getProperties().entrySet()) {
            final String key = entry.getKey().toString();
            if (key.startsWith(LOGGER_VARIABLE_PREFIX)) {
                properties.put(key.substring(LOGGER_VARIABLE_PREFIX_INDEX), entry.getValue().toString());
            }
        }
        return unmodifiableOrderedMap(properties);
    }
}
