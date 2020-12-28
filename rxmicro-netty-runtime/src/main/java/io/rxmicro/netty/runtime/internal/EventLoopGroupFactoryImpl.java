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

import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.kqueue.KQueueEventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.EventExecutorGroup;
import io.netty.util.concurrent.Future;
import io.rxmicro.netty.runtime.local.EventLoopGroupFactory;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.stream.Collectors.toList;

/**
 * @author nedis
 * @since 0.8
 */
public final class EventLoopGroupFactoryImpl extends EventLoopGroupFactory {

    private static final Map<String, Integer> REGISTERED_STATIC_THREAD_NAMES = new ConcurrentHashMap<>();

    private static final int DEFAULT_PERIOD_IN_MILLIS = 50;

    private final EventLoopGroup sharedAcceptorEventLoopGroup;

    private final EventLoopGroup sharedWorkerEventLoopGroup;

    private final List<EventLoopGroup> createdNotDaemonEventLoopGroups = new CopyOnWriteArrayList<>();

    private final List<EventLoopGroup> allEventLoopGroups = new CopyOnWriteArrayList<>();

    public EventLoopGroupFactoryImpl() {
        if (nettyRuntimeConfig.isShareWorkerThreads()) {
            sharedAcceptorEventLoopGroup = createSharedAcceptorEventLoopGroup();
            sharedWorkerEventLoopGroup = createSharedWorkerEventLoopGroup();
        } else {
            sharedAcceptorEventLoopGroup = null;
            sharedWorkerEventLoopGroup = null;
        }
    }

    @Override
    public Optional<EventLoopGroup> getSharedAcceptorEventLoopGroup() {
        return Optional.ofNullable(sharedAcceptorEventLoopGroup);
    }

    @Override
    public Optional<EventLoopGroup> getSharedWorkerEventLoopGroup() {
        return Optional.ofNullable(sharedWorkerEventLoopGroup);
    }

    @Override
    public List<Future<?>> shutdownGracefully() {
        return createdNotDaemonEventLoopGroups.stream().map(EventExecutorGroup::shutdownGracefully).collect(toList());
    }

    @Override
    protected void shutdownAll() {
        REGISTERED_STATIC_THREAD_NAMES.clear();
        for (final EventLoopGroup eventLoopGroup : allEventLoopGroups) {
            if (!eventLoopGroup.isShutdown()) {
                eventLoopGroup.shutdownGracefully(DEFAULT_PERIOD_IN_MILLIS, DEFAULT_PERIOD_IN_MILLIS, MILLISECONDS).awaitUninterruptibly();
            }
        }
    }

    @Override
    protected EventLoopGroup createSharedAcceptorEventLoopGroup() {
        return newEventLoopGroup(
                new EventLoopThreadFactory.Builder()
                        .setThreadCount(nettyRuntimeConfig.getAcceptorThreadCount())
                        .setThreadNameCategory(nettyRuntimeConfig.getAcceptorThreadNameCategory())
                        .setThreadPriority(nettyRuntimeConfig.getAcceptorThreadPriority())
                        .build(REGISTERED_STATIC_THREAD_NAMES)
        );
    }

    @Override
    protected EventLoopGroup createWorkerEventLoopGroup(final String threadNameQualifier) {
        return newEventLoopGroup(
                createNewWorkerEventLoopThreadFactoryBuilder()
                        .setThreadNameQualifier(threadNameQualifier)
                        .build(REGISTERED_STATIC_THREAD_NAMES)
        );
    }

    private EventLoopGroup createSharedWorkerEventLoopGroup() {
        return newEventLoopGroup(
                createNewWorkerEventLoopThreadFactoryBuilder()
                        .build(REGISTERED_STATIC_THREAD_NAMES)
        );
    }

    private EventLoopThreadFactory.Builder createNewWorkerEventLoopThreadFactoryBuilder() {
        return new EventLoopThreadFactory.Builder()
                .setDaemon(nettyRuntimeConfig.isWorkerThreadDaemon())
                .setThreadCount(nettyRuntimeConfig.getWorkerThreadCount())
                .setThreadNameCategory(nettyRuntimeConfig.getWorkerThreadNameCategory())
                .setThreadPriority(nettyRuntimeConfig.getWorkerThreadPriority());
    }

    private EventLoopGroup newEventLoopGroup(final EventLoopThreadFactory eventLoopThreadFactory) {
        final EventLoopGroup eventLoopGroup;
        if (nettyTransport == NettyTransport.EPOLL) {
            eventLoopGroup = new EpollEventLoopGroup(eventLoopThreadFactory.getThreadCount(), eventLoopThreadFactory);
        } else if (nettyTransport == NettyTransport.KQUEUE) {
            eventLoopGroup = new KQueueEventLoopGroup(eventLoopThreadFactory.getThreadCount(), eventLoopThreadFactory);
        } else {
            eventLoopGroup = new NioEventLoopGroup(eventLoopThreadFactory.getThreadCount(), eventLoopThreadFactory);
        }
        if (!eventLoopThreadFactory.isDaemon()) {
            createdNotDaemonEventLoopGroups.add(eventLoopGroup);
        }
        allEventLoopGroups.add(eventLoopGroup);
        return eventLoopGroup;
    }
}
