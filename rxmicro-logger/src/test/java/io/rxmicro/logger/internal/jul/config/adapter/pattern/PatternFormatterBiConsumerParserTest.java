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

package io.rxmicro.logger.internal.jul.config.adapter.pattern;

import io.rxmicro.logger.jul.PatternFormatterParseException;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author nedis
 * @since 0.7.3
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
final class PatternFormatterBiConsumerParserTest {

    private final PatternFormatterBiConsumerParser parser = new PatternFormatterBiConsumerParser();

    @ParameterizedTest
    @Order(1)
    @CsvSource(delimiter = ';', value = {
            "%logger{;                      Missing '}' symbol for '%logger' conversion specifier!",
            "%date{yyyy-MM-dd, UTC;         Missing '}' symbol for '%date' conversion specifier!",
            "%rxmicro;                      Unsupported conversation specifier: '%rxmicro'! Only following conversation specifiers are " +
                    "supported: [%c, %lo, %logger, %C, %class, %d, %date, %F, %file, %L, %line, %m, %mes, %message, %M, %method, " +
                    "%n, %p, %le, %level, %r, %relative, %t, %thread, %id, %rid, %request-id, %request_id, %requestId]",
            "%date{yyyy-MM-dd, UTC, FULL};  Unsupported option(s) for '%date' conversion specifier: [FULL]. Remove the unsupported option(s)!",
            "%date{yyyy-MM-dd, UTC, 1, 2};  Unsupported option(s) for '%date' conversion specifier: [1, 2]. Remove the unsupported option(s)!",
            "%date{rxmicro};                'rxmicro' is invalid pattern for date of the logging event: Unknown pattern letter: r! " +
                    "The RxMicro framework uses java.time.format.DateTimeFormatter.ofPattern(String) method to format datetime!",
            "%date{yyyy-MM-dd, rxmicro};    'rxmicro' is invalid zone for date of the logging event: Unknown time-zone ID: rxmicro! " +
                    "The RxMicro framework uses java.time.ZoneId.of(String) method to parse zone id!",
            "%logger{1};                    Unsupported option for '%logger' conversion specifier: {1}. This conversion specifier supports " +
                    "the following options only: [short, 0, full]",
            "%logger{0, 8};                 Unsupported option(s) for '%logger' conversion specifier: [8]. Remove the unsupported option(s)!",
            "%logger{0, 8, 9};              Unsupported option(s) for '%logger' conversion specifier: [8, 9]. Remove the unsupported option(s)!",
            "%class{1};                     Unsupported option for '%class' conversion specifier: {1}. This conversion specifier supports " +
                    "the following options only: [short, 0, full]",
            "%class{0, 8};                  Unsupported option(s) for '%class' conversion specifier: [8]. Remove the unsupported option(s)!",
            "%class{0, 8, 9};               Unsupported option(s) for '%class' conversion specifier: [8, 9]. Remove the unsupported option(s)!",
            "%file{rxmicro};                Unsupported option(s) for '%file' conversion specifier: [rxmicro]. Remove the unsupported option(s)!",
            "%line{rxmicro};                Unsupported option(s) for '%line' conversion specifier: [rxmicro]. Remove the unsupported option(s)!",
            "%message{rxmicro};             Unsupported option(s) for '%message' conversion specifier: [rxmicro]. Remove the unsupported option(s)!",
            "%method{rxmicro};              Unsupported option(s) for '%method' conversion specifier: [rxmicro]. Remove the unsupported option(s)!",
            "%n{rxmicro};                   Unsupported option(s) for '%n' conversion specifier: [rxmicro]. Remove the unsupported option(s)!",
            "%relative{rxmicro};            Unsupported option(s) for '%relative' conversion specifier: [rxmicro]. Remove the unsupported option(s)!",
            "%thread{rxmicro};              Unsupported option(s) for '%thread' conversion specifier: [rxmicro]. Remove the unsupported option(s)!",
            "%requestId{rxmicro};           Unsupported option(s) for '%requestId' conversion specifier: [rxmicro]. Remove the unsupported option(s)!"
    })
    void Should_throw_PatternFormatterParseException(final String pattern,
                                                     final String expectedMessage) {
        final PatternFormatterParseException exception = assertThrows(PatternFormatterParseException.class, () -> parser.parse(pattern));
        assertEquals(expectedMessage, exception.getMessage());
    }
}
