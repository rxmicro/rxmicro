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

package io.rxmicro.validation.local;

import io.rxmicro.model.ModelType;
import io.rxmicro.validation.ConstraintValidator;
import io.rxmicro.validation.internal.reporter.AggregateViolationsConstraintViolationReporter;
import io.rxmicro.validation.internal.reporter.ConstraintViolationReportHelper;
import io.rxmicro.validation.internal.reporter.ConstraintViolationReporter;
import io.rxmicro.validation.internal.reporter.ThrowExceptionConstraintViolationReporter;
import io.rxmicro.validation.internal.runtime.ValidatorDescriptor;
import io.rxmicro.validation.internal.runtime.ValidatorDescriptorFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/**
 * @author nedis
 * @since 0.12
 */
public final class RuntimeValidators {

    private static final Map<String, ValidatorDescriptor> DESCRIPTORS = new ConcurrentHashMap<>();

    public static <T> T collectAllViolationsAndTranslateIntoConfigException(final ValidationOptions options,
                                                                            final Supplier<T> supplier) {
        try {
            final ConstraintViolationReporter currentConstraintViolationReporter = new AggregateViolationsConstraintViolationReporter();
            ConstraintViolationReportHelper.setCurrentConstraintViolationReporter(currentConstraintViolationReporter);
            ConstraintViolationReportHelper.startValidation(options);
            return supplier.get();
        } finally {
            try {
                ConstraintViolationReportHelper.completeValidation();
            } finally {
                // Reset to default. This reporter will be used by rxmicro framework for validation of requests and responses
                ConstraintViolationReportHelper.setCurrentConstraintViolationReporter(new ThrowExceptionConstraintViolationReporter());
            }
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static void validateProperty(final Object instance,
                                        final ModelType modelType,
                                        final String propertyName,
                                        final String fullPropertyName,
                                        final Object value) {
        final ValidatorDescriptor descriptor =
                DESCRIPTORS.computeIfAbsent(fullPropertyName, k -> ValidatorDescriptorFactory.create(instance, propertyName));

        for (final ConstraintValidator validator : descriptor.getValidators()) {
            validator.validate(value, modelType, fullPropertyName);
        }
    }

    private RuntimeValidators() {
    }
}
