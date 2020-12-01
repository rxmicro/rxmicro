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

package io.rxmicro.annotation.processor.common.util;

import io.rxmicro.annotation.processor.common.model.LoggableClassName;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.TypeMirror;

import static io.rxmicro.annotation.processor.common.util.Names.getSimpleName;
import static io.rxmicro.common.util.Formats.format;
import static java.util.stream.Collectors.joining;

/**
 * @author nedis
 * @since 0.7.2
 */
public final class LoggerMessages {

    public static final String LOG_MESSAGE_LINE_DELIMITER = "------------------------------------------------------------------------";

    public static final String DEFAULT_OFFSET = "  ";

    public static String getLoggableParentChildRelationFragment(final int repeatOffset,
                                                                final boolean showParent,
                                                                final LoggableClassName parentClass,
                                                                final LoggableClassName childClass) {
        final char rowHead = '^';
        final String rowTail = "|-";
        final int offsetValue = repeatOffset * 3 + 1;
        return (showParent ? DEFAULT_OFFSET.repeat(offsetValue) + parentClass.getLoggableFullClassName() + '\n' : "") +
                DEFAULT_OFFSET.repeat(offsetValue) + DEFAULT_OFFSET + rowHead + '\n' +
                DEFAULT_OFFSET.repeat(offsetValue) + DEFAULT_OFFSET + rowTail + DEFAULT_OFFSET + childClass.getLoggableFullClassName();
    }

    public static String getAlignedWithLineDelimiterMessage(final String template,
                                                            final Object... args) {
        final String message = format(template, args);
        final int length = LOG_MESSAGE_LINE_DELIMITER.length();
        if (message.length() == length) {
            return message;
        } else if (message.length() > length) {
            return message.substring(0, length);
        } else if (message.length() + 2 < length) {
            final int leftRepeat = (length - message.length() - 2) / 2;
            final int rightRepeat = length - message.length() - 2 - leftRepeat;
            return "-".repeat(leftRepeat) + ' ' + message + ' ' + "-".repeat(rightRepeat);
        } else {
            return message;
        }
    }

    public static String getLoggableMethodName(final ExecutableElement method) {
        return format(
                "?(?)",
                method.getSimpleName(),
                method.getParameters().stream().map(v -> {
                    final TypeMirror typeMirror = v.asType();
                    if (typeMirror.getKind().isPrimitive()) {
                        return format("? ?", typeMirror, v.getSimpleName());
                    } else {
                        return format("? ?", getSimpleName(typeMirror), v.getSimpleName());
                    }
                }).collect(joining(", "))
        );
    }

    private LoggerMessages() {
    }
}
