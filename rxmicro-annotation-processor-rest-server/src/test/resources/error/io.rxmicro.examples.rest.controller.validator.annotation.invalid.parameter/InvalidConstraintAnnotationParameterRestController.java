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

package io.rxmicro.example.restcontroller.validator.annotation.invalid.parameter;

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
 * @link http://rxmicro.io
 */
enum Color {

    RED,

    GREEN,

    BLUE
}

/**
 * @author nedis
 * @link http://rxmicro.io
 */
final class InvalidConstraintAnnotationParameterRestController {

    @GET("/verification")
    void consume(final Request request) {
    }

}

/**
 * @author nedis
 * @link http://rxmicro.io
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