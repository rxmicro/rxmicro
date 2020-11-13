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

package io.rxmicro.validation.detail;

import io.rxmicro.http.error.ValidationException;
import io.rxmicro.validation.ConstraintValidator;
import io.rxmicro.validation.UnexpectedResponseException;

import java.util.List;

/**
 * Used by generated code that created by the {@code RxMicro Annotation Processor}.
 *
 * @author nedis
 * @hidden
 * @since 0.1
 */
public final class ResponseValidators {

    public static <T> void validateResponse(final boolean shouldValidationBeActivated,
                                            final ConstraintValidator<T> validator,
                                            final T response) {
        if (shouldValidationBeActivated) {
            try {
                validator.validate(response);
            } catch (final ValidationException ex) {
                throw new UnexpectedResponseException("Response is invalid: ?", ex.getMessage());
            }
        }
    }

    public static <T> void validateResponse(final boolean shouldValidationBeActivated,
                                            final ConstraintValidator<T> validator,
                                            final List<T> response) {
        if (shouldValidationBeActivated) {
            try {
                validator.validateIterable(response);
            } catch (final ValidationException ex) {
                throw new UnexpectedResponseException("Response is invalid: ?", ex.getMessage());
            }
        }
    }

    public static <T> void validateIfResponseExists(final boolean shouldValidationBeActivated,
                                                    final ConstraintValidator<T> validator,
                                                    final T response) {
        if (response != null) {
            validateResponse(shouldValidationBeActivated, validator, response);
        }
    }

    public static <T> void validateIfResponseExists(final boolean shouldValidationBeActivated,
                                                    final ConstraintValidator<T> validator,
                                                    final List<T> response) {
        if (response != null) {
            validateResponse(shouldValidationBeActivated, validator, response);
        }
    }

    private ResponseValidators() {
    }
}
