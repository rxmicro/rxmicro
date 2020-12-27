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

package io.rxmicro.logger.internal.jul;

import io.rxmicro.logger.LoggerEvent;
import io.rxmicro.logger.LoggerEventBuilder;
import io.rxmicro.logger.RequestIdSupplier;

import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @since 0.8
 */
public final class JULLoggerEvent implements LoggerEvent {

    final RequestIdSupplier requestIdSupplier;

    final String sourceClassName;

    final String sourceMethodName;

    final String sourceFileName;

    final int sourceLineNumber;

    final String message;

    final long threadId;

    final String threadName;

    final Throwable throwable;

    private JULLoggerEvent(final RequestIdSupplier requestIdSupplier,
                           final String sourceClassName,
                           final String sourceMethodName,
                           final String sourceFileName,
                           final int sourceLineNumber,
                           final String message,
                           final long threadId,
                           final String threadName,
                           final Throwable throwable) {
        this.requestIdSupplier = requestIdSupplier;
        this.sourceClassName = sourceClassName;
        this.sourceMethodName = sourceMethodName;
        this.sourceFileName = sourceFileName;
        this.sourceLineNumber = sourceLineNumber;
        this.message = message;
        this.threadId = threadId;
        this.threadName = threadName;
        this.throwable = throwable;
    }

    boolean isStackFramePresent() {
        return sourceClassName != null || sourceMethodName != null || sourceFileName != null || sourceLineNumber != 0;
    }

    /**
     * @author nedis
     * @since 0.8
     */
    public static final class Builder implements LoggerEventBuilder {

        private RequestIdSupplier requestIdSupplier;

        private String sourceClassName;

        private String sourceMethodName;

        private String sourceFileName;

        private int sourceLineNumber;

        private String message;

        private long threadId;

        private String threadName;

        private Throwable throwable;

        @Override
        public Builder setRequestIdSupplier(final RequestIdSupplier requestIdSupplier) {
            this.requestIdSupplier = require(requestIdSupplier);
            return this;
        }

        @Override
        public LoggerEventBuilder setStackFrame(final String sourceClassName,
                                                final String sourceMethodName,
                                                final String sourceFileName,
                                                final int sourceLineNumber) {
            setSourceClassName(sourceClassName);
            setSourceMethodName(sourceMethodName);
            setSourceFileName(sourceFileName);
            setSourceLineNumber(sourceLineNumber);
            return this;
        }

        @Override
        public Builder setSourceClassName(final String sourceClassName) {
            this.sourceClassName = require(sourceClassName);
            return this;
        }

        @Override
        public Builder setSourceMethodName(final String sourceMethodName) {
            this.sourceMethodName = require(sourceMethodName);
            return this;
        }

        @Override
        public Builder setSourceFileName(final String sourceFileName) {
            this.sourceFileName = require(sourceFileName);
            return this;
        }

        @Override
        public Builder setSourceLineNumber(final int sourceLineNumber) {
            if (sourceLineNumber <= 0) {
                throw new IllegalArgumentException("sourceLineNumber must be > 0!");
            }
            this.sourceLineNumber = sourceLineNumber;
            return this;
        }

        @Override
        public Builder setMessage(final String message) {
            this.message = require(message);
            return this;
        }

        @Override
        public Builder setThreadId(final long threadId) {
            if (threadId <= 0) {
                throw new IllegalArgumentException("threadId must be > 0!");
            }
            this.threadId = threadId;
            return this;
        }

        @Override
        public Builder setThreadName(final String threadName) {
            this.threadName = require(threadName);
            return this;
        }

        @Override
        public Builder setThrowable(final Throwable throwable) {
            this.throwable = require(throwable);
            return this;
        }

        @Override
        public JULLoggerEvent build() {
            return new JULLoggerEvent(
                    requestIdSupplier, sourceClassName, sourceMethodName, sourceFileName, sourceLineNumber,
                    message, threadId, threadName, throwable
            );
        }
    }
}
