/*
 * Copyright 2019 https://rxmicro.io
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

package io.rxmicro.data.mongo.operation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Denotes a repository method that must execute a
 * <a href="https://docs.mongodb.com/manual/reference/method/db.collection.find/">{@code db.collection.find()}</a> operation.
 *
 * @author nedis
 * @link https://rxmicro.io
 * @link https://docs.mongodb.com/manual/reference/method/db.collection.find/
 * @since 0.1
 */
@Documented
@Retention(SOURCE)
@Target(METHOD)
public @interface Find {

    /**
     * Returns the selection filter using query operators.
     * <p>
     * Read more:
     * <a href="https://docs.mongodb.com/manual/reference/operator/">
     *     https://docs.mongodb.com/manual/reference/operator/
     * </a>
     *
     * @return the selection filter using query operators.
     * @link https://docs.mongodb.com/manual/reference/operator/
     */
    String query() default "";

    /**
     * Returns the query projection
     * <p>
     * Read more:
     * <p>
     * <a href="https://docs.mongodb.com/manual/reference/method/db.collection.find/#projection">
     *     https://docs.mongodb.com/manual/reference/method/db.collection.find/#projection
     * </a>
     * <p>
     * <a href="https://docs.mongodb.com/manual/reference/method/db.collection.find/#projections">
     *     https://docs.mongodb.com/manual/reference/method/db.collection.find/#projections
     * </a>
     *
     * @return the query projection
     * @link https://docs.mongodb.com/manual/reference/method/db.collection.find/#projection
     * @link https://docs.mongodb.com/manual/reference/method/db.collection.find/#projections
     */
    String projection() default "";

    /**
     * Returns the index to use for the find operation
     * <p>
     * Read more:
     * <a href="https://docs.mongodb.com/manual/reference/method/cursor.hint/#cursor.hint">
     *     https://docs.mongodb.com/manual/reference/method/cursor.hint/#cursor.hint
     * </a>
     *
     * @return the index to use for the find operation
     * @link https://docs.mongodb.com/manual/reference/method/cursor.hint/#cursor.hint
     */
    String hint() default "";

    /**
     * Returns the sort expression
     * <p>
     * Read more:
     * <a href="https://docs.mongodb.com/manual/reference/method/db.collection.find/#order-documents-in-the-result-set">
     *     https://docs.mongodb.com/manual/reference/method/db.collection.find/#order-documents-in-the-result-set
     * </a>
     *
     * @return the sort expression
     * @link https://docs.mongodb.com/manual/reference/method/db.collection.find/#order-documents-in-the-result-set
     */
    String sort() default "";

    /**
     * Returns the limit of the result set
     * <p>
     * Read more:
     * <a href="https://docs.mongodb.com/manual/reference/method/db.collection.find/#limit-the-number-of-documents-to-return">
     *     https://docs.mongodb.com/manual/reference/method/db.collection.find/#limit-the-number-of-documents-to-return
     * </a>
     *
     * @return the limit of the result set
     * @link https://docs.mongodb.com/manual/reference/method/db.collection.find/#limit-the-number-of-documents-to-return
     */
    int limit() default -1;

    /**
     * Returns the start point of the result set
     * <p>
     * Read more:
     * <a href="https://docs.mongodb.com/manual/reference/method/db.collection.find/#set-the-starting-point-of-the-result-set">
     *     https://docs.mongodb.com/manual/reference/method/db.collection.find/#set-the-starting-point-of-the-result-set
     * </a>
     *
     * @return the start point of the result set
     * @link https://docs.mongodb.com/manual/reference/method/db.collection.find/#set-the-starting-point-of-the-result-set
     */
    int skip() default -1;
}
