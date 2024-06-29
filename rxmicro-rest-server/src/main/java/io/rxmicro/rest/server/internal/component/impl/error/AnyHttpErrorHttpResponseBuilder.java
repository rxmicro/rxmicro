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

package io.rxmicro.rest.server.internal.component.impl.error;

import io.rxmicro.common.meta.BuilderMethod;
import io.rxmicro.http.HttpHeaders;
import io.rxmicro.http.HttpVersion;
import io.rxmicro.http.error.HttpErrorException;
import io.rxmicro.logger.LoggerEventBuilder;
import io.rxmicro.logger.LoggerFactory;
import io.rxmicro.logger.RequestIdSupplier;
import io.rxmicro.rest.server.RestServerConfig;
import io.rxmicro.rest.server.detail.component.CustomExceptionServerModelWriter;
import io.rxmicro.rest.server.detail.component.HttpResponseBuilder;
import io.rxmicro.rest.server.detail.model.HttpResponse;
import io.rxmicro.rest.server.feature.ErrorHandler;
import io.rxmicro.rest.server.local.component.HttpErrorResponseBodyBuilder;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.common.util.Requires.require;
import static io.rxmicro.rest.server.detail.component.CustomExceptionServerModelWriters.getCustomExceptionWriter;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Map.entry;

/**
 * @author nedis
 * @since 0.1
 */
public final class AnyHttpErrorHttpResponseBuilder extends ErrorHandler {

    private final HttpResponseBuilder httpResponseBuilder;

    private final HttpErrorResponseBodyBuilder httpErrorResponseBodyBuilder;

    private final RestServerConfig restServerConfig;

    public AnyHttpErrorHttpResponseBuilder(final HttpResponseBuilder httpResponseBuilder,
                                           final HttpErrorResponseBodyBuilder httpErrorResponseBodyBuilder,
                                           final RestServerConfig restServerConfig) {
        this.httpResponseBuilder = require(httpResponseBuilder);
        this.httpErrorResponseBodyBuilder = require(httpErrorResponseBodyBuilder);
        this.restServerConfig = restServerConfig;
    }

    @SuppressWarnings("unchecked")
    public <T extends HttpErrorException> HttpResponse build(final RequestIdSupplier requestIdSupplier,
                                                             final T ex) {
        final boolean logHttpErrorExceptions = restServerConfig.isLogHttpErrorExceptions();
        final Optional<CustomExceptionServerModelWriter<HttpErrorException>> optional =
                getCustomExceptionWriter((Class<HttpErrorException>) ex.getClass());
        if (optional.isPresent()) {
            final CustomExceptionServerModelWriter<HttpErrorException> writer = optional.get();
            if (restServerConfig.isEnableAdditionalValidations()) {
                try {
                    writer.validate(ex);
                } catch (final HttpErrorException exception) {
                    exception.addSuppressed(ex);
                    return processStandardError(requestIdSupplier, exception, logHttpErrorExceptions);
                }
            }
            final HttpResponse response = logHttpErrorExceptions ?
                    new HttpResponseProxy(httpResponseBuilder.build()) :
                    httpResponseBuilder.build();
            response.setStatus(ex.getStatusCode());
            writer.write(ex, response);
            if (logHttpErrorExceptions) {
                final HttpResponseProxy responseProxy = (HttpResponseProxy) response;
                LOGGER.error(requestIdSupplier, "HTTP error: ?", responseProxy.getNonNullParams(ex));
                return responseProxy.originalResponse;
            } else {
                return response;
            }
        } else {
            return processStandardError(requestIdSupplier, ex, logHttpErrorExceptions);
        }
    }

    private <T extends HttpErrorException> HttpResponse processStandardError(final RequestIdSupplier requestIdSupplier,
                                                                             final T ex,
                                                                             final boolean logHttpErrorExceptions) {
        if (logHttpErrorExceptions) {
            final LoggerEventBuilder builder = LoggerFactory.newLoggerEventBuilder()
                    .setMessage("HTTP error: status=?, message=?, class=?", ex.getStatusCode(), ex.getMessage(), ex.getClass().getName())
                    .setRequestIdSupplier(requestIdSupplier);
            if (ex.getSuppressed().length > 0) {
                builder.setThrowable(ex);
            }
            LOGGER.error(builder.build());
        }
        return httpErrorResponseBodyBuilder.build(httpResponseBuilder, ex);
    }

    /**
     * @author nedis
     * @since 0.9
     */
    private static final class HttpResponseProxy implements HttpResponse {

        private static final String DELIMITER = ", ";

        private static final int DEFAULT_CAPACITY = 50;

        private final HttpResponse originalResponse;

        private HttpVersion httpVersion;

        private int status;

        private List<Map.Entry<String, Object>> headers;

        private String body;

        private HttpResponseProxy(final HttpResponse originalResponse) {
            this.originalResponse = originalResponse;
        }

        private String getNonNullParams(final HttpErrorException exception) {
            final StringBuilder stringBuilder = new StringBuilder(DEFAULT_CAPACITY);
            stringBuilder.append("status=").append(status);
            if (httpVersion != null) {
                stringBuilder.append(DELIMITER).append("version=").append(httpVersion);
            }
            if (headers != null) {
                stringBuilder.append(DELIMITER).append("headers=").append(headers);
            }
            if (body != null) {
                stringBuilder.append(DELIMITER).append("body=").append(body);
            }
            stringBuilder.append(DELIMITER).append("class=").append(exception.getClass().getName());
            return stringBuilder.toString();
        }

        @Override
        @BuilderMethod
        public HttpResponse setStatus(final int status) {
            this.status = status;
            return originalResponse.setStatus(status);
        }

        @Override
        @BuilderMethod
        public HttpResponse setVersion(final HttpVersion httpVersion) {
            this.httpVersion = httpVersion;
            return originalResponse.setVersion(httpVersion);
        }

        @Override
        @BuilderMethod
        public HttpResponse addHeader(final String name,
                                      final String value) {
            addHeaderEntry(name, value);
            return originalResponse.addHeader(name, value);
        }

        @Override
        @BuilderMethod
        public HttpResponse setOrAddHeaders(final HttpHeaders headers) {
            for (final Map.Entry<String, String> entry : headers.getEntries()) {
                addHeaderEntry(entry.getKey(), entry.getValue());
            }
            return originalResponse.setOrAddHeaders(headers);
        }

        @Override
        @BuilderMethod
        public HttpResponse setHeader(final String name,
                                      final String value) {
            addHeaderEntry(name, value);
            return originalResponse.setHeader(name, value);
        }

        @Override
        @BuilderMethod
        public HttpResponse setContent(final byte[] content) {
            this.body = new String(content, UTF_8);
            return originalResponse.setContent(content);
        }

        @Override
        @BuilderMethod
        public HttpResponse setContent(final String content) {
            this.body = content;
            return originalResponse.setContent(content);
        }

        @Override
        @BuilderMethod
        public HttpResponse sendFile(final Path path) {
            this.body = format("<send-file: ?>", path.toAbsolutePath().toString());
            return originalResponse.sendFile(path);
        }

        private void addHeaderEntry(final String name,
                                    final String value) {
            if (value != null) {
                if (headers == null) {
                    headers = new ArrayList<>();
                }
                headers.add(entry(name, value));
            }
        }
    }
}
