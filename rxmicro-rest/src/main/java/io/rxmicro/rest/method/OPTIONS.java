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
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Annotation that can be applied to method to signify the method receives a OPTIONS request.
 * See https://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html#sec9.2.
 *
 * @author nedis
 * @link https://rxmicro.io
 * @link https://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html#sec9.2
 * @since 0.1
 */
@Documented
@Retention(SOURCE)
@Target(METHOD)
@Repeatable(OPTIONS.List.class)
public @interface OPTIONS {

    /**
     * @return The URI of the route
     */
    String value();

    /**
     * Defines several {@link OPTIONS} annotations on the same element.
     *
     * @author nedis
     * @link https://rxmicro.io
     * @since 0.1
     */
    @Documented
    @Retention(SOURCE)
    @Target(METHOD)
    @interface List {

        OPTIONS[] value();
    }
}
