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
import io.rxmicro.validation.base.AbstractContainerConstraintValidator;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static io.rxmicro.validation.ConstraintViolations.reportViolation;

/**
 * Validator for the {@link io.rxmicro.validation.constraint.UniqueItems} constraint.
 *
 * @author nedis
 * @see io.rxmicro.validation.constraint.UniqueItems
 * @since 0.1
 */
public class UniqueItemsListConstraintValidator extends AbstractContainerConstraintValidator<List<?>> {

    @Override
    public void validateNonNull(final List<?> actual,
                                final ModelType modelType,
                                final String modelName) {
        final Set<?> set = new HashSet<>(actual);
        if (set.size() != actual.size()) {
            reportViolation(
                    "Invalid ? \"?\": Expected unique array items, but actual is ?!",
                    modelType, modelName, actual
            );
        }
    }
}
