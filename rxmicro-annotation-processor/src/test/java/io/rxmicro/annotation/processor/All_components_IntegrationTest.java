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

import io.rxmicro.annotation.processor.common.component.impl.AbstractProcessorComponent;
import io.rxmicro.annotation.processor.integration.test.AbstractRxMicroAnnotationProcessorIntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import javax.annotation.processing.Processor;
import java.io.IOException;

import static io.rxmicro.annotation.processor.common.SupportedOptions.RX_MICRO_LOG_LEVEL;
import static io.rxmicro.annotation.processor.integration.test.ExternalModule.EXTERNAL_MONGO_DB_BSON_MODULE;
import static io.rxmicro.annotation.processor.integration.test.ExternalModule.EXTERNAL_MONGO_DB_DRIVER_CORE_MODULE;
import static io.rxmicro.annotation.processor.integration.test.ExternalModule.EXTERNAL_MONGO_DB_REACTIVE_DRIVER_MODULE;
import static io.rxmicro.annotation.processor.integration.test.ExternalModule.EXTERNAL_NETTY_BUFFER_MODULE;
import static io.rxmicro.annotation.processor.integration.test.ExternalModule.EXTERNAL_NETTY_CODEC_HTTP_MODULE;
import static io.rxmicro.annotation.processor.integration.test.ExternalModule.EXTERNAL_NETTY_CODEC_MODULE;
import static io.rxmicro.annotation.processor.integration.test.ExternalModule.EXTERNAL_NETTY_COMMON_MODULE;
import static io.rxmicro.annotation.processor.integration.test.ExternalModule.EXTERNAL_NETTY_TRANSPORT_MODULE;
import static io.rxmicro.annotation.processor.integration.test.ExternalModule.EXTERNAL_R2DBC_POOL_MODULE;
import static io.rxmicro.annotation.processor.integration.test.ExternalModule.EXTERNAL_R2DBC_POSTGRESQL_MODULE;
import static io.rxmicro.annotation.processor.integration.test.ExternalModule.EXTERNAL_R2DBC_SPI_MODULE;
import static io.rxmicro.annotation.processor.integration.test.ExternalModule.EXTERNAL_REACTIVE_STREAMS_MODULE;
import static io.rxmicro.annotation.processor.integration.test.ExternalModule.EXTERNAL_REACTOR_CORE_MODULE;
import static io.rxmicro.cdi.BeanFactory.BEAN_FACTORY_IMPL_CLASS_NAME;
import static io.rxmicro.data.RepositoryFactory.REPOSITORY_FACTORY_IMPL_CLASS_NAME;
import static io.rxmicro.rest.client.RestClientFactory.REST_CLIENT_FACTORY_IMPL_CLASS_NAME;
import static io.rxmicro.rest.server.detail.component.RestControllerAggregator.REST_CONTROLLER_AGGREGATOR_IMPL_CLASS_NAME;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
final class All_components_IntegrationTest extends AbstractRxMicroAnnotationProcessorIntegrationTest {

    public All_components_IntegrationTest() {
        super(
                REST_CONTROLLER_AGGREGATOR_IMPL_CLASS_NAME,
                REST_CLIENT_FACTORY_IMPL_CLASS_NAME,
                BEAN_FACTORY_IMPL_CLASS_NAME,
                REPOSITORY_FACTORY_IMPL_CLASS_NAME
        );
        addCompilerOption(RX_MICRO_LOG_LEVEL, AbstractProcessorComponent.Level.DEBUG.name());
    }

    @BeforeEach
    void beforeEach() {
        addExternalModule(EXTERNAL_REACTIVE_STREAMS_MODULE);

        addExternalModule(EXTERNAL_REACTOR_CORE_MODULE);

        addExternalModule(EXTERNAL_R2DBC_SPI_MODULE);
        addExternalModule(EXTERNAL_R2DBC_POOL_MODULE);
        addExternalModule(EXTERNAL_R2DBC_POSTGRESQL_MODULE);

        addExternalModule(EXTERNAL_MONGO_DB_BSON_MODULE);
        addExternalModule(EXTERNAL_MONGO_DB_REACTIVE_DRIVER_MODULE);
        addExternalModule(EXTERNAL_MONGO_DB_DRIVER_CORE_MODULE);

        addExternalModule(EXTERNAL_NETTY_COMMON_MODULE);
        addExternalModule(EXTERNAL_NETTY_CODEC_HTTP_MODULE);
        addExternalModule(EXTERNAL_NETTY_CODEC_MODULE);
        addExternalModule(EXTERNAL_NETTY_BUFFER_MODULE);
        addExternalModule(EXTERNAL_NETTY_TRANSPORT_MODULE);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "io.rxmicro.examples.processor.all.components",
            "io.rxmicro.examples.processor.cdi",
            "io.rxmicro.examples.processor.microservice",
            "io.rxmicro.examples.processor.mongo",
            "io.rxmicro.examples.processor.r2dbc.postgresql",
            "io.rxmicro.examples.processor.restclient"
    })
    void verify(final String packageName) throws IOException {
        super.verifyAllClassesInPackage(packageName);
    }

    @Override
    protected Processor createAnnotationProcessor() {
        return new RxMicroAnnotationProcessor();
    }
}
