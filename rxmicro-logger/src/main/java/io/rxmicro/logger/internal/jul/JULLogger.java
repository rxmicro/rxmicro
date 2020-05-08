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
import io.rxmicro.logger.impl.AbstractLogger;

import static io.rxmicro.logger.internal.jul.LevelMappings.LEVEL_MAPPING;

/**
 * Read more:
 * https://docs.oracle.com/en/java/javase/11/docs/api/java.logging/java/util/logging/package-summary.html
 *
 * @author nedis
 * @since 0.1
 */
final class JULLogger extends AbstractLogger {

    private final java.util.logging.Logger logger;

    JULLogger(final String name) {
        logger = java.util.logging.Logger.getLogger(name);
    }

    @Override
    protected boolean isLevelEnabled(final Level level) {
        return logger.isLoggable(LEVEL_MAPPING.get(level));
    }

    @Override
    protected void log(final Level level,
                       final String message) {
        logger.log(LEVEL_MAPPING.get(level), message);
    }

    @Override
    protected void log(final Level level,
                       final String message,
                       final Throwable throwable) {
        logger.log(LEVEL_MAPPING.get(level), message, throwable);
    }
}
