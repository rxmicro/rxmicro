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

import io.rxmicro.data.sql.VariableValues;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static io.rxmicro.common.CommonConstants.EMPTY_STRING;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * Denotes a repository method that must execute a {@code INSERT} SQL operation.
 *
 * @author nedis
 * @see io.rxmicro.data.sql.SupportedVariables
 * @see Delete
 * @see Select
 * @see Update
 * @since 0.1
 */
@Documented
@Retention(CLASS)
@Target(METHOD)
public @interface Insert {

    /**
     * The default {@code INSERT} statement if value is empty and
     * repository method returns primitive model type: {@link Void}, {@link Integer} or {@link Boolean}.
     *
     * @see io.rxmicro.data.sql.SupportedVariables
     */
    String DEFAULT_INSERT = "INSERT INTO ${table}(${inserted-columns}) VALUES(${values})";

    /**
     * The default {@code INSERT} statement if value is empty and repository method returns entity or
     * {@link io.rxmicro.data.sql.model.EntityFieldList} or {@link io.rxmicro.data.sql.model.EntityFieldMap} model types.
     *
     * @see io.rxmicro.data.sql.SupportedVariables
     * @see io.rxmicro.data.sql.model.EntityFieldMap
     * @see io.rxmicro.data.sql.model.EntityFieldList
     */
    String DEFAULT_INSERT_WITH_RETURNING_ID = "INSERT INTO ${table}(${inserted-columns}) VALUES(${values}) RETURNING ${id-columns}";

    /**
     * Customize {@code INSERT} query.
     *
     * <p>
     * By default, Rx Micro generate the default sql.
     *
     * <p>
     * See {@link #DEFAULT_INSERT} or {@link #DEFAULT_INSERT_WITH_RETURNING_ID} for details.
     *
     * @return the custom {@code INSERT} SQL
     */
    String value() default EMPTY_STRING;

    /**
     * entityClass is used to resolve
     * <code>${table}</code>, <code>${updated-columns}</code> or <code>${by-id-filter}</code> variable values.
     *
     * <p>
     * To determine the value of the predefined variable used in the query specified for the repository method, the RxMicro framework
     * uses the following algorithm:
     * <ol>
     *     <li>
     *         If the repository method returns or accepts the entity model as a parameter, the entity model class is used to define
     *         the variable value.
     *     </li>
     *     <li>
     *         Otherwise, the RxMicro framework analyzes the optional entityClass parameter defined in the
     *         {@link Select}, {@link Insert}, {@link Update} and {@link Delete} annotations.
     *     </li>
     *     <li>
     *         If the optional entityClass parameter is set, the class specified in this parameter is used to define the variable value.
     *     </li>
     *     <li>
     *         If the optional entityClass parameter is missing, the RxMicro framework tries to extract the variable value from the
     *         {@link VariableValues} annotation, which annotates this repository method.
     *     </li>
     *     <li>
     *         If the repository method is not annotated with the {@link VariableValues} annotation or the {@link VariableValues} annotation
     *         does not contain the value of a predefined variable, then the RxMicro framework tries to extract the value of this variable
     *         from the {@link VariableValues} annotation, which annotates the repository interface.
     *     </li>
     *     <li>
     *         If the variable value is undefined in all specified places, then the RxMicro framework notifies the developer
     *         about the error.
     *     </li>
     * </ol>
     *
     * @return the entity class
     */
    Class<?> entityClass() default Void.class;
}
