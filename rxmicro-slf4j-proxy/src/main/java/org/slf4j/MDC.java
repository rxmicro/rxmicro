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

import org.slf4j.internal.DoNothingMDCAdapter;
import org.slf4j.spi.MDCAdapter;

import java.io.Closeable;
import java.util.Map;

/**
 * Unfortunately some db drivers removed support of JUL,
 * so the RxMicro framework requires a org.slf4j proxy to enable logging without slf4j-api:
 * <a href="http://www.slf4j.org">http://www.slf4j.org</a>
 *
 * <p>
 * Read more:
 * <a href="https://github.com/mongodb/mongo-java-driver/commit/6a163f715fe08ed8d39acac3d11c896ae547df73">
 * https://github.com/mongodb/mongo-java-driver/commit/6a163f715fe08ed8d39acac3d11c896ae547df73
 * </a>
 *
 * <p>
 * Do nothing.
 *
 * <p>
 * The RxMicro framework does not support MDC, because {@link MDC} can be used for
 * <a href=https://en.wikipedia.org/wiki/Multithreading_(computer_architecture)">multithreading programming model</a> only.
 *
 * @author nedis
 * @see <a href="https://www.javadoc.io/doc/org.slf4j/slf4j-api/1.7.30/org/slf4j/MDC.html">
 * https://www.javadoc.io/doc/org.slf4j/slf4j-api/1.7.30/org/slf4j/MDC.html</a>
 * @since 0.7
 */
public final class MDC {

    /**
     * Do nothing.
     *
     * <p>
     * The RxMicro framework does not support MDC, because {@link MDC} can be used for
     * <a href=https://en.wikipedia.org/wiki/Multithreading_(computer_architecture)">multithreading programming model</a> only.
     *
     * @param key non-null key
     * @param val value to put in the map
     */
    public static void put(final String key, final String val) {
        // do nothing
    }

    /**
     * Do nothing.
     *
     * <p>
     * The RxMicro framework does not support MDC, because {@link MDC} can be used for
     * <a href=https://en.wikipedia.org/wiki/Multithreading_(computer_architecture)">multithreading programming model</a> only.
     *
     * @param key non-null key
     * @param val value to put in the map
     * @return a <code>Closeable</code> instance that does nothing.
     */
    public static MDCCloseable putCloseable(final String key, final String val) {
        return MDCCloseable.DO_NOTHING;
    }

    /**
     * Do nothing.
     *
     * <p>
     * The RxMicro framework does not support MDC, because {@link MDC} can be used for
     * <a href=https://en.wikipedia.org/wiki/Multithreading_(computer_architecture)">multithreading programming model</a> only.
     *
     * @param key non-null key
     * @return {@code null}.
     */
    public static String get(final String key) {
        return null;
    }

    /**
     * Do nothing.
     *
     * <p>
     * The RxMicro framework does not support MDC, because {@link MDC} can be used for
     * <a href=https://en.wikipedia.org/wiki/Multithreading_(computer_architecture)">multithreading programming model</a> only.
     *
     * @param key non-null key
     */
    public static void remove(final String key) {
        // do nothing
    }

    /**
     * Do nothing.
     *
     * <p>
     * The RxMicro framework does not support MDC, because {@link MDC} can be used for
     * <a href=https://en.wikipedia.org/wiki/Multithreading_(computer_architecture)">multithreading programming model</a> only.
     */
    public static void clear() {
        // do nothing
    }

    /**
     * Do nothing.
     *
     * <p>
     * The RxMicro framework does not support MDC, because {@link MDC} can be used for
     * <a href=https://en.wikipedia.org/wiki/Multithreading_(computer_architecture)">multithreading programming model</a> only.
     *
     * @return {@code null}
     */
    public static Map<String, String> getCopyOfContextMap() {
        return null;
    }

    /**
     * Do nothing.
     *
     * <p>
     * The RxMicro framework does not support MDC, because {@link MDC} can be used for
     * <a href=https://en.wikipedia.org/wiki/Multithreading_(computer_architecture)">multithreading programming model</a> only.
     *
     * @param contextMap must contain only keys and values of type String
     */
    public static void setContextMap(final Map<String, String> contextMap) {
        // do nothing
    }

    /**
     * Returns the MDCAdapter instance currently in use.
     *
     * @return the MDCAdapter instance currently in use.
     */
    public static MDCAdapter getMDCAdapter() {
        return DoNothingMDCAdapter.getInstance();
    }

    private MDC() {
    }

    /**
     * Do nothing.
     *
     * @author nedis
     * @see <a href="https://www.javadoc.io/doc/org.slf4j/slf4j-api/1.7.30/org/slf4j/MDC.html">
     * https://www.javadoc.io/doc/org.slf4j/slf4j-api/1.7.30/org/slf4j/MDC.html</a>
     * @since 0.7
     */
    public static class MDCCloseable implements Closeable {

        private static final MDCCloseable DO_NOTHING = new MDCCloseable();

        @Override
        public void close() {
            // do nothing
        }
    }
}
