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
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * A composite annotation that specifies the settings for generating the Introduction section.
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
 * @see License
 * @see ResourceDefinition
 * @see ResourceGroupDefinition
 * @see Title
 * @since 0.1
 */
@Documented
@Retention(SOURCE)
@Target(ANNOTATION_TYPE)
public @interface IntroductionDefinition {

    /**
     * Returns the custom sections.
     *
     * @return the custom sections
     */
    String[] customSection() default {};

    /**
     * Returns the include mode. See {@link IncludeMode} for details.
     *
     * @return the include mode
     */
    IncludeMode includeMode() default IncludeMode.INCLUDE_REFERENCE;

    /**
     * Returns the current section order.
     *
     * @return the current section order
     */
    Section[] sectionOrder() default {
            Section.COMMON_CONCEPT,
            Section.BASE_ENDPOINT,
            Section.HTTP_VERBS,
            Section.ERROR_MODEL,
            Section.HANDLER_NOT_FOUND,
            Section.LICENSES,
            Section.SPECIFICATION
    };

    /**
     * Supported section types.
     *
     * @author nedis
     * @since 0.1
     */
    enum Section {

        /**
         * Common concept standard section.
         */
        COMMON_CONCEPT,

        /**
         * base endpoint standard section.
         */
        BASE_ENDPOINT,

        /**
         * HTTP verbs standard section.
         */
        HTTP_VERBS,

        /**
         * Error model standard section.
         */
        ERROR_MODEL,

        /**
         * `Handler not found` standard section.
         */
        HANDLER_NOT_FOUND,

        /**
         * Licences standard section.
         */
        LICENSES,

        /**
         * Specification standard section.
         */
        SPECIFICATION,

        /**
         * Custom section.
         *
         * <p>
         * If CUSTOM_SECTION is added to {@link IntroductionDefinition#sectionOrder()} it is necessary to
         * add path to custom section to {@link IntroductionDefinition#customSection()}.
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
