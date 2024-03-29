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

package io.rxmicro.logger.impl;

import io.rxmicro.logger.Level;
import io.rxmicro.logger.Logger;
import io.rxmicro.logger.LoggerEvent;
import io.rxmicro.logger.RequestIdSupplier;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static io.rxmicro.logger.LoggerFactory.newLoggerEventBuilder;
import static io.rxmicro.logger.impl.AbstractLoggerTestFactory.LOGGER_EVENT;
import static io.rxmicro.logger.impl.AbstractLoggerTestFactory.REQUEST_ID_SUPPLIER;
import static io.rxmicro.logger.impl.AbstractLoggerTestFactory.THROWABLE;
import static io.rxmicro.logger.impl.AbstractLoggerTestFactory.createIsLevelEnabledStream;
import static io.rxmicro.logger.impl.AbstractLoggerTestFactory.createLoggerEventStream;
import static io.rxmicro.logger.impl.AbstractLoggerTestFactory.createMessageOnlyStream;
import static io.rxmicro.logger.impl.AbstractLoggerTestFactory.createRequestIdSupplierWithMessageStream;
import static io.rxmicro.logger.impl.AbstractLoggerTestFactory.createRequestIdSupplierWithThrowableAndMessageStream;
import static io.rxmicro.logger.impl.AbstractLoggerTestFactory.createThrowableWithMessageStream;
import static java.util.function.Function.identity;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author nedis
 * @since 0.7.3
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SuppressWarnings("PMD.ProperLogger")
final class AbstractLoggerTest {

    private final AtomicBoolean levelEnabled = new AtomicBoolean(false);

    private final AbstractLogger logger = spy(new AbstractLogger() {

        @Override
        public String getName() {
            return "name";
        }

        @Override
        protected boolean isLevelEnabled(final Level level) {
            return levelEnabled.get();
        }

        @Override
        protected void log(final Level level,
                           final LoggerEvent loggerEvent) {
            // do nothing
        }

        @Override
        protected void log(final Level level,
                           final String message) {
            // do nothing
        }

        @Override
        protected void log(final Level level,
                           final String message,
                           final Throwable throwable) {
            // do nothing
        }

        @Override
        protected void log(final RequestIdSupplier requestIdSupplier,
                           final Level level,
                           final String message) {
            // do nothing
        }

        @Override
        protected void log(final RequestIdSupplier requestIdSupplier,
                           final Level level,
                           final String message,
                           final Throwable throwable) {
            // do nothing
        }
    });

    @Order(1)
    @ParameterizedTest
    @ArgumentsSource(AllLoggerActionArgumentsProvider.class)
    void Should_not_log_any_message(final Consumer<Logger> loggerConsumer) {
        levelEnabled.set(false);

        loggerConsumer.accept(logger);

        verifyLoggerSpy(LogMethod.NONE);
    }

    @Order(2)
    @ParameterizedTest
    @ArgumentsSource(MessageOnlyArgumentsProvider.class)
    void Should_log_message_only(final Consumer<Logger> loggerConsumer) {
        levelEnabled.set(true);

        loggerConsumer.accept(logger);

        verifyLoggerSpy(LogMethod.MESSAGE);
    }

    @Order(3)
    @ParameterizedTest
    @ArgumentsSource(ThrowableWithMessageArgumentsProvider.class)
    void Should_log_throwable_with_message(final Consumer<Logger> loggerConsumer) {
        levelEnabled.set(true);

        loggerConsumer.accept(logger);

        verifyLoggerSpy(LogMethod.MESSAGE_AND_THROWABLE);
    }

    @Order(4)
    @ParameterizedTest
    @ArgumentsSource(RequestIdSupplierWithMessageArgumentsProvider.class)
    void Should_log_request_id_supplier_with_message(final Consumer<Logger> loggerConsumer) {
        levelEnabled.set(true);

        loggerConsumer.accept(logger);

        verifyLoggerSpy(LogMethod.REQUEST_ID_SUPPLIER_AND_MESSAGE);
    }

    @Order(5)
    @ParameterizedTest
    @ArgumentsSource(RequestIdSupplierWithThrowableAndMessageArgumentsProvider.class)
    void Should_log_request_id_supplier_with_throwable_and_message(final Consumer<Logger> loggerConsumer) {
        levelEnabled.set(true);

        loggerConsumer.accept(logger);

        verifyLoggerSpy(LogMethod.REQUEST_ID_SUPPLIER_MESSAGE_AND_THROWABLE);
    }

    @Order(6)
    @ParameterizedTest
    @ArgumentsSource(LoggerEventsArgumentsProvider.class)
    void Should_log_the_specified_logger_event(final Consumer<Logger> loggerConsumer) {
        levelEnabled.set(true);

        loggerConsumer.accept(logger);

        verifyLoggerSpy(LogMethod.LOG_EVENT);
    }

    @Order(7)
    @ParameterizedTest
    @ArgumentsSource(FormattedMessageArgumentsArgumentsProvider.class)
    void message_building_should_not_invoked_if_logger_is_disabled(final Object arg) {
        levelEnabled.set(false);

        if (arg instanceof Supplier) {
            logger.error(newLoggerEventBuilder()
                    .setMessage("TestTemplate", (Supplier<?>) arg)
                    .build());
        } else {
            logger.error(newLoggerEventBuilder()
                    .setMessage("TestTemplate", arg)
                    .build());
        }

        verifyLoggerSpy(LogMethod.NONE);
    }

    @Order(8)
    @ParameterizedTest
    @ArgumentsSource(IsLevelEnabledArgumentsProvider.class)
    void Should_verify_if_level_enabled(final Function<Logger, Boolean> loggerConsumer) {
        levelEnabled.set(true);

        assertTrue(loggerConsumer.apply(logger));

        verifyLoggerSpy(LogMethod.NONE);
    }

    /**
     * @author nedis
     * @since 0.10
     */
    private enum LogMethod {

        NONE,

        LOG_EVENT,

        MESSAGE,

        MESSAGE_AND_THROWABLE,

        REQUEST_ID_SUPPLIER_AND_MESSAGE,

        REQUEST_ID_SUPPLIER_MESSAGE_AND_THROWABLE
    }

    private void verifyLoggerSpy(final LogMethod logMethod) {
        verify(logger, times(1)).isLevelEnabled(any(Level.class));
        if (logMethod == LogMethod.LOG_EVENT) {
            verify(logger, times(1)).log(any(Level.class), eq(LOGGER_EVENT));
        } else {
            verify(logger, never()).log(any(Level.class), any(LoggerEvent.class));
        }
        if (logMethod == LogMethod.MESSAGE) {
            verify(logger, times(1)).log(any(Level.class), anyString());
        } else {
            verify(logger, never()).log(any(Level.class), anyString());
        }
        if (logMethod == LogMethod.MESSAGE_AND_THROWABLE) {
            verify(logger, times(1)).log(any(Level.class), eq("123456"), eq(THROWABLE));
        } else {
            verify(logger, never()).log(any(Level.class), anyString(), any(Throwable.class));
        }
        if (logMethod == LogMethod.REQUEST_ID_SUPPLIER_AND_MESSAGE) {
            verify(logger, times(1)).log(eq(REQUEST_ID_SUPPLIER), any(Level.class), eq("123456"));
        } else {
            verify(logger, never()).log(any(RequestIdSupplier.class), any(Level.class), anyString());
        }
        if (logMethod == LogMethod.REQUEST_ID_SUPPLIER_MESSAGE_AND_THROWABLE) {
            verify(logger, times(1)).log(eq(REQUEST_ID_SUPPLIER), any(Level.class), eq("123456"), eq(THROWABLE));
        } else {
            verify(logger, never()).log(any(RequestIdSupplier.class), any(Level.class), anyString(), any(Throwable.class));
        }
    }

    /**
     * @author nedis
     * @since 0.7.3
     */
    public static class AllLoggerActionArgumentsProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(final ExtensionContext context) {
            return Stream.of(
                    createLoggerEventStream(),
                    createMessageOnlyStream(),
                    createThrowableWithMessageStream(),
                    createRequestIdSupplierWithMessageStream(),
                    createRequestIdSupplierWithThrowableAndMessageStream()
            ).flatMap(identity()).map(Arguments::of);
        }
    }

    /**
     * @author nedis
     * @since 0.7.3
     */
    public static class MessageOnlyArgumentsProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(final ExtensionContext context) {
            return createMessageOnlyStream().map(Arguments::of);
        }
    }

    /**
     * @author nedis
     * @since 0.7.3
     */
    public static class ThrowableWithMessageArgumentsProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(final ExtensionContext context) {
            return createThrowableWithMessageStream().map(Arguments::of);
        }
    }

    /**
     * @author nedis
     * @since 0.7.3
     */
    public static class RequestIdSupplierWithMessageArgumentsProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(final ExtensionContext context) {
            return createRequestIdSupplierWithMessageStream().map(Arguments::of);
        }
    }

    /**
     * @author nedis
     * @since 0.7.3
     */
    public static class RequestIdSupplierWithThrowableAndMessageArgumentsProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(final ExtensionContext context) {
            return createRequestIdSupplierWithThrowableAndMessageStream().map(Arguments::of);
        }
    }

    /**
     * @author nedis
     * @since 0.7.3
     */
    public static class IsLevelEnabledArgumentsProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(final ExtensionContext context) {
            return createIsLevelEnabledStream().map(Arguments::of);
        }
    }

    /**
     * @author nedis
     * @since 0.8
     */
    public static class LoggerEventsArgumentsProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(final ExtensionContext context) {
            return createLoggerEventStream().map(Arguments::of);
        }
    }

    /**
     * @author nedis
     * @since 0.8
     */
    public static class FormattedMessageArgumentsArgumentsProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(final ExtensionContext context) {
            return Stream.of(
                    arguments(new Object() {
                        @Override
                        public String toString() {
                            return fail("The message should be built only if appropriate logger level is enabled!");
                        }
                    }),
                    arguments((Supplier<Object>) () -> {
                        return fail("The message should be built only if appropriate logger level is enabled!");
                    })
            );
        }
    }
}
