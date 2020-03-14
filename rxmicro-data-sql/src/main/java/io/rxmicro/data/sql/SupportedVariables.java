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

package io.rxmicro.data.sql;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @see io.rxmicro.data.sql.operation.Select
 * @see io.rxmicro.data.sql.operation.Insert
 * @see io.rxmicro.data.sql.operation.Update
 * @see io.rxmicro.data.sql.operation.Delete
 * @since 0.1
 */
public final class SupportedVariables {

    /**
     * Database full table name.
     * i.e. if schema defined, then ${table} = schemaName.tableName, otherwise ${table} = tableName
     */
    public static final String TABLE = "${table}";

    /**
     * All table columns defined at java class, separated by comma (,)
     * <p>
     * For example: <code>${all-columns} = id, login, name, password</code>
     */
    public static final String ALL_COLUMNS = "${all-columns}";

    /**
     * Table columns defined at java class and available to insert to database table, separated by comma (,)
     * ${inserted-columns} = ${all-columns} minus {Columns annotated by @NotInsertable} minus {auto generated primary keys}
     * <p>
     * For example: <code>${inserted-columns} = login, name, password</code>
     */
    public static final String INSERTED_COLUMNS = "${inserted-columns}";

    /**
     * Table column set expressions defined at java class and available to update to database table, separated by comma (,)
     * ${updated-columns} = ${all-columns} minus {Columns annotated by @NotUpdatable} minus {primary keys}
     * <p>
     * For example: <code>${updated-columns} = login = ?, name = ?, password = ?</code>
     */
    public static final String UPDATED_COLUMNS = "${updated-columns}";

    /**
     * Insertable values, separated by comma (,)
     * <p>
     * For example: <code>${values} = ?, ?, ?</code>
     */
    public static final String VALUES = "${values}";

    /**
     * Primary keys defined at java class, separated by comma (,)
     * ${id-columns} = {primary keys}
     * <p>
     * For example: <code>${id-columns} = id1, id2</code> if primary key is complex
     * For example: <code>${id-columns} = id</code> if primary key is simple
     */
    public static final String ID_COLUMNS = "${id-columns}";

    /**
     * By primary key filters, separated by comma (,)
     * <p>
     * For example: <code>${by-id-filter} = id1 = ? AND id2 = ?</code> if primary key is complex
     * For example: <code>${by-id-filter} = id = ?</code> if primary key is simple
     */
    public static final String BY_ID_FILTER = "${by-id-filter}";

    private SupportedVariables() {
    }
}
