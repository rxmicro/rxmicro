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

package io.rxmicro.test.local;

import io.rxmicro.common.meta.BuilderMethod;
import io.rxmicro.config.SingletonConfigClass;
import io.rxmicro.http.ProtocolSchema;
import io.rxmicro.rest.BaseUrlPath;
import io.rxmicro.rest.Version;
import io.rxmicro.rest.client.RestClientConfig;

import java.time.Duration;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.config.Secrets.hideSecretInfo;
import static io.rxmicro.http.ProtocolSchema.HTTP;

/**
 * @author nedis
 * @since 0.1
 */
@SuppressWarnings("UnusedReturnValue")
@SingletonConfigClass
public final class BlockingHttpClientConfig extends RestClientConfig {

    private static final int DEFAULT_HTTP_PORT = 8080;

    private String baseUrlPath = "";

    private BaseUrlPath.Position baseUrlPosition = BaseUrlPath.Position.AFTER_VERSION;

    private Version.Strategy versionStrategy = Version.Strategy.URL_PATH;

    private String versionValue = "";

    public Version.Strategy getVersionStrategy() {
        return versionStrategy;
    }

    public BlockingHttpClientConfig() {
        setSchema(HTTP);
        setHost("localhost");
        setPort(DEFAULT_HTTP_PORT);
    }

    public String getBaseUrlPath() {
        return baseUrlPath;
    }

    @BuilderMethod
    public BlockingHttpClientConfig setBaseUrlPath(final String baseUrlPath) {
        this.baseUrlPath = baseUrlPath;
        return this;
    }

    public BaseUrlPath.Position getBaseUrlPosition() {
        return baseUrlPosition;
    }

    @BuilderMethod
    public BlockingHttpClientConfig setBaseUrlPosition(final BaseUrlPath.Position baseUrlPosition) {
        this.baseUrlPosition = baseUrlPosition;
        return this;
    }

    @BuilderMethod
    public BlockingHttpClientConfig setVersionStrategy(final Version.Strategy versionStrategy) {
        this.versionStrategy = versionStrategy;
        return this;
    }

    public String getVersionValue() {
        return versionValue;
    }

    @BuilderMethod
    public BlockingHttpClientConfig setVersionValue(final String versionValue) {
        this.versionValue = versionValue;
        return this;
    }

    @Override
    public BlockingHttpClientConfig setAccessKey(final String accessKey) {
        return (BlockingHttpClientConfig) super.setAccessKey(accessKey);
    }

    @Override
    public BlockingHttpClientConfig setFollowRedirects(final boolean followRedirects) {
        return (BlockingHttpClientConfig) super.setFollowRedirects(followRedirects);
    }

    @Override
    public BlockingHttpClientConfig setRequestTimeout(final Duration requestTimeout) {
        return (BlockingHttpClientConfig) super.setRequestTimeout(requestTimeout);
    }

    @Override
    public BlockingHttpClientConfig setSchema(final ProtocolSchema schema) {
        return (BlockingHttpClientConfig) super.setSchema(schema);
    }

    @Override
    public BlockingHttpClientConfig setHost(final String host) {
        return (BlockingHttpClientConfig) super.setHost(host);
    }

    @Override
    public BlockingHttpClientConfig setPort(final int port) {
        return (BlockingHttpClientConfig) super.setPort(port);
    }

    @Override
    public BlockingHttpClientConfig setConnectionString(final String connectionString) {
        return (BlockingHttpClientConfig) super.setConnectionString(connectionString);
    }

    @Override
    public String toString() {
        return "BlockingHttpClientConfig{" +
                "connectionString=" + getConnectionString() +
                ", accessKey='" + hideSecretInfo(getAccessKey()) + '\'' +
                ", followRedirects=" + isFollowRedirects() +
                ", connectTimeout=" + format(getConnectTimeout()) +
                ", requestTimeout=" + format(getRequestTimeout()) +
                ", enableAdditionalValidations=" + isEnableAdditionalValidations() +
                ", evictionInterval=" + getEvictionInterval() +
                ", maxConnections=" + getMaxConnections() +
                ", pendingAcquireMaxCount=" + getPendingAcquireMaxCount() +
                ", pendingAcquireTimeout=" + getPendingAcquireTimeout() +
                ", maxIdleTime=" + getMaxIdleTime() +
                ", maxLifeTime=" + getMaxLifeTime() +
                ", leasingStrategy=" + getLeasingStrategy() +
                ", baseUrlPath='" + baseUrlPath + '\'' +
                ", baseUrlPosition=" + baseUrlPosition +
                ", versionStrategy=" + versionStrategy +
                ", versionValue='" + versionValue + '\'' +
                '}';
    }
}
