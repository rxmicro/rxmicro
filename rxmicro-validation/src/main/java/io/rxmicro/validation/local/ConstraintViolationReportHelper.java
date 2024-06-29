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

import io.rxmicro.common.RxMicroException;
import io.rxmicro.reflection.Reflections;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.common.util.Requires.require;
import static io.rxmicro.common.util.Strings.capitalize;

/**
 * @author nedis
 * @since 0.12
 */
public final class ConstraintViolationReportHelper {

    private static ConstraintViolationReporter currentConstraintViolationReporter = new ThrowExceptionConstraintViolationReporter();

    public static void setCurrentConstraintViolationReporter(final ConstraintViolationReporter currentConstraintViolationReporter) {
        ConstraintViolationReportHelper.currentConstraintViolationReporter = require(currentConstraintViolationReporter);
    }

    public static void reportViolation(final String message,
                                       final Object... args) {
        final String finalMessage = capitalize(args == null || args.length == 0 ? message : format(message, args));
        currentConstraintViolationReporter.reportViolation(finalMessage);
    }

    public static void startValidation(final ValidationOptions options) {
        if (options.getTranslateConstraintViolationExceptionTo() != null) {
            ExceptionTranslationHelper.translateConstraintViolationExceptionTo(options.getTranslateConstraintViolationExceptionTo());
        }
        currentConstraintViolationReporter.onValidationStarted();
    }

    public static void startValidation() {
        startValidation(new ValidationOptions());
    }

    public static void completeValidation() {
        ExceptionTranslationHelper.EXCEPTION_CLASS_NAME_FOR_TRANSLATION.remove();
        currentConstraintViolationReporter.onValidationCompleted();
    }

    private ConstraintViolationReportHelper() {
    }

    /**
     * @author nedis
     * @since 0.12
     */
    static final class ExceptionTranslationHelper {

        static final Map<String, Class<? extends RxMicroException>> CACHE = new HashMap<>();

        static final ThreadLocal<Class<? extends RxMicroException>> EXCEPTION_CLASS_NAME_FOR_TRANSLATION = new ThreadLocal<>();

        @SuppressWarnings("unchecked")
        static void translateConstraintViolationExceptionTo(final String className) {
            EXCEPTION_CLASS_NAME_FOR_TRANSLATION.set(
                    CACHE.computeIfAbsent(className, cl -> (Class<? extends RxMicroException>) Reflections.classForName(cl))
            );
        }

        static Optional<RxMicroException> newCustomException(final String finalMessage) {
            return Optional.ofNullable(EXCEPTION_CLASS_NAME_FOR_TRANSLATION.get())
                    .map(classInstance -> Reflections.instantiate(classInstance, false, finalMessage));
        }

        private ExceptionTranslationHelper() {
        }
    }
}
