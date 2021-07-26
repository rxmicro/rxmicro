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

package io.rxmicro.data.mongo.internal;

import io.rxmicro.data.local.InvalidValueTypeException;
import io.rxmicro.data.mongo.internal.codec.CustomBinaryCodec;
import io.rxmicro.data.mongo.internal.converter.PatternConverter;
import org.bson.BSONException;
import org.bson.BsonRegularExpression;
import org.bson.types.Binary;
import org.bson.types.Code;
import org.bson.types.Decimal128;
import org.bson.types.ObjectId;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.regex.Pattern;

import static java.util.Collections.unmodifiableList;

/**
 * @author nedis
 * @since 0.1
 */
@SuppressWarnings({"ForLoopReplaceableByForEach", "unchecked", "SameParameterValue"})
public abstract class AbstractEntityMongoDBConverter {

    private static final PatternConverter PATTERN_CONVERTER = new PatternConverter();

    // -----------------------------------------------------------------------------------------------------------------

    protected final ObjectId toObjectId(final Object value,
                                        final String fieldName) {
        return toType(ObjectId.class, value, fieldName);
    }

    protected final List<ObjectId> toObjectIdList(final Object list,
                                                  final String fieldName) {
        return toList(ObjectId.class, list, fieldName);
    }

    // -----------------------------------------------------------------------------------------------------------------

    protected final Boolean toBoolean(final Object value,
                                      final String fieldName) {
        return toType(Boolean.class, value, fieldName);
    }

    protected final List<Boolean> toBooleanList(final Object list,
                                                final String fieldName) {
        return toList(Boolean.class, list, fieldName);
    }

    // -----------------------------------------------------------------------------------------------------------------

    protected final Byte toByte(final Object value,
                                final String fieldName) {
        final Integer integer = toType(Integer.class, value, fieldName);
        if (integer != null) {
            if (integer < Byte.MIN_VALUE || integer > Byte.MAX_VALUE) {
                throw new InvalidValueTypeException("'?' can not be converted into a Byte.", value);
            }
            return integer.byteValue();
        } else {
            return null;
        }
    }

    protected final List<Byte> toByteList(final Object list,
                                          final String fieldName) {
        return toList(list, fieldName, this::toByte);
    }

    // -----------------------------------------------------------------------------------------------------------------

    protected final Short toShort(final Object value,
                                  final String fieldName) {
        final Integer integer = toType(Integer.class, value, fieldName);
        if (integer != null) {
            if (integer < Short.MIN_VALUE || integer > Short.MAX_VALUE) {
                throw new InvalidValueTypeException("'?' can not be converted into a Byte.", value);
            }
            return integer.shortValue();
        } else {
            return null;
        }
    }

    protected final List<Short> toShortList(final Object list,
                                            final String fieldName) {
        return toList(list, fieldName, this::toShort);
    }

    // -----------------------------------------------------------------------------------------------------------------

    protected final Integer toInteger(final Object value,
                                      final String fieldName) {
        return toType(Integer.class, value, fieldName);
    }

    protected final List<Integer> toIntegerList(final Object list,
                                                final String fieldName) {
        return toList(Integer.class, list, fieldName);
    }

    // -----------------------------------------------------------------------------------------------------------------

    protected final Long toLong(final Object value,
                                final String fieldName) {
        return toType(Long.class, value, fieldName);
    }

    protected final List<Long> toLongList(final Object list,
                                          final String fieldName) {
        return toList(Long.class, list, fieldName);
    }

    // -----------------------------------------------------------------------------------------------------------------

    protected final Float toFloat(final Object value,
                                  final String fieldName) {
        final Double aDouble = toType(Double.class, value, fieldName);
        if (aDouble != null) {
            if (aDouble < -Float.MAX_VALUE || aDouble > Float.MAX_VALUE) {
                throw new InvalidValueTypeException("'?' can not be converted into a Float.", value);
            }
            return aDouble.floatValue();
        } else {
            return null;
        }
    }

    protected final List<Float> toFloatList(final Object list,
                                            final String fieldName) {
        return toList(list, fieldName, this::toFloat);
    }

    // -----------------------------------------------------------------------------------------------------------------

    protected final Double toDouble(final Object value,
                                    final String fieldName) {
        return toType(Double.class, value, fieldName);
    }

    protected final List<Double> toDoubleList(final Object list,
                                              final String fieldName) {
        return toList(Double.class, list, fieldName);
    }

    // -----------------------------------------------------------------------------------------------------------------

    protected final BigDecimal toBigDecimal(final Object value,
                                            final String fieldName) {
        if (value == null) {
            return null;
        } else if (isIntegerNumber(value)) {
            final Number number = toType(Number.class, value, fieldName);
            return BigDecimal.valueOf(number.longValue());
        } else if (isDoubleOrFloatNumber(value)) {
            final Number number = toType(Number.class, value, fieldName);
            return BigDecimal.valueOf(number.doubleValue());
        } else {
            return toType(Decimal128.class, value, fieldName).bigDecimalValue();
        }
    }

    private boolean isIntegerNumber(final Object value) {
        return value instanceof Integer || value instanceof Long;
    }

    private boolean isDoubleOrFloatNumber(final Object value) {
        return value instanceof Double || value instanceof Float;
    }

    protected final List<BigDecimal> toBigDecimalList(final Object list,
                                                      final String fieldName) {
        return toList(list, fieldName, this::toBigDecimal);
    }

    // -----------------------------------------------------------------------------------------------------------------

    protected final Character toCharacter(final Object value,
                                          final String fieldName) {
        final String string = toType(String.class, value, fieldName);
        if (string != null) {
            return string.charAt(0);
        } else {
            return null;
        }
    }

    protected final List<Character> toCharacterList(final Object list,
                                                    final String fieldName) {
        return toList(list, fieldName, this::toCharacter);
    }

    // -----------------------------------------------------------------------------------------------------------------

    protected final String toString(final Object value,
                                    final String fieldName) {
        return toType(String.class, value, fieldName);
    }

    protected final List<String> toStringList(final Object list,
                                              final String fieldName) {
        return toList(String.class, list, fieldName);
    }

    // -----------------------------------------------------------------------------------------------------------------

    protected final Pattern toPattern(final Object value,
                                      final String fieldName) {
        final BsonRegularExpression expression = toType(BsonRegularExpression.class, value, fieldName);
        if (expression != null) {
            return PATTERN_CONVERTER.apply(expression);
        } else {
            return null;
        }
    }

    protected final List<Pattern> toPatternList(final Object list,
                                                final String fieldName) {
        return toList(list, fieldName, this::toPattern);
    }

    // -----------------------------------------------------------------------------------------------------------------

    protected final Instant toInstant(final Object value,
                                      final String fieldName) {
        final Date date = toType(Date.class, value, fieldName);
        if (date != null) {
            return date.toInstant();
        } else {
            return null;
        }
    }

    protected final List<Instant> toInstantList(final Object list,
                                                final String fieldName) {
        return toList(list, fieldName, this::toInstant);
    }

    // -----------------------------------------------------------------------------------------------------------------

    protected final LocalDate toLocalDate(final Object value,
                                          final String fieldName) {
        final Instant instant = toInstant(value, fieldName);
        if (instant != null) {
            return instant
                    .atZone(ZoneOffset.UTC)
                    .toLocalDate();
        } else {
            return null;
        }
    }

    protected final List<LocalDate> toLocalDateList(final Object list,
                                                    final String fieldName) {
        return toList(list, fieldName, this::toLocalDate);
    }

    // -----------------------------------------------------------------------------------------------------------------

    protected final LocalDateTime toLocalDateTime(final Object value,
                                                  final String fieldName) {
        final Instant instant = toInstant(value, fieldName);
        if (instant != null) {
            return instant
                    .atZone(ZoneOffset.UTC)
                    .toLocalDateTime();
        } else {
            return null;
        }
    }

    protected final List<LocalDateTime> toLocalDateTimeList(final Object list,
                                                            final String fieldName) {
        return toList(list, fieldName, this::toLocalDateTime);
    }

    // -----------------------------------------------------------------------------------------------------------------

    protected final LocalTime toLocalTime(final Object value,
                                          final String fieldName) {
        final Instant instant = toInstant(value, fieldName);
        if (instant != null) {
            return instant
                    .atZone(ZoneOffset.UTC)
                    .toLocalTime();
        } else {
            return null;
        }
    }

    protected final List<LocalTime> toLocalTimeList(final Object list,
                                                    final String fieldName) {
        return toList(list, fieldName, this::toLocalTime);
    }

    // -----------------------------------------------------------------------------------------------------------------

    protected final UUID toUUID(final Object value,
                                final String fieldName) {
        if (value instanceof CustomBinaryCodec.UUIDBinary) {
            try {
                return ((CustomBinaryCodec.UUIDBinary) value).toUUID();
            } catch (final BSONException ex) {
                throw new InvalidValueTypeException(
                        "Invalid value for \"?\" field: ? (Error code: ?)",
                        fieldName, ex, ex.getMessage(), ex.getErrorCode()
                );
            }
        } else {
            return toType(UUID.class, value, fieldName);
        }
    }

    protected final List<UUID> toUUIDList(final Object list,
                                          final String fieldName) {
        return toList(list, fieldName, this::toUUID);
    }

    // -----------------------------------------------------------------------------------------------------------------

    protected final Code toCode(final Object value,
                                final String fieldName) {
        return toType(Code.class, value, fieldName);
    }

    protected final List<Code> toCodeList(final Object list,
                                          final String fieldName) {
        return toList(Code.class, list, fieldName);
    }

    // -----------------------------------------------------------------------------------------------------------------

    protected final Binary toBinary(final Object value,
                                    final String fieldName) {
        return toType(Binary.class, value, fieldName);
    }

    protected final List<Binary> toBinaryList(final Object list,
                                              final String fieldName) {
        return toList(list, fieldName, this::toBinary);
    }

    // -----------------------------------------------------------------------------------------------------------------

    @SuppressWarnings("unchecked")
    protected final <T> T toType(final Class<T> type,
                                 final Object value,
                                 final String fieldName) {
        if (value == null) {
            return null;
        } else {
            if (type.isAssignableFrom(value.getClass())) {
                return (T) value;
            } else {
                throw new InvalidValueTypeException(
                        "Invalid value for \"?\" field: Expected '?' type but actual is '?'!",
                        fieldName, type.getName(), value.getClass().getName()
                );
            }
        }
    }

    protected final <T> List<T> toList(final Class<T> type,
                                       final Object list,
                                       final String fieldName) {
        if (list != null) {
            final List<?> result = (List<?>) list;
            for (int i = 0; i < result.size(); i++) {
                if (result.get(i) != null && result.get(i).getClass() != type) {
                    throw new InvalidValueTypeException(
                            "Invalid value for \"?\" field: Expected a '?' array, but actual is '?'!",
                            fieldName, type.getSimpleName(), list
                    );
                }
            }
            return unmodifiableList((List<T>) result);
        } else {
            return List.of();
        }
    }

    protected final <T> List<T> toList(final Object list,
                                       final String fieldName,
                                       final BiFunction<Object, String, T> converter) {
        if (list != null) {
            final List<?> originalList = (List<?>) list;
            final List<T> resultList = new ArrayList<>(originalList.size());
            for (final Object item : originalList) {
                resultList.add(converter.apply(item, fieldName));
            }
            return unmodifiableList(resultList);
        } else {
            return List.of();
        }
    }

    protected final <T extends Enum<T>> T toEnum(final Class<T> type,
                                                 final Object value,
                                                 final String fieldName) {
        if (value == null) {
            return null;
        } else {
            try {
                return Enum.valueOf(type, value.toString());
            } catch (final IllegalArgumentException ignored) {
                throw new InvalidValueTypeException(
                        "Invalid value for \"?\" field: Expected one of the following '?' but actual is '?'!",
                        fieldName, Arrays.toString(type.getEnumConstants()), value.toString()
                );
            }
        }
    }

    protected final <T extends Enum<T>> List<T> toEnumList(final Class<T> type,
                                                           final Object value,
                                                           final String fieldName) {
        return toList(value, fieldName, (v, f) -> toEnum(type, v, f));
    }
}
