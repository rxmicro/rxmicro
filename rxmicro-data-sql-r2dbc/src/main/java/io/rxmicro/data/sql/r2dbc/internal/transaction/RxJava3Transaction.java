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

import io.reactivex.rxjava3.core.Completable;
import io.rxmicro.data.sql.model.IsolationLevel;
import io.rxmicro.data.sql.model.SavePoint;
import io.rxmicro.data.sql.model.rxjava3.Transaction;
import io.rxmicro.data.sql.r2dbc.internal.AbstractTransaction;
import io.rxmicro.data.sql.r2dbc.detail.RepositoryConnection;

import static io.reactivex.rxjava3.core.Completable.fromPublisher;

/**
 * @author nedis
 * @since 0.1
 */
public final class RxJava3Transaction extends AbstractTransaction implements Transaction {

    public RxJava3Transaction(final RepositoryConnection connection) {
        super(connection);
    }

    @Override
    public Completable commit() {
        return fromPublisher(baseCommit());
    }

    @Override
    public Completable rollback() {
        return fromPublisher(baseRollback());
    }

    @Override
    public Completable rollback(final SavePoint savePoint) {
        return fromPublisher(baseRollback(savePoint));
    }

    @Override
    public Completable create(final SavePoint savePoint) {
        return fromPublisher(baseCreate(savePoint));
    }

    @Override
    public Completable release(final SavePoint savePoint) {
        return fromPublisher(baseRelease(savePoint));
    }

    @Override
    public Completable setIsolationLevel(final IsolationLevel isolationLevel) {
        return fromPublisher(baseSetIsolationLevel(isolationLevel));
    }
}
