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

import io.rxmicro.config.internal.validator.ConfigPropertyValidators;

import java.util.List;

import static io.rxmicro.common.util.Requires.require;
import static io.rxmicro.common.util.Strings.splitByCamelCase;
import static io.rxmicro.common.util.Strings.unCapitalize;
import static java.util.stream.Collectors.joining;

/**
 * The parent class for all config classes.
 *
 * @author nedis
 * @see ConfigSource
 * @since 0.1
 */
public abstract class Config {

    /**
     * Defines a character that used as list (set, sorted set, map entries) values delimiter.
     *
     * <p>
     * Collection examples:
     * <code>
     * propertyName1=value1,value2,value3
     * </code>
     * For such cases {@code propertyName1} can be converted to {@link List}{@code <String>}
     * (or {@link java.util.Set}{@code <String>} or {@link java.util.SortedSet}{@code <String>} if possible) instance automatically.
     *
     * <p>
     * Map examples:
     * <code>
     * propertyName2=key1=value1,key2=value2,key3=value3
     * </code>
     * For such cases {@code propertyName2} can be converted to {@link java.util.Map}{@code <String, String>} instance automatically.
     */
    public static final Character VALUES_DELIMITER = ',';

    /**
     * Defines a character that used as key-value map entry delimiter.
     *
     * <p>
     * For example:
     * <code>
     * propertyName=key1=value1,key2=value2,key3=value3
     * </code>
     * For such cases {@code propertyName} can be converted to {@link java.util.Map}{@code <String, String>} instance automatically.
     */
    public static final Character KEY_VALUE_ENTRY_DELIMITER = '=';

    /**
     * Default name for config file or class path resource without extension.
     *
     * <p>
     * This name used by {@link ConfigSource#RXMICRO_CLASS_PATH_RESOURCE} or
     * {@link ConfigSource#RXMICRO_FILE_AT_THE_HOME_DIR} or
     * {@link ConfigSource#RXMICRO_FILE_AT_THE_RXMICRO_CONFIG_DIR} or
     * {@link ConfigSource#RXMICRO_FILE_AT_THE_CURRENT_DIR}.
     */
    public static final String RX_MICRO_CONFIG_FILE_NAME = "rxmicro";

    /**
     * Allows defining the prefix name that will be used for all environment variable source for configs.
     *
     * <p>
     * The RxMicro framework allows using the environment variables for configuring the config instances.
     * By default the <code>${namespace}.${propertyName}=${propertyValue}</code> format is used.
     *
     * <p>
     * But some tools add the additional restrictions for environment variables:
     *
     * <p>
     * Environment variable names used by the utilities in the Shell and Utilities volume of IEEE Std 1003.1-2001 consist solely of
     * uppercase letters, digits, and the '_' (underscore) from the characters defined in Portable Character Set and do not begin with
     * a digit. Other characters may be permitted by an implementation; applications shall tolerate the presence of such names.
     *
     * <p>
     * So the RxMicro framework must support both:
     * <ul>
     *     <li>{@code export TEST_CONFIG_CLASS_PROPERTY_NAME=value}</li>
     *     <li>{@code export test-config-class.propertyName=value}</li>
     * </ul>
     * environment variable names for the {@code propertyName} that defined at the {@code TestConfigClassConfig} class.
     *
     * <p>
     * If {@value #RX_MICRO_CONFIG_ENVIRONMENT_VARIABLE_PREFIX} is defined the RxMicro framework will expect the following
     * environment variable name for the {@code propertyName} that defined at the {@code TestConfigClassConfig} class:
     * {@code export ${RX_MICRO_CONFIG_ENVIRONMENT_VARIABLE_PREFIX}_TEST_CONFIG_CLASS_PROPERTY_NAME=value}
     */
    public static final String RX_MICRO_CONFIG_ENVIRONMENT_VARIABLE_PREFIX = "RX_MICRO_CONFIG_ENVIRONMENT_VARIABLE_PREFIX";

    /**
     * Default name for config directory.
     *
     * <p>
     * This name used by {@link ConfigSource#SEPARATE_FILE_AT_THE_RXMICRO_CONFIG_DIR} or
     * {@link ConfigSource#RXMICRO_FILE_AT_THE_RXMICRO_CONFIG_DIR}.
     */
    public static final String RX_MICRO_CONFIG_DIRECTORY_NAME = ".rxmicro";

    private static final String AS_MAP_CONFIG_NAME = AsMapConfig.class.getSimpleName();

    private static final String CONFIG_NAME = Config.class.getSimpleName();

    /**
     * Defines the default namespace for the config class.
     *
     * @param configClass the config class
     * @return the default namespace
     */
    public static String getDefaultNameSpace(final Class<? extends Config> configClass) {
        return getDefaultNameSpace(configClass.getSimpleName());
    }

    /**
     * Defines the default namespace for config simple class name (without package).
     *
     * @param configSimpleClassName the config simple class name
     * @return the default namespace
     */
    public static String getDefaultNameSpace(final String configSimpleClassName) {
        final List<String> words = splitByCamelCase(getValidConfigSimpleClassName(configSimpleClassName));
        return words.stream()
                .map(String::toLowerCase)
                .collect(joining("-"));
    }

    private static String getValidConfigSimpleClassName(final String name) {
        if (name.endsWith(AS_MAP_CONFIG_NAME)) {
            return name.substring(0, name.length() - AS_MAP_CONFIG_NAME.length());
        } else if (name.endsWith(CONFIG_NAME)) {
            return name.substring(0, name.length() - CONFIG_NAME.length());
        } else {
            return name;
        }
    }

    private final String namespace;

    public Config(final String namespace) {
        this.namespace = require(namespace);
    }

    /**
     * Returns the namespace for the current config instance.
     *
     * @return the namespace for the current config instance.
     */
    public final String getNameSpace() {
        return namespace;
    }

    /**
     * Validates the config instance state after injection all properties using some custom rules, i.e. rules that can't
     * be described via {@code io.rxmicro.validation.constraint.*} constraints.
     */
    protected void validateUsingCustomRules() {
        // child classes can add custom validation logic.
    }

    /**
     * Ensures that the provided value is valid for the property represented by the provided name for the current config instance.
     *
     * @param propertyName the provided name for a property for the current config instance.
     * @param value        the provided value.
     * @param <T>          value type.
     * @return the provided value.
     */
    protected final <T> T ensureValidProperty(final String propertyName, final T value) {
        ConfigPropertyValidators.validateProperty(this, propertyName, value);
        return value;
    }

    /**
     * Ensures that the provided value is valid for the property calculated automatically using current stack trace for the current config instance.
     *
     * @param value the provided value.
     * @param <T>   value type.
     * @return the provided value.
     */
    protected final <T> T ensureValid(final T value) {
        return ensureValidProperty(PropertyNameAutoResolver.resolve(), value);
    }

    static final class PropertyNameAutoResolver {

        static final int SETTER_PREFIX_LENGTH = "set".length();

        static String resolve() {
            final StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            return unCapitalize(stackTrace[3].getMethodName().substring(SETTER_PREFIX_LENGTH));
        }
    }
}
