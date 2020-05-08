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
import io.rxmicro.test.internal.CommonTestValidator;
import io.rxmicro.test.local.BlockingHttpClientConfig;
import io.rxmicro.test.local.InvalidTestConfigException;
import io.rxmicro.test.local.model.TestModel;

import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * @author nedis
 * @since 0.1
 */
public final class IntegrationTestValidator extends CommonTestValidator {

    public IntegrationTestValidator(final Set<Class<? extends Annotation>> supportedTestAnnotations) {
        super(supportedTestAnnotations);
    }

    @Override
    public void validate(final TestModel testModel) {
        super.validate(testModel);
        if (testModel.isStaticConfigsPresent() || testModel.isInstanceConfigsPresent()) {
            throw new InvalidTestConfigException(
                    "Integration test does not support custom configs. " +
                            "Remove all fields annotated by '@?' annotation!",
                    WithConfig.class.getName()
            );
        }
        if (!testModel.getHttpClientFactories().isEmpty() ||
                !testModel.getSqlConnectionPools().isEmpty() ||
                !testModel.getRestClients().isEmpty() ||
                !testModel.getMongoDataBases().isEmpty() ||
                !testModel.getRepositories().isEmpty() ||
                !testModel.getTestedComponents().isEmpty() ||
                !testModel.getUserCreatedComponents().isEmpty()) {
            throw new InvalidTestConfigException(
                    "Integration test does not support alternatives. " +
                            "Remove all fields annotated by '@?' annotation!",
                    Alternative.class.getName()
            );
        }
    }

    public void validate(final BlockingHttpClientConfig config) {
        if (!config.isFollowRedirects()) {
            throw new InvalidTestConfigException(
                    "For integration tests blocking HTTP client must support follow redirects!"
            );
        }
    }
}
