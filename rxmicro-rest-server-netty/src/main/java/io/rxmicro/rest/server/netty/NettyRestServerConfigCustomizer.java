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

package io.rxmicro.rest.server.netty;

import static io.rxmicro.rest.server.netty.internal.component.NettyConfiguratorController.getNettyConfiguratorController;

/**
 * Allows configuring the application specific configs for netty HTTP server.
 *
 * <ul>
 *     <li>{@link io.rxmicro.netty.runtime.NettyRuntimeConfig} must be used for environment specific configs.</li>
 *     <li>{@link NettyRestServerConfigCustomizer} must be used for application specific configs.</li>
 * </ul>
 *
 * @author nedis
 * @see NettyConfiguratorBuilder
 * @see io.rxmicro.netty.runtime.NettyRuntimeConfig
 * @see NettyRestServerConfig
 * @since 0.7.2
 */
public final class NettyRestServerConfigCustomizer {

    /**
     * Returns the current {@link NettyConfiguratorBuilder} instance that must be used for configuration of netty HTTP server.
     *
     * <p>
     * The {@link NettyConfiguratorBuilder} must be used for application specific configs.
     *
     * @return the current {@link NettyConfiguratorBuilder} instance
     * @throws IllegalStateException if Netty configurator already built.
     */
    public static NettyConfiguratorBuilder getCurrentNettyConfiguratorBuilder() {
        return getNettyConfiguratorController().getNettyConfiguratorBuilder();
    }

    private NettyRestServerConfigCustomizer() {
    }
}
