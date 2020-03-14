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

package io.rxmicro.test.local.component;

import io.rxmicro.test.local.model.TestModel;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.ServiceLoader;
import java.util.Set;

import static java.util.Collections.unmodifiableSet;
import static java.util.stream.Collectors.toSet;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public final class TestExtensions {

    private final static Set<TestExtension> TEST_EXTENSIONS;

    static {
        final Set<TestExtension> testExtensions = new HashSet<>();
        for (final TestExtension testExtension : ServiceLoader.load(TestExtension.class)) {
            testExtensions.add(testExtension);
        }
        TEST_EXTENSIONS = unmodifiableSet(testExtensions);
    }

    public static void validateUsingTestExtensions(final TestModel testModel,
                                                   final Set<Class<? extends Annotation>> supportedRxMicroTestAnnotations) {
        TEST_EXTENSIONS.forEach(ex -> ex.validate(testModel, supportedRxMicroTestAnnotations));
    }

    public static Set<Class<? extends Annotation>> supportedPerClassAnnotationsFromTestExtensions() {
        return TEST_EXTENSIONS.stream().flatMap(ex -> ex.supportedPerClassAnnotations().stream()).collect(toSet());
    }

    private TestExtensions() {
    }
}
