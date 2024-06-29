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

package io.rxmicro.test.dbunit.internal.db.postgres;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;

import java.sql.Connection;

import static org.dbunit.database.DatabaseConfig.PROPERTY_DATATYPE_FACTORY;
import static org.dbunit.database.DatabaseConfig.PROPERTY_ESCAPE_PATTERN;

/**
 * @author nedis
 * @link http://dbunit.sourceforge.net/properties.html
 * @since 0.7
 */
public final class RxMicroPostgresDatabaseConnection extends DatabaseConnection {

    public RxMicroPostgresDatabaseConnection(final Connection connection) throws DatabaseUnitException {
        super(connection);
        customize();
    }

    public RxMicroPostgresDatabaseConnection(final Connection connection,
                                             final String schema,
                                             final boolean validate) throws DatabaseUnitException {
        super(connection, schema, validate);
        customize();
    }

    private void customize() {
        getConfig().setProperty(PROPERTY_DATATYPE_FACTORY, new RxMicroPostgresqlDataTypeFactory());
        getConfig().setProperty(PROPERTY_ESCAPE_PATTERN, "\"?\"");
    }
}
