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

import static io.rxmicro.common.CommonConstants.EMPTY_STRING;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Denotes the exception class to be analyzed by the {@code RxMicro Annotation Processor} for generating the unsuccessful
 * HTTP response description of REST-based microservice.
 *
 * @author nedis
 * @see Description
 * @see Example
 * @see IncludeDescription
 * @see IncludeMode
 * @see SimpleErrorResponse
 * @since 0.1
 */
@Documented
@Retention(SOURCE)
@Target({METHOD, TYPE})
@Repeatable(ModelExceptionErrorResponse.List.class)
public @interface ModelExceptionErrorResponse {

    /**
     * Returns the HTTP error exception class.
     *
     * @return the HTTP error exception class
     */
    Class<? extends HttpErrorException> value();

    /**
     * Returns the custom description for the current http error exception.
     *
     * <p>
     * If the custom description is not set, the {@code RxMicro Annotation Processor} will analyze the current http error exception class,
     * provided by the {@link #value()} annotation parameter to get a description.
     *
     * @return the custom description for the current http error exception.
     */
    String description() default EMPTY_STRING;

    /**
     * Returns the custom included description if it is necessary add a complex formatted description.
     *
     * <p>
     * If the custom description is not set, the {@code RxMicro Annotation Processor} will analyze the current http error exception class,
     * provided by the {@link #value()} annotation parameter to get a description.
     *
     * @return the custom included description
     */
    IncludeDescription includeDescription() default @IncludeDescription(resource = EMPTY_STRING);

    /**
     * Returns the variable definitions for the provided error description, using key-value format.
     *
     * <p>
     * For example:
     *
     * <pre>
     * {@code
     * @ModelExceptionErrorResponse(
     *      value = CustomExceptionClass.class,
     *      description = "If ${key} = ${value} than this field contains true, otherwise false",
     *      variables = {
     *          "${key}", EXTERNAL_CONSTANT_KEY,
     *          "${value}", EXTERNAL_CONSTANT_VALUE
     *      }
     * )
     * }
     * </pre>
     *
     * @return the variable definitions for the provided error description, using key-value format.
     */
    String[] variables() default {};

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
