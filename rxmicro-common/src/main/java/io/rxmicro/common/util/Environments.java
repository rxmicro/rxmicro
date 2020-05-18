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

import java.util.Locale;

import static java.lang.System.getProperty;

/**
 * Environment utility class
 *
 * @author nedis
 * @since 0.1
 * @see System#getProperty(String)
 */
public final class Environments {

    private static final String OS_NAME = "os.name";

    private static final String UNKNOWN = "unknown";

    /**
     * Returns {@code true} if current OS is macOS
     *
     * @return {@code true} if current OS is macOS
     */
    public static boolean isCurrentOsMac() {
        return getProperty(OS_NAME, UNKNOWN).toLowerCase(Locale.ENGLISH).contains("mac");
    }

    /**
     * Returns {@code true} if current OS is Linux
     *
     * @return {@code true} if current OS is Linux
     */
    public static boolean isCurrentOsLinux() {
        return getProperty(OS_NAME, UNKNOWN).toLowerCase(Locale.ENGLISH).contains("linux");
    }

    /**
     * Returns {@code true} if current OS is Windows
     *
     * @return {@code true} if current OS is Windows
     */
    public static boolean isCurrentOsWindows() {
        return getProperty(OS_NAME, UNKNOWN).toLowerCase(Locale.ENGLISH).contains("windows");
    }

    private Environments() {
    }
}
