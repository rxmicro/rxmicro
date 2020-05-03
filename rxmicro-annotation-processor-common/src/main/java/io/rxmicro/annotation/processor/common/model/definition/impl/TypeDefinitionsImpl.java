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
import io.rxmicro.annotation.processor.common.model.definition.TypeDefinitions;

import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;
import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toUnmodifiableList;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class TypeDefinitionsImpl<T extends TypeDefinition> implements TypeDefinitions<T> {

    private final List<T> typeDefinitions;

    @SafeVarargs
    public TypeDefinitionsImpl(final T... typeDefinitions) {
        this.typeDefinitions = List.of(typeDefinitions);
    }

    public TypeDefinitionsImpl(final List<TypeDefinitions<T>> typeDefinitions) {
        this.typeDefinitions = typeDefinitions.stream()
                .flatMap(d -> d.getTypeDefinitions().stream())
                .collect(toUnmodifiableList());
    }

    @Override
    public boolean contains(final TypeMirror typeMirror) {
        return typeDefinitions.stream().anyMatch(d -> d.isEqual(typeMirror));
    }

    @Override
    public boolean contains(final Element element) {
        return typeDefinitions.stream().anyMatch(d -> d.isEqual(element));
    }

    @Override
    public Collection<T> getTypeDefinitions() {
        return typeDefinitions;
    }

    @Override
    public String toString() {
        return typeDefinitions.toString();
    }
}
