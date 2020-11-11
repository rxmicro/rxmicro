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

import io.rxmicro.common.util.Formats;
import io.rxmicro.logger.internal.jul.config.adapter.pattern.PatternFormatterBiConsumerParser;

import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.logging.Formatter;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;

/**
 * This class supports conversion specifiers that can be used as format control expressions.
 *
 * <p>
 * Each conversion specifier starts with a percent sign {@code '%'} and is followed by optional format modifiers,
 * a conversion word and optional parameters between braces.
 * The conversion word controls the data field to convert, e.g. logger name or date format.
 *
 * <p>
 * The {@link PatternFormatter} supports the following conversion specifiers:
 * <table>
 *     <tr>
 *         <th>Conversion specifiers</th>
 *         <th>Description</th>
 *     </tr>
 *     <tr>
 *         <td>
 *              <strong>c</strong><i>{length}</i><br>
 *              <strong>lo</strong><i>{length}</i><br>
 *              <strong>logger</strong><i>{length}</i><br>
 *         </td>
 *         <td>
 *              Outputs the name of the logger at the origin of the logging event.
 *              <p>
 *              This conversion specifier takes a string as its first and only option.
 *              Currently supported only one of the following options: {short}, {0}, {full}.
 *              {short} is synonym for {0} option.
 *              If no option defined this conversion specifier uses {full} option.
 *              <p>
 *              The following table describes option usage results:
 *              <table>
 *                  <tr>
 *                      <th>Conversion specifier</th>
 *                      <th>Logger name</th>
 *                      <th>Result</th>
 *                  </tr>
 *                  <tr>
 *                      <td>%logger</td>
 *                      <td>mainPackage.sub.sample.Bar</td>
 *                      <td>mainPackage.sub.sample.Bar</td>
 *                  </tr>
 *                  <tr>
 *                      <td>%logger{full}</td>
 *                      <td>mainPackage.sub.sample.Bar</td>
 *                      <td>mainPackage.sub.sample.Bar</td>
 *                  </tr>
 *                  <tr>
 *                      <td>%logger{short}</td>
 *                      <td>mainPackage.sub.sample.Bar</td>
 *                      <td>Bar</td>
 *                  </tr>
 *                  <tr>
 *                      <td>%logger{0}</td>
 *                      <td>mainPackage.sub.sample.Bar</td>
 *                      <td>Bar</td>
 *                  </tr>
 *              </table>
 *         </td>
 *     </tr>
 *     <tr>
 *         <td>
 *              <strong>C</strong><i>{length}</i><br>
 *              <strong>class</strong><i>{length}</i><br>
 *         </td>
 *         <td>
 *              Outputs the fully-qualified class name of the caller issuing the logging request.
 *              <p>
 *              This conversion specifier takes a string as its first and only option.
 *              Currently supported only one of the following options: {short}, {0}, {full}.
 *              {short} is synonym for {0} option.
 *              If no option defined this conversion specifier uses {full} option.
 *              <p>
 *              The following table describes option usage results.
 *              <table>
 *                  <tr>
 *                      <th>Conversion specifier</th>
 *                      <th>Class name</th>
 *                      <th>Result</th>
 *                  </tr>
 *                  <tr>
 *                      <td>%logger</td>
 *                      <td>mainPackage.sub.sample.Bar</td>
 *                      <td>mainPackage.sub.sample.Bar</td>
 *                  </tr>
 *                  <tr>
 *                      <td>%logger{full}</td>
 *                      <td>mainPackage.sub.sample.Bar</td>
 *                      <td>mainPackage.sub.sample.Bar</td>
 *                  </tr>
 *                  <tr>
 *                      <td>%logger{short}</td>
 *                      <td>mainPackage.sub.sample.Bar</td>
 *                      <td>Bar</td>
 *                  </tr>
 *                  <tr>
 *                      <td>%logger{0}</td>
 *                      <td>mainPackage.sub.sample.Bar</td>
 *                      <td>Bar</td>
 *                  </tr>
 *              </table>
 *              <p>
 *              <i>Generating the caller class information is not particularly fast.
 *              Thus, its use should be avoided unless execution speed is not an issue.</i>
 *         </td>
 *     </tr>
 *     <tr>
 *         <td>
 *              <strong>d</strong><i>{pattern}</i><br>
 *              <strong>date</strong><i>{pattern}</i><br>
 *              <strong>d</strong><i>{pattern, timezone}</i><br>
 *              <strong>date</strong><i>{pattern, timezone}</i><br>
 *         </td>
 *         <td>
 *              Used to output the date of the logging event.
 *              <p>
 *              The date conversion word admits a pattern string as a parameter.
 *              The pattern syntax is compatible with the format accepted by {@link java.time.format.DateTimeFormatter}.
 *              If {@code timezone} is specified, this conversion specifier uses {@link java.time.ZoneId#of(String)} method to parse it,
 *              so timezone syntax must be compatible with zone id format.
 *              <p>
 *              Here are some sample parameter values:
 *              <table>
 *                  <tr>
 *                      <th>Conversion pattern</th>
 *                      <th>Result</th>
 *                  </tr>
 *                  <tr>
 *                      <td>%date</td>
 *                      <td>{@code 2020-01-02 03:04:05.123}</td>
 *                  </tr>
 *                  <tr>
 *                      <td>%date{yyyy-MM-dd}</td>
 *                      <td>{@code 2020-01-02}</td>
 *                  </tr>
 *                  <tr>
 *                      <td>%date{HH:mm:ss.SSS}</td>
 *                      <td>{@code 03:04:05.123}</td>
 *                  </tr>
 *                  <tr>
 *                      <td>%date{, UTC}</td>
 *                      <td>{@code 2020-01-02 03:04:05.123}</td>
 *                  </tr>
 *              </table>
 *              <p>
 *              If pattern is missing (For example: {@code %d}, {@code %date}, {@code %date{, UTC}}, the default pattern will be used:
 *              {@value io.rxmicro.logger.internal.jul.config.adapter.pattern.consumers.DateTimeOfLoggingEventBiConsumer#DEFAULT_PATTERN}
 *         </td>
 *     </tr>
 *     <tr>
 *         <td>
 *              <strong>F</strong><br>
 *              <strong>file</strong><br>
 *         </td>
 *         <td>
 *              Outputs the file name of the Java source file where the logging request was issued.
 *              <p>
 *              <i>Generating the file information is not particularly fast.
 *              Thus, its use should be avoided unless execution speed is not an issue.</i>
 *         </td>
 *     </tr>
 *     <tr>
 *         <td>
 *              <strong>L</strong><br>
 *              <strong>line</strong><br>
 *         </td>
 *         <td>
 *              Outputs the line number from where the logging request was issued.
 *              <p>
 *              <i>Generating the line number information is not particularly fast.
 *              Thus, its use should be avoided unless execution speed is not an issue.</i>
 *         </td>
 *     </tr>
 *     <tr>
 *         <td>
 *              <strong>m</strong><br>
 *              <strong>mes</strong><br>
 *              <strong>message</strong><br>
 *         </td>
 *         <td>
 *              Outputs the application-supplied message associated with the logging event.
 *         </td>
 *     </tr>
 *     <tr>
 *         <td>
 *              <strong>M</strong><br>
 *              <strong>method</strong><br>
 *         </td>
 *         <td>
 *              Outputs the method name where the logging request was issued.
 *              <p>
 *              <i>Generating the method name is not particularly fast.
 *              Thus, its use should be avoided unless execution speed is not an issue.</i>
 *         </td>
 *     </tr>
 *     <tr>
 *         <td>
 *              <strong>n</strong><br>
 *         </td>
 *         <td>
 *              Outputs the platform dependent line separator character or characters.
 *              <p>
 *              This conversion word offers practically the same performance as using non-portable line separator strings
 *              such as "\n", or "\r\n". Thus, it is the preferred way of specifying a line separator.
 *         </td>
 *     </tr>
 *     <tr>
 *         <td>
 *              <strong>p</strong><br>
 *              <strong>le</strong><br>
 *              <strong>level</strong><br>
 *         </td>
 *         <td>
 *              Outputs the level of the logging event.
 *         </td>
 *     </tr>
 *     <tr>
 *         <td>
 *              <strong>r</strong><br>
 *              <strong>relative</strong><br>
 *         </td>
 *         <td>
 *              Outputs the number of milliseconds elapsed since the start of the application until the creation of the logging event.
 *         </td>
 *     </tr>
 *     <tr>
 *         <td>
 *              <strong>t</strong><br>
 *              <strong>thread</strong><br>
 *         </td>
 *         <td>
 *              Outputs the name of the thread that generated the logging event.
 *         </td>
 *     </tr>
 * </table>
 *
 *
 * @author nedis
 * @link https://logback.qos.ch/manual/layouts.html#conversionWord
 * @since 0.7
 */
public final class PatternFormatter extends Formatter {

    /**
     * Default pattern.
     */
    public static final String DEFAULT_PATTERN = "%d{yyyy-MM-dd HH:mm:ss.SSS} [%p] %c: %m%n";

    private static final int DEFAULT_MESSAGE_BUILDER_CAPACITY = 200;

    private final List<BiConsumer<StringBuilder, LogRecord>> biConsumers;

    private static String resolvePattern() {
        final LogManager manager = LogManager.getLogManager();
        return Optional.ofNullable(manager.getProperty(Formats.format("?.pattern", PatternFormatter.class.getName())))
                .orElse(DEFAULT_PATTERN);
    }

    /**
     * Creates an instance of {@link PatternFormatter} class with the specified pattern.
     *
     * @param pattern the specified pattern.
     */
    public PatternFormatter(final String pattern) {
        this.biConsumers = new PatternFormatterBiConsumerParser().parse(pattern);
    }

    /**
     * Creates an instance of {@link PatternFormatter} class with the pattern resolved from properties file.
     *
     * <p>
     * If configuration file contains the following property:
     * <pre><code>
     * io.rxmicro.logger.jul.PatternFormatter.pattern=${CUSTOM_PATTERN}
     * </code></pre>
     * then {@code ${CUSTOM_PATTERN}} will be used as pattern instead of default one: '{@value #DEFAULT_PATTERN}'
     */
    public PatternFormatter() {
        this(resolvePattern());
    }

    @Override
    public String format(final LogRecord record) {
        final StringBuilder messageBuilder = new StringBuilder(DEFAULT_MESSAGE_BUILDER_CAPACITY);
        for (final BiConsumer<StringBuilder, LogRecord> biConsumer : biConsumers) {
            biConsumer.accept(messageBuilder, record);
        }
        return messageBuilder.toString();
    }

    @Override
    public String formatMessage(final LogRecord record) {
        return format(record);
    }
}
