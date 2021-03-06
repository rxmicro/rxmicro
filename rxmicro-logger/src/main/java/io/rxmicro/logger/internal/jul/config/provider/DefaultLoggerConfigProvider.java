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
import io.rxmicro.logger.jul.SystemConsoleHandler;

import java.util.LinkedHashMap;
import java.util.Map;

import static io.rxmicro.common.util.ExCollections.unmodifiableOrderedMap;

/**
 * @author nedis
 * @since 0.7
 */
public final class DefaultLoggerConfigProvider implements LoggerConfigProvider {

    private static final String DEFAULT_LOGGER_ROOT_LEVEL = "INFO";

    @Override
    public Map<String, String> getConfiguration() {
        final String handler = SystemConsoleHandler.class.getName();
        final Map<String, String> properties = new LinkedHashMap<>();
        properties.put(".level", DEFAULT_LOGGER_ROOT_LEVEL);
        properties.put("handlers", handler);
        properties.put(handler + ".level", "ALL");
        return unmodifiableOrderedMap(properties);
    }
}
