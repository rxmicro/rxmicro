/*
 * Copyright 2019 https://rxmicro.io
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
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class Exceptions {

    public static <T> T reThrow(final Throwable throwable) {
        if (throwable instanceof RuntimeException) {
            throw (RuntimeException) throwable;
        } else if (throwable instanceof Error) {
            throw (Error) throwable;
        } else {
            throw new CheckedWrapperException(throwable);
        }
    }

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

    public static Throwable getRealThrowable(final Throwable throwable) {
        return getCause(throwable, CompletionException.class, CheckedWrapperException.class).orElse(throwable);
    }

    private Exceptions() {
    }
}
