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

package io.rxmicro.test.junit.internal;

import io.rxmicro.test.junit.RxMicroComponentTest;
import io.rxmicro.test.junit.RxMicroIntegrationTest;
import io.rxmicro.test.junit.RxMicroRestBasedMicroServiceTest;
import io.rxmicro.test.local.InvalidTestConfigException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Set;

import static io.rxmicro.tool.common.Reflections.getFieldValue;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
final class TestObjects {

    static final Set<Class<? extends Annotation>> SUPPORTED_TEST_ANNOTATIONS = Set.of(
            RxMicroIntegrationTest.class,
            RxMicroRestBasedMicroServiceTest.class,
            RxMicroComponentTest.class
    );

    static Class<?> getOwnerTestClass(final ExtensionContext context) {
        Class<?> testClass = context.getRequiredTestClass();
        if (testClass.isMemberClass()) {
            if (testClass.isAnnotationPresent(Nested.class)) {
                testClass = testClass.getDeclaringClass();
                if (testClass.isAnnotationPresent(Nested.class)) {
                    throw new InvalidTestConfigException(
                            "Supported ? classes with level 1 only!", Nested.class.getName()
                    );
                }
            } else {
                throw new InvalidTestConfigException(
                        "Missing '?' annotation for '?' class",
                        Nested.class.getName(), testClass.getName()
                );
            }
        }
        return testClass;
    }

    static List<Object> getTestInstances(final ExtensionContext context) {
        final Object testInstance = context.getRequiredTestInstance();
        if (testInstance.getClass().isMemberClass()) {
            return List.of(testInstance, getFieldValue(testInstance, "this$0"));
        } else {
            return List.of(testInstance);
        }
    }

    private TestObjects() {
    }
}
