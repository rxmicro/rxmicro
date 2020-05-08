/*
 * Copyright 2019 https://rxmicro.io
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

package io.rxmicro.annotation.processor.common.component.impl;

import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.lang.model.element.Element;
import java.util.ArrayList;
import java.util.List;

import static io.rxmicro.common.util.Formats.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author nedis
 *
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith({
        MockitoExtension.class
})
final class AbstractExpressionParser_UnitTest {

    private final AbstractExpressionParser parser = new AbstractExpressionParser() {
    };

    @Mock
    private Element owner;

    @Test
    @Order(1)
    void Should_extract_no_variables() {
        final StringBuilder templateBuilder = new StringBuilder();
        final List<String> variables = new ArrayList<>();
        parser.extractTemplateAndVariables(owner, templateBuilder, variables, "/group1/path1", false);

        assertEquals("/group1/path1", templateBuilder.toString());
        assertEquals(List.of(), variables);
    }

    @ParameterizedTest
    @CsvSource({
            "/${path},                      /?,                 path",
            "/${path}/${path},              /?/?,               path;path",
            "/${path1}-${path2},            /?-?,               path1;path2",
            "/group1-${path1}-${path2},     /group1-?-?,        path1;path2"
    })
    @Order(2)
    void Should_extract_template_and_variables_successfully(final String expression,
                                                            final String expectedTemplate,
                                                            final String expectedVariables) {
        final StringBuilder templateBuilder = new StringBuilder();
        final List<String> variables = new ArrayList<>();
        parser.extractTemplateAndVariables(owner, templateBuilder, variables, expression, false);

        assertEquals(expectedTemplate, templateBuilder.toString());
        assertEquals(List.of(expectedVariables.split(";")), variables);
    }

    @ParameterizedTest
    @CsvSource({
            "/${path",
            "/${path}/${path",
            "/${path1}-${path2",
            "/group1-${path1}-${path2-suffix"
    })
    @Order(3)
    void Should_throw_InterruptProcessingException_if_closing_brace_missing(final String expression) {
        final InterruptProcessingException exception = assertThrows(InterruptProcessingException.class, () ->
                parser.extractTemplateAndVariables(owner, new StringBuilder(), new ArrayList<>(), expression, false));
        assertEquals(
                format("Invalid expression: '?' -> Missing '}'", expression),
                exception.getMessage()
        );
        assertSame(owner, exception.getElement());
    }

    @ParameterizedTest
    @CsvSource({
            "/{path}",
            "/${path}/{path}",
            "/${path1}-{path2",
            "/group1-{path1-${path2-suffix"
    })
    @Order(4)
    void Should_throw_InterruptProcessingException_if_dollar_missing_before_opening_brace(final String expression) {
        final InterruptProcessingException exception = assertThrows(InterruptProcessingException.class, () ->
                parser.extractTemplateAndVariables(owner, new StringBuilder(), new ArrayList<>(), expression, false));
        assertEquals(
                format("Invalid expression: '?' -> Missing '$' before '{'", expression),
                exception.getMessage()
        );
        assertSame(owner, exception.getElement());
    }

    @ParameterizedTest
    @CsvSource({
            "/${path-${nested}}",
            "/${path/${path}",
            "/${path1${nested}}-${path2}",
            "/${path1}-${path2${nested}}",
            "/group1-${path1-${path2-suffix"
    })
    @Order(5)
    void Should_throw_InterruptProcessingException_if_nested_expression_detected(final String expression) {
        final InterruptProcessingException exception = assertThrows(InterruptProcessingException.class, () ->
                parser.extractTemplateAndVariables(owner, new StringBuilder(), new ArrayList<>(), expression, false));
        assertEquals(
                format("Invalid expression: '?' -> Nested expression not allowed", expression),
                exception.getMessage()
        );
        assertSame(owner, exception.getElement());
    }

    @ParameterizedTest
    @CsvSource({
            "/${path}$",
            "/${path}$path",
            "/${path1}-$path2",
            "/${path1-$path2",
            "/${path1-$path2}",
            "$",
            "$path"
    })
    @Order(6)
    void Should_throw_InterruptProcessingException_if_opening_brace_missing_after_dollar(final String expression) {
        final InterruptProcessingException exception = assertThrows(InterruptProcessingException.class, () ->
                parser.extractTemplateAndVariables(owner, new StringBuilder(), new ArrayList<>(), expression, false));
        assertEquals(
                format("Invalid expression: '?' -> Missing '{' after '$'", expression),
                exception.getMessage()
        );
        assertSame(owner, exception.getElement());
    }
}