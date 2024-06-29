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

package io.rxmicro.config;

import io.rxmicro.validation.constraint.MaxInt;
import io.rxmicro.validation.constraint.MinInt;

/**
 * @author nedis
 * @since 0.12
 */
@SingletonConfigClass
public final class TestForValidationConfig extends Config {

    @MinInt(Thread.MIN_PRIORITY)
    @MaxInt(Thread.MAX_PRIORITY)
    private Integer threadPriority = Thread.NORM_PRIORITY;

    public TestForValidationConfig() {
        super(Config.getDefaultNameSpace(TestForValidationConfig.class));
    }

    public Integer getThreadPriority() {
        return threadPriority;
    }

    public TestForValidationConfig setThreadPriority(final Integer threadPriority) {
        this.threadPriority = ensureValid(threadPriority);
        return this;
    }
}
