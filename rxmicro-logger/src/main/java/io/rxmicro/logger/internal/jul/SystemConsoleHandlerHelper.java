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

import io.rxmicro.common.CheckedWrapperException;
import io.rxmicro.logger.jul.SystemConsoleHandler;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.LogManager;

import static io.rxmicro.common.util.Exceptions.getRealThrowable;
import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.logger.internal.jul.InternalLoggerHelper.logInternal;
import static io.rxmicro.logger.internal.jul.LevelMappings.getJulLevel;
import static io.rxmicro.logger.jul.SystemConsoleHandler.AUTO;
import static io.rxmicro.logger.jul.SystemConsoleHandler.STD_ERR;
import static io.rxmicro.logger.jul.SystemConsoleHandler.STD_OUT;
import static io.rxmicro.reflection.Reflections.instantiate;

/**
 * This class is necessary because useful methods from {@link LogManager} has package(default) level access :(
 *
 * @author nedis
 * @since 0.9
 */
public final class SystemConsoleHandlerHelper {

    private static final String FULL_CLASS_NAME = SystemConsoleHandler.class.getName();

    public static Optional<String> getPropertyValue(final LogManager manager,
                                                    final String propertySimpleName) {
        return Optional.ofNullable(manager.getProperty(format("?.?", FULL_CLASS_NAME, propertySimpleName)));
    }

    public static String getConfiguredStream(final LogManager manager) {
        final String stream = getPropertyValue(manager, "stream").orElse(AUTO);
        if (!Set.of(STD_OUT, STD_ERR, AUTO).contains(stream)) {
            logInternal(
                    Level.WARNING,
                    "Unsupported stream name: ?! (Must be one of the following: ?) Set '?' as destination stream!",
                    stream, List.of(AUTO, STD_OUT, STD_ERR), AUTO
            );
            return AUTO;
        }
        return stream;
    }

    @SuppressWarnings("unchecked")
    public static <T> Optional<T> getConfiguredComponent(final LogManager manager,
                                                         final String propertySimpleName,
                                                         final Class<T> expectedType) {
        final String propertyFullName = format("?.?", FULL_CLASS_NAME, propertySimpleName);
        final String className = manager.getProperty(propertyFullName);
        if (className != null) {
            try {
                final Object object = instantiate(className);
                if (expectedType.isAssignableFrom(object.getClass())) {
                    return Optional.of((T) object);
                } else {
                    logCantInstantiateErrorAsWarning(
                            propertyFullName,
                            className,
                            new ClassCastException(format("'?' does not extend '?'!", className, expectedType.getName()))
                    );
                }
            } catch (final SecurityException | CheckedWrapperException | IllegalArgumentException | ExceptionInInitializerError ex) {
                logCantInstantiateErrorAsWarning(propertyFullName, className, ex);
            }
        }
        return Optional.empty();
    }

    public static Optional<Level> getConfiguredLevel(final LogManager manager) {
        final String property = format("?.level", FULL_CLASS_NAME);
        final String level = manager.getProperty(property);
        if (level != null) {
            try {
                return Optional.of(parseLevel(level));
            } catch (final IllegalArgumentException ignore) {
                logUnsupportedLevelValueAsWarning(property, level);
            }
        }
        return Optional.empty();
    }

    public static Predicate<Level> getConfiguredErrLevelForPredicate(final LogManager manager) {
        final String property = format("?.errLevelFor", FULL_CLASS_NAME);
        final String list = manager.getProperty(property);
        if (list != null) {
            final Set<Level> levels = new HashSet<>();
            for (final String level : list.split(",")) {
                try {
                    levels.add(parseLevel(level));
                } catch (final IllegalArgumentException ignore) {
                    logUnsupportedLevelValueAsWarning(property, level);
                }
            }
            if (!levels.isEmpty()) {
                final Set<Level> set = Set.copyOf(levels);
                return set::contains;
            }
        }
        return Level.SEVERE::equals;
    }

    private static Level parseLevel(final String levelValue) {
        try {
            return getJulLevel(io.rxmicro.logger.Level.valueOf(levelValue));
        } catch (final IllegalArgumentException ignore) {
            return Level.parse(levelValue);
        }
    }

    private static void logUnsupportedLevelValueAsWarning(final String parameterName,
                                                          final String level) {
        logInternal(
                Level.WARNING,
                "Unsupported level name: '?' for '?' parameter! (Must be one of the following: " +
                        "{OFF, ERROR, SEVERE, WARN, WARNING, INFO, DEBUG, TRACE, ALL, CONFIG, FINE, FINER, FINES})! " +
                        "This parameter is ignored!",
                level, parameterName
        );
    }

    private static void logCantInstantiateErrorAsWarning(final String property,
                                                         final String className,
                                                         final Throwable throwable) {
        logInternal(
                Level.WARNING,
                "'?' parameter has invalid value: ?, because ?" +
                        "This parameter is ignored!",
                property, className, getRealThrowable(throwable).getMessage()
        );
    }

    private SystemConsoleHandlerHelper() {
    }
}
