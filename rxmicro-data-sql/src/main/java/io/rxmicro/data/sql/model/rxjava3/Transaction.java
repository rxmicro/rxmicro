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
import io.reactivex.rxjava3.core.CompletableSource;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.MaybeSource;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleSource;
import io.reactivex.rxjava3.functions.Function;
import io.rxmicro.data.sql.model.IsolationLevel;
import io.rxmicro.data.sql.model.SavePoint;
import org.reactivestreams.Publisher;

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
     * This factory method allows simplifying the setting of the error handler.
     *
     * <p>
     * Instead of long fragment:
     * <pre><code>
     * .onErrorResumeNext(e -> transaction.rollback()
     *          .andThen(Maybe.error(e))
     * )
     * </code></pre>
     *
     * <p>
     * You can use the shortest version:
     * <pre><code>
     * .onErrorResumeNext(transaction.createRollbackThenReturnMaybeErrorFallback());
     * </code></pre>
     *
     * @param <T> the type of the single value of returned {@link Maybe}
     * @return the function that handles errors
     * @see Maybe#onErrorResumeNext(Function)
     */
    default <T> Function<? super Throwable, ? extends MaybeSource<? extends T>> createRollbackThenReturnMaybeErrorFallback() {
        return throwable -> rollback()
                .andThen(Maybe.error(throwable));
    }

    /**
     * This factory method allows simplifying the setting of the error handler.
     *
     * <p>
     * Instead of long fragment:
     * <pre><code>
     * .onErrorResumeNext(e -> transaction.rollback()
     *          .andThen(Single.error(e))
     * )
     * </code></pre>
     *
     * <p>
     * You can use the shortest version:
     * <pre><code>
     * .onErrorResumeNext(transaction.createRollbackThenReturnSingleErrorFallback());
     * </code></pre>
     *
     * @param <T> the type of the single value of returned {@link Single}
     * @return the function that handles errors
     * @see Single#onErrorResumeNext(Function)
     */
    default <T> Function<? super Throwable, ? extends SingleSource<? extends T>> createRollbackThenReturnSingleErrorFallback() {
        return throwable -> rollback()
                .andThen(Single.error(throwable));
    }

    /**
     * This factory method allows simplifying the setting of the error handler.
     *
     * <p>
     * Instead of long fragment:
     * <pre><code>
     * .onErrorResumeNext(e -> transaction.rollback()
     *          .andThen(Completable.error(e))
     * )
     * </code></pre>
     *
     * <p>
     * You can use the shortest version:
     * <pre><code>
     * .onErrorResumeNext(transaction.createRollbackThenReturnCompletableErrorFallback());
     * </code></pre>
     *
     * @return the function that handles errors
     * @see Completable#onErrorResumeNext(Function)
     */
    default Function<? super Throwable, ? extends CompletableSource> createRollbackThenReturnCompletableErrorFallback() {
        return throwable -> rollback()
                .andThen(Completable.error(throwable));
    }

    /**
     * This factory method allows simplifying the setting of the error handler.
     *
     * <p>
     * Instead of long fragment:
     * <pre><code>
     * .onErrorResumeNext(e -> transaction.rollback()
     *          .andThen(Flowable.error(e))
     * )
     * </code></pre>
     *
     * <p>
     * You can use the shortest version:
     * <pre><code>
     * .onErrorResumeNext(transaction.createRollbackThenReturnFlowableErrorFallback());
     * </code></pre>
     *
     * @param <T> the type of the single value of returned {@link Flowable}
     * @return the function that handles errors
     * @see Flowable#onErrorResumeNext(Function)
     */
    default <T> Function<? super Throwable, ? extends Publisher<? extends T>> createRollbackThenReturnFlowableErrorFallback() {
        return throwable -> rollback()
                .andThen(Flowable.error(throwable));
    }

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
