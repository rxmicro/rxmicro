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
import io.rxmicro.validation.base.AbstractMinConstraintValidator;
import io.rxmicro.validation.constraint.Min;

import java.math.BigInteger;

/**
 * Validator for the {@link Min} constraint.
 *
 * @author nedis
 * @see Min
 * @since 0.1
 */
public class MinBigIntegerConstraintValidator extends AbstractMinConstraintValidator<BigInteger>
        implements ConstraintValidator<BigInteger> {

    /**
     * Creates the default instance of {@link MinBigIntegerConstraintValidator} with the specified parameters.
     *
     * @param minValue  the supported min value.
     * @param inclusive whether the specified minimum is inclusive or exclusive.
     */
    public MinBigIntegerConstraintValidator(final String minValue,
                                            final boolean inclusive) {
        super(new BigInteger(minValue), inclusive);
    }
}
