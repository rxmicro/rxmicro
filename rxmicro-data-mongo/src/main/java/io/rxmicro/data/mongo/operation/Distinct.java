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

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Denotes a repository method that must execute a
 * <a href="https://docs.mongodb.com/manual/reference/method/db.collection.distinct/">{@code db.collection.distinct()}</a> operation.
 *
 * @author nedis
 * @since 0.1
 * @see io.rxmicro.data.mongo.MongoRepository
 * @see Aggregate
 * @see CountDocuments
 * @see Delete
 * @see EstimatedDocumentCount
 * @see Find
 * @see Insert
 * @see Update
 */
@Documented
@Retention(SOURCE)
@Target(METHOD)
public @interface Distinct {

    /**
     * Returns the field for which to return distinct values.
     *
     * @return the field for which to return distinct values.
     */
    String field();

    /**
     * Returns the query that specifies the documents from which to retrieve the distinct values.
     * <p>
     * Read more:
     * <a href="https://docs.mongodb.com/manual/reference/operator/">
     *     https://docs.mongodb.com/manual/reference/operator/
     * </a>
     *
     * @return the query that specifies the documents from which to retrieve the distinct values.
     */
    String query() default "";
}
