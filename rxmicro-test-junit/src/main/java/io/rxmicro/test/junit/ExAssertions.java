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
import org.junit.jupiter.api.Assertions;
import org.opentest4j.AssertionFailedError;

import java.time.Duration;
import java.time.Instant;
import java.util.function.Supplier;

import static io.rxmicro.config.Configs.getConfig;

/**
 * @author nedis
 * @since 0.7
 */
public class ExAssertions extends Assertions {

    private static boolean configInit = false;

    public static void assertInstantEquals(final Instant expected,
                                           final Instant actual,
                                           final String message) {
        assertInstantEquals(expected, actual, getDefaultInstantCompareDelta(), message);
    }

    public static void assertInstantEquals(final Instant expected,
                                           final Instant actual,
                                           final Supplier<String> messageSupplier) {
        assertInstantEquals(expected, actual, getDefaultInstantCompareDelta(), messageSupplier);
    }

    public static void assertInstantEquals(final Instant expected,
                                           final Instant actual) {
        assertInstantEquals(expected, actual, getDefaultInstantCompareDelta(), (String) null);
    }

    public static void assertInstantEquals(final Instant expected,
                                           final Instant actual,
                                           final Duration delta,
                                           final String message) {
        if (isNotEqual(expected, actual, delta)) {
            throw new AssertionFailedError(message, expected, actual);
        }
    }

    public static void assertInstantEquals(final Instant expected,
                                           final Instant actual,
                                           final Duration delta,
                                           final Supplier<String> messageSupplier) {
        if (isNotEqual(expected, actual, delta)) {
            throw new AssertionFailedError(messageSupplier.get(), expected, actual);
        }
    }

    public static void assertInstantEquals(final Instant expected,
                                           final Instant actual,
                                           final Duration delta) {
        assertInstantEquals(expected, actual, delta, (String) null);
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
    }
}
