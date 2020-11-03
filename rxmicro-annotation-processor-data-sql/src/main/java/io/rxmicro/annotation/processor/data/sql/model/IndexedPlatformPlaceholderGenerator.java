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

package io.rxmicro.annotation.processor.data.sql.model;

import static io.rxmicro.common.util.Formats.format;

/**
 * @author nedis
 * @since 0.1
 */
public final class IndexedPlatformPlaceholderGenerator implements PlatformPlaceholderGenerator {

    private final String prefix;

    private final String suffix;

    private int index;

    public static IndexedPlatformPlaceholderGenerator createPrefixedGenerator(final String prefix,
                                                                              final int startValue) {
        return new IndexedPlatformPlaceholderGenerator(prefix, startValue, "");
    }

    private IndexedPlatformPlaceholderGenerator(final String prefix,
                                                final int startValue,
                                                final String suffix) {
        this.prefix = prefix;
        this.index = startValue;
        this.suffix = suffix;
    }

    @Override
    public String getNextPlaceHolder() {
        return format("???", prefix, index++, suffix);
    }
}
