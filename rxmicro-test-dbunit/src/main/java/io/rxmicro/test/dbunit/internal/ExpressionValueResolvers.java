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

package io.rxmicro.test.dbunit.internal;

import io.rxmicro.config.ConfigException;
import io.rxmicro.test.dbunit.internal.data.ExpressionValueResolver;
import io.rxmicro.test.dbunit.internal.data.resolver.InstantIntervalExpressionValueResolver;
import io.rxmicro.test.dbunit.internal.data.resolver.LongIntervalExpressionValueResolver;
import io.rxmicro.test.dbunit.internal.data.resolver.NowInstantExpressionValueResolver;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author nedis
 * @since 0.7
 */
public final class ExpressionValueResolvers {

    private static final List<ExpressionValueResolver> EXPRESSION_VALUE_RESOLVERS = List.of(
            new NowInstantExpressionValueResolver(),
            new InstantIntervalExpressionValueResolver(),
            new LongIntervalExpressionValueResolver()
    );

    public static boolean isExpressionValue(final Object value) {
        return value instanceof String && ExpressionValueResolver.isExpression((String) value);
    }

    public static Object resolveExpressionValue(final Object value) {
        final String stringValue = (String) value;
        // Extract '${' and '}' fragments:
        final String expression = stringValue.substring(2, stringValue.length() - 1);
        for (final ExpressionValueResolver expressionValueResolver : EXPRESSION_VALUE_RESOLVERS) {
            if (expressionValueResolver.isSupport(expression)) {
                return expressionValueResolver.resolve(expression);
            }
        }
        throw new ConfigException(
                "Unsupported test value expression: '?'! " +
                        "Currently the RxMicro framework supports the following expressions: ?",
                stringValue,
                EXPRESSION_VALUE_RESOLVERS.stream().flatMap(resolver -> resolver.getExamples().stream()).collect(toList())
        );
    }

    private ExpressionValueResolvers() {
    }
}
