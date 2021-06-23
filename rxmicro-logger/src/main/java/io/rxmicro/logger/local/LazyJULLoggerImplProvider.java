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

package io.rxmicro.logger.local;

import io.rxmicro.logger.Logger;
import io.rxmicro.logger.LoggerEventBuilder;
import io.rxmicro.logger.impl.LoggerImplProvider;
import io.rxmicro.logger.internal.jul.JULLoggerImplProvider;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.function.Supplier;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.logger.internal.LoggerImplProviderFactoryHelper.createAndSetupLoggerImplProvider;
import static java.lang.reflect.Proxy.newProxyInstance;

/**
 * Designed for annotation processor environment only!
 *
 * @author nedis
 * @since 0.10
 */
public final class LazyJULLoggerImplProvider implements LoggerImplProvider {

    private final ClassLoader classLoader = LoggerImplProvider.class.getClassLoader();

    private final Class<?>[] loggerInterfaces = {Logger.class};

    private final LoggerImplProviderSupplier supplier = new LoggerImplProviderSupplier();

    @Override
    public void setup() {
        // do nothing
    }

    @Override
    public Logger getLogger(final String name) {
        return (Logger) newProxyInstance(classLoader, loggerInterfaces, new LoggerInvocationHandler(name, supplier));
    }

    @Override
    public LoggerEventBuilder newLoggerEventBuilder() {
        return supplier.get().newLoggerEventBuilder();
    }

    /**
     * Designed for annotation processor environment only!
     *
     * @author nedis
     * @since 0.10
     */
    private static final class LoggerImplProviderSupplier implements Supplier<LoggerImplProvider> {

        private volatile LoggerImplProvider loggerImplProvider;

        @Override
        public LoggerImplProvider get() {
            if (loggerImplProvider == null) {
                synchronized (this) {
                    if (loggerImplProvider == null) {
                        loggerImplProvider = createAndSetupLoggerImplProvider(JULLoggerImplProvider.class);
                    }
                }
            }
            return loggerImplProvider;
        }
    }

    /**
     * Designed for annotation processor environment only!
     *
     * @author nedis
     * @since 0.10
     */
    private static final class LoggerInvocationHandler implements InvocationHandler {

        private final String name;

        private final Supplier<LoggerImplProvider> loggerImplProviderSupplier;

        private Logger logger;

        private LoggerInvocationHandler(final String name,
                                        final Supplier<LoggerImplProvider> loggerImplProviderSupplier) {
            this.loggerImplProviderSupplier = loggerImplProviderSupplier;
            this.name = name;
        }

        @Override
        public Object invoke(final Object proxy,
                             final Method method,
                             final Object[] args) throws Throwable {
            if ("toString".equals(method.getName()) && method.getParameterCount() == 0) {
                return format("? Lazy Proxy for '?'", Logger.class.getSimpleName(), getLogger());
            } else {
                return method.invoke(getLogger(), args);
            }
        }

        private Logger getLogger() {
            if (logger == null) {
                logger = loggerImplProviderSupplier.get().getLogger(name);
            }
            return logger;
        }
    }
}
