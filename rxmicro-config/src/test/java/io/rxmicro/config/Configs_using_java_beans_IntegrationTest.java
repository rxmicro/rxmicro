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

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author nedis
 *
 * @since 0.3
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public final class Configs_using_java_beans_IntegrationTest extends AbstractConfigsIntegrationTest {

    @Test
    void Should_resolve_config_from_all_supported_sources() {
        final TestConfig config = Configs.getConfig(TestConfig.class);
        assertEquals("defaultConfigValues", config.getDefaultConfigValues());
        assertEquals("rxmicroClassPathResource", config.getRxmicroClassPathResource());
        assertEquals("separateClassPathResource", config.getSeparateClassPathResource());
        assertEquals("environmentVariables", config.getEnvironmentVariables());
        assertEquals("normalizedEnvironmentVariables", config.getNormalizedEnvironmentVariables());
        assertEquals("rxmicroFileAtTheHomeDir", config.getRxmicroFileAtTheHomeDir());
        assertEquals("rxmicroFileAtTheRxmicroConfigDir", config.getRxmicroFileAtTheRxmicroConfigDir());
        assertEquals("rxmicroFileAtTheCurrentDir", config.getRxmicroFileAtTheCurrentDir());
        assertEquals("separateFileAtTheHomeDir", config.getSeparateFileAtTheHomeDir());
        assertEquals("separateFileAtTheRxmicroConfigDir", config.getSeparateFileAtTheRxmicroConfigDir());
        assertEquals("separateFileAtTheCurrentDir", config.getSeparateFileAtTheCurrentDir());
        assertEquals("javaSystemProperties", config.getJavaSystemProperties());
        assertEquals("commandLineArguments", config.getCommandLineArguments());
    }

    /**
     * @author nedis
     *
     * @since 0.3
     */
    @SuppressWarnings("unused")
    public static final class TestConfig extends Config {

        private String defaultConfigValues;

        private String rxmicroClassPathResource;

        private String separateClassPathResource;

        private String environmentVariables;

        private String normalizedEnvironmentVariables;

        private String rxmicroFileAtTheHomeDir;

        private String rxmicroFileAtTheRxmicroConfigDir;

        private String rxmicroFileAtTheCurrentDir;

        private String separateFileAtTheHomeDir;

        private String separateFileAtTheRxmicroConfigDir;

        private String separateFileAtTheCurrentDir;

        private String javaSystemProperties;

        private String commandLineArguments;

        public String getDefaultConfigValues() {
            return defaultConfigValues;
        }

        public TestConfig setDefaultConfigValues(final String defaultConfigValues) {
            this.defaultConfigValues = defaultConfigValues;
            return this;
        }

        public String getRxmicroClassPathResource() {
            return rxmicroClassPathResource;
        }

        public TestConfig setRxmicroClassPathResource(final String rxmicroClassPathResource) {
            this.rxmicroClassPathResource = rxmicroClassPathResource;
            return this;
        }

        public String getSeparateClassPathResource() {
            return separateClassPathResource;
        }

        public TestConfig setSeparateClassPathResource(final String separateClassPathResource) {
            this.separateClassPathResource = separateClassPathResource;
            return this;
        }

        public String getEnvironmentVariables() {
            return environmentVariables;
        }

        public TestConfig setEnvironmentVariables(final String environmentVariables) {
            this.environmentVariables = environmentVariables;
            return this;
        }

        public String getNormalizedEnvironmentVariables() {
            return normalizedEnvironmentVariables;
        }

        public TestConfig setNormalizedEnvironmentVariables(final String normalizedEnvironmentVariables) {
            this.normalizedEnvironmentVariables = normalizedEnvironmentVariables;
            return this;
        }

        public String getRxmicroFileAtTheHomeDir() {
            return rxmicroFileAtTheHomeDir;
        }

        public TestConfig setRxmicroFileAtTheHomeDir(final String rxmicroFileAtTheHomeDir) {
            this.rxmicroFileAtTheHomeDir = rxmicroFileAtTheHomeDir;
            return this;
        }

        public String getRxmicroFileAtTheRxmicroConfigDir() {
            return rxmicroFileAtTheRxmicroConfigDir;
        }

        public TestConfig setRxmicroFileAtTheRxmicroConfigDir(final String rxmicroFileAtTheRxmicroConfigDir) {
            this.rxmicroFileAtTheRxmicroConfigDir = rxmicroFileAtTheRxmicroConfigDir;
            return this;
        }

        public String getRxmicroFileAtTheCurrentDir() {
            return rxmicroFileAtTheCurrentDir;
        }

        public TestConfig setRxmicroFileAtTheCurrentDir(final String rxmicroFileAtTheCurrentDir) {
            this.rxmicroFileAtTheCurrentDir = rxmicroFileAtTheCurrentDir;
            return this;
        }

        public String getSeparateFileAtTheHomeDir() {
            return separateFileAtTheHomeDir;
        }

        public TestConfig setSeparateFileAtTheHomeDir(final String separateFileAtTheHomeDir) {
            this.separateFileAtTheHomeDir = separateFileAtTheHomeDir;
            return this;
        }

        public String getSeparateFileAtTheRxmicroConfigDir() {
            return separateFileAtTheRxmicroConfigDir;
        }

        public TestConfig setSeparateFileAtTheRxmicroConfigDir(final String separateFileAtTheRxmicroConfigDir) {
            this.separateFileAtTheRxmicroConfigDir = separateFileAtTheRxmicroConfigDir;
            return this;
        }

        public String getSeparateFileAtTheCurrentDir() {
            return separateFileAtTheCurrentDir;
        }

        public TestConfig setSeparateFileAtTheCurrentDir(final String separateFileAtTheCurrentDir) {
            this.separateFileAtTheCurrentDir = separateFileAtTheCurrentDir;
            return this;
        }

        public String getJavaSystemProperties() {
            return javaSystemProperties;
        }

        public TestConfig setJavaSystemProperties(final String javaSystemProperties) {
            this.javaSystemProperties = javaSystemProperties;
            return this;
        }

        public String getCommandLineArguments() {
            return commandLineArguments;
        }

        public TestConfig setCommandLineArguments(final String commandLineArguments) {
            this.commandLineArguments = commandLineArguments;
            return this;
        }
    }
}
