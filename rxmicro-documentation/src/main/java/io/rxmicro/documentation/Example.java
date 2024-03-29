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

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * Denotes the model field value used as an example in the generated REST-based microservice documentation.
 *
 * @author nedis
 * @see Description
 * @see IncludeDescription
 * @see IncludeMode
 * @since 0.1
 */
@Documented
@Retention(CLASS)
@Target({FIELD, METHOD, PARAMETER})
@Repeatable(Example.List.class)
public @interface Example {

    /**
     * Returns the example value.
     *
     * @return the example value
     */
    String value();

    /**
     * Returns the variable definitions for the provided example value, using key-value format.
     *
     * <p>
     * For example:
     *
     * <pre>
     * {@code
     * @Example(
     *      value = "${key}.${value}",
     *      variables = {
     *          "${key}", EXTERNAL_CONSTANT_KEY,
     *          "${value}", EXTERNAL_CONSTANT_VALUE
     *      }
     * )
     * }
     * </pre>
     *
     * @return the variable definitions for the provided example value, using key-value format.
     */
    String[] variables() default {};

    /**
     * Defines several {@link Example} annotations on the same element.
     *
     * @author nedis
     * @since 0.1
     */
    @Documented
    @Retention(CLASS)
    @Target({FIELD, METHOD, PARAMETER})
    @interface List {

        /**
         * Returns the several {@link Example} annotations on the same element.
         *
         * @return the several {@link Example} annotations on the same element.
         */
        Example[] value();
    }
}
