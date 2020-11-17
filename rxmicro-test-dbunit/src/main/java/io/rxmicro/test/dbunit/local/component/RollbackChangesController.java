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

package io.rxmicro.test.dbunit.local.component;

import io.rxmicro.common.CheckedWrapperException;
import io.rxmicro.test.dbunit.RollbackChanges;

import java.sql.Connection;
import java.sql.SQLException;

import static io.rxmicro.test.dbunit.local.DatabaseConnectionHelper.getCurrentDatabaseConnection;

/**
 * @author nedis
 * @since 0.7
 */
public final class RollbackChangesController {

    private boolean testTransactionStarted;

    private boolean previousAutoCommitState = true;

    private int previousIsolationLevel = Connection.TRANSACTION_NONE;

    @SuppressWarnings("MagicConstant")
    public void startTestTransaction(final RollbackChanges rollbackChanges) {
        try {
            final Connection connection = getCurrentDatabaseConnection().getConnection();
            previousAutoCommitState = connection.getAutoCommit();
            connection.setAutoCommit(false);
            if (rollbackChanges.isolationLevel() != RollbackChanges.IsolationLevel.DEFAULT) {
                previousIsolationLevel = connection.getTransactionIsolation();
                connection.setTransactionIsolation(rollbackChanges.isolationLevel().getLevel());
            }
        } catch (final SQLException ex) {
            throw new CheckedWrapperException(ex);
        }
        testTransactionStarted = true;
    }

    public boolean isTestTransactionStarted() {
        return testTransactionStarted;
    }

    public void rollbackChanges() {
        try {
            final Connection connection = getCurrentDatabaseConnection().getConnection();
            connection.rollback();
            if (previousAutoCommitState != connection.getAutoCommit()) {
                connection.setAutoCommit(previousAutoCommitState);
            }
            if (previousIsolationLevel != Connection.TRANSACTION_NONE &&
                    previousIsolationLevel != connection.getTransactionIsolation()) {
                connection.setTransactionIsolation(previousIsolationLevel);
            }
        } catch (final SQLException ex) {
            throw new CheckedWrapperException(ex);
        } finally {
            testTransactionStarted = false;
        }
    }
}
