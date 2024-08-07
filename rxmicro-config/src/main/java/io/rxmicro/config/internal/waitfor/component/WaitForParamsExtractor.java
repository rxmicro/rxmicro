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
import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.config.WaitFor.WAIT_FOR_COMMAND_LINE_ARG;
import static io.rxmicro.config.WaitFor.WAIT_FOR_DELIMITER;
import static io.rxmicro.config.WaitFor.WAIT_FOR_ENV_VAR_OR_JAVA_SYS_PROP_NAME;
import static io.rxmicro.config.WaitFor.WAIT_FOR_PARAM_PREFIX;
import static io.rxmicro.config.internal.waitfor.model.Params.DESTINATION;
import static java.lang.System.lineSeparator;

/**
 * @author nedis
 * @since 0.3
 */
public final class WaitForParamsExtractor {

    private static final String SPACE_DELIMITER = " ";

    public static List<String> extractWaitForParams(final String... commandLineArgs) {
        validateEnvVariables();
        if (commandLineArgs.length > 0) {
            final Iterator<String> iterator = Arrays.asList(commandLineArgs).iterator();
            final List<String> result = new ArrayList<>();
            while (iterator.hasNext()) {
                final String arg = iterator.next();
                validateCommandLineArg(arg);
                if (extract(iterator, result, arg)) {
                    validateOnlyOneWaitForConfigurationPerProject(result);
                    return unmodifiableList(result);
                }
            }
        }
        validateOnlyOneWaitForConfigurationPerProject(List.of());
        return getParamsFromEvnVariables();
    }

    private static boolean extract(final Iterator<String> iterator,
                                   final List<String> result,
                                   final String arg) {
        if (WAIT_FOR_COMMAND_LINE_ARG.equals(arg)) {
            while (iterator.hasNext()) {
                final String next = iterator.next();
                result.add(next);
                if (!next.startsWith(WAIT_FOR_PARAM_PREFIX) && !next.contains(":") && !WAIT_FOR_DELIMITER.equals(next)) {
                    break;
                }
            }
            if (result.isEmpty()) {
                throw new ConfigException("Expected ?. For example: java Main.class wait-for localhost:8080", DESTINATION);
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
                    WAIT_FOR_ENV_VAR_OR_JAVA_SYS_PROP_NAME
            );
        }
        if (System.getenv(WAIT_FOR_COMMAND_LINE_ARG) != null) {
            throw new ConfigException(
                    "Invalid environment variable: '?'. Use '?' instead!",
                    WAIT_FOR_COMMAND_LINE_ARG,
                    WAIT_FOR_ENV_VAR_OR_JAVA_SYS_PROP_NAME
            );
        }
    }

    private static void validateCommandLineArg(final String arg) {
        if (WAIT_FOR_ENV_VAR_OR_JAVA_SYS_PROP_NAME.equals(arg)) {
            throw new ConfigException(
                    "Invalid command line argument: '?'. Use '?' instead!",
                    WAIT_FOR_ENV_VAR_OR_JAVA_SYS_PROP_NAME,
                    WAIT_FOR_COMMAND_LINE_ARG
            );
        }
    }

    private static List<String> getParamsFromEvnVariables() {
        final String property = System.getProperty(WAIT_FOR_ENV_VAR_OR_JAVA_SYS_PROP_NAME);
        if (property != null) {
            return getParamsFromEvnVariablesOrJavaSystemProperties(property);
        } else {
            return getParamsFromEvnVariablesOrJavaSystemProperties(System.getenv(WAIT_FOR_ENV_VAR_OR_JAVA_SYS_PROP_NAME));
        }
    }

    private static List<String> getParamsFromEvnVariablesOrJavaSystemProperties(final String nullableValue) {
        final List<String> result = Optional.ofNullable(nullableValue)
                .map(values -> Arrays.asList(values.split(SPACE_DELIMITER)))
                .orElse(null);
        if (result != null) {
            if (result.isEmpty()) {
                throw new ConfigException("Expected destination. For example: export WAIT_FOR=localhost:8080");
            }
            return unmodifiableList(result);
        } else {
            return List.of();
        }
    }

    private static void validateOnlyOneWaitForConfigurationPerProject(final List<String> commandLineArguments) {
        final List<String> sources = new ArrayList<>();
        if (!commandLineArguments.isEmpty()) {
            sources.add(format("Command line arguments: ?", String.join(SPACE_DELIMITER, commandLineArguments)));
        }
        List<String> params = getParamsFromEvnVariablesOrJavaSystemProperties(System.getProperty(WAIT_FOR_ENV_VAR_OR_JAVA_SYS_PROP_NAME));
        if (!params.isEmpty()) {
            sources.add(
                    format("? Java system property: ?", WAIT_FOR_ENV_VAR_OR_JAVA_SYS_PROP_NAME, String.join(SPACE_DELIMITER, params))
            );
        }
        params = getParamsFromEvnVariablesOrJavaSystemProperties(System.getenv(WAIT_FOR_ENV_VAR_OR_JAVA_SYS_PROP_NAME));
        if (!params.isEmpty()) {
            sources.add(
                    format("? environment variable: ?", WAIT_FOR_ENV_VAR_OR_JAVA_SYS_PROP_NAME, String.join(SPACE_DELIMITER, params))
            );
        }
        if (sources.size() > 1) {
            throw new ConfigException(
                    "Detected a duplicate of the wait for service configuration:\n\t?! Only one configuration source must be used!",
                    String.join(lineSeparator() + "\t", sources)
            );
        }
    }

    private WaitForParamsExtractor() {
    }
}
