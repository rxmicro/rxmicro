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

/**
 * RxMicro uses {@link String#split(String)} to split values by regex!
 *
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.3
 */
@SuppressWarnings("UnusedReturnValue")
public final class SecretsConfig extends Config {

    private String regex = ";";

    private String values;

    public String getRegex() {
        return regex;
    }

    /**
     * Sets a regex that used at {@link String#split(String)} method
     *
     * @param regex regex that used at {@link String#split(String)} method
     * @return A reference to this {@code SecretsConfig}
     */
    public SecretsConfig setRegex(final String regex) {
        this.regex = regex;
        return this;
    }

    public String getValues() {
        return values;
    }

    /**
     * Sets a secret values that used as string source
     *
     * @param values values
     * @return A reference to this {@code SecretsConfig}
     */
    public SecretsConfig setValues(final String values) {
        this.values = values;
        return this;
    }

    public boolean hasValues() {
        return values != null && !values.trim().isEmpty();
    }
}
