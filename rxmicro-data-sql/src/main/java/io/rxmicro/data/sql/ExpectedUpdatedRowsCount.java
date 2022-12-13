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

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * Enables validation for updated rows count during DML operation, like {@link io.rxmicro.data.sql.operation.Insert},
 * {@link io.rxmicro.data.sql.operation.Update} and {@link io.rxmicro.data.sql.operation.Delete}.
 *
 * <p>
 * If current database has invalid state the {@link io.rxmicro.data.sql.model.InvalidDatabaseStateException} will be thrown!
 *
 * @author nedis
 * @see io.rxmicro.data.sql.model.InvalidDatabaseStateException
 * @since 0.7
 */
@Documented
@Retention(CLASS)
@Target(METHOD)
public @interface ExpectedUpdatedRowsCount {

    /**
     * Returns the expected updated rows count during DML operation, like {@link io.rxmicro.data.sql.operation.Insert},
     * {@link io.rxmicro.data.sql.operation.Update} and {@link io.rxmicro.data.sql.operation.Delete}.
     *
     * @return the expected updated rows count
     */
    long value();
}
