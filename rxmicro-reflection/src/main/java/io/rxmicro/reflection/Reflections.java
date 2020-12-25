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

import io.rxmicro.common.CheckedWrapperException;
import io.rxmicro.reflection.internal.FinalFieldUpdater;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import static io.rxmicro.common.util.ExCollections.unmodifiableOrderedSet;
import static io.rxmicro.common.util.Formats.format;
import static java.lang.reflect.Modifier.isFinal;
import static java.lang.reflect.Modifier.isPublic;
import static java.lang.reflect.Modifier.isStatic;
import static java.util.Collections.unmodifiableList;
import static java.util.stream.Collectors.joining;
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
     * <p>
     * Fields from super class are returned first.
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
            fields.addAll(0, Arrays.stream(current.getDeclaredFields())
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
     * <p>
     * Methods from super class are returned first.
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
            methods.addAll(0, Arrays.stream(current.getDeclaredMethods())
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
     * <p>
     * Parent classes are returned first.
     *
     * @param clazz the specified class
     * @return the unmodifiable {@link Set} of all parents for the specified class
     * @throws NullPointerException if any parameter is null
     */
    public static Set<Class<?>> allSuperClasses(final Class<?> clazz) {
        final List<Class<?>> set = new ArrayList<>();
        Class<?> currentClass = clazz.getSuperclass();
        while (currentClass != null && currentClass != Object.class) {
            set.add(0, currentClass);
            currentClass = currentClass.getSuperclass();
        }
        return unmodifiableOrderedSet(set);
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
        final Field field = findField(classInstance, fieldName);
        return getFieldValue(instance, field);
    }

    /**
     * Returns the field value.
     *
     * @param instance the specified instance. If requested field is static the specified instance can be {@link Class} instance.
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
     * Returns the declared field from instance or from any it parent.
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
        return findField(classInstance, fieldName);
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
        final Class<?> classInstance =
                instance.getClass() == Class.class ? (Class<?>) instance : instance.getClass();
        final Field field = findField(classInstance, fieldName);
        final Object validInstance = instance.getClass() == Class.class ? null : instance;
        setFieldValue(validInstance, field, value);
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
                FinalFieldUpdater.setFinalFieldValue(validInstance, field, value);
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
            Method method;
            try {
                method = findMethod(instance.getClass(), methodName, parameterTypes);
            } catch (final CheckedWrapperException ignore) {
                method = null;
            }
            if (method != null) {
                if (!method.canAccess(instance)) {
                    method.setAccessible(true);
                }
                final Object validInstance = isStatic(method.getModifiers()) ? null : instance;
                return invokeMethod(validInstance, method, args);
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
     * @param instance  the specified instance
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
     * </ul>
     * @throws NullPointerException         if any parameter is null
     * @throws ExceptionInInitializerError  if the initialization provoked by this method fails.
     * @throws java.lang.reflect.InaccessibleObjectException if access cannot be enabled
     */
    @SuppressWarnings("UnusedReturnValue")
    public static Object invokeMethod(final Object instance,
                                      final String methodName,
                                      final Object... args) {
        final Class<?>[] parameterTypes = Arrays.stream(args).map(Object::getClass).toArray(Class[]::new);
        final Method method = findMethod(instance.getClass(), methodName, parameterTypes);
        final Object validInstance = isStatic(method.getModifiers()) ? null : instance;
        if (!method.canAccess(instance)) {
            method.setAccessible(true);
        }
        return invokeMethod(validInstance, method, args);
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
        } catch (final IllegalAccessException | InvocationTargetException ex) {
            throw new CheckedWrapperException(ex);
        }
    }

    /**
     * Creates an instance of the target class using provided constructor arguments.
     *
     * @param targetClassName the target class full name.
     * @param constructorArgs constructor argument values.
     * @param <T> created type
     * @return an instance of the target class using provided constructor arguments.
     * @throws SecurityException If a security manager, <i>s</i>, is present and the caller's class loader is not the same as or
     *              an ancestor of the class loader for the current class and invocation of
     *              {@link SecurityManager#checkPackageAccess s.checkPackageAccess()} denies access to the package of this class.
     * @throws CheckedWrapperException
     * <ul>
     *     <li>
     *         If class not found by name.
     *     </li>
     *     <li>
     *         if the underlying constructor with the specified argument types is not defined or
     *     </li>
     *     <li>
     *         if this {@code Constructor} object is enforcing Java language access control and the underlying constructor is inaccessible.
     *     </li>
     *     <li>
     *         if the class that declares the underlying constructor represents an abstract class or interface or annotation.
     *     </li>
     *     <li>
     *         if the underlying constructor throws an exception.
     *     </li>
     * </ul>
     * @throws IllegalArgumentException
     * <ul>
     *     <li>
     *         if the number of actual and formal parameters differ
     *     </li>
     *     <li>
     *         if an unwrapping conversion for primitive arguments fails
     *     </li>
     *     <li>
     *         if after possible unwrapping, a parameter value cannot be converted to the corresponding formal parameter type
     *         by a method invocation conversion;
     *     </li>
     *     <li>
     *         if this constructor pertains to an enum type
     *     </li>
     * </ul>
     * @throws ExceptionInInitializerError if the initialization provoked by this method fails.
     */
    @SuppressWarnings("unchecked")
    public static <T> T instantiate(final String targetClassName,
                                    final Object... constructorArgs) {
        try {
            return (T) instantiate(Class.forName(targetClassName), constructorArgs);
        } catch (final ClassNotFoundException ex) {
            throw new CheckedWrapperException(ex, "Class ? not found", targetClassName);
        }
    }

    /**
     * Creates an instance of the target class using provided constructor arguments.
     *
     * @param targetClassName the target class full name.
     * @param argTypes array of constructor argument types.
     * @param constructorArgs constructor argument values.
     * @param <T> created type
     * @return an instance of the target class using provided constructor arguments.
     * @throws SecurityException If a security manager, <i>s</i>, is present and the caller's class loader is not the same as or
     *              an ancestor of the class loader for the current class and invocation of
     *              {@link SecurityManager#checkPackageAccess s.checkPackageAccess()} denies access to the package of this class.
     * @throws CheckedWrapperException
     * <ul>
     *     <li>
     *         If class not found by name.
     *     </li>
     *     <li>
     *         if the underlying constructor with the specified argument types is not defined or
     *     </li>
     *     <li>
     *         if this {@code Constructor} object is enforcing Java language access control and the underlying constructor is inaccessible.
     *     </li>
     *     <li>
     *         if the class that declares the underlying constructor represents an abstract class or interface or annotation.
     *     </li>
     *     <li>
     *         if the underlying constructor throws an exception.
     *     </li>
     * </ul>
     * @throws IllegalArgumentException
     * <ul>
     *     <li>
     *         if the number of actual and formal parameters differ
     *     </li>
     *     <li>
     *         if an unwrapping conversion for primitive arguments fails
     *     </li>
     *     <li>
     *         if after possible unwrapping, a parameter value cannot be converted to the corresponding formal parameter type
     *         by a method invocation conversion;
     *     </li>
     *     <li>
     *         if this constructor pertains to an enum type
     *     </li>
     * </ul>
     * @throws ExceptionInInitializerError if the initialization provoked by this method fails.
     */
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

    /**
     * Creates an instance of the target class using provided constructor arguments.
     *
     * @param targetClass the target class
     * @param constructorArgs constructor argument values.
     * @param <T> created type
     * @return an instance of the target class using provided constructor arguments.
     * @throws SecurityException If a security manager, <i>s</i>, is present and the caller's class loader is not the same as or
     *              an ancestor of the class loader for the current class and invocation of
     *              {@link SecurityManager#checkPackageAccess s.checkPackageAccess()} denies access to the package of this class.
     * @throws CheckedWrapperException
     * <ul>
     *     <li>
     *         if the underlying constructor with the specified argument types is not defined or
     *     </li>
     *     <li>
     *         if this {@code Constructor} object is enforcing Java language access control and the underlying constructor is inaccessible.
     *     </li>
     *     <li>
     *         if the class that declares the underlying constructor represents an abstract class or interface or annotation.
     *     </li>
     *     <li>
     *         if the underlying constructor throws an exception.
     *     </li>
     * </ul>
     * @throws IllegalArgumentException
     * <ul>
     *     <li>
     *         if the number of actual and formal parameters differ
     *     </li>
     *     <li>
     *         if an unwrapping conversion for primitive arguments fails
     *     </li>
     *     <li>
     *         if after possible unwrapping, a parameter value cannot be converted to the corresponding formal parameter type
     *         by a method invocation conversion;
     *     </li>
     *     <li>
     *         if this constructor pertains to an enum type
     *     </li>
     *     <li>
     *         if any constructor argument is {@code null}
     *     </li>
     * </ul>
     * @throws ExceptionInInitializerError if the initialization provoked by this method fails.
     */
    public static <T> T instantiate(final Class<T> targetClass,
                                    final Object... constructorArgs) {
        final Class<?>[] argTypes = Arrays.stream(constructorArgs)
                .peek(arg -> {
                    if (arg == null) {
                        throw new IllegalArgumentException(
                                format("Can't instantiate ? class, because detected `null` argument value. " +
                                                "Use instantiate(Class<T>, Class<?>[], Object...) method instead!",
                                        targetClass.getName()
                                )
                        );
                    }
                })
                .map(Object::getClass)
                .toArray(Class[]::new);
        return instantiate(targetClass, argTypes, constructorArgs);
    }

    /**
     * Creates an instance of the target class using provided constructor arguments.
     *
     * @param targetClass the target class
     * @param argTypes array of constructor argument types.
     * @param constructorArgs constructor argument values.
     * @param <T> created type
     * @return an instance of the target class using provided constructor arguments.
     * @throws SecurityException If a security manager, <i>s</i>, is present and the caller's class loader is not the same as or
     *              an ancestor of the class loader for the current class and invocation of
     *              {@link SecurityManager#checkPackageAccess s.checkPackageAccess()} denies access to the package of this class.
     * @throws CheckedWrapperException
     * <ul>
     *     <li>
     *         if the underlying constructor with the specified argument types is not defined or
     *     </li>
     *     <li>
     *         if this {@code Constructor} object is enforcing Java language access control and the underlying constructor is inaccessible.
     *     </li>
     *     <li>
     *         if the class that declares the underlying constructor represents an abstract class or interface or annotation.
     *     </li>
     *     <li>
     *         if the underlying constructor throws an exception.
     *     </li>
     * </ul>
     * @throws IllegalArgumentException
     * <ul>
     *     <li>
     *         if the number of actual and formal parameters differ
     *     </li>
     *     <li>
     *         if an unwrapping conversion for primitive arguments fails
     *     </li>
     *     <li>
     *         if after possible unwrapping, a parameter value cannot be converted to the corresponding formal parameter type
     *         by a method invocation conversion;
     *     </li>
     *     <li>
     *         if this constructor pertains to an enum type
     *     </li>
     * </ul>
     * @throws ExceptionInInitializerError if the initialization provoked by this method fails.
     */
    public static <T> T instantiate(final Class<T> targetClass,
                                    final Class<?>[] argTypes,
                                    final Object... constructorArgs) {
        try {
            return targetClass.getConstructor(argTypes).newInstance(constructorArgs);
        } catch (final NoSuchMethodException ex) {
            throw new CheckedWrapperException(
                    ex,
                    "Class ? must contain a required constructor: public <init>(?)",
                    targetClass.getName(),
                    Arrays.stream(argTypes)
                            .map(Class::getName)
                            .collect(joining(","))
            );
        } catch (final IllegalAccessException | InstantiationException ex) {
            throw new CheckedWrapperException(ex, "Can't instantiate ? class: ?", targetClass.getName(), ex.getMessage());
        } catch (final InvocationTargetException ex) {
            throw new CheckedWrapperException(
                    ex,
                    "Can't instantiate ? class, because constructor throws an exception with message: ?",
                    targetClass.getName(), ex.getTargetException().getMessage()
            );
        }
    }

    /**
     * Creates an instance of the target class using provided constructor arguments.
     *
     * @param targetClass the target class
     * @param <T> created type
     * @return an instance of the target class using provided constructor arguments.
     * @throws SecurityException If a security manager, <i>s</i>, is present and the caller's class loader is not the same as or
     *              an ancestor of the class loader for the current class and invocation of
     *              {@link SecurityManager#checkPackageAccess s.checkPackageAccess()} denies access to the package of this class.
     * @throws CheckedWrapperException
     * <ul>
     *     <li>
     *         if the underlying constructor with the specified argument types is not defined or
     *     </li>
     *     <li>
     *         if this {@code Constructor} object is enforcing Java language access control and the underlying constructor is inaccessible.
     *     </li>
     *     <li>
     *         if the class that declares the underlying constructor represents an abstract class or interface or annotation.
     *     </li>
     *     <li>
     *         if the underlying constructor throws an exception.
     *     </li>
     * </ul>
     * @throws IllegalArgumentException
     * <ul>
     *     <li>
     *         if the number of actual and formal parameters differ
     *     </li>
     *     <li>
     *         if an unwrapping conversion for primitive arguments fails
     *     </li>
     *     <li>
     *         if after possible unwrapping, a parameter value cannot be converted to the corresponding formal parameter type
     *         by a method invocation conversion;
     *     </li>
     *     <li>
     *         if this constructor pertains to an enum type
     *     </li>
     * </ul>
     * @throws ExceptionInInitializerError if the initialization provoked by this method fails.
     */
    public static <T> T instantiate(final Class<T> targetClass) {
        return instantiate(targetClass, new Class[0], new Object[0]);
    }

    /**
     * Returns the {@code methodNameCandidate} if the specified class contains the public method with name: {@code methodNameCandidate}.
     * Otherwise throws {@link CheckedWrapperException} exception.
     *
     * @param clazz the specified class
     * @param methodNameCandidate the method name candidate
     * @return the {@code methodNameCandidate} if the specified class contains the public method with name: {@code methodNameCandidate}.
     * @throws CheckedWrapperException if the specified class does not contain the public method with name: {@code methodNameCandidate}.
     */
    public static String getValidatedMethodName(final Class<?> clazz,
                                                final String methodNameCandidate) {
        try {
            return clazz.getMethod(methodNameCandidate).getName();
        } catch (final NoSuchMethodException ignore) {
            for (final Method method : clazz.getMethods()) {
                if (method.getName().equals(methodNameCandidate)) {
                    return methodNameCandidate;
                }
            }
            throw new CheckedWrapperException(new NoSuchMethodException(
                    format("'?' class does not contain public method with name: '?'!", clazz.getName(), methodNameCandidate)
            ));
        }
    }

    /**
     * Returns the {@code fieldNameCandidate} if the specified class contains the public field with name: {@code fieldNameCandidate}.
     * Otherwise throws {@link CheckedWrapperException} exception.
     *
     * @param clazz the specified class
     * @param fieldNameCandidate the field name candidate
     * @return the {@code fieldNameCandidate} if the specified class contains the public field with name: {@code fieldNameCandidate}.
     * @throws CheckedWrapperException if the specified class does not contain the public field with name: {@code fieldNameCandidate}.
     */
    public static String getValidatedFieldName(final Class<?> clazz,
                                               final String fieldNameCandidate) {
        try {
            return clazz.getField(fieldNameCandidate).getName();
        } catch (final NoSuchFieldException ignore) {
            for (final Field field : clazz.getFields()) {
                if (field.getName().equals(fieldNameCandidate)) {
                    return fieldNameCandidate;
                }
            }
            throw new CheckedWrapperException(new NoSuchFieldException(
                    format("'?' class does not contain public field with name: '?'!", clazz.getName(), fieldNameCandidate)
            ));
        }
    }

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

    private static boolean isPublicSetter(final Method method) {
        return method.getName().startsWith("set") &&
                isPublic(method.getModifiers()) &&
                method.getParameterCount() == 1;
    }

    private static boolean isValidInstance(final Member member,
                                           final Object instance) {
        if (instance == null) {
            return isStatic(member.getModifiers());
        } else {
            return instance.getClass() == member.getDeclaringClass() ||
                    allSuperClasses(instance.getClass()).contains(member.getDeclaringClass());
        }
    }

    private static Field findField(final Class<?> clazz,
                                   final String fieldName) {
        try {
            return clazz.getDeclaredField(fieldName);
        } catch (final NoSuchFieldException ex) {
            for (final Class<?> superClass : allSuperClasses(clazz)) {
                try {
                    return superClass.getDeclaredField(fieldName);
                } catch (final NoSuchFieldException ignore) {
                    // do nothing
                }
            }
            throw new CheckedWrapperException(ex, "Field '?.?' not defined", clazz.getName(), fieldName);
        }
    }

    private static Method findMethod(final Class<?> clazz,
                                     final String methodName,
                                     final Class<?>... parameterTypes) {
        try {
            return clazz.getDeclaredMethod(methodName, parameterTypes);
        } catch (final NoSuchMethodException ex) {
            for (final Class<?> superClass : allSuperClasses(clazz)) {
                try {
                    return superClass.getDeclaredMethod(methodName, parameterTypes);
                } catch (final NoSuchMethodException ignore) {
                    // do nothing
                }
            }
            throw new CheckedWrapperException(
                    ex,
                    "Method '?.?(?)' not defined",
                    clazz.getName(),
                    methodName,
                    Arrays.stream(parameterTypes).map(Class::getName).collect(joining(", "))
            );
        }
    }

    private Reflections() {
    }
}
