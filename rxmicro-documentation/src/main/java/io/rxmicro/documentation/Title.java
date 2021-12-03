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
import static java.lang.annotation.ElementType.MODULE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * Denotes the name of the generated REST-based microservice documentation.
 *
 * <p>
 * (<i>Allows overriding the name of the generated REST-based microservice documentation specified in the name directive to pom.xml</i>)
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
 * @see ResourceDefinition
 * @see ResourceGroupDefinition
 * @since 0.1
 */
@Documented
@Retention(CLASS)
@Target({MODULE, METHOD, TYPE, ANNOTATION_TYPE})
public @interface Title {

    /**
     * Returns the title.
     *
     * @return the title
     */
    String value();

    /**
     * Returns the variable definitions for the provided title, using key-value format.
     *
     * <p>
     * For example:
     *
     * <pre>
     * {@code
     * @Title(
     *      value = "If ${key} = ${value} than this field contains true, otherwise false",
     *      variables = {
     *          "${key}", EXTERNAL_CONSTANT_KEY,
     *          "${value}", EXTERNAL_CONSTANT_VALUE
     *      }
     * )
     * }
     * </pre>
     *
     * @return the variable definitions for the provided title, using key-value format.
     */
    String[] variables() default {};
}
