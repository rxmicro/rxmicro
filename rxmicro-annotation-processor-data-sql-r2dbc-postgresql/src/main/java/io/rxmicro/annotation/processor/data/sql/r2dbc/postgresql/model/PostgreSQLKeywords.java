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

package io.rxmicro.annotation.processor.data.sql.r2dbc.postgresql.model;

import io.rxmicro.annotation.processor.data.sql.model.SQLKeywords;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public final class PostgreSQLKeywords extends SQLKeywords {

    public static final String RETURNING = "RETURNING";

    public static final String LIMIT = "LIMIT";

    public static final String OFFSET = "OFFSET";

    public static final String FETCH = "FETCH";

    public static final String FOR = "FOR";

    private PostgreSQLKeywords() {
    }
}
