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
import io.rxmicro.json.JsonNumber;
import io.rxmicro.rest.local.AbstractValidatedConverter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static io.rxmicro.common.local.Examples.INSTANT_EXAMPLE;
import static io.rxmicro.common.util.ExCollections.unmodifiableOrderedSet;
import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.json.JsonHelper.toJsonString;
import static io.rxmicro.json.JsonTypes.asJsonArray;
import static io.rxmicro.json.JsonTypes.asJsonObject;
import static io.rxmicro.rest.model.HttpModelType.PARAMETER;
import static java.util.Collections.unmodifiableList;

/**
 * Used by generated code that created by the {@code RxMicro Annotation Processor}.
 *
 * @author nedis
 * @hidden
 * @since 0.1
 */
@SuppressWarnings({"unchecked", "ForLoopReplaceableByForEach"})
public abstract class ModelFromJsonConverter<T> extends AbstractValidatedConverter {

    public final List<T> fromJsonArray(final Object body) {
        try {
            return fromJsonArray(asJsonArray(body));
        } catch (final ClassCastException | JsonException ignore) {
            throw new ValidationException("Invalid http body: Expected an array of json objects!");
        }
    }

    public final List<T> fromJsonArray(final Collection<Object> list,
                                       final String modelName) {
        try {
            return fromJsonArray(list);
        } catch (final ClassCastException ignore) {
            throw new ValidationException("Invalid ? \"?\": Expected an array of json objects!", PARAMETER, modelName);
        }
    }

    private List<T> fromJsonArray(final Collection<Object> list) {
        final List<T> array = new ArrayList<>(list.size());
        for (final Object item : list) {
            if (item != null) {
                array.add(fromJsonObject((Map<String, Object>) item));
            } else {
                array.add(null);
            }
        }
        return unmodifiableList(array);
    }

    // -------------------------------------------------------------------------------------------------------------------------------------

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

    // -------------------------------------------------------------------------------------------------------------------------------------

    protected final <E> E convertToObjectIfNotNull(final ModelFromJsonConverter<E> converter,
                                                   final Map<String, Object> json) {
        return json != null ? converter.fromJsonObject(json) : null;
    }

    protected final <E> List<E> convertToListIfNotNull(final ModelFromJsonConverter<E> converter,
                                                       final Collection<Object> list,
                                                       final String modelName) {
        return list != null ? converter.fromJsonArray(list, modelName) : null;
    }

    protected final <E> Set<E> convertToSetIfNotNull(final ModelFromJsonConverter<E> converter,
                                                     final Collection<Object> list,
                                                     final String modelName) {
        return list != null ? unmodifiableOrderedSet(converter.fromJsonArray(list, modelName)) : null;
    }

    // -------------------------------------------------------------------------------------------------------------------------------------

    protected final <E extends Enum<E>> E toEnum(final Class<E> enumClass,
                                                 final Object value,
                                                 final String modelName) {
        if (value == null) {
            return null;
        } else {
            try {
                return Enum.valueOf(enumClass, (String) value);
            } catch (final IllegalArgumentException ignore) {
                throw new ValidationException(
                        "Invalid ? \"?\": Expected a string value from the set ?, but actual is '?'!",
                        PARAMETER, modelName, Arrays.toString(enumClass.getEnumConstants()), value
                );
            } catch (final ClassCastException ignore) {
                throw createExpectedStringValidationException(value, modelName);
            }
        }
    }

    protected final <E extends Enum<E>> List<E> toEnumList(final Class<E> enumClass,
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
            throw createExpectedArrayOfStringsValidationException(list, modelName);
        } else {
            return List.of();
        }
    }

    protected final <E extends Enum<E>> Set<E> toEnumSet(final Class<E> enumClass,
                                                         final Object list,
                                                         final String modelName) {
        return unmodifiableOrderedSet(toEnumList(enumClass, list, modelName));
    }

    // -------------------------------------------------------------------------------------------------------------------------------------

    protected final Boolean toBoolean(final Object value,
                                      final String modelName) {
        if (value == null) {
            return null;
        } else {
            try {
                return (Boolean) value;
            } catch (final ClassCastException ignore) {
                throw new ValidationException(
                        "Invalid ? \"?\": Expected a boolean value, but actual is ?!",
                        PARAMETER, modelName, getJsonActual(value)
                );
            }
        }
    }

    protected final List<Boolean> toBooleanList(final Object list,
                                                final String modelName) {
        if (list != null) {
            if (list instanceof List<?>) {
                try {
                    final List<Boolean> result = (List<Boolean>) list;
                    boolean last = false;
                    for (int i = 0; i < result.size(); i++) {
                        last = result.get(i);
                    }
                    return unmodifiableList(result);
                } catch (final ClassCastException ignore) {
                    //goto throw new ValidationException
                }
            }
            throw new ValidationException(
                    "Invalid ? \"?\": Expected an array of booleans, but actual is ?!",
                    PARAMETER, modelName, getJsonActual(list)
            );
        } else {
            return List.of();
        }
    }

    protected final Set<Boolean> toBooleanSet(final Object list,
                                              final String modelName) {
        return unmodifiableOrderedSet(toBooleanList(list, modelName));
    }

    // -------------------------------------------------------------------------------------------------------------------------------------

    protected final Byte toByte(final Object value,
                                final String modelName) {
        if (value == null) {
            return null;
        } else {
            try {
                return ((JsonNumber) value).byteValueExact();
            } catch (final NumberFormatException ignore) {
                throw createExpectedIntegerValidationException(value.toString(), PARAMETER, modelName, Byte.MIN_VALUE, Byte.MAX_VALUE);
            } catch (final ClassCastException ignore) {
                throw createExpectedIntegerValidationException(value, modelName);
            }
        }
    }

    protected final List<Byte> toByteList(final Object list,
                                          final String modelName) {
        if (list != null) {
            if (list instanceof List<?>) {
                try {
                    final List<JsonNumber> numbers = (List<JsonNumber>) list;
                    final List<Byte> result = new ArrayList<>(numbers.size());
                    for (int i = 0; i < numbers.size(); i++) {
                        final JsonNumber value = numbers.get(i);
                        result.add(toByte(value, modelName));
                    }
                    return unmodifiableList(result);
                } catch (final ClassCastException ignore) {
                    //goto throw new ValidationException
                }
            }
            throw createExpectedArrayOfNumbersValidationException(list, modelName);
        } else {
            return List.of();
        }
    }

    protected final Set<Byte> toByteSet(final Object list,
                                        final String modelName) {
        return unmodifiableOrderedSet(toByteList(list, modelName));
    }

    // -------------------------------------------------------------------------------------------------------------------------------------

    protected final Short toShort(final Object value,
                                  final String modelName) {
        if (value == null) {
            return null;
        } else {
            try {
                return ((JsonNumber) value).shortValueExact();
            } catch (final NumberFormatException ignore) {
                throw createExpectedIntegerValidationException(value.toString(), PARAMETER, modelName, Short.MIN_VALUE, Short.MAX_VALUE);
            } catch (final ClassCastException ignore) {
                throw createExpectedIntegerValidationException(value, modelName);
            }
        }
    }

    protected final List<Short> toShortList(final Object list,
                                            final String modelName) {
        if (list != null) {
            if (list instanceof List<?>) {
                try {
                    final List<JsonNumber> numbers = (List<JsonNumber>) list;
                    final List<Short> result = new ArrayList<>(numbers.size());
                    for (int i = 0; i < numbers.size(); i++) {
                        final JsonNumber value = numbers.get(i);
                        result.add(toShort(value, modelName));
                    }
                    return unmodifiableList(result);
                } catch (final ClassCastException ignore) {
                    //goto throw new ValidationException
                }
            }
            throw createExpectedArrayOfNumbersValidationException(list, modelName);
        } else {
            return List.of();
        }
    }

    protected final Set<Short> toShortSet(final Object list,
                                          final String modelName) {
        return unmodifiableOrderedSet(toShortList(list, modelName));
    }

    // -------------------------------------------------------------------------------------------------------------------------------------

    protected final Integer toInteger(final Object val,
                                      final String modelName) {
        if (val == null) {
            return null;
        } else {
            try {
                return ((JsonNumber) val).intValueExact();
            } catch (final NumberFormatException ignore) {
                throw createExpectedIntegerValidationException(val.toString(), PARAMETER, modelName, Integer.MIN_VALUE, Integer.MAX_VALUE);
            } catch (final ClassCastException ignore) {
                throw createExpectedIntegerValidationException(val, modelName);
            }
        }
    }

    protected final List<Integer> toIntegerList(final Object list,
                                                final String modelName) {
        if (list != null) {
            if (list instanceof List<?>) {
                try {
                    final List<JsonNumber> numbers = (List<JsonNumber>) list;
                    final List<Integer> result = new ArrayList<>(numbers.size());
                    for (int i = 0; i < numbers.size(); i++) {
                        final JsonNumber value = numbers.get(i);
                        result.add(toInteger(value, modelName));
                    }
                    return unmodifiableList(result);
                } catch (final ClassCastException ignore) {
                    //goto throw new ValidationException
                }
            }
            throw createExpectedArrayOfNumbersValidationException(list, modelName);
        } else {
            return List.of();
        }
    }

    protected final Set<Integer> toIntegerSet(final Object list,
                                              final String modelName) {
        return unmodifiableOrderedSet(toIntegerList(list, modelName));
    }

    // -------------------------------------------------------------------------------------------------------------------------------------

    protected final Long toLong(final Object value,
                                final String modelName) {
        if (value == null) {
            return null;
        } else {
            try {
                return ((JsonNumber) value).longValueExact();
            } catch (final NumberFormatException ignore) {
                throw createExpectedIntegerValidationException(value.toString(), PARAMETER, modelName, Long.MIN_VALUE, Long.MAX_VALUE);
            } catch (final ClassCastException ignore) {
                throw createExpectedIntegerValidationException(value, modelName);
            }
        }
    }

    protected final List<Long> toLongList(final Object list,
                                          final String modelName) {
        if (list != null) {
            if (list instanceof List<?>) {
                try {
                    final List<JsonNumber> numbers = (List<JsonNumber>) list;
                    final List<Long> result = new ArrayList<>(numbers.size());
                    for (int i = 0; i < numbers.size(); i++) {
                        final JsonNumber value = numbers.get(i);
                        result.add(toLong(value, modelName));
                    }
                    return unmodifiableList(result);
                } catch (final ClassCastException ignore) {
                    //goto throw new ValidationException
                }
            }
            throw createExpectedArrayOfNumbersValidationException(list, modelName);
        } else {
            return List.of();
        }
    }

    protected final Set<Long> toLongSet(final Object list,
                                        final String modelName) {
        return unmodifiableOrderedSet(toLongList(list, modelName));
    }

    // -------------------------------------------------------------------------------------------------------------------------------------

    protected final BigInteger toBigInteger(final Object value,
                                            final String modelName) {
        if (value == null) {
            return null;
        } else {
            try {
                return ((JsonNumber) value).bigIntegerValueExact();
            } catch (final NumberFormatException ignore) {
                throw new ValidationException(
                        "Invalid ? \"?\": Expected an integer value, but actual is '?'!",
                        PARAMETER, modelName, value
                );
            } catch (final ClassCastException ignore) {
                throw createExpectedIntegerValidationException(value, modelName);
            }
        }
    }

    protected final List<BigInteger> toBigIntegerList(final Object list,
                                                      final String modelName) {
        if (list != null) {
            if (list instanceof List<?>) {
                try {
                    final List<JsonNumber> numbers = (List<JsonNumber>) list;
                    final List<BigInteger> result = new ArrayList<>(numbers.size());
                    for (int i = 0; i < numbers.size(); i++) {
                        final JsonNumber value = numbers.get(i);
                        result.add(toBigInteger(value, modelName));
                    }
                    return unmodifiableList(result);
                } catch (final ClassCastException ignore) {
                    //goto throw new ValidationException
                }
            }
            throw createExpectedArrayOfNumbersValidationException(list, modelName);
        } else {
            return List.of();
        }
    }

    protected final Set<BigInteger> toBigIntegerSet(final Object list,
                                                    final String modelName) {
        return unmodifiableOrderedSet(toBigIntegerList(list, modelName));
    }

    // -------------------------------------------------------------------------------------------------------------------------------------

    protected final Float toFloat(final Object value,
                                  final String modelName) {
        if (value == null) {
            return null;
        } else {
            try {
                return floatIfValid(value.toString(), ((JsonNumber) value).floatValueExact(), PARAMETER, modelName);
            } catch (final NumberFormatException | ClassCastException ex) {
                throw createExpectedDecimalValidationException(value, modelName, ex);
            }
        }
    }

    protected final List<Float> toFloatList(final Object list,
                                            final String modelName) {
        if (list != null) {
            if (list instanceof List<?>) {
                try {
                    final List<JsonNumber> numbers = (List<JsonNumber>) list;
                    final List<Float> result = new ArrayList<>(numbers.size());
                    for (int i = 0; i < numbers.size(); i++) {
                        final JsonNumber value = numbers.get(i);
                        result.add(toFloat(value, modelName));
                    }
                    return unmodifiableList(result);
                } catch (final ClassCastException ignore) {
                    //goto throw new ValidationException
                }
            }
            throw createExpectedArrayOfNumbersValidationException(list, modelName);
        } else {
            return List.of();
        }
    }

    protected final Set<Float> toFloatSet(final Object list,
                                          final String modelName) {
        return unmodifiableOrderedSet(toFloatList(list, modelName));
    }

    // -------------------------------------------------------------------------------------------------------------------------------------

    protected final Double toDouble(final Object value,
                                    final String modelName) {
        if (value == null) {
            return null;
        } else {
            try {
                return doubleIfValid(value.toString(), ((JsonNumber) value).doubleValueExact(), PARAMETER, modelName);
            } catch (final NumberFormatException | ClassCastException ex) {
                throw createExpectedDecimalValidationException(value, modelName, ex);
            }
        }
    }

    protected final List<Double> toDoubleList(final Object list,
                                              final String modelName) {
        if (list != null) {
            if (list instanceof List<?>) {
                try {
                    final List<JsonNumber> numbers = (List<JsonNumber>) list;
                    final List<Double> result = new ArrayList<>(numbers.size());
                    for (int i = 0; i < numbers.size(); i++) {
                        final JsonNumber value = numbers.get(i);
                        result.add(toDouble(value, modelName));
                    }
                    return unmodifiableList(result);
                } catch (final ClassCastException ignore) {
                    //goto throw new ValidationException
                }
            }
            throw createExpectedArrayOfNumbersValidationException(list, modelName);
        } else {
            return List.of();
        }
    }

    protected final Set<Double> toDoubleSet(final Object list,
                                            final String modelName) {
        return unmodifiableOrderedSet(toDoubleList(list, modelName));
    }

    // -------------------------------------------------------------------------------------------------------------------------------------

    protected final BigDecimal toBigDecimal(final Object value,
                                            final String modelName) {
        if (value == null) {
            return null;
        } else {
            try {
                return ((JsonNumber) value).bigDecimalValueExact();
            } catch (final NumberFormatException | ClassCastException ex) {
                throw createExpectedDecimalValidationException(value, modelName, ex);
            }
        }
    }

    protected final List<BigDecimal> toBigDecimalList(final Object list,
                                                      final String modelName) {
        if (list != null) {
            if (list instanceof List<?>) {
                try {
                    final List<JsonNumber> numbers = (List<JsonNumber>) list;
                    final List<BigDecimal> result = new ArrayList<>(numbers.size());
                    for (int i = 0; i < numbers.size(); i++) {
                        final JsonNumber value = numbers.get(i);
                        result.add(toBigDecimal(value, modelName));
                    }
                    return unmodifiableList(result);
                } catch (final ClassCastException ignore) {
                    //goto throw new ValidationException
                }
            }
            throw createExpectedArrayOfNumbersValidationException(list, modelName);
        } else {
            return List.of();
        }
    }

    protected final Set<BigDecimal> toBigDecimalSet(final Object list,
                                                    final String modelName) {
        return unmodifiableOrderedSet(toBigDecimalList(list, modelName));
    }

    // -------------------------------------------------------------------------------------------------------------------------------------

    protected final Instant toInstant(final Object value,
                                      final String modelName) {
        if (value == null) {
            return null;
        } else {
            try {
                return Instant.parse((String) value);
            } catch (final DateTimeParseException ignore) {
                throw new ValidationException(
                        "Invalid ? \"?\": Expected an ISO-8601 instant " +
                                "(Example: '?'), but actual is '?'!",
                        PARAMETER, modelName, INSTANT_EXAMPLE, value
                );
            } catch (final ClassCastException ignore) {
                throw createExpectedStringValidationException(value, modelName);
            }
        }
    }

    protected final List<Instant> toInstantList(final Object list,
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
            throw createExpectedArrayOfStringsValidationException(list, modelName);
        } else {
            return List.of();
        }
    }

    protected final Set<Instant> toInstantSet(final Object list,
                                              final String modelName) {
        return unmodifiableOrderedSet(toInstantList(list, modelName));
    }

    // -------------------------------------------------------------------------------------------------------------------------------------

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

    protected final List<Character> toCharacterList(final Object list,
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
                    "Invalid ? \"?\": Expected an array of characters, but actual is ?!",
                    PARAMETER, modelName, getJsonActual(list)
            );
        } else {
            return List.of();
        }
    }

    protected final Set<Character> toCharacterSet(final Object list,
                                                  final String modelName) {
        return unmodifiableOrderedSet(toCharacterList(list, modelName));
    }

    // -------------------------------------------------------------------------------------------------------------------------------------

    protected final String toString(final Object value,
                                    final String modelName) {
        if (value == null) {
            return null;
        } else {
            try {
                return (String) value;
            } catch (final ClassCastException ignore) {
                throw createExpectedStringValidationException(value, modelName);
            }
        }
    }

    protected final List<String> toStringList(final Object list,
                                              final String modelName) {
        if (list != null) {
            if (list instanceof List<?>) {
                try {
                    final List<String> result = (List<String>) list;
                    String last;
                    for (int i = 0; i < result.size(); i++) {
                        last = result.get(i);
                    }
                    return unmodifiableList(result);
                } catch (final ClassCastException ignore) {
                    //goto throw new ValidationException
                }
            }
            throw createExpectedArrayOfStringsValidationException(list, modelName);
        } else {
            return List.of();
        }
    }

    protected final Set<String> toStringSet(final Object list,
                                            final String modelName) {
        return unmodifiableOrderedSet(toStringList(list, modelName));
    }

    // -------------------------------------------------------------------------------------------------------------------------------------

    private String getJsonActual(final Object value) {
        if (value instanceof String) {
            return format("'?' (string)", value);
        } else if (value instanceof JsonNumber || value instanceof Number) {
            return format("'?' (number)", value);
        } else if (value instanceof Boolean) {
            return format("'?' (boolean)", value);
        } else if (value instanceof List) {
            return getActualJsonArray((List<?>) value);
        } else if (value instanceof Map) {
            return format("'?' (object)", toJsonString(value, false));
        } else {
            return "null";
        }
    }

    private String getActualJsonArray(final List<?> list) {
        final Set<String> types = new LinkedHashSet<>();
        for (final Object item : list) {
            if (item instanceof String) {
                types.add("strings");
            } else if (item instanceof JsonNumber) {
                types.add("numbers");
            } else if (item instanceof Boolean) {
                types.add("booleans");
            } else if (item instanceof List) {
                types.add("arrays");
            } else if (item instanceof Map) {
                types.add("objects");
            } else {
                types.add("nulls");
            }
        }
        if (!types.isEmpty()) {
            return format("'?' (array of ?)", toJsonString(list, false), String.join(", ", types));
        } else {
            return "'[]' (empty array)";
        }
    }

    private ValidationException createExpectedIntegerValidationException(final Object value,
                                                                         final String modelName) {
        return new ValidationException(
                "Invalid ? \"?\": Expected an integer value, but actual is ?!",
                PARAMETER, modelName, getJsonActual(value)
        );
    }

    private ValidationException createExpectedStringValidationException(final Object value,
                                                                        final String modelName) {
        return new ValidationException(
                "Invalid ? \"?\": Expected a string value, but actual is ?!",
                PARAMETER, modelName, getJsonActual(value)
        );
    }

    private ValidationException createExpectedDecimalValidationException(final Object value,
                                                                         final String modelName,
                                                                         final Exception exception) {
        if (exception.getClass() == NumberFormatException.class) {
            return new ValidationException(
                    "Invalid ? \"?\": Expected a decimal value, but actual is '?'!",
                    PARAMETER, modelName, value
            );
        } else {
            return new ValidationException(
                    "Invalid ? \"?\": Expected a decimal value, but actual is ?!",
                    PARAMETER, modelName, getJsonActual(value)
            );
        }
    }

    private ValidationException createExpectedArrayOfNumbersValidationException(final Object list,
                                                                                final String modelName) {
        return new ValidationException(
                "Invalid ? \"?\": Expected an array of numbers, but actual is ?!",
                PARAMETER, modelName, getJsonActual(list)
        );
    }

    private ValidationException createExpectedArrayOfStringsValidationException(final Object list,
                                                                                final String modelName) {
        return new ValidationException(
                "Invalid ? \"?\": Expected an array of strings, but actual is ?!",
                PARAMETER, modelName, getJsonActual(list)
        );
    }
}
