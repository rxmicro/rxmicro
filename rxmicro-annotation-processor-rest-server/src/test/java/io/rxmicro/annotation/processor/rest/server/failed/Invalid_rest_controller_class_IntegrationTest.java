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

package io.rxmicro.annotation.processor.rest.server.failed;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import javax.tools.JavaFileObject;

import static com.google.testing.compile.CompilationSubject.assertThat;

/**
 * @author nedis
 * @link https://rxmicro.io
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
final class Invalid_rest_controller_class_IntegrationTest
        extends AbstractFailedRestServerAnnotationProcessorIntegrationTest {

    @ParameterizedTest
    @Order(1)
    @CsvSource({
            "interface,         Rest controller class must be a class! " +
                    "If this component is Rest client, that it must be annotated by '@io.rxmicro.rest.client.RestClient' annotation!",
            "@interface,        Rest controller class must be a class. " +
                    "Current element kind is: ANNOTATION_TYPE"
    })
    void Should_be_a_class(final String keyword,
                           final String message) {
        final JavaFileObject restController = JavaFileObjects.forSourceLines("simple.RestController",
                "package simple;",
                keyword + " RestController {",
                "   @io.rxmicro.rest.method.GET(\"/\")",
                "   void test();",
                "}"
        );
        final Compilation compilation = compile(restController, moduleInfo());
        assertThat(compilation).hadErrorCount(1);
        assertThat(compilation)
                .hadErrorContaining(
                        message
                )
                .inFile(restController)
                .onLine(2);
    }

    @Test
    @Order(2)
    void Should_not_be_an_enum() {
        final JavaFileObject restController = JavaFileObjects.forSourceLines("simple.RestController",
                "package simple;",
                "enum RestController {",
                "   TEST;",
                "   @io.rxmicro.rest.method.GET(\"/\")",
                "   void test(){}",
                "}"
        );
        final Compilation compilation = compile(restController, moduleInfo());
        assertThat(compilation).hadErrorCount(1);
        assertThat(compilation)
                .hadErrorContaining(
                        "Rest controller class must be a class. Current element kind is: ENUM"
                )
                .inFile(restController)
                .onLine(2);
    }

    @Test
    @Order(3)
    void Should_extend_Object() {
        final JavaFileObject restController = JavaFileObjects.forSourceLines("simple.RestController",
                "package simple;",
                "class RestController extends Throwable{",
                "   @io.rxmicro.rest.method.GET(\"/\")",
                "   void test(){}",
                "}"
        );
        final Compilation compilation = compile(restController, moduleInfo());
        assertThat(compilation).hadErrorCount(1);
        assertThat(compilation)
                .hadErrorContaining(
                        "Rest controller class must extend java.lang.Object class only"
                )
                .inFile(restController)
                .onLine(2);
    }

    @Test
    @Order(4)
    void Should_not_be_an_abstract_class() {
        final JavaFileObject restController = JavaFileObjects.forSourceLines("simple.RestController",
                "package simple;",
                "abstract class RestController {",
                "   @io.rxmicro.rest.method.GET(\"/\")",
                "   void test(){}",
                "}"
        );
        final Compilation compilation = compile(restController, moduleInfo());
        assertThat(compilation).hadErrorCount(1);
        assertThat(compilation)
                .hadErrorContaining(
                        "Rest controller class couldn't be an abstract class"
                )
                .inFile(restController)
                .onLine(2);
    }

    @Test
    @Order(5)
    void Should_not_be_a_nested_class() {
        final JavaFileObject restController = JavaFileObjects.forSourceLines("simple.RestController",
                "package simple;",
                "class Outer {",
                "static class RestController {",
                "   @io.rxmicro.rest.method.GET(\"/\")",
                "   void test(){}",
                "}",
                "}"
        );
        final Compilation compilation = compile(restController, moduleInfo());
        assertThat(compilation).hadErrorCount(1);
        assertThat(compilation)
                .hadErrorContaining(
                        "Rest controller class couldn't be a nested class"
                )
                .inFile(restController)
                .onLine(3);
    }

    @Test
    @Order(6)
    void Should_contain_an_accessible_constructor_without_parameters() {
        final JavaFileObject restController = JavaFileObjects.forSourceLines("simple.RestController",
                "package simple;",
                "class RestController {",
                "   private RestController(){}",
                "   @io.rxmicro.rest.method.GET(\"/\")",
                "   void test(){}",
                "}"
        );
        final Compilation compilation = compile(restController, moduleInfo());
        assertThat(compilation).hadErrorCount(1);
        assertThat(compilation)
                .hadErrorContaining(
                        "Class 'simple.RestController' must declare a public or protected or default constructor without parameters"
                )
                .inFile(restController)
                .onLine(2);
    }
}
