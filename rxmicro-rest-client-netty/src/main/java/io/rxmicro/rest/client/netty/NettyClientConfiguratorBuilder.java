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

package io.rxmicro.rest.client.netty;

import io.netty.channel.ChannelOption;
import io.rxmicro.common.meta.BuilderMethod;
import reactor.netty.http.client.HttpResponseDecoderSpec;

/**
 * Allows configuring the application specific configs for netty HTTP client.
 *
 * @author nedis
 * @since 0.8
 */
@SuppressWarnings("UnusedReturnValue")
public interface NettyClientConfiguratorBuilder {

    /**
     * Sets (adds or replaces) client channel option.
     *
     * @param option option name
     * @param value  option value
     * @param <T>    option type
     * @return the reference to this {@link NettyClientConfiguratorBuilder} instance
     * @see ChannelOption
     * @see java.util.Map
     * @throws NullPointerException if {@code option} or {@code value} is {@code null}
     * @throws IllegalStateException if Netty configurator already built
     */
    @BuilderMethod
    <T> NettyClientConfiguratorBuilder setClientOption(ChannelOption<T> option,
                                                       T value);

    /**
     * Returns the {@link HttpResponseDecoderSpec} instance to configure netty decoder settings.
     *
     * @return the {@link HttpResponseDecoderSpec} instance to configure netty decoder settings.
     * @throws IllegalStateException if Netty configurator already built
     */
    HttpResponseDecoderSpec getHttpResponseDecoderSpec();
}
