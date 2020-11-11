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
import io.rxmicro.common.util.Formats;
import io.rxmicro.runtime.detail.RxMicroRuntime;
import io.rxmicro.tool.common.Reflections;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
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
 * @author nedis
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
                    .addStaticImport(Formats.class, "format")
                    .addStaticImport(RxMicroRuntime.class, "getRuntimeModule")
                    .build();
        }

        protected abstract void customizeClassHeader(ClassHeader.Builder builder);
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
        protected void customizeClassHeader(final ClassHeader.Builder builder) {
            builder
                    .addImports(Stream.class)
                    .addStaticImport(Reflections.class, "getToolCommonModule");
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
        protected void customizeClassHeader(final ClassHeader.Builder builder) {
            // do nothing
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
        protected void customizeClassHeader(final ClassHeader.Builder builder) {
            // do nothing
        }

        @Override
        public String getTemplateName() {
            return "test/$$IntegrationTestFixerTemplate.javaftl";
        }
    }
}
