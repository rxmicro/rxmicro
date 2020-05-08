/*
 * Copyright (c) 2020 https://rxmicro.io
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

package io.rxmicro.rest.server.local.component;

import io.rxmicro.config.Config;
import io.rxmicro.config.Configs;
import io.rxmicro.rest.server.RestServerConfig;
import io.rxmicro.rest.server.ServerInstance;
import io.rxmicro.rest.server.detail.component.RestControllerAggregator;
import io.rxmicro.rest.server.internal.Router;
import io.rxmicro.rest.server.internal.component.ComponentResolver;
import io.rxmicro.rest.server.internal.component.impl.ComponentResolverImpl;
import io.rxmicro.rest.server.local.model.RestControllerRegistrationFilter;
import io.rxmicro.rest.server.local.model.ServerContainer;

import java.util.Map;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.common.util.Requires.require;
import static io.rxmicro.config.Configs.getConfig;
import static io.rxmicro.rest.server.detail.component.RestControllerAggregator.REST_CONTROLLER_AGGREGATOR_IMPL_CLASS_NAME;
import static io.rxmicro.runtime.detail.Runtimes.ENTRY_POINT_PACKAGE;
import static io.rxmicro.runtime.local.Instances.instantiate;

/**
 * @author nedis
 * @since 0.1
 */
public final class RestServerLauncher {

    private static final String REST_CONTROLLER_AGGREGATOR_IMPL_FULL_CLASS_NAME =
            format("?.?", ENTRY_POINT_PACKAGE, REST_CONTROLLER_AGGREGATOR_IMPL_CLASS_NAME);

    public static ServerContainer launchWithoutRestControllers(final Map<String, Config> configs) {
        new Configs.Builder().withConfigs(configs).build();
        return launch0(null);
    }

    public static ServerContainer launchWithFilter(final RestControllerRegistrationFilter filter) {
        new Configs.Builder().buildIfNotConfigured();
        return launch0(require(filter));
    }

    private static ServerContainer launch0(final RestControllerRegistrationFilter filter) {
        final RestControllerAggregator restControllerAggregator = instantiate(REST_CONTROLLER_AGGREGATOR_IMPL_FULL_CLASS_NAME);
        final RestServerConfig restServerConfig = getConfig(RestServerConfig.class);
        final ComponentResolver componentResolver = new ComponentResolverImpl(restServerConfig);
        final Router router = new Router(componentResolver);
        if (filter != null) {
            restControllerAggregator.register(router, filter);
        }
        final ServerFactory serverFactory = componentResolver.getServerFactory();
        final ServerInstance serverInstance = serverFactory.startNewServer(router);
        return new ServerContainer(serverInstance, router, restControllerAggregator);
    }

    private RestServerLauncher() {
    }
}
