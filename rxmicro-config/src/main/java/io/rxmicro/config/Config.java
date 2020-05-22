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

import java.util.List;

import static io.rxmicro.common.util.Strings.splitByCamelCase;
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
     * Default name for config file or class path resource without extension.
     *
     * <p>
     * This name used by {@link ConfigSource#RXMICRO_CLASS_PATH_RESOURCE} or
     *                   {@link ConfigSource#RXMICRO_FILE_AT_THE_HOME_DIR} or
     *                   {@link ConfigSource#RXMICRO_FILE_AT_THE_RXMICRO_CONFIG_DIR} or
     *                   {@link ConfigSource#RXMICRO_FILE_AT_THE_CURRENT_DIR}.
     */
    public static final String RX_MICRO_CONFIG_FILE_NAME = "rxmicro";

    /**
     * Default name for config directory.
     *
     * <p>
     * This name used by {@link ConfigSource#SEPARATE_FILE_AT_THE_RXMICRO_CONFIG_DIR} or
     *                   {@link ConfigSource#RXMICRO_FILE_AT_THE_RXMICRO_CONFIG_DIR}.
     */
    public static final String RX_MICRO_CONFIG_DIRECTORY_NAME = ".rxmicro";

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
        final List<String> words = splitByCamelCase(configSimpleClassName);
        return words.stream()
                .map(String::toLowerCase)
                .filter(w -> !"config".equals(w))
                .collect(joining("-"));
    }

    /**
     * Returns the default namespace for config instance.
     *
     * @return the default namespace for config instance
     */
    public String getNameSpace() {
        return getDefaultNameSpace(getClass());
    }
}
