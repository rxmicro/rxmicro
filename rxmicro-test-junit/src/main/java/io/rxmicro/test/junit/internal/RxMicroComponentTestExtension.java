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

import io.rxmicro.cdi.BeanFactory;
import io.rxmicro.config.Configs;
import io.rxmicro.logger.Logger;
import io.rxmicro.logger.LoggerFactory;
import io.rxmicro.test.junit.RxMicroComponentTest;
import io.rxmicro.test.local.component.TestedComponentResolver;
import io.rxmicro.test.local.component.builder.TestModelBuilder;
import io.rxmicro.test.local.component.injector.BeanFactoryInjector;
import io.rxmicro.test.local.component.injector.InjectorFactory;
import io.rxmicro.test.local.component.injector.RepositoryInjector;
import io.rxmicro.test.local.component.injector.RestClientInjector;
import io.rxmicro.test.local.component.injector.RuntimeContextComponentInjector;
import io.rxmicro.test.local.component.injector.SystemStreamInjector;
import io.rxmicro.test.local.component.injector.UserCreatedComponentInjector;
import io.rxmicro.test.local.component.validator.ComponentTestValidator;
import io.rxmicro.test.local.model.TestModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.lang.reflect.InaccessibleObjectException;
import java.util.List;

import static io.rxmicro.config.local.DefaultConfigValueBuildHelperReSetter.resetDefaultConfigValueStorage;
import static io.rxmicro.netty.runtime.local.EventLoopGroupFactory.clearEventLoopGroupFactory;
import static io.rxmicro.runtime.detail.ChildrenInitHelper.resetChildrenInitializer;
import static io.rxmicro.runtime.local.AbstractFactory.clearFactories;
import static io.rxmicro.runtime.local.InstanceContainer.clearContainer;
import static io.rxmicro.test.junit.local.LoggerUtils.logAfterAll;
import static io.rxmicro.test.junit.local.LoggerUtils.logAfterEach;
import static io.rxmicro.test.junit.local.LoggerUtils.logAfterTestExecution;
import static io.rxmicro.test.junit.local.LoggerUtils.logBeforeAll;
import static io.rxmicro.test.junit.local.LoggerUtils.logBeforeEach;
import static io.rxmicro.test.junit.local.LoggerUtils.logBeforeTestExecution;
import static io.rxmicro.test.junit.local.TestObjects.getTestInstances;
import static io.rxmicro.test.local.UnNamedModuleFixers.componentTestsFix;
import static io.rxmicro.test.local.component.StatelessComponentFactory.getConfigResolver;
import static io.rxmicro.test.local.util.Annotations.getRequiredAnnotation;
import static io.rxmicro.test.local.util.Modules.isRequiredModule;
import static io.rxmicro.test.local.util.TestExceptions.reThrowInaccessibleObjectException;

/**
 * Execution order:
 * (Read more: https://junit.org/junit5/docs/current/user-guide/#extensions-execution-order-overview)
 *
 * 1) BeforeAllCallback.beforeAll
 * 2) @BeforeAll
 * 3) LifecycleMethodExecutionExceptionHandler.handleBeforeAllMethodExecutionException
 * 4) BeforeEachCallback.beforeEach
 * 5) @BeforeEach
 * 6) LifecycleMethodExecutionExceptionHandler.handleBeforeEachMethodExecutionException
 * 7) BeforeTestExecutionCallback.beforeTestExecution
 * 8) @Test
 * 9) TestExecutionExceptionHandler.handleTestExecutionException
 * 10) AfterTestExecutionCallback.afterTestExecution
 * 11) @AfterEach
 * 12) LifecycleMethodExecutionExceptionHandler.handleAfterEachMethodExecutionException
 * 13) AfterEachCallback.afterEach
 * 14) @AfterAll
 * 15) LifecycleMethodExecutionExceptionHandler.handleAfterAllMethodExecutionException
 * 16) AfterAllCallback.afterAll
 *
 * @author nedis
 * @since 0.1
 */
public final class RxMicroComponentTestExtension extends BaseJUnitTestExtension implements
        BeforeAllCallback, BeforeEachCallback, BeforeTestExecutionCallback,
        AfterTestExecutionCallback, AfterEachCallback, AfterAllCallback {

    private static final Logger LOGGER = LoggerFactory.getLogger(RxMicroComponentTestExtension.class);

    static {
        componentTestsFix();
    }

    private TestModel testModel;

    private RepositoryInjector repositoryInjector;

    private RestClientInjector restClientInjector;

    private RuntimeContextComponentInjector runtimeContextComponentInjector;

    private UserCreatedComponentInjector userCreatedComponentInjector;

    private BeanFactoryInjector beanFactoryInjector;

    private TestedComponentResolver testedComponentResolver;

    private SystemStreamInjector systemStreamInjector;

    @Override
    public void beforeAll(final ExtensionContext context) {
        logBeforeAll(LOGGER, context);
        final Class<?> testClass = context.getRequiredTestClass();
        final Class<?> componentClass = getRequiredAnnotation(testClass, RxMicroComponentTest.class).value();
        final Module module = componentClass.getModule();
        final TestModelBuilder testModelBuilder = new TestModelBuilder(
                componentClass,
                isRequiredModule(module, BeanFactory.class.getModule())
        );
        testModel = testModelBuilder.build(testClass);
        new ComponentTestValidator(SUPPORTED_TEST_ANNOTATIONS).validate(testModel);

        getConfigResolver().setDefaultConfigValues(testClass);
        if (testModel.isStaticConfigsPresent()) {
            new Configs.Builder()
                    .withConfigs(getConfigResolver().getStaticConfigMap(testModel))
                    .build();
        } else {
            new Configs.Builder().buildIfNotConfigured();
        }
        final InjectorFactory injectorFactory = new InjectorFactory(module, testModel);
        repositoryInjector = injectorFactory.createRepositoryInjector();
        restClientInjector = injectorFactory.createRestClientInjector();
        runtimeContextComponentInjector = injectorFactory.createRuntimeContextComponentInjector();
        userCreatedComponentInjector = injectorFactory.createUserCreatedComponentInjector();
        beanFactoryInjector = injectorFactory.createBeanFactoryInjector();
        testedComponentResolver = injectorFactory.createTestedComponentResolver();
        systemStreamInjector = injectorFactory.createSystemOutInjector();
    }

    @Override
    public void beforeEach(final ExtensionContext context) {
        logBeforeEach(LOGGER, context);
        final List<Object> testInstances = getTestInstances(context);
        testedComponentResolver.verifyState(testInstances, BeforeEach.class);

        runtimeContextComponentInjector.injectIfFound(testInstances);
        repositoryInjector.injectIfFound(testInstances);
        restClientInjector.injectIfFound(testInstances);
        beanFactoryInjector.injectIfFound(testInstances);
        systemStreamInjector.injectIfFound(testInstances);
    }

    @Override
    public void beforeTestExecution(final ExtensionContext context) {
        final List<Object> testInstances = getTestInstances(context);
        getBeforeTestInvoker().invokeIfFound(context, testInstances);
        if (testModel.isInstanceConfigsPresent()) {
            new Configs.Builder()
                    .withConfigs(getConfigResolver().getInstanceConfigMap(testModel, testInstances))
                    .build();
        }
        final Object testedComponentInstance = testedComponentResolver.getTestedComponentInstance(testInstances);
        try {
            userCreatedComponentInjector.injectIfFound(testInstances, List.of(testedComponentInstance));
        } catch (final InaccessibleObjectException ex) {
            reThrowInaccessibleObjectException(ex);
        }
        logBeforeTestExecution(LOGGER, context);
    }

    @Override
    public void afterTestExecution(final ExtensionContext context) {
        logAfterTestExecution(LOGGER, context);
    }

    @Override
    public void afterEach(final ExtensionContext context) {
        clearContainer();
        clearFactories();
        resetChildrenInitializer();
        resetDefaultConfigValueStorage();
        resetConfigurationIfPossible();
        systemStreamInjector.resetIfNecessary();
        logAfterEach(LOGGER, context);
    }

    @Override
    public void afterAll(final ExtensionContext context) {
        clearEventLoopGroupFactory();
        logAfterAll(LOGGER, context);
    }
}
