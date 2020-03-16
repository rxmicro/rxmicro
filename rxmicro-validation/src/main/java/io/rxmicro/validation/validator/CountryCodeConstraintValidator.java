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

import io.rxmicro.common.RxMicroException;
import io.rxmicro.http.error.ValidationException;
import io.rxmicro.rest.model.HttpModelType;
import io.rxmicro.validation.ConstraintValidator;
import io.rxmicro.validation.base.AbstractCompositionConstraintValidator;
import io.rxmicro.validation.constraint.CountryCode;
import io.rxmicro.validation.detail.ValidatorPool;

import java.util.List;
import java.util.Map;

import static io.rxmicro.validation.detail.ValidatorPool.getStatelessValidator;
import static java.util.Optional.ofNullable;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class CountryCodeConstraintValidator implements ConstraintValidator<String> {

    private static final Map<CountryCode.Format, Class<? extends ConstraintValidator<String>>> map = Map.of(
            CountryCode.Format.ISO_3166_1_alpha2, ISO_3166_1_alpha2ConstraintValidator.class,
            CountryCode.Format.ISO_3166_1_alpha3, ISO_3166_1_alpha3ConstraintValidator.class,
            CountryCode.Format.ISO_3166_1_numeric, ISO_3166_1_numericConstraintValidator.class
    );

    private final ConstraintValidator<String> constraintValidator;

    public CountryCodeConstraintValidator(final CountryCode.Format format) {
        this.constraintValidator = ofNullable(map.get(format))
                .map(ValidatorPool::getStatelessValidator)
                .orElseThrow(() -> {
                    throw new RxMicroException("Unsupported format: " + format);
                });
    }

    @Override
    public void validate(final String actual,
                         final HttpModelType httpModelType,
                         final String modelName) throws ValidationException {
        constraintValidator.validate(actual, httpModelType, modelName);
    }

    /**
     * @author nedis
     * @link https://rxmicro.io
     * @since 0.1
     */
    public static final class ISO_3166_1_alpha2ConstraintValidator extends AbstractCompositionConstraintValidator<String> {

        public ISO_3166_1_alpha2ConstraintValidator() {
            super(List.of(
                    new LengthConstraintValidator(2),
                    getStatelessValidator(UppercaseConstraintValidator.class)
            ));
        }
    }

    /**
     * @author nedis
     * @link https://rxmicro.io
     * @since 0.1
     */
    public static final class ISO_3166_1_alpha3ConstraintValidator extends AbstractCompositionConstraintValidator<String> {

        public ISO_3166_1_alpha3ConstraintValidator() {
            super(List.of(
                    new LengthConstraintValidator(3),
                    getStatelessValidator(UppercaseConstraintValidator.class)
            ));
        }
    }

    /**
     * @author nedis
     * @link https://rxmicro.io
     * @since 0.1
     */
    public static final class ISO_3166_1_numericConstraintValidator extends AbstractCompositionConstraintValidator<String> {

        public ISO_3166_1_numericConstraintValidator() {
            super(List.of(
                    new LengthConstraintValidator(3),
                    getStatelessValidator(DigitsOnlyConstraintValidator.class)
            ));
        }
    }
}
