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

package io.rxmicro.data.sql.operation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @link https://www.postgresql.org/docs/12/sql-delete.html
 * @see io.rxmicro.data.sql.SupportedVariables
 * @since 0.1
 */
@Documented
@Retention(SOURCE)
@Target(METHOD)
public @interface Delete {

    /**
     * The default delete statement if value is empty
     *
     * @see io.rxmicro.data.sql.SupportedVariables
     */
    String DEFAULT_DELETE = "DELETE FROM ${table} WHERE ${by-id-filter}";

    /**
     * Customize DELETE query.
     * <p>
     * By default, Rx Micro generate the default sql.
     * See {@link #DEFAULT_DELETE} for details
     *
     * @return custom delete sql
     */
    String value() default "";

    /**
     * entityClass is used to resolve ${table}, ${updated-columns} or ${by-id-filter} variable value.
     *
     * @return entity class
     */
    Class<?> entityClass() default Void.class;
}
