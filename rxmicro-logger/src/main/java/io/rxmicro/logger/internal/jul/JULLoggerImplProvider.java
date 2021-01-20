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

import io.rxmicro.logger.Logger;
import io.rxmicro.logger.LoggerEventBuilder;
import io.rxmicro.logger.impl.LoggerImplProvider;
import io.rxmicro.logger.internal.jul.config.LoggerConfigBuilder;
import io.rxmicro.logger.internal.jul.config.adapter.RxMicroLogRecord;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.stream.Stream;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.logger.LoggerConstants.CONFIGURATION_PROPERTIES_HIDE;
import static io.rxmicro.logger.internal.jul.InternalLoggerHelper.logInternal;
import static io.rxmicro.logger.internal.jul.LevelMappings.fixLevelValue;
import static java.lang.System.lineSeparator;
import static java.util.function.Function.identity;
import static java.util.logging.Level.SEVERE;
import static java.util.stream.Collectors.joining;

/**
 * @author nedis
 * @since 0.1
 */
public final class JULLoggerImplProvider implements LoggerImplProvider {

    private final Map<String, Logger> loggerCache = new ConcurrentHashMap<>();

    static {
        // Workaround for https://bugs.openjdk.java.net/browse/JDK-6448699
        try {
            Class.forName("io.rxmicro.logger.jul.SystemConsoleHandler", true, ClassLoader.getSystemClassLoader());
        } catch (final LinkageError | ClassNotFoundException | SecurityException throwable) {
            logInternal(SEVERE, throwable, "Can't register io.rxmicro.logger.jul.SystemConsoleHandler: ?", throwable.getMessage());
        }
    }

    @Override
    public void setup() throws IOException {
        final Map<String, String> config = new LoggerConfigBuilder().build();
        final byte[] configBytes = toConfigBytes(config);
        LogManager.getLogManager().readConfiguration(new ByteArrayInputStream(configBytes));

        if (!Boolean.parseBoolean(config.get(CONFIGURATION_PROPERTIES_HIDE))) {
            logInternal(
                    Level.INFO,
                    Stream.of(
                            Stream.of("Using java.util.logging with the following config:", ""),
                            config.entrySet().stream().map(e -> format("\t?=?", e.getKey(), e.getValue())),
                            Stream.of("")
                    ).flatMap(identity()).collect(joining(lineSeparator()))
            );
        }
    }

    @Override
    public Logger getLogger(final String name) {
        return loggerCache.computeIfAbsent(name, JULLogger::new);
    }

    @Override
    public LoggerEventBuilder newLoggerEventBuilder() {
        return new RxMicroLogRecord.Builder();
    }

    // Simplest version without Unicode and special characters support
    // @see java.util.Properties.store
    private byte[] toConfigBytes(final Map<String, String> config) throws IOException {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (BufferedWriter writer =
                     new BufferedWriter(new OutputStreamWriter(byteArrayOutputStream, "8859_1"))) {
            for (final Map.Entry<String, String> entry : config.entrySet()) {
                writer.write(entry.getKey());
                writer.write('=');
                writer.write(fixLevelValue(entry.getValue()));
                writer.newLine();
            }
        }
        return byteArrayOutputStream.toByteArray();
    }
}
