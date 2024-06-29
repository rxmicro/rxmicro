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
import io.rxmicro.http.ProtocolSchema;
import io.rxmicro.validation.constraint.Min;
import io.rxmicro.validation.constraint.Nullable;

import java.time.Duration;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.config.Secrets.hideSecretInfo;

/**
 * Allows configuring a REST client options.
 *
 * @author nedis
 * @since 0.7
 */
public class RestClientConfig extends HttpClientConnectionPoolConfig {

    /**
     * Default connect timeout.
     */
    public static final Duration DEFAULT_CONNECT_TIMEOUT = Duration.ofSeconds(3);

    /**
     * Default request timeout.
     */
    public static final Duration DEFAULT_REQUEST_TIMEOUT = Duration.ofSeconds(7);

    @Nullable
    private String accessKey;

    private boolean followRedirects = true;

    private boolean enableAdditionalValidations;

    @Min("PT0S")
    private Duration connectTimeout = DEFAULT_CONNECT_TIMEOUT;

    @Min("PT0S")
    private Duration requestTimeout = DEFAULT_REQUEST_TIMEOUT;

    /**
     * Creates a rest client config instance with default settings.
     */
    public RestClientConfig(final String namespace) {
        super(namespace);
    }

    /**
     * For setting properties from child classes ignoring validation, i.e. ignoring correspond {@link #ensureValid(Object)} invocations.
     */
    protected RestClientConfig(final String namespace,
                               final ProtocolSchema schema,
                               final String host,
                               final Integer port) {
        super(namespace, schema, host, port);
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
        this.accessKey = ensureValid(accessKey);
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
        this.connectTimeout = ensureValid(connectTimeout);
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
        this.requestTimeout = ensureValid(requestTimeout);
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
    public RestClientConfig setPort(final Integer port) {
        return (RestClientConfig) super.setPort(port);
    }

    @Override
    public RestClientConfig setConnectionString(final String connectionString) {
        return (RestClientConfig) super.setConnectionString(connectionString);
    }

    @Override
    public RestClientConfig setEvictionInterval(final Duration evictionInterval) {
        return (RestClientConfig) super.setEvictionInterval(evictionInterval);
    }

    @Override
    public RestClientConfig setMaxConnections(final int maxConnections) {
        return (RestClientConfig) super.setMaxConnections(maxConnections);
    }

    @Override
    public RestClientConfig setPendingAcquireMaxCount(final Integer pendingAcquireMaxCount) {
        return (RestClientConfig) super.setPendingAcquireMaxCount(pendingAcquireMaxCount);
    }

    @Override
    public RestClientConfig setPendingAcquireTimeout(final Duration pendingAcquireTimeout) {
        return (RestClientConfig) super.setPendingAcquireTimeout(pendingAcquireTimeout);
    }

    @Override
    public RestClientConfig setMaxIdleTime(final Duration maxIdleTime) {
        return (RestClientConfig) super.setMaxIdleTime(maxIdleTime);
    }

    @Override
    public RestClientConfig setMaxLifeTime(final Duration maxLifeTime) {
        return (RestClientConfig) super.setMaxLifeTime(maxLifeTime);
    }

    @Override
    public RestClientConfig setLeasingStrategy(final LeasingStrategy leasingStrategy) {
        return (RestClientConfig) super.setLeasingStrategy(leasingStrategy);
    }

    @Override
    public String toString() {
        return "RestClientConfig{connectionString=" + getConnectionString() +
                ", accessKey='" + hideSecretInfo(accessKey) + '\'' +
                ", followRedirects=" + followRedirects +
                ", connectTimeout=" + format(connectTimeout) +
                ", requestTimeout=" + format(requestTimeout) +
                ", enableAdditionalValidations=" + enableAdditionalValidations +
                ", evictionInterval=" + getEvictionInterval() +
                ", maxConnections=" + getMaxConnections() +
                ", pendingAcquireMaxCount=" + getPendingAcquireMaxCount() +
                ", pendingAcquireTimeout=" + getPendingAcquireTimeout() +
                ", maxIdleTime=" + getMaxIdleTime() +
                ", maxLifeTime=" + getMaxLifeTime() +
                ", leasingStrategy=" + getLeasingStrategy() +
                '}';
    }
}
