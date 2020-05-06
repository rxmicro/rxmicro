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

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Base url path for all http endpoints
 *
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
@Documented
@Retention(SOURCE)
@Target(TYPE)
@Repeatable(BaseUrlPath.List.class)
public @interface BaseUrlPath {

    /**
     * Returns the base url
     *
     * @return the base url
     */
    String value();

    /**
     * Returns the position of the base url according to {@link Version} if {@link Version.Strategy#URL_PATH} is used
     *
     * @return the position of the base url according to {@link Version} if {@link Version.Strategy#URL_PATH} is used
     */
    Position position() default Position.AFTER_VERSION;

    /**
     * @author nedis
     * @link https://rxmicro.io
     * @since 0.1
     */
    enum Position {

        /**
         * Base url must be generated before {@link Version} if {@link Version.Strategy#URL_PATH} is used
         */
        BEFORE_VERSION,

        /**
         * Base url must be generated after {@link Version} if {@link Version.Strategy#URL_PATH} is used
         */
        AFTER_VERSION
    }

    /**
     * Defines several {@link BaseUrlPath} annotations on the same element.
     *
     * @author nedis
     * @link https://rxmicro.io
     * @since 0.1
     */
    @Documented
    @Retention(SOURCE)
    @Target(TYPE)
    @interface List {

        /**
         * Returns the several {@link BaseUrlPath} annotations on the same element.
         *
         * @return the several {@link BaseUrlPath} annotations on the same element.
         */
        BaseUrlPath[] value();
    }
}
