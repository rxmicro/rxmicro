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

package io.rxmicro.data.sql;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Denotes a sequence that must be used to get the next unique value for model field.
 *
 * @author nedis
 * @since 0.1
 * @see Schema
 * @see Table
 * @see PrimaryKey
 */
@Documented
@Retention(SOURCE)
@Target({FIELD, METHOD})
public @interface SequenceGenerator {

    /**
     * Sequence name can be template.
     * <p>
     * Allowed variables:
     * <ul>
     *     <li><code>${schema}</code> - the table schema</li>
     *     <li><code>${table}</code> - the table name</li>
     * </ul>
     * <p>
     * If 'schema' parameter is set, 'value' must contain required <code>${schema}</code> variable,
     * otherwise 'value' is absolute sequence name.
     *
     * @return the sequence name.
     */
    String value() default "${schema}.${table}_seq";

    /**
     * Provides the schema name for sequence object
     *
     * @return the schema name
     */
    String schema() default "";
}
