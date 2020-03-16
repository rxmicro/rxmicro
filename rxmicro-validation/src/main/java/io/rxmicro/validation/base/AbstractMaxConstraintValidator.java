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

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public abstract class AbstractMaxConstraintValidator<T extends Comparable<T>> {

    private final T maxValue;

    private final boolean inclusive;

    protected AbstractMaxConstraintValidator(final T maxValue,
                                             final boolean inclusive) {
        this.maxValue = maxValue;
        this.inclusive = inclusive;
    }

    public final void validate(final T actual,
                               final HttpModelType httpModelType,
                               final String modelName) {
        validate(actual, httpModelType, modelName,
                "Invalid ? \"?\": Expected that 'value' <= ?, where 'value' is '?'!",
                "Invalid ? \"?\": Expected that 'value' < ?, where 'value' is '?'!");
    }

    protected final void validate(final T value,
                                  final HttpModelType httpModelType,
                                  final String name,
                                  final String inclusiveErrorMessageTemplate,
                                  final String exclusiveErrorMessageTemplate) {
        if (value != null) {
            if (inclusive) {
                if (value.compareTo(maxValue) > 0) {
                    throw new ValidationException(
                            inclusiveErrorMessageTemplate,
                            httpModelType, name, maxValue, value);
                }
            } else {
                if (value.compareTo(maxValue) >= 0) {
                    throw new ValidationException(
                            exclusiveErrorMessageTemplate,
                            httpModelType, name, maxValue, value);
                }
            }
        }
    }
}
