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

package io.rxmicro.test.mockito.r2dbc;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public enum ErrorDuringSQLInvocationType {

    CREATE_CONNECTION_FAILED,

    CREATE_STATEMENT_FAILED,

    BIND_STATEMENT_ARGUMENTS_FAILED,

    EXECUTE_STATEMENT_FAILED,

    RETURN_RESULT_SET_FAILED,

    RETURN_ROWS_UPDATED_FAILED,

    CLOSE_CONNECTION_FAILED
}
