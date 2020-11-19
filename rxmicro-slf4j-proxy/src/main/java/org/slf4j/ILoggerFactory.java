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
 * so the RxMicro framework requires a org.slf4j proxy to enable logging without slf4j-api:
 * <a href="http://www.slf4j.org">http://www.slf4j.org</a>.
 *
 * <p>
 * Read more:
 * <a href="https://github.com/mongodb/mongo-java-driver/commit/6a163f715fe08ed8d39acac3d11c896ae547df73">
 *     https://github.com/mongodb/mongo-java-driver/commit/6a163f715fe08ed8d39acac3d11c896ae547df73
 * </a>
 *
 * <p>
 * <code>ILoggerFactory</code> instances manufacture {@link Logger}
 * instances by name.
 *
 * <p>Most users retrieve {@link Logger} instances through the static
 * {@link LoggerFactory#getLogger(String)} method. An instance of of this
 * interface is bound internally with {@link LoggerFactory} class at
 * compile time.
 *
 * @author nedis
 * @see <a href="https://www.javadoc.io/doc/org.slf4j/slf4j-api/1.7.30/org/slf4j/ILoggerFactory.html">
 *     https://www.javadoc.io/doc/org.slf4j/slf4j-api/1.7.30/org/slf4j/ILoggerFactory.html</a>
 * @since 0.3
 */
public interface ILoggerFactory {

    /**
     * See
     * <a href="https://www.javadoc.io/doc/org.slf4j/slf4j-api/1.7.30/org/slf4j/ILoggerFactory.html">
     *     https://www.javadoc.io/doc/org.slf4j/slf4j-api/1.7.30/org/slf4j/ILoggerFactory.html</a>.
     *
     * <p>
     * Return an appropriate {@link Logger} instance as specified by the
     * <code>name</code> parameter.
     *
     * <p>
     * If the name parameter is equal to {@link Logger#ROOT_LOGGER_NAME}, that is
     * the string value "ROOT" (case insensitive), then the root logger of the
     * underlying logging system is returned.
     *
     * <p>
     * Null-valued name arguments are considered invalid.
     *
     * <p>
     * Certain extremely simple logging systems, e.g. NOP, may always
     * return the same logger instance regardless of the requested name.
     *
     * @param name the name of the Logger to return
     * @return a Logger instance
     */
    Logger getLogger(String name);
}
