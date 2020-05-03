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

package io.rxmicro.annotation.processor.common.model;

import io.rxmicro.annotation.processor.common.util.Names;
import io.rxmicro.annotation.processor.common.util.UsedByFreemarker;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ElementVisitor;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.type.TypeMirror;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Set;

import static io.rxmicro.annotation.processor.common.util.ProcessingEnvironmentHelper.getElements;
import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public abstract class ModelField implements Comparable<ModelField>, Element {

    private final AnnotatedModelElement annotatedModelElement;

    private final String modelName;

    public ModelField(final AnnotatedModelElement annotatedModelElement,
                      final String modelName) {
        this.annotatedModelElement = require(annotatedModelElement);
        this.modelName = require(modelName);
    }

    public Element getFieldElement() {
        return annotatedModelElement.getField();
    }

    public <T extends Annotation> Element getElementAnnotatedBy(final Class<T> annotationClass) {
        return annotatedModelElement.getElementAnnotatedBy(annotationClass)
                .orElse(annotatedModelElement.getField());
    }

    public Element getElementAnnotatedBy(final AnnotationMirror annotationMirror) {
        return annotatedModelElement.getElementAnnotatedBy(annotationMirror)
                .orElse(annotatedModelElement.getField());
    }

    public final TypeMirror getFieldClass() {
        return annotatedModelElement.getField().asType();
    }

    @UsedByFreemarker
    public final String getFieldSimpleType() {
        return Names.getSimpleName(getFieldClass());
    }

    @UsedByFreemarker
    public final String getFieldName() {
        return annotatedModelElement.getField().getSimpleName().toString();
    }

    @UsedByFreemarker
    public final String getModelName() {
        return modelName;
    }

    @Override
    public <A extends Annotation> A[] getAnnotationsByType(final Class<A> annotationType) {
        return annotatedModelElement.getAnnotationsByType(annotationType);
    }

    public <T extends Annotation> boolean hasAnnotation(final Class<T> annotationClass) {
        return getAnnotation(annotationClass) != null;
    }

    @UsedByFreemarker
    public ModelAccessorType getModelReadAccessorType() {
        return annotatedModelElement.getModelReadAccessorType();
    }

    @UsedByFreemarker
    public ModelAccessorType getModelWriteAccessorType() {
        return annotatedModelElement.getModelWriteAccessorType();
    }

    @UsedByFreemarker
    public String getGetter() {
        return annotatedModelElement.getGetter();
    }

    @UsedByFreemarker
    public String getSetter() {
        return annotatedModelElement.getSetter();
    }

    public boolean isNotFinal() {
        return !annotatedModelElement.isFinal();
    }

    @Override
    public final int hashCode() {
        return getFieldName().hashCode();
    }

    @Override
    public final boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        final ModelField that = (ModelField) other;
        return getFieldName().equals(that.getFieldName());
    }

    @Override
    public TypeMirror asType() {
        return getFieldElement().asType();
    }

    @Override
    public ElementKind getKind() {
        return getFieldElement().getKind();
    }

    @Override
    public Set<Modifier> getModifiers() {
        return getFieldElement().getModifiers();
    }

    @Override
    public Name getSimpleName() {
        return getFieldElement().getSimpleName();
    }

    @Override
    public Element getEnclosingElement() {
        return getFieldElement().getEnclosingElement();
    }

    @Override
    public List<? extends Element> getEnclosedElements() {
        return List.of();
    }

    @Override
    public List<? extends AnnotationMirror> getAnnotationMirrors() {
        return annotatedModelElement.getAllAnnotationMirrors(getElements());
    }

    @Override
    public <T extends Annotation> T getAnnotation(final Class<T> annotationClass) {
        return annotatedModelElement.getAnnotation(annotationClass);
    }

    @Override
    public <R, P> R accept(final ElementVisitor<R, P> visitor, final P parameter) {
        return getFieldElement().accept(visitor, parameter);
    }

    @Override
    public int compareTo(final ModelField other) {
        return getFieldName().compareTo(other.getFieldName());
    }
}
