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

package io.rxmicro.annotation.processor.integration.test;

import com.google.testing.compile.Compilation;
import io.rxmicro.annotation.processor.integration.test.config.ExcludeExample;
import io.rxmicro.annotation.processor.integration.test.config.IncludeExample;
import io.rxmicro.annotation.processor.integration.test.internal.AbstractAnnotationProcessorIntegrationTest;
import io.rxmicro.annotation.processor.integration.test.internal.SourceCodeResource;
import io.rxmicro.annotation.processor.integration.test.model.CompilationError;
import io.rxmicro.annotation.processor.integration.test.model.ExampleWithError;
import io.rxmicro.common.InvalidStateException;
import io.rxmicro.common.RxMicroModule;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import javax.tools.JavaFileObject;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static com.google.testing.compile.JavaFileObjects.forSourceLines;
import static io.rxmicro.annotation.processor.integration.test.ClasspathResources.getOnlyChildrenAtTheFolder;
import static io.rxmicro.annotation.processor.integration.test.ClasspathResources.getResourcesAtTheFolderWithAllNestedOnes;
import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.common.util.Requires.require;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

/**
 * @author nedis
 * @since 0.1
 */
public abstract class AbstractRxMicroAnnotationProcessorIntegrationTest extends AbstractAnnotationProcessorIntegrationTest {

    private static final String ERROR = "error";

    private static final String INPUT = "input";

    private static final String OUTPUT = "output";

    private static final String PATH_ELEMENT_SEPARATOR = "/";

    private final Set<ExternalModule> externalModules = new HashSet<>();

    public static String getInputAbsolutePath(final Class<?> clazz) {
        return getAbsolutePath(clazz, INPUT);
    }

    public static String getOutputAbsolutePath(final Class<?> clazz) {
        return getAbsolutePath(clazz, OUTPUT);
    }

    private static String getAbsolutePath(final Class<?> clazz,
                                          final String resource) {
        try {
            return new File(require(clazz.getClassLoader().getResource(resource)).toURI()).getAbsolutePath();
        } catch (final URISyntaxException ex) {
            throw new IllegalArgumentException("Classpath resource not found: " + ex.getMessage(), ex);
        }
    }

    public AbstractRxMicroAnnotationProcessorIntegrationTest() {
        super(
                Arrays.stream(require(new File("..").list()))
                        .filter(module -> module.startsWith("rxmicro"))
                        .map(module -> "../" + module + "/target/classes")
                        .collect(toSet())
        );
    }

    protected JavaFileObject moduleInfo(final Collection<RxMicroModule> rxMicroModules,
                                        final Collection<ExternalModule> externalModules) {
        return forSourceLines("module-info",
                Stream.of(
                        Stream.of("module rxmicro.dynamic.integration.test {"),
                        rxMicroModules.stream().map(m -> format("    requires ?;", m.getName())),
                        externalModules.stream().map(m -> format("    requires ?;", m.getModuleName())),
                        Stream.of("}")
                ).flatMap(identity()).collect(toList())
        );
    }

    protected JavaFileObject moduleInfo(final RxMicroModule... rxMicroModules) {
        return moduleInfo(Arrays.asList(rxMicroModules), externalModules);
    }

    protected void addExternalModule(final ExternalModule externalModule) {
        this.externalModules.add(externalModule);
        addToModulePath(externalModule.getJarPath());
    }

    protected final void shouldCompileAndGenerateClassesSuccessfully(final String packageName) throws IOException {
        final Compilation compilation = compileAllIn(packageName);
        assertThat(compilation).succeeded();
        assertAllGeneratedIn(compilation, packageName);
    }

    protected final void shouldThrowCompilationError(final String classpathResource,
                                                     final RxMicroModule... rxMicroModules) {
        final ExampleWithError exampleWithError = getExampleWithError(classpathResource);

        final JavaFileObject restController = forSourceLines(exampleWithError.getName(), exampleWithError.getSource());
        final Compilation compilation = compile(restController, moduleInfo(rxMicroModules));

        for (final CompilationError compilationError : exampleWithError.getCompilationErrors()) {
            if (compilationError.isLineNumberPresents()) {
                assertThat(compilation)
                        .hadErrorContaining(compilationError.getMessage())
                        .inFile(restController)
                        .onLine(compilationError.getLineNumber());
            } else {
                assertThat(compilation).hadErrorContaining(compilationError.getMessage());
            }
        }
        assertThat(compilation).hadErrorCount(exampleWithError.getCompilationErrors().size());
    }

    protected Compilation compileAllIn(final String packageName) {
        return compile(getResourcesAtTheFolderWithAllNestedOnes(INPUT + PATH_ELEMENT_SEPARATOR + packageName, r -> r.endsWith(".java")));
    }

    protected String getModuleName(final String packageName) {
        final Set<String> sourceCodes =
                getResourcesAtTheFolderWithAllNestedOnes(INPUT + PATH_ELEMENT_SEPARATOR + packageName, r -> r.endsWith(".java"));
        for (final String sourceCode : sourceCodes) {
            if (sourceCode.contains("moduleinfo.java")) {
                final String content = ClasspathResources.getResourceContent(sourceCode);
                final String[] lines = content.split(System.lineSeparator());
                for (final String line : lines) {
                    if (line.startsWith("module ")) {
                        // Reads module name from module-info.java file:
                        return line.replace("module", "")
                                .replace("{", "")
                                .trim();
                    }
                }
                throw new IllegalStateException("Invalid content of the file: " + sourceCode);
            }
        }
        return "unnamed-" + packageName;
    }

    protected SourceCodeResource sourceCodeResource(final String resource) {
        if (resource.startsWith(OUTPUT)) {
            return new SourceCodeResource(OUTPUT, resource);
        } else {
            return new SourceCodeResource(OUTPUT, OUTPUT + PATH_ELEMENT_SEPARATOR + resource);
        }
    }

    private void assertAllGeneratedIn(final Compilation compilation,
                                      final String packageName) throws IOException {
        final String moduleName = getModuleName(packageName);
        assertGenerated(
                compilation,
                Stream.concat(
                                getResourcesAtTheFolderWithAllNestedOnes(
                                        OUTPUT + PATH_ELEMENT_SEPARATOR + packageName,
                                        r -> r.endsWith(".java"))
                                        .stream(),
                                getResourcesAtTheFolderWithAllNestedOnes(
                                        OUTPUT + PATH_ELEMENT_SEPARATOR + "rxmicro" + PATH_ELEMENT_SEPARATOR + moduleName,
                                        r -> r.endsWith(".java"))
                                        .stream()
                        )
                        .map(this::sourceCodeResource).distinct().toArray(SourceCodeResource[]::new)
        );
    }

    private static void validateIncludesAndExcludes(final Method method,
                                                    final List<IncludeExample> includeExamples,
                                                    final List<ExcludeExample> excludeExamples) {
        if (!includeExamples.isEmpty() && !excludeExamples.isEmpty()) {
            throw new InvalidStateException("Only includes OR excludes must be specified per test method: ?", method);
        }
    }

    private static Predicate<String> createPackagePredicate(final List<IncludeExample> includeExamples,
                                                            final List<ExcludeExample> excludeExamples) {

        if (!includeExamples.isEmpty()) {
            final List<Pattern> patterns = includeExamples.stream()
                    .map(annotation -> Pattern.compile(annotation.value()))
                    .collect(toList());
            return resource ->
                    patterns.stream().anyMatch(pattern -> pattern.matcher(resource.replace('/', '.')).find());
        } else if (!excludeExamples.isEmpty()) {
            final List<Pattern> patterns = excludeExamples.stream()
                    .map(annotation -> Pattern.compile(annotation.value()))
                    .collect(toList());
            return resource ->
                    patterns.stream().noneMatch(pattern -> pattern.matcher(resource.replace('/', '.')).find());
        } else {
            return resource -> true;
        }
    }

    /**
     * @author nedis
     * @since 0.1
     */
    protected static final class AllInputPackagesArgumentsProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(final ExtensionContext extensionContext) {
            final List<IncludeExample> includeExamples =
                    List.of(extensionContext.getRequiredTestMethod().getAnnotationsByType(IncludeExample.class));
            final List<ExcludeExample> excludeExamples =
                    List.of(extensionContext.getRequiredTestMethod().getAnnotationsByType(ExcludeExample.class));
            validateIncludesAndExcludes(extensionContext.getRequiredTestMethod(), includeExamples, excludeExamples);
            return getOnlyChildrenAtTheFolder(INPUT, r -> r.startsWith("io.rxmicro.examples")).stream()
                    .filter(createPackagePredicate(includeExamples, excludeExamples))
                    .map(Arguments::of);
        }
    }

    /**
     * @author nedis
     * @since 0.7.2
     */
    protected static final class AllErrorPackagesArgumentsProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(final ExtensionContext extensionContext) {
            final List<IncludeExample> includeExamples =
                    List.of(extensionContext.getRequiredTestMethod().getAnnotationsByType(IncludeExample.class));
            final List<ExcludeExample> excludeExamples =
                    List.of(extensionContext.getRequiredTestMethod().getAnnotationsByType(ExcludeExample.class));
            validateIncludesAndExcludes(extensionContext.getRequiredTestMethod(), includeExamples, excludeExamples);
            return getResourcesAtTheFolderWithAllNestedOnes(ERROR, r -> !r.endsWith(".class")).stream()
                    .filter(createPackagePredicate(includeExamples, excludeExamples))
                    .map(Arguments::of);
        }
    }
}
