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
     *      paramNames = {"${key}-${value}"},
     *      paramValueExamples = {"${key}-${value}"},
     *      paramDescriptions = {"${key}-${value}"},
     *      headerNames = {"${key}-${value}"},
     *      headerValueExamples = {"${key}-${value}"},
     *      headerDescriptions = {"${key}-${value}"}
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
     * Returns the param names.
     *
     * <p>
     * For example: If microservice must return error HTTP response with the following body content:
     * <pre>
     * {@code
     * {
     *     "message": "Internal error",
     *     "readMore": "https://rxmicro.io/doc/read-more.html"
     * }
     * }
     * </pre>
     * than it is necessary to configure {@link SimpleErrorResponse} annotation:
     * <pre>
     * {@code
     * @SimpleErrorResponse}(
     *      paramNames = {"message", "readMore"},
     *      paramValueExamples = {"Internal error", "https://rxmicro.io/doc/read-more.html"}
     * )
     * }
     * </pre>
     *
     * <p>
     * Note: If {@code paramNames} exist that {@code paramValueExamples} must be provided as well!
     *
     * <p>
     * Param type is resolved automatically using provided param value examples.
     * For example: If {@code paramValueExamples = {"true"}}, than the RxMicro framework will resolve param as param with boolean type!
     *
     * @return the param names.
     * @since 0.9
     */
    String[] paramNames() default {};

    /**
     * Returns the required characteristics for the provided params.
     *
     * <p>
     * If {@code true}, than parameter is required, otherwise this parameter is optional!
     *
     * <p>
     * By default if {@code paramsRequired() = {}}, than all provided params are {@code required}, i.e.
     * {@code undefined} state is equal to the {@code required} state!
     *
     * @return the required characteristics for the provided params.
     * @since 0.9
     */
    boolean[] paramsRequired() default {};

    /**
     * Returns the descriptions for the provided parameters.
     *
     * @return the descriptions for the provided parameters.
     * @since 0.9
     */
    String[] paramDescriptions() default {};

    /**
     * Returns the param value examples.
     *
     * <p>
     * For example: If microservice must return error HTTP response with the following body content:
     * <pre>
     * {@code
     * {
     *     "message": "Internal error",
     *     "readMore": "https://rxmicro.io/doc/read-more.html"
     * }
     * }
     * </pre>
     * than it is necessary to configure {@link SimpleErrorResponse} annotation:
     * <pre>
     * {@code
     * @SimpleErrorResponse}(
     *      paramNames = {"message", "readMore"},
     *      paramValueExamples = {"Internal error", "https://rxmicro.io/doc/read-more.html"}
     * )
     * }
     * </pre>
     *
     * <p>
     * Note: If {@code paramValueExamples} exist that {@code paramNames} must be provided as well!
     *
     * @return the param value examples.
     * @since 0.9
     */
    String[] paramValueExamples() default {};

    /**
     * Returns the header names.
     *
     * <p>
     * For example: If microservice must return error HTTP response with the following HTTP headers:
     * <pre>
     * {@code
     * Error-Code: 67
     * Error-Message: Not acceptable
     * }
     * </pre>
     * than it is necessary to configure {@link SimpleErrorResponse} annotation:
     * <pre>
     * {@code
     * @SimpleErrorResponse}(
     *      headerNames = {"Error-Code", "Error-Message"},
     *      headerValueExamples = {"67", "Not acceptable"}
     * )
     * }
     * </pre>
     *
     * <p>
     * Note: If {@code headerNames} exist that {@code headerValueExamples} must be provided as well!
     *
     * @return the header names.
     * @since 0.9
     */
    String[] headerNames() default {};

    /**
     * Returns the header value examples.
     *
     * <p>
     * For example: If microservice must return error HTTP response with the following HTTP headers:
     * <pre>
     * {@code
     * Error-Code: 67
     * Error-Message: Not acceptable
     * }
     * </pre>
     * than it is necessary to configure {@link SimpleErrorResponse} annotation:
     * <pre>
     * {@code
     * @SimpleErrorResponse}(
     *      headerNames = {"Error-Code", "Error-Message"},
     *      headerValueExamples = {"67", "Not acceptable"}
     * )
     * }
     * </pre>
     *
     * <p>
     * Note: If {@code headerValueExamples} exist that {@code headerNames} must be provided as well!
     *
     * @return the header value examples.
     * @since 0.9
     */
    String[] headerValueExamples() default {};

    /**
     * Returns the required characteristics for the provided headers.
     *
     * <p>
     * If {@code true}, than header is required, otherwise this header is optional!
     *
     * <p>
     * By default if {@code headersRequired() = {}}, than all provided headers are {@code required}, i.e.
     * {@code undefined} state is equal to the {@code required} state!
     *
     * @return the required characteristics for the provided headers.
     * @since 0.9
     */
    boolean[] headersRequired() default {};

    /**
     * Returns the descriptions for the provided headers.
     *
     * @return the descriptions for the provided headers.
     * @since 0.9
     */
    String[] headerDescriptions() default {};

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
