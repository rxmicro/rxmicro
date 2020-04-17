/*
 * Copyright (c) 2020. http://rxmicro.io
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

package io.rxmicro.config;

import java.util.Arrays;
import java.util.Map;

import static io.rxmicro.common.util.Strings.hideSecureInfo;
import static io.rxmicro.config.Configs.getConfig;
import static java.util.Map.entry;
import static java.util.stream.Collectors.toUnmodifiableMap;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.3
 */
public final class Secrets {

    private static final Secrets INSTANCE = new Secrets();

    public static Secrets getInstance() {
        return INSTANCE;
    }

    private final Map<String, String> secrets;

    private Secrets() {
        final SecretsConfig config = getConfig(SecretsConfig.class);
        if (config.hasValues()) {
            this.secrets = Arrays.stream(config.getValues().split(config.getRegex()))
                    .map(secret -> entry(secret, hideSecureInfo(secret)))
                    .collect(toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue));
        } else {
            this.secrets = Map.of();
        }
    }

    public String hideIfSecret(final String value) {
        if (secrets.isEmpty()) {
            return value;
        } else {
            final String result = secrets.get(value);
            return result != null ? result : value;
        }
    }

    public String replaceAllSecretsIfFound(final String message) {
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
