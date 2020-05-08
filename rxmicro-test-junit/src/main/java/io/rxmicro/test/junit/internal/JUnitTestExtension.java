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

import io.rxmicro.test.junit.RxMicroComponentTest;
import io.rxmicro.test.junit.RxMicroIntegrationTest;
import io.rxmicro.test.junit.RxMicroRestBasedMicroServiceTest;
import io.rxmicro.test.local.InvalidTestConfigException;
import io.rxmicro.test.local.component.TestExtension;
import io.rxmicro.test.local.model.TestModel;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.Extension;

import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * @author nedis
 * @since 0.1
 */
public final class JUnitTestExtension implements TestExtension {

    @Override
    public void validate(final TestModel testModel,
                         final Set<Class<? extends Annotation>> supportedRxMicroTestAnnotations) {
        final ExtendWith[] extendWiths = testModel.getTestClass().getAnnotationsByType(ExtendWith.class);
        for (final ExtendWith extendWith : extendWiths) {
            for (final Class<? extends Extension> extension : extendWith.value()) {
                if (extension == RxMicroComponentTestExtension.class) {
                    throw new InvalidTestConfigException(
                            "Unsupported test extension: '?'. Use '@?' instead!",
                            RxMicroComponentTestExtension.class.getName(),
                            RxMicroComponentTest.class.getName()
                    );
                } else if (extension == RxMicroIntegrationTestExtension.class) {
                    throw new InvalidTestConfigException(
                            "Unsupported test extension: '?'. Use '@?' instead!",
                            RxMicroIntegrationTestExtension.class.getName(),
                            RxMicroIntegrationTest.class.getName()
                    );
                } else if (extension == RxMicroRestBasedMicroServiceTestExtension.class) {
                    throw new InvalidTestConfigException(
                            "Unsupported test extension: '?'. Use '@?' instead!",
                            RxMicroRestBasedMicroServiceTestExtension.class.getName(),
                            RxMicroRestBasedMicroServiceTest.class.getName()
                    );
                }
            }
        }
    }

    @Override
    public Set<Class<? extends Annotation>> supportedPerClassAnnotations() {
        return Set.of();
    }
}
