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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Validator for the {@link io.rxmicro.validation.constraint.SubEnum} constraint.
 *
 * @author nedis
 * @param <T> the type to validate
 * @see io.rxmicro.validation.constraint.SubEnum
 * @since 0.1
 */
public class SubEnumConstraintValidator<T extends Enum<T>> implements ConstraintValidator<T> {

    private final Set<String> allowed;

    /**
     * Creates the default instance of {@link SubEnumConstraintValidator} with the specified parameters.
     *
     * @param enumClass enum class
     * @param include include enum constants
     * @param exclude exclude enum constants
     */
    public SubEnumConstraintValidator(final Class<T> enumClass,
                                      final List<String> include,
                                      final List<String> exclude) {
        allowed = new HashSet<>();
        if (!include.isEmpty()) {
            for (final Enum<T> en : enumClass.getEnumConstants()) {
                if (include.contains(en.name())) {
                    allowed.add(en.name());
                }
            }
        }
        if (!exclude.isEmpty()) {
            for (final Enum<T> en : enumClass.getEnumConstants()) {
                if (!exclude.contains(en.name())) {
                    allowed.add(en.name());
                }
            }
        }
    }

    @Override
    public void validate(final T actual,
                         final HttpModelType httpModelType,
                         final String modelName) {
        if (actual != null && !allowed.contains(actual.name())) {
            throw new ValidationException(
                    "Invalid ? \"?\": Expected a value from the set ?, but actual is '?'!",
                    httpModelType, modelName, allowed, actual
            );
        }
    }
}
