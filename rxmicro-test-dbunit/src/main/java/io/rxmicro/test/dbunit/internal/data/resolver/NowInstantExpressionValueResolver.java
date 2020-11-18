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
import io.rxmicro.test.GlobalTestConfig;
import io.rxmicro.test.dbunit.Expressions;
import io.rxmicro.test.dbunit.internal.data.value.InstantIntervalValue;

import java.time.Duration;
import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Set;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.config.Configs.getConfig;
import static io.rxmicro.test.dbunit.Expressions.NOW_INSTANT_1;
import static io.rxmicro.test.dbunit.Expressions.NOW_INSTANT_2;
import static io.rxmicro.test.dbunit.Expressions.NOW_INSTANT_3;
import static io.rxmicro.test.dbunit.internal.TestValueProviders.getTestValueProvider;

/**
 * @author nedis
 * @since 0.7
 */
public final class NowInstantExpressionValueResolver extends AbstractExpressionValueResolver {

    public NowInstantExpressionValueResolver() {
        super(Set.of(
                NOW_INSTANT_1,
                NOW_INSTANT_2,
                NOW_INSTANT_3
        ));
    }

    @Override
    public List<String> getExamples() {
        return List.of(
                format("${?}", NOW_INSTANT_1),
                format("${?:PT1S}", NOW_INSTANT_1),
                format("${?:PT2.345S}", NOW_INSTANT_1),
                format("${?:PT2.345S}", NOW_INSTANT_2)
        );
    }

    @Override
    public Object resolve(final String exp) {
        final ParsedExpression expression = parse(exp);
        if (expression.getParamsSize() > 1) {
            throw new ConfigException(
                    "Invalid ${?} expression: '?'. Expression must follow the next templates: '${?}' " +
                            "or '${?:${delta}}'. Valid examples are: ?!",
                    expression.getName(), expression, expression.getName(),
                    expression.getName(), getExamples()
            );
        } else {
            final Duration delta;
            if (expression.isParamsPresent()) {
                delta = getDelta(expression);
            } else {
                delta = getConfig(GlobalTestConfig.class).getDefaultInstantCompareDelta();
            }
            final Instant instant = (Instant) getTestValueProvider(Expressions.NOW_INSTANT_1).getValue();
            return new InstantIntervalValue(instant, delta);
        }
    }

    private Duration getDelta(final ParsedExpression expression) {
        try {
            return Duration.parse(expression.getParam(0));
        } catch (final DateTimeParseException ex) {
            throw new ConfigException(
                    "Invalid ${?} expression: '?'. Delta must be parsable duration: '?'. Valid examples are: ?!",
                    expression.getName(), expression, ex.getMessage(), getExamples()
            );
        }
    }
}
