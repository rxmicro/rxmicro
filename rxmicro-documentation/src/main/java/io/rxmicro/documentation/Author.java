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

import static io.rxmicro.documentation.DocumentationConstants.DEFAULT_AUTHOR;
import static io.rxmicro.documentation.DocumentationConstants.DEFAULT_EMAIL;
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.MODULE;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * Denotes the author of the generated REST-based microservice documentation.
 *
 * <p>
 * (<i>Allows overriding the author specified in the developer directive to pom.xml</i>)
 *
 * @author nedis
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
 * @see Title
 * @since 0.1
 */
@Documented
@Retention(CLASS)
@Target({MODULE, ANNOTATION_TYPE})
@Repeatable(Author.List.class)
public @interface Author {

    /**
     * Returns the author name.
     *
     * @return the author name
     */
    String name() default DEFAULT_AUTHOR;

    /**
     * Returns the author email.
     *
     * @return the author email
     */
    String email() default DEFAULT_EMAIL;

    /**
     * Returns the variable definitions for the provided author name and(or) email, using key-value format.
     *
     * <p>
     * For example:
     *
     * <pre>
     * {@code
     * @Author(
     *      name = "${firstName}-${lastName}",
     *      email = "${firstName}-${lastName}@gmail.com",
     *      variables = {
     *          "${firstName}", EXTERNAL_CONSTANT_FIRST_NAME,
     *          "${lastName}", EXTERNAL_CONSTANT_LAST_NAME
     *      }
     * )
     * }
     * </pre>
     *
     * @return the variable definitions for the provided author name and(or) email, using key-value format.
     */
    String[] variables() default {};

    /**
     * Defines several {@link Author} annotations on the same element.
     *
     * @author nedis
     * @since 0.1
     */
    @Documented
    @Retention(CLASS)
    @Target({MODULE, ANNOTATION_TYPE})
    @interface List {

        /**
         * Returns the several {@link Author} annotations on the same element.
         *
         * @return the several {@link Author} annotations on the same element.
         */
        Author[] value();
    }
}
