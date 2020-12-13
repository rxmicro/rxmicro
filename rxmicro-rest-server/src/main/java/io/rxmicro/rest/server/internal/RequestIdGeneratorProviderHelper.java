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

package io.rxmicro.rest.server.internal;

import io.rxmicro.logger.Logger;
import io.rxmicro.rest.server.RequestIdGeneratorProvider;
import io.rxmicro.rest.server.RestServerConfig;
import io.rxmicro.rest.server.feature.RequestIdGenerator;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;
import java.util.function.Supplier;

import static java.util.concurrent.Executors.newSingleThreadExecutor;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * @author nedis
 * @since 0.7.3
 */
public final class RequestIdGeneratorProviderHelper {

    public static RequestIdGenerator getRequestIdGenerator(final Logger logger,
                                                           final Supplier<RequestIdGenerator> preferredRequestIdGeneratorSupplier,
                                                           final Supplier<RequestIdGenerator> fallbackRequestIdGeneratorSupplier,
                                                           final RestServerConfig restServerConfig) {
        final ExecutorService executorService = newSingleThreadExecutor(r -> {
            final Thread thread = new Thread(r, "RequestIdGenerator-Verifier");
            thread.setDaemon(true);
            return thread;
        });
        final RequestIdGenerator preferredRequestIdGenerator = preferredRequestIdGeneratorSupplier.get();
        try {
            final Future<RequestIdGenerator> future = executorService.submit(() -> {
                // Verify that 'secureRandom.nextBytes(bytes)' is not blocked
                preferredRequestIdGenerator.getNextId();
                return preferredRequestIdGenerator;
            });
            return future.get(restServerConfig.getWaitingForRequestIdGeneratorInitTimeoutInMillis(), MILLISECONDS);
        } catch (final InterruptedException | ExecutionException | TimeoutException | CancellationException ignore) {
            if (fallbackRequestIdGeneratorSupplier != null) {
                final RequestIdGenerator fallbackRequestIdGenerator = fallbackRequestIdGeneratorSupplier.get();
                logger.warn(
                        "'?' request generator is blocked by operation system, so '?' one will be used instead!",
                        preferredRequestIdGenerator.getClass().getName(),
                        fallbackRequestIdGenerator.getClass().getName()
                );
                return fallbackRequestIdGenerator;
            } else {
                throw new RequestIdGeneratorProvider.CurrentRequestIdGeneratorCantBeUsedException(preferredRequestIdGenerator);
            }
        } finally {
            executorService.shutdownNow();
        }
    }

    private RequestIdGeneratorProviderHelper() {
    }
}
