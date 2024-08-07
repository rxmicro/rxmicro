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

import io.rxmicro.model.ModelType;
import io.rxmicro.validation.ConstraintValidator;

import java.util.Collection;
import java.util.Set;

import static io.rxmicro.common.util.ExCollections.unmodifiableOrderedSet;
import static io.rxmicro.validation.ConstraintViolations.reportViolation;

/**
 * Validator for the {@link io.rxmicro.validation.constraint.Enumeration} constraint.
 *
 * @author nedis
 * @see io.rxmicro.validation.constraint.Enumeration
 * @since 0.1
 */
public class EnumerationStringConstraintValidator implements ConstraintValidator<String> {

    private final Set<String> allowed;

    /**
     * Creates the default instance of {@link EnumerationCharacterConstraintValidator} with the the allowed enum strings.
     *
     * @param allowed the allowed enum strings
     */
    public EnumerationStringConstraintValidator(final Collection<String> allowed) {
        this.allowed = unmodifiableOrderedSet(allowed);
    }

    @Override
    public void validateNonNull(final String actual,
                                final ModelType modelType,
                                final String modelName) {
        if (!allowed.contains(actual)) {
            reportViolation(
                    "Invalid ? \"?\": Expected a value from the set ?, but actual is '?'!",
                    modelType, modelName, allowed, actual
            );
        }
    }
}
