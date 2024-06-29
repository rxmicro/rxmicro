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

import io.rxmicro.validation.base.AbstractCompositionConstraintValidator;
import io.rxmicro.validation.base.LocationAccuracy;
import io.rxmicro.validation.constraint.Lat;

import java.math.BigDecimal;
import java.util.List;

import static io.rxmicro.validation.constraint.Numeric.ValidationType.EXACT;

/**
 * Validator for the {@link io.rxmicro.validation.constraint.Lat} constraint.
 *
 * @author nedis
 * @see io.rxmicro.validation.constraint.Lat
 * @since 0.1
 */
public final class LatConstraintValidator extends AbstractCompositionConstraintValidator<BigDecimal> {

    /**
     * Creates the default instance of {@link LatConstraintValidator} with the specified location accuracy.
     *
     * @param locationAccuracy the specified location accuracy
     */
    public LatConstraintValidator(final LocationAccuracy locationAccuracy) {
        super(List.of(
                new MinBigDecimalConstraintValidator(Lat.MIN_LAT_VALUE.toPlainString(), true),
                new MaxBigDecimalConstraintValidator(Lat.MAX_LAT_VALUE.toPlainString(), true),
                new NumericConstraintValidator(-1, locationAccuracy.getCoordinateScale(), EXACT)
        ));
    }
}
