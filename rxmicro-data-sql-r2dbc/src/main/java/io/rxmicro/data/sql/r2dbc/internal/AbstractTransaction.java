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

package io.rxmicro.data.sql.r2dbc.internal;

import io.rxmicro.common.InvalidStateException;
import io.rxmicro.data.sql.model.IsolationLevel;
import io.rxmicro.data.sql.model.SavePoint;
import io.rxmicro.data.sql.r2dbc.detail.RepositoryConnection;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @since 0.1
 */
public abstract class AbstractTransaction {

    private static final Map<IsolationLevel, io.r2dbc.spi.IsolationLevel> MAPPING = Map.of(
            IsolationLevel.READ_COMMITTED, io.r2dbc.spi.IsolationLevel.READ_COMMITTED,
            IsolationLevel.READ_UNCOMMITTED, io.r2dbc.spi.IsolationLevel.READ_UNCOMMITTED,
            IsolationLevel.REPEATABLE_READ, io.r2dbc.spi.IsolationLevel.REPEATABLE_READ,
            IsolationLevel.SERIALIZABLE, io.r2dbc.spi.IsolationLevel.SERIALIZABLE
    );

    private static final Map<io.r2dbc.spi.IsolationLevel, IsolationLevel> REVERSE_MAPPING = Map.of(
            io.r2dbc.spi.IsolationLevel.READ_COMMITTED, IsolationLevel.READ_COMMITTED,
            io.r2dbc.spi.IsolationLevel.READ_UNCOMMITTED, IsolationLevel.READ_UNCOMMITTED,
            io.r2dbc.spi.IsolationLevel.REPEATABLE_READ, IsolationLevel.REPEATABLE_READ,
            io.r2dbc.spi.IsolationLevel.SERIALIZABLE, IsolationLevel.SERIALIZABLE
    );

    private final RepositoryConnection connection;

    private List<SavePoint> savePoints = List.of();

    private boolean active;

    protected AbstractTransaction(final RepositoryConnection connection) {
        this.connection = require(connection);
        this.active = true;
    }

    RepositoryConnection getConnection() {
        return connection;
    }

    protected Publisher<Void> baseCommit() {
        checkActive();
        active = false;
        return Mono.from(connection.commitTransaction())
                .then(Mono.from(connection.close()));
    }

    protected Publisher<Void> baseRollback() {
        checkActive();
        active = false;
        return Mono.from(connection.rollbackTransaction())
                .then(Mono.from(connection.close()));
    }

    protected Publisher<Void> baseRollback(final SavePoint savePoint) {
        checkActive();
        if (savePoints.contains(savePoint)) {
            final ListIterator<SavePoint> listIterator = savePoints.listIterator(savePoints.size());
            while (listIterator.hasPrevious()) {
                final SavePoint previous = listIterator.previous();
                if (savePoint.equals(previous)) {
                    break;
                } else {
                    listIterator.remove();
                }
            }
        } else {
            throw new IllegalArgumentException("Save point not defined: " + savePoint);
        }
        return connection.rollbackTransaction();
    }

    protected Publisher<Void> baseCreate(final SavePoint savePoint) {
        checkActive();
        if (savePoints.getClass() == List.of().getClass()) {
            savePoints = new ArrayList<>();
        } else if (savePoints.contains(savePoint)) {
            throw new IllegalArgumentException("Save point already defined: " + savePoint);
        }
        savePoints.add(savePoint);
        return connection.createSavepoint(savePoint.getName());
    }

    protected Publisher<Void> baseRelease(final SavePoint savePoint) {
        checkActive();
        if (!savePoints.remove(savePoint)) {
            throw new IllegalArgumentException("Save point not defined: " + savePoint);
        }
        return connection.releaseSavepoint(savePoint.getName());
    }

    @SuppressWarnings("UnusedReturnValue")
    public final IsolationLevel getIsolationLevel() {
        checkActive();
        return REVERSE_MAPPING.getOrDefault(connection.getTransactionIsolationLevel(), IsolationLevel.READ_COMMITTED);
    }

    protected Publisher<Void> baseSetIsolationLevel(final IsolationLevel isolationLevel) {
        checkActive();
        return connection.setTransactionIsolationLevel(MAPPING.get(require(isolationLevel)));
    }

    protected final void checkActive() {
        if (!active) {
            throw new InvalidStateException("Current transaction is not active!");
        }
    }
}
