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

package io.rxmicro.annotation.processor.documentation.asciidoctor.success;

import com.google.testing.compile.Compilation;
import io.rxmicro.annotation.processor.documentation.asciidoctor.AbstractAsciidoctorDocumentationAnnotationProcessorIntegrationTest;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.io.IOException;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static io.rxmicro.annotation.processor.documentation.TestSystemProperties.RX_MICRO_POM_XML_ABSOLUTE_PATH;
import static io.rxmicro.common.util.Formats.format;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
final class AsciidoctorProjectDocumentation_IntegrationTest
        extends AbstractAsciidoctorDocumentationAnnotationProcessorIntegrationTest {

    @ParameterizedTest
    @ArgumentsSource(AllInputPackagesArgumentsProvider.class)
    void verify(final String packageName) throws IOException {
        System.setProperty(RX_MICRO_POM_XML_ABSOLUTE_PATH, format("?/?/pom.xml", INPUT_DIR, packageName));
        final Compilation compilation = compileAllIn(packageName);
        assertThat(compilation).succeeded();
        assertGeneratedDoc(packageName);
    }
}
