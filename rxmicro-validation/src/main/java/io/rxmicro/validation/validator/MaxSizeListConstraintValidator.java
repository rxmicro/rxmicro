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
import io.rxmicro.validation.base.AbstractContainerConstraintValidator;

import java.util.List;

import static io.rxmicro.validation.internal.ConstraintValidators.validateMaxValue;

/**
 * Validator for the {@link io.rxmicro.validation.constraint.MaxSize} constraint.
 *
 * @author nedis
 * @see io.rxmicro.validation.constraint.MaxSize
 * @since 0.1
 */
public class MaxSizeListConstraintValidator extends AbstractContainerConstraintValidator<List<?>>
        implements ConstraintValidator<List<?>> {

    private final int maxValue;

    private final boolean inclusive;

    /**
     * Creates the default instance of {@link MaxSizeListConstraintValidator} with the specified parameters.
     *
     * @param maxValue the supported max value.
     * @param inclusive whether the specified minimum is inclusive or exclusive.
     */
    public MaxSizeListConstraintValidator(final int maxValue,
                                          final boolean inclusive) {

        this.maxValue = maxValue;
        this.inclusive = inclusive;
    }

    @Override
    public void validate(final List<?> values,
                         final HttpModelType httpModelType,
                         final String modelName) {
        if (values != null) {
            final int actual = values.size();
            validateMaxValue(
                    maxValue, inclusive, actual, httpModelType, modelName,
                    "Invalid ? \"?\": Expected that array length <= ?, but actual is ?. (array: " + values + ")!",
                    "Invalid ? \"?\": Expected that array length < ?, but actual is ?. (array: " + values + ")!"
            );
        }
    }
}
