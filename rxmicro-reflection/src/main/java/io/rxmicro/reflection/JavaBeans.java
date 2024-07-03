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

package io.rxmicro.reflection;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static io.rxmicro.common.util.Strings.capitalize;
import static java.lang.reflect.Modifier.isPublic;

/**
 * Java bean utils.
 *
 * @author nedis
 * @since 0.12
 */
public final class JavaBeans {

    /**
     * Finds the public setters for the specified class.
     *
     * @param classInstance the specified class
     * @return the public setters for the specified class.
     */
    public static List<Method> findPublicSetters(final Class<?> classInstance) {
        final Set<String> methodNames = new HashSet<>();
        final List<Method> methods = new ArrayList<>();
        Class<?> current = classInstance;
        while (current != null && current != Object.class) {
            for (final Method method : current.getDeclaredMethods()) {
                if (isPublicSetter(method) &&
                        methodNames.add(method.getName() + method.getParameterTypes()[0].getName())) {
                    methods.add(method);
                }
            }
            current = current.getSuperclass();
        }
        return methods;
    }

    /**
     * Verifies if the provided method is a public setter.
     *
     * @param method the provided method.
     * @return result of verification.
     */
    public static boolean isPublicSetter(final Method method) {
        return method.getName().startsWith("set") &&
                isPublic(method.getModifiers()) &&
                method.getParameterCount() == 1;
    }

    /**
     * Verifies if the provided method is a public getter.
     *
     * @param method the provided method.
     * @return result of verification.
     */
    public static boolean isPublicGetter(final Method method) {
        return isPublic(method.getModifiers()) &&
                method.getParameterCount() == 0 &&
                (method.getName().startsWith("get") ||
                        method.getReturnType().equals(Boolean.TYPE) && method.getName().startsWith("is"));
    }


    /**
     * Finds all annotated elements for the provided class and all its parents and property name.
     * <p>Annotated elements are: {@link java.lang.reflect.Field} or {@link Method} instances.
     *
     * @param classInstance the provided class instance.
     * @param propertyName  the provided property name.
     * @return all found annotated elements.
     * @implNote Annotated elements from parent class will be first. For example, for the following class hierarchy:
     * <pre><code>
     * public class Parent {
     *     private String value;                        // (1)
     *     public String getValue() { return value; }   // (2)
     * }
     * public class Child extends Parent {
     *     private String value;                        // (3)
     *     public String getValue() { return value; }   // (4)
     * }
     * </code></pre>
     * this method returns {@code 4} annotated elements if {@code propertyName="value"}:
     * <ol>
     *     <li>{@link java.lang.reflect.Field} instance from {@code Parent} class;</li>
     *     <li>Getter {@link Method} instance from {@code Parent} class;</li>
     *     <li>{@link java.lang.reflect.Field} instance from {@code Child} class;</li>
     *     <li>Getter {@link Method} instance from {@code Child} class;</li>
     * </ol>
     */
    public static List<AnnotatedElement> getAnnotatedElements(final Class<?> classInstance,
                                                              final String propertyName) {
        final List<AnnotatedElement> localResult = new ArrayList<>(3);
        final List<AnnotatedElement> result = new ArrayList<>();
        final String getter1Name = "get" + capitalize(propertyName);
        final String getter2Name = "is" + capitalize(propertyName);
        Class<?> current = classInstance;
        while (current != null && current != Object.class) {
            try {
                localResult.add(current.getDeclaredField(propertyName));
            } catch (final NoSuchFieldException ignore) {
                // do nothing
            }
            for (final Method method : current.getDeclaredMethods()) {
                if (isPublicGetter(method) && (getter1Name.equals(method.getName()) || getter2Name.equals(method.getName()))) {
                    localResult.add(method);
                }
            }
            if (!localResult.isEmpty()) {
                result.addAll(0, localResult);
                localResult.clear();
            }
            current = current.getSuperclass();
        }
        return result;
    }

    private JavaBeans() {
    }
}
