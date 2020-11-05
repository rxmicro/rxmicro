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

package io.rxmicro.rest.local;

import io.rxmicro.http.error.ValidationException;
import io.rxmicro.rest.model.HttpModelType;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.rxmicro.common.local.Examples.INSTANT_EXAMPLE;
import static io.rxmicro.common.util.Strings.split;
import static io.rxmicro.http.HttpValues.STRING_ARRAY_DELIMITER;
import static java.util.Collections.unmodifiableList;

/**
 * @author nedis
 * @since 0.1
 */
@SuppressWarnings({"ForLoopReplaceableByForEach", "rawtypes", "unchecked"})
public abstract class FromStringValueConverter extends AbstractValidatedConverter {

    private static final int DEFAULT_SIZE = 5;

    private static final List EMPTY_LIST = List.of();

    /**
     * Enable for overriding by sub classes
     */
    @SuppressWarnings("SameReturnValue")
    protected String getStringArrayDelimiter() {
        return STRING_ARRAY_DELIMITER;
    }

    // -------------------------------------------------------------------------------------------------------------------------------------

    protected final <E extends Enum<E>> E toEnum(final Class<E> enumClass,
                                                 final String value,
                                                 final HttpModelType httpModelType,
                                                 final String modelName) {
        if (value == null) {
            return null;
        } else {
            try {
                return Enum.valueOf(enumClass, value);
            } catch (final IllegalArgumentException ignore) {
                throw new ValidationException(
                        "Invalid ? \"?\": Expected a value from the set ?, but actual is '?'!",
                        httpModelType, modelName, Arrays.toString(enumClass.getEnumConstants()), value
                );
            }
        }
    }

    protected final <E extends Enum<E>> List<E> toEnumArray(final Class<E> enumClass,
                                                            final List<String> list,
                                                            final HttpModelType httpModelType,
                                                            final String modelName) {
        if (list != null) {
            final int size = list.size();
            final List<E> result = new ArrayList<>(size == 1 ? DEFAULT_SIZE : size);
            for (int i = 0; i < size; i++) {
                final String value = list.get(i);
                final List<String> strings = split(value, getStringArrayDelimiter());
                for (int j = 0; j < strings.size(); j++) {
                    result.add(toEnum(enumClass, strings.get(j), httpModelType, modelName));
                }
            }
            return unmodifiableList(result);
        } else {
            return EMPTY_LIST;
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------------------

    protected final Boolean toBoolean(final String value,
                                      final HttpModelType httpModelType,
                                      final String modelName) {
        if (value == null) {
            return null;
        } else {
            if (Boolean.TRUE.toString().equals(value)) {
                return Boolean.TRUE;
            } else if (Boolean.FALSE.toString().equals(value)) {
                return Boolean.FALSE;
            } else {
                throw new ValidationException(
                        "Invalid ? \"?\": Expected a boolean value ('true' or 'false'), but actual is '?'!",
                        httpModelType, modelName, value
                );
            }
        }
    }

    protected final List<Boolean> toBooleanArray(final List<String> list,
                                                 final HttpModelType httpModelType,
                                                 final String modelName) {
        if (list != null) {
            final int size = list.size();
            final List<Boolean> result = new ArrayList<>(size == 1 ? DEFAULT_SIZE : size);
            for (int i = 0; i < size; i++) {
                final String value = list.get(i);
                final List<String> array = split(value, getStringArrayDelimiter());
                for (int j = 0; j < array.size(); j++) {
                    result.add(toBoolean(array.get(j), httpModelType, modelName));
                }
            }
            return unmodifiableList(result);
        } else {
            return EMPTY_LIST;
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------------------

    protected final Byte toByte(final String value,
                                final HttpModelType httpModelType,
                                final String modelName) {
        if (value == null) {
            return null;
        } else {
            try {
                return Byte.parseByte(value);
            } catch (final NumberFormatException ignore) {
                throw createExpectedIntegerValidationException(value, httpModelType, modelName, Byte.MIN_VALUE, Byte.MAX_VALUE);
            }
        }
    }

    protected final List<Byte> toByteArray(final List<String> list,
                                           final HttpModelType httpModelType,
                                           final String modelName) {
        if (list != null) {
            final int size = list.size();
            final List<Byte> result = new ArrayList<>(size == 1 ? DEFAULT_SIZE : size);
            for (int i = 0; i < size; i++) {
                final String value = list.get(i);
                final List<String> array = split(value, getStringArrayDelimiter());
                for (int j = 0; j < array.size(); j++) {
                    result.add(toByte(array.get(j), httpModelType, modelName));
                }
            }
            return unmodifiableList(result);
        } else {
            return EMPTY_LIST;
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------------------

    protected final Short toShort(final String value,
                                  final HttpModelType httpModelType,
                                  final String modelName) {
        if (value == null) {
            return null;
        } else {
            try {
                return Short.parseShort(value);
            } catch (final NumberFormatException ignore) {
                throw createExpectedIntegerValidationException(value, httpModelType, modelName, Short.MIN_VALUE, Short.MAX_VALUE);
            }
        }
    }

    protected final List<Short> toShortArray(final List<String> list,
                                             final HttpModelType httpModelType,
                                             final String modelName) {
        if (list != null) {
            final int size = list.size();
            final List<Short> result = new ArrayList<>(size == 1 ? DEFAULT_SIZE : size);
            for (int i = 0; i < size; i++) {
                final String value = list.get(i);
                final List<String> array = split(value, getStringArrayDelimiter());
                for (int j = 0; j < array.size(); j++) {
                    result.add(toShort(array.get(j), httpModelType, modelName));
                }
            }
            return unmodifiableList(result);
        } else {
            return EMPTY_LIST;
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------------------

    protected final Integer toInteger(final String value,
                                      final HttpModelType httpModelType,
                                      final String modelName) {
        if (value == null) {
            return null;
        } else {
            try {
                return Integer.parseInt(value);
            } catch (final NumberFormatException ignore) {
                throw createExpectedIntegerValidationException(value, httpModelType, modelName, Integer.MIN_VALUE, Integer.MAX_VALUE);
            }
        }
    }

    protected final List<Integer> toIntegerArray(final List<String> list,
                                                 final HttpModelType httpModelType,
                                                 final String modelName) {
        if (list != null) {
            final int size = list.size();
            final List<Integer> result = new ArrayList<>(size == 1 ? DEFAULT_SIZE : size);
            for (int i = 0; i < size; i++) {
                final String value = list.get(i);
                final List<String> array = split(value, getStringArrayDelimiter());
                for (int j = 0; j < array.size(); j++) {
                    result.add(toInteger(array.get(j), httpModelType, modelName));
                }
            }
            return unmodifiableList(result);
        } else {
            return EMPTY_LIST;
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------------------

    protected final Long toLong(final String value,
                                final HttpModelType httpModelType,
                                final String modelName) {
        if (value == null) {
            return null;
        } else {
            try {
                return Long.parseLong(value);
            } catch (final NumberFormatException ignore) {
                throw createExpectedIntegerValidationException(value, httpModelType, modelName, Long.MIN_VALUE, Long.MAX_VALUE);
            }
        }
    }

    protected final List<Long> toLongArray(final List<String> list,
                                           final HttpModelType httpModelType,
                                           final String modelName) {
        if (list != null) {
            final int size = list.size();
            final List<Long> result = new ArrayList<>(size == 1 ? DEFAULT_SIZE : size);
            for (int i = 0; i < size; i++) {
                final String value = list.get(i);
                final List<String> array = split(value, getStringArrayDelimiter());
                for (int j = 0; j < array.size(); j++) {
                    result.add(toLong(array.get(j), httpModelType, modelName));
                }
            }
            return unmodifiableList(result);
        } else {
            return EMPTY_LIST;
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------------------

    protected final BigInteger toBigInteger(final String value,
                                            final HttpModelType httpModelType,
                                            final String modelName) {
        if (value == null) {
            return null;
        } else {
            try {
                return new BigInteger(value);
            } catch (final NumberFormatException ignore) {
                throw new ValidationException(
                        "Invalid ? \"?\": Expected an integer value, but actual is '?'!",
                        httpModelType, modelName, value
                );
            }
        }
    }

    protected final List<BigInteger> toBigIntegerArray(final List<String> list,
                                                       final HttpModelType httpModelType,
                                                       final String modelName) {
        if (list != null) {
            final int size = list.size();
            final List<BigInteger> result = new ArrayList<>(size == 1 ? DEFAULT_SIZE : size);
            for (int i = 0; i < size; i++) {
                final String value = list.get(i);
                final List<String> array = split(value, getStringArrayDelimiter());
                for (int j = 0; j < array.size(); j++) {
                    result.add(toBigInteger(array.get(j), httpModelType, modelName));
                }
            }
            return unmodifiableList(result);
        } else {
            return EMPTY_LIST;
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------------------

    protected final Float toFloat(final String value,
                                  final HttpModelType httpModelType,
                                  final String modelName) {
        if (value == null) {
            return null;
        } else {
            try {
                return floatIfValid(value, Float.parseFloat(value), httpModelType, modelName);
            } catch (final NumberFormatException ignore) {
                throw new ValidationException(
                        "Invalid ? \"?\": Expected a decimal value, but actual is '?'!",
                        httpModelType, modelName, value
                );
            }
        }
    }

    protected final List<Float> toFloatArray(final List<String> list,
                                             final HttpModelType httpModelType,
                                             final String modelName) {
        if (list != null) {
            final int size = list.size();
            final List<Float> result = new ArrayList<>(size == 1 ? DEFAULT_SIZE : size);
            for (int i = 0; i < size; i++) {
                final String value = list.get(i);
                final List<String> array = split(value, getStringArrayDelimiter());
                for (int j = 0; j < array.size(); j++) {
                    result.add(toFloat(array.get(j), httpModelType, modelName));
                }
            }
            return unmodifiableList(result);
        } else {
            return EMPTY_LIST;
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------------------

    protected final Double toDouble(final String value,
                                    final HttpModelType httpModelType,
                                    final String modelName) {
        if (value == null) {
            return null;
        } else {
            try {
                return doubleIfValid(value, Double.parseDouble(value), httpModelType, modelName);
            } catch (final NumberFormatException ignore) {
                throw new ValidationException(
                        "Invalid ? \"?\": Expected a decimal value, but actual is '?'!",
                        httpModelType, modelName, value
                );
            }
        }
    }

    protected final List<Double> toDoubleArray(final List<String> list,
                                               final HttpModelType httpModelType,
                                               final String modelName) {
        if (list != null) {
            final int size = list.size();
            final List<Double> result = new ArrayList<>(size == 1 ? DEFAULT_SIZE : size);
            for (int i = 0; i < size; i++) {
                final String value = list.get(i);
                final List<String> array = split(value, getStringArrayDelimiter());
                for (int j = 0; j < array.size(); j++) {
                    result.add(toDouble(array.get(j), httpModelType, modelName));
                }
            }
            return unmodifiableList(result);
        } else {
            return EMPTY_LIST;
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------------------

    protected final BigDecimal toBigDecimal(final String value,
                                            final HttpModelType httpModelType,
                                            final String modelName) {
        if (value == null) {
            return null;
        } else {
            try {
                return new BigDecimal(value);
            } catch (final NumberFormatException ignore) {
                throw new ValidationException(
                        "Invalid ? \"?\": Expected a decimal value, but actual is '?'!",
                        httpModelType, modelName, value
                );
            }
        }
    }

    protected final List<BigDecimal> toBigDecimalArray(final List<String> list,
                                                       final HttpModelType httpModelType,
                                                       final String modelName) {
        if (list != null) {
            final int size = list.size();
            final List<BigDecimal> result = new ArrayList<>(size == 1 ? DEFAULT_SIZE : size);
            for (int i = 0; i < size; i++) {
                final String value = list.get(i);
                final List<String> array = split(value, getStringArrayDelimiter());
                for (int j = 0; j < array.size(); j++) {
                    result.add(toBigDecimal(array.get(j), httpModelType, modelName));
                }
            }
            return unmodifiableList(result);
        } else {
            return EMPTY_LIST;
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------------------

    protected final Instant toInstant(final String value,
                                      final HttpModelType httpModelType,
                                      final String modelName) {
        if (value == null) {
            return null;
        } else {
            try {
                return Instant.parse(value);
            } catch (final DateTimeParseException ignore) {
                throw new ValidationException(
                        "Invalid ? \"?\": Expected an ISO-8601 instant " +
                                "(Example: '?'), but actual is '?'!",
                        httpModelType, modelName, INSTANT_EXAMPLE, value);
            }
        }
    }

    protected final List<Instant> toInstantArray(final List<String> list,
                                                 final HttpModelType httpModelType,
                                                 final String modelName) {
        if (list != null) {
            final int size = list.size();
            final List<Instant> result = new ArrayList<>(size == 1 ? DEFAULT_SIZE : size);
            for (int i = 0; i < size; i++) {
                final String value = list.get(i);
                final List<String> array = split(value, getStringArrayDelimiter());
                for (int j = 0; j < array.size(); j++) {
                    result.add(toInstant(array.get(j), httpModelType, modelName));
                }
            }
            return unmodifiableList(result);
        } else {
            return EMPTY_LIST;
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------------------

    protected final Character toCharacter(final String value,
                                          final HttpModelType httpModelType,
                                          final String modelName) {
        if (value == null) {
            return null;
        } else {
            if (value.length() != 1) {
                throw new ValidationException(
                        "Invalid ? \"?\": Expected a character, but actual is '?'!",
                        httpModelType, modelName, value
                );
            }
            return value.charAt(0);
        }
    }

    protected final List<Character> toCharacterArray(final List<String> list,
                                                     final HttpModelType httpModelType,
                                                     final String modelName) {
        if (list != null) {
            final int size = list.size();
            final List<Character> result = new ArrayList<>(size == 1 ? DEFAULT_SIZE : size);
            for (int i = 0; i < size; i++) {
                final String value = list.get(i);
                final List<String> array = split(value, getStringArrayDelimiter());
                for (int j = 0; j < array.size(); j++) {
                    result.add(toCharacter(array.get(j), httpModelType, modelName));
                }
            }
            return unmodifiableList(result);
        } else {
            return EMPTY_LIST;
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------------------

    @SuppressWarnings("unused")
    protected final String toString(final String value,
                                    final HttpModelType httpModelType,
                                    final String modelName) {
        return value;
    }

    protected final List<String> toStringArray(final List<String> list,
                                               final HttpModelType httpModelType,
                                               final String modelName) {
        if (list != null) {
            final int size = list.size();
            final List<String> result = new ArrayList<>(size == 1 ? DEFAULT_SIZE : size);
            for (int i = 0; i < size; i++) {
                final String value = list.get(i);
                final List<String> array = split(value, getStringArrayDelimiter());
                for (int j = 0; j < array.size(); j++) {
                    result.add(toString(array.get(j), httpModelType, modelName));
                }
            }
            return unmodifiableList(result);
        } else {
            return EMPTY_LIST;
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------------------

    protected final void throwNotImplYet(final String message) {
        throw new UnsupportedOperationException(message);
    }
}
