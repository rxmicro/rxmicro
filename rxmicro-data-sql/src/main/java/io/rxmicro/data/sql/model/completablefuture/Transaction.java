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

package io.rxmicro.data.sql.model.completablefuture;

import io.rxmicro.data.sql.model.IsolationLevel;
import io.rxmicro.data.sql.model.SavePoint;

import java.util.concurrent.CompletableFuture;

/**
 * Current implementation delegates all method calls to {@code io.r2dbc.spi.Connection},
 * but in future this API can be extended by using other SPI
 *
 * @author nedis
 * @since 0.1
 * @see io.rxmicro.data.sql.model.TransactionType
 * @see io.rxmicro.data.sql.model.reactor.Transaction
 * @see io.rxmicro.data.sql.model.rxjava3.Transaction
 * @see SavePoint
 * @see IsolationLevel
 */
public interface Transaction {

    /**
     * Commits the current transaction and close transactional connection.
     *
     * @return the {@link CompletableFuture} that indicates that the transaction has been committed and the connection has been closed.
     */
    CompletableFuture<Void> commit();

    /**
     * Rolls back the current transaction and close transactional connection.
     *
     * @return the {@link CompletableFuture} that indicates that the transaction has been rolled back and the connection has been closed.
     */
    CompletableFuture<Void> rollback();

    /**
     * Rolls back to the save point in the current transaction.
     *
     * @param savePoint the save point to rollback to
     * @return the {@link CompletableFuture} that indicates that the save point has been rolled back to
     * @throws UnsupportedOperationException if save points are not supported
     * @throws IllegalArgumentException if the specified save point is invalid
     */
    CompletableFuture<Void> rollback(SavePoint savePoint);

    /**
     * Creates the save point in the current transaction.
     *
     * @param savePoint the save point to create
     * @return the {@link CompletableFuture} that indicates that the save point has been created
     * @throws UnsupportedOperationException if save points are not supported
     * @throws IllegalArgumentException if the specified save point is invalid
     */
    CompletableFuture<Void> create(SavePoint savePoint);

    /**
     * Releases the save point in the current transaction.  Calling this for drivers not supporting save point release results in a no-op.
     *
     * @param savePoint the save point to release
     * @return the {@link CompletableFuture} that indicates that the save point has been released
     * @throws IllegalArgumentException if the specified save point is invalid
     */
    CompletableFuture<Void> release(SavePoint savePoint);

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
     * @return the {@link CompletableFuture} that indicates that the transaction level has been configured
     * @throws NullPointerException if {@code isolationLevel} is {@code null}
     */
    CompletableFuture<Void> setIsolationLevel(IsolationLevel isolationLevel);
}
