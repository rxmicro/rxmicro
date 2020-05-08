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
 * Denotes a static HTTP header that must be added to the response, created by the request handler from REST controller
 * or
 * denotes a static HTTP header that must be added to the request, formed by the request handler from REST client.
 *
 * @author nedis
 * @since 0.1
 * @see SetHeader
 * @see Header
 * @see HeaderMappingStrategy
 * @see RepeatHeader
 */
@Documented
@Retention(SOURCE)
@Target({TYPE, METHOD})
@Repeatable(AddHeader.List.class)
public @interface AddHeader {

    /**
     * Returns the static header name
     *
     * @return the static header name
     */
    String name();

    /**
     * Returns the static header value
     *
     * @return the static header value
     */
    String value();

    /**
     * Defines several {@link AddHeader} annotations on the same element.
     *
     * @author nedis
     * @since 0.1
     */
    @Documented
    @Retention(SOURCE)
    @Target({TYPE, METHOD})
    @interface List {

        /**
         * Returns the several {@link AddHeader} annotations on the same element.
         *
         * @return the several {@link AddHeader} annotations on the same element.
         */
        AddHeader[] value();
    }
}
