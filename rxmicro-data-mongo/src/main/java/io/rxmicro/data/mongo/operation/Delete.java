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
 * <a href="https://docs.mongodb.com/manual/reference/method/db.collection.deleteMany/">{@code db.collection.deleteMany()}</a> operation.
 *
 * @author nedis
 * @see io.rxmicro.data.mongo.MongoRepository
 * @see Aggregate
 * @see CountDocuments
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
public @interface Delete {

    /**
     * Returns the deletion criteria using query operators.
     *
     * <p>
     * Read more:
     * <a href="https://docs.mongodb.com/manual/reference/operator/">
     * https://docs.mongodb.com/manual/reference/operator/
     * </a>
     *
     * @return the deletion criteria using query operators.
     */
    String filter() default EMPTY_STRING;
}
