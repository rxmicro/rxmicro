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

import io.netty.util.concurrent.Future;
import io.rxmicro.netty.runtime.local.EventLoopGroupFactory;

import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

import static io.rxmicro.netty.runtime.local.EventLoopGroupFactory.getEventLoopGroupFactory;
import static java.util.function.Function.identity;

/**
 * Event loop group util class.
 *
 * @author nedis
 * @since 0.8
 */
public final class EventLoopGroups {

    static {
        EventLoopGroupFactory.init();
    }

    /**
     * Returns the shared worker event loop group if enabled.
     *
     * <p>
     * This method allows using shared thread pool for async tasks.
     * For example:
     * <ul>
     *     <li>{@link java.util.concurrent.CompletionStage#thenAcceptAsync(Consumer, Executor)}; </li>
     *     <li>{@link java.util.concurrent.CompletionStage#thenApplyAsync(Function, Executor)};  </li>
     *     <li>{@link java.util.concurrent.CompletionStage#thenCombineAsync(CompletionStage, BiFunction, Executor)}; </li>
     *     <li>{@link java.util.concurrent.CompletionStage#thenComposeAsync(Function, Executor)} ;</li>
     *     <li>{@link java.util.concurrent.CompletionStage#applyToEitherAsync(CompletionStage, Function, Executor)}; </li>
     *     <li>{@link java.util.concurrent.CompletionStage#acceptEitherAsync(CompletionStage, Consumer, Executor)}; </li>
     *     <li>{@link java.util.concurrent.CompletionStage#handleAsync(BiFunction, Executor)}; </li>
     *     <li>{@link java.util.concurrent.CompletionStage#runAfterBothAsync(CompletionStage, Runnable, Executor)}; </li>
     *     <li>{@link java.util.concurrent.CompletionStage#runAfterEitherAsync(CompletionStage, Runnable, Executor)}; </li>
     *     <li>{@link java.util.concurrent.CompletionStage#thenAcceptAsync(Consumer, Executor)}; </li>
     * </ul>
     *
     * @return the shared worker event loop group if enabled.
     */
    public static Optional<Executor> getSharedExecutor() {
        return getEventLoopGroupFactory().getSharedWorkerEventLoopGroup().map(identity());
    }

    /**
     * Shuts down all created event loop groups.
     *
     * <p>
     * This method is useful to shut down the client event loops if worker threads are not daemon!
     *
     * <p>
     * Note that this method shuts down the rest netty server as well!
     */
    public static void shutdownAllGracefully() {
        getEventLoopGroupFactory().shutdownGracefully().forEach(Future::awaitUninterruptibly);
    }

    private EventLoopGroups() {
    }
}
