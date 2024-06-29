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

import io.rxmicro.logger.internal.message.MessageBuilder;
import io.rxmicro.logger.jul.PatternFormatterParseException;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.logging.LogRecord;

/**
 * @author nedis
 * @since 0.7
 */
public abstract class AbstractBiConsumer implements BiConsumer<MessageBuilder, LogRecord> {

    protected final ConversionSpecifier conversionSpecifier;

    protected AbstractBiConsumer(final BiConsumerArguments arguments) {
        this.conversionSpecifier = arguments.getConversionSpecifier();
    }

    protected final void validateNoOptions(final BiConsumerArguments arguments) {
        if (!arguments.getOptions().isEmpty()) {
            throw new PatternFormatterParseException(
                    "Unsupported option(s) for '?' conversion specifier: ?. Remove the unsupported option(s)!",
                    arguments.getConversionSpecifier(),
                    arguments.getOptions()
            );
        }
    }

    protected static PatternFormatterParseException createUnsupportedOptionException(final BiConsumerArguments arguments,
                                                                                     final List<String> options) {
        return new PatternFormatterParseException(
                "Unsupported option(s) for '?' conversion specifier: ?. Remove the unsupported option(s)!",
                arguments.getConversionSpecifier(),
                options
        );
    }

    protected static PatternFormatterParseException createUnsupportedOptionException(final BiConsumerArguments arguments,
                                                                                     final String option,
                                                                                     final String... supportedOptions) {
        return new PatternFormatterParseException(
                "Unsupported option for '?' conversion specifier: {?}. " +
                        "This conversion specifier supports the following options only: ?",
                arguments.getConversionSpecifier(),
                option,
                Arrays.toString(supportedOptions)
        );
    }

    @Override
    public String toString() {
        return conversionSpecifier.toString();
    }
}
