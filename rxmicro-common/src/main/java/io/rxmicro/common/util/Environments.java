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

import io.rxmicro.common.model.StringIterator;

import java.util.Locale;
import java.util.Map;

import static io.rxmicro.common.util.Formats.format;
import static java.lang.System.getProperty;

/**
 * Environment utility class.
 *
 * @author nedis
 * @see System#getProperty(String)
 * @since 0.1
 */
public final class Environments {

    private static final String OS_NAME = "os.name";

    private static final String UNKNOWN = "unknown";

    /**
     * Returns {@code true} if current OS is macOS.
     *
     * @return {@code true} if current OS is macOS.
     */
    public static boolean isCurrentOsMac() {
        return getProperty(OS_NAME, UNKNOWN).toLowerCase(Locale.ENGLISH).contains("mac");
    }

    /**
     * Returns {@code true} if current OS is Linux.
     *
     * @return {@code true} if current OS is Linux.
     */
    public static boolean isCurrentOsLinux() {
        return getProperty(OS_NAME, UNKNOWN).toLowerCase(Locale.ENGLISH).contains("linux");
    }

    /**
     * Returns {@code true} if current OS is Windows.
     *
     * @return {@code true} if current OS is Windows.
     */
    public static boolean isCurrentOsWindows() {
        return getProperty(OS_NAME, UNKNOWN).toLowerCase(Locale.ENGLISH).contains("windows");
    }

    /**
     * Finds variables in the specified expression and replaces found variables by the configured environment variables.
     *
     * <p>
     * Supported variable format: <code>${VAR1}</code>
     *
     * @param expression the specified expression that can contains variables
     * @return the resolved expression without variables
     * @see System#getenv()
     * @since 0.6
     * @throws IllegalArgumentException if the specified expression contains undefined variable or has invalid syntax
     */
    public static String resolveEnvironmentVariables(final String expression) {
        return resolveVariables(expression, System.getenv());
    }

    /**
     * Finds variables in the specified expression and replaces found variables by the provided values.
     *
     * <p>
     * Supported variable format: <code>${VAR1}</code>
     *
     * @param expression the specified expression that can contains variables
     * @param variableValues the variable map that contains the pairs of names nad values
     * @return the resolved expression without variables
     * @since 0.6
     * @throws IllegalArgumentException if the specified expression contains undefined variable or has invalid syntax
     */
    public static String resolveVariables(final String expression,
                                          final Map<String, String> variableValues) {
        final StringBuilder stringBuilder = new StringBuilder(expression.length());
        final StringIterator iterator = new StringIterator(expression);
        resolve(variableValues, stringBuilder, iterator);
        return stringBuilder.toString();
    }

    private static void resolve(final Map<String, String> variableValues,
                                final StringBuilder stringBuilder,
                                final StringIterator iterator) {
        while (iterator.next()) {
            final char ch = iterator.getCurrent();
            if ('$' == ch) {
                final String variableName = readVariableName(iterator);
                final String value = variableValues.get(variableName);
                if (value == null) {
                    throw new IllegalArgumentException(format(
                            "Invalid expression: '?'. Variable '?' not defined!",
                            iterator.getSource(), variableName
                    ));
                } else {
                    stringBuilder.append(value);
                }
            } else if ('{' == ch || '}' == ch) {
                throw new IllegalArgumentException(format(
                        "Invalid expression: '?'. Missing '$' at ? position",
                        iterator.getSource(), iterator.getIndex()
                ));
            } else {
                stringBuilder.append(ch);
            }
        }
    }

    private static String readVariableName(final StringIterator iterator) {
        if (iterator.next()) {
            final char ch = iterator.getCurrent();
            if ('{' == ch) {
                final int start = iterator.getIndex() + 1;
                while (iterator.next()) {
                    if ('}' == iterator.getCurrent()) {
                        final int end = iterator.getIndex();
                        if (end > start) {
                            return iterator.getSource().substring(start, end);
                        } else {
                            throw new IllegalArgumentException(format(
                                    "Invalid expression: '?'. Missing variable name at ? position!",
                                    iterator.getSource(), end
                            ));
                        }
                    }
                }
                throw new IllegalArgumentException(format(
                        "Invalid expression: '?'. Missing '}'",
                        iterator.getSource()
                ));
            }
        }
        throw new IllegalArgumentException(format(
                "Invalid expression: '?'. Expected '{' after '$' at ? position!",
                iterator.getSource(), iterator.getIndex()
        ));
    }

    private Environments() {
    }
}
