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

import io.rxmicro.data.sql.operation.Delete;
import io.rxmicro.data.sql.operation.Insert;
import io.rxmicro.data.sql.operation.Select;
import io.rxmicro.data.sql.operation.Update;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Denotes a storage with the values of the predefined variables.
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
 *         If the variable value is undefined in all specified places, then the RxMicro framework notifies the developer about the error.
 *     </li>
 * </ol>
 *
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
@Documented
@Retention(SOURCE)
@Target({TYPE, METHOD})
public @interface VariableValues {

    /**
     * Must be even item counts:
     * <p>
     * {@code name1, value1, name2, value2, ...}
     *
     * @return pairs of 'name, value', which is used to resolve variables in the all SQL queries per interface
     */
    String[] value();
}
