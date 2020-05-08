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
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static io.rxmicro.http.HttpStandardHeaderNames.API_VERSION;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Denotes a version of the REST controller or REST client.
 *
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
@Documented
@Retention(SOURCE)
@Target(TYPE)
public @interface Version {

    /**
     * Returns the REST API version value
     *
     * @return the REST API version value
     */
    String value();

    /**
     * Returns the version {@link Strategy}
     *
     * @return the version {@link Strategy}
     */
    Strategy strategy() default Strategy.URL_PATH;

    /**
     * REST version strategy
     *
     * @author nedis
     * @link https://rxmicro.io
     * @since 0.1
     */
    enum Strategy {

        /**
         * Overrides an url.
         * <p>
         * For example:
         * <ul>
         *     <li>{@code GET https://rxmicro.io/api/status} - REST endpoint without version</li>
         *     <li>{@code GET https://rxmicro.io/api/v1.0/status} - REST endpoint with version {@code v1.0}</li>
         * </ul>
         */
        URL_PATH(null),

        /**
         * Adds HTTP header, which defines REST API version.
         * <p>
         * <ul>
         *     <li>
         *         {@code GET https://rxmicro.io/api/status} - REST endpoint without version
         *     </li>
         *     <li>
         *         {@code GET https://rxmicro.io/api/v1.0/status} - REST endpoint with version {@code v1.0} <br>
         *         {@code Api-Version: v1.0}
         *     </li>
         * </ul>
         *
         * @see io.rxmicro.http.HttpHeaders
         */
        HEADER(API_VERSION);

        private final String paramName;

        Strategy(final String paramName) {
            this.paramName = paramName;
        }

        public String getParamName() {
            return paramName;
        }
    }
}
