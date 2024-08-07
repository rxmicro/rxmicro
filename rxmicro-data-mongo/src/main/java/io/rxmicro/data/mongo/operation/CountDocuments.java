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
 * <a href="https://docs.mongodb.com/manual/reference/method/db.collection.countDocuments/">{@code db.collection.countDocuments()}</a>
 * operation.
 *
 * @author nedis
 * @see io.rxmicro.data.mongo.MongoRepository
 * @see Aggregate
 * @see Delete
 * @see Distinct
 * @see EstimatedDocumentCount
 * @see Find
 * @see Insert
 * @see Update
 * @since 0.1
 */
@Documented
@Retention(CLASS)
@Target(METHOD)
public @interface CountDocuments {

    /**
     * Returns the query selection criteria.
     *
     * <p>
     * Read more:
     * <a href="https://docs.mongodb.com/manual/reference/operator/">
     * https://docs.mongodb.com/manual/reference/operator/
     * </a>
     *
     * @return the query selection criteria
     */
    String query() default EMPTY_STRING;

    /**
     * Returns the maximum number of documents to count.
     *
     * @return the maximum number of documents to count.
     */
    int limit() default -1;

    /**
     * Returns the number of documents to skip before counting.
     *
     * @return the number of documents to skip before counting.
     */
    int skip() default -1;

    /**
     * Returns the index name or the index specification to use for the query.
     *
     * @return the index name or the index specification to use for the query.
     */
    String hint() default EMPTY_STRING;
}
