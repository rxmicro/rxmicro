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

package io.rxmicro.common.util;

import io.rxmicro.common.CheckedWrapperException;

import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.CompletionException;

/**
 * Utils class to work with exceptions.
 *
 * @author nedis
 * @since 0.1
 */
public final class Exceptions {

    /**
     * Rethrows {@code throwable} if it is unchecked exception, otherwise throws a {@link CheckedWrapperException}
     * instance with {@code throwable} as cause.
     *
     * @param throwable throwable that must be thrown
     * @param <T> any type
     * @return nothing
     * @throws CheckedWrapperException if {@code throwable} is checked exception otherwise throws {@code throwable}
     * @see Error
     * @see RuntimeException
     * @see CheckedWrapperException
     */
    public static <T> T reThrow(final Throwable throwable) {
        if (throwable instanceof RuntimeException) {
            throw (RuntimeException) throwable;
        } else if (throwable instanceof Error) {
            throw (Error) throwable;
        } else {
            throw new CheckedWrapperException((Exception) throwable);
        }
    }

    /**
     * Verifies if {@code throwable} is instance of {@code exceptionType} type.
     *
     * <p>
     * In comparison with the {@code instanceof} operator this method extract real throwable from
     * {@link CompletionException} and {@link CheckedWrapperException} ones.
     *
     * @param throwable tested throwable
     * @param exceptionType expected exception type
     * @return  {@code true} if {@code throwable} is instance of {@code exceptionType} type
     * @see CompletionException
     * @see CheckedWrapperException
     */
    public static boolean isInstanceOf(final Throwable throwable,
                                       final Class<? extends Throwable> exceptionType) {
        if (throwable != null) {
            if (exceptionType.isAssignableFrom(throwable.getClass())) {
                return true;
            }
            if (throwable instanceof CompletionException) {
                return getCause(throwable, CompletionException.class)
                        .map(th -> exceptionType.isAssignableFrom(th.getClass()))
                        .orElse(false);
            }
            if (throwable instanceof CheckedWrapperException) {
                return getCause(throwable, CheckedWrapperException.class)
                        .map(th -> exceptionType.isAssignableFrom(th.getClass()))
                        .orElse(false);
            }
        }
        return false;
    }

    /**
     * Returns the {@link Optional} containing the cause of {@code throwable}.
     *
     * <p>
     * If the cause is one of provided {@code containerExceptionClasses} this methods inspects the cause deeper.
     *
     * @param throwable the tested throwable
     * @param containerExceptionClasses the ignored container exception classes
     * @return the {@link Optional} containing the cause of the {@code throwable}, if present
     */
    @SafeVarargs
    public static Optional<Throwable> getCause(final Throwable throwable,
                                               final Class<? extends Throwable>... containerExceptionClasses) {
        Throwable result = throwable.getCause();
        while (true) {
            if (result == null) {
                break;
            }
            final Throwable throwableInstance = result;
            if (Arrays.stream(containerExceptionClasses).noneMatch(e -> e.isAssignableFrom(throwableInstance.getClass()))) {
                break;
            }
            result = result.getCause();
        }
        return Optional.ofNullable(result);
    }

    /**
     * Returns real throwable ignoring {@link CompletionException} and {@link CheckedWrapperException} containers.
     *
     * @param throwable tested throwable
     * @return real throwable
     * @see CompletionException
     * @see CheckedWrapperException
     */
    public static Throwable getRealThrowable(final Throwable throwable) {
        return getCause(throwable, CompletionException.class, CheckedWrapperException.class).orElse(throwable);
    }

    private Exceptions() {
    }
}
