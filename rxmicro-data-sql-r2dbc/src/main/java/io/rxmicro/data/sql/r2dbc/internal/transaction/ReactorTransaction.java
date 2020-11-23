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

package io.rxmicro.data.sql.r2dbc.internal.transaction;

import io.rxmicro.data.sql.model.IsolationLevel;
import io.rxmicro.data.sql.model.SavePoint;
import io.rxmicro.data.sql.model.reactor.Transaction;
import io.rxmicro.data.sql.r2dbc.detail.RepositoryConnection;
import io.rxmicro.data.sql.r2dbc.internal.AbstractTransaction;
import reactor.core.publisher.Mono;

/**
 * @author nedis
 * @since 0.1
 */
public final class ReactorTransaction extends AbstractTransaction implements Transaction {

    public ReactorTransaction(final RepositoryConnection connection) {
        super(connection);
    }

    @Override
    public Mono<Void> commit() {
        return Mono.from(baseCommit());
    }

    @Override
    public Mono<Void> rollback() {
        return Mono.from(baseRollback());
    }

    @Override
    public Mono<Void> rollback(final SavePoint savePoint) {
        return Mono.from(baseRollback(savePoint));
    }

    @Override
    public Mono<Void> create(final SavePoint savePoint) {
        return Mono.from(baseCreate(savePoint));
    }

    @Override
    public Mono<Void> release(final SavePoint savePoint) {
        return Mono.from(baseRelease(savePoint));
    }

    @Override
    public Mono<Void> setIsolationLevel(final IsolationLevel isolationLevel) {
        return Mono.from(baseSetIsolationLevel(isolationLevel));
    }
}
