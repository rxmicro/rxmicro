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

package io.rxmicro.data;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.MODULE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * Allows configuring the repository generation process.
 *
 * @author nedis
 * @since 0.1
 */
@Documented
@Retention(CLASS)
@Target({TYPE, MODULE, ANNOTATION_TYPE})
public @interface DataRepositoryGeneratorConfig {

    /**
     * Returns {@code true} if the RxMicro framework must add original query to generated code as string comment.
     *
     * @return {@code true} if the RxMicro framework must add original query to generated code as string comment
     */
    boolean addOriginalQueryToGeneratedCodeAsComment() default true;
}
