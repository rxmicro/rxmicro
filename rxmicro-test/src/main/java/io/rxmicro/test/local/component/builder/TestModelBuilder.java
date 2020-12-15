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

package io.rxmicro.test.local.component.builder;

import io.rxmicro.test.Alternative;
import io.rxmicro.test.BlockingHttpClient;
import io.rxmicro.test.SystemOut;
import io.rxmicro.test.internal.BlockingHttpClientImpl;
import io.rxmicro.test.internal.SystemStreamImpl;
import io.rxmicro.test.local.InvalidTestConfigException;
import io.rxmicro.test.local.component.builder.internal.ReplacementExclusion;
import io.rxmicro.test.local.model.TestModel;

import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.Set;

import static io.rxmicro.common.util.Reflections.allFields;
import static io.rxmicro.common.util.Requires.require;
import static io.rxmicro.test.internal.DetectTypeRules.isBlockingHttpClient;
import static io.rxmicro.test.internal.DetectTypeRules.isConfig;
import static io.rxmicro.test.internal.DetectTypeRules.isHttpClientFactory;
import static io.rxmicro.test.internal.DetectTypeRules.isMongoDatabase;
import static io.rxmicro.test.internal.DetectTypeRules.isRepositoryField;
import static io.rxmicro.test.internal.DetectTypeRules.isRestClientField;
import static io.rxmicro.test.internal.DetectTypeRules.isSqlConnectionPool;
import static io.rxmicro.test.internal.DetectTypeRules.isSystemErr;
import static io.rxmicro.test.internal.DetectTypeRules.isSystemOut;
import static io.rxmicro.test.local.component.builder.internal.MockReplacementExclusion.MOCK_REPLACEMENT_EXCLUSION;
import static io.rxmicro.test.local.util.Inners.getOuterClass;
import static io.rxmicro.test.local.util.Inners.isInnerClass;
import static io.rxmicro.test.local.util.UserComponents.isUserComponentField;

/**
 * @author nedis
 * @since 0.1
 */
public final class TestModelBuilder {

    private static final Map<Class<?>, Map<?, Set<ReplacementExclusion>>> REPLACEMENT = Map.of(
            BlockingHttpClient.class,
            Map.of(
                    java.net.http.HttpClient.class, Set.of(MOCK_REPLACEMENT_EXCLUSION),
                    io.rxmicro.http.client.HttpClient.class, Set.of(MOCK_REPLACEMENT_EXCLUSION),
                    BlockingHttpClientImpl.class, Set.of()
            ),
            SystemOut.class,
            Map.of(
                    SystemStreamImpl.class, Set.of(),
                    PrintStream.class, Set.of(MOCK_REPLACEMENT_EXCLUSION)
            )
    );

    private final Class<?> testedComponentClass;

    private final boolean rxMicroCdiAvailable;

    public TestModelBuilder(final Class<?> testedComponentClass,
                            final boolean rxMicroCdiAvailable) {
        this.testedComponentClass = require(testedComponentClass);
        this.rxMicroCdiAvailable = rxMicroCdiAvailable;
    }

    public TestModelBuilder(final boolean rxMicroCdiAvailable) {
        this(NotAccessedClass.class, rxMicroCdiAvailable);
    }

    public TestModel build(final Class<?> testClass) {
        final TestModel.Builder builder = new TestModel.Builder(testClass);
        if (testedComponentClass != NotAccessedClass.class) {
            builder.setTestedComponentClass(testedComponentClass);
        }
        if (isInnerClass(testClass)) {
            populateBuilder(getOuterClass(testClass), builder);
        }
        populateBuilder(testClass, builder);
        return builder.build();
    }

    private void populateBuilder(final Class<?> testClass,
                                 final TestModel.Builder builder) {
        for (final Field field : allFields(testClass, v -> true)) {
            validate(field);
            if (isSystemOut(field.getType())) {
                builder.addSystemOut(field);
            } else if (isSystemErr(field.getType())) {
                builder.addSystemErr(field);
            } else if (isBlockingHttpClient(field.getType())) {
                builder.addBlockingHttpClient(field);
            } else if (field.getType() == testedComponentClass) {
                builder.addTestedComponent(field);
            } else if (isConfig(field.getType())) {
                addConfig(builder, field);
            } else if (isRepositoryField(field.getType())) {
                builder.addRepository(field);
            } else if (isRestClientField(field.getType())) {
                builder.addRestClient(field);
            } else if (isMongoDatabase(field.getType())) {
                builder.addMongoDatabase(field);
            } else if (isSqlConnectionPool(field.getType())) {
                builder.addSqlConnectionPool(field);
            } else if (isHttpClientFactory(field.getType())) {
                builder.addHttpClientFactory(field);
            } else if (field.isAnnotationPresent(Alternative.class)) {
                addAlternative(builder, field);
            }
        }
    }

    private void addConfig(final TestModel.Builder builder, final Field field) {
        if (Modifier.isStatic(field.getModifiers())) {
            builder.addStaticConfig(field);
        } else {
            builder.addInstanceConfig(field);
        }
    }

    private void addAlternative(final TestModel.Builder builder,
                                final Field field) {
        if (isUserComponentField(field)) {
            builder.addUserCreatedComponent(field);
            if (rxMicroCdiAvailable) {
                builder.addBeanComponent(field);
            }
        } else {
            throw new InvalidTestConfigException(
                    "'@?' annotation couldn't be applied to type: '?'",
                    Alternative.class.getName(),
                    field.getType().getName()
            );
        }
    }

    private void validate(final Field field) {
        REPLACEMENT.forEach((cl, map) -> {
            final Set<ReplacementExclusion> replacementExclusions = map.get(field.getType());
            if (replacementExclusions != null) {
                for (final ReplacementExclusion replacementExclusion : replacementExclusions) {
                    if (replacementExclusion.isReplacementExclude(field)) {
                        return;
                    }
                }
                throw new InvalidTestConfigException(
                        "Use '?' instead of '?'",
                        cl.getName(), field.getType().getName()
                );
            }
        });
    }

    /**
     * @author nedis
     * @since 0.1
     */
    private static final class NotAccessedClass {

    }
}
