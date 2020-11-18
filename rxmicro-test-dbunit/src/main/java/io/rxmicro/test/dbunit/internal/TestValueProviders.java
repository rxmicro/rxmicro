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

import io.rxmicro.common.ImpossibleException;
import io.rxmicro.test.dbunit.internal.data.TestValueProvider;
import io.rxmicro.test.dbunit.internal.data.provider.NowInstantTestValueProvider;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static io.rxmicro.test.dbunit.Expressions.NOW_INSTANT_1;
import static io.rxmicro.test.dbunit.Expressions.NOW_INSTANT_2;
import static io.rxmicro.test.dbunit.Expressions.NOW_INSTANT_3;
import static java.util.Map.entry;

/**
 * @author nedis
 * @since 0.7
 */
public final class TestValueProviders {

    private static final TestValueProvider NOW_INSTANT_TEST_VALUE_PROVIDER = new NowInstantTestValueProvider();

    private static final Map<String, TestValueProvider> VALUE_PROVIDER_MAP = Map.ofEntries(
            entry(NOW_INSTANT_1, NOW_INSTANT_TEST_VALUE_PROVIDER),
            entry(NOW_INSTANT_2, NOW_INSTANT_TEST_VALUE_PROVIDER),
            entry(NOW_INSTANT_3, NOW_INSTANT_TEST_VALUE_PROVIDER)
    );

    public static TestValueProvider getTestValueProvider(final String expression) {
        return Optional.ofNullable(VALUE_PROVIDER_MAP.get(expression)).orElseThrow(() -> {
            throw new ImpossibleException("Current configuration must contain test value providers for all supported types!");
        });
    }

    public static Set<Map.Entry<String, TestValueProvider>> getAllTestValueProviders() {
        return VALUE_PROVIDER_MAP.entrySet();
    }

    private TestValueProviders() {
    }
}
