/*
 * Copyright (c) 2020. http://rxmicro.io
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
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
@Documented
@Retention(SOURCE)
@Target(ANNOTATION_TYPE)
public @interface IntroductionDefinition {

    String[] customSection() default {};

    IncludeMode includeMode() default IncludeMode.INCLUDE_REFERENCE;

    Section[] sectionOrder() default {
            Section.Common_Concept,
            Section.Base_Endpoint,
            Section.Http_Verbs,
            Section.Error_Model,
            Section.Handler_Not_Found,
            Section.Licenses,
            Section.Specification
    };

    /**
     * @author nedis
     * @link http://rxmicro.io
     * @since 0.1
     */
    enum Section {

        Common_Concept,

        Base_Endpoint,

        Http_Verbs,

        Error_Model,

        Handler_Not_Found,

        Licenses,

        Specification,

        Custom_section;

        public boolean isCustomSection() {
            return this == Custom_section;
        }
    }
}
