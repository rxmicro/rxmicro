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

package io.rxmicro.test.local.component;

import io.rxmicro.test.local.InvalidTestConfigException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

import static io.rxmicro.cdi.BeanFactory.getBean;
import static io.rxmicro.common.util.Reflections.getFieldValue;
import static io.rxmicro.common.util.Reflections.setFieldValue;
import static io.rxmicro.data.RepositoryFactory.getRepository;
import static io.rxmicro.rest.client.RestClientFactory.getRestClient;
import static io.rxmicro.runtime.local.Instances.instantiate;
import static io.rxmicro.test.internal.DetectTypeRules.isBeanField;
import static io.rxmicro.test.internal.DetectTypeRules.isRepositoryField;
import static io.rxmicro.test.internal.DetectTypeRules.isRestClientField;

/**
 * @author nedis
 * @since 0.1
 */
public final class TestedComponentResolver {

    private final Field testedComponent;

    private final Class<?> testedComponentClass;

    public TestedComponentResolver(final Field testedComponent,
                                   final Class<?> testedComponentClass) {
        this.testedComponent = testedComponent;
        this.testedComponentClass = testedComponentClass;
    }

    public Object getTestedComponentInstance(final List<Object> testInstances) {
        if (testedComponent != null) {
            return getInstance(testInstances, testedComponent, true);
        } else {
            return createTestComponentInstance(testedComponentClass);
        }
    }

    public void verifyState(final List<Object> testInstances,
                            final Class<? extends Annotation> beforeEachAnnotation) {
        if (testedComponent != null && getInstance(testInstances, testedComponent, false) != null) {
            throw new InvalidTestConfigException("Invalid initialization of tested component: ?. " +
                    "These components must be instantiated by the RxMicro framework automatically or " +
                    "inside method, annotated by '@?' annotation.", testedComponent, beforeEachAnnotation.getName());
        }
    }

    private Object getInstance(final List<Object> testInstances,
                               final Field field,
                               final boolean instantiateIfNotFound) {
        final Object userCreatedInstance = getFieldValue(testInstances, field);
        if (instantiateIfNotFound) {
            return Optional.ofNullable(userCreatedInstance)
                    .orElseGet(() -> {
                        final Object instance = createTestComponentInstance(field.getType());
                        setFieldValue(testInstances, field, instance);
                        return instance;
                    });
        } else {
            return userCreatedInstance;
        }
    }

    private Object createTestComponentInstance(final Class<?> type) {
        final Object autoCreatedInstance;
        if (isRepositoryField(type)) {
            autoCreatedInstance = getRepository(type);
        } else if (isRestClientField(type)) {
            autoCreatedInstance = getRestClient(type);
        } else if (isBeanField(type)) {
            autoCreatedInstance = getBean(type);
        } else {
            autoCreatedInstance = instantiate(type);
        }
        return autoCreatedInstance;
    }
}
