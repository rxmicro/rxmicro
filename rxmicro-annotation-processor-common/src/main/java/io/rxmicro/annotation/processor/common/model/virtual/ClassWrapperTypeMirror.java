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
import java.util.List;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVisitor;

import static javax.lang.model.type.TypeKind.DECLARED;

/**
 * @author nedis
 * @since 0.1
 */
public final class ClassWrapperTypeMirror implements TypeMirror {

    private final Class<?> typeClass;

    public ClassWrapperTypeMirror(final Class<?> typeClass) {
        this.typeClass = typeClass;
    }

    public Class<?> getTypeClass() {
        return typeClass;
    }

    @Override
    public TypeKind getKind() {
        return DECLARED;
    }

    @Override
    public <R, P> R accept(final TypeVisitor<R, P> visitor, final P parameter) {
        throw new UnsupportedOperationException();
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
    public <A extends Annotation> A[] getAnnotationsByType(final Class<A> annotationType) {
        return typeClass.getAnnotationsByType(annotationType);
    }

    @Override
    public String toString() {
        return typeClass.getName();
    }
}
