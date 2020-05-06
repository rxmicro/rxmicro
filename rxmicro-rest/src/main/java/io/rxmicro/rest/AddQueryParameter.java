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

package io.rxmicro.rest;

import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Denotes a static query parameter that must be added to the request, created by REST client implementation.
 *
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
@Documented
@Retention(SOURCE)
@Target({TYPE, METHOD})
@Repeatable(AddQueryParameter.List.class)
public @interface AddQueryParameter {

    /**
     * Returns the static query parameter name
     *
     * @return the static query parameter name
     */
    String name();

    /**
     * Returns the static query parameter value
     *
     * @return the static query parameter value
     */
    String value();

    /**
     * Defines several {@link AddQueryParameter} annotations on the same element.
     *
     * @author nedis
     * @link https://rxmicro.io
     * @since 0.1
     */
    @Documented
    @Retention(SOURCE)
    @Target({TYPE, METHOD})
    @interface List {

        /**
         * Returns the several {@link AddQueryParameter} annotations on the same element.
         *
         * @return the several {@link AddQueryParameter} annotations on the same element.
         */
        AddQueryParameter[] value();
    }
}
