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

package io.rxmicro.common.util;

/**
 * Defines the rules for generated classes by the {@code RxMicro Annotation Processor}.
 *
 * @author nedis
 * @since 0.1
 */
@SuppressWarnings("JavaDoc")
public final class GeneratedClassRules {

    /**
     * Each generated class by the {@code RxMicro Annotation Processor} starts with {@value #GENERATED_CLASS_NAME_PREFIX} prefix.
     *
     * <p>
     * This prefix allow differing generated class from the class written by human.
     */
    public static final String GENERATED_CLASS_NAME_PREFIX = "$$";

    /**
     * For the injected primitive parameters to the HTTP request handler methods the {@code RxMicro Annotation Processor}
     * generates virtual classes.
     *
     * <p>
     * These classes contain sub prefix: {@value #GENERATED_VIRTUAL_CLASS_SUB_PREFIX}
     */
    public static final String GENERATED_VIRTUAL_CLASS_SUB_PREFIX = "Virtual";

    /**
     * Returns {@code true} if the tested class is generated by the {@code RxMicro Annotation Processor}.
     *
     * @param clazz the tested class
     * @return {@code true} if the tested class is generated by the {@code RxMicro Annotation Processor}
     */
    public static boolean isGeneratedClass(final Class<?> clazz) {
        return clazz.getSimpleName().startsWith(GENERATED_CLASS_NAME_PREFIX);
    }

    private GeneratedClassRules() {
    }
}
