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

import io.rxmicro.logger.internal.jul.config.adapter.RxMicroLogRecord;
import io.rxmicro.logger.internal.jul.config.adapter.pattern.AbstractBiConsumer;
import io.rxmicro.logger.internal.jul.config.adapter.pattern.BiConsumerArguments;
import io.rxmicro.logger.internal.message.MessageBuilder;

import java.util.logging.LogRecord;

import static io.rxmicro.logger.RequestIdSupplier.UNDEFINED_REQUEST_ID;
import static io.rxmicro.logger.RequestIdSupplier.UNSUPPORTED_REQUEST_ID_FEATURE;

/**
 * @author nedis
 * @since 0.7
 */
public final class RequestIdBiConsumer extends AbstractBiConsumer {

    public RequestIdBiConsumer(final BiConsumerArguments arguments) {
        super(arguments);
        validateNoOptions(arguments);
    }

    @Override
    public void accept(final MessageBuilder messageBuilder,
                       final LogRecord logRecord) {
        String requestId;
        if (logRecord instanceof RxMicroLogRecord) {
            requestId = ((RxMicroLogRecord) logRecord).getRequestId();
            if (requestId == null) {
                requestId = UNDEFINED_REQUEST_ID;
            }
        } else {
            requestId = UNSUPPORTED_REQUEST_ID_FEATURE;
        }
        messageBuilder.append(requestId);
    }
}
