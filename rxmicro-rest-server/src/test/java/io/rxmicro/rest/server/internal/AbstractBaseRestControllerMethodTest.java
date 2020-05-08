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

import static io.rxmicro.logger.impl.LoggerImplProviderFactory.getLoggerImplFactory;
import static io.rxmicro.logger.impl.LoggerImplProviderFactory.setLoggerImplFactory;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
abstract class AbstractBaseRestControllerMethodTest {

    static {
        setLoggerImplFactory(new MockLoggerImplProvider());
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
        ((MockLoggerImplProvider) getLoggerImplFactory()).setLogger(logger);
    }

    /**
     * @author nedis
     * @link https://rxmicro.io
     * @since 0.1
     */
    private static final class MockLoggerImplProvider implements LoggerImplProvider {

        private final ProxyLogger logger = new ProxyLogger();

        private void setLogger(final Logger logger) {
            this.logger.setLogger(logger);
        }

        @Override
        public void setup() {
        }

        @Override
        public Logger getLogger(final String name) {
            return logger;
        }
    }

    /**
     * @author nedis
     * @link https://rxmicro.io
     * @since 0.1
     */
    private static final class ProxyLogger implements Logger {

        private Logger logger;

        private void setLogger(final Logger logger) {
            this.logger = logger;
        }

        @Override
        public boolean isTraceEnabled() {
            return logger.isTraceEnabled();
        }

        @Override
        public void trace(final String msg) {
            logger.trace(msg);
        }

        @Override
        public void trace(final String msg, final Object arg1) {
            logger.trace(msg, arg1);
        }

        @Override
        public void trace(final String msg, final Object arg1, final Object arg2) {
            logger.trace(msg, arg1, arg2);
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
        public void trace(final String format, final Object arg1, final Object arg2, final Object arg3, final Object arg4, final Object arg5) {
            logger.trace(format, arg1, arg2, arg3, arg4, arg5);
        }

        @Override
        public void trace(final String format, final Object... arguments) {
            logger.trace(format, arguments);
        }

        @Override
        public void trace(final String msg, final Supplier<?> arg1) {
            logger.trace(msg, arg1);
        }

        @Override
        public void trace(final String msg, final Supplier<?> arg1, final Supplier<?> arg2) {
            logger.trace(msg, arg1, arg2);
        }

        @Override
        public void trace(final String msg, final Supplier<?> arg1, final Supplier<?> arg2, final Supplier<?> arg3) {
            logger.trace(msg, arg1, arg2, arg3);
        }

        @Override
        public void trace(final String msg, final Supplier<?> arg1, final Supplier<?> arg2, final Supplier<?> arg3, final Supplier<?> arg4) {
            logger.trace(msg, arg1, arg2, arg3, arg4);
        }

        @Override
        public void trace(final String msg, final Supplier<?> arg1, final Supplier<?> arg2, final Supplier<?> arg3, final Supplier<?> arg4, final Supplier<?> arg5) {
            logger.trace(msg, arg1, arg2, arg3, arg4, arg5);
        }

        @Override
        public void trace(final String msg, final Supplier<?>... suppliers) {
            logger.trace(msg, suppliers);
        }

        @Override
        public void trace(final Throwable throwable, final String msg, final Object arg1) {
            logger.trace(throwable, msg, arg1);
        }

        @Override
        public void trace(final Throwable throwable, final String msg, final Object arg1, final Object arg2) {
            logger.trace(throwable, msg, arg1, arg2);
        }

        @Override
        public void trace(final Throwable throwable, final String msg, final Object arg1, final Object arg2, final Object arg3) {
            logger.trace(throwable, msg, arg1, arg2, arg3);
        }

        @Override
        public void trace(final Throwable throwable, final String msg, final Object arg1, final Object arg2, final Object arg3, final Object arg4) {
            logger.trace(throwable, msg, arg1, arg2, arg3, arg4);
        }

        @Override
        public void trace(final Throwable throwable, final String msg, final Object arg1, final Object arg2, final Object arg3, final Object arg4, final Object arg5) {
            logger.trace(throwable, msg, arg1, arg2, arg3, arg4, arg5);
        }

        @Override
        public void trace(final Throwable throwable, final String msg, final Object... arguments) {
            logger.trace(throwable, msg, arguments);
        }

        @Override
        public void trace(final Throwable throwable, final String msg, final Supplier<?> arg1) {
            logger.trace(throwable, msg, arg1);
        }

        @Override
        public void trace(final Throwable throwable, final String msg, final Supplier<?> arg1, final Supplier<?> arg2) {
            logger.trace(throwable, msg, arg1, arg2);
        }

        @Override
        public void trace(final Throwable throwable, final String msg, final Supplier<?> arg1, final Supplier<?> arg2, final Supplier<?> arg3) {
            logger.trace(throwable, msg, arg1, arg2, arg3);
        }

        @Override
        public void trace(final Throwable throwable, final String msg, final Supplier<?> arg1, final Supplier<?> arg2, final Supplier<?> arg3, final Supplier<?> arg4) {
            logger.trace(throwable, msg, arg1, arg2, arg3, arg4);
        }

        @Override
        public void trace(final Throwable throwable, final String msg, final Supplier<?> arg1, final Supplier<?> arg2, final Supplier<?> arg3, final Supplier<?> arg4, final Supplier<?> arg5) {
            logger.trace(throwable, msg, arg1, arg2, arg3, arg4, arg5);
        }

        @Override
        public void trace(final Throwable throwable, final String msg, final Supplier<?>... suppliers) {
            logger.trace(throwable, msg, suppliers);
        }

        @Override
        public boolean isDebugEnabled() {
            return logger.isDebugEnabled();
        }

        @Override
        public void debug(final String msg) {
            logger.debug(msg);
        }

        @Override
        public void debug(final String msg, final Object arg1) {
            logger.debug(msg, arg1);
        }

        @Override
        public void debug(final String msg, final Object arg1, final Object arg2) {
            logger.debug(msg, arg1, arg2);
        }

        @Override
        public void debug(final String msg, final Object arg1, final Object arg2, final Object arg3) {
            logger.debug(msg, arg1, arg2, arg3);
        }

        @Override
        public void debug(final String msg, final Object arg1, final Object arg2, final Object arg3, final Object arg4) {
            logger.debug(msg, arg1, arg2, arg3, arg4);
        }

        @Override
        public void debug(final String msg, final Object arg1, final Object arg2, final Object arg3, final Object arg4, final Object arg5) {
            logger.debug(msg, arg1, arg2, arg3, arg4, arg5);
        }

        @Override
        public void debug(final String msg, final Object... arguments) {
            logger.debug(msg, arguments);
        }

        @Override
        public void debug(final String msg, final Supplier<?> arg1) {
            logger.debug(msg, arg1);
        }

        @Override
        public void debug(final String msg, final Supplier<?> arg1, final Supplier<?> arg2) {
            logger.debug(msg, arg1, arg2);
        }

        @Override
        public void debug(final String msg, final Supplier<?> arg1, final Supplier<?> arg2, final Supplier<?> arg3) {
            logger.debug(msg, arg1, arg2, arg3);
        }

        @Override
        public void debug(final String msg, final Supplier<?> arg1, final Supplier<?> arg2, final Supplier<?> arg3, final Supplier<?> arg4) {
            logger.debug(msg, arg1, arg2, arg3, arg4);
        }

        @Override
        public void debug(final String msg, final Supplier<?> arg1, final Supplier<?> arg2, final Supplier<?> arg3, final Supplier<?> arg4, final Supplier<?> arg5) {
            logger.debug(msg, arg1, arg2, arg3, arg4, arg5);
        }

        @Override
        public void debug(final String msg, final Supplier<?>... suppliers) {
            logger.debug(msg, suppliers);
        }

        @Override
        public void debug(final Throwable throwable, final String msg, final Object arg1) {
            logger.debug(throwable, msg, arg1);
        }

        @Override
        public void debug(final Throwable throwable, final String msg, final Object arg1, final Object arg2) {
            logger.debug(throwable, msg, arg1, arg2);
        }

        @Override
        public void debug(final Throwable throwable, final String msg, final Object arg1, final Object arg2, final Object arg3) {
            logger.debug(throwable, msg, arg1, arg2, arg3);
        }

        @Override
        public void debug(final Throwable throwable, final String msg, final Object arg1, final Object arg2, final Object arg3, final Object arg4) {
            logger.debug(throwable, msg, arg1, arg2, arg3, arg4);
        }

        @Override
        public void debug(final Throwable throwable, final String msg, final Object arg1, final Object arg2, final Object arg3, final Object arg4, final Object arg5) {
            logger.debug(throwable, msg, arg1, arg2, arg3, arg4, arg5);
        }

        @Override
        public void debug(final Throwable throwable, final String msg, final Object... arguments) {
            logger.debug(throwable, msg, arguments);
        }

        @Override
        public void debug(final Throwable throwable, final String msg, final Supplier<?> arg1) {
            logger.debug(throwable, msg, arg1);
        }

        @Override
        public void debug(final Throwable throwable, final String msg, final Supplier<?> arg1, final Supplier<?> arg2) {
            logger.debug(throwable, msg, arg1, arg2);
        }

        @Override
        public void debug(final Throwable throwable, final String msg, final Supplier<?> arg1, final Supplier<?> arg2, final Supplier<?> arg3) {
            logger.debug(throwable, msg, arg1, arg2, arg3);
        }

        @Override
        public void debug(final Throwable throwable, final String msg, final Supplier<?> arg1, final Supplier<?> arg2, final Supplier<?> arg3, final Supplier<?> arg4) {
            logger.debug(throwable, msg, arg1, arg2, arg3, arg4);
        }

        @Override
        public void debug(final Throwable throwable, final String msg, final Supplier<?> arg1, final Supplier<?> arg2, final Supplier<?> arg3, final Supplier<?> arg4, final Supplier<?> arg5) {
            logger.debug(throwable, msg, arg1, arg2, arg3, arg4, arg5);
        }

        @Override
        public void debug(final Throwable throwable, final String msg, final Supplier<?>... suppliers) {
            logger.debug(throwable, msg, suppliers);
        }

        @Override
        public boolean isInfoEnabled() {
            return logger.isInfoEnabled();
        }

        @Override
        public void info(final String msg) {
            logger.info(msg);
        }

        @Override
        public void info(final String msg, final Object arg1) {
            logger.info(msg, arg1);
        }

        @Override
        public void info(final String msg, final Object arg1, final Object arg2) {
            logger.info(msg, arg1, arg2);
        }

        @Override
        public void info(final String msg, final Object arg1, final Object arg2, final Object arg3) {
            logger.info(msg, arg1, arg2, arg3);
        }

        @Override
        public void info(final String msg, final Object arg1, final Object arg2, final Object arg3, final Object arg4) {
            logger.info(msg, arg1, arg2, arg3, arg4);
        }

        @Override
        public void info(final String msg, final Object arg1, final Object arg2, final Object arg3, final Object arg4, final Object arg5) {
            logger.info(msg, arg1, arg2, arg3, arg4, arg5);
        }

        @Override
        public void info(final String msg, final Object... arguments) {
            logger.info(msg, arguments);
        }

        @Override
        public void info(final String msg, final Supplier<?> arg1) {
            logger.info(msg, arg1);
        }

        @Override
        public void info(final String msg, final Supplier<?> arg1, final Supplier<?> arg2) {
            logger.info(msg, arg1, arg2);
        }

        @Override
        public void info(final String msg, final Supplier<?> arg1, final Supplier<?> arg2, final Supplier<?> arg3) {
            logger.info(msg, arg1, arg2, arg3);
        }

        @Override
        public void info(final String msg, final Supplier<?> arg1, final Supplier<?> arg2, final Supplier<?> arg3, final Supplier<?> arg4) {
            logger.info(msg, arg1, arg2, arg3, arg4);
        }

        @Override
        public void info(final String msg, final Supplier<?> arg1, final Supplier<?> arg2, final Supplier<?> arg3, final Supplier<?> arg4, final Supplier<?> arg5) {
            logger.info(msg, arg1, arg2, arg3, arg4, arg5);
        }

        @Override
        public void info(final String msg, final Supplier<?>... suppliers) {
            logger.info(msg, suppliers);
        }

        @Override
        public void info(final Throwable throwable, final String msg, final Object arg1) {
            logger.info(throwable, msg, arg1);
        }

        @Override
        public void info(final Throwable throwable, final String msg, final Object arg1, final Object arg2) {
            logger.info(throwable, msg, arg1, arg2);
        }

        @Override
        public void info(final Throwable throwable, final String msg, final Object arg1, final Object arg2, final Object arg3) {
            logger.info(throwable, msg, arg1, arg2, arg3);
        }

        @Override
        public void info(final Throwable throwable, final String msg, final Object arg1, final Object arg2, final Object arg3, final Object arg4) {
            logger.info(throwable, msg, arg1, arg2, arg3, arg4);
        }

        @Override
        public void info(final Throwable throwable, final String msg, final Object arg1, final Object arg2, final Object arg3, final Object arg4, final Object arg5) {
            logger.info(throwable, msg, arg1, arg2, arg3, arg4, arg5);
        }

        @Override
        public void info(final Throwable throwable, final String msg, final Object... arguments) {
            logger.info(throwable, msg, arguments);
        }

        @Override
        public void info(final Throwable throwable, final String msg, final Supplier<?> arg1) {
            logger.info(throwable, msg, arg1);
        }

        @Override
        public void info(final Throwable throwable, final String msg, final Supplier<?> arg1, final Supplier<?> arg2) {
            logger.info(throwable, msg, arg1, arg2);
        }

        @Override
        public void info(final Throwable throwable, final String msg, final Supplier<?> arg1, final Supplier<?> arg2, final Supplier<?> arg3) {
            logger.info(throwable, msg, arg1, arg2, arg3);
        }

        @Override
        public void info(final Throwable throwable, final String msg, final Supplier<?> arg1, final Supplier<?> arg2, final Supplier<?> arg3, final Supplier<?> arg4) {
            logger.info(throwable, msg, arg1, arg2, arg3, arg4);
        }

        @Override
        public void info(final Throwable throwable, final String msg, final Supplier<?> arg1, final Supplier<?> arg2, final Supplier<?> arg3, final Supplier<?> arg4, final Supplier<?> arg5) {
            logger.info(throwable, msg, arg1, arg2, arg3, arg4, arg5);
        }

        @Override
        public void info(final Throwable throwable, final String msg, final Supplier<?>... suppliers) {
            logger.info(throwable, msg, suppliers);
        }

        @Override
        public boolean isWarnEnabled() {
            return logger.isWarnEnabled();
        }

        @Override
        public void warn(final String msg) {
            logger.warn(msg);
        }

        @Override
        public void warn(final String msg, final Object arg1) {
            logger.warn(msg, arg1);
        }

        @Override
        public void warn(final String msg, final Object arg1, final Object arg2) {
            logger.warn(msg, arg1, arg2);
        }

        @Override
        public void warn(final String msg, final Object arg1, final Object arg2, final Object arg3) {
            logger.warn(msg, arg1, arg2, arg3);
        }

        @Override
        public void warn(final String msg, final Object arg1, final Object arg2, final Object arg3, final Object arg4) {
            logger.warn(msg, arg1, arg2, arg3, arg4);
        }

        @Override
        public void warn(final String msg, final Object arg1, final Object arg2, final Object arg3, final Object arg4, final Object arg5) {
            logger.warn(msg, arg1, arg2, arg3, arg4, arg5);
        }

        @Override
        public void warn(final String msg, final Object... arguments) {
            logger.warn(msg, arguments);
        }

        @Override
        public void warn(final String msg, final Supplier<?> arg1) {
            logger.warn(msg, arg1);
        }

        @Override
        public void warn(final String msg, final Supplier<?> arg1, final Supplier<?> arg2) {
            logger.warn(msg, arg1, arg2);
        }

        @Override
        public void warn(final String msg, final Supplier<?> arg1, final Supplier<?> arg2, final Supplier<?> arg3) {
            logger.warn(msg, arg1, arg2, arg3);
        }

        @Override
        public void warn(final String msg, final Supplier<?> arg1, final Supplier<?> arg2, final Supplier<?> arg3, final Supplier<?> arg4) {
            logger.warn(msg, arg1, arg2, arg3, arg4);
        }

        @Override
        public void warn(final String msg, final Supplier<?> arg1, final Supplier<?> arg2, final Supplier<?> arg3, final Supplier<?> arg4, final Supplier<?> arg5) {
            logger.warn(msg, arg1, arg2, arg3, arg4, arg5);
        }

        @Override
        public void warn(final String msg, final Supplier<?>... suppliers) {
            logger.warn(msg, suppliers);
        }

        @Override
        public void warn(final Throwable throwable, final String msg, final Object arg1) {
            logger.warn(throwable, msg, arg1);
        }

        @Override
        public void warn(final Throwable throwable, final String msg, final Object arg1, final Object arg2) {
            logger.warn(throwable, msg, arg1, arg2);
        }

        @Override
        public void warn(final Throwable throwable, final String msg, final Object arg1, final Object arg2, final Object arg3) {
            logger.warn(throwable, msg, arg1, arg2, arg3);
        }

        @Override
        public void warn(final Throwable throwable, final String msg, final Object arg1, final Object arg2, final Object arg3, final Object arg4) {
            logger.warn(throwable, msg, arg1, arg2, arg3, arg4);
        }

        @Override
        public void warn(final Throwable throwable, final String msg, final Object arg1, final Object arg2, final Object arg3, final Object arg4, final Object arg5) {
            logger.warn(throwable, msg, arg1, arg2, arg3, arg4, arg5);
        }

        @Override
        public void warn(final Throwable throwable, final String msg, final Object... arguments) {
            logger.warn(throwable, msg, arguments);
        }

        @Override
        public void warn(final Throwable throwable, final String msg, final Supplier<?> arg1) {
            logger.warn(throwable, msg, arg1);
        }

        @Override
        public void warn(final Throwable throwable, final String msg, final Supplier<?> arg1, final Supplier<?> arg2) {
            logger.warn(throwable, msg, arg1, arg2);
        }

        @Override
        public void warn(final Throwable throwable, final String msg, final Supplier<?> arg1, final Supplier<?> arg2, final Supplier<?> arg3) {
            logger.warn(throwable, msg, arg1, arg2, arg3);
        }

        @Override
        public void warn(final Throwable throwable, final String msg, final Supplier<?> arg1, final Supplier<?> arg2, final Supplier<?> arg3, final Supplier<?> arg4) {
            logger.warn(throwable, msg, arg1, arg2, arg3, arg4);
        }

        @Override
        public void warn(final Throwable throwable, final String msg, final Supplier<?> arg1, final Supplier<?> arg2, final Supplier<?> arg3, final Supplier<?> arg4, final Supplier<?> arg5) {
            logger.warn(throwable, msg, arg1, arg2, arg3, arg4, arg5);
        }

        @Override
        public void warn(final Throwable throwable, final String msg, final Supplier<?>... suppliers) {
            logger.warn(throwable, msg, suppliers);
        }

        @Override
        public boolean isErrorEnabled() {
            return logger.isErrorEnabled();
        }

        @Override
        public void error(final String msg) {
            logger.error(msg);
        }

        @Override
        public void error(final String msg, final Object arg1) {
            logger.error(msg, arg1);
        }

        @Override
        public void error(final String msg, final Object arg1, final Object arg2) {
            logger.error(msg, arg1, arg2);
        }

        @Override
        public void error(final String msg, final Object arg1, final Object arg2, final Object arg3) {
            logger.error(msg, arg1, arg2, arg3);
        }

        @Override
        public void error(final String msg, final Object arg1, final Object arg2, final Object arg3, final Object arg4) {
            logger.error(msg, arg1, arg2, arg3, arg4);
        }

        @Override
        public void error(final String msg, final Object arg1, final Object arg2, final Object arg3, final Object arg4, final Object arg5) {
            logger.error(msg, arg1, arg2, arg3, arg4, arg5);
        }

        @Override
        public void error(final String msg, final Object... arguments) {
            logger.error(msg, arguments);
        }

        @Override
        public void error(final String msg, final Supplier<?> arg1) {
            logger.error(msg, arg1);
        }

        @Override
        public void error(final String msg, final Supplier<?> arg1, final Supplier<?> arg2) {
            logger.error(msg, arg1, arg2);
        }

        @Override
        public void error(final String msg, final Supplier<?> arg1, final Supplier<?> arg2, final Supplier<?> arg3) {
            logger.error(msg, arg1, arg2, arg3);
        }

        @Override
        public void error(final String msg, final Supplier<?> arg1, final Supplier<?> arg2, final Supplier<?> arg3, final Supplier<?> arg4) {
            logger.error(msg, arg1, arg2, arg3, arg4);
        }

        @Override
        public void error(final String msg, final Supplier<?> arg1, final Supplier<?> arg2, final Supplier<?> arg3, final Supplier<?> arg4, final Supplier<?> arg5) {
            logger.error(msg, arg1, arg2, arg3, arg4, arg5);
        }

        @Override
        public void error(final String msg, final Supplier<?>... suppliers) {
            logger.error(msg, suppliers);
        }

        @Override
        public void error(final Throwable throwable, final String msg, final Object arg1) {
            logger.error(throwable, msg, arg1);
        }

        @Override
        public void error(final Throwable throwable, final String msg, final Object arg1, final Object arg2) {
            logger.error(throwable, msg, arg1, arg2);
        }

        @Override
        public void error(final Throwable throwable, final String msg, final Object arg1, final Object arg2, final Object arg3) {
            logger.error(throwable, msg, arg1, arg2, arg3);
        }

        @Override
        public void error(final Throwable throwable, final String msg, final Object arg1, final Object arg2, final Object arg3, final Object arg4) {
            logger.error(throwable, msg, arg1, arg2, arg3, arg4);
        }

        @Override
        public void error(final Throwable throwable, final String msg, final Object arg1, final Object arg2, final Object arg3, final Object arg4, final Object arg5) {
            logger.error(throwable, msg, arg1, arg2, arg3, arg4, arg5);
        }

        @Override
        public void error(final Throwable throwable, final String msg, final Object... arguments) {
            logger.error(throwable, msg, arguments);
        }

        @Override
        public void error(final Throwable throwable, final String msg, final Supplier<?> arg1) {
            logger.error(throwable, msg, arg1);
        }

        @Override
        public void error(final Throwable throwable, final String msg, final Supplier<?> arg1, final Supplier<?> arg2) {
            logger.error(throwable, msg, arg1, arg2);
        }

        @Override
        public void error(final Throwable throwable, final String msg, final Supplier<?> arg1, final Supplier<?> arg2, final Supplier<?> arg3) {
            logger.error(throwable, msg, arg1, arg2, arg3);
        }

        @Override
        public void error(final Throwable throwable, final String msg, final Supplier<?> arg1, final Supplier<?> arg2, final Supplier<?> arg3, final Supplier<?> arg4) {
            logger.error(throwable, msg, arg1, arg2, arg3, arg4);
        }

        @Override
        public void error(final Throwable throwable, final String msg, final Supplier<?> arg1, final Supplier<?> arg2, final Supplier<?> arg3, final Supplier<?> arg4, final Supplier<?> arg5) {
            logger.error(throwable, msg, arg1, arg2, arg3, arg4, arg5);
        }

        @Override
        public void error(final Throwable throwable, final String msg, final Supplier<?>... suppliers) {
            logger.error(throwable, msg, suppliers);
        }
    }
}
