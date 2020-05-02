/*
 * Copyright (c) 2020. http://rxmicro.io
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

package io.rxmicro.annotation.processor.common.util;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.4
 */
public final class InternalLoggers {

    public static void logMessage(final String level,
                                  final String message) {
        System.out.println("[" + level + "] " + message);
    }

    public static void logThrowableStackTrace(final Throwable throwable) {
        throwable.printStackTrace();
    }

    private InternalLoggers() {
    }
}