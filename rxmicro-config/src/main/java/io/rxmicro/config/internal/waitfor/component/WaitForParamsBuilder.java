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

package io.rxmicro.config.internal.waitfor.component;

import io.rxmicro.config.ConfigException;
import io.rxmicro.config.internal.waitfor.model.Params;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static io.rxmicro.config.WaitFor.WAIT_FOR_TCP_SOCKET_TYPE_NAME;
import static io.rxmicro.config.WaitFor.WAIT_FOR_TIMEOUT;
import static io.rxmicro.config.WaitFor.WAIT_FOR_TIMEOUT_DEFAULT_VALUE_IN_SECONDS;
import static io.rxmicro.config.WaitFor.WAIT_FOR_TYPE_PARAM_NAME;
import static java.util.Map.entry;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.3
 */
public final class WaitForParamsBuilder {

    private static final String DESTINATION = "destination";

    public static Params buildWaitForParams(final List<String> paramsList) {
        final Map<String, String> paramsMap = getParamsMap(paramsList);
        return new Params(
                paramsMap.getOrDefault(WAIT_FOR_TYPE_PARAM_NAME, WAIT_FOR_TCP_SOCKET_TYPE_NAME),
                getTimeout(paramsMap),
                Optional.ofNullable(paramsMap.get(DESTINATION)).orElseThrow(() -> {
                    throw new ConfigException("Wait for destination nor found");
                })
        );
    }

    private static Map<String, String> getParamsMap(final List<String> params) {
        if (params.isEmpty()) {
            return Map.of();
        } else {
            final Map<String, String> map = new HashMap<>();
            for (final String param : params) {
                final String key;
                final String value;
                if (param.startsWith("--")) {
                    final Map.Entry<String, String> entry = parseParam(param);
                    key = entry.getKey();
                    value = entry.getValue();
                } else {
                    key = DESTINATION;
                    value = param;
                }
                final String oldValue = map.put(key, value);
                if (oldValue != null) {
                    throw new ConfigException(
                            "Detected a duplicate of parameter: name=?, value1=?, value2=?",
                            key, value, oldValue
                    );
                }
            }
            return map;
        }
    }

    private static Map.Entry<String, String> parseParam(final String param) {
        final String[] data = param.substring(2).split("=");
        if (data.length != 2) {
            throw new ConfigException("Invalid parameter expression. Expected '--${name}=${value}', but actual is '?'", param);
        }
        return entry(data[0], data[1]);
    }

    private static Duration getTimeout(final Map<String, String> params) {
        final String value = params.getOrDefault(WAIT_FOR_TIMEOUT, WAIT_FOR_TIMEOUT_DEFAULT_VALUE_IN_SECONDS);
        try {
            return Duration.ofSeconds(Long.parseLong(value));
        } catch (final NumberFormatException e) {
            return Duration.parse(value);
        }
    }

    private WaitForParamsBuilder() {
    }
}
