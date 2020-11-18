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

package io.rxmicro.test.dbunit;

import io.rxmicro.common.meta.BuilderMethod;
import io.rxmicro.test.local.model.BaseTestConfig;

import java.time.Duration;

/**
 * @author nedis
 * @since 0.7
 */
public final class TestValueProviderConfig extends BaseTestConfig {

    public static final Duration DEFAULT_NOW_INSTANT_GENERATION_DISCRETE_STEP = Duration.ofSeconds(10);

    public static final Duration DEFAULT_NOW_INSTANT_COMPARE_DELTA = Duration.ofSeconds(5);

    private Duration nowInstantGenerationDiscreteStep = DEFAULT_NOW_INSTANT_GENERATION_DISCRETE_STEP;

    private Duration nowInstantCompareDelta = DEFAULT_NOW_INSTANT_COMPARE_DELTA;

    public Duration getNowInstantGenerationDiscreteStep() {
        return nowInstantGenerationDiscreteStep;
    }

    @BuilderMethod
    public TestValueProviderConfig setNowInstantGenerationDiscreteStep(final Duration nowInstantGenerationDiscreteStep) {
        this.nowInstantGenerationDiscreteStep = nowInstantGenerationDiscreteStep;
        return this;
    }

    public Duration getNowInstantCompareDelta() {
        return nowInstantCompareDelta;
    }

    @BuilderMethod
    public TestValueProviderConfig setNowInstantCompareDelta(final Duration nowInstantCompareDelta) {
        this.nowInstantCompareDelta = nowInstantCompareDelta;
        return this;
    }
}
