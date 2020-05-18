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

package io.rxmicro.json.internal.validator;

import static io.rxmicro.common.util.Strings.startsWith;
import static io.rxmicro.json.JsonNumber.INFINITY;
import static io.rxmicro.json.JsonNumber.NAN;
import static io.rxmicro.json.JsonNumber.NEGATIVE_INFINITY;
import static io.rxmicro.json.JsonNumber.NEGATIVE_NAN;
import static io.rxmicro.json.JsonNumber.POSITIVE_INFINITY;
import static io.rxmicro.json.JsonNumber.POSITIVE_NAN;

/**
 * @author nedis
 * @since 0.4
 */
public final class Validators {

    public static void validateNumber(final String number) {
        if (number.isEmpty() ||
                "+".equals(number) ||
                "-".equals(number) ||
                startsWith(number, 'e') ||
                startsWith(number, 'E')) {
            throw new NumberFormatException("Not a number: " + number);
        } else if (!isNan(number) &&
                !isPositiveInfinite(number) &&
                !isNegativeInfinite(number)) {
            validateUsualNumber(number);
        }
    }

    private static void validateUsualNumber(final String number) {
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

    private static boolean isNan(final String number) {
        return NAN.equals(number) || POSITIVE_NAN.equals(number) || NEGATIVE_NAN.equals(number);
    }

    private static boolean isPositiveInfinite(final String number) {
        return INFINITY.equals(number) || POSITIVE_INFINITY.equals(number);
    }

    private static boolean isNegativeInfinite(final String number) {
        return NEGATIVE_INFINITY.equals(number);
    }

    private Validators() {
    }
}
