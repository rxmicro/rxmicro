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

package io.rxmicro.data.sql.operation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * Denotes a string parameter of repository method, the value of that must be used as custom {@code SELECT} SQL query.
 *
 * @author nedis
 * @see io.rxmicro.data.sql.SupportedVariables
 * @see Select
 * @since 0.1
 */
@Documented
@Retention(CLASS)
@Target(PARAMETER)
public @interface CustomSelect {

    /**
     * Returns the support of the universal placeholder.
     *
     * <p>
     * Read more about db specific placeholders:
     * <a href="https://r2dbc.io/spec/0.8.0.RELEASE/spec/html/#statements.parameterized">
     *     https://r2dbc.io/spec/0.8.0.RELEASE/spec/html/#statements.parameterized
     * </a>
     *
     * @return {@code true} if the RxMicro framework must replace '?' placeholder by db specific placeholder before executing of
     *                      the custom SQL,
     *         {@code false} if custom select query must contain only db specific placeholders. i.e. '$1', '$2', etc.
     */
    boolean supportUniversalPlaceholder() default true;

    /**
     * Returns the selected columns that returned by this custom {@code SELECT} query.
     *
     * @return the selected columns that returned by this custom {@code SELECT} query.
     */
    String[] selectedColumns() default {};
}
