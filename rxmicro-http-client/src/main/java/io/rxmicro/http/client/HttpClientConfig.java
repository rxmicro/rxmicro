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

package io.rxmicro.http.client;

import io.rxmicro.common.meta.BuilderMethod;
import io.rxmicro.http.HttpConfig;
import io.rxmicro.http.ProtocolSchema;

import java.time.Duration;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.common.util.Requires.require;
import static io.rxmicro.config.Secrets.hideSecretInfo;
import static java.time.Duration.ofSeconds;

/**
 * Allows configuring HTTP client options.
 *
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
@SuppressWarnings("UnusedReturnValue")
public class HttpClientConfig extends HttpConfig {

    public static final int DEFAULT_HTTP_CLIENT_TIMEOUT_VALUE_IN_SECONDS = 7;

    private String accessKey;

    private boolean followRedirects = true;

    private Duration requestTimeout = ofSeconds(DEFAULT_HTTP_CLIENT_TIMEOUT_VALUE_IN_SECONDS);

    /**
     * Creates a HTTP client config instance with default settings
     */
    public HttpClientConfig() {
        setHost("localhost");
    }

    /**
     * Returns the set access key or {@code null} if access key is not configured
     *
     * @return the set access key or {@code null} if access key is not configured
     */
    public String getAccessKey() {
        return accessKey;
    }

    /**
     * Sets the access key that can be used for authentication on the server side
     *
     * @param accessKey the access key
     * @return the reference to this {@link HttpClientConfig} instance
     */
    @BuilderMethod
    public HttpClientConfig setAccessKey(final String accessKey) {
        this.accessKey = require(accessKey);
        return this;
    }

    /**
     * Returns {@code true} if current HTTP client must follow redirects
     *
     * @return {@code true} if current HTTP client must follow redirects
     */
    public boolean isFollowRedirects() {
        return followRedirects;
    }

    /**
     * Sets that current HTTP client must follow redirects or not
     *
     * @param followRedirects follow redirects or not
     * @return the reference to this {@link HttpClientConfig} instance
     */
    @BuilderMethod
    public HttpClientConfig setFollowRedirects(final boolean followRedirects) {
        this.followRedirects = followRedirects;
        return this;
    }

    /**
     * Returns the request timeout
     *
     * @return the request timeout
     */
    public Duration getRequestTimeout() {
        return requestTimeout;
    }

    /**
     * Sets the request timeout.
     * <p>
     * {@link Duration#ZERO} means the infinite timeout
     *
     * @param requestTimeout the request timeout
     * @return the reference to this {@link HttpClientConfig} instance
     */
    @BuilderMethod
    public HttpClientConfig setRequestTimeout(final Duration requestTimeout) {
        this.requestTimeout = require(requestTimeout);
        return this;
    }

    @Override
    public HttpClientConfig setSchema(final ProtocolSchema schema) {
        return (HttpClientConfig) super.setSchema(schema);
    }

    @Override
    public HttpClientConfig setHost(final String host) {
        return (HttpClientConfig) super.setHost(host);
    }

    @Override
    public HttpClientConfig setPort(final int port) {
        return (HttpClientConfig) super.setPort(port);
    }

    @Override
    public HttpClientConfig setConnectionString(final String connectionString) {
        return (HttpClientConfig) super.setConnectionString(connectionString);
    }

    @Override
    public String toString() {
        return "HttpClientConfig {connectionString=" + getConnectionString() +
                ", accessKey=" + hideSecretInfo(getAccessKey()) +
                ", followRedirects=" + followRedirects + '}' +
                ", requestTimeout=" + format(requestTimeout) + '}';

    }
}
