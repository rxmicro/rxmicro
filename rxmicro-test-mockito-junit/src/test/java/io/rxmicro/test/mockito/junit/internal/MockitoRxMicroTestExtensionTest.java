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

package io.rxmicro.test.mockito.junit.internal;

import io.rxmicro.common.util.Reflections;
import io.rxmicro.test.junit.RxMicroIntegrationTest;
import io.rxmicro.test.local.InvalidTestConfigException;
import io.rxmicro.test.local.model.TestModel;
import io.rxmicro.test.mockito.junit.InitMocks;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author nedis
 * @since 0.7.3
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
final class MockitoRxMicroTestExtensionTest {

    private final MockitoRxMicroTestExtension testExtension = new MockitoRxMicroTestExtension();

    @Test
    @Order(1)
    void Should_throw_InvalidTestConfigException_if_InitMocks_annotation_not_found(){
        final TestModel.Builder builder = new TestModel.Builder(TestClass1.class);
        builder.addBeanComponent(Reflections.getDeclaredField(TestClass1.class, "bean"));

        final InvalidTestConfigException exception = assertThrows(InvalidTestConfigException.class, () ->
                testExtension.validate(builder.build(), Set.of()));
        assertEquals(
                "'io.rxmicro.test.mockito.junit.internal.MockitoRxMicroTestExtensionTest$TestClass1' " +
                        "class must be annotated by '@io.rxmicro.test.mockito.junit.InitMocks' annotation!",
                exception.getMessage()
        );
    }

    @Test
    @Order(2)
    void Should_throw_InvalidTestConfigException_if_InitMocks_after_RxMicroIntegrationTest(){
        final TestModel.Builder builder = new TestModel.Builder(TestClass2.class);
        builder.addBeanComponent(Reflections.getDeclaredField(TestClass2.class, "bean"));

        final InvalidTestConfigException exception = assertThrows(InvalidTestConfigException.class, () ->
                testExtension.validate(builder.build(), Set.of(RxMicroIntegrationTest.class)));
        assertEquals(
                "'@io.rxmicro.test.mockito.junit.InitMocks' must be added before '@io.rxmicro.test.junit.RxMicroIntegrationTest'" +
                        " annotation for class: 'io.rxmicro.test.mockito.junit.internal.MockitoRxMicroTestExtensionTest$TestClass2'",
                exception.getMessage()
        );
    }

    @Test
    @Order(3)
    void Should_throw_InvalidTestConfigException_if_ExtendWith_used_instead_of_InitMocks(){
        final TestModel.Builder builder = new TestModel.Builder(TestClass3.class);
        builder.addBeanComponent(Reflections.getDeclaredField(TestClass3.class, "bean"));

        final InvalidTestConfigException exception = assertThrows(InvalidTestConfigException.class, () ->
                testExtension.validate(builder.build(), Set.of()));
        assertEquals(
                "Use '@io.rxmicro.test.mockito.junit.InitMocks' annotation instead of '@ExtendWith(MockitoExtension.class)'!",
                exception.getMessage()
        );
    }

    /**
     * @author nedis
     * @since 0.7.3
     */
    private static final class TestClass1 {

        @Mock
        private Runnable bean;
    }

    /**
     * @author nedis
     * @since 0.7.3
     */
    @RxMicroIntegrationTest
    @InitMocks
    private static final class TestClass2 {

        @Mock
        private Runnable bean;
    }

    /**
     * @author nedis
     * @since 0.7.3
     */
    @ExtendWith(MockitoExtension.class)
    private static final class TestClass3 {

        @Mock
        private Runnable bean;
    }
}