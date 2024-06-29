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

package io.rxmicro.rest.method;

import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * Annotation that can be applied to method to signify the method receives a GET request.
 *
 * <p>
 * See <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html#sec9.5">
 * https://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html#sec9.5
 * </a>
 *
 * @author nedis
 * @see DELETE
 * @see GET
 * @see HEAD
 * @see OPTIONS
 * @see PATCH
 * @see PUT
 * @see io.rxmicro.rest.model.HttpMethod
 * @see HttpMethods
 * @since 0.1
 */
@Documented
@Retention(CLASS)
@Target(METHOD)
@Repeatable(POST.List.class)
public @interface POST {

    /**
     * Returns the URI of the route.
     *
     * @return the URI of the route
     */
    String value();

    /**
     * Informs how the request parameters must be transferred via HTTP protocol: in the start line or in the request body.
     *
     * @return {@code true} if HTTP body is used for transferring of http parameters,
     * {@code false} if parameters are transferred via HTTP query string
     */
    boolean httpBody() default true;

    /**
     * Defines several {@link POST} annotations on the same element.
     *
     * @author nedis
     * @since 0.1
     */
    @Documented
    @Retention(CLASS)
    @Target(METHOD)
    @interface List {

        /**
         * Returns the several {@link POST} annotations on the same element.
         *
         * @return the several {@link POST} annotations on the same element.
         */
        POST[] value();
    }
}
