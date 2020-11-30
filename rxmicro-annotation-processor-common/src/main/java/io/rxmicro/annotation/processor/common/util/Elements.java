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

package io.rxmicro.annotation.processor.common.util;

import io.rxmicro.annotation.processor.common.model.type.ObjectModelClass;
import io.rxmicro.annotation.processor.common.model.virtual.VirtualTypeElement;
import io.rxmicro.annotation.processor.common.model.virtual.VirtualTypeMirror;
import io.rxmicro.model.NotStandardSerializableEnum;
import io.rxmicro.model.Transient;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Predicate;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.NoType;
import javax.lang.model.type.TypeMirror;

import static io.rxmicro.annotation.processor.common.util.Names.getPackageName;
import static io.rxmicro.annotation.processor.common.util.ProcessingEnvironmentHelper.getTypes;
import static io.rxmicro.common.local.DeniedPackages.isDeniedPackage;
import static io.rxmicro.common.util.ExCollectors.toUnmodifiableOrderedSet;
import static io.rxmicro.common.util.Strings.capitalize;
import static java.util.stream.Collectors.toList;
import static javax.lang.model.element.ElementKind.CONSTRUCTOR;
import static javax.lang.model.element.ElementKind.ENUM;
import static javax.lang.model.element.ElementKind.ENUM_CONSTANT;
import static javax.lang.model.element.ElementKind.FIELD;
import static javax.lang.model.element.ElementKind.METHOD;
import static javax.lang.model.element.Modifier.DEFAULT;
import static javax.lang.model.element.Modifier.PRIVATE;
import static javax.lang.model.element.Modifier.PUBLIC;
import static javax.lang.model.element.Modifier.STATIC;
import static javax.lang.model.element.Modifier.TRANSIENT;
import static javax.lang.model.element.Modifier.VOLATILE;

/**
 * @author nedis
 * @since 0.1
 */
public final class Elements {

    public static final Comparator<TypeElement> UNIQUE_TYPES_COMPARATOR =
            Comparator.comparing(o -> o.getQualifiedName().toString());

    public static Set<String> getAllowedEnumConstants(final TypeMirror typeMirror) {
        return asEnumElement(typeMirror)
                .map(Elements::getAllowedEnumConstants)
                .orElse(Set.of());
    }

    public static Set<String> getAllowedEnumConstants(final TypeElement typeElement) {
        return typeElement.getEnclosedElements().stream()
                .filter(e -> e.getKind() == ENUM_CONSTANT)
                .map(e -> e.getSimpleName().toString())
                .collect(toUnmodifiableOrderedSet());
    }

    public static int expectedGenericArgumentCount(final TypeMirror typeMirror) {
        return asTypeElement(typeMirror)
                .map(te -> te.getTypeParameters().size())
                .orElse(0);
    }

    public static boolean isGenericType(final TypeMirror typeMirror) {
        return asTypeElement(typeMirror)
                .map(te -> !te.getTypeParameters().isEmpty())
                .orElse(false);
    }

    public static boolean isVirtualTypeElement(final TypeElement typeElement) {
        return typeElement instanceof VirtualTypeElement;
    }

    public static boolean isNotStandardEnum(final TypeMirror typeMirror) {
        return asEnumElement(typeMirror)
                .map(te -> te.getInterfaces().stream()
                        .anyMatch(i -> NotStandardSerializableEnum.class.getName().equals(i.toString())))
                .orElse(false);
    }

    public static List<VariableElement> allModelFields(final TypeElement typeElement) {
        return allModelFields(typeElement, true);
    }

    public static List<VariableElement> allModelFields(final TypeElement typeElement,
                                                       final boolean withFieldsFromParentClasses) {
        final Set<Modifier> excludeModifiers = Set.of(STATIC, TRANSIENT, VOLATILE);
        return allFields(
                typeElement,
                withFieldsFromParentClasses,
                el -> el.getModifiers().stream().noneMatch(excludeModifiers::contains) && el.getAnnotation(Transient.class) == null
        );
    }

    public static List<VariableElement> allFields(final TypeElement typeElement,
                                                  final Predicate<VariableElement> filter) {
        return allFields(typeElement, true, filter);
    }

    /**
     * Returns ordered declared fields: fields that declared at super class are returned at the beginning of list
     *
     * @see ObjectModelClass#getAllOrderedDeclaredFields()
     */
    public static List<VariableElement> allFields(final TypeElement typeElement,
                                                  final boolean withFieldsFromParentClasses,
                                                  final Predicate<VariableElement> filter) {
        final List<VariableElement> fields = new ArrayList<>();
        TypeElement currentTypeElement = typeElement;
        while (true) {
            // Some the RxMicro Annotation processor logic depends on fields order: For example AbstractModelClassHierarchyBuilder
            // component requires that fields that declared at super class were at the beginning of returning list!!!
            fields.addAll(0, currentTypeElement.getEnclosedElements().stream()
                    .filter(el -> el.getKind() == FIELD)
                    .map(el -> (VariableElement) el)
                    .filter(filter)
                    .collect(toList()));
            final TypeMirror superClass = currentTypeElement.getSuperclass();
            if (superClassIsObject(superClass) || !withFieldsFromParentClasses) {
                break;
            } else {
                currentTypeElement = asTypeElement(superClass).orElseThrow();
            }
        }
        return fields;
    }

    public static List<ExecutableElement> allConstructors(final TypeElement typeElement) {
        return allConstructors(typeElement, e -> true);
    }

    public static List<ExecutableElement> allConstructors(final TypeElement typeElement,
                                                          final Predicate<ExecutableElement> filter) {
        return typeElement.getEnclosedElements().stream()
                .filter(el -> el.getKind() == CONSTRUCTOR)
                .map(el -> (ExecutableElement) el)
                .filter(filter)
                .collect(toList());
    }

    public static List<ExecutableElement> allMethodsFromType(final TypeElement typeElement) {
        return allMethodsFromType(typeElement, e -> true);
    }

    public static List<ExecutableElement> allMethodsFromType(final TypeElement typeElement,
                                                             final Predicate<ExecutableElement> filter) {
        return typeElement.getEnclosedElements().stream()
                .filter(el -> el.getKind() == METHOD)
                .map(el -> (ExecutableElement) el)
                .filter(filter)
                .collect(toList());
    }

    public static List<ExecutableElement> allImplementableMethods(final TypeElement typeElement) {
        return allMethods(typeElement, e -> {
            if (e.getAnnotationMirrors().stream().anyMatch(a -> a.getAnnotationType().toString().startsWith("io.rxmicro."))) {
                return true;
            } else {
                return Set.of(STATIC, PRIVATE, DEFAULT).stream().noneMatch(m -> e.getModifiers().contains(m));
            }
        });
    }

    public static List<ExecutableElement> allMethods(final TypeElement typeElement) {
        return allMethods(typeElement, e -> true);
    }

    public static List<ExecutableElement> allMethods(final TypeElement typeElement,
                                                     final Predicate<ExecutableElement> filter) {
        final List<ExecutableElement> methods = new ArrayList<>();
        TypeElement currentTypeElement = typeElement;
        while (true) {
            methods.addAll(0, currentTypeElement.getEnclosedElements().stream()
                    .filter(el -> el.getKind() == METHOD)
                    .map(el -> (ExecutableElement) el)
                    .filter(filter)
                    .collect(toList()));
            final TypeMirror superClass = currentTypeElement.getSuperclass();
            if (superClassIsObject(superClass)) {
                break;
            } else {
                currentTypeElement = asTypeElement(superClass).orElseThrow();
            }
        }
        return methods;
    }

    public static boolean superClassIsObject(final TypeMirror superClass) {
        return superClass instanceof NoType || Object.class.getName().equals(superClass.toString());
    }

    public static Optional<TypeElement> asTypeElement(final TypeMirror typeMirror) {
        if (typeMirror instanceof VirtualTypeMirror) {
            return Optional.of(((VirtualTypeMirror) typeMirror).getVirtualTypeElement());
        } else {
            return Optional.ofNullable(getTypes().asElement(typeMirror))
                    .filter(e -> e instanceof TypeElement)
                    .map(e -> (TypeElement) e);
        }
    }

    public static Optional<TypeElement> asEnumElement(final TypeMirror typeMirror) {
        return asTypeElement(typeMirror)
                .filter(e -> e.getKind() == ENUM);
    }

    public static List<ExecutableElement> findGetters(final TypeElement typeElement,
                                                      final VariableElement variableElement) {
        return findGetterOrSetter(typeElement, variableElement, true);
    }

    public static List<ExecutableElement> findSetters(final TypeElement typeElement,
                                                      final VariableElement variableElement) {
        return findGetterOrSetter(typeElement, variableElement, false);
    }

    public static Optional<ExecutableElement> findSetter(final TypeElement typeElement,
                                                         final String propertyName) {
        final Set<String> methodNames = Set.of(
                "set" + capitalize(propertyName),
                "set" + propertyName
        );
        TypeElement currentTypeElement = typeElement;
        while (true) {
            final List<ExecutableElement> methods = currentTypeElement.getEnclosedElements().stream()
                    .filter(el -> el.getKind() == METHOD &&
                            !el.getModifiers().contains(STATIC) &&
                            methodNames.contains(el.getSimpleName().toString()) &&
                            el.getModifiers().contains(PUBLIC))
                    .map(e -> (ExecutableElement) e)
                    .filter(el -> el.getParameters().size() == 1)
                    .collect(toList());
            if (!methods.isEmpty()) {
                return Optional.of(methods.get(0));
            }
            final TypeMirror superClass = currentTypeElement.getSuperclass();
            if (superClassIsObject(superClass)) {
                break;
            } else {
                currentTypeElement = asTypeElement(superClass).orElseThrow();
            }
        }
        return Optional.empty();
    }

    private static List<ExecutableElement> findGetterOrSetter(final TypeElement typeElement,
                                                              final VariableElement variableElement,
                                                              final boolean getter) {
        final String fieldType = variableElement.asType().toString();
        final String fieldName = variableElement.getSimpleName().toString();
        final Set<String> methodNames = getSettersOrGettersMethodNames(getter, fieldName);
        final List<ExecutableElement> methods = new ArrayList<>();
        TypeElement currentTypeElement = typeElement;
        while (true) {
            methods.addAll(currentTypeElement.getEnclosedElements().stream()
                    .filter(el -> el.getKind() == METHOD &&
                            !el.getModifiers().contains(STATIC) &&
                            methodNames.contains(el.getSimpleName().toString()) &&
                            el.getModifiers().contains(PUBLIC))
                    .map(e -> (ExecutableElement) e)
                    .filter(el -> getter ?
                            el.getReturnType().toString().equals(fieldType) :
                            el.getParameters().size() == 1 &&
                                    el.getParameters().get(0).asType().toString().equals(fieldType))
                    .collect(toList()));
            final TypeMirror superClass = currentTypeElement.getSuperclass();
            if (superClassIsObject(superClass)) {
                break;
            } else {
                currentTypeElement = asTypeElement(superClass).orElseThrow();
            }
        }
        return methods;
    }

    private static Set<String> getSettersOrGettersMethodNames(final boolean getter,
                                                              final String fieldName) {
        if (getter) {
            return Set.copyOf(List.of(
                    "get" + capitalize(fieldName),
                    "get" + fieldName,
                    "is" + capitalize(fieldName),
                    "is" + fieldName
            ));
        } else {
            return Set.copyOf(List.of(
                    "set" + capitalize(fieldName),
                    "set" + fieldName
            ));
        }
    }

    public static boolean methodSignatureEquals(final ExecutableElement method1,
                                                final ExecutableElement method2) {
        return method2.getSimpleName().toString().equals(method1.getSimpleName().toString()) &&
                method2.getParameters().stream().map(v -> v.asType().toString()).collect(toList())
                        .equals(method1.getParameters().stream().map(v -> v.asType().toString()).collect(toList()));
    }

    public static boolean doesExtendSuperType(final TypeElement type,
                                              final Class<?> expectedSuperType) {
        return findSuperType(type, expectedSuperType).isPresent();
    }

    public static Optional<? extends TypeMirror> findSuperType(final TypeElement type,
                                                               final Class<?> expectedSuperType) {
        TypeElement currentTypeElement = type;
        while (true) {
            final Optional<? extends TypeMirror> first = currentTypeElement.getInterfaces().stream()
                    .filter(t -> getTypes().erasure(t).toString().equals(expectedSuperType.getName()))
                    .findFirst();
            if (first.isPresent()) {
                return first;
            }
            final TypeMirror superClass = currentTypeElement.getSuperclass();
            if (superClassIsObject(superClass)) {
                return Optional.empty();
            } else if (getTypes().erasure(superClass).toString().equals(expectedSuperType.getName())) {
                return Optional.of(superClass);
            } else {
                currentTypeElement = asTypeElement(superClass).orElseThrow();
            }
        }
    }

    public static List<TypeElement> allSuperTypes(final TypeElement type) {
        final List<TypeElement> list = new ArrayList<>();
        TypeElement currentTypeElement = type;
        while (true) {
            final TypeMirror superClass = currentTypeElement.getSuperclass();
            if (superClassIsObject(superClass)) {
                break;
            } else {
                currentTypeElement = asTypeElement(superClass).orElseThrow();
                if (isDeniedPackage(getPackageName(currentTypeElement))) {
                    break;
                }
                list.add(currentTypeElement);
            }
        }
        return list;
    }

    public static SortedSet<TypeElement> allSuperTypesAndInterfaces(final TypeElement type,
                                                                    final boolean withThis,
                                                                    final boolean excludeLibClasses) {
        final SortedSet<TypeElement> result = new TreeSet<>(Comparator.comparing(o -> o.getQualifiedName().toString()));
        if (withThis) {
            result.add(type);
        }
        final Set<String> names = new HashSet<>();
        TypeElement currentTypeElement = type;
        while (true) {
            currentTypeElement.getInterfaces().stream()
                    .filter(t -> !excludeLibClasses || !isDeniedPackage(getPackageName(t)))
                    .flatMap(t -> asTypeElement(t).stream())
                    .forEach(te -> {
                        if (names.add(te.getQualifiedName().toString())) {
                            result.add(te);
                        }
                    });
            final TypeMirror superClass = currentTypeElement.getSuperclass();
            if (superClassIsObject(superClass)) {
                break;
            } else {
                currentTypeElement = asTypeElement(superClass).orElseThrow();
                if ((!excludeLibClasses || !isDeniedPackage(getPackageName(currentTypeElement))) &&
                        names.add(currentTypeElement.getQualifiedName().toString())) {
                    result.add(currentTypeElement);
                }
            }
        }
        return result;
    }

    private Elements() {
    }
}
