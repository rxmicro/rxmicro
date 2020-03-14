/*
 * Copyright 2019 http://rxmicro.io
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

import io.rxmicro.common.RxMicroException;
import io.rxmicro.runtime.detail.Reflections;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import static io.rxmicro.annotation.processor.common.model.ClassHeader.newClassHeaderBuilder;
import static io.rxmicro.annotation.processor.common.util.GeneratedClassNames.$$_REFLECTIONS_FULL_CLASS_NAME;
import static io.rxmicro.annotation.processor.common.util.GeneratedClassNames.$$_REFLECTIONS_SIMPLE_CLASS_NAME;
import static io.rxmicro.runtime.detail.Runtimes.ENTRY_POINT_PACKAGE;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public final class ReflectionsClassStructure extends ClassStructure {

    private final boolean getterRequired;

    private final boolean setterRequired;

    private final boolean invokeRequired;

    public ReflectionsClassStructure(final boolean getterRequired,
                                     final boolean setterRequired,
                                     final boolean invokeRequired) {
        this.getterRequired = getterRequired;
        this.setterRequired = setterRequired;
        this.invokeRequired = invokeRequired;
    }

    @Override
    public String getTargetFullClassName() {
        return $$_REFLECTIONS_FULL_CLASS_NAME;
    }

    @Override
    public String getTemplateName() {
        return "$$ReflectionsTemplate.javaftl";
    }

    @Override
    public Map<String, Object> getTemplateVariables() {
        return Map.of(
                "CLASS_NAME", $$_REFLECTIONS_SIMPLE_CLASS_NAME,
                "GETTER_REQUIRED", getterRequired,
                "SETTER_REQUIRED", setterRequired,
                "INVOKE_REQUIRED", invokeRequired
        );
    }

    @Override
    public ClassHeader getClassHeader() {
        final ClassHeader.Builder builder = newClassHeaderBuilder(ENTRY_POINT_PACKAGE)
                .addImports(RxMicroException.class);
        if (getterRequired || setterRequired) {
            builder
                    .addImports(Field.class)
                    .addStaticImport(Reflections.class, "getField");
        }
        if (invokeRequired) {
            builder
                    .addImports(Method.class, InvocationTargetException.class)
                    .addStaticImport(Reflections.class, "getMethod")
                    .addStaticImport(Arrays.class, "stream")
                    .addStaticImport(Collectors.class, "toList");
        }
        return builder.build();
    }
}
