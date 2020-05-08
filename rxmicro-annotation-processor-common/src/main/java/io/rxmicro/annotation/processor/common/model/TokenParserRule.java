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

import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

import static io.rxmicro.common.util.ExCollections.join;
import static java.util.stream.Collectors.toSet;

/**
 * @author nedis
 * @since 0.1
 */
public abstract class TokenParserRule {

    private final Map<String, SortedSet<String>> sortedOperatorMap;

    private final Set<String> lineCommentStartedTokens;

    private final Set<String> multiLineCommentStartedTokens;

    private final Set<String> multiLineCommentFinishedTokens;

    private final Set<Character> multiLineCommentFinishedTokenFirstChars;

    public TokenParserRule() {
        lineCommentStartedTokens = getLineCommentStartedTokens();
        multiLineCommentStartedTokens = getMultiLineCommentStartedTokens();
        multiLineCommentFinishedTokens = getMultiLineCommentFinishedTokens();
        multiLineCommentFinishedTokenFirstChars = multiLineCommentFinishedTokens.stream()
                .map(s -> s.charAt(0))
                .collect(toSet());
        final Set<String> significantTokenDelimiters = join(
                getOperatorTokenDelimiters(),
                getNotOperatorTokenDelimiters(),
                lineCommentStartedTokens,
                multiLineCommentStartedTokens,
                multiLineCommentFinishedTokens
        );
        sortedOperatorMap = new SortedOperatorMapBuilder(significantTokenDelimiters)
                .buildUnmodifiableMapWithSortedValues((o1, o2) -> {
                    final int res = o2.length() - o1.length();
                    return res == 0 ? o1.compareTo(o2) : res;
                });
    }

    public boolean isIgnoredDelimiter(final char ch) {
        return Set.of(' ', '\u00A0', '\n', '\t', '\r').contains(ch);
    }

    public boolean isStringDelimiter(final char ch) {
        return Set.of('\'', '"').contains(ch);
    }

    public Map<String, SortedSet<String>> getSortedOperatorMap() {
        return sortedOperatorMap;
    }

    @SuppressWarnings("SameReturnValue")
    public abstract boolean supportVariables();

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

    protected Set<String> getLineCommentStartedTokens() {
        return Set.of();
    }

    protected Set<String> getMultiLineCommentStartedTokens() {
        return Set.of();
    }

    protected Set<String> getMultiLineCommentFinishedTokens() {
        return Set.of();
    }

    protected abstract Set<String> getOperatorTokenDelimiters();

    protected abstract Set<String> getNotOperatorTokenDelimiters();
}
