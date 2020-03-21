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
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.NoType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeVisitor;
import java.lang.annotation.Annotation;
import java.util.List;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.2
 */
public final class VirtualModuleTypeMirror implements NoType {

    private final TypeElement virtualModuleInfoAnnotation;

    public VirtualModuleTypeMirror(final TypeElement virtualModuleInfoAnnotation) {
        this.virtualModuleInfoAnnotation = virtualModuleInfoAnnotation;
    }

    @Override
    public TypeKind getKind() {
        return TypeKind.MODULE;
    }

    @Override
    public <R, P> R accept(final TypeVisitor<R, P> v, final P p) {
        return null;
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
    public <A extends Annotation> A[] getAnnotationsByType(final Class<A> annotationType) {
        return virtualModuleInfoAnnotation.getAnnotationsByType(annotationType);
    }
}
