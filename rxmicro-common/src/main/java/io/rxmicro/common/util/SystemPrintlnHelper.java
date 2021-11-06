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

import java.io.PrintStream;

/**
 * Util class that delegates printing of messages to stdout or stderr.
 *
 * @author nedis
 * @since 0.10
 */
public final class SystemPrintlnHelper {

    private static final PrintStream STD_OUT = System.out;

    private static final PrintStream STD_ERR = System.err;

    /**
     * Prints the {@code message} to the default out stream (stdout).
     *
     * @param message the message
     */
    public static void printlnToStdOut(final String message) {
        STD_OUT.println(message);
    }

    /**
     * Prints the {@code message} to the default error stream (stderr).
     *
     * @param message the message
     */
    public static void printlnToStdErr(final String message) {
        STD_ERR.println(message);
    }

    /**
     * Prints the stack trace for the specified throwable to the default out stream (stdout).
     *
     * @param throwable the specified throwable
     */
    public static void printStackTraceToStdOut(final Throwable throwable) {
        throwable.printStackTrace(STD_OUT);
    }

    /**
     * Prints the stack trace for the specified throwable to the default error stream (stderr).
     *
     * @param throwable the specified throwable
     */
    public static void printStackTraceToStdErr(final Throwable throwable) {
        throwable.printStackTrace(STD_ERR);
    }

    private SystemPrintlnHelper() {
    }
}
