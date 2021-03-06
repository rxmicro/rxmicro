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

package io.rxmicro.annotation.processor.common.util.proxy;

import io.rxmicro.annotation.processor.common.model.virtual.VirtualFieldElement;
import io.rxmicro.annotation.processor.common.model.virtual.VirtualTypeElement;

import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.lang.model.AnnotatedConstruct;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.ModuleElement;
import javax.lang.model.element.Name;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @since 0.1
 */
public final class ProxyElements implements Elements {

    private final Elements elements;

    public ProxyElements(final Elements elements) {
        this.elements = require(elements);
    }

    @Override
    public PackageElement getPackageElement(final CharSequence name) {
        return elements.getPackageElement(name);
    }

    @Override
    public PackageElement getPackageElement(final ModuleElement module,
                                            final CharSequence name) {
        return elements.getPackageElement(module, name);
    }

    @Override
    public Set<? extends PackageElement> getAllPackageElements(final CharSequence name) {
        return elements.getAllPackageElements(name);
    }

    @Override
    public TypeElement getTypeElement(final CharSequence name) {
        return elements.getTypeElement(name);
    }

    @Override
    public TypeElement getTypeElement(final ModuleElement module,
                                      final CharSequence name) {
        return elements.getTypeElement(module, name);
    }

    @Override
    public Set<? extends TypeElement> getAllTypeElements(final CharSequence name) {
        return elements.getAllTypeElements(name);
    }

    @Override
    public ModuleElement getModuleElement(final CharSequence name) {
        return elements.getModuleElement(name);
    }

    @Override
    public Set<? extends ModuleElement> getAllModuleElements() {
        return elements.getAllModuleElements();
    }

    @Override
    public Map<? extends ExecutableElement, ? extends AnnotationValue> getElementValuesWithDefaults(final AnnotationMirror mirror) {
        return elements.getElementValuesWithDefaults(mirror);
    }

    @Override
    public String getDocComment(final Element element) {
        return elements.getDocComment(element);
    }

    @Override
    public boolean isDeprecated(final Element element) {
        return elements.isDeprecated(element);
    }

    @Override
    public Origin getOrigin(final Element element) {
        return elements.getOrigin(element);
    }

    @Override
    public Origin getOrigin(final AnnotatedConstruct annotatedConstruct,
                            final AnnotationMirror annotationMirror) {
        return elements.getOrigin(annotatedConstruct, annotationMirror);
    }

    @Override
    public Origin getOrigin(final ModuleElement moduleElement,
                            final ModuleElement.Directive directive) {
        return elements.getOrigin(moduleElement, directive);
    }

    @Override
    public boolean isBridge(final ExecutableElement element) {
        return elements.isBridge(element);
    }

    @Override
    public Name getBinaryName(final TypeElement type) {
        return elements.getBinaryName(type);
    }

    @Override
    public PackageElement getPackageOf(final Element type) {
        return elements.getPackageOf(type);
    }

    @Override
    public ModuleElement getModuleOf(final Element type) {
        return elements.getModuleOf(type);
    }

    @Override
    public List<? extends Element> getAllMembers(final TypeElement type) {
        return elements.getAllMembers(type);
    }

    @Override
    public List<? extends AnnotationMirror> getAllAnnotationMirrors(final Element element) {
        if (element instanceof VirtualTypeElement || element instanceof VirtualFieldElement) {
            return element.getAnnotationMirrors();
        } else {
            return elements.getAllAnnotationMirrors(element);
        }
    }

    @Override
    public boolean hides(final Element hider,
                         final Element hidden) {
        return elements.hides(hider, hidden);
    }

    @Override
    public boolean overrides(final ExecutableElement overrider,
                             final ExecutableElement overridden,
                             final TypeElement type) {
        return elements.overrides(overrider, overridden, type);
    }

    @Override
    public String getConstantExpression(final Object value) {
        return elements.getConstantExpression(value);
    }

    @Override
    public void printElements(final Writer writer,
                              final Element... elements) {
        this.elements.printElements(writer, elements);
    }

    @Override
    public Name getName(final CharSequence cs) {
        return elements.getName(cs);
    }

    @Override
    public boolean isFunctionalInterface(final TypeElement type) {
        return elements.isFunctionalInterface(type);
    }
}
