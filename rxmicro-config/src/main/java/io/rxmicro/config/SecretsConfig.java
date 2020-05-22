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

import io.rxmicro.common.meta.BuilderMethod;

import static io.rxmicro.common.util.Requires.require;

/**
 * Allows configuring the {@link Secrets} instance.
 *
 * <p>
 * The RxMicro framework uses {@link String#split(String)} to split values by regex!
 *
 * @author nedis
 * @see String#split(String)
 * @since 0.3
 */
@SuppressWarnings("UnusedReturnValue")
public final class SecretsConfig extends Config {

    private String regex = ";";

    private String values;

    /**
     * Returns the regex for values delimiter that used at {@link String#split(String)} method.
     *
     * @return the regex for values delimiter that used at {@link String#split(String)} method
     */
    public String getRegex() {
        return regex;
    }

    /**
     * Sets the regex for values delimiter that used at {@link String#split(String)} method.
     *
     * @param regex the regex for values delimiter that used at {@link String#split(String)} method.
     * @return the reference to this {@link SecretsConfig} instance.
     */
    @BuilderMethod
    public SecretsConfig setRegex(final String regex) {
        this.regex = require(regex);
        return this;
    }

    /**
     * Returns the secret values.
     *
     * @return the secret values.
     */
    public String getValues() {
        return values;
    }

    /**
     * Sets the secret values values that used as string source.
     *
     * @param values the secret values.
     * @return the reference to this {@link SecretsConfig} instance.
     */
    @BuilderMethod
    public SecretsConfig setValues(final String values) {
        this.values = require(values);
        return this;
    }

    /**
     * Returns {@code true} if secret values found.
     *
     * @return {@code true} if secret values found.
     */
    public boolean hasValues() {
        return values != null && !values.isBlank();
    }
}
