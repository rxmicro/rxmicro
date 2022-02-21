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

import static io.rxmicro.validation.internal.ConstraintValidators.validateMinValue;

/**
 * Validator for the {@link io.rxmicro.validation.constraint.MinSize} constraint.
 *
 * @author nedis
 * @see io.rxmicro.validation.constraint.MinSize
 * @since 0.1
 */
public class MinSizeListConstraintValidator extends AbstractContainerConstraintValidator<List<?>>
        implements ConstraintValidator<List<?>> {

    private final int minValue;

    private final boolean inclusive;

    /**
     * Creates the default instance of {@link MinSizeListConstraintValidator} with the specified parameters.
     *
     * @param minValue the supported min value.
     * @param inclusive whether the specified minimum is inclusive or exclusive.
     */
    public MinSizeListConstraintValidator(final int minValue,
                                          final boolean inclusive) {

        this.minValue = minValue;
        this.inclusive = inclusive;
    }

    @Override
    public void validateNonNull(final List<?> values,
                                final HttpModelType httpModelType,
                                final String modelName) {
        final int actual = values.size();
        validateMinValue(
                minValue, inclusive, actual, httpModelType, modelName,
                "Invalid ? \"?\": Expected that array length >= ?, but actual is ?. (array: " + values + ")!",
                "Invalid ? \"?\": Expected that array length > ?, but actual is ?. (array: " + values + ")!"
        );
    }
}
