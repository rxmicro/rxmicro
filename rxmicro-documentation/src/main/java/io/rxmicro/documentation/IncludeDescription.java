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
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.MODULE;
import static java.lang.annotation.ElementType.PACKAGE;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * Denotes the AsciiDoc fragment to be imported into the generated REST-based microservice documentation.
 *
 * <p>
 * In addition to the description of all REST-based microservice documentation, this annotation also allows to developer
 * to specify the AsciiDoc fragment for separate elements: sections, model fields, etc.
 *
 * @author nedis
 * @see Author
 * @see BaseEndpoint
 * @see DocumentationConstants
 * @see Description
 * @see DocumentationDefinition
 * @see DocumentationVersion
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
@Target({FIELD, TYPE, MODULE, PACKAGE, PARAMETER, CONSTRUCTOR, ANNOTATION_TYPE, METHOD})
public @interface IncludeDescription {

    /**
     * Returns the resource name.
     *
     * @return the resource name
     */
    String resource();

    /**
     * Returns the variable definitions for the provided resource name, using key-value format.
     *
     * <p>
     * For example:
     *
     * <pre>
     * {@code
     * @IncludeDescription(
     *      resource = "/${home}/destination",
     *      variables = {
     *          "${home}", EXTERNAL_CONSTANT_HOME_DIRECTORY
     *      }
     * )
     * }
     * </pre>
     *
     * @return the variable definitions for the provided resource name, using key-value format.
     */
    String[] variables() default {};

    /**
     * Returns the include mode. See {@link IncludeMode} for details.
     *
     * @return the include mode.
     */
    IncludeMode includeMode() default IncludeMode.INCLUDE_REFERENCE;
}
