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
import io.rxmicro.config.SingletonConfigClass;
import io.rxmicro.test.local.model.BaseTestConfig;
import io.rxmicro.validation.constraint.Min;

import java.time.Duration;

/**
 * Allows configuring the test value provider.
 *
 * @author nedis
 * @since 0.7
 */
@SingletonConfigClass
public final class TestValueProviderConfig extends BaseTestConfig {

    /**
     * Default duration of the now instant generation discrete step.
     */
    public static final Duration DEFAULT_NOW_INSTANT_GENERATION_DISCRETE_STEP = Duration.ofSeconds(5);

    @Min("PT0S")
    private Duration nowInstantGenerationDiscreteStep = DEFAULT_NOW_INSTANT_GENERATION_DISCRETE_STEP;

    public TestValueProviderConfig() {
        super(getDefaultNameSpace(TestValueProviderConfig.class));
    }

    /**
     * Returns the duration of the now instant generation discrete step.
     *
     * @return the duration of the now instant generation discrete step.
     */
    public Duration getNowInstantGenerationDiscreteStep() {
        return nowInstantGenerationDiscreteStep;
    }

    /**
     * Sets the duration of the now instant generation discrete step.
     *
     * @param nowInstantGenerationDiscreteStep the duration of the now instant generation discrete step.
     * @return the reference to this  {@link TestValueProviderConfig} instance
     */
    @BuilderMethod
    public TestValueProviderConfig setNowInstantGenerationDiscreteStep(final Duration nowInstantGenerationDiscreteStep) {
        this.nowInstantGenerationDiscreteStep = ensureValid(nowInstantGenerationDiscreteStep);
        return this;
    }

    @Override
    public String toString() {
        return "TestValueProviderConfig{" +
                "nowInstantGenerationDiscreteStep=" + nowInstantGenerationDiscreteStep +
                '}';
    }
}
