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

package io.rxmicro.data.sql.r2dbc.internal.transaction;

import io.r2dbc.spi.Connection;
import io.rxmicro.data.sql.model.IsolationLevel;
import io.rxmicro.data.sql.model.SavePoint;
import io.rxmicro.data.sql.model.completablefuture.Transaction;
import io.rxmicro.data.sql.r2dbc.internal.AbstractTransaction;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public final class CompletableFutureTransaction extends AbstractTransaction implements Transaction {

    public CompletableFutureTransaction(final Connection connection) {
        super(connection);
    }

    @Override
    public CompletableFuture<Void> commit() {
        return Mono.from(_commit()).toFuture();
    }

    @Override
    public CompletableFuture<Void> rollback() {
        return Mono.from(_rollback()).toFuture();
    }

    @Override
    public CompletableFuture<Void> create(final SavePoint savePoint) {
        return Mono.from(_create(savePoint)).toFuture();
    }

    @Override
    public CompletableFuture<Void> release(final SavePoint savePoint) {
        return Mono.from(_release(savePoint)).toFuture();
    }

    @Override
    public CompletableFuture<Void> rollback(final SavePoint savePoint) {
        return Mono.from(_rollback(savePoint)).toFuture();
    }

    @Override
    public CompletableFuture<Void> setIsolationLevel(final IsolationLevel isolationLevel) {
        return Mono.from(_setIsolationLevel(isolationLevel)).toFuture();
    }
}
