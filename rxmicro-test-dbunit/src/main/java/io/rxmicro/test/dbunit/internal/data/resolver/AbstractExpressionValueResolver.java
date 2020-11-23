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

package io.rxmicro.test.dbunit.internal.data.resolver;

import io.rxmicro.config.ConfigException;
import io.rxmicro.test.dbunit.internal.data.ExpressionValueResolver;

import java.util.List;
import java.util.Set;

import static io.rxmicro.common.util.ExCollections.unmodifiableOrderedSet;

/**
 * @author nedis
 * @since 0.7
 */
public abstract class AbstractExpressionValueResolver implements ExpressionValueResolver {

    private final Set<String> supportedExpressions;

    public AbstractExpressionValueResolver(final Set<String> supportedExpressions) {
        this.supportedExpressions = unmodifiableOrderedSet(supportedExpressions);
    }

    @Override
    public final boolean isSupport(final String expression) {
        return supportedExpressions.stream().anyMatch(expression::startsWith);
    }

    protected final ParsedExpression parse(final String expression) {
        return supportedExpressions.stream()
                .filter(expression::startsWith)
                .map(supportedExp -> new ParsedExpression(supportedExp, extractParams(supportedExp, expression)))
                .findFirst()
                .orElseThrow();
    }

    private List<String> extractParams(final String supportedExpression,
                                       final String expression) {
        if (expression.length() == supportedExpression.length()) {
            return List.of();
        } else {
            final char delimiter = expression.charAt(supportedExpression.length());
            if (':' != delimiter) {
                throw new ConfigException(
                        "Invalid ${?} expression: '?'. Expression must use ':' separator! Valid examples are: ?!",
                        supportedExpression, expression, getExamples()
                );
            }
            return List.of(expression.substring(supportedExpression.length() + 1).split(":"));
        }
    }

    protected final <T extends Comparable<T>> void validateInterval(final ParsedExpression expression,
                                                                    final T min,
                                                                    final T max) {
        if (min.compareTo(max) >= 0) {
            throw new ConfigException(
                    "Invalid interval values for ${?} expression, because ? must be < than ?!",
                    expression, min, max
            );
        }
    }

    /**
     * @author nedis
     * @since 0.7
     */
    protected static final class ParsedExpression {

        private final String name;

        private final List<String> params;

        private ParsedExpression(final String name,
                                 final List<String> params) {
            this.name = name;
            this.params = params;
        }

        public String getName() {
            return name;
        }

        public boolean isParamsPresent() {
            return !params.isEmpty();
        }

        public int getParamsSize() {
            return params.size();
        }

        public String getParam(final int index) {
            return params.get(index);
        }

        @Override
        public String toString() {
            if (params.isEmpty()) {
                return name;
            } else {
                return name + ":" + String.join(":", params);
            }
        }
    }
}
