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
import io.rxmicro.validation.constraint.Nullable;
import io.rxmicro.validation.constraint.NullableArrayItem;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @see Nullable
 * @see NullableArrayItem
 * @since 0.1
 */
public final class RequiredConstraintValidator<T> implements ConstraintValidator<T> {

    @Override
    public void validate(final T actual,
                         final HttpModelType httpModelType,
                         final String modelName) throws ValidationException {
        if (actual == null) {
            throw new ValidationException("? \"?\" is required!", httpModelType, modelName);
        }
    }
}
