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

package io.rxmicro.test.junit.internal;

import io.rxmicro.test.local.InvalidTestConfigException;
import io.rxmicro.test.local.model.TestModel;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author nedis
 * @since 0.7.3
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
final class JUnitRxMicroTestExtensionTest {

    private final JUnitRxMicroTestExtension testExtension = new JUnitRxMicroTestExtension();

    @Test
    @Order(1)
    void Should_throw_InvalidTestConfigException_if_RxMicroComponentTestExtension_is_used() {
        final TestModel.Builder builder = new TestModel.Builder(TestClass1.class);

        final InvalidTestConfigException exception = assertThrows(InvalidTestConfigException.class, () ->
                testExtension.validate(builder.build(), Set.of()));
        assertEquals(
                "Use '@io.rxmicro.test.junit.RxMicroComponentTest' annotation instead of " +
                        "@ExtendWith(RxMicroComponentTestExtension.class)!",
                exception.getMessage()
        );
    }

    @Test
    @Order(2)
    void Should_throw_InvalidTestConfigException_if_RxMicroRestBasedMicroServiceTestExtension_is_used() {
        final TestModel.Builder builder = new TestModel.Builder(TestClass2.class);

        final InvalidTestConfigException exception = assertThrows(InvalidTestConfigException.class, () ->
                testExtension.validate(builder.build(), Set.of()));
        assertEquals(
                "Use '@io.rxmicro.test.junit.RxMicroRestBasedMicroServiceTest' annotation instead of " +
                        "@ExtendWith(RxMicroRestBasedMicroServiceTestExtension.class)!",
                exception.getMessage()
        );
    }

    @Test
    @Order(3)
    void Should_throw_InvalidTestConfigException_if_RxMicroIntegrationTestExtension_is_used() {
        final TestModel.Builder builder = new TestModel.Builder(TestClass3.class);

        final InvalidTestConfigException exception = assertThrows(InvalidTestConfigException.class, () ->
                testExtension.validate(builder.build(), Set.of()));
        assertEquals(
                "Use '@io.rxmicro.test.junit.RxMicroIntegrationTest' annotation instead of " +
                        "@ExtendWith(RxMicroIntegrationTestExtension.class)!",
                exception.getMessage()
        );
    }

    /**
     * @author nedis
     * @since 0.7.3
     */
    @ExtendWith(RxMicroComponentTestExtension.class)
    private static final class TestClass1 {

    }

    /**
     * @author nedis
     * @since 0.7.3
     */
    @ExtendWith(RxMicroRestBasedMicroServiceTestExtension.class)
    private static final class TestClass2 {

    }

    /**
     * @author nedis
     * @since 0.7.3
     */
    @ExtendWith(RxMicroIntegrationTestExtension.class)
    private static final class TestClass3 {

    }
}
