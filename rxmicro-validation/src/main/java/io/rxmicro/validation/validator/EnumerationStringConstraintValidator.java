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

import java.util.Collection;
import java.util.Set;

/**
 * Validator for the {@link io.rxmicro.validation.constraint.Enumeration} constraint
 *
 * @author nedis
 * @link https://rxmicro.io
 * @see io.rxmicro.validation.constraint.Enumeration
 * @since 0.1
 */
public class EnumerationStringConstraintValidator implements ConstraintValidator<String> {

    private final Set<String> allowed;

    public EnumerationStringConstraintValidator(final Collection<String> allowed) {
        this.allowed = Set.copyOf(allowed);
    }

    @Override
    public void validate(final String actual,
                         final HttpModelType httpModelType,
                         final String modelName) {
        if (actual != null && !allowed.contains(actual)) {
            throw new ValidationException(
                    "Invalid ? \"?\": Expected a value from the set ?, but actual is '?'!",
                    httpModelType, modelName, allowed, actual
            );
        }
    }
}
