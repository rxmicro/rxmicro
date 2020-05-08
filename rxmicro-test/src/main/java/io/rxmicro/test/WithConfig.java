/*
 * Copyright 2019 https://rxmicro.io
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

package io.rxmicro.test;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Declares the static field of the test class as a configuration which must be registered in the configuration manager
 * before starting the test.
 * <p>
 * This annotation allows declaring a configuration using Java classes.
 * <i>(The configuration defined in this way is only available while the test is running.)</i>
 * <p>
 * The RxMicro framework supports test configuration only for REST-based microservice tests and component unit tests.
 *
 * @author nedis
 * @since 0.1
 * @see io.rxmicro.config.Config
 * @see io.rxmicro.config.Configs#getConfig(Class)
 * @see io.rxmicro.config.Configs#getConfig(String, Class)
 */
@Documented
@Retention(RUNTIME)
@Target(FIELD)
public @interface WithConfig {

    /**
     * Returns the custom namespace for specified configuration
     *
     * @return the custom namespace for specified configuration
     * @see io.rxmicro.config.Config#getDefaultNameSpace(Class)
     * @see io.rxmicro.config.Configs#getConfig(String, Class)
     */
    String value() default "";
}
