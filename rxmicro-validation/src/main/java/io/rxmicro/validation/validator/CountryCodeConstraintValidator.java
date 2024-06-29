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

import io.rxmicro.common.ImpossibleException;
import io.rxmicro.model.ModelType;
import io.rxmicro.validation.ConstraintValidator;
import io.rxmicro.validation.base.AbstractCompositionConstraintValidator;
import io.rxmicro.validation.constraint.CountryCode;

import java.util.List;
import java.util.Map;

import static io.rxmicro.validation.detail.StatelessValidators.getStatelessValidator;
import static java.util.Optional.ofNullable;

/**
 * Validator for the {@link CountryCode} constraint.
 *
 * @author nedis
 * @see CountryCode
 * @since 0.1
 */
public final class CountryCodeConstraintValidator implements ConstraintValidator<String> {

    private static final Map<CountryCode.Format, ConstraintValidator<String>> CLASS_MAP = Map.of(
            CountryCode.Format.ISO_3166_1_ALPHA_2, new Alpha2ConstraintValidator(),
            CountryCode.Format.ISO_3166_1_ALPHA_3, new Alpha3ConstraintValidator(),
            CountryCode.Format.ISO_3166_1_NUMERIC, new NumericConstraintValidator()
    );

    private final ConstraintValidator<String> constraintValidator;

    /**
     * Creates the default instance of {@link CountryCodeConstraintValidator} with the specified country code format.
     *
     * @param format the specified country code format
     * @throws ImpossibleException if country code format is unsupported
     */
    public CountryCodeConstraintValidator(final CountryCode.Format format) {
        this.constraintValidator = ofNullable(CLASS_MAP.get(format))
                .orElseThrow(() -> new ImpossibleException("Unsupported format: ?", format));
    }

    @Override
    public void validateNonNull(final String actual,
                                final ModelType modelType,
                                final String modelName) {
        constraintValidator.validate(actual, modelType, modelName);
    }

    /**
     * Validator for the {@link CountryCode.Format#ISO_3166_1_ALPHA_2} constraint.
     *
     * @author nedis
     * @since 0.1
     */
    private static final class Alpha2ConstraintValidator extends AbstractCompositionConstraintValidator<String> {

        private Alpha2ConstraintValidator() {
            super(List.of(
                    new LengthConstraintValidator(2),
                    getStatelessValidator(UppercaseConstraintValidator.class)
            ));
        }
    }

    /**
     * Validator for the {@link CountryCode.Format#ISO_3166_1_ALPHA_3} constraint.
     *
     * @author nedis
     * @since 0.1
     */
    private static final class Alpha3ConstraintValidator extends AbstractCompositionConstraintValidator<String> {

        private Alpha3ConstraintValidator() {
            super(List.of(
                    new LengthConstraintValidator(3),
                    getStatelessValidator(UppercaseConstraintValidator.class)
            ));
        }
    }

    /**
     * Validator for the {@link CountryCode.Format#ISO_3166_1_NUMERIC} constraint.
     *
     * @author nedis
     * @since 0.1
     */
    private static final class NumericConstraintValidator extends AbstractCompositionConstraintValidator<String> {

        private NumericConstraintValidator() {
            super(List.of(
                    new LengthConstraintValidator(3),
                    getStatelessValidator(DigitsOnlyConstraintValidator.class)
            ));
        }
    }
}
