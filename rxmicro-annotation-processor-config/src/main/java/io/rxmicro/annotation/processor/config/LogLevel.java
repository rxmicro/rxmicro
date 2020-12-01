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

package io.rxmicro.annotation.processor.config;

/**
 * The {@code RxMicro Annotation Processor} Logger Level.
 *
 * @author nedis
 * @since 0.7.2
 */
public enum LogLevel {

    /**
     * Enable info, debug and trace messages.
     *
     * <p>
     * Current version of the {@code RxMicro Annotation Processor} does not show any trace messages.
     * It will be implemented later.
     */
    TRACE,

    /**
     * Enable info and debug messages.
     */
    DEBUG,

    /**
     * Enable info messages only.
     */
    INFO,

    /**
     * Disable all info messages.
     *
     * <p>
     * Warnings and errors will be shown always!
     */
    OFF;

    /**
     * Returns {@code true} if current log level allows showing a message with the expected log level.
     *
     * @param expectedLogLevel the expected log level.
     * @return {@code true} if current log level allows showing a message with the expected log level.
     */
    public boolean isEnabled(final LogLevel expectedLogLevel) {
        if (this == OFF) {
            return false;
        } else {
            return ordinal() <= expectedLogLevel.ordinal();
        }
    }
}
