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

import io.rxmicro.rest.model.HttpModelType;
import io.rxmicro.validation.ConstraintValidator;
import io.rxmicro.validation.base.AbstractMaxConstraintValidator;

import static io.rxmicro.validation.internal.ConstraintValidators.validateMaxValue;

/**
 * Validator for the {@link io.rxmicro.validation.constraint.MaxLength} constraint.
 *
 * @author nedis
 * @see io.rxmicro.validation.constraint.MaxLength
 * @since 0.1
 */
public class MaxLengthConstraintValidator extends AbstractMaxConstraintValidator<Integer>
        implements ConstraintValidator<String> {

    /**
     * Creates the default instance of {@link MaxLengthConstraintValidator} with the specified parameters.
     *
     * @param maxValue the supported max value.
     * @param inclusive whether the specified minimum is inclusive or exclusive.
     */
    public MaxLengthConstraintValidator(final int maxValue,
                                        final boolean inclusive) {
        super(maxValue, inclusive);
    }

    @Override
    public void validateNonNull(final String value,
                                final HttpModelType httpModelType,
                                final String modelName) {
        final int actual = value.length();
        validateMaxValue(
                maxValue, inclusive, actual, httpModelType, modelName,
                "Invalid ? \"?\": Expected that 'string length' <= ?, where 'string length' is '?'!",
                "Invalid ? \"?\": Expected that 'string length' < ?, where 'string length' is '?'!"
        );
    }
}
