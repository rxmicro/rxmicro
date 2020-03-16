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
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import java.util.List;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class ContainerTypeDefinition implements TypeDefinition {

    private final TypeDefinition containerTypeDefinition;

    private final TypeDefinition itemTypeDefinition;

    public ContainerTypeDefinition(final TypeDefinition containerTypeDefinition,
                                   final TypeDefinition itemTypeDefinition) {
        this.containerTypeDefinition = require(containerTypeDefinition);
        this.itemTypeDefinition = itemTypeDefinition;
    }

    public ContainerTypeDefinition(final Class<?> containerClass) {
        this(new ByNameTypeDefinition(containerClass), null);
    }

    public TypeDefinition getContainerTypeDefinition() {
        return containerTypeDefinition;
    }

    @Override
    public boolean equals(final TypeMirror typeMirror) {
        final boolean result = containerTypeDefinition.equals(typeMirror);
        if (!result) {
            return false;
        }
        if (itemTypeDefinition != null && typeMirror instanceof DeclaredType) {
            final List<? extends TypeMirror> typeArguments = ((DeclaredType) typeMirror).getTypeArguments();
            if (typeArguments.size() == 1) {
                return itemTypeDefinition.equals(typeArguments.get(0));
            } else {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean equals(final Element element) {
        final boolean result = containerTypeDefinition.equals(element);
        if (!result) {
            return false;
        }
        return equals(element.asType());
    }

    @Override
    public String toString() {
        if (itemTypeDefinition == null) {
            return containerTypeDefinition.toString();
        } else {
            return format("?<?>", containerTypeDefinition, itemTypeDefinition);
        }
    }
}
