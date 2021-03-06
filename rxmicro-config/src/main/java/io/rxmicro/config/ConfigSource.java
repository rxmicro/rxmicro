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

/**
 * Defines sources for configuration.
 *
 * <p>
 * The RxMicro framework supports the following source of configurations:
 * <ul>
 *     <li>Properties files</li>
 *     <li>Classpath properties resources</li>
 *     <li>Environment variables</li>
 *     <li>Java system properties</li>
 *     <li>Command line arguments</li>
 *     <li>Java configuration classes</li>
 *     <li>Java annotations</li>
 * </ul>
 *
 * @author nedis
 * @see Config
 * @see Configs#getConfig(String, Class)
 * @see Configs#getConfig(Class)
 * @since 0.1
 */
public enum ConfigSource {

    /**
     * Hardcoded config using annotations.
     *
     * @see DefaultConfigValue
     */
    DEFAULT_CONFIG_VALUES,

    /**
     * Config from {@code classpath:rxmicro.properties}.
     *
     * @see Config#RX_MICRO_CONFIG_FILE_NAME
     */
    RXMICRO_CLASS_PATH_RESOURCE,

    /**
     * Config from {@code classpath:${name_space}.properties}.
     */
    SEPARATE_CLASS_PATH_RESOURCE,

    /**
     * Config from env variables: {@code export name_space.property=value}.
     */
    ENVIRONMENT_VARIABLES,

    /**
     * Config from file: {@code ~/rxmicro.properties}.
     *
     * @see Config#RX_MICRO_CONFIG_FILE_NAME
     */
    RXMICRO_FILE_AT_THE_HOME_DIR,

    /**
     * Config from file: {@code ~/.rxmicro/rxmicro.properties}.
     *
     * @see Config#RX_MICRO_CONFIG_DIRECTORY_NAME
     * @see Config#RX_MICRO_CONFIG_FILE_NAME
     */
    RXMICRO_FILE_AT_THE_RXMICRO_CONFIG_DIR,

    /**
     * Config from file: {@code ./rxmicro.properties}.
     *
     * @see Config#RX_MICRO_CONFIG_FILE_NAME
     */
    RXMICRO_FILE_AT_THE_CURRENT_DIR,

    /**
     * Config from file: {@code ~/${name_space}.properties}.
     */
    SEPARATE_FILE_AT_THE_HOME_DIR,

    /**
     * Config from file: {@code ~/.rxmicro/${name_space}.properties}.
     *
     * @see Config#RX_MICRO_CONFIG_DIRECTORY_NAME
     */
    SEPARATE_FILE_AT_THE_RXMICRO_CONFIG_DIR,

    /**
     * Config from file: {@code ./${name_space}.properties}.
     */
    SEPARATE_FILE_AT_THE_CURRENT_DIR,

    /**
     * Config from Java system properties: {@code java ... -Dname_space.property=value ...}.
     */
    JAVA_SYSTEM_PROPERTIES
}
