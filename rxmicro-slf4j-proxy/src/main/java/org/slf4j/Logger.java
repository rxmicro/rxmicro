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

package org.slf4j;

/**
 * Unfortunately some db drivers removed support of JUL,
 * so the RxMicro framework requires a org.slf4j proxy to enable logging without slf4j-api
 * <p>
 * Read more:
 * <a href="https://github.com/mongodb/mongo-java-driver/commit/6a163f715fe08ed8d39acac3d11c896ae547df73">
 *     https://github.com/mongodb/mongo-java-driver/commit/6a163f715fe08ed8d39acac3d11c896ae547df73
 * </a>
 *
 * @author nedis
 * @link https://rxmicro.io
 * @link http://www.slf4j.org
 * @since 0.3
 */
public interface Logger {

    String getName();

    boolean isTraceEnabled();

    void trace(String msg);

    void trace(String format, Object arg);

    void trace(String format, Object arg1, Object arg2);

    void trace(String format, Object... arguments);

    void trace(String msg, Throwable throwable);

    boolean isTraceEnabled(Marker marker);

    void trace(Marker marker, String msg);

    void trace(Marker marker, String format, Object arg);

    void trace(Marker marker, String format, Object arg1, Object arg2);

    void trace(Marker marker, String format, Object... argArray);

    void trace(Marker marker, String msg, Throwable throwable);

    boolean isDebugEnabled();

    void debug(String msg);

    void debug(String format, Object arg);

    void debug(String format, Object arg1, Object arg2);

    void debug(String format, Object... arguments);

    void debug(String msg, Throwable throwable);

    boolean isDebugEnabled(Marker marker);

    void debug(Marker marker, String msg);

    void debug(Marker marker, String format, Object arg);

    void debug(Marker marker, String format, Object arg1, Object arg2);

    void debug(Marker marker, String format, Object... arguments);

    void debug(Marker marker, String msg, Throwable throwable);

    boolean isInfoEnabled();

    void info(String msg);

    void info(String format, Object arg);

    void info(String format, Object arg1, Object arg2);

    void info(String format, Object... arguments);

    void info(String msg, Throwable throwable);

    boolean isInfoEnabled(Marker marker);

    void info(Marker marker, String msg);

    void info(Marker marker, String format, Object arg);

    void info(Marker marker, String format, Object arg1, Object arg2);

    void info(Marker marker, String format, Object... arguments);

    void info(Marker marker, String msg, Throwable throwable);

    boolean isWarnEnabled();

    void warn(String msg);

    void warn(String format, Object arg);

    void warn(String format, Object... arguments);

    void warn(String format, Object arg1, Object arg2);

    void warn(String msg, Throwable throwable);

    boolean isWarnEnabled(Marker marker);

    void warn(Marker marker, String msg);

    void warn(Marker marker, String format, Object arg);

    void warn(Marker marker, String format, Object arg1, Object arg2);

    void warn(Marker marker, String format, Object... arguments);

    void warn(Marker marker, String msg, Throwable throwable);

    boolean isErrorEnabled();

    void error(String msg);

    void error(String format, Object arg);

    void error(String format, Object arg1, Object arg2);

    void error(String format, Object... arguments);

    void error(String msg, Throwable throwable);

    boolean isErrorEnabled(Marker marker);

    void error(Marker marker, String msg);

    void error(Marker marker, String format, Object arg);

    void error(Marker marker, String format, Object arg1, Object arg2);

    void error(Marker marker, String format, Object... arguments);

    void error(Marker marker, String msg, Throwable throwable);
}
