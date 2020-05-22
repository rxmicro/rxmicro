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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

import static java.lang.Character.isLowerCase;
import static java.lang.Character.isUpperCase;
import static java.lang.Character.toLowerCase;
import static java.lang.Character.toUpperCase;
import static java.util.Collections.unmodifiableList;

/**
 * Extended {@link String} utils.
 *
 * @author nedis
 * @see String
 * @since 0.1
 */
public final class Strings {

    private static final int HEX_CODE_LENGTH = 4;

    /**
     * Capitalizes the specified string.
     *
     * @param string the specified string
     * @return the capitalized string
     */
    public static String capitalize(final String string) {
        if (string.length() > 1) {
            if (isUpperCase(string.charAt(0))) {
                return string;
            } else {
                return toUpperCase(string.charAt(0)) + string.substring(1);
            }
        } else if (string.length() == 1) {
            return string.toUpperCase(Locale.ENGLISH);
        } else {
            return string;
        }
    }

    /**
     * Uncapitalizes the specified string.
     *
     * @param string the specified string
     * @return the uncapitalized string
     */
    public static String unCapitalize(final String string) {
        if (string.length() > 1) {
            if (isLowerCase(string.charAt(0))) {
                return string;
            } else {
                return toLowerCase(string.charAt(0)) + string.substring(1);
            }
        } else if (string.length() == 1) {
            return string.toLowerCase(Locale.ENGLISH);
        } else {
            return string;
        }
    }

    /**
     * Splits the specified variable name on words.
     *
     * <p>
     * This method uses <a href="https://en.wikipedia.org/wiki/Camel_case"> Camel case notation</a> for defining the word delimiter.
     *
     * @param variableName the specified variable name
     * @return the word list extracted from the specified variable name
     */
    public static List<String> splitByCamelCase(final String variableName) {
        final List<String> list = new ArrayList<>();
        final StringBuilder wordBuilder = new StringBuilder();
        for (int i = 0; i < variableName.length(); i++) {
            final char ch = variableName.charAt(i);
            if (isUpperCase(ch) && wordBuilder.length() > 0) {
                list.add(wordBuilder.toString());
                wordBuilder.delete(0, wordBuilder.length());
            }
            wordBuilder.append(ch);
        }
        if (wordBuilder.length() > 0) {
            list.add(wordBuilder.toString());
        }
        return collectAbbreviation(list);
    }

    private static List<String> collectAbbreviation(final List<String> list) {
        final List<String> result = new ArrayList<>();
        final Iterator<String> iterator = list.iterator();
        final StringBuilder abbreviationBuilder = new StringBuilder();
        while (iterator.hasNext()) {
            final String word = iterator.next();
            if (word.length() == 1 && isUpperCase(word.charAt(0))) {
                abbreviationBuilder.append(word);
            } else {
                if (abbreviationBuilder.length() > 0) {
                    result.add(abbreviationBuilder.toString());
                    abbreviationBuilder.delete(0, abbreviationBuilder.length());
                }
                result.add(word);
            }
        }
        if (abbreviationBuilder.length() > 0) {
            result.add(abbreviationBuilder.toString());
            abbreviationBuilder.delete(0, abbreviationBuilder.length());
        }
        return result;
    }

    /**
     * Splits the specified {@code source} by the provided {@code delimiter}.
     *
     * @param source the specified string source
     * @param delimiter the specified delimiter string
     * @return the token list
     * @see StringTokenizer
     */
    public static List<String> split(final String source,
                                     final String delimiter) {
        if (source == null || source.isEmpty()) {
            return List.of();
        } else {
            final List<String> result = new ArrayList<>(5);
            final StringTokenizer stringTokenizer = new StringTokenizer(source, delimiter);
            while (stringTokenizer.hasMoreTokens()) {
                result.add(stringTokenizer.nextToken());
            }
            return unmodifiableList(result);
        }
    }

    /**
     * Replaces the unread characters by its readable codes.
     *
     * @param original the original string
     * @return the escaped string based on the original string
     */
    public static String escapeString(final String original) {
        final StringBuilder resultBuilder = new StringBuilder();
        escapeString(resultBuilder, original);
        return resultBuilder.toString();
    }

    /**
     * Replaces the unread characters by its readable codes.
     *
     * @param resultBuilder the result string builder
     * @param original the original string
     * @see StringBuilder
     */
    public static void escapeString(final StringBuilder resultBuilder,
                                    final String original) {
        for (int i = 0; i < original.length(); i++) {
            final char ch = original.charAt(i);
            if (ch == '\\' || ch == '"') {
                resultBuilder.append('\\').append(ch);
            } else if (ch == '\b') {
                resultBuilder.append("\\b");
            } else if (ch == '\t') {
                resultBuilder.append("\\t");
            } else if (ch == '\n') {
                resultBuilder.append("\\n");
            } else if (ch == '\f') {
                resultBuilder.append("\\f");
            } else if (ch == '\r') {
                resultBuilder.append("\\r");
            } else if (ch < ' ' || (ch >= '\u0080' && ch < '\u00a0') ||
                    (ch >= '\u2000' && ch < '\u2100')) {
                resultBuilder.append("\\u");
                final String hexCode = Integer.toHexString(ch);
                resultBuilder.append("0000", 0, HEX_CODE_LENGTH - hexCode.length());
                resultBuilder.append(hexCode);
            } else {
                resultBuilder.append(ch);
            }
        }
    }

    /**
     * More effective version of {@link String#startsWith(String)} method if the {@code prefix} is one character.
     *
     * @param string the tested string
     * @param prefix the prefix
     * @return {@code true} if the tested string is not empty and starts with the prefix character
     * @see String#startsWith(String)
     */
    public static boolean startsWith(final String string,
                                     final char prefix) {
        return !string.isEmpty() && string.charAt(0) == prefix;
    }

    private Strings() {
    }
}
