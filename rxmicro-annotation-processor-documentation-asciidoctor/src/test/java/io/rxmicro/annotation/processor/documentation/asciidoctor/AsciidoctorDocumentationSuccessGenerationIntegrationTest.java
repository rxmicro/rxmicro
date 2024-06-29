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

package io.rxmicro.annotation.processor.documentation.asciidoctor;

import com.google.testing.compile.Compilation;
import io.rxmicro.annotation.processor.integration.test.config.ExcludeExample;
import io.rxmicro.annotation.processor.integration.test.config.IncludeExample;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.io.IOException;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static io.rxmicro.annotation.processor.config.SupportedOptions.RX_MICRO_BUILD_UNNAMED_MODULE;
import static io.rxmicro.annotation.processor.documentation.DocSystemProperties.RX_MICRO_POM_XML_ABSOLUTE_PATH;
import static io.rxmicro.common.util.Formats.format;

/**
 * @author nedis
 * @since 0.7.2
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
final class AsciidoctorDocumentationSuccessGenerationIntegrationTest
        extends AbstractAsciidoctorDocumentationAnnotationProcessorIntegrationTest {

    @Order(1)
    @ParameterizedTest
    @IncludeExample("io.rxmicro.examples.unnamed.module")
    @ArgumentsSource(AllInputPackagesArgumentsProvider.class)
    void Should_generate_documentation_for_unnamed_module_successfully(final String packageName) throws IOException {
        System.setProperty(RX_MICRO_POM_XML_ABSOLUTE_PATH, format("?/?/pom.xml", INPUT_DIR, packageName));
        addCompilerOption(RX_MICRO_BUILD_UNNAMED_MODULE, "true");

        final Compilation compilation = compileAllIn(packageName);
        assertThat(compilation).succeeded();
        assertGeneratedDoc(packageName);
    }

    @Order(2)
    @ParameterizedTest
    @ExcludeExample("io.rxmicro.examples.unnamed.module")
    @ArgumentsSource(AllInputPackagesArgumentsProvider.class)
    void Should_generate_documentation_successfully(final String packageName) throws IOException {
        System.setProperty(RX_MICRO_POM_XML_ABSOLUTE_PATH, format("?/?/pom.xml", INPUT_DIR, packageName));

        final Compilation compilation = compileAllIn(packageName);
        assertThat(compilation).succeeded();
        assertGeneratedDoc(packageName);
    }
}
