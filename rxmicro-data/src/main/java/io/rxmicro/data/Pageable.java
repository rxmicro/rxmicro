/*
 * Copyright 2019 https://rxmicro.io
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

package io.rxmicro.data;

import java.util.Set;

/**
 * Basic class for pagination information.
 * <p>
 * This class can be passed to a dynamic data repository method.
 *
 * @author nedis
 * @since 0.1
 */
public final class Pageable {

    /**
     * Set of predefined dynamic data repository method parameter names that must be interpret by
     * the RxMicro Annotation Processor as {@code offset} or {@code skip} parameter
     */
    public static final Set<String> OFFSET_NAMES = Set.of("offset", "skip", "omit", "ignore");

    /**
     * Set of predefined dynamic data repository method parameter names that must be interpret by
     * the RxMicro Annotation Processor as {@code limit} parameter
     */
    public static final Set<String> LIMIT_NAMES = Set.of("limit", "count", "rows", "returns");

    private final int offset;

    private final int limit;

    /**
     * Creates a {@link Pageable} instance
     *
     * @param offset the number of items to be skipped during execution of query.
     * @param limit the number of items to be returned.
     */
    public Pageable(final int offset,
                    final int limit) {
        this.offset = offset;
        this.limit = limit;
    }

    public Pageable(final int limit) {
        this(0, limit);
    }

    /**
     * Returns the number of items to be skipped during execution of query.
     * <p>
     * <i>Alias for {@link #getSkip()} method.</i>
     *
     * @return the number of items to be skipped during execution of query.
     */
    public int getOffset() {
        return offset;
    }

    /**
     * Returns the number of items to be skipped during execution of query.
     * <p>
     * <i>Alias for {@link #getOffset()} method.</i>
     *
     * @return the number of items to be skipped during execution of query.
     */
    public int getSkip() {
        return offset;
    }

    /**
     * Returns the number of items to be returned.
     *
     * @return the number of items to be returned.
     */
    public int getLimit() {
        return limit;
    }
}
