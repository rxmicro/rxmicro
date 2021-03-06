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

package io.rxmicro.test.dbunit;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Provides the expected state of tested database after execution of the test method.
 * If expected state does not match to the actual database state the {@link AssertionError} error will be thrown.
 *
 * @author nedis
 * @since 0.7
 */
@Documented
@Retention(RUNTIME)
@Target(METHOD)
public @interface ExpectedDataSet {

    /**
     * Returns the list of dataset files.
     *
     * @return the list of dataset files.
     */
    String[] value() default {};

    /**
     * Returns column names to sort the dataset with.
     *
     * <p>
     * If column has the same name for two or more table, use full column name: ${tableName}.${columnName}.
     *
     * @return column names to sort the dataset with
     */
    String[] orderBy() default {};
}
