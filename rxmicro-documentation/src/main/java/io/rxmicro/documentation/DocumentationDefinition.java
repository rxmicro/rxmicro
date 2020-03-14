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

import static io.rxmicro.documentation.DocumentationDefinition.GenerationOutput.SINGLE_DOCUMENT;
import static java.lang.annotation.ElementType.MODULE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
@Documented
@Retention(SOURCE)
@Target(MODULE)
public @interface DocumentationDefinition {

    GenerationOutput[] output() default {SINGLE_DOCUMENT};

    String destinationDirectory() default "";

    boolean withTips() default true;

    boolean withGeneratedDate() default true;

    IntroductionDefinition introduction() default @IntroductionDefinition;

    ResourceGroupDefinition resourceGroup() default @ResourceGroupDefinition;

    ResourceDefinition resource() default @ResourceDefinition;

    /**
     * @author nedis
     * @link http://rxmicro.io
     * @since 0.1
     */
    enum GenerationOutput {

        SINGLE_DOCUMENT,

        BASICS_SECTION,

        RESOURCES_SECTION
    }
}
