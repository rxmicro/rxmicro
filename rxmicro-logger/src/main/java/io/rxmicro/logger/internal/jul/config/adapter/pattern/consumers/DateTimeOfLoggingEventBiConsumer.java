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

package io.rxmicro.logger.internal.jul.config.adapter.pattern.consumers;

import io.rxmicro.logger.internal.jul.config.adapter.pattern.AbstractBiConsumer;
import io.rxmicro.logger.internal.jul.config.adapter.pattern.BiConsumerArguments;
import io.rxmicro.logger.internal.message.MessageBuilder;
import io.rxmicro.logger.jul.PatternFormatterParseException;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.logging.LogRecord;

import static io.rxmicro.common.util.Formats.format;
import static java.time.LocalDateTime.ofInstant;

/**
 * @author nedis
 * @since 0.7
 */
public final class DateTimeOfLoggingEventBiConsumer extends AbstractBiConsumer {

    public static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";

    private final DateTimeFormatter dateTimeFormatter;

    private final ZoneId zoneId;

    private final String pattern;

    public DateTimeOfLoggingEventBiConsumer(final BiConsumerArguments arguments) {
        super(arguments);
        final List<String> options = arguments.getOptions();
        this.pattern = !options.isEmpty() ?
                Optional.of(options.get(0)).filter(v -> !v.isEmpty()).orElse(DEFAULT_PATTERN) :
                DEFAULT_PATTERN;
        try {
            this.dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        } catch (final IllegalArgumentException ex) {
            throw new PatternFormatterParseException(
                    "'?' is invalid pattern for date of the logging event: ?! " +
                            "The RxMicro framework uses ?.ofPattern(String) method to format datetime!",
                    pattern,
                    ex.getMessage(),
                    DateTimeFormatter.class.getName()
            );
        }
        try {
            this.zoneId = options.size() >= 2 ? ZoneId.of(options.get(1)) : ZoneId.systemDefault();
        } catch (final DateTimeException ex) {
            throw new PatternFormatterParseException(
                    "'?' is invalid zone for date of the logging event: ?! " +
                            "The RxMicro framework uses ?.of(String) method to parse zone id!",
                    options.get(1),
                    ex.getMessage(),
                    ZoneId.class.getName()
            );
        }
        if (options.size() > 2) {
            throw createUnsupportedOptionException(arguments, options.subList(2, options.size()));
        }
    }

    @Override
    public void accept(final MessageBuilder messageBuilder,
                       final LogRecord logRecord) {
        final LocalDateTime localDateTime = ofInstant(logRecord.getInstant(), zoneId);
        messageBuilder.append(localDateTime.format(dateTimeFormatter));
    }

    @Override
    public String toString() {
        return format("?{?}", conversionSpecifier, pattern);
    }
}
