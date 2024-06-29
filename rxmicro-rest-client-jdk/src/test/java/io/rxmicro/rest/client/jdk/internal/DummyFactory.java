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

package io.rxmicro.rest.client.jdk.internal;

import io.rxmicro.logger.Level;
import io.rxmicro.logger.LoggerEvent;
import io.rxmicro.logger.RequestIdSupplier;
import io.rxmicro.logger.impl.AbstractLogger;
import io.rxmicro.rest.client.detail.HttpClientContentConverter;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author nedis
 * @since 0.8
 */
final class DummyFactory {

    static final String TEST_CONTENT_DATA = "Hello world!";

    static final String TEST_CONTENT_LENGTH = String.valueOf(TEST_CONTENT_DATA.length());

    static final String TEST_CONTENT_TYPE = "text/plain";

    /**
     * @author nedis
     * @since 0.8
     */
    static final class StubHttpClientContentConverter implements HttpClientContentConverter {

        @Override
        public Function<Object, byte[]> getRequestContentConverter() {
            return o -> String.valueOf(o).getBytes(UTF_8);
        }

        @Override
        public Function<byte[], Object> getResponseContentConverter() {
            return bytes -> new String(bytes, UTF_8);
        }

        @Override
        public String getContentType() {
            return "text/plain";
        }
    }

    /**
     * @author nedis
     * @since 0.8
     */
    static class StubAbstractLogger extends AbstractLogger {

        private final Map<Level, Boolean> levelEnabledMap;

        public StubAbstractLogger(final Map<Level, Boolean> levelEnabledMap) {
            this.levelEnabledMap = levelEnabledMap;
        }

        @Override
        public boolean isLevelEnabled(final Level level) {
            return Optional.ofNullable(levelEnabledMap.get(level)).orElse(false);
        }

        @Override
        public void log(final Level level,
                        final LoggerEvent loggerEvent) {
            // do nothing
        }

        @Override
        public void log(final Level level,
                        final String message) {
            // do nothing
        }

        @Override
        public void log(final Level level,
                        final String message,
                        final Throwable throwable) {
            // do nothing
        }

        @Override
        public void log(final RequestIdSupplier requestIdSupplier,
                        final Level level,
                        final String message) {
            // do nothing
        }

        @Override
        public void log(final RequestIdSupplier requestIdSupplier,
                        final Level level,
                        final String message,
                        final Throwable throwable) {
            // do nothing
        }

        @Override
        public String getName() {
            return "test";
        }
    }
}
