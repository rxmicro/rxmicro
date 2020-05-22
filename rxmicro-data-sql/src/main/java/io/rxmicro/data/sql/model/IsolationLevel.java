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

/**
 * Represents a transaction isolation level constant.
 *
 * @author nedis
 * @see TransactionType
 * @see io.rxmicro.data.sql.model.reactor.Transaction
 * @see io.rxmicro.data.sql.model.rxjava3.Transaction
 * @see io.rxmicro.data.sql.model.completablefuture.Transaction
 * @see SavePoint
 * @since 0.1
 */
public enum IsolationLevel {

    /**
     * The read committed isolation level.
     */
    READ_COMMITTED,

    /**
     * The read uncommitted isolation level.
     */
    READ_UNCOMMITTED,

    /**
     * The repeatable read isolation level.
     */
    REPEATABLE_READ,

    /**
     * The serializable isolation level.
     */
    SERIALIZABLE
}
