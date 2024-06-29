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

import io.rxmicro.common.CheckedWrapperException;
import io.rxmicro.config.Configs;
import io.rxmicro.logger.Logger;
import io.rxmicro.logger.LoggerFactory;
import io.rxmicro.test.dbunit.ExpectedDataSet;
import io.rxmicro.test.dbunit.InitialDataSet;
import io.rxmicro.test.dbunit.RollbackChanges;
import io.rxmicro.test.dbunit.TestDatabaseConfig;
import io.rxmicro.test.dbunit.junit.DbUnitTest;
import io.rxmicro.test.dbunit.junit.RetrieveConnectionStrategy;
import io.rxmicro.test.dbunit.local.component.DatabaseStateInitializer;
import io.rxmicro.test.dbunit.local.component.DatabaseStateRestorer;
import io.rxmicro.test.dbunit.local.component.DatabaseStateVerifier;
import io.rxmicro.test.dbunit.local.component.RollbackChangesController;
import io.rxmicro.test.dbunit.local.component.validator.DBUnitTestValidator;
import io.rxmicro.test.local.component.builder.TestModelBuilder;
import io.rxmicro.test.local.model.TestModel;
import org.dbunit.database.DatabaseConnection;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

import static io.rxmicro.common.util.Exceptions.reThrow;
import static io.rxmicro.config.Configs.getConfig;
import static io.rxmicro.test.dbunit.TestDatabaseConfig.getCurrentTestDatabaseConfig;
import static io.rxmicro.test.dbunit.TestDatabaseConfig.releaseCurrentTestDatabaseConfig;
import static io.rxmicro.test.dbunit.junit.RetrieveConnectionStrategy.PER_ALL_TEST_CLASSES;
import static io.rxmicro.test.dbunit.junit.RetrieveConnectionStrategy.PER_TEST_CLASS;
import static io.rxmicro.test.dbunit.junit.RetrieveConnectionStrategy.PER_TEST_METHOD;
import static io.rxmicro.test.dbunit.local.DatabaseConnectionFactory.createNewDatabaseConnection;
import static io.rxmicro.test.dbunit.local.DatabaseConnectionHelper.getSharedDatabaseConnection;
import static io.rxmicro.test.dbunit.local.DatabaseConnectionHelper.isCurrentDatabaseConnectionPresent;
import static io.rxmicro.test.dbunit.local.DatabaseConnectionHelper.isCurrentDatabaseConnectionShared;
import static io.rxmicro.test.dbunit.local.DatabaseConnectionHelper.releaseCurrentDatabaseConnection;
import static io.rxmicro.test.dbunit.local.DatabaseConnectionHelper.setCurrentDatabaseConnection;
import static io.rxmicro.test.junit.local.LoggerUtils.logAfterAll;
import static io.rxmicro.test.junit.local.LoggerUtils.logAfterEach;
import static io.rxmicro.test.junit.local.LoggerUtils.logAfterTestExecution;
import static io.rxmicro.test.junit.local.LoggerUtils.logBeforeAll;
import static io.rxmicro.test.junit.local.LoggerUtils.logBeforeEach;
import static io.rxmicro.test.junit.local.LoggerUtils.logBeforeTestExecution;
import static io.rxmicro.test.junit.local.TestObjects.getOwnerTestClass;
import static io.rxmicro.test.junit.local.TestObjects.getTestInstances;
import static io.rxmicro.test.local.component.StatelessComponentFactory.getConfigResolver;
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
 * @since 0.7
 */
public final class DbUnitTestExtension implements
        BeforeAllCallback, BeforeEachCallback, BeforeTestExecutionCallback,
        AfterTestExecutionCallback, AfterEachCallback, AfterAllCallback {

    private static final Logger LOGGER = LoggerFactory.getLogger(DbUnitTestExtension.class);

    private final DatabaseStateInitializer databaseStateInitializer = new DatabaseStateInitializer();

    private final DatabaseStateVerifier databaseStateVerifier = new DatabaseStateVerifier();

    private final RollbackChangesController rollbackChangesController = new RollbackChangesController();

    private final DatabaseStateRestorer databaseStateRestorer = new DatabaseStateRestorer();

    private TestModel testModel;

    private RetrieveConnectionStrategy retrieveConnectionStrategy;

    @Override
    public void beforeAll(final ExtensionContext context) {
        logBeforeAll(LOGGER, context);
        final Class<?> testClass = getOwnerTestClass(context);
        retrieveConnectionStrategy = getRequiredAnnotation(testClass, DbUnitTest.class).retrieveConnectionStrategy();
        testModel = new TestModelBuilder(false).build(testClass);
        new DBUnitTestValidator().validate(testModel);
        getConfigResolver().setDefaultConfigValues(testClass);
        // Each test must use the separate config storage
        if (testModel.isStaticConfigsPresent()) {
            new Configs.Builder()
                    .withConfigs(getConfigResolver().getStaticConfigMap(testModel))
                    .build();
        } else {
            new Configs.Builder().buildIfNotConfigured();
        }
        if (retrieveConnectionStrategy == PER_ALL_TEST_CLASSES) {
            setSharedDatabaseConnection();
        }
    }

    private void setSharedDatabaseConnection() {
        final Optional<DatabaseConnection> sharedDatabaseConnectionOptional = getSharedDatabaseConnection();
        if (sharedDatabaseConnectionOptional.isPresent()) {
            // Shared database connection is already set, so 'shared' must be `false`!
            setCurrentDatabaseConnection(sharedDatabaseConnectionOptional.get(), false);
        } else {
            final DatabaseConnection sharedDatabaseConnection = createNewDatabaseConnection(getConfig(TestDatabaseConfig.class));
            setCurrentDatabaseConnection(sharedDatabaseConnection, true);
        }
    }

    @Override
    public void beforeEach(final ExtensionContext context) {
        logBeforeEach(LOGGER, context);
        // It is necessary to set connection after @BeforeAll, before @BeforeEach and only once per class.
        // See https://junit.org/junit5/docs/current/user-guide/#extensions-execution-order-overview
        if (retrieveConnectionStrategy == PER_TEST_CLASS &&
                (!isCurrentDatabaseConnectionPresent() || isCurrentDatabaseConnectionShared())) {
            setCurrentDatabaseConnection(createNewDatabaseConnection(getCurrentTestDatabaseConfig()), false);
        }
    }

    @Override
    public void beforeTestExecution(final ExtensionContext context) {
        final List<Object> testInstances = getTestInstances(context);
        if (testModel.isInstanceConfigsPresent()) {
            new Configs.Builder()
                    .withConfigs(getConfigResolver().getInstanceConfigMap(testModel, testInstances))
                    .build();
        }
        if (retrieveConnectionStrategy == PER_TEST_METHOD) {
            setCurrentDatabaseConnection(createNewDatabaseConnection(getCurrentTestDatabaseConfig()), false);
        }
        final Method testMethod = context.getRequiredTestMethod();
        Optional.ofNullable(testMethod.getAnnotation(RollbackChanges.class)).ifPresent(rollbackChangesController::startTestTransaction);
        Optional.ofNullable(testMethod.getAnnotation(InitialDataSet.class)).ifPresent(databaseStateInitializer::initWith);
        logBeforeTestExecution(LOGGER, context);
    }

    @Override
    public void afterTestExecution(final ExtensionContext context) {
        logAfterTestExecution(LOGGER, context);
        final Method testMethod = context.getRequiredTestMethod();
        boolean success = false;
        try {
            Optional.ofNullable(testMethod.getAnnotation(ExpectedDataSet.class)).ifPresent(databaseStateVerifier::verifyExpected);
            Optional.ofNullable(testMethod.getAnnotation(InitialDataSet.class)).ifPresent(databaseStateRestorer::restoreStateIfEnabled);
            success = true;
            rollbackChangesIfTestTransactionStarted(null);
        } catch (final CheckedWrapperException | Error ex) {
            if (success) {
                throw ex;
            } else {
                rollbackChangesIfTestTransactionStarted(ex);
            }
        } finally {
            if (retrieveConnectionStrategy == PER_TEST_METHOD) {
                releaseCurrentTestDatabaseConfig();
                releaseCurrentDatabaseConnection();
            }
        }
    }

    private void rollbackChangesIfTestTransactionStarted(final Throwable throwable) {
        try {
            if (rollbackChangesController.isTestTransactionStarted()) {
                rollbackChangesController.rollbackChanges();
            }
        } catch (final CheckedWrapperException ex) {
            if (throwable != null) {
                throwable.addSuppressed(ex);
                reThrow(throwable);
            } else {
                throw ex;
            }
        }
        if (throwable != null) {
            reThrow(throwable);
        }
    }

    @Override
    public void afterEach(final ExtensionContext context) {
        logAfterEach(LOGGER, context);
    }

    @Override
    public void afterAll(final ExtensionContext context) {
        if (retrieveConnectionStrategy == PER_TEST_CLASS) {
            releaseCurrentTestDatabaseConfig();
            releaseCurrentDatabaseConnection();
        }
        logAfterAll(LOGGER, context);
    }
}
