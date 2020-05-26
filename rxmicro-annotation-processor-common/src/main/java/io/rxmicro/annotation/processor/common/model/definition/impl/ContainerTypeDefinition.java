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

package io.rxmicro.annotation.processor.common.model.definition.impl;

import io.rxmicro.annotation.processor.common.model.definition.TypeDefinition;

import java.util.List;
import javax.lang.model.element.Element;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @since 0.1
 */
public final class ContainerTypeDefinition implements TypeDefinition {

    private final TypeDefinition ownerTypeDefinition;

    private final TypeDefinition itemTypeDefinition;

    public ContainerTypeDefinition(final TypeDefinition ownerTypeDefinition,
                                   final TypeDefinition itemTypeDefinition) {
        this.ownerTypeDefinition = require(ownerTypeDefinition);
        this.itemTypeDefinition = require(itemTypeDefinition);
    }

    public ContainerTypeDefinition(final Class<?> containerClass) {
        this.ownerTypeDefinition = new ByNameTypeDefinition(containerClass);
        this.itemTypeDefinition = null;
    }

    public TypeDefinition getOwnerTypeDefinition() {
        return ownerTypeDefinition;
    }

    @Override
    public boolean isEqual(final TypeMirror typeMirror) {
        final boolean result = ownerTypeDefinition.isEqual(typeMirror);
        if (!result) {
            return false;
        }
        if (itemTypeDefinition != null && typeMirror instanceof DeclaredType) {
            final List<? extends TypeMirror> typeArguments = ((DeclaredType) typeMirror).getTypeArguments();
            if (typeArguments.size() == 1) {
                return itemTypeDefinition.isEqual(typeArguments.get(0));
            } else {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isEqual(final Element element) {
        final boolean result = ownerTypeDefinition.isEqual(element);
        if (!result) {
            return false;
        }
        return isEqual(element.asType());
    }

    @Override
    public String toString() {
        if (itemTypeDefinition == null) {
            return ownerTypeDefinition.toString();
        } else {
            return format("?<?>", ownerTypeDefinition, itemTypeDefinition);
        }
    }
}
