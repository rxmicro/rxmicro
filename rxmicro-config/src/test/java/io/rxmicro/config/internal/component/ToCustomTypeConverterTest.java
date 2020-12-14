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

package io.rxmicro.config.internal.component;

import io.rxmicro.config.ConfigException;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author nedis
 * @since 0.7.3
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
final class ToCustomTypeConverterTest {

    private final ToCustomTypeConverter converter = new ToCustomTypeConverter();

    @ParameterizedTest
    @Order(1)
    @ValueSource(strings = {
            "test",
            "@",
            "@java.lang.Object"
    })
    void Should_ignore_to_custom_type_conversion(final String value) {
        final Optional<Object> result = converter.convertToCustomType(Object.class, value);
        assertEquals(Optional.empty(), result);
    }

    @ParameterizedTest
    @Order(2)
    @CsvSource(delimiter = ';', value = {
            "@test.NotFoundEnum:ENUM_CONSTANT;          Can't convert '@test.NotFoundEnum:ENUM_CONSTANT' to 'java.lang.Runnable', " +
                    "because 'test.NotFoundEnum' class not defined!",
            "@java.util.concurrent.TimeUnit:YEARS;      Can't convert '@java.util.concurrent.TimeUnit.YEARS' to 'java.lang.Runnable', " +
                    "because 'java.util.concurrent.TimeUnit' enum does not contain 'YEARS' enum constant!",

            "@test.TestClass:NOT_STATIC;                Can't convert '@test.TestClass.NOT_STATIC' to 'java.lang.Runnable', " +
                    "because 'NOT_STATIC' field declared at 'test.TestClass' class not static!",
            "@test.TestClass:PRIVATE;                   Can't convert '@test.TestClass.PRIVATE' to 'java.lang.Runnable', " +
                    "because 'PRIVATE' field declared at 'test.TestClass' class not public!",
            "@test.TestClass:PROTECTED;                 Can't convert '@test.TestClass.PROTECTED' to 'java.lang.Runnable', " +
                    "because 'PROTECTED' field declared at 'test.TestClass' class not public!",
            "@test.TestClass:DEFAULT;                   Can't convert '@test.TestClass.DEFAULT' to 'java.lang.Runnable', " +
                    "because 'DEFAULT' field declared at 'test.TestClass' class not public!",
            "@test.TestClass:NOT_FINAL;                 Can't convert '@test.TestClass.NOT_FINAL' to 'java.lang.Runnable', " +
                    "because 'NOT_FINAL' field declared at 'test.TestClass' class not final!",

            "@test.TestClass:NOT_FOUND;                 Can't convert '@test.TestClass.NOT_FOUND' to 'java.lang.Runnable', " +
                    "because 'test.TestClass' class does not contain 'NOT_FOUND' public static final field!",
            "@test.TestInterface:NOT_FOUND;             Can't convert '@test.TestInterface.NOT_FOUND' to 'java.lang.Runnable', " +
                    "because 'test.TestInterface' interface does not contain 'NOT_FOUND' public static final field!",
            "@test.TestAnnotation:NOT_FOUND;            Can't convert '@test.TestAnnotation.NOT_FOUND' to 'java.lang.Runnable', " +
                    "because 'test.TestAnnotation' annotation does not contain 'NOT_FOUND' public static final field!"
    })
    void Should_throw_ConfigException(final String value,
                                      final String expectedMessage) {
        final ConfigException exception =
                assertThrows(ConfigException.class, () -> converter.convertToCustomType(Runnable.class, value));
        assertEquals(expectedMessage, exception.getMessage());
    }
}