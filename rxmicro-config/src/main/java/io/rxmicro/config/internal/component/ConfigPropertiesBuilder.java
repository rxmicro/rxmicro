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

package io.rxmicro.config.internal.component;

import io.rxmicro.config.Config;
import io.rxmicro.config.internal.model.ConfigProperties;
import io.rxmicro.config.internal.model.ConfigProperty;

import static io.rxmicro.common.util.Strings.unCapitalize;
import static io.rxmicro.runtime.local.Beans.findPublicSetters;
import static java.util.stream.Collectors.toList;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class ConfigPropertiesBuilder {

    public ConfigProperties build(final String nameSpace,
                                  final Config config) {
        return new ConfigProperties(
                nameSpace,
                findPublicSetters(config.getClass()).stream()
                        .map(method -> new ConfigProperty(
                                nameSpace,
                                getPropertyName(method.getName()),
                                method,
                                config))
                        .collect(toList()));
    }

    private String getPropertyName(final String methodName) {
        return unCapitalize(methodName.substring(3));
    }
}
