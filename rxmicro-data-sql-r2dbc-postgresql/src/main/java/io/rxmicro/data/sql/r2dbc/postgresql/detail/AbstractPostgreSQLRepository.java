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

package io.rxmicro.data.sql.r2dbc.postgresql.detail;

import io.r2dbc.pool.ConnectionPool;
import io.rxmicro.data.sql.r2dbc.detail.AbstractR2DBCRepository;
import io.rxmicro.data.sql.r2dbc.detail.RepositoryConnectionFactory;
import io.rxmicro.data.sql.r2dbc.detail.RepositoryConnectionPool;

import static io.rxmicro.common.util.Formats.FORMAT_PLACEHOLDER_CHAR;

/**
 * Used by generated code that created by the {@code RxMicro Annotation Processor}.
 *
 * <p>
 * Represents a base class for the PostgreSQL data repository implementations.
 *
 * <p>
 * Read more:
 * <a href="https://docs.rxmicro.io/latest/user-guide/data-postgresql.html#data-postgresql-section">
 *     https://docs.rxmicro.io/latest/user-guide/data-postgresql.html#data-postgresql-section
 * </a>
 *
 * @author nedis
 * @since 0.1
 */
public abstract class AbstractPostgreSQLRepository extends AbstractR2DBCRepository {

    /**
     * Represents the connection factory.
     */
    protected final RepositoryConnectionFactory connectionFactory;

    /**
     * Creates an instance of {@link AbstractPostgreSQLRepository} type.
     *
     * @param repositoryInterface the specified repository interface
     * @param pool connection pool
     */
    protected AbstractPostgreSQLRepository(final Class<?> repositoryInterface, final ConnectionPool pool) {
        super(repositoryInterface);
        this.connectionFactory = new RepositoryConnectionPool(repositoryInterface, pool);
    }

    /**
     * Replaces the universal placeholders.
     *
     * @param customSQL the custom SQL statement
     * @return the custom SQL statement with replaced universal placeholders
     */
    protected final String replaceUniversalPlaceholder(final String customSQL) {
        if (customSQL.indexOf(FORMAT_PLACEHOLDER_CHAR) == -1) {
            return customSQL;
        } else {
            int index = 1;
            final StringBuilder sqlBuilder = new StringBuilder(customSQL.length() + 5);
            for (int i = 0; i < customSQL.length(); i++) {
                final char ch = customSQL.charAt(i);
                if (ch == FORMAT_PLACEHOLDER_CHAR) {
                    sqlBuilder.append('$').append(index++);
                } else {
                    sqlBuilder.append(ch);
                }
            }
            return sqlBuilder.toString();
        }
    }
}
