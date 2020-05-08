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
import io.rxmicro.rest.model.HttpModelType;
import io.rxmicro.validation.ConstraintValidator;
import io.rxmicro.validation.base.AbstractCompositionConstraintValidator;
import io.rxmicro.validation.constraint.CountryCode;
import io.rxmicro.validation.detail.StatelessValidators;

import java.util.List;
import java.util.Map;

import static io.rxmicro.validation.detail.StatelessValidators.getStatelessValidator;
import static java.util.Optional.ofNullable;

/**
 * Validator for the {@link CountryCode} constraint
 *
 * @author nedis
 * @since 0.1
 * @see CountryCode
 */
public final class CountryCodeConstraintValidator implements ConstraintValidator<String> {

    private static final Map<CountryCode.Format, Class<? extends ConstraintValidator<String>>> CLASS_MAP = Map.of(
            CountryCode.Format.ISO_3166_1_ALPHA_2, Alpha2ConstraintValidator.class,
            CountryCode.Format.ISO_3166_1_ALPHA_3, Alpha3ConstraintValidator.class,
            CountryCode.Format.ISO_3166_1_NUMERIC, NumericConstraintValidator.class
    );

    private final ConstraintValidator<String> constraintValidator;

    public CountryCodeConstraintValidator(final CountryCode.Format format) {
        this.constraintValidator = ofNullable(CLASS_MAP.get(format))
                .map(StatelessValidators::getStatelessValidator)
                .orElseThrow(() -> {
                    throw new ImpossibleException("Unsupported format: ?", format);
                });
    }

    @Override
    public void validate(final String actual,
                         final HttpModelType httpModelType,
                         final String modelName) {
        constraintValidator.validate(actual, httpModelType, modelName);
    }

    /**
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
