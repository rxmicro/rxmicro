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

import java.math.BigDecimal;

/**
 * Validator for the {@link io.rxmicro.validation.constraint.MinNumber} constraint.
 *
 * @author nedis
 * @see io.rxmicro.validation.constraint.MinNumber
 * @since 0.1
 */
public class MinBigDecimalNumberConstraintValidator extends AbstractMinConstraintValidator<BigDecimal>
        implements ConstraintValidator<BigDecimal> {

    /**
     * Creates the default instance of {@link MinBigDecimalNumberConstraintValidator} with the specified parameters.
     *
     * @param minValue the supported min value.
     * @param inclusive whether the specified minimum is inclusive or exclusive.
     */
    public MinBigDecimalNumberConstraintValidator(final String minValue,
                                                  final boolean inclusive) {
        super(new BigDecimal(minValue), inclusive);
    }
}
