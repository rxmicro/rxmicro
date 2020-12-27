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

import io.rxmicro.logger.Level;
import io.rxmicro.logger.RequestIdSupplier;
import io.rxmicro.logger.internal.jul.config.adapter.RxMicroLogRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.ArgumentCaptor;

import static io.rxmicro.reflection.Reflections.setFieldValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

/**
 * @author nedis
 * @since 0.7.3
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
final class JULLoggerTest {

    private static final String NAME = "test";

    private final Throwable throwable = new Throwable("test");

    private final RequestIdSupplier requestIdSupplier = () -> "TestRequestId";

    private final JULLogger julLogger = new JULLogger(NAME);

    private final java.util.logging.Logger realLogger = spy(java.util.logging.Logger.getLogger(NAME));

    @BeforeEach
    void beforeEach() {
        setFieldValue(julLogger, "logger", realLogger);
    }

    @Test
    @Order(1)
    void getName() {
        assertEquals(NAME, julLogger.getName());
    }

    @Test
    @Order(2)
    void isLevelEnabled() {
        julLogger.isLevelEnabled(Level.DEBUG);

        verify(realLogger).isLoggable(java.util.logging.Level.FINE);
    }

    @Test
    @Order(3)
    void log_the_specified_logger_event_with_exact_message() {
        final Thread thread = new Thread(mock(Runnable.class), "thread-1") {
            @Override
            public long getId() {
                return 1L;
            }
        };

        julLogger.log(Level.DEBUG, new RxMicroLogRecord.Builder()
                .setRequestIdSupplier(requestIdSupplier)
                .setStackFrame("Class", "method", "file", 15)
                .setMessage("message")
                .setThread(thread)
                .setThrowable(throwable)
                .build());

        ArgumentCaptor<RxMicroLogRecord> argumentCaptor = forClass(RxMicroLogRecord.class);
        verify(realLogger).log(argumentCaptor.capture());
        final RxMicroLogRecord record = argumentCaptor.getValue();

        assertEquals(java.util.logging.Level.FINE, record.getLevel());
        assertEquals("message", record.getMessage());
        assertEquals(requestIdSupplier.getRequestId(), record.getRequestId());
        assertSame(throwable, record.getThrown());
        assertEquals("Class", record.getSourceClassName());
        assertEquals("method", record.getSourceMethodName());
        assertEquals("file", record.getFileName());
        assertEquals(15, record.getLineNumber());
        assertEquals(1, record.getThreadID());
        assertEquals("thread-1", record.getThreadName());
    }

    @Test
    @Order(4)
    void log_the_specified_logger_event_with_message_template() {

        julLogger.log(Level.DEBUG, new RxMicroLogRecord.Builder()
                .setMessage("message: ?-?", 1, 2)
                .build());

        ArgumentCaptor<RxMicroLogRecord> argumentCaptor = forClass(RxMicroLogRecord.class);
        verify(realLogger).log(argumentCaptor.capture());
        final RxMicroLogRecord record = argumentCaptor.getValue();

        assertEquals(java.util.logging.Level.FINE, record.getLevel());
        assertEquals("message: 1-2", record.getMessage());
    }

    @Test
    @Order(5)
    void log_message_only() {
        julLogger.log(Level.DEBUG, "debug");

        ArgumentCaptor<RxMicroLogRecord> argumentCaptor = forClass(RxMicroLogRecord.class);
        verify(realLogger).log(argumentCaptor.capture());
        final RxMicroLogRecord record = argumentCaptor.getValue();

        assertNull(record.getRequestId());
        assertNull(record.getThrown());
        assertEquals("debug", record.getMessage());
        assertEquals(java.util.logging.Level.FINE, record.getLevel());
    }

    @Test
    @Order(6)
    void log_message_with_throwable() {
        julLogger.log(Level.DEBUG, "debug", throwable);

        ArgumentCaptor<RxMicroLogRecord> argumentCaptor = forClass(RxMicroLogRecord.class);
        verify(realLogger).log(argumentCaptor.capture());
        final RxMicroLogRecord record = argumentCaptor.getValue();

        assertNull(record.getRequestId());
        assertSame(throwable, record.getThrown());
        assertEquals("debug", record.getMessage());
        assertEquals(java.util.logging.Level.FINE, record.getLevel());
    }

    @Test
    @Order(7)
    void log_request_id_supplier_with_message() {
        julLogger.log(requestIdSupplier, Level.DEBUG, "debug");

        ArgumentCaptor<RxMicroLogRecord> argumentCaptor = forClass(RxMicroLogRecord.class);
        verify(realLogger).log(argumentCaptor.capture());
        final RxMicroLogRecord record = argumentCaptor.getValue();

        assertEquals("TestRequestId", record.getRequestId());
        assertNull(record.getThrown());
        assertEquals("debug", record.getMessage());
        assertEquals(java.util.logging.Level.FINE, record.getLevel());
    }

    @Test
    @Order(8)
    void log_request_id_supplier_with_message_and_throwable() {
        julLogger.log(requestIdSupplier, Level.DEBUG, "debug", throwable);

        ArgumentCaptor<RxMicroLogRecord> argumentCaptor = forClass(RxMicroLogRecord.class);
        verify(realLogger).log(argumentCaptor.capture());
        final RxMicroLogRecord record = argumentCaptor.getValue();

        assertEquals("TestRequestId", record.getRequestId());
        assertSame(throwable, record.getThrown());
        assertEquals("debug", record.getMessage());
        assertEquals(java.util.logging.Level.FINE, record.getLevel());
    }
}