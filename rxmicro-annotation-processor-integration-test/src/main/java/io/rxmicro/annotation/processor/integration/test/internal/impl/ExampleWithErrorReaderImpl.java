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

package io.rxmicro.annotation.processor.integration.test.internal.impl;

import io.rxmicro.annotation.processor.integration.test.internal.ExampleWithErrorReader;
import io.rxmicro.annotation.processor.integration.test.model.CompilationError;
import io.rxmicro.annotation.processor.integration.test.model.ExampleWithError;
import io.rxmicro.common.InvalidStateException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

import static io.rxmicro.annotation.processor.integration.test.ClasspathResources.getResourceContent;
import static io.rxmicro.common.util.ExCollections.unmodifiableList;
import static java.lang.System.lineSeparator;

/**
 * @author nedis
 * @since 0.7.2
 */
public final class ExampleWithErrorReaderImpl implements ExampleWithErrorReader {

    private static final String JAVA_EXTENSION_WITH_DOT = ".java";

    private static final String ERROR_LINE_NUMBER_PREFIX = "// Line:";

    private static final String ERROR_MESSAGE_PREFIX = "// Error:";

    private static final CompilationError COMMON_COMPILATION_ERROR =
            new CompilationError("Annotations processing completed with errors.");

    @Override
    public ExampleWithError read(final String classpathResource) {
        final String name = getName(classpathResource);
        final String content = getResourceContent(classpathResource);
        final List<String> lines = Arrays.asList(content.split(lineSeparator()));
        final List<CompilationError> compilationErrors = getEditableCompilationErrors(classpathResource, lines);
        compilationErrors.add(COMMON_COMPILATION_ERROR);
        return new ExampleWithError(name, content, unmodifiableList(compilationErrors));
    }

    private String getName(final String classpathResource) {
        String name = classpathResource.replace('/', '.');
        if (name.endsWith(JAVA_EXTENSION_WITH_DOT)) {
            name = name.substring(0, name.length() - JAVA_EXTENSION_WITH_DOT.length());
        }
        return name;
    }

    private List<CompilationError> getEditableCompilationErrors(final String classpathResource,
                                                                final List<String> lines) {
        final List<CompilationError> compilationErrors = new ArrayList<>();
        final ListIterator<String> iterator = lines.listIterator();
        int lineNumber = -1;
        String message = null;
        while (iterator.hasNext()) {
            final String line = iterator.next();
            if (line.startsWith(ERROR_LINE_NUMBER_PREFIX)) {
                validateLineNumber(classpathResource, lineNumber);
                lineNumber = getErrorLineNumber(classpathResource, line);
            } else if (line.startsWith(ERROR_MESSAGE_PREFIX)) {
                validateMessage(classpathResource, message);
                message = getErrorMessage(classpathResource, line, iterator);
            }
            if (lineNumber != -1 && message != null) {
                compilationErrors.add(new CompilationError(lineNumber, message));
                lineNumber = -1;
                message = null;
            }
        }
        if (compilationErrors.isEmpty()) {
            throw new InvalidStateException(
                    "'?' classpath resource does not contain required error line number and error message comment. " +
                            "Example of missing comments are: '? 12' and '? Invalid data'",
                    classpathResource, ERROR_LINE_NUMBER_PREFIX, ERROR_MESSAGE_PREFIX
            );
        }
        return compilationErrors;
    }

    private void validateLineNumber(final String classpathResource,
                                    final int lineNumber) {
        if (lineNumber != -1) {
            throw new InvalidStateException(
                    "'?' classpath resource does not contain required error message comment for found error line number: '? ?'! " +
                            "Example of missing comment is: '? Invalid data'",
                    classpathResource, ERROR_LINE_NUMBER_PREFIX, lineNumber, ERROR_MESSAGE_PREFIX
            );
        }
    }

    private void validateMessage(final String classpathResource,
                                 final String message) {
        if (message != null) {
            throw new InvalidStateException(
                    "'?' classpath resource does not contain required error line number comment for found error message: '? ?'. " +
                            "Example of missing comment is: '? 12'",
                    classpathResource, ERROR_MESSAGE_PREFIX, message, ERROR_LINE_NUMBER_PREFIX
            );
        }
    }

    private int getErrorLineNumber(final String classpathResource,
                                   final String line) {
        try {
            return Integer.parseInt(line.substring(ERROR_LINE_NUMBER_PREFIX.length()).trim());
        } catch (final NumberFormatException ex) {
            throw new InvalidStateException(
                    "'?' classpath resource contains invalid error line number comment: '?': ?",
                    classpathResource, line, ex.getMessage()
            );
        }
    }

    private String getErrorMessage(final String classpathResource,
                                   final String line,
                                   final ListIterator<String> iterator) {
        final StringBuilder errorMessageBuilder = new StringBuilder()
                .append(line.substring(ERROR_MESSAGE_PREFIX.length()).trim());
        while (iterator.hasNext()) {
            final String nextLine = iterator.next();
            if (nextLine.startsWith(ERROR_LINE_NUMBER_PREFIX)) {
                iterator.previous();
                break;
            }
            if (nextLine.startsWith("//")) {
                errorMessageBuilder.append(' ').append(nextLine.substring(2).trim());
            } else {
                break;
            }
        }
        final String message = errorMessageBuilder.toString().trim();
        if (message.isEmpty()) {
            throw new InvalidStateException(
                    "'?' classpath resource contains invalid error message comment: Expected NOT EMPTY message after '?' prefix. " +
                            "Example of missing comment is: '? Invalid data'",
                    classpathResource, ERROR_MESSAGE_PREFIX, ERROR_MESSAGE_PREFIX
            );
        }
        return message;
    }
}
