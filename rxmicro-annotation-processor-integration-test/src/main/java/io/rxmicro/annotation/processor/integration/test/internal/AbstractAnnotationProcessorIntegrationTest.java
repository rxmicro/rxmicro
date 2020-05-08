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

package io.rxmicro.annotation.processor.integration.test.internal;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjectSubject;
import io.rxmicro.annotation.processor.common.component.impl.AbstractProcessorComponent;
import io.rxmicro.common.CheckedWrapperException;
import org.opentest4j.AssertionFailedError;

import javax.annotation.processing.Processor;
import javax.tools.FileObject;
import javax.tools.JavaFileObject;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static com.google.testing.compile.Compiler.javac;
import static com.google.testing.compile.JavaFileObjects.forSourceString;
import static io.rxmicro.annotation.processor.common.SupportedOptions.RX_MICRO_LOG_LEVEL;
import static io.rxmicro.common.util.ExCollectors.toTreeSet;
import static io.rxmicro.common.util.Formats.format;
import static java.lang.System.lineSeparator;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.joining;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author nedis
 * @since 0.1
 */
public abstract class AbstractAnnotationProcessorIntegrationTest {

    private static Field javaFileObjectSubjectActualField;

    final Map<String, String> compilerOptions = new LinkedHashMap<>(
            Map.of(RX_MICRO_LOG_LEVEL, AbstractProcessorComponent.Level.OFF.name())
    );

    private final Set<String> modulePath;

    private final Map<String, String> overriddenSourceOutput = new HashMap<>();

    protected AbstractAnnotationProcessorIntegrationTest(final Set<String> modulePath) {
        this.modulePath = modulePath;
    }

    protected void addToModulePath(final String path) {
        modulePath.add(path);
    }

    protected void removeFromModulePath(final String pathFragment) {
        modulePath.removeIf(path -> path.contains(pathFragment));
    }

    protected void addCompilerOption(final String name,
                                     final String value) {
        compilerOptions.put(name, value);
    }

    protected void registerOverriddenSourceOutput(final String sourceCode,
                                                  final String content) {
        overriddenSourceOutput.put(sourceCode, content);
    }

    protected abstract Processor createAnnotationProcessor();

    protected Compilation compile(final Collection<String> files) {
        return compile(
                files.stream()
                        .map(JavaSources::forResource)
                        .toArray(JavaFileObject[]::new)
        );
    }

    protected Compilation compile(final JavaFileObject... files) {
        final Stream<String> pathStream;
        if (Arrays.stream(files).anyMatch(jfo -> jfo.getName().endsWith("module-info.java"))) {
            pathStream = Stream.of("--module-path", String.join(File.pathSeparator, modulePath));
        } else {
            pathStream = Stream.of("-classpath", String.join(File.pathSeparator, modulePath));
        }
        return javac()
                .withOptions(Stream.of(
                        compilerOptions.entrySet().stream()
                                .map(e -> format("-A?=?", e.getKey(), e.getValue())),
                        pathStream
                ).flatMap(identity()).collect(Collectors.toList()))
                .withProcessors(createAnnotationProcessor())
                .compile(files);
    }

    protected void assertGenerated(final Compilation compilation,
                                   final SourceCodeResource... expectedSourceCodeResources) throws IOException {
        assertGenerated(compilation, false, expectedSourceCodeResources);
    }

    @SuppressWarnings("SameParameterValue")
    protected void assertGenerated(final Compilation compilation,
                                   final boolean useEqualsToInsteadOfEquivalentSource,
                                   final SourceCodeResource... expectedSourceCodeResources) throws IOException {
        final Set<String> expectedGeneratedSourceFiles = Arrays.stream(expectedSourceCodeResources)
                .map(SourceCodeResource::getGeneratedClassNameSourceCodeFile)
                .collect(toTreeSet());
        final Set<String> actualGeneratedSourceFiles = compilation.generatedSourceFiles().stream()
                .map(FileObject::getName)
                .collect(toTreeSet());
        assertEquals(
                expectedGeneratedSourceFiles.stream().collect(joining(lineSeparator())),
                actualGeneratedSourceFiles.stream().collect(joining(lineSeparator()))
        );

        for (final SourceCodeResource expectedSourceCodeResource : expectedSourceCodeResources) {
            assertGeneratedFile(compilation, expectedSourceCodeResource, useEqualsToInsteadOfEquivalentSource);
        }
    }

    private void assertGeneratedFile(final Compilation compilation,
                                     final SourceCodeResource expectedSourceCodeResource,
                                     final boolean useEqualsToInsteadOfEquivalentSource) throws IOException {
        final JavaFileObject expectedJavaFileObject;
        if (overriddenSourceOutput.containsKey(expectedSourceCodeResource.getFullClassName())) {
            expectedJavaFileObject = forSourceString(
                    expectedSourceCodeResource.getFullClassName() + ".java",
                    overriddenSourceOutput.get(expectedSourceCodeResource.getFullClassName())
            );
        } else {
            expectedJavaFileObject = JavaSources.forResource(expectedSourceCodeResource.getOriginalClasspathResource());
        }
        final JavaFileObjectSubject javaFileObjectSubject =
                assertThat(compilation)
                        .generatedSourceFile(expectedSourceCodeResource.getFullClassName());
        try {
            if (useEqualsToInsteadOfEquivalentSource) {
                javaFileObjectSubject.isEqualTo(expectedJavaFileObject);
            } else {
                javaFileObjectSubject.hasSourceEquivalentTo(expectedJavaFileObject);
            }
        } catch (final AssertionError e) {
            throw new AssertionFailedError(
                    e.getMessage(),
                    expectedJavaFileObject.getCharContent(false),
                    getActual(javaFileObjectSubject).getCharContent(false));
        }
    }

    private JavaFileObject getActual(final JavaFileObjectSubject javaFileObjectSubject) {
        try {
            if (javaFileObjectSubjectActualField == null) {
                javaFileObjectSubjectActualField = JavaFileObjectSubject.class.getDeclaredField("actual");
                if (!javaFileObjectSubjectActualField.canAccess(javaFileObjectSubject)) {
                    javaFileObjectSubjectActualField.setAccessible(true);
                }
            }
            return (JavaFileObject) javaFileObjectSubjectActualField.get(javaFileObjectSubject);
        } catch (final NoSuchFieldException | IllegalAccessException e) {
            throw new CheckedWrapperException(e);
        }
    }

}
