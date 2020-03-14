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

package io.rxmicro.http;

import io.rxmicro.config.Config;
import io.rxmicro.http.internal.HttpConfigExtractor;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.common.util.Requires.require;
import static io.rxmicro.config.Networks.validatePort;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public abstract class HttpConfig extends Config {

    private ProtocolSchema schema = ProtocolSchema.http;

    private String host;

    private int port = 8080;

    public ProtocolSchema getSchema() {
        return schema;
    }

    /**
     * Sets the protocol schema
     *
     * @param schema protocol schema
     * @return A reference to this {@code HttpConfig}
     */
    public HttpConfig setSchema(final ProtocolSchema schema) {
        this.schema = require(schema);
        return this;
    }

    public String getHost() {
        return host;
    }

    /**
     * Sets the host name
     *
     * @param host host name
     * @return A reference to this {@code HttpConfig}
     */
    public HttpConfig setHost(final String host) {
        this.host = require(host);
        return this;
    }

    public int getPort() {
        return port;
    }

    /**
     * Sets the port
     *
     * @param port server port
     * @return A reference to this {@code HttpConfig}
     */
    public HttpConfig setPort(final int port) {
        this.port = validatePort(port);
        return this;
    }

    public String getConnectionString() {
        if (port == schema.getPort()) {
            return format("?://?", schema.getSchema(), host);
        } else {
            return format("?://?:?", schema.getSchema(), host, port);
        }
    }

    /**
     * Sets protocol schema, host and port
     * <p>
     * Example of valid connectionString:
     * <p>
     * rxmicro.io
     * http://rxmicro.io
     * https://rxmicro.io
     * rxmicro.io:8080
     * http://rxmicro.io:8080
     * https://rxmicro.io:8443
     *
     * @param connectionString connection string. See description above.
     * @return A reference to this {@code HttpConfig}
     */
    public HttpConfig setConnectionString(final String connectionString) {
        new HttpConfigExtractor().extract(connectionString, this);
        return this;
    }

    @Override
    public String toString() {
        return "HttpConfig {connectionString=" + getConnectionString() + '}';
    }
}
