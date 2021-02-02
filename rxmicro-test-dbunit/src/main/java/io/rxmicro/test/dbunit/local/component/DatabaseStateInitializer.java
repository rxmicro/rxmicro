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
import io.rxmicro.test.dbunit.internal.data.TestValueProvider;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.operation.DatabaseOperation;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Map;

import static io.rxmicro.test.dbunit.Expressions.NULL_VALUE;
import static io.rxmicro.test.dbunit.internal.DataSetLoaders.loadIDataSet;
import static io.rxmicro.test.dbunit.internal.ExceptionReThrowers.convertToCheckedWrapperException;
import static io.rxmicro.test.dbunit.internal.TestValueProviders.getAllTestValueProviders;
import static io.rxmicro.test.dbunit.internal.data.ExpressionValueResolver.asExpression;
import static io.rxmicro.test.dbunit.local.DatabaseConnectionHelper.getCurrentDatabaseConnection;
import static java.util.stream.Collectors.toList;

/**
 * @author nedis
 * @since 0.7
 */
public final class DatabaseStateInitializer extends AbstractDatabaseStateChanger {

    public void initWith(final InitialDataSet initialDataSet) {
        final DatabaseConnection databaseConnection = getCurrentDatabaseConnection();
        IDataSet dataSet = loadIDataSet(initialDataSet.value());
        try {
            dataSet = decorateWithReplacementDataSet(dataSet);
            executeBeforeStatementsAndScripts(databaseConnection, initialDataSet);
            final DatabaseOperation operation = initialDataSet.initDatabaseStrategy().getOperation();
            operation.execute(databaseConnection, dataSet);
        } catch (final DatabaseUnitException | SQLException ex) {
            throw convertToCheckedWrapperException(databaseConnection, ex);
        }
    }

    private IDataSet decorateWithReplacementDataSet(final IDataSet dataSet) {
        final ReplacementDataSet replacementDataSet = new ReplacementDataSet(dataSet);
        replacementDataSet.addReplacementObject(asExpression(NULL_VALUE), null);
        for (final Map.Entry<String, TestValueProvider> entry : getAllTestValueProviders()) {
            replacementDataSet.addReplacementObject(asExpression(entry.getKey()), entry.getValue().getValue());
        }
        return replacementDataSet;
    }

    private void executeBeforeStatementsAndScripts(final DatabaseConnection databaseConnection,
                                                   final InitialDataSet initialDataSet) throws SQLException {
        if (initialDataSet.executeStatementsBefore().length > 0) {
            executeJdbcStatements(
                    databaseConnection,
                    Arrays.stream(initialDataSet.executeStatementsBefore()).filter(s -> !s.trim().isEmpty()).collect(toList())
            );
        }
        if (initialDataSet.executeScriptsBefore().length > 0) {
            executeSqlScripts(
                    databaseConnection,
                    Arrays.stream(initialDataSet.executeScriptsBefore()).filter(s -> !s.trim().isEmpty()).collect(toList())
            );
        }
    }
}
