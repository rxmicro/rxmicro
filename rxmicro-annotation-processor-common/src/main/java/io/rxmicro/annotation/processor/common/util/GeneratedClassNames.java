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

package io.rxmicro.annotation.processor.common.util;

import io.rxmicro.runtime.detail.RxMicroRuntime;

import javax.lang.model.element.ModuleElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

import static io.rxmicro.annotation.processor.common.util.Names.getDefaultVarName;
import static io.rxmicro.annotation.processor.common.util.Names.getPackageName;
import static io.rxmicro.annotation.processor.common.util.Names.getSimpleName;
import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.common.util.GeneratedClassRules.GENERATED_CLASS_NAME_PREFIX;

/**
 * @author nedis
 * @since 0.1
 */
public final class GeneratedClassNames {

    public static final String ENVIRONMENT_CUSTOMIZER_SIMPLE_CLASS_NAME = format("?EnvironmentCustomizer", GENERATED_CLASS_NAME_PREFIX);

    public static final String REFLECTIONS_SIMPLE_CLASS_NAME = format("?Reflections", GENERATED_CLASS_NAME_PREFIX);

    public static String getEntryPointPackage(final ModuleElement moduleElement) {
        if (moduleElement.isUnnamed()) {
            return RxMicroRuntime.getUnnamedModuleEntryPointPackage();
        } else {
            return RxMicroRuntime.getEntryPointPackage(moduleElement.getQualifiedName().toString());
        }
    }

    public static String getEnvironmentCustomizerFullClassName(final ModuleElement moduleElement) {
        return getEntryPointFullClassName(moduleElement, ENVIRONMENT_CUSTOMIZER_SIMPLE_CLASS_NAME);
    }

    public static String getReflectionsFullClassName(final ModuleElement moduleElement) {
        return getEntryPointFullClassName(moduleElement, REFLECTIONS_SIMPLE_CLASS_NAME);
    }

    public static String getEntryPointFullClassName(final ModuleElement moduleElement,
                                                    final String simpleClassName) {
        return getEntryPointPackage(moduleElement) + "." + simpleClassName;
    }

    public static String getModelTransformerInstanceName(final TypeMirror typeMirror,
                                                         final Class<?> baseTransformerClass) {
        return getModelTransformerInstanceName(getSimpleName(typeMirror), baseTransformerClass);
    }

    public static String getModelTransformerInstanceName(final TypeElement typeElement,
                                                         final Class<?> baseTransformerClass) {
        return getModelTransformerInstanceName(typeElement.asType(), baseTransformerClass);
    }

    public static String getModelTransformerInstanceName(final String modelSimpleName,
                                                         final Class<?> baseTransformerClass) {
        return format("??",
                getDefaultVarName(modelSimpleName.startsWith(GENERATED_CLASS_NAME_PREFIX) ?
                        modelSimpleName.substring(2) :
                        modelSimpleName),
                baseTransformerClass.getSimpleName()
        );
    }

    public static String getModelTransformerSimpleClassName(final TypeElement typeElement,
                                                            final Class<?> baseTransformerClass) {
        final String simpleName = typeElement.getSimpleName().toString();
        if (simpleName.startsWith(GENERATED_CLASS_NAME_PREFIX)) {
            return format("??",
                    simpleName,
                    baseTransformerClass.getSimpleName()
            );
        } else {
            return format("???",
                    GENERATED_CLASS_NAME_PREFIX,
                    simpleName,
                    baseTransformerClass.getSimpleName()
            );
        }
    }

    public static String getModelTransformerSimpleClassName(final String modelSimpleName,
                                                            final Class<?> baseTransformerClass) {
        if (modelSimpleName.startsWith(GENERATED_CLASS_NAME_PREFIX)) {
            return format("??",
                    modelSimpleName,
                    baseTransformerClass.getSimpleName()
            );
        } else {
            return format("???",
                    GENERATED_CLASS_NAME_PREFIX,
                    modelSimpleName,
                    baseTransformerClass.getSimpleName()
            );
        }
    }

    public static String getModelTransformerFullClassName(final String packageName,
                                                          final String modelSimpleName,
                                                          final Class<?> baseTransformerClass) {
        return format("?.?",
                packageName,
                getModelTransformerSimpleClassName(modelSimpleName, baseTransformerClass)
        );
    }

    public static String getModelTransformerFullClassName(final TypeElement typeElement,
                                                          final Class<?> baseTransformerClass) {
        return format("?.?",
                getPackageName(typeElement),
                getModelTransformerSimpleClassName(typeElement, baseTransformerClass)
        );
    }

    private GeneratedClassNames() {
    }
}
