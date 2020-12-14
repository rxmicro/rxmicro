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

package io.rxmicro.logger.jul;

import io.rxmicro.logger.internal.jul.config.adapter.RxMicroLogRecord;
import io.rxmicro.logger.internal.jul.config.adapter.pattern.consumers.RelativeTimeBiConsumer;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.lang.reflect.Field;
import java.time.Instant;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.logging.LogRecord;

import static java.lang.System.lineSeparator;
import static java.util.logging.Level.INFO;
import static java.util.stream.Collectors.joining;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author nedis
 * @since 0.7
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
final class PatternFormatterTest {

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "Hello world;                                                    Hello world",
            "%message%n;                                                     message",
            "%c: %message%;                                                  full.LoggerName: message%",
            "%c{full}: %message%;                                            full.LoggerName: message%",
            "%c{short}: %message%;                                           LoggerName: message%",
            "%c{0}: %message%;                                               LoggerName: message%",
            "%lo: %message%n;                                                full.LoggerName: message",
            "%lo{full}: %message%n;                                          full.LoggerName: message",
            "%lo{short}: %message%n;                                         LoggerName: message",
            "%lo{0}: %message%n;                                             LoggerName: message",
            "%logger: %message%%%n;                                          full.LoggerName: message%",
            "%logger{full}: %message%%%n;                                    full.LoggerName: message%",
            "%logger{short}: %message%%%n;                                   LoggerName: message%",
            "%logger{0}: %message%%%n;                                       LoggerName: message%",

            "%class: %message%%%n;                                           package.Class: message%",
            "%class{full}: %message%%%n;                                     package.Class: message%",
            "%class{short}: %message%%%n;                                    Class: message%",
            "%class{0}: %message%%%n;                                        Class: message%",
            "%C: %message%%%n;                                               package.Class: message%",
            "%C{full}: %message%%%n;                                         package.Class: message%",
            "%C{short}: %message%%%n;                                        Class: message%",
            "%C{0}: %message%%%n;                                            Class: message%",

            "{%id}: %message%%%n;                                            {ID12345}: message%",
            "{%rid}: %message%%%n;                                           {ID12345}: message%",
            "{%request-id}: %message%%%n;                                    {ID12345}: message%",
            "{%request_id}: %message%%%n;                                    {ID12345}: message%",
            "{%requestId}: %message%%%n;                                     {ID12345}: message%",

            "%d{yyyy-MM-dd HH:mm:ss.SSS, UTC} [%p] %c: %m%n;                 2020-01-02 03:04:05.123 [INFO] full.LoggerName: message",
            "%d{, UTC} {%t{}} %c{}: %m{}%n{};                                2020-01-02 03:04:05.123 {thread-1} full.LoggerName: message",
            "%date{, UTC} [%level] %logger: %message%n;                      2020-01-02 03:04:05.123 [INFO] full.LoggerName: message",
            "%d{HH:mm:ss.SSS, UTC} {%t} [%le] %C.%M: %m%n;                   03:04:05.123 {thread-1} [INFO] package.Class.method: message",
            "%date{yyyy-MM-dd, UTC} {%thread} [%le] %class.%method: %mes%n;  2020-01-02 {thread-1} [INFO] package.Class.method: message",
            "%d{HH:mm:ss.SSS, UTC} %C.%M(%F:%L): %m%n;                       03:04:05.123 package.Class.method(Class.java:15): message",
            "%class{0}.%method(%file:%line): %mes%n;                         Class.method(Class.java:15): message"
    })
    @Order(1)
    void Should_format_correctly(final String pattern,
                                 final String expectedMessage) {
        final RxMicroLogRecord record = new RxMicroLogRecord(() -> "ID12345", "full.LoggerName", INFO, "message");
        record.setInstant(Instant.parse("2020-01-02T03:04:05.123Z"));
        record.setThreadName("thread-1");
        record.setStackFrame("package.Class", "method", "Class.java", 15);

        final String validExpected = pattern.endsWith("%n") || pattern.endsWith("%n{}") ?
                expectedMessage + lineSeparator() :
                expectedMessage;

        final PatternFormatter patternFormatter = assertDoesNotThrow(() -> new PatternFormatter(pattern));
        assertEquals(
                validExpected,
                patternFormatter.format(record)
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "%r [%p] %c: %m%n",
            "%relative [%level] %logger: %message%n"
    })
    @Order(2)
    void Should_format_with_relative_time_and_throwable(final String pattern) throws NoSuchFieldException, IllegalAccessException {
        final Exception exception = new Exception("Test exception");
        exception.setStackTrace(new StackTraceElement[]{
                new StackTraceElement("package.Class", "method", "Class.java", 15),
                new StackTraceElement("Launcher", "main", "Launcher.java", 10)
        });

        final RxMicroLogRecord record = new RxMicroLogRecord("LoggerName", INFO, "message");
        record.setInstant(Instant.parse("2020-01-02T03:04:05.123Z"));
        record.setThreadName("thread-1");
        record.setStackFrame("package.Class", "method", "Class.java", 15);
        record.setThrown(exception);

        final PatternFormatter patternFormatter = assertDoesNotThrow(() -> new PatternFormatter(pattern));
        setRelativeTimeStart(patternFormatter);

        assertEquals(
                List.of(
                        "1577934245123 [INFO] LoggerName: message",
                        "",
                        "java.lang.Exception: Test exception",
                        "\tat package.Class.method(Class.java:15)",
                        "\tat Launcher.main(Launcher.java:10)",
                        ""
                ).stream().collect(joining(lineSeparator())),
                patternFormatter.format(record)
        );
    }

    @Order(3)
    @Test
    void toString_should_display_all_configured_consumers() {
        final String pattern = "%logger %class %date %file %line %message %method %n %level %relative %thread %requestId";
        final PatternFormatter patternFormatter = assertDoesNotThrow(() -> new PatternFormatter(pattern));

        assertEquals(
                "PatternFormatter{biConsumers=[" +
                        "%logger{full}, ' ', %class{full}, ' ', %date{yyyy-MM-dd HH:mm:ss.SSS}, ' ', %file, ' ', %line, ' ', %message, " +
                        "' ', %method, ' ', %n, ' ', %level, ' ', %relative, ' ', %thread, ' ', %requestId, %throwable]}",
                patternFormatter.toString()
        );
    }

    @Order(4)
    @Test
    void Should_use_default_pattern_if_the_specified_one_is_invalid(){
        final String pattern = "%logger{";
        final PatternFormatter patternFormatter = assertDoesNotThrow(() -> new PatternFormatter(pattern));

        assertEquals(
                "PatternFormatter{biConsumers=[%d{yyyy-MM-dd HH:mm:ss.SSS}, ' [', %p, '] ', %c{full}, ': ', %m, %n, %throwable]}",
                patternFormatter.toString()
        );
    }

    @SuppressWarnings("unchecked")
    private void setRelativeTimeStart(final PatternFormatter patternFormatter) throws NoSuchFieldException, IllegalAccessException {
        final Field biConsumersField = PatternFormatter.class.getDeclaredField("biConsumers");
        if (!biConsumersField.canAccess(patternFormatter)) {
            biConsumersField.setAccessible(true);
        }
        final BiConsumer<StringBuilder, LogRecord> biConsumer =
                ((List<BiConsumer<StringBuilder, LogRecord>>) biConsumersField.get(patternFormatter)).stream()
                        .filter(c -> c instanceof RelativeTimeBiConsumer)
                        .findFirst()
                        .orElseThrow();
        final Field startField = RelativeTimeBiConsumer.class.getDeclaredField("start");
        if (!startField.canAccess(biConsumer)) {
            startField.setAccessible(true);
        }
        startField.set(biConsumer, 0L);
    }
}