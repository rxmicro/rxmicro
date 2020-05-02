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

package io.rxmicro.test.mockito.junit.internal;

import io.rxmicro.test.local.InvalidTestConfigException;
import io.rxmicro.test.local.component.TestExtension;
import io.rxmicro.test.local.model.TestModel;
import io.rxmicro.test.mockito.junit.InitMocks;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.Extension;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class MockitoTestExtension implements TestExtension {

    @Override
    public void validate(final TestModel testModel,
                         final Set<Class<? extends Annotation>> supportedRxMicroTestAnnotations) {
        if (testModel.getMockTestFields().stream().anyMatch(f -> f.isAnnotationPresent(Mock.class))) {
            Annotation initMocksAnnotation = null;
            for (final Annotation annotation : testModel.getTestClass().getAnnotations()) {
                if (isInitMocksAnnotation(annotation)) {
                    initMocksAnnotation = annotation;
                } else if (supportedRxMicroTestAnnotations.contains(annotation.annotationType()) &&
                        initMocksAnnotation == null) {
                    throw new InvalidTestConfigException(
                            "'@?' must be added before '@?' annotation for class: '?'",
                            InitMocks.class.getName(),
                            annotation.annotationType().getName(),
                            testModel.getTestClass().getName()
                    );
                }
            }
            if (initMocksAnnotation == null) {
                throw new InvalidTestConfigException(
                        "'?' class must be annotated by '@?' annotation!",
                        testModel.getTestClass().getName(),
                        InitMocks.class.getName()
                );
            }
        }
    }

    @Override
    public Set<Class<? extends Annotation>> supportedPerClassAnnotations() {
        return Set.of(InitMocks.class);
    }

    private boolean isInitMocksAnnotation(final Annotation annotation) {
        if (annotation.annotationType() == InitMocks.class) {
            return true;
        }
        if (annotation.annotationType() == ExtendWith.class) {
            final ExtendWith extendWith = (ExtendWith) annotation;
            for (final Class<? extends Extension> extensionClass : extendWith.value()) {
                if (extensionClass == MockitoExtension.class) {
                    throw new InvalidTestConfigException(
                            "Unsupported test extension: '?'. Use '@?' instead!",
                            MockitoExtension.class.getName(),
                            InitMocks.class.getName()
                    );
                }
            }
        }
        return false;
    }
}
