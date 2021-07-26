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

package io.rxmicro.runtime.detail;

import io.rxmicro.common.CheckedWrapperException;
import io.rxmicro.common.InvalidStateException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static io.rxmicro.runtime.internal.RuntimeVersion.setRxMicroVersion;
import static java.lang.reflect.Modifier.isStatic;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

/**
 * Used by generated code that created by the {@code RxMicro Annotation Processor}.
 *
 * @author nedis
 * @hidden
 * @since 0.1
 */
public final class RuntimeReflections {

    private static final Map<Class<?>, Map<String, Field>> CACHE = new HashMap<>();

    static {
        setRxMicroVersion();
    }

    public static void setFieldValue(final Object model,
                                     final String fieldName,
                                     final Object value,
                                     final Consumer<Field> setAccessibleConsumer) {
        final Field field = getCachedField(model, fieldName, setAccessibleConsumer);
        try {
            field.set(model, value);
        } catch (final IllegalAccessException ex) {
            throw new CheckedWrapperException(ex);
        }
    }

    public static Object getFieldValue(final Object model,
                                       final String fieldName,
                                       final Consumer<Field> setAccessibleConsumer) {
        final Field field = getCachedField(model, fieldName, setAccessibleConsumer);
        try {
            return field.get(model);
        } catch (final IllegalAccessException ex) {
            throw new CheckedWrapperException(ex);
        }
    }

    public static Object invoke(final Object model,
                                final String methodName,
                                final Consumer<Method> setAccessibleConsumer,
                                final Object... args) {
        final List<Class<?>> argTypes = Arrays.stream(args).map(Object::getClass).collect(toList());
        final Method method = getMethod(model, methodName, argTypes, setAccessibleConsumer);
        try {
            return method.invoke(model, args);
        } catch (final IllegalAccessException | InvocationTargetException ex) {
            throw new CheckedWrapperException(ex);
        }
    }

    private static Field getCachedField(final Object model,
                                        final String fieldName,
                                        final Consumer<Field> setAccessibleConsumer) {
        final Class<?> cl = model.getClass();
        final Map<String, Field> fieldMap = CACHE.computeIfAbsent(cl, c -> createFieldMap(cl, setAccessibleConsumer));

        final Field field = fieldMap.get(fieldName);
        if (field == null) {
            throw new InvalidStateException("Field '?.?' is not defined", cl.getName(), fieldName);
        }
        return field;
    }

    private static Method getMethod(final Object model,
                                    final String methodName,
                                    final List<Class<?>> argTypes,
                                    final Consumer<Method> setAccessibleConsumer) {
        final Class<?> cl = model instanceof Class ? (Class<?>) model : model.getClass();
        try {
            final Method method = cl.getDeclaredMethod(methodName, argTypes.toArray(new Class[0]));
            if (setAccessibleConsumer != null) {
                setAccessibleConsumer.accept(method);
            }
            return method;
        } catch (final NoSuchMethodException ignored) {
            throw new InvalidStateException(
                    "Method '?.?(?)' is not defined",
                    cl.getName(),
                    methodName,
                    argTypes.stream().map(Class::getName).collect(joining(", "))
            );
        }
    }

    private static Map<String, Field> createFieldMap(final Class<?> cl,
                                                     final Consumer<Field> setAccessibleConsumer) {
        final Map<String, Field> map = new LinkedHashMap<>();
        Class<?> currentClass = cl;
        while (currentClass != null && currentClass != Object.class) {
            for (final Field field : currentClass.getDeclaredFields()) {
                if (!isStatic(field.getModifiers()) && !field.isSynthetic()) {
                    if (setAccessibleConsumer != null) {
                        setAccessibleConsumer.accept(field);
                    }
                    if (map.put(field.getName(), field) != null) {
                        throw new InvalidStateException("Class '?' has duplicate field with name: '?'", cl.getName(), field.getName());
                    }
                }
            }
            currentClass = currentClass.getSuperclass();
        }
        return map;
    }

    private RuntimeReflections() {
    }
}
