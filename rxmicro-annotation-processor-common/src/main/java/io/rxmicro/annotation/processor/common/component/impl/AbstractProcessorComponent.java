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

package io.rxmicro.annotation.processor.common.component.impl;

import io.rxmicro.annotation.processor.common.SupportedOptions;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;

import javax.lang.model.element.Element;
import javax.tools.Diagnostic;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Supplier;

import static io.rxmicro.annotation.processor.common.util.AnnotationProcessorEnvironment.getCompilerOptions;
import static io.rxmicro.annotation.processor.common.util.AnnotationProcessorEnvironment.errorDetected;
import static io.rxmicro.annotation.processor.common.util.AnnotationProcessorEnvironment.getMessager;
import static io.rxmicro.annotation.processor.common.util.InternalLoggers.logThrowableStackTrace;
import static io.rxmicro.annotation.processor.common.util.InternalLoggers.logMessage;
import static io.rxmicro.common.util.Formats.format;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public abstract class AbstractProcessorComponent {

    private Level level;

    private Level getLevel() {
        if (level == null) {
            level = Level.of(getStringOption(
                    SupportedOptions.RX_MICRO_LOG_LEVEL,
                    SupportedOptions.RX_MICRO_LOG_LEVEL_DEFAULT_VALUE
            ));
        }
        return level;
    }

    protected final void debug(final Supplier<String> supplier) {
        if (getLevel() == Level.DEBUG) {
            logMessage(Level.DEBUG.name(), supplier.get());
        }
    }

    protected final void debug(final String message,
                               final Object... args) {
        if (getLevel() == Level.DEBUG) {
            logMessage(Level.DEBUG.name(), format(message, args));
        }
    }

    protected final void debug(final String message,
                               final Supplier<?>... args) {
        if (getLevel() == Level.DEBUG) {
            logMessage(Level.DEBUG.name(), format(message, Arrays.stream(args).map(Supplier::get).toArray()));
        }
    }

    protected final void info(final Supplier<String> supplier) {
        if (getLevel() == Level.INFO) {
            logMessage(Level.INFO.name(), supplier.get());
        }
    }

    protected final void info(final String message,
                              final Object... args) {
        if (getLevel() == Level.INFO) {
            logMessage(Level.INFO.name(), format(message, args));
        }
    }

    protected final void info(final String message,
                              final Supplier<?>... args) {
        if (getLevel() == Level.INFO) {
            logMessage(Level.INFO.name(), format(message, Arrays.stream(args).map(Supplier::get).toArray()));
        }
    }

    protected final void error(final String message,
                               final Object... args) {
        if (getLevel() != Level.OFF) {
            logMessage("ERROR", format(message, args));
        }
    }

    protected final void warn(final Element element,
                              final String message,
                              final Object... args) {
        final String mes = (args.length == 0) ? message : format(message, args);
        getMessager().printMessage(Diagnostic.Kind.MANDATORY_WARNING, mes, element);
    }

    protected final void error(final InterruptProcessingException e) {
        error(e.getElement(), e.getMessage());
    }

    protected final void error(final Element element,
                               final String message,
                               final Object... args) {
        final String mes = (args.length == 0) ? message : format(message, args);
        getMessager().printMessage(Diagnostic.Kind.ERROR, mes, element);
        errorDetected();
    }

    protected final void cantGenerateClass(final String generatedClassName,
                                           final Throwable throwable) {
        logThrowableStackTrace(throwable);
        getMessager().printMessage(Diagnostic.Kind.ERROR,
                format("Can't generate class ?: ?", generatedClassName, throwable.getMessage()));
        errorDetected();
    }

    protected final void cantGenerateDocument(final String documentName,
                                              final Throwable throwable) {
        logThrowableStackTrace(throwable);
        getMessager().printMessage(Diagnostic.Kind.ERROR,
                format("Can't generate document ?: ?", documentName, throwable.getMessage()));
        errorDetected();
    }

    protected final void cantGenerateMethodBody(final String templateName,
                                                final Throwable throwable) {
        logThrowableStackTrace(throwable);
        getMessager().printMessage(Diagnostic.Kind.ERROR,
                format("Can't generate method body using '?' template: ?",
                        templateName, throwable.getMessage()));
        errorDetected();
    }

    protected final int getIntOption(final String propertyName,
                                     final int defaultValue) {
        final String value = getStringOption(propertyName, null);
        if (value != null) {
            try {
                return Integer.parseInt(value);
            } catch (final NumberFormatException e) {
                // return defaultValue
            }
        }
        return defaultValue;
    }

    protected final boolean getBooleanOption(final String propertyName,
                                             final boolean defaultValue) {
        final String value = getStringOption(propertyName, null);
        return Optional.ofNullable(value).map(Boolean::parseBoolean).orElse(defaultValue);
    }

    protected final String getStringOption(final String propertyName,
                                           final String defaultValue) {

        String value = getCompilerOptions().get(propertyName);
        if (value != null) {
            return value;
        }
        value = System.getProperty(propertyName);
        if (value != null) {
            return value;
        }
        value = System.getenv().get(propertyName);
        if (value != null) {
            return value;
        }
        return defaultValue;
    }

    /**
     * @author nedis
     * @link https://rxmicro.io
     * @since 0.1
     */
    public enum Level {

        INFO,

        DEBUG,

        OFF;

        static Level of(final String name) {
            for (final Level value : values()) {
                if (value.name().equalsIgnoreCase(name)) {
                    return value;
                }
            }
            return OFF;
        }
    }
}
