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

package io.rxmicro.config.detail;

import io.rxmicro.config.ConfigException;
import io.rxmicro.config.internal.model.DefaultConfigValueStorage;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class DefaultConfigValuePopulator extends DefaultConfigValueStorage {

    public static void putDefaultConfigValue(final String name,
                                             final String value) {
        final String oldValue = CONFIGS.put(name, value);
        if (oldValue != null) {
            throw new ConfigException(
                    "Detected a duplicate of default config value: key=?, value1=?, value2=?",
                    name, value, oldValue
            );
        }
    }

    private DefaultConfigValuePopulator() {
    }
}
