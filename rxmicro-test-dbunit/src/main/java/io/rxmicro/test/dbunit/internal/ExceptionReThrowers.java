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

import io.rxmicro.common.CheckedWrapperException;
import org.dbunit.database.DatabaseConnection;

import java.sql.SQLException;

import static io.rxmicro.test.dbunit.local.DatabaseConnectionFactory.getCashedConnectionSetting;

/**
 * @author nedis
 * @since 0.9
 */
public final class ExceptionReThrowers {

    public static CheckedWrapperException convertToCheckedWrapperException(final DatabaseConnection databaseConnection,
                                                                           final Throwable throwable) {
        if (throwable instanceof SQLException) {
            final String jdbcUrl = getCashedConnectionSetting(databaseConnection);
            return new CheckedWrapperException(
                    throwable,
                    "DB error using 'DatabaseConnection#?' connection with '?' jdbc url detected: ?",
                    System.identityHashCode(databaseConnection), jdbcUrl, throwable.getMessage()
            );
        } else {
            return new CheckedWrapperException(throwable);
        }
    }

    private ExceptionReThrowers() {
    }
}
