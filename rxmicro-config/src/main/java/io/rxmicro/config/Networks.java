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
 * Network utils.
 *
 * @author nedis
 * @since 0.1
 */
public final class Networks {

    /**
     * Min possible port value.
     */
    public static final int MIN_PORT = 0;

    /**
     * Max possible port value.
     */
    public static final int MAX_PORT = 65_535;

    /**
     * Validates that provided integer value is valid port number.
     *
     * @param port the port value to verify
     * @return the provided port value
     * @throws ConfigException if provided integer value is not valid port number
     */
    public static int validatePort(final int port) {
        if (port <= MIN_PORT || port >= MAX_PORT) {
            throw new ConfigException(
                    "Invalid port value: ? (Must be ? < ? < ?)",
                    port, MIN_PORT, port, MAX_PORT);
        }
        return port;
    }

    private Networks() {
    }
}
