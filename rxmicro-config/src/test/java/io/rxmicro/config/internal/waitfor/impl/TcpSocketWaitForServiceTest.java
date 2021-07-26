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
import io.rxmicro.config.internal.waitfor.model.Params;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.net.ServerSocket;
import java.time.Duration;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.config.WaitFor.WAIT_FOR_TCP_SOCKET_TYPE_NAME;
import static io.rxmicro.config.WaitFor.WAIT_FOR_TIMEOUT_DEFAULT_VALUE_IN_SECONDS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author nedis
 * @since 0.7.3
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
final class TcpSocketWaitForServiceTest {

    @ParameterizedTest
    @ValueSource(strings = {
            "localhost",
            "8080",
            "localhost:8080:8443"
    })
    @Order(1)
    void Should_throw_ConfigException_if_destination_is_invalid(final String destination) {
        final ConfigException exception = assertThrows(ConfigException.class, () ->
                new TcpSocketWaitForService(new Params(
                        WAIT_FOR_TCP_SOCKET_TYPE_NAME,
                        Duration.ofSeconds(Long.parseLong(WAIT_FOR_TIMEOUT_DEFAULT_VALUE_IN_SECONDS)),
                        destination)));
        assertEquals(
                format("Invalid destination. Expected '${host}:${port}', but actual is '?'", destination),
                exception.getMessage()
        );
    }

    @Test
    @Order(2)
    void Should_throw_ConfigException_if_port_is_not_number() {
        final ConfigException exception = assertThrows(ConfigException.class, () ->
                new TcpSocketWaitForService(new Params(
                        WAIT_FOR_TCP_SOCKET_TYPE_NAME,
                        Duration.ofSeconds(Long.parseLong(WAIT_FOR_TIMEOUT_DEFAULT_VALUE_IN_SECONDS)),
                        "localhost:DEFAULT")));
        assertEquals(
                "Invalid port value. Expected an integer value, but actual is 'DEFAULT'",
                exception.getMessage()
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "-1",
            "65535",
            "99999"
    })
    @Order(3)
    void Should_throw_ConfigException_if_port_is_not_valid_number(final String port) {
        final ConfigException exception = assertThrows(ConfigException.class, () ->
                new TcpSocketWaitForService(new Params(
                        WAIT_FOR_TCP_SOCKET_TYPE_NAME,
                        Duration.ofSeconds(Long.parseLong(WAIT_FOR_TIMEOUT_DEFAULT_VALUE_IN_SECONDS)),
                        "localhost:" + port)));
        assertEquals(
                format("Invalid port value: ? (Must be 0 < ? < 65535)", port, port),
                exception.getMessage()
        );
    }

    @Test
    @Order(4)
    void Should_throw_ServiceYetNotAvailableException_if_tcp_socket_is_not_available() {
        final int randomFreePort = getRandomFreePort();
        final TcpSocketWaitForService service = new TcpSocketWaitForService(new Params(
                WAIT_FOR_TCP_SOCKET_TYPE_NAME,
                Duration.ofMillis(300),
                "localhost:" + randomFreePort)
        );
        final ServiceYetNotAvailableException exception = assertThrows(ServiceYetNotAvailableException.class, service::start);
        assertEquals(format("tcp socket 'localhost:?' is not available yet!", randomFreePort), exception.getMessage());
    }

    private static int getRandomFreePort() {
        try (ServerSocket serverSocket = new ServerSocket(0)) {
            return serverSocket.getLocalPort();
        } catch (final IOException ignored) {
            return 9876;
        }
    }
}
