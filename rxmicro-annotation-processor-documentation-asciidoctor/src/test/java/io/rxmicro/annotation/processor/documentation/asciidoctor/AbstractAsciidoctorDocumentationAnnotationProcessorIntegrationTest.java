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

package io.rxmicro.annotation.processor.documentation.asciidoctor;

import io.rxmicro.annotation.processor.common.BaseRxMicroAnnotationProcessor;
import io.rxmicro.annotation.processor.integration.test.AbstractRxMicroAnnotationProcessorIntegrationTest;
import io.rxmicro.annotation.processor.rest.server.RestServerModuleClassStructuresBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.io.TempDir;

import javax.annotation.processing.Processor;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.TreeSet;

import static io.rxmicro.annotation.processor.common.SupportedOptions.RX_MICRO_DOC_DESTINATION_DIR;
import static io.rxmicro.annotation.processor.documentation.TestSystemProperties.RX_MICRO_POM_XML_ABSOLUTE_PATH;
import static io.rxmicro.annotation.processor.documentation.TestSystemProperties.RX_MICRO_PROJECT_DIRECTORY_PATH;
import static io.rxmicro.annotation.processor.documentation.TestSystemProperties.RX_MICRO_PROJECT_DOCUMENTATION_VERSION;
import static io.rxmicro.annotation.processor.documentation.asciidoctor.TestOptions.RX_MICRO_IGNORE_EMPTY_SPACES_DURING_ASCIIDOCTOR_CONTENT_COMPARISON;
import static io.rxmicro.annotation.processor.documentation.asciidoctor.TestOptions.RX_MICRO_IGNORE_EMPTY_SPACES_DURING_ASCIIDOCTOR_CONTENT_COMPARISON_VALUE;
import static io.rxmicro.annotation.processor.integration.test.ExternalModule.EXTERNAL_NETTY_BUFFER_MODULE;
import static io.rxmicro.annotation.processor.integration.test.ExternalModule.EXTERNAL_NETTY_CODEC_HTTP_MODULE;
import static io.rxmicro.annotation.processor.integration.test.ExternalModule.EXTERNAL_NETTY_CODEC_MODULE;
import static io.rxmicro.annotation.processor.integration.test.ExternalModule.EXTERNAL_NETTY_COMMON_MODULE;
import static io.rxmicro.annotation.processor.integration.test.ExternalModule.EXTERNAL_NETTY_TRANSPORT_MODULE;
import static java.lang.System.lineSeparator;
import static java.nio.file.FileVisitResult.CONTINUE;
import static java.nio.file.Files.walkFileTree;
import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author nedis
 * @link http://rxmicro.io
 */
public abstract class AbstractAsciidoctorDocumentationAnnotationProcessorIntegrationTest
        extends AbstractRxMicroAnnotationProcessorIntegrationTest {

    protected static final String INPUT_DIR;

    private static final String OUTPUT_DIR;

    private static final boolean IGNORE_EMPTY_SPACES;

    @TempDir
    static File destinationDir;

    static {
        INPUT_DIR = getInputAbsolutePath(AbstractAsciidoctorDocumentationAnnotationProcessorIntegrationTest.class);
        OUTPUT_DIR = getOutputAbsolutePath(AbstractAsciidoctorDocumentationAnnotationProcessorIntegrationTest.class);
        IGNORE_EMPTY_SPACES = Optional.ofNullable(System.getProperty(RX_MICRO_IGNORE_EMPTY_SPACES_DURING_ASCIIDOCTOR_CONTENT_COMPARISON))
                .or(() -> Optional.ofNullable(System.getenv(RX_MICRO_IGNORE_EMPTY_SPACES_DURING_ASCIIDOCTOR_CONTENT_COMPARISON)))
                .map(Boolean::parseBoolean)
                .orElse(RX_MICRO_IGNORE_EMPTY_SPACES_DURING_ASCIIDOCTOR_CONTENT_COMPARISON_VALUE);
    }

    @BeforeAll
    static void beforeAll() {
        System.setProperty(RX_MICRO_PROJECT_DIRECTORY_PATH, INPUT_DIR);
        System.setProperty(RX_MICRO_PROJECT_DOCUMENTATION_VERSION, "1.0-SNAPSHOT");
    }

    public AbstractAsciidoctorDocumentationAnnotationProcessorIntegrationTest() {
        addCompilerOption(RX_MICRO_DOC_DESTINATION_DIR, destinationDir.getAbsolutePath());
    }

    @BeforeEach
    void beforeEach() {
        addExternalModule(EXTERNAL_NETTY_COMMON_MODULE);
        addExternalModule(EXTERNAL_NETTY_CODEC_HTTP_MODULE);
        addExternalModule(EXTERNAL_NETTY_CODEC_MODULE);
        addExternalModule(EXTERNAL_NETTY_BUFFER_MODULE);
        addExternalModule(EXTERNAL_NETTY_TRANSPORT_MODULE);
    }

    @Override
    protected final Processor createAnnotationProcessor() {
        return new BaseRxMicroAnnotationProcessor(RestServerModuleClassStructuresBuilder.create());
    }

    protected final void assertGeneratedDoc(final String outputPackage) throws IOException {
        final File[] expectedDocs = getSortedFiles(new File(OUTPUT_DIR + "/" + outputPackage));
        final File[] actualDocs = getSortedFiles(destinationDir);
        assertEquals(
                Arrays.stream(expectedDocs).map(File::getName).collect(toList()),
                Arrays.stream(actualDocs).map(File::getName).collect(toList())
        );
        for (int i = 0; i < expectedDocs.length; i++) {
            final String expectedContent = normalize(Files.readAllLines(expectedDocs[i].toPath()));
            final String actualContent = normalize(Files.readAllLines(actualDocs[i].toPath()));
            assertEquals(expectedContent, actualContent);
        }
    }

    private File[] getSortedFiles(final File directory) {
        final File[] files = directory.listFiles();
        assert files != null;
        return new TreeSet<>(Arrays.asList(files)).toArray(new File[0]);
    }

    private String normalize(final List<String> lines) {
        final StringBuilder stringBuilder = new StringBuilder();
        for (final String line : lines) {
            if (IGNORE_EMPTY_SPACES) {
                final String trimmedLine = line.trim();
                if (!trimmedLine.isEmpty()) {
                    stringBuilder.append(trimmedLine).append(lineSeparator());
                }
            } else {
                stringBuilder.append(line).append(lineSeparator());
            }
        }
        return stringBuilder.toString();
    }

    @AfterEach
    void afterEach() throws IOException {
        walkFileTree(destinationDir.toPath(), new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(final Path file,
                                             final BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return CONTINUE;
            }
        });
    }

    @AfterAll
    static void afterAll() {
        System.getProperties().remove(RX_MICRO_PROJECT_DIRECTORY_PATH);
        System.getProperties().remove(RX_MICRO_PROJECT_DOCUMENTATION_VERSION);
        System.getProperties().remove(RX_MICRO_POM_XML_ABSOLUTE_PATH);
    }
}