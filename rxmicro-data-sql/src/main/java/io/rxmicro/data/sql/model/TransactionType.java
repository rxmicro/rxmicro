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

package io.rxmicro.data.sql.model;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Supported reactive libraries for database transactions
 *
 * @author nedis
 * @since 0.1
 * @see io.rxmicro.data.sql.model.reactor.Transaction
 * @see io.rxmicro.data.sql.model.rxjava3.Transaction
 * @see io.rxmicro.data.sql.model.completablefuture.Transaction
 * @see SavePoint
 * @see IsolationLevel
 */
public enum TransactionType {

    /**
     * The transaction model for <a href="https://projectreactor.io/docs/core/release/reference/">Project Reactor</a> reactive library.
     */
    REACTOR(io.rxmicro.data.sql.model.reactor.Transaction.class),

    /**
     * The transaction model for <a href="https://github.com/ReactiveX/RxJava/blob/3.x/README.md">RxJava 3</a> reactive library.
     */
    RX_JAVA_3(io.rxmicro.data.sql.model.rxjava3.Transaction.class),

    /**
     * The transaction model for
     * <a href="https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/concurrent/package-summary.html">
     *     java.util.concurrent
     * </a>
     * reactive library.
     */
    COMPLETABLE_FUTURE(io.rxmicro.data.sql.model.completablefuture.Transaction.class);

    /**
     * All supported reactive libraries
     */
    public static final Set<String> SUPPORTED_TRANSACTION_TYPES = Arrays.stream(TransactionType.values())
            .map(e -> e.transactionClass.getName())
            .collect(Collectors.toUnmodifiableSet());

    private final Class<?> transactionClass;

    /**
     * Returns the enum constant of the {@link TransactionType} type with the specified full class name.
     *
     * @param fullClassName the specified full class name
     * @return the enum constant of the {@link TransactionType} type
     */
    public static TransactionType byClassName(final String fullClassName) {
        for (final TransactionType transactionType : TransactionType.values()) {
            if (transactionType.transactionClass.getName().equals(fullClassName)) {
                return transactionType;
            }
        }
        throw new IllegalArgumentException("Unsupported transaction type: " + fullClassName);
    }

    TransactionType(final Class<?> transactionClass) {
        this.transactionClass = transactionClass;
    }
}
