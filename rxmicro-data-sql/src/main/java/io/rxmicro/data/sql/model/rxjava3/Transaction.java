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

package io.rxmicro.data.sql.model.rxjava3;

import io.reactivex.rxjava3.core.Completable;
import io.rxmicro.data.sql.model.IsolationLevel;
import io.rxmicro.data.sql.model.SavePoint;

/**
 * Current implementation delegates all method calls to {@code io.r2dbc.spi.Connection},
 * but in future this API can be extended by using other SPI.
 *
 * @author nedis
 * @see io.rxmicro.data.sql.model.TransactionType
 * @see io.rxmicro.data.sql.model.reactor.Transaction
 * @see io.rxmicro.data.sql.model.completablefuture.Transaction
 * @see SavePoint
 * @see IsolationLevel
 * @since 0.1
 */
public interface Transaction {

    /**
     * Commits the current transaction and close transactional connection.
     *
     * @return the {@link Completable} that indicates that the transaction has been committed and the connection has been closed.
     */
    Completable commit();

    /**
     * Rolls back the current transaction and close transactional connection.
     *
     * @return the {@link Completable} that indicates that the transaction has been rolled back and the connection has been closed.
     */
    Completable rollback();

    /**
     * Rolls back to the save point in the current transaction.
     *
     * @param savePoint the save point to rollback to
     * @return the {@link Completable} that indicates that the save point has been rolled back to
     * @throws UnsupportedOperationException if save points are not supported
     * @throws IllegalArgumentException if the specified save point is invalid
     */
    Completable rollback(SavePoint savePoint);

    /**
     * Creates the save point in the current transaction.
     *
     * @param savePoint the save point to create
     * @return the {@link Completable} that indicates that the save point has been created
     * @throws UnsupportedOperationException if save points are not supported
     * @throws IllegalArgumentException if the specified save point is invalid
     */
    Completable create(SavePoint savePoint);

    /**
     * Releases the save point in the current transaction.
     * Calling this for drivers not supporting save point release results in a no-op.
     *
     * @param savePoint the save point to release
     * @return the {@link Completable} that indicates that the save point has been released
     * @throws IllegalArgumentException if the specified save point is invalid
     */
    Completable release(SavePoint savePoint);

    /**
     * Returns the {@link IsolationLevel} for this connection.
     *
     * <p>
     * Isolation level is typically one of the following constants:
     *
     * <ul>
     * <li>{@link IsolationLevel#READ_UNCOMMITTED}</li>
     * <li>{@link IsolationLevel#READ_COMMITTED}</li>
     * <li>{@link IsolationLevel#REPEATABLE_READ}</li>
     * <li>{@link IsolationLevel#SERIALIZABLE}</li>
     * </ul>
     *
     * @return the {@link IsolationLevel} for this connection.
     */
    IsolationLevel getIsolationLevel();

    /**
     * Configures the isolation level for the current transaction.
     *
     * <p>
     * Isolation level is typically one of the following constants:
     *
     * <ul>
     * <li>{@link IsolationLevel#READ_UNCOMMITTED}</li>
     * <li>{@link IsolationLevel#READ_COMMITTED}</li>
     * <li>{@link IsolationLevel#REPEATABLE_READ}</li>
     * <li>{@link IsolationLevel#SERIALIZABLE}</li>
     * </ul>
     *
     * @param isolationLevel the isolation level for this transaction
     * @return the {@link Completable} that indicates that the transaction level has been configured
     * @throws NullPointerException if {@code isolationLevel} is {@code null}
     */
    Completable setIsolationLevel(IsolationLevel isolationLevel);
}
