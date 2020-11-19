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

package org.slf4j.internal;

import io.rxmicro.logger.Logger;
import io.rxmicro.logger.LoggerFactory;
import org.slf4j.Marker;

import static io.rxmicro.common.util.Formats.FORMAT_PLACEHOLDER_TOKEN;

/**
 * Unfortunately some db drivers removed support of JUL,
 * so the RxMicro framework requires a org.slf4j proxy to enable logging without slf4j-api:
 * <a href="http://www.slf4j.org">http://www.slf4j.org</a>
 * <p>
 * Read more:
 * <a href="https://github.com/mongodb/mongo-java-driver/commit/6a163f715fe08ed8d39acac3d11c896ae547df73">
 *     https://github.com/mongodb/mongo-java-driver/commit/6a163f715fe08ed8d39acac3d11c896ae547df73
 * </a>
 *
 * @author nedis
 * @since 0.3
 */
public final class Slf4jLoggerProxy implements org.slf4j.Logger {

    private static final String SLF4J_PLACEHOLDER = "{}";

    private final Logger logger;

    public Slf4jLoggerProxy(final String name) {
        this.logger = LoggerFactory.getLogger(name);
    }

    public Slf4jLoggerProxy(final Class<?> clazz) {
        this.logger = LoggerFactory.getLogger(clazz);
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
    public boolean isTraceEnabled(final Marker marker) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void trace(final String msg) {
        logger.trace(msg);
    }

    @Override
    public void trace(final String format,
                      final Object arg) {
        logger.trace(fixPlaceholders(format), arg);
    }

    @Override
    public void trace(final String format,
                      final Object arg1,
                      final Object arg2) {
        logger.trace(fixPlaceholders(format), arg1, arg2);
    }

    @Override
    public void trace(final String format,
                      final Object... arguments) {
        logger.trace(fixPlaceholders(format), arguments);
    }

    @Override
    public void trace(final String msg,
                      final Throwable throwable) {
        logger.trace(throwable, msg);
    }

    @Override
    public void trace(final Marker marker,
                      final String msg) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void trace(final Marker marker,
                      final String format,
                      final Object arg) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void trace(final Marker marker,
                      final String format,
                      final Object arg1,
                      final Object arg2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void trace(final Marker marker,
                      final String format,
                      final Object... argArray) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void trace(final Marker marker,
                      final String msg,
                      final Throwable throwable) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isDebugEnabled() {
        return logger.isDebugEnabled();
    }

    @Override
    public boolean isDebugEnabled(final Marker marker) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void debug(final String msg) {
        logger.debug(msg);
    }

    @Override
    public void debug(final String format,
                      final Object arg) {
        logger.debug(fixPlaceholders(format), arg);
    }

    @Override
    public void debug(final String format,
                      final Object arg1,
                      final Object arg2) {
        logger.debug(fixPlaceholders(format), arg1, arg2);
    }

    @Override
    public void debug(final String format,
                      final Object... arguments) {
        logger.debug(fixPlaceholders(format), arguments);
    }

    @Override
    public void debug(final String msg,
                      final Throwable throwable) {
        logger.debug(throwable, msg);
    }

    @Override
    public void debug(final Marker marker,
                      final String msg) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void debug(final Marker marker,
                      final String format,
                      final Object arg) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void debug(final Marker marker,
                      final String format,
                      final Object arg1,
                      final Object arg2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void debug(final Marker marker,
                      final String format,
                      final Object... arguments) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void debug(final Marker marker,
                      final String msg,
                      final Throwable throwable) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isInfoEnabled() {
        return logger.isInfoEnabled();
    }

    @Override
    public boolean isInfoEnabled(final Marker marker) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void info(final String msg) {
        logger.info(msg);
    }

    @Override
    public void info(final String format,
                     final Object arg) {
        logger.info(fixPlaceholders(format), arg);
    }

    @Override
    public void info(final String format,
                     final Object arg1,
                     final Object arg2) {
        logger.info(fixPlaceholders(format), arg1, arg2);
    }

    @Override
    public void info(final String format,
                     final Object... arguments) {
        logger.info(fixPlaceholders(format), arguments);
    }

    @Override
    public void info(final String msg,
                     final Throwable throwable) {
        logger.info(throwable, msg);
    }

    @Override
    public void info(final Marker marker,
                     final String msg) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void info(final Marker marker,
                     final String format,
                     final Object arg) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void info(final Marker marker,
                     final String format,
                     final Object arg1,
                     final Object arg2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void info(final Marker marker,
                     final String format,
                     final Object... arguments) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void info(final Marker marker,
                     final String msg,
                     final Throwable throwable) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isWarnEnabled() {
        return logger.isWarnEnabled();
    }

    @Override
    public boolean isWarnEnabled(final Marker marker) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void warn(final String msg) {
        logger.warn(msg);
    }

    @Override
    public void warn(final String format,
                     final Object arg) {
        logger.warn(fixPlaceholders(format), arg);
    }

    @Override
    public void warn(final String format,
                     final Object... arguments) {
        logger.warn(fixPlaceholders(format), arguments);
    }

    @Override
    public void warn(final String format,
                     final Object arg1,
                     final Object arg2) {
        logger.warn(fixPlaceholders(format), arg1, arg2);
    }

    @Override
    public void warn(final String msg,
                     final Throwable throwable) {
        logger.warn(throwable, msg);
    }

    @Override
    public void warn(final Marker marker,
                     final String msg) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void warn(final Marker marker,
                     final String format,
                     final Object arg) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void warn(final Marker marker,
                     final String format,
                     final Object arg1,
                     final Object arg2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void warn(final Marker marker,
                     final String format,
                     final Object... arguments) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void warn(final Marker marker,
                     final String msg,
                     final Throwable throwable) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isErrorEnabled() {
        return logger.isErrorEnabled();
    }

    @Override
    public boolean isErrorEnabled(final Marker marker) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void error(final String msg) {
        logger.error(msg);
    }

    @Override
    public void error(final String format,
                      final Object arg) {
        logger.error(fixPlaceholders(format), arg);
    }

    @Override
    public void error(final String format,
                      final Object arg1,
                      final Object arg2) {
        logger.error(fixPlaceholders(format), arg1, arg2);
    }

    @Override
    public void error(final String format,
                      final Object... arguments) {
        logger.error(fixPlaceholders(format), arguments);
    }

    @Override
    public void error(final String msg,
                      final Throwable throwable) {
        logger.error(throwable, msg);
    }

    @Override
    public void error(final Marker marker,
                      final String msg) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void error(final Marker marker,
                      final String format,
                      final Object arg) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void error(final Marker marker,
                      final String format,
                      final Object arg1,
                      final Object arg2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void error(final Marker marker,
                      final String format,
                      final Object... arguments) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void error(final Marker marker,
                      final String msg,
                      final Throwable throwable) {
        throw new UnsupportedOperationException();
    }

    private String fixPlaceholders(final String template) {
        return template.replace(SLF4J_PLACEHOLDER, FORMAT_PLACEHOLDER_TOKEN);
    }
}
