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
import io.rxmicro.test.dbunit.ExpectedDataSet;
import io.rxmicro.test.dbunit.InitialDataSet;
import io.rxmicro.test.dbunit.RollbackChanges;
import io.rxmicro.test.dbunit.junit.DbUnitTest;
import io.rxmicro.test.dbunit.junit.RetrieveConnectionStrategy;
import io.rxmicro.test.dbunit.local.DatabaseStateInitializer;
import io.rxmicro.test.dbunit.local.DatabaseStateRestorer;
import io.rxmicro.test.dbunit.local.DatabaseStateVerifier;
import io.rxmicro.test.dbunit.local.RollbackChangesController;
import io.rxmicro.test.dbunit.local.component.validator.DBUnitTestValidator;
import io.rxmicro.test.local.component.builder.TestModelBuilder;
import io.rxmicro.test.local.model.TestModel;
import org.dbunit.database.DatabaseConnection;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

import static io.rxmicro.common.util.Exceptions.reThrow;
import static io.rxmicro.test.dbunit.TestDatabaseConfig.getCurrentTestDatabaseConfig;
import static io.rxmicro.test.dbunit.TestDatabaseConfig.releaseCurrentTestDatabaseConfig;
import static io.rxmicro.test.dbunit.junit.RetrieveConnectionStrategy.PER_ALL_TEST_CLASSES;
import static io.rxmicro.test.dbunit.junit.RetrieveConnectionStrategy.PER_TEST_CLASS;
import static io.rxmicro.test.dbunit.junit.RetrieveConnectionStrategy.PER_TEST_METHOD;
import static io.rxmicro.test.dbunit.local.DatabaseConnectionFactory.createNewDatabaseConnection;
import static io.rxmicro.test.dbunit.local.DatabaseConnectionHelper.closeDatabaseConnection;
import static io.rxmicro.test.dbunit.local.DatabaseConnectionHelper.isCurrentDatabaseConnectionPresent;
import static io.rxmicro.test.dbunit.local.DatabaseConnectionHelper.releaseCurrentDatabaseConnection;
import static io.rxmicro.test.dbunit.local.DatabaseConnectionHelper.setCurrentDatabaseConnection;
import static io.rxmicro.test.junit.local.TestObjects.getOwnerTestClass;
import static io.rxmicro.test.junit.local.TestObjects.getTestInstances;
import static io.rxmicro.test.local.component.StatelessComponentFactory.getConfigResolver;
import static io.rxmicro.test.local.util.Annotations.getRequiredAnnotation;

/**
 * @author nedis
 * @since 0.7
 * @link https://junit.org/junit5/docs/current/user-guide/#extensions-execution-order-overview
 */
public final class DbUnitTestExtension implements
        BeforeAllCallback, BeforeEachCallback, BeforeTestExecutionCallback,
        AfterTestExecutionCallback, AfterAllCallback {

    private final DatabaseStateInitializer databaseStateInitializer = new DatabaseStateInitializer();

    private final DatabaseStateVerifier databaseStateVerifier = new DatabaseStateVerifier();

    private final RollbackChangesController rollbackChangesController = new RollbackChangesController();

    private final DatabaseStateRestorer databaseStateRestorer = new DatabaseStateRestorer();

    private TestModel testModel;

    private RetrieveConnectionStrategy retrieveConnectionStrategy;

    @Override
    public void beforeAll(final ExtensionContext context) {
        final Class<?> testClass = getOwnerTestClass(context);
        retrieveConnectionStrategy = getRequiredAnnotation(testClass, DbUnitTest.class).retrieveConnectionStrategy();
        testModel = new TestModelBuilder(false).build(testClass);
        new DBUnitTestValidator().validate(testModel);
        getConfigResolver().setDefaultConfigValues(testClass);
        if (testModel.isStaticConfigsPresent()) {
            new Configs.Builder()
                    .withConfigs(getConfigResolver().getStaticConfigMap(testModel))
                    .build();
        } else {
            new Configs.Builder().buildIfNotConfigured();
        }
    }

    @Override
    public void beforeEach(final ExtensionContext context) {
        // It is necessary to set connection after @BeforeAll and only one per class.
        // See https://junit.org/junit5/docs/current/user-guide/#extensions-execution-order-overview
        if (!isCurrentDatabaseConnectionPresent()) {
            final DatabaseConnection databaseConnection = createNewDatabaseConnection(getCurrentTestDatabaseConfig());
            if (retrieveConnectionStrategy == PER_TEST_CLASS || retrieveConnectionStrategy == PER_ALL_TEST_CLASSES) {
                setCurrentDatabaseConnection(databaseConnection);
            }
            if (retrieveConnectionStrategy == PER_ALL_TEST_CLASSES) {
                Runtime.getRuntime().addShutdownHook(new Thread(
                        () -> closeDatabaseConnection(databaseConnection),
                        "Close shared database connection hook"
                ));
            }
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
            setCurrentDatabaseConnection(createNewDatabaseConnection(getCurrentTestDatabaseConfig()));
        }
        final Method testMethod = context.getRequiredTestMethod();
        Optional.ofNullable(testMethod.getAnnotation(RollbackChanges.class)).ifPresent(rollbackChangesController::startTestTransaction);
        Optional.ofNullable(testMethod.getAnnotation(InitialDataSet.class)).ifPresent(databaseStateInitializer::initWith);
    }

    @Override
    public void afterTestExecution(final ExtensionContext context) {
        final Method testMethod = context.getRequiredTestMethod();
        boolean success = false;
        try {
            Optional.ofNullable(testMethod.getAnnotation(ExpectedDataSet.class)).ifPresent(databaseStateVerifier::verifyExpected);
            Optional.ofNullable(testMethod.getAnnotation(InitialDataSet.class)).ifPresent(databaseStateRestorer::restoreStateIfEnabled);
            success = true;
            rollbackChangesIfTestTransactionStarted(null);
        } catch (final CheckedWrapperException | Error ex) {
            if (!success) {
                rollbackChangesIfTestTransactionStarted(ex);
            } else {
                throw ex;
            }
        } finally {
            if (retrieveConnectionStrategy == PER_TEST_METHOD) {
                releaseCurrentDatabaseConnection();
                releaseCurrentTestDatabaseConfig();
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
    public void afterAll(final ExtensionContext context) {
        if (retrieveConnectionStrategy == PER_TEST_CLASS) {
            releaseCurrentDatabaseConnection();
        }
        releaseCurrentTestDatabaseConfig();
    }
}
