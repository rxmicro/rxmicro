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

package io.rxmicro.common.util;

import static java.lang.System.getProperty;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class Environments {

    private static final String UNKNOWN = "unknown";

    public static boolean isCurrentOsMac() {
        return getProperty("os.name", UNKNOWN).toLowerCase().contains("mac");
    }

    public static boolean isCurrentOsLinux() {
        return getProperty("os.name", UNKNOWN).toLowerCase().contains("linux");
    }

    public static boolean isCurrentOsWindows() {
        return getProperty("os.name", UNKNOWN).toLowerCase().contains("windows");
    }

    private Environments() {
    }
}
