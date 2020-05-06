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

package io.rxmicro.config.internal;

import io.rxmicro.config.Secrets;
import io.rxmicro.config.SecretsConfig;

import java.util.Arrays;
import java.util.Map;

import static io.rxmicro.config.Configs.getConfig;
import static java.util.Map.entry;
import static java.util.stream.Collectors.toUnmodifiableMap;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.3
 */
public final class SecretsImpl implements Secrets {

    public static final SecretsImpl INSTANCE = new SecretsImpl();

    private final Map<String, String> secrets;

    private SecretsImpl() {
        final SecretsConfig config = getConfig(SecretsConfig.class);
        if (config.hasValues()) {
            this.secrets = Arrays.stream(config.getValues().split(config.getRegex()))
                    .map(secret -> entry(secret, Secrets.hideSecretInfo(secret)))
                    .collect(toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue));
        } else {
            this.secrets = Map.of();
        }
    }

    @Override
    public String hideIfSecret(final String value) {
        if (secrets.isEmpty()) {
            return value;
        } else {
            final String result = secrets.get(value);
            return result != null ? result : value;
        }
    }

    @Override
    public String hideAllSecretsIn(final String message) {
        if (secrets.isEmpty()) {
            return message;
        } else {
            String result = message;
            for (final Map.Entry<String, String> secret : secrets.entrySet()) {
                result = result.replace(secret.getKey(), secret.getValue());
            }
            return result;
        }
    }
}
