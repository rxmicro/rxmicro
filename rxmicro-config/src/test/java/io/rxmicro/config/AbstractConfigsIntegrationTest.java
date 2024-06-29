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

package io.rxmicro.config;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.Map;

import static io.rxmicro.common.util.SystemPrintlnHelper.printStackTraceToStdErr;
import static io.rxmicro.config.detail.DefaultConfigValueBuildHelper.putDefaultConfigValue;
import static io.rxmicro.config.internal.ExternalSourceProviderFactory.setCurrentDir;
import static io.rxmicro.config.internal.ExternalSourceProviderFactory.setEnvironmentVariables;
import static io.rxmicro.config.internal.model.PropertyNames.USER_HOME_PROPERTY;
import static io.rxmicro.config.local.DefaultConfigValueBuildHelperReSetter.resetDefaultConfigValueStorage;

/**
 * @author nedis
 * @since 0.7
 */
public abstract class AbstractConfigsIntegrationTest {

    private static final String REAL_USER_HOME = System.getProperty(USER_HOME_PROPERTY);

    private static final Path TEMP_DIRECTORY =
            Paths.get(System.getProperty("java.io.tmpdir") + "/rxmicro-configs-integration-test").toAbsolutePath();

    private static Thread deleteTempDirShutdownHook;

    private String mkDir(final String relativePath) throws IOException {
        return Files.createDirectories(Paths.get(TEMP_DIRECTORY.toString() + "/" + relativePath)).toAbsolutePath().toString();
    }

    @BeforeEach
    void beforeEach() throws IOException {
        prepareTempDirectory();
        putDefaultConfigValue("test.defaultConfigValues", "defaultConfigValues");

        System.setProperty(USER_HOME_PROPERTY, mkDir("home/"));
        mkDir("home/.rxmicro/");
        setCurrentDir(mkDir("current/"));
        setEnvironmentVariables(Map.of(
                "test.environmentVariables", "environmentVariables",
                "TEST_NORMALIZED_ENVIRONMENT_VARIABLES", "normalizedEnvironmentVariables"
        ));
        System.setProperty("test.javaSystemProperties", "javaSystemProperties");

        final String root = TEMP_DIRECTORY.toString();
        Files.write(
                Paths.get(root + "/home/rxmicro.properties"),
                List.of("test.rxmicroFileAtTheHomeDir=rxmicroFileAtTheHomeDir")
        );
        Files.write(
                Paths.get(root + "/home/test.properties"),
                List.of("separateFileAtTheHomeDir=separateFileAtTheHomeDir")
        );

        Files.write(
                Paths.get(root + "/home/.rxmicro/rxmicro.properties"),
                List.of("test.rxmicroFileAtTheRxmicroConfigDir=rxmicroFileAtTheRxmicroConfigDir")
        );
        Files.write(
                Paths.get(root + "/home/.rxmicro/test.properties"),
                List.of("separateFileAtTheRxmicroConfigDir=separateFileAtTheRxmicroConfigDir")
        );

        Files.write(
                Paths.get(root + "/current/rxmicro.properties"),
                List.of("test.rxmicroFileAtTheCurrentDir=rxmicroFileAtTheCurrentDir")
        );
        Files.write(
                Paths.get(root + "/current/test.properties"),
                List.of("separateFileAtTheCurrentDir=separateFileAtTheCurrentDir")
        );

        new Configs.Builder()
                .withAllConfigSources()
                .withCommandLineArguments("test.commandLineArguments=commandLineArguments")
                .build();

        if (deleteTempDirShutdownHook == null) {
            deleteTempDirShutdownHook = new Thread(this::deleteTempDirectory, "DELETE_TEMP_DIR_SHUTDOWN_HOOK");
            Runtime.getRuntime().addShutdownHook(deleteTempDirShutdownHook);
        }
    }

    private void prepareTempDirectory() throws IOException {
        Files.createDirectories(TEMP_DIRECTORY);
    }

    @AfterEach
    void afterEach() {
        resetDefaultConfigValueStorage();
        System.setProperty(USER_HOME_PROPERTY, REAL_USER_HOME);
    }

    private void deleteTempDirectory() {
        if (Files.exists(TEMP_DIRECTORY)) {
            try {
                if (Files.isDirectory(TEMP_DIRECTORY)) {
                    Files.walkFileTree(TEMP_DIRECTORY, new SimpleFileVisitor<>() {
                        @Override
                        public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {
                            Files.delete(file);
                            return FileVisitResult.CONTINUE;
                        }

                        @Override
                        public FileVisitResult postVisitDirectory(final Path dir, final IOException exc) throws IOException {
                            try {
                                Files.delete(dir);
                            } catch (final DirectoryNotEmptyException ex) {
                                printStackTraceToStdErr(ex);
                            }
                            return FileVisitResult.CONTINUE;
                        }
                    });
                } else {
                    Files.delete(TEMP_DIRECTORY);
                }
            } catch (final IOException io) {
                printStackTraceToStdErr(io);
            }
        }
    }
}
