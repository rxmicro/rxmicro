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

package io.rxmicro.rest.client.netty.internal;

import io.netty.handler.codec.http.HttpHeaders;
import io.rxmicro.config.Secrets;
import io.rxmicro.http.HttpVersion;
import io.rxmicro.logger.Logger;
import io.rxmicro.logger.LoggerFactory;
import reactor.netty.http.client.HttpClientResponse;

import java.time.Duration;
import java.util.Optional;

import static io.rxmicro.common.CommonConstants.EMPTY_STRING;
import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.http.HttpStandardHeaderNames.REQUEST_ID;
import static io.rxmicro.logger.RequestIdSupplier.UNDEFINED_REQUEST_ID;
import static java.lang.System.lineSeparator;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.stream.Collectors.joining;

/**
 * @author nedis
 * @since 0.8
 */
final class NettyHttpClientLogger {

    private final Logger logger;

    private final Secrets secrets;

    NettyHttpClientLogger(final Class<?> loggerClass,
                          final Secrets secrets) {
        this.logger = LoggerFactory.getLogger(loggerClass);
        this.secrets = secrets;
    }

    boolean isTraceEnabled() {
        return logger.isTraceEnabled();
    }

    void trace(final String method,
               final String path,
               final HttpHeaders nettyHeaders,
               final byte[] requestBodyBytes) {
        final String requestId = Optional.ofNullable(nettyHeaders.get(REQUEST_ID)).orElse(UNDEFINED_REQUEST_ID);
        logger.trace(
                () -> requestId,
                "HTTP request sent:\n? ?\n?\n\n?",
                format("? ?", method, secrets.hideAllSecretsIn(path)),
                HttpVersion.HTTP_1_1,
                nettyHeaders.entries().stream()
                        .map(e -> format("?: ?", e.getKey(), secrets.hideIfSecret(e.getValue())))
                        .collect(joining(lineSeparator())),
                requestBodyBytes != null ?
                        secrets.hideAllSecretsIn(new String(requestBodyBytes, UTF_8)) :
                        EMPTY_STRING
        );
    }

    void trace(final long startTime,
               final HttpClientResponse response,
               final byte[] responseBodyBytes) {
        final String requestId = Optional.ofNullable(response.requestHeaders().get(REQUEST_ID)).orElse(UNDEFINED_REQUEST_ID);
        logger.trace(
                () -> requestId,
                "HTTP response received (Duration=?):\n? ?\n?\n\n?",
                format(Duration.ofNanos(System.nanoTime() - startTime)),
                response.version(),
                response.status(),
                response.responseHeaders().entries().stream()
                        .map(e -> format("?: ?", e.getKey(), secrets.hideIfSecret(e.getValue())))
                        .collect(joining(lineSeparator())),
                responseBodyBytes.length > 0 ?
                        secrets.hideAllSecretsIn(new String(responseBodyBytes, UTF_8)) :
                        EMPTY_STRING
        );
    }

    boolean isDebugEnabled() {
        return logger.isDebugEnabled();
    }

    void debug(final String method,
               final String path,
               final HttpHeaders nettyHeaders,
               final byte[] requestBodyBytes) {
        final String requestId = Optional.ofNullable(nettyHeaders.get(REQUEST_ID)).orElse(UNDEFINED_REQUEST_ID);
        logger.debug(
                () -> requestId,
                "HTTP request sent: '?'?",
                format("? ?", method, path),
                requestBodyBytes == null ? EMPTY_STRING : format(" with ? request body bytes", requestBodyBytes.length)
        );
    }

    void debug(final long startTime,
               final HttpClientResponse response,
               final byte[] responseBodyBytes) {
        final String requestId = Optional.ofNullable(response.requestHeaders().get(REQUEST_ID)).orElse(UNDEFINED_REQUEST_ID);
        logger.debug(
                () -> requestId,
                "HTTP response received (Duration=?): '?/?', Content=? bytes",
                format(Duration.ofNanos(System.nanoTime() - startTime)),
                response.status(),
                response.version(),
                responseBodyBytes.length
        );
    }
}
