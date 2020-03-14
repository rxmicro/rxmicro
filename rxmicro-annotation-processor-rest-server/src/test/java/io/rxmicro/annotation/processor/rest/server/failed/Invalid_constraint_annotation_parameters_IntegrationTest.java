/*
 * Copyright 2019 http://rxmicro.io
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

import javax.tools.JavaFileObject;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static io.rxmicro.common.RxMicroModule.RX_MICRO_VALIDATION_MODULE;
import static io.rxmicro.common.util.Formats.format;

/**
 * @author nedis
 * @link http://rxmicro.io
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
final class Invalid_constraint_annotation_parameters_IntegrationTest extends
        AbstractFailedRestServerAnnotationProcessorIntegrationTest {

    @Test
    @Order(1)
    void Should_validate_constraint_annotation_values() {
        final String packageName = "io.rxmicro.examples.rest.controller.validator.annotation.invalid.parameter";
        final String restControllerClass = "InvalidConstraintAnnotationParameterRestController";

        final JavaFileObject restController = JavaFileObjects.forResource(
                format("error/?/?.java", packageName, restControllerClass));

        final Compilation compilation = compile(restController, moduleInfo(RX_MICRO_VALIDATION_MODULE));
        assertThat(compilation).failed();
        assertThat(compilation)
                .hadErrorContaining(
                        "Annotation '@Enumeration' has invalid parameter: " +
                                "Value couldn't be empty")
                .inFile(restController)
                .onLine(65);
        assertThat(compilation)
                .hadErrorContaining(
                        "Annotation '@Enumeration' has invalid parameter: " +
                                "Expected character enum constant, but actual is ''")
                .inFile(restController)
                .onLine(68);
        assertThat(compilation)
                .hadErrorContaining(
                        "Annotation '@Enumeration' has invalid parameter: " +
                                "Expected character enum constant, but actual is 'hello'")
                .inFile(restController)
                .onLine(68);
        assertThat(compilation)
                .hadErrorContaining(
                        "Annotation '@Enumeration' has invalid parameter: " +
                                "Expected character enum constant, but actual is '1234'")
                .inFile(restController)
                .onLine(68);
        assertThat(compilation)
                .hadErrorContaining(
                        "Annotation '@Pattern' has invalid parameter: " +
                                "Invalid regular expression: Unmatched closing ')'")
                .inFile(restController)
                .onLine(71);
        assertThat(compilation)
                .hadErrorContaining(
                        "Annotation '@SubEnum' has invalid parameter: " +
                                "Expected include or exclude values")
                .inFile(restController)
                .onLine(74);
        assertThat(compilation)
                .hadErrorContaining(
                        "Annotation '@SubEnum' has invalid parameter: " +
                                "Value 'ORANGE' is invalid enum constant. Allowed values: [RED, GREEN, BLUE]")
                .inFile(restController)
                .onLine(77);
        assertThat(compilation)
                .hadErrorContaining(
                        "Annotation '@SubEnum' has invalid parameter: " +
                                "Value 'YELLOW' is invalid enum constant. Allowed values: [RED, GREEN, BLUE]")
                .inFile(restController)
                .onLine(77);
        assertThat(compilation)
                .hadErrorContaining(
                        "Annotation '@MinDouble' has invalid parameter: " +
                                "Value out of range: Expected 1.4E-45 < 4.9E-324 < 3.4028235E38")
                .inFile(restController)
                .onLine(81);
        assertThat(compilation)
                .hadErrorContaining(
                        "Annotation '@MaxDouble' has invalid parameter: " +
                                "Value out of range: Expected 1.4E-45 < 4.9E-324 < 3.4028235E38")
                .inFile(restController)
                .onLine(81);
        assertThat(compilation)
                .hadErrorContaining(
                        "Annotation '@MinDouble' has invalid parameter: " +
                                "Value out of range: Expected 1.4E-45 < 1.7976931348623157E308 < 3.4028235E38")
                .inFile(restController)
                .onLine(85);
        assertThat(compilation)
                .hadErrorContaining(
                        "Annotation '@MaxDouble' has invalid parameter: " +
                                "Value out of range: Expected 1.4E-45 < 1.7976931348623157E308 < 3.4028235E38")
                .inFile(restController)
                .onLine(85);
        assertThat(compilation)
                .hadErrorContaining(
                        "Annotation '@MinInt' has invalid parameter: " +
                                "Value out of range: Expected -128 < -9223372036854775808 < 127")
                .inFile(restController)
                .onLine(89);
        assertThat(compilation)
                .hadErrorContaining(
                        "Annotation '@MaxInt' has invalid parameter: " +
                                "Value out of range: Expected -128 < -9223372036854775808 < 127")
                .inFile(restController)
                .onLine(89);
        assertThat(compilation)
                .hadErrorContaining(
                        "Annotation '@MinInt' has invalid parameter: " +
                                "Value out of range: Expected -128 < 9223372036854775807 < 127")
                .inFile(restController)
                .onLine(93);
        assertThat(compilation)
                .hadErrorContaining(
                        "Annotation '@MaxInt' has invalid parameter: " +
                                "Value out of range: Expected -128 < 9223372036854775807 < 127")
                .inFile(restController)
                .onLine(93);
        assertThat(compilation)
                .hadErrorContaining(
                        "Annotation '@MinInt' has invalid parameter: " +
                                "Value out of range: Expected -32768 < -9223372036854775808 < 32767")
                .inFile(restController)
                .onLine(97);
        assertThat(compilation)
                .hadErrorContaining(
                        "Annotation '@MaxInt' has invalid parameter: " +
                                "Value out of range: Expected -32768 < -9223372036854775808 < 32767")
                .inFile(restController)
                .onLine(97);
        assertThat(compilation)
                .hadErrorContaining(
                        "Annotation '@MinInt' has invalid parameter: " +
                                "Value out of range: Expected -32768 < 9223372036854775807 < 32767")
                .inFile(restController)
                .onLine(101);
        assertThat(compilation)
                .hadErrorContaining(
                        "Annotation '@MaxInt' has invalid parameter: " +
                                "Value out of range: Expected -32768 < 9223372036854775807 < 32767")
                .inFile(restController)
                .onLine(101);
        assertThat(compilation)
                .hadErrorContaining(
                        "Annotation '@MinInt' has invalid parameter: " +
                                "Value out of range: Expected -2147483648 < -9223372036854775808 < 2147483647")
                .inFile(restController)
                .onLine(105);
        assertThat(compilation)
                .hadErrorContaining(
                        "Annotation '@MaxInt' has invalid parameter: " +
                                "Value out of range: Expected -2147483648 < -9223372036854775808 < 2147483647")
                .inFile(restController)
                .onLine(105);
        assertThat(compilation)
                .hadErrorContaining(
                        "Annotation '@MinInt' has invalid parameter: " +
                                "Value out of range: Expected -2147483648 < 9223372036854775807 < 2147483647")
                .inFile(restController)
                .onLine(109);
        assertThat(compilation)
                .hadErrorContaining(
                        "Annotation '@MaxInt' has invalid parameter: " +
                                "Value out of range: Expected -2147483648 < 9223372036854775807 < 2147483647")
                .inFile(restController)
                .onLine(109);
        assertThat(compilation)
                .hadErrorContaining(
                        "Annotation '@MinNumber' has invalid parameter: " +
                                "Expected an integer number")
                .inFile(restController)
                .onLine(113);
        assertThat(compilation)
                .hadErrorContaining(
                        "Annotation '@MaxNumber' has invalid parameter: " +
                                "Expected an integer number")
                .inFile(restController)
                .onLine(113);
        assertThat(compilation)
                .hadErrorContaining(
                        "Annotation '@MinNumber' has invalid parameter: " +
                                "Expected an integer number")
                .inFile(restController)
                .onLine(117);
        assertThat(compilation)
                .hadErrorContaining(
                        "Annotation '@MaxNumber' has invalid parameter: " +
                                "Expected an integer number")
                .inFile(restController)
                .onLine(117);
        assertThat(compilation)
                .hadErrorContaining(
                        "Annotation '@MinNumber' has invalid parameter: " +
                                "Value out of range: Expected -128 < -9999999999999999999 < 127")
                .inFile(restController)
                .onLine(121);
        assertThat(compilation)
                .hadErrorContaining(
                        "Annotation '@MaxNumber' has invalid parameter: " +
                                "Value out of range: Expected -128 < -9999999999999999999 < 127")
                .inFile(restController)
                .onLine(121);
        assertThat(compilation)
                .hadErrorContaining(
                        "Annotation '@MinNumber' has invalid parameter: " +
                                "Value out of range: Expected -128 < 9999999999999999999 < 127")
                .inFile(restController)
                .onLine(125);
        assertThat(compilation)
                .hadErrorContaining(
                        "Annotation '@MaxNumber' has invalid parameter: " +
                                "Value out of range: Expected -128 < 9999999999999999999 < 127")
                .inFile(restController)
                .onLine(125);
        assertThat(compilation)
                .hadErrorContaining(
                        "Annotation '@MinNumber' has invalid parameter: " +
                                "Expected an integer number")
                .inFile(restController)
                .onLine(129);
        assertThat(compilation)
                .hadErrorContaining(
                        "Annotation '@MaxNumber' has invalid parameter: " +
                                "Expected an integer number")
                .inFile(restController)
                .onLine(129);
        assertThat(compilation)
                .hadErrorContaining(
                        "Annotation '@MinNumber' has invalid parameter: " +
                                "Value out of range: Expected -32768 < -9999999999999999999 < 32767")
                .inFile(restController)
                .onLine(133);
        assertThat(compilation)
                .hadErrorContaining(
                        "Annotation '@MaxNumber' has invalid parameter: " +
                                "Value out of range: Expected -32768 < -9999999999999999999 < 32767")
                .inFile(restController)
                .onLine(133);
        assertThat(compilation)
                .hadErrorContaining(
                        "Annotation '@MinNumber' has invalid parameter: " +
                                "Value out of range: Expected -32768 < 9999999999999999999 < 32767")
                .inFile(restController)
                .onLine(137);
        assertThat(compilation)
                .hadErrorContaining(
                        "Annotation '@MaxNumber' has invalid parameter: " +
                                "Value out of range: Expected -32768 < 9999999999999999999 < 32767")
                .inFile(restController)
                .onLine(137);
        assertThat(compilation)
                .hadErrorContaining(
                        "Annotation '@MinNumber' has invalid parameter: " +
                                "Expected an integer number")
                .inFile(restController)
                .onLine(141);
        assertThat(compilation)
                .hadErrorContaining(
                        "Annotation '@MaxNumber' has invalid parameter: " +
                                "Expected an integer number")
                .inFile(restController)
                .onLine(141);
        assertThat(compilation)
                .hadErrorContaining(
                        "Annotation '@MinNumber' has invalid parameter: " +
                                "Value out of range: Expected -2147483648 < -9999999999999999999 < 2147483647")
                .inFile(restController)
                .onLine(145);
        assertThat(compilation)
                .hadErrorContaining(
                        "Annotation '@MaxNumber' has invalid parameter: " +
                                "Value out of range: Expected -2147483648 < -9999999999999999999 < 2147483647")
                .inFile(restController)
                .onLine(145);
        assertThat(compilation)
                .hadErrorContaining(
                        "Annotation '@MinNumber' has invalid parameter: " +
                                "Value out of range: Expected -2147483648 < 9999999999999999999 < 2147483647")
                .inFile(restController)
                .onLine(149);
        assertThat(compilation)
                .hadErrorContaining(
                        "Annotation '@MaxNumber' has invalid parameter: " +
                                "Value out of range: Expected -2147483648 < 9999999999999999999 < 2147483647")
                .inFile(restController)
                .onLine(149);
        assertThat(compilation)
                .hadErrorContaining(
                        "Annotation '@MinNumber' has invalid parameter: " +
                                "Expected an integer number")
                .inFile(restController)
                .onLine(153);
        assertThat(compilation)
                .hadErrorContaining(
                        "Annotation '@MaxNumber' has invalid parameter: " +
                                "Expected an integer number")
                .inFile(restController)
                .onLine(153);
        assertThat(compilation)
                .hadErrorContaining(
                        "Annotation '@MinNumber' has invalid parameter: " +
                                "Value out of range: Expected -9223372036854775808 < -9999999999999999999 < 9223372036854775807")
                .inFile(restController)
                .onLine(157);
        assertThat(compilation)
                .hadErrorContaining(
                        "Annotation '@MaxNumber' has invalid parameter: " +
                                "Value out of range: Expected -9223372036854775808 < -9999999999999999999 < 9223372036854775807")
                .inFile(restController)
                .onLine(157);
        assertThat(compilation)
                .hadErrorContaining(
                        "Annotation '@MinNumber' has invalid parameter: " +
                                "Value out of range: Expected -9223372036854775808 < 9999999999999999999 < 9223372036854775807")
                .inFile(restController)
                .onLine(161);
        assertThat(compilation)
                .hadErrorContaining(
                        "Annotation '@MaxNumber' has invalid parameter: " +
                                "Value out of range: Expected -9223372036854775808 < 9999999999999999999 < 9223372036854775807")
                .inFile(restController)
                .onLine(161);
        assertThat(compilation)
                .hadErrorContaining(
                        "Annotation '@MinNumber' has invalid parameter: " +
                                "Expected a number")
                .inFile(restController)
                .onLine(165);
        assertThat(compilation)
                .hadErrorContaining(
                        "Annotation '@MaxNumber' has invalid parameter: " +
                                "Expected a number")
                .inFile(restController)
                .onLine(165);
        assertThat(compilation)
                .hadErrorContaining(
                        "Annotation '@MinNumber' has invalid parameter: " +
                                "Expected a number")
                .inFile(restController)
                .onLine(169);
        assertThat(compilation)
                .hadErrorContaining(
                        "Annotation '@MaxNumber' has invalid parameter: " +
                                "Expected a number")
                .inFile(restController)
                .onLine(169);
        assertThat(compilation)
                .hadErrorContaining(
                        "Annotation '@MinNumber' has invalid parameter: " +
                                "Value out of range: Expected 1.4E-45 < 4.9E-324 < 3.4028235E38")
                .inFile(restController)
                .onLine(173);
        assertThat(compilation)
                .hadErrorContaining(
                        "Annotation '@MaxNumber' has invalid parameter: " +
                                "Value out of range: Expected 1.4E-45 < 4.9E-324 < 3.4028235E38")
                .inFile(restController)
                .onLine(173);
        assertThat(compilation)
                .hadErrorContaining(
                        "Annotation '@MinNumber' has invalid parameter: " +
                                "Value out of range: Expected 1.4E-45 < 1.8E308 < 3.4028235E38")
                .inFile(restController)
                .onLine(177);
        assertThat(compilation)
                .hadErrorContaining(
                        "Annotation '@MaxNumber' has invalid parameter: " +
                                "Value out of range: Expected 1.4E-45 < 1.8E308 < 3.4028235E38")
                .inFile(restController)
                .onLine(177);
        assertThat(compilation).hadErrorCount(58);
    }
}
