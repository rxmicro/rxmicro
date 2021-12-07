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

package io.rxmicro.annotation.processor.rest.server.model;

import io.rxmicro.annotation.processor.common.model.ClassHeader;
import io.rxmicro.annotation.processor.common.model.ClassStructure;
import io.rxmicro.config.Configs;
import io.rxmicro.rest.server.RestServerConfig;
import io.rxmicro.rest.server.detail.component.CustomExceptionServerModelWriters;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.lang.model.element.ModuleElement;

import static io.rxmicro.annotation.processor.common.util.GeneratedClassNames.ENVIRONMENT_CUSTOMIZER_SIMPLE_CLASS_NAME;
import static io.rxmicro.annotation.processor.common.util.GeneratedClassNames.getEntryPointFullClassName;
import static io.rxmicro.annotation.processor.common.util.GeneratedClassNames.getEntryPointPackage;
import static io.rxmicro.rest.server.detail.component.CustomExceptionServerModelWriters.CUSTOM_EXCEPTION_MODEL_WRITERS_CUSTOMIZER_CLASS_NAME;

/**
 * @author nedis
 * @since 0.10
 */
public final class CustomExceptionServerModelWritersCustomizerClassStructure extends ClassStructure {

    private final ModuleElement moduleElement;

    private final Set<CustomExceptionServerWriterClassStructure> customExceptionModelWriters;

    public CustomExceptionServerModelWritersCustomizerClassStructure(
            final ModuleElement moduleElement,
            final Set<CustomExceptionServerWriterClassStructure> customExceptionModelWriters) {
        this.moduleElement = moduleElement;
        this.customExceptionModelWriters = customExceptionModelWriters;
    }

    @Override
    public String getTargetFullClassName() {
        return getEntryPointFullClassName(moduleElement, CUSTOM_EXCEPTION_MODEL_WRITERS_CUSTOMIZER_CLASS_NAME);
    }

    @Override
    public String getTemplateName() {
        return "rest/server/$$CustomExceptionModelWritersCustomizerTemplate.javaftl";
    }

    @Override
    protected boolean shouldSourceCodeBeGenerated(final boolean isLibraryModule) {
        return !customExceptionModelWriters.isEmpty();
    }

    @Override
    public Map<String, Object> getTemplateVariables() {
        final Map<String, Object> map = new HashMap<>();
        map.put("CLASS_NAME", CUSTOM_EXCEPTION_MODEL_WRITERS_CUSTOMIZER_CLASS_NAME);
        map.put("ENVIRONMENT_CUSTOMIZER_CLASS", ENVIRONMENT_CUSTOMIZER_SIMPLE_CLASS_NAME);
        map.put("CUSTOM_EXCEPTION_MODEL_WRITERS", customExceptionModelWriters);
        return map;
    }

    @Override
    public ClassHeader getClassHeader() {
        final ClassHeader.Builder builder = ClassHeader.newClassHeaderBuilder(getEntryPointPackage(moduleElement))
                .addStaticImport(CustomExceptionServerModelWriters.class, "CUSTOM_EXCEPTION_MODEL_WRITERS_CUSTOMIZER_CLASS_NAME")
                .addStaticImport(CustomExceptionServerModelWriters.class, "registerCustomExceptionServerModelWriter")
                .addStaticImport(Configs.class, "getConfig")
                .addImports(RestServerConfig.class);
        customExceptionModelWriters.forEach(structure ->
                builder.addImports(structure.getModelFullClassName()).addImports(structure.getTargetFullClassName())
        );
        return builder.build();
    }
}
