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
import io.rxmicro.test.dbunit.ExpectedDataSet;
import org.dbunit.Assertion;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseDataSet;
import org.dbunit.dataset.IDataSet;

import java.sql.SQLException;

import static io.rxmicro.test.dbunit.internal.DataSetLoaders.loadIDataSet;
import static io.rxmicro.test.dbunit.local.DatabaseConnectionHelper.getCurrentDatabaseConnection;

/**
 * @author nedis
 * @since 0.7
 */
public final class DatabaseStateVerifier {

    public void verifyExpected(final ExpectedDataSet expectedDataSetAnnotation) {
        final IDataSet expectedDataSet = loadIDataSet(expectedDataSetAnnotation.value());
        try {
            final IDataSet actualDataSet = getCurrentDatabaseConnection().createDataSet();

            Assertion.assertEquals(expectedDataSet, actualDataSet);
        } catch (final SQLException | DatabaseUnitException ex) {
            throw new CheckedWrapperException(ex);
        }
    }
}
