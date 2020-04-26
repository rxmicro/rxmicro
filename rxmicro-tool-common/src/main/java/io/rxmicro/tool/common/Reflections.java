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

package io.rxmicro.tool.common;

import io.rxmicro.common.CheckedWrapperException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static io.rxmicro.common.util.Formats.format;
import static java.lang.reflect.Modifier.isFinal;
import static java.lang.reflect.Modifier.isStatic;
import static java.util.Collections.unmodifiableSet;
import static java.util.stream.Collectors.toList;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class Reflections {

    public static Module getToolCommonModule() {
        return Reflections.class.getModule();
    }

    public static List<Field> allFields(final Class<?> classInstance,
                                        final Predicate<Field> predicate) {
        final List<Field> fields = new ArrayList<>();
        Class<?> current = classInstance;
        while (current != null && current != Object.class) {
            fields.addAll(Arrays.stream(current.getDeclaredFields())
                    .filter(predicate)
                    .collect(Collectors.toList()));
            current = current.getSuperclass();
        }
        return fields;
    }

    public static List<Method> allMethods(final Class<?> classInstance,
                                          final Predicate<Method> predicate) {
        final List<Method> methods = new ArrayList<>();
        Class<?> current = classInstance;
        while (current != null && current != Object.class) {
            methods.addAll(Arrays.stream(current.getDeclaredMethods())
                    .filter(predicate)
                    .collect(Collectors.toList()));
            current = current.getSuperclass();
        }
        return methods;
    }

    public static Set<Class<?>> allSuperClasses(final Class<?> clazz) {
        final Set<Class<?>> set = new LinkedHashSet<>();
        Class<?> currentClass = clazz.getSuperclass();
        while (currentClass != null && currentClass != Object.class) {
            set.add(currentClass);
            currentClass = currentClass.getSuperclass();
        }
        return unmodifiableSet(set);
    }

    public static Object getFieldValue(final List<Object> instances,
                                       final Field field) {
        for (final Object instance : instances) {
            if (isValidInstance(field, instance)) {
                return getFieldValue(instance, field);
            }
        }
        throw new IllegalArgumentException(
                format(
                        "At least one class must contain the field: field=?, classes=?",
                        field, instances.stream().map(Object::getClass).collect(toList())
                )
        );
    }

    private static boolean isValidInstance(final Field field,
                                           final Object instance) {
        if (instance == null) {
            return isStatic(field.getModifiers());
        } else {
            return instance.getClass() == field.getDeclaringClass() ||
                    allSuperClasses(instance.getClass()).contains(field.getDeclaringClass());
        }
    }

    public static Object getFieldValue(final Object instance,
                                       final Field field) {
        try {
            final Object validInstance = isStatic(field.getModifiers()) ? null : instance;
            if (!field.canAccess(validInstance)) {
                field.setAccessible(true);
            }
            return field.get(validInstance);
        } catch (final IllegalAccessException e) {
            throw new CheckedWrapperException(e);
        }
    }

    public static Object getFieldValue(final Object instance,
                                       final Class<?> classInstance,
                                       final String fieldName) {
        try {
            final Object validInstance = instance.getClass() == Class.class ? null : instance;
            final Field field = classInstance.getDeclaredField(fieldName);
            return getFieldValue(validInstance, field);
        } catch (final NoSuchFieldException e) {
            throw new CheckedWrapperException(e, "Field '?.?' not defined", instance.getClass().getName(), fieldName);
        }
    }

    public static Field getDeclaredField(final Class<?> classInstance,
                                         final String fieldName) {
        try {
            return classInstance.getDeclaredField(fieldName);
        } catch (final NoSuchFieldException e) {
            throw new CheckedWrapperException(e, "Field '?.?' not defined", classInstance.getName(), fieldName);
        }
    }

    public static Object getFieldValue(final Object instance,
                                       final String fieldName) {
        final Class<?> classInstance =
                instance.getClass() == Class.class ? (Class<?>) instance : instance.getClass();
        return getFieldValue(instance, classInstance, fieldName);
    }

    public static void setFieldValue(final Object instance,
                                     final String fieldName,
                                     final Object value) {
        try {
            final Class<?> classInstance =
                    instance.getClass() == Class.class ? (Class<?>) instance : instance.getClass();
            final Object validInstance = instance.getClass() == Class.class ? null : instance;
            final Field field = classInstance.getDeclaredField(fieldName);
            setFieldValue(validInstance, field, value);
        } catch (final NoSuchFieldException e) {
            throw new CheckedWrapperException(e, "Field '?.?' not defined", instance.getClass().getName(), fieldName);
        }
    }

    public static void setFieldValue(final List<Object> instances,
                                     final Field field,
                                     final Object value) {
        for (final Object instance : instances) {
            if (isValidInstance(field, instance)) {
                setFieldValue(instance, field, value);
                return;
            }
        }
        throw new IllegalArgumentException(
                format(
                        "At least one class must contain the field: field=?, classes=?",
                        field, instances.stream().map(Object::getClass).collect(toList())
                )
        );
    }

    public static void setFieldValue(final Object instance,
                                     final Field field,
                                     final Object value) {
        try {
            final Object validInstance = isStatic(field.getModifiers()) ? null : instance;
            if (!field.canAccess(validInstance)) {
                field.setAccessible(true);
            }
            if (isFinal(field.getModifiers())) {
                removeFinalModifier(field);
            }
            field.set(validInstance, value);
        } catch (final IllegalAccessException e) {
            throw new CheckedWrapperException(e);
        }
    }

    private static void removeFinalModifier(final Field field)
            throws IllegalAccessException {
        try {
            final Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
        } catch (final NoSuchFieldException e) {
            // Read more: https://bugs.openjdk.java.net/browse/JDK-8217225
            throw new IllegalArgumentException(format(
                    "Can't update final field: ?. Read more: https://bugs.openjdk.java.net/browse/JDK-8217225",
                    field
            ));
        }
    }

    @SuppressWarnings("UnusedReturnValue")
    public static Object invokeMethod(final List<Object> instances,
                                      final String methodName,
                                      final Object... args) {
        final Class<?>[] parameterTypes = Arrays.stream(args).map(Object::getClass).toArray(Class[]::new);
        for (final Object instance : instances) {
            try {
                final Method method = instance.getClass().getDeclaredMethod(methodName, parameterTypes);
                if (!method.canAccess(instance)) {
                    method.setAccessible(true);
                }
                return invokeMethod(instance, method, args);
            } catch (final NoSuchMethodException e) {
                // do nothing
            }
        }
        throw new CheckedWrapperException(
                new NoSuchMethodException(), "At least one class must contain the method 'void ?()': classes=?",
                methodName, instances.stream().map(Object::getClass).collect(toList())
        );
    }

    public static Object invokeMethod(final Object instance,
                                      final Method method,
                                      final Object... args) {
        try {
            return method.invoke(instance, args);
        } catch (IllegalAccessException e) {
            throw new CheckedWrapperException(e);
        } catch (InvocationTargetException e) {
            throw new CheckedWrapperException(e.getTargetException());
        }
    }

    private Reflections() {
    }
}
