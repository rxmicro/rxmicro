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

package io.rxmicro.logger.internal.jul.config.adapter.pattern;

import java.util.Arrays;
import java.util.Optional;

/**
 * @author nedis
 * @since 0.7
 */
public enum ConversionSpecifier {

    /**
     * Outputs the name of the logger at the origin of the logging event.
     */
    LOGGER_NAME_1("c"),

    /**
     * Outputs the name of the logger at the origin of the logging event.
     */
    LOGGER_NAME_2("lo"),

    /**
     * Outputs the name of the logger at the origin of the logging event.
     */
    LOGGER_NAME_3("logger"),

    /**
     * Outputs the fully-qualified class name of the caller issuing the logging request.
     *
     * <p>
     * Generating the caller class information is not particularly fast.
     * Thus, its use should be avoided unless execution speed is not an issue.
     */
    FULL_QUALIFIED_CLASS_NAME_1("C"),

    /**
     * Outputs the fully-qualified class name of the caller issuing the logging request.
     *
     * <p>
     * Generating the caller class information is not particularly fast.
     * Thus, its use should be avoided unless execution speed is not an issue.
     */
    FULL_QUALIFIED_CLASS_NAME_2("class"),

    /**
     * Outputs the date of the logging event.
     */
    DATE_OF_LOGGING_EVENT_1("d"),

    /**
     * Outputs the date of the logging event.
     */
    DATE_OF_LOGGING_EVENT_2("date"),

    /**
     * Outputs the file name of the Java source file where the logging request was issued.
     */
    FILE_NAME_1("F"),

    /**
     * Outputs the file name of the Java source file where the logging request was issued.
     */
    FILE_NAME_2("file"),

    /**
     * Outputs the line number from where the logging request was issued.
     */
    LINE_NUMBER_1("L"),

    /**
     * Outputs the line number from where the logging request was issued.
     */
    LINE_NUMBER_2("line"),

    /**
     * Outputs the application-supplied message associated with the logging event.
     */
    LOGGING_MESSAGE_1("m"),

    /**
     * Outputs the application-supplied message associated with the logging event.
     */
    LOGGING_MESSAGE_2("mes"),

    /**
     * Outputs the application-supplied message associated with the logging event.
     */
    LOGGING_MESSAGE_3("message"),

    /**
     * Outputs the method name where the logging request was issued.
     *
     * <p>
     * Generating the method information is not particularly fast.
     * Thus, its use should be avoided unless execution speed is not an issue.
     */
    METHOD_NAME_1("M"),

    /**
     * Outputs the method name where the logging request was issued.
     *
     * <p>
     * Generating the method information is not particularly fast.
     * Thus, its use should be avoided unless execution speed is not an issue.
     */
    METHOD_NAME_2("method"),

    /**
     * Outputs the platform dependent line separator character or characters.
     */
    PLATFORM_DEPENDENT_LINE_SEPARATOR("n"),

    /**
     * Outputs the level of the logging event.
     */
    LOGGING_LEVEL_1("p"),

    /**
     * Outputs the level of the logging event.
     */
    LOGGING_LEVEL_2("le"),

    /**
     * Outputs the level of the logging event.
     */
    LOGGING_LEVEL_3("level"),

    /**
     * Outputs the number of milliseconds elapsed since the start of the application until the creation of the logging event.
     */
    RELATIVE_TIME_1("r"),

    /**
     * Outputs the number of milliseconds elapsed since the start of the application until the creation of the logging event.
     */
    RELATIVE_TIME_2("relative"),

    /**
     * Outputs the name of the thread that generated the logging event.
     */
    THREAD_NAME_1("t"),

    /**
     * Outputs the name of the thread that generated the logging event.
     */
    THREAD_NAME_2("thread"),

    /**
     * Outputs the request id if specified
     */
    REQUEST_ID_1("id"),

    /**
     * Outputs the request id if specified
     */
    REQUEST_ID_2("rid"),

    /**
     * Outputs the request id if specified
     */
    REQUEST_ID_3("request-id"),

    /**
     * Outputs the request id if specified
     */
    REQUEST_ID_4("request_id"),

    /**
     * Outputs the request id if specified
     */
    REQUEST_ID_5("requestId");

    private final String specifier;

    public static Optional<ConversionSpecifier> ofConversionSpecifier(final String specifier) {
        return Arrays.stream(ConversionSpecifier.values())
                .filter(conversionSpecifier -> conversionSpecifier.getSpecifier().equals(specifier))
                .findFirst();
    }

    ConversionSpecifier(final String specifier) {
        this.specifier = specifier;
    }

    public String getSpecifier() {
        return specifier;
    }

    @Override
    public String toString() {
        return "%" + specifier;
    }
}
