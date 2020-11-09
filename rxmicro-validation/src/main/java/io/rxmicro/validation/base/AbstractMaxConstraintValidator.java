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

package io.rxmicro.validation.base;

import io.rxmicro.http.error.ValidationException;
import io.rxmicro.rest.model.HttpModelType;

import static io.rxmicro.validation.internal.ConstraintValidators.validateMaxValue;

/**
 * Base validator class for maximum constraints.
 *
 * @author nedis
 * @param <T> the type to validate
 * @since 0.1
 */
public abstract class AbstractMaxConstraintValidator<T extends Comparable<T>> {

    protected final T maxValue;

    protected final boolean inclusive;

    /**
     * Creates an instance of the base validator class for maximum constraint.
     *
     * @param maxValue the maximum supported value
     * @param inclusive specifies whether the specified min is inclusive or not.
     */
    protected AbstractMaxConstraintValidator(final T maxValue,
                                             final boolean inclusive) {
        this.maxValue = maxValue;
        this.inclusive = inclusive;
    }

    /**
     * Validates the single actual.
     *
     * <p>
     * The state of the {@code actual} must not be altered.
     *
     * @param actual        the actual value to validate
     * @param httpModelType the http model type
     * @param modelName     the parameter or header name
     * @throws ValidationException if actual does not pass the constraint
     */
    public final void validate(final T actual,
                               final HttpModelType httpModelType,
                               final String modelName) {
        validateMaxValue(
                maxValue, inclusive, actual, httpModelType, modelName,
                "Invalid ? \"?\": Expected that value <= ?, but actual is ?!",
                "Invalid ? \"?\": Expected that value < ?, but actual is ?!"
        );
    }
}
