/*
 * Copyright 2019 http://rxmicro.io
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
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public enum ConfigLoadSource {

    DEFAULT_CONFIG_VALUES,

    RXMICRO_CLASS_PATH_RESOURCE,

    SEPARATE_CLASS_PATH_RESOURCE,

    ENVIRONMENT_VARIABLES,

    RXMICRO_FILE_AT_THE_HOME_DIR,

    RXMICRO_FILE_AT_THE_CURRENT_DIR,

    SEPARATE_FILE_AT_THE_HOME_DIR,

    SEPARATE_FILE_AT_THE_CURRENT_DIR,

    JAVA_SYSTEM_PROPERTIES
}
