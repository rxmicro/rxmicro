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

package io.rxmicro.config;

import io.rxmicro.config.internal.SecretsImpl;

/**
 * Base interface that supports hiding a secret info.
 *
 * <p>
 * (<i>This feature is useful for hiding a secret info in log messages.</i>)
 *
 * @author nedis
 * @since 0.3
 */
public interface Secrets {

    /**
     * Returns the default implementation of {@link Secrets} instance.
     *
     * @return Default implementation
     */
    static Secrets getDefaultInstance() {
        return SecretsImpl.INSTANCE;
    }

    /**
     * Define an algorithm to hide secure info.
     *
     * @param value string value
     * @return secret value or {@code null} if argument is {@code null}
     */
    static String hideSecretInfo(final String value) {
        if (value != null) {
            final int minLengthForLongText = 10;
            if (value.length() > minLengthForLongText) {
                final int asteriskCount = 4;
                return "****" + value.substring(value.length() - asteriskCount);
            } else {
                return "********";
            }
        } else {
            return null;
        }
    }

    /**
     * Returns `****` placeholder if value is secret.
     *
     * @param value secret candidate
     * @return `****` placeholder if value is secret, otherwise value
     */
    String hideIfSecret(String value);

    /**
     * Replace all substrings if there are secrets.
     *
     * @param message string that can contain secret substrings
     * @return processed string
     */
    String hideAllSecretsIn(String message);
}
