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

package io.rxmicro.config;

import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.MODULE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Allows overriding a default value for config property.
 * <p>
 * Configuration using java classes similar to configuration using java annotations.
 * The main difference between configuring with annotations and configuring with Java classes is support of the settings inheritance and
 * overriding. In other words, when using configuration via annotations, the RxMicro can also read other configuration sources.
 * When using configuration via Java classes, other configuration sources are always ignored.
 * <p>
 * Please, pay attention when overriding the default value with the annotations!
 * If You make a mistake when specifying a setting name (this refers to the namespace and field name),
 * no error will occur upon starting! The overridden value will be simply ignored!
 * <p>
 * This annotation can be applied to override primitive values only:
 * <ul>
 *     <li>strings</li>
 *     <li>boolean</li>
 *     <li>numbers</li>
 *     <li>dates</li>
 *     <li>times</li>
 *     <li>enums</li>
 * </ul>
 * <p>
 * If it is necessary to override a complex value, {@link DefaultConfigValueSupplier} annotation must be used instead.
 *
 * @author nedis
 * @since 0.1
 * @see DefaultConfigValueSupplier
 * @see Config
 * @see Configs#getConfig(String, Class)
 * @see Configs#getConfig(Class)
 */
@Documented
@Retention(SOURCE)
@Target({TYPE, MODULE, ANNOTATION_TYPE})
@Repeatable(DefaultConfigValue.List.class)
public @interface DefaultConfigValue {

    /**
     * When overriding the configuration value, it is necessary to specify the configuration class.
     * <p>
     * If the configuration class is specified, the namespace may not be specified.
     * <p>
     * See {@link DefaultConfigValue#name()}.
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
     * @return property name without namespace if {@link DefaultConfigValue#configClass()} is specified
     *         or property name with required namespace if {@link DefaultConfigValue#configClass()} is not set
     */
    String name();

    /**
     * Returns the overridden property value
     *
     * @return the overridden property value
     */
    String value();

    /**
     * Defines several {@link DefaultConfigValue} annotations on the same element.
     *
     * @author nedis
     * @since 0.1
     */
    @Documented
    @Retention(SOURCE)
    @Target({TYPE, MODULE, ANNOTATION_TYPE})
    @interface List {

        /**
         * Returns the several {@link DefaultConfigValue} annotations on the same element.
         *
         * @return the several {@link DefaultConfigValue} annotations on the same element.
         */
        DefaultConfigValue[] value();
    }
}
