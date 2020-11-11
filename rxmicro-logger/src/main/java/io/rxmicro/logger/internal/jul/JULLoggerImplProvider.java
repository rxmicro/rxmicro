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
import io.rxmicro.logger.Constants;
import io.rxmicro.logger.Logger;
import io.rxmicro.logger.impl.LoggerImplProvider;
import io.rxmicro.logger.internal.jul.config.LoggerConfigBuilder;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.stream.Stream;

import static io.rxmicro.common.local.DeniedPackages.isDeniedPackage;
import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.logger.Constants.CONFIGURATION_LOGGER_CLASS_NAME_TRIM_MODE;
import static io.rxmicro.logger.Constants.CONFIGURATION_PROPERTIES_HIDE;
import static io.rxmicro.logger.internal.jul.LevelMappings.fixLevelValue;
import static java.lang.System.lineSeparator;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.joining;

/**
 * @author nedis
 * @since 0.1
 */
public final class JULLoggerImplProvider implements LoggerImplProvider {

    private final LoggerConfigBuilder loggerConfigBuilder = new LoggerConfigBuilder();

    private Constants.TrimMode trimMode;

    @Override
    public void setup() {
        final Map<String, String> config = loggerConfigBuilder.build();
        trimMode = getTrimMode(config);
        try {
            final byte[] configBytes = toConfigBytes(config);
            LogManager.getLogManager()
                    .readConfiguration(new ByteArrayInputStream(configBytes));
        } catch (final IOException ex) {
            throw new ImpossibleException(ex, "Configuration created automatically, so IO error is impossible!");
        }
        if (!Boolean.parseBoolean(config.get(CONFIGURATION_PROPERTIES_HIDE))) {
            java.util.logging.Logger.getGlobal().log(
                    Level.INFO,
                    Stream.of(
                            Stream.of("Using java.util.logging with the following config:", ""),
                            config.entrySet().stream().map(e -> format("\t?=?", e.getKey(), e.getValue())),
                            Stream.of("")
                    ).flatMap(identity()).collect(joining(lineSeparator()))
            );
        }
    }

    private Constants.TrimMode getTrimMode(final Map<String, String> config) {
        return Optional.ofNullable(config.get(CONFIGURATION_LOGGER_CLASS_NAME_TRIM_MODE))
                .map(name -> {
                    try {
                        return Constants.TrimMode.valueOf(name);
                    } catch (final IllegalArgumentException ignore) {
                        final String message = format(
                                "Unsupported ?: '?'. Must be one of the following: ?!",
                                CONFIGURATION_LOGGER_CLASS_NAME_TRIM_MODE, name, Arrays.toString(Constants.TrimMode.values())
                        );
                        java.util.logging.Logger.getGlobal().log(Level.SEVERE, message);
                        throw new ExceptionInInitializerError(message);
                    }
                })
                .orElse(Constants.TrimMode.ALL_CLASSES);
    }

    @Override
    public Logger getLogger(final String name) {
        return new JULLogger(name);
    }

    @Override
    public Logger getLogger(final Class<?> clazz) {
        return new JULLogger(getTrimmedName(clazz));
    }

    private String getTrimmedName(final Class<?> clazz) {
        if (trimMode == Constants.TrimMode.ALL_CLASSES) {
            return clazz.getSimpleName();
        } else if (trimMode == Constants.TrimMode.FRAMEWORK_CLASSES_ONLY) {
            if (isDeniedPackage(clazz.getPackageName())) {
                return clazz.getSimpleName();
            } else {
                return clazz.getName();
            }
        } else {
            return clazz.getName();
        }
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
