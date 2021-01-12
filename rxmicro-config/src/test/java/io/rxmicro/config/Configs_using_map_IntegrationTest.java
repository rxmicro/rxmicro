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
public final class Configs_using_map_IntegrationTest extends AbstractConfigsIntegrationTest {

    @Test
    void Should_resolve_config_from_all_supported_sources() {
        final TestConfig config = Configs.getConfig(TestConfig.class);
        assertEquals("defaultConfigValues", config.getString("defaultConfigValues"));
        assertEquals("rxmicroClassPathResource", config.getString("rxmicroClassPathResource"));
        assertEquals("separateClassPathResource", config.getString("separateClassPathResource"));
        assertEquals("environmentVariables", config.getString("environmentVariables"));
        assertEquals("normalizedEnvironmentVariables", config.getString("NORMALIZED_ENVIRONMENT_VARIABLES"));
        assertEquals("rxmicroFileAtTheHomeDir", config.getString("rxmicroFileAtTheHomeDir"));
        assertEquals("rxmicroFileAtTheRxmicroConfigDir", config.getString("rxmicroFileAtTheRxmicroConfigDir"));
        assertEquals("rxmicroFileAtTheCurrentDir", config.getString("rxmicroFileAtTheCurrentDir"));
        assertEquals("separateFileAtTheHomeDir", config.getString("separateFileAtTheHomeDir"));
        assertEquals("separateFileAtTheRxmicroConfigDir", config.getString("separateFileAtTheRxmicroConfigDir"));
        assertEquals("separateFileAtTheCurrentDir", config.getString("separateFileAtTheCurrentDir"));
        assertEquals("javaSystemProperties", config.getString("javaSystemProperties"));
        assertEquals("commandLineArguments", config.getString("commandLineArguments"));
    }

    /**
     * @author nedis
     *
     * @since 0.7
     */
    public static final class TestConfig extends AsMapConfig {

    }
}
