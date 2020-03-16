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

package io.rxmicro.http.internal;

import io.rxmicro.http.local.AbstractRepeatableValues;
import io.rxmicro.http.local.RepeatableHttpHeaders;

import java.util.Collection;
import java.util.Map;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.http.HttpValues.objectToString;
import static io.rxmicro.http.local.HttpValidators.validateHeaderName;
import static java.util.stream.Collectors.joining;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class HttpHeadersImpl extends AbstractRepeatableValues<RepeatableHttpHeaders> implements RepeatableHttpHeaders {

    public HttpHeadersImpl(final Collection<Map.Entry<String, Object>> headers) {
        for (final Map.Entry<String, Object> entry : headers) {
            setOrAdd(validateHeaderName(entry.getKey()), objectToString(entry.getValue()));
        }
    }

    @Override
    public String toString() {
        return getEntries().stream()
                .map(e -> format("?: ?", e.getKey(), e.getValue()))
                .collect(joining(", "));
    }
}
