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
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * For internal use only!
 * Don't use this annotation for your project!
 *
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
@Documented
@Retention(SOURCE)
@Target(TYPE)
@Repeatable(OverrideGeneratorConfig.List.class)
public @interface OverrideGeneratorConfig {

    Class<? extends Annotation> annotationConfigClass();

    String parameterName();

    String overriddenValue();

    /**
     * @author nedis
     * @link https://rxmicro.io
     * @since 0.1
     */
    @Documented
    @Retention(SOURCE)
    @Target(TYPE)
    @interface List {

        OverrideGeneratorConfig[] value();
    }
}
