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

package io.rxmicro.config.internal.waitfor.impl;

import io.rxmicro.config.internal.waitfor.WaitForService;

import java.util.List;

import static io.rxmicro.common.util.ExCollections.unmodifiableList;

/**
 * @author nedis
 * @since 0.10
 */
public final class CompositeWaitForService implements WaitForService {

    private final List<WaitForService> waitForServices;

    public CompositeWaitForService(final List<WaitForService> waitForServices) {
        if (waitForServices.size() < 2) {
            throw new IllegalArgumentException("At least two waitForServices must be configured!");
        }
        this.waitForServices = unmodifiableList(waitForServices);
    }

    @Override
    public void start() {
        for (final WaitForService waitForService : waitForServices) {
            waitForService.start();
        }
    }
}
