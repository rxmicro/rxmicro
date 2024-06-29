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

package io.rxmicro.netty.runtime;

import io.rxmicro.common.meta.BuilderMethod;
import io.rxmicro.config.Config;
import io.rxmicro.config.ConfigException;
import io.rxmicro.config.SingletonConfigClass;
import io.rxmicro.validation.constraint.MaxInt;
import io.rxmicro.validation.constraint.MinInt;

import static java.lang.Thread.MAX_PRIORITY;
import static java.lang.Thread.MIN_PRIORITY;

/**
 * Allows configuring the netty runtime.
 *
 * @author nedis
 * @since 0.8
 */
@SingletonConfigClass
public final class NettyRuntimeConfig extends Config {

    /**
     * Max possible thread count.
     */
    public static final int MAX_POSSIBLE_WORKER_THREAD_COUNT = 256;

    /**
     * Default acceptor thread count.
     */
    public static final int DEFAULT_ACCEPTOR_THREAD_COUNT = 1;

    /**
     * Default acceptor thread category name.
     */
    public static final String DEFAULT_ACCEPTOR_THREAD_NAME_CATEGORY = "acceptor";

    /**
     * Default acceptor thread priority.
     */
    public static final int DEFAULT_ACCEPTOR_THREAD_PRIORITY = Thread.NORM_PRIORITY;

    /**
     * Default worker thread count.
     */
    public static final int DEFAULT_WORKER_THREAD_COUNT = Math.max(Runtime.getRuntime().availableProcessors(), 4);

    /**
     * Default worker thread category name.
     */
    public static final String DEFAULT_WORKER_THREAD_NAME_CATEGORY = "worker";

    /**
     * Default worker thread priority.
     */
    public static final int DEFAULT_WORKER_THREAD_PRIORITY = Thread.NORM_PRIORITY + 1;

    private boolean shareWorkerThreads;

    @MinInt(1)
    @MaxInt(MAX_POSSIBLE_WORKER_THREAD_COUNT)
    private int acceptorThreadCount = DEFAULT_ACCEPTOR_THREAD_COUNT;

    private String acceptorThreadNameCategory = DEFAULT_ACCEPTOR_THREAD_NAME_CATEGORY;

    @MinInt(MIN_PRIORITY)
    @MaxInt(MAX_PRIORITY)
    private int acceptorThreadPriority = DEFAULT_ACCEPTOR_THREAD_PRIORITY;

    private boolean workerThreadDaemon = true;

    @MinInt(1)
    @MaxInt(MAX_POSSIBLE_WORKER_THREAD_COUNT)
    private int workerThreadCount = DEFAULT_WORKER_THREAD_COUNT;

    private String workerThreadNameCategory = DEFAULT_WORKER_THREAD_NAME_CATEGORY;

    @MinInt(MIN_PRIORITY)
    @MaxInt(MAX_PRIORITY)
    private int workerThreadPriority = DEFAULT_WORKER_THREAD_PRIORITY;

    private NettyChannelIdType channelIdType = PredefinedNettyChannelIdType.SHORT;

    public NettyRuntimeConfig() {
        super(Config.getDefaultNameSpace(NettyRuntimeConfig.class));
    }

    /**
     * Returns {@code true} if worker threads must be shared between rest server, rest client and data repository handlers.
     *
     * @return {@code true} if worker threads must be shared between rest server, rest client and data repository handlers.
     */
    public boolean isShareWorkerThreads() {
        return shareWorkerThreads;
    }

    /**
     * Activates or disables the share worker thread options.
     *
     * <p>
     * If {@code true} than the worker threads must be shared between rest server, rest client and data repository handlers.
     *
     * @param shareWorkerThreads share worker threads or not
     * @return the reference to this {@link NettyRuntimeConfig} instance
     */
    @BuilderMethod
    public NettyRuntimeConfig setShareWorkerThreads(final boolean shareWorkerThreads) {
        this.shareWorkerThreads = shareWorkerThreads;
        return this;
    }

    /**
     * Returns the acceptor thread count.
     *
     * @return the acceptor thread count
     */
    public int getAcceptorThreadCount() {
        return acceptorThreadCount;
    }

    /**
     * Sets the acceptor thread count.
     *
     * @param acceptorThreadCount the acceptor thread count.
     * @return the reference to this {@link NettyRuntimeConfig} instance
     * @throws ConfigException if the specified thread count is invalid
     */
    @BuilderMethod
    public NettyRuntimeConfig setAcceptorThreadCount(final int acceptorThreadCount) {
        this.acceptorThreadCount = ensureValid(acceptorThreadCount);
        return this;
    }

    /**
     * Returns the acceptor thread category name.
     *
     * @return the acceptor thread category name
     */
    public String getAcceptorThreadNameCategory() {
        return acceptorThreadNameCategory;
    }

    /**
     * Sets the acceptor thread category name.
     *
     * @param acceptorThreadNameCategory the acceptor thread category name
     * @return the reference to this {@link NettyRuntimeConfig} instance
     */
    @BuilderMethod
    public NettyRuntimeConfig setAcceptorThreadNameCategory(final String acceptorThreadNameCategory) {
        this.acceptorThreadNameCategory = ensureValid(acceptorThreadNameCategory);
        return this;
    }

    /**
     * Retruns the acceptor thread priority.
     *
     * @return the acceptor thread priority.
     */
    public int getAcceptorThreadPriority() {
        return acceptorThreadPriority;
    }

    /**
     * Sets the acceptor thread priority.
     *
     * @param acceptorThreadPriority the acceptor thread priority.
     * @return the reference to this {@link NettyRuntimeConfig} instance
     * @throws ConfigException if the specified thread priority is invalid.
     */
    @BuilderMethod
    public NettyRuntimeConfig setAcceptorThreadPriority(final int acceptorThreadPriority) {
        this.acceptorThreadPriority = ensureValid(acceptorThreadPriority);
        return this;
    }

    /**
     * Returns {@code true} if worker threads must be daemons.
     *
     * @return {@code true} if worker threads must be daemons.
     */
    public boolean isWorkerThreadDaemon() {
        return workerThreadDaemon;
    }

    /**
     * Enables or disables daemon flag for all worker threads.
     *
     * @param workerThreadDaemon daemon flag for all worker threads.
     * @return the reference to this {@link NettyRuntimeConfig} instance
     */
    @BuilderMethod
    public NettyRuntimeConfig setWorkerThreadDaemon(final boolean workerThreadDaemon) {
        this.workerThreadDaemon = workerThreadDaemon;
        return this;
    }

    /**
     * Returns the worker thread count.
     *
     * @return the worker thread count.
     */
    public int getWorkerThreadCount() {
        return workerThreadCount;
    }

    /**
     * Sets the worker thread count.
     *
     * @param workerThreadCount the worker thread count.
     * @return the reference to this {@link NettyRuntimeConfig} instance
     * @throws ConfigException if the specified thread count is invalid
     */
    @BuilderMethod
    public NettyRuntimeConfig setWorkerThreadCount(final int workerThreadCount) {
        this.workerThreadCount = ensureValid(workerThreadCount);
        return this;
    }

    /**
     * Returns the worker thread category name.
     *
     * @return the worker thread category name.
     */
    public String getWorkerThreadNameCategory() {
        return workerThreadNameCategory;
    }

    /**
     * Sets the worker thread category name.
     *
     * @param workerThreadNameCategory the worker thread category name.
     * @return the reference to this {@link NettyRuntimeConfig} instance
     */
    @BuilderMethod
    public NettyRuntimeConfig setWorkerThreadNameCategory(final String workerThreadNameCategory) {
        this.workerThreadNameCategory = ensureValid(workerThreadNameCategory);
        return this;
    }

    /**
     * Returns the worker thread priority.
     *
     * @return the worker thread priority.
     */
    public int getWorkerThreadPriority() {
        return workerThreadPriority;
    }

    /**
     * Sets the worker thread priority.
     *
     * @param workerThreadPriority the worker thread priority.
     * @return the reference to this {@link NettyRuntimeConfig} instance
     * @throws ConfigException if the specified thread priority is invalid.
     */
    @BuilderMethod
    public NettyRuntimeConfig setWorkerThreadPriority(final int workerThreadPriority) {
        this.workerThreadPriority = ensureValid(workerThreadPriority);
        return this;
    }

    /**
     * Returns the channel id type.
     *
     * @return the channel id type
     */
    public NettyChannelIdType getChannelIdType() {
        return channelIdType;
    }

    /**
     * Sets the channel id type.
     *
     * @param channelIdType {@link NettyChannelIdType} which must be used
     * @return the reference to this {@link NettyRuntimeConfig} instance
     */
    @BuilderMethod
    public NettyRuntimeConfig setChannelIdType(final NettyChannelIdType channelIdType) {
        this.channelIdType = ensureValid(channelIdType);
        return this;
    }

    @Override
    public String toString() {
        return "NettyRuntimeConfig{" +
                "shareWorkerThreads=" + shareWorkerThreads +
                ", acceptorThreadCount=" + acceptorThreadCount +
                ", acceptorThreadNameCategory='" + acceptorThreadNameCategory + '\'' +
                ", acceptorThreadPriority=" + acceptorThreadPriority +
                ", workerThreadDaemon=" + workerThreadDaemon +
                ", workerThreadCount=" + workerThreadCount +
                ", workerThreadNameCategory='" + workerThreadNameCategory + '\'' +
                ", workerThreadPriority=" + workerThreadPriority +
                ", channelIdType=" + channelIdType +
                '}';
    }
}
