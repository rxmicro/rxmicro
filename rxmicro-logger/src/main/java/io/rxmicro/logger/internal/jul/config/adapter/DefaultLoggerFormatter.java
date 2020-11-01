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

import io.rxmicro.logger.Level;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

import static java.lang.System.lineSeparator;
import static java.time.LocalDateTime.ofInstant;
import static java.time.ZoneId.systemDefault;

/**
 * @author nedis
 * @since 0.1
 */
public class DefaultLoggerFormatter extends Formatter {

    private static final Map<java.util.logging.Level, Level> LEVEL_MAPPING = Map.of(
            java.util.logging.Level.OFF, Level.OFF,
            java.util.logging.Level.SEVERE, Level.ERROR,
            java.util.logging.Level.WARNING, Level.WARN,
            java.util.logging.Level.CONFIG, Level.INFO,
            java.util.logging.Level.INFO, Level.INFO,
            java.util.logging.Level.FINE, Level.DEBUG,
            java.util.logging.Level.FINER, Level.DEBUG,
            java.util.logging.Level.FINEST, Level.TRACE,
            java.util.logging.Level.ALL, Level.ALL
    );

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    @Override
    public String format(final LogRecord record) {
        final LocalDateTime localDateTime = ofInstant(record.getInstant(), systemDefault());

        final String source = record.getLoggerName();

        final String throwable;
        if (record.getThrown() != null) {
            final StringWriter sw = new StringWriter();
            final PrintWriter pw = new PrintWriter(sw);
            pw.println();
            record.getThrown().printStackTrace(pw);
            pw.close();
            throwable = sw.toString();
        } else {
            throwable = null;
        }

        final StringBuilder sb = new StringBuilder(localDateTime.format(FORMATTER))
                .append(" [").append(LEVEL_MAPPING.get(record.getLevel())).append("] ")
                .append(source).append(" : ")
                .append(record.getMessage());
        if (throwable != null) {
            sb.append(throwable);
        }
        return sb.append(lineSeparator()).toString();
    }
}
