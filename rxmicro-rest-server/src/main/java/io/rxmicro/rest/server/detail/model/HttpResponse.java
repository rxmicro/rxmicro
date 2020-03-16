/*
 * Copyright 2019 https://rxmicro.io
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

package io.rxmicro.rest.server.detail.model;

import io.rxmicro.http.HttpHeaders;
import io.rxmicro.http.HttpVersion;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static io.rxmicro.http.HttpValues.listToString;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public interface HttpResponse {

    byte[] EMPTY_CONTENT = new byte[0];

    void setStatus(int status);

    void setVersion(HttpVersion httpVersion);

    void addHeader(String name,
                   String value);

    default void addHeader(final String name,
                           final Object value) {
        if (value != null) {
            addHeader(name, value.toString());
        }
    }

    default void addHeader(final String name,
                           final BigDecimal value) {
        if (value != null) {
            addHeader(name, value.toPlainString());
        }
    }

    default void addHeader(final String name,
                           final List<?> value) {
        if (value != null && value.size() > 0) {
            addHeader(name, listToString(value));
        }
    }

    void setOrAddHeaders(final HttpHeaders headers);

    void setHeader(String name,
                   String value);

    default void setHeader(final String name,
                           final Object value) {
        if (value != null) {
            setHeader(name, value.toString());
        }
    }

    default void setHeader(final String name,
                           final BigDecimal value) {
        if (value != null) {
            setHeader(name, value.toPlainString());
        }
    }

    default void setHeader(final String name,
                           final List<?> value) {
        if (value != null && value.size() > 0) {
            setHeader(name, listToString(value));
        }
    }

    void setContent(byte[] content);

    default void setContent(final String content) {
        setContent(content.getBytes(StandardCharsets.UTF_8));
    }
}
