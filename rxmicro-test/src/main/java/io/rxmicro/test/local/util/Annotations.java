/*
 * Copyright (c) 2020. http://rxmicro.io
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

package io.rxmicro.test.local.util;

import io.rxmicro.test.local.InvalidTestConfigException;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Proxy;
import java.util.Optional;

import static io.rxmicro.common.util.Formats.format;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public final class Annotations {

    public static <T extends Annotation> T getRequiredAnnotation(final Class<?> testClass,
                                                                 final Class<T> annotationClass) {
        return Optional.ofNullable(testClass.getAnnotation(annotationClass))
                .orElseThrow(() -> {
                    throw new InvalidTestConfigException(
                            "'?' test class must be annotated by '@?' annotation",
                            testClass.getName(),
                            annotationClass.getName());
                });
    }

    public static <T extends Annotation> T getPresentOrDefaultAnnotation(final AnnotatedElement annotatedElement,
                                                                         final Class<T> annotationClass) {
        return Optional.ofNullable(annotatedElement.getAnnotation(annotationClass))
                .orElseGet(() -> defaultAnnotationInstance(annotationClass));
    }

    @SuppressWarnings("unchecked")
    public static <T extends Annotation> T defaultAnnotationInstance(final Class<T> annotationClass) {
        final Object mock = new Object();
        return (T) Proxy.newProxyInstance(
                annotationClass.getClassLoader(),
                new Class<?>[]{annotationClass},
                (proxy, method, args) -> {
                    if ("toString".equals(method.getName()) && method.getParameterCount() == 0) {
                        return format("? Proxy", annotationClass.getSimpleName());
                    } else if (method.getDeclaringClass() == Object.class) {
                        return method.invoke(mock, args);
                    } else {
                        return method.getDefaultValue();
                    }
                }
        );
    }

    private Annotations() {
    }
}
