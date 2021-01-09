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

import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Contains metadata about the unsuccessful HTTP response of REST-based microservice.
 *
 * @author nedis
 * @see ModelExceptionErrorResponse
 * @since 0.1
 */
@Documented
@Retention(SOURCE)
@Target({METHOD, TYPE})
@Repeatable(SimpleErrorResponse.List.class)
public @interface SimpleErrorResponse {

    /**
     * Returns the status code.
     *
     * @return the status code
     */
    int status();

    /**
     * Returns the error description.
     *
     * @return the error description
     */
    String description() default "";

    /**
     * Returns the variable definitions for the provided error description and(or) exampleErrorMessage, using key-value format.
     *
     * <p>
     * For example:
     *
     * <pre>
     * {@code
     * @SimpleErrorResponse(
     *      status = 404,
     *      description = "If ${key} = ${value} than this field contains true, otherwise false",
     *      variables = {
     *          "${key}", EXTERNAL_CONSTANT_KEY,
     *          "${value}", EXTERNAL_CONSTANT_VALUE
     *      },
     *      exampleErrorMessage = "${key}-${value}"
     * )
     * }
     * </pre>
     *
     * @return the variable definitions for the provided error description and(or) exampleErrorMessage, using key-value format.
     */
    String[] variables() default {};

    /**
     * Returns the included description if it is necessary add a complex formatted description.
     *
     * @return the included description
     */
    IncludeDescription includeDescription() default @IncludeDescription(resource = "");

    /**
     * Returns the example of error message.
     *
     * @return the example of error message
     */
    String exampleErrorMessage() default "";

    /**
     * Defines several {@link SimpleErrorResponse} annotations on the same element.
     *
     * @author nedis
     * @since 0.1
     */
    @Documented
    @Retention(SOURCE)
    @Target({METHOD, TYPE})
    @interface List {

        /**
         * Returns the several {@link SimpleErrorResponse} annotations on the same element.
         *
         * @return the several {@link SimpleErrorResponse} annotations on the same element.
         */
        SimpleErrorResponse[] value();
    }
}
