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
import io.rxmicro.config.Config;
import io.rxmicro.logger.Logger;
import io.rxmicro.logger.LoggerFactory;
import io.rxmicro.rest.server.HttpServerConfig;
import io.rxmicro.rest.server.RestServerConfig;
import io.rxmicro.rest.server.local.model.ServerContainer;
import io.rxmicro.test.BlockingHttpClient;
import io.rxmicro.test.junit.RxMicroRestBasedMicroServiceTest;
import io.rxmicro.test.local.BlockingHttpClientConfig;
import io.rxmicro.test.local.InvalidTestConfigException;
import io.rxmicro.test.local.component.ConfigResolver;
import io.rxmicro.test.local.component.RestControllerInstanceResolver;
import io.rxmicro.test.local.component.builder.TestModelBuilder;
import io.rxmicro.test.local.component.injector.BeanFactoryInjector;
import io.rxmicro.test.local.component.injector.BlockingHttpClientInjector;
import io.rxmicro.test.local.component.injector.InjectorFactory;
import io.rxmicro.test.local.component.injector.RepositoryInjector;
import io.rxmicro.test.local.component.injector.RestClientInjector;
import io.rxmicro.test.local.component.injector.RuntimeContextComponentInjector;
import io.rxmicro.test.local.component.injector.SystemStreamInjector;
import io.rxmicro.test.local.component.injector.UserCreatedComponentInjector;
import io.rxmicro.test.local.component.validator.RestBasedMicroServiceTestValidator;
import io.rxmicro.test.local.model.TestModel;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.lang.reflect.InaccessibleObjectException;
import java.util.List;
import java.util.Map;

import static io.rxmicro.config.local.DefaultConfigValueBuildHelperReSetter.resetDefaultConfigValueStorage;
import static io.rxmicro.netty.runtime.local.EventLoopGroupFactory.clearEventLoopGroupFactory;
import static io.rxmicro.rest.server.local.component.RestServerLauncher.launchWithoutRestControllers;
import static io.rxmicro.runtime.detail.ChildrenInitHelper.resetChildrenInitializer;
import static io.rxmicro.runtime.local.AbstractFactory.clearFactories;
import static io.rxmicro.runtime.local.InstanceContainer.clearContainer;
import static io.rxmicro.test.HttpServers.getRandomFreePort;
import static io.rxmicro.test.junit.local.LoggerUtils.logAfterAll;
import static io.rxmicro.test.junit.local.LoggerUtils.logAfterEach;
import static io.rxmicro.test.junit.local.LoggerUtils.logAfterTestExecution;
import static io.rxmicro.test.junit.local.LoggerUtils.logBeforeAll;
import static io.rxmicro.test.junit.local.LoggerUtils.logBeforeEach;
import static io.rxmicro.test.junit.local.LoggerUtils.logBeforeTestExecution;
import static io.rxmicro.test.junit.local.TestObjects.getOwnerTestClass;
import static io.rxmicro.test.junit.local.TestObjects.getTestInstances;
import static io.rxmicro.test.local.UnNamedModuleFixers.restBasedMicroServiceTestsFix;
import static io.rxmicro.test.local.component.StatelessComponentFactory.getBlockingHttpClientBuilder;
import static io.rxmicro.test.local.component.StatelessComponentFactory.getConfigResolver;
import static io.rxmicro.test.local.component.StatelessComponentFactory.getServerPortHelper;
import static io.rxmicro.test.local.util.Annotations.getRequiredAnnotation;
import static io.rxmicro.test.local.util.Modules.isRequiredModule;
import static io.rxmicro.test.local.util.Safes.safeInvoke;
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
public final class RxMicroRestBasedMicroServiceTestExtension extends BaseJUnitTestExtension implements
        BeforeAllCallback, BeforeEachCallback, BeforeTestExecutionCallback,
        AfterTestExecutionCallback, AfterEachCallback, AfterAllCallback {

    private static final Logger LOGGER = LoggerFactory.getLogger(RxMicroRestBasedMicroServiceTestExtension.class);

    static {
        restBasedMicroServiceTestsFix();
    }

    private final RestBasedMicroServiceTestValidator restBasedMicroServiceTestValidator =
            new RestBasedMicroServiceTestValidator(SUPPORTED_TEST_ANNOTATIONS);

    private RestControllerInstanceResolver restControllerInstanceResolver;

    private BlockingHttpClient blockingHttpClient;

    private RuntimeContextComponentInjector runtimeContextComponentInjector;

    private RepositoryInjector repositoryInjector;

    private RestClientInjector restClientInjector;

    private BlockingHttpClientInjector blockingHttpClientInjector;

    private SystemStreamInjector systemStreamInjector;

    private UserCreatedComponentInjector userCreatedComponentInjector;

    private BeanFactoryInjector beanFactoryInjector;

    private ServerContainer serverContainer;

    private boolean waitForTestHttpServerStopped;

    @Override
    public void beforeAll(final ExtensionContext context) {
        logBeforeAll(LOGGER, context);
        final Class<?> testClass = getOwnerTestClass(context);
        final RxMicroRestBasedMicroServiceTest annotation = getRequiredAnnotation(testClass, RxMicroRestBasedMicroServiceTest.class);
        waitForTestHttpServerStopped = annotation.waitForTestHttpServerStopped();
        final Class<?>[] restControllerClasses = annotation.value();

        validateNotEmptyArray(restControllerClasses);
        final Module module = restControllerClasses[0].getModule();
        final TestModelBuilder testModelBuilder = new TestModelBuilder(
                isRequiredModule(module, BeanFactory.class.getModule())
        );
        final TestModel testModel = testModelBuilder.build(testClass);
        restBasedMicroServiceTestValidator.validate(testModel, restControllerClasses);
        final int randomFreePort = getRandomFreePort();
        final ConfigResolver configResolver = getConfigResolver();
        configResolver.setDefaultConfigValues(testClass);
        final Map<String, Config> configs = configResolver.getStaticConfigMap(
                testModel,
                new HttpServerConfig()
                        .setPort(randomFreePort)
                        .setStartTimeTrackerEnabled(false),
                new RestServerConfig()
                        .setDevelopmentMode(true)
        );
        serverContainer = launchWithoutRestControllers(module, configs);
        restControllerInstanceResolver = new RestControllerInstanceResolver(restControllerClasses, serverContainer);
        createInjectors(module, testClass, testModel, configs);
    }

    private void validateNotEmptyArray(final Class<?>... restControllerClasses) {
        if (restControllerClasses.length == 0) {
            throw new InvalidTestConfigException(
                    "Annotation '?' must contain at least one REST controller class",
                    RxMicroRestBasedMicroServiceTest.class.getName()
            );
        }
    }

    private void createInjectors(final Module module,
                                 final Class<?> testClass,
                                 final TestModel testModel,
                                 final Map<String, Config> configs) {
        final InjectorFactory injectorFactory = new InjectorFactory(module, testModel);
        blockingHttpClientInjector = injectorFactory.createBlockingHttpClientInjector();
        systemStreamInjector = injectorFactory.createSystemOutInjector();
        if (blockingHttpClientInjector.hasField()) {
            final int serverPort = getServerPortHelper().getServerPort(configs.values());
            final BlockingHttpClientConfig config = blockingHttpClientInjector.getConfig(testClass, false, serverPort);
            restBasedMicroServiceTestValidator.validate(testModel, config);
            blockingHttpClient = getBlockingHttpClientBuilder().build(config);
        }
        runtimeContextComponentInjector = injectorFactory.createRuntimeContextComponentInjector();
        userCreatedComponentInjector = injectorFactory.createUserCreatedComponentInjector();
        beanFactoryInjector = injectorFactory.createBeanFactoryInjector();
        repositoryInjector = injectorFactory.createRepositoryInjector();
        restClientInjector = injectorFactory.createRestClientInjector();
    }

    @Override
    public void beforeEach(final ExtensionContext context) {
        logBeforeEach(LOGGER, context);
        final List<Object> testInstances = getTestInstances(context);

        runtimeContextComponentInjector.injectIfFound(testInstances);
        repositoryInjector.injectIfFound(testInstances);
        restClientInjector.injectIfFound(testInstances);
        beanFactoryInjector.injectIfFound(testInstances);

        blockingHttpClientInjector.injectIfFound(testInstances, blockingHttpClient);
        systemStreamInjector.injectIfFound(testInstances);
    }

    @Override
    public void beforeTestExecution(final ExtensionContext context) {
        final List<Object> testInstances = getTestInstances(context);
        getBeforeTestInvoker().invokeIfFound(context, testInstances);

        serverContainer.register(restControllerInstanceResolver.getRestControllerClasses());
        try {
            userCreatedComponentInjector.injectIfFound(testInstances, restControllerInstanceResolver.getRestControllerInstances());
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
        serverContainer.unregisterAllRestControllers();
        systemStreamInjector.resetIfNecessary();
        logAfterEach(LOGGER, context);
    }

    @Override
    public void afterAll(final ExtensionContext context) {
        safeInvoke(blockingHttpClient, BlockingHttpClient::release);
        if (waitForTestHttpServerStopped) {
            safeInvoke(serverContainer, container -> container.getServerInstance().shutdownAndWait());
        } else {
            safeInvoke(serverContainer, container -> container.getServerInstance().shutdown());
        }
        clearEventLoopGroupFactory();
        logAfterAll(LOGGER, context);
    }
}
