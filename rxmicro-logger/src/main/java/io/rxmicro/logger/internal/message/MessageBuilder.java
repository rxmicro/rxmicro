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

package io.rxmicro.logger.internal.message;

import io.rxmicro.common.meta.BuilderMethod;

/**
 * @author nedis
 * @since 0.9
 */
public class MessageBuilder {

    private static final int DEFAULT_MESSAGE_BUILDER_CAPACITY = 200;

    protected final StringBuilder stringBuilder;

    public MessageBuilder() {
        this.stringBuilder = new StringBuilder(DEFAULT_MESSAGE_BUILDER_CAPACITY);
    }

    @BuilderMethod
    public final MessageBuilder appendWithoutTransformation(final String value) {
        stringBuilder.append(value);
        return this;
    }

    @BuilderMethod
    public MessageBuilder append(final String value) {
        stringBuilder.append(value);
        return this;
    }

    @BuilderMethod
    public MessageBuilder append(final int value) {
        stringBuilder.append(value);
        return this;
    }

    @BuilderMethod
    public MessageBuilder append(final long value) {
        stringBuilder.append(value);
        return this;
    }

    @BuilderMethod
    public MessageBuilder append(final Enum<?> value) {
        stringBuilder.append(value);
        return this;
    }

    public final String build() {
        return stringBuilder.toString();
    }
}
