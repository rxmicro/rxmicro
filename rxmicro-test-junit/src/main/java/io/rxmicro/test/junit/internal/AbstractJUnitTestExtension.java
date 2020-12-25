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

package io.rxmicro.test.junit.internal;

import io.rxmicro.rest.server.local.component.ServerConfigurationResetController;
import io.rxmicro.test.junit.RxMicroComponentTest;
import io.rxmicro.test.junit.RxMicroIntegrationTest;
import io.rxmicro.test.junit.RxMicroRestBasedMicroServiceTest;

import java.lang.annotation.Annotation;
import java.util.ServiceLoader;
import java.util.Set;

import static io.rxmicro.runtime.local.Implementations.getImplementation;

/**
 * @author nedis
 * @since 0.7
 */
abstract class AbstractJUnitTestExtension {

    static final Set<Class<? extends Annotation>> SUPPORTED_TEST_ANNOTATIONS = Set.of(
            RxMicroIntegrationTest.class,
            RxMicroRestBasedMicroServiceTest.class,
            RxMicroComponentTest.class
    );

    private final BeforeTestInvoker beforeTestInvoker = new BeforeTestInvoker();

    BeforeTestInvoker getBeforeTestInvoker() {
        return beforeTestInvoker;
    }

    void resetServerConfigurationIfPossible() {
        final ServerConfigurationResetController serverConfigurationResetController =
                getImplementation(ServerConfigurationResetController.class, false, ServiceLoader::load);
        if (serverConfigurationResetController != null) {
            serverConfigurationResetController.resetConfiguration();
        }
    }
}
