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
import io.rxmicro.annotation.processor.integration.test.config.IncludeExample;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import javax.annotation.processing.Processor;

import static io.rxmicro.annotation.processor.integration.test.ExternalModule.EXTERNAL_REACTIVE_STREAMS_MODULE;
import static io.rxmicro.annotation.processor.integration.test.ExternalModule.EXTERNAL_REACTOR_CORE_MODULE;
import static io.rxmicro.annotation.processor.integration.test.ExternalModule.EXTERNAL_RX_JAVA_3_MODULE;
import static io.rxmicro.common.RxMicroModule.RX_MICRO_REST_SERVER_EXCHANGE_JSON_MODULE;
import static io.rxmicro.common.RxMicroModule.RX_MICRO_VALIDATION_MODULE;

/**
 * @author nedis
 * @since 0.7.2
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
final class RestControllerFailedCompilationIntegrationTest extends AbstractRxMicroAnnotationProcessorIntegrationTest {

    @Override
    protected Processor createAnnotationProcessor() {
        return new BaseRxMicroAnnotationProcessor(RestServerModuleClassStructuresBuilder.create());
    }

    @BeforeEach
    void beforeEach() {
        addExternalModule(EXTERNAL_REACTIVE_STREAMS_MODULE);
        addExternalModule(EXTERNAL_REACTOR_CORE_MODULE);
        addExternalModule(EXTERNAL_RX_JAVA_3_MODULE);
    }

    @Order(1)
    @ParameterizedTest
    @IncludeExample("error.controller")
    @ArgumentsSource(AllErrorPackagesArgumentsProvider.class)
    void Should_throw_compilation_error_if_RestController_has_invalid_declaration(final String classpathResource) {
        shouldThrowCompilationError(classpathResource, RX_MICRO_REST_SERVER_EXCHANGE_JSON_MODULE, RX_MICRO_VALIDATION_MODULE);
    }

    @Order(2)
    @ParameterizedTest
    @IncludeExample("error.method")
    @ArgumentsSource(AllErrorPackagesArgumentsProvider.class)
    void Should_throw_compilation_error_if_RestController_method_has_invalid_declaration(final String classpathResource) {
        shouldThrowCompilationError(classpathResource, RX_MICRO_REST_SERVER_EXCHANGE_JSON_MODULE, RX_MICRO_VALIDATION_MODULE);
    }

    @Order(3)
    @ParameterizedTest
    @IncludeExample("error.validation")
    @ArgumentsSource(AllErrorPackagesArgumentsProvider.class)
    void Should_throw_compilation_error_if_Request_model_contains_invalid_constraints(final String classpathResource) {
        shouldThrowCompilationError(classpathResource, RX_MICRO_REST_SERVER_EXCHANGE_JSON_MODULE, RX_MICRO_VALIDATION_MODULE);
    }
}
