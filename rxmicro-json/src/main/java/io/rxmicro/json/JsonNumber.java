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

import static io.rxmicro.common.util.Strings.startsWith;

/**
 * Java class which store json number value.
 * <p>
 * Json number is stored as Java string and parsed by demand
 *
 * @author nedis
 * @since 0.1
 * @see JsonTypes
 * @see JsonHelper
 * @see JsonException
 */
public final class JsonNumber extends Number {

    private static final String NAN = "NaN";

    private static final String POSITIVE_NAN = "+NaN";

    private static final String NEGATIVE_NAN = "-NaN";

    private static final String INFINITY = "Infinity";

    private static final String POSITIVE_INFINITY = "+Infinity";

    private static final String NEGATIVE_INFINITY = "-Infinity";

    private final String number;

    /**
     * Creates a new instance of {@link JsonNumber} type from the string representation of a number
     *
     * @param number the string representation of a number
     * @throws NumberFormatException if the specified string is not the string representation of a number
     */
    public JsonNumber(final String number) {
        this.number = validate(number);
    }

    private String validate(final String number) {
        if (isNan(number)) {
            return NAN;
        } else if (isPositiveInfinite(number)) {
            return POSITIVE_INFINITY;
        } else if (isNegativeInfinite(number)) {
            return NEGATIVE_INFINITY;
        } else if (number.isEmpty() ||
                "+".equals(number) || "-".equals(number) ||
                startsWith(number, 'e') || startsWith(number, 'E')) {
            throw new NumberFormatException("Not a number: " + number);
        } else {
            validateNumber(number);
        }
        return number;
    }

    private void validateNumber(final String number) {
        boolean foundPoint = false;
        boolean foundE = false;
        int index = startsWith(number, '+') || startsWith(number, '-') ? 1 : 0;
        while (index < number.length()) {
            final char ch = number.charAt(index);
            if (ch == '.') {
                if (foundPoint) {
                    if (foundE) {
                        throw new NumberFormatException(
                                "Exponent value must be an integer for scientific notation of the number: " + number);
                    } else {
                        throw new NumberFormatException(
                                "Multiple points: " + number);
                    }
                }
                foundPoint = true;
            } else if (ch == 'e' || ch == 'E') {
                if (foundE) {
                    throw new NumberFormatException(
                            "Multiple E delimiters: " + number);
                }
                foundE = true;
                // For verification of exponent value, i.e.
                // if scientific notation detected then point not allowed more
                foundPoint = true;
                if (index == number.length() - 1) {
                    throw new NumberFormatException(
                            "Missing exponent value for scientific notation of number: " + number);
                } else if (number.charAt(index + 1) == '-' || number.charAt(index + 1) == '+') {
                    index++;
                }
            } else if (ch < '0' || ch > '9') {
                throw new NumberFormatException(
                        "Not a number: " + number);
            }
            index++;
        }
    }

    private boolean isNan(final String number) {
        if (number.length() >= 2) {
            return number.charAt(0) == NAN.charAt(0) && NAN.equals(number) ||
                    number.charAt(0) == '+' && number.charAt(1) == POSITIVE_NAN.charAt(1) && POSITIVE_NAN.equals(number) ||
                    number.charAt(0) == '-' && number.charAt(1) == POSITIVE_NAN.charAt(1) && NEGATIVE_NAN.equals(number);
        }
        return false;
    }

    private boolean isPositiveInfinite(final String number) {
        if (number.length() >= 2) {
            return number.charAt(0) == INFINITY.charAt(0) && INFINITY.equals(number) ||
                    number.charAt(0) == '+' && number.charAt(1) == POSITIVE_INFINITY.charAt(1) && POSITIVE_INFINITY.equals(number);
        }
        return false;
    }

    private boolean isNegativeInfinite(final String number) {
        if (number.length() >= 2) {
            return number.charAt(0) == '-' && number.charAt(1) == NEGATIVE_INFINITY.charAt(1) && NEGATIVE_INFINITY.equals(number);
        }
        return false;
    }

    @Override
    public int intValue() {
        return Integer.parseInt(number);
    }

    @Override
    public long longValue() {
        return Long.parseLong(number);
    }

    @Override
    public float floatValue() {
        return Float.parseFloat(number);
    }

    @Override
    public double doubleValue() {
        return Double.parseDouble(number);
    }

    @Override
    public byte byteValue() {
        return Byte.parseByte(number);
    }

    @Override
    public short shortValue() {
        return Short.parseShort(number);
    }

    /**
     * Returns the value of the specified number as an {@link BigInteger}.
     *
     * @return the value of the specified number as an {@link BigInteger}.
     * @throws NumberFormatException If the string does not contain a parsable {@link BigInteger}.
     */
    public BigInteger bigIntegerValue() {
        return new BigInteger(number);
    }

    /**
     * Returns the value of the specified number as an {@link BigDecimal}.
     *
     * @return the value of the specified number as an {@link BigDecimal}.
     * @throws NumberFormatException If the string does not contain a parsable {@link BigInteger}.
     */
    public BigDecimal bigDecimalValue() {
        return new BigDecimal(number);
    }

    /**
     * Returns {@code true} if current number is {@code NaN}
     *
     * @return {@code true} if current number is {@code NaN}
     */
    public boolean isNaN() {
        return number.charAt(0) == NAN.charAt(0);
    }

    /**
     * Returns {@code true} if current number is {@code infinite}
     *
     * @return {@code true} if current number is {@code infinite}
     */
    public boolean isInfinite() {
        return isPositiveInfinite() || isNegativeInfinite();
    }

    /**
     * Returns {@code true} if current number is {@code positive infinite}
     *
     * @return {@code true} if current number is {@code positive infinite}
     */
    public boolean isPositiveInfinite() {
        return number.charAt(0) == INFINITY.charAt(0);
    }

    /**
     * Returns {@code true} if current number is {@code negative infinite}
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
