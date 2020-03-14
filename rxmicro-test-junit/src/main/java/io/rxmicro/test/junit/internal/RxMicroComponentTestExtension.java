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

import io.rxmicro.cdi.BeanFactory;
import io.rxmicro.config.Configs;
import io.rxmicro.test.junit.RxMicroComponentTest;
import io.rxmicro.test.local.component.ConfigResolver;
import io.rxmicro.test.local.component.TestedComponentResolver;
import io.rxmicro.test.local.component.builder.TestModelBuilder;
import io.rxmicro.test.local.component.injector.BeanFactoryInjector;
import io.rxmicro.test.local.component.injector.InjectorFactory;
import io.rxmicro.test.local.component.injector.RepositoryInjector;
import io.rxmicro.test.local.component.injector.RestClientInjector;
import io.rxmicro.test.local.component.injector.RuntimeContextComponentInjector;
import io.rxmicro.test.local.component.injector.SystemOutInjector;
import io.rxmicro.test.local.component.injector.UserCreatedComponentInjector;
import io.rxmicro.test.local.component.validator.ComponentTestValidator;
import io.rxmicro.test.local.model.TestModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.List;

import static io.rxmicro.runtime.local.AbstractFactory.clearFactories;
import static io.rxmicro.runtime.local.InstanceContainer.clearContainer;
import static io.rxmicro.test.junit.internal.TestObjects.SUPPORTED_TEST_ANNOTATIONS;
import static io.rxmicro.test.junit.internal.TestObjects.getTestInstances;
import static io.rxmicro.test.local.UnNamedModuleFixer.componentTestsFix;
import static io.rxmicro.test.local.util.Annotations.getRequiredAnnotation;
import static io.rxmicro.test.local.util.Modules.isRequiredModule;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public final class RxMicroComponentTestExtension
        implements BeforeAllCallback, BeforeEachCallback, BeforeTestExecutionCallback, AfterEachCallback {

    static {
        componentTestsFix();
    }

    private final ComponentTestValidator componentTestValidator =
            new ComponentTestValidator(SUPPORTED_TEST_ANNOTATIONS);

    private final BeforeTestInvoker beforeTestInvoker =
            new BeforeTestInvoker();

    private final ConfigResolver configResolver =
            new ConfigResolver();

    private TestModel testModel;

    private RepositoryInjector repositoryInjector;

    private RestClientInjector restClientInjector;

    private RuntimeContextComponentInjector runtimeContextComponentInjector;

    private UserCreatedComponentInjector userCreatedComponentInjector;

    private BeanFactoryInjector beanFactoryInjector;

    private TestedComponentResolver testedComponentResolver;

    private SystemOutInjector systemOutInjector;

    @Override
    public void beforeAll(final ExtensionContext context) {
        final Class<?> testClass = context.getRequiredTestClass();
        final Class<?> componentClass = getRequiredAnnotation(testClass, RxMicroComponentTest.class).value();
        final TestModelBuilder testModelBuilder = new TestModelBuilder(
                componentClass,
                isRequiredModule(componentClass.getModule(), BeanFactory.class.getModule())
        );
        testModel = testModelBuilder.build(testClass);
        componentTestValidator.validate(testModel);
        configResolver.setDefaultConfigValues(testClass);
        if (testModel.isStaticConfigsPresent()) {
            new Configs.Builder()
                    .withConfigs(configResolver.getStaticConfigMap(testModel))
                    .build();
        }
        final InjectorFactory injectorFactory = new InjectorFactory(testModel);
        repositoryInjector = injectorFactory.createRepositoryInjector();
        restClientInjector = injectorFactory.createRestClientInjector();
        runtimeContextComponentInjector = injectorFactory.createRuntimeContextComponentInjector();
        userCreatedComponentInjector = injectorFactory.createUserCreatedComponentInjector();
        beanFactoryInjector = injectorFactory.createBeanFactoryInjector();
        testedComponentResolver = injectorFactory.createTestedComponentResolver();
        systemOutInjector = injectorFactory.createSystemOutInjector();
    }

    @Override
    public void beforeEach(final ExtensionContext context) {
        final List<Object> testInstances = getTestInstances(context);
        testedComponentResolver.verifyState(testInstances, BeforeEach.class);

        runtimeContextComponentInjector.injectIfFound(testInstances);
        repositoryInjector.injectIfFound(testInstances);
        restClientInjector.injectIfFound(testInstances);
        beanFactoryInjector.injectIfFound(testInstances);
        systemOutInjector.injectIfFound(testInstances);
    }

    @Override
    public void beforeTestExecution(final ExtensionContext context) {
        final List<Object> testInstances = getTestInstances(context);
        beforeTestInvoker.invokeIfFound(context, testInstances);
        if (testModel.isInstanceConfigsPresent()) {
            new Configs.Builder()
                    .withConfigs(configResolver.getInstanceConfigMap(testModel, testInstances))
                    .build();
        }
        final Object testedComponentInstance = testedComponentResolver.getTestedComponentInstance(testInstances);
        if (!beanFactoryInjector.isBeansFound()) {
            userCreatedComponentInjector.injectIfFound(testInstances, List.of(testedComponentInstance));
        }
        if (beanFactoryInjector.isBeansFound()) {
            beanFactoryInjector.validateThatAllAlternativesAreUsed();
        }
    }

    @Override
    public void afterEach(final ExtensionContext context) {
        clearContainer();
        clearFactories();
        systemOutInjector.resetIfNecessary();
    }
}
