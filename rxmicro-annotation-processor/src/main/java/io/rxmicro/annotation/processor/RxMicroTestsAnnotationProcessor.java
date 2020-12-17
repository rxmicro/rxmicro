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

package io.rxmicro.annotation.processor;

import io.rxmicro.annotation.processor.common.BaseRxMicroAnnotationProcessor;
import io.rxmicro.annotation.processor.common.FormatSourceCodeDependenciesModule;
import io.rxmicro.annotation.processor.common.component.impl.AbstractModuleClassStructuresBuilder;
import io.rxmicro.annotation.processor.common.model.AnnotationProcessorType;
import io.rxmicro.annotation.processor.common.model.ClassHeader;
import io.rxmicro.annotation.processor.common.model.ClassStructure;
import io.rxmicro.annotation.processor.common.model.EnvironmentContext;
import io.rxmicro.common.util.ExCollections;
import io.rxmicro.common.util.Formats;
import io.rxmicro.common.util.TestLoggers;
import io.rxmicro.reflection.ReflectionConstants;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;

import static io.rxmicro.annotation.processor.common.model.AnnotationProcessorType.TESTS_COMPILE;
import static io.rxmicro.annotation.processor.common.model.ClassHeader.newClassHeaderBuilder;
import static io.rxmicro.annotation.processor.common.util.GeneratedClassNames.getEntryPointFullClassName;
import static io.rxmicro.annotation.processor.common.util.Injects.injectDependencies;
import static io.rxmicro.runtime.detail.RxMicroRuntime.ENTRY_POINT_PACKAGE;
import static io.rxmicro.tool.common.TestFixers.COMPONENT_TEST_FIXER;
import static io.rxmicro.tool.common.TestFixers.INTEGRATION_TEST_FIXER;
import static io.rxmicro.tool.common.TestFixers.REST_BASED_MICRO_SERVICE_TEST_FIXER;

/**
 * This is the {@code RxMicro Test Annotation Processor}.
 *
 * <p>
 * The RxMicro framework uses the {@link javax.annotation.processing.Processor}, which generates standard code using RxMicro annotations.
 *
 * <p>
 * <strong>How it works?</strong>
 *
 * <p>
 * Java 9 has introduced the <a href="https://www.oracle.com/corporate/features/understanding-java-9-modules.html">JPMS</a>.
 *
 * <p>
 * This system requires that a developer defines the {@code module-info.java} descriptor for each project.
 * In this descriptor, the developer must describe all the dependencies of the current project.
 * In the context of the unit module system, the tests required for each project should be configured as a separate module,
 * since they depend on libraries that should not be available in the runtime.
 * Usually such libraries are unit testing libraries (e.g. <a href="https://junit.org/junit5/">JUnit 5</a>),
 * mock creation libraries (e.g. <a href="https://site.mockito.org/">Mockito</a>), etc.
 *
 * <p>
 * When trying to create a separate {@code module-info.java} descriptor available only for unit tests, many modern IDEs report an error.
 *
 * <p>
 * <strong>Therefore, the simplest and most common solution to this problem is to organize unit tests in the form of automatic module.
 * This solution allows You to correct compilation errors, but when starting tests, there will be runtime errors.
 * To fix runtime errors, when starting the Java virtual machine, You must add options that configure the Java module system at runtime.
 * </strong>
 *
 * <p>
 * In case the tests are run, these options must be added to the {@code maven-surefire-plugin}:
 * <pre>
 * {@code
 * <plugin>
 *     <artifactId>maven-surefire-plugin</artifactId>
 *     <version>2.22.1</version>
 *     <configuration>
 *         <argLine>
 *             --add-exports ...
 *             --add-opens ...
 *             --patch-module ...
 *             --add-modules ...
 *             --add-reads ...
 *         </argLine>
 *     </configuration>
 * </plugin>
 * }</pre>
 *
 * <p>
 * The specified configuration options for the Java module system at runtime
 * can also be added using the features of the {@link Module} class.
 *
 * <p>
 * In order the developer is relieved of the need to add the necessary options to the {@code maven-surefire-plugin} configuration,
 * the RxMicro framework provides a special {@link RxMicroTestsAnnotationProcessor} component that generates {@code ?TestFixer} classes
 * with all required exports.
 *
 * @author nedis
 * @see <a href="https://docs.rxmicro.io/latest/user-guide/testing.html#testing-how-it-works-section">
 *     Documentation -&gt; Testing -&gt; How It Works
 * </a>
 * @since 0.1
 */
public final class RxMicroTestsAnnotationProcessor extends BaseRxMicroAnnotationProcessor {

    /**
     * Default constructor for the {@code RxMicro Test Annotation Processor}.
     */
    public RxMicroTestsAnnotationProcessor() {
        super(new TestModuleClassStructuresBuilder());
    }

    @Override
    protected AnnotationProcessorType getAnnotationProcessorType() {
        return TESTS_COMPILE;
    }

    /**
     * @author nedis
     * @since 0.1
     */
    private static final class TestModuleClassStructuresBuilder extends AbstractModuleClassStructuresBuilder {

        private final Map<String, ClassStructure> testFixerMap = Map.of(
                "io.rxmicro.test.junit.RxMicroRestBasedMicroServiceTest",
                new RestBasedMicroServiceTestFixerClassStructure(),

                "io.rxmicro.test.junit.RxMicroIntegrationTest",
                new IntegrationTestFixerClassStructure(),

                "io.rxmicro.test.junit.RxMicroComponentTest",
                new ComponentTestFixerClassStructure()
        );

        private TestModuleClassStructuresBuilder() {
            injectDependencies(this, new FormatSourceCodeDependenciesModule());
        }

        @Override
        public String getBuilderName() {
            return "test-fixer-annotation-processor-module";
        }

        @Override
        public Set<String> getSupportedAnnotationTypes() {
            return testFixerMap.keySet();
        }

        @Override
        protected Set<? extends ClassStructure> buildClassStructures(final EnvironmentContext environmentContext,
                                                                     final Set<? extends TypeElement> annotations,
                                                                     final RoundEnvironment roundEnv) {
            if (environmentContext.getCurrentModule().isUnnamed()) {
                return Set.of();
            } else {
                return annotations.stream()
                        .map(a -> testFixerMap.get(a.getQualifiedName().toString()))
                        .collect(Collectors.toSet());
            }
        }

        @Override
        protected boolean isEnvironmentCustomizerMustBeGenerated() {
            return false;
        }
    }

    /**
     * @author nedis
     * @since 0.4
     */
    private abstract static class TestFixerClassStructure extends ClassStructure {

        protected abstract String getSimpleClassName();

        @Override
        public final String getTargetFullClassName() {
            return getEntryPointFullClassName(getSimpleClassName());
        }

        @Override
        public final Map<String, Object> getTemplateVariables() {
            return Map.of(
                    "PACKAGE_NAME", ENTRY_POINT_PACKAGE,
                    "JAVA_CLASS_NAME", getSimpleClassName()
            );
        }

        @Override
        public final ClassHeader getClassHeader() {
            final ClassHeader.Builder builder = newClassHeaderBuilder(ENTRY_POINT_PACKAGE);
            customizeClassHeader(builder);
            return builder
                    .addImports(Set.class)
                    .addStaticImport(Formats.class, "format")
                    .addStaticImport(ReflectionConstants.class, "RX_MICRO_REFLECTION_MODULE")
                    .addStaticImport(TestLoggers.class, "logInfoTestMessage")
                    .addStaticImport(ExCollections.class, "unmodifiableOrderedSet")
                    .build();
        }

        protected void customizeClassHeader(final ClassHeader.Builder builder) {
            // do nothing
        }
    }

    /**
     * @author nedis
     * @since 0.1
     */
    private static final class ComponentTestFixerClassStructure extends TestFixerClassStructure {

        @Override
        protected String getSimpleClassName() {
            return COMPONENT_TEST_FIXER;
        }

        @Override
        public String getTemplateName() {
            return "test/$$ComponentTestFixerTemplate.javaftl";
        }
    }

    /**
     * @author nedis
     * @since 0.1
     */
    private static final class RestBasedMicroServiceTestFixerClassStructure extends TestFixerClassStructure {

        @Override
        protected String getSimpleClassName() {
            return REST_BASED_MICRO_SERVICE_TEST_FIXER;
        }

        @Override
        public String getTemplateName() {
            return "test/$$RestBasedMicroServiceTestFixerTemplate.javaftl";
        }
    }

    /**
     * @author nedis
     * @since 0.1
     */
    private static final class IntegrationTestFixerClassStructure extends TestFixerClassStructure {

        @Override
        protected String getSimpleClassName() {
            return INTEGRATION_TEST_FIXER;
        }

        @Override
        public String getTemplateName() {
            return "test/$$IntegrationTestFixerTemplate.javaftl";
        }
    }
}
