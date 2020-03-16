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

package io.rxmicro.examples.rest.client.expressions;

import io.rxmicro.http.ProtocolSchema;
import io.rxmicro.http.client.HttpClientConfig;

// tag::content[]
public final class CustomHttpClientConfig extends HttpClientConfig {

    private boolean useProxy = true;

    private Mode mode = Mode.PRODUCTION;

    public boolean isUseProxy() {
        return useProxy;
    }

    public CustomHttpClientConfig setUseProxy(final boolean useProxy) {
        this.useProxy = useProxy;
        return this;
    }

    public Mode getMode() {
        return mode;
    }

    public CustomHttpClientConfig setMode(final Mode mode) {
        this.mode = mode;
        return this;
    }

    @Override
    public CustomHttpClientConfig setSchema(final ProtocolSchema schema) {
        return (CustomHttpClientConfig) super.setSchema(schema);
    }

    @Override
    public CustomHttpClientConfig setHost(final String host) {
        return (CustomHttpClientConfig) super.setHost(host);
    }

    @Override
    public CustomHttpClientConfig setPort(final int port) {
        return (CustomHttpClientConfig) super.setPort(port);
    }

    public enum Mode {

        PRODUCTION,

        TEST
    }
}
// end::content[]
