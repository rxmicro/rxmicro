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
import io.rxmicro.test.dbunit.internal.data.ExpressionValueResolver;
import io.rxmicro.test.dbunit.internal.data.type.InstantIntervalValue;

import java.time.Duration;
import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.List;

import static io.rxmicro.test.dbunit.internal.TestValueProviders.getTestValueProvider;

/**
 * @author nedis
 * @since 0.7
 */
public final class InstantIntervalExpressionValueResolver implements ExpressionValueResolver {

    private static final String NAME = "interval";

    @Override
    public List<String> getExamples() {
        return List.of(
                "${interval:now:-PT5S:PT5S}",
                "${interval:now+PT5S:-PT5S:PT5S}",
                "${interval:now-PT5S:now+PT5S}",
                "${interval:now:now+PT5S}",
                "${interval:2020-01-15T10:25:45Z:2020-01-16T11:35:55Z}",
                "${interval:2020-01-15T10:25:45.123Z:now+PT5S}"
        );
    }

    @Override
    public boolean isSupport(final String expression) {
        return expression.startsWith(NAME);
    }

    @Override
    public Object resolve(final String expression) {
        final String[] parts = expression.split(":");
        if (parts.length == 3) {
            final Instant minValue = parseComplexInstant(expression, parts[1]);
            final Instant maxValue = parseComplexInstant(expression, parts[2]);
            return new InstantIntervalValue(minValue, maxValue);
        } else if (parts.length == 4) {
            final Instant value = parseComplexInstant(expression, parts[1]);
            final Duration minDelta = parseDuration(expression, parts[2]);
            final Duration maxDelta = parseDuration(expression, parts[3]);
            return new InstantIntervalValue(value.plusMillis(minDelta.toMillis()), value.plusMillis(maxDelta.toMillis()));
        } else {
            throw new ConfigException(
                    "Invalid ${?} expression: '?'. Expression must follow the next templates: '${interval:${minInstant}:${maxInstant}}' " +
                            "or '${interval:${instant}:${minDelta}:${maxDelta}}'. Valid examples are: ?!",
                    NAME, expression, getExamples()
            );
        }
    }

    private Instant parseComplexInstant(final String expression,
                                        final String instantValue) {
        final List<String> parts = splitBySign(instantValue);
        if(parts.size() == 1){
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

    private Instant parseInstant(final String expression,
                                 final String instant) {
        if ("now".equals(instant)) {
            return (Instant) getTestValueProvider(Expressions.NOW_INSTANT).getValue();
        } else {
            try {
                return Instant.parse(instant);
            } catch (final DateTimeParseException ex) {
                throw new ConfigException(
                        "Invalid ${?} expression: '?'. '?' value must be parsable instant: '?'. Valid examples are: ?!",
                        NAME, expression, instant, ex.getMessage(), getExamples()
                );
            }
        }
    }

    private Duration parseDuration(final String expression,
                                   final String duration) {
        try {
            return Duration.parse(duration);
        } catch (final DateTimeParseException ex) {
            throw new ConfigException(
                    "Invalid ${?} expression: '?'. '?' delta must be parsable duration: '?'. Valid examples are: ?!",
                    NAME, expression, duration, ex.getMessage(), getExamples()
            );
        }
    }
}
