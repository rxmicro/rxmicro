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

import io.rxmicro.test.dbunit.InitialDataSet;
import io.rxmicro.test.dbunit.internal.component.AbstractDatabaseStateChanger;
import org.dbunit.database.DatabaseConnection;

import java.sql.SQLException;
import java.util.Arrays;

import static io.rxmicro.test.dbunit.internal.ExceptionReThrowers.convertToCheckedWrapperException;
import static io.rxmicro.test.dbunit.local.DatabaseConnectionHelper.getCurrentDatabaseConnection;
import static java.util.stream.Collectors.toList;

/**
 * @author nedis
 * @since 0.7
 */
public final class DatabaseStateRestorer extends AbstractDatabaseStateChanger {

    public void restoreStateIfEnabled(final InitialDataSet initialDataSet) {
        final DatabaseConnection databaseConnection = getCurrentDatabaseConnection();
        try {
            if (initialDataSet.executeStatementsAfter().length > 0) {
                executeJdbcStatements(
                        databaseConnection,
                        Arrays.stream(initialDataSet.executeStatementsAfter()).filter(s -> !s.trim().isEmpty()).collect(toList())
                );
            }
            if (initialDataSet.executeScriptsAfter().length > 0) {
                executeSqlScripts(
                        databaseConnection,
                        Arrays.stream(initialDataSet.executeScriptsAfter()).filter(s -> !s.trim().isEmpty()).collect(toList())
                );
            }
        } catch (final SQLException ex) {
            throw convertToCheckedWrapperException(databaseConnection, ex);
        }
    }
}
