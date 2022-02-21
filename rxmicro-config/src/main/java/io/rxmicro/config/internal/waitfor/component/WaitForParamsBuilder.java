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
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static io.rxmicro.config.WaitFor.WAIT_FOR_DELIMITER;
import static io.rxmicro.config.WaitFor.WAIT_FOR_PARAM_PREFIX;
import static io.rxmicro.config.WaitFor.WAIT_FOR_TCP_SOCKET_TYPE_NAME;
import static io.rxmicro.config.WaitFor.WAIT_FOR_TIMEOUT;
import static io.rxmicro.config.WaitFor.WAIT_FOR_TIMEOUT_DEFAULT_VALUE_IN_SECONDS;
import static io.rxmicro.config.WaitFor.WAIT_FOR_TYPE_PARAM_NAME;
import static java.util.Map.entry;

/**
 * @author nedis
 * @since 0.3
 */
public final class WaitForParamsBuilder {

    static final String DESTINATION = "destination";

    public static List<Params> buildWaitForParams(final List<String> paramsList) {
        final List<Map<String, String>> groupedParams = buildGroupedParams(paramsList);
        return groupedParams.stream()
                .map(paramsMap -> new Params(
                        paramsMap.getOrDefault(WAIT_FOR_TYPE_PARAM_NAME, WAIT_FOR_TCP_SOCKET_TYPE_NAME),
                        getTimeout(paramsMap),
                        Optional.ofNullable(paramsMap.get(DESTINATION)).orElseThrow(() -> {
                            throw new ConfigException("Expected destination. For example: java Main.class wait-for localhost:8080");
                        })
                ))
                .collect(Collectors.toList());
    }

    private static List<Map<String, String>> buildGroupedParams(final List<String> params) {
        final List<Map<String, String>> groupedParams = new ArrayList<>();
        Map<String, String> currentMap = new HashMap<>();
        for (final String param : params) {
            final String key;
            final String value;
            if (WAIT_FOR_DELIMITER.equals(param)) {
                groupedParams.add(currentMap);
                currentMap = new HashMap<>();
            } else {
                if (param.startsWith(WAIT_FOR_PARAM_PREFIX)) {
                    final Map.Entry<String, String> entry = parseParam(param);
                    key = entry.getKey();
                    value = entry.getValue();
                } else {
                    key = DESTINATION;
                    value = param;
                }
                final String oldValue = currentMap.put(key, value);
                if (oldValue != null) {
                    throw new ConfigException(
                            "Detected a duplicate of parameter: name=?, value1=?, value2=?",
                            key, value, oldValue
                    );
                }
            }
        }
        if (!currentMap.isEmpty()) {
            groupedParams.add(currentMap);
        }
        return groupedParams;
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
        } catch (final NumberFormatException ignored) {
            return parseAsStringDuration(value);
        }
    }

    private static Duration parseAsStringDuration(final String value) {
        try {
            return Duration.parse(value);
        } catch (final DateTimeParseException ignored) {
            throw new ConfigException(
                    "Invalid ? value: '?'! Must be a parsable by ?.parse(String) method duration!",
                    WAIT_FOR_TIMEOUT, value, Duration.class.getName()
            );
        }
    }

    private WaitForParamsBuilder() {
    }
}
