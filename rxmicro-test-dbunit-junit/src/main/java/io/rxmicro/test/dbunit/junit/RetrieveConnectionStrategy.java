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

package io.rxmicro.test.dbunit.junit;

/**
 * Declares the strategies that used by BDUnit for retrieving of connection to database.
 *
 * @author nedis
 * @since 0.7
 */
public enum RetrieveConnectionStrategy {

    /**
     * This strategy informs the DBUnit to use single connection per all tests for your project.
     *
     * <p>
     * The RxMicro team recommends using this strategy for external databases only.
     */
    PER_ALL_TEST_CLASSES,

    /**
     * This strategy informs the DBUnit to create a new connection before run all tests for each test class and
     * to close after running all tests for each test class.
     */
    PER_TEST_CLASS,

    /**
     * This strategy informs the DBUnit to create a new connection before each test method and to close after each one.
     */
    PER_TEST_METHOD
}
