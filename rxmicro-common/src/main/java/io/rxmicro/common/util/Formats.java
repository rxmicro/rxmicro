/*
 * Copyright (c) 2020 https://rxmicro.io
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

import static io.rxmicro.common.util.Environments.isCurrentOsWindows;
import static io.rxmicro.common.util.Requires.require;
import static java.lang.System.lineSeparator;

/**
 * TODO
 *
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class Formats {

    /**
     * TODO
     */
    public static final char FORMAT_PLACEHOLDER_CHAR = '?';

    /**
     * TODO
     */
    public static final String FORMAT_PLACEHOLDER_TOKEN = String.valueOf(FORMAT_PLACEHOLDER_CHAR);

    private static final String LINE_SEPARATOR = lineSeparator();

    private static final boolean IS_CURRENT_OS_WINDOWS = isCurrentOsWindows();

    /**
     * Use '?' character as placeholder for argument.
     * <p>
     * This method also replaces '\n' character by System.lineSeparator()
     *
     * @param template string template
     * @param args     arguments
     * @return formatted string
     * @throws NullPointerException if {@code template} is {@code null}
     */
    public static String format(final String template,
                                final Object... args) {
        if (args.length == 0) {
            if (IS_CURRENT_OS_WINDOWS) {
                final StringBuilder sb = new StringBuilder(template.length());
                for (int i = 0; i < template.length(); i++) {
                    final char ch = template.charAt(i);
                    if (ch == '\n') {
                        sb.append(LINE_SEPARATOR);
                    } else {
                        sb.append(ch);
                    }
                }
                return sb.toString();
            } else {
                return require(template);
            }
        } else {
            final StringBuilder sb = new StringBuilder(template.length() * 3 / 2);
            int index = 0;
            try {
                for (int i = 0; i < template.length(); i++) {
                    final char ch = template.charAt(i);
                    if (ch == '\n') {
                        sb.append(LINE_SEPARATOR);
                    } else if (ch == FORMAT_PLACEHOLDER_CHAR) {
                        sb.append(args[index++]);
                    } else {
                        sb.append(ch);
                    }
                }
            } catch (final ArrayIndexOutOfBoundsException e) {
                throw new IllegalArgumentException(
                        "Redundant placeholder or missing argument: {{" + template + "}} with " + Arrays.toString(args));
            }
            if (index != args.length) {
                throw new IllegalArgumentException(
                        "Missing placeholder or redundant argument: {{" + template + "}} with " + Arrays.toString(args));
            }
            return sb.toString();
        }
    }

    /**
     * TODO
     *
     * @param size
     * @param withOriginalValue
     * @return
     */
    public static String formatSize(final long size,
                                    final boolean withOriginalValue) {
        final List<String> parts = new ArrayList<>();
        long current = size;
        while (current > 0) {
            final long value = current % 1024;
            parts.add(String.valueOf(value));
            current = current / 1024;
        }
        return parts.get(parts.size() - 1) + " " + getUnits(parts) +
                (withOriginalValue ? " (" + NumberFormat.getNumberInstance().format(size) + " bytes)" : "");
    }

    /**
     * TODO
     *
     * @param size
     * @return
     */
    public static String formatSize(final long size) {
        return formatSize(size, true);
    }

    private static String getUnits(final List<String> parts) {
        switch (parts.size()) {
            case 1:
                return "bytes";
            case 2:
                return "Kb";
            case 3:
                return "Mb";
            case 4:
                return "Gb";
            case 5:
                return "Tb";
            default:
                throw new IllegalArgumentException("Unsupported units");
        }
    }

    /**
     * TODO
     *
     * @param duration
     * @return
     * @since 0.3
     */
    public static String format(final Duration duration) {
        if (duration.getSeconds() == 0) {
            return (((double) duration.getNano()) / 1_000_000) + "ms";
        } else {
            return duration.toString()
                    .substring(2)
                    .replaceAll("(\\d[HMS])(?!$)", "$1 ")
                    .toLowerCase(Locale.ENGLISH);
        }
    }

    private Formats() {
    }
}
