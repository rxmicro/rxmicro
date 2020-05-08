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

package io.rxmicro.test.mockito.r2dbc;

/**
 * Represents the constants that can be used to emulate an error during invocation of an SQL query
 *
 * @author nedis
 * @since 0.1
 */
public enum ErrorDuringSQLInvocationType {

    /**
     * Emulates an error that occurs during the stage of creation the connection to db
     */
    CREATE_CONNECTION_FAILED,

    /**
     * Emulates an error that occurs during the stage of creation the statement for SQL query
     */
    CREATE_STATEMENT_FAILED,

    /**
     * Emulates an error that occurs during the stage of binding the SQL query parameters
     */
    BIND_STATEMENT_ARGUMENTS_FAILED,

    /**
     * Emulates an error that occurs during the stage of execution the statement for SQL query
     */
    EXECUTE_STATEMENT_FAILED,

    /**
     * Emulates an error that occurs during the stage of returning the result set for SQL query
     */
    RETURN_RESULT_SET_FAILED,

    /**
     * Emulates an error that occurs during the stage of updating the table rows for SQL query
     */
    RETURN_ROWS_UPDATED_FAILED,

    /**
     * Emulates an error that occurs during the stage of closing the connection to db
     */
    CLOSE_CONNECTION_FAILED
}
