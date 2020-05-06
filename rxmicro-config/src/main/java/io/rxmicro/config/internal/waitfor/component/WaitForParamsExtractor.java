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

package io.rxmicro.config.internal.waitfor.component;

import io.rxmicro.config.ConfigException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static io.rxmicro.common.util.ExCollections.unmodifiableList;
import static io.rxmicro.config.WaitFor.WAIT_FOR_COMMAND_LINE_ARG;
import static io.rxmicro.config.WaitFor.WAIT_FOR_ENV_VAR_OF_JAVA_SYS_PROP_NAME;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.3
 */
public final class WaitForParamsExtractor {

    public static List<String> extractWaitForParams(final String... commandLineArgs) {
        validateEnvVariables();
        if (commandLineArgs.length > 0) {
            final Iterator<String> iterator = Arrays.asList(commandLineArgs).iterator();
            final List<String> result = new ArrayList<>();
            while (iterator.hasNext()) {
                final String arg = iterator.next();
                validateCommandLineArg(arg);
                if (extract(iterator, result, arg)) {
                    return unmodifiableList(result);
                }
            }
        }
        return getParamsFromEvnVariables();
    }

    private static boolean extract(final Iterator<String> iterator,
                                   final List<String> result,
                                   final String arg) {
        if (WAIT_FOR_COMMAND_LINE_ARG.equals(arg)) {
            while (iterator.hasNext()) {
                final String next = iterator.next();
                result.add(next);
                if (!next.startsWith("--")) {
                    break;
                }
            }
            if (result.isEmpty()) {
                throw new ConfigException("Expected destination. For example: java Main.class wait-for ${destination}");
            }
            return true;
        }
        return false;
    }

    private static void validateEnvVariables() {
        if (System.getProperty(WAIT_FOR_COMMAND_LINE_ARG) != null) {
            throw new ConfigException(
                    "Invalid Java system property: '?'. Use '?' instead!",
                    WAIT_FOR_COMMAND_LINE_ARG,
                    WAIT_FOR_ENV_VAR_OF_JAVA_SYS_PROP_NAME
            );
        }
        if (System.getenv(WAIT_FOR_COMMAND_LINE_ARG) != null) {
            throw new ConfigException(
                    "Invalid environment variable: '?'. Use '?' instead!",
                    WAIT_FOR_COMMAND_LINE_ARG,
                    WAIT_FOR_ENV_VAR_OF_JAVA_SYS_PROP_NAME
            );
        }
    }

    private static void validateCommandLineArg(final String arg) {
        if (WAIT_FOR_ENV_VAR_OF_JAVA_SYS_PROP_NAME.equals(arg)) {
            throw new ConfigException(
                    "Invalid command line argument: '?'. Use '?' instead!",
                    WAIT_FOR_ENV_VAR_OF_JAVA_SYS_PROP_NAME,
                    WAIT_FOR_COMMAND_LINE_ARG
            );
        }
    }

    private static List<String> getParamsFromEvnVariables() {
        final List<String> result = Optional.ofNullable(System.getProperty(WAIT_FOR_ENV_VAR_OF_JAVA_SYS_PROP_NAME))
                .or(() -> Optional.ofNullable(System.getenv(WAIT_FOR_ENV_VAR_OF_JAVA_SYS_PROP_NAME)))
                .map(values -> Arrays.asList(values.split(" ")))
                .orElse(null);
        if (result != null) {
            if (result.isEmpty()) {
                throw new ConfigException("Expected destination. For example: export WAIT_FOR=${destination}");
            }
            return unmodifiableList(result);
        } else {
            return List.of();
        }
    }

    private WaitForParamsExtractor() {
    }
}
