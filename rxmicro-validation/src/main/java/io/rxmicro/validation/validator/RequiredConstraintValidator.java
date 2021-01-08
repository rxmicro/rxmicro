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

import java.util.Map;

/**
 * Validator for the required constraint.
 *
 * @author nedis
 * @see io.rxmicro.validation.constraint.Nullable
 * @see io.rxmicro.validation.constraint.NullableArrayItem
 * @since 0.1
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class RequiredConstraintValidator implements ConstraintValidator<Object> {

    @Override
    public void validate(final Object actual,
                         final HttpModelType httpModelType,
                         final String modelName) {
        if (actual == null) {
            throw new ValidationException("? \"?\" is required!", httpModelType, modelName);
        }
    }

    // These methods fix the following compilation error:
    // incompatible types: java.util.List<T> cannot be converted to java.lang.Iterable<java.lang.Object>
    // incompatible types: java.util.Map<String, T> cannot be converted to java.util.Map<String, java.lang.Object>
    @Override
    public void validateIterable(final Iterable iterable,
                                 final HttpModelType httpModelType,
                                 final String modelName) {
        if (iterable != null) {
            for (final Object actual : iterable) {
                validate(actual, httpModelType, modelName);
            }
        }
    }

    @Override
    public void validateIterable(final Iterable iterable) {
        validateIterable(iterable, null, null);
    }

    @Override
    public void validateMapValues(final Map map,
                                  final HttpModelType httpModelType,
                                  final String modelName) {
        if (map != null) {
            validateIterable((Iterable<Object>) map.values(), httpModelType, modelName);
        }
    }

    @Override
    public void validateMapValues(final Map map) {
        validateMapValues(map, null, null);
    }
}
