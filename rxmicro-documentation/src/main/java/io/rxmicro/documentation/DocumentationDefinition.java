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

import static io.rxmicro.documentation.DocumentationDefinition.GenerationOutput.SINGLE_DOCUMENT;
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.MODULE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * A composite annotation that specifies the settings for generating a whole document.
 *
 * @author nedis
 * @see Author
 * @see BaseEndpoint
 * @see DocumentationConstants
 * @see Description
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
@Retention(SOURCE)
@Target({MODULE, ANNOTATION_TYPE})
public @interface DocumentationDefinition {

    /**
     * Returns the array of outputs. See {@link GenerationOutput} for details.
     *
     * @return the array of outputs. See {@link GenerationOutput} for details.
     */
    GenerationOutput[] output() default {SINGLE_DOCUMENT};

    /**
     * Returns the custom destination directory.
     *
     * @return the custom destination directory
     */
    String destinationDirectory() default "";

    /**
     * Returns {@code true} if the RxMicro framework must generate tips for REST-based microservice documentation.
     *
     * @return {@code true} if the RxMicro framework must generate tips for REST-based microservice documentation
     */
    boolean withTips() default true;

    /**
     * Returns {@code true} if the RxMicro framework must generate date of generating of REST-based microservice documentation.
     *
     * @return {@code true} if the RxMicro framework must generate date of generating of REST-based microservice documentation
     */
    boolean withGeneratedDate() default true;

    /**
     * Returns the composite annotation that specifies the settings for generating the Introduction section.
     *
     * @return the composite annotation that specifies the settings for generating the Introduction section.
     */
    IntroductionDefinition introduction() default @IntroductionDefinition;

    /**
     * Returns the composite annotation that specifies the settings for generating the ResourceGroupDefinition section.
     *
     * @return the composite annotation that specifies the settings for generating the ResourceGroupDefinition section.
     */
    ResourceGroupDefinition resourceGroup() default @ResourceGroupDefinition;

    /**
     * Returns the composite annotation that specifies the settings for generating the ResourceDefinition section.
     *
     * @return the composite annotation that specifies the settings for generating the ResourceDefinition section.
     */
    ResourceDefinition resource() default @ResourceDefinition;

    /**
     * Supported generation output.
     *
     * @author nedis
     * @since 0.1
     */
    enum GenerationOutput {

        /**
         * Whole document.
         */
        SINGLE_DOCUMENT,

        /**
         * Only basic section.
         */
        BASICS_SECTION,

        /**
         * Only resources section.
         */
        RESOURCES_SECTION
    }
}
