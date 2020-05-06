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

/**
 * Supported variables that can be used in the SQL queries.
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
 * @see io.rxmicro.data.sql.operation.Select
 * @see io.rxmicro.data.sql.operation.Insert
 * @see io.rxmicro.data.sql.operation.Update
 * @see io.rxmicro.data.sql.operation.Delete
 * @since 0.1
 */
public final class SupportedVariables {

    /**
     * Database full table name.
     * i.e. if schema defined, then <code>${table} = schemaName.tableName}</code>,
     * otherwise <code>${table} = tableName}</code>
     */
    public static final String TABLE = "${table}";

    /**
     * All table columns defined at java class, separated by comma {@code ,}
     * <p>
     * For example: <code>${all-columns} = id, login, name, password</code>
     */
    public static final String ALL_COLUMNS = "${all-columns}";

    /**
     * Table columns defined at java class and available to insert to database table, separated by comma {@code ,}
     * <p>
     * <code>${inserted-columns} =
     * ${all-columns} minus {Columns annotated by @{@link NotInsertable}} minus {auto generated primary keys}</code>
     * <p>
     * For example: <code>${inserted-columns} = login, name, password</code>
     */
    public static final String INSERTED_COLUMNS = "${inserted-columns}";

    /**
     * Table column set expressions defined at java class and available to update to database table, separated by comma {@code ,}
     * <p>
     * <code>${updated-columns} = ${all-columns} minus {Columns annotated by @{@link NotUpdatable}} minus {primary keys}</code>
     * <p>
     * For example: <code>${updated-columns} = login = ?, name = ?, password = ?</code>
     */
    public static final String UPDATED_COLUMNS = "${updated-columns}";

    /**
     * Insertable values, separated by comma {@code ,}
     * <p>
     * For example: <code>${values} = ?, ?, ?</code>
     */
    public static final String VALUES = "${values}";

    /**
     * Primary keys defined at java class, separated by comma {@code ,}
     * <p>
     * <code>${id-columns} = {primary keys}</code>
     * <p>
     * For example: <code>${id-columns} = id1, id2</code> if primary key is complex
     * For example: <code>${id-columns} = id</code> if primary key is simple
     */
    public static final String ID_COLUMNS = "${id-columns}";

    /**
     * By primary key filters, separated by comma {@code ,}
     * <p>
     * For example: <code>${by-id-filter} = id1 = ? AND id2 = ?</code> if primary key is complex
     * For example: <code>${by-id-filter} = id = ?</code> if primary key is simple
     */
    public static final String BY_ID_FILTER = "${by-id-filter}";

    private SupportedVariables() {
    }
}
