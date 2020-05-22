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

package io.rxmicro.http;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

/**
 * Utility class for HTTP values.
 *
 * @author nedis
 * @see QueryParams
 * @since 0.1
 */
@SuppressWarnings("JavaDoc")
public final class HttpValues {

    /**
     * Defines a delimiter that used for separation of the list values.
     *
     * <p>
     * If the HTTP header (or HTT query parameter) of an HTTP request (or response) is a list of values,
     * the list elements are transferred by default via the HTTP protocol as a string separated by the
     * {@value #STRING_ARRAY_DELIMITER} symbol.
     */
    public static final String STRING_ARRAY_DELIMITER = "|";

    /**
     * Converts the array to the string separated by the {@value #STRING_ARRAY_DELIMITER} symbol.
     *
     * @param array the array to convert
     * @return the string separated by the {@value #STRING_ARRAY_DELIMITER} symbol
     */
    public static String arrayToString(final Object array) {
        final StringBuilder stringBuilder = new StringBuilder(50);
        final int length = Array.getLength(array);
        for (int i = 0; i < length; i++) {
            if (i > 0) {
                stringBuilder.append(STRING_ARRAY_DELIMITER);
            }
            stringBuilder.append(simpleObjectToString(Array.get(array, i)));
        }
        return stringBuilder.toString();
    }

    /**
     * Converts the list to the string separated by the {@value #STRING_ARRAY_DELIMITER} symbol.
     *
     * @param list the list to convert
     * @return the string separated by the {@value #STRING_ARRAY_DELIMITER} symbol
     */
    public static String listToString(final List<?> list) {
        final StringBuilder stringBuilder = new StringBuilder(50);
        for (int i = 0; i < list.size(); i++) {
            if (i > 0) {
                stringBuilder.append(STRING_ARRAY_DELIMITER);
            }
            stringBuilder.append(simpleObjectToString(list.get(i)));
        }
        return stringBuilder.toString();
    }

    /**
     * Converts the collection to the string separated by the {@value #STRING_ARRAY_DELIMITER} symbol.
     *
     * @param collection the collection to convert
     * @return the string separated by the {@value #STRING_ARRAY_DELIMITER} symbol
     */
    public static String collectionToString(final Collection<?> collection) {
        final StringBuilder stringBuilder = new StringBuilder(50);
        for (final Object o : collection) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append(STRING_ARRAY_DELIMITER);
            }
            stringBuilder.append(simpleObjectToString(o));
        }
        return stringBuilder.toString();
    }

    /**
     * Converts the object value to the string representation.
     *
     * @param value the object value
     * @return the string representation of the specified object value.
     */
    public static String objectToString(final Object value) {
        if (value == null) {
            return null;
        } else if (value instanceof BigDecimal) {
            return ((BigDecimal) value).toPlainString();
        } else if (value instanceof Collection) {
            return collectionToString((Collection<?>) value);
        } else if (value.getClass().isArray()) {
            return arrayToString(value);
        } else {
            return value.toString();
        }
    }

    private static String simpleObjectToString(final Object value) {
        if (value == null) {
            return null;
        } else if (value instanceof BigDecimal) {
            return ((BigDecimal) value).toPlainString();
        } else {
            return value.toString();
        }
    }

    private HttpValues() {
    }
}
