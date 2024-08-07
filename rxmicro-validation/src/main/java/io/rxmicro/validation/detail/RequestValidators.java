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

import static io.rxmicro.validation.internal.reporter.ConstraintViolationReportHelper.completeValidation;
import static io.rxmicro.validation.internal.reporter.ConstraintViolationReportHelper.startValidation;


/**
 * Used by generated code that created by the {@code RxMicro Annotation Processor}.
 *
 * @author nedis
 * @hidden
 * @since 0.1
 */
public final class RequestValidators {

    private static final String VALIDATION_EXCEPTION_CLASS_NAME = "io.rxmicro.http.error.ValidationException";

    private static final ValidationOptions VALIDATION_OPTIONS = ValidationOptions.builder()
            .setTranslateConstraintViolationExceptionTo(VALIDATION_EXCEPTION_CLASS_NAME)
            .build();

    private RequestValidators() {
    }

    public static <T> void validateRequest(final boolean shouldValidationBeActivated,
                                           final ConstraintValidator<T> validator,
                                           final T request) {
        if (shouldValidationBeActivated) {
            startValidation(VALIDATION_OPTIONS);
            try {
                validator.validate(request);
            } finally {
                completeValidation();
            }
        }
    }
}
