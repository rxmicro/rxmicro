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

package io.rxmicro.annotation.processor.rest.component.impl;

import com.google.testing.compile.Compilation;
import io.rxmicro.annotation.processor.common.model.ClassHeader;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.annotation.processor.integration.test.AbstractRxMicroAnnotationProcessorIntegrationTest;
import io.rxmicro.annotation.processor.rest.model.validator.ModelConstraintAnnotation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.Temporal;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

/**
 * @author nedis
 *
 * @since 0.2
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
final class AnnotationValueConverterImplTest extends AbstractRxMicroAnnotationProcessorIntegrationTest {

    private static ModelConstraintAnnotation annotation;

    private final AnnotationValueConverterImpl annotationValueConverter = new AnnotationValueConverterImpl();

    @Mock
    private ClassHeader.Builder classHeaderBuilder;

    @Mock
    private Element owner;

    @Override
    protected Processor createAnnotationProcessor() {
        return new TestAnnotationProcessor();
    }

    @BeforeEach
    void beforeEach() {
        if (annotation == null) {
            final Compilation compilation = compileAllIn("io.rxmicro.examples.annotations");
            assertThat(compilation).succeeded();
        }
    }

    @ParameterizedTest
    @CsvSource({
            "BOOLEAN,       true",
            "BYTE,          (byte)127",
            "SHORT,         (short)32767",
            "INT,           2147483647",
            "LONG,          9223372036854775807L",
            "DOUBLE,        1.7976931348623157E308",
            "FLOAT,         (float)3.4028235E38",
            "CHAR,          'r'",
            "STRING,        \"rxmicro\"",
    })
    @Order(10)
    void Should_support_primitives_and_String(final String name,
                                              final String expected) {
        final String actual = annotationValueConverter.convert(owner, classHeaderBuilder, getAnnotationValue(name));

        assertEquals("CHAR".equals(name) ? "'r'" : expected, actual);
        verifyNoInteractions(classHeaderBuilder);
        verifyNoInteractions(owner);
    }

    @Test
    @Order(11)
    void Should_support_class() {
        final String result = annotationValueConverter.convert(
                owner, classHeaderBuilder, getAnnotationValue("CLASS")
        );

        assertEquals("Temporal.class", result);
        verify(classHeaderBuilder).addImports(Temporal.class.getName());
        verifyNoInteractions(owner);
    }

    @Test
    @Order(12)
    void Should_support_enum_constant() {
        final String result = annotationValueConverter.convert(
                owner, classHeaderBuilder, getAnnotationValue("TIME_UNIT")
        );

        assertEquals("TimeUnit.SECONDS", result);
        verify(classHeaderBuilder).addImports(TimeUnit.class.getName());
        verifyNoInteractions(owner);
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "BOOLEANS;       List.of(false, true)",
            "BYTES;          List.of((byte)-128, (byte)127)",
            "SHORTS;         List.of((short)-32768, (short)32767)",
            "INTS;           List.of(-2147483648, 2147483647)",
            "LONGS;          List.of(-9223372036854775808L, 9223372036854775807L)",
            "DOUBLES;        List.of(4.9E-324, 0.0, 1.7976931348623157E308)",
            "FLOATS;         List.of((float)1.4E-45, (float)0.0, (float)3.4028235E38)",
            "CHARS;          List.of('r', 'x')",
            "STRINGS;        List.of(\"rxmicro\", \"io\")",
    })
    @Order(20)
    void Should_support_primitive_and_String_array(final String name,
                                                   final String expected) {
        final String actual = annotationValueConverter.convert(owner, classHeaderBuilder, getAnnotationValue(name));

        assertEquals("CHAR".equals(name) ? "List.of('r', 'x')" : expected, actual);
        verify(classHeaderBuilder).addImports(List.class);
        verifyNoInteractions(owner);
    }

    @Test
    @Order(21)
    void Should_support_class_array() {
        final String result = annotationValueConverter.convert(
                owner, classHeaderBuilder, getAnnotationValue("CLASSES")
        );

        assertEquals("List.of(Instant.class, LocalDate.class)", result);
        verify(classHeaderBuilder).addImports(List.class);
        verify(classHeaderBuilder).addImports(Instant.class.getName());
        verify(classHeaderBuilder).addImports(LocalDate.class.getName());
        verifyNoInteractions(owner);
    }

    @Test
    @Order(22)
    void Should_support_enum_constant_array() {
        final String result = annotationValueConverter.convert(
                owner, classHeaderBuilder, getAnnotationValue("TIME_UNITS")
        );

        assertEquals("List.of(TimeUnit.SECONDS, TimeUnit.MICROSECONDS)", result);
        verify(classHeaderBuilder).addImports(List.class);
        verify(classHeaderBuilder, times(2)).addImports(TimeUnit.class.getName());
        verifyNoInteractions(owner);
    }

    @ParameterizedTest
    @ValueSource(strings = {"OVERRIDE", "OVERRIDES"})
    @Order(99)
    void Should_not_support_nested_annotations(final String name) {
        final InterruptProcessingException exception = assertThrows(InterruptProcessingException.class, () ->
                annotationValueConverter.convert(owner, classHeaderBuilder, getAnnotationValue(name)));

        assertSame(owner, exception.getElement());
        assertEquals(
                "Annotation value: @java.lang.Override (type=com.sun.tools.javac.code.Attribute$Compound) not supported!",
                exception.getMessage()
        );
    }

    private Object getAnnotationValue(final String name) {
        return annotation.getElementValues().entrySet().stream()
                .filter(e -> e.getKey().getSimpleName().toString().equals(name))
                .findFirst()
                .map(e -> e.getValue().getValue())
                .orElseGet(() -> fail("Annotation parameter not found: " + name));
    }

    /**
     * @author nedis
     *
     * @since 0.2
     */
    @SuppressWarnings("PMD.AvoidBranchingStatementAsLastInLoop")
    static final class TestAnnotationProcessor extends AbstractProcessor {

        @Override
        public Set<String> getSupportedAnnotationTypes() {
            return Set.of(
                    "io.rxmicro.examples.annotations.AllSupportedTypes"
            );
        }

        @Override
        public boolean process(final Set<? extends TypeElement> annotations,
                               final RoundEnvironment roundEnv) {
            for (final TypeElement annotation : annotations) {
                for (final Element element : roundEnv.getElementsAnnotatedWith(annotation)) {
                    final AnnotationMirror annotationMirror = element.getAnnotationMirrors().get(0);
                    AnnotationValueConverterImplTest.annotation = new ModelConstraintAnnotation(
                            processingEnv.getElementUtils().getElementValuesWithDefaults(annotationMirror),
                            annotationMirror,
                            mock(TypeElement.class)
                    );
                    return true;
                }
            }
            return false;
        }
    }
}
