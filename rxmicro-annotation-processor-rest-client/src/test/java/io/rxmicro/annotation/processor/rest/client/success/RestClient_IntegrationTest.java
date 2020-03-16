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

package io.rxmicro.annotation.processor.rest.client.success;

import io.rxmicro.annotation.processor.rest.client.AbstractRestClientAnnotationProcessorIntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.io.IOException;

import static io.rxmicro.annotation.processor.integration.test.ExternalModule.EXTERNAL_REACTIVE_STREAMS_MODULE;
import static io.rxmicro.annotation.processor.integration.test.ExternalModule.EXTERNAL_REACTOR_CORE_MODULE;
import static io.rxmicro.annotation.processor.integration.test.ExternalModule.EXTERNAL_RX_JAVA_3_MODULE;

/**
 * @author nedis
 * @link https://rxmicro.io
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
final class RestClient_IntegrationTest extends AbstractRestClientAnnotationProcessorIntegrationTest {

    @BeforeEach
    void beforeEach() {
        addExternalModule(EXTERNAL_REACTIVE_STREAMS_MODULE);
        addExternalModule(EXTERNAL_REACTOR_CORE_MODULE);
        addExternalModule(EXTERNAL_RX_JAVA_3_MODULE);
    }

    @ParameterizedTest
    @ArgumentsSource(AllInputPackagesArgumentsProvider.class)
    void verify(final String packageName) throws IOException {
        super.verifyAllClassesInPackage(packageName);
    }
}
