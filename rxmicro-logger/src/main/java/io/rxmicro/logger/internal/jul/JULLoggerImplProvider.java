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

import io.rxmicro.common.ImpossibleException;
import io.rxmicro.logger.Logger;
import io.rxmicro.logger.impl.LoggerImplProvider;
import io.rxmicro.logger.internal.jul.config.SystemOutConsoleHandler;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.LogManager;

/**
 * @author nedis
 * @since 0.1
 */
public final class JULLoggerImplProvider implements LoggerImplProvider {

    private static final String DEFAULT_LOGGER_ROOT_LEVEL = "INFO";

    private final ConfigCustomizer configCustomizer = new ConfigCustomizer();

    @Override
    public void setup() {
        final Map<String, String> config = getDefaultConfiguration();
        final byte[] configBytes = toConfigBytes(config);
        final LogManager logManager = LogManager.getLogManager();
        try {
            logManager.readConfiguration(new ByteArrayInputStream(configBytes));
        } catch (final IOException e) {
            throw new ImpossibleException(e, "Configuration created automatically, so IO error is impossible!");
        }
        final Optional<String> customConfig = configCustomizer.customizeConfig(config);
        if (customConfig.isPresent()) {
            java.util.logging.Logger.getGlobal().log(Level.INFO,
                    "Using java.util.logging with custom config: " + customConfig.get());
        } else {
            java.util.logging.Logger.getGlobal().log(Level.INFO,
                    "Using java.util.logging with default config");
        }
    }

    @Override
    public Logger getLogger(final String name) {
        return new JULLogger(name);
    }

    // Simplest version without Unicode and special characters support
    // @see java.util.Properties.store
    private byte[] toConfigBytes(final Map<String, String> config) {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (BufferedWriter writer =
                     new BufferedWriter(new OutputStreamWriter(byteArrayOutputStream, "8859_1"))) {
            for (final Map.Entry<String, String> entry : config.entrySet()) {
                writer.write(entry.getKey());
                writer.write('=');
                writer.write(entry.getValue());
                writer.newLine();
            }
        } catch (final IOException e) {
            throw new ImpossibleException(e, "Writer uses byte array, so IO exception is impossible!");
        }
        return byteArrayOutputStream.toByteArray();
    }

    private Map<String, String> getDefaultConfiguration() {
        final String handler = SystemOutConsoleHandler.class.getName();
        final Map<String, String> properties = new LinkedHashMap<>();
        properties.put("handlers", handler);
        properties.put(".level", DEFAULT_LOGGER_ROOT_LEVEL);
        properties.put(handler + ".level", "ALL");
        return properties;
    }
}
