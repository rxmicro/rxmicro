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

package io.rxmicro.annotation.processor.rest.server;

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
import static io.rxmicro.annotation.processor.integration.test.ExternalModule.EXTERNAL_NETTY_BUFFER_MODULE;
import static io.rxmicro.annotation.processor.integration.test.ExternalModule.EXTERNAL_NETTY_CODEC_HTTP_MODULE;
import static io.rxmicro.annotation.processor.integration.test.ExternalModule.EXTERNAL_NETTY_CODEC_MODULE;
import static io.rxmicro.annotation.processor.integration.test.ExternalModule.EXTERNAL_NETTY_COMMON_MODULE;
import static io.rxmicro.annotation.processor.integration.test.ExternalModule.EXTERNAL_NETTY_HANDLER_MODULE;
import static io.rxmicro.annotation.processor.integration.test.ExternalModule.EXTERNAL_NETTY_TRANSPORT_MODULE;
import static io.rxmicro.annotation.processor.integration.test.ExternalModule.EXTERNAL_REACTIVE_STREAMS_MODULE;
import static io.rxmicro.annotation.processor.integration.test.ExternalModule.EXTERNAL_REACTOR_CORE_MODULE;
import static io.rxmicro.annotation.processor.integration.test.ExternalModule.EXTERNAL_RX_JAVA_3_MODULE;
import static io.rxmicro.rest.server.detail.component.RestControllerAggregator.REST_CONTROLLER_AGGREGATOR_IMPL_CLASS_NAME;

/**
 * @author nedis
 * @since 0.7.2
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
final class RestController_SuccessCompilation_IntegrationTest extends AbstractRxMicroAnnotationProcessorIntegrationTest {

    public RestController_SuccessCompilation_IntegrationTest() {
        super(REST_CONTROLLER_AGGREGATOR_IMPL_CLASS_NAME);
    }

    @Override
    protected final Processor createAnnotationProcessor() {
        return new BaseRxMicroAnnotationProcessor(RestServerModuleClassStructuresBuilder.create());
    }

    @BeforeEach
    void beforeEach() {
        addExternalModule(EXTERNAL_NETTY_COMMON_MODULE);
        addExternalModule(EXTERNAL_NETTY_CODEC_HTTP_MODULE);
        addExternalModule(EXTERNAL_NETTY_CODEC_MODULE);
        addExternalModule(EXTERNAL_NETTY_BUFFER_MODULE);
        addExternalModule(EXTERNAL_NETTY_HANDLER_MODULE);
        addExternalModule(EXTERNAL_NETTY_TRANSPORT_MODULE);

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
        removeFromModulePath("rxmicro-documentation-asciidoctor");
        shouldCompileAndGenerateClassesSuccessfully(packageName);
    }

    @Order(2)
    @ParameterizedTest
    @IncludeExample("io.rxmicro.examples.rest.controller.extendable.model")
    @IncludeExample("io.rxmicro.examples.validation.server.extendable.model")
    @ArgumentsSource(AllInputPackagesArgumentsProvider.class)
    void Should_compile_extendable_model_successful(final String packageName) throws IOException {
        addAggregator("$$EnvironmentCustomizer");
        shouldCompileAndGenerateClassesSuccessfully(packageName);
    }

    @Order(3)
    @ParameterizedTest
    @ExcludeExample("io.rxmicro.examples.unnamed.module")
    @ExcludeExample("io.rxmicro.examples.rest.controller.extendable.model")
    @ExcludeExample("io.rxmicro.examples.validation.server.extendable.model")
    @ArgumentsSource(AllInputPackagesArgumentsProvider.class)
    void Should_compile_successful(final String packageName) throws IOException {
        shouldCompileAndGenerateClassesSuccessfully(packageName);
    }
}
