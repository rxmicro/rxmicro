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

package io.rxmicro.logger.internal.jul.config.adapter;

import io.rxmicro.common.InvalidStateException;
import io.rxmicro.logger.LoggerEvent;
import io.rxmicro.logger.LoggerEventBuilder;
import io.rxmicro.logger.RequestIdSupplier;
import io.rxmicro.logger.impl.AbstractLogger;
import io.rxmicro.logger.internal.jul.InternalLoggerHelper;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @since 0.7
 */
public final class RxMicroLogRecord extends LogRecord implements LoggerEvent {

    private static final RequestIdSupplier UNDEFINED_REQUEST_ID_SUPPLIER = () -> null;

    private RequestIdSupplier requestIdSupplier;

    private String threadName;

    private boolean needToInferCaller;

    private String fileName;

    private int lineNumber;

    private RxMicroLogRecord() {
        super(Level.OFF, "null");
        needToInferCaller = true;
    }

    public RxMicroLogRecord(final String loggerName,
                            final Level level,
                            final String msg) {
        this(UNDEFINED_REQUEST_ID_SUPPLIER, loggerName, level, msg, null);
    }

    public RxMicroLogRecord(final RequestIdSupplier requestIdSupplier,
                            final String loggerName,
                            final Level level,
                            final String msg) {
        this(requestIdSupplier, loggerName, level, msg, null);
    }

    public RxMicroLogRecord(final String loggerName,
                            final Level level,
                            final String msg,
                            final Throwable throwable) {
        this(UNDEFINED_REQUEST_ID_SUPPLIER, loggerName, level, msg, throwable);
    }

    public RxMicroLogRecord(final RequestIdSupplier requestIdSupplier,
                            final String loggerName,
                            final Level level,
                            final String msg,
                            final Throwable throwable) {
        super(level, msg);
        this.requestIdSupplier = requestIdSupplier;
        setLoggerName(loggerName);
        setThrown(throwable);
        threadName = Thread.currentThread().getName();
        needToInferCaller = true;
    }

    public String getRequestId() {
        return requestIdSupplier.getRequestId();
    }

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(final String threadName) {
        this.threadName = require(threadName);
    }

    @Override
    public String getSourceClassName() {
        if (needToInferCaller) {
            extractDataFromStackFrameIfPossible();
        }
        return super.getSourceClassName();
    }

    @Override
    public void setSourceClassName(final String sourceClassName) {
        throw new UnsupportedOperationException("Use setStackFrame() instead!");
    }

    @Override
    public String getSourceMethodName() {
        if (needToInferCaller) {
            extractDataFromStackFrameIfPossible();
        }
        return super.getSourceMethodName();
    }

    @Override
    public void setSourceMethodName(final String sourceMethodName) {
        throw new UnsupportedOperationException("Use setStackFrame() instead!");
    }

    private void extractDataFromStackFrameIfPossible() {
        final Optional<StackWalker.StackFrame> optionalStackFrame = getStackFrame();
        if (optionalStackFrame.isPresent()) {
            final StackWalker.StackFrame stackFrame = optionalStackFrame.get();
            super.setSourceClassName(stackFrame.getClassName());
            super.setSourceMethodName(stackFrame.getMethodName());
            this.fileName = stackFrame.getFileName();
            this.lineNumber = stackFrame.getLineNumber();
        } else {
            super.setSourceClassName(null);
            super.setSourceMethodName(null);
        }
        needToInferCaller = false;
    }

    public String getFileName() {
        if (needToInferCaller) {
            extractDataFromStackFrameIfPossible();
        }
        return fileName;
    }

    public int getLineNumber() {
        if (needToInferCaller) {
            extractDataFromStackFrameIfPossible();
        }
        return lineNumber;
    }

    public void setStackFrame(final String sourceClassName,
                              final String sourceMethodName,
                              final String fileName,
                              final int lineNumber) {
        super.setSourceClassName(sourceClassName);
        super.setSourceMethodName(sourceMethodName);
        this.fileName = fileName;
        this.lineNumber = lineNumber;
        needToInferCaller = false;
    }

    private Optional<StackWalker.StackFrame> getStackFrame() {
        return StackWalkerHolder.STACK_WALKER.walk(stackFrameStream -> stackFrameStream.filter(new StackFrameFilter()).findFirst());
    }

    /**
     * @author nedis
     * @since 0.7
     */
    private static final class StackWalkerHolder {

        private static final StackWalker STACK_WALKER = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);

    }

    /**
     * @author nedis
     * @since 0.7
     */
    private static class StackFrameFilter implements Predicate<StackWalker.StackFrame> {

        private static final Set<Class<?>> CLASSES = Set.of(
                AbstractLogger.class,
                InternalLoggerHelper.class
        );

        private boolean nextFrameInValid;

        @Override
        public boolean test(final StackWalker.StackFrame frame) {
            if (nextFrameInValid) {
                return true;
            } else {
                if (CLASSES.contains(frame.getDeclaringClass())) {
                    nextFrameInValid = true;
                }
                return false;
            }
        }
    }

    /**
     * @author nedis
     * @since 0.8
     */
    public static final class Builder implements LoggerEventBuilder {

        private final RxMicroLogRecord logRecord = new RxMicroLogRecord();

        private String messageOrTemplate;

        private Object[] templateArgs;

        private boolean areTemplateArgsSuppliers;

        private boolean built;

        @Override
        public Builder setRequestIdSupplier(final RequestIdSupplier requestIdSupplier) {
            validateBuilderState();
            logRecord.requestIdSupplier = require(requestIdSupplier);
            return this;
        }

        @Override
        public LoggerEventBuilder setStackFrame(final String sourceClassName,
                                                final String sourceMethodName,
                                                final String sourceFileName,
                                                final int sourceLineNumber) {
            if (sourceLineNumber <= 0) {
                throw new IllegalArgumentException("'sourceLineNumber' parameter must be > 0!");
            }
            validateBuilderState();
            logRecord.setStackFrame(require(sourceClassName), require(sourceMethodName), require(sourceFileName), sourceLineNumber);
            return this;
        }

        @Override
        public Builder setMessage(final String message) {
            validateBuilderState();
            validateMessageState();
            messageOrTemplate = require(message);
            logRecord.setMessage(messageOrTemplate);
            return this;
        }

        @Override
        public LoggerEventBuilder setMessage(final String template,
                                             final Object... args) {
            validateBuilderState();
            validateMessageState();
            messageOrTemplate = require(template);
            templateArgs = args;
            areTemplateArgsSuppliers = false;
            return this;
        }

        @Override
        public LoggerEventBuilder setMessage(final String template,
                                             final Supplier<?>... suppliers) {
            validateBuilderState();
            validateMessageState();
            messageOrTemplate = require(template);
            templateArgs = suppliers;
            areTemplateArgsSuppliers = true;
            return this;
        }

        @Override
        public Builder setThreadId(final long threadId) {
            if (threadId <= 0) {
                throw new IllegalArgumentException("'threadId' parameter must be > 0!");
            }
            validateBuilderState();
            // See LogRecord#MIN_SEQUENTIAL_THREAD_ID and LogRecord#defaultThreadID()
            if (threadId < Integer.MAX_VALUE / 2) {
                logRecord.setThreadID((int) threadId);
            }
            return this;
        }

        @Override
        public Builder setThreadName(final String threadName) {
            validateBuilderState();
            logRecord.setThreadName(threadName);
            return this;
        }

        @Override
        public Builder setThrowable(final Throwable throwable) {
            validateBuilderState();
            logRecord.setThrown(require(throwable));
            return this;
        }

        @Override
        public LoggerEvent build() {
            validateBuilderState();
            built = true;
            if (templateArgs != null) {
                return new RxMicroLogRecordHolder(logRecord, messageOrTemplate, templateArgs, areTemplateArgsSuppliers);
            } else {
                return logRecord;
            }
        }

        private void validateBuilderState() {
            if (built) {
                throw new InvalidStateException("The logger event already built! Create a new instance of the logger event builder!");
            }
        }

        private void validateMessageState() {
            if (messageOrTemplate != null) {
                throw new InvalidStateException(
                        "Message already set: ?!",
                        templateArgs == null ?
                                messageOrTemplate :
                                RxMicroLogRecordHolder.getFormattedMessage(messageOrTemplate, templateArgs, areTemplateArgsSuppliers)
                );
            }
        }
    }

    /**
     * @author nedis
     * @since 0.8
     */
    public static final class RxMicroLogRecordHolder implements LoggerEvent {

        private final RxMicroLogRecord logRecord;

        private final String template;

        private final Object[] args;

        private final boolean areTemplateArgsSuppliers;

        private boolean returned;

        private RxMicroLogRecordHolder(final RxMicroLogRecord logRecord,
                                       final String template,
                                       final Object[] args,
                                       final boolean areTemplateArgsSuppliers) {
            this.logRecord = logRecord;
            this.template = template;
            this.args = args;
            this.areTemplateArgsSuppliers = areTemplateArgsSuppliers;
        }

        public RxMicroLogRecord getLogRecord() {
            if (returned) {
                throw new InvalidStateException("Log record already returned!");
            }
            logRecord.setMessage(getFormattedMessage(template, args, areTemplateArgsSuppliers));
            if (logRecord.getThreadName() == null) {
                logRecord.setThreadName(Thread.currentThread().getName());
            }
            returned = true;
            return logRecord;
        }

        private static String getFormattedMessage(final String template,
                                                  final Object[] args,
                                                  final boolean areTemplateArgsSuppliers) {
            if (areTemplateArgsSuppliers) {
                return format(template, Arrays.stream(args).map(o -> ((Supplier<?>) o).get()).toArray());
            } else {
                return format(template, args);
            }
        }
    }
}
