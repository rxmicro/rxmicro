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

import io.rxmicro.config.ConfigException;
import io.rxmicro.config.ServiceYetNotAvailableException;
import io.rxmicro.config.internal.Validations;
import io.rxmicro.config.internal.waitfor.WaitForService;
import io.rxmicro.config.internal.waitfor.model.Params;
import io.rxmicro.logger.Logger;
import io.rxmicro.logger.LoggerFactory;
import io.rxmicro.validation.validator.HostNameConstraintValidator;
import io.rxmicro.validation.validator.PortConstraintValidator;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.config.internal.model.ConfigModelType.COMMAND_LINE_ARGUMENT;
import static io.rxmicro.config.internal.waitfor.model.Params.DESTINATION;
import static io.rxmicro.validation.detail.StatelessValidators.getStatelessValidator;
import static io.rxmicro.validation.local.RuntimeValidators.collectAllViolationsAndTranslateIntoConfigException;

/**
 * @author nedis
 * @since 0.3
 */
public final class TcpSocketWaitForService implements WaitForService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TcpSocketWaitForService.class);

    private static final int DEFAULT_SLEEP_DURATION_IN_MILLIS = 500;

    private final SocketData socketData;

    private final long timeoutInNanos;

    private final Params params;

    public TcpSocketWaitForService(final Params params) {
        this.params = params;
        this.socketData = validateSocketData(parse(params.getDestination()));
        this.timeoutInNanos = params.getTimeout().toNanos();
    }

    private SocketData parse(final String destination) {
        final String[] data = destination.split(":");
        if (data.length != 2) {
            throw new ConfigException("Invalid destination. Expected '${host}:${port}', but actual is '?'", destination);
        }
        try {
            return new SocketData(data[0], Integer.parseInt(data[1]));
        } catch (final NumberFormatException ignored) {
            throw new ConfigException("Invalid port value. Expected an integer value, but actual is '?'", data[1]);
        }
    }

    private SocketData validateSocketData(final SocketData socketData) {
        return collectAllViolationsAndTranslateIntoConfigException(Validations.VALIDATION_OPTIONS, () -> {
            new HostNameConstraintValidator(true).validate(socketData.host, COMMAND_LINE_ARGUMENT, DESTINATION);
            getStatelessValidator(PortConstraintValidator.class).validate(socketData.port, COMMAND_LINE_ARGUMENT, DESTINATION);
            return socketData;
        });
    }

    @Override
    public void start() {
        LOGGER.info(
                "wait-for stared: type=tcp-socket, timeout=?, tcp socket=?",
                format(params.getTimeout()), params.getDestination()
        );
        final long start = System.nanoTime();
        do {
            LOGGER.trace("Connecting to '?' ...", params.getDestination());
            try (Socket ignored = new Socket(this.socketData.host, this.socketData.port)) {
                LOGGER.debug("tcp socket is available: ?", params.getDestination());
                return;
            } catch (final IOException ex) {
                // do nothing
                LOGGER.debug(
                        "tcp socket '?' in not available yet: ? (?)",
                        params.getDestination(),
                        ex.getMessage(),
                        ex.getClass().getSimpleName()
                );
            }
            try {
                TimeUnit.MILLISECONDS.sleep(DEFAULT_SLEEP_DURATION_IN_MILLIS);
            } catch (final InterruptedException ignored) {
                // do nothing
            }
        } while (System.nanoTime() - start < timeoutInNanos);
        throw new ServiceYetNotAvailableException("tcp socket '?' is not available yet!", params.getDestination());
    }

    private static final class SocketData {
        private final String host;

        private final int port;

        private SocketData(final String host, final int port) {
            this.host = host;
            this.port = port;
        }
    }
}
