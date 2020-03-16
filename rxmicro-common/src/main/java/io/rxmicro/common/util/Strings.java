/*
 * Copyright 2019 https://rxmicro.io
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
import java.util.StringTokenizer;

import static java.lang.Character.isLowerCase;
import static java.lang.Character.isUpperCase;
import static java.lang.Character.toLowerCase;
import static java.lang.Character.toUpperCase;
import static java.util.Collections.unmodifiableList;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class Strings {

    public static String capitalize(final String message) {
        if (message.length() > 1) {
            if (isUpperCase(message.charAt(0))) {
                return message;
            } else {
                return toUpperCase(message.charAt(0)) + message.substring(1);
            }
        } else if (message.length() == 1) {
            return message.toUpperCase();
        } else {
            return message;
        }
    }

    public static String unCapitalize(final String message) {
        if (message.length() > 1) {
            if (isLowerCase(message.charAt(0))) {
                return message;
            } else {
                return toLowerCase(message.charAt(0)) + message.substring(1);
            }
        } else if (message.length() == 1) {
            return message.toLowerCase();
        } else {
            return message;
        }
    }

    public static List<String> splitByCamelCase(final String variableName) {
        final List<String> list = new ArrayList<>();
        final StringBuilder wordBuilder = new StringBuilder();
        for (int i = 0; i < variableName.length(); i++) {
            final char ch = variableName.charAt(i);
            if (isUpperCase(ch)) {
                if (wordBuilder.length() > 0) {
                    list.add(wordBuilder.toString());
                    wordBuilder.delete(0, wordBuilder.length());
                }
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
            if (word.length() == 1 && Character.isUpperCase(word.charAt(0))) {
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

    public static String hideSecureInfo(final String message) {
        if (message != null) {
            if (message.length() > 10) {
                return "****" + message.substring(message.length() - 4);
            } else {
                return "********";
            }
        } else {
            return null;
        }

    }

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

    private Strings() {
    }
}
