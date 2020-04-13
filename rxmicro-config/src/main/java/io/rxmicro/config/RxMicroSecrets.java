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
import static io.rxmicro.config.local.ExternalValues.getExternalValue;
import static java.util.Map.entry;
import static java.util.stream.Collectors.toUnmodifiableMap;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.3
 */
public final class RxMicroSecrets {

    /**
     * This is an environment variable or Java system property.
     *
     * This variable describes values that must be hidden at log files
     *
     * Format: strings separated by semicolon.
     * Example: RX_MICRO_SECRETS=my-password;my-access-token
     */
    public static final String RX_MICRO_SECRETS = "RX_MICRO_SECRETS";

    private static final RxMicroSecrets INSTANCE = new RxMicroSecrets();

    public static RxMicroSecrets getInstance() {
        return INSTANCE;
    }

    private RxMicroSecrets() {
    }

    private final Map<String, String> secrets = getExternalValue(RX_MICRO_SECRETS)
            .stream()
            .flatMap(values -> Arrays.stream(values.split(";")))
            .map(secret -> entry(secret, hideSecureInfo(secret)))
            .collect(toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue));

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
