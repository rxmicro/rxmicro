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

package io.rxmicro.rest.client.netty.internal;

import io.netty.channel.ChannelOption;
import io.rxmicro.common.CheckedWrapperException;
import io.rxmicro.rest.client.netty.NettyClientConfiguratorBuilder;
import reactor.netty.http.client.HttpResponseDecoderSpec;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @since 0.8
 */
public final class NettyClientConfiguratorBuilderImpl implements NettyClientConfiguratorBuilder {

    static final Set<String> PROCESSED_NAMESPACES = new HashSet<>();

    final String namespace;

    final Map<ChannelOption<?>, Object> clientOptions = new LinkedHashMap<>();

    HttpResponseDecoderSpec httpResponseDecoderSpec;

    public NettyClientConfiguratorBuilderImpl(final String namespace) {
        this.namespace = namespace;
    }

    @Override
    public <T> NettyClientConfiguratorBuilder setClientOption(final ChannelOption<T> option,
                                                              final T value) {
        if (PROCESSED_NAMESPACES.contains(namespace)) {
            throw new IllegalStateException("Netty configurator already built! " +
                    "Any customizations must be done before building of the netty configurator!");
        }
        clientOptions.put(require(option), require(value));
        return this;
    }

    @Override
    public HttpResponseDecoderSpec getHttpResponseDecoderSpec() {
        if (PROCESSED_NAMESPACES.contains(namespace)) {
            throw new IllegalStateException("Netty configurator already built! " +
                    "Any customizations must be done before building of the netty configurator!");
        }
        if (httpResponseDecoderSpec == null) {
            try {
                final Constructor<HttpResponseDecoderSpec> constructor = HttpResponseDecoderSpec.class.getDeclaredConstructor();
                constructor.setAccessible(true);
                httpResponseDecoderSpec = constructor.newInstance();
            } catch (final NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException exception) {
                throw new CheckedWrapperException(exception);
            }
        }
        return httpResponseDecoderSpec;
    }
}
