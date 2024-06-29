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

import io.rxmicro.validation.ConstraintValidator;
import io.rxmicro.validation.local.ValidationOptions;

import java.util.List;

import static io.rxmicro.validation.local.ConstraintViolationReportManager.completeValidation;
import static io.rxmicro.validation.local.ConstraintViolationReportManager.startValidation;

/**
 * Used by generated code that created by the {@code RxMicro Annotation Processor}.
 *
 * @author nedis
 * @hidden
 * @since 0.1
 */
public final class ResponseValidators {

    private static final String UNEXPECTED_RESPONSE_EXCEPTION_CLASS_NAME = "io.rxmicro.http.error.UnexpectedResponseException";

    private static final ValidationOptions VALIDATION_OPTIONS = new ValidationOptions()
            .setTranslateConstraintViolationExceptionTo(UNEXPECTED_RESPONSE_EXCEPTION_CLASS_NAME);

    private ResponseValidators() {
    }

    public static <T> void validateResponse(final boolean shouldValidationBeActivated,
                                            final ConstraintValidator<T> validator,
                                            final T response) {
        if (shouldValidationBeActivated) {
            validateResponse(validator, response);
        }
    }

    public static <T> void validateResponse(final ConstraintValidator<T> validator,
                                            final T response) {
        startValidation(VALIDATION_OPTIONS);
        try {
            validator.validate(response);
        } finally {
            completeValidation();
        }
    }

    public static <T> void validateResponse(final boolean shouldValidationBeActivated,
                                            final ConstraintValidator<T> validator,
                                            final List<T> response) {
        if (shouldValidationBeActivated) {
            validateResponse(validator, response);
        }
    }

    public static <T> void validateResponse(final ConstraintValidator<T> validator,
                                            final List<T> response) {
        startValidation(VALIDATION_OPTIONS);
        try {
            validator.validateIterable(response);
        } finally {
            completeValidation();
        }
    }

    public static <T> void validateIfResponseExists(final ConstraintValidator<T> validator,
                                                    final T response) {
        if (response != null) {
            validateResponse(validator, response);
        }
    }

    public static <T> void validateIfResponseExists(final ConstraintValidator<T> validator,
                                                    final List<T> response) {
        if (response != null) {
            validateResponse(validator, response);
        }
    }
}
