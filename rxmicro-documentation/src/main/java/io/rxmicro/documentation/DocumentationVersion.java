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
import static java.lang.annotation.ElementType.MODULE;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * Denotes the version of REST-based microservice in the generated REST-based microservice documentation.
 *
 * <p>
 * (<i>Allows overriding the version of REST-based microservice specified in the version directive to pom.xml</i>)
 *
 * @author nedis
 * @see Author
 * @see BaseEndpoint
 * @see DocumentationConstants
 * @see Description
 * @see DocumentationDefinition
 * @see IncludeDescription
 * @see IncludeMode
 * @see IntroductionDefinition
 * @see License
 * @see ResourceDefinition
 * @see ResourceGroupDefinition
 * @see Title
 * @since 0.1
 */
@Documented
@Retention(CLASS)
@Target({MODULE, ANNOTATION_TYPE})
public @interface DocumentationVersion {

    /**
     * Returns the version of REST-based microservice.
     *
     * @return the version of REST-based microservice
     */
    String value();

    /**
     * Returns the variable definitions for the provided version of REST-based microservice, using key-value format.
     *
     * <p>
     * For example:
     *
     * <pre>
     * {@code
     * @DocumentationVersion(
     *      value = "${major}.${minor}",
     *      variables = {
     *          "${major}", EXTERNAL_CONSTANT_MAJOR_VERSION,
     *          "${minor}", EXTERNAL_CONSTANT_MINOR_VERSION
     *      }
     * )
     * }
     * </pre>
     *
     * @return the variable definitions for the provided version of REST-based microservice, using key-value format.
     */
    String[] variables() default {};
}
