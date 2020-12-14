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

package io.rxmicro.runtime.detail;

import io.rxmicro.common.CheckedWrapperException;
import io.rxmicro.common.InvalidStateException;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Consumer;

import static io.rxmicro.runtime.detail.RuntimeReflections.getFieldValue;
import static io.rxmicro.runtime.detail.RuntimeReflections.invoke;
import static io.rxmicro.runtime.detail.RuntimeReflections.setFieldValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author nedis
 * @since 0.7.3
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
final class RuntimeReflectionsTest {

    @Test
    @Order(1)
    void setFieldValue_should_wrap_IllegalAccessException() {
        final CheckedWrapperException exception = assertThrows(CheckedWrapperException.class, () ->
                setFieldValue(new TestClass(), "value", "test", null));

        assertEquals(
                "java.lang.IllegalAccessException: class io.rxmicro.runtime.detail.RuntimeReflections " +
                        "(in module rxmicro.runtime) " +
                        "cannot access a member of class io.rxmicro.runtime.detail.RuntimeReflectionsTest$TestClass " +
                        "(in module rxmicro.runtime) " +
                        "with modifiers \"private\"",
                exception.getMessage()
        );
    }

    @Test
    @Order(2)
    void setFieldValue_should_throw_InvalidStateException_if_field_not_found() {
        final InvalidStateException exception = assertThrows(InvalidStateException.class, () ->
                setFieldValue(new TestClass(), "not_found", "test", null));

        assertEquals(
                "Field 'io.rxmicro.runtime.detail.RuntimeReflectionsTest$TestClass.not_found' is not defined",
                exception.getMessage()
        );
    }

    @Test
    @Order(3)
    void setFieldValue_should_throw_InvalidStateException_if_class_contains_field_duplicate() {
        final Consumer<Field> fieldConsumer = field -> field.setAccessible(true);
        final InvalidStateException exception = assertThrows(InvalidStateException.class, () ->
                setFieldValue(new Child(), "value", "test", fieldConsumer));

        assertEquals(
                "Class 'io.rxmicro.runtime.detail.RuntimeReflectionsTest$Child' has duplicate field with name: 'value'",
                exception.getMessage()
        );
    }

    @Test
    @Order(4)
    void getFieldValue_should_wrap_IllegalAccessException() {
        final Consumer<Field> fieldConsumer = field -> {
        };
        final CheckedWrapperException exception = assertThrows(CheckedWrapperException.class, () ->
                getFieldValue(new TestClass(), "value", fieldConsumer));

        assertEquals(
                "java.lang.IllegalAccessException: class io.rxmicro.runtime.detail.RuntimeReflections " +
                        "(in module rxmicro.runtime) " +
                        "cannot access a member of class io.rxmicro.runtime.detail.RuntimeReflectionsTest$TestClass " +
                        "(in module rxmicro.runtime) " +
                        "with modifiers \"private\"",
                exception.getMessage()
        );
    }

    @Test
    @Order(5)
    void getFieldValue_should_throw_InvalidStateException_if_field_not_found() {
        final Consumer<Field> fieldConsumer = field -> {
        };
        final InvalidStateException exception = assertThrows(InvalidStateException.class, () ->
                getFieldValue(new TestClass(), "not_found", fieldConsumer));

        assertEquals(
                "Field 'io.rxmicro.runtime.detail.RuntimeReflectionsTest$TestClass.not_found' is not defined",
                exception.getMessage()
        );
    }

    @Test
    @Order(6)
    void getFieldValue_should_throw_InvalidStateException_if_class_contains_field_duplicate() {
        final Consumer<Field> fieldConsumer = field -> field.setAccessible(true);
        final InvalidStateException exception = assertThrows(InvalidStateException.class, () ->
                getFieldValue(new Child(), "value", fieldConsumer));

        assertEquals(
                "Class 'io.rxmicro.runtime.detail.RuntimeReflectionsTest$Child' has duplicate field with name: 'value'",
                exception.getMessage()
        );
    }

    @Test
    @Order(7)
    void invoke_should_wrap_IllegalAccessException() {
        final CheckedWrapperException exception = assertThrows(CheckedWrapperException.class, () ->
                invoke(new TestClass(), "privateMethod", null));

        assertEquals(
                "java.lang.IllegalAccessException: class io.rxmicro.runtime.detail.RuntimeReflections " +
                        "(in module rxmicro.runtime) " +
                        "cannot access a member of class io.rxmicro.runtime.detail.RuntimeReflectionsTest$TestClass " +
                        "(in module rxmicro.runtime) " +
                        "with modifiers \"private\"",
                exception.getMessage()
        );
    }

    @Test
    @Order(8)
    void invoke_should_wrap_InvocationTargetException() {
        final Consumer<Method> methodConsumer = method -> {
        };
        final CheckedWrapperException exception = assertThrows(CheckedWrapperException.class, () ->
                invoke(TestClass.class, "methodThatThrowsException", methodConsumer));

        assertEquals(
                "java.lang.reflect.InvocationTargetException",
                exception.getMessage()
        );
        assertEquals(
                "RuntimeException",
                ((InvocationTargetException)exception.getCause()).getTargetException().getMessage()
        );
    }

    @Test
    @Order(9)
    void invoke_should_throw_InvalidStateException_if_method_not_found() {
        final Consumer<Method> methodConsumer = method -> {
        };
        final InvalidStateException exception = assertThrows(InvalidStateException.class, () ->
                invoke(Runnable.class, "notFound", methodConsumer));

        assertEquals(
                "Method 'java.lang.Runnable.notFound()' is not defined",
                exception.getMessage()
        );
    }

    /**
     * @author nedis
     * @since 0.7.3
     */
    @SuppressWarnings("FieldMayBeFinal")
    private static class TestClass {

        private String value = "init";

        private void privateMethod(){

        }

        public static void methodThatThrowsException(){
            throw new RuntimeException("RuntimeException");
        }
    }

    /**
     * @author nedis
     * @since 0.7.3
     */
    static class Child extends TestClass {

        static final int CONSTANT = 1;

        String value = "init";
    }
}