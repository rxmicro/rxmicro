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
import io.rxmicro.test.dbunit.TestValueProviderConfig;
import io.rxmicro.test.dbunit.internal.data.ExpressionValueResolver;
import io.rxmicro.test.dbunit.internal.data.type.InstantIntervalValue;

import java.time.Duration;
import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.List;

import static io.rxmicro.config.Configs.getConfig;
import static io.rxmicro.test.dbunit.internal.TestValueProviders.getTestValueProvider;

/**
 * @author nedis
 * @since 0.7
 */
public final class NowInstantExpressionValueResolver implements ExpressionValueResolver {

    private static final String NAME = "now";

    @Override
    public List<String> getExamples() {
        return List.of("${now}", "${now:PT1S}", "${now:PT2.345S}");
    }

    @Override
    public boolean isSupport(final String expression) {
        return expression.startsWith(NAME);
    }

    @Override
    public Object resolve(final String expression) {
        final Duration delta = getDelta(expression);
        final Instant instant = (Instant) getTestValueProvider(Expressions.NOW_INSTANT).getValue();
        return new InstantIntervalValue(instant, delta);
    }

    private Duration getDelta(final String expression) {
        if (expression.length() > 4) {
            try {
                return Duration.parse(expression.substring(4));
            } catch (final DateTimeParseException ex) {
                throw new ConfigException(
                        "Invalid ${?} expression: '?'. Delta must be parsable duration: '?'. Valid examples are: ?!",
                        NAME, expression, ex.getMessage(), getExamples()
                );
            }
        } else {
            if (expression.length() == 4) {
                if (expression.charAt(3) != ':') {
                    throw new ConfigException(
                            "Invalid ${?} expression: '?'. Expression must use ':' separator! Valid examples are: ?!",
                            NAME, expression, getExamples()
                    );
                }
            }
            return getConfig(TestValueProviderConfig.class).getNowInstantCompareDelta();
        }
    }
}
