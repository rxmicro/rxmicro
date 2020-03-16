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

package io.rxmicro.rest.model;

import io.rxmicro.http.HttpHeaders;
import io.rxmicro.http.HttpVersion;
import io.rxmicro.http.error.HttpErrorException;

import static io.rxmicro.common.util.Formats.format;
import static java.lang.System.lineSeparator;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.stream.Collectors.joining;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public abstract class HttpCallFailedException extends HttpErrorException {

    private final HttpVersion version;

    private final HttpHeaders headers;

    private byte[] body;

    private String bodyAsString;

    protected HttpCallFailedException(final int statusCode,
                                      final HttpVersion version,
                                      final HttpHeaders headers,
                                      final byte[] body,
                                      final String bodyAsString) {
        super(statusCode);
        this.version = version;
        this.headers = headers;
        this.body = body;
        this.bodyAsString = bodyAsString;
    }

    public final HttpVersion getVersion() {
        return version;
    }

    public final HttpHeaders getHeaders() {
        return headers;
    }

    public final byte[] getBody() {
        if (body == null) {
            body = bodyAsString.getBytes(UTF_8);
        }
        return body;
    }

    public final String getBodyAsString() {
        if (bodyAsString == null) {
            bodyAsString = body.length > 0 ? new String(body, UTF_8) : "";
        }
        return bodyAsString;
    }

    @Override
    public final String getMessage() {
        return format("? ??????",
                getStatusCode(),
                version.getText(),
                lineSeparator(),
                headers.getEntries().stream()
                        .map(e -> format("?: ?", e.getKey(), String.join(", ", e.getValue())))
                        .collect(joining(lineSeparator())),
                lineSeparator(),
                lineSeparator(),
                getBodyAsString());
    }

    public boolean isBodyPresent() {
        if (body != null) {
            return body.length != 0;
        } else {
            return !getBodyAsString().isEmpty();
        }
    }
}
