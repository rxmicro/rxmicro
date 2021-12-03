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

package io.rxmicro.data.sql.r2dbc.postgresql;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * Denotes that an interface is a dynamic generated PostgreSQL data repository.
 *
 * @author nedis
 * @see io.rxmicro.data.DataRepositoryGeneratorConfig
 * @see io.rxmicro.data.RepositoryFactory
 * @see PostgreSQLConfig
 * @see PostgreSQLClientFactory
 * @since 0.1
 */
@Documented
@Retention(CLASS)
@Target(TYPE)
public @interface PostgreSQLRepository {

    /**
     * Returns the config namespace for the current repository.
     *
     * @return the config namespace for the current repository
     */
    String configNameSpace() default "postgre-sql";
}
