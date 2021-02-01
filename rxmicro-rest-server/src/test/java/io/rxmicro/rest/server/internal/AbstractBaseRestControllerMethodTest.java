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

package io.rxmicro.rest.server.internal;

import io.rxmicro.logger.Logger;
import io.rxmicro.logger.LoggerEvent;
import io.rxmicro.logger.LoggerEventBuilder;
import io.rxmicro.logger.RequestIdSupplier;
import io.rxmicro.logger.impl.LoggerImplProvider;
import io.rxmicro.rest.model.PathVariableMapping;
import io.rxmicro.rest.server.RestServerConfig;
import io.rxmicro.rest.server.detail.component.HttpResponseBuilder;
import io.rxmicro.rest.server.detail.model.HttpRequest;
import io.rxmicro.rest.server.detail.model.HttpResponse;
import io.rxmicro.rest.server.local.component.HttpErrorResponseBodyBuilder;
import org.mockito.Mock;

import java.util.concurrent.CompletionStage;
import java.util.function.BiFunction;
import java.util.function.Supplier;

import static io.rxmicro.logger.LoggerImplProviderFactory.setLoggerImplFactory;

/**
 * @author nedis
 *
 * @since 0.1
 */
abstract class AbstractBaseRestControllerMethodTest {

    private static final MockLoggerImplProvider MOCK_LOGGER_IMPL_PROVIDER = new MockLoggerImplProvider();

    static {
        setLoggerImplFactory(MOCK_LOGGER_IMPL_PROVIDER);
    }

    @Mock
    protected HttpResponseBuilder httpResponseBuilder;

    @Mock
    protected RestServerConfig restServerConfig;

    @Mock
    protected HttpErrorResponseBodyBuilder httpErrorResponseBodyBuilder;

    protected final BaseRestControllerMethod build(final String parentUrl,
                                                   final boolean corsRequestPossible,
                                                   final BiFunction<PathVariableMapping, HttpRequest, CompletionStage<HttpResponse>> func) {
        final BaseRestController baseRestController = new BaseRestController() {
            @Override
            public Class<?> getRestControllerClass() {
                throw new UnsupportedOperationException();
            }
        };
        baseRestController.httpResponseBuilder = httpResponseBuilder;
        baseRestController.httpErrorResponseBodyBuilder = httpErrorResponseBodyBuilder;
        baseRestController.restServerConfig = restServerConfig;

        return new BaseRestControllerMethod(parentUrl, baseRestController, corsRequestPossible) {
            @Override
            protected CompletionStage<HttpResponse> invoke(final PathVariableMapping pathVariableMapping,
                                                           final HttpRequest request) {
                return func.apply(pathVariableMapping, request);
            }
        };
    }

    protected final void setLoggerMock(final Logger logger) {
        MOCK_LOGGER_IMPL_PROVIDER.setLogger(logger);
    }

    protected final void setLoggerEventBuilderMock(final LoggerEventBuilder loggerEventBuilder) {
        MOCK_LOGGER_IMPL_PROVIDER.setLoggerEventBuilder(loggerEventBuilder);
    }

    /**
     * @author nedis
     *
     * @since 0.1
     */
    private static final class MockLoggerImplProvider implements LoggerImplProvider {

        private final ProxyLogger logger = new ProxyLogger();

        private LoggerEventBuilder loggerEventBuilder;

        private void setLogger(final Logger logger) {
            this.logger.setLogger(logger);
        }

        public void setLoggerEventBuilder(final LoggerEventBuilder loggerEventBuilder) {
            this.loggerEventBuilder = loggerEventBuilder;
        }

        @Override
        public void setup() {
        }

        @Override
        public Logger getLogger(final String name) {
            return logger;
        }

        @Override
        public LoggerEventBuilder newLoggerEventBuilder() {
            return loggerEventBuilder;
        }
    }

    /**
     * @author nedis
     *
     * @since 0.1
     */
    private static final class ProxyLogger implements Logger {

        private Logger logger;

        private void setLogger(final Logger logger) {
            this.logger = logger;
        }

        @Override
        public String getName() {
            return logger.getName();
        }

        @Override
        public boolean isTraceEnabled() {
            return logger.isTraceEnabled();
        }

        @Override
        public void trace(final LoggerEvent loggerEvent) {
            logger.trace(loggerEvent);
        }

        @Override
        public void trace(final String msg) {
            logger.trace(msg);
        }

        @Override
        public void trace(final String format, final Object arg1) {
            logger.trace(format, arg1);
        }

        @Override
        public void trace(final String format, final Object arg1, final Object arg2) {
            logger.trace(format, arg1, arg2);
        }

        @Override
        public void trace(final String format, final Object arg1, final Object arg2, final Object arg3) {
            logger.trace(format, arg1, arg2, arg3);
        }

        @Override
        public void trace(final String format, final Object arg1, final Object arg2, final Object arg3, final Object arg4) {
            logger.trace(format, arg1, arg2, arg3, arg4);
        }

        @Override
        public void trace(final String format, final Object arg1, final Object arg2, final Object arg3, final Object arg4,
                          final Object arg5) {
            logger.trace(format, arg1, arg2, arg3, arg4, arg5);
        }

        @Override
        public void trace(final String format, final Object... arguments) {
            logger.trace(format, arguments);
        }

        @Override
        public void trace(final String format, final Supplier<?> arg1) {
            logger.trace(format, arg1);
        }

        @Override
        public void trace(final String format, final Supplier<?> arg1, final Supplier<?> arg2) {
            logger.trace(format, arg1, arg2);
        }

        @Override
        public void trace(final String format, final Supplier<?> arg1, final Supplier<?> arg2, final Supplier<?> arg3) {
            logger.trace(format, arg1, arg2, arg3);
        }

        @Override
        public void trace(final String format, final Supplier<?> arg1, final Supplier<?> arg2, final Supplier<?> arg3,
                          final Supplier<?> arg4) {
            logger.trace(format, arg1, arg2, arg3, arg4);
        }

        @Override
        public void trace(final String format, final Supplier<?> arg1, final Supplier<?> arg2, final Supplier<?> arg3,
                          final Supplier<?> arg4, final Supplier<?> arg5) {
            logger.trace(format, arg1, arg2, arg3, arg4, arg5);
        }

        @Override
        public void trace(final String format, final Supplier<?>... suppliers) {
            logger.trace(format, suppliers);
        }

        @Override
        public void trace(final Throwable throwable, final String msg) {
            logger.trace(throwable, msg);
        }

        @Override
        public void trace(final Throwable throwable, final String format, final Object arg1) {
            logger.trace(throwable, format, arg1);
        }

        @Override
        public void trace(final Throwable throwable, final String format, final Object arg1, final Object arg2) {
            logger.trace(throwable, format, arg1, arg2);
        }

        @Override
        public void trace(final Throwable throwable, final String format, final Object arg1, final Object arg2, final Object arg3) {
            logger.trace(throwable, format, arg1, arg2, arg3);
        }

        @Override
        public void trace(final Throwable throwable, final String format, final Object arg1, final Object arg2, final Object arg3,
                          final Object arg4) {
            logger.trace(throwable, format, arg1, arg2, arg3, arg4);
        }

        @Override
        public void trace(final Throwable throwable, final String format, final Object arg1, final Object arg2, final Object arg3,
                          final Object arg4, final Object arg5) {
            logger.trace(throwable, format, arg1, arg2, arg3, arg4, arg5);
        }

        @Override
        public void trace(final Throwable throwable, final String format, final Object... arguments) {
            logger.trace(throwable, format, arguments);
        }

        @Override
        public void trace(final Throwable throwable, final String format, final Supplier<?> arg1) {
            logger.trace(throwable, format, arg1);
        }

        @Override
        public void trace(final Throwable throwable, final String format, final Supplier<?> arg1, final Supplier<?> arg2) {
            logger.trace(throwable, format, arg1, arg2);
        }

        @Override
        public void trace(final Throwable throwable, final String format, final Supplier<?> arg1, final Supplier<?> arg2,
                          final Supplier<?> arg3) {
            logger.trace(throwable, format, arg1, arg2, arg3);
        }

        @Override
        public void trace(final Throwable throwable, final String format, final Supplier<?> arg1, final Supplier<?> arg2,
                          final Supplier<?> arg3, final Supplier<?> arg4) {
            logger.trace(throwable, format, arg1, arg2, arg3, arg4);
        }

        @Override
        public void trace(final Throwable throwable, final String format, final Supplier<?> arg1, final Supplier<?> arg2,
                          final Supplier<?> arg3, final Supplier<?> arg4, final Supplier<?> arg5) {
            logger.trace(throwable, format, arg1, arg2, arg3, arg4, arg5);
        }

        @Override
        public void trace(final Throwable throwable, final String format, final Supplier<?>... suppliers) {
            logger.trace(throwable, format, suppliers);
        }

        @Override
        public void trace(final RequestIdSupplier requestIdSupplier, final String msg) {
            logger.trace(requestIdSupplier, msg);
        }

        @Override
        public void trace(final RequestIdSupplier requestIdSupplier, final String format, final Object arg1) {
            logger.trace(requestIdSupplier, format, arg1);
        }

        @Override
        public void trace(final RequestIdSupplier requestIdSupplier, final String format, final Object arg1, final Object arg2) {
            logger.trace(requestIdSupplier, format, arg1, arg2);
        }

        @Override
        public void trace(final RequestIdSupplier requestIdSupplier, final String format, final Object arg1, final Object arg2,
                          final Object arg3) {
            logger.trace(requestIdSupplier, format, arg1, arg2, arg3);
        }

        @Override
        public void trace(final RequestIdSupplier requestIdSupplier, final String format, final Object arg1, final Object arg2,
                          final Object arg3, final Object arg4) {
            logger.trace(requestIdSupplier, format, arg1, arg2, arg3, arg4);
        }

        @Override
        public void trace(final RequestIdSupplier requestIdSupplier, final String format, final Object arg1, final Object arg2,
                          final Object arg3, final Object arg4, final Object arg5) {
            logger.trace(requestIdSupplier, format, arg1, arg2, arg3, arg4, arg5);
        }

        @Override
        public void trace(final RequestIdSupplier requestIdSupplier, final String format, final Object... arguments) {
            logger.trace(requestIdSupplier, format, arguments);
        }

        @Override
        public void trace(final RequestIdSupplier requestIdSupplier, final String format, final Supplier<?> arg1) {
            logger.trace(requestIdSupplier, format, arg1);
        }

        @Override
        public void trace(final RequestIdSupplier requestIdSupplier, final String format, final Supplier<?> arg1,
                          final Supplier<?> arg2) {
            logger.trace(requestIdSupplier, format, arg1, arg2);
        }

        @Override
        public void trace(final RequestIdSupplier requestIdSupplier, final String format, final Supplier<?> arg1,
                          final Supplier<?> arg2, final Supplier<?> arg3) {
            logger.trace(requestIdSupplier, format, arg1, arg2, arg3);
        }

        @Override
        public void trace(final RequestIdSupplier requestIdSupplier, final String format, final Supplier<?> arg1,
                          final Supplier<?> arg2, final Supplier<?> arg3, final Supplier<?> arg4) {
            logger.trace(requestIdSupplier, format, arg1, arg2, arg3, arg4);
        }

        @Override
        public void trace(final RequestIdSupplier requestIdSupplier, final String format, final Supplier<?> arg1,
                          final Supplier<?> arg2, final Supplier<?> arg3, final Supplier<?> arg4, final Supplier<?> arg5) {
            logger.trace(requestIdSupplier, format, arg1, arg2, arg3, arg4, arg5);
        }

        @Override
        public void trace(final RequestIdSupplier requestIdSupplier, final String format, final Supplier<?>... suppliers) {
            logger.trace(requestIdSupplier, format, suppliers);
        }

        @Override
        public void trace(final RequestIdSupplier requestIdSupplier, final Throwable throwable, final String format,
                          final Object arg1) {
            logger.trace(requestIdSupplier, throwable, format, arg1);
        }

        @Override
        public void trace(final RequestIdSupplier requestIdSupplier, final Throwable throwable, final String format,
                          final Object arg1, final Object arg2) {
            logger.trace(requestIdSupplier, throwable, format, arg1, arg2);
        }

        @Override
        public void trace(final RequestIdSupplier requestIdSupplier, final Throwable throwable, final String format,
                          final Object arg1, final Object arg2, final Object arg3) {
            logger.trace(requestIdSupplier, throwable, format, arg1, arg2, arg3);
        }

        @Override
        public void trace(final RequestIdSupplier requestIdSupplier, final Throwable throwable, final String format,
                          final Object arg1, final Object arg2, final Object arg3, final Object arg4) {
            logger.trace(requestIdSupplier, throwable, format, arg1, arg2, arg3, arg4);
        }

        @Override
        public void trace(final RequestIdSupplier requestIdSupplier, final Throwable throwable, final String format,
                          final Object arg1, final Object arg2, final Object arg3, final Object arg4, final Object arg5) {
            logger.trace(requestIdSupplier, throwable, format, arg1, arg2, arg3, arg4, arg5);
        }

        @Override
        public void trace(final RequestIdSupplier requestIdSupplier, final Throwable throwable, final String format,
                          final Object... arguments) {
            logger.trace(requestIdSupplier, throwable, format, arguments);
        }

        @Override
        public void trace(final RequestIdSupplier requestIdSupplier, final Throwable throwable, final String format,
                          final Supplier<?> arg1) {
            logger.trace(requestIdSupplier, throwable, format, arg1);
        }

        @Override
        public void trace(final RequestIdSupplier requestIdSupplier, final Throwable throwable, final String format,
                          final Supplier<?> arg1, final Supplier<?> arg2) {
            logger.trace(requestIdSupplier, throwable, format, arg1, arg2);
        }

        @Override
        public void trace(final RequestIdSupplier requestIdSupplier, final Throwable throwable, final String format,
                          final Supplier<?> arg1, final Supplier<?> arg2, final Supplier<?> arg3) {
            logger.trace(requestIdSupplier, throwable, format, arg1, arg2, arg3);
        }

        @Override
        public void trace(final RequestIdSupplier requestIdSupplier, final Throwable throwable, final String format,
                          final Supplier<?> arg1, final Supplier<?> arg2, final Supplier<?> arg3, final Supplier<?> arg4) {
            logger.trace(requestIdSupplier, throwable, format, arg1, arg2, arg3, arg4);
        }

        @Override
        public void trace(final RequestIdSupplier requestIdSupplier, final Throwable throwable, final String format,
                          final Supplier<?> arg1, final Supplier<?> arg2, final Supplier<?> arg3, final Supplier<?> arg4, final Supplier<?> arg5) {
            logger.trace(requestIdSupplier, throwable, format, arg1, arg2, arg3, arg4, arg5);
        }

        @Override
        public void trace(final RequestIdSupplier requestIdSupplier, final Throwable throwable, final String format,
                          final Supplier<?>... suppliers) {
            logger.trace(requestIdSupplier, throwable, format, suppliers);
        }

        @Override
        public void trace(final RequestIdSupplier requestIdSupplier, final Throwable throwable, final String msg) {
            logger.trace(requestIdSupplier, throwable, msg);
        }

        @Override
        public boolean isDebugEnabled() {
            return logger.isDebugEnabled();
        }

        @Override
        public void debug(final LoggerEvent loggerEvent) {
            logger.debug(loggerEvent);
        }

        @Override
        public void debug(final String msg) {
            logger.debug(msg);
        }

        @Override
        public void debug(final String format, final Object arg1) {
            logger.debug(format, arg1);
        }

        @Override
        public void debug(final String format, final Object arg1, final Object arg2) {
            logger.debug(format, arg1, arg2);
        }

        @Override
        public void debug(final String format, final Object arg1, final Object arg2, final Object arg3) {
            logger.debug(format, arg1, arg2, arg3);
        }

        @Override
        public void debug(final String format, final Object arg1, final Object arg2, final Object arg3, final Object arg4) {
            logger.debug(format, arg1, arg2, arg3, arg4);
        }

        @Override
        public void debug(final String format, final Object arg1, final Object arg2, final Object arg3, final Object arg4,
                          final Object arg5) {
            logger.debug(format, arg1, arg2, arg3, arg4, arg5);
        }

        @Override
        public void debug(final String format, final Object... arguments) {
            logger.debug(format, arguments);
        }

        @Override
        public void debug(final String format, final Supplier<?> arg1) {
            logger.debug(format, arg1);
        }

        @Override
        public void debug(final String format, final Supplier<?> arg1, final Supplier<?> arg2) {
            logger.debug(format, arg1, arg2);
        }

        @Override
        public void debug(final String format, final Supplier<?> arg1, final Supplier<?> arg2, final Supplier<?> arg3) {
            logger.debug(format, arg1, arg2, arg3);
        }

        @Override
        public void debug(final String format, final Supplier<?> arg1, final Supplier<?> arg2, final Supplier<?> arg3,
                          final Supplier<?> arg4) {
            logger.debug(format, arg1, arg2, arg3, arg4);
        }

        @Override
        public void debug(final String format, final Supplier<?> arg1, final Supplier<?> arg2, final Supplier<?> arg3,
                          final Supplier<?> arg4, final Supplier<?> arg5) {
            logger.debug(format, arg1, arg2, arg3, arg4, arg5);
        }

        @Override
        public void debug(final String format, final Supplier<?>... suppliers) {
            logger.debug(format, suppliers);
        }

        @Override
        public void debug(final Throwable throwable, final String msg) {
            logger.debug(throwable, msg);
        }

        @Override
        public void debug(final Throwable throwable, final String format, final Object arg1) {
            logger.debug(throwable, format, arg1);
        }

        @Override
        public void debug(final Throwable throwable, final String format, final Object arg1, final Object arg2) {
            logger.debug(throwable, format, arg1, arg2);
        }

        @Override
        public void debug(final Throwable throwable, final String format, final Object arg1, final Object arg2, final Object arg3) {
            logger.debug(throwable, format, arg1, arg2, arg3);
        }

        @Override
        public void debug(final Throwable throwable, final String format, final Object arg1, final Object arg2, final Object arg3,
                          final Object arg4) {
            logger.debug(throwable, format, arg1, arg2, arg3, arg4);
        }

        @Override
        public void debug(final Throwable throwable, final String format, final Object arg1, final Object arg2, final Object arg3,
                          final Object arg4, final Object arg5) {
            logger.debug(throwable, format, arg1, arg2, arg3, arg4, arg5);
        }

        @Override
        public void debug(final Throwable throwable, final String format, final Object... arguments) {
            logger.debug(throwable, format, arguments);
        }

        @Override
        public void debug(final Throwable throwable, final String format, final Supplier<?> arg1) {
            logger.debug(throwable, format, arg1);
        }

        @Override
        public void debug(final Throwable throwable, final String format, final Supplier<?> arg1, final Supplier<?> arg2) {
            logger.debug(throwable, format, arg1, arg2);
        }

        @Override
        public void debug(final Throwable throwable, final String format, final Supplier<?> arg1, final Supplier<?> arg2,
                          final Supplier<?> arg3) {
            logger.debug(throwable, format, arg1, arg2, arg3);
        }

        @Override
        public void debug(final Throwable throwable, final String format, final Supplier<?> arg1, final Supplier<?> arg2,
                          final Supplier<?> arg3, final Supplier<?> arg4) {
            logger.debug(throwable, format, arg1, arg2, arg3, arg4);
        }

        @Override
        public void debug(final Throwable throwable, final String format, final Supplier<?> arg1, final Supplier<?> arg2,
                          final Supplier<?> arg3, final Supplier<?> arg4, final Supplier<?> arg5) {
            logger.debug(throwable, format, arg1, arg2, arg3, arg4, arg5);
        }

        @Override
        public void debug(final Throwable throwable, final String format, final Supplier<?>... suppliers) {
            logger.debug(throwable, format, suppliers);
        }

        @Override
        public void debug(final RequestIdSupplier requestIdSupplier, final String msg) {
            logger.debug(requestIdSupplier, msg);
        }

        @Override
        public void debug(final RequestIdSupplier requestIdSupplier, final String format, final Object arg1) {
            logger.debug(requestIdSupplier, format, arg1);
        }

        @Override
        public void debug(final RequestIdSupplier requestIdSupplier, final String format, final Object arg1, final Object arg2) {
            logger.debug(requestIdSupplier, format, arg1, arg2);
        }

        @Override
        public void debug(final RequestIdSupplier requestIdSupplier, final String format, final Object arg1, final Object arg2,
                          final Object arg3) {
            logger.debug(requestIdSupplier, format, arg1, arg2, arg3);
        }

        @Override
        public void debug(final RequestIdSupplier requestIdSupplier, final String format, final Object arg1, final Object arg2,
                          final Object arg3, final Object arg4) {
            logger.debug(requestIdSupplier, format, arg1, arg2, arg3, arg4);
        }

        @Override
        public void debug(final RequestIdSupplier requestIdSupplier, final String format, final Object arg1, final Object arg2,
                          final Object arg3, final Object arg4, final Object arg5) {
            logger.debug(requestIdSupplier, format, arg1, arg2, arg3, arg4, arg5);
        }

        @Override
        public void debug(final RequestIdSupplier requestIdSupplier, final String format, final Object... arguments) {
            logger.debug(requestIdSupplier, format, arguments);
        }

        @Override
        public void debug(final RequestIdSupplier requestIdSupplier, final String format, final Supplier<?> arg1) {
            logger.debug(requestIdSupplier, format, arg1);
        }

        @Override
        public void debug(final RequestIdSupplier requestIdSupplier, final String format, final Supplier<?> arg1, final Supplier<?> arg2) {
            logger.debug(requestIdSupplier, format, arg1, arg2);
        }

        @Override
        public void debug(final RequestIdSupplier requestIdSupplier, final String format, final Supplier<?> arg1, final Supplier<?> arg2,
                          final Supplier<?> arg3) {
            logger.debug(requestIdSupplier, format, arg1, arg2, arg3);
        }

        @Override
        public void debug(final RequestIdSupplier requestIdSupplier, final String format, final Supplier<?> arg1, final Supplier<?> arg2,
                          final Supplier<?> arg3, final Supplier<?> arg4) {
            logger.debug(requestIdSupplier, format, arg1, arg2, arg3, arg4);
        }

        @Override
        public void debug(final RequestIdSupplier requestIdSupplier, final String format, final Supplier<?> arg1, final Supplier<?> arg2,
                          final Supplier<?> arg3, final Supplier<?> arg4, final Supplier<?> arg5) {
            logger.debug(requestIdSupplier, format, arg1, arg2, arg3, arg4, arg5);
        }

        @Override
        public void debug(final RequestIdSupplier requestIdSupplier, final String format, final Supplier<?>... suppliers) {
            logger.debug(requestIdSupplier, format, suppliers);
        }

        @Override
        public void debug(final RequestIdSupplier requestIdSupplier, final Throwable throwable, final String format, final Object arg1) {
            logger.debug(requestIdSupplier, throwable, format, arg1);
        }

        @Override
        public void debug(final RequestIdSupplier requestIdSupplier, final Throwable throwable, final String format, final Object arg1,
                          final Object arg2) {
            logger.debug(requestIdSupplier, throwable, format, arg1, arg2);
        }

        @Override
        public void debug(final RequestIdSupplier requestIdSupplier, final Throwable throwable, final String format, final Object arg1,
                          final Object arg2, final Object arg3) {
            logger.debug(requestIdSupplier, throwable, format, arg1, arg2, arg3);
        }

        @Override
        public void debug(final RequestIdSupplier requestIdSupplier, final Throwable throwable, final String format, final Object arg1,
                          final Object arg2, final Object arg3, final Object arg4) {
            logger.debug(requestIdSupplier, throwable, format, arg1, arg2, arg3, arg4);
        }

        @Override
        public void debug(final RequestIdSupplier requestIdSupplier, final Throwable throwable, final String format, final Object arg1,
                          final Object arg2, final Object arg3, final Object arg4, final Object arg5) {
            logger.debug(requestIdSupplier, throwable, format, arg1, arg2, arg3, arg4, arg5);
        }

        @Override
        public void debug(final RequestIdSupplier requestIdSupplier, final Throwable throwable, final String format,
                          final Object... arguments) {
            logger.debug(requestIdSupplier, throwable, format, arguments);
        }

        @Override
        public void debug(final RequestIdSupplier requestIdSupplier, final Throwable throwable, final String format,
                          final Supplier<?> arg1) {
            logger.debug(requestIdSupplier, throwable, format, arg1);
        }

        @Override
        public void debug(final RequestIdSupplier requestIdSupplier, final Throwable throwable, final String format,
                          final Supplier<?> arg1, final Supplier<?> arg2) {
            logger.debug(requestIdSupplier, throwable, format, arg1, arg2);
        }

        @Override
        public void debug(final RequestIdSupplier requestIdSupplier, final Throwable throwable, final String format,
                          final Supplier<?> arg1, final Supplier<?> arg2, final Supplier<?> arg3) {
            logger.debug(requestIdSupplier, throwable, format, arg1, arg2, arg3);
        }

        @Override
        public void debug(final RequestIdSupplier requestIdSupplier, final Throwable throwable, final String format,
                          final Supplier<?> arg1, final Supplier<?> arg2, final Supplier<?> arg3, final Supplier<?> arg4) {
            logger.debug(requestIdSupplier, throwable, format, arg1, arg2, arg3, arg4);
        }

        @Override
        public void debug(final RequestIdSupplier requestIdSupplier, final Throwable throwable, final String format,
                          final Supplier<?> arg1, final Supplier<?> arg2, final Supplier<?> arg3, final Supplier<?> arg4, final Supplier<?> arg5) {
            logger.debug(requestIdSupplier, throwable, format, arg1, arg2, arg3, arg4, arg5);
        }

        @Override
        public void debug(final RequestIdSupplier requestIdSupplier, final Throwable throwable, final String format,
                          final Supplier<?>... suppliers) {
            logger.debug(requestIdSupplier, throwable, format, suppliers);
        }

        @Override
        public void debug(final RequestIdSupplier requestIdSupplier, final Throwable throwable, final String msg) {
            logger.debug(requestIdSupplier, throwable, msg);
        }

        @Override
        public boolean isInfoEnabled() {
            return logger.isInfoEnabled();
        }

        @Override
        public void info(final LoggerEvent loggerEvent) {
            logger.info(loggerEvent);
        }

        @Override
        public void info(final String msg) {
            logger.info(msg);
        }

        @Override
        public void info(final String format, final Object arg1) {
            logger.info(format, arg1);
        }

        @Override
        public void info(final String format, final Object arg1, final Object arg2) {
            logger.info(format, arg1, arg2);
        }

        @Override
        public void info(final String format, final Object arg1, final Object arg2, final Object arg3) {
            logger.info(format, arg1, arg2, arg3);
        }

        @Override
        public void info(final String format, final Object arg1, final Object arg2, final Object arg3, final Object arg4) {
            logger.info(format, arg1, arg2, arg3, arg4);
        }

        @Override
        public void info(final String format, final Object arg1, final Object arg2, final Object arg3, final Object arg4,
                         final Object arg5) {
            logger.info(format, arg1, arg2, arg3, arg4, arg5);
        }

        @Override
        public void info(final String format, final Object... arguments) {
            logger.info(format, arguments);
        }

        @Override
        public void info(final String format, final Supplier<?> arg1) {
            logger.info(format, arg1);
        }

        @Override
        public void info(final String format, final Supplier<?> arg1, final Supplier<?> arg2) {
            logger.info(format, arg1, arg2);
        }

        @Override
        public void info(final String format, final Supplier<?> arg1, final Supplier<?> arg2, final Supplier<?> arg3) {
            logger.info(format, arg1, arg2, arg3);
        }

        @Override
        public void info(final String format, final Supplier<?> arg1, final Supplier<?> arg2, final Supplier<?> arg3,
                         final Supplier<?> arg4) {
            logger.info(format, arg1, arg2, arg3, arg4);
        }

        @Override
        public void info(final String format, final Supplier<?> arg1, final Supplier<?> arg2, final Supplier<?> arg3,
                         final Supplier<?> arg4, final Supplier<?> arg5) {
            logger.info(format, arg1, arg2, arg3, arg4, arg5);
        }

        @Override
        public void info(final String format, final Supplier<?>... suppliers) {
            logger.info(format, suppliers);
        }

        @Override
        public void info(final Throwable throwable, final String msg) {
            logger.info(throwable, msg);
        }

        @Override
        public void info(final Throwable throwable, final String format, final Object arg1) {
            logger.info(throwable, format, arg1);
        }

        @Override
        public void info(final Throwable throwable, final String format, final Object arg1, final Object arg2) {
            logger.info(throwable, format, arg1, arg2);
        }

        @Override
        public void info(final Throwable throwable, final String format, final Object arg1, final Object arg2, final Object arg3) {
            logger.info(throwable, format, arg1, arg2, arg3);
        }

        @Override
        public void info(final Throwable throwable, final String format, final Object arg1, final Object arg2, final Object arg3,
                         final Object arg4) {
            logger.info(throwable, format, arg1, arg2, arg3, arg4);
        }

        @Override
        public void info(final Throwable throwable, final String format, final Object arg1, final Object arg2, final Object arg3,
                         final Object arg4, final Object arg5) {
            logger.info(throwable, format, arg1, arg2, arg3, arg4, arg5);
        }

        @Override
        public void info(final Throwable throwable, final String format, final Object... arguments) {
            logger.info(throwable, format, arguments);
        }

        @Override
        public void info(final Throwable throwable, final String format, final Supplier<?> arg1) {
            logger.info(throwable, format, arg1);
        }

        @Override
        public void info(final Throwable throwable, final String format, final Supplier<?> arg1, final Supplier<?> arg2) {
            logger.info(throwable, format, arg1, arg2);
        }

        @Override
        public void info(final Throwable throwable, final String format, final Supplier<?> arg1, final Supplier<?> arg2,
                         final Supplier<?> arg3) {
            logger.info(throwable, format, arg1, arg2, arg3);
        }

        @Override
        public void info(final Throwable throwable, final String format, final Supplier<?> arg1, final Supplier<?> arg2,
                         final Supplier<?> arg3, final Supplier<?> arg4) {
            logger.info(throwable, format, arg1, arg2, arg3, arg4);
        }

        @Override
        public void info(final Throwable throwable, final String format, final Supplier<?> arg1, final Supplier<?> arg2,
                         final Supplier<?> arg3, final Supplier<?> arg4, final Supplier<?> arg5) {
            logger.info(throwable, format, arg1, arg2, arg3, arg4, arg5);
        }

        @Override
        public void info(final Throwable throwable, final String format, final Supplier<?>... suppliers) {
            logger.info(throwable, format, suppliers);
        }

        @Override
        public void info(final RequestIdSupplier requestIdSupplier, final String msg) {
            logger.info(requestIdSupplier, msg);
        }

        @Override
        public void info(final RequestIdSupplier requestIdSupplier, final String format, final Object arg1) {
            logger.info(requestIdSupplier, format, arg1);
        }

        @Override
        public void info(final RequestIdSupplier requestIdSupplier, final String format, final Object arg1, final Object arg2) {
            logger.info(requestIdSupplier, format, arg1, arg2);
        }

        @Override
        public void info(final RequestIdSupplier requestIdSupplier, final String format, final Object arg1, final Object arg2,
                         final Object arg3) {
            logger.info(requestIdSupplier, format, arg1, arg2, arg3);
        }

        @Override
        public void info(final RequestIdSupplier requestIdSupplier, final String format, final Object arg1, final Object arg2,
                         final Object arg3, final Object arg4) {
            logger.info(requestIdSupplier, format, arg1, arg2, arg3, arg4);
        }

        @Override
        public void info(final RequestIdSupplier requestIdSupplier, final String format, final Object arg1, final Object arg2,
                         final Object arg3, final Object arg4, final Object arg5) {
            logger.info(requestIdSupplier, format, arg1, arg2, arg3, arg4, arg5);
        }

        @Override
        public void info(final RequestIdSupplier requestIdSupplier, final String format, final Object... arguments) {
            logger.info(requestIdSupplier, format, arguments);
        }

        @Override
        public void info(final RequestIdSupplier requestIdSupplier, final String format, final Supplier<?> arg1) {
            logger.info(requestIdSupplier, format, arg1);
        }

        @Override
        public void info(final RequestIdSupplier requestIdSupplier, final String format, final Supplier<?> arg1, final Supplier<?> arg2) {
            logger.info(requestIdSupplier, format, arg1, arg2);
        }

        @Override
        public void info(final RequestIdSupplier requestIdSupplier, final String format, final Supplier<?> arg1, final Supplier<?> arg2,
                         final Supplier<?> arg3) {
            logger.info(requestIdSupplier, format, arg1, arg2, arg3);
        }

        @Override
        public void info(final RequestIdSupplier requestIdSupplier, final String format, final Supplier<?> arg1, final Supplier<?> arg2,
                         final Supplier<?> arg3, final Supplier<?> arg4) {
            logger.info(requestIdSupplier, format, arg1, arg2, arg3, arg4);
        }

        @Override
        public void info(final RequestIdSupplier requestIdSupplier, final String format, final Supplier<?> arg1, final Supplier<?> arg2,
                         final Supplier<?> arg3, final Supplier<?> arg4, final Supplier<?> arg5) {
            logger.info(requestIdSupplier, format, arg1, arg2, arg3, arg4, arg5);
        }

        @Override
        public void info(final RequestIdSupplier requestIdSupplier, final String format, final Supplier<?>... suppliers) {
            logger.info(requestIdSupplier, format, suppliers);
        }

        @Override
        public void info(final RequestIdSupplier requestIdSupplier, final Throwable throwable, final String format, final Object arg1) {
            logger.info(requestIdSupplier, throwable, format, arg1);
        }

        @Override
        public void info(final RequestIdSupplier requestIdSupplier, final Throwable throwable, final String format, final Object arg1,
                         final Object arg2) {
            logger.info(requestIdSupplier, throwable, format, arg1, arg2);
        }

        @Override
        public void info(final RequestIdSupplier requestIdSupplier, final Throwable throwable, final String format, final Object arg1,
                         final Object arg2, final Object arg3) {
            logger.info(requestIdSupplier, throwable, format, arg1, arg2, arg3);
        }

        @Override
        public void info(final RequestIdSupplier requestIdSupplier, final Throwable throwable, final String format, final Object arg1,
                         final Object arg2, final Object arg3, final Object arg4) {
            logger.info(requestIdSupplier, throwable, format, arg1, arg2, arg3, arg4);
        }

        @Override
        public void info(final RequestIdSupplier requestIdSupplier, final Throwable throwable, final String format, final Object arg1,
                         final Object arg2, final Object arg3, final Object arg4, final Object arg5) {
            logger.info(requestIdSupplier, throwable, format, arg1, arg2, arg3, arg4, arg5);
        }

        @Override
        public void info(final RequestIdSupplier requestIdSupplier, final Throwable throwable, final String format,
                         final Object... arguments) {
            logger.info(requestIdSupplier, throwable, format, arguments);
        }

        @Override
        public void info(final RequestIdSupplier requestIdSupplier, final Throwable throwable, final String format,
                         final Supplier<?> arg1) {
            logger.info(requestIdSupplier, throwable, format, arg1);
        }

        @Override
        public void info(final RequestIdSupplier requestIdSupplier, final Throwable throwable, final String format,
                         final Supplier<?> arg1, final Supplier<?> arg2) {
            logger.info(requestIdSupplier, throwable, format, arg1, arg2);
        }

        @Override
        public void info(final RequestIdSupplier requestIdSupplier, final Throwable throwable, final String format,
                         final Supplier<?> arg1, final Supplier<?> arg2, final Supplier<?> arg3) {
            logger.info(requestIdSupplier, throwable, format, arg1, arg2, arg3);
        }

        @Override
        public void info(final RequestIdSupplier requestIdSupplier, final Throwable throwable, final String format,
                         final Supplier<?> arg1, final Supplier<?> arg2, final Supplier<?> arg3, final Supplier<?> arg4) {
            logger.info(requestIdSupplier, throwable, format, arg1, arg2, arg3, arg4);
        }

        @Override
        public void info(final RequestIdSupplier requestIdSupplier, final Throwable throwable, final String format,
                         final Supplier<?> arg1, final Supplier<?> arg2, final Supplier<?> arg3, final Supplier<?> arg4,
                         final Supplier<?> arg5) {
            logger.info(requestIdSupplier, throwable, format, arg1, arg2, arg3, arg4, arg5);
        }

        @Override
        public void info(final RequestIdSupplier requestIdSupplier, final Throwable throwable, final String format,
                         final Supplier<?>... suppliers) {
            logger.info(requestIdSupplier, throwable, format, suppliers);
        }

        @Override
        public void info(final RequestIdSupplier requestIdSupplier, final Throwable throwable, final String msg) {
            logger.info(requestIdSupplier, throwable, msg);
        }

        @Override
        public boolean isWarnEnabled() {
            return logger.isWarnEnabled();
        }

        @Override
        public void warn(final LoggerEvent loggerEvent) {
            logger.warn(loggerEvent);
        }

        @Override
        public void warn(final String msg) {
            logger.warn(msg);
        }

        @Override
        public void warn(final String format, final Object arg1) {
            logger.warn(format, arg1);
        }

        @Override
        public void warn(final String format, final Object arg1, final Object arg2) {
            logger.warn(format, arg1, arg2);
        }

        @Override
        public void warn(final String format, final Object arg1, final Object arg2, final Object arg3) {
            logger.warn(format, arg1, arg2, arg3);
        }

        @Override
        public void warn(final String format, final Object arg1, final Object arg2, final Object arg3, final Object arg4) {
            logger.warn(format, arg1, arg2, arg3, arg4);
        }

        @Override
        public void warn(final String format, final Object arg1, final Object arg2, final Object arg3, final Object arg4,
                         final Object arg5) {
            logger.warn(format, arg1, arg2, arg3, arg4, arg5);
        }

        @Override
        public void warn(final String format, final Object... arguments) {
            logger.warn(format, arguments);
        }

        @Override
        public void warn(final String format, final Supplier<?> arg1) {
            logger.warn(format, arg1);
        }

        @Override
        public void warn(final String format, final Supplier<?> arg1, final Supplier<?> arg2) {
            logger.warn(format, arg1, arg2);
        }

        @Override
        public void warn(final String format, final Supplier<?> arg1, final Supplier<?> arg2, final Supplier<?> arg3) {
            logger.warn(format, arg1, arg2, arg3);
        }

        @Override
        public void warn(final String format, final Supplier<?> arg1, final Supplier<?> arg2, final Supplier<?> arg3,
                         final Supplier<?> arg4) {
            logger.warn(format, arg1, arg2, arg3, arg4);
        }

        @Override
        public void warn(final String format, final Supplier<?> arg1, final Supplier<?> arg2, final Supplier<?> arg3,
                         final Supplier<?> arg4, final Supplier<?> arg5) {
            logger.warn(format, arg1, arg2, arg3, arg4, arg5);
        }

        @Override
        public void warn(final String format, final Supplier<?>... suppliers) {
            logger.warn(format, suppliers);
        }

        @Override
        public void warn(final Throwable throwable, final String msg) {
            logger.warn(throwable, msg);
        }

        @Override
        public void warn(final Throwable throwable, final String format, final Object arg1) {
            logger.warn(throwable, format, arg1);
        }

        @Override
        public void warn(final Throwable throwable, final String format, final Object arg1, final Object arg2) {
            logger.warn(throwable, format, arg1, arg2);
        }

        @Override
        public void warn(final Throwable throwable, final String format, final Object arg1, final Object arg2, final Object arg3) {
            logger.warn(throwable, format, arg1, arg2, arg3);
        }

        @Override
        public void warn(final Throwable throwable, final String format, final Object arg1, final Object arg2, final Object arg3,
                         final Object arg4) {
            logger.warn(throwable, format, arg1, arg2, arg3, arg4);
        }

        @Override
        public void warn(final Throwable throwable, final String format, final Object arg1, final Object arg2, final Object arg3,
                         final Object arg4, final Object arg5) {
            logger.warn(throwable, format, arg1, arg2, arg3, arg4, arg5);
        }

        @Override
        public void warn(final Throwable throwable, final String format, final Object... arguments) {
            logger.warn(throwable, format, arguments);
        }

        @Override
        public void warn(final Throwable throwable, final String format, final Supplier<?> arg1) {
            logger.warn(throwable, format, arg1);
        }

        @Override
        public void warn(final Throwable throwable, final String format, final Supplier<?> arg1, final Supplier<?> arg2) {
            logger.warn(throwable, format, arg1, arg2);
        }

        @Override
        public void warn(final Throwable throwable, final String format, final Supplier<?> arg1, final Supplier<?> arg2,
                         final Supplier<?> arg3) {
            logger.warn(throwable, format, arg1, arg2, arg3);
        }

        @Override
        public void warn(final Throwable throwable, final String format, final Supplier<?> arg1, final Supplier<?> arg2,
                         final Supplier<?> arg3, final Supplier<?> arg4) {
            logger.warn(throwable, format, arg1, arg2, arg3, arg4);
        }

        @Override
        public void warn(final Throwable throwable, final String format, final Supplier<?> arg1, final Supplier<?> arg2,
                         final Supplier<?> arg3, final Supplier<?> arg4, final Supplier<?> arg5) {
            logger.warn(throwable, format, arg1, arg2, arg3, arg4, arg5);
        }

        @Override
        public void warn(final Throwable throwable, final String format, final Supplier<?>... suppliers) {
            logger.warn(throwable, format, suppliers);
        }

        @Override
        public void warn(final RequestIdSupplier requestIdSupplier, final String msg) {
            logger.warn(requestIdSupplier, msg);
        }

        @Override
        public void warn(final RequestIdSupplier requestIdSupplier, final String format, final Object arg1) {
            logger.warn(requestIdSupplier, format, arg1);
        }

        @Override
        public void warn(final RequestIdSupplier requestIdSupplier, final String format, final Object arg1, final Object arg2) {
            logger.warn(requestIdSupplier, format, arg1, arg2);
        }

        @Override
        public void warn(final RequestIdSupplier requestIdSupplier, final String format, final Object arg1, final Object arg2,
                         final Object arg3) {
            logger.warn(requestIdSupplier, format, arg1, arg2, arg3);
        }

        @Override
        public void warn(final RequestIdSupplier requestIdSupplier, final String format, final Object arg1, final Object arg2,
                         final Object arg3, final Object arg4) {
            logger.warn(requestIdSupplier, format, arg1, arg2, arg3, arg4);
        }

        @Override
        public void warn(final RequestIdSupplier requestIdSupplier, final String format, final Object arg1, final Object arg2,
                         final Object arg3, final Object arg4, final Object arg5) {
            logger.warn(requestIdSupplier, format, arg1, arg2, arg3, arg4, arg5);
        }

        @Override
        public void warn(final RequestIdSupplier requestIdSupplier, final String format, final Object... arguments) {
            logger.warn(requestIdSupplier, format, arguments);
        }

        @Override
        public void warn(final RequestIdSupplier requestIdSupplier, final String format, final Supplier<?> arg1) {
            logger.warn(requestIdSupplier, format, arg1);
        }

        @Override
        public void warn(final RequestIdSupplier requestIdSupplier, final String format, final Supplier<?> arg1,
                         final Supplier<?> arg2) {
            logger.warn(requestIdSupplier, format, arg1, arg2);
        }

        @Override
        public void warn(final RequestIdSupplier requestIdSupplier, final String format, final Supplier<?> arg1,
                         final Supplier<?> arg2, final Supplier<?> arg3) {
            logger.warn(requestIdSupplier, format, arg1, arg2, arg3);
        }

        @Override
        public void warn(final RequestIdSupplier requestIdSupplier, final String format, final Supplier<?> arg1,
                         final Supplier<?> arg2, final Supplier<?> arg3, final Supplier<?> arg4) {
            logger.warn(requestIdSupplier, format, arg1, arg2, arg3, arg4);
        }

        @Override
        public void warn(final RequestIdSupplier requestIdSupplier, final String format, final Supplier<?> arg1,
                         final Supplier<?> arg2, final Supplier<?> arg3, final Supplier<?> arg4, final Supplier<?> arg5) {
            logger.warn(requestIdSupplier, format, arg1, arg2, arg3, arg4, arg5);
        }

        @Override
        public void warn(final RequestIdSupplier requestIdSupplier, final String format, final Supplier<?>... suppliers) {
            logger.warn(requestIdSupplier, format, suppliers);
        }

        @Override
        public void warn(final RequestIdSupplier requestIdSupplier, final Throwable throwable, final String format,
                         final Object arg1) {
            logger.warn(requestIdSupplier, throwable, format, arg1);
        }

        @Override
        public void warn(final RequestIdSupplier requestIdSupplier, final Throwable throwable, final String format,
                         final Object arg1, final Object arg2) {
            logger.warn(requestIdSupplier, throwable, format, arg1, arg2);
        }

        @Override
        public void warn(final RequestIdSupplier requestIdSupplier, final Throwable throwable, final String format,
                         final Object arg1, final Object arg2, final Object arg3) {
            logger.warn(requestIdSupplier, throwable, format, arg1, arg2, arg3);
        }

        @Override
        public void warn(final RequestIdSupplier requestIdSupplier, final Throwable throwable, final String format,
                         final Object arg1, final Object arg2, final Object arg3, final Object arg4) {
            logger.warn(requestIdSupplier, throwable, format, arg1, arg2, arg3, arg4);
        }

        @Override
        public void warn(final RequestIdSupplier requestIdSupplier, final Throwable throwable, final String format,
                         final Object arg1, final Object arg2, final Object arg3, final Object arg4, final Object arg5) {
            logger.warn(requestIdSupplier, throwable, format, arg1, arg2, arg3, arg4, arg5);
        }

        @Override
        public void warn(final RequestIdSupplier requestIdSupplier, final Throwable throwable, final String format,
                         final Object... arguments) {
            logger.warn(requestIdSupplier, throwable, format, arguments);
        }

        @Override
        public void warn(final RequestIdSupplier requestIdSupplier, final Throwable throwable, final String format,
                         final Supplier<?> arg1) {
            logger.warn(requestIdSupplier, throwable, format, arg1);
        }

        @Override
        public void warn(final RequestIdSupplier requestIdSupplier, final Throwable throwable, final String format,
                         final Supplier<?> arg1, final Supplier<?> arg2) {
            logger.warn(requestIdSupplier, throwable, format, arg1, arg2);
        }

        @Override
        public void warn(final RequestIdSupplier requestIdSupplier, final Throwable throwable, final String format,
                         final Supplier<?> arg1, final Supplier<?> arg2, final Supplier<?> arg3) {
            logger.warn(requestIdSupplier, throwable, format, arg1, arg2, arg3);
        }

        @Override
        public void warn(final RequestIdSupplier requestIdSupplier, final Throwable throwable, final String format,
                         final Supplier<?> arg1, final Supplier<?> arg2, final Supplier<?> arg3, final Supplier<?> arg4) {
            logger.warn(requestIdSupplier, throwable, format, arg1, arg2, arg3, arg4);
        }

        @Override
        public void warn(final RequestIdSupplier requestIdSupplier, final Throwable throwable, final String format,
                         final Supplier<?> arg1, final Supplier<?> arg2, final Supplier<?> arg3, final Supplier<?> arg4, final Supplier<?> arg5) {
            logger.warn(requestIdSupplier, throwable, format, arg1, arg2, arg3, arg4, arg5);
        }

        @Override
        public void warn(final RequestIdSupplier requestIdSupplier, final Throwable throwable, final String format,
                         final Supplier<?>... suppliers) {
            logger.warn(requestIdSupplier, throwable, format, suppliers);
        }

        @Override
        public void warn(final RequestIdSupplier requestIdSupplier, final Throwable throwable, final String msg) {
            logger.warn(requestIdSupplier, throwable, msg);
        }

        @Override
        public boolean isErrorEnabled() {
            return logger.isErrorEnabled();
        }

        @Override
        public void error(final LoggerEvent loggerEvent) {
            logger.error(loggerEvent);
        }

        @Override
        public void error(final String msg) {
            logger.error(msg);
        }

        @Override
        public void error(final String format, final Object arg1) {
            logger.error(format, arg1);
        }

        @Override
        public void error(final String format, final Object arg1, final Object arg2) {
            logger.error(format, arg1, arg2);
        }

        @Override
        public void error(final String format, final Object arg1, final Object arg2, final Object arg3) {
            logger.error(format, arg1, arg2, arg3);
        }

        @Override
        public void error(final String format, final Object arg1, final Object arg2, final Object arg3, final Object arg4) {
            logger.error(format, arg1, arg2, arg3, arg4);
        }

        @Override
        public void error(final String format, final Object arg1, final Object arg2, final Object arg3, final Object arg4,
                          final Object arg5) {
            logger.error(format, arg1, arg2, arg3, arg4, arg5);
        }

        @Override
        public void error(final String format, final Object... arguments) {
            logger.error(format, arguments);
        }

        @Override
        public void error(final String format, final Supplier<?> arg1) {
            logger.error(format, arg1);
        }

        @Override
        public void error(final String format, final Supplier<?> arg1, final Supplier<?> arg2) {
            logger.error(format, arg1, arg2);
        }

        @Override
        public void error(final String format, final Supplier<?> arg1, final Supplier<?> arg2, final Supplier<?> arg3) {
            logger.error(format, arg1, arg2, arg3);
        }

        @Override
        public void error(final String format, final Supplier<?> arg1, final Supplier<?> arg2, final Supplier<?> arg3,
                          final Supplier<?> arg4) {
            logger.error(format, arg1, arg2, arg3, arg4);
        }

        @Override
        public void error(final String format, final Supplier<?> arg1, final Supplier<?> arg2, final Supplier<?> arg3,
                          final Supplier<?> arg4, final Supplier<?> arg5) {
            logger.error(format, arg1, arg2, arg3, arg4, arg5);
        }

        @Override
        public void error(final String format, final Supplier<?>... suppliers) {
            logger.error(format, suppliers);
        }

        @Override
        public void error(final Throwable throwable, final String msg) {
            logger.error(throwable, msg);
        }

        @Override
        public void error(final Throwable throwable, final String format, final Object arg1) {
            logger.error(throwable, format, arg1);
        }

        @Override
        public void error(final Throwable throwable, final String format, final Object arg1, final Object arg2) {
            logger.error(throwable, format, arg1, arg2);
        }

        @Override
        public void error(final Throwable throwable, final String format, final Object arg1, final Object arg2, final Object arg3) {
            logger.error(throwable, format, arg1, arg2, arg3);
        }

        @Override
        public void error(final Throwable throwable, final String format, final Object arg1, final Object arg2, final Object arg3,
                          final Object arg4) {
            logger.error(throwable, format, arg1, arg2, arg3, arg4);
        }

        @Override
        public void error(final Throwable throwable, final String format, final Object arg1, final Object arg2, final Object arg3,
                          final Object arg4, final Object arg5) {
            logger.error(throwable, format, arg1, arg2, arg3, arg4, arg5);
        }

        @Override
        public void error(final Throwable throwable, final String format, final Object... arguments) {
            logger.error(throwable, format, arguments);
        }

        @Override
        public void error(final Throwable throwable, final String format, final Supplier<?> arg1) {
            logger.error(throwable, format, arg1);
        }

        @Override
        public void error(final Throwable throwable, final String format, final Supplier<?> arg1, final Supplier<?> arg2) {
            logger.error(throwable, format, arg1, arg2);
        }

        @Override
        public void error(final Throwable throwable, final String format, final Supplier<?> arg1, final Supplier<?> arg2,
                          final Supplier<?> arg3) {
            logger.error(throwable, format, arg1, arg2, arg3);
        }

        @Override
        public void error(final Throwable throwable, final String format, final Supplier<?> arg1, final Supplier<?> arg2,
                          final Supplier<?> arg3, final Supplier<?> arg4) {
            logger.error(throwable, format, arg1, arg2, arg3, arg4);
        }

        @Override
        public void error(final Throwable throwable, final String format, final Supplier<?> arg1, final Supplier<?> arg2,
                          final Supplier<?> arg3, final Supplier<?> arg4, final Supplier<?> arg5) {
            logger.error(throwable, format, arg1, arg2, arg3, arg4, arg5);
        }

        @Override
        public void error(final Throwable throwable, final String format, final Supplier<?>... suppliers) {
            logger.error(throwable, format, suppliers);
        }

        @Override
        public void error(final RequestIdSupplier requestIdSupplier, final String msg) {
            logger.error(requestIdSupplier, msg);
        }

        @Override
        public void error(final RequestIdSupplier requestIdSupplier, final String format, final Object arg1) {
            logger.error(requestIdSupplier, format, arg1);
        }

        @Override
        public void error(final RequestIdSupplier requestIdSupplier, final String format, final Object arg1, final Object arg2) {
            logger.error(requestIdSupplier, format, arg1, arg2);
        }

        @Override
        public void error(final RequestIdSupplier requestIdSupplier, final String format, final Object arg1, final Object arg2,
                          final Object arg3) {
            logger.error(requestIdSupplier, format, arg1, arg2, arg3);
        }

        @Override
        public void error(final RequestIdSupplier requestIdSupplier, final String format, final Object arg1, final Object arg2,
                          final Object arg3, final Object arg4) {
            logger.error(requestIdSupplier, format, arg1, arg2, arg3, arg4);
        }

        @Override
        public void error(final RequestIdSupplier requestIdSupplier, final String format, final Object arg1, final Object arg2,
                          final Object arg3, final Object arg4, final Object arg5) {
            logger.error(requestIdSupplier, format, arg1, arg2, arg3, arg4, arg5);
        }

        @Override
        public void error(final RequestIdSupplier requestIdSupplier, final String format, final Object... arguments) {
            logger.error(requestIdSupplier, format, arguments);
        }

        @Override
        public void error(final RequestIdSupplier requestIdSupplier, final String format, final Supplier<?> arg1) {
            logger.error(requestIdSupplier, format, arg1);
        }

        @Override
        public void error(final RequestIdSupplier requestIdSupplier, final String format, final Supplier<?> arg1,
                          final Supplier<?> arg2) {
            logger.error(requestIdSupplier, format, arg1, arg2);
        }

        @Override
        public void error(final RequestIdSupplier requestIdSupplier, final String format, final Supplier<?> arg1,
                          final Supplier<?> arg2, final Supplier<?> arg3) {
            logger.error(requestIdSupplier, format, arg1, arg2, arg3);
        }

        @Override
        public void error(final RequestIdSupplier requestIdSupplier, final String format, final Supplier<?> arg1,
                          final Supplier<?> arg2, final Supplier<?> arg3, final Supplier<?> arg4) {
            logger.error(requestIdSupplier, format, arg1, arg2, arg3, arg4);
        }

        @Override
        public void error(final RequestIdSupplier requestIdSupplier, final String format, final Supplier<?> arg1,
                          final Supplier<?> arg2, final Supplier<?> arg3, final Supplier<?> arg4, final Supplier<?> arg5) {
            logger.error(requestIdSupplier, format, arg1, arg2, arg3, arg4, arg5);
        }

        @Override
        public void error(final RequestIdSupplier requestIdSupplier, final String format, final Supplier<?>... suppliers) {
            logger.error(requestIdSupplier, format, suppliers);
        }

        @Override
        public void error(final RequestIdSupplier requestIdSupplier, final Throwable throwable, final String format,
                          final Object arg1) {
            logger.error(requestIdSupplier, throwable, format, arg1);
        }

        @Override
        public void error(final RequestIdSupplier requestIdSupplier, final Throwable throwable, final String format,
                          final Object arg1, final Object arg2) {
            logger.error(requestIdSupplier, throwable, format, arg1, arg2);
        }

        @Override
        public void error(final RequestIdSupplier requestIdSupplier, final Throwable throwable, final String format,
                          final Object arg1, final Object arg2, final Object arg3) {
            logger.error(requestIdSupplier, throwable, format, arg1, arg2, arg3);
        }

        @Override
        public void error(final RequestIdSupplier requestIdSupplier, final Throwable throwable, final String format,
                          final Object arg1, final Object arg2, final Object arg3, final Object arg4) {
            logger.error(requestIdSupplier, throwable, format, arg1, arg2, arg3, arg4);
        }

        @Override
        public void error(final RequestIdSupplier requestIdSupplier, final Throwable throwable, final String format,
                          final Object arg1, final Object arg2, final Object arg3, final Object arg4, final Object arg5) {
            logger.error(requestIdSupplier, throwable, format, arg1, arg2, arg3, arg4, arg5);
        }

        @Override
        public void error(final RequestIdSupplier requestIdSupplier, final Throwable throwable, final String format,
                          final Object... arguments) {
            logger.error(requestIdSupplier, throwable, format, arguments);
        }

        @Override
        public void error(final RequestIdSupplier requestIdSupplier, final Throwable throwable, final String format,
                          final Supplier<?> arg1) {
            logger.error(requestIdSupplier, throwable, format, arg1);
        }

        @Override
        public void error(final RequestIdSupplier requestIdSupplier, final Throwable throwable, final String format,
                          final Supplier<?> arg1, final Supplier<?> arg2) {
            logger.error(requestIdSupplier, throwable, format, arg1, arg2);
        }

        @Override
        public void error(final RequestIdSupplier requestIdSupplier, final Throwable throwable, final String format,
                          final Supplier<?> arg1, final Supplier<?> arg2, final Supplier<?> arg3) {
            logger.error(requestIdSupplier, throwable, format, arg1, arg2, arg3);
        }

        @Override
        public void error(final RequestIdSupplier requestIdSupplier, final Throwable throwable, final String format,
                          final Supplier<?> arg1, final Supplier<?> arg2, final Supplier<?> arg3, final Supplier<?> arg4) {
            logger.error(requestIdSupplier, throwable, format, arg1, arg2, arg3, arg4);
        }

        @Override
        public void error(final RequestIdSupplier requestIdSupplier, final Throwable throwable, final String format,
                          final Supplier<?> arg1, final Supplier<?> arg2, final Supplier<?> arg3, final Supplier<?> arg4,
                          final Supplier<?> arg5) {
            logger.error(requestIdSupplier, throwable, format, arg1, arg2, arg3, arg4, arg5);
        }

        @Override
        public void error(final RequestIdSupplier requestIdSupplier, final Throwable throwable, final String format,
                          final Supplier<?>... suppliers) {
            logger.error(requestIdSupplier, throwable, format, suppliers);
        }

        @Override
        public void error(final RequestIdSupplier requestIdSupplier, final Throwable throwable, final String msg) {
            logger.error(requestIdSupplier, throwable, msg);
        }
    }
}
