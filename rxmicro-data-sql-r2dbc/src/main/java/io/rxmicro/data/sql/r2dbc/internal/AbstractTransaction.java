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

import io.r2dbc.spi.Connection;
import io.rxmicro.common.InvalidStateException;
import io.rxmicro.data.sql.model.IsolationLevel;
import io.rxmicro.data.sql.model.SavePoint;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import static io.rxmicro.common.util.Requires.require;
import static io.rxmicro.data.sql.model.IsolationLevel.READ_COMMITTED;
import static io.rxmicro.data.sql.model.IsolationLevel.READ_UNCOMMITTED;
import static io.rxmicro.data.sql.model.IsolationLevel.REPEATABLE_READ;
import static io.rxmicro.data.sql.model.IsolationLevel.SERIALIZABLE;

/**
 * @author nedis
 * @since 0.1
 */
public abstract class AbstractTransaction {

    private static final Map<IsolationLevel, io.r2dbc.spi.IsolationLevel> MAPPING = Map.of(
            READ_COMMITTED, io.r2dbc.spi.IsolationLevel.READ_COMMITTED,
            READ_UNCOMMITTED, io.r2dbc.spi.IsolationLevel.READ_UNCOMMITTED,
            REPEATABLE_READ, io.r2dbc.spi.IsolationLevel.REPEATABLE_READ,
            SERIALIZABLE, io.r2dbc.spi.IsolationLevel.SERIALIZABLE
    );

    private static final Map<io.r2dbc.spi.IsolationLevel, IsolationLevel> REVERSE_MAPPING = Map.of(
            io.r2dbc.spi.IsolationLevel.READ_COMMITTED, READ_COMMITTED,
            io.r2dbc.spi.IsolationLevel.READ_UNCOMMITTED, READ_UNCOMMITTED,
            io.r2dbc.spi.IsolationLevel.REPEATABLE_READ, REPEATABLE_READ,
            io.r2dbc.spi.IsolationLevel.SERIALIZABLE, SERIALIZABLE
    );

    private final Connection connection;

    private List<SavePoint> savePoints = List.of();

    private boolean active;

    protected AbstractTransaction(final Connection connection) {
        this.connection = require(connection);
        this.active = true;
    }

    Connection getConnection() {
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

    protected Publisher<Void> baseRollback(final SavePoint savePoint) {
        checkActive();
        if (!savePoints.contains(savePoint)) {
            throw new IllegalArgumentException("Save point not defined: " + savePoint);
        } else {
            final ListIterator<SavePoint> listIterator = savePoints.listIterator(savePoints.size());
            while (listIterator.hasPrevious()) {
                final SavePoint previous = listIterator.previous();
                if (!savePoint.equals(previous)) {
                    listIterator.remove();
                } else {
                    break;
                }
            }
        }
        return connection.rollbackTransaction();
    }

    @SuppressWarnings("UnusedReturnValue")
    public final IsolationLevel getIsolationLevel() {
        checkActive();
        return REVERSE_MAPPING.getOrDefault(connection.getTransactionIsolationLevel(), READ_COMMITTED);
    }

    protected Publisher<Void> baseSetIsolationLevel(final IsolationLevel isolationLevel) {
        checkActive();
        return connection.setTransactionIsolationLevel(MAPPING.get(require(isolationLevel)));
    }

    protected final void checkActive() {
        if (!active) {
            throw new InvalidStateException("Current transaction is not active");
        }
    }
}
