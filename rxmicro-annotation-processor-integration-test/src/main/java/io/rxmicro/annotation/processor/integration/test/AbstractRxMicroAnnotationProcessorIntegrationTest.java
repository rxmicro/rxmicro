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

package io.rxmicro.annotation.processor.integration.test;

import com.google.testing.compile.Compilation;
import io.rxmicro.annotation.processor.integration.test.internal.AbstractAnnotationProcessorIntegrationTest;
import io.rxmicro.annotation.processor.integration.test.internal.SourceCodeResource;
import io.rxmicro.common.RxMicroModule;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import javax.tools.JavaFileObject;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static com.google.testing.compile.JavaFileObjects.forSourceLines;
import static io.rxmicro.annotation.processor.integration.test.ClasspathResources.getOnlyChildrenAtTheFolder;
import static io.rxmicro.annotation.processor.integration.test.ClasspathResources.getResourceContent;
import static io.rxmicro.annotation.processor.integration.test.ClasspathResources.getResourcesAtTheFolderWithAllNestedOnes;
import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.common.util.Requires.require;
import static io.rxmicro.runtime.detail.Runtimes.ENTRY_POINT_PACKAGE;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;
import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public abstract class AbstractRxMicroAnnotationProcessorIntegrationTest extends AbstractAnnotationProcessorIntegrationTest {

    private static final String INPUT = "input";

    private static final String OUTPUT = "output";

    private final Map<String, String> aggregators;

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
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Classpath resource not found: " + e.getMessage(), e);
        }
    }

    public AbstractRxMicroAnnotationProcessorIntegrationTest(final String... aggregatorSimpleClassName) {
        super(
                Arrays.stream(require(new File("..").list()))
                        .filter(module -> module.startsWith("rxmicro"))
                        .map(module -> "../" + module + "/target/classes")
                        .collect(toSet())
        );
        this.aggregators = Stream.concat(
                Arrays.stream(aggregatorSimpleClassName),
                Stream.of("$$Reflections")
        ).collect(toMap(identity(), cl -> format("?.?", ENTRY_POINT_PACKAGE, cl)));
    }

    protected JavaFileObject moduleInfo(final Collection<RxMicroModule> rxMicroModules,
                                        final Collection<ExternalModule> externalModules) {
        return forSourceLines("module-info",
                Stream.of(
                        Stream.of("module rxmicro.dynamic {"),
                        rxMicroModules.stream().map(m -> format("    requires ?;", m.getName())),
                        externalModules.stream().map(m -> format("    requires ?;", m.getModuleName())),
                        Stream.of("}")
                ).flatMap(identity()).collect(toList())
        );
    }

    protected JavaFileObject moduleInfo(final RxMicroModule... rxMicroModules) {
        return moduleInfo(Arrays.asList(rxMicroModules), List.of());
    }

    protected void addExternalModule(final ExternalModule externalModule) {
        addModulePath(externalModule.getJarPath());
    }

    protected void verifyAllClassesInPackage(final String packageName) throws IOException {
        if (packageName == null) {
            // ignore not found packageName
            return;
        }
        final Compilation compilation = compileAllIn(packageName);
        assertThat(compilation).succeeded();
        assertAllGeneratedIn(compilation, packageName);
    }

    protected Compilation compileAllIn(final String packageName) {
        return compile(getResourcesAtTheFolderWithAllNestedOnes(INPUT + "/" + packageName, r -> r.endsWith(".java")));
    }

    protected SourceCodeResource sourceCodeResource(final String resource) {
        if (resource.startsWith(OUTPUT)) {
            return new SourceCodeResource(OUTPUT, resource);
        } else {
            return new SourceCodeResource(OUTPUT, OUTPUT + "/" + resource);
        }
    }

    protected boolean withEnvironmentCustomizer() {
        return true;
    }

    private void assertAllGeneratedIn(final Compilation compilation,
                                      final String packageName) throws IOException {
        final Set<SourceCodeResource> resources = getResourcesAtTheFolderWithAllNestedOnes(
                OUTPUT + "/" + packageName,
                r -> r.endsWith(".java"))
                .stream()
                .map(this::sourceCodeResource)
                .collect(toSet());
        registerAllOverriddenSourceOutputs(resources);
        if (withEnvironmentCustomizer()) {
            resources.add(sourceCodeResource(format("?/?/$$EnvironmentCustomizer.java", OUTPUT, ENTRY_POINT_PACKAGE)));
        }
        assertGenerated(
                compilation,
                resources.toArray(new SourceCodeResource[0])
        );
    }

    private void registerAllOverriddenSourceOutputs(final Set<SourceCodeResource> resources) {
        final Iterator<SourceCodeResource> iterator = resources.iterator();
        final Set<SourceCodeResource> toAdd = new HashSet<>();
        while (iterator.hasNext()) {
            final SourceCodeResource resource = iterator.next();
            Optional.ofNullable(aggregators.get(resource.getSimpleClassName())).ifPresent(aggregatorFullClassName -> {
                final String aggregatorResource = format("?/?.java", OUTPUT, aggregatorFullClassName.replace(".", "/"));
                registerOverriddenSourceOutput(aggregatorFullClassName, getResourceContent(resource.getOriginalClasspathResource()));
                iterator.remove();
                toAdd.add(sourceCodeResource(aggregatorResource));
            });
        }
        resources.addAll(toAdd);
    }

    /**
     * @author nedis
     * @link http://rxmicro.io
     * @since 0.1
     */
    protected static final class AllInputPackagesArgumentsProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(final ExtensionContext extensionContext) {
            try {
                return getOnlyChildrenAtTheFolder(INPUT, r -> r.startsWith("io.rxmicro.examples")).stream().map(Arguments::of);
            } catch (final IllegalArgumentException e) {
                // Temp fix. See verifyAllClassesInPackage
                return Stream.of(arguments((Object) null));
            }
        }
    }
}
