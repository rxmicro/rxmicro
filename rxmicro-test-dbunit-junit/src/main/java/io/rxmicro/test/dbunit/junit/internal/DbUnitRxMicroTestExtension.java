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

package io.rxmicro.test.dbunit.junit.internal;

import io.rxmicro.test.dbunit.ExpectedDataSet;
import io.rxmicro.test.dbunit.InitialDataSet;
import io.rxmicro.test.dbunit.junit.DbUnitTest;
import io.rxmicro.test.local.InvalidTestConfigException;
import io.rxmicro.test.local.component.RxMicroTestExtension;
import io.rxmicro.test.local.model.TestModel;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.Extension;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.function.Predicate;

import static io.rxmicro.tool.common.Reflections.containsMethod;

/**
 * @author nedis
 * @since 0.7
 */
public final class DbUnitRxMicroTestExtension implements RxMicroTestExtension {

    private static final String TEST_CONTAINERS_ANNOTATION_CLASS_NAME = "org.testcontainers.junit.jupiter.Testcontainers";

    @Override
    public void validate(final TestModel testModel,
                         final Set<Class<? extends Annotation>> supportedRxMicroTestAnnotations) {
        validateDbUnitTestUsageInsteadOfExtendsWith(testModel);
        validateAnnotationOrder(testModel);
        validateIfDbUnitTestIsRedundantAnnotation(testModel);
    }

    private void validateDbUnitTestUsageInsteadOfExtendsWith(final TestModel testModel) {
        final ExtendWith[] extendWiths = testModel.getTestClass().getAnnotationsByType(ExtendWith.class);
        for (final ExtendWith extendWith : extendWiths) {
            for (final Class<? extends Extension> extension : extendWith.value()) {
                if (extension == DbUnitTestExtension.class) {
                    throw new InvalidTestConfigException(
                            "Unsupported test extension: '?'. Use '@?' instead!",
                            DbUnitTestExtension.class.getName(),
                            ExtendWith.class.getName()
                    );
                }
            }
        }
    }

    private void validateAnnotationOrder(final TestModel testModel) {
        boolean dbUnitFound = false;
        for (final Annotation annotation : testModel.getTestClass().getAnnotations()) {
            if (annotation.annotationType() == DbUnitTest.class) {
                dbUnitFound = true;
            } else if (TEST_CONTAINERS_ANNOTATION_CLASS_NAME.equals(annotation.annotationType().getName())) {
                if (dbUnitFound) {
                    throw new InvalidTestConfigException(
                            "'@?' must be added before '@?' annotation for class: '?'",
                            DbUnitTest.class.getName(),
                            annotation.annotationType().getName(),
                            testModel.getTestClass().getName()
                    );
                }
            }
        }
    }

    private void validateIfDbUnitTestIsRedundantAnnotation(final TestModel testModel) {
        if (testModel.getTestClass().isAnnotationPresent(DbUnitTest.class)) {
            final Predicate<Method> methodHasDataSetAnnotationPredicate =
                    m -> m.getAnnotation(InitialDataSet.class) != null || m.getAnnotation(ExpectedDataSet.class) != null;
            if (containsMethod(testModel.getTestClass(), methodHasDataSetAnnotationPredicate)) {
                return;
            }
            boolean hasNested = false;
            for (final Class<?> nestMember : testModel.getTestClass().getNestMembers()) {
                if (nestMember.isAnnotationPresent(Nested.class)) {
                    hasNested = true;
                    if (containsMethod(nestMember, methodHasDataSetAnnotationPredicate)) {
                        return;
                    }
                }
            }
            throw new InvalidTestConfigException(
                    "It seems that '@?' is redundant annotation, " +
                            "because '?' test class? does not contain any test methods annotated by '@?' or '@?' annotations!" +
                            "Remove the redundant annotation!",
                    DbUnitTest.class.getName(),
                    hasNested ? " (or any it nested class(es))" : "",
                    testModel.getTestClass().getName(),
                    InitialDataSet.class.getSimpleName(), ExpectedDataSet.class.getSimpleName()
            );
        }
    }

    @Override
    public Set<Class<? extends Annotation>> supportedPerClassAnnotations() {
        return Set.of(DbUnitTest.class);
    }
}
