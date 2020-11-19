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

package org.slf4j.spi;

import java.util.Map;

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
 * Do nothing.
 * <p>
 * The RxMicro framework does not support MDC, because {@link org.slf4j.MDC} can be used for
 * <a href=https://en.wikipedia.org/wiki/Multithreading_(computer_architecture)">multithreading programming model</a> only.
 *
 * @author nedis
 * @since 0.7
 */
public interface MDCAdapter {

    /**
     * See
     * <a href="https://www.javadoc.io/doc/org.slf4j/slf4j-api/1.7.26/org/slf4j/spi/MDCAdapter.html">
     *     https://www.javadoc.io/doc/org.slf4j/slf4j-api/1.7.26/org/slf4j/spi/MDCAdapter.html</a>.
     *
     * <p>
     * Put a context value (the <code>val</code> parameter) as identified with
     * the <code>key</code> parameter into the current thread's context map.
     * The <code>key</code> parameter cannot be null. The <code>val</code> parameter
     * can be null only if the underlying implementation supports it.
     *
     * <p>If the current thread does not have a context map it is created as a side
     * effect of this call.
     */
    void put(String key, String val);

    /**
     * See
     * <a href="https://www.javadoc.io/doc/org.slf4j/slf4j-api/1.7.26/org/slf4j/spi/MDCAdapter.html">
     *     https://www.javadoc.io/doc/org.slf4j/slf4j-api/1.7.26/org/slf4j/spi/MDCAdapter.html</a>.
     *
     * <p>
     * Get the context identified by the <code>key</code> parameter.
     * The <code>key</code> parameter cannot be null.
     *
     * @return the string value identified by the <code>key</code> parameter.
     */
    String get(String key);

    /**
     * See
     * <a href="https://www.javadoc.io/doc/org.slf4j/slf4j-api/1.7.26/org/slf4j/spi/MDCAdapter.html">
     *     https://www.javadoc.io/doc/org.slf4j/slf4j-api/1.7.26/org/slf4j/spi/MDCAdapter.html</a>.
     *
     * <p>
     * Remove the the context identified by the <code>key</code> parameter.
     * The <code>key</code> parameter cannot be null.
     *
     * <p>
     * This method does nothing if there is no previous value
     * associated with <code>key</code>.
     */
    void remove(String key);

    /**
     * See
     * <a href="https://www.javadoc.io/doc/org.slf4j/slf4j-api/1.7.26/org/slf4j/spi/MDCAdapter.html">
     *     https://www.javadoc.io/doc/org.slf4j/slf4j-api/1.7.26/org/slf4j/spi/MDCAdapter.html</a>.
     *
     * <p>
     * Clear all entries in the MDC.
     */
    void clear();

    /**
     * See
     * <a href="https://www.javadoc.io/doc/org.slf4j/slf4j-api/1.7.26/org/slf4j/spi/MDCAdapter.html">
     *     https://www.javadoc.io/doc/org.slf4j/slf4j-api/1.7.26/org/slf4j/spi/MDCAdapter.html</a>.
     *
     * <p>
     * Return a copy of the current thread's context map, with keys and
     * values of type String. Returned value may be null.
     *
     * @return A copy of the current thread's context map. May be null.
     * @since 1.5.1
     */
    Map<String, String> getCopyOfContextMap();

    /**
     * See
     * <a href="https://www.javadoc.io/doc/org.slf4j/slf4j-api/1.7.26/org/slf4j/spi/MDCAdapter.html">
     *     https://www.javadoc.io/doc/org.slf4j/slf4j-api/1.7.26/org/slf4j/spi/MDCAdapter.html</a>.
     *
     * <p>
     * Set the current thread's context map by first clearing any existing
     * map and then copying the map passed as parameter. The context map
     * parameter must only contain keys and values of type String.
     *
     * @param contextMap must contain only keys and values of type String
     *
     * @since 1.5.1
     */
    void setContextMap(Map<String, String> contextMap);
}
