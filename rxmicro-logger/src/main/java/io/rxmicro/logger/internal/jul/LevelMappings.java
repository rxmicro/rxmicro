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

package io.rxmicro.logger.internal.jul;

import io.rxmicro.logger.Level;

import java.util.Arrays;
import java.util.Locale;
import java.util.Map;

import static java.util.Locale.ENGLISH;
import static java.util.Map.entry;
import static java.util.stream.Collectors.toUnmodifiableMap;

/**
 * @author nedis
 * @since 0.1
 */
final class LevelMappings {

    private static final java.util.logging.Level[] JUL_LEVELS = {
            /* Level.OFF   -> */ java.util.logging.Level.OFF,
            /* Level.ERROR -> */ java.util.logging.Level.SEVERE,
            /* Level.WARN  -> */ java.util.logging.Level.WARNING,
            /* Level.INFO  -> */ java.util.logging.Level.INFO,
            /* Level.DEBUG -> */ java.util.logging.Level.FINE,
            /* Level.TRACE -> */ java.util.logging.Level.FINEST,
            /* Level.ALL   -> */ java.util.logging.Level.ALL
    };

    private static final Map<String, String> LEVEL_NAME_MAPPING =
            Arrays.stream(Level.values())
                    .map(level -> entry(level.name(), getJulLevel(level).getName()))
                    .collect(toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue));

    public static String fixLevelValue(final String value) {
        return LEVEL_NAME_MAPPING.getOrDefault(value.toUpperCase(ENGLISH), value);
    }

    public static java.util.logging.Level getJulLevel(final Level level) {
        return JUL_LEVELS[level.ordinal()];
    }

    private LevelMappings() {
    }
}
