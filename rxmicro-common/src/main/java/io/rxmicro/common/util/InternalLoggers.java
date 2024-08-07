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

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.common.util.SystemPrintlnHelper.printlnToStdOut;

/**
 * Simplest logger utils for internal use only.
 *
 * <p>Don't use this util for your production code!
 *
 * <p>Use {@code rxmicro-logger} module instead!
 *
 * @author nedis
 * @since 0.4
 */
public final class InternalLoggers {

    /**
     * Log the message at the {@code INFO} level.
     *
     * <p>
     * <i>(FYI: This method uses {@link Formats#format(String, Object...)} method to format info message.)</i>
     *
     * @param messageTemplate the message template
     * @param args            the message template arguments
     */
    public static void logInfoTestMessage(final String messageTemplate,
                                          final Object... args) {
        logTestMessage("INFO", messageTemplate, args);
    }

    /**
     * Log the message at the {@code ERROR} level.
     *
     * <p>
     * <i>(FYI: This method uses {@link Formats#format(String, Object...)} method to format error message.)</i>
     *
     * @param messageTemplate the message template
     * @param args            the message template arguments
     */
    public static void logErrorTestMessage(final String messageTemplate,
                                           final Object... args) {
        logTestMessage("ERROR", messageTemplate, args);
    }

    private static void logTestMessage(final String level,
                                       final String message,
                                       final Object... args) {
        printlnToStdOut(format("[?] ", level) + format(message, args));
    }

    private InternalLoggers() {
    }
}
