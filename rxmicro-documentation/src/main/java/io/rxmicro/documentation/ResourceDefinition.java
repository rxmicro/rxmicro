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
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * A composite annotation that specifies the settings for generating the ResourceDefinition section.
 *
 * @author nedis
 * @see Author
 * @see BaseEndpoint
 * @see DocumentationConstants
 * @see Description
 * @see DocumentationDefinition
 * @see DocumentationVersion
 * @see IncludeDescription
 * @see IncludeMode
 * @see IntroductionDefinition
 * @see License
 * @see ResourceGroupDefinition
 * @see Title
 * @since 0.1
 */
@Documented
@Retention(SOURCE)
@Target({ANNOTATION_TYPE, METHOD, TYPE})
public @interface ResourceDefinition {

    /**
     * Returns {@code true} if the RxMicro framework must generates examples of HTTP requests and responses.
     *
     * @return {@code true} if the RxMicro framework must generates examples of HTTP requests and responses
     */
    boolean withExamples() default true;

    /**
     * Returns {@code true} if the RxMicro framework must generates a description table for path variables.
     *
     * @return {@code true} if the RxMicro framework must generates a description table for path variables
     */
    boolean withPathVariablesDescriptionTable() default true;

    /**
     * Returns {@code true} if the RxMicro framework must generates a description table for HTTP headers.
     *
     * @return {@code true} if the RxMicro framework must generates a description table for HTTP headers
     */
    boolean withHeadersDescriptionTable() default true;

    /**
     * Returns {@code true} if the RxMicro framework must generates a description table for HTTP query parameters.
     *
     * @return {@code true} if the RxMicro framework must generates a description table for HTTP query parameters
     */
    boolean withQueryParametersDescriptionTable() default true;

    /**
     * Returns {@code true} if the RxMicro framework must generates a description table for HTTP body parameters.
     *
     * @return {@code true} if the RxMicro framework must generates a description table for HTTP body parameters
     */
    boolean withBodyParametersDescriptionTable() default true;

    /**
     * Returns {@code true} if the RxMicro framework must generates a JSON schema for HTTP requests and responses.
     *
     * @return {@code true} if the RxMicro framework must generates a JSON schema for HTTP requests and responses
     */
    boolean withJsonSchema() default true;

    /**
     * Returns {@code true} if the RxMicro framework must generates a standard description for HTTP headers, parameters and path variables.
     *
     * @return {@code true} if the RxMicro framework must generates a standard description for HTTP headers, parameters and path variables
     */
    boolean withStandardDescriptions() default true;

    /**
     * Returns {@code true} if the RxMicro framework must generates read more links to external resources.
     *
     * @return {@code true} if the RxMicro framework must generates read more links to external resources
     */
    boolean withReadMore() default true;

    /**
     * Returns {@code true} if the RxMicro framework must generates a documentation for validation response.
     *
     * @return {@code true} if the RxMicro framework must generates a documentation for validation response
     */
    boolean withValidationResponse() default true;

    /**
     * Returns {@code true} if the RxMicro framework must generates a documentation for internal error response.
     *
     * @return {@code true} if the RxMicro framework must generates a documentation for internal error response
     */
    boolean withInternalErrorResponse() default true;

    /**
     * Returns {@code true} if the RxMicro framework must generates a documentation standard {@code Request-Id} HTTP header.
     *
     * @return {@code true} if the RxMicro framework must generates a documentation standard {@code Request-Id} HTTP header
     */
    boolean withRequestIdResponseHeader() default true;
}
