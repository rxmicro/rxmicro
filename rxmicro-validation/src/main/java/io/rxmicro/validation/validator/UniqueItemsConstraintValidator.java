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
import io.rxmicro.validation.base.AbstractListConstraintValidator;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class UniqueItemsConstraintValidator extends AbstractListConstraintValidator {

    @Override
    public void validate(final List<?> actual,
                         final HttpModelType httpModelType,
                         final String modelName) throws ValidationException {
        if (actual != null) {
            final Set<?> set = new HashSet<>(actual);
            if (set.size() != actual.size()) {
                throw new ValidationException(
                        "Invalid ? \"?\": Expected unique items, but actual is '?'!",
                        httpModelType, modelName, actual);
            }
        }
    }
}
