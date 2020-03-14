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

package io.rxmicro.annotation.processor.common.model.virtual;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ElementVisitor;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Set;

import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public final class VirtualFieldElement implements VariableElement, VirtualElement {

    private final VirtualTypeElement virtualTypeElement;

    private final VariableElement parameter;

    VirtualFieldElement(final VirtualTypeElement virtualTypeElement,
                        final VariableElement parameter) {
        this.virtualTypeElement = require(virtualTypeElement);
        this.parameter = require(parameter);
    }

    @Override
    public VariableElement getRealElement() {
        return parameter;
    }

    @Override
    public Object getConstantValue() {
        return null;
    }

    @Override
    public Name getSimpleName() {
        return parameter.getSimpleName();
    }

    @Override
    public Element getEnclosingElement() {
        return virtualTypeElement;
    }

    @Override
    public TypeMirror asType() {
        return parameter.asType();
    }

    @Override
    public ElementKind getKind() {
        return ElementKind.FIELD;
    }

    @Override
    public Set<Modifier> getModifiers() {
        return Set.of(Modifier.PUBLIC);
    }

    @Override
    public List<? extends Element> getEnclosedElements() {
        return List.of();
    }

    @Override
    public List<? extends AnnotationMirror> getAnnotationMirrors() {
        return parameter.getAnnotationMirrors();
    }

    @Override
    public <A extends Annotation> A getAnnotation(final Class<A> annotationType) {
        return parameter.getAnnotation(annotationType);
    }

    @Override
    public <R, P> R accept(final ElementVisitor<R, P> v, final P p) {
        return null;
    }

    @Override
    public <A extends Annotation> A[] getAnnotationsByType(final Class<A> annotationType) {
        return parameter.getAnnotationsByType(annotationType);
    }
}
