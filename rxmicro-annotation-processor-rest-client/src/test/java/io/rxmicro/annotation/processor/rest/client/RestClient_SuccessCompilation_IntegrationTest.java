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

package io.rxmicro.annotation.processor.rest.client;

import io.rxmicro.annotation.processor.common.BaseRxMicroAnnotationProcessor;
import io.rxmicro.annotation.processor.integration.test.AbstractRxMicroAnnotationProcessorIntegrationTest;
import io.rxmicro.annotation.processor.integration.test.config.ExcludeExample;
import io.rxmicro.annotation.processor.integration.test.config.IncludeExample;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.io.IOException;
import javax.annotation.processing.Processor;

import static io.rxmicro.annotation.processor.config.SupportedOptions.RX_MICRO_BUILD_UNNAMED_MODULE;
import static io.rxmicro.annotation.processor.integration.test.ExternalModule.EXTERNAL_REACTIVE_STREAMS_MODULE;
import static io.rxmicro.annotation.processor.integration.test.ExternalModule.EXTERNAL_REACTOR_CORE_MODULE;
import static io.rxmicro.annotation.processor.integration.test.ExternalModule.EXTERNAL_RX_JAVA_3_MODULE;
import static io.rxmicro.rest.client.RestClientFactory.REST_CLIENT_FACTORY_IMPL_CLASS_NAME;

/**
 * @author nedis
 * @since 0.7.2
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
final class RestClient_SuccessCompilation_IntegrationTest extends AbstractRxMicroAnnotationProcessorIntegrationTest {

    public RestClient_SuccessCompilation_IntegrationTest() {
        super(REST_CLIENT_FACTORY_IMPL_CLASS_NAME);
    }

    @Override
    protected Processor createAnnotationProcessor() {
        return new BaseRxMicroAnnotationProcessor(RestClientModuleClassStructuresBuilder.create());
    }

    @BeforeEach
    void beforeEach() {
        addExternalModule(EXTERNAL_REACTIVE_STREAMS_MODULE);
        addExternalModule(EXTERNAL_REACTOR_CORE_MODULE);
        addExternalModule(EXTERNAL_RX_JAVA_3_MODULE);
    }

    @Order(1)
    @ParameterizedTest
    @IncludeExample("io.rxmicro.examples.unnamed.module")
    @ArgumentsSource(AllInputPackagesArgumentsProvider.class)
    void Should_compile_unnamed_module_successful(final String packageName) throws IOException {
        addAggregator("$$EnvironmentCustomizer");
        addCompilerOption(RX_MICRO_BUILD_UNNAMED_MODULE, "true");
        shouldCompileAndGenerateClassesSuccessfully(packageName);
    }

    @Order(2)
    @ParameterizedTest
    @ExcludeExample("io.rxmicro.examples.unnamed.module")
    @ArgumentsSource(AllInputPackagesArgumentsProvider.class)
    void Should_compile_successful(final String packageName) throws IOException {
        shouldCompileAndGenerateClassesSuccessfully(packageName);
    }
}
