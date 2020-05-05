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

package io.rxmicro.rest;

import io.rxmicro.model.MappingStrategy;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Declares a strategy of header name formation based on Java model field name analysis.
 *
 * By default, the CAPITALIZE_WITH_HYPHEN strategy is used.
 * Thus, by using this strategy, the {@code 'Header-Name'} name header corresponds to the {@code 'headerName'} field name.
 *
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
@Documented
@Retention(SOURCE)
@Target({TYPE, METHOD})
public @interface HeaderMappingStrategy {

    /**
     * By default, the CAPITALIZE_WITH_HYPHEN strategy is used.
     * Thus, by using this strategy, the {@code 'Header-Name'} name header corresponds to the {@code 'headerName'} field name.
     *
     * @return mapping strategy
     */
    MappingStrategy value() default MappingStrategy.CAPITALIZE_WITH_HYPHEN;
}
