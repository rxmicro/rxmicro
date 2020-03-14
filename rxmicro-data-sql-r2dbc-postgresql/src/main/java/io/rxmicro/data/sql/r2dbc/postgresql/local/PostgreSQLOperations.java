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

package io.rxmicro.data.sql.r2dbc.postgresql.local;

import java.lang.annotation.Annotation;
import java.util.Set;

import static io.rxmicro.data.sql.local.SQLOperations.SQL_OPERATION_ANNOTATIONS;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public final class PostgreSQLOperations {

    public static final Set<Class<? extends Annotation>> POSTGRESQL_OPERATION_ANNOTATIONS = SQL_OPERATION_ANNOTATIONS;

    private PostgreSQLOperations() {
    }
}
