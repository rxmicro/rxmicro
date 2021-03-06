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

package io.rxmicro.test.junit;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Used to specify the method from the current test class to be invoked by the RxMicro framework before running the test method.
 *
 * <p>
 * (<i>The RxMicro framework supports the {@code @}{@link BeforeThisTest} annotation only for
 * REST-based microservice tests and component unit tests.</i>)
 *
 * @author nedis
 * @see BeforeIterationMethodSource
 * @see org.junit.jupiter.api.BeforeEach
 * @since 0.1
 */
@Documented
@Retention(RUNTIME)
@Target(METHOD)
public @interface BeforeThisTest {

    /**
     * Returns the method from the current test class to be invoked by the RxMicro framework before running the test method.
     *
     * @return the method from the current test class to be invoked by the RxMicro framework before running the test method.
     */
    String method();
}
