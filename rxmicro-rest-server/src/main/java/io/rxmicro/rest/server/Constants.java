/*
 * Copyright (c) 2020. http://rxmicro.io
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

package io.rxmicro.rest.server;

/**
 * Rest server constants: environment variables or Java system properties
 *
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.3
 */
public final class Constants {

    /**
     * This is an environment variable or Java system property.
     *
     * If this variable is set and rest server is started,
     * the RxMicro framework will print the short info about the current runtime: available processor cores and memory usage.
     *
     * Format: boolean
     * Example: RX_MICRO_PRINT_RUNTIME=true
     */
    public static final String RX_MICRO_PRINT_RUNTIME = "RX_MICRO_PRINT_RUNTIME";

    private Constants(){
    }
}
