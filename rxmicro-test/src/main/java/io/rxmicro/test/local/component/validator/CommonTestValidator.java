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

import io.rxmicro.test.SetConfigValue;
import io.rxmicro.test.internal.validator.impl.BlockingHttpClientFieldValidator;
import io.rxmicro.test.internal.validator.impl.ConfigFieldValidator;
import io.rxmicro.test.internal.validator.impl.HttpClientFactoryFieldValidator;
import io.rxmicro.test.internal.validator.impl.MongoDatabaseFieldValidator;
import io.rxmicro.test.internal.validator.impl.RepositoryFieldValidator;
import io.rxmicro.test.internal.validator.impl.RestClientFieldValidator;
import io.rxmicro.test.internal.validator.impl.SqlConnectionPoolFieldValidator;
import io.rxmicro.test.internal.validator.impl.SystemOutFieldValidator;
import io.rxmicro.test.internal.validator.impl.UserCreatedComponentFieldValidator;
import io.rxmicro.test.local.InvalidTestConfigException;
import io.rxmicro.test.local.model.TestModel;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static io.rxmicro.common.RxMicroModule.isRxMicroPackage;
import static io.rxmicro.test.internal.DetectTypeRules.isMongoRepositoryField;
import static io.rxmicro.test.internal.DetectTypeRules.isPostgreRepositoryField;
import static io.rxmicro.test.local.component.RxMicroTestExtensions.supportedPerClassAnnotationsFromTestExtensions;
import static io.rxmicro.test.local.util.FieldNames.getHumanReadableFieldName;
import static java.util.stream.Collectors.toSet;

/**
 * @author nedis
 * @since 0.1
 */
public abstract class CommonTestValidator {

    private static final Set<Class<? extends Annotation>> SUPPORTED_PER_CLASS_ANNOTATIONS = Set.of(
            SetConfigValue.class,
            SetConfigValue.List.class
    );

    public final void validate(final TestModel testModel) {
        validateUsingCommonRules(testModel);
        validateUsingSpecificRules(testModel);

        validateThatClassNotContainRedundantAnnotation(testModel);
    }

    private void validateUsingCommonRules(final TestModel testModel) {
        new ConfigFieldValidator()
                .validate(testModel.getStaticConfigs());
        new ConfigFieldValidator()
                .validate(testModel.getInstanceConfigs());
        new BlockingHttpClientFieldValidator()
                .validate(testModel.getBlockingHttpClients());
        new SystemOutFieldValidator()
                .validate(testModel.getSystemOuts());
        new UserCreatedComponentFieldValidator()
                .validate(testModel.getUserCreatedComponents());
        new RestClientFieldValidator()
                .validate(testModel.getRestClients());
        new RepositoryFieldValidator()
                .validate(testModel.getRepositories());
        new MongoDatabaseFieldValidator()
                .validate(testModel.getMongoDataBases());
        new SqlConnectionPoolFieldValidator()
                .validate(testModel.getSqlConnectionPools());
        new HttpClientFactoryFieldValidator()
                .validate(testModel.getHttpClientFactories());

        validateRestClientFactoryState(testModel);
        validateRepositoryFactoryState(testModel);
    }

    protected abstract void validateUsingSpecificRules(TestModel testModel);

    protected final void validateThatOnlyOneAnnotationExistsPerTestClass(final TestModel testModel,
                                                                         final Set<Class<? extends Annotation>> supportedAnnotations) {
        final Set<Class<? extends Annotation>> presentAnnotations = supportedAnnotations.stream()
                .filter(a -> testModel.getTestClass().isAnnotationPresent(a))
                .collect(toSet());
        if (presentAnnotations.size() > 1) {
            throw new InvalidTestConfigException(
                    "Per test class only one annotation is allowed from the list: ?. " +
                            "Remove redundant annotation(s) from '?' test class!",
                    supportedAnnotations,
                    testModel.getTestClass().getName()
            );
        }
    }

    private void validateThatClassNotContainRedundantAnnotation(final TestModel testModel) {
        final Set<Class<? extends Annotation>> supportedPerClassAnnotations = new HashSet<>(SUPPORTED_PER_CLASS_ANNOTATIONS);
        supportedPerClassAnnotations.addAll(supportedPerClassAnnotationsFromTestExtensions());

        Arrays.stream(testModel.getTestClass().getAnnotations())
                .filter(a -> isRxMicroPackage(a.annotationType().getPackageName()) &&
                        !supportedPerClassAnnotations.contains(a.annotationType()))
                .forEach(a -> {
                    throw new InvalidTestConfigException(
                            "Test class annotated by redundant annotation: '@?'. " +
                                    "Remove redundant annotation from '?' test class!",
                            a.annotationType().getName(),
                            testModel.getTestClass().getName()
                    );
                });
    }

    private void validateRestClientFactoryState(final TestModel testModel) {
        if (!testModel.getRestClients().isEmpty() && !testModel.getHttpClientFactories().isEmpty()) {
            final String httpClientFactoryField = getHumanReadableFieldName(testModel.getHttpClientFactories().get(0));
            final String restClientField = getHumanReadableFieldName(testModel.getRestClients().get(0));
            throw new InvalidTestConfigException(
                    "'?' http client factory alternative conflicts with '?' rest client. " +
                            "Remove the http client factory alternative or the rest client field!",
                    httpClientFactoryField,
                    restClientField
            );
        }
    }

    private void validateRepositoryFactoryState(final TestModel testModel) {
        if (!testModel.getRepositories().isEmpty()) {
            validateMongoRepositoryFactoryState(testModel);
            validatePostgresRepositoryFactoryState(testModel);
        }
    }

    private void validateMongoRepositoryFactoryState(final TestModel testModel) {
        if (!testModel.getMongoDataBases().isEmpty()) {
            for (final Field field : testModel.getRepositories()) {
                if (isMongoRepositoryField(field.getType())) {
                    final String mongoField = getHumanReadableFieldName(testModel.getMongoDataBases().get(0));
                    final String repositoryField = getHumanReadableFieldName(field);
                    throw new InvalidTestConfigException(
                            "'?' mongo database alternative conflicts with '?' repository. " +
                                    "Remove the mongo database alternative or the repository field!",
                            mongoField,
                            repositoryField
                    );
                }
            }
        }
    }

    private void validatePostgresRepositoryFactoryState(final TestModel testModel) {
        if (!testModel.getSqlConnectionPools().isEmpty()) {
            for (final Field field : testModel.getRepositories()) {
                if (isPostgreRepositoryField(field.getType())) {
                    final String connectionPoolField = getHumanReadableFieldName(testModel.getSqlConnectionPools().get(0));
                    final String repositoryField = getHumanReadableFieldName(field);
                    throw new InvalidTestConfigException(
                            "'?' sql connection pool alternative conflicts with '?' repository. " +
                                    "Remove the sql connection pool alternative or the repository field!",
                            connectionPoolField,
                            repositoryField
                    );
                }
            }
        }
    }
}
