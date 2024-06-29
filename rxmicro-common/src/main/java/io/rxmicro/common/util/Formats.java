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

package io.rxmicro.common.util;

import java.text.NumberFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static io.rxmicro.common.CommonConstants.EMPTY_STRING;
import static io.rxmicro.common.util.Environments.isCurrentOsWindows;
import static java.lang.System.lineSeparator;

/**
 * Format utility class.
 *
 * @author nedis
 * @since 0.1
 */
@SuppressWarnings("JavaDoc")
public final class Formats {

    /**
     * The universal format character placeholder: {@value #FORMAT_PLACEHOLDER_CHAR}.
     */
    public static final char FORMAT_PLACEHOLDER_CHAR = '?';

    /**
     * The universal format string placeholder: {@value #FORMAT_PLACEHOLDER_CHAR}.
     */
    public static final String FORMAT_PLACEHOLDER_TOKEN = String.valueOf(FORMAT_PLACEHOLDER_CHAR);

    private static final String LINE_SEPARATOR = lineSeparator();

    private static final boolean IS_CURRENT_OS_WINDOWS = isCurrentOsWindows();

    private static final int BYTES_IN_1_KIB = 1024;

    private static final int NANOS_IN_1_MILLIS = (int) TimeUnit.MILLISECONDS.toNanos(1);

    private static final int B_PARTS_SIZE = 1;

    private static final int KB_PARTS_SIZE = 2;

    private static final int MB_PARTS_SIZE = 3;

    private static final int GB_PARTS_SIZE = 4;

    private static final int TB_PARTS_SIZE = 5;

    /**
     * Formats the string template using specified arguments.
     *
     * <p>
     * This method never throws any exception.
     *
     * <p>
     * If provided arguments are invalid, this method produces formatted string with unprocessed placeholders or 'Unused arguments' suffix.
     * See examples section below for details.
     *
     * <p>
     * Use {@value #FORMAT_PLACEHOLDER_CHAR} character as placeholder for argument.
     *
     * <p>
     * <i>(This method also replaces '\n' character by {@link System#lineSeparator()}.)</i>
     *
     * <p>
     * Examples:
     * <pre><code>
     * format("? ?!", "Hello", "world")                 ->   Hello world!
     * format("? ?!", "Hello")                          ->   Hello ?!
     * format("? ? ? ?!", "Hello", "world")             ->   Hello world ? ?!
     * format("? ?!", "Hello", "world", "or", "java")   ->   Hello world! -> Unused arguments: [or, java]
     * format(null, "or", "java")                       ->   null -> Unused arguments: [or, java]
     * </code></pre>
     *
     * <p>
     * This method never returns {@code null}!
     * If {@code messageTemplate} is null and {@code arg} is empty, the {@code "null"} string returned!
     *
     * @param messageTemplate the message template
     * @param args            the message template arguments
     * @return the formatted string
     */
    public static String format(final String messageTemplate,
                                final Object... args) {
        if (args == null || args.length == 0) {
            return formatWithoutArguments(messageTemplate);
        } else {
            return formatWithArguments(messageTemplate, args);
        }
    }

    /**
     * Formats the specified {@link Duration} into the human readable format.
     *
     * @param duration the specified {@link Duration}
     * @return the human readable format of the specified {@link Duration}
     * @see Duration
     * @since 0.3
     */
    public static String format(final Duration duration) {
        if (duration.getSeconds() == 0) {
            return (((double) duration.getNano()) / NANOS_IN_1_MILLIS) + "ms";
        } else {
            return duration.toString()
                    .substring(2)
                    .replaceAll("(\\d[HMS])(?!$)", "$1 ")
                    .toLowerCase(Locale.ENGLISH);
        }
    }

    /**
     * Formats the data size in bytes into the formatted human readable data size.
     *
     * @param size              the data size in bytes
     * @param withOriginalValue show or not original data size in bytes
     * @return the formatted human readable data size
     */
    public static String formatSize(final long size,
                                    final boolean withOriginalValue) {
        final List<String> parts = new ArrayList<>();
        long current = size;
        while (current > 0) {
            final long value = current % BYTES_IN_1_KIB;
            parts.add(String.valueOf(value));
            current = current / BYTES_IN_1_KIB;
        }
        final String originalFragment = withOriginalValue ? " (" + NumberFormat.getNumberInstance().format(size) + " bytes)" : EMPTY_STRING;
        return parts.get(parts.size() - 1) + " " + getUnits(parts) + originalFragment;
    }

    /**
     * Formats the data size in bytes into the formatted human readable data size and displays it with the original data size in bytes.
     *
     * @param size the data size in bytes
     * @return the formatted human readable data size
     */
    public static String formatSize(final long size) {
        return formatSize(size, true);
    }

    private static String formatWithoutArguments(final String messageTemplate) {
        if (IS_CURRENT_OS_WINDOWS && messageTemplate != null) {
            final StringBuilder sb = new StringBuilder(messageTemplate.length());
            for (int i = 0; i < messageTemplate.length(); i++) {
                final char ch = messageTemplate.charAt(i);
                if (ch == '\n') {
                    sb.append(LINE_SEPARATOR);
                } else {
                    sb.append(ch);
                }
            }
            return sb.toString();
        }
        return String.valueOf(messageTemplate);
    }

    private static String formatWithArguments(final String messageTemplate,
                                              final Object... args) {
        if (messageTemplate == null) {
            return "null -> Unused arguments: " + Arrays.toString(args);
        } else {
            final StringBuilder sb = new StringBuilder(messageTemplate.length() * 3 / 2);
            int index = 0;
            for (int i = 0; i < messageTemplate.length(); i++) {
                final char ch = messageTemplate.charAt(i);
                if (ch == '\n') {
                    sb.append(LINE_SEPARATOR);
                } else if (ch == FORMAT_PLACEHOLDER_CHAR) {
                    if (index < args.length) {
                        sb.append(args[index++]);
                    } else {
                        sb.append(FORMAT_PLACEHOLDER_CHAR);
                    }
                } else {
                    sb.append(ch);
                }
            }
            if (index < args.length) {
                sb.append(" -> Unused arguments: [");
                for (int i = index; i < args.length; i++) {
                    if (i != index) {
                        sb.append(", ");
                    }
                    sb.append(args[i]);
                }
                sb.append(']');
            }
            return sb.toString();
        }
    }

    private static String getUnits(final List<String> parts) {
        switch (parts.size()) {
            case B_PARTS_SIZE: {
                return "bytes";
            }
            case KB_PARTS_SIZE: {
                return "Kb";
            }
            case MB_PARTS_SIZE: {
                return "Mb";
            }
            case GB_PARTS_SIZE: {
                return "Gb";
            }
            case TB_PARTS_SIZE: {
                return "Tb";
            }
            default: {
                throw new IllegalArgumentException("Unsupported units");
            }
        }
    }

    private Formats() {
    }
}
