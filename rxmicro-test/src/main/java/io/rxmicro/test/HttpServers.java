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

package io.rxmicro.test;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Random;

/**
 * HTTP server utility class.
 *
 * @author nedis
 * @since 0.1
 */
public final class HttpServers {

    private static final int MIN_RANDOM_PORT = 9_000;

    private static final int MAX_RANDOM_PORT = 59_999;

    /**
     * Returns the random free port on the current host.
     *
     * @return the random free port on the current host
     * @see ServerSocket
     */
    public static int getRandomFreePort() {
        try (ServerSocket serverSocket = new ServerSocket(0)) {
            return serverSocket.getLocalPort();
        } catch (final IOException ignored) {
            return RandomHolder.RANDOM.nextInt(MAX_RANDOM_PORT - MIN_RANDOM_PORT + 1) + MIN_RANDOM_PORT;
        }
    }

    /**
     * @author nedis
     * @since 0.10
     */
    private static final class RandomHolder {

        private static final Random RANDOM = new Random();
    }

    private HttpServers() {
    }
}
