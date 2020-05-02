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

package io.rxmicro.test.local.component;

import io.rxmicro.rest.server.local.model.ServerContainer;

import java.util.List;
import java.util.Set;

import static io.rxmicro.common.util.Requires.require;
import static io.rxmicro.tool.common.Reflections.getFieldValue;
import static java.util.stream.Collectors.toList;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class RestControllerInstanceResolver {

    private final Set<Class<?>> restControllerClasses;

    private final ServerContainer serverContainer;

    public RestControllerInstanceResolver(final Class<?>[] restControllerClasses,
                                          final ServerContainer serverContainer) {
        this.restControllerClasses = Set.of(restControllerClasses);
        this.serverContainer = require(serverContainer);
    }

    public Set<Class<?>> getRestControllerClasses() {
        return restControllerClasses;
    }

    public List<Object> getRestControllerInstances() {
        return serverContainer.getRegisteredRestControllers()
                .stream()
                .filter(s -> restControllerClasses.contains(s.getRestControllerClass()))
                .map(wrapper -> getFieldValue(wrapper, "restController"))
                .collect(toList());
    }
}
