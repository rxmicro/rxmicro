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

import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.annotation.processor.config.LogLevel;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Supplier;
import javax.lang.model.element.Element;
import javax.tools.Diagnostic;

import static io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException.READ_MORE_TEMPLATE;
import static io.rxmicro.annotation.processor.common.util.InternalLoggers.logMessage;
import static io.rxmicro.annotation.processor.common.util.InternalLoggers.logThrowableStackTrace;
import static io.rxmicro.annotation.processor.common.util.ProcessingEnvironmentHelper.aNewCompilationErrorDetected;
import static io.rxmicro.annotation.processor.common.util.ProcessingEnvironmentHelper.getCompilerOptions;
import static io.rxmicro.annotation.processor.common.util.ProcessingEnvironmentHelper.getMessager;
import static io.rxmicro.annotation.processor.config.LogLevel.DEBUG;
import static io.rxmicro.annotation.processor.config.LogLevel.INFO;
import static io.rxmicro.annotation.processor.config.LogLevel.TRACE;
import static io.rxmicro.annotation.processor.config.SupportedOptions.RX_MICRO_BUILD_UNNAMED_MODULE;
import static io.rxmicro.annotation.processor.config.SupportedOptions.RX_MICRO_BUILD_UNNAMED_MODULE_DEFAULT_VALUE;
import static io.rxmicro.annotation.processor.config.SupportedOptions.RX_MICRO_DOC_ANALYZE_PARENT_POM;
import static io.rxmicro.annotation.processor.config.SupportedOptions.RX_MICRO_DOC_ANALYZE_PARENT_POM_DEFAULT_VALUE;
import static io.rxmicro.annotation.processor.config.SupportedOptions.RX_MICRO_LIBRARY_MODULE;
import static io.rxmicro.annotation.processor.config.SupportedOptions.RX_MICRO_LIBRARY_MODULE_DEFAULT_VALUE;
import static io.rxmicro.annotation.processor.config.SupportedOptions.RX_MICRO_LOG_LEVEL;
import static io.rxmicro.annotation.processor.config.SupportedOptions.RX_MICRO_LOG_LEVEL_DEFAULT_VALUE;
import static io.rxmicro.annotation.processor.config.SupportedOptions.RX_MICRO_MAX_JSON_NESTED_DEPTH;
import static io.rxmicro.annotation.processor.config.SupportedOptions.RX_MICRO_MAX_JSON_NESTED_DEPTH_DEFAULT_VALUE;
import static io.rxmicro.annotation.processor.config.SupportedOptions.RX_MICRO_STRICT_MODE;
import static io.rxmicro.annotation.processor.config.SupportedOptions.RX_MICRO_STRICT_MODE_DEFAULT_VALUE;
import static io.rxmicro.common.util.Formats.format;

/**
 * @author nedis
 * @since 0.1
 */
public class BaseProcessorComponent {

    private LogLevel level;

    protected BaseProcessorComponent() {
        // This is basic class designed for extension only.
    }

    private LogLevel getLevel() {
        if (level == null) {
            final String stringLogLevel = getStringOption(RX_MICRO_LOG_LEVEL, RX_MICRO_LOG_LEVEL_DEFAULT_VALUE.name());
            try {
                level = LogLevel.valueOf(stringLogLevel);
            } catch (final IllegalArgumentException ignored) {
                getMessager().printMessage(
                        Diagnostic.Kind.MANDATORY_WARNING,
                        format("Unsupported logger level for the RxMicro Annotation Processor: '?'. " +
                                        "Only following supported: ?. Using default level: '?'",
                                stringLogLevel, Arrays.toString(LogLevel.values()), RX_MICRO_LOG_LEVEL_DEFAULT_VALUE
                        )
                );
                level = RX_MICRO_LOG_LEVEL_DEFAULT_VALUE;
            }
        }
        return level;
    }

    protected final boolean isTraceEnabled() {
        return getLevel().isEnabled(TRACE);
    }

    protected final void trace(final Supplier<String> supplier) {
        if (isTraceEnabled()) {
            logMessage(TRACE, supplier.get());
        }
    }

    protected final void trace(final String message,
                               final Object... args) {
        if (isTraceEnabled()) {
            logMessage(TRACE, format(message, args));
        }
    }

    protected final void trace(final String message,
                               final Supplier<?>... args) {
        if (isTraceEnabled()) {
            logMessage(TRACE, format(message, Arrays.stream(args).map(Supplier::get).toArray()));
        }
    }

    protected final boolean isDebugEnabled() {
        return getLevel().isEnabled(DEBUG);
    }

    protected final void debug(final Supplier<String> supplier) {
        if (isDebugEnabled()) {
            logMessage(DEBUG, supplier.get());
        }
    }

    protected final void debug(final String message,
                               final Object... args) {
        if (isDebugEnabled()) {
            logMessage(DEBUG, format(message, args));
        }
    }

    protected final void debug(final String message,
                               final Supplier<?>... args) {
        if (isDebugEnabled()) {
            logMessage(DEBUG, format(message, Arrays.stream(args).map(Supplier::get).toArray()));
        }
    }

    protected final boolean isInfoEnabled() {
        return getLevel().isEnabled(INFO);
    }

    protected final void info(final Supplier<String> supplier) {
        if (isInfoEnabled()) {
            logMessage(INFO, supplier.get());
        }
    }

    protected final void info(final String message,
                              final Object... args) {
        if (isInfoEnabled()) {
            logMessage(INFO, format(message, args));
        }
    }

    protected final void info(final String message,
                              final Supplier<?>... args) {
        if (isInfoEnabled()) {
            logMessage(INFO, format(message, Arrays.stream(args).map(Supplier::get).toArray()));
        }
    }

    protected final void warn(final String readMoreLink,
                              final Element element,
                              final String message,
                              final Object... args) {
        final String mes = format(message, args) + format(READ_MORE_TEMPLATE, readMoreLink);
        getMessager().printMessage(Diagnostic.Kind.MANDATORY_WARNING, mes, element);
    }

    protected final void warn(final Element element,
                              final String message,
                              final Object... args) {
        final String mes = (args.length == 0) ? message : format(message, args);
        getMessager().printMessage(Diagnostic.Kind.MANDATORY_WARNING, mes, element);
    }

    protected final void error(final String message,
                               final Object... args) {
        getMessager().printMessage(Diagnostic.Kind.ERROR, format(message, args));
    }

    protected final void error(final InterruptProcessingException exception) {
        error(exception.getElement(), exception.getMessage());
    }

    protected final void error(final String readMoreLink,
                               final Element element,
                               final String message,
                               final Object... args) {
        final String mes = format(message, args) + format(READ_MORE_TEMPLATE, readMoreLink);
        getMessager().printMessage(Diagnostic.Kind.ERROR, mes, element);
        aNewCompilationErrorDetected();
    }

    protected final void error(final Element element,
                               final String message,
                               final Object... args) {
        final String mes = (args.length == 0) ? message : format(message, args);
        getMessager().printMessage(Diagnostic.Kind.ERROR, mes, element);
        aNewCompilationErrorDetected();
    }

    protected final void cantGenerateClass(final String generatedClassName,
                                           final Throwable throwable) {
        logThrowableStackTrace(throwable);
        getMessager().printMessage(Diagnostic.Kind.ERROR,
                format("Can't generate class ?: ?", generatedClassName, throwable.getMessage()));
        aNewCompilationErrorDetected();
    }

    protected final void cantGenerateDocument(final String documentName,
                                              final Throwable throwable) {
        logThrowableStackTrace(throwable);
        getMessager().printMessage(Diagnostic.Kind.ERROR,
                format("Can't generate document ?: ?", documentName, throwable.getMessage()));
        aNewCompilationErrorDetected();
    }

    protected final void cantGenerateMethodBody(final String templateName,
                                                final Throwable throwable) {
        logThrowableStackTrace(throwable);
        getMessager().printMessage(Diagnostic.Kind.ERROR,
                format("Can't generate method body using '?' template: ?",
                        templateName, throwable.getMessage()));
        aNewCompilationErrorDetected();
    }

    protected final boolean isLibraryModule() {
        return getBooleanOption(RX_MICRO_LIBRARY_MODULE, RX_MICRO_LIBRARY_MODULE_DEFAULT_VALUE);
    }

    protected final int getMaxJsonNestedDepth() {
        return getIntOption(RX_MICRO_MAX_JSON_NESTED_DEPTH, RX_MICRO_MAX_JSON_NESTED_DEPTH_DEFAULT_VALUE);
    }

    protected final boolean isUnnamedModule() {
        return getBooleanOption(RX_MICRO_BUILD_UNNAMED_MODULE, RX_MICRO_BUILD_UNNAMED_MODULE_DEFAULT_VALUE);
    }

    protected final boolean isStrictModeEnabled() {
        return getBooleanOption(RX_MICRO_STRICT_MODE, RX_MICRO_STRICT_MODE_DEFAULT_VALUE);
    }

    protected final boolean isAnalyzeOfParentPOMDuringDocCreationEnabled() {
        return getBooleanOption(RX_MICRO_DOC_ANALYZE_PARENT_POM, RX_MICRO_DOC_ANALYZE_PARENT_POM_DEFAULT_VALUE);
    }

    @SuppressWarnings("SameParameterValue")
    private int getIntOption(final String propertyName,
                             final int defaultValue) {
        final String value = getStringOption(propertyName, null);
        if (value != null) {
            try {
                return Integer.parseInt(value);
            } catch (final NumberFormatException ignored) {
                // do nothing: return defaultValue
            }
        }
        return defaultValue;
    }

    private boolean getBooleanOption(final String propertyName,
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
}
