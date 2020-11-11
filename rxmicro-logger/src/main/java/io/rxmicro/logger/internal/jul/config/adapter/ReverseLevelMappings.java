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

import io.rxmicro.logger.Level;

import java.util.Map;

/**
 * @author nedis
 * @since 0.7
 */
public final class ReverseLevelMappings {

    private static final Map<java.util.logging.Level, Level> REVERSE_LEVEL_MAPPING = Map.of(
            java.util.logging.Level.OFF, Level.OFF,
            java.util.logging.Level.SEVERE, Level.ERROR,
            java.util.logging.Level.WARNING, Level.WARN,
            java.util.logging.Level.CONFIG, Level.INFO,
            java.util.logging.Level.INFO, Level.INFO,
            java.util.logging.Level.FINE, Level.DEBUG,
            java.util.logging.Level.FINER, Level.DEBUG,
            java.util.logging.Level.FINEST, Level.TRACE,
            java.util.logging.Level.ALL, Level.ALL
    );

    public static Level getRxMicroLevel(final java.util.logging.Level level) {
        return REVERSE_LEVEL_MAPPING.get(level);
    }

    private ReverseLevelMappings() {

    }
}
