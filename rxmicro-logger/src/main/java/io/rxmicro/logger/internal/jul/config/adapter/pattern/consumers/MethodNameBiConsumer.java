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

import java.util.logging.LogRecord;

import static io.rxmicro.common.Constants.RX_MICRO_RUNTIME_STRICT_MODE;
import static io.rxmicro.logger.internal.jul.InternalLogger.logInternal;
import static java.util.logging.Level.WARNING;

/**
 * @author nedis
 * @since 0.7
 */
public final class MethodNameBiConsumer extends AbstractBiConsumer {

    public MethodNameBiConsumer(final BiConsumerArguments arguments) {
        super(arguments);
        validateNoOptions(arguments);
        if (Boolean.parseBoolean(System.getenv(RX_MICRO_RUNTIME_STRICT_MODE))) {
            logInternal(
                    WARNING,
                    "Generating the method name is not particularly fast. " +
                            "Thus, its use should be avoided unless execution speed is not an issue."
            );
        }
    }

    @Override
    public void accept(final StringBuilder messageBuilder,
                       final LogRecord record) {
        messageBuilder.append(record.getSourceMethodName());
    }
}
