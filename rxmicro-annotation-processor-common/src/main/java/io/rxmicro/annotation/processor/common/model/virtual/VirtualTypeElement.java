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

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ElementVisitor;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.NestingKind;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.type.TypeMirror;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static io.rxmicro.annotation.processor.common.model.virtual.VirtualNames.buildVirtualClassName;
import static io.rxmicro.annotation.processor.common.util.AnnotationProcessorEnvironment.elements;
import static io.rxmicro.common.util.Requires.require;
import static javax.lang.model.element.ElementKind.CLASS;
import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PUBLIC;
import static javax.lang.model.element.NestingKind.TOP_LEVEL;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class VirtualTypeElement implements TypeElement, VirtualElement {

    final String packageName;

    final String className;

    final ExecutableElement method;

    private final TypeElement ownerClass;

    public VirtualTypeElement(final String classNameTemplate,
                              final ExecutableElement method) {
        this.method = require(method);
        this.packageName = ((PackageElement) method.getEnclosingElement().getEnclosingElement()).getQualifiedName().toString();
        this.className = buildVirtualClassName(classNameTemplate, method);
        this.ownerClass = (TypeElement) method.getEnclosingElement();
    }

    @Override
    public ExecutableElement getRealElement() {
        return method;
    }

    public List<TypeMirror> getFieldTypes() {
        return getEnclosedElements().stream().map(Element::asType).collect(Collectors.toList());
    }

    @Override
    public List<? extends Element> getEnclosedElements() {
        return getVirtualFieldElements();
    }

    @Override
    public NestingKind getNestingKind() {
        return TOP_LEVEL;
    }

    @Override
    public Name getQualifiedName() {
        return elements().getName(packageName + "." + className);
    }

    @Override
    public Name getSimpleName() {
        return elements().getName(className);
    }

    @Override
    public TypeMirror getSuperclass() {
        return elements().getTypeElement(Object.class.getName()).asType();
    }

    @Override
    public List<? extends TypeMirror> getInterfaces() {
        return List.of();
    }

    @Override
    public List<? extends TypeParameterElement> getTypeParameters() {
        return List.of();
    }

    @Override
    public Element getEnclosingElement() {
        return method.getEnclosingElement().getEnclosingElement();
    }

    public List<VirtualFieldElement> getVirtualFieldElements() {
        return method.getParameters().stream()
                .map(v -> new VirtualFieldElement(this, v))
                .collect(Collectors.toList());
    }

    @Override
    public <A extends Annotation> A[] getAnnotationsByType(final Class<A> annotationType) {
        final A[] annotations = method.getAnnotationsByType(annotationType);
        return annotations.length != 0 ? annotations : ownerClass.getAnnotationsByType(annotationType);
    }

    @Override
    public TypeMirror asType() {
        return new VirtualTypeMirror(this);
    }

    @Override
    public ElementKind getKind() {
        return CLASS;
    }

    @Override
    public Set<Modifier> getModifiers() {
        return Set.of(FINAL, PUBLIC);
    }

    @Override
    public List<? extends AnnotationMirror> getAnnotationMirrors() {
        final List<? extends AnnotationMirror> methodMirrors = method.getAnnotationMirrors();
        final List<? extends AnnotationMirror> classMirrors = ownerClass.getAnnotationMirrors();
        final List<AnnotationMirror> mirrors = new ArrayList<>();
        for (final AnnotationMirror classMirror : classMirrors) {
            if (methodMirrors.stream().noneMatch(m ->
                    m.getAnnotationType().toString().equals(classMirror.getAnnotationType().toString()))) {
                mirrors.add(classMirror);
            }
        }
        mirrors.addAll(methodMirrors);
        return mirrors;
    }

    @Override
    public <A extends Annotation> A getAnnotation(final Class<A> annotationType) {
        final A a = method.getAnnotation(annotationType);
        return a != null ? a : ownerClass.getAnnotation(annotationType);
    }

    @Override
    public <R, P> R accept(final ElementVisitor<R, P> v, final P p) {
        return null;
    }

    @Override
    public String toString() {
        return getQualifiedName().toString();
    }
}
