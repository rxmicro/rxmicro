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

package io.rxmicro.http;

import java.util.Locale;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public enum ProtocolSchema {

    HTTP(80),

    HTTPS(443);

    private final int port;

    private final String schema;

    ProtocolSchema(final int port) {
        this.port = port;
        this.schema = name().toLowerCase(Locale.ENGLISH);
    }

    public String getSchema() {
        return schema;
    }

    public int getPort() {
        return port;
    }
}
