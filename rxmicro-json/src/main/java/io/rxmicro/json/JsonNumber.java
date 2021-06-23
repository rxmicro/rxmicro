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
 * @see BigDecimal
 * @since 0.1
 */
public final class JsonNumber implements Comparable<JsonNumber> {

    private final String number;

    /**
     * Creates a new instance of {@link JsonNumber} type from the string representation of a number.
     *
     * @param number the string representation of a number
     * @throws NumberFormatException if the specified string is not the string representation of a number
     */
    public JsonNumber(final String number) {
        JsonNumberValidators.validateJsonNumber(number);
        this.number = number;
    }

    /**
     * Returns the value of the specified number as an {@code int}.
     *
     * @return the numeric value represented by this object after conversion
     *          to type {@code int}.
     * @throws NumberFormatException If the string does not contain a parsable {@code int}..
     */
    public int intValueExact() {
        return Integer.parseInt(number);
    }

    /**
     * Returns the value of the specified number as a {@code long}.
     *
     * @return the numeric value represented by this object after conversion
     *          to type {@code long}.
     * @throws NumberFormatException If the string does not contain a parsable {@code long}.
     */
    public long longValueExact() {
        return Long.parseLong(number);
    }

    /**
     * Returns the value of the specified number as a {@code float}.
     *
     * @return the numeric value represented by this object after conversion
     *          to type {@code float}.
     * @throws NumberFormatException If the string does not contain a parsable {@code float}.
     */
    public float floatValueExact() {
        return Float.parseFloat(number);
    }

    /**
     * Returns the value of the specified number as a {@code double}.
     *
     * @return the numeric value represented by this object after conversion
     *          to type {@code double}.
     * @throws NumberFormatException If the string does not contain a parsable {@code double}.
     */
    public double doubleValueExact() {
        return Double.parseDouble(number);
    }

    /**
     * Returns the value of the specified number as a {@code byte}.
     *
     * @return the numeric value represented by this object after conversion
     *          to type {@code byte}.
     * @throws NumberFormatException If the string does not contain a parsable {@code byte}.
     */
    public byte byteValueExact() {
        return Byte.parseByte(number);
    }

    /**
     * Returns the value of the specified number as a {@code short}.
     *
     * @return the numeric value represented by this object after conversion
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

    @Override
    public int compareTo(final JsonNumber other) {
        return new BigDecimal(number).compareTo(new BigDecimal(other.number));
    }

    /**
     * This class contains validation logic for {@link JsonNumber} class.
     *
     * @author nedis
     * @since 0.7.3
     */
    private static final class JsonNumberValidators {

        static void validateJsonNumber(final String number) {
            if (number.isEmpty()) {
                throw new NumberFormatException("Empty string is not valid number!");
            }
            if (number.length() == 1) {
                final char ch = number.charAt(0);
                if (ch == '+' || ch == '-' || ch == 'e' || ch == 'E' || ch == '.') {
                    throw new NumberFormatException("Not a number: " + number);
                }
            }
            validateUsualNumber(number);
        }

        private static void validateUsualNumber(final String number) {
            boolean foundPoint = false;
            boolean foundE = false;
            boolean foundDigit = false;
            int index = number.charAt(0) == '+' || number.charAt(0) == '-' ? 1 : 0;
            while (index < number.length()) {
                final char ch = number.charAt(index);
                if (ch == '.') {
                    if (foundPoint) {
                        throw createNumberFormatException(number, foundE);
                    }
                    foundPoint = true;
                } else if (ch == 'e' || ch == 'E') {
                    if (foundE) {
                        throw new NumberFormatException("Multiple E delimiters: " + number);
                    }
                    if (!foundDigit) {
                        throw new NumberFormatException("Missing mantissa for the scientific notation: " + number);
                    }
                    if (index < number.length() - 1 && (number.charAt(index + 1) == '-' || number.charAt(index + 1) == '+')) {
                        index++;
                    }
                    if (index == number.length() - 1) {
                        throw new NumberFormatException("Missing exponent value for scientific notation of number: " + number);
                    }
                    foundE = true;
                    // For verification of exponent value, i.e. if scientific notation detected then point not allowed more
                    foundPoint = true;
                } else {
                    if ((ch < '0' || ch > '9') && !Character.isDigit(ch)) {
                        throw new NumberFormatException("Not a number: " + number);
                    }
                    foundDigit = true;
                }
                index++;
            }
        }

        private static NumberFormatException createNumberFormatException(final String number,
                                                                         final boolean foundE) {
            if (foundE) {
                return new NumberFormatException("Exponent value must be an integer for scientific notation of the number: " + number);
            } else {
                return new NumberFormatException("Multiple points: " + number);
            }
        }

        private JsonNumberValidators() {
        }
    }
}
