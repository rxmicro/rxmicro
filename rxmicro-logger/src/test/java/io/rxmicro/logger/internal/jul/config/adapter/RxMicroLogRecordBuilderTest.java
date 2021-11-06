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

package io.rxmicro.logger.internal.jul.config.adapter;

import io.rxmicro.logger.RequestIdSupplier;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.Mockito.mock;

/**
 * @author nedis
 * @since 0.8
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
final class RxMicroLogRecordBuilderTest {

    private final RxMicroLogRecord.Builder builder = new RxMicroLogRecord.Builder();

    @Test
    @Order(1)
    void method_setStackFrame_should_throw_IllegalArgumentException_if_sourceLineNumber_is_invalid() {
        final IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> builder.setStackFrame("", "", "", -1));
        assertEquals("'sourceLineNumber' parameter must be > 0!", exception.getMessage());
    }

    @Test
    @Order(2)
    void method_setThreadId_should_throw_IllegalArgumentException_if_threadId_is_invalid() {
        final IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> builder.setThreadId(-1));
        assertEquals("'threadId' parameter must be > 0!", exception.getMessage());
    }

    @Test
    @Order(3)
    void method_setThreadId_should_not_set_threadID_if_it_has_large_value() {
        builder.setThreadId(Integer.MAX_VALUE);

        assertNotEquals(Integer.MAX_VALUE, ((RxMicroLogRecord) builder.build()).getThreadID());
    }

    @ParameterizedTest
    @ArgumentsSource(AllMethodsArgumentsProvider.class)
    @Order(4)
    void all_methods_should_not_throw_any_exceptions_if_logger_event_already_built(final Consumer<RxMicroLogRecord.Builder> consumer) {
        builder.build();

        assertDoesNotThrow(() -> consumer.accept(builder));
    }

    @ParameterizedTest
    @ArgumentsSource(SetMessageArgumentsProvider.class)
    @Order(5)
    void method_setMessage_should_not_throw_any_exceptions_if_message_already_set(final Consumer<RxMicroLogRecord.Builder> first,
                                                                                  final Consumer<RxMicroLogRecord.Builder> second) {
        first.accept(builder);

        assertDoesNotThrow(() -> second.accept(builder));
    }

    /**
     * @author nedis
     * @since 0.8
     */
    private static final class AllMethodsArgumentsProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(final ExtensionContext context) {
            final Stream<Consumer<RxMicroLogRecord.Builder>> stream = Stream.of(
                    builder -> builder.setRequestIdSupplier(mock(RequestIdSupplier.class)),
                    builder -> builder.setStackFrame("", "", "", 15),
                    builder -> builder.setMessage("message"),
                    builder -> builder.setMessage("?-?", 1, 2),
                    builder -> builder.setMessage("?-?", () -> 1, () -> 2),
                    builder -> builder.setThreadId(1),
                    builder -> builder.setThreadName("threadName"),
                    builder -> builder.setThread(new Thread(mock(Runnable.class), "threadName")),
                    builder -> builder.setThrowable(new Throwable("test")),
                    RxMicroLogRecord.Builder::build
            );
            return stream.map(Arguments::of);
        }
    }

    /**
     * @author nedis
     * @since 0.8
     */
    private static final class SetMessageArgumentsProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(final ExtensionContext context) {
            final List<Consumer<RxMicroLogRecord.Builder>> consumers = List.of(
                    builder -> builder.setMessage("message: 1-2"),
                    builder -> builder.setMessage("message: ?-?", 1, 2),
                    builder -> builder.setMessage("message: ?-?", () -> 1, () -> 2)
            );
            return Stream.of(
                    arguments(consumers.get(0), consumers.get(0)),
                    arguments(consumers.get(0), consumers.get(1)),
                    arguments(consumers.get(0), consumers.get(2)),

                    arguments(consumers.get(1), consumers.get(0)),
                    arguments(consumers.get(1), consumers.get(1)),
                    arguments(consumers.get(1), consumers.get(2)),

                    arguments(consumers.get(2), consumers.get(0)),
                    arguments(consumers.get(2), consumers.get(1)),
                    arguments(consumers.get(2), consumers.get(2))
            );
        }
    }
}
