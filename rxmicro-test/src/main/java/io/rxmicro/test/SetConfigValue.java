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

import io.rxmicro.config.Config;

import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Allows overriding the default value for any configuration available only for the test environment.
 * <p>
 * This annotation allows declaring a configuration using annotations.
 * <i>(The configuration defined in this way is only available while the test is running.
 * It means that this annotation is analogous to the ${@code @}{@link io.rxmicro.config.DefaultConfigValue} annotation!)</i>
 * <p>
 * The RxMicro framework supports test configuration only for REST-based microservice tests and component unit tests.
 *
 * @author nedis
 * @since 0.1
 * @see io.rxmicro.config.DefaultConfigValue
 * @see io.rxmicro.config.DefaultConfigValueSupplier
 * @see Config
 * @see io.rxmicro.config.Configs#getConfig(String, Class)
 * @see io.rxmicro.config.Configs#getConfig(Class)
 */
@Documented
@Retention(RUNTIME)
@Target(TYPE)
@Repeatable(SetConfigValue.List.class)
public @interface SetConfigValue {

    /**
     * When overriding the configuration value, it is necessary to specify the configuration class.
     * <p>
     * If the configuration class is specified, the namespace may not be specified.
     * <p>
     * See {@link SetConfigValue#name()}.
     * <p>
     * (It means the field of the specified configuration class.)
     *
     * @return config class
     */
    Class<? extends Config> configClass() default Config.class;

    /**
     * If no configuration class is specified, the name must contain a namespace.
     * <p>
     * (The namespace allows You to clearly define to which configuration class the specified setting belongs.)
     *
     * @return property name without namespace if {@link SetConfigValue#configClass()} is specified
     *         or property name with required namespace if {@link SetConfigValue#configClass()} is not set
     */
    String name();

    /**
     * Returns the overridden property value
     *
     * @return the overridden property value
     */
    String value();

    /**
     * Defines several {@link SetConfigValue} annotations on the same element.
     *
     * @author nedis
     * @since 0.1
     */
    @Documented
    @Retention(RUNTIME)
    @Target(TYPE)
    @interface List {

        /**
         * Returns the several {@link SetConfigValue} annotations on the same element.
         *
         * @return the several {@link SetConfigValue} annotations on the same element.
         */
        SetConfigValue[] value();
    }
}
