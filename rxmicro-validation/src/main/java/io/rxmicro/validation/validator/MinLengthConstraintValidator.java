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
import io.rxmicro.validation.base.AbstractMinConstraintValidator;

/**
 * Validator for the {@link io.rxmicro.validation.constraint.MinLength} constraint.
 *
 * @author nedis
 * @see io.rxmicro.validation.constraint.MinLength
 * @since 0.1
 */
public class MinLengthConstraintValidator extends AbstractMinConstraintValidator<Integer>
        implements ConstraintValidator<String> {

    /**
     * Creates the default instance of {@link MinLengthConstraintValidator} with the specified parameters.
     *
     * @param minValue the supported min value.
     * @param inclusive whether the specified minimum is inclusive or exclusive.
     */
    public MinLengthConstraintValidator(final int minValue,
                                        final boolean inclusive) {
        super(minValue, inclusive);
    }

    @Override
    public void validate(final String value,
                         final HttpModelType httpModelType,
                         final String modelName) {
        if (value != null) {
            final int actual = value.length();
            validate(actual, httpModelType, modelName,
                    "Invalid ? \"?\": Expected that 'string length' > ?, where 'string length' is '?'!",
                    "Invalid ? \"?\": Expected that 'string length' >= ?, where 'string length' is '?'!");
        }
    }
}
