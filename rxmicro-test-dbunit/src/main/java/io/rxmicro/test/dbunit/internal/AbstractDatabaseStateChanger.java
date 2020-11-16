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

package io.rxmicro.test.dbunit.internal;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static io.rxmicro.test.dbunit.local.DatabaseConnectionHelper.getCurrentDatabaseConnection;

/**
 * @author nedis
 * @since 0.7
 */
public abstract class AbstractDatabaseStateChanger {

    protected final void executeJdbcStatements(final List<String> sqlStatements) throws SQLException {
        final Connection connection = getCurrentDatabaseConnection().getConnection();
        try (final Statement statement = connection.createStatement()) {
            for (final String sqlStatement : sqlStatements) {
                statement.addBatch(sqlStatement);
            }
            statement.executeBatch();
        }
    }

    protected final void executeSqlScripts(final List<String> sqlScripts) throws SQLException {
        // TODO
    }
}
