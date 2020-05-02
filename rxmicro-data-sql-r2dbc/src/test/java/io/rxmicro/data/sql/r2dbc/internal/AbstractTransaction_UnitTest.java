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
import io.rxmicro.data.sql.model.IsolationLevel;
import io.rxmicro.data.sql.model.SavePoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivestreams.Publisher;

import static io.rxmicro.data.sql.model.IsolationLevel.READ_COMMITTED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
final class AbstractTransaction_UnitTest {

    private static final SavePoint SAVE_POINT_A = new SavePoint("A");

    private static final SavePoint SAVE_POINT_B = new SavePoint("B");

    private static final SavePoint SAVE_POINT_C = new SavePoint("C");

    private static final IsolationLevel ISOLATION_LEVEL = READ_COMMITTED;

    @Mock
    private Connection connection;

    @Mock
    private Publisher<Void> mockPublisher;

    private AbstractTransaction transaction;

    @BeforeEach
    void beforeEach() {
        transaction = new AbstractTransaction(connection) {

        };
    }

    @Test
    void Should_deactivate_transaction_after_commit() {
        when(connection.commitTransaction()).thenReturn(mockPublisher);
        when(connection.close()).thenReturn(mockPublisher);

        transaction.baseCommit();

        assertCurrentTransactionNotActive();
    }

    @Test
    void Should_deactivate_transaction_after_rollback() {
        when(connection.rollbackTransaction()).thenReturn(mockPublisher);
        when(connection.close()).thenReturn(mockPublisher);

        transaction.baseRollback();

        assertCurrentTransactionNotActive();
    }

    @Test
    void Should_deactivate_transaction_after_rollback_to_all_save_points() {
        when(connection.rollbackTransaction()).thenReturn(mockPublisher);
        when(connection.close()).thenReturn(mockPublisher);

        transaction.baseCreate(SAVE_POINT_A);
        transaction.baseCreate(SAVE_POINT_B);
        transaction.baseCreate(SAVE_POINT_C);
        transaction.baseRollback(SAVE_POINT_C);
        transaction.baseRollback(SAVE_POINT_B);
        transaction.baseRollback(SAVE_POINT_A);
        transaction.baseRollback();

        assertCurrentTransactionNotActive();
    }

    @Test
    void Should_deactivate_transaction_after_rollback_to_first_save_points() {
        when(connection.rollbackTransaction()).thenReturn(mockPublisher);
        when(connection.close()).thenReturn(mockPublisher);

        transaction.baseCreate(SAVE_POINT_A);
        transaction.baseCreate(SAVE_POINT_B);
        transaction.baseCreate(SAVE_POINT_C);
        transaction.baseRollback(SAVE_POINT_A);
        transaction.baseRollback();

        assertCurrentTransactionNotActive();
    }

    private void assertCurrentTransactionNotActive() {
        assertCurrentTransactionNotActive(() -> transaction.baseCommit());
        assertCurrentTransactionNotActive(() -> transaction.baseRollback());
        assertCurrentTransactionNotActive(() -> transaction.baseCreate(SAVE_POINT_A));
        assertCurrentTransactionNotActive(() -> transaction.baseRelease(SAVE_POINT_A));
        assertCurrentTransactionNotActive(() -> transaction.baseRollback(SAVE_POINT_A));
        assertCurrentTransactionNotActive(() -> transaction.getIsolationLevel());
        assertCurrentTransactionNotActive(() -> transaction.baseSetIsolationLevel(ISOLATION_LEVEL));
    }

    private void assertCurrentTransactionNotActive(final Executable executable) {
        final IllegalStateException exception = assertThrows(IllegalStateException.class, executable);
        assertEquals("Current transaction is not active", exception.getMessage());
    }
}