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

package io.rxmicro.logger;

import java.util.logging.LogRecord;

/**
 * Represents a logger event with custom data that can be logged.
 *
 * @author nedis
 * @see LoggerFactory#newLoggerEventBuilder()
 * @see LoggerEventBuilder
 * @see Logger#trace(LoggerEvent)
 * @see Logger#debug(LoggerEvent)
 * @see Logger#info(LoggerEvent)
 * @see Logger#warn(LoggerEvent)
 * @see Logger#error(LoggerEvent)
 * @since 0.8
 */
public interface LoggerEvent {

    /**
     * Returns the logger record that associated with this event.
     *
     * @return the logger record that associated with this event
     * @throws IllegalArgumentException if current logger event contains a message template with redundant placeholders or missing arguments
     */
    LogRecord getLogRecord();
}
