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

import io.rxmicro.http.error.ValidationException;
import io.rxmicro.rest.model.HttpModelType;
import io.rxmicro.validation.ConstraintValidator;

/**
 * Validator for the {@link io.rxmicro.validation.constraint.EndsWith} constraint.
 *
 * @author nedis
 * @see io.rxmicro.validation.constraint.EndsWith
 * @since 0.9
 */
public final class EndsWithConstraintValidator implements ConstraintValidator<String> {

    private final String suffix;

    /**
     * Creates the default instance of {@link EndsWithConstraintValidator} type.
     *
     * @param suffix the required suffix for the field value.
     */
    public EndsWithConstraintValidator(final String suffix) {
        this.suffix = suffix;
    }

    @Override
    public void validateNonNull(final String actual,
                                final HttpModelType httpModelType,
                                final String modelName) {
        if (!actual.endsWith(suffix)) {
            throw new ValidationException(
                    "Invalid ? \"?\": Expected that value ends with '?' suffix, but actual is '?'!",
                    httpModelType, modelName, suffix, actual
            );
        }
    }
}
