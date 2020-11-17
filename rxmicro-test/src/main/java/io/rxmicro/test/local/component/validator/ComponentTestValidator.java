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

import io.rxmicro.test.BlockingHttpClient;
import io.rxmicro.test.internal.validator.impl.TestedComponentFieldValidator;
import io.rxmicro.test.local.InvalidTestConfigException;
import io.rxmicro.test.local.model.TestModel;

import java.lang.annotation.Annotation;
import java.util.Set;

import static io.rxmicro.test.local.component.RxMicroTestExtensions.validateUsingTestExtensions;

/**
 * @author nedis
 * @since 0.1
 */
public final class ComponentTestValidator extends CommonTestValidator {

    private final Set<Class<? extends Annotation>> supportedTestAnnotations;

    public ComponentTestValidator(final Set<Class<? extends Annotation>> supportedTestAnnotations) {
        this.supportedTestAnnotations = supportedTestAnnotations;
    }

    @Override
    protected void validateUsingSpecificRules(final TestModel testModel) {
        validateThatOnlyOneAnnotationExistsPerTestClass(testModel, supportedTestAnnotations);
        validateUsingTestExtensions(testModel, supportedTestAnnotations);
        if (!testModel.getBlockingHttpClients().isEmpty()) {
            throw new InvalidTestConfigException(
                    "Component test does not support '?' field. Remove this field from ? test class!",
                    BlockingHttpClient.class.getName(),
                    testModel.getTestClass().getName()
            );
        }
        if (testModel.isInstanceConfigsPresent() && testModel.isStaticConfigsPresent()) {
            throw new InvalidTestConfigException(
                    "Redundant static config(s): ?. Remove redundant config(s) from ? test class!",
                    testModel.getStaticConfigs(),
                    testModel.getTestClass().getName()
            );
        }
        new TestedComponentFieldValidator(testModel.getTestedComponentClass().orElseThrow()).validate(testModel.getTestedComponents());
    }
}
