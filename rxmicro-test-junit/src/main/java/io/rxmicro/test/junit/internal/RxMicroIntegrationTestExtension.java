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

import io.rxmicro.test.BlockingHttpClient;
import io.rxmicro.test.junit.RxMicroIntegrationTest;
import io.rxmicro.test.local.BlockingHttpClientConfig;
import io.rxmicro.test.local.component.builder.TestModelBuilder;
import io.rxmicro.test.local.component.injector.BlockingHttpClientInjector;
import io.rxmicro.test.local.component.injector.InjectorFactory;
import io.rxmicro.test.local.component.injector.SystemOutInjector;
import io.rxmicro.test.local.component.validator.IntegrationTestValidator;
import io.rxmicro.test.local.model.TestModel;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.List;

import static io.rxmicro.test.junit.internal.StatelessComponentFactory.getBeforeTestInvoker;
import static io.rxmicro.test.junit.internal.StatelessComponentFactory.getBlockingHttpClientBuilder;
import static io.rxmicro.test.junit.local.TestObjects.SUPPORTED_TEST_ANNOTATIONS;
import static io.rxmicro.test.junit.local.TestObjects.getOwnerTestClass;
import static io.rxmicro.test.junit.local.TestObjects.getTestInstances;
import static io.rxmicro.test.local.UnNamedModuleFixers.integrationTestsFix;
import static io.rxmicro.test.local.util.Annotations.getRequiredAnnotation;

/**
 * @author nedis
 * @since 0.1
 * @link https://junit.org/junit5/docs/current/user-guide/#extensions-execution-order-overview
 */
public final class RxMicroIntegrationTestExtension
        implements BeforeAllCallback, BeforeEachCallback, AfterEachCallback {

    static {
        integrationTestsFix();
    }

    private BlockingHttpClient blockingHttpClient;

    private BlockingHttpClientInjector blockingHttpClientInjector;

    private SystemOutInjector systemOutInjector;

    @Override
    public void beforeAll(final ExtensionContext context) {
        final Class<?> testClass = getOwnerTestClass(context);
        getRequiredAnnotation(testClass, RxMicroIntegrationTest.class);
        final TestModel testModel = new TestModelBuilder(false).build(testClass);
        final IntegrationTestValidator integrationTestValidator = new IntegrationTestValidator(SUPPORTED_TEST_ANNOTATIONS);

        integrationTestValidator.validate(testModel);
        final InjectorFactory injectorFactory = new InjectorFactory(testModel);
        blockingHttpClientInjector = injectorFactory.createBlockingHttpClientInjector();
        systemOutInjector = injectorFactory.createSystemOutInjector();
        if (blockingHttpClientInjector.hasField()) {
            final BlockingHttpClientConfig config =
                    blockingHttpClientInjector.getConfig(testClass, true, 8080);
            integrationTestValidator.validate(config);
            blockingHttpClient = getBlockingHttpClientBuilder().build(testClass, config);
        }
    }

    @Override
    public void beforeEach(final ExtensionContext context) {
        getBeforeTestInvoker().throwErrorIfFound(context);
        final List<Object> testInstances = getTestInstances(context);
        blockingHttpClientInjector.injectIfFound(testInstances, blockingHttpClient);
        systemOutInjector.injectIfFound(testInstances);
    }

    @Override
    public void afterEach(final ExtensionContext context) {
        systemOutInjector.resetIfNecessary();
    }
}
