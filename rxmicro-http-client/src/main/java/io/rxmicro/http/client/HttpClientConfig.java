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

package io.rxmicro.http.client;

import io.rxmicro.http.HttpConfig;
import io.rxmicro.http.ProtocolSchema;

import java.time.Duration;

import static io.rxmicro.common.util.Requires.require;
import static io.rxmicro.common.util.Strings.hideSecureInfo;
import static java.time.Duration.ofSeconds;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public class HttpClientConfig extends HttpConfig {

    public static final int DEFAULT_HTTP_CLIENT_TIMEOUT_VALUE_IN_SECONDS = 7;

    private String accessKey;

    private boolean followRedirects = true;

    private Duration requestTimeout = ofSeconds(DEFAULT_HTTP_CLIENT_TIMEOUT_VALUE_IN_SECONDS);

    public HttpClientConfig() {
        setHost("localhost");
    }

    public String getAccessKey() {
        return accessKey;
    }

    public HttpClientConfig setAccessKey(final String accessKey) {
        this.accessKey = require(accessKey);
        return this;
    }

    public boolean isFollowRedirects() {
        return followRedirects;
    }

    public HttpClientConfig setFollowRedirects(final boolean followRedirects) {
        this.followRedirects = followRedirects;
        return this;
    }

    public Duration getRequestTimeout() {
        return requestTimeout;
    }

    /**
     * Duration.ZERO means infinite timeout
     */
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
                ", accessKey=" + hideSecureInfo(getAccessKey()) +
                ", followRedirects=" + followRedirects + '}' +
                ", requestTimeout=" + requestTimeout + '}';

    }
}
