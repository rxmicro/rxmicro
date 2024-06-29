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

import java.util.List;
import java.util.logging.LogRecord;

/**
 * @author nedis
 * @since 0.7
 */
abstract class AbstractClassNameBiConsumer extends AbstractBiConsumer {

    public static final String SHORT = "short";

    public static final String FULL = "full";

    private final boolean fullName;

    private String option = FULL;

    AbstractClassNameBiConsumer(final BiConsumerArguments arguments) {
        super(ensureValid(arguments));
        final List<String> options = arguments.getOptions();
        this.fullName = options.isEmpty() || parseFullNameOption(arguments, options.get(0));
    }

    protected static BiConsumerArguments ensureValid(final BiConsumerArguments arguments) {
        final List<String> options = arguments.getOptions();
        if (options.size() > 1) {
            throw createUnsupportedOptionException(arguments, options.subList(1, options.size()));
        }
        return arguments;
    }

    private boolean parseFullNameOption(final BiConsumerArguments arguments,
                                        final String option) {
        this.option = option;
        if ("0".equals(option) || SHORT.equals(option)) {
            return false;
        } else if (FULL.equals(option)) {
            return true;
        } else {
            throw createUnsupportedOptionException(arguments, option, SHORT, "0", FULL);
        }
    }

    @Override
    public final void accept(final MessageBuilder messageBuilder,
                             final LogRecord logRecord) {
        if (fullName) {
            messageBuilder.appendWithoutTransformation(getName(logRecord));
        } else {
            final String fullName = getName(logRecord);
            final int index = fullName.lastIndexOf('.');
            final String shortName = index != -1 ? fullName.substring(index + 1) : fullName;
            messageBuilder.appendWithoutTransformation(shortName);
        }
    }

    protected abstract String getName(LogRecord logRecord);

    @Override
    public String toString() {
        return conversionSpecifier.toString() + '{' + option + '}';
    }
}
