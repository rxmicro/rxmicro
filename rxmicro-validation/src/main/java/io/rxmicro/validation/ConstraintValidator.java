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

import io.rxmicro.model.ModelType;

import java.util.Map;

/**
 * Defines the logic to validate a given constraint for a given object type.
 *
 * @param <T> the type to validate
 * @author nedis
 * @see DisableValidation
 * @since 0.1
 */
public interface ConstraintValidator<T> {

    /**
     * Validates the single not null actual.
     *
     * <p>
     * The state of the {@code actual} must not be altered.
     *
     * @param actual    the actual value to validate
     * @param modelType the model type
     * @param modelName the parameter or header name
     * @throws ConstraintViolationException if actual does not pass the constraint
     */
    void validateNonNull(T actual,
                         ModelType modelType,
                         String modelName);

    /**
     * Validates the single actual.
     *
     * <p>
     * The state of the {@code actual} must not be altered.
     *
     * @param actual    the actual value to validate
     * @param modelType the http model type
     * @param modelName the parameter or header name
     * @throws ConstraintViolationException if actual does not pass the constraint
     */
    default void validate(final T actual,
                          final ModelType modelType,
                          final String modelName) {
        if (actual != null) {
            validateNonNull(actual, modelType, modelName);
        }
    }

    /**
     * Validates the root model.
     *
     * <p>
     * The state of the {@code model} must not be altered.
     *
     * @param model the root model to validate
     * @throws ConstraintViolationException if value does not pass the constraint
     */
    default void validate(final T model) {
        validate(model, null, null);
    }

    /**
     * Validates all items from the iterable.
     *
     * <p>
     * The state of the {@code iterable} must not be altered.
     *
     * <p>
     * If the {@code iterable} is {@code null} validator does not throw {@link ConstraintViolationException}.
     *
     * @param iterable  the iterable to validate
     * @param modelType the http model type
     * @param modelName the parameter or header name
     * @throws ConstraintViolationException if value does not pass the constraint
     */
    default void validateIterable(final Iterable<T> iterable,
                                  final ModelType modelType,
                                  final String modelName) {
        if (iterable != null) {
            for (final T actual : iterable) {
                validate(actual, modelType, modelName);
            }
        }
    }

    /**
     * Validates all items from the iterable.
     *
     * <p>
     * The state of the {@code value} must not be altered.
     *
     * <p>
     * If the {@code iterable} is {@code null} validator does not throw {@link ConstraintViolationException}.
     *
     * @param iterable the iterable to validate
     * @throws ConstraintViolationException if value does not pass the constraint
     */
    default void validateIterable(final Iterable<T> iterable) {
        validateIterable(iterable, null, null);
    }

    /**
     * Validates the map values.
     *
     * <p>
     * The state of the {@code map} must not be altered.
     *
     * @param map       the map with values to validate
     * @param modelType the http model type
     * @param modelName the parameter or header name
     * @throws ConstraintViolationException if value does not pass the constraint
     */
    default void validateMapValues(final Map<String, T> map,
                                   final ModelType modelType,
                                   final String modelName) {
        if (map != null) {
            validateIterable(map.values(), modelType, modelName);
        }
    }

    /**
     * Validates the root model map values.
     *
     * <p>
     * The state of the {@code map} must not be altered.
     *
     * @param map the map with values to validate
     * @throws ConstraintViolationException if value does not pass the constraint
     */
    default void validateMapValues(final Map<String, T> map) {
        validateMapValues(map, null, null);
    }
}
