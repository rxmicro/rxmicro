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
import java.util.Set;

import static io.rxmicro.common.local.Examples.INSTANT_EXAMPLE;
import static io.rxmicro.common.util.ExCollections.unmodifiableOrderedSet;
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
            } catch (final IllegalArgumentException ignored) {
                throw new ValidationException(
                        "Invalid ? \"?\": Expected a value from the set ?, but actual is '?'!",
                        httpModelType, modelName, Arrays.toString(enumClass.getEnumConstants()), value
                );
            }
        }
    }

    protected final <E extends Enum<E>> List<E> toEnumList(final Class<E> enumClass,
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

    protected final <E extends Enum<E>> Set<E> toEnumSet(final Class<E> enumClass,
                                                         final List<String> list,
                                                         final HttpModelType httpModelType,
                                                         final String modelName) {
        return unmodifiableOrderedSet(toEnumList(enumClass, list, httpModelType, modelName));
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

    protected final List<Boolean> toBooleanList(final List<String> list,
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

    protected final Set<Boolean> toBooleanSet(final List<String> list,
                                              final HttpModelType httpModelType,
                                              final String modelName) {
        return unmodifiableOrderedSet(toBooleanList(list, httpModelType, modelName));
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
            } catch (final NumberFormatException ignored) {
                throw createExpectedIntegerValidationException(value, httpModelType, modelName, Byte.MIN_VALUE, Byte.MAX_VALUE);
            }
        }
    }

    protected final List<Byte> toByteList(final List<String> list,
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

    protected final Set<Byte> toByteSet(final List<String> list,
                                        final HttpModelType httpModelType,
                                        final String modelName) {
        return unmodifiableOrderedSet(toByteList(list, httpModelType, modelName));
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
            } catch (final NumberFormatException ignored) {
                throw createExpectedIntegerValidationException(value, httpModelType, modelName, Short.MIN_VALUE, Short.MAX_VALUE);
            }
        }
    }

    protected final List<Short> toShortList(final List<String> list,
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

    protected final Set<Short> toShortSet(final List<String> list,
                                          final HttpModelType httpModelType,
                                          final String modelName) {
        return unmodifiableOrderedSet(toShortList(list, httpModelType, modelName));
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
            } catch (final NumberFormatException ignored) {
                throw createExpectedIntegerValidationException(value, httpModelType, modelName, Integer.MIN_VALUE, Integer.MAX_VALUE);
            }
        }
    }

    protected final List<Integer> toIntegerList(final List<String> list,
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

    protected final Set<Integer> toIntegerSet(final List<String> list,
                                              final HttpModelType httpModelType,
                                              final String modelName) {
        return unmodifiableOrderedSet(toIntegerList(list, httpModelType, modelName));
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
            } catch (final NumberFormatException ignored) {
                throw createExpectedIntegerValidationException(value, httpModelType, modelName, Long.MIN_VALUE, Long.MAX_VALUE);
            }
        }
    }

    protected final List<Long> toLongList(final List<String> list,
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

    protected final Set<Long> toLongSet(final List<String> list,
                                        final HttpModelType httpModelType,
                                        final String modelName) {
        return unmodifiableOrderedSet(toLongList(list, httpModelType, modelName));
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
            } catch (final NumberFormatException ignored) {
                throw new ValidationException(
                        "Invalid ? \"?\": Expected an integer value, but actual is '?'!",
                        httpModelType, modelName, value
                );
            }
        }
    }

    protected final List<BigInteger> toBigIntegerList(final List<String> list,
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

    protected final Set<BigInteger> toBigIntegerSet(final List<String> list,
                                                    final HttpModelType httpModelType,
                                                    final String modelName) {
        return unmodifiableOrderedSet(toBigIntegerList(list, httpModelType, modelName));
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
            } catch (final NumberFormatException ignored) {
                throw new ValidationException(
                        "Invalid ? \"?\": Expected a decimal value, but actual is '?'!",
                        httpModelType, modelName, value
                );
            }
        }
    }

    protected final List<Float> toFloatList(final List<String> list,
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

    protected final Set<Float> toFloatSet(final List<String> list,
                                          final HttpModelType httpModelType,
                                          final String modelName) {
        return unmodifiableOrderedSet(toFloatList(list, httpModelType, modelName));
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
            } catch (final NumberFormatException ignored) {
                throw new ValidationException(
                        "Invalid ? \"?\": Expected a decimal value, but actual is '?'!",
                        httpModelType, modelName, value
                );
            }
        }
    }

    protected final List<Double> toDoubleList(final List<String> list,
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

    protected final Set<Double> toDoubleSet(final List<String> list,
                                            final HttpModelType httpModelType,
                                            final String modelName) {
        return unmodifiableOrderedSet(toDoubleList(list, httpModelType, modelName));
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
            } catch (final NumberFormatException ignored) {
                throw new ValidationException(
                        "Invalid ? \"?\": Expected a decimal value, but actual is '?'!",
                        httpModelType, modelName, value
                );
            }
        }
    }

    protected final List<BigDecimal> toBigDecimalList(final List<String> list,
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

    protected final Set<BigDecimal> toBigDecimalSet(final List<String> list,
                                                    final HttpModelType httpModelType,
                                                    final String modelName) {
        return unmodifiableOrderedSet(toBigDecimalList(list, httpModelType, modelName));
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
            } catch (final DateTimeParseException ignored) {
                throw new ValidationException(
                        "Invalid ? \"?\": Expected an ISO-8601 instant " +
                                "(Example: '?'), but actual is '?'!",
                        httpModelType, modelName, INSTANT_EXAMPLE, value);
            }
        }
    }

    protected final List<Instant> toInstantList(final List<String> list,
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

    protected final Set<Instant> toInstantSet(final List<String> list,
                                              final HttpModelType httpModelType,
                                              final String modelName) {
        return unmodifiableOrderedSet(toInstantList(list, httpModelType, modelName));
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

    protected final List<Character> toCharacterList(final List<String> list,
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

    protected final Set<Character> toCharacterSet(final List<String> list,
                                                  final HttpModelType httpModelType,
                                                  final String modelName) {
        return unmodifiableOrderedSet(toCharacterList(list, httpModelType, modelName));
    }

    // -------------------------------------------------------------------------------------------------------------------------------------

    @SuppressWarnings("unused")
    protected final String toString(final String value,
                                    final HttpModelType httpModelType,
                                    final String modelName) {
        return value;
    }

    protected final List<String> toStringList(final List<String> list,
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

    protected final Set<String> toStringSet(final List<String> list,
                                            final HttpModelType httpModelType,
                                            final String modelName) {
        return unmodifiableOrderedSet(toStringList(list, httpModelType, modelName));
    }

    // -------------------------------------------------------------------------------------------------------------------------------------

    protected final void throwNotImplYet(final String message) {
        throw new UnsupportedOperationException(message);
    }
}
