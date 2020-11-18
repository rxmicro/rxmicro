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

package io.rxmicro.annotation.processor.common.model;

import io.rxmicro.config.detail.DefaultConfigValueBuilder;
import io.rxmicro.runtime.RuntimeConstants;

import java.util.List;
import java.util.Map;
import javax.lang.model.element.ModuleElement;

import static io.rxmicro.annotation.processor.common.model.ClassHeader.newClassHeaderBuilder;
import static io.rxmicro.annotation.processor.common.util.GeneratedClassNames.ENVIRONMENT_CUSTOMIZER_SIMPLE_CLASS_NAME;
import static io.rxmicro.annotation.processor.common.util.GeneratedClassNames.getEntryPointFullClassName;
import static io.rxmicro.runtime.detail.RxMicroRuntime.ENTRY_POINT_PACKAGE;

/**
 * @author nedis
 * @since 0.1
 */
public final class EnvironmentCustomizerClassStructure extends ClassStructure {

    private final ModuleElement currentModule;

    private final List<Map.Entry<String, DefaultConfigProxyValue>> defaultConfigProxyValues;

    public EnvironmentCustomizerClassStructure(final ModuleElement currentModule,
                                               final List<Map.Entry<String, DefaultConfigProxyValue>> defaultConfigProxyValues) {
        this.currentModule = currentModule;
        this.defaultConfigProxyValues = defaultConfigProxyValues;
    }

    @Override
    public String getTargetFullClassName() {
        return getEntryPointFullClassName(ENVIRONMENT_CUSTOMIZER_SIMPLE_CLASS_NAME);
    }

    @Override
    public String getTemplateName() {
        return "$$EnvironmentCustomizerTemplate.javaftl";
    }

    @Override
    public Map<String, Object> getTemplateVariables() {
        return Map.of(
                "PACKAGE_NAME", ENTRY_POINT_PACKAGE,
                "CURRENT_MODULE_IS_NAMED", !currentModule.isUnnamed(),
                "CLASS_NAME", ENVIRONMENT_CUSTOMIZER_SIMPLE_CLASS_NAME,
                "DEFAULT_CONFIG_VALUES", defaultConfigProxyValues
        );
    }

    @Override
    public ClassHeader getClassHeader() {
        return newClassHeaderBuilder(ENTRY_POINT_PACKAGE)
                .addStaticImport(RuntimeConstants.class, "RX_MICRO_RUNTIME_MODULE")
                .addStaticImport(DefaultConfigValueBuilder.class, "putDefaultConfigValue")
                .build();
    }
}
