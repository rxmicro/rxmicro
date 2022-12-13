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
import io.rxmicro.reflection.ReflectionConstants;
import io.rxmicro.runtime.detail.ChildrenInitHelper;

import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.lang.model.element.ModuleElement;

import static io.rxmicro.annotation.processor.common.model.ClassHeader.newClassHeaderBuilder;
import static io.rxmicro.annotation.processor.common.util.GeneratedClassNames.ENVIRONMENT_CUSTOMIZER_SIMPLE_CLASS_NAME;
import static io.rxmicro.annotation.processor.common.util.GeneratedClassNames.getEntryPointPackage;

/**
 * @author nedis
 * @since 0.1
 */
public final class EnvironmentCustomizerClassStructure extends ClassStructure {

    private final String packageName;

    private final ModuleElement currentModule;

    private final List<Map.Entry<String, DefaultConfigProxyValue>> defaultConfigProxyValues;

    private final Set<String> packagesThatMustBeOpenedToRxMicroReflectionModule;

    public EnvironmentCustomizerClassStructure(final ModuleElement currentModule,
                                               final List<Map.Entry<String, DefaultConfigProxyValue>> defaultConfigProxyValues,
                                               final Set<String> packagesThatMustBeOpenedToRxMicroReflectionModule) {
        this.packageName = getEntryPointPackage(currentModule);
        this.currentModule = currentModule;
        this.defaultConfigProxyValues = defaultConfigProxyValues;
        this.packagesThatMustBeOpenedToRxMicroReflectionModule = packagesThatMustBeOpenedToRxMicroReflectionModule;
    }

    @Override
    public String getTargetFullClassName() {
        return packageName + "." + ENVIRONMENT_CUSTOMIZER_SIMPLE_CLASS_NAME;
    }

    @Override
    public String getTemplateName() {
        return "$$EnvironmentCustomizerTemplate.javaftl";
    }

    @Override
    public Map<String, Object> getTemplateVariables() {
        return Map.of(
                "PACKAGE_NAME", packageName,
                "CURRENT_MODULE_IS_NAMED", !currentModule.isUnnamed(),
                "CLASS_NAME", ENVIRONMENT_CUSTOMIZER_SIMPLE_CLASS_NAME,
                "DEFAULT_CONFIG_VALUES", defaultConfigProxyValues,
                "PACKAGES_THAT_MUST_BE_OPENED_TO_RX_MICRO_REFLECTION_MODULE", packagesThatMustBeOpenedToRxMicroReflectionModule,
                "ENVIRONMENT_CUSTOMIZER_SIMPLE_CLASS_NAME", ENVIRONMENT_CUSTOMIZER_SIMPLE_CLASS_NAME
        );
    }

    @Override
    public ClassHeader getClassHeader() {
        return newClassHeaderBuilder(packageName)
                .addStaticImport(ReflectionConstants.class, "RX_MICRO_REFLECTION_MODULE")
                .addStaticImport(DefaultConfigValueBuilder.class, "putDefaultConfigValue")
                .addStaticImport(ChildrenInitHelper.class, "invokeAllStaticSections")
                .build();
    }
}
