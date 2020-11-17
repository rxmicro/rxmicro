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

package io.rxmicro.test.dbunit.local.component.validator;

import io.rxmicro.config.Config;
import io.rxmicro.test.Alternative;
import io.rxmicro.test.WithConfig;
import io.rxmicro.test.dbunit.TestDatabaseConfig;
import io.rxmicro.test.local.InvalidTestConfigException;
import io.rxmicro.test.local.component.validator.CommonTestValidator;
import io.rxmicro.test.local.model.TestModel;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;

/**
 * @author nedis
 * @since 0.7
 */
public final class DBUnitTestValidator extends CommonTestValidator {

    private static final Set<Class<? extends Config>> SUPPORTED_CONFIG_CLASSES = Set.of(
            TestDatabaseConfig.class
    );

    @Override
    protected void validateUsingSpecificRules(final TestModel testModel) {
        if (testModel.isStaticConfigsPresent()) {
            validateSupportedConfigClasses(testModel, testModel.getStaticConfigs());
        }
        if (testModel.isInstanceConfigsPresent()) {
            validateSupportedConfigClasses(testModel, testModel.getInstanceConfigs());
        }
        if (!testModel.getHttpClientFactories().isEmpty() ||
                !testModel.getSqlConnectionPools().isEmpty() ||
                !testModel.getRestClients().isEmpty() ||
                !testModel.getMongoDataBases().isEmpty() ||
                !testModel.getRepositories().isEmpty() ||
                !testModel.getTestedComponents().isEmpty() ||
                !testModel.getUserCreatedComponents().isEmpty()) {
            throw new InvalidTestConfigException(
                    "DBUnit test does not support alternatives. " +
                            "Remove all fields annotated by '@?' annotation from '?' test class!",
                    Alternative.class.getName(),
                    testModel.getTestClass().getName()
            );
        }
    }

    private void validateSupportedConfigClasses(final TestModel testModel,
                                                final List<Field> configFields) {
        for (final Field field : configFields) {
            if (!SUPPORTED_CONFIG_CLASSES.contains(field.getType())) {
                throw new InvalidTestConfigException(
                        "DBUnit test does not support custom configs except ? ones. " +
                                "Remove field annotated by '@?' annotation from '?' test class!",
                        SUPPORTED_CONFIG_CLASSES,
                        WithConfig.class.getName(),
                        testModel.getTestClass().getName()
                );
            }
        }
    }
}
