/*
 * Copyright (c) 2020. http://rxmicro.io
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
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public final class HttpValues {

    public static final String STRING_ARRAY_DELIMITER = "|";

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

    private static String simpleObjectToString(final Object value) {
        if (value == null) {
            return null;
        } else if (value instanceof BigDecimal) {
            return ((BigDecimal) value).toPlainString();
        } else {
            return value.toString();
        }
    }

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

    private HttpValues() {
    }
}
