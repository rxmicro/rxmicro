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

import io.rxmicro.common.CheckedWrapperException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import static io.rxmicro.common.internal.FinalFieldUpdater.setFinalFieldValue;
import static io.rxmicro.common.util.Formats.format;
import static java.lang.reflect.Modifier.isFinal;
import static java.lang.reflect.Modifier.isStatic;
import static java.util.Collections.unmodifiableList;
import static java.util.Collections.unmodifiableSet;
import static java.util.stream.Collectors.toList;

/**
 * Reflection utils.
 *
 * @author nedis
 * @since 0.1
 */
public final class Reflections {

    /**
     * Returns the all declared fields from the specified class instance and all it parents (except {@link Object})
     * that filtered by {@code predicate}.
     *
     * @param classInstance the specified class instance
     * @param predicate     the field selection predicate
     * @return the all declared fields as unmodified list
     * @see Class#getDeclaredFields()
     * @see Class#getSuperclass()
     * @throws NullPointerException if any parameter is null
     */
    public static List<Field> allFields(final Class<?> classInstance,
                                        final Predicate<Field> predicate) {
        final List<Field> fields = new ArrayList<>();
        Class<?> current = classInstance;
        while (current != null && current != Object.class) {
            fields.addAll(Arrays.stream(current.getDeclaredFields())
                    .filter(predicate)
                    .collect(toList()));
            current = current.getSuperclass();
        }
        return unmodifiableList(fields);
    }

    /**
     * Returns the all declared methods from the specified class instance and all it parents (except {@link Object})
     * that filtered by {@code predicate}.
     *
     * @param classInstance the specified class instance
     * @param predicate     the method selection predicate
     * @return the all declared methods as unmodified list
     * @see Class#getDeclaredMethods()
     * @see Class#getSuperclass()
     * @throws NullPointerException if any parameter is null
     */
    public static List<Method> allMethods(final Class<?> classInstance,
                                          final Predicate<Method> predicate) {
        final List<Method> methods = new ArrayList<>();
        Class<?> current = classInstance;
        while (current != null && current != Object.class) {
            methods.addAll(Arrays.stream(current.getDeclaredMethods())
                    .filter(predicate)
                    .collect(toList()));
            current = current.getSuperclass();
        }
        return unmodifiableList(methods);
    }

    /**
     * Returns {@code true} if the specified class instance contains at least one method, that matches to the method selection predicate.
     *
     * @param classInstance the specified class instance
     * @param predicate     the method selection predicate
     * @return {@code true} if the specified class instance contains at least one method, that matches to the method selection predicate
     * @see Class#getDeclaredMethods()
     * @see Class#getSuperclass()
     * @throws NullPointerException if any parameter is null
     */
    public static boolean containsMethod(final Class<?> classInstance,
                                         final Predicate<Method> predicate) {
        Class<?> current = classInstance;
        while (current != null && current != Object.class) {
            if (Arrays.stream(current.getDeclaredMethods()).anyMatch(predicate)) {
                return true;
            }
            current = current.getSuperclass();
        }
        return false;
    }

    /**
     * Returns the unmodifiable {@link Set} of all parents for the specified class.
     *
     * @param clazz the specified class
     * @return the unmodifiable {@link Set} of all parents for the specified class
     * @throws NullPointerException if any parameter is null
     */
    public static Set<Class<?>> allSuperClasses(final Class<?> clazz) {
        final Set<Class<?>> set = new LinkedHashSet<>();
        Class<?> currentClass = clazz.getSuperclass();
        while (currentClass != null && currentClass != Object.class) {
            set.add(currentClass);
            currentClass = currentClass.getSuperclass();
        }
        return unmodifiableSet(set);
    }

    /**
     * Returns the field value.
     *
     * @param instances all possible instances
     * @param field the specified field instance
     * @return the field value
     * @see Field#canAccess(Object)
     * @see Field#setAccessible(boolean)
     * @see Field#get(Object)
     * @throws CheckedWrapperException      if this {@code Field} object is enforcing Java language access control and the underlying
     *                                      field is inaccessible.
     * @throws SecurityException            if the request is denied by the security manager
     * @throws IllegalArgumentException
     * <ul>
     *      <li>
     *          if this reflected object is a static member or constructor and the given {@code obj} is non-{@code null}, or
     *      </li>
     *      <li>
     *          if this reflected object is an instance method or field and the given {@code obj} is {@code null} or
     *          of type that is not a subclass of the {@link java.lang.reflect.Member#getDeclaringClass() declaring class} of the member, or
     *      </li>
     *      <li>
     *          if the specified object is not an instance of the class or interface declaring the underlying
     *          field (or a subclass or implementor thereof), or
     *      </li>
     *      <li>
     *          if the specified instances are invalid
     *      </li>
     * </ul>
     * @throws NullPointerException         if any parameter is null
     * @throws ExceptionInInitializerError  if the initialization provoked by this method fails.
     * @throws java.lang.reflect.InaccessibleObjectException if access cannot be enabled
     */
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

    /**
     * Returns the field value.
     *
     * @param instance the specified instance
     * @param field the specified field instance
     * @return the field value
     * @see Field#canAccess(Object)
     * @see Field#setAccessible(boolean)
     * @see Field#get(Object)
     * @throws CheckedWrapperException      if this {@code Field} object is enforcing Java language access control and the underlying
     *                                      field is inaccessible.
     * @throws SecurityException            if the request is denied by the security manager
     * @throws IllegalArgumentException
     * <ul>
     *      <li>
     *          if this reflected object is a static member or constructor and the given {@code obj} is non-{@code null}, or
     *      </li>
     *      <li>
     *          if this reflected object is an instance method or field and the given {@code obj} is {@code null} or
     *          of type that is not a subclass of the {@link java.lang.reflect.Member#getDeclaringClass() declaring class} of the member, or
     *      </li>
     *      <li>
     *          if the specified object is not an instance of the class or interface declaring the underlying
     *          field (or a subclass or implementor thereof).
     *      </li>
     * </ul>
     * @throws NullPointerException         if any parameter is null
     * @throws ExceptionInInitializerError  if the initialization provoked by this method fails.
     * @throws java.lang.reflect.InaccessibleObjectException if access cannot be enabled
     */
    public static Object getFieldValue(final Object instance,
                                       final Field field) {
        try {
            final Object validInstance = isStatic(field.getModifiers()) ? null : instance;
            if (!field.canAccess(validInstance)) {
                field.setAccessible(true);
            }
            return field.get(validInstance);
        } catch (final IllegalAccessException ex) {
            throw new CheckedWrapperException(ex);
        }
    }

    /**
     * Returns the field value.
     *
     * @param instance the specified instance
     * @param classInstance the class instance
     * @param fieldName the field name
     * @return the field value.
     * @see Class#getDeclaredField(String)
     * @see Field#canAccess(Object)
     * @see Field#setAccessible(boolean)
     * @see Field#get(Object)
     * @throws CheckedWrapperException
     * <ul>
     *     <li>
     *         if this {@code Field} object is enforcing Java language access control and the underlying field is inaccessible, or
     *     </li>
     *     <li>
     *         if a field with the specified name is not found.
     *     </li>
     * </ul>
     * @throws SecurityException            if the request is denied by the security manager
     * @throws IllegalArgumentException
     * <ul>
     *      <li>
     *          if this reflected object is a static member or constructor and the given {@code obj} is non-{@code null}, or
     *      </li>
     *      <li>
     *          if this reflected object is an instance method or field and the given {@code obj} is {@code null} or
     *          of type that is not a subclass of the {@link java.lang.reflect.Member#getDeclaringClass() declaring class} of the member, or
     *      </li>
     *      <li>
     *          if the specified object is not an instance of the class or interface declaring the underlying
     *          field (or a subclass or implementor thereof).
     *      </li>
     * </ul>
     * @throws NullPointerException         if any parameter is null
     * @throws ExceptionInInitializerError  if the initialization provoked by this method fails.
     * @throws java.lang.reflect.InaccessibleObjectException if access cannot be enabled
     */
    public static Object getFieldValue(final Object instance,
                                       final Class<?> classInstance,
                                       final String fieldName) {
        try {
            final Object validInstance = instance.getClass() == Class.class ? null : instance;
            final Field field = classInstance.getDeclaredField(fieldName);
            return getFieldValue(validInstance, field);
        } catch (final NoSuchFieldException ex) {
            throw new CheckedWrapperException(ex, "Field '?.?' not defined", instance.getClass().getName(), fieldName);
        }
    }

    /**
     * Returns the field value.
     *
     * @param instance the specified instance
     * @param fieldName the field name
     * @return the field value.
     * @see Class#getDeclaredField(String)
     * @see Field#canAccess(Object)
     * @see Field#setAccessible(boolean)
     * @see Field#get(Object)
     * @throws CheckedWrapperException
     * <ul>
     *     <li>
     *         if this {@code Field} object is enforcing Java language access control and the underlying field is inaccessible, or
     *     </li>
     *     <li>
     *         if a field with the specified name is not found.
     *     </li>
     * </ul>
     * @throws SecurityException            if the request is denied by the security manager
     * @throws IllegalArgumentException
     * <ul>
     *      <li>
     *          if this reflected object is a static member or constructor and the given {@code obj} is non-{@code null}, or
     *      </li>
     *      <li>
     *          if this reflected object is an instance method or field and the given {@code obj} is {@code null} or
     *          of type that is not a subclass of the {@link java.lang.reflect.Member#getDeclaringClass() declaring class} of the member, or
     *      </li>
     *      <li>
     *          if the specified object is not an instance of the class or interface declaring the underlying
     *          field (or a subclass or implementor thereof).
     *      </li>
     * </ul>
     * @throws NullPointerException         if any parameter is null
     * @throws ExceptionInInitializerError  if the initialization provoked by this method fails.
     * @throws java.lang.reflect.InaccessibleObjectException if access cannot be enabled
     */
    public static Object getFieldValue(final Object instance,
                                       final String fieldName) {
        final Class<?> classInstance =
                instance.getClass() == Class.class ? (Class<?>) instance : instance.getClass();
        return getFieldValue(instance, classInstance, fieldName);
    }

    /**
     * Returns the declared field instance.
     *
     * @param classInstance the specified class instance
     * @param fieldName the specified field name
     * @return the declared field instance
     * @see Class#getDeclaredField(String)
     * @throws CheckedWrapperException     if a field with the specified name is not found.
     * @throws NullPointerException        if any parameter is null
     * @throws SecurityException           If a security manager, <i>s</i>, is present and any of the following conditions is met:
     * <ul>
     *      <li>
     *          the caller's class loader is not the same as the
     *          class loader of this class and invocation of
     *          {@link SecurityManager#checkPermission
     *          s.checkPermission} method with
     *          {@code RuntimePermission("accessDeclaredMembers")}
     *          denies access to the declared field
     *      </li>
     *      <li>
     *          the caller's class loader is not the same as or an
     *          ancestor of the class loader for the current class and
     *          invocation of {@link SecurityManager#checkPackageAccess
     *          s.checkPackageAccess()} denies access to the package
     *          of this class
     *      </li>
     * </ul>
     */
    public static Field getDeclaredField(final Class<?> classInstance,
                                         final String fieldName) {
        try {
            return classInstance.getDeclaredField(fieldName);
        } catch (final NoSuchFieldException ex) {
            throw new CheckedWrapperException(ex, "Field '?.?' not defined", classInstance.getName(), fieldName);
        }
    }

    /**
     * Sets the field value.
     *
     * @param instance the specified instances
     * @param fieldName the specified field name
     * @param value the new value to set
     * @see Class#getDeclaredField(String)
     * @see Field#canAccess(Object)
     * @see Field#setAccessible(boolean)
     * @see Field#set(Object, Object)
     * @throws IllegalArgumentException
     * <ul>
     *      <li>
     *          if this reflected object is a static member or constructor and the given {@code obj} is non-{@code null}, or
     *      </li>
     *      <li>
     *          if this reflected object is an instance method or field and the given {@code obj} is {@code null} or of type
     *          that is not a subclass of the {@link java.lang.reflect.Member#getDeclaringClass() declaring class} of the member, or
     *      </li>
     *      <li>
     *          if current field is final
     *      </li>
     *      <li>
     *          if the specified instances are invalid
     *      </li>
     * </ul>
     * @throws SecurityException            if the request is denied by the security manager
     * @throws ExceptionInInitializerError  if the initialization provoked by this method fails.
     * @throws NullPointerException         if any parameter is null
     * @throws CheckedWrapperException      if this {@code Field} object is enforcing Java language access control and the underlying
     *                                      field is either inaccessible or final.
     * @throws java.lang.reflect.InaccessibleObjectException if access cannot be enabled
     */
    public static void setFieldValue(final Object instance,
                                     final String fieldName,
                                     final Object value) {
        try {
            final Class<?> classInstance =
                    instance.getClass() == Class.class ? (Class<?>) instance : instance.getClass();
            final Object validInstance = instance.getClass() == Class.class ? null : instance;
            final Field field = classInstance.getDeclaredField(fieldName);
            setFieldValue(validInstance, field, value);
        } catch (final NoSuchFieldException ex) {
            throw new CheckedWrapperException(ex, "Field '?.?' not defined", instance.getClass().getName(), fieldName);
        }
    }

    /**
     * Sets the field value.
     *
     * @param instances the specified instances
     * @param field the specified field instance
     * @param value the new value to set
     * @see Field#canAccess(Object)
     * @see Field#setAccessible(boolean)
     * @see Field#set(Object, Object)
     * @throws IllegalArgumentException
     * <ul>
     *      <li>
     *          if this reflected object is a static member or constructor and the given {@code obj} is non-{@code null}, or
     *      </li>
     *      <li>
     *          if this reflected object is an instance method or field and the given {@code obj} is {@code null} or of type
     *          that is not a subclass of the {@link java.lang.reflect.Member#getDeclaringClass() declaring class} of the member, or
     *      </li>
     *      <li>
     *          if current field is final
     *      </li>
     *      <li>
     *          if the specified instances are invalid
     *      </li>
     * </ul>
     * @throws SecurityException            if the request is denied by the security manager
     * @throws ExceptionInInitializerError  if the initialization provoked by this method fails.
     * @throws NullPointerException         if any parameter is null
     * @throws CheckedWrapperException      if this {@code Field} object is enforcing Java language access control and the underlying
     *                                      field is either inaccessible or final.
     * @throws java.lang.reflect.InaccessibleObjectException if access cannot be enabled
     */
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

    /**
     * Sets the field value.
     *
     * @param instance the specified instance
     * @param field the specified field instance
     * @param value the new value to set
     * @see Field#canAccess(Object)
     * @see Field#setAccessible(boolean)
     * @see Field#set(Object, Object)
     * @throws IllegalArgumentException
     * <ul>
     *      <li>
     *          if this reflected object is a static member or constructor and the given {@code obj} is non-{@code null}, or
     *      </li>
     *      <li>
     *          if this reflected object is an instance method or field and the given {@code obj} is {@code null} or of type
     *          that is not a subclass of the {@link java.lang.reflect.Member#getDeclaringClass() declaring class} of the member, or
     *      </li>
     *      <li>
     *          if current field is final
     *      </li>
     * </ul>
     * @throws SecurityException            if the request is denied by the security manager
     * @throws ExceptionInInitializerError  if the initialization provoked by this method fails.
     * @throws NullPointerException         if any parameter is null
     * @throws CheckedWrapperException      if this {@code Field} object is enforcing Java language access control and the underlying
     *                                      field is either inaccessible or final.
     * @throws java.lang.reflect.InaccessibleObjectException if access cannot be enabled
     */
    public static void setFieldValue(final Object instance,
                                     final Field field,
                                     final Object value) {
        try {
            final Object validInstance = isStatic(field.getModifiers()) ? null : instance;
            if (!field.canAccess(validInstance)) {
                field.setAccessible(true);
            }
            if (isFinal(field.getModifiers())) {
                setFinalFieldValue(validInstance, field, value);
            } else {
                field.set(validInstance, value);
            }
        } catch (final IllegalAccessException ex) {
            throw new CheckedWrapperException(ex);
        }
    }

    /**
     * Invokes the method by name.
     *
     * @param instances  the specified instances
     * @param methodName the specified method name
     * @param args       the arguments for the method
     * @return the method result
     * @see Method#canAccess(Object)
     * @see Method#setAccessible(boolean)
     * @see Method#invoke(Object, Object...)
     * @throws CheckedWrapperException
     * <ul>
     *     <li>
     *         if this {@code Method} object is enforcing Java language access control and the underlying method is inaccessible, or
     *     </li>
     *     <li>
     *         if the underlying method throws an exception.
     *     </li>
     * </ul>
     * @throws SecurityException            if the request is denied by the security manager
     * @throws IllegalArgumentException
     * <ul>
     *      <li>
     *          if this reflected object is a static member or constructor and the given {@code obj} is non-{@code null}, or
     *      </li>
     *      <li>
     *          if this reflected object is an instance method or field and the given {@code obj} is {@code null} or
     *          of type that is not a subclass of the {@link java.lang.reflect.Member#getDeclaringClass() declaring class} of the member, or
     *      </li>
     *      <li>
     *          if the specified object is not an instance of the class or interface declaring the underlying
     *          field (or a subclass or implementor thereof), or
     *      </li>
     *      <li>
     *          if the specified instances are invalid
     *      </li>
     * </ul>
     * @throws NullPointerException         if any parameter is null
     * @throws ExceptionInInitializerError  if the initialization provoked by this method fails.
     * @throws java.lang.reflect.InaccessibleObjectException if access cannot be enabled
     */
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
            } catch (final NoSuchMethodException ignore) {
                // do nothing
            }
        }
        throw new IllegalArgumentException(format(
                "At least one class must contain the method 'void ?()': classes=?",
                methodName, instances.stream().map(Object::getClass).collect(toList())
        ));
    }

    /**
     * Invokes the method by name.
     *
     * @param instance the specified instance
     * @param method   the specified method name
     * @param args     the arguments for the method
     * @return the method result
     * @see Method#invoke(Object, Object...)
     * @throws CheckedWrapperException
     * <ul>
     *     <li>
     *         if this {@code Method} object is enforcing Java language access control and the underlying method is inaccessible, or
     *     </li>
     *     <li>
     *         if the underlying method throws an exception.
     *     </li>
     * </ul>
     * @throws SecurityException            if the request is denied by the security manager
     * @throws IllegalArgumentException
     * <ul>
     *      <li>
     *          if this reflected object is a static member or constructor and the given {@code obj} is non-{@code null}, or
     *      </li>
     *      <li>
     *          if this reflected object is an instance method or field and the given {@code obj} is {@code null} or
     *          of type that is not a subclass of the {@link java.lang.reflect.Member#getDeclaringClass() declaring class} of the member, or
     *      </li>
     *      <li>
     *          if the specified object is not an instance of the class or interface declaring the underlying
     *          field (or a subclass or implementor thereof), or
     *      </li>
     * </ul>
     * @throws NullPointerException         if any parameter is null
     * @throws ExceptionInInitializerError  if the initialization provoked by this method fails.
     * @throws java.lang.reflect.InaccessibleObjectException if access cannot be enabled
     */
    public static Object invokeMethod(final Object instance,
                                      final Method method,
                                      final Object... args) {
        try {
            return method.invoke(instance, args);
        } catch (final IllegalAccessException ex) {
            throw new CheckedWrapperException(ex);
        } catch (final InvocationTargetException ex) {
            throw new CheckedWrapperException(ex.getTargetException());
        }
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

    private Reflections() {
    }
}