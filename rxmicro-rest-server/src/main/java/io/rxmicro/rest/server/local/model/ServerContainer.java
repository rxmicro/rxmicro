/*
 * Copyright 2019 https://rxmicro.io
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

package io.rxmicro.rest.server.local.model;

import io.rxmicro.rest.server.ServerInstance;
import io.rxmicro.rest.server.detail.component.AbstractRestController;
import io.rxmicro.rest.server.detail.component.RestControllerAggregator;
import io.rxmicro.rest.server.local.component.DynamicRestControllerRegistrar;

import java.util.Set;

import static io.rxmicro.rest.server.local.model.RestControllerRegistrationFilter.createFilter;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class ServerContainer {

    private final ServerInstance serverInstance;

    private final DynamicRestControllerRegistrar dynamicRestControllerRegistrar;

    private final RestControllerAggregator restControllerAggregator;

    public ServerContainer(final ServerInstance serverInstance,
                           final DynamicRestControllerRegistrar dynamicRestControllerRegistrar,
                           final RestControllerAggregator restControllerAggregator) {
        this.serverInstance = serverInstance;
        this.dynamicRestControllerRegistrar = dynamicRestControllerRegistrar;
        this.restControllerAggregator = restControllerAggregator;
    }

    public ServerInstance getServerInstance() {
        return serverInstance;
    }

    public void register(final Set<Class<?>> restControllerClasses) {
        restControllerAggregator.register(dynamicRestControllerRegistrar, createFilter(restControllerClasses));
    }

    public void unregisterAllRestControllers() {
        dynamicRestControllerRegistrar.clear();
    }

    public Set<AbstractRestController> getRegisteredRestControllers() {
        return dynamicRestControllerRegistrar.getRegisteredRestControllers();
    }
}
