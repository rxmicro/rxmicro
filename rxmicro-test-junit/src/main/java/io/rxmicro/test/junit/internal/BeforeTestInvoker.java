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

import io.rxmicro.common.CheckedWrapperException;
import io.rxmicro.test.junit.BeforeIterationMethodSource;
import io.rxmicro.test.junit.BeforeTest;
import io.rxmicro.test.local.InvalidTestConfigException;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static io.rxmicro.tool.common.Reflections.invokeMethod;

/**
 * @author nedis
 * @since 0.1
 */
final class BeforeTestInvoker {

    private final Map<Method, AtomicInteger> methodInvocationCounter = new HashMap<>();

    void throwErrorIfFound(final ExtensionContext context) {
        final Method method = context.getRequiredTestMethod();
        final BeforeIterationMethodSource beforeIterationMethodSource = method.getAnnotation(BeforeIterationMethodSource.class);
        if (beforeIterationMethodSource != null) {
            throw new InvalidTestConfigException(
                    "'@?' annotation is redundant for '?.?()' test method. " +
                            "Remove redundant annotation!",
                    BeforeIterationMethodSource.class.getSimpleName(),
                    method.getDeclaringClass().getSimpleName(),
                    method.getName());
        }
        final BeforeTest beforeTest = method.getAnnotation(BeforeTest.class);
        if (beforeTest != null) {
            throw new InvalidTestConfigException(
                    "'@?' annotation is redundant for '?.?()' test method. " +
                            "Remove redundant annotation!",
                    BeforeTest.class.getSimpleName(),
                    method.getDeclaringClass().getSimpleName(),
                    method.getName());
        }
    }

    void invokeIfFound(final ExtensionContext context,
                       final List<Object> testInstances) {
        final Method method = context.getRequiredTestMethod();
        final AtomicInteger counter = methodInvocationCounter.computeIfAbsent(method,
                m -> new AtomicInteger(0));
        final int index = counter.getAndIncrement();
        final BeforeIterationMethodSource beforeIterationMethodSource = method.getAnnotation(BeforeIterationMethodSource.class);
        if (beforeIterationMethodSource != null) {
            validateRedundantAnnotation(method);
            invokeBeforeMethod(testInstances, beforeIterationMethodSource.methods()[index]);
        } else {
            final BeforeTest beforeTest = method.getAnnotation(BeforeTest.class);
            if (beforeTest != null) {
                invokeBeforeMethod(testInstances, beforeTest.method());
            }
        }
    }

    private void validateRedundantAnnotation(final Method method) {
        if (method.isAnnotationPresent(BeforeTest.class)) {
            throw new InvalidTestConfigException("Redundant annotation: '@?'. Remove it",
                    BeforeTest.class.getName());
        }
    }

    private void invokeBeforeMethod(final List<Object> testInstances,
                                    final String methodName) {
        try {
            invokeMethod(testInstances, methodName);
        } catch (final CheckedWrapperException ex) {
            if (ex.isCause(NoSuchMethodException.class)) {
                throw new InvalidTestConfigException(ex.getMessage());
            } else {
                throw ex;
            }
        }
    }
}
