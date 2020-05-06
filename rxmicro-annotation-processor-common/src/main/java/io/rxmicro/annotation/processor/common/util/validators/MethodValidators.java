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

package io.rxmicro.annotation.processor.common.util.validators;

import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;

import javax.lang.model.element.ExecutableElement;

import static javax.lang.model.element.Modifier.ABSTRACT;
import static javax.lang.model.element.Modifier.DEFAULT;
import static javax.lang.model.element.Modifier.NATIVE;
import static javax.lang.model.element.Modifier.PRIVATE;
import static javax.lang.model.element.Modifier.STATIC;
import static javax.lang.model.element.Modifier.SYNCHRONIZED;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class MethodValidators {

    public static void validateNotPrivateMethod(final ExecutableElement method,
                                                final String errorMessage,
                                                final Object... args) {
        if (method.getModifiers().contains(PRIVATE)) {
            throw new InterruptProcessingException(method, errorMessage, args);
        }
    }

    public static void validateNotAbstractMethod(final ExecutableElement method,
                                                 final String errorMessage,
                                                 final Object... args) {
        if (method.getModifiers().contains(ABSTRACT)) {
            throw new InterruptProcessingException(method, errorMessage, args);
        }
    }

    public static void validateStaticMethod(final ExecutableElement method,
                                            final String errorMessage,
                                            final Object... args) {
        if (!method.getModifiers().contains(STATIC)) {
            throw new InterruptProcessingException(method, errorMessage, args);
        }
    }

    public static void validateNotStaticMethod(final ExecutableElement method,
                                               final String errorMessage,
                                               final Object... args) {
        if (method.getModifiers().contains(STATIC)) {
            throw new InterruptProcessingException(method, errorMessage, args);
        }
    }

    public static void validateNotSynchronizedMethod(final ExecutableElement method,
                                                     final String errorMessage,
                                                     final Object... args) {
        if (method.getModifiers().contains(SYNCHRONIZED)) {
            throw new InterruptProcessingException(method, errorMessage, args);
        }
    }

    public static void validateNotNativeMethod(final ExecutableElement method,
                                               final String errorMessage,
                                               final Object... args) {
        if (method.getModifiers().contains(NATIVE)) {
            throw new InterruptProcessingException(method, errorMessage, args);
        }
    }

    public static void validateNotDefaultMethod(final ExecutableElement method,
                                                final String errorMessage,
                                                final Object... args) {
        if (method.getModifiers().contains(DEFAULT)) {
            throw new InterruptProcessingException(method, errorMessage, args);
        }
    }

    public static void validateWithoutParametersMethod(final ExecutableElement method,
                                                       final String errorMessage,
                                                       final Object... args) {
        if (!method.getParameters().isEmpty()) {
            throw new InterruptProcessingException(method, errorMessage, args);
        }
    }

    private MethodValidators() {
    }
}
