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

import io.rxmicro.test.dbunit.ExpectedDataSet;
import io.rxmicro.test.dbunit.internal.component.OrderByColumnExtractor;
import io.rxmicro.test.dbunit.internal.component.RxMicroDefaultFailureHandler;
import org.dbunit.DatabaseUnitException;
import org.dbunit.assertion.DbUnitAssert;
import org.dbunit.assertion.FailureHandler;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.ITableIterator;
import org.dbunit.dataset.NoSuchTableException;
import org.dbunit.dataset.ReplacementTable;
import org.dbunit.dataset.SortedTable;
import org.dbunit.dataset.filter.DefaultColumnFilter;

import java.sql.SQLException;
import java.util.Map;
import java.util.Set;

import static io.rxmicro.test.dbunit.Expressions.NULL_VALUE;
import static io.rxmicro.test.dbunit.internal.DataSetLoaders.loadIDataSet;
import static io.rxmicro.test.dbunit.internal.ExceptionReThrowers.convertToCheckedWrapperException;
import static io.rxmicro.test.dbunit.internal.data.ExpressionValueResolver.asExpression;
import static io.rxmicro.test.dbunit.local.DatabaseConnectionHelper.getCurrentDatabaseConnection;

/**
 * @author nedis
 * @since 0.7
 */
public final class DatabaseStateVerifier {

    private final FailureHandler failureHandler = new RxMicroDefaultFailureHandler();

    private final DbUnitAssert dbUnitAssert = new DbUnitAssert();

    private final OrderByColumnExtractor orderByColumnExtractor = new OrderByColumnExtractor();

    public void verifyExpected(final ExpectedDataSet expectedDataSetAnnotation) {
        final IDataSet expectedDataSet = loadIDataSet(expectedDataSetAnnotation.value());
        final DatabaseConnection databaseConnection = getCurrentDatabaseConnection();
        try {
            final IDataSet actualDataSet = databaseConnection.createDataSet();
            assertEquals(expectedDataSet, actualDataSet, expectedDataSetAnnotation);
        } catch (final SQLException | DatabaseUnitException ex) {
            throw convertToCheckedWrapperException(databaseConnection, ex);
        }
    }

    private void assertEquals(final IDataSet expectedDataSet,
                              final IDataSet actualDataSet,
                              final ExpectedDataSet expectedDataSetAnnotation) throws DatabaseUnitException {
        final ITableIterator iterator = expectedDataSet.iterator();
        final Map<String, Set<String>> orderByColumnMap =
                orderByColumnExtractor.getOrderByColumns(expectedDataSet, expectedDataSetAnnotation.orderBy());
        while (iterator.next()) {
            ITable expectedTable = iterator.getTable();
            ITable actualTable;
            final String tableName = expectedTable.getTableMetaData().getTableName();
            try {
                actualTable = actualDataSet.getTable(tableName);
            } catch (final NoSuchTableException ex) {
                throw failureHandler.createFailure(
                        "Actual dataset does not contain expected table!",
                        tableName,
                        "null"
                );
            }
            expectedTable = decorateWithReplacementTable(expectedTable);
            actualTable = DefaultColumnFilter.includedColumnsTable(actualTable, expectedTable.getTableMetaData().getColumns());

            final Set<String> orderByColumns = orderByColumnMap.get(tableName);
            if (orderByColumns != null) {
                expectedTable = new SortedTable(expectedTable, orderByColumns.toArray(new String[0]));
                actualTable = new SortedTable(actualTable, orderByColumns.toArray(new String[0]));
            }
            dbUnitAssert.assertEquals(expectedTable, actualTable, failureHandler);
        }
    }

    private ITable decorateWithReplacementTable(final ITable expectedTable) {
        final ReplacementTable replacementTable = new ReplacementTable(expectedTable);
        replacementTable.addReplacementObject(asExpression(NULL_VALUE), null);
        return replacementTable;
    }
}
