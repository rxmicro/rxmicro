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

package io.rxmicro.test.local.component;

import io.rxmicro.config.Config;
import io.rxmicro.rest.server.HttpServerConfig;

import java.util.Collection;

/**
 * @author nedis
 * @since 0.1
 */
public final class ServerPortHelper {

    public int getServerPort(final Collection<Config> configs) {
        for (final Config config : configs) {
            if (config instanceof HttpServerConfig) {
                return ((HttpServerConfig) config).getPort();
            }
        }
        throw new UnsupportedOperationException("Impossible exception: HttpServerConfig always found");
    }
}
