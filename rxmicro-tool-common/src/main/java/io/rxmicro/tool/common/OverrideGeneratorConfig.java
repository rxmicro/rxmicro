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

package io.rxmicro.tool.common;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * For internal use only.
 *
 * <p>
 * Don't use this annotation for your project!
 *
 * @author nedis
 * @since 0.1
 */
@Documented
@Retention(CLASS)
@Target(TYPE)
@Repeatable(OverrideGeneratorConfig.List.class)
public @interface OverrideGeneratorConfig {

    /**
     * For internal use only.
     *
     * @return the annotation config class
     */
    Class<? extends Annotation> annotationConfigClass();

    /**
     * For internal use only.
     *
     * @return the parameter name
     */
    String parameterName();

    /**
     * For internal use only.
     *
     * @return the overridden value
     */
    String overriddenValue();

    /**
     * Defines several {@link OverrideGeneratorConfig} annotations on the same element.
     *
     * @author nedis
     * @since 0.1
     */
    @Documented
    @Retention(CLASS)
    @Target(TYPE)
    @interface List {

        /**
         * Returns the several {@link OverrideGeneratorConfig} annotations on the same element.
         *
         * @return the several {@link OverrideGeneratorConfig} annotations on the same element.
         */
        OverrideGeneratorConfig[] value();
    }
}
