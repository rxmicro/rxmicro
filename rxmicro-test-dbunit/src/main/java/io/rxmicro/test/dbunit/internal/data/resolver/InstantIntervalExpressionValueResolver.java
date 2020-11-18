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
import io.rxmicro.test.dbunit.Expressions;
import io.rxmicro.test.dbunit.internal.data.value.InstantIntervalValue;

import java.time.Duration;
import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Set;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.test.dbunit.Expressions.INSTANT_INTERVAL_1;
import static io.rxmicro.test.dbunit.Expressions.INSTANT_INTERVAL_2;
import static io.rxmicro.test.dbunit.Expressions.INSTANT_INTERVAL_3;
import static io.rxmicro.test.dbunit.internal.TestValueProviders.getTestValueProvider;

/**
 * @author nedis
 * @since 0.7
 */
public final class InstantIntervalExpressionValueResolver extends AbstractExpressionValueResolver {

    public InstantIntervalExpressionValueResolver() {
        super(Set.of(
                INSTANT_INTERVAL_1,
                INSTANT_INTERVAL_2,
                INSTANT_INTERVAL_3
        ));
    }

    @Override
    public List<String> getExamples() {
        return List.of(
                format("${?:now:-PT5S:PT5S}", INSTANT_INTERVAL_1),
                format("${?:now+PT5S:-PT5S:PT5S}", INSTANT_INTERVAL_1),
                format("${?:now-PT5S:now+PT5S}", INSTANT_INTERVAL_2),
                format("${?:now:now+PT5S}", INSTANT_INTERVAL_2),
                format("${?:2020-01-15T10:25:45Z:2020-01-16T11:35:55Z}", INSTANT_INTERVAL_2),
                format("${?:2020-01-15T10:25:45.123Z:now+PT5S}", INSTANT_INTERVAL_3)
        );
    }

    @Override
    public Object resolve(final String exp) {
        final ParsedExpression expression = parse(exp);
        if (expression.getParamsSize() == 2) {
            final Instant minValue = parseComplexInstant(expression, expression.getParam(0));
            final Instant maxValue = parseComplexInstant(expression, expression.getParam(1));
            return createInstantIntervalValue(expression, minValue, maxValue);
        } else if (expression.getParamsSize() == 3) {
            final Instant value = parseComplexInstant(expression, expression.getParam(0));
            final Duration minDelta = parseDuration(expression, expression.getParam(1));
            final Duration maxDelta = parseDuration(expression, expression.getParam(2));
            return createInstantIntervalValue(expression, value.plusMillis(minDelta.toMillis()), value.plusMillis(maxDelta.toMillis()));
        } else {
            throw new ConfigException(
                    "Invalid ${?} expression: '?'. Expression must follow the next templates: '${?:${minInstant}:${maxInstant}}' " +
                            "or '${?:${instant}:${minDelta}:${maxDelta}}'. Valid examples are: ?!",
                    expression.getName(), expression, expression.getName(),
                    expression.getName(), getExamples()
            );
        }
    }

    private InstantIntervalValue createInstantIntervalValue(final ParsedExpression expression,
                                                            final Instant minValue,
                                                            final Instant maxValue) {
        validateInterval(expression, minValue, maxValue);
        return new InstantIntervalValue(minValue, maxValue);
    }

    private Instant parseComplexInstant(final ParsedExpression expression,
                                        final String instantValue) {
        final List<String> parts = splitBySign(instantValue);
        if (parts.size() == 1) {
            return parseInstant(expression, instantValue);
        } else {
            final Instant instant = parseInstant(expression, parts.get(0));
            final Duration duration = parseDuration(expression, parts.get(1));
            return instant.plusMillis(duration.toMillis());
        }
    }

    private List<String> splitBySign(final String instant) {
        for (int i = 0; i < instant.length(); i++) {
            final char ch = instant.charAt(i);
            if (ch == '-' || ch == '+') {
                return List.of(
                        instant.substring(0, i),
                        instant.substring(i)
                );
            }
        }
        return List.of(instant);
    }

    private Instant parseInstant(final ParsedExpression expression,
                                 final String instant) {
        if ("now".equals(instant)) {
            return (Instant) getTestValueProvider(Expressions.NOW_INSTANT_1).getValue();
        } else {
            try {
                return Instant.parse(instant);
            } catch (final DateTimeParseException ex) {
                throw new ConfigException(
                        "Invalid ${?} expression: '?'. '?' value must be parsable instant: '?'. Valid examples are: ?!",
                        expression.getName(), expression, instant, ex.getMessage(), getExamples()
                );
            }
        }
    }

    private Duration parseDuration(final ParsedExpression expression,
                                   final String duration) {
        try {
            return Duration.parse(duration);
        } catch (final DateTimeParseException ex) {
            throw new ConfigException(
                    "Invalid ${?} expression: '?'. '?' delta must be parsable duration: '?'. Valid examples are: ?!",
                    expression.getName(), expression, duration, ex.getMessage(), getExamples()
            );
        }
    }
}
