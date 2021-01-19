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

package io.rxmicro.logger.internal.message;

import static io.rxmicro.common.util.Environments.isCurrentOsWindows;

/**
 * This impl replaces the {@code '\r\n'} or {@code '\n'} characters by {@code replacement}.
 *
 * @author nedis
 * @since 0.9
 */
public final class ReplaceLineSeparatorMessageBuilder extends MessageBuilder {

    private static final String DEFAULT_REPLACEMENT = isCurrentOsWindows() ? "\\r\\n" : "\\n";

    private final String replacement;

    public ReplaceLineSeparatorMessageBuilder() {
        this(DEFAULT_REPLACEMENT);
    }

    public ReplaceLineSeparatorMessageBuilder(final String replacement) {
        this.replacement = replacement;
    }

    @Override
    public MessageBuilder append(final String value) {
        if (value == null) {
            stringBuilder.append((String) null);
        } else {
            final int length = value.length();
            int index = 0;
            while (index < length) {
                final char ch = value.charAt(index);
                if (ch == '\r') {
                    final int next = index + 1;
                    if (next < length && value.charAt(next) == '\n') {
                        index++;
                    }
                    stringBuilder.append(replacement);
                } else if (ch == '\n') {
                    stringBuilder.append(replacement);
                } else {
                    stringBuilder.append(ch);
                }
                index++;
            }
        }
        return this;
    }
}
