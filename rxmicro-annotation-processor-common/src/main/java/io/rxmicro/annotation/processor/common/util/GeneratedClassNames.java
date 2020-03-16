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

package io.rxmicro.annotation.processor.common.util;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

import static io.rxmicro.annotation.processor.common.util.Names.getDefaultVarName;
import static io.rxmicro.annotation.processor.common.util.Names.getPackageName;
import static io.rxmicro.annotation.processor.common.util.Names.getSimpleName;
import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.common.util.GeneratedClassRules.GENERATED_CLASS_NAME_PREFIX;
import static io.rxmicro.runtime.detail.Runtimes.ENTRY_POINT_PACKAGE;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class GeneratedClassNames {

    public static final String $$_ENVIRONMENT_CUSTOMIZER_SIMPLE_CLASS_NAME = format("?EnvironmentCustomizer", GENERATED_CLASS_NAME_PREFIX);

    public static final String $$_REFLECTIONS_SIMPLE_CLASS_NAME = format("?Reflections", GENERATED_CLASS_NAME_PREFIX);

    public static final String $$_REFLECTIONS_FULL_CLASS_NAME = getEntryPointFullClassName($$_REFLECTIONS_SIMPLE_CLASS_NAME);

    public static String getEntryPointFullClassName(final String simpleClassName) {
        return format("?.?", ENTRY_POINT_PACKAGE, simpleClassName);
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
