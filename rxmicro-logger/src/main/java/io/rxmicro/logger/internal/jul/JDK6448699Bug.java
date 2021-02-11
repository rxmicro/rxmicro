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

import io.rxmicro.common.ImpossibleException;

/**
 * @author nedis
 * @since 0.10
 */
public final class JDK6448699Bug {

    static {
        // Workaround for https://bugs.openjdk.java.net/browse/JDK-6448699
        try {
            Class.forName("io.rxmicro.logger.jul.SystemConsoleHandler", true, ClassLoader.getSystemClassLoader());
        } catch (final LinkageError | ClassNotFoundException | SecurityException throwable) {
            new ImpossibleException(
                    throwable,
                    "Can't register io.rxmicro.logger.jul.SystemConsoleHandler: ?",
                    throwable.getMessage()
            ).printStackTrace();
        }
    }

    public static void fix() {
        //do nothing. All required logic done at the static section
    }

    private JDK6448699Bug() {
    }
}
