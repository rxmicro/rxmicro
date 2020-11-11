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

import io.rxmicro.logger.impl.AbstractLogger;
import io.rxmicro.logger.internal.jul.InternalLogger;

import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @since 0.7
 */
public final class RxMicroLogRecord extends LogRecord {

    private String threadName;

    private boolean needToInferCaller;

    private String fileName;

    private int lineNumber;

    public RxMicroLogRecord(final String loggerName,
                            final Level level,
                            final String msg) {
        super(level, msg);
        threadName = Thread.currentThread().getName();
        setLoggerName(loggerName);
        needToInferCaller = true;
    }

    public RxMicroLogRecord(final String loggerName,
                            final Level level,
                            final String msg,
                            final Throwable throwable) {
        super(level, msg);
        setThrown(throwable);
        threadName = Thread.currentThread().getName();
        setLoggerName(loggerName);
        needToInferCaller = true;
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
            getDataFromStackFrame();
        }
        return super.getSourceClassName();
    }

    @Override
    public void setSourceClassName(final String sourceClassName) {
        throw new UnsupportedOperationException("Use setStackFrame instead!");
    }

    @Override
    public String getSourceMethodName() {
        if (needToInferCaller) {
            getDataFromStackFrame();
        }
        return super.getSourceMethodName();
    }

    @Override
    public void setSourceMethodName(final String sourceMethodName) {
        throw new UnsupportedOperationException("Use setStackFrame instead!");
    }

    private void getDataFromStackFrame() {
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
            getDataFromStackFrame();
        }
        return fileName;
    }

    public int getLineNumber() {
        if (needToInferCaller) {
            getDataFromStackFrame();
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

        private static final Set<Class<?>> classes = Set.of(
                AbstractLogger.class,
                InternalLogger.class
        );

        private boolean nextFrameInValid;

        @Override
        public boolean test(final StackWalker.StackFrame frame) {
            if (nextFrameInValid) {
                return true;
            } else {
                if (classes.contains(frame.getDeclaringClass())) {
                    nextFrameInValid = true;
                }
                return false;
            }
        }
    }
}
