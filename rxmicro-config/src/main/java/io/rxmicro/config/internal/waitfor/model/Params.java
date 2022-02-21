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

package io.rxmicro.config.internal.waitfor.model;

import java.time.Duration;

/**
 * @author nedis
 * @since 0.3
 */
public final class Params {

    private final String type;

    private final Duration timeout;

    private final String destination;

    public Params(final String type,
                  final Duration timeout,
                  final String destination) {
        this.type = type;
        this.timeout = timeout;
        this.destination = destination;
    }

    public String getType() {
        return type;
    }

    public Duration getTimeout() {
        return timeout;
    }

    public String getDestination() {
        return destination;
    }

    @Override
    public String toString() {
        return "Params{" + "type='" + type + '\'' +
                ", timeout=" + timeout +
                ", destination='" + destination + '\'' +
                '}';
    }
}
