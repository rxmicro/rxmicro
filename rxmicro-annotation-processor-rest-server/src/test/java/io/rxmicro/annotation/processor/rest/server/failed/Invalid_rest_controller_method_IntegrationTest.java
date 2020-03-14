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

package io.rxmicro.annotation.processor.rest.server.failed;

import com.google.testing.compile.Compilation;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import io.rxmicro.rest.HeaderMappingStrategy;
import io.rxmicro.rest.ParameterMappingStrategy;
import io.rxmicro.validation.constraint.LatinAlphabetOnly;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import reactor.core.publisher.Mono;

import javax.tools.JavaFileObject;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static com.google.testing.compile.JavaFileObjects.forSourceLines;
import static io.rxmicro.annotation.processor.integration.test.ExternalModule.EXTERNAL_REACTIVE_STREAMS_MODULE;
import static io.rxmicro.annotation.processor.integration.test.ExternalModule.EXTERNAL_REACTOR_CORE_MODULE;
import static io.rxmicro.annotation.processor.integration.test.ExternalModule.EXTERNAL_RX_JAVA_3_MODULE;
import static io.rxmicro.common.RxMicroModule.RX_MICRO_VALIDATION_MODULE;
import static io.rxmicro.common.util.Formats.format;

/**
 * @author nedis
 * @link http://rxmicro.io
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
final class Invalid_rest_controller_method_IntegrationTest
        extends AbstractFailedRestServerAnnotationProcessorIntegrationTest {

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "private;               <default> or protected or public",
            "abstract;              not abstract",
            "static;                not static",
            "synchronized;          not synchronized",
            "native;                not native"
    })
    @Order(1)
    void Should_contain_valid_modifiers(final String modifier,
                                        final String expected) {
        final JavaFileObject restController = forSourceLines("simple.RestController",
                "package simple;",
                "class RestController{",
                "   @io.rxmicro.rest.method.GET(\"/\")",
                format("   ? void test(){}", modifier),
                "}"
        );
        final Compilation compilation = compile(restController, moduleInfo());
        assertThat(compilation).hadErrorCount(1);
        assertThat(compilation)
                .hadErrorContaining(
                        format("Annotation(s) '@io.rxmicro.rest.method.GET' " +
                                "couldn't be applied to the ? method. " +
                                "Only ? methods are supported", modifier, expected)
                )
                .inFile(restController)
                .onLine(4);
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "postConstruct;     Invalid rest controller method name. The name '?' is reserved for CDI module. Rename the method!"
    })
    @Order(2)
    void Should_have_a_valid_name(final String methodName,
                                  final String errorMessage) {
        final JavaFileObject restController = forSourceLines("simple.RestController",
                "package simple;",
                "class RestController{",
                "   @io.rxmicro.rest.method.GET(\"/\")",
                format("   void ?(){}", methodName),
                "}"
        );
        final Compilation compilation = compile(restController, moduleInfo());
        assertThat(compilation).hadErrorCount(1);
        assertThat(compilation)
                .hadErrorContaining(format(errorMessage, methodName))
                .inFile(restController)
                .onLine(4);
    }

    @ParameterizedTest
    @ValueSource(classes = {
            HeaderMappingStrategy.class,
            ParameterMappingStrategy.class,
            LatinAlphabetOnly.class
    })
    @Order(3)
    void Should_not_contain_any_redundant_annotations(final Class<?> annotationClass) {
        final JavaFileObject restController = forSourceLines("simple.RestController",
                "package simple;",
                "class RestController{",
                "   @io.rxmicro.rest.method.GET(\"/\")",
                format("   @?", annotationClass.getName()),
                "   void test(){}",
                "}"
        );
        final Compilation compilation = compile(restController, moduleInfo(RX_MICRO_VALIDATION_MODULE));
        assertThat(compilation).hadErrorCount(1);
        assertThat(compilation)
                .hadErrorContaining(format("Detected redundant annotation: '@?'.", annotationClass.getName()))
                .inFile(restController)
                .onLine(5);
    }

    @ParameterizedTest
    @Order(4)
    @CsvSource(delimiter = ';', value = {
            "void method(boolean a){};  Primitive parameter type is not allowed. Use Java boxed type instead of 'boolean' type!",
            "void method(byte a){};     Primitive parameter type is not allowed. Use Java boxed type instead of 'byte' type!",
            "void method(short a){};    Primitive parameter type is not allowed. Use Java boxed type instead of 'short' type!",
            "void method(int a){};      Primitive parameter type is not allowed. Use Java boxed type instead of 'int' type!",
            "void method(long a){};     Primitive parameter type is not allowed. Use Java boxed type instead of 'long' type!",
            "void method(float a){};    Primitive parameter type is not allowed. Use Java boxed type instead of 'float' type!",
            "void method(double a){};   Primitive parameter type is not allowed. Use Java boxed type instead of 'double' type!",
            "void method(char a){};     Primitive parameter type is not allowed. Use Java boxed type instead of 'char' type!"
    })
    void Should_not_contain_a_primitive_parameter(
            final String methodDeclaration,
            final String expectedError) {
        final JavaFileObject restController = forSourceLines("simple.RestController",
                "package simple;",
                "class RestController{",
                "   @io.rxmicro.rest.method.GET(\"/\")",
                methodDeclaration,
                "}"
        );
        final Compilation compilation = compile(restController, moduleInfo());
        assertThat(compilation).hadErrorCount(1);
        assertThat(compilation)
                .hadErrorContaining(
                        expectedError
                )
                .inFile(restController)
                .onLine(4);
    }

    @ParameterizedTest
    @Order(5)
    @ValueSource(strings = {
            "String method(){}",
            "Integer method(){}",
            "java.util.List<String> method(){}",
            "java.util.List<Integer> method(){}"
    })
    void Should_return_void_or_reactive_type(final String methodDeclaration) {
        final JavaFileObject restController = forSourceLines("simple.RestController",
                "package simple;",
                "class RestController{",
                "   @io.rxmicro.rest.method.GET(\"/\")",
                methodDeclaration,
                "}"
        );
        final Compilation compilation = compile(restController, moduleInfo());
        assertThat(compilation).hadErrorCount(1);
        assertThat(compilation)
                .hadErrorContaining("Invalid return type. " +
                        "Expected one of the following: [" +
                        "java.util.concurrent.CompletionStage, java.util.concurrent.CompletableFuture, " +
                        "reactor.core.publisher.Mono, " +
                        "io.reactivex.rxjava3.core.Maybe, io.reactivex.rxjava3.core.Single, io.reactivex.rxjava3.core.Completable" +
                        "]")
                .inFile(restController)
                .onLine(4);
    }

    @ParameterizedTest
    @Order(6)
    @ValueSource(classes = {
            CompletionStage.class,
            CompletableFuture.class,
            Mono.class,
            Maybe.class,
            Single.class
    })
    void Should_return_parametrized_type(final Class<?> returnType) {
        addExternalModule(EXTERNAL_REACTIVE_STREAMS_MODULE);
        addExternalModule(EXTERNAL_REACTOR_CORE_MODULE);
        addExternalModule(EXTERNAL_RX_JAVA_3_MODULE);

        final JavaFileObject restController = forSourceLines("simple.RestController",
                "package simple;",
                "class RestController{",
                "   @io.rxmicro.rest.method.GET(\"/\")",
                returnType.getName() + " method(){}",
                "}"
        );
        final Compilation compilation = compile(restController, moduleInfo());
        assertThat(compilation).hadErrorCount(1);
        assertThat(compilation)
                .hadErrorContaining(format(
                        "Invalid return type: Expected generic type with 1 parameter(s): ?",
                        returnType.getName()))
                .inFile(restController)
                .onLine(4);
    }
}
