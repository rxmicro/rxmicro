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

import io.rxmicro.config.internal.SecretsImpl;

/**
 * Base interface that supports hiding a secret info.
 * This feature is useful for hiding a secret info in log messages.
 *
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.3
 */
public interface Secrets {

    /**
     * @return Default implementation
     */
    static Secrets getDefaultInstance() {
        return SecretsImpl.INSTANCE;
    }

    /**
     * Return `****` placeholder if value is secret
     *
     * @param value secret candidate
     * @return `****` placeholder if value is secret,
     *          otherwise value
     */
    String hideIfSecret(String value);

    /**
     * Replace all substrings if there are secrets
     *
     * @param message string that can contain secret substrings
     * @return processed string
     */
    String hideAllSecretsIn(String message);

    /**
     * Define an algorithm to hide secure info
     *
     * @param value string value
     * @return secret value or {@code null} if argument is {@code null}
     */
    static String hideSecretInfo(final String value) {
        if (value != null) {
            if (value.length() > 10) {
                return "****" + value.substring(value.length() - 4);
            } else {
                return "********";
            }
        } else {
            return null;
        }
    }
}
