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

import io.rxmicro.annotation.processor.common.model.error.InternalErrorException;
import io.rxmicro.model.MappingStrategy;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Supplier;
import java.util.stream.Stream;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;

import static io.rxmicro.annotation.processor.common.model.ModelAccessorType.DIRECT;
import static io.rxmicro.annotation.processor.common.model.ModelAccessorType.JAVA_BEAN;
import static io.rxmicro.annotation.processor.common.model.ModelAccessorType.REFLECTION;
import static io.rxmicro.common.util.Requires.require;
import static javax.lang.model.element.Modifier.PRIVATE;

/**
 * @author nedis
 * @since 0.1
 */
public final class AnnotatedModelElement {

    private final VariableElement field;

    private final List<ExecutableElement> getters;

    private final ExecutableElement setter;

    private final ModelAccessorType readModelAccessorType;

    private final ModelAccessorType writeModelAccessorType;

    public AnnotatedModelElement(final VariableElement field,
                                 final List<ExecutableElement> getters,
                                 final ExecutableElement setter) {
        this.field = require(field);
        this.getters = require(getters);
        this.setter = setter;
        this.readModelAccessorType = getModelAccessorType(() -> !getters.isEmpty());
        this.writeModelAccessorType = getModelAccessorType(() -> setter != null);
    }

    public AnnotatedModelElement(final VariableElement constructorArgument) {
        this.field = require(constructorArgument);
        this.getters = List.of();
        this.setter = null;
        this.readModelAccessorType = constructorArgument.getModifiers().contains(PRIVATE) ? REFLECTION : DIRECT;
        this.writeModelAccessorType = constructorArgument.getModifiers().contains(PRIVATE) ? REFLECTION : DIRECT;
    }


    String getGetter() {
        try {
            return getters.get(0).getSimpleName().toString();
        } catch (final IndexOutOfBoundsException ignored) {
            throw new InternalErrorException(
                    "Getter not defined: class=?, field=?",
                    field.getEnclosingElement().asType(), field.getSimpleName()
            );
        }
    }

    String getSetter() {
        if (setter == null) {
            throw new InternalErrorException(
                    "Setter not defined: class=?, field=?",
                    field.getEnclosingElement().asType(), field.getSimpleName()
            );
        }
        return setter.getSimpleName().toString();
    }

    ModelAccessorType getModelReadAccessorType() {
        return readModelAccessorType;
    }

    ModelAccessorType getModelWriteAccessorType() {
        return writeModelAccessorType;
    }

    private ModelAccessorType getModelAccessorType(final Supplier<Boolean> javaBeanUsagePredicate) {
        if (field.getModifiers().contains(PRIVATE)) {
            if (javaBeanUsagePredicate.get()) {
                return JAVA_BEAN;
            } else {
                return REFLECTION;
            }
        } else {
            return DIRECT;
        }
    }

    public VariableElement getField() {
        return field;
    }

    public <T extends Annotation> boolean isAnnotationPresent(final Class<T> annotationClass) {
        return getAnnotation(annotationClass) != null;
    }

    public <T extends Annotation> T getAnnotation(final Class<T> annotationClass) {
        return Stream.concat(
                        getters.stream(),
                        Stream.of(field))
                .flatMap(e -> Optional.ofNullable(e.getAnnotation(annotationClass)).stream())
                .findFirst()
                .orElse(null);
    }

    @SuppressWarnings("unchecked")
    public <A extends Annotation> A[] getAnnotationsByType(final Class<A> annotationType) {
        return Stream.concat(
                        getters.stream(),
                        Stream.of(field))
                .flatMap(e -> Arrays.stream(e.getAnnotationsByType(annotationType)))
                .toArray(value -> (A[]) java.lang.reflect.Array.newInstance(annotationType, value));
    }

    public <T extends Annotation> Optional<Element> getElementAnnotatedBy(final Class<T> annotationClass) {
        return Stream.concat(
                        getters.stream(),
                        Stream.of(field).map(e -> (Element) e))
                .filter(e -> e.getAnnotation(annotationClass) != null)
                .findFirst();
    }

    Optional<Element> getElementAnnotatedBy(final AnnotationMirror annotationMirror) {
        final String annotationClassName = annotationMirror.getAnnotationType().toString();
        return Stream.concat(
                        getters.stream(),
                        Stream.of(field).map(e -> (Element) e))
                .filter(e -> e.getAnnotationMirrors().stream()
                        .anyMatch(m -> m.getAnnotationType().toString().equals(annotationClassName)))
                .findFirst();
    }

    public List<? extends AnnotationMirror> getAllAnnotationMirrors(final Elements elements) {
        final Set<AnnotationMirror> set = new TreeSet<>(Comparator.comparing(o -> o.getAnnotationType().toString()));
        Stream.concat(
                getters.stream(),
                Stream.of(field).map(e -> (Element) e)
        ).forEach(e -> set.addAll(elements.getAllAnnotationMirrors(e)));
        return new ArrayList<>(set);
    }

    public boolean isFinal() {
        return field.getModifiers().contains(Modifier.FINAL);
    }

    public String getModelName(final String customModelName,
                               final Annotation mappingStrategyAnnotation,
                               final Supplier<MappingStrategy> mappingStrategySupplier) {
        if (!customModelName.isEmpty()) {
            return customModelName;
        } else if (mappingStrategyAnnotation != null) {
            return mappingStrategySupplier.get().getModelName(field.getSimpleName().toString());
        } else {
            return field.getSimpleName().toString();
        }
    }
}
