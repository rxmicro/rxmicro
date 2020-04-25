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

import io.rxmicro.http.ProtocolSchema;
import io.rxmicro.http.client.HttpClientConfig;
import io.rxmicro.rest.Version;

import java.time.Duration;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
@SuppressWarnings("UnusedReturnValue")
public final class BlockingHttpClientConfig extends HttpClientConfig {

    private Version.Strategy versionStrategy = Version.Strategy.URL_PATH;

    private String versionValue = "";

    public Version.Strategy getVersionStrategy() {
        return versionStrategy;
    }

    public BlockingHttpClientConfig setVersionStrategy(final Version.Strategy versionStrategy) {
        this.versionStrategy = versionStrategy;
        return this;
    }

    public String getVersionValue() {
        return versionValue;
    }

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
}
