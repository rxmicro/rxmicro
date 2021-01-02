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

package io.rxmicro.test.dbunit.junit.internal;

import io.rxmicro.test.dbunit.junit.DbUnitTest;
import io.rxmicro.test.local.InvalidTestConfigException;
import io.rxmicro.test.local.model.TestModel;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author nedis
 * @since 0.7.3
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
final class DbUnitRxMicroTestExtensionTest {

    private final DbUnitRxMicroTestExtension testExtension = new DbUnitRxMicroTestExtension();

    @Test
    @Order(1)
    void Should_throw_InvalidTestConfigException_if_ExtendWith_is_used_instead_of_DbUnitTest() {
        final TestModel.Builder builder = new TestModel.Builder(TestClass1.class);

        final InvalidTestConfigException exception = assertThrows(InvalidTestConfigException.class, () ->
                testExtension.validate(builder.build(), Set.of()));
        assertEquals(
                "Use '@io.rxmicro.test.dbunit.junit.DbUnitTest' annotation instead of " +
                        "@ExtendWith(DbUnitTestExtension.class)!",
                exception.getMessage()
        );
    }

    @Test
    @Order(2)
    void Should_throw_InvalidTestConfigException_if_DbUnitTest_before_Testcontainers() {
        final TestModel.Builder builder = new TestModel.Builder(TestClass2.class);

        final InvalidTestConfigException exception = assertThrows(InvalidTestConfigException.class, () ->
                testExtension.validate(builder.build(), Set.of()));
        assertEquals(
                "'@io.rxmicro.test.dbunit.junit.DbUnitTest' must be added before " +
                        "'@org.testcontainers.junit.jupiter.Testcontainers' annotation for class: " +
                        "'io.rxmicro.test.dbunit.junit.internal.DbUnitRxMicroTestExtensionTest$TestClass2'",
                exception.getMessage()
        );
    }

    @Test
    @Order(3)
    void Should_throw_InvalidTestConfigException_if_DbUnitTest_is_redundant_without_nested_class() {
        final TestModel.Builder builder = new TestModel.Builder(TestClass3.class);

        final InvalidTestConfigException exception = assertThrows(InvalidTestConfigException.class, () ->
                testExtension.validate(builder.build(), Set.of()));
        assertEquals(
                "It seems that '@io.rxmicro.test.dbunit.junit.DbUnitTest' is redundant annotation, because " +
                        "'io.rxmicro.test.dbunit.junit.internal.DbUnitRxMicroTestExtensionTest$TestClass3' test class " +
                        "does not contain any test methods annotated by " +
                        "'@InitialDataSet' or '@ExpectedDataSet' annotations! " +
                        "Remove the redundant annotation!",
                exception.getMessage()
        );
    }

    @Test
    @Order(4)
    void Should_throw_InvalidTestConfigException_if_DbUnitTest_is_redundant_with_nested_class() {
        final TestModel.Builder builder = new TestModel.Builder(TestClass4.class);

        final InvalidTestConfigException exception = assertThrows(InvalidTestConfigException.class, () ->
                testExtension.validate(builder.build(), Set.of()));
        assertEquals(
                "It seems that '@io.rxmicro.test.dbunit.junit.DbUnitTest' is redundant annotation, because " +
                        "'io.rxmicro.test.dbunit.junit.internal.DbUnitRxMicroTestExtensionTest$TestClass4' test class " +
                        "(or any it nested class(es)) does not contain any test methods annotated by " +
                        "'@InitialDataSet' or '@ExpectedDataSet' annotations! " +
                        "Remove the redundant annotation!",
                exception.getMessage()
        );
    }

    /**
     * @author nedis
     * @since 0.7.3
     */
    @ExtendWith(DbUnitTestExtension.class)
    private static final class TestClass1 {

    }

    /**
     * @author nedis
     * @since 0.7.3
     */
    @DbUnitTest
    @Testcontainers
    private static final class TestClass2 {

    }

    /**
     * @author nedis
     * @since 0.7.3
     */
    @DbUnitTest
    private static final class TestClass3 {

    }

    /**
     * @author nedis
     * @since 0.7.3
     */
    @DbUnitTest
    private static final class TestClass4 {

        @Nested
        private final class NestedClass {

        }
    }
}
