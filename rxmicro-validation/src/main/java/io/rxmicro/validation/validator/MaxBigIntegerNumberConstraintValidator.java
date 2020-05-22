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

import java.math.BigInteger;

/**
 * Validator for the {@link io.rxmicro.validation.constraint.MaxNumber} constraint.
 *
 * @author nedis
 * @see io.rxmicro.validation.constraint.MaxNumber
 * @since 0.1
 */
public class MaxBigIntegerNumberConstraintValidator extends AbstractMaxConstraintValidator<BigInteger>
        implements ConstraintValidator<BigInteger> {

    /**
     * Creates the default instance of {@link MaxBigIntegerNumberConstraintValidator} with the specified parameters.
     *
     * @param maxValue the supported max value.
     * @param inclusive whether the specified minimum is inclusive or exclusive.
     */
    public MaxBigIntegerNumberConstraintValidator(final String maxValue,
                                                  final boolean inclusive) {
        super(new BigInteger(maxValue), inclusive);
    }
}
