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

package io.rxmicro.test.local.util;

import io.rxmicro.test.local.InvalidTestConfigException;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Optional;

import static io.rxmicro.common.util.Formats.format;

/**
 * @author nedis
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
        return (T) Proxy.newProxyInstance(
                annotationClass.getClassLoader(),
                new Class<?>[]{annotationClass},
                new AnnotationInvocationHandler<>(annotationClass)
        );
    }

    private Annotations() {
    }

    /**
     * @author nedis
     * @since 0.4
     */
    private static final class AnnotationInvocationHandler<T> implements InvocationHandler {

        private final Class<T> annotationClass;

        private AnnotationInvocationHandler(final Class<T> annotationClass) {
            this.annotationClass = annotationClass;
        }

        @Override
        public Object invoke(final Object proxy,
                             final Method method,
                             final Object[] args) throws InvocationTargetException, IllegalAccessException {
            if ("toString".equals(method.getName()) && method.getParameterCount() == 0) {
                return format("? Proxy", annotationClass.getSimpleName());
            } else if (method.getDeclaringClass() == Object.class) {
                return method.invoke(this, args);
            } else {
                return method.getDefaultValue();
            }
        }
    }
}
