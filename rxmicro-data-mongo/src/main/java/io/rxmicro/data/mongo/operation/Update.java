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

package io.rxmicro.data.mongo.operation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static io.rxmicro.common.CommonConstants.EMPTY_STRING;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * Denotes a repository method that must execute a
 * <a href="https://docs.mongodb.com/manual/reference/method/db.collection.updateMany/">{@code db.collection.updateMany()}</a> operation.
 *
 * @author nedis
 * @see io.rxmicro.data.mongo.MongoRepository
 * @see Aggregate
 * @see CountDocuments
 * @see Delete
 * @see Distinct
 * @see EstimatedDocumentCount
 * @see Find
 * @see Insert
 * @since 0.1
 */
@Documented
@Retention(CLASS)
@Target(METHOD)
public @interface Update {

    /**
     * Returns the modifications to apply.
     *
     * <p>
     * Read more:
     * <a href="https://docs.mongodb.com/manual/reference/operator/update/">
     * https://docs.mongodb.com/manual/reference/operator/update/
     * </a>
     *
     * @return the modifications to apply.
     */
    String update() default EMPTY_STRING;

    /**
     * Returns the selection criteria for the update.
     *
     * <p>
     * Read more:
     * <a href="https://docs.mongodb.com/manual/reference/operator/">
     * https://docs.mongodb.com/manual/reference/operator/
     * </a>
     *
     * @return the selection criteria for the update.
     */
    String filter() default EMPTY_STRING;

    /**
     * Returns {@code true} if Mongo server must create a new document if no documents match the filter.
     *
     * @return {@code true} if Mongo server must create a new document if no documents match the filter.
     */
    boolean upsert() default false;
}
