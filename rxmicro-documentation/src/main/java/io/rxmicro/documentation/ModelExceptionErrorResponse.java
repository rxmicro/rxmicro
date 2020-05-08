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

package io.rxmicro.documentation;

import io.rxmicro.http.error.HttpErrorException;

import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Denotes the exception class to be analyzed by the {@code RxMicro Annotation Processor} for generating the unsuccessful
 * HTTP response description of REST-based microservice.
 *
 * @author nedis
 * @since 0.1
 * @see Description
 * @see Example
 * @see IncludeDescription
 * @see IncludeMode
 * @see SimpleErrorResponse
 */
@Documented
@Retention(SOURCE)
@Target({METHOD, TYPE})
@Repeatable(ModelExceptionErrorResponse.List.class)
public @interface ModelExceptionErrorResponse {

    /**
     * Returns the HTTP error exception class
     *
     * @return the HTTP error exception class
     */
    Class<? extends HttpErrorException> value();

    /**
     * Defines several {@link ModelExceptionErrorResponse} annotations on the same element.
     *
     * @author nedis
     * @since 0.1
     */
    @Documented
    @Retention(SOURCE)
    @Target({METHOD, TYPE})
    @interface List {

        /**
         * Returns the several {@link ModelExceptionErrorResponse} annotations on the same element.
         *
         * @return the several {@link ModelExceptionErrorResponse} annotations on the same element.
         */
        ModelExceptionErrorResponse[] value();
    }
}
