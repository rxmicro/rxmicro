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
import io.rxmicro.test.dbunit.internal.data.value.LongIntervalValue;

import java.util.List;
import java.util.Set;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.test.dbunit.Expressions.BIGINT_INTERVAL;
import static io.rxmicro.test.dbunit.Expressions.INTEGER_INTERVAL_1;
import static io.rxmicro.test.dbunit.Expressions.INTEGER_INTERVAL_2;
import static io.rxmicro.test.dbunit.Expressions.LONG_INTERVAL;
import static io.rxmicro.test.dbunit.Expressions.SHORT_INTERVAL;
import static io.rxmicro.test.dbunit.Expressions.SMALLINT_INTERVAL;
import static io.rxmicro.test.dbunit.Expressions.TINYINT_INTERVAL;

/**
 * @author nedis
 * @since 0.7
 */
public final class LongIntervalExpressionValueResolver extends AbstractExpressionValueResolver{

    public LongIntervalExpressionValueResolver() {
        super(Set.of(
                INTEGER_INTERVAL_1,
                INTEGER_INTERVAL_2,
                TINYINT_INTERVAL,
                SHORT_INTERVAL,
                SMALLINT_INTERVAL,
                LONG_INTERVAL,
                BIGINT_INTERVAL
        ));
    }

    @Override
    public List<String> getExamples() {
        return List.of(
                format("${?:4:100}", INTEGER_INTERVAL_1),
                format("${?:4:100}", INTEGER_INTERVAL_2),
                format("${?:4:100}", TINYINT_INTERVAL),
                format("${?:4:100}", SHORT_INTERVAL),
                format("${?:4:100}", SMALLINT_INTERVAL),
                format("${?:4:100}", LONG_INTERVAL),
                format("${?:4:100}", BIGINT_INTERVAL)
        );
    }

    @Override
    public Object resolve(final String exp) {
        final ParsedExpression expression = parse(exp);
        if (expression.getParamsSize() == 2) {
            final long min = parseLong(expression, expression.getParam(0));
            final long max = parseLong(expression, expression.getParam(1));
            validateInterval(expression, min, max);
            return new LongIntervalValue(min, max);
        } else {
            throw new ConfigException(
                    "Invalid ${?} expression: '?'. Expression must follow the next templates: '${?:${min}:${max}}' Valid examples are: ?!",
                    expression.getName(), expression, expression.getName(), getExamples()
            );
        }
    }

    private long parseLong(final ParsedExpression expression,
                           final String value) {
        try {
            return Long.parseLong(value);
        } catch (final NumberFormatException ex) {
            throw new ConfigException(
                    "Invalid ${?} expression: '?'. '?' value must be parsable long integer: '?'. Valid examples are: ?!",
                    expression.getName(), expression, value, ex.getMessage(), getExamples()
            );
        }
    }
}
