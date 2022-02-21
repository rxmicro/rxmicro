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

package io.rxmicro.validation.internal;

import io.rxmicro.http.error.ValidationException;
import io.rxmicro.rest.model.HttpModelType;

/**
 * @author nedis
 * @since 0.7
 */
public final class ConstraintValidators {

    public static <T extends Comparable<T>> void validateMaxValue(final T maxValue,
                                                                  final boolean inclusive,
                                                                  final T actual,
                                                                  final HttpModelType httpModelType,
                                                                  final String modelName,
                                                                  final String inclusiveErrorMessageTemplate,
                                                                  final String exclusiveErrorMessageTemplate) {
        if (inclusive) {
            if (actual.compareTo(maxValue) > 0) {
                throw new ValidationException(
                        inclusiveErrorMessageTemplate,
                        httpModelType, modelName, maxValue, actual
                );
            }
        } else {
            if (actual.compareTo(maxValue) >= 0) {
                throw new ValidationException(
                        exclusiveErrorMessageTemplate,
                        httpModelType, modelName, maxValue, actual
                );
            }
        }
    }

    public static <T extends Comparable<T>> void validateMinValue(final T minValue,
                                                                  final boolean inclusive,
                                                                  final T actual,
                                                                  final HttpModelType httpModelType,
                                                                  final String modelName,
                                                                  final String inclusiveErrorMessageTemplate,
                                                                  final String exclusiveErrorMessageTemplate) {
        if (inclusive) {
            if (actual.compareTo(minValue) < 0) {
                throw new ValidationException(
                        inclusiveErrorMessageTemplate,
                        httpModelType, modelName, minValue, actual
                );
            }
        } else {
            if (actual.compareTo(minValue) <= 0) {
                throw new ValidationException(
                        exclusiveErrorMessageTemplate,
                        httpModelType, modelName, minValue, actual
                );
            }
        }
    }

    private ConstraintValidators() {
    }
}
