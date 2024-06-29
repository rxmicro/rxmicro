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

import io.rxmicro.logger.Logger;
import io.rxmicro.logger.LoggerFactory;
import io.rxmicro.test.BlockingHttpClient;
import io.rxmicro.test.junit.RxMicroIntegrationTest;
import io.rxmicro.test.local.BlockingHttpClientConfig;
import io.rxmicro.test.local.component.builder.TestModelBuilder;
import io.rxmicro.test.local.component.injector.BlockingHttpClientInjector;
import io.rxmicro.test.local.component.injector.InjectorFactory;
import io.rxmicro.test.local.component.injector.SystemStreamInjector;
import io.rxmicro.test.local.component.validator.IntegrationTestValidator;
import io.rxmicro.test.local.model.TestModel;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

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
import static io.rxmicro.test.junit.local.TestObjects.getOwnerTestClass;
import static io.rxmicro.test.junit.local.TestObjects.getTestInstances;
import static io.rxmicro.test.local.UnNamedModuleFixers.integrationTestsFix;
import static io.rxmicro.test.local.component.StatelessComponentFactory.getBlockingHttpClientBuilder;
import static io.rxmicro.test.local.util.Annotations.getRequiredAnnotation;

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
public final class RxMicroIntegrationTestExtension extends BaseJUnitTestExtension implements
        BeforeAllCallback, BeforeEachCallback, BeforeTestExecutionCallback,
        AfterTestExecutionCallback, AfterEachCallback, AfterAllCallback {

    private static final Logger LOGGER = LoggerFactory.getLogger(RxMicroIntegrationTestExtension.class);

    static {
        integrationTestsFix();
    }

    private BlockingHttpClient blockingHttpClient;

    private BlockingHttpClientInjector blockingHttpClientInjector;

    private SystemStreamInjector systemStreamInjector;

    @Override
    public void beforeAll(final ExtensionContext context) {
        logBeforeAll(LOGGER, context);
        final Class<?> testClass = getOwnerTestClass(context);
        getRequiredAnnotation(testClass, RxMicroIntegrationTest.class);
        final TestModel testModel = new TestModelBuilder(false).build(testClass);
        final IntegrationTestValidator integrationTestValidator = new IntegrationTestValidator(SUPPORTED_TEST_ANNOTATIONS);

        integrationTestValidator.validate(testModel);
        final InjectorFactory injectorFactory = new InjectorFactory(testModel);
        blockingHttpClientInjector = injectorFactory.createBlockingHttpClientInjector();
        systemStreamInjector = injectorFactory.createSystemOutInjector();
        if (blockingHttpClientInjector.hasField()) {
            final BlockingHttpClientConfig config =
                    blockingHttpClientInjector.getConfig(testClass, true, 8080);
            integrationTestValidator.validate(testModel, config);
            blockingHttpClient = getBlockingHttpClientBuilder().build(config);
        }
    }

    @Override
    public void beforeEach(final ExtensionContext context) {
        logBeforeEach(LOGGER, context);
        getBeforeTestInvoker().throwErrorIfFound(context);
        final List<Object> testInstances = getTestInstances(context);
        blockingHttpClientInjector.injectIfFound(testInstances, blockingHttpClient);
        systemStreamInjector.injectIfFound(testInstances);
    }

    @Override
    public void beforeTestExecution(final ExtensionContext context) {
        logBeforeTestExecution(LOGGER, context);
    }

    @Override
    public void afterTestExecution(final ExtensionContext context) {
        logAfterTestExecution(LOGGER, context);
    }

    @Override
    public void afterEach(final ExtensionContext context) {
        systemStreamInjector.resetIfNecessary();
        logAfterEach(LOGGER, context);
    }

    @Override
    public void afterAll(final ExtensionContext context) {
        clearContainer();
        clearFactories();
        resetChildrenInitializer();
        resetDefaultConfigValueStorage();
        clearEventLoopGroupFactory();
        resetConfigurationIfPossible();
        logAfterAll(LOGGER, context);
    }
}
