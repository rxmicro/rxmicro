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

package io.rxmicro.annotation.processor;

import io.rxmicro.annotation.processor.common.BaseRxMicroAnnotationProcessor;
import io.rxmicro.annotation.processor.common.FormatSourceCodeDependenciesModule;
import io.rxmicro.annotation.processor.common.component.impl.AbstractModuleClassStructuresBuilder;
import io.rxmicro.annotation.processor.common.model.AnnotationProcessorType;
import io.rxmicro.annotation.processor.common.model.ClassHeader;
import io.rxmicro.annotation.processor.common.model.ClassStructure;
import io.rxmicro.annotation.processor.common.model.EnvironmentContext;
import io.rxmicro.common.util.Formats;
import io.rxmicro.runtime.detail.Runtimes;
import io.rxmicro.tool.common.Reflections;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.rxmicro.annotation.processor.common.model.AnnotationProcessorType.TESTS_COMPILE;
import static io.rxmicro.annotation.processor.common.model.ClassHeader.newClassHeaderBuilder;
import static io.rxmicro.annotation.processor.common.util.GeneratedClassNames.getEntryPointFullClassName;
import static io.rxmicro.annotation.processor.common.util.Injects.injectDependencies;
import static io.rxmicro.runtime.detail.Runtimes.ENTRY_POINT_PACKAGE;
import static io.rxmicro.tool.common.TestFixers.COMPONENT_TEST_FIXER;
import static io.rxmicro.tool.common.TestFixers.INTEGRATION_TEST_FIXER;
import static io.rxmicro.tool.common.TestFixers.REST_BASED_MICRO_SERVICE_TEST_FIXER;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class RxMicroTestsAnnotationProcessor extends BaseRxMicroAnnotationProcessor {

    public RxMicroTestsAnnotationProcessor() {
        super(new TestModuleClassStructuresBuilder());
    }

    @Override
    protected AnnotationProcessorType getAnnotationProcessorType() {
        return TESTS_COMPILE;
    }

    /**
     * @author nedis
     * @link https://rxmicro.io
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

        public TestModuleClassStructuresBuilder() {
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
            return annotations.stream()
                    .map(a -> testFixerMap.get(a.getQualifiedName().toString()))
                    .collect(Collectors.toSet());
        }
    }

    /**
     * @author nedis
     * @link https://rxmicro.io
     * @since 0.1
     */
    private static final class ComponentTestFixerClassStructure extends ClassStructure {

        @Override
        public String getTargetFullClassName() {
            return getEntryPointFullClassName(COMPONENT_TEST_FIXER);
        }

        @Override
        public String getTemplateName() {
            return "test/$$ComponentTestFixerTemplate.javaftl";
        }

        @Override
        public Map<String, Object> getTemplateVariables() {
            return Map.of(
                    "PACKAGE_NAME", ENTRY_POINT_PACKAGE,
                    "JAVA_CLASS_NAME", COMPONENT_TEST_FIXER
            );
        }

        @Override
        public ClassHeader getClassHeader() {
            return newClassHeaderBuilder(ENTRY_POINT_PACKAGE)
                    .addImports(Stream.class)
                    .addStaticImport(Formats.class, "format")
                    .addStaticImport(Runtimes.class, "getRuntimeModule")
                    .addStaticImport(Reflections.class, "getToolCommonModule")
                    .build();
        }
    }

    /**
     * @author nedis
     * @link https://rxmicro.io
     * @since 0.1
     */
    private static final class RestBasedMicroServiceTestFixerClassStructure extends ClassStructure {

        @Override
        public String getTargetFullClassName() {
            return getEntryPointFullClassName(REST_BASED_MICRO_SERVICE_TEST_FIXER);
        }

        @Override
        public String getTemplateName() {
            return "test/$$RestBasedMicroServiceTestFixerTemplate.javaftl";
        }

        @Override
        public Map<String, Object> getTemplateVariables() {
            return Map.of(
                    "PACKAGE_NAME", ENTRY_POINT_PACKAGE,
                    "JAVA_CLASS_NAME", REST_BASED_MICRO_SERVICE_TEST_FIXER
            );
        }

        @Override
        public ClassHeader getClassHeader() {
            return newClassHeaderBuilder(ENTRY_POINT_PACKAGE)
                    .addStaticImport(Formats.class, "format")
                    .addStaticImport(Runtimes.class, "getRuntimeModule")
                    .build();
        }
    }

    /**
     * @author nedis
     * @link https://rxmicro.io
     * @since 0.1
     */
    private static final class IntegrationTestFixerClassStructure extends ClassStructure {

        @Override
        public String getTargetFullClassName() {
            return getEntryPointFullClassName(INTEGRATION_TEST_FIXER);
        }

        @Override
        public String getTemplateName() {
            return "test/$$IntegrationTestFixerTemplate.javaftl";
        }

        @Override
        public Map<String, Object> getTemplateVariables() {
            return Map.of(
                    "PACKAGE_NAME", ENTRY_POINT_PACKAGE,
                    "JAVA_CLASS_NAME", INTEGRATION_TEST_FIXER
            );
        }

        @Override
        public ClassHeader getClassHeader() {
            return newClassHeaderBuilder(ENTRY_POINT_PACKAGE)
                    .addStaticImport(Formats.class, "format")
                    .addStaticImport(Runtimes.class, "getRuntimeModule")
                    .build();
        }
    }
}
