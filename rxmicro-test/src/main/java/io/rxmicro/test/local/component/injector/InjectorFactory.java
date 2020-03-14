/*
 * Copyright 2019 http://rxmicro.io
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

package io.rxmicro.test.local.component.injector;

import io.rxmicro.test.Alternative;
import io.rxmicro.test.local.AlternativeEntryPoint;
import io.rxmicro.test.local.component.TestedComponentResolver;
import io.rxmicro.test.local.model.TestModel;

import java.lang.reflect.Field;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toList;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public final class InjectorFactory {

    private static final Function<Field, AlternativeEntryPoint> MAPPER = f ->
            new AlternativeEntryPoint(f.getAnnotation(Alternative.class), f);

    private final List<AlternativeEntryPoint> runtimeContextRegistrationComponents;

    private final List<AlternativeEntryPoint> repositoryComponents;

    private final List<AlternativeEntryPoint> restClientComponents;

    private final List<AlternativeEntryPoint> userCreatedComponents;

    private final List<AlternativeEntryPoint> beanComponents;

    private final Field testedComponent;

    private final Class<?> testedComponentClass;

    private final Field blockingHttpClientField;

    private final Field systemOutField;

    public InjectorFactory(final TestModel testModel) {
        blockingHttpClientField = testModel.getBlockingHttpClients().stream().findFirst()
                .orElse(null);
        systemOutField = testModel.getSystemOuts().stream().findFirst()
                .orElse(null);
        testedComponent = testModel.getTestedComponents().stream().findFirst().orElse(null);
        testedComponentClass = testModel.getTestedComponentClass().orElse(null);

        runtimeContextRegistrationComponents = Stream.of(
                testModel.getMongoDataBases().stream(),
                testModel.getSqlConnectionPools().stream(),
                testModel.getHttpClientFactories().stream()
        ).flatMap(identity())
                .map(MAPPER)
                .collect(toList());
        repositoryComponents = testModel.getRepositories().stream().map(MAPPER).collect(toList());
        restClientComponents = testModel.getRestClients().stream().map(MAPPER).collect(toList());
        userCreatedComponents = testModel.getUserCreatedComponents().stream().map(MAPPER).collect(toList());
        beanComponents = testModel.getBeanComponents().stream().map(MAPPER).collect(toList());
    }

    public BlockingHttpClientInjector createBlockingHttpClientInjector() {
        return new BlockingHttpClientInjector(blockingHttpClientField);
    }

    public SystemOutInjector createSystemOutInjector() {
        return new SystemOutInjector(systemOutField);
    }

    public RuntimeContextComponentInjector createRuntimeContextComponentInjector() {
        return new RuntimeContextComponentInjector(runtimeContextRegistrationComponents);
    }

    public TestedComponentResolver createTestedComponentResolver() {
        return new TestedComponentResolver(testedComponent, testedComponentClass);
    }

    public RepositoryInjector createRepositoryInjector() {
        return new RepositoryInjector(repositoryComponents);
    }

    public RestClientInjector createRestClientInjector() {
        return new RestClientInjector(restClientComponents);
    }

    public UserCreatedComponentInjector createUserCreatedComponentInjector() {
        return new UserCreatedComponentInjector(userCreatedComponents);
    }

    public BeanFactoryInjector createBeanFactoryInjector() {
        return new BeanFactoryInjector(beanComponents);
    }
}
