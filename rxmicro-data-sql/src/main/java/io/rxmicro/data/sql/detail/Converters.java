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

package io.rxmicro.data.sql.detail;

import io.rxmicro.data.local.InvalidValueTypeException;

import java.util.Arrays;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class Converters {

    public static <T extends Enum<T>> T toEnum(final Class<T> type,
                                               final Object value,
                                               final String fieldName) {
        if (value == null) {
            return null;
        } else {
            try {
                return Enum.valueOf(type, value.toString());
            } catch (final IllegalArgumentException e) {
                throw new InvalidValueTypeException("Invalid value for \"?\" field: Expected one of the following \"?\" but actual is \"?\"",
                        fieldName, Arrays.toString(type.getEnumConstants()), value.toString());
            }
        }
    }

    public static <T extends Enum<T>> T toEnum(final Class<T> type,
                                               final Object value) {
        if (value == null) {
            return null;
        } else {
            try {
                return Enum.valueOf(type, value.toString());
            } catch (final IllegalArgumentException e) {
                throw new InvalidValueTypeException("Invalid enum value: Expected one of the following \"?\" but actual is \"?\"",
                        Arrays.toString(type.getEnumConstants()), value.toString());
            }
        }
    }

    private Converters() {
    }
}
