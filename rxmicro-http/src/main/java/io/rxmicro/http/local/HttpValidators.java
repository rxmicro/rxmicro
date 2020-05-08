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

package io.rxmicro.http.local;

import static io.rxmicro.common.util.Formats.format;

/**
 * @author nedis
 * @since 0.1
 */
public final class HttpValidators {

    private static final String PROHIBITED_HEADERS_CHARACTERS = "\t\n\f\r ,:;=";

    private static final String PROHIBITED_QUERY_PARAMETERS_CHARACTERS = "\t\n\f\r ?&=";

    /**
     * Validates HTTP header name
     *
     * @param headerName - header name
     * @throws IllegalArgumentException if header name is invalid
     */
    public static String validateHeaderName(final String headerName) {
        for (int i = 0; i < headerName.length(); i++) {
            validateNameCharacter("header", headerName, PROHIBITED_HEADERS_CHARACTERS, headerName.charAt(i));
        }
        return headerName;
    }

    /**
     * Validates HTTP query parameter name
     *
     * @param queryParameter - query parameter name
     * @throws IllegalArgumentException if query parameter name is invalid
     */
    public static String validateParameterName(final String queryParameter) {
        for (int i = 0; i < queryParameter.length(); i++) {
            validateNameCharacter("parameter", queryParameter, PROHIBITED_QUERY_PARAMETERS_CHARACTERS, queryParameter.charAt(i));
        }
        return queryParameter;
    }

    public static void validateQueryParameterNameCharacter(final char value) {
        validateNameCharacter("query parameter or value", "", PROHIBITED_QUERY_PARAMETERS_CHARACTERS, value);
    }

    private static void validateNameCharacter(final String type,
                                              final String name,
                                              final String prohibitedCharacters,
                                              final char value) {
        // 0x00 is NULL, 0x0b is Vertical tab
        if (value == 0x00 || value == 0x0b || prohibitedCharacters.indexOf(value) != -1) {
            throw new IllegalArgumentException(
                    format("A ? name ?cannot contain the following prohibited characters: ?",
                            type,
                            name.isEmpty() ? name : format("'?' ", name),
                            toString(prohibitedCharacters))
            );
        } else if (value > 127) {
            throw new IllegalArgumentException(
                    format("A ? name ?cannot contain non-ASCII character: '?'",
                            type,
                            name.isEmpty() ? name : format("'?' ", name),
                            value)
            );
        }
    }

    private static String toString(final String prohibitedCharacters) {
        final StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < prohibitedCharacters.length(); i++) {
            final char ch = prohibitedCharacters.charAt(i);
            if (ch == '\t') {
                stringBuilder.append("\\t");
            } else if (ch == '\n') {
                stringBuilder.append("\\n");
            } else if (ch == '\r') {
                stringBuilder.append("\\r");
            } else if (ch == '\f') {
                stringBuilder.append("\\f");
            } else {
                stringBuilder.append(ch);
            }
        }
        return stringBuilder.toString();
    }

    private HttpValidators() {
    }
}
