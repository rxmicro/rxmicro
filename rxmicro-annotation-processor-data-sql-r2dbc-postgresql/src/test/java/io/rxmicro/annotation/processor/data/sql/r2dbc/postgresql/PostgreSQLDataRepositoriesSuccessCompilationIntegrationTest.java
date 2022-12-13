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

package io.rxmicro.annotation.processor.data.sql.r2dbc.postgresql;

import io.rxmicro.annotation.processor.common.BaseRxMicroAnnotationProcessor;
import io.rxmicro.annotation.processor.integration.test.AbstractRxMicroAnnotationProcessorIntegrationTest;
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

import static io.rxmicro.annotation.processor.integration.test.ExternalModule.EXTERNAL_NETTY_BUFFER_MODULE;
import static io.rxmicro.annotation.processor.integration.test.ExternalModule.EXTERNAL_NETTY_COMMON_MODULE;
import static io.rxmicro.annotation.processor.integration.test.ExternalModule.EXTERNAL_NETTY_TRANSPORT_MODULE;
import static io.rxmicro.annotation.processor.integration.test.ExternalModule.EXTERNAL_R2DBC_POOL_MODULE;
import static io.rxmicro.annotation.processor.integration.test.ExternalModule.EXTERNAL_R2DBC_POSTGRESQL_MODULE;
import static io.rxmicro.annotation.processor.integration.test.ExternalModule.EXTERNAL_R2DBC_SPI_MODULE;
import static io.rxmicro.annotation.processor.integration.test.ExternalModule.EXTERNAL_REACTIVE_STREAMS_MODULE;
import static io.rxmicro.annotation.processor.integration.test.ExternalModule.EXTERNAL_REACTOR_CORE_MODULE;
import static io.rxmicro.annotation.processor.integration.test.ExternalModule.EXTERNAL_RX_JAVA_3_MODULE;

/**
 * @author nedis
 * @since 0.7.2
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
final class PostgreSQLDataRepositoriesSuccessCompilationIntegrationTest extends AbstractRxMicroAnnotationProcessorIntegrationTest {

    @Override
    protected Processor createAnnotationProcessor() {
        return new BaseRxMicroAnnotationProcessor(PostgreSQLModuleClassStructuresBuilder.create());
    }

    @BeforeEach
    void beforeEach() {
        addExternalModule(EXTERNAL_R2DBC_SPI_MODULE);
        addExternalModule(EXTERNAL_R2DBC_POSTGRESQL_MODULE);
        addExternalModule(EXTERNAL_R2DBC_POOL_MODULE);

        addExternalModule(EXTERNAL_REACTIVE_STREAMS_MODULE);
        addExternalModule(EXTERNAL_REACTOR_CORE_MODULE);
        addExternalModule(EXTERNAL_RX_JAVA_3_MODULE);

        addExternalModule(EXTERNAL_NETTY_COMMON_MODULE);
        addExternalModule(EXTERNAL_NETTY_BUFFER_MODULE);
        addExternalModule(EXTERNAL_NETTY_TRANSPORT_MODULE);
    }

    @Order(1)
    @ParameterizedTest
    @ArgumentsSource(AllInputPackagesArgumentsProvider.class)
    void Should_compile_successful(final String packageName) throws IOException {
        shouldCompileAndGenerateClassesSuccessfully(packageName);
    }
}
