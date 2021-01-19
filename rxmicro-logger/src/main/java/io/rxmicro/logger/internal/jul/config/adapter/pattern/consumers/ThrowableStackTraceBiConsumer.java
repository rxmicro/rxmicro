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

import io.rxmicro.logger.internal.message.MessageBuilder;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.function.BiConsumer;
import java.util.logging.LogRecord;

import static java.lang.System.lineSeparator;

/**
 * @author nedis
 * @since 0.7
 */
public final class ThrowableStackTraceBiConsumer implements BiConsumer<MessageBuilder, LogRecord> {

    private final boolean singleLineEnabled;

    public ThrowableStackTraceBiConsumer(final boolean singleLineEnabled) {
        this.singleLineEnabled = singleLineEnabled;
    }

    @Override
    public void accept(final MessageBuilder messageBuilder,
                       final LogRecord record) {
        if (record.getThrown() != null) {
            final StringWriter sw = new StringWriter();
            final PrintWriter pw = new PrintWriter(sw);
            record.getThrown().printStackTrace(pw);
            pw.flush();
            if (singleLineEnabled) {
                messageBuilder.append(lineSeparator());
            }
            messageBuilder.append(sw.toString());
        }
    }

    @Override
    public String toString() {
        return "%throwable";
    }
}
