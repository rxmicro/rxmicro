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

package io.rxmicro.config.internal.component;

import io.rxmicro.config.AsMapConfig;
import io.rxmicro.config.Config;
import io.rxmicro.config.internal.model.ConfigProperties;
import io.rxmicro.config.internal.model.JavaBeanConfigProperties;
import io.rxmicro.config.internal.model.MapConfigProperties;

/**
 * @author nedis
 * @since 0.1
 */
public final class ConfigPropertiesBuilder {

    public ConfigProperties build(final String namespace,
                                  final Config config) {
        if (config instanceof AsMapConfig) {
            return new MapConfigProperties(namespace, (AsMapConfig) config);
        } else {
            return new JavaBeanConfigProperties(namespace, config);
        }
    }
}
