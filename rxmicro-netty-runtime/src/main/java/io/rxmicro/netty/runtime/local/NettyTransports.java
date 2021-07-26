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

package io.rxmicro.netty.runtime.local;

import io.netty.channel.epoll.Epoll;
import io.netty.channel.kqueue.KQueue;

/**
 * @author nedis
 * @since 0.8
 */
final class NettyTransports {

    static boolean isEPollNativeAdded() {
        try {
            checkIfClassFound(Epoll.class);
            return true;
        } catch (final NoClassDefFoundError ignored) {
            return false;
        }
    }

    static boolean isEPollNativeAvailable() {
        return Epoll.isAvailable();
    }

    static Throwable getEPollNativeUnavailabilityCause() {
        return Epoll.unavailabilityCause();
    }

    static boolean isKQueueNativeAdded() {
        try {
            checkIfClassFound(KQueue.class);
            return true;
        } catch (final NoClassDefFoundError ignored) {
            return false;
        }
    }

    static boolean isKQueueNativeAvailable() {
        return KQueue.isAvailable();
    }

    static Throwable getKQueueNativeUnavailabilityCause() {
        return KQueue.unavailabilityCause();
    }

    private static void checkIfClassFound(final Class<?> className) {
        // do nothing
    }

    private NettyTransports() {
    }
}
