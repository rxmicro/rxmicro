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

package io.rxmicro.netty.runtime.internal;

import io.rxmicro.common.meta.BuilderMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import static io.rxmicro.common.CommonConstants.RX_MICRO_FRAMEWORK_NAME;
import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @since 0.8
 */
final class EventLoopThreadFactory implements ThreadFactory {

    private final int threadCount;

    private final boolean daemon;

    private final String threadName;

    private final boolean singleThread;

    private final int threadPriority;

    private final Thread.UncaughtExceptionHandler uncaughtExceptionHandler;

    private final AtomicInteger threadSequenceCounter = new AtomicInteger(1);

    private EventLoopThreadFactory(final int threadCount,
                                   final boolean daemon,
                                   final String threadName,
                                   final int threadPriority,
                                   final Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {
        this.daemon = daemon;
        this.threadCount = threadCount;
        this.threadName = require(threadName);
        this.singleThread = threadCount == 1;
        this.threadPriority = threadPriority;
        this.uncaughtExceptionHandler = uncaughtExceptionHandler;
    }

    public boolean isDaemon() {
        return daemon;
    }

    public int getThreadCount() {
        return threadCount;
    }

    @Override
    @SuppressWarnings("NullableProblems")
    public Thread newThread(final Runnable runnable) {
        final Thread thread = new Thread(runnable);
        if (singleThread) {
            thread.setName(threadName);
        } else {
            thread.setName(threadName + "-" + threadSequenceCounter.getAndIncrement());
        }
        if (thread.isDaemon() != daemon) {
            thread.setDaemon(daemon);
        }
        if (thread.getPriority() != threadPriority) {
            thread.setPriority(threadPriority);
        }
        thread.setUncaughtExceptionHandler(uncaughtExceptionHandler);
        return thread;
    }

    /**
     * @author nedis
     * @since 0.8
     */
    @SuppressWarnings("UnusedReturnValue")
    static final class Builder {

        private boolean daemon;

        private int threadCount;

        private String threadNameQualifier;

        private String threadNameCategory;

        private int threadPriority;

        private Thread.UncaughtExceptionHandler uncaughtExceptionHandler;

        @BuilderMethod
        public Builder setDaemon(final boolean daemon) {
            this.daemon = daemon;
            return this;
        }

        @BuilderMethod
        public Builder setThreadCount(final int threadCount) {
            this.threadCount = threadCount;
            return this;
        }

        @BuilderMethod
        public Builder setThreadNameQualifier(final String threadNameQualifier) {
            this.threadNameQualifier = require(threadNameQualifier);
            return this;
        }

        @BuilderMethod
        public Builder setThreadNameCategory(final String threadNameCategory) {
            this.threadNameCategory = require(threadNameCategory);
            return this;
        }

        @BuilderMethod
        public Builder setThreadPriority(final int threadPriority) {
            this.threadPriority = threadPriority;
            return this;
        }

        @BuilderMethod
        public Builder setUncaughtExceptionHandler(final Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {
            this.uncaughtExceptionHandler = require(uncaughtExceptionHandler);
            return this;
        }

        public EventLoopThreadFactory build(final Map<String, Integer> registeredStaticThreadNames) {
            final String threadName = generateStaticThreadName();
            final Integer count = getNewThreadCount(registeredStaticThreadNames, threadName);
            registeredStaticThreadNames.put(threadName, count);
            // For test environment the RxMicro framework can create a few threads with the same name.
            final String uniqueThreadName = count == 1 ? threadName : threadName + "-" + count;
            return new EventLoopThreadFactory(threadCount, daemon, uniqueThreadName, threadPriority, uncaughtExceptionHandler);
        }

        private String generateStaticThreadName() {
            final List<String> fragments = new ArrayList<>(3);
            fragments.add(RX_MICRO_FRAMEWORK_NAME);
            if (threadNameQualifier != null) {
                fragments.add(threadNameQualifier);
            }
            fragments.add(require(threadNameCategory, "'threadNameCategory' parameter must be set!"));
            return String.join("-", fragments);
        }

        private Integer getNewThreadCount(final Map<String, Integer> registeredStaticThreadNames,
                                          final String threadName) {
            final Integer count = registeredStaticThreadNames.get(threadName);
            if (count == null) {
                return 1;
            } else {
                return count + 1;
            }
        }
    }
}
