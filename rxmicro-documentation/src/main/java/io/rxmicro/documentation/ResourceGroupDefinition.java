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
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * A composite annotation that specifies the settings for generating the ResourceGroupDefinition section.
 *
 * @author nedis
 * @since 0.1
 * @see Author
 * @see BaseEndpoint
 * @see Constants
 * @see Description
 * @see DocumentationDefinition
 * @see DocumentationVersion
 * @see IncludeDescription
 * @see IncludeMode
 * @see IntroductionDefinition
 * @see License
 * @see ResourceDefinition
 * @see Title
 */
@Documented
@Retention(SOURCE)
@Target({ANNOTATION_TYPE, TYPE})
public @interface ResourceGroupDefinition {

    /**
     * Returns the custom sections
     *
     * @return the custom sections
     */
    String[] customSection() default {};

    /**
     * Returns the include mode. See {@link IncludeMode} for details.
     *
     * @return the include mode.
     */
    IncludeMode includeMode() default IncludeMode.INCLUDE_REFERENCE;

    /**
     * Returns the current section order
     *
     * @return the current section order
     */
    Section[] sectionOrder() default {
            Section.VERSIONING,
            Section.CORS
    };

    /**
     * Supported section types
     *
     * @author nedis
     * @since 0.1
     */
    enum Section {

        /**
         * Versioning standard section
         */
        VERSIONING,

        /**
         * CORS standard section
         */
        CORS,

        /**
         * Custom section.
         *
         * If CUSTOM_SECTION is added to {@link ResourceGroupDefinition#sectionOrder()} it is necessary to
         * add path to custom section to {@link ResourceGroupDefinition#customSection()}.
         * Otherwise the RxMicro framework throws error during generation of REST-based microservice documentation.
         */
        CUSTOM_SECTION;

        /**
         * Returns {@code true} if current section is custom.
         *
         * @return {@code true} if current section is custom.
         */
        public boolean isCustomSection() {
            return this == CUSTOM_SECTION;
        }
    }
}
