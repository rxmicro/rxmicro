/*
 * Copyright 2019 https://rxmicro.io
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

package io.rxmicro.annotation.processor.common.model;

import io.rxmicro.config.detail.DefaultConfigValuePopulator;
import io.rxmicro.runtime.detail.Runtimes;

import java.util.List;
import java.util.Map;

import static io.rxmicro.annotation.processor.common.model.ClassHeader.newClassHeaderBuilder;
import static io.rxmicro.annotation.processor.common.util.GeneratedClassNames.$$_ENVIRONMENT_CUSTOMIZER_SIMPLE_CLASS_NAME;
import static io.rxmicro.annotation.processor.common.util.GeneratedClassNames.getEntryPointFullClassName;
import static io.rxmicro.common.util.Requires.require;
import static io.rxmicro.runtime.detail.Runtimes.ENTRY_POINT_PACKAGE;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class EnvironmentCustomizerClassStructure extends ClassStructure {

    private final List<Map.Entry<String, String>> defaultConfigValues;

    public EnvironmentCustomizerClassStructure(final List<Map.Entry<String, String>> defaultConfigValues) {
        this.defaultConfigValues = require(defaultConfigValues);
    }

    @Override
    public String getTargetFullClassName() {
        return getEntryPointFullClassName($$_ENVIRONMENT_CUSTOMIZER_SIMPLE_CLASS_NAME);
    }

    @Override
    public String getTemplateName() {
        return "$$EnvironmentCustomizerTemplate.javaftl";
    }

    @Override
    public Map<String, Object> getTemplateVariables() {
        return Map.of(
                "PACKAGE_NAME", ENTRY_POINT_PACKAGE,
                "CLASS_NAME", $$_ENVIRONMENT_CUSTOMIZER_SIMPLE_CLASS_NAME,
                "DEFAULT_CONFIG_VALUES", defaultConfigValues
        );
    }

    @Override
    public ClassHeader getClassHeader() {
        return newClassHeaderBuilder(ENTRY_POINT_PACKAGE)
                .addStaticImport(Runtimes.class, "getRuntimeModule")
                .addStaticImport(DefaultConfigValuePopulator.class, "putDefaultConfigValue")
                .build();
    }
}
