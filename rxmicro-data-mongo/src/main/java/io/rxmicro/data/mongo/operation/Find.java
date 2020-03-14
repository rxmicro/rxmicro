/*
 * Copyright 2019 http://rxmicro.io
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
 * @author nedis
 * @link http://rxmicro.io
 * @link https://docs.mongodb.com/manual/reference/method/db.collection.find/
 * @since 0.1
 */
@Documented
@Retention(SOURCE)
@Target(METHOD)
public @interface Find {

    /**
     * @link https://docs.mongodb.com/manual/reference/operator/
     */
    String query() default "";

    /**
     * @link https://docs.mongodb.com/manual/reference/method/db.collection.find/#projection
     * @link https://docs.mongodb.com/manual/reference/method/db.collection.find/#projections
     */
    String projection() default "";

    String hint() default "";

    /**
     * @link https://docs.mongodb.com/manual/reference/method/db.collection.find/#order-documents-in-the-result-set
     */
    String sort() default "";

    /**
     * @link https://docs.mongodb.com/manual/reference/method/db.collection.find/#limit-the-number-of-documents-to-return
     */
    int limit() default -1;

    /**
     * @link https://docs.mongodb.com/manual/reference/method/db.collection.find/#set-the-starting-point-of-the-result-set
     */
    int skip() default -1;
}
