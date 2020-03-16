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

package io.rxmicro.annotation.processor.common.component.impl;

import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.common.component.TokenParser;
import io.rxmicro.annotation.processor.common.model.TokenParserResult;
import io.rxmicro.annotation.processor.common.model.TokenParserRule;
import io.rxmicro.common.model.StringIterator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
@Singleton
public final class TokenParserImpl implements TokenParser {

    @Override
    public TokenParserResult parse(final String line,
                                   final TokenParserRule rule,
                                   final boolean multilineCommentStarted) {
        if (rule.isLineBlank(line)) {
            return new TokenParserResult(multilineCommentStarted);
        } else if (!multilineCommentStarted && rule.isLineStartsWithLineComment(line)) {
            return new TokenParserResult(false);
        } else {
            final List<String> tokens = new ArrayList<>();
            final StringIterator iterator = new StringIterator(line);
            final boolean multilineCommentStartedResult = extractTokens(
                    rule, tokens, iterator, multilineCommentStarted
            );
            return new TokenParserResult(tokens, multilineCommentStartedResult);
        }
    }

    private boolean extractTokens(final TokenParserRule rule,
                                  final List<String> tokens,
                                  final StringIterator iterator,
                                  final boolean multilineCommentStarted) {
        final StringBuilder tokenBuilder = new StringBuilder();
        boolean multilineCommentStartedResult = multilineCommentStarted;
        while (iterator.next()) {
            final char ch = iterator.getCurrent();
            if (multilineCommentStartedResult) {
                if (rule.isMultiLineCommentFinishedTokenCandidate(ch) &&
                        rule.isMultiLineCommentFinishedToken(readOperatorToken(rule.getSortedOperatorMap(), ch, iterator))) {
                    multilineCommentStartedResult = false;
                }
            } else if (rule.isIgnoredDelimiter(ch)) {
                addTokenIfPresent(tokens, tokenBuilder);
            } else if (rule.isStringDelimiter(ch)) {
                addTokenIfPresent(tokens, tokenBuilder);
                tokens.add(readStringToken(ch, iterator));
            } else if (isOperator(rule.getSortedOperatorMap(), ch)) {
                addTokenIfPresent(tokens, tokenBuilder);
                final String operator = readOperatorToken(rule.getSortedOperatorMap(), ch, iterator);
                if (rule.isLineCommentToken(operator)) {
                    return false;
                } else if (rule.isMultiLineCommentStartedToken(operator)) {
                    multilineCommentStartedResult = true;
                } else {
                    tokens.add(operator);
                }
            } else if (ch == '$') {
                if (rule.supportVariables()) {
                    if (iterator.next()) {
                        if ('{' == iterator.getCurrent()) {
                            addTokenIfPresent(tokens, tokenBuilder);
                            tokens.add(readVariableToken(iterator));
                        } else {
                            tokenBuilder.append(ch);
                        }
                    } else {
                        break;
                    }
                } else {
                    tokenBuilder.append(ch);
                }
            } else {
                tokenBuilder.append(ch);
            }
        }
        addTokenIfPresent(tokens, tokenBuilder);
        return multilineCommentStartedResult;
    }

    private boolean isOperator(final Map<String, SortedSet<String>> sortedOperatorMap,
                               final char ch) {
        return sortedOperatorMap.containsKey(String.valueOf(ch));
    }

    private String readVariableToken(final StringIterator iterator) {
        final StringBuilder stringBuilder = new StringBuilder().append("${");
        while (iterator.next()) {
            final char ch = iterator.getCurrent();
            stringBuilder.append(ch);
            if ('}' == ch) {
                return stringBuilder.toString();
            }
        }
        return stringBuilder.toString();
    }

    private String readStringToken(final char firstChar,
                                   final StringIterator iterator) {
        final StringBuilder stringBuilder = new StringBuilder().append(firstChar);
        while (iterator.next()) {
            final char ch = iterator.getCurrent();
            stringBuilder.append(ch);
            if (firstChar == ch) {
                return stringBuilder.toString();
            }
        }
        return stringBuilder.toString();
    }

    private String readOperatorToken(final Map<String, SortedSet<String>> sortedOperatorMap,
                                     final char operator,
                                     final StringIterator iterator) {
        final String op = String.valueOf(operator);
        final Set<String> possibleOperators = sortedOperatorMap.get(op);
        if (possibleOperators.isEmpty()) {
            return op;
        } else {
            return readOperatorToken(operator, iterator, possibleOperators);
        }
    }

    private String readOperatorToken(final char operator,
                                     final StringIterator iterator,
                                     final Set<String> possibleOperators) {
        final StringBuilder actualValue = new StringBuilder().append(operator);
        fillActualValue(actualValue, iterator, getMaxPossibleOperatorLength(possibleOperators) - 1);
        for (final String possibleOperator : possibleOperators) {
            resetActualValueWithIteratorIfRequired(iterator, actualValue, possibleOperator);
            if (possibleOperator.equals(actualValue.toString())) {
                return possibleOperator;
            }
        }
        final String op = String.valueOf(operator);
        resetActualValueWithIteratorIfRequired(iterator, actualValue, op);
        return op;
    }

    private void resetActualValueWithIteratorIfRequired(final StringIterator iterator,
                                                        final StringBuilder actualValue,
                                                        final String possibleOperator) {
        while (possibleOperator.length() < actualValue.length()) {
            actualValue.deleteCharAt(actualValue.length() - 1);
            iterator.previous();
        }
    }

    private int getMaxPossibleOperatorLength(final Set<String> possibleOperators) {
        return possibleOperators.iterator().next().length();
    }

    private void fillActualValue(final StringBuilder builder,
                                 final StringIterator iterator,
                                 final int count) {
        for (int i = 0; i < count && iterator.next(); i++) {
            builder.append(iterator.getCurrent());
        }
    }

    private void addTokenIfPresent(final List<String> tokens,
                                   final StringBuilder tokenBuilder) {
        if (tokenBuilder.length() > 0) {
            tokens.add(tokenBuilder.toString());
            tokenBuilder.delete(0, tokenBuilder.length());
        }
    }
}
