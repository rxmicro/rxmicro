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

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.logging.Level;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author nedis
 * @since 0.7.3
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
final class RxMicroLogRecordTest {

    private final RxMicroLogRecord record = new RxMicroLogRecord("LoggerName", Level.INFO, "message");

    @Test
    @Order(1)
    void setSourceClassName_should_throw_UnsupportedOperationException() {
        final UnsupportedOperationException exception =
                assertThrows(UnsupportedOperationException.class, () -> record.setSourceClassName("Class"));
        assertEquals("Use setStackFrame() instead!", exception.getMessage());
    }

    @Test
    @Order(2)
    void setSourceMethodName_should_throw_UnsupportedOperationException() {
        final UnsupportedOperationException exception =
                assertThrows(UnsupportedOperationException.class, () -> record.setSourceMethodName("method"));
        assertEquals("Use setStackFrame() instead!", exception.getMessage());
    }

    @Test
    @Order(3)
    void getSourceClassName_should_invoke_extractDataFromStackFrameIfPossible() {
        assertNull(record.getSourceClassName());
    }

    @Test
    @Order(4)
    void getSourceMethodName_should_invoke_extractDataFromStackFrameIfPossible() {
        assertNull(record.getSourceMethodName());
    }

    @Test
    @Order(5)
    void getFileName_should_invoke_extractDataFromStackFrameIfPossible() {
        assertNull(record.getFileName());
    }

    @Test
    @Order(6)
    void getLineNumber_should_invoke_extractDataFromStackFrameIfPossible() {
        assertEquals(0, record.getLineNumber());
    }
}
