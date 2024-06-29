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

package io.rxmicro.config;

import io.rxmicro.common.meta.BuilderMethod;
import io.rxmicro.validation.constraint.MaxInt;
import io.rxmicro.validation.constraint.MinInt;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static io.rxmicro.common.util.Formats.format;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author nedis
 * @since 0.12
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ValidationTest {

    private static final String DUMMY_VALIDATION = "dummy-validation";

    private static final String DUMMY_VALIDATION_THREAD_PRIORITY = DUMMY_VALIDATION + ".threadPriority";

    @BeforeAll
    static void prepareConfigs() {
        new Configs.Builder()
                .withOrderedConfigSources(
                        ConfigSource.JAVA_SYSTEM_PROPERTIES
                )
                .build();
    }

    @AfterAll
    static void destroyConfigs() {
        new Configs.Destroyer()
                .destroy();
    }

    @Test
    @Order(1)
    void should_throw_ConfigException_because_tread_priority_less_then_min_allowed() {
        System.setProperty(DUMMY_VALIDATION_THREAD_PRIORITY, "0");

        try {
            final ConfigException exception =
                    assertThrows(ConfigException.class, () -> Configs.getConfig(DUMMY_VALIDATION, DummyValidationConfig.class));

            assertEquals(
                    format(
                            "Invalid configuration parameter \"?\": Expected that value >= 1, but actual is 0!",
                            DUMMY_VALIDATION_THREAD_PRIORITY
                    ),
                    exception.getMessage()
            );
        } finally {
            System.clearProperty(DUMMY_VALIDATION_THREAD_PRIORITY);
        }
    }

    @Test
    @Order(2)
    void should_throw_ConfigException_because_tread_priority_more_then_max_allowed() {
        System.setProperty(DUMMY_VALIDATION_THREAD_PRIORITY, "20");

        try {
            final ConfigException exception =
                    assertThrows(ConfigException.class, () -> Configs.getConfig(DUMMY_VALIDATION, DummyValidationConfig.class));

            assertEquals(
                    format(
                            "Invalid configuration parameter \"?\": Expected that value <= 10, but actual is 20!",
                            DUMMY_VALIDATION_THREAD_PRIORITY
                    ),
                    exception.getMessage()
            );
        } finally {
            System.clearProperty(DUMMY_VALIDATION_THREAD_PRIORITY);
        }
    }

    @Test
    @Order(Integer.MAX_VALUE)
    void should_not_throw_any_exception_because_properties_are_not_set() {
        final DummyValidationConfig config =
                assertDoesNotThrow(() -> Configs.getConfig(DUMMY_VALIDATION, DummyValidationConfig.class));

        assertEquals(Thread.NORM_PRIORITY, config.getThreadPriority());
    }

    @SingletonConfigClass
    public static final class DummyValidationConfig extends Config {

        @MinInt(Thread.MIN_PRIORITY)
        @MaxInt(Thread.MAX_PRIORITY)
        private Integer threadPriority = Thread.NORM_PRIORITY;

        public DummyValidationConfig() {
            super(DUMMY_VALIDATION);
        }

        public Integer getThreadPriority() {
            return threadPriority;
        }

        @SuppressWarnings("unused") // Used via reflection by `Config*` classes
        @BuilderMethod
        public DummyValidationConfig setThreadPriority(final Integer threadPriority) {
            this.threadPriority = ensureValid(threadPriority);
            return this;
        }
    }
}
