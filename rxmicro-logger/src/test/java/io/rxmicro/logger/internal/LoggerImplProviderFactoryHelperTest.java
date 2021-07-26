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

package io.rxmicro.logger.internal;

import io.rxmicro.logger.Logger;
import io.rxmicro.logger.LoggerEventBuilder;
import io.rxmicro.logger.impl.LoggerImplProvider;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import static io.rxmicro.logger.internal.LoggerImplProviderFactoryHelper.createAndSetupLoggerImplProvider;
import static java.lang.System.lineSeparator;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author nedis
 * @since 0.7.3
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
final class LoggerImplProviderFactoryHelperTest {

    private static final PrintStream ORIGINAL_SYS_ERROR = System.err;

    @Test
    @Order(1)
    void createAndSetupLoggerImplProvider_should_log_all_exceptions() {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        System.setErr(new PrintStream(byteArrayOutputStream));
        try {
            final LoggerImplProvider provider = createAndSetupLoggerImplProvider(TestLoggerImplProvider.class);
            assertEquals(TestLoggerImplProvider.class, provider.getClass());
            final List<String> lines = Arrays.stream(new String(byteArrayOutputStream.toByteArray(), UTF_8)
                    .split(lineSeparator()))
                    .collect(toList());
            assertEquals("Can't setup logger impl factory: test", lines.get(0));
            assertEquals("java.io.IOException: test", lines.get(1));
        } finally {
            System.setErr(ORIGINAL_SYS_ERROR);
        }
    }

    /**
     * @author nedis
     * @since 0.7.3
     */
    public static final class TestLoggerImplProvider implements LoggerImplProvider {

        @Override
        public void setup() throws IOException {
            throw new IOException("test");
        }

        @Override
        public Logger getLogger(final String name) {
            return null;
        }

        @Override
        public LoggerEventBuilder newLoggerEventBuilder() {
            return null;
        }
    }
}
