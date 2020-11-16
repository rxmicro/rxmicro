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

package io.rxmicro.test.dbunit.local;

import io.rxmicro.common.CheckedWrapperException;
import io.rxmicro.test.dbunit.InitialDataSet;
import io.rxmicro.test.dbunit.internal.AbstractDatabaseStateChanger;
import org.dbunit.DatabaseUnitException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;

import java.sql.SQLException;
import java.util.Arrays;

import static io.rxmicro.test.dbunit.internal.DataSetLoaders.loadIDataSet;
import static io.rxmicro.test.dbunit.local.DatabaseConnectionHelper.getCurrentDatabaseConnection;
import static java.util.stream.Collectors.toList;

/**
 * @author nedis
 * @since 0.7
 */
public final class DatabaseStateInitializer extends AbstractDatabaseStateChanger {

    public void initWith(final InitialDataSet initialDataSet) {
        final IDataSet dataSet = loadIDataSet(initialDataSet.value());
        try {
            executeBeforeStatementsAndScripts(initialDataSet);
            final DatabaseOperation operation = initialDataSet.initDatabaseStrategy().getOperation();
            operation.execute(getCurrentDatabaseConnection(), dataSet);
        } catch (final DatabaseUnitException | SQLException ex) {
            throw new CheckedWrapperException(ex);
        }
    }

    private void executeBeforeStatementsAndScripts(final InitialDataSet initialDataSet) throws SQLException {
        if (initialDataSet.executeStatementsBefore().length > 0) {
            executeJdbcStatements(Arrays.stream(initialDataSet.executeStatementsBefore())
                    .filter(s -> !s.trim().isEmpty())
                    .collect(toList()));
        }
        if (initialDataSet.executeScriptsBefore().length > 0) {
            executeSqlScripts(Arrays.stream(initialDataSet.executeScriptsBefore())
                    .filter(s -> !s.trim().isEmpty())
                    .collect(toList()));
        }
    }
}
