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

package io.rxmicro.exchange.json.detail;

import io.rxmicro.http.error.ValidationException;
import io.rxmicro.json.JsonException;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static io.rxmicro.common.local.Examples.INSTANT_EXAMPLE;
import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.json.JsonHelper.toJsonString;
import static io.rxmicro.json.JsonTypes.asJsonArray;
import static io.rxmicro.json.JsonTypes.asJsonObject;
import static io.rxmicro.rest.model.HttpModelType.PARAMETER;
import static java.util.Collections.unmodifiableList;

/**
 * Used by generated code that was created by {@code RxMicro Annotation Processor}
 *
 * @author nedis
 * @since 0.1
 */
@SuppressWarnings({"unchecked", "ForLoopReplaceableByForEach"})
public abstract class ModelFromJsonConverter<T> {

    public final List<T> fromJsonArray(final Object body) {
        try {
            return fromJsonArray(asJsonArray(body));
        } catch (final JsonException ignore) {
            throw new ValidationException("Invalid http body: Expected a json array!");
        } catch (final ClassCastException ignore) {
            throw new ValidationException("Invalid http body: Expected a json array of json objects!");
        }
    }

    public final List<T> fromJsonArray(final List<Object> list,
                                       final String modelName) {
        try {
            return fromJsonArray(list);
        } catch (final ClassCastException ignore) {
            throw new ValidationException("Invalid ? \"?\": Expected an object array!", PARAMETER, modelName);
        }
    }

    private List<T> fromJsonArray(final List<Object> list) {
        final List<T> array = new ArrayList<>(list.size());
        for (int i = 0; i < list.size(); i++) {
            final Object item = list.get(i);
            if (item != null) {
                array.add(fromJsonObject((Map<String, Object>) item));
            } else {
                array.add(null);
            }
        }
        return unmodifiableList(array);
    }

    // ----------------------------------------------------------------------------------------------------------------

    public final T fromJsonObject(final Object body) {
        try {
            return fromJsonObject(asJsonObject(body));
        } catch (final JsonException ignore) {
            throw new ValidationException("Invalid http body: Expected a json object!");
        }
    }

    public T fromJsonObject(final Map<String, Object> jsonObject) {
        throw new AbstractMethodError("Annotation processor did not generate an implementation of this method!");
    }

    // ----------------------------------------------------------------------------------------------------------------

    protected final <E> E convertIfNotNull(final ModelFromJsonConverter<E> converter,
                                           final Map<String, Object> json) {
        return json != null ? converter.fromJsonObject(json) : null;
    }

    protected final <E> List<E> convertIfNotNull(final ModelFromJsonConverter<E> converter,
                                                 final List<Object> list,
                                                 final String modelName) {
        return list != null ? converter.fromJsonArray(list, modelName) : null;
    }

    // -----------------------------------------------------------------------------------------------------------------

    protected final <E extends Enum<E>> E toEnum(final Class<E> enumClass,
                                                 final Object value,
                                                 final String modelName) {
        if (value == null) {
            return null;
        } else if (value instanceof String) {
            try {
                return Enum.valueOf(enumClass, (String) value);
            } catch (final IllegalArgumentException ignore) {
                throw new ValidationException(
                        "Invalid ? \"?\": Expected a value from the set ?, but actual is ?!",
                        PARAMETER, modelName, Arrays.toString(enumClass.getEnumConstants()), getJsonActual(value)
                );
            }
        } else {
            throw new ValidationException(
                    "Invalid ? \"?\": Expected a string value, but actual is ?!",
                    PARAMETER, modelName, getJsonActual(value)
            );
        }
    }

    protected final <E extends Enum<E>> List<E> toEnumArray(final Class<E> enumClass,
                                                            final Object list,
                                                            final String modelName) {
        if (list != null) {
            if (list instanceof List<?>) {
                try {
                    final List<String> strings = (List<String>) list;
                    final List<E> result = new ArrayList<>(strings.size());
                    for (int i = 0; i < strings.size(); i++) {
                        final String value = strings.get(i);
                        result.add(toEnum(enumClass, value, modelName));
                    }
                    return unmodifiableList(result);
                } catch (final ClassCastException ignore) {
                    //goto throw new ValidationException
                }
            }
            throw new ValidationException(
                    "Invalid ? \"?\": Expected a string array, but actual is ?!",
                    PARAMETER, modelName, getJsonActual(list)
            );
        } else {
            return List.of();
        }
    }

    // -----------------------------------------------------------------------------------------------------------------

    protected final Boolean toBoolean(final Object value,
                                      final String modelName) {
        if (value == null) {
            return null;
        } else if (value instanceof Boolean) {
            return (Boolean) value;
        } else {
            throw new ValidationException(
                    "Invalid ? \"?\": Expected a boolean value, but actual is ?!",
                    PARAMETER, modelName, getJsonActual(value)
            );
        }
    }

    protected final List<Boolean> toBooleanArray(final Object list,
                                                 final String modelName) {
        if (list != null) {
            if (list instanceof List<?>) {
                try {
                    final List<Boolean> result = (List<Boolean>) list;
                    for (int i = 0; i < result.size(); i++) {
                        if (result.get(i) != null && result.get(i).getClass() != Boolean.class) {
                            throw new ClassCastException();
                        }
                    }
                    return unmodifiableList(result);
                } catch (final ClassCastException ignore) {
                    //goto throw new ValidationException
                }
            }
            throw new ValidationException(
                    "Invalid ? \"?\": Expected a boolean array, but actual is ?!",
                    PARAMETER, modelName, getJsonActual(list)
            );
        } else {
            return List.of();
        }
    }

    // -----------------------------------------------------------------------------------------------------------------

    protected final Byte toByte(final Object value,
                                final String modelName) {
        if (value == null) {
            return null;
        } else {
            if (value instanceof Number) {
                try {
                    return ((Number) value).byteValue();
                } catch (final NumberFormatException ignore) {
                    //goto throw new ValidationException
                }
            }
            throw new ValidationException(
                    "Invalid ? \"?\": Expected a byte value, but actual is ?!",
                    PARAMETER, modelName, getJsonActual(value)
            );
        }
    }

    protected final List<Byte> toByteArray(final Object list,
                                           final String modelName) {
        if (list != null) {
            if (list instanceof List<?>) {
                try {
                    final List<Number> numbers = (List<Number>) list;
                    final List<Byte> result = new ArrayList<>(numbers.size());
                    for (int i = 0; i < numbers.size(); i++) {
                        final Number value = numbers.get(i);
                        result.add(toByte(value, modelName));
                    }
                    return unmodifiableList(result);
                } catch (final ClassCastException ignore) {
                    //goto throw new ValidationException
                }
            }
            throw new ValidationException(
                    "Invalid ? \"?\": Expected a byte array, but actual is ?!",
                    PARAMETER, modelName, getJsonActual(list)
            );
        } else {
            return List.of();
        }
    }

    // -----------------------------------------------------------------------------------------------------------------

    protected final Short toShort(final Object value,
                                  final String modelName) {
        if (value == null) {
            return null;
        } else {
            if (value instanceof Number) {
                try {
                    return ((Number) value).shortValue();
                } catch (final NumberFormatException ignore) {
                    //goto throw new ValidationException
                }
            }
            throw new ValidationException(
                    "Invalid ? \"?\": Expected a short value, but actual is ?!",
                    PARAMETER, modelName, getJsonActual(value)
            );
        }
    }

    protected final List<Short> toShortArray(final Object list,
                                             final String modelName) {
        if (list != null) {
            if (list instanceof List<?>) {
                try {
                    final List<Number> numbers = (List<Number>) list;
                    final List<Short> result = new ArrayList<>(numbers.size());
                    for (int i = 0; i < numbers.size(); i++) {
                        final Number value = numbers.get(i);
                        result.add(toShort(value, modelName));
                    }
                    return unmodifiableList(result);
                } catch (final ClassCastException ignore) {
                    //goto throw new ValidationException
                }
            }
            throw new ValidationException(
                    "Invalid ? \"?\": Expected a short array, but actual is ?!",
                    PARAMETER, modelName, getJsonActual(list)
            );
        } else {
            return List.of();
        }
    }

    // -----------------------------------------------------------------------------------------------------------------

    protected final Integer toInteger(final Object value,
                                      final String modelName) {
        if (value == null) {
            return null;
        } else {
            if (value instanceof Number) {
                try {
                    return ((Number) value).intValue();
                } catch (final NumberFormatException ignore) {
                    //goto throw new ValidationException
                }
            }
            throw new ValidationException(
                    "Invalid ? \"?\": Expected a integer value, but actual is ?!",
                    PARAMETER, modelName, getJsonActual(value)
            );
        }
    }

    protected final List<Integer> toIntegerArray(final Object list,
                                                 final String modelName) {
        if (list != null) {
            if (list instanceof List<?>) {
                try {
                    final List<Number> numbers = (List<Number>) list;
                    final List<Integer> result = new ArrayList<>(numbers.size());
                    for (int i = 0; i < numbers.size(); i++) {
                        final Number value = numbers.get(i);
                        result.add(toInteger(value, modelName));
                    }
                    return unmodifiableList(result);
                } catch (final ClassCastException ignore) {
                    //goto throw new ValidationException
                }
            }
            throw new ValidationException(
                    "Invalid ? \"?\": Expected an integer array, but actual is ?!",
                    PARAMETER, modelName, getJsonActual(list)
            );
        } else {
            return List.of();
        }
    }

    // -----------------------------------------------------------------------------------------------------------------

    protected final Long toLong(final Object value,
                                final String modelName) {
        if (value == null) {
            return null;
        } else {
            if (value instanceof Number) {
                try {
                    return ((Number) value).longValue();
                } catch (final NumberFormatException ignore) {
                    //goto throw new ValidationException
                }
            }
            throw new ValidationException(
                    "Invalid ? \"?\": Expected a long value, but actual is ?!",
                    PARAMETER, modelName, getJsonActual(value)
            );
        }
    }

    protected final List<Long> toLongArray(final Object list,
                                           final String modelName) {
        if (list != null) {
            if (list instanceof List<?>) {
                try {
                    final List<Number> numbers = (List<Number>) list;
                    final List<Long> result = new ArrayList<>(numbers.size());
                    for (int i = 0; i < numbers.size(); i++) {
                        final Number value = numbers.get(i);
                        result.add(toLong(value, modelName));
                    }
                    return unmodifiableList(result);
                } catch (final ClassCastException ignore) {
                    //goto throw new ValidationException
                }
            }
            throw new ValidationException(
                    "Invalid ? \"?\": Expected a long array, but actual is ?!",
                    PARAMETER, modelName, getJsonActual(list)
            );
        } else {
            return List.of();
        }
    }

    // -----------------------------------------------------------------------------------------------------------------

    protected final Character toCharacter(final Object value,
                                          final String modelName) {
        if (value == null) {
            return null;
        } else {
            try {
                final String string = (String) value;
                if (string.length() == 1) {
                    return string.charAt(0);
                }
            } catch (final ClassCastException ignore) {
                //goto throw new ValidationException
            }
            throw new ValidationException(
                    "Invalid ? \"?\": Expected a character, but actual is ?!",
                    PARAMETER, modelName, getJsonActual(value)
            );
        }
    }

    protected final List<Character> toCharacterArray(final Object list,
                                                     final String modelName) {
        if (list != null) {
            if (list instanceof List<?>) {
                try {
                    final List<String> numbers = (List<String>) list;
                    final List<Character> result = new ArrayList<>(numbers.size());
                    for (int i = 0; i < numbers.size(); i++) {
                        final String value = numbers.get(i);
                        result.add(toCharacter(value, modelName));
                    }
                    return unmodifiableList(result);
                } catch (final ClassCastException ignore) {
                    //goto throw new ValidationException
                }
            }
            throw new ValidationException(
                    "Invalid ? \"?\": Expected a character array, but actual is ?!",
                    PARAMETER, modelName, getJsonActual(list)
            );
        } else {
            return List.of();
        }
    }

    // -----------------------------------------------------------------------------------------------------------------

    protected final Float toFloat(final Object value,
                                  final String modelName) {
        if (value == null) {
            return null;
        } else {
            if (value instanceof Number) {
                try {
                    return ((Number) value).floatValue();
                } catch (final NumberFormatException ignore) {
                    //goto throw new ValidationException
                }
            }
            throw new ValidationException(
                    "Invalid ? \"?\": Expected a float value, but actual is ?!",
                    PARAMETER, modelName, getJsonActual(value)
            );
        }
    }

    protected final List<Float> toFloatArray(final Object list,
                                             final String modelName) {
        if (list != null) {
            if (list instanceof List<?>) {
                try {
                    final List<Number> numbers = (List<Number>) list;
                    final List<Float> result = new ArrayList<>(numbers.size());
                    for (int i = 0; i < numbers.size(); i++) {
                        final Number value = numbers.get(i);
                        result.add(toFloat(value, modelName));
                    }
                    return unmodifiableList(result);
                } catch (final ClassCastException ignore) {
                    //goto throw new ValidationException
                }
            }
            throw new ValidationException(
                    "Invalid ? \"?\": Expected a float array, but actual is ?!",
                    PARAMETER, modelName, getJsonActual(list)
            );
        } else {
            return List.of();
        }
    }

    // -----------------------------------------------------------------------------------------------------------------

    protected final Double toDouble(final Object value,
                                    final String modelName) {
        if (value == null) {
            return null;
        } else {
            if (value instanceof Number) {
                try {
                    return ((Number) value).doubleValue();
                } catch (final NumberFormatException ignore) {
                    //goto throw new ValidationException
                }
            }
            throw new ValidationException(
                    "Invalid ? \"?\": Expected a double value, but actual is ?!",
                    PARAMETER, modelName, getJsonActual(value)
            );
        }
    }

    protected final List<Double> toDoubleArray(final Object list,
                                               final String modelName) {
        if (list != null) {
            if (list instanceof List<?>) {
                try {
                    final List<Number> numbers = (List<Number>) list;
                    final List<Double> result = new ArrayList<>(numbers.size());
                    for (int i = 0; i < numbers.size(); i++) {
                        final Number value = numbers.get(i);
                        result.add(toDouble(value, modelName));
                    }
                    return unmodifiableList(result);
                } catch (final ClassCastException ignore) {
                    //goto throw new ValidationException
                }
            }
            throw new ValidationException(
                    "Invalid ? \"?\": Expected a double array, but actual is ?!",
                    PARAMETER, modelName, getJsonActual(list)
            );
        } else {
            return List.of();
        }
    }

    // -----------------------------------------------------------------------------------------------------------------

    protected final BigDecimal toBigDecimal(final Object value,
                                            final String modelName) {
        if (value == null) {
            return null;
        } else if (value instanceof Number) {
            return new BigDecimal(value.toString());
        } else {
            throw new ValidationException(
                    "Invalid ? \"?\": Expected a big decimal value, but actual is ?!",
                    PARAMETER, modelName, getJsonActual(value)
            );
        }
    }

    protected final List<BigDecimal> toBigDecimalArray(final Object list,
                                                       final String modelName) {
        if (list != null) {
            if (list instanceof List<?>) {
                try {
                    final List<Number> numbers = (List<Number>) list;
                    final List<BigDecimal> result = new ArrayList<>(numbers.size());
                    for (int i = 0; i < numbers.size(); i++) {
                        final Number value = numbers.get(i);
                        result.add(toBigDecimal(value, modelName));
                    }
                    return unmodifiableList(result);
                } catch (final ClassCastException ignore) {
                    //goto throw new ValidationException
                }
            }
            throw new ValidationException(
                    "Invalid ? \"?\": Expected a big decimal array, but actual is ?!",
                    PARAMETER, modelName, getJsonActual(list)
            );
        } else {
            return List.of();
        }
    }

    // -----------------------------------------------------------------------------------------------------------------

    protected final BigInteger toBigInteger(final Object value,
                                            final String modelName) {
        if (value == null) {
            return null;
        } else {
            if (value instanceof Number) {
                try {
                    return new BigInteger(value.toString());
                } catch (final NumberFormatException ignore) {
                    //goto throw new ValidationException
                }
            }
            throw new ValidationException(
                    "Invalid ? \"?\": Expected a big integer value, but actual is ?!",
                    PARAMETER, modelName, getJsonActual(value)
            );
        }
    }

    protected final List<BigInteger> toBigIntegerArray(final Object list,
                                                       final String modelName) {
        if (list != null) {
            if (list instanceof List<?>) {
                try {
                    final List<Number> numbers = (List<Number>) list;
                    final List<BigInteger> result = new ArrayList<>(numbers.size());
                    for (int i = 0; i < numbers.size(); i++) {
                        final Number value = numbers.get(i);
                        result.add(toBigInteger(value, modelName));
                    }
                    return unmodifiableList(result);
                } catch (final ClassCastException ignore) {
                    //goto throw new ValidationException
                }
            }
            throw new ValidationException(
                    "Invalid ? \"?\": Expected a big integer array, but actual is ?!",
                    PARAMETER, modelName, getJsonActual(list)
            );
        } else {
            return List.of();
        }
    }

    // -----------------------------------------------------------------------------------------------------------------

    protected final Instant toInstant(final Object value,
                                      final String modelName) {
        if (value == null) {
            return null;
        } else {
            try {
                return Instant.parse(value.toString());
            } catch (final DateTimeParseException ignore) {
                throw new ValidationException(
                        "Invalid ? \"?\": Expected an ISO-8601 instant " +
                                "(Example: '?'), but actual is ?!",
                        PARAMETER, modelName, INSTANT_EXAMPLE, getJsonActual(value)
                );
            }
        }
    }

    protected final List<Instant> toInstantArray(final Object list,
                                                 final String modelName) {
        if (list != null) {
            if (list instanceof List<?>) {
                try {
                    final List<String> numbers = (List<String>) list;
                    final List<Instant> result = new ArrayList<>(numbers.size());
                    for (final String number : numbers) {
                        result.add(toInstant(number, modelName));
                    }
                    return unmodifiableList(result);
                } catch (final ClassCastException ignore) {
                    //goto throw new ValidationException
                }
            }
            throw new ValidationException(
                    "Invalid ? \"?\": Expected an instant array, but actual is ?!",
                    PARAMETER, modelName, getJsonActual(list)
            );
        } else {
            return List.of();
        }
    }

    // -----------------------------------------------------------------------------------------------------------------

    protected final String toString(final Object value,
                                    final String modelName) {
        if (value == null) {
            return null;
        } else {
            if (value instanceof String) {
                return value.toString();
            }
            throw new ValidationException(
                    "Invalid ? \"?\": Expected a string value, but actual is ?!",
                    PARAMETER, modelName, getJsonActual(value)
            );
        }
    }

    protected final List<String> toStringArray(final Object list,
                                               final String modelName) {
        if (list != null) {
            if (list instanceof List<?>) {
                try {
                    final List<String> result = (List<String>) list;
                    for (int i = 0; i < result.size(); i++) {
                        if (result.get(i) != null && result.get(i).getClass() != String.class) {
                            throw new ClassCastException();
                        }
                    }
                    return unmodifiableList(result);
                } catch (final ClassCastException ignore) {
                    //goto throw new ValidationException
                }
            }
            throw new ValidationException(
                    "Invalid ? \"?\": Expected a string array, but actual is ?!",
                    PARAMETER, modelName, getJsonActual(list)
            );
        } else {
            return List.of();
        }
    }

    // -----------------------------------------------------------------------------------------------------------------

    private String getJsonActual(final Object object) {
        if (object instanceof String) {
            return format("string: '?'", object);
        } else if (object instanceof Number) {
            return format("number: ?", object);
        } else if (object instanceof Boolean) {
            return format("boolean: ?", object);
        } else if (object instanceof List) {
            return format("json array: ?", toJsonString(object, false));
        } else if (object instanceof Map) {
            return format("json object: ?", toJsonString(object, false));
        } else {
            return "null";
        }
    }
}
