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

package io.rxmicro.config.internal;

import java.util.Map;

import static io.rxmicro.common.util.ExCollections.unmodifiableMap;
import static io.rxmicro.common.util.Requires.require;

/**
 * Temporary class for simplest unit testing
 *
 * @author nedis
 * @since 0.3
 */
public final class ExternalSourceProviderFactory {

    private static Map<String, String> environmentVariables;

    private static String currentDir;

    static {
        resetEnvironmentVariables();
        resetCurrentDir();
    }

    public static Map<String, String> getEnvironmentVariables() {
        return environmentVariables;
    }

    public static void setEnvironmentVariables(final Map<String, String> environmentVariables) {
        ExternalSourceProviderFactory.environmentVariables = unmodifiableMap(environmentVariables);
    }

    public static void resetEnvironmentVariables() {
        environmentVariables = System.getenv();
    }

    public static String getCurrentDir() {
        return currentDir;
    }

    public static void setCurrentDir(final String currentDir) {
        ExternalSourceProviderFactory.currentDir = require(currentDir);
    }

    public static void resetCurrentDir() {
        currentDir = ".";
    }

    private ExternalSourceProviderFactory() {
    }
}
