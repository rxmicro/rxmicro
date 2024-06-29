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

package io.rxmicro.validation.validator;

import io.rxmicro.validation.ConstraintValidator;
import io.rxmicro.validation.base.AbstractMaxConstraintValidator;
import io.rxmicro.validation.constraint.Max;
import io.rxmicro.validation.internal.ValidatorHelper;

import java.time.Duration;

/**
 * Validator for the {@link Max} constraint.
 *
 * @author nedis
 * @see Max
 * @since 0.12
 */
public class MaxDurationConstraintValidator extends AbstractMaxConstraintValidator<Duration>
        implements ConstraintValidator<Duration> {

    /**
     * Creates the default instance of {@link MaxDurationConstraintValidator} with the specified parameters.
     *
     * @param maxValue  the supported max value.
     * @param inclusive whether the specified minimum is inclusive or exclusive.
     */
    public MaxDurationConstraintValidator(final String maxValue,
                                          final boolean inclusive) {
        super(ValidatorHelper.parseDuration(maxValue), inclusive);
    }
}
