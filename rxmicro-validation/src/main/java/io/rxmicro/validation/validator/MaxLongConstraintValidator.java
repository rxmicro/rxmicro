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

/**
 * Validator for the {@link io.rxmicro.validation.constraint.MaxInt} and the {@link Max} constraints.
 *
 * @author nedis
 * @see io.rxmicro.validation.constraint.MaxInt
 * @see Max
 * @since 0.1
 */
public class MaxLongConstraintValidator extends AbstractMaxConstraintValidator<Long>
        implements ConstraintValidator<Long> {

    /**
     * Creates the default instance of {@link MaxLongConstraintValidator} with the specified parameters.
     *
     * @param maxValue  the supported max value.
     * @param inclusive whether the specified minimum is inclusive or exclusive.
     */
    public MaxLongConstraintValidator(final long maxValue,
                                      final boolean inclusive) {
        super(maxValue, inclusive);
    }

    /**
     * Creates the default instance of {@link MaxLongConstraintValidator} with the specified parameters.
     *
     * @param maxValue  the supported max value.
     * @param inclusive whether the specified minimum is inclusive or exclusive.
     */
    public MaxLongConstraintValidator(final String maxValue,
                                      final boolean inclusive) {
        super(Long.parseLong(maxValue), inclusive);
    }
}
