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

import io.rxmicro.common.model.StringIterator;
import io.rxmicro.logger.internal.jul.config.adapter.pattern.consumers.ClassNameBiConsumer;
import io.rxmicro.logger.internal.jul.config.adapter.pattern.consumers.DateTimeOfLoggingEventBiConsumer;
import io.rxmicro.logger.internal.jul.config.adapter.pattern.consumers.FileNameBiConsumer;
import io.rxmicro.logger.internal.jul.config.adapter.pattern.consumers.LineNumberBiConsumer;
import io.rxmicro.logger.internal.jul.config.adapter.pattern.consumers.LoggerLevelBiConsumer;
import io.rxmicro.logger.internal.jul.config.adapter.pattern.consumers.LoggerNameBiConsumer;
import io.rxmicro.logger.internal.jul.config.adapter.pattern.consumers.LoggingMessageBiConsumer;
import io.rxmicro.logger.internal.jul.config.adapter.pattern.consumers.MethodNameBiConsumer;
import io.rxmicro.logger.internal.jul.config.adapter.pattern.consumers.PlatformDependentLineSeparatorBiConsumer;
import io.rxmicro.logger.internal.jul.config.adapter.pattern.consumers.RelativeTimeBiConsumer;
import io.rxmicro.logger.internal.jul.config.adapter.pattern.consumers.RequestIdBiConsumer;
import io.rxmicro.logger.internal.jul.config.adapter.pattern.consumers.StringConstantBiConsumer;
import io.rxmicro.logger.internal.jul.config.adapter.pattern.consumers.ThreadNameBiConsumer;
import io.rxmicro.logger.internal.jul.config.adapter.pattern.consumers.ThrowableStackTraceBiConsumer;
import io.rxmicro.logger.jul.PatternFormatterParseException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.logging.LogRecord;

import static io.rxmicro.common.util.ExCollections.unmodifiableList;
import static io.rxmicro.logger.internal.jul.config.adapter.pattern.ConversionSpecifier.DATE_OF_LOGGING_EVENT_1;
import static io.rxmicro.logger.internal.jul.config.adapter.pattern.ConversionSpecifier.DATE_OF_LOGGING_EVENT_2;
import static io.rxmicro.logger.internal.jul.config.adapter.pattern.ConversionSpecifier.FILE_NAME_1;
import static io.rxmicro.logger.internal.jul.config.adapter.pattern.ConversionSpecifier.FILE_NAME_2;
import static io.rxmicro.logger.internal.jul.config.adapter.pattern.ConversionSpecifier.FULL_QUALIFIED_CLASS_NAME_1;
import static io.rxmicro.logger.internal.jul.config.adapter.pattern.ConversionSpecifier.FULL_QUALIFIED_CLASS_NAME_2;
import static io.rxmicro.logger.internal.jul.config.adapter.pattern.ConversionSpecifier.LINE_NUMBER_1;
import static io.rxmicro.logger.internal.jul.config.adapter.pattern.ConversionSpecifier.LINE_NUMBER_2;
import static io.rxmicro.logger.internal.jul.config.adapter.pattern.ConversionSpecifier.LOGGER_NAME_1;
import static io.rxmicro.logger.internal.jul.config.adapter.pattern.ConversionSpecifier.LOGGER_NAME_2;
import static io.rxmicro.logger.internal.jul.config.adapter.pattern.ConversionSpecifier.LOGGER_NAME_3;
import static io.rxmicro.logger.internal.jul.config.adapter.pattern.ConversionSpecifier.LOGGING_LEVEL_1;
import static io.rxmicro.logger.internal.jul.config.adapter.pattern.ConversionSpecifier.LOGGING_LEVEL_2;
import static io.rxmicro.logger.internal.jul.config.adapter.pattern.ConversionSpecifier.LOGGING_LEVEL_3;
import static io.rxmicro.logger.internal.jul.config.adapter.pattern.ConversionSpecifier.LOGGING_MESSAGE_1;
import static io.rxmicro.logger.internal.jul.config.adapter.pattern.ConversionSpecifier.LOGGING_MESSAGE_2;
import static io.rxmicro.logger.internal.jul.config.adapter.pattern.ConversionSpecifier.LOGGING_MESSAGE_3;
import static io.rxmicro.logger.internal.jul.config.adapter.pattern.ConversionSpecifier.METHOD_NAME_1;
import static io.rxmicro.logger.internal.jul.config.adapter.pattern.ConversionSpecifier.METHOD_NAME_2;
import static io.rxmicro.logger.internal.jul.config.adapter.pattern.ConversionSpecifier.PLATFORM_DEPENDENT_LINE_SEPARATOR;
import static io.rxmicro.logger.internal.jul.config.adapter.pattern.ConversionSpecifier.RELATIVE_TIME_1;
import static io.rxmicro.logger.internal.jul.config.adapter.pattern.ConversionSpecifier.RELATIVE_TIME_2;
import static io.rxmicro.logger.internal.jul.config.adapter.pattern.ConversionSpecifier.REQUEST_ID_1;
import static io.rxmicro.logger.internal.jul.config.adapter.pattern.ConversionSpecifier.REQUEST_ID_2;
import static io.rxmicro.logger.internal.jul.config.adapter.pattern.ConversionSpecifier.REQUEST_ID_3;
import static io.rxmicro.logger.internal.jul.config.adapter.pattern.ConversionSpecifier.REQUEST_ID_4;
import static io.rxmicro.logger.internal.jul.config.adapter.pattern.ConversionSpecifier.REQUEST_ID_5;
import static io.rxmicro.logger.internal.jul.config.adapter.pattern.ConversionSpecifier.THREAD_NAME_1;
import static io.rxmicro.logger.internal.jul.config.adapter.pattern.ConversionSpecifier.THREAD_NAME_2;
import static java.util.Map.entry;
import static java.util.stream.Collectors.toList;

/**
 * @author nedis
 * @since 0.7
 */
public final class PatternFormatterBiConsumerParser {

    private final Map<ConversionSpecifier, Function<BiConsumerArguments, BiConsumer<StringBuilder, LogRecord>>> biConsumerBuilderMap;

    public PatternFormatterBiConsumerParser() {
        this.biConsumerBuilderMap = Map.ofEntries(
                entry(LOGGER_NAME_1, LoggerNameBiConsumer::new),
                entry(LOGGER_NAME_2, LoggerNameBiConsumer::new),
                entry(LOGGER_NAME_3, LoggerNameBiConsumer::new),
                entry(FULL_QUALIFIED_CLASS_NAME_1, ClassNameBiConsumer::new),
                entry(FULL_QUALIFIED_CLASS_NAME_2, ClassNameBiConsumer::new),
                entry(DATE_OF_LOGGING_EVENT_1, DateTimeOfLoggingEventBiConsumer::new),
                entry(DATE_OF_LOGGING_EVENT_2, DateTimeOfLoggingEventBiConsumer::new),
                entry(FILE_NAME_1, FileNameBiConsumer::new),
                entry(FILE_NAME_2, FileNameBiConsumer::new),
                entry(LINE_NUMBER_1, LineNumberBiConsumer::new),
                entry(LINE_NUMBER_2, LineNumberBiConsumer::new),
                entry(LOGGING_MESSAGE_1, LoggingMessageBiConsumer::new),
                entry(LOGGING_MESSAGE_2, LoggingMessageBiConsumer::new),
                entry(LOGGING_MESSAGE_3, LoggingMessageBiConsumer::new),
                entry(METHOD_NAME_1, MethodNameBiConsumer::new),
                entry(METHOD_NAME_2, MethodNameBiConsumer::new),
                entry(PLATFORM_DEPENDENT_LINE_SEPARATOR, PlatformDependentLineSeparatorBiConsumer::new),
                entry(LOGGING_LEVEL_1, LoggerLevelBiConsumer::new),
                entry(LOGGING_LEVEL_2, LoggerLevelBiConsumer::new),
                entry(LOGGING_LEVEL_3, LoggerLevelBiConsumer::new),
                entry(RELATIVE_TIME_1, RelativeTimeBiConsumer::new),
                entry(RELATIVE_TIME_2, RelativeTimeBiConsumer::new),
                entry(THREAD_NAME_1, ThreadNameBiConsumer::new),
                entry(THREAD_NAME_2, ThreadNameBiConsumer::new),
                entry(REQUEST_ID_1, RequestIdBiConsumer::new),
                entry(REQUEST_ID_2, RequestIdBiConsumer::new),
                entry(REQUEST_ID_3, RequestIdBiConsumer::new),
                entry(REQUEST_ID_4, RequestIdBiConsumer::new),
                entry(REQUEST_ID_5, RequestIdBiConsumer::new)
        );
    }

    public List<BiConsumer<StringBuilder, LogRecord>> parse(final String pattern) {
        final List<BiConsumer<StringBuilder, LogRecord>> list = new ArrayList<>();
        final StringIterator iterator = new StringIterator(pattern);
        final StringBuilder stringConstantBuilder = new StringBuilder();
        while (iterator.next()) {
            final char current = iterator.getCurrent();
            if ('%' == current) {
                if (stringConstantBuilder.length() > 0) {
                    list.add(new StringConstantBiConsumer(stringConstantBuilder.toString()));
                    stringConstantBuilder.delete(0, stringConstantBuilder.length());
                }
                if (iterator.next()) {
                    iterator.previous();
                    final Optional<BiConsumerArguments> optionalBiConsumerArguments = getBiConsumerArguments(iterator);
                    if (optionalBiConsumerArguments.isPresent()) {
                        final BiConsumerArguments biConsumerArguments = optionalBiConsumerArguments.get();
                        list.add(biConsumerBuilderMap.get(biConsumerArguments.getConversionSpecifier()).apply(biConsumerArguments));
                    } else {
                        stringConstantBuilder.append(iterator.getCurrent());
                    }
                } else {
                    stringConstantBuilder.append(current);
                }
            } else {
                stringConstantBuilder.append(current);
            }
        }
        if (stringConstantBuilder.length() > 0) {
            list.add(new StringConstantBiConsumer(stringConstantBuilder.toString()));
        }
        list.add(new ThrowableStackTraceBiConsumer());
        return unmodifiableList(list);
    }

    private Optional<BiConsumerArguments> getBiConsumerArguments(final StringIterator iterator) {
        final StringBuilder wordBuilder = new StringBuilder();
        while (iterator.next()) {
            final char ch = iterator.getCurrent();
            if ('{' == ch) {
                final ConversionSpecifier conversionSpecifier = getConversionSpecifier(wordBuilder.toString());
                final List<String> options = getBiConsumerArgumentsOptions(conversionSpecifier, iterator);
                return Optional.of(new BiConsumerArguments(conversionSpecifier, options));
            } else if (Character.isLetter(ch) || ch == '-' || ch == '_') {
                wordBuilder.append(ch);
            } else if ('%' == ch && wordBuilder.length() == 0) {
                return Optional.empty();
            } else {
                iterator.previous();
                return Optional.of(new BiConsumerArguments(getConversionSpecifier(wordBuilder.toString())));
            }
        }
        return Optional.of(new BiConsumerArguments(getConversionSpecifier(wordBuilder.toString())));
    }

    private ConversionSpecifier getConversionSpecifier(final String conversionSpecifier) {
        return ConversionSpecifier.ofConversionSpecifier(conversionSpecifier).orElseThrow(() -> {
            throw new PatternFormatterParseException(
                    "Unsupported conversation specifier: '%?'! Only following conversation specifiers are supported: ?",
                    conversionSpecifier,
                    Arrays.stream(ConversionSpecifier.values())
                            .map(ConversionSpecifier::toString)
                            .collect(toList())
            );
        });
    }

    private List<String> getBiConsumerArgumentsOptions(final ConversionSpecifier conversionSpecifier,
                                                       final StringIterator iterator) {
        final List<String> options = new ArrayList<>();
        final StringBuilder paramsBuilder = new StringBuilder();
        while (iterator.next()) {
            final char ch = iterator.getCurrent();
            if ('}' == ch) {
                if (paramsBuilder.length() > 0) {
                    options.add(paramsBuilder.toString().trim());
                }
                return unmodifiableList(options);
            } else if (',' == ch) {
                options.add(paramsBuilder.toString().trim());
                paramsBuilder.delete(0, paramsBuilder.length());
            } else {
                paramsBuilder.append(ch);
            }
        }

        throw new PatternFormatterParseException("Missing '}' symbol for '%?' conversion specifier!", conversionSpecifier.getSpecifier());
    }
}
