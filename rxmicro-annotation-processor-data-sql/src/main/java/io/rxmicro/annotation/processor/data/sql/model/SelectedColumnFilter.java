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

import java.util.Set;

import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class SelectedColumnFilter {

    private final int startIndex;

    private final Set<String> ignoredTokens;

    private final Set<String> breakTokens;

    private SelectedColumnFilter(final int startIndex,
                                 final Set<String> ignoredTokens,
                                 final Set<String> breakTokens) {
        this.startIndex = startIndex;
        this.ignoredTokens = require(ignoredTokens);
        this.breakTokens = require(breakTokens);
    }

    public int getStartIndex() {
        return startIndex;
    }

    public Set<String> getIgnoredTokens() {
        return ignoredTokens;
    }

    public Set<String> getBreakTokens() {
        return breakTokens;
    }

    /**
     * @author nedis
     * @link https://rxmicro.io
     * @since 0.1
     */
    @SuppressWarnings("UnusedReturnValue")
    public static final class Builder {

        private int startIndex;

        private Set<String> ignoredTokens = Set.of();

        private Set<String> breakTokens = Set.of();

        public Builder setStartIndex(final int startIndex) {
            this.startIndex = startIndex;
            return this;
        }

        public Builder setIgnoredTokens(final Set<String> ignoredTokens) {
            this.ignoredTokens = ignoredTokens;
            return this;
        }

        public Builder setBreakTokens(final Set<String> breakTokens) {
            this.breakTokens = breakTokens;
            return this;
        }

        public SelectedColumnFilter build() {
            return new SelectedColumnFilter(startIndex, ignoredTokens, breakTokens);
        }
    }
}
