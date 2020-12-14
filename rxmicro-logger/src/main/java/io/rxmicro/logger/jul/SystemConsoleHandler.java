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

package io.rxmicro.logger.jul;

import java.io.OutputStream;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;
import java.util.logging.StreamHandler;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.logger.internal.jul.InternalLoggerHelper.logInternal;

/**
 * This {@link java.util.logging.Handler} publishes log records to {@code System.out}.
 * By default the {@link PatternFormatter} with {@value PatternFormatter#DEFAULT_PATTERN} pattern is used to generate messages.
 *
 * @author nedis
 * @since 0.1
 */
public class SystemConsoleHandler extends StreamHandler {

    /**
     * The standard output stream.
     *
     * @see System#out
     */
    public static final String STD_OUT = "stdout";

    /**
     * The standard error stream.
     *
     * @see System#err
     */
    public static final String STD_ERROR = "stderror";

    private static OutputStream resolveStream() {
        final LogManager manager = LogManager.getLogManager();
        final String stream = Optional.ofNullable(manager.getProperty(format("?.stream", SystemConsoleHandler.class.getName())))
                .orElse(STD_OUT);
        if (STD_ERROR.equals(stream)) {
            return System.err;
        } else {
            if (!STD_OUT.equals(stream)) {
                logInternal(Level.WARNING, "Unsupported stream name: ?! Set 'stdout' as destination stream!", stream);
            }
            return System.out;
        }
    }

    /**
     * Creates an instance of {@link SystemConsoleHandler} class.
     *
     * <p>
     * To configure output stream use the following property:
     * {@code io.rxmicro.logger.jul.SystemConsoleHandler.stream=stdout}
     * or
     * {@code io.rxmicro.logger.jul.SystemConsoleHandler.stream=stderror}
     */
    public SystemConsoleHandler() {
        super(resolveStream(), new PatternFormatter());
    }

    @Override
    public synchronized void publish(final LogRecord record) {
        super.publish(record);
        flush();
    }
}
