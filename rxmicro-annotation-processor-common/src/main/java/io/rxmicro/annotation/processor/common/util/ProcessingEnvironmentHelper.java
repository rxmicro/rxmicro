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

import io.rxmicro.annotation.processor.common.util.proxy.ProxyElements;
import io.rxmicro.annotation.processor.common.util.proxy.ProxyMessager;
import io.rxmicro.annotation.processor.common.util.proxy.ProxyTypes;

import java.util.Map;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import static io.rxmicro.common.util.ExCollections.unmodifiableOrderedMap;
import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @since 0.1
 */
public final class ProcessingEnvironmentHelper {

    private static final String ERROR_TEMPLATE = "? is not initialized yet";

    private static ProcessingEnvironment processingEnvironment;

    private static int compilationErrors;

    private static Elements elements;

    private static Types types;

    private static Filer filer;

    private static Messager messager;

    private static Map<String, String> compilerOptions;

    public static void init(final ProcessingEnvironment processingEnv) {
        processingEnvironment = processingEnv;
        elements = new ProxyElements(processingEnv.getElementUtils());
        types = new ProxyTypes(processingEnv.getTypeUtils());
        messager = new ProxyMessager(processingEnv.getMessager());
        filer = processingEnv.getFiler();
        compilerOptions = unmodifiableOrderedMap(processingEnv.getOptions());
        compilationErrors = 0;
    }

    public static ProcessingEnvironment getProcessingEnvironment() {
        return processingEnvironment;
    }

    public static Elements getElements() {
        return require(elements, ERROR_TEMPLATE, ProcessingEnvironmentHelper.class.getName());
    }

    public static Types getTypes() {
        return require(types, ERROR_TEMPLATE, ProcessingEnvironmentHelper.class.getName());
    }

    public static Filer getFiler() {
        return require(filer, ERROR_TEMPLATE, ProcessingEnvironmentHelper.class.getName());
    }

    public static Messager getMessager() {
        return require(messager, ERROR_TEMPLATE, ProcessingEnvironmentHelper.class.getName());
    }

    public static Map<String, String> getCompilerOptions() {
        return require(compilerOptions, ERROR_TEMPLATE, ProcessingEnvironmentHelper.class.getName());
    }

    public static boolean doesContainCompilationErrors() {
        return compilationErrors > 0;
    }

    public static boolean doesNotContainCompilationErrors() {
        return compilationErrors == 0;
    }

    public static void aNewCompilationErrorDetected() {
        compilationErrors++;
    }

    private ProcessingEnvironmentHelper() {
    }
}
