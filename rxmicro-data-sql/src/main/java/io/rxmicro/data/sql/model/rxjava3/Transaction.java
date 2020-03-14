/*
 * Copyright (c) 2020. http://rxmicro.io
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

package io.rxmicro.data.sql.model.rxjava3;

import io.reactivex.rxjava3.core.Completable;
import io.rxmicro.data.sql.model.IsolationLevel;
import io.rxmicro.data.sql.model.SavePoint;

/**
 * Current implementation delegates all method calls to <code>io.r2dbc.spi.Connection</code>,
 * but in future this API can be extended by using other SPI
 *
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public interface Transaction {

    /**
     * Commits the current transaction and close transactional connection.
     *
     * @return a {@link Completable} that indicates that a transaction has been committed and a connection has been closed.
     */
    Completable commit();

    /**
     * Rolls back the current transaction and close transactional connection.
     *
     * @return a {@link Completable} that indicates that a transaction has been rolled back and a connection has been closed.
     */
    Completable rollback();

    /**
     * Creates a save point in the current transaction.
     *
     * @param savePoint the save point to create
     * @return a {@link Completable} that indicates that a save point has been created
     * @throws UnsupportedOperationException if save points are not supported
     */
    Completable create(SavePoint savePoint);

    /**
     * Releases a save point in the current transaction.  Calling this for drivers not supporting save point release results in a no-op.
     *
     * @param savePoint the save point to release
     * @return a {@link Completable} that indicates that a save point has been released
     */
    Completable release(SavePoint savePoint);

    /**
     * Rolls back to a save point in the current transaction.
     *
     * @param savePoint the save point to rollback to
     * @return a {@link Completable} that indicates that a save point has been rolled back to
     * @throws UnsupportedOperationException if save points are not supported
     */
    Completable rollback(SavePoint savePoint);

    /**
     * Returns the {@link IsolationLevel} for this connection.
     * <p>Isolation level is typically one of the following constants:
     *
     * <ul>
     * <li>{@link IsolationLevel#READ_UNCOMMITTED}</li>
     * <li>{@link IsolationLevel#READ_COMMITTED}</li>
     * <li>{@link IsolationLevel#REPEATABLE_READ}</li>
     * <li>{@link IsolationLevel#SERIALIZABLE}</li>
     * </ul>
     * <p>
     *
     * @return the {@link IsolationLevel} for this connection.
     */
    IsolationLevel getIsolationLevel();

    /**
     * Configures the isolation level for the current transaction.
     * <p>Isolation level is typically one of the following constants:
     *
     * <ul>
     * <li>{@link IsolationLevel#READ_UNCOMMITTED}</li>
     * <li>{@link IsolationLevel#READ_COMMITTED}</li>
     * <li>{@link IsolationLevel#REPEATABLE_READ}</li>
     * <li>{@link IsolationLevel#SERIALIZABLE}</li>
     * </ul>
     * <p>
     *
     * @param isolationLevel the isolation level for this transaction
     * @return a {@link Completable} that indicates that a transaction level has been configured
     * @throws NullPointerException if {@code isolationLevel} is {@code null}
     */
    Completable setIsolationLevel(IsolationLevel isolationLevel);
}
