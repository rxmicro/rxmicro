/*
 * Copyright (c) 2020. http://rxmicro.io
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
 * @link http://rxmicro.io
 * @since 0.1
 */
public interface ConstraintValidator<T> {

    /**
     * Validates a single actual.
     * The state of {@code actual} must not be altered.
     *
     * @param actual        actual value to validate
     * @param httpModelType http model type
     * @param modelName     parameter or header name
     * @throws ValidationException if actual does not pass the constraint
     */
    void validate(T actual,
                  HttpModelType httpModelType,
                  String modelName) throws ValidationException;

    /**
     * Validates a root model.
     * The state of {@code value} must not be altered.
     *
     * @param model object to validate
     * @throws ValidationException if value does not pass the constraint
     */
    default void validate(final T model) throws ValidationException {
        validate(model, null, null);
    }

    /**
     * Validates a value list.
     * The state of {@code list} must not be altered.
     *
     * @param list          list to validate
     * @param httpModelType http model type
     * @param modelName     parameter or header name
     * @throws ValidationException if value does not pass the constraint
     */
    default void validateList(final List<T> list,
                              final HttpModelType httpModelType,
                              final String modelName) throws ValidationException {
        if (list != null) {
            for (final T actual : list) {
                validate(actual, httpModelType, modelName);
            }
        }
    }

    /**
     * Validates a root model list.
     * The state of {@code value} must not be altered.
     *
     * @param models object list to validate
     * @throws ValidationException if value does not pass the constraint
     */
    default void validateList(final List<T> models) throws ValidationException {
        validateList(models, null, null);
    }
}
