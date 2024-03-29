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

package io.rxmicro.config.internal.waitfor;

import io.rxmicro.config.ConfigException;
import io.rxmicro.config.internal.waitfor.impl.CompositeWaitForService;
import io.rxmicro.config.internal.waitfor.impl.TcpSocketWaitForService;
import io.rxmicro.config.internal.waitfor.model.Params;
import io.rxmicro.logger.Logger;
import io.rxmicro.logger.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static io.rxmicro.config.WaitFor.WAIT_FOR_TCP_SOCKET_TYPE_NAME;
import static io.rxmicro.config.internal.waitfor.component.WaitForParamsBuilder.buildWaitForParams;
import static io.rxmicro.config.internal.waitfor.component.WaitForParamsExtractor.extractWaitForParams;

/**
 * @author nedis
 * @since 0.3
 */
public final class WaitForServiceFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(WaitForServiceFactory.class);

    public static Optional<WaitForService> createWaitForService(final String... commandLineArgs) {
        final List<String> paramsList = extractWaitForParams(commandLineArgs);
        if (paramsList.isEmpty()) {
            LOGGER.warn("wait-for is not configured");
            return Optional.empty();
        } else {
            final List<Params> params = buildWaitForParams(paramsList);
            if (params.size() == 1) {
                return Optional.of(build(params.get(0)));
            } else {
                return Optional.of(
                        new CompositeWaitForService(
                                params.stream().map(WaitForServiceFactory::build).collect(Collectors.toList())
                        )
                );
            }
        }
    }

    private static WaitForService build(final Params params) {
        if (WAIT_FOR_TCP_SOCKET_TYPE_NAME.equals(params.getType())) {
            return new TcpSocketWaitForService(params);
        } else {
            throw new ConfigException("Unsupported type: '?'!", params.getType());
        }
    }

    private WaitForServiceFactory() {
    }
}
