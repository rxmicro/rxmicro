/*
 * Copyright 2019 https://rxmicro.io
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

package io.rxmicro.annotation.processor.common.model.definition.impl;

import io.rxmicro.annotation.processor.common.model.definition.TypeDefinition;

import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;
import java.lang.annotation.Annotation;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @since 0.1
 */
public final class AnnotatedByTypeDefinition implements TypeDefinition {

    private final Class<? extends Annotation> annotationClass;

    public AnnotatedByTypeDefinition(final Class<? extends Annotation> annotationClass) {
        this.annotationClass = require(annotationClass);
    }

    @Override
    public boolean isEqual(final TypeMirror typeMirror) {
        return typeMirror.getAnnotation(annotationClass) != null;
    }

    @Override
    public boolean isEqual(final Element element) {
        return element.getAnnotation(annotationClass) != null;
    }

    @Override
    public String toString() {
        return format("Annotated by '@?'", annotationClass.getName());
    }
}
