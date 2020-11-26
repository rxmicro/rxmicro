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

import io.rxmicro.annotation.processor.common.util.Names;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Set;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ElementVisitor;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.ModuleElement;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

import static io.rxmicro.annotation.processor.common.util.ProcessingEnvironmentHelper.getElements;
import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @since 0.2
 */
public final class VirtualModuleElement implements ModuleElement, VirtualElement {

    private final ModuleElement unnamedModuleElement;

    private final TypeElement virtualModuleInfoAnnotation;

    private final String name;

    public VirtualModuleElement(final ModuleElement unnamedModuleElement,
                                final TypeElement virtualModuleInfoAnnotation,
                                final String name) {
        this.unnamedModuleElement = require(unnamedModuleElement);
        this.virtualModuleInfoAnnotation = require(virtualModuleInfoAnnotation);
        this.name = require(name);
    }

    @Override
    public Element getRealElement() {
        return virtualModuleInfoAnnotation;
    }

    @Override
    public Name getQualifiedName() {
        return getElements().getName(name);
    }

    @Override
    public Name getSimpleName() {
        return getElements().getName(Names.getSimpleName(name));
    }

    @Override
    public List<? extends Element> getEnclosedElements() {
        return unnamedModuleElement.getEnclosedElements();
    }

    @Override
    public boolean isOpen() {
        return true;
    }

    @Override
    public boolean isUnnamed() {
        return true;
    }

    @Override
    public Element getEnclosingElement() {
        return null;
    }

    @Override
    public List<? extends Directive> getDirectives() {
        return unnamedModuleElement.getDirectives();
    }

    @Override
    public TypeMirror asType() {
        return new VirtualModuleTypeMirror(virtualModuleInfoAnnotation);
    }

    @Override
    public ElementKind getKind() {
        return ElementKind.MODULE;
    }

    @Override
    public Set<Modifier> getModifiers() {
        return unnamedModuleElement.getModifiers();
    }

    @Override
    public List<? extends AnnotationMirror> getAnnotationMirrors() {
        return virtualModuleInfoAnnotation.getAnnotationMirrors();
    }

    @Override
    public <A extends Annotation> A getAnnotation(final Class<A> annotationType) {
        return virtualModuleInfoAnnotation.getAnnotation(annotationType);
    }

    @Override
    public <R, P> R accept(final ElementVisitor<R, P> visitor, final P parameter) {
        return unnamedModuleElement.accept(visitor, parameter);
    }

    @Override
    public <A extends Annotation> A[] getAnnotationsByType(final Class<A> annotationType) {
        return virtualModuleInfoAnnotation.getAnnotationsByType(annotationType);
    }
}
