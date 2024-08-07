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

import static io.rxmicro.common.CommonConstants.EMPTY_STRING;
import static io.rxmicro.common.util.Formats.format;
import static java.lang.System.lineSeparator;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.stream.Collectors.joining;

/**
 * Represents the invalid http call to the external REST endpoint.
 *
 * <p>
 * An instance of this class contains the received HTTP response to get additional info about error.
 *
 * @author nedis
 * @since 0.1
 */
public abstract class HttpCallFailedException extends HttpErrorException {

    private static final long serialVersionUID = -5123474100491750132L;

    private final HttpVersion version;

    private final HttpHeaders headers;

    private byte[] body;

    private String bodyAsString;

    /**
     * Creates a {@link HttpCallFailedException} instance with received failed HTTP response.
     *
     * <p>
     * For all child classes which extend the HttpErrorException class, when creating an exception instance the stack trace is not filled,
     * as this information is redundant.
     *
     * <p>
     * (<i>This behavior is achieved by using the {@link RuntimeException#RuntimeException(String, Throwable, boolean, boolean)}.</i>)
     *
     * @param statusCode   the status code
     * @param version      the HTTP protocol version
     * @param headers      the HTTP headers
     * @param body         the HTTP body as byte array
     * @param bodyAsString the HTTP body as string
     */
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

    /**
     * Returns the received HTTP protocol version.
     *
     * @return the received HTTP protocol version
     */
    public final HttpVersion getVersion() {
        return version;
    }

    /**
     * Returns the received HTTP headers.
     *
     * @return the received HTTP headers
     */
    public final HttpHeaders getHeaders() {
        return headers;
    }

    /**
     * Returns the received HTTP body as byte array.
     *
     * @return the received HTTP body as byte array
     */
    public final byte[] getBody() {
        if (body == null) {
            body = bodyAsString.getBytes(UTF_8);
        }
        return body;
    }

    /**
     * Returns the received HTTP body as {@link String}.
     *
     * @return the received HTTP body as {@link String}
     */
    public final String getBodyAsString() {
        if (bodyAsString == null) {
            bodyAsString = body.length > 0 ? new String(body, UTF_8) : EMPTY_STRING;
        }
        return bodyAsString;
    }

    /**
     * Returns {@code true} if HTTP body is present.
     *
     * @return {@code true} if HTTP body is present
     */
    public boolean isBodyPresent() {
        if (body != null) {
            return body.length != 0;
        } else {
            return !getBodyAsString().isEmpty();
        }
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
}
