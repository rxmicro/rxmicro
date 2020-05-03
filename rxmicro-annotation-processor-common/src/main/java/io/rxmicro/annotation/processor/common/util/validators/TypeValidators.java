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

package io.rxmicro.annotation.processor.common.util.validators;

import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.annotation.processor.common.model.virtual.VirtualTypeMirror;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.ModuleElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import java.util.List;

import static io.rxmicro.annotation.processor.common.util.ProcessingEnvironmentHelper.getElements;
import static io.rxmicro.annotation.processor.common.util.ProcessingEnvironmentHelper.getTypes;
import static io.rxmicro.annotation.processor.common.util.Elements.asTypeElement;
import static io.rxmicro.annotation.processor.common.util.Elements.expectedGenericArgumentCount;
import static io.rxmicro.annotation.processor.common.util.Elements.isGenericType;
import static io.rxmicro.annotation.processor.common.util.Elements.superClassIsObject;
import static io.rxmicro.tool.common.DeniedPackages.isDeniedPackage;
import static javax.lang.model.element.ElementKind.ENUM;
import static javax.lang.model.element.Modifier.ABSTRACT;
import static javax.lang.model.element.Modifier.PROTECTED;
import static javax.lang.model.element.Modifier.PUBLIC;
import static javax.lang.model.type.TypeKind.DECLARED;
import static javax.lang.model.type.TypeKind.WILDCARD;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class TypeValidators {

    public static void validateThatElementIsPublic(final Element element,
                                                   final String errorMessage,
                                                   final Object... args) {
        if (!element.getModifiers().contains(PUBLIC)) {
            throw new InterruptProcessingException(element, errorMessage, args);
        }
    }

    public static void validateExpectedElementKind(final Element element,
                                                   final ElementKind expectedElementKind,
                                                   final String errorMessage,
                                                   final Object... args) {
        if (element.getKind() != expectedElementKind) {
            throw new InterruptProcessingException(element, errorMessage, args);
        }
    }

    public static void validateNotNestedClass(final TypeElement element,
                                              final String errorMessage,
                                              final Object... args) {
        if (element.getNestingKind().isNested()) {
            throw new InterruptProcessingException(element, errorMessage, args);
        }
    }

    public static void validateNotAbstractClass(final TypeElement element,
                                                final String errorMessage,
                                                final Object... args) {
        if (element.getModifiers().contains(ABSTRACT)) {
            throw new InterruptProcessingException(element, errorMessage, args);
        }
    }

    public static void validateNotSuperClass(final TypeElement element,
                                             final String errorMessage,
                                             final Object... args) {
        if (!Object.class.getName().equals(element.getSuperclass().toString())) {
            throw new InterruptProcessingException(element, errorMessage, args);
        }
    }

    public static void validateNotSuperInterfaces(final TypeElement element,
                                                  final String errorMessage,
                                                  final Object... args) {
        if (!element.getInterfaces().isEmpty()) {
            throw new InterruptProcessingException(element, errorMessage, args);
        }
    }

    public static void validateAccessibleDefaultConstructor(final TypeElement typeElement) {
        if (typeElement.getEnclosedElements().stream()
                .filter(e -> e instanceof ExecutableElement)
                .map(e -> (ExecutableElement) e)
                .noneMatch(e -> (e.getModifiers().isEmpty() ||
                        e.getModifiers().contains(PUBLIC) ||
                        e.getModifiers().contains(PROTECTED)) &&
                        "<init>".equals(e.getSimpleName().toString()) &&
                        e.getParameters().isEmpty())) {
            throw new InterruptProcessingException(
                    typeElement,
                    "Class '?' must declare a public or protected or default constructor without parameters",
                    typeElement.asType()
            );
        }
    }

    public static void validateAccessibleConstructor(final ExecutableElement constructorElement) {
        if (!constructorElement.getModifiers().isEmpty() &&
                !constructorElement.getModifiers().contains(PUBLIC) &&
                !constructorElement.getModifiers().contains(PROTECTED)) {
            throw new InterruptProcessingException(
                    constructorElement,
                    "Constructor must be a public or protected or default"
            );
        }
    }

    public static TypeElement validateAndGetModelType(final ModuleElement ownerModule,
                                                      final Element owner,
                                                      final TypeMirror type,
                                                      final String prefix,
                                                      final boolean requireDefConstructor) {
        if (type instanceof VirtualTypeMirror) {
            return ((VirtualTypeMirror) type).getVirtualTypeElement();
        } else {
            final String validPrefix = prefix == null || prefix.isBlank() ? "" : prefix + ": ";
            final Element element = getTypes().asElement(type);
            if (type.getKind() != DECLARED || !(element instanceof TypeElement)) {
                throw new InterruptProcessingException(owner, "?? is not a class", validPrefix, type);
            }
            if (isDeniedPackage(type.toString())) {
                throw new InterruptProcessingException(owner,
                        "?Model class couldn't be a library class: ?. Create a custom class at the current module", validPrefix, type);
            }
            final TypeElement typeElement = (TypeElement) element;
            if (typeElement.getNestingKind().isNested()) {
                throw new InterruptProcessingException(owner, "?Model class couldn't be a nested class: ?", validPrefix, type);
            }
            if (typeElement.getKind().isInterface()) {
                throw new InterruptProcessingException(owner, "?Model class couldn't be an interface: ?", validPrefix, type);
            }
            if (typeElement.getKind() == ENUM) {
                throw new InterruptProcessingException(owner, "?Model class couldn't be an enum: ?", validPrefix, type);
            }
            if (!typeElement.getTypeParameters().isEmpty()) {
                throw new InterruptProcessingException(owner, "?Model class couldn't be a parametrized class: ?", validPrefix, type);
            }
            if (requireDefConstructor) {
                validateAccessibleDefaultConstructor(typeElement);
            }
            validateModuleDeclaration(ownerModule, owner, validPrefix, typeElement);
            return typeElement;
        }
    }

    private static void validateModuleDeclaration(final ModuleElement ownerModule,
                                                  final Element owner,
                                                  final String validPrefix,
                                                  final TypeElement typeElement) {
        if (!ownerModule.isUnnamed()) {
            TypeElement currentTypeElement = typeElement;
            while (true) {
                if (currentTypeElement.getEnclosedElements().stream().anyMatch(e -> e instanceof VariableElement)) {
                    final ModuleElement resultModule = getElements().getModuleOf(currentTypeElement);
                    if (resultModule == null ||
                            !ownerModule.getQualifiedName().equals(resultModule.getQualifiedName())) {
                        throw new InterruptProcessingException(owner,
                                "?Model class '?' must be declared at '?' module",
                                validPrefix, currentTypeElement.getQualifiedName(), ownerModule.getQualifiedName());
                    }
                }
                final TypeMirror superClass = currentTypeElement.getSuperclass();
                if (superClassIsObject(superClass)) {
                    break;
                } else {
                    currentTypeElement = asTypeElement(superClass).orElseThrow();
                }
            }
        }
    }

    public static void validateGenericType(final Element owner,
                                           final TypeMirror type) {
        validateGenericType(owner, type, null);
    }

    public static void validateGenericType(final Element owner,
                                           final TypeMirror type,
                                           final String prefix) {
        final String validPrefix = prefix == null || prefix.isBlank() ? "" : prefix + ": ";
        if (!isGenericType(type)) {
            throw new InterruptProcessingException(owner,
                    "?Expected generic type: ?", validPrefix, type);
        }
        final int expectedCount = expectedGenericArgumentCount(type);
        if (type instanceof DeclaredType) {
            final List<? extends TypeMirror> typeArguments = ((DeclaredType) type).getTypeArguments();
            if (expectedCount != typeArguments.size()) {
                throw new InterruptProcessingException(owner,
                        "?Expected generic type with ? parameter(s): ?", validPrefix, expectedCount, type);
            }
            for (final TypeMirror itemType : typeArguments) {
                if (itemType.getKind() == WILDCARD) {
                    throw new InterruptProcessingException(owner,
                            "?Wildcard is not allowed: ?", validPrefix, type);
                }
                if (isGenericType(itemType)) {
                    validateGenericType(owner, itemType, prefix);
                }
            }
        } else {
            throw new InterruptProcessingException(owner, "?Expected declared type: ?", validPrefix, type);
        }
    }

    private TypeValidators() {
    }
}
