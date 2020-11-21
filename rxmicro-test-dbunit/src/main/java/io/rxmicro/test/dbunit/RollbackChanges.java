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

package io.rxmicro.test.dbunit;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.sql.Connection;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Declares the transactional test.
 * The transaction test means that all changes made by test will rolled back after test execution.
 *
 * @author nedis
 * @since 0.7
 */
@Documented
@Retention(RUNTIME)
@Target(METHOD)
public @interface RollbackChanges {

    /**
     * Returns the transaction isolation level that should be used for transactional test.
     *
     * @return the transaction isolation level that should be used for transactional test.
     */
    IsolationLevel isolationLevel() default IsolationLevel.DEFAULT;

    /**
     * Represents a transaction isolation level constant.
     *
     * @author nedis
     * @since 0.7
     */
    enum IsolationLevel {

        /**
         * Use configured by default.
         */
        DEFAULT(-1),

        /**
         * The read committed isolation level.
         */
        READ_COMMITTED(Connection.TRANSACTION_READ_COMMITTED),

        /**
         * The read uncommitted isolation level.
         */
        READ_UNCOMMITTED(Connection.TRANSACTION_READ_UNCOMMITTED),

        /**
         * The repeatable read isolation level.
         */
        REPEATABLE_READ(Connection.TRANSACTION_REPEATABLE_READ),

        /**
         * The serializable isolation level.
         */
        SERIALIZABLE(Connection.TRANSACTION_SERIALIZABLE);

        private final int level;

        IsolationLevel(final int level) {
            this.level = level;
        }

        /**
         * Returns the transaction isolation level.
         *
         * @return the transaction isolation level.
         * @see Connection#TRANSACTION_READ_COMMITTED
         * @see Connection#TRANSACTION_READ_UNCOMMITTED
         * @see Connection#TRANSACTION_REPEATABLE_READ
         * @see Connection#TRANSACTION_SERIALIZABLE
         */
        public int getLevel() {
            return level;
        }
    }
}
