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

package io.rxmicro.test.dbunit.internal.data.provider;

import io.rxmicro.test.dbunit.TestValueProviderConfig;
import io.rxmicro.test.dbunit.internal.data.TestValueProvider;

import java.time.Instant;

import static io.rxmicro.config.Configs.getConfig;
import static java.time.temporal.ChronoUnit.MILLIS;
import static java.time.temporal.ChronoUnit.SECONDS;

/**
 * Returns now instant with discrete intervals.
 * (By default interval equals to {@link TestValueProviderConfig#DEFAULT_NOW_INSTANT_GENERATION_DISCRETE_STEP}).
 * This feature allows to compare ${now} before test with ${now} after test correctly!
 *
 * @author nedis
 * @see TestValueProviderConfig#getNowInstantGenerationDiscreteStep()
 * @since 0.7
 */
public final class NowInstantTestValueProvider implements TestValueProvider {

    private Instant lastUsed;

    @Override
    public Object getValue() {
        final Instant realNow = Instant.now().truncatedTo(SECONDS);
        final long amountToSubtract = getConfig(TestValueProviderConfig.class).getNowInstantGenerationDiscreteStep().toMillis();
        if (lastUsed == null || realNow.minus(amountToSubtract, MILLIS).compareTo(lastUsed) > 0) {
            lastUsed = realNow;
        }
        return lastUsed;
    }
}
