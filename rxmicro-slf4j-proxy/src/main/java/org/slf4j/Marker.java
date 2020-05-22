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

import java.io.Serializable;
import java.util.Iterator;

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
 * Markers are named objects used to enrich log statements. Conforming logging
 * system Implementations of SLF4J determine how information conveyed by markers
 * are used, if at all. In particular, many conforming logging systems ignore
 * marker data.
 *
 * <p>
 * Markers can contain references to other markers, which in turn may contain
 * references of their own.
 *
 * @author nedis
 * @see <a href="http://www.slf4j.org/apidocs/org/slf4j/Marker.html">http://www.slf4j.org/apidocs/org/slf4j/Marker.html</a>
 * @since 0.3
 */
public interface Marker extends Serializable {

    /**
     * See <a href="http://www.slf4j.org/apidocs/org/slf4j/Marker.html">http://www.slf4j.org/apidocs/org/slf4j/Marker.html</a>.
     *
     * <p>
     * This constant represents any marker, including a {@code null} marker.
     */
    String ANY_MARKER = "*";

    /**
     * See <a href="http://www.slf4j.org/apidocs/org/slf4j/Marker.html">http://www.slf4j.org/apidocs/org/slf4j/Marker.html</a>.
     *
     * <p>
     * This constant represents any non-null marker.
     */
    String ANY_NON_NULL_MARKER = "+";

    /**
     * See <a href="http://www.slf4j.org/apidocs/org/slf4j/Marker.html">http://www.slf4j.org/apidocs/org/slf4j/Marker.html</a>.
     *
     * <p>
     * Get the name of this Marker.
     *
     * @return name of marker
     */
    String getName();

    /**
     * See <a href="http://www.slf4j.org/apidocs/org/slf4j/Marker.html">http://www.slf4j.org/apidocs/org/slf4j/Marker.html</a>.
     *
     * <p>
     * Add a reference to another Marker.
     *
     * @param reference a reference to another marker
     * @throws IllegalArgumentException if 'reference' is {@code null}
     */
    void add(Marker reference);

    /**
     * See <a href="http://www.slf4j.org/apidocs/org/slf4j/Marker.html">http://www.slf4j.org/apidocs/org/slf4j/Marker.html</a>.
     *
     * <p>
     * Remove a marker reference.
     *
     * @param reference the marker reference to remove
     * @return {@code true} if reference could be found and removed, {@code false} otherwise.
     */
    boolean remove(Marker reference);

    /**
     * See <a href="http://www.slf4j.org/apidocs/org/slf4j/Marker.html">http://www.slf4j.org/apidocs/org/slf4j/Marker.html</a>.
     *
     * <p>
     * Does this marker have any references?
     *
     * @deprecated Replaced by {@link #hasReferences()}.
     * @return {@code true} if this marker has one or more references, {@code false} otherwise.
     */
    @Deprecated
    boolean hasChildren();

    /**
     * See <a href="http://www.slf4j.org/apidocs/org/slf4j/Marker.html">http://www.slf4j.org/apidocs/org/slf4j/Marker.html</a>.
     *
     * <p>
     * Does this marker have any references?
     *
     * @return {@code true} if this marker has one or more references, {@code false} otherwise.
     */
    boolean hasReferences();

    /**
     * See <a href="http://www.slf4j.org/apidocs/org/slf4j/Marker.html">http://www.slf4j.org/apidocs/org/slf4j/Marker.html</a>.
     *
     * <p>
     * Returns an Iterator which can be used to iterate over the references of this
     * marker. An empty iterator is returned when this marker has no references.
     *
     * @return Iterator over the references of this marker
     */
    Iterator<Marker> iterator();

    /**
     * See <a href="http://www.slf4j.org/apidocs/org/slf4j/Marker.html">http://www.slf4j.org/apidocs/org/slf4j/Marker.html</a>.
     *
     * <p>
     * Does this marker contain a reference to the 'other' marker? Marker A is defined 
     * to contain marker B, if A == B or if B is referenced by A, or if B is referenced
     * by any one of A's references (recursively).
     *
     * @param other The marker to test for inclusion.
     * @return Whether this marker contains the other marker.
     * @throws IllegalArgumentException if 'other' is {@code null}
     */
    boolean contains(Marker other);

    /**
     * See <a href="http://www.slf4j.org/apidocs/org/slf4j/Marker.html">http://www.slf4j.org/apidocs/org/slf4j/Marker.html</a>.
     *
     * <p>
     * Does this marker contain the marker named 'name'?
     *
     * <p>
     * If 'name' is {@code null} the returned value is always {@code false}.
     *
     * @param name The marker name to test for inclusion.
     * @return Whether this marker contains the other marker.
     */
    boolean contains(String name);
}
