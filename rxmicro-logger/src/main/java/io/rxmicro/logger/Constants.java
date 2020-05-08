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

package io.rxmicro.logger;

/**
 * {@link Logger} constants
 *
 * @author nedis
 * @since 0.1
 * @see LoggerFactory
 * @see Logger
 */
public final class Constants {

    /**
     * The prefix for environment variable or java system properties that
     * must be interpret by the RxMicro framework as configuration for logger module
     */
    public static final String LOGGER_VARIABLE_PREFIX = "logger";

    private Constants() {
    }
}
