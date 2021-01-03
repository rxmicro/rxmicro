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

package io.rxmicro.rest.client;

import io.rxmicro.common.meta.BuilderMethod;
import io.rxmicro.http.HttpConfig;
import io.rxmicro.http.ProtocolSchema;

import java.time.Duration;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.common.util.Requires.require;
import static io.rxmicro.config.Secrets.hideSecretInfo;
import static io.rxmicro.http.ProtocolSchema.HTTP;

/**
 * Allows configuring a REST client options.
 *
 * @author nedis
 * @since 0.7
 */
public class RestClientConfig extends HttpConfig {

    /**
     * Default HTTP port.
     */
    public static final int DEFAULT_HTTP_PORT = 80;

    /**
     * Default connect timeout.
     */
    public static final Duration DEFAULT_CONNECT_TIMEOUT = Duration.ofSeconds(3);

    /**
     * Default request timeout.
     */
    public static final Duration DEFAULT_REQUEST_TIMEOUT = Duration.ofSeconds(7);

    private String accessKey;

    private boolean followRedirects;

    private Duration connectTimeout;

    private Duration requestTimeout;

    private boolean enableAdditionalValidations;

    /**
     * Creates a rest client config instance with default settings.
     */
    public RestClientConfig() {
        super.setSchema(HTTP);
        super.setHost("localhost");
        super.setPort(DEFAULT_HTTP_PORT);
        this.followRedirects = true;
        this.connectTimeout = DEFAULT_CONNECT_TIMEOUT;
        this.requestTimeout = DEFAULT_REQUEST_TIMEOUT;
    }

    /**
     * Returns the set access key or {@code null} if access key is not configured.
     *
     * @return the set access key or {@code null} if access key is not configured
     */
    public String getAccessKey() {
        return accessKey;
    }

    /**
     * Sets the access key that can be used for authentication on the server side.
     *
     * @param accessKey the access key
     * @return the reference to this {@link RestClientConfig} instance
     */
    @BuilderMethod
    public RestClientConfig setAccessKey(final String accessKey) {
        this.accessKey = require(accessKey);
        return this;
    }

    /**
     * Returns {@code true} if current HTTP client must follow redirects.
     *
     * @return {@code true} if current HTTP client must follow redirects
     */
    public boolean isFollowRedirects() {
        return followRedirects;
    }

    /**
     * Sets that current HTTP client must follow redirects or not.
     *
     * @param followRedirects follow redirects or not
     * @return the reference to this {@link RestClientConfig} instance
     */
    @BuilderMethod
    public RestClientConfig setFollowRedirects(final boolean followRedirects) {
        this.followRedirects = followRedirects;
        return this;
    }

    /**
     * Returns the connect timeout.
     *
     * @return the connect timeout.
     */
    public Duration getConnectTimeout() {
        return connectTimeout;
    }

    /**
     * Sets the connect timeout.
     *
     * <p>
     * {@link Duration#ZERO} means the infinite timeout
     *
     * @param connectTimeout the connect timeout.
     * @return the reference to this {@link RestClientConfig} instance
     */
    @BuilderMethod
    public RestClientConfig setConnectTimeout(final Duration connectTimeout) {
        this.connectTimeout = connectTimeout;
        return this;
    }

    /**
     * Returns the request timeout.
     *
     * @return the request timeout
     */
    public Duration getRequestTimeout() {
        return requestTimeout;
    }

    /**
     * Sets the request timeout.
     *
     * <p>
     * {@link Duration#ZERO} means the infinite timeout
     *
     * @param requestTimeout the request timeout
     * @return the reference to this {@link RestClientConfig} instance
     */
    @BuilderMethod
    public RestClientConfig setRequestTimeout(final Duration requestTimeout) {
        this.requestTimeout = require(requestTimeout);
        return this;
    }

    /**
     * Returns {@code true} if additional validations are enabled.
     *
     * @return {@code true} if additional validations are enabled
     */
    public boolean isEnableAdditionalValidations() {
        return enableAdditionalValidations;
    }

    /**
     * Sets {@code true} if additional validations must be activated.
     *
     * @param enableAdditionalValidations enable additional validations or not
     * @return the reference to this {@link RestClientConfig} instance
     */
    @BuilderMethod
    public RestClientConfig setEnableAdditionalValidations(final boolean enableAdditionalValidations) {
        this.enableAdditionalValidations = enableAdditionalValidations;
        return this;
    }

    @Override
    public RestClientConfig setSchema(final ProtocolSchema schema) {
        return (RestClientConfig) super.setSchema(schema);
    }

    @Override
    public RestClientConfig setHost(final String host) {
        return (RestClientConfig) super.setHost(host);
    }

    @Override
    public RestClientConfig setPort(final int port) {
        return (RestClientConfig) super.setPort(port);
    }

    @Override
    public RestClientConfig setConnectionString(final String connectionString) {
        return (RestClientConfig) super.setConnectionString(connectionString);
    }

    @Override
    public String toString() {
        return "RestClientConfig{connectionString=" + getConnectionString() +
                ", accessKey='" + hideSecretInfo(accessKey) + '\'' +
                ", followRedirects=" + followRedirects +
                ", connectTimeout=" + format(connectTimeout) +
                ", requestTimeout=" + format(requestTimeout) +
                ", enableAdditionalValidations=" + enableAdditionalValidations +
                '}';
    }
}
