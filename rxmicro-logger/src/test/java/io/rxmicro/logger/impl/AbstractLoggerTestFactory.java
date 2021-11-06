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

package io.rxmicro.logger.impl;

import io.rxmicro.logger.Logger;
import io.rxmicro.logger.LoggerEvent;
import io.rxmicro.logger.RequestIdSupplier;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.logging.LogRecord;
import java.util.stream.Stream;

/**
 * @author nedis
 * @since 0.7.3
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
final class AbstractLoggerTestFactory {

    static final Throwable THROWABLE = new Throwable("test");

    static final RequestIdSupplier REQUEST_ID_SUPPLIER = () -> "TestRequestId";

    static final LoggerEvent LOGGER_EVENT = new LoggerEvent() {
        @Override
        public LogRecord getLogRecord() {
            return null;
        }
    };

    static Stream<Consumer<Logger>> createLoggerEventStream() {
        return Stream.of(
                logger -> logger.trace(LOGGER_EVENT),
                logger -> logger.debug(LOGGER_EVENT),
                logger -> logger.info(LOGGER_EVENT),
                logger -> logger.warn(LOGGER_EVENT),
                logger -> logger.error(LOGGER_EVENT)
        );
    }

    static Stream<Consumer<Logger>> createMessageOnlyStream() {
        return Stream.of(
                logger -> logger.trace("123456"),
                logger -> logger.trace("?23456", "1"),
                logger -> logger.trace("??3456", "1", "2"),
                logger -> logger.trace("???456", "1", "2", "3"),
                logger -> logger.trace("????56", "1", "2", "3", "4"),
                logger -> logger.trace("?????6", "1", "2", "3", "4", "5"),
                logger -> logger.trace("??????", "1", "2", "3", "4", "5", "6"),
                logger -> logger.trace("?23456", () -> "1"),
                logger -> logger.trace("??3456", () -> "1", () -> "2"),
                logger -> logger.trace("???456", () -> "1", () -> "2", () -> "3"),
                logger -> logger.trace("????56", () -> "1", () -> "2", () -> "3", () -> "4"),
                logger -> logger.trace("?????6", () -> "1", () -> "2", () -> "3", () -> "4", () -> "5"),
                logger -> logger.trace("??????", () -> "1", () -> "2", () -> "3", () -> "4", () -> "5", () -> "6"),

                logger -> logger.debug("123456"),
                logger -> logger.debug("?23456", "1"),
                logger -> logger.debug("??3456", "1", "2"),
                logger -> logger.debug("???456", "1", "2", "3"),
                logger -> logger.debug("????56", "1", "2", "3", "4"),
                logger -> logger.debug("?????6", "1", "2", "3", "4", "5"),
                logger -> logger.debug("??????", "1", "2", "3", "4", "5", "6"),
                logger -> logger.debug("?23456", () -> "1"),
                logger -> logger.debug("??3456", () -> "1", () -> "2"),
                logger -> logger.debug("???456", () -> "1", () -> "2", () -> "3"),
                logger -> logger.debug("????56", () -> "1", () -> "2", () -> "3", () -> "4"),
                logger -> logger.debug("?????6", () -> "1", () -> "2", () -> "3", () -> "4", () -> "5"),
                logger -> logger.debug("??????", () -> "1", () -> "2", () -> "3", () -> "4", () -> "5", () -> "6"),

                logger -> logger.info("123456"),
                logger -> logger.info("?23456", "1"),
                logger -> logger.info("??3456", "1", "2"),
                logger -> logger.info("???456", "1", "2", "3"),
                logger -> logger.info("????56", "1", "2", "3", "4"),
                logger -> logger.info("?????6", "1", "2", "3", "4", "5"),
                logger -> logger.info("??????", "1", "2", "3", "4", "5", "6"),
                logger -> logger.info("?23456", () -> "1"),
                logger -> logger.info("??3456", () -> "1", () -> "2"),
                logger -> logger.info("???456", () -> "1", () -> "2", () -> "3"),
                logger -> logger.info("????56", () -> "1", () -> "2", () -> "3", () -> "4"),
                logger -> logger.info("?????6", () -> "1", () -> "2", () -> "3", () -> "4", () -> "5"),
                logger -> logger.info("??????", () -> "1", () -> "2", () -> "3", () -> "4", () -> "5", () -> "6"),

                logger -> logger.warn("123456"),
                logger -> logger.warn("?23456", "1"),
                logger -> logger.warn("??3456", "1", "2"),
                logger -> logger.warn("???456", "1", "2", "3"),
                logger -> logger.warn("????56", "1", "2", "3", "4"),
                logger -> logger.warn("?????6", "1", "2", "3", "4", "5"),
                logger -> logger.warn("??????", "1", "2", "3", "4", "5", "6"),
                logger -> logger.warn("?23456", () -> "1"),
                logger -> logger.warn("??3456", () -> "1", () -> "2"),
                logger -> logger.warn("???456", () -> "1", () -> "2", () -> "3"),
                logger -> logger.warn("????56", () -> "1", () -> "2", () -> "3", () -> "4"),
                logger -> logger.warn("?????6", () -> "1", () -> "2", () -> "3", () -> "4", () -> "5"),
                logger -> logger.warn("??????", () -> "1", () -> "2", () -> "3", () -> "4", () -> "5", () -> "6"),

                logger -> logger.error("123456"),
                logger -> logger.error("?23456", "1"),
                logger -> logger.error("??3456", "1", "2"),
                logger -> logger.error("???456", "1", "2", "3"),
                logger -> logger.error("????56", "1", "2", "3", "4"),
                logger -> logger.error("?????6", "1", "2", "3", "4", "5"),
                logger -> logger.error("??????", "1", "2", "3", "4", "5", "6"),
                logger -> logger.error("?23456", () -> "1"),
                logger -> logger.error("??3456", () -> "1", () -> "2"),
                logger -> logger.error("???456", () -> "1", () -> "2", () -> "3"),
                logger -> logger.error("????56", () -> "1", () -> "2", () -> "3", () -> "4"),
                logger -> logger.error("?????6", () -> "1", () -> "2", () -> "3", () -> "4", () -> "5"),
                logger -> logger.error("??????", () -> "1", () -> "2", () -> "3", () -> "4", () -> "5", () -> "6")
        );
    }

    static Stream<Consumer<Logger>> createThrowableWithMessageStream() {
        return Stream.of(
                logger -> logger.trace(THROWABLE, "123456"),
                logger -> logger.trace(THROWABLE, "?23456", "1"),
                logger -> logger.trace(THROWABLE, "??3456", "1", "2"),
                logger -> logger.trace(THROWABLE, "???456", "1", "2", "3"),
                logger -> logger.trace(THROWABLE, "????56", "1", "2", "3", "4"),
                logger -> logger.trace(THROWABLE, "?????6", "1", "2", "3", "4", "5"),
                logger -> logger.trace(THROWABLE, "??????", "1", "2", "3", "4", "5", "6"),
                logger -> logger.trace(THROWABLE, "?23456", () -> "1"),
                logger -> logger.trace(THROWABLE, "??3456", () -> "1", () -> "2"),
                logger -> logger.trace(THROWABLE, "???456", () -> "1", () -> "2", () -> "3"),
                logger -> logger.trace(THROWABLE, "????56", () -> "1", () -> "2", () -> "3", () -> "4"),
                logger -> logger.trace(THROWABLE, "?????6", () -> "1", () -> "2", () -> "3", () -> "4", () -> "5"),
                logger -> logger.trace(THROWABLE, "??????", () -> "1", () -> "2", () -> "3", () -> "4", () -> "5", () -> "6"),

                logger -> logger.debug(THROWABLE, "123456"),
                logger -> logger.debug(THROWABLE, "?23456", "1"),
                logger -> logger.debug(THROWABLE, "??3456", "1", "2"),
                logger -> logger.debug(THROWABLE, "???456", "1", "2", "3"),
                logger -> logger.debug(THROWABLE, "????56", "1", "2", "3", "4"),
                logger -> logger.debug(THROWABLE, "?????6", "1", "2", "3", "4", "5"),
                logger -> logger.debug(THROWABLE, "??????", "1", "2", "3", "4", "5", "6"),
                logger -> logger.debug(THROWABLE, "?23456", () -> "1"),
                logger -> logger.debug(THROWABLE, "??3456", () -> "1", () -> "2"),
                logger -> logger.debug(THROWABLE, "???456", () -> "1", () -> "2", () -> "3"),
                logger -> logger.debug(THROWABLE, "????56", () -> "1", () -> "2", () -> "3", () -> "4"),
                logger -> logger.debug(THROWABLE, "?????6", () -> "1", () -> "2", () -> "3", () -> "4", () -> "5"),
                logger -> logger.debug(THROWABLE, "??????", () -> "1", () -> "2", () -> "3", () -> "4", () -> "5", () -> "6"),

                logger -> logger.info(THROWABLE, "123456"),
                logger -> logger.info(THROWABLE, "?23456", "1"),
                logger -> logger.info(THROWABLE, "??3456", "1", "2"),
                logger -> logger.info(THROWABLE, "???456", "1", "2", "3"),
                logger -> logger.info(THROWABLE, "????56", "1", "2", "3", "4"),
                logger -> logger.info(THROWABLE, "?????6", "1", "2", "3", "4", "5"),
                logger -> logger.info(THROWABLE, "??????", "1", "2", "3", "4", "5", "6"),
                logger -> logger.info(THROWABLE, "?23456", () -> "1"),
                logger -> logger.info(THROWABLE, "??3456", () -> "1", () -> "2"),
                logger -> logger.info(THROWABLE, "???456", () -> "1", () -> "2", () -> "3"),
                logger -> logger.info(THROWABLE, "????56", () -> "1", () -> "2", () -> "3", () -> "4"),
                logger -> logger.info(THROWABLE, "?????6", () -> "1", () -> "2", () -> "3", () -> "4", () -> "5"),
                logger -> logger.info(THROWABLE, "??????", () -> "1", () -> "2", () -> "3", () -> "4", () -> "5", () -> "6"),

                logger -> logger.warn(THROWABLE, "123456"),
                logger -> logger.warn(THROWABLE, "?23456", "1"),
                logger -> logger.warn(THROWABLE, "??3456", "1", "2"),
                logger -> logger.warn(THROWABLE, "???456", "1", "2", "3"),
                logger -> logger.warn(THROWABLE, "????56", "1", "2", "3", "4"),
                logger -> logger.warn(THROWABLE, "?????6", "1", "2", "3", "4", "5"),
                logger -> logger.warn(THROWABLE, "??????", "1", "2", "3", "4", "5", "6"),
                logger -> logger.warn(THROWABLE, "?23456", () -> "1"),
                logger -> logger.warn(THROWABLE, "??3456", () -> "1", () -> "2"),
                logger -> logger.warn(THROWABLE, "???456", () -> "1", () -> "2", () -> "3"),
                logger -> logger.warn(THROWABLE, "????56", () -> "1", () -> "2", () -> "3", () -> "4"),
                logger -> logger.warn(THROWABLE, "?????6", () -> "1", () -> "2", () -> "3", () -> "4", () -> "5"),
                logger -> logger.warn(THROWABLE, "??????", () -> "1", () -> "2", () -> "3", () -> "4", () -> "5", () -> "6"),

                logger -> logger.error(THROWABLE, "123456"),
                logger -> logger.error(THROWABLE, "?23456", "1"),
                logger -> logger.error(THROWABLE, "??3456", "1", "2"),
                logger -> logger.error(THROWABLE, "???456", "1", "2", "3"),
                logger -> logger.error(THROWABLE, "????56", "1", "2", "3", "4"),
                logger -> logger.error(THROWABLE, "?????6", "1", "2", "3", "4", "5"),
                logger -> logger.error(THROWABLE, "??????", "1", "2", "3", "4", "5", "6"),
                logger -> logger.error(THROWABLE, "?23456", () -> "1"),
                logger -> logger.error(THROWABLE, "??3456", () -> "1", () -> "2"),
                logger -> logger.error(THROWABLE, "???456", () -> "1", () -> "2", () -> "3"),
                logger -> logger.error(THROWABLE, "????56", () -> "1", () -> "2", () -> "3", () -> "4"),
                logger -> logger.error(THROWABLE, "?????6", () -> "1", () -> "2", () -> "3", () -> "4", () -> "5"),
                logger -> logger.error(THROWABLE, "??????", () -> "1", () -> "2", () -> "3", () -> "4", () -> "5", () -> "6")
        );
    }

    static Stream<Consumer<Logger>> createRequestIdSupplierWithMessageStream() {
        return Stream.of(
                logger -> logger.trace(REQUEST_ID_SUPPLIER, "123456"),
                logger -> logger.trace(REQUEST_ID_SUPPLIER, "?23456", "1"),
                logger -> logger.trace(REQUEST_ID_SUPPLIER, "??3456", "1", "2"),
                logger -> logger.trace(REQUEST_ID_SUPPLIER, "???456", "1", "2", "3"),
                logger -> logger.trace(REQUEST_ID_SUPPLIER, "????56", "1", "2", "3", "4"),
                logger -> logger.trace(REQUEST_ID_SUPPLIER, "?????6", "1", "2", "3", "4", "5"),
                logger -> logger.trace(REQUEST_ID_SUPPLIER, "??????", "1", "2", "3", "4", "5", "6"),
                logger -> logger.trace(REQUEST_ID_SUPPLIER, "?23456", () -> "1"),
                logger -> logger.trace(REQUEST_ID_SUPPLIER, "??3456", () -> "1", () -> "2"),
                logger -> logger.trace(REQUEST_ID_SUPPLIER, "???456", () -> "1", () -> "2", () -> "3"),
                logger -> logger.trace(REQUEST_ID_SUPPLIER, "????56", () -> "1", () -> "2", () -> "3", () -> "4"),
                logger -> logger.trace(REQUEST_ID_SUPPLIER, "?????6", () -> "1", () -> "2", () -> "3", () -> "4", () -> "5"),
                logger -> logger.trace(REQUEST_ID_SUPPLIER, "??????", () -> "1", () -> "2", () -> "3", () -> "4", () -> "5", () -> "6"),

                logger -> logger.debug(REQUEST_ID_SUPPLIER, "123456"),
                logger -> logger.debug(REQUEST_ID_SUPPLIER, "?23456", "1"),
                logger -> logger.debug(REQUEST_ID_SUPPLIER, "??3456", "1", "2"),
                logger -> logger.debug(REQUEST_ID_SUPPLIER, "???456", "1", "2", "3"),
                logger -> logger.debug(REQUEST_ID_SUPPLIER, "????56", "1", "2", "3", "4"),
                logger -> logger.debug(REQUEST_ID_SUPPLIER, "?????6", "1", "2", "3", "4", "5"),
                logger -> logger.debug(REQUEST_ID_SUPPLIER, "??????", "1", "2", "3", "4", "5", "6"),
                logger -> logger.debug(REQUEST_ID_SUPPLIER, "?23456", () -> "1"),
                logger -> logger.debug(REQUEST_ID_SUPPLIER, "??3456", () -> "1", () -> "2"),
                logger -> logger.debug(REQUEST_ID_SUPPLIER, "???456", () -> "1", () -> "2", () -> "3"),
                logger -> logger.debug(REQUEST_ID_SUPPLIER, "????56", () -> "1", () -> "2", () -> "3", () -> "4"),
                logger -> logger.debug(REQUEST_ID_SUPPLIER, "?????6", () -> "1", () -> "2", () -> "3", () -> "4", () -> "5"),
                logger -> logger.debug(REQUEST_ID_SUPPLIER, "??????", () -> "1", () -> "2", () -> "3", () -> "4", () -> "5", () -> "6"),

                logger -> logger.info(REQUEST_ID_SUPPLIER, "123456"),
                logger -> logger.info(REQUEST_ID_SUPPLIER, "?23456", "1"),
                logger -> logger.info(REQUEST_ID_SUPPLIER, "??3456", "1", "2"),
                logger -> logger.info(REQUEST_ID_SUPPLIER, "???456", "1", "2", "3"),
                logger -> logger.info(REQUEST_ID_SUPPLIER, "????56", "1", "2", "3", "4"),
                logger -> logger.info(REQUEST_ID_SUPPLIER, "?????6", "1", "2", "3", "4", "5"),
                logger -> logger.info(REQUEST_ID_SUPPLIER, "??????", "1", "2", "3", "4", "5", "6"),
                logger -> logger.info(REQUEST_ID_SUPPLIER, "?23456", () -> "1"),
                logger -> logger.info(REQUEST_ID_SUPPLIER, "??3456", () -> "1", () -> "2"),
                logger -> logger.info(REQUEST_ID_SUPPLIER, "???456", () -> "1", () -> "2", () -> "3"),
                logger -> logger.info(REQUEST_ID_SUPPLIER, "????56", () -> "1", () -> "2", () -> "3", () -> "4"),
                logger -> logger.info(REQUEST_ID_SUPPLIER, "?????6", () -> "1", () -> "2", () -> "3", () -> "4", () -> "5"),
                logger -> logger.info(REQUEST_ID_SUPPLIER, "??????", () -> "1", () -> "2", () -> "3", () -> "4", () -> "5", () -> "6"),

                logger -> logger.warn(REQUEST_ID_SUPPLIER, "123456"),
                logger -> logger.warn(REQUEST_ID_SUPPLIER, "?23456", "1"),
                logger -> logger.warn(REQUEST_ID_SUPPLIER, "??3456", "1", "2"),
                logger -> logger.warn(REQUEST_ID_SUPPLIER, "???456", "1", "2", "3"),
                logger -> logger.warn(REQUEST_ID_SUPPLIER, "????56", "1", "2", "3", "4"),
                logger -> logger.warn(REQUEST_ID_SUPPLIER, "?????6", "1", "2", "3", "4", "5"),
                logger -> logger.warn(REQUEST_ID_SUPPLIER, "??????", "1", "2", "3", "4", "5", "6"),
                logger -> logger.warn(REQUEST_ID_SUPPLIER, "?23456", () -> "1"),
                logger -> logger.warn(REQUEST_ID_SUPPLIER, "??3456", () -> "1", () -> "2"),
                logger -> logger.warn(REQUEST_ID_SUPPLIER, "???456", () -> "1", () -> "2", () -> "3"),
                logger -> logger.warn(REQUEST_ID_SUPPLIER, "????56", () -> "1", () -> "2", () -> "3", () -> "4"),
                logger -> logger.warn(REQUEST_ID_SUPPLIER, "?????6", () -> "1", () -> "2", () -> "3", () -> "4", () -> "5"),
                logger -> logger.warn(REQUEST_ID_SUPPLIER, "??????", () -> "1", () -> "2", () -> "3", () -> "4", () -> "5", () -> "6"),

                logger -> logger.error(REQUEST_ID_SUPPLIER, "123456"),
                logger -> logger.error(REQUEST_ID_SUPPLIER, "?23456", "1"),
                logger -> logger.error(REQUEST_ID_SUPPLIER, "??3456", "1", "2"),
                logger -> logger.error(REQUEST_ID_SUPPLIER, "???456", "1", "2", "3"),
                logger -> logger.error(REQUEST_ID_SUPPLIER, "????56", "1", "2", "3", "4"),
                logger -> logger.error(REQUEST_ID_SUPPLIER, "?????6", "1", "2", "3", "4", "5"),
                logger -> logger.error(REQUEST_ID_SUPPLIER, "??????", "1", "2", "3", "4", "5", "6"),
                logger -> logger.error(REQUEST_ID_SUPPLIER, "?23456", () -> "1"),
                logger -> logger.error(REQUEST_ID_SUPPLIER, "??3456", () -> "1", () -> "2"),
                logger -> logger.error(REQUEST_ID_SUPPLIER, "???456", () -> "1", () -> "2", () -> "3"),
                logger -> logger.error(REQUEST_ID_SUPPLIER, "????56", () -> "1", () -> "2", () -> "3", () -> "4"),
                logger -> logger.error(REQUEST_ID_SUPPLIER, "?????6", () -> "1", () -> "2", () -> "3", () -> "4", () -> "5"),
                logger -> logger.error(REQUEST_ID_SUPPLIER, "??????", () -> "1", () -> "2", () -> "3", () -> "4", () -> "5", () -> "6")
        );
    }

    static Stream<Consumer<Logger>> createRequestIdSupplierWithThrowableAndMessageStream() {
        return Stream.of(
                logger -> logger.trace(REQUEST_ID_SUPPLIER, THROWABLE, "123456"),
                logger -> logger.trace(REQUEST_ID_SUPPLIER, THROWABLE, "?23456", "1"),
                logger -> logger.trace(REQUEST_ID_SUPPLIER, THROWABLE, "??3456", "1", "2"),
                logger -> logger.trace(REQUEST_ID_SUPPLIER, THROWABLE, "???456", "1", "2", "3"),
                logger -> logger.trace(REQUEST_ID_SUPPLIER, THROWABLE, "????56", "1", "2", "3", "4"),
                logger -> logger.trace(REQUEST_ID_SUPPLIER, THROWABLE, "?????6", "1", "2", "3", "4", "5"),
                logger -> logger.trace(REQUEST_ID_SUPPLIER, THROWABLE, "??????", "1", "2", "3", "4", "5", "6"),
                logger -> logger.trace(REQUEST_ID_SUPPLIER, THROWABLE, "?23456", () -> "1"),
                logger -> logger.trace(REQUEST_ID_SUPPLIER, THROWABLE, "??3456", () -> "1", () -> "2"),
                logger -> logger.trace(REQUEST_ID_SUPPLIER, THROWABLE, "???456", () -> "1", () -> "2", () -> "3"),
                logger -> logger.trace(REQUEST_ID_SUPPLIER, THROWABLE, "????56", () -> "1", () -> "2", () -> "3", () -> "4"),
                logger -> logger.trace(REQUEST_ID_SUPPLIER, THROWABLE, "?????6", () -> "1", () -> "2", () -> "3", () -> "4", () -> "5"),
                logger -> logger.trace(REQUEST_ID_SUPPLIER, THROWABLE, "??????", () -> "1", () -> "2", () -> "3", () -> "4", () -> "5", () -> "6"),

                logger -> logger.debug(REQUEST_ID_SUPPLIER, THROWABLE, "123456"),
                logger -> logger.debug(REQUEST_ID_SUPPLIER, THROWABLE, "?23456", "1"),
                logger -> logger.debug(REQUEST_ID_SUPPLIER, THROWABLE, "??3456", "1", "2"),
                logger -> logger.debug(REQUEST_ID_SUPPLIER, THROWABLE, "???456", "1", "2", "3"),
                logger -> logger.debug(REQUEST_ID_SUPPLIER, THROWABLE, "????56", "1", "2", "3", "4"),
                logger -> logger.debug(REQUEST_ID_SUPPLIER, THROWABLE, "?????6", "1", "2", "3", "4", "5"),
                logger -> logger.debug(REQUEST_ID_SUPPLIER, THROWABLE, "??????", "1", "2", "3", "4", "5", "6"),
                logger -> logger.debug(REQUEST_ID_SUPPLIER, THROWABLE, "?23456", () -> "1"),
                logger -> logger.debug(REQUEST_ID_SUPPLIER, THROWABLE, "??3456", () -> "1", () -> "2"),
                logger -> logger.debug(REQUEST_ID_SUPPLIER, THROWABLE, "???456", () -> "1", () -> "2", () -> "3"),
                logger -> logger.debug(REQUEST_ID_SUPPLIER, THROWABLE, "????56", () -> "1", () -> "2", () -> "3", () -> "4"),
                logger -> logger.debug(REQUEST_ID_SUPPLIER, THROWABLE, "?????6", () -> "1", () -> "2", () -> "3", () -> "4", () -> "5"),
                logger -> logger.debug(REQUEST_ID_SUPPLIER, THROWABLE, "??????", () -> "1", () -> "2", () -> "3", () -> "4", () -> "5", () -> "6"),

                logger -> logger.info(REQUEST_ID_SUPPLIER, THROWABLE, "123456"),
                logger -> logger.info(REQUEST_ID_SUPPLIER, THROWABLE, "?23456", "1"),
                logger -> logger.info(REQUEST_ID_SUPPLIER, THROWABLE, "??3456", "1", "2"),
                logger -> logger.info(REQUEST_ID_SUPPLIER, THROWABLE, "???456", "1", "2", "3"),
                logger -> logger.info(REQUEST_ID_SUPPLIER, THROWABLE, "????56", "1", "2", "3", "4"),
                logger -> logger.info(REQUEST_ID_SUPPLIER, THROWABLE, "?????6", "1", "2", "3", "4", "5"),
                logger -> logger.info(REQUEST_ID_SUPPLIER, THROWABLE, "??????", "1", "2", "3", "4", "5", "6"),
                logger -> logger.info(REQUEST_ID_SUPPLIER, THROWABLE, "?23456", () -> "1"),
                logger -> logger.info(REQUEST_ID_SUPPLIER, THROWABLE, "??3456", () -> "1", () -> "2"),
                logger -> logger.info(REQUEST_ID_SUPPLIER, THROWABLE, "???456", () -> "1", () -> "2", () -> "3"),
                logger -> logger.info(REQUEST_ID_SUPPLIER, THROWABLE, "????56", () -> "1", () -> "2", () -> "3", () -> "4"),
                logger -> logger.info(REQUEST_ID_SUPPLIER, THROWABLE, "?????6", () -> "1", () -> "2", () -> "3", () -> "4", () -> "5"),
                logger -> logger.info(REQUEST_ID_SUPPLIER, THROWABLE, "??????", () -> "1", () -> "2", () -> "3", () -> "4", () -> "5", () -> "6"),

                logger -> logger.warn(REQUEST_ID_SUPPLIER, THROWABLE, "123456"),
                logger -> logger.warn(REQUEST_ID_SUPPLIER, THROWABLE, "?23456", "1"),
                logger -> logger.warn(REQUEST_ID_SUPPLIER, THROWABLE, "??3456", "1", "2"),
                logger -> logger.warn(REQUEST_ID_SUPPLIER, THROWABLE, "???456", "1", "2", "3"),
                logger -> logger.warn(REQUEST_ID_SUPPLIER, THROWABLE, "????56", "1", "2", "3", "4"),
                logger -> logger.warn(REQUEST_ID_SUPPLIER, THROWABLE, "?????6", "1", "2", "3", "4", "5"),
                logger -> logger.warn(REQUEST_ID_SUPPLIER, THROWABLE, "??????", "1", "2", "3", "4", "5", "6"),
                logger -> logger.warn(REQUEST_ID_SUPPLIER, THROWABLE, "?23456", () -> "1"),
                logger -> logger.warn(REQUEST_ID_SUPPLIER, THROWABLE, "??3456", () -> "1", () -> "2"),
                logger -> logger.warn(REQUEST_ID_SUPPLIER, THROWABLE, "???456", () -> "1", () -> "2", () -> "3"),
                logger -> logger.warn(REQUEST_ID_SUPPLIER, THROWABLE, "????56", () -> "1", () -> "2", () -> "3", () -> "4"),
                logger -> logger.warn(REQUEST_ID_SUPPLIER, THROWABLE, "?????6", () -> "1", () -> "2", () -> "3", () -> "4", () -> "5"),
                logger -> logger.warn(REQUEST_ID_SUPPLIER, THROWABLE, "??????", () -> "1", () -> "2", () -> "3", () -> "4", () -> "5", () -> "6"),

                logger -> logger.error(REQUEST_ID_SUPPLIER, THROWABLE, "123456"),
                logger -> logger.error(REQUEST_ID_SUPPLIER, THROWABLE, "?23456", "1"),
                logger -> logger.error(REQUEST_ID_SUPPLIER, THROWABLE, "??3456", "1", "2"),
                logger -> logger.error(REQUEST_ID_SUPPLIER, THROWABLE, "???456", "1", "2", "3"),
                logger -> logger.error(REQUEST_ID_SUPPLIER, THROWABLE, "????56", "1", "2", "3", "4"),
                logger -> logger.error(REQUEST_ID_SUPPLIER, THROWABLE, "?????6", "1", "2", "3", "4", "5"),
                logger -> logger.error(REQUEST_ID_SUPPLIER, THROWABLE, "??????", "1", "2", "3", "4", "5", "6"),
                logger -> logger.error(REQUEST_ID_SUPPLIER, THROWABLE, "?23456", () -> "1"),
                logger -> logger.error(REQUEST_ID_SUPPLIER, THROWABLE, "??3456", () -> "1", () -> "2"),
                logger -> logger.error(REQUEST_ID_SUPPLIER, THROWABLE, "???456", () -> "1", () -> "2", () -> "3"),
                logger -> logger.error(REQUEST_ID_SUPPLIER, THROWABLE, "????56", () -> "1", () -> "2", () -> "3", () -> "4"),
                logger -> logger.error(REQUEST_ID_SUPPLIER, THROWABLE, "?????6", () -> "1", () -> "2", () -> "3", () -> "4", () -> "5"),
                logger -> logger.error(REQUEST_ID_SUPPLIER, THROWABLE, "??????", () -> "1", () -> "2", () -> "3", () -> "4", () -> "5", () -> "6")
        );
    }

    static Stream<Function<Logger, Boolean>> createIsLevelEnabledStream() {
        return Stream.of(
                Logger::isTraceEnabled,
                Logger::isDebugEnabled,
                Logger::isInfoEnabled,
                Logger::isWarnEnabled,
                Logger::isErrorEnabled
        );
    }

    private AbstractLoggerTestFactory() {
    }
}
