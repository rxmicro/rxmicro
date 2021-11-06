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
import io.rxmicro.logger.internal.message.MessageBuilder;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.lang.reflect.Field;
import java.time.Instant;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.logging.LogRecord;

import static io.rxmicro.logger.jul.PatternFormatter.IGNORE_REPLACEMENT;
import static java.lang.System.lineSeparator;
import static java.util.logging.Level.INFO;
import static java.util.stream.Collectors.joining;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.condition.OS.LINUX;
import static org.junit.jupiter.api.condition.OS.MAC;
import static org.junit.jupiter.api.condition.OS.WINDOWS;

/**
 * @author nedis
 * @since 0.7
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
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
        final RxMicroLogRecord logRecord = new RxMicroLogRecord(() -> "ID12345", "full.LoggerName", INFO, "message");
        logRecord.setInstant(Instant.parse("2020-01-02T03:04:05.123Z"));
        logRecord.setThreadName("thread-1");
        logRecord.setStackFrame("package.Class", "method", "Class.java", 15);

        final String validExpected = pattern.endsWith("%n") || pattern.endsWith("%n{}") ?
                expectedMessage + lineSeparator() :
                expectedMessage;

        final PatternFormatter patternFormatter = assertDoesNotThrow(() -> new PatternFormatter(pattern, false, null));
        assertEquals(
                validExpected,
                patternFormatter.format(logRecord)
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

        final RxMicroLogRecord logRecord = new RxMicroLogRecord("LoggerName", INFO, "message");
        logRecord.setInstant(Instant.parse("2020-01-02T03:04:05.123Z"));
        logRecord.setThreadName("thread-1");
        logRecord.setStackFrame("package.Class", "method", "Class.java", 15);
        logRecord.setThrown(exception);

        final PatternFormatter patternFormatter = assertDoesNotThrow(() -> new PatternFormatter(pattern, false, null));
        setRelativeTimeStart(patternFormatter);

        assertEquals(
                List.of(
                        "1577934245123 [INFO] LoggerName: message",
                        "java.lang.Exception: Test exception",
                        "\tat package.Class.method(Class.java:15)",
                        "\tat Launcher.main(Launcher.java:10)",
                        ""
                ).stream().collect(joining(lineSeparator())),
                patternFormatter.format(logRecord)
        );
    }

    @Order(3)
    @Test
    void method_toString_should_display_all_configured_consumers() {
        final String pattern = "%logger %class %date %file %line %message %method %n %level %relative %thread %requestId";
        final PatternFormatter patternFormatter = assertDoesNotThrow(() -> new PatternFormatter(pattern, false, null));

        assertEquals(
                "PatternFormatter{biConsumers=[" +
                        "%logger{full}, ' ', %class{full}, ' ', %date{yyyy-MM-dd HH:mm:ss.SSS}, ' ', %file, ' ', %line, ' ', %message, " +
                        "' ', %method, ' ', %n, ' ', %level, ' ', %relative, ' ', %thread, ' ', %requestId, %throwable]}",
                patternFormatter.toString()
        );
    }

    @Order(4)
    @Test
    void Should_use_default_pattern_if_the_specified_one_is_invalid() {
        final String pattern = "%logger{";
        final PatternFormatter patternFormatter = assertDoesNotThrow(() -> new PatternFormatter(pattern, false, null));

        assertEquals(
                "PatternFormatter{biConsumers=[%d{yyyy-MM-dd HH:mm:ss.SSS}, ' [', %p, '] ', %c{full}, ': ', %m, %n, %throwable]}",
                patternFormatter.toString()
        );
    }

    @Order(5)
    @Test
    void Should_support_LogRecord_type_if_RxMicroLogRecord_is_not_used() {
        final LogRecord logRecord = new LogRecord(INFO, "message");
        logRecord.setLoggerName("LoggerName");
        logRecord.setInstant(Instant.parse("2020-01-02T03:04:05.123Z"));
        logRecord.setThreadID(15);

        final String pattern = "%logger{0} (%file:%line) {%thread} %requestId";

        final PatternFormatter patternFormatter = assertDoesNotThrow(() -> new PatternFormatter(pattern, false, null));
        assertEquals(
                "LoggerName (null:null) {Thread#15} unsupported-request-id-feature",
                patternFormatter.format(logRecord)
        );
    }

    @Order(6)
    @Test
    void Should_ignore_lineSeparator() {
        final LogRecord logRecord = new LogRecord(INFO, "\nPattern \r\nformatter \rmust \nignore \nall \r\nline separators\r");
        assertEquals(
                "Pattern formatter must ignore all line separators" + lineSeparator(),
                new PatternFormatter("%message%n", true, IGNORE_REPLACEMENT).format(logRecord)
        );
    }

    @Order(7)
    @Test
    void Should_replace_lineSeparator() {
        final LogRecord logRecord = new LogRecord(INFO, "\nPattern \r\nformatter \rmust \nreplace \nall \r\nline separators\r");
        assertEquals(
                "|Pattern |formatter |must |replace |all |line separators|" + lineSeparator(),
                new PatternFormatter("%message%n", true, "|").format(logRecord)
        );
    }

    @EnabledOnOs(value = WINDOWS, disabledReason = "This test uses Windows specific line separator")
    @Order(8)
    @Test
    void Should_replace_lineSeparator_on_Windows() {
        final LogRecord logRecord = new LogRecord(INFO, "Pattern formatter \r\n must replace windows specific \r\n line separators");
        assertEquals(
                "Pattern formatter \\r\\n must replace windows specific \\r\\n line separators" + lineSeparator(),
                new PatternFormatter("%message%n", true, null).format(logRecord)
        );
    }

    @EnabledOnOs(value = {LINUX, MAC}, disabledReason = "This test uses Linux/OSX specific line separator")
    @Order(9)
    @Test
    void Should_replace_lineSeparator_on_Linux_or_Osx() {
        final LogRecord logRecord = new LogRecord(INFO, "Pattern formatter \n must replace linux/osx specific \n line separators");
        assertEquals(
                "Pattern formatter \\n must replace linux/osx specific \\n line separators" + lineSeparator(),
                new PatternFormatter("%message%n", true, null).format(logRecord)
        );
    }

    @Order(10)
    @Test
    void Should_replace_lineSeparator_with_stack_trace() {
        final Exception exception = new Exception("Test exception");
        exception.setStackTrace(new StackTraceElement[]{
                new StackTraceElement("Launcher", "main", "Launcher.java", 10)
        });
        final LogRecord logRecord = new LogRecord(INFO, "\nPattern \r\nformatter \rmust \nreplace \nall \r\nline separators");
        logRecord.setThrown(exception);

        assertEquals(
                "|Pattern |formatter |must |replace |all |line separators|" +
                        "java.lang.Exception: Test exception|\tat Launcher.main(Launcher.java:10)|" + lineSeparator(),
                new PatternFormatter("%message%n", true, "|").format(logRecord)
        );
    }

    @SuppressWarnings({"unchecked", "PMD.AvoidAccessibilityAlteration"})
    private void setRelativeTimeStart(final PatternFormatter patternFormatter) throws NoSuchFieldException, IllegalAccessException {
        final Field biConsumersField = PatternFormatter.class.getDeclaredField("biConsumers");
        if (!biConsumersField.canAccess(patternFormatter)) {
            biConsumersField.setAccessible(true);
        }
        final BiConsumer<MessageBuilder, LogRecord> biConsumer =
                ((List<BiConsumer<MessageBuilder, LogRecord>>) biConsumersField.get(patternFormatter)).stream()
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
