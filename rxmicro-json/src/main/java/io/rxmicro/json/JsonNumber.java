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

package io.rxmicro.json;

import java.math.BigDecimal;
import java.math.BigInteger;

import static io.rxmicro.json.internal.validator.Validators.validateNumber;

/**
 * Java class which store json number value.
 *
 * <p>
 * Json number is stored as Java string and parsed by demand.
 *
 * @author nedis
 * @see JsonTypes
 * @see JsonHelper
 * @see JsonException
 * @since 0.1
 */
public final class JsonNumber {

    /**
     * A constant holding a Not-a-Number (NaN) value.
     */
    public static final String NAN = "NaN";

    /**
     * A constant holding a Not-a-Number (NaN) positive value.
     */
    public static final String POSITIVE_NAN = "+NaN";

    /**
     * A constant holding a Not-a-Number (NaN) negative value.
     */
    public static final String NEGATIVE_NAN = "-NaN";

    /**
     * A constant holding the infinity.
     */
    public static final String INFINITY = "Infinity";

    /**
     * A constant holding the positive infinity.
     */
    public static final String POSITIVE_INFINITY = "+Infinity";

    /**
     * A constant holding the negative infinity.
     */
    public static final String NEGATIVE_INFINITY = "-Infinity";

    private final String number;

    /**
     * Creates a new instance of {@link JsonNumber} type from the string representation of a number.
     *
     * @param number the string representation of a number
     * @throws NumberFormatException if the specified string is not the string representation of a number
     */
    public JsonNumber(final String number) {
        validateNumber(number);
        this.number = number;
    }

    /**
     * Returns the value of the specified number as an {@code int}.
     *
     * @return  the numeric value represented by this object after conversion
     *          to type {@code int}.
     * @throws NumberFormatException If the string does not contain a parsable {@code int}..
     */
    public int intValueExact() {
        return Integer.parseInt(number);
    }

    /**
     * Returns the value of the specified number as a {@code long}.
     *
     * @return  the numeric value represented by this object after conversion
     *          to type {@code long}.
     * @throws NumberFormatException If the string does not contain a parsable {@code long}.
     */
    public long longValueExact() {
        return Long.parseLong(number);
    }

    /**
     * Returns the value of the specified number as a {@code float}.
     *
     * @return  the numeric value represented by this object after conversion
     *          to type {@code float}.
     * @throws NumberFormatException If the string does not contain a parsable {@code float}.
     */
    public float floatValueExact() {
        return Float.parseFloat(number);
    }

    /**
     * Returns the value of the specified number as a {@code double}.
     *
     * @return  the numeric value represented by this object after conversion
     *          to type {@code double}.
     * @throws NumberFormatException If the string does not contain a parsable {@code double}.
     */
    public double doubleValueExact() {
        return Double.parseDouble(number);
    }

    /**
     * Returns the value of the specified number as a {@code byte}.
     *
     * @return  the numeric value represented by this object after conversion
     *          to type {@code byte}.
     * @throws NumberFormatException If the string does not contain a parsable {@code byte}.
     */
    public byte byteValueExact() {
        return Byte.parseByte(number);
    }

    /**
     * Returns the value of the specified number as a {@code short}.
     *
     * @return  the numeric value represented by this object after conversion
     *          to type {@code short}.
     * @throws NumberFormatException If the string does not contain a parsable {@code short}.
     */
    public short shortValueExact() {
        return Short.parseShort(number);
    }

    /**
     * Returns the value of the specified number as an {@link BigInteger}.
     *
     * @return the value of the specified number as an {@link BigInteger}.
     * @throws NumberFormatException If the string does not contain a parsable {@link BigInteger}.
     */
    public BigInteger bigIntegerValueExact() {
        return new BigInteger(number);
    }

    /**
     * Returns the value of the specified number as an {@link BigDecimal}.
     *
     * @return the value of the specified number as an {@link BigDecimal}.
     */
    public BigDecimal bigDecimalValueExact() {
        return new BigDecimal(number);
    }

    /**
     * Returns {@code true} if current number is {@code NaN}.
     *
     * @return {@code true} if current number is {@code NaN}
     */
    public boolean isNaN() {
        return number.charAt(0) == NAN.charAt(0);
    }

    /**
     * Returns {@code true} if current number is {@code infinite}.
     *
     * @return {@code true} if current number is {@code infinite}
     */
    public boolean isInfinite() {
        return isPositiveInfinite() || isNegativeInfinite();
    }

    /**
     * Returns {@code true} if current number is {@code positive infinite}.
     *
     * @return {@code true} if current number is {@code positive infinite}
     */
    public boolean isPositiveInfinite() {
        return number.charAt(0) == INFINITY.charAt(0);
    }

    /**
     * Returns {@code true} if current number is {@code negative infinite}.
     *
     * @return {@code true} if current number is {@code negative infinite}
     */
    public boolean isNegativeInfinite() {
        return number.length() >= 2 && number.charAt(0) == '-' && number.charAt(1) == NEGATIVE_INFINITY.charAt(1);
    }

    @Override
    public int hashCode() {
        return number.hashCode();
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        final JsonNumber that = (JsonNumber) other;
        return number.equals(that.number);
    }

    @Override
    public String toString() {
        return number;
    }
}
