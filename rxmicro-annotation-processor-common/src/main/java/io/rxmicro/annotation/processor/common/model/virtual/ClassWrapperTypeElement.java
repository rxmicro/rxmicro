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
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.NestingKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.type.TypeMirror;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Set;

import static io.rxmicro.annotation.processor.common.util.ProcessingEnvironmentHelper.getElements;

/**
 * @author nedis
 * @link https://rxmicro.io
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
        throw new UnsupportedOperationException();
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
        throw new UnsupportedOperationException();
    }

    @Override
    public List<? extends TypeMirror> getInterfaces() {
        throw new UnsupportedOperationException();
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
        throw new UnsupportedOperationException();
    }

    @Override
    public TypeMirror asType() {
        return new ClassWrapperTypeMirror(typeClass);
    }

    @Override
    public ElementKind getKind() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<Modifier> getModifiers() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<? extends AnnotationMirror> getAnnotationMirrors() {
        throw new UnsupportedOperationException();
    }

    @Override
    public <A extends Annotation> A getAnnotation(final Class<A> annotationType) {
        throw new UnsupportedOperationException();
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
