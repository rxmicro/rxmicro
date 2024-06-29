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

    private static final long serialVersionUID = 2754105492942720819L;

    private static final RequestIdSupplier UNDEFINED_REQUEST_ID_SUPPLIER = () -> null;

    private transient RequestIdSupplier requestIdSupplier;

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

    @Override
    public RxMicroLogRecord getLogRecord() {
        return this;
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
    private static final class StackFrameFilter implements Predicate<StackWalker.StackFrame> {

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

        private RequestIdSupplier requestIdSupplier;

        private String sourceClassName;

        private String sourceMethodName;

        private String sourceFileName;

        private int sourceLineNumber;

        private String messageOrTemplate;

        private Object[] templateArgs;

        private boolean areTemplateArgsSuppliers;

        private int threadId;

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
            if (sourceLineNumber <= 0) {
                throw new IllegalArgumentException("'sourceLineNumber' parameter must be > 0!");
            }
            this.sourceClassName = require(sourceClassName);
            this.sourceMethodName = require(sourceMethodName);
            this.sourceFileName = require(sourceFileName);
            this.sourceLineNumber = sourceLineNumber;
            return this;
        }

        @Override
        public Builder setMessage(final String message) {
            messageOrTemplate = require(message);
            templateArgs = null;
            areTemplateArgsSuppliers = false;
            return this;
        }

        @Override
        public LoggerEventBuilder setMessage(final String template,
                                             final Object... args) {
            messageOrTemplate = require(template);
            templateArgs = require(args);
            areTemplateArgsSuppliers = false;
            return this;
        }

        @Override
        public LoggerEventBuilder setMessage(final String template,
                                             final Supplier<?>... suppliers) {
            messageOrTemplate = require(template);
            templateArgs = require(suppliers);
            areTemplateArgsSuppliers = true;
            return this;
        }

        @Override
        public Builder setThreadId(final long threadId) {
            if (threadId <= 0) {
                throw new IllegalArgumentException("'threadId' parameter must be > 0!");
            }
            // See LogRecord#MIN_SEQUENTIAL_THREAD_ID and LogRecord#defaultThreadID()
            if (threadId < Integer.MAX_VALUE / 2) {
                this.threadId = (int) threadId;
            }
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

        private RxMicroLogRecord newRxMicroLogRecord() {
            final RxMicroLogRecord logRecord = new RxMicroLogRecord();
            if (requestIdSupplier != null) {
                logRecord.requestIdSupplier = requestIdSupplier;
            }
            if (sourceClassName != null) {
                logRecord.setStackFrame(sourceClassName, sourceMethodName, sourceFileName, sourceLineNumber);
            }
            if (threadId != 0) {
                logRecord.setThreadID(threadId);
            }
            if (threadName != null) {
                logRecord.setThreadName(threadName);
            }
            if (throwable != null) {
                logRecord.setThrown(throwable);
            }
            return logRecord;
        }

        @Override
        public LoggerEvent build() {
            if (templateArgs != null) {
                return new RxMicroLogRecordProxy(this);
            } else {
                final RxMicroLogRecord logRecord = newRxMicroLogRecord();
                logRecord.setMessage(messageOrTemplate);
                return logRecord;
            }
        }
    }

    /**
     * @author nedis
     * @since 0.8
     */
    public static final class RxMicroLogRecordProxy implements LoggerEvent {

        private final Builder builder;

        private RxMicroLogRecord logRecord;

        private RxMicroLogRecordProxy(final Builder builder) {
            this.builder = builder;
        }

        @Override
        public RxMicroLogRecord getLogRecord() {
            if (logRecord == null) {
                logRecord = builder.newRxMicroLogRecord();
                logRecord.setMessage(getFormattedMessage(builder));
                if (logRecord.getThreadName() == null) {
                    logRecord.setThreadName(Thread.currentThread().getName());
                }
            }
            return logRecord;
        }

        private static String getFormattedMessage(final Builder builder) {
            if (builder.areTemplateArgsSuppliers) {
                return format(builder.messageOrTemplate, Arrays.stream(builder.templateArgs).map(o -> ((Supplier<?>) o).get()).toArray());
            } else {
                return format(builder.messageOrTemplate, builder.templateArgs);
            }
        }
    }
}
