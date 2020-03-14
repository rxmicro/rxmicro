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
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
@Documented
@Retention(SOURCE)
@Target({ANNOTATION_TYPE, METHOD, TYPE})
public @interface ResourceDefinition {

    boolean withExamples() default true;

    boolean withPathVariablesDescriptionTable() default true;

    boolean withHeadersDescriptionTable() default true;

    boolean withQueryParametersDescriptionTable() default true;

    boolean withBodyParametersDescriptionTable() default true;

    boolean withJsonSchema() default true;

    boolean withStandardDescriptions() default true;

    boolean withReadMore() default true;

    boolean withValidationResponse() default true;

    boolean withInternalErrorResponse() default true;

    boolean withRequestIdResponseHeader() default true;
}
