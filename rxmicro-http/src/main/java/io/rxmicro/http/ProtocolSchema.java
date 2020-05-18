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

package io.rxmicro.http;

import java.util.Locale;

/**
 * Represents the supported protocols
 *
 * @author nedis
 * @since 0.1
 */
public enum ProtocolSchema {

    /**
     * HTTP protocol
     */
    HTTP(80),

    /**
     * HTTPS protocol
     */
    HTTPS(443);

    private final int port;

    private final String schema;

    ProtocolSchema(final int port) {
        this.port = port;
        this.schema = name().toLowerCase(Locale.ENGLISH);
    }

    /**
     * Returns the schema name
     *
     * @return the schema name
     */
    public String getSchema() {
        return schema;
    }

    /**
     * Returns the default port
     *
     * @return the default port
     */
    public int getPort() {
        return port;
    }
}
