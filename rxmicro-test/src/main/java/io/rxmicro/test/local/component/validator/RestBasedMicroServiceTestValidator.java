/*
 * Copyright (c) 2020. http://rxmicro.io
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

package io.rxmicro.test.local.component.validator;

import io.rxmicro.http.ProtocolSchema;
import io.rxmicro.rest.server.detail.component.AbstractRestController;
import io.rxmicro.test.internal.CommonTestValidator;
import io.rxmicro.test.local.BlockingHttpClientConfig;
import io.rxmicro.test.local.InvalidTestConfigException;
import io.rxmicro.test.local.model.TestModel;

import java.lang.annotation.Annotation;
import java.util.Set;

import static io.rxmicro.test.local.util.GeneratedClasses.isClassGenerated;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public final class RestBasedMicroServiceTestValidator extends CommonTestValidator {

    public RestBasedMicroServiceTestValidator(final Set<Class<? extends Annotation>> supportedTestAnnotations) {
        super(supportedTestAnnotations);
    }

    public void validate(final TestModel testModel,
                         final Class<?>[] restControllerClasses) {
        validate(testModel);
        validate(testModel.getTestClass(), restControllerClasses);
        if (testModel.isInstanceConfigsPresent()) {
            throw new InvalidTestConfigException("Rest-based microservice test supports static configs only! " +
                    "Remove not static configs or add the missing 'static' modifier for ?",
                    testModel.getInstanceConfigs());
        }
    }

    private void validate(final Class<?> testClass,
                          final Class<?>[] restControllerClasses) {
        for (final Class<?> restControllerClass : restControllerClasses) {
            if (!isClassGenerated(restControllerClass, "?.$$?", AbstractRestController.class)) {
                throw new InvalidTestConfigException(
                        "'?' is not REST controller class. " +
                                "Set valid REST controller class for REST based micro service test: '?'",
                        restControllerClass.getName(),
                        testClass.getName()
                );
            }
        }
    }

    public void validate(final BlockingHttpClientConfig config) {
        if (!"localhost".equals(config.getHost())) {
            throw new InvalidTestConfigException(
                    "For REST based micro service tests HTTP server host must be 'localhost' only!"
            );
        }
        if (config.getSchema() != ProtocolSchema.http) {
            throw new InvalidTestConfigException(
                    "For REST based micro service tests HTTP server supports the 'http' schema only!"
            );
        }
    }
}
