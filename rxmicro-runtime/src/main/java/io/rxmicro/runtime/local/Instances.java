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

package io.rxmicro.runtime.local;

import io.rxmicro.common.CheckedWrapperException;
import io.rxmicro.common.RxMicroException;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.ServiceLoader;
import java.util.function.Function;

import static io.rxmicro.runtime.internal.RuntimeVersion.setRxMicroVersion;
import static java.util.stream.Collectors.joining;

/**
 * @author nedis
 * @since 0.1
 */
public final class Instances {

    static {
        setRxMicroVersion();
    }

    @SuppressWarnings("unchecked")
    public static <T> T instantiate(final String targetClassName,
                                    final Object... constructorArgs) {
        try {
            return (T) instantiate(Class.forName(targetClassName), constructorArgs);
        } catch (final ClassNotFoundException ex) {
            throw new CheckedWrapperException(ex, "Class ? not found", targetClassName);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T instantiate(final String targetClassName,
                                    final Class<?>[] argTypes,
                                    final Object... constructorArgs) {
        try {
            return (T) instantiate(Class.forName(targetClassName), argTypes, constructorArgs);
        } catch (final ClassNotFoundException ex) {
            throw new CheckedWrapperException(ex, "Class ? not found", targetClassName);
        }
    }

    public static <T> T instantiate(final Class<T> targetClass,
                                    final Object... constructorArgs) {
        final Class<?>[] argTypes = Arrays.stream(constructorArgs).map(Object::getClass).toArray(Class[]::new);
        return instantiate(targetClass, argTypes, constructorArgs);
    }

    public static <T> T instantiate(final Class<T> targetClass,
                                    final Class<?>[] argTypes,
                                    final Object... constructorArgs) {
        try {
            return targetClass.getConstructor(argTypes).newInstance(constructorArgs);
        } catch (final NoSuchMethodException ex) {
            throw new CheckedWrapperException(ex, "Class ? must contain a required constructor: " +
                    "public <init>(?)", targetClass.getName(),
                    Arrays.stream(argTypes)
                            .map(cl -> cl.getName() + " arg" + cl.getSimpleName())
                            .collect(joining(",")));
        } catch (final IllegalAccessException | InstantiationException ex) {
            throw new CheckedWrapperException(ex, "Can't instantiate ? class: ?",
                    targetClass.getName(), ex.getMessage());
        } catch (final InvocationTargetException ex) {
            throw new CheckedWrapperException(ex.getTargetException(), "Can't instantiate ? class: ?",
                    targetClass.getName(), ex.getTargetException().getMessage());
        }
    }

    public static <T> T getImplementation(final Class<T> serviceInterface,
                                          final boolean required,
                                          final Function<Class<T>, ServiceLoader<T>> serviceLoader) {
        final ServiceLoader<T> loader = serviceLoader.apply(serviceInterface);
        final Iterator<T> iterator = loader.iterator();
        T impl = null;

        while (iterator.hasNext()) {
            final T item = iterator.next();
            if (impl != null) {
                throw new ImplementationLoadFailedException(
                        "Detected a few implementations of component ?: ? and ?",
                        serviceInterface.getName(),
                        impl.getClass().getName(),
                        item.getClass().getName());
            }
            impl = item;
        }

        if (impl == null && required) {
            throw new ImplementationLoadFailedException(
                    "Missing an implementation of component ?", serviceInterface.getName());
        }
        return impl;
    }

    private Instances() {
    }

    /**
     * @author nedis
     * @since 0.1
     */
    public static final class ImplementationLoadFailedException extends RxMicroException {

        private ImplementationLoadFailedException(final String message,
                                                  final Object... args) {
            super(message, args);
        }
    }
}
