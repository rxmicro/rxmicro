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

package error.validation;

import io.rxmicro.rest.method.GET;
import io.rxmicro.validation.constraint.Enumeration;
import io.rxmicro.validation.constraint.MaxDouble;
import io.rxmicro.validation.constraint.MaxInt;
import io.rxmicro.validation.constraint.MaxNumber;
import io.rxmicro.validation.constraint.MinDouble;
import io.rxmicro.validation.constraint.MinInt;
import io.rxmicro.validation.constraint.MinNumber;
import io.rxmicro.validation.constraint.Pattern;
import io.rxmicro.validation.constraint.SubEnum;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author nedis
 *
 */
enum Color {

    RED,

    GREEN,

    BLUE
}

/**
 * @author nedis
 *
 */
final class InvalidConstraintAnnotationParameterRestController {

    @GET("/verification")
    void consume(final Request request) {
    }

}

/**
 * @author nedis
 *
 */
final class Request {

    @Enumeration({})
    String emptyEnum;

    @Enumeration({"", "hello", "1234"})
    Character notCharacterEnum;

    @Pattern(regexp = ")")
    String invalidPattern;

    @SubEnum
    Color missingIncludesAndExcludes;

    @SubEnum(include = "YELLOW", exclude = "ORANGE")
    Color invalidIncludesAndExcludes;

    @MinDouble(Double.MIN_VALUE)
    @MaxDouble(Double.MIN_VALUE)
    Float minFloat1;

    @MinDouble(Double.MAX_VALUE)
    @MaxDouble(Double.MAX_VALUE)
    Float maxFloat1;

    @MinInt(Long.MIN_VALUE)
    @MaxInt(Long.MIN_VALUE)
    Byte minByte1;

    @MinInt(Long.MAX_VALUE)
    @MaxInt(Long.MAX_VALUE)
    Byte maxByte1;

    @MinInt(Long.MIN_VALUE)
    @MaxInt(Long.MIN_VALUE)
    Short minShort1;

    @MinInt(Long.MAX_VALUE)
    @MaxInt(Long.MAX_VALUE)
    Short maxShort1;

    @MinInt(Long.MIN_VALUE)
    @MaxInt(Long.MIN_VALUE)
    Integer minInteger1;

    @MinInt(Long.MAX_VALUE)
    @MaxInt(Long.MAX_VALUE)
    Integer maxInteger1;

    @MinNumber("hello")
    @MaxNumber("hello")
    BigInteger bigInteger2;

    @MinNumber("hello")
    @MaxNumber("hello")
    Byte minByte2;

    @MinNumber("-9999999999999999999")
    @MaxNumber("-9999999999999999999")
    Byte minByte3;

    @MinNumber("9999999999999999999")
    @MaxNumber("9999999999999999999")
    Byte maxByte3;

    @MinNumber("hello")
    @MaxNumber("hello")
    Short minShort2;

    @MinNumber("-9999999999999999999")
    @MaxNumber("-9999999999999999999")
    Short minShort3;

    @MinNumber("9999999999999999999")
    @MaxNumber("9999999999999999999")
    Short maxShort3;

    @MinNumber("hello")
    @MaxNumber("hello")
    Integer minInteger2;

    @MinNumber("-9999999999999999999")
    @MaxNumber("-9999999999999999999")
    Integer minInteger3;

    @MinNumber("9999999999999999999")
    @MaxNumber("9999999999999999999")
    Integer maxInteger3;

    @MinNumber("hello")
    @MaxNumber("hello")
    Long minLong2;

    @MinNumber("-9999999999999999999")
    @MaxNumber("-9999999999999999999")
    Long minLong3;

    @MinNumber("9999999999999999999")
    @MaxNumber("9999999999999999999")
    Long maxLong3;

    @MinNumber("hello")
    @MaxNumber("hello")
    BigDecimal bigDecimal2;

    @MinNumber("hello")
    @MaxNumber("hello")
    Float minFloat2;

    @MinNumber("4.9E-324")
    @MaxNumber("4.9E-324")
    Float minFloat3;

    @MinNumber("1.8E308")
    @MaxNumber("1.8E308")
    Float maxFloat3;

}

// Error: Annotation '@Enumeration' has invalid parameter: Value couldn't be empty
// Line: 65


// Error: Annotation '@Enumeration' has invalid parameter: Expected character enum constant, but actual is ''
// Line: 68


// Error: Annotation '@Enumeration' has invalid parameter: Expected character enum constant, but actual is 'hello'
// Line: 68


// Error: Annotation '@Enumeration' has invalid parameter: Expected character enum constant, but actual is '1234'
// Line: 68


// Error: Annotation '@Pattern' has invalid parameter: Invalid regular expression: Unmatched closing ')'
// Line: 71


// Error: Annotation '@SubEnum' has invalid parameter: Expected include or exclude values
// Line: 74


// Error: Annotation '@SubEnum' has invalid parameter: Value 'ORANGE' is invalid enum constant. Allowed values: [RED, GREEN, BLUE]
// Line: 77


// Error: Annotation '@SubEnum' has invalid parameter: Value 'YELLOW' is invalid enum constant. Allowed values: [RED, GREEN, BLUE]
// Line: 77


// Error: Annotation '@MinDouble' has invalid parameter: Value out of range: Expected 1.4E-45 < 4.9E-324 < 3.4028235E38
// Line: 81


// Error: Annotation '@MaxDouble' has invalid parameter: Value out of range: Expected 1.4E-45 < 4.9E-324 < 3.4028235E38
// Line: 81


// Error: Annotation '@MinDouble' has invalid parameter: Value out of range: Expected 1.4E-45 < 1.7976931348623157E308 < 3.4028235E38
// Line: 85


// Error: Annotation '@MaxDouble' has invalid parameter: Value out of range: Expected 1.4E-45 < 1.7976931348623157E308 < 3.4028235E38
// Line: 85


// Error: Annotation '@MinInt' has invalid parameter: Value out of range: Expected -128 < -9223372036854775808 < 127
// Line: 89


// Error: Annotation '@MaxInt' has invalid parameter: Value out of range: Expected -128 < -9223372036854775808 < 127
// Line: 89


// Error: Annotation '@MinInt' has invalid parameter: Value out of range: Expected -128 < 9223372036854775807 < 127
// Line: 93


// Error: Annotation '@MaxInt' has invalid parameter: Value out of range: Expected -128 < 9223372036854775807 < 127
// Line: 93


// Error: Annotation '@MinInt' has invalid parameter: Value out of range: Expected -32768 < -9223372036854775808 < 32767
// Line: 97


// Error: Annotation '@MaxInt' has invalid parameter: Value out of range: Expected -32768 < -9223372036854775808 < 32767
// Line: 97


// Error: Annotation '@MinInt' has invalid parameter: Value out of range: Expected -32768 < 9223372036854775807 < 32767
// Line: 101


// Error: Annotation '@MaxInt' has invalid parameter: Value out of range: Expected -32768 < 9223372036854775807 < 32767
// Line: 101


// Error: Annotation '@MinInt' has invalid parameter: Value out of range: Expected -2147483648 < -9223372036854775808 < 2147483647
// Line: 105


// Error: Annotation '@MaxInt' has invalid parameter: Value out of range: Expected -2147483648 < -9223372036854775808 < 2147483647
// Line: 105


// Error: Annotation '@MinInt' has invalid parameter: Value out of range: Expected -2147483648 < 9223372036854775807 < 2147483647
// Line: 109


// Error: Annotation '@MaxInt' has invalid parameter: Value out of range: Expected -2147483648 < 9223372036854775807 < 2147483647
// Line: 109


// Error: Annotation '@MinNumber' has invalid parameter: Expected an integer number
// Line: 113


// Error: Annotation '@MaxNumber' has invalid parameter: Expected an integer number
// Line: 113


// Error: Annotation '@MinNumber' has invalid parameter: Expected an integer number
// Line: 117


// Error: Annotation '@MaxNumber' has invalid parameter: Expected an integer number
// Line: 117


// Error: Annotation '@MinNumber' has invalid parameter: Value out of range: Expected -128 < -9999999999999999999 < 127
// Line: 121


// Error: Annotation '@MaxNumber' has invalid parameter: Value out of range: Expected -128 < -9999999999999999999 < 127
// Line: 121


// Error: Annotation '@MinNumber' has invalid parameter: Value out of range: Expected -128 < 9999999999999999999 < 127
// Line: 125


// Error: Annotation '@MaxNumber' has invalid parameter: Value out of range: Expected -128 < 9999999999999999999 < 127
// Line: 125


// Error: Annotation '@MinNumber' has invalid parameter: Expected an integer number
// Line: 129


// Error: Annotation '@MaxNumber' has invalid parameter: Expected an integer number
// Line: 129


// Error: Annotation '@MinNumber' has invalid parameter: Value out of range: Expected -32768 < -9999999999999999999 < 32767
// Line: 133


// Error: Annotation '@MaxNumber' has invalid parameter: Value out of range: Expected -32768 < -9999999999999999999 < 32767
// Line: 133


// Error: Annotation '@MinNumber' has invalid parameter: Value out of range: Expected -32768 < 9999999999999999999 < 32767
// Line: 137


// Error: Annotation '@MaxNumber' has invalid parameter: Value out of range: Expected -32768 < 9999999999999999999 < 32767
// Line: 137


// Error: Annotation '@MinNumber' has invalid parameter: Expected an integer number
// Line: 141


// Error: Annotation '@MaxNumber' has invalid parameter: Expected an integer number
// Line: 141


// Error: Annotation '@MinNumber' has invalid parameter: Value out of range: Expected -2147483648 < -9999999999999999999 < 2147483647
// Line: 145


// Error: Annotation '@MaxNumber' has invalid parameter: Value out of range: Expected -2147483648 < -9999999999999999999 < 2147483647
// Line: 145


// Error: Annotation '@MinNumber' has invalid parameter: Value out of range: Expected -2147483648 < 9999999999999999999 < 2147483647
// Line: 149


// Error: Annotation '@MaxNumber' has invalid parameter: Value out of range: Expected -2147483648 < 9999999999999999999 < 2147483647
// Line: 149


// Error: Annotation '@MinNumber' has invalid parameter: Expected an integer number
// Line: 153


// Error: Annotation '@MaxNumber' has invalid parameter: Expected an integer number
// Line: 153


// Error: Annotation '@MinNumber' has invalid parameter:
//        Value out of range: Expected -9223372036854775808 < -9999999999999999999 < 9223372036854775807
// Line: 157


// Error: Annotation '@MaxNumber' has invalid parameter:
//        Value out of range: Expected -9223372036854775808 < -9999999999999999999 < 9223372036854775807
// Line: 157


// Error: Annotation '@MinNumber' has invalid parameter:
//        Value out of range: Expected -9223372036854775808 < 9999999999999999999 < 9223372036854775807
// Line: 161


// Error: Annotation '@MaxNumber' has invalid parameter:
//        Value out of range: Expected -9223372036854775808 < 9999999999999999999 < 9223372036854775807
// Line: 161


// Error: Annotation '@MinNumber' has invalid parameter: Expected a number
// Line: 165


// Error: Annotation '@MaxNumber' has invalid parameter: Expected a number
// Line: 165


// Error: Annotation '@MinNumber' has invalid parameter: Expected a number
// Line: 169


// Error: Annotation '@MaxNumber' has invalid parameter: Expected a number
// Line: 169


// Error: Annotation '@MinNumber' has invalid parameter: Value out of range: Expected 1.4E-45 < 4.9E-324 < 3.4028235E38
// Line: 173


// Error: Annotation '@MaxNumber' has invalid parameter: Value out of range: Expected 1.4E-45 < 4.9E-324 < 3.4028235E38
// Line: 173


// Error: Annotation '@MinNumber' has invalid parameter: Value out of range: Expected 1.4E-45 < 1.8E308 < 3.4028235E38
// Line: 177


// Error: Annotation '@MaxNumber' has invalid parameter: Value out of range: Expected 1.4E-45 < 1.8E308 < 3.4028235E38
// Line: 177
