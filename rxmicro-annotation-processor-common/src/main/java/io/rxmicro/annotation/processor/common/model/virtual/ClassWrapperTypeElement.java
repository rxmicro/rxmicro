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

package io.rxmicro.annotation.processor.common.model.virtual;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ElementVisitor;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.NestingKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.type.TypeMirror;

import static io.rxmicro.annotation.processor.common.util.ProcessingEnvironmentHelper.getElements;
import static java.util.Collections.unmodifiableSet;
import static java.util.stream.Collectors.toUnmodifiableList;

/**
 * @author nedis
 * @since 0.1
 */
public final class ClassWrapperTypeElement implements TypeElement {

    private final Class<?> typeClass;

    public ClassWrapperTypeElement(final Class<?> typeClass) {
        this.typeClass = typeClass;
    }

    @Override
    public List<? extends Element> getEnclosedElements() {
        throw new UnsupportedOperationException();
    }

    @Override
    public NestingKind getNestingKind() {
        if (typeClass.isLocalClass()) {
            return NestingKind.LOCAL;
        } else if (typeClass.isMemberClass()) {
            return NestingKind.MEMBER;
        } else {
            return NestingKind.TOP_LEVEL;
        }
    }

    @Override
    public Name getQualifiedName() {
        return getElements().getName(typeClass.getName());
    }

    @Override
    public Name getSimpleName() {
        return getElements().getName(typeClass.getSimpleName());
    }

    @Override
    public TypeMirror getSuperclass() {
        final Class<?> superclass = typeClass.getSuperclass();
        if (superclass == null) {
            return null;
        } else {
            return new ClassWrapperTypeMirror(superclass);
        }
    }

    @Override
    public List<? extends TypeMirror> getInterfaces() {
        return Arrays.stream(typeClass.getInterfaces()).map(ClassWrapperTypeMirror::new).collect(toUnmodifiableList());
    }

    @Override
    public List<? extends TypeParameterElement> getTypeParameters() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Element getEnclosingElement() {
        throw new UnsupportedOperationException();
    }

    @Override
    public <A extends Annotation> A[] getAnnotationsByType(final Class<A> annotationType) {
        return typeClass.getAnnotationsByType(annotationType);
    }

    @Override
    public TypeMirror asType() {
        return new ClassWrapperTypeMirror(typeClass);
    }

    @Override
    public ElementKind getKind() {
        if (typeClass.isEnum()) {
            return ElementKind.ENUM;
        } else if (typeClass.isAnnotation()) {
            return ElementKind.ANNOTATION_TYPE;
        } else if (typeClass.isInterface()) {
            return ElementKind.INTERFACE;
        } else {
            return ElementKind.CLASS;
        }
    }

    @Override
    public Set<Modifier> getModifiers() {
        final Set<Modifier> result = new HashSet<>();
        final int modifiers = typeClass.getModifiers();
        if (java.lang.reflect.Modifier.isAbstract(modifiers)) {
            result.add(Modifier.ABSTRACT);
        }
        if (java.lang.reflect.Modifier.isFinal(modifiers)) {
            result.add(Modifier.FINAL);
        }
        if (java.lang.reflect.Modifier.isPublic(modifiers)) {
            result.add(Modifier.PUBLIC);
        }
        if (java.lang.reflect.Modifier.isProtected(modifiers)) {
            result.add(Modifier.PROTECTED);
        }
        if (java.lang.reflect.Modifier.isPrivate(modifiers)) {
            result.add(Modifier.PRIVATE);
        }
        if (java.lang.reflect.Modifier.isStatic(modifiers)) {
            result.add(Modifier.STATIC);
        }
        return unmodifiableSet(result);
    }

    @Override
    public List<? extends AnnotationMirror> getAnnotationMirrors() {
        throw new UnsupportedOperationException();
    }

    @Override
    public <A extends Annotation> A getAnnotation(final Class<A> annotationType) {
        return typeClass.getAnnotation(annotationType);
    }

    @Override
    public <R, P> R accept(final ElementVisitor<R, P> visitor, final P parameter) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        return typeClass.getName();
    }
}
