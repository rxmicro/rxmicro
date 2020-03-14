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

import io.rxmicro.test.BlockingHttpClient;
import io.rxmicro.test.internal.CommonTestValidator;
import io.rxmicro.test.internal.validator.impl.TestedComponentFieldValidator;
import io.rxmicro.test.local.InvalidTestConfigException;
import io.rxmicro.test.local.model.TestModel;

import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public final class ComponentTestValidator extends CommonTestValidator {

    public ComponentTestValidator(final Set<Class<? extends Annotation>> supportedTestAnnotations) {
        super(supportedTestAnnotations);
    }

    @Override
    public void validate(final TestModel testModel) {
        super.validate(testModel);
        if (!testModel.getBlockingHttpClients().isEmpty()) {
            throw new InvalidTestConfigException(
                    "Component test does not support '?' field. Remove this field!",
                    BlockingHttpClient.class.getName()
            );
        }
        if (testModel.isInstanceConfigsPresent() && testModel.isStaticConfigsPresent()) {
            throw new InvalidTestConfigException(
                    "Redundant static config(s): ?. Remove redundant config(s)",
                    testModel.getStaticConfigs()
            );
        }

        // This check is redundant for CDI beans testing:
        /*if (testModel.getTestedComponents().isEmpty()) {
            final Class<?> testedComponentClass = testModel.getTestedComponentClass();
            throw new InvalidTestConfigException("Missing tested component field. " +
                    "Add 'private ? ?;' to the component test class: '?'",
                    testedComponentClass.getName(),
                    unCapitalize(testedComponentClass.getSimpleName()),
                    testModel.getTestClass().getName()
            );
        }*/
        new TestedComponentFieldValidator(testModel.getTestedComponentClass().orElseThrow())
                .validate(testModel.getTestedComponents());
    }
}
