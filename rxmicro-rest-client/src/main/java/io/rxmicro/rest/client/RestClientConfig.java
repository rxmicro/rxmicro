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
import io.rxmicro.http.client.HttpClientConfig;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.config.Secrets.hideSecretInfo;

/**
 * Allows to configure a REST client options.
 *
 * @author nedis
 * @since 0.7
 */
public class RestClientConfig extends HttpClientConfig {

    private boolean enableAdditionalValidations;

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
    public String toString() {
        return "RestClientConfig {connectionString=" + getConnectionString() +
                ", accessKey=" + hideSecretInfo(getAccessKey()) +
                ", followRedirects=" + isFollowRedirects() +
                ", requestTimeout=" + format(getRequestTimeout()) +
                ", enableAdditionalValidations=" + enableAdditionalValidations +
                '}';
    }
}
