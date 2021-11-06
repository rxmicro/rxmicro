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

package io.rxmicro.logger.internal.jul;

import io.rxmicro.logger.Level;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static io.rxmicro.logger.internal.jul.LevelMappings.getJulLevel;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author nedis
 * @since 0.7
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
final class LevelMappingsTest {

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "OFF;       OFF",
            "ERROR;     SEVERE",
            "WARN;      WARNING",
            "INFO;      INFO",
            "DEBUG;     FINE",
            "TRACE;     FINEST",
            "ALL;       ALL"
    })
    void method_getJulLevel_should_convert_level_correctly(final String rxMicroLevel,
                                                           final String expectedJulLevel) {
        final java.util.logging.Level actualLevel = getJulLevel(Level.valueOf(rxMicroLevel));

        assertEquals(expectedJulLevel, actualLevel.getName());
    }
}
