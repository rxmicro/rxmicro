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

package io.rxmicro.rest.server.feature.request.id.generator;

import io.rxmicro.rest.server.RestServerConfig;
import io.rxmicro.rest.server.feature.RequestIdGenerator;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static io.rxmicro.rest.server.PredefinedRequestIdGeneratorProvider.DETERMINISTIC_96_BITS;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author nedis
 * @since 0.7.3
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
final class Deterministic96BitsRequestIdGeneratorMultiThreadTest {

    private static final int THREAD_COUNT = 10;

    private static final int REQUEST_ID_COUNT = 10_000;

    private final RequestIdGenerator requestIdGenerator = DETERMINISTIC_96_BITS.getRequestIdGenerator(new RestServerConfig());

    @Test
    void Should_generate_unique_request_id() throws ExecutionException, InterruptedException {
        final ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        try {
            final List<Future<List<String>>> futures = new ArrayList<>(THREAD_COUNT);
            for (int i = 0; i < THREAD_COUNT; i++) {
                futures.add(executorService.submit(() -> {
                    final List<String> result = new ArrayList<>(REQUEST_ID_COUNT);
                    for (int j = 0; j < REQUEST_ID_COUNT; j++) {
                        result.add(requestIdGenerator.getNextId());
                    }
                    return result;
                }));
            }
            final Set<String> total = new HashSet<>();
            for (final Future<List<String>> future : futures) {
                assertEquals(REQUEST_ID_COUNT, new HashSet<>(future.get()).size());
                total.addAll(future.get());
            }
            assertEquals(THREAD_COUNT * REQUEST_ID_COUNT, total.size());
        } finally {
            executorService.shutdownNow();
        }
    }
}
