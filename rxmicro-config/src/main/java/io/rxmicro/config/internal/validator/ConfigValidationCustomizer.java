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

package io.rxmicro.config.internal.validator;

import io.rxmicro.config.ConfigException;
import io.rxmicro.validation.local.ConstraintViolationReportManager;
import io.rxmicro.validation.local.ConstraintViolationReporter;
import io.rxmicro.validation.local.ThrowExceptionConstraintViolationReporter;
import io.rxmicro.validation.local.ValidationOptions;

import java.util.function.Supplier;

/**
 * @author nedis
 * @since 0.12
 */
public final class ConfigValidationCustomizer {

    private static final ValidationOptions VALIDATION_OPTIONS = new ValidationOptions()
            .setTranslateConstraintViolationExceptionTo(ConfigException.class.getName());

    public static <T> T collectAllViolationsAndTranslateIntoConfigException(final Supplier<T> supplier) {
        try {
            final ConstraintViolationReporter currentConstraintViolationReporter
                    = new DefaultConfigConstraintViolationReporter();
            ConstraintViolationReportManager.setCurrentConstraintViolationReporter(currentConstraintViolationReporter);
            ConstraintViolationReportManager.startValidation(VALIDATION_OPTIONS);
            return supplier.get();
        } finally {
            try {
                ConstraintViolationReportManager.completeValidation();
            } finally {
                // Reset to default. This reporter will be used by rxmicro framework for validation of requests and responses
                ConstraintViolationReportManager.setCurrentConstraintViolationReporter(new ThrowExceptionConstraintViolationReporter());
            }
        }
    }

    private ConfigValidationCustomizer() {
    }
}
