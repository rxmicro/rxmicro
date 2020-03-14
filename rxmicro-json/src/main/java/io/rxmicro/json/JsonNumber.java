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

package io.rxmicro.json;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Java class which store json number value.
 * Json number is stored as Java string and parsed by demand
 *
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public final class JsonNumber extends Number {

    private static final String NAN = "NaN";

    private static final String POSITIVE_NAN = "+NaN";

    private static final String NEGATIVE_NAN = "-NaN";

    private static final String INFINITY = "Infinity";

    private static final String POSITIVE_INFINITY = "+Infinity";

    private static final String NEGATIVE_INFINITY = "-Infinity";

    private final String number;

    public JsonNumber(final String number) throws NumberFormatException {
        this.number = validate(number);
    }

    private String validate(final String number) throws NumberFormatException {
        if (isNan(number)) {
            return NAN;
        } else if (isPositiveInfinite(number)) {
            return POSITIVE_INFINITY;
        } else if (isNegativeInfinite(number)) {
            return NEGATIVE_INFINITY;
        } else if (number.isEmpty() ||
                "+".equals(number) || "-".equals(number) ||
                number.startsWith("e") || number.startsWith("E")) {
            throw new NumberFormatException("Not a number: " + number);
        } else {
            validateNumber(number);
        }
        return number;
    }

    private void validateNumber(final String number) {
        boolean foundPoint = false;
        boolean foundE = false;
        final int startIndex = number.startsWith("+") || number.startsWith("-") ? 1 : 0;
        for (int i = startIndex; i < number.length(); i++) {
            final char ch = number.charAt(i);
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
                if (i == number.length() - 1) {
                    throw new NumberFormatException(
                            "Missing exponent value for scientific notation of number: " + number);
                } else if (number.charAt(i + 1) == '-' || number.charAt(i + 1) == '+') {
                    i++;
                }
            } else if (ch < '0' || ch > '9') {
                throw new NumberFormatException(
                        "Not a number: " + number);
            }
        }
    }

    private boolean isNan(final String number) {
        if (number.length() >= 2) {
            return (number.charAt(0) == NAN.charAt(0) && NAN.equals(number)) ||
                    (number.charAt(0) == '+' && number.charAt(1) == POSITIVE_NAN.charAt(1)
                            && POSITIVE_NAN.equals(number)) ||
                    (number.charAt(0) == '-' && number.charAt(1) == POSITIVE_NAN.charAt(1)
                            && NEGATIVE_NAN.equals(number));
        }
        return false;
    }

    private boolean isPositiveInfinite(final String number) {
        if (number.length() >= 2) {
            return (number.charAt(0) == INFINITY.charAt(0) && INFINITY.equals(number)) ||
                    (number.charAt(0) == '+' && number.charAt(1) == POSITIVE_INFINITY.charAt(1)
                            && POSITIVE_INFINITY.equals(number));
        }
        return false;
    }

    private boolean isNegativeInfinite(final String number) {
        if (number.length() >= 2) {
            return (number.charAt(0) == '-' && number.charAt(1) == NEGATIVE_INFINITY.charAt(1)
                    && NEGATIVE_INFINITY.equals(number));
        }
        return false;
    }

    @Override
    public int intValue() throws NumberFormatException {
        return Integer.parseInt(number);
    }

    @Override
    public long longValue() throws NumberFormatException {
        return Long.parseLong(number);
    }

    @Override
    public float floatValue() throws NumberFormatException {
        return Float.parseFloat(number);
    }

    @Override
    public double doubleValue() throws NumberFormatException {
        return Double.parseDouble(number);
    }

    @Override
    public byte byteValue() throws NumberFormatException {
        return Byte.parseByte(number);
    }

    @Override
    public short shortValue() throws NumberFormatException {
        return Short.parseShort(number);
    }

    public BigInteger bigIntegerValue() throws NumberFormatException {
        return new BigInteger(number);
    }

    public BigDecimal bigDecimalValue() throws NumberFormatException {
        return new BigDecimal(number);
    }

    public boolean isNaN() {
        return number.charAt(0) == NAN.charAt(0);
    }

    public boolean isInfinite() {
        return isPositiveInfinite() || isNegativeInfinite();
    }

    public boolean isPositiveInfinite() {
        return number.charAt(0) == INFINITY.charAt(0);
    }

    public boolean isNegativeInfinite() {
        return number.length() >= 2 && number.charAt(0) == '-' && number.charAt(1) == NEGATIVE_INFINITY.charAt(1);
    }

    @Override
    public int hashCode() {
        return number.hashCode();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final JsonNumber that = (JsonNumber) o;
        return number.equals(that.number);
    }

    @Override
    public String toString() {
        return number;
    }
}
