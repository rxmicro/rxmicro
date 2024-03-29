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
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * Denotes a static query parameter that must be set to the request, created by REST client implementation.
 *
 * @author nedis
 * @see AddQueryParameter
 * @see RepeatQueryParameter
 * @see Parameter
 * @see ParameterMappingStrategy
 * @since 0.1
 */
@Documented
@Retention(CLASS)
@Target({TYPE, METHOD})
@Repeatable(SetQueryParameter.List.class)
public @interface SetQueryParameter {

    /**
     * Returns the static query parameter name.
     *
     * @return the static query parameter name
     */
    String name();

    /**
     * Returns the static query parameter value.
     *
     * @return the static query parameter value
     */
    String value();

    /**
     * Defines several {@link SetQueryParameter} annotations on the same element.
     *
     * @author nedis
     * @since 0.1
     */
    @Documented
    @Retention(CLASS)
    @Target({TYPE, METHOD})
    @interface List {

        /**
         * Returns the several {@link SetQueryParameter} annotations on the same element.
         *
         * @return the several {@link SetQueryParameter} annotations on the same element.
         */
        SetQueryParameter[] value();
    }
}
