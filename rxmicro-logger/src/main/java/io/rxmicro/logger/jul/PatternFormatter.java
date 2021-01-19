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
import io.rxmicro.logger.internal.message.IgnoreLineSeparatorMessageBuilder;
import io.rxmicro.logger.internal.message.MessageBuilder;
import io.rxmicro.logger.internal.message.ReplaceLineSeparatorMessageBuilder;

import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;

import static io.rxmicro.logger.internal.jul.InternalLoggerHelper.logInternal;
import static java.util.logging.LogManager.getLogManager;

/**
 * This class supports conversion specifiers that can be used as format control expressions.
 *
 * <p>
 * Each conversion specifier starts with a percent sign {@code '%'} and is followed by optional format modifiers,
 * a conversion word and optional parameters between braces.
 * The conversion word controls the data field to convert, e.g. logger name or date format.
 *
 * <p>
 * <b>Configuration:</b>
 * By default each {@link PatternFormatter} is initialized using the following {@link LogManager} configuration properties.
 * If properties are not defined (or have invalid values) then the specified default values are used.
 * <ul>
 *      <li>
 *          {@code io.rxmicro.logger.jul.PatternFormatter.pattern} specifies the pattern for the logged messages
 *          (defaults to {@value #DEFAULT_PATTERN}).
 *      </li>
 *      <li>
 *          {@code io.rxmicro.logger.jul.PatternFormatter.singleLine} indicates that all logged messages must be formatted as single line,
 *          i.e. the {@code '\r\n'} or {@code '\n'} characters must be replaced. (defaults to {@code false}).
 *      </li>
 *      <li>
 *          {@code io.rxmicro.logger.jul.PatternFormatter.replacement} specifies the string that must be used as replacement for
 *          the {@code '\r\n'} or {@code '\n'} characters.
 *          This parameter is ignored if {@code io.rxmicro.logger.jul.PatternFormatter.singleLine} is not {@code true}.
 *          If this parameter is {@value #IGNORE_REPLACEMENT}, than the {@code '\r\n'} or {@code '\n'} characters will be ignored.
 *          (defaults to platform specific strings:
 *          {@code "\\r\\n"} for Windows, {@code "\\n"} for Linux and Osx)
 *      </li>
 * </ul>
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
 *     <tr>
 *         <td>
 *              <strong>id</strong><br>
 *              <strong>rid</strong><br>
 *              <strong>request-id</strong><br>
 *              <strong>request_id</strong><br>
 *              <strong>requestId</strong><br>
 *         </td>
 *         <td>
 *              Outputs the request id if specified.
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

    /**
     * Ignore replacement constant.
     */
    public static final String IGNORE_REPLACEMENT = "ignore";

    private static final String FULL_CLASS_NAME = PatternFormatter.class.getName();

    private final List<BiConsumer<MessageBuilder, LogRecord>> biConsumers;

    private final Supplier<MessageBuilder> messageBuilderSupplier;

    private static String resolvePattern() {
        return Optional.ofNullable(getLogManager().getProperty(Formats.format("?.pattern", FULL_CLASS_NAME)))
                .orElse(DEFAULT_PATTERN);
    }

    private static boolean isSingleLineEnabled() {
        return Boolean.parseBoolean(getLogManager().getProperty(Formats.format("?.singleLine", FULL_CLASS_NAME)));
    }

    private static String resolveReplacement() {
        return getLogManager().getProperty(Formats.format("?.replacement", FULL_CLASS_NAME));
    }

    /**
     * Creates an instance of {@link PatternFormatter} class with the specified pattern.
     *
     * @param pattern the specified pattern.
     * @param singleLineEnabled flag that indicates that current {@link PatternFormatter} must format message as single line.
     * @param replacement the specified replacement or {@code null} if must be default value
     */
    public PatternFormatter(final String pattern,
                            final boolean singleLineEnabled,
                            final String replacement) {
        List<BiConsumer<MessageBuilder, LogRecord>> biConsumers;
        try {
            biConsumers = new PatternFormatterBiConsumerParser().parse(pattern);
        } catch (final PatternFormatterParseException ex) {
            logInternal(
                    Level.SEVERE,
                    "The '?' pattern is invalid: ?. Set '?' as pattern for all log messages!",
                    pattern, ex.getMessage(), DEFAULT_PATTERN
            );
            biConsumers = new PatternFormatterBiConsumerParser().parse(DEFAULT_PATTERN);
        }
        this.biConsumers = biConsumers;
        if (singleLineEnabled) {
            if (replacement == null) {
                // Default replacement
                this.messageBuilderSupplier = ReplaceLineSeparatorMessageBuilder::new;
            } else if (IGNORE_REPLACEMENT.equals(replacement)) {
                this.messageBuilderSupplier = IgnoreLineSeparatorMessageBuilder::new;
            } else {
                this.messageBuilderSupplier = () -> new ReplaceLineSeparatorMessageBuilder(replacement);
            }
        } else {
            this.messageBuilderSupplier = MessageBuilder::new;
        }

    }

    /**
     * Creates an instance of {@link PatternFormatter} class with the {@code pattern} and {@code singleLineEnabled} parameters
     * resolved from properties file.
     *
     * <p>
     * If configuration file contains the following property:
     * <pre><code>
     * io.rxmicro.logger.jul.PatternFormatter.pattern=${CUSTOM_PATTERN}
     * </code></pre>
     * then {@code ${CUSTOM_PATTERN}} will be used as pattern instead of default one: '{@value #DEFAULT_PATTERN}'
     *
     * <p>
     * If configuration file contains the following property:
     * {@code io.rxmicro.logger.jul.PatternFormatter.singleLine=true}
     * then the current instance formats all messages as single line, i.e. replaces the {@code '\r\n'} or {@code '\n'}
     * characters by {@code "\\n"} for Linux and Osx or {@code "\\r\\n"} for Windows string.
     *
     * <p>
     * If configuration file contains the following property:
     * {@code io.rxmicro.logger.jul.PatternFormatter.replacement=ANY_PROVIDED_STRING}
     * then the provided string is used as replacement for the {@code '\r\n'} or {@code '\n'} characters.
     */
    public PatternFormatter() {
        this(resolvePattern(), isSingleLineEnabled(), resolveReplacement());
    }

    @Override
    public String format(final LogRecord record) {
        final MessageBuilder messageBuilder = messageBuilderSupplier.get();
        for (final BiConsumer<MessageBuilder, LogRecord> biConsumer : biConsumers) {
            biConsumer.accept(messageBuilder, record);
        }
        return messageBuilder.build();
    }

    @Override
    public String toString() {
        return "PatternFormatter{" +
                "biConsumers=" + biConsumers +
                '}';
    }
}
