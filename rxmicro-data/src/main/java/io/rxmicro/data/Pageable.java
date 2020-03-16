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
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class Pageable {

    public static final Set<String> OFFSET_NAMES = Set.of("offset", "skip", "omit", "ignore");

    public static final Set<String> LIMIT_NAMES = Set.of("limit", "count", "rows", "returns");

    private final int offset;

    private final int limit;

    public Pageable(final int offset,
                    final int limit) {
        this.offset = offset;
        this.limit = limit;
    }

    public Pageable(final int limit) {
        this(0, limit);
    }

    public int getOffset() {
        return offset;
    }

    public int getSkip() {
        return offset;
    }

    public int getLimit() {
        return limit;
    }
}
