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

package io.rxmicro.rest.client.jdk.internal;

import io.rxmicro.config.Secrets;
import io.rxmicro.logger.Logger;
import io.rxmicro.logger.LoggerFactory;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.http.HttpStandardHeaderNames.REQUEST_ID;
import static io.rxmicro.logger.RequestIdSupplier.UNDEFINED_REQUEST_ID;
import static java.lang.System.lineSeparator;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Map.entry;
import static java.util.stream.Collectors.joining;

/**
 * @author nedis
 * @since 0.8
 */
final class JdkHttpClientLogger {

    private final Logger logger;

    private final Secrets secrets;

    JdkHttpClientLogger(final Class<?> loggerClass,
                        final Secrets secrets) {
        this.logger = LoggerFactory.getLogger(loggerClass);
        this.secrets = secrets;
    }

    boolean isTraceEnabled() {
        return logger.isTraceEnabled();
    }

    CompletableFuture<HttpResponse<byte[]>> trace(final HttpRequest request,
                                                  final byte[] requestBody,
                                                  final CompletableFuture<HttpResponse<byte[]>> response) {
        final String requestId = request.headers().firstValue(REQUEST_ID).orElse(UNDEFINED_REQUEST_ID);
        final long startTime = System.nanoTime();
        logger.trace(
                () -> requestId,
                "HTTP request sent:\n? ?\n?\n\n?",
                format("? ?", request.method(), secrets.hideAllSecretsIn(request.uri().toString())),
                request.version().map(Enum::toString).orElse(""),
                request.headers().map().entrySet().stream()
                        .flatMap(e -> e.getValue().stream().map(v -> entry(e.getKey(), v)))
                        .map(e -> format("?: ?", e.getKey(), secrets.hideIfSecret(e.getValue())))
                        .collect(joining(lineSeparator())),
                requestBody != null ?
                        secrets.hideAllSecretsIn(new String(requestBody, UTF_8)) :
                        ""
        );
        return response.whenComplete((resp, th) -> {
            if (resp != null) {
                logger.trace(
                        () -> requestId,
                        "HTTP response received (Duration=?):\n? ?\n?\n\n?",
                        format(Duration.ofNanos(System.nanoTime() - startTime)),
                        resp.version(),
                        resp.statusCode(),
                        resp.headers().map().entrySet().stream()
                                .flatMap(e -> e.getValue().stream().map(v -> entry(e.getKey(), v)))
                                .map(e -> format("?: ?", e.getKey(), secrets.hideIfSecret(e.getValue())))
                                .collect(joining(lineSeparator())),
                        resp.body().length > 0 ?
                                secrets.hideAllSecretsIn(new String(resp.body(), UTF_8)) :
                                ""
                );
            }
        });
    }

    boolean isDebugEnabled() {
        return logger.isDebugEnabled();
    }

    CompletableFuture<HttpResponse<byte[]>> debug(final HttpRequest request,
                                                  final byte[] requestBody,
                                                  final CompletableFuture<HttpResponse<byte[]>> response) {
        final String requestId = request.headers().firstValue(REQUEST_ID).orElse(UNDEFINED_REQUEST_ID);
        final String uri = request.uri().toString();
        final int index = uri.indexOf('?');
        final long startTime = System.nanoTime();
        logger.debug(
                () -> requestId,
                "HTTP request sent: '?'?",
                format("? ?", request.method(),
                        index != -1 ? uri.substring(0, index) : uri),
                requestBody == null ? "" : format(" with ? request body bytes", requestBody.length)
        );
        return response.whenComplete((resp, th) -> {
            if (resp != null) {
                logger.debug(
                        () -> requestId,
                        "HTTP response received (Duration=?): '?/?', Content=? bytes",
                        format(Duration.ofNanos(System.nanoTime() - startTime)),
                        resp.statusCode(),
                        resp.version(),
                        resp.body().length
                );
            }
        });
    }
}
