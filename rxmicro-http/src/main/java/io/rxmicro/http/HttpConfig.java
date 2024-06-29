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

import io.rxmicro.common.CommonConstants;
import io.rxmicro.common.meta.BuilderMethod;
import io.rxmicro.config.Config;
import io.rxmicro.http.internal.HttpConfigExtractor;
import io.rxmicro.validation.constraint.HostName;
import io.rxmicro.validation.constraint.Port;

import static io.rxmicro.common.util.Formats.format;

/**
 * Allows configuring common HTTP options.
 *
 * @author nedis
 * @since 0.1
 */
@SuppressWarnings("UnusedReturnValue")
public class HttpConfig extends Config {

    private ProtocolSchema schema = ProtocolSchema.HTTP;

    @HostName
    private String host = CommonConstants.LOCALHOST;

    @Port
    private Integer port = ProtocolSchema.HTTP.getPort();

    /**
     * This is basic class designed for extension only.
     */
    protected HttpConfig(final String namespace) {
        super(namespace);
    }

    /**
     * For setting properties from child classes ignoring validation, i.e. ignoring correspond {@link #ensureValid(Object)} invocations.
     */
    protected HttpConfig(final String namespace,
                         final ProtocolSchema schema,
                         final String host,
                         final Integer port) {
        super(namespace);
        this.schema = schema;
        this.host = host;
        this.port = port;
    }

    /**
     * Returns the protocol schema.
     *
     * @return the protocol schema
     */
    public ProtocolSchema getSchema() {
        return schema;
    }

    /**
     * Sets the protocol schema.
     *
     * @param schema the protocol schema
     * @return the reference to this {@link HttpConfig} instance
     */
    @BuilderMethod
    public HttpConfig setSchema(final ProtocolSchema schema) {
        this.schema = ensureValid(schema);
        return this;
    }

    /**
     * Returns the host name.
     *
     * @return the host name
     */
    public String getHost() {
        return host;
    }

    /**
     * Sets the host name.
     *
     * @param host the host name
     * @return the reference to this {@link HttpConfig} instance
     */
    @BuilderMethod
    public HttpConfig setHost(final String host) {
        this.host = ensureValid(host);
        return this;
    }

    /**
     * Returns the server port.
     *
     * @return the server port
     */
    public Integer getPort() {
        return port;
    }

    /**
     * Sets the port.
     *
     * @param port the server port
     * @return the reference to this {@link HttpConfig} instance
     */
    @BuilderMethod
    public HttpConfig setPort(final Integer port) {
        this.port = ensureValid(port);
        return this;
    }

    /**
     * Returns the connection string built from schema, host and port parameters.
     *
     * @return the connection string
     */
    public String getConnectionString() {
        if (port == schema.getPort()) {
            return format("?://?", schema.getSchema(), host);
        } else {
            return format("?://?:?", schema.getSchema(), host, port);
        }
    }

    /**
     * Sets protocol schema, host and port.
     *
     * <p>
     * Example of valid connectionString:
     * <ul>
     *     <li>rxmicro.io</li>
     *     <li>https://rxmicro.io</li>
     *     <li>rxmicro.io:8080</li>
     *     <li>http://rxmicro.io:8080</li>
     *     <li>https://rxmicro.io:8443</li>
     * </ul>
     *
     * @param connectionString the connection string. See description above.
     * @return the reference to this {@link HttpConfig} instance
     */
    @BuilderMethod
    public HttpConfig setConnectionString(final String connectionString) {
        new HttpConfigExtractor().extract(connectionString, this);
        return this;
    }

    @Override
    public String toString() {
        return "HttpConfig {connectionString='" + getConnectionString() + "'}";
    }
}
