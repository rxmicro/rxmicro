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

package io.rxmicro.test.local.component.validator;

import io.rxmicro.common.CommonConstants;
import io.rxmicro.http.ProtocolSchema;
import io.rxmicro.rest.server.detail.component.AbstractRestController;
import io.rxmicro.test.local.BlockingHttpClientConfig;
import io.rxmicro.test.local.InvalidTestConfigException;
import io.rxmicro.test.local.model.TestModel;

import java.lang.annotation.Annotation;
import java.util.Set;

import static io.rxmicro.test.local.component.RxMicroTestExtensions.validateUsingTestExtensions;
import static io.rxmicro.test.local.util.GeneratedClasses.isClassGenerated;

/**
 * @author nedis
 * @since 0.1
 */
public final class RestBasedMicroServiceTestValidator extends CommonTestValidator {

    private final Set<Class<? extends Annotation>> supportedTestAnnotations;

    public RestBasedMicroServiceTestValidator(final Set<Class<? extends Annotation>> supportedTestAnnotations) {
        this.supportedTestAnnotations = supportedTestAnnotations;
    }

    public void validate(final TestModel testModel,
                         final Class<?>... restControllerClasses) {
        validate(testModel);
        for (final Class<?> restControllerClass : restControllerClasses) {
            if (!isClassGenerated(restControllerClass, "?.$$?", AbstractRestController.class)) {
                throw new InvalidTestConfigException(
                        "'?' is not REST controller class or the the RxMicro Annotation Processor does not generate wrapper for " +
                                "'?' class. Set valid REST controller class for the REST based micro service test: '?' or " +
                                "recompile the project with activated RxMicro Annotation Processor! " +
                                "Read more at https://docs.rxmicro.io/latest/user-guide/quick-start.html#compiling_the_project",
                        restControllerClass.getName(),
                        restControllerClass.getName(),
                        testModel.getTestClass().getName()
                );
            }
        }
    }

    public void validate(final TestModel testModel,
                         final BlockingHttpClientConfig config) {
        if (!CommonConstants.LOCALHOST.equals(config.getHost())) {
            throw new InvalidTestConfigException(
                    "For REST based micro service tests HTTP server host must be '?' only! Fix setting for ? test class!",
                    CommonConstants.LOCALHOST, testModel.getTestClass().getName()
            );
        }
        if (config.getSchema() != ProtocolSchema.HTTP) {
            throw new InvalidTestConfigException(
                    "For REST based micro service tests HTTP server supports the '?' schema only! Fix setting for ? test class!",
                    ProtocolSchema.HTTP, testModel.getTestClass().getName()
            );
        }
    }

    @Override
    protected void validateUsingSpecificRules(final TestModel testModel) {
        validateThatOnlyOneAnnotationExistsPerTestClass(testModel, supportedTestAnnotations);
        validateUsingTestExtensions(testModel, supportedTestAnnotations);
        if (testModel.isInstanceConfigsPresent()) {
            throw new InvalidTestConfigException(
                    "Rest-based microservice test supports static configs only! " +
                            "Remove not static configs or add the missing 'static' modifier for ? from '?' test class!",
                    testModel.getInstanceConfigs(),
                    testModel.getTestClass().getName()
            );
        }
    }


}
