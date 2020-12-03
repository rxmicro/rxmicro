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

package io.rxmicro.annotation.processor.integration.test.model;

import static io.rxmicro.common.util.Formats.format;

/**
 * @author nedis
 * @since 0.7.2
 */
public final class CompilationError {

    private final int lineNumber;

    private final String message;

    public CompilationError(final int lineNumber,
                            final String message) {
        this.lineNumber = lineNumber;
        this.message = message;
    }

    public CompilationError(final String message) {
        this(0, message);
    }

    public boolean isLineNumberPresents() {
        return lineNumber > 0;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        if (isLineNumberPresents()) {
            return format("CompilationError{line=?, message='?'}", lineNumber, message);
        } else {
            return format("CompilationError{message='?'}", message);
        }
    }
}
