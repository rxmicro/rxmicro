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

import io.rxmicro.test.Alternative;
import io.rxmicro.test.WithConfig;
import io.rxmicro.test.local.BlockingHttpClientConfig;
import io.rxmicro.test.local.InvalidTestConfigException;
import io.rxmicro.test.local.model.BaseTestConfig;
import io.rxmicro.test.local.model.TestModel;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;

import static io.rxmicro.test.local.component.RxMicroTestExtensions.validateUsingTestExtensions;

/**
 * @author nedis
 * @since 0.1
 */
public final class IntegrationTestValidator extends CommonTestValidator {

    private final Set<Class<? extends Annotation>> supportedTestAnnotations;

    public IntegrationTestValidator(final Set<Class<? extends Annotation>> supportedTestAnnotations) {
        this.supportedTestAnnotations = supportedTestAnnotations;
    }

    public void validate(final TestModel testModel,
                         final BlockingHttpClientConfig config) {
        if (!config.isFollowRedirects()) {
            throw new InvalidTestConfigException(
                    "For integration tests blocking HTTP client must support follow redirects! Fix setting for ? test class!",
                    testModel.getTestClass().getName()
            );
        }
    }

    @Override
    protected void validateUsingSpecificRules(final TestModel testModel) {
        validateThatOnlyOneAnnotationExistsPerTestClass(testModel, supportedTestAnnotations);
        validateUsingTestExtensions(testModel, supportedTestAnnotations);
        if (testModel.isStaticConfigsPresent()) {
            validateSupportedConfigClasses(testModel, testModel.getStaticConfigs());
        }
        if (testModel.isInstanceConfigsPresent()) {
            validateSupportedConfigClasses(testModel, testModel.getInstanceConfigs());
        }
        final boolean[] emptyCollections = {
                testModel.getHttpClientFactories().isEmpty(),
                testModel.getSqlConnectionPools().isEmpty(),
                testModel.getRestClients().isEmpty(),
                testModel.getMongoDataBases().isEmpty(),
                testModel.getRepositories().isEmpty(),
                testModel.getTestedComponents().isEmpty(),
                testModel.getUserCreatedComponents().isEmpty()
        };
        for (final boolean emptyCollection : emptyCollections) {
            if (!emptyCollection) {
                throw new InvalidTestConfigException(
                        "Integration test does not support alternatives. " +
                                "Remove all fields annotated by '@?' annotation from '?' test class!",
                        Alternative.class.getName(),
                        testModel.getTestClass().getName()
                );
            }
        }
    }

    private void validateSupportedConfigClasses(final TestModel testModel,
                                                final List<Field> configFields) {
        for (final Field field : configFields) {
            if (!BaseTestConfig.class.isAssignableFrom(field.getType())) {
                throw new InvalidTestConfigException(
                        "Integration test does not support custom configs. " +
                                "Remove all fields annotated by '@?' annotation from '?' test class!",
                        WithConfig.class.getName(),
                        testModel.getTestClass().getName()
                );
            }
        }
    }
}
