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

package io.rxmicro.common;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.File;
import java.util.Optional;
import java.util.Set;

import static io.rxmicro.common.CommonConstants.RX_MICRO_HOME_VARIABLE_NAME;
import static io.rxmicro.common.util.Formats.format;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * @author nedis
 * @since 0.7.4
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
final class RxMicroModuleTest {

    private final Set<String> ignoreModules = Set.of(
            // rxmicro-slf4j-proxy is a workaround module, so it was excluded from this list
            "rxmicro-slf4j-proxy",

            // native modules do not contain any code
            "rxmicro-netty-native",
            "rxmicro-netty-native-all",
            "rxmicro-netty-native-linux",
            "rxmicro-netty-native-osx",

            // annotation processor modules are system modules and must be ignored
            "rxmicro-annotation-processor",
            "rxmicro-annotation-processor-integration-test",
            "rxmicro-annotation-processor-config",
            "rxmicro-annotation-processor-common",
            "rxmicro-annotation-processor-cdi",
            "rxmicro-annotation-processor-rest",
            "rxmicro-annotation-processor-rest-server",
            "rxmicro-annotation-processor-rest-client",
            "rxmicro-annotation-processor-data",
            "rxmicro-annotation-processor-data-mongo",
            "rxmicro-annotation-processor-data-sql",
            "rxmicro-annotation-processor-data-sql-r2dbc",
            "rxmicro-annotation-processor-data-sql-r2dbc-postgresql",
            "rxmicro-annotation-processor-data-aggregator",
            "rxmicro-annotation-processor-documentation",
            "rxmicro-annotation-processor-documentation-asciidoctor"
    );

    @Test
    @Order(1)
    void Should_contain_all_declared_modules() {
        final File rootDirectory = new File(Optional.ofNullable(System.getenv(RX_MICRO_HOME_VARIABLE_NAME)).orElseThrow(() -> {
            throw new InvalidStateException("Required '?' environment variable not defined", RX_MICRO_HOME_VARIABLE_NAME);
        }));
        final File[] modules = rootDirectory.listFiles((dir, name) -> name.startsWith("rxmicro-"));
        if (modules == null) {
            fail("Module directories not found. Is it valid root directory: " + rootDirectory.getAbsolutePath() + "?");
        }
        for (final File moduleDirectory : modules) {
            if (!ignoreModules.contains(moduleDirectory.getName())) {
                final String moduleName = moduleDirectory.getName().replace('-', '.');
                if (RxMicroModule.of(moduleName).isEmpty()) {
                    fail(format("The ? enum does not contain definition for '?' module",
                            RxMicroModule.class.getSimpleName(),
                            moduleName
                    ));
                }
            }
        }
    }
}