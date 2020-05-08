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

package io.rxmicro.validation;

import io.rxmicro.http.error.ValidationException;
import io.rxmicro.rest.model.HttpModelType;

import java.util.List;

/**
 * Defines the logic to validate a given constraint for a given object type.
 *
 * @author nedis
 * @since 0.1
 * @see DisableValidation
 */
public interface ConstraintValidator<T> {

    /**
     * Validates the single actual.<p>
     * The state of the {@code actual} must not be altered.
     *
     * @param actual        the actual value to validate
     * @param httpModelType the http model type
     * @param modelName     the parameter or header name
     * @throws ValidationException if actual does not pass the constraint
     */
    void validate(T actual,
                  HttpModelType httpModelType,
                  String modelName);

    /**
     * Validates the root model.<p>
     * The state of the {@code model} must not be altered.
     *
     * @param model the root model to validate
     * @throws ValidationException if value does not pass the constraint
     */
    default void validate(final T model) {
        validate(model, null, null);
    }

    /**
     * Validates the value list.<p>
     * The state of the {@code list} must not be altered.
     *
     * @param list          the list to validate
     * @param httpModelType the http model type
     * @param modelName     the parameter or header name
     * @throws ValidationException if value does not pass the constraint
     */
    default void validateList(final List<T> list,
                              final HttpModelType httpModelType,
                              final String modelName) {
        if (list != null) {
            for (final T actual : list) {
                validate(actual, httpModelType, modelName);
            }
        }
    }

    /**
     * Validates the root model list.<p>
     * The state of the {@code value} must not be altered.
     *
     * @param models the model list to validate
     * @throws ValidationException if value does not pass the constraint
     */
    default void validateList(final List<T> models) {
        validateList(models, null, null);
    }
}
