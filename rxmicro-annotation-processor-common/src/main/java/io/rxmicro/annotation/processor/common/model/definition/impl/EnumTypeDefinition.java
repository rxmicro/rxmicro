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

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.type.TypeMirror;

import static io.rxmicro.annotation.processor.common.util.Elements.asEnumElement;

/**
 * @author nedis
 * @since 0.1
 */
public final class EnumTypeDefinition implements TypeDefinition {

    @Override
    public boolean isEqual(final TypeMirror typeMirror) {
        return asEnumElement(typeMirror).isPresent();
    }

    @Override
    public boolean isEqual(final Element element) {
        return element.getKind() == ElementKind.ENUM;
    }

    @Override
    public String toString() {
        return "? extends Enum<?>";
    }
}
