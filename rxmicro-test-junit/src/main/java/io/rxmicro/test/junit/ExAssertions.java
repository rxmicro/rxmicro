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

package io.rxmicro.test.junit;

import io.rxmicro.config.Configs;
import io.rxmicro.test.GlobalTestConfig;
import io.rxmicro.test.SystemErr;
import io.rxmicro.test.SystemOut;
import org.junit.jupiter.api.Assertions;
import org.opentest4j.AssertionFailedError;

import java.time.Duration;
import java.time.Instant;
import java.util.function.Supplier;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.config.Configs.getConfig;

/**
 * {@code ExAssertions} is a collection of utility methods that extends the {@link Assertions} utils.
 *
 * @author nedis
 * @since 0.7
 */
public class ExAssertions extends Assertions {

    private static boolean configInit;

    /**
     * Asserts that {@code expected} and {@code actual} are equal within the default {@code delta} configured
     * via {@link GlobalTestConfig} config class.
     *
     * @param expected the expected instant
     * @param actual the actual instant
     * @param message the custom error message that should be used if assertion is failed
     * @throws AssertionFailedError if {@code expected} and {@code actual} are not equal
     * @see GlobalTestConfig#getDefaultInstantCompareDelta()
     */
    public static void assertInstantEquals(final Instant expected,
                                           final Instant actual,
                                           final String message) {
        assertInstantEquals(expected, actual, getDefaultInstantCompareDelta(), message);
    }

    /**
     * Asserts that {@code expected} and {@code actual} are equal within the default {@code delta} configured
     * via {@link GlobalTestConfig} config class.
     *
     * @param expected the expected instant
     * @param actual the actual instant
     * @param messageSupplier the custom error message supplier that should be used if assertion is failed
     * @throws AssertionFailedError if {@code expected} and {@code actual} are not equal
     * @see GlobalTestConfig#getDefaultInstantCompareDelta()
     */
    public static void assertInstantEquals(final Instant expected,
                                           final Instant actual,
                                           final Supplier<String> messageSupplier) {
        assertInstantEquals(expected, actual, getDefaultInstantCompareDelta(), messageSupplier);
    }

    /**
     * Asserts that {@code expected} and {@code actual} are equal within the default {@code delta} configured
     * via {@link GlobalTestConfig} config class.
     *
     * @param expected the expected instant
     * @param actual the actual instant
     * @throws AssertionFailedError if {@code expected} and {@code actual} are not equal
     * @see GlobalTestConfig#getDefaultInstantCompareDelta()
     */
    public static void assertInstantEquals(final Instant expected,
                                           final Instant actual) {
        assertInstantEquals(expected, actual, getDefaultInstantCompareDelta(), (String) null);
    }

    /**
     * Asserts that {@code expected} and {@code actual} are equal within the given non-negative {@code delta}.
     *
     * @param expected the expected instant
     * @param actual the actual instant
     * @param delta the custom delta
     * @param message the custom error message that should be used if assertion is failed.
     * @throws IllegalArgumentException if delta is negative
     * @throws AssertionFailedError if {@code expected} and {@code actual} are not equal
     */
    public static void assertInstantEquals(final Instant expected,
                                           final Instant actual,
                                           final Duration delta,
                                           final String message) {
        if (isNotEqual(expected, actual, delta)) {
            throw new AssertionFailedError(message, expected, actual);
        }
    }

    /**
     * Asserts that {@code expected} and {@code actual} are equal within the given non-negative {@code delta}.
     *
     * @param expected the expected instant
     * @param actual the actual instant
     * @param delta the custom delta
     * @param messageSupplier the custom error message supplier that should be used if assertion is failed.
     * @throws IllegalArgumentException if delta is negative
     * @throws AssertionFailedError if {@code expected} and {@code actual} are not equal
     */
    public static void assertInstantEquals(final Instant expected,
                                           final Instant actual,
                                           final Duration delta,
                                           final Supplier<String> messageSupplier) {
        if (isNotEqual(expected, actual, delta)) {
            throw new AssertionFailedError(messageSupplier.get(), expected, actual);
        }
    }

    /**
     * Asserts that {@code expected} and {@code actual} are equal within the given non-negative {@code delta}.
     *
     * @param expected the expected instant
     * @param actual the actual instant
     * @param delta the custom delta
     * @throws IllegalArgumentException if delta is negative
     * @throws AssertionFailedError if {@code expected} and {@code actual} are not equal
     */
    public static void assertInstantEquals(final Instant expected,
                                           final Instant actual,
                                           final Duration delta) {
        assertInstantEquals(expected, actual, delta, (String) null);
    }

    /**
     * Asserts that the current {@link SystemOut} contain the required {@code message}.
     *
     * @param systemOut the current {@link SystemOut}
     * @param message the required message
     * @throws AssertionFailedError if the current {@link SystemOut} does not contain required message
     */
    public static void assertSystemOutContains(final SystemOut systemOut,
                                               final String message) {
        assertSystemOutContains(systemOut.asString(), message);
    }

    /**
     * Asserts that the current {@code systemOut} contain the required {@code message}.
     *
     * @param systemOut the current system out
     * @param message the required message
     * @throws AssertionFailedError if the current {@code systemOut} does not contain required message
     */
    public static void assertSystemOutContains(final String systemOut,
                                               final String message) {
        assertTrue(
                systemOut.contains(message),
                () -> format("System out does not contain required message: '?'! Full out is:\n?", message, systemOut)
        );
    }

    /**
     * Asserts that the current {@link SystemErr} contain the required {@code message}.
     *
     * @param systemErr the current {@link SystemErr}
     * @param message the required message
     * @throws AssertionFailedError if the current {@link SystemOut} does not contain required message
     */
    public static void assertSystemErrContains(final SystemErr systemErr,
                                               final String message) {
        assertSystemErrContains(systemErr.asString(), message);
    }

    /**
     * Asserts that the current {@code systemOut} contain the required {@code message}.
     *
     * @param systemErr the current system out
     * @param message the required message
     * @throws AssertionFailedError if the current {@code systemOut} does not contain required message
     */
    public static void assertSystemErrContains(final String systemErr,
                                               final String message) {
        assertTrue(
                systemErr.contains(message),
                () -> format("System err does not contain required message: '?'! Full out is:\n?", message, systemErr)
        );
    }

    private static boolean isNotEqual(final Instant expected,
                                      final Instant actual,
                                      final Duration delta) {
        if (delta.isNegative()) {
            throw new IllegalArgumentException("delta must be positive!");
        }
        final Instant minExpected = expected.minusMillis(delta.toMillis());
        final Instant maxExpected = expected.plusMillis(delta.toMillis());
        return actual.isBefore(minExpected) || actual.isAfter(maxExpected);
    }

    private static Duration getDefaultInstantCompareDelta() {
        if (!configInit) {
            new Configs.Builder().buildIfNotConfigured();
            configInit = true;
        }
        return getConfig(GlobalTestConfig.class).getDefaultInstantCompareDelta();
    }

    /**
     * Protected constructor allowing subclassing but not direct instantiation.
     */
    protected ExAssertions() {
        // Protected constructor allowing subclassing but not direct instantiation.
    }
}
