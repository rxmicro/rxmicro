/*
 * Copyright (c) 2020 https://rxmicro.io
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

package io.rxmicro.json.internal.writer;

import java.math.BigDecimal;

import static io.rxmicro.common.util.Strings.escapeString;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
@SuppressWarnings("UnusedReturnValue")
abstract class JsonToStringBuilder {

    final StringBuilder sb = new StringBuilder(100);

    JsonToStringBuilder beginObject() {
        sb.append('{');
        return this;
    }

    JsonToStringBuilder endObject() {
        sb.append('}');
        return this;
    }

    JsonToStringBuilder beginArray() {
        sb.append('[');
        return this;
    }

    JsonToStringBuilder endArray() {
        sb.append(']');
        return this;
    }

    abstract JsonToStringBuilder nameSeparator();

    abstract JsonToStringBuilder valueSeparator();

    abstract JsonToStringBuilder tab(int count);

    abstract JsonToStringBuilder newLine();

    JsonToStringBuilder string(final String value) {
        sb.append('"');
        escapeString(sb, value);
        sb.append('"');
        return this;
    }

    JsonToStringBuilder number(final Object value) {
        if (value instanceof BigDecimal) {
            sb.append(((BigDecimal) value).toPlainString());
        } else {
            sb.append(value.toString());
        }
        return this;
    }

    JsonToStringBuilder bool(final Boolean value) {
        sb.append(value ? "true" : "false");
        return this;
    }

    JsonToStringBuilder nullValue() {
        sb.append("null");
        return this;
    }

    final String build() {
        return sb.toString();
    }

    @Override
    public final String toString() {
        return build();
    }
}
