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

import org.slf4j.internal.Slf4jLoggerProxy;

/**
 * Unfortunately some db drivers removed support of JUL,
 * so the RxMicro framework requires a org.slf4j proxy to enable logging without slf4j-api:
 * <a href="http://www.slf4j.org">http://www.slf4j.org</a>
 *
 * <p>
 * Read more:
 * <a href="https://github.com/mongodb/mongo-java-driver/commit/6a163f715fe08ed8d39acac3d11c896ae547df73">
 *     https://github.com/mongodb/mongo-java-driver/commit/6a163f715fe08ed8d39acac3d11c896ae547df73
 * </a>
 *
 * <p>
 * The <code>LoggerFactory</code> is a utility class producing Loggers for
 * various logging APIs, most notably for log4j, logback and JDK 1.4 logging.
 *
 * <p>
 * <code>LoggerFactory</code> is essentially a wrapper around an
 * {@link ILoggerFactory} instance bound with <code>LoggerFactory</code> at
 * compile time.
 *
 * <p>
 * Please note that all methods in <code>LoggerFactory</code> are static.
 *
 * @author nedis
 * @see <a href="http://www.slf4j.org/apidocs/org/slf4j/LoggerFactory.html">http://www.slf4j.org/apidocs/org/slf4j/LoggerFactory.html</a>
 * @since 0.3
 */
public final class LoggerFactory {

    /**
     * See
     * <a href="http://www.slf4j.org/apidocs/org/slf4j/LoggerFactory.html">http://www.slf4j.org/apidocs/org/slf4j/LoggerFactory.html</a>.
     *
     * <p>
     * Return a logger named according to the name parameter using the
     * statically bound {@link ILoggerFactory} instance.
     *
     * @param name The name of the logger.
     * @return logger
     */
    public static Logger getLogger(final String name) {
        return new Slf4jLoggerProxy(name);
    }

    /**
     * See
     * <a href="http://www.slf4j.org/apidocs/org/slf4j/LoggerFactory.html">http://www.slf4j.org/apidocs/org/slf4j/LoggerFactory.html</a>.
     *
     * <p>
     * Return a logger named corresponding to the class passed as parameter,
     * using the statically bound {@link ILoggerFactory} instance.
     *
     * <p>
     * In case the the <code>clazz</code> parameter differs from the name of the
     * caller as computed internally by SLF4J, a logger name mismatch warning
     * will be printed but only if the
     * <code>slf4j.detectLoggerNameMismatch</code> system property is set to
     * true. By default, this property is not set and no warnings will be
     * printed even in case of a logger name mismatch.
     *
     * @param clazz the returned logger will be named after clazz
     * @return logger
     */
    public static Logger getLogger(final Class<?> clazz) {
        return new Slf4jLoggerProxy(clazz);
    }

    /**
     * See
     * <a href="http://www.slf4j.org/apidocs/org/slf4j/LoggerFactory.html">http://www.slf4j.org/apidocs/org/slf4j/LoggerFactory.html</a>.
     *
     * <p>
     * Return the {@link ILoggerFactory} instance in use.
     *
     * <p>
     * ILoggerFactory instance is bound with this class at compile time.
     *
     * @return nothing
     * @throws UnsupportedOperationException proxy does not contain ILoggerFactory implementation
     */
    public static ILoggerFactory getILoggerFactory() {
        throw new UnsupportedOperationException("Proxy does not contain ILoggerFactory implementation");
    }

    private LoggerFactory() {
    }
}
