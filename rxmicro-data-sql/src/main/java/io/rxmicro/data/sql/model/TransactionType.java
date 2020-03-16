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
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public enum TransactionType {

    reactor(io.rxmicro.data.sql.model.reactor.Transaction.class),

    rxjava3(io.rxmicro.data.sql.model.rxjava3.Transaction.class),

    completable_future(io.rxmicro.data.sql.model.completablefuture.Transaction.class);

    public static final Set<String> SUPPORTED_TRANSACTION_TYPES = Arrays.stream(TransactionType.values())
            .map(e -> e.transactionClass.getName())
            .collect(Collectors.toUnmodifiableSet());

    private final Class<?> transactionClass;

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
