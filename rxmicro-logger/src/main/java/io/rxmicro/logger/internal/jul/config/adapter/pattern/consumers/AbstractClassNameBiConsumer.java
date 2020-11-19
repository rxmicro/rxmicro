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

    AbstractClassNameBiConsumer(final BiConsumerArguments arguments) {
        super(arguments);
        final List<String> options = arguments.getOptions();
        this.fullName = options.isEmpty() || parseFullNameOption(arguments, options.get(0));
        if (options.size() > 1) {
            throw createUnsupportedOptionException(arguments, options.subList(1, options.size()));
        }
    }

    private boolean parseFullNameOption(final BiConsumerArguments arguments,
                                        final String option) {
        if ("0".equals(option) || SHORT.equals(option)) {
            return false;
        } else if (FULL.equals(option)) {
            return true;
        } else {
            throw createUnsupportedOptionException(arguments, option, SHORT, "0", FULL);
        }
    }

    @Override
    public final void accept(final StringBuilder messageBuilder,
                             final LogRecord record) {
        if (fullName) {
            messageBuilder.append(getName(record));
        } else {
            final String fullName = getName(record);
            final int index = fullName.lastIndexOf('.');
            final String shortName = index != -1 ? fullName.substring(index + 1) : fullName;
            messageBuilder.append(shortName);
        }
    }

    protected abstract String getName(LogRecord record);
}
