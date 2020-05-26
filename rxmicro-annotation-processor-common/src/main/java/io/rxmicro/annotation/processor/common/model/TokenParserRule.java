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

package io.rxmicro.annotation.processor.common.model;

import io.rxmicro.annotation.processor.common.util.SortedOperatorMapBuilder;
import io.rxmicro.common.meta.BuilderMethod;

import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

import static io.rxmicro.common.util.ExCollections.join;
import static io.rxmicro.common.util.Requires.require;
import static java.util.stream.Collectors.toSet;

/**
 * @author nedis
 * @since 0.1
 */
public final class TokenParserRule {

    private final boolean variablesSupported;

    private final Set<Character> ignoredDelimiter;

    private final Set<Character> stringDelimiter;

    private final Set<String> lineCommentStartedTokens;

    private final Set<String> multiLineCommentStartedTokens;

    private final Set<String> multiLineCommentFinishedTokens;

    private final Set<Character> multiLineCommentFinishedTokenFirstChars;

    private final Map<String, SortedSet<String>> sortedOperatorMap;

    private TokenParserRule(final boolean variablesSupported,
                            final Set<Character> ignoredDelimiter,
                            final Set<Character> stringDelimiter,
                            final Set<String> lineCommentStartedTokens,
                            final Set<String> multiLineCommentStartedTokens,
                            final Set<String> multiLineCommentFinishedTokens,
                            final Map<String, SortedSet<String>> sortedOperatorMap) {
        this.variablesSupported = variablesSupported;
        this.ignoredDelimiter = require(ignoredDelimiter);
        this.stringDelimiter = require(stringDelimiter);
        this.lineCommentStartedTokens = require(lineCommentStartedTokens);
        this.multiLineCommentStartedTokens = require(multiLineCommentStartedTokens);
        this.multiLineCommentFinishedTokens = require(multiLineCommentFinishedTokens);
        this.multiLineCommentFinishedTokenFirstChars = multiLineCommentFinishedTokens.stream()
                .map(s -> s.charAt(0))
                .collect(toSet());
        this.sortedOperatorMap = require(sortedOperatorMap);
    }

    public boolean isIgnoredDelimiter(final char ch) {
        return ignoredDelimiter.contains(ch);
    }

    public boolean isStringDelimiter(final char ch) {
        return stringDelimiter.contains(ch);
    }

    public Map<String, SortedSet<String>> getSortedOperatorMap() {
        return sortedOperatorMap;
    }

    public boolean areVariablesSupported() {
        return variablesSupported;
    }

    public boolean isLineBlank(final String line) {
        return line.isEmpty() || line.chars().allMatch(ch -> isIgnoredDelimiter((char) ch));
    }

    public boolean isLineStartsWithLineComment(final String line) {
        return lineCommentStartedTokens.stream().anyMatch(line::startsWith);
    }

    public boolean isLineCommentToken(final String token) {
        return lineCommentStartedTokens.contains(token);
    }

    public boolean isMultiLineCommentStartedToken(final String token) {
        return multiLineCommentStartedTokens.contains(token);
    }

    public boolean isMultiLineCommentFinishedTokenCandidate(final char ch) {
        return multiLineCommentFinishedTokenFirstChars.contains(ch);
    }

    public boolean isMultiLineCommentFinishedToken(final String token) {
        return multiLineCommentFinishedTokens.contains(token);
    }

    /**
     * @author nedis
     * @since 0.5
     */
    public static final class Builder {

        private boolean variablesSupported;

        private Set<Character> ignoredDelimiter = Set.of(' ', '\u00A0', '\n', '\t', '\r');

        private Set<Character> stringDelimiter = Set.of('\'', '"');

        private Set<String> lineCommentStartedTokens = Set.of();

        private Set<String> multiLineCommentStartedTokens = Set.of();

        private Set<String> multiLineCommentFinishedTokens = Set.of();

        private Set<String> operatorTokenDelimiters = Set.of();

        private Set<String> notOperatorTokenDelimiters = Set.of();

        @BuilderMethod
        public Builder setVariablesSupported(final boolean variablesSupported) {
            this.variablesSupported = variablesSupported;
            return this;
        }

        @BuilderMethod
        public Builder setIgnoredDelimiter(final Set<Character> ignoredDelimiter) {
            this.ignoredDelimiter = require(ignoredDelimiter);
            return this;
        }

        @BuilderMethod
        public Builder setStringDelimiter(final Set<Character> stringDelimiter) {
            this.stringDelimiter = require(stringDelimiter);
            return this;
        }

        @BuilderMethod
        public Builder setLineCommentStartedTokens(final Set<String> lineCommentStartedTokens) {
            this.lineCommentStartedTokens = require(lineCommentStartedTokens);
            return this;
        }

        @BuilderMethod
        public Builder setMultiLineCommentStartedTokens(final Set<String> multiLineCommentStartedTokens) {
            this.multiLineCommentStartedTokens = require(multiLineCommentStartedTokens);
            return this;
        }

        @BuilderMethod
        public Builder setMultiLineCommentFinishedTokens(final Set<String> multiLineCommentFinishedTokens) {
            this.multiLineCommentFinishedTokens = require(multiLineCommentFinishedTokens);
            return this;
        }

        @BuilderMethod
        public Builder setOperatorTokenDelimiters(final Set<String> operatorTokenDelimiters) {
            this.operatorTokenDelimiters = require(operatorTokenDelimiters);
            return this;
        }

        @BuilderMethod
        public Builder setNotOperatorTokenDelimiters(final Set<String> notOperatorTokenDelimiters) {
            this.notOperatorTokenDelimiters = require(notOperatorTokenDelimiters);
            return this;
        }

        public TokenParserRule build() {
            final Set<String> significantTokenDelimiters = join(
                    operatorTokenDelimiters,
                    notOperatorTokenDelimiters,
                    lineCommentStartedTokens,
                    multiLineCommentStartedTokens,
                    multiLineCommentFinishedTokens
            );
            return new TokenParserRule(
                    variablesSupported,
                    ignoredDelimiter,
                    stringDelimiter,
                    lineCommentStartedTokens,
                    multiLineCommentStartedTokens,
                    multiLineCommentFinishedTokens,
                    buildSortedOperatorMap(significantTokenDelimiters)
            );
        }

        private Map<String, SortedSet<String>> buildSortedOperatorMap(final Set<String> significantTokenDelimiters) {
            return new SortedOperatorMapBuilder(significantTokenDelimiters)
                    .buildUnmodifiableMapWithSortedValues((o1, o2) -> {
                        final int res = o2.length() - o1.length();
                        return res == 0 ? o1.compareTo(o2) : res;
                    });
        }
    }
}
