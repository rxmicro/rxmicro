/*
 * Copyright (c) 2020. http://rxmicro.io
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

import static io.rxmicro.common.util.Requires.require;

/**
 * Unfortunately some db drivers removed support of JUL,
 * so the RxMicro framework requires a org.slf4j proxy to enable logging without slf4j-api
 *
 * Read more: https://github.com/mongodb/mongo-java-driver/commit/6a163f715fe08ed8d39acac3d11c896ae547df73
 *
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.3
 */
public final class Slf4jLoggerProxy implements org.slf4j.Logger {

    private final String name;

    private final Logger logger;

    public Slf4jLoggerProxy(final String name) {
        this.name = require(name);
        this.logger = LoggerFactory.getLogger(name);
    }

    @Override
    public String getName() {
        return name;
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
    public void trace(final String format, final Object arg) {
        logger.trace(format.replace("{}", "?"), arg);
    }

    @Override
    public void trace(final String format, final Object arg1, final Object arg2) {
        logger.trace(format.replace("{}", "?"), arg1, arg2);
    }

    @Override
    public void trace(final String format, final Object... arguments) {
        logger.trace(format.replace("{}", "?"), arguments);
    }

    @Override
    public void trace(final String msg, final Throwable throwable) {
        //TODO
    }

    @Override
    public boolean isTraceEnabled(final Marker marker) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void trace(final Marker marker, final String msg) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void trace(final Marker marker, final String format, final Object arg) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void trace(final Marker marker, final String format, final Object arg1, final Object arg2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void trace(final Marker marker, final String format, final Object... argArray) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void trace(final Marker marker, final String msg, final Throwable throwable) {
        throw new UnsupportedOperationException();
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
    public void debug(final String format, final Object arg) {
        logger.debug(format.replace("{}", "?"), arg);
    }

    @Override
    public void debug(final String format, final Object arg1, final Object arg2) {
        logger.debug(format.replace("{}", "?"), arg1, arg2);
    }

    @Override
    public void debug(final String format, final Object... arguments) {
        logger.debug(format.replace("{}", "?"), arguments);
    }

    @Override
    public void debug(final String msg, final Throwable throwable) {
        //TODO
    }

    @Override
    public boolean isDebugEnabled(final Marker marker) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void debug(final Marker marker, final String msg) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void debug(final Marker marker, final String format, final Object arg) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void debug(final Marker marker, final String format, final Object arg1, final Object arg2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void debug(final Marker marker, final String format, final Object... arguments) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void debug(final Marker marker, final String msg, final Throwable throwable) {
        throw new UnsupportedOperationException();
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
    public void info(final String format, final Object arg) {
        logger.info(format.replace("{}", "?"), arg);
    }

    @Override
    public void info(final String format, final Object arg1, final Object arg2) {
        logger.info(format.replace("{}", "?"), arg1, arg2);
    }

    @Override
    public void info(final String format, final Object... arguments) {
        logger.info(format.replace("{}", "?"), arguments);
    }

    @Override
    public void info(final String msg, final Throwable throwable) {
        //TODO
    }

    @Override
    public boolean isInfoEnabled(final Marker marker) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void info(final Marker marker, final String msg) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void info(final Marker marker, final String format, final Object arg) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void info(final Marker marker, final String format, final Object arg1, final Object arg2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void info(final Marker marker, final String format, final Object... arguments) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void info(final Marker marker, final String msg, final Throwable throwable) {
        throw new UnsupportedOperationException();
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
    public void warn(final String format, final Object arg) {
        logger.warn(format.replace("{}", "?"), arg);
    }

    @Override
    public void warn(final String format, final Object... arguments) {
        logger.warn(format.replace("{}", "?"), arguments);
    }

    @Override
    public void warn(final String format, final Object arg1, final Object arg2) {
        logger.warn(format.replace("{}", "?"), arg1, arg2);
    }

    @Override
    public void warn(final String msg, final Throwable throwable) {
        //TODO
    }

    @Override
    public boolean isWarnEnabled(final Marker marker) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void warn(final Marker marker, final String msg) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void warn(final Marker marker, final String format, final Object arg) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void warn(final Marker marker, final String format, final Object arg1, final Object arg2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void warn(final Marker marker, final String format, final Object... arguments) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void warn(final Marker marker, final String msg, final Throwable throwable) {
        throw new UnsupportedOperationException();
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
    public void error(final String format, final Object arg) {
        logger.error(format.replace("{}", "?"), arg);
    }

    @Override
    public void error(final String format, final Object arg1, final Object arg2) {
        logger.error(format.replace("{}", "?"), arg1, arg2);
    }

    @Override
    public void error(final String format, final Object... arguments) {
        logger.error(format.replace("{}", "?"), arguments);
    }

    @Override
    public void error(final String msg, final Throwable throwable) {
        logger.error(throwable, msg);
    }

    @Override
    public boolean isErrorEnabled(final Marker marker) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void error(final Marker marker, final String msg) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void error(final Marker marker, final String format, final Object arg) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void error(final Marker marker, final String format, final Object arg1, final Object arg2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void error(final Marker marker, final String format, final Object... arguments) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void error(final Marker marker, final String msg, final Throwable throwable) {
        throw new UnsupportedOperationException();
    }
}
