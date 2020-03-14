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

package io.rxmicro.annotation.processor.common.util;

import io.rxmicro.annotation.processor.common.util.proxy.ProxyElements;
import io.rxmicro.annotation.processor.common.util.proxy.ProxyMessager;
import io.rxmicro.annotation.processor.common.util.proxy.ProxyTypes;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.util.Map;

import static io.rxmicro.common.util.ExCollections.unmodifiableOrderedMap;
import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public final class AnnotationProcessorEnvironment {

    private static int errorCount;

    private static Elements elements;

    private static Types types;

    private static Filer filer;

    private static Messager messager;

    private static Map<String, String> compilerOptions;

    public static void init(final ProcessingEnvironment processingEnvironment) {
        elements = new ProxyElements(processingEnvironment.getElementUtils());
        types = new ProxyTypes(processingEnvironment.getTypeUtils());
        messager = new ProxyMessager(processingEnvironment.getMessager());
        filer = processingEnvironment.getFiler();
        compilerOptions = unmodifiableOrderedMap(processingEnvironment.getOptions());
        errorCount = 0;
    }

    public static Elements elements() {
        return require(elements, "? is not initialized yet", AnnotationProcessorEnvironment.class.getName());
    }

    public static Types types() {
        return require(types, "? is not initialized yet", AnnotationProcessorEnvironment.class.getName());
    }

    public static Filer filer() {
        return require(filer, "? is not initialized yet", AnnotationProcessorEnvironment.class.getName());
    }

    public static Messager messager() {
        return require(messager, "? is not initialized yet", AnnotationProcessorEnvironment.class.getName());
    }

    public static Map<String, String> compilerOptions() {
        return require(compilerOptions, "? is not initialized yet", AnnotationProcessorEnvironment.class.getName());
    }

    public static boolean doesNotContainErrors() {
        return errorCount == 0;
    }

    public static void errorDetected() {
        errorCount++;
    }

    private AnnotationProcessorEnvironment() {
    }
}
